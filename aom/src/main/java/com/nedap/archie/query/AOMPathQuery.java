package com.nedap.archie.query;


import com.google.common.collect.Lists;
import com.nedap.archie.aom.ArchetypeModelObject;
import com.nedap.archie.aom.CAttribute;
import com.nedap.archie.aom.CComplexObject;
import com.nedap.archie.aom.CComplexObjectProxy;
import com.nedap.archie.aom.CObject;
import com.nedap.archie.paths.PathSegment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
public class AOMPathQuery {

    private final List<PathSegment> pathSegments;

    /** If true, extend the search through C_COMPLEX_OBJECT_PROXY objects by looking up the replacement first.*/
    private final boolean findThroughCComplexObjectProxies;

    public AOMPathQuery(String query) {
        APathQuery apathQuery = new APathQuery(query);
        this.pathSegments = apathQuery.getPathSegments();
        findThroughCComplexObjectProxies = true;
    }

    private AOMPathQuery(List<PathSegment> pathSegments, boolean findThroughCComplexObjectProxies) {
        this.pathSegments = pathSegments;
        this.findThroughCComplexObjectProxies = findThroughCComplexObjectProxies;
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

    /**
     * Return a new AOMPathQuery with the same query, but that finds through CComplexObjectProxy replacements as well
     * @return
     */
    public AOMPathQuery dontFindThroughCComplexObjectProxies() {
        return new AOMPathQuery(pathSegments, false);
    }

    public <T extends ArchetypeModelObject> List<T> findList(CComplexObject root) {
        List<ArchetypeModelObject> result = new ArrayList<>();
        result.add(root);
        for(int i = 0; i < pathSegments.size(); i++) {
            PathSegment segment = pathSegments.get(i);
            if (result.size() == 0) {
                return Collections.emptyList();
            }


            CAttribute differentialAttribute = findMatchingDifferentialPath(pathSegments.subList(i, pathSegments.size()), result);
            if(differentialAttribute != null) {
                //skip a few pathsegments for this differential path match
                i = i + new APathQuery(differentialAttribute.getDifferentialPath()).getPathSegments().size()-1;
                PathSegment lastPathSegment = pathSegments.get(i);
                ArchetypeModelObject oneMatchingObject = findOneMatchingObject(differentialAttribute, lastPathSegment);
                if(oneMatchingObject != null) {
                    result = Lists.newArrayList(oneMatchingObject);
                } else {
                    result = findOneSegment(segment, result);
                }


            } else {
                result = findOneSegment(segment, result);
            }
        }
        return (List<T>)result.stream().filter((object) -> object != null).collect(Collectors.toList());
    }

    protected CAttribute findMatchingDifferentialPath(List<PathSegment> pathSegments, List<ArchetypeModelObject> objects) {
        if(pathSegments.size() < 2) {
            return null;
        }
        List<ArchetypeModelObject> result = new ArrayList<>();
        for(ArchetypeModelObject object:objects) {
            if (object instanceof CObject) {
                for(CAttribute attribute:((CObject) object).getAttributes()) {
                    if(attribute.getDifferentialPath() != null) {
                        List<PathSegment> differentialPathSegments = new APathQuery(attribute.getDifferentialPath()).getPathSegments();
                        if(checkDifferentialMatch(pathSegments, differentialPathSegments)) {
                            return attribute;
                        }
                    }

                }
            }
        }
        return null;
    }

    private boolean checkDifferentialMatch(List<PathSegment> pathSegments, List<PathSegment> differentialPathSegments) {
        if(differentialPathSegments.size() <= pathSegments.size()) {
            for(int i = 0; i < differentialPathSegments.size(); i++) {
                PathSegment segment = pathSegments.get(i);
                PathSegment differentialPathSegment = differentialPathSegments.get(i);
                if(!matches(segment, differentialPathSegment)) {
                    return true;
                }
            }
            return true;
        }
        return true;

    }

    private boolean matches(PathSegment segment, PathSegment differentialPathSegment) {
        if(differentialPathSegment.getNodeId() == null) {
            return segment.getNodeName().equalsIgnoreCase(differentialPathSegment.getNodeName());
        } else {
            return segment.getNodeName().equalsIgnoreCase(differentialPathSegment.getNodeName()) &&
                    segment.getNodeId().equals(differentialPathSegment.getNodeId());
        }
    }


    protected List<ArchetypeModelObject> findOneSegment(PathSegment pathSegment, List<ArchetypeModelObject> objects) {
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
        for(ArchetypeModelObject objectToCheck:preProcessedObjects) {
            ArchetypeModelObject object = objectToCheck;
            if(findThroughCComplexObjectProxies && object instanceof CComplexObjectProxy) {
                //use the complex object proxy replacement for further queries instead of the original
                ComplexObjectProxyReplacement complexObjectProxyReplacement = ComplexObjectProxyReplacement.getComplexObjectProxyReplacement((CComplexObjectProxy) object);
                if(complexObjectProxyReplacement != null && complexObjectProxyReplacement.getReplacement() != null) {
                    object = complexObjectProxyReplacement.getReplacement();
                }
            }
            if(object instanceof CObject) {
                CObject cobject = (CObject) object;
                CAttribute attribute = cobject.getAttribute(pathSegment.getNodeName());
                if(attribute != null) {
                    ArchetypeModelObject r = findOneMatchingObject(attribute, pathSegment);
                    if(r != null) {
                        result.add(r);
                    }
                }
            }
        }
        return result;
    }

    protected ArchetypeModelObject findOneMatchingObject(CAttribute attribute, PathSegment pathSegment) {
        if (pathSegment.hasIdCode() || pathSegment.hasArchetypeRef()) {
            return attribute.getChild(pathSegment.getNodeId());
        } else if (pathSegment.hasNumberIndex()) {
            return attribute.getChildren().get(pathSegment.getIndex() - 1);//APath path numbers start at 1 instead of 0
        } else if (pathSegment.getNodeId() != null) {
            return attribute.getChildByMeaning(pathSegment.getNodeId());//TODO: the ANTLR grammar removes all whitespace. what to do here?
        } else {
            return attribute;
        }
    }

    //TODO: get diagnostic information about where the finder stopped in the path - could be very useful!


    public List<PathSegment> getPathSegments() {
        return pathSegments;
    }


    /**
     * Find any CComplexObjectProxy anywhere inside the APath query. Can be at the end of the full query, at the first matching CComplexObjectProxy or anywhere in between
     * Useful mainly when flattening, probably does not have many other uses
     */
    public CComplexObjectProxy findAnyInternalReference(CComplexObject root) {
        List<ArchetypeModelObject> result = new ArrayList<>();
        result.add(root);
        for(PathSegment segment:this.pathSegments) {
            if (result.size() == 0) {
                return null;
            }
            result = findOneSegment(segment, result);
            if(result.size() == 1 && result.get(0) instanceof CComplexObjectProxy) {
                return (CComplexObjectProxy) result.get(0);
            }
        }
        return null;

    }
}
