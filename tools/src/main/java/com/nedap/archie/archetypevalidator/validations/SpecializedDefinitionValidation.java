package com.nedap.archie.archetypevalidator.validations;

import com.nedap.archie.aom.ArchetypeModelObject;
import com.nedap.archie.aom.CAttribute;
import com.nedap.archie.aom.CObject;
import com.nedap.archie.aom.CPrimitiveObject;
import com.nedap.archie.aom.utils.AOMUtils;
import com.nedap.archie.aom.utils.NodeIdUtil;
import com.nedap.archie.aom.utils.RedefinitionStatus;
import com.nedap.archie.archetypevalidator.ErrorType;
import com.nedap.archie.archetypevalidator.ValidatingVisitor;
import com.nedap.archie.paths.PathSegment;
import com.nedap.archie.rminfo.ModelInfoLookup;
import java.util.List;

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
        boolean nodeOk = checkSpecializedNode(cObject);
    }

    private boolean checkSpecializedNode(CObject cObject) {
        if(cObject.isRootNode() || !cObject.getParent().isSecondOrderConstrained()) {
            if(AOMUtils.getSpecializationDepthFromCode(cObject.getNodeId()) <= flatParent.specializationDepth()
                    || new NodeIdUtil(cObject.getNodeId()).isRedefined()) {
                if(!phantomPath(cObject.getPathSegments(), flatParent.specializationDepth())) {
                    String flatPath = AOMUtils.pathAtSpecializationLevel(cObject.getPathSegments(), flatParent.specializationDepth());
                    CObject parentCObject = getCObject(flatParent.itemAtPath(flatPath));
                    if(parentCObject != null) {
                        if(cObject.isProhibited()) {
                            if(!cObject.getClass().equals(parentCObject.getClass())) {
                                addMessage(ErrorType.VSONPT, cObject.path());
                            } else if(!parentCObject.getNodeId().equals(cObject.getNodeId())) {
                                addMessage(ErrorType.VSONPI, cObject.path());
                            }
                        }
                        return true;
                    } else if(!(cObject instanceof CPrimitiveObject)) {
                        addMessage(ErrorType.VSONIN, cObject.path());
                        return false;
                    } else {
                        return true;//i think?
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
        }//else in eiffel code is cattribute method below
        return true;
    }

    private CObject getCObject(ArchetypeModelObject archetypeModelObject) {
        if(archetypeModelObject instanceof CAttribute) {
            CAttribute attribute = (CAttribute) archetypeModelObject;
            if(attribute.getChildren().size() == 1) {
                return attribute.getChildren().get(0);
            }
        } else if(archetypeModelObject instanceof CObject) {
            return (CObject) archetypeModelObject;
        }
        return null;
    }


    @Override
    public void validate(CAttribute attribute) {

    }

    //check if the last node id in the path has a bigger specialization level than the specialization level of the parent
    //but it does a little loop to check if it happens somewhere else as well. ok...
    private boolean phantomPath(List<PathSegment> pathSegments, int specializationDepth) {
        for(int i = pathSegments.size()-1; i >=0; i--) {
            String nodeId = pathSegments.get(i).getNodeId();
            if(nodeId != null && AOMUtils.isValidIdCode(nodeId) && specializationDepth < AOMUtils.getSpecializationDepthFromCode(nodeId)) {
                return codeExistsAtLevel(nodeId, specializationDepth);
            }
        }
        return false;
    }

    private boolean codeExistsAtLevel(String nodeId, int specializationDepth) {
        NodeIdUtil nodeIdUtil = new NodeIdUtil(nodeId);
        int specializationDepthOfCode = AOMUtils.getSpecializationDepthFromCode(nodeId);
        if(specializationDepth > specializationDepthOfCode) {
            return true; //TODO: IMPLEMENT ME and check if code is valid
        }
        return false;
    }

    //1. is_phantom_path_at_level(path, level)
    //2.level = get the node id with the maximum specialization depth level from the path
    //
}
