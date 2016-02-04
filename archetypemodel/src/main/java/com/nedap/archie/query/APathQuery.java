package com.nedap.archie.query;


import com.nedap.archie.rm.archetypes.Locatable;
import com.nedap.archie.rm.archetypes.Pathable;
import com.nedap.archie.util.NamingUtil;
import com.nedap.archie.xpath.antlr.XPathParser;
import com.nedap.archie.xpath.antlr.XPathParser.*;
import com.nedap.archie.xpath.antlr.XPathLexer;
import com.nedap.archie.aom.ArchetypeModelObject;
import com.nedap.archie.aom.CAttribute;
import com.nedap.archie.aom.CComplexObject;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import com.nedap.archie.paths.PathSegment;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * For now only accepts rather simple xpath-like expressions. TODO: get a proper parser library, there should be one out there, like jaxen (seems old?)
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
            throw new UnsupportedOperationException("relative xpath expressions not yet supported");
        }
        if(!absoluteLocationPathNorootContext.getTokens(XPathLexer.ABRPATH).isEmpty()) {
            throw new UnsupportedOperationException("absolute path starting with // not yet supported");
        }
        RelativeLocationPathContext relativeLocationPathContext = absoluteLocationPathNorootContext.relativeLocationPath();

        if(!relativeLocationPathContext.getTokens(XPathLexer.ABRPATH).isEmpty()) {
            throw new UnsupportedOperationException("relative path with // between steps not yet supported");
        }
        List<StepContext> stepContexts = relativeLocationPathContext.step();
        for(StepContext stepContext:stepContexts) {
            String nodeName = stepContext.nodeTest().getText();
            List<PredicateContext> predicateContexts = stepContext.predicate();
            String nodeId = null;
            for(PredicateContext predicateContext:predicateContexts) {
                //TODO: this is not a full parser. Find one because writing an XPath parser seems like a thing that's been done before.
                //this is a bit of a hack, but it makes sure that /items[id2 and name="ignored"] works. Only in that order
                nodeId = predicateContext.expr().orExpr().andExpr(0).equalityExpr().get(0).getText();
            }
            pathSegments.add(new PathSegment(nodeName, nodeId));
        }
    }

    public <T extends ArchetypeModelObject> T find(CComplexObject root) {
        ArchetypeModelObject currentObject = root;
        int i =0;
        for(PathSegment segment:pathSegments) {
            if(i >= pathSegments.size()) {
                return (T) currentObject;
            }
            CAttribute attribute = null;
            if(currentObject instanceof CComplexObject) {
                attribute = ((CComplexObject) currentObject).getAttribute(segment.getNodeName());
            } else {
                //erm.. right. we could do something with reflection and return a bean property/field/whatever?
                return null;
            }
            if(attribute == null) {
                return null;
            }
            currentObject = attribute;
            if(segment.getNodeId() == null) {
                if(i == pathSegments.size()-1) {
                    return (T) attribute;
                }
                continue;
            }
            if(segment.hasIdCode() || segment.hasArchetypeRef()) {
                currentObject = attribute.getChild(segment.getNodeId());
            } else if(segment.hasNumberIndex()) {
                currentObject = attribute.getChildren().get(Integer.parseInt(segment.getNodeId())-1);//APath path numbers start at 1 instead of 0
            } else {
                currentObject = attribute.getChildByMeaning(segment.getNodeId());//TODO: the ANTLR grammar removes all whitespace. what to do here?
            }
            if(currentObject == null) {
                return null;
            }

            i++;
        }
        return (T) currentObject;
    }

    //TODO: get diagnostic information about where the finder stopped in the path - could be very useful!
    public <T> T find(Pathable root) {
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
                    if(segment.getNodeId() == null) {
                        //TODO: check if this is correct
                        currentObject = collection;
                    } else {
                        currentObject = findRMObject(segment, collection);
                    }
                } else if(currentObject instanceof Locatable) {

                    if(segment.getNodeId() != null) {
                        Locatable locatable = (Locatable) currentObject;
                        if(segment.hasIdCode()) {
                            if (!locatable.getArchetypeNodeId().equals(segment.getNodeId())) {
                                return null;
                            }
                        } else if (segment.hasNumberIndex()) {
                            int number = Integer.parseInt(segment.getNodeId());
                            if(number != 1) {
                                return null;
                            }
                        } else {
                            throw new IllegalArgumentException("cannot handle RM-queries with node names or archetype references yet");
                        }
                    }
                } else if (segment.hasNumberIndex()) {
                    int number = Integer.parseInt(segment.getNodeId());
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

    private Object findRMObject(PathSegment segment, Collection collection) {

        if(segment.hasNumberIndex()) {
            int number = Integer.parseInt(segment.getNodeId());
            for(Object object:collection) {
                if(number == 1) {
                    return object;
                }
                number--;
            }
        }
        for(Object o:collection) {
            Locatable locatable = (Locatable) o;

            if (segment.hasIdCode()) {
                if (locatable.getArchetypeNodeId().equals(segment.getNodeId())) {
                    return o;
                }
            } else if (segment.hasArchetypeRef()) {
                throw new IllegalArgumentException("cannot handle RM-queries with archetype references yet");
            } else {
                if(equalsName(locatable.getName(), segment.getNodeId())) {
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
