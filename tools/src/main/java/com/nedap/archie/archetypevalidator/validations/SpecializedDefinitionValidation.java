package com.nedap.archie.archetypevalidator.validations;

import com.nedap.archie.aom.ArchetypeModelObject;
import com.nedap.archie.aom.ArchetypeSlot;
import com.nedap.archie.aom.CArchetypeRoot;
import com.nedap.archie.aom.CAttribute;
import com.nedap.archie.aom.CComplexObject;
import com.nedap.archie.aom.CComplexObjectProxy;
import com.nedap.archie.aom.CObject;
import com.nedap.archie.aom.CPrimitiveObject;
import com.nedap.archie.aom.utils.AOMUtils;
import com.nedap.archie.aom.utils.NodeIdUtil;
import com.nedap.archie.aom.utils.RedefinitionStatus;
import com.nedap.archie.archetypevalidator.ErrorType;
import com.nedap.archie.archetypevalidator.ValidatingVisitor;
import com.nedap.archie.rminfo.ModelInfoLookup;

public class SpecializedDefinitionValidation extends ValidatingVisitor {
    public SpecializedDefinitionValidation(ModelInfoLookup lookup) {
        super(lookup);
    }

    @Override
    public void validate() {
        //only run these if the archetype is specialized and the parent has been found and flattened
        if(archetype.isSpecialized() && flatParent != null) {
            super.validate();
        } else if (archetype.isSpecialized() && flatParent == null) {
            addMessage(ErrorType.VASID, "parent archetype not found or can not be flattened");
        }
    }

    @Override
    public void validate(CObject cObject) {
        boolean nodeOk = checkSpecializedNodeHasMatchingPathInParent(cObject);
        if(nodeOk) {
            checkSpecializedNode(cObject);
        }

    }

    private void checkSpecializedNode(CObject cObject) {
        String flatPath = AOMUtils.pathAtSpecializationLevel(cObject.getPathSegments(), flatParent.specializationDepth());
        CObject parentCObject = getCObject(flatParent.itemAtPath(flatPath));

        if(parentCObject == null) {
            System.err.println("KAPOT!");
        }
        else if(cObject instanceof CArchetypeRoot && parentCObject instanceof ArchetypeSlot) {

        } else if (cObject instanceof ArchetypeSlot && parentCObject instanceof ArchetypeSlot) {

        } else if(cObject instanceof CArchetypeRoot && parentCObject instanceof  CArchetypeRoot) {

        } else if (cObject instanceof CComplexObject && parentCObject instanceof CComplexObjectProxy) {

        } else if (parentCObject instanceof  CComplexObject && ((CComplexObject) parentCObject).isAnyAllowed()) {
            //any allowed in parent, so fine here!
        } else if (!cObject.getClass().equals(parentCObject.getClass())) {
            addMessageWithPath(ErrorType.VSONT, cObject.path());
        }
    }

    /**
     * Complicated method - does validations and returns true if the given node has a matching path in the parent
     * @param cObject
     * @return
     */
    private boolean checkSpecializedNodeHasMatchingPathInParent(CObject cObject) {
        boolean result = false;
        if(cObject.isRootNode() || !cObject.getParent().isSecondOrderConstrained()) {
            if(AOMUtils.getSpecializationDepthFromCode(cObject.getNodeId()) <= flatParent.specializationDepth()
                    || new NodeIdUtil(cObject.getNodeId()).isRedefined()) {
                if(!AOMUtils.isPhantomPathAtLevel(cObject.getPathSegments(), flatParent.specializationDepth())) {
                    String flatPath = AOMUtils.pathAtSpecializationLevel(cObject.getPathSegments(), flatParent.specializationDepth());
                    CObject parentCObject = getCObject(flatParent.itemAtPath(flatPath));
                    result = parentCObject != null;
                    if(parentCObject != null) {
                        if(cObject.isProhibited()) {
                            if(!cObject.getClass().equals(parentCObject.getClass())) {
                                addMessage(ErrorType.VSONPT, cObject.path());
                            } else if(!parentCObject.getNodeId().equals(cObject.getNodeId())) {
                                addMessage(ErrorType.VSONPI, cObject.path());
                            }
                        }
                    } else if(!(cObject instanceof CPrimitiveObject)) {
                        addMessage(ErrorType.VSONIN, cObject.path());
                    }
                } else if(AOMUtils.getSpecialisationStatusFromCode(cObject.getNodeId(), cObject.specialisationDepth()) == RedefinitionStatus.REDEFINED) {
                    //TODO method in if not yet implemented
                    addMessage(ErrorType.VSONIN, cObject.path());
                }
            } else {
                //special checks if it is a non-overlay node...
                //- if it has a sibling order, check that the sibling order refers to a valid node in the flat ancestor.
                if(cObject.getSiblingOrder() != null) {
                    CAttribute parentAttribute = flatParent.itemAtPath(AOMUtils.pathAtSpecializationLevel(cObject.getParent().getPathSegments(), flatParent.specializationDepth()));
                    if(parentAttribute != null) { //null check is handled by VDIFP
                        //TODO: although this is a bit strange, nodeid could be unspecialized, parent can be specialized
                        CObject child = parentAttribute.getChild(cObject.getSiblingOrder().getSiblingNodeId());
                        if (child == null) {
                            addMessage(ErrorType.VSSM, cObject.path());
                        }
                    }
                }

                if(cObject.isProhibited()) {
                    //should be 'attribute_at_path'

                    addMessage(ErrorType.VSONPO, cObject.path());
                }
            }
        }//else in eiffel code is separate cattribute method here
        return result;
    }

    private CObject getCObject(ArchetypeModelObject archetypeModelObject) {
        if(archetypeModelObject instanceof CAttribute) {
            CAttribute attribute = (CAttribute) archetypeModelObject;
            if(attribute.getChildren().size() == 1) {
                return attribute.getChildren().get(0);
            }//TODO: add a numeric identifier to the getPath() method in CObject so this can be deleted and actually works in all cases!
        } else if(archetypeModelObject instanceof CObject) {
            return (CObject) archetypeModelObject;
        }
        return null;
    }


    @Override
    public void validate(CAttribute attribute) {

    }


}
