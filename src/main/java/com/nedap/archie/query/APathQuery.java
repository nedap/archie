package com.nedap.archie.query;


import com.google.common.collect.Lists;
import com.nedap.archie.aom.ArchetypeModelObject;
import com.nedap.archie.aom.CAttribute;
import com.nedap.archie.aom.CComplexObject;
import com.nedap.archie.aom.CObject;
import com.nedap.archie.paths.PathSegment;
import com.nedap.archie.rm.archetyped.Locatable;
import com.nedap.archie.rminfo.ModelInfoLookup;
import com.nedap.archie.rminfo.RMAttributeInfo;
import com.nedap.archie.util.NamingUtil;
import com.nedap.archie.adlparser.antlr.XPathLexer;
import com.nedap.archie.adlparser.antlr.XPathParser;
import com.nedap.archie.adlparser.antlr.XPathParser.*;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * For now only accepts rather simple xpath-like expressions.
 *
 * The only queries fully supported at the moment are absolute queries with node ids, such as '/items[id1]/content[id2]/value'.
 *
 * Any expression after the ID-code, such as in '[id1 and name="ignored"] are currently ignored, but they parse and function
 * as long as you add the id-code as first part of the expression.
 *
 * Created by pieter.bos on 19/10/15.
 */
public class APathQuery {

    private List<PathSegment> pathSegments = new ArrayList<>();

    public APathQuery(String query) {
        XPathLexer lexer = new XPathLexer(new ANTLRInputStream(query));
        XPathParser parser = new XPathParser(new CommonTokenStream(lexer));
        LocationPathContext locationPathContext = parser.locationPath();
        AbsoluteLocationPathNorootContext absoluteLocationPathNorootContext = locationPathContext.absoluteLocationPathNoroot();
        if(absoluteLocationPathNorootContext == null) {
            throw new UnsupportedOperationException("relative xpath expressions not yet supported: " + query);
        }
        if(!absoluteLocationPathNorootContext.getTokens(XPathLexer.ABRPATH).isEmpty()) {
            throw new UnsupportedOperationException("absolute path starting with // not yet supported");
        }
        RelativeLocationPathContext relativeLocationPathContext = absoluteLocationPathNorootContext.relativeLocationPath();

        if(!relativeLocationPathContext.getTokens(XPathLexer.ABRPATH).isEmpty()) {
            throw new UnsupportedOperationException("relative path with // between steps not yet supported");
        }
        Pattern isDigit = Pattern.compile("\\d+");

        List<StepContext> stepContexts = relativeLocationPathContext.step();
        for(StepContext stepContext:stepContexts) {
            String nodeName = stepContext.nodeTest().getText();
            List<PredicateContext> predicateContexts = stepContext.predicate();
            PathSegment pathSegment = new PathSegment(nodeName);
            for(PredicateContext predicateContext:predicateContexts) {
                //TODO: this is not a full parser. We really need one. Find one because writing an XPath parser seems like a thing that's been done before.

                AndExprContext andExpressionContext = predicateContext.expr().orExpr().andExpr(0);
                for(EqualityExprContext equalityExprContext: andExpressionContext.equalityExpr()) {
                    if(equalityExprContext.relationalExpr().size() == 1) { //do not yet support equals or not equals operator, ignore for now
                        String expression = equalityExprContext.getText();
                        if(isDigit.matcher(expression).matches()) {
                            pathSegment.setIndex(Integer.parseInt(expression));
                        } else {
                            pathSegment.setNodeId(expression);
                        }
                    }

                }
            }
            pathSegments.add(pathSegment);
        }
    }

    public <T extends ArchetypeModelObject> T find(CComplexObject root) {
       List<T> list = findList(root);
        if(list.isEmpty()) {
            return null;
        } else if (list.size() == 1) {
            return list.get(0);
        } else {
            throw new UnsupportedOperationException("cannot find without list with more than 1 element");
        }
    }

    public <T extends ArchetypeModelObject> List<T> findList(CComplexObject root) {
        List<ArchetypeModelObject> result = new ArrayList<>();
        result.add(root);
        for(PathSegment segment:this.pathSegments) {
            if (result.size() == 0) {
                return Collections.emptyList();
            }
            result = findOneSegment(segment, result);
        }
        return (List<T>) result;
    }

    private List<ArchetypeModelObject> findOneSegment(PathSegment pathSegment, List<ArchetypeModelObject> objects) {
        List<ArchetypeModelObject> result = new ArrayList<>();

        List<ArchetypeModelObject> preProcessedObjects = new ArrayList<>();

        for(ArchetypeModelObject object:objects) {
            if (object instanceof CAttribute) {
                CAttribute cAttribute = (CAttribute) object;
                preProcessedObjects.addAll(cAttribute.getChildren());
            } else {
                preProcessedObjects.add(object);
            }

        }
        for(ArchetypeModelObject object:preProcessedObjects) {
            if(object instanceof CObject) {
                CObject cobject = (CObject) object;
                CAttribute attribute = cobject.getAttribute(pathSegment.getNodeName());
                if(pathSegment.hasIdCode() || pathSegment.hasArchetypeRef()) {
                    result.add(attribute.getChild(pathSegment.getNodeId()));
                } else if(pathSegment.hasNumberIndex()) {
                    result.add(attribute.getChildren().get(pathSegment.getIndex()-1));//APath path numbers start at 1 instead of 0
                } else if(pathSegment.getNodeId() != null) {
                    result.add(attribute.getChildByMeaning(pathSegment.getNodeId()));//TODO: the ANTLR grammar removes all whitespace. what to do here?
                } else {
                    result.add(attribute);
                }
            }
        }
        return result;
    }

    //TODO: get diagnostic information about where the finder stopped in the path - could be very useful!

    /**
     * Deprecated. Use find(ModelInfoLookup, object) instead. It has a fix for both performance and security problems
     * @param root
     * @param <T>
     * @return
     */
    @Deprecated
    public <T> T find(Object root) {
        //TODO: you can access undesired methods like the getClass().getClassLoader() methods with these queries
        //find a way to whitelist the resulting classes? Or switch to field-based queries?

        Object currentObject = root;
        try {
            for(PathSegment segment:pathSegments) {
                if(currentObject == null) {
                    return null;
                }
                Method method = currentObject.getClass().getMethod(NamingUtil.attributeNameToGetMethod(segment.getNodeName()));
                currentObject = method.invoke(currentObject);
                if(currentObject == null) {
                    return null;
                }
                if(currentObject instanceof Collection) {
                    Collection collection = (Collection) currentObject;
                    if(!segment.hasExpressions()) {
                        //TODO: check if this is correct
                        currentObject = collection;
                    } else {
                        currentObject = findRMObject(segment, collection);
                    }
                } else if(currentObject instanceof Locatable) {

                    if(segment.hasExpressions()) {
                        Locatable locatable = (Locatable) currentObject;
                        if(segment.hasIdCode()) {
                            if (!locatable.getArchetypeNodeId().equals(segment.getNodeId())) {
                                return null;
                            }
                        } else if (segment.hasNumberIndex()) {
                            int number = segment.getIndex();
                            if(number != 1) {
                                return null;
                            }
                        } else if (segment.hasArchetypeRef()) {
                            //operational templates in RM Objects have their archetype node ID set to an archetype ref. That
                            //we support. Other things not so much
                            if (!locatable.getArchetypeNodeId().equals(segment.getNodeId())) {
                                throw new IllegalArgumentException("cannot handle RM-queries with node names or archetype references yet");
                            }

                        }
                    }
                } else if (segment.hasNumberIndex()) {
                    int number = segment.getIndex();
                    if(number != 1) {
                        return null;
                    }
                } else {
                    //not a locatable, but that's fine
                    //in openehr, in archetypes everythign has node ids. Datavalues do not in the rm. a bit ugly if you ask
                    //me, but that's why there's no 'if there's a nodeId set, this won't match!' code here.
                }
            }
            return (T) currentObject;
        } catch (NoSuchMethodException e) {
            return null;
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Deprecated for querying RMObjects. Use RMQueryContext instead.
     * For querying CObjects there is no other solution yet.
     */
    @Deprecated
    public <T> T find(ModelInfoLookup lookup, Object root) {
        Object currentObject = root;
        try {
            for (PathSegment segment : pathSegments) {
                if (currentObject == null) {
                    return null;
                }
                RMAttributeInfo attributeInfo = lookup.getAttributeInfo(currentObject.getClass(), segment.getNodeName());
                if (attributeInfo == null) {
                    return null;
                }
                Method method = attributeInfo.getGetMethod();
                currentObject = method.invoke(currentObject);
                if (currentObject == null) {
                    return null;
                }
                if (currentObject instanceof Collection) {
                    Collection collection = (Collection) currentObject;
                    if (!segment.hasExpressions()) {
                        //TODO: check if this is correct
                        currentObject = collection;
                    } else {
                        currentObject = findRMObject(segment, collection);
                    }
                } else if (currentObject instanceof Locatable) {

                    if (segment.hasExpressions()) {
                        Locatable locatable = (Locatable) currentObject;
                        if (segment.hasIdCode()) {
                            if (!locatable.getArchetypeNodeId().equals(segment.getNodeId())) {
                                return null;
                            }
                        } else if (segment.hasNumberIndex()) {
                            int number = segment.getIndex();
                            if (number != 1) {
                                return null;
                            }
                        } else if (segment.hasArchetypeRef()) {
                            //operational templates in RM Objects have their archetype node ID set to an archetype ref. That
                            //we support. Other things not so much
                            if (!locatable.getArchetypeNodeId().equals(segment.getNodeId())) {
                                throw new IllegalArgumentException("cannot handle RM-queries with node names or archetype references yet");
                            }

                        }
                    }
                } else if (segment.hasNumberIndex()) {
                    int number = segment.getIndex();
                    if (number != 1) {
                        return null;
                    }
                } else {
                    //not a locatable, but that's fine
                    //in openehr, in archetypes everythign has node ids. Datavalues do not in the rm. a bit ugly if you ask
                    //me, but that's why there's no 'if there's a nodeId set, this won't match!' code here.
                }
            }
            return (T) currentObject;
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deprecated for querying RMObjects. Use RMQueryContext instead.
     * For querying CObjects there is no other solution yet.
     */
    @Deprecated
    public <T> List<RMObjectWithPath> findList(ModelInfoLookup lookup, Object root) {
        List<RMObjectWithPath> currentObjects = Lists.newArrayList(new RMObjectWithPath(root, "/"));
        try {
            for (PathSegment segment : pathSegments) {
                if(currentObjects.isEmpty()){
                    return Collections.emptyList();
                }
                List<RMObjectWithPath> newCurrentObjects = new ArrayList<>();

                for(int i = 0; i < currentObjects.size(); i++) {
                    RMObjectWithPath currentObject = currentObjects.get(i);
                    Object currentRMObject = currentObject.getObject();
                    RMAttributeInfo attributeInfo = lookup.getAttributeInfo(currentRMObject.getClass(), segment.getNodeName());
                    if (attributeInfo == null) {
                        continue;
                    }
                    Method method = attributeInfo.getGetMethod();
                    currentRMObject = method.invoke(currentRMObject);
                    String pathSeparator = "/";
                    if(currentObject.getPath().endsWith("/")) {
                        pathSeparator = "";
                    }
                    String newPath = currentObject.getPath() + pathSeparator + segment.getNodeName();

                    if (currentRMObject == null) {
                        continue;
                    }
                    if (currentRMObject instanceof Collection) {
                        Collection collection = (Collection) currentRMObject;
                        if (!segment.hasExpressions()) {
                            addAllFromCollection(newCurrentObjects, collection, newPath);
                        } else {
                            //TODO
                            newCurrentObjects.addAll(findRMObjectsWithPathCollection(segment, collection, newPath));
                        }
                    } else if (currentRMObject instanceof Locatable) {

                        if (segment.hasExpressions()) {
                            Locatable locatable = (Locatable) currentRMObject;
                            if (segment.hasIdCode()) {
                                if (!locatable.getArchetypeNodeId().equals(segment.getNodeId())) {
                                    continue;
                                }
                            } else if (segment.hasNumberIndex()) {
                                int number = segment.getIndex();
                                if (number != 1) {
                                    continue;
                                }
                            } else if (segment.hasArchetypeRef()) {
                                //operational templates in RM Objects have their archetype node ID set to an archetype ref. That
                                //we support. Other things not so much
                                if (!locatable.getArchetypeNodeId().equals(segment.getNodeId())) {
                                    throw new IllegalArgumentException("cannot handle RM-queries with node names or archetype references yet");
                                }

                            }
                            newCurrentObjects.add(createRMObjectWithPath(currentRMObject, newPath));
                        }
                    } else if (segment.hasNumberIndex()) {
                        int number = segment.getIndex();
                        if (number != 1) {
                            continue;
                        }
                    } else {
                        //not a locatable, but that's fine
                        //in openehr, in archetypes everythign has node ids. Datavalues do not in the rm. a bit ugly if you ask
                        //me, but that's why there's no 'if there's a nodeId set, this won't match!' code here.
                        newCurrentObjects.add(createRMObjectWithPath(currentRMObject, newPath));
                    }
                }
                currentObjects = newCurrentObjects;
            }
            return currentObjects;
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    private RMObjectWithPath createRMObjectWithPath(Object currentObject, String newPath) {
        String archetypeNodeId = getArchetypeNodeId(currentObject);
        String pathConstraint = buildPathConstraint(null, archetypeNodeId);
        return new RMObjectWithPath(currentObject, newPath + pathConstraint);
    }

    private String getArchetypeNodeId(Object rmObject) {
        if(rmObject instanceof Locatable) {
            Locatable o = (Locatable) rmObject;
            return o.getArchetypeNodeId();
        }
        return null;

    }

    /**
     * Add all the elements from the collection toAdd to the newCurrentObjects Lists.
     * basePath must be the path under which to add the elements, without the "[]" part
     * @param newCurrentObjects
     * @param toAdd
     * @param basePath
     */
    private void addAllFromCollection(List<RMObjectWithPath> newCurrentObjects, Collection toAdd, String basePath) {
        int index = 1;
        for(Object object:toAdd) {
            String constraint = buildPathConstraint(index, getArchetypeNodeId(object));
            newCurrentObjects.add(new RMObjectWithPath(object, basePath + constraint));
            index++;
        }
    }

    @NotNull
    private String buildPathConstraint(Integer index, String archetypeNodeId) {
        if(index == null && archetypeNodeId == null) {
            return "";//nothing to add
        }
        if(archetypeNodeId != null && index == null) {
            return "[" + archetypeNodeId + "]";
        }
        StringBuilder constraint = new StringBuilder("[");
        boolean first = true;
        if(archetypeNodeId != null) {
            constraint.append(archetypeNodeId);
            first = false;
        }
        if(index != null) {
            if(!first) {
                constraint.append(", ");
            }
            constraint.append(Integer.toString(index));
        }

        constraint.append("]");
        return constraint.toString();
    }

    private Collection<RMObjectWithPath> findRMObjectsWithPathCollection(PathSegment segment, Collection<Object> collection, String path) {

        if(segment.hasNumberIndex()) {
            int number = segment.getIndex();
            int i = 1;
            for(Object object:collection) {
                System.out.println("checking " + i + " with " + number);
                if(number == i) {
                    //TODO: check for other constraints as well
                    return Lists.newArrayList(new RMObjectWithPath(object, path + buildPathConstraint(i-1, getArchetypeNodeId(object))));
                }
                i++;
            }
        }
        List<RMObjectWithPath> result = new ArrayList<>();
        int i = 1;
        for(Object object:collection) {
            Locatable locatable = (Locatable) object;

            if (segment.hasIdCode()) {
                if (locatable.getArchetypeNodeId().equals(segment.getNodeId())) {
                    result.add(new RMObjectWithPath(object, path + buildPathConstraint(i, locatable.getArchetypeNodeId())));
                }
            } else if (segment.hasArchetypeRef()) {
                //operational templates in RM Objects have their archetype node ID set to an archetype ref. That
                //we support. Other things not so much
                if (locatable.getArchetypeNodeId().equals(segment.getNodeId())) {
                    result.add(new RMObjectWithPath(object, path + buildPathConstraint(i, locatable.getArchetypeNodeId())));
                }
                throw new IllegalArgumentException("cannot handle RM-queries with archetype references yet");
            } else {
                if(equalsName(locatable.getNameAsString(), segment.getNodeId())) {
                    result.add(new RMObjectWithPath(object, path + buildPathConstraint(i, locatable.getArchetypeNodeId())));
                }
            }
            i++;
        }
        return result;
    }

    private Object findRMObject(PathSegment segment, Collection collection) {

        if(segment.hasNumberIndex()) {
            int number = segment.getIndex();
            for(Object object:collection) {
                if(number == 1) {
                    return object;
                }
                number--;
            }
            return null;
        }
        for(Object o:collection) {
            Locatable locatable = (Locatable) o;

            if (segment.hasIdCode()) {
                if (locatable.getArchetypeNodeId().equals(segment.getNodeId())) {
                    return o;
                }
            } else if (segment.hasArchetypeRef()) {
                //operational templates in RM Objects have their archetype node ID set to an archetype ref. That
                //we support. Other things not so much
                if (locatable.getArchetypeNodeId().equals(segment.getNodeId())) {
                    return o;
                }
            } else {
                if(equalsName(locatable.getNameAsString(), segment.getNodeId())) {
                    return o;
                }
            }
        }
        return null;
    }

    private boolean equalsName(String name, String nameFromQuery) {
        //the grammar throws away whitespace. And it should, because it's kind of tricky otherwise. So match names without whitespace
        //TODO: should this be case sensitive?
        if(name == null) {
            return false;
        }
        name = name.replaceAll("( |\\t|\\n|\\r)+", "");
        return name.equalsIgnoreCase(nameFromQuery);

    }
}
