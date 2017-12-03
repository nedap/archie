package com.nedap.archie.flattener;

import com.nedap.archie.aom.ArchetypeModelObject;
import com.nedap.archie.aom.CAttribute;
import com.nedap.archie.aom.CAttributeTuple;
import com.nedap.archie.aom.CComplexObject;
import com.nedap.archie.aom.CComplexObjectProxy;
import com.nedap.archie.aom.CObject;
import com.nedap.archie.aom.CPrimitiveObject;
import com.nedap.archie.aom.CPrimitiveTuple;
import com.nedap.archie.aom.SiblingOrder;
import com.nedap.archie.aom.utils.AOMUtils;
import com.nedap.archie.base.MultiplicityInterval;
import com.nedap.archie.paths.PathSegment;
import com.nedap.archie.paths.PathUtil;
import com.nedap.archie.query.AOMPathQuery;
import com.nedap.archie.query.APathQuery;
import com.nedap.archie.rminfo.RMAttributeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Flattens attributes, taking sibling order into account.
 */
class CAttributeFlattener {

    private final Flattener flattener;

    public CAttributeFlattener(Flattener flattener) {
        this.flattener = flattener;
    }

    protected void flattenSingleAttribute(CComplexObject newObject, CAttribute attribute) {
        if(attribute.getDifferentialPath() != null) {
            //this overrides a specific path
            ArchetypeModelObject object = newObject.itemAtPath(attribute.getDifferentialPath());
            if(object == null) {
                //it is possible that the object points to a reference, in which case we need to clone the referenced node, then try again
                //AOM spec paragraph 7.2: 'proxy reference targets are expanded inline if the child archetype overrides them.'
                //also examples in ADL2 spec about internal references
                //so find the internal references here!
                //TODO: AOMUtils.pathAtSpecializationLevel(pathSegments.subList(0, pathSegments.size()-1), flatParent.specializationDepth());
                CComplexObjectProxy internalReference = new AOMPathQuery(attribute.getDifferentialPath()).findAnyInternalReference(newObject);
                if(internalReference != null) {
                    //in theory this can be a use node within a use node.
                    ComplexObjectProxyReplacement complexObjectProxyReplacement =
                            ComplexObjectProxyReplacement.getComplexObjectProxyReplacement(internalReference);
                    if(complexObjectProxyReplacement != null) {
                        complexObjectProxyReplacement.replace();
                        //and again!
                        flattenSingleAttribute(newObject, attribute);
                    } else {
                        throw new RuntimeException("cannot find target in CComplexObjectProxy");
                    }
                } else {
                    //lookup the parent and try to add the last attribute if it does not exist
                    List<PathSegment> pathSegments = new APathQuery(attribute.getDifferentialPath()).getPathSegments();
                    String pathMinusLastNode = PathUtil.getPath(pathSegments.subList(0, pathSegments.size()-1));
                    CObject parentObject = newObject.itemAtPath(pathMinusLastNode);
                    if(parentObject != null && parentObject instanceof CComplexObject) {
                        //attribute does not exist, but does exist in RM (or it would not have passed the ArchetypeValidator, or the person using
                        //this flattener does not care
                        CAttribute realAttribute = new CAttribute(pathSegments.get(pathSegments.size()-1).getNodeName());
                        ((CComplexObject) parentObject).addAttribute(realAttribute);
                        flattenAttribute(newObject, realAttribute, attribute);
                    }

                }
            }
            else if(object instanceof CAttribute) {
                CAttribute realAttribute = (CAttribute) object;
                flattenAttribute(newObject, realAttribute, attribute);
            } else if (object instanceof CObject) {
                //TODO: what does this mean?
            }

        } else {
            //this overrides the same path
            flattenAttribute(newObject, newObject.getAttribute(attribute.getRmAttributeName()), attribute);
        }
    }

    protected void flattenAttribute(CComplexObject root, CAttribute attributeInParent, CAttribute attributeInSpecialization) {
        if(attributeInParent == null) {
            CAttribute childCloned = attributeInSpecialization.clone();
            root.addAttribute(childCloned);
        } else {

            attributeInParent.setExistence(FlattenerUtil.getPossiblyOverridenValue(attributeInParent.getExistence(), attributeInSpecialization.getExistence()));
            attributeInParent.setCardinality(FlattenerUtil.getPossiblyOverridenValue(attributeInParent.getCardinality(), attributeInSpecialization.getCardinality()));

            if (attributeInSpecialization.getChildren().size() > 0 && attributeInSpecialization.getChildren().get(0) instanceof CPrimitiveObject) {
                //in case of a primitive object, just replace all nodes
                attributeInParent.setChildren(attributeInSpecialization.getChildren());
            } else {

                //ordering the children correctly is tricky.
                // First reorder parentCObjects if necessary, in the case that a sibling order refers to a redefined node
                // in the attributeInSpecialization archetype
                reorderSiblingOrdersReferringToSameLevel(attributeInSpecialization);

                //Now maintain an insertion anchor
                //for when a sibling node has been set somewhere.
                // insert everything after/before the anchor if it is set,
                // at the defined position from the spec if it is null
                SiblingOrder anchor = null;

                List<CObject> parentCObjects = attributeInParent.getChildren();

                for (CObject specializedChildCObject : attributeInSpecialization.getChildren()) {

                    //find matching attributeInParent and create the child node with it
                    CObject matchingParentObject = findMatchingParentCObject(specializedChildCObject, parentCObjects);


                    if(specializedChildCObject.getSiblingOrder() != null) {
                        //new sibling order, update the anchor
                        anchor = specializedChildCObject.getSiblingOrder();
                    }

                    if (anchor != null) {
                        mergeObjectIntoAttribute(attributeInParent, specializedChildCObject, matchingParentObject, attributeInSpecialization.getChildren(), anchor);
                        anchor = nextAnchor(anchor, specializedChildCObject);
                    } else { //no sibling order
                        CObject specializedObject = flattener.createSpecializeCObject(attributeInParent, matchingParentObject, specializedChildCObject);
                        if(matchingParentObject == null) {
                            //extension nodes should be added to the last position
                            attributeInParent.addChild(specializedObject);
                        } else {
                            attributeInParent.addChild(specializedObject, SiblingOrder.createAfter(findLastSpecializedChildDirectlyAfter(attributeInParent, matchingParentObject)));
                            if(shouldRemoveParent(specializedChildCObject, matchingParentObject, attributeInSpecialization.getChildren())) {
                                //we should remove the attributeInParent
                                attributeInParent.removeChild(matchingParentObject.getNodeId());
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Given the last used siblingorder anchor and the last specialized object added, return the SiblingOrder where the
     * next specialized object should be added - if that next object does not have a new sibling order
     * @param lastAnchor
     * @param lastSpecializedObject
     * @return
     */
    private SiblingOrder nextAnchor(SiblingOrder lastAnchor, CObject lastSpecializedObject) {
        if(lastAnchor.isBefore()) {
            return lastAnchor;
        } else {
            return SiblingOrder.createAfter(lastSpecializedObject.getNodeId());
        }
    }

    /**
     * Add the specializedChildObject to the parentAttribute at the given siblingOrder. This method automatically checks if the matchingParentObject should be removed, and removes it if necessary.
     *
     * @param parentAttribute the attribute to add the new object to
     * @param specializedChildCObject the specialized object that should be merged into the parent object
     * @param matchingParentObject the matching parent CObject for the given specializedChildObject
     * @param allSpecializedChildren all the specialized children in the same container as specialedChildCObject
     * @param siblingOrder the sibling order where to add the specializedChild to. Directly adds, no preprocessing or anchor support in this method, you must do that before.
     */
    private void mergeObjectIntoAttribute(CAttribute parentAttribute, CObject specializedChildCObject, CObject matchingParentObject, List<CObject> allSpecializedChildren, SiblingOrder siblingOrder) {
        CObject specializedObject = flattener.createSpecializeCObject(parentAttribute, matchingParentObject, specializedChildCObject);
        if (shouldRemoveParent(specializedChildCObject, matchingParentObject, allSpecializedChildren)) {
            parentAttribute.removeChild(matchingParentObject.getNodeId());
        }
        parentAttribute.addChild(specializedObject, siblingOrder);
    }


    /**
     * If the following occurs:
     *
     * after[id3]
     * ELEMENT[id2]
     * ELEMENT[id3.1]
     *
     * Reorder it and remove sibling orders:
     *
     * ELEMENT[id3.1]
     * ELEMENT[id2]
     *
     * If sibling order do not refer to specialized nodes at this level, leaves them alone
     * @param parent the attribute to reorder the child nodes for
     */
    private void reorderSiblingOrdersReferringToSameLevel(CAttribute parent) {
        for(CObject cObject:new ArrayList<>(parent.getChildren())) {
            if(cObject.getSiblingOrder() != null) {
                String matchingNodeId = findCObjectMatchingSiblingOrder(cObject.getSiblingOrder(), parent.getChildren());
                if(matchingNodeId != null) {
                    parent.removeChild(cObject.getNodeId());
                    SiblingOrder siblingOrder = new SiblingOrder();
                    siblingOrder.setSiblingNodeId(matchingNodeId);
                    siblingOrder.setBefore(cObject.getSiblingOrder().isBefore());
                    parent.addChild(cObject, siblingOrder);
                    cObject.setSiblingOrder(null);//unset sibling order, it has been processed already
                }
            }
        }
    }

    /**
     * Find the CObject in the given list of cObjects that matches with the given sibling order
     * @param siblingOrder
     * @param cObjectList
     * @return
     */
    private String findCObjectMatchingSiblingOrder(SiblingOrder siblingOrder, List<CObject> cObjectList) {
        for(CObject object:cObjectList) {
            if(FlattenerUtil.isOverriddenIdCode(object.getNodeId(), siblingOrder.getSiblingNodeId())) {
                return object.getNodeId();
            }
        }
        return null;
    }

    /**
     * Give an attribute and a CObject that is a child of that attribute, find the node id of the last object that is
     * in the list of nodes directly after the child attribute that specialize the matching parent. If the parent
     * has not yet been specialized, returns the parent node id
     *
     * @param parent
     * @param matchingParentObject
     * @return
     */
    private String findLastSpecializedChildDirectlyAfter(CAttribute parent, CObject matchingParentObject) {
        int matchingIndex = parent.getIndexOfChildWithNodeId(matchingParentObject.getNodeId());
        String result = matchingParentObject.getNodeId();
        for(int i = matchingIndex+1; i < parent.getChildren().size(); i++) {
            if(FlattenerUtil.isOverriddenIdCode(parent.getChildren().get(i).getNodeId(), matchingParentObject.getNodeId())) {
                result = parent.getChildren().get(i).getNodeId();
            }
        }
        return result;
    }

    /**
     * For the given specialized CObject that is to be added to the archetype, specializing matchingParentObject, and given
     * the list of all specialized children of the same container as the given specialized cobject, return if the parent
     * should be removed from the resulting list of flattened CObjects after inserting the specialized check or not.
     *
     *
     * @param specializedChildCObject the specialized child object that is to be added
     * @param matchingParentObject the CObject that matches with the specializedChildObject. Can be null.
     * @param allSpecializedChildren all specialized children under the specializing child container
     * @return
     */
    private boolean shouldRemoveParent(CObject specializedChildCObject, CObject matchingParentObject, List<CObject> allSpecializedChildren) {
        if(matchingParentObject == null) {
            return false;
        }
        List<CObject> allMatchingChildren = new ArrayList<>();
        for (CObject specializedChild : allSpecializedChildren) {
            if (FlattenerUtil.isOverridenCObject(specializedChild, matchingParentObject)) {
                allMatchingChildren.add(specializedChild);
            }

        }
        //the last matching child should possibly replace the parent, the rest should just add
        //if there is just one child, that's fine, it should still work
        if(allMatchingChildren.get(allMatchingChildren.size()-1).getNodeId().equalsIgnoreCase(specializedChildCObject.getNodeId())) {
            return shouldReplaceParent(matchingParentObject, allMatchingChildren);
        }
        return false;
    }

    private boolean shouldReplaceParent(CObject parent, List<CObject> differentialNodes) {
        for(CObject differentialNode: differentialNodes) {
            if(differentialNode.getNodeId().equals(parent.getNodeId())) {
                //same node id, so no specialization
                return true;
            }
        }
        MultiplicityInterval occurrences = parent.effectiveOccurrences(flattener.getLookup()::referenceModelPropMultiplicity);
        //isSingle/isMultiple is tricky and not doable just in the parser. Don't use those
        if(isSingle(parent.getParent())) {
            return true;
        } else if(occurrences != null && occurrences.upperIsOne()) {
            //REFINE the parent node case 1, the parent has occurrences upper == 1
            return true;
        } else if (differentialNodes.size() == 1
                && differentialNodes.get(0).effectiveOccurrences(flattener.getLookup()::referenceModelPropMultiplicity).upperIsOne()) {
            //REFINE the parent node case 2, only one child with occurrences upper == 1
            return true;
        }
        return false;
    }

    private boolean isSingle(CAttribute attribute) {
        if(attribute != null && attribute.getParent() != null && attribute.getDifferentialPath() == null) {
            RMAttributeInfo attributeInfo = flattener.getLookup().getAttributeInfo(attribute.getParent().getRmTypeName(), attribute.getRmAttributeName());
            return attributeInfo != null && !attributeInfo.isMultipleValued();
        }
        return false;
    }

    /**
     * Find the matching parent CObject given a specialized child. REturns null if not found.
     * @param specializedChildCObject
     * @param parentCObjects
     * @return
     */
    private CObject findMatchingParentCObject(CObject specializedChildCObject, List<CObject> parentCObjects) {
        for (CObject parentCObject : parentCObjects) {
            if (FlattenerUtil.isOverridenCObject(specializedChildCObject, parentCObject)) {
                return parentCObject;
            }
        }
        return null;
    }


}
