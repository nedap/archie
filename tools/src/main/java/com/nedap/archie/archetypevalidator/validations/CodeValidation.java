package com.nedap.archie.archetypevalidator.validations;

import com.nedap.archie.aom.CAttribute;
import com.nedap.archie.aom.CObject;
import com.nedap.archie.aom.CPrimitiveObject;
import com.nedap.archie.aom.primitives.CTerminologyCode;
import com.nedap.archie.aom.terminology.ValueSet;
import com.nedap.archie.aom.utils.AOMUtils;
import com.nedap.archie.archetypevalidator.ErrorType;
import com.nedap.archie.archetypevalidator.ValidatingVisitor;
import com.nedap.archie.archetypevalidator.ValidationMessage;
import com.nedap.archie.rminfo.ModelInfoLookup;
import com.nedap.archie.rminfo.RMAttributeInfo;

import java.util.ArrayList;
import java.util.List;

public class CodeValidation extends ValidatingVisitor {

    public CodeValidation() {
        super();
    }

    @Override
    public void validate(CObject cObject) {
        if(cObject instanceof CTerminologyCode) {
            validate((CTerminologyCode) cObject);
        }
        String nodeId = cObject.getNodeId();
        int codeSpecializationDepth = AOMUtils.getSpecializationDepthFromCode(nodeId);
        int archetypeSpecializationDepth = archetype.specializationDepth();
        if(codeSpecializationDepth > archetypeSpecializationDepth) {
            addMessageWithPath(ErrorType.VTSD, cObject.path());
        } else if (cObject.isRoot() || parentIsMultiple(cObject)) {
            if( (codeSpecializationDepth < archetypeSpecializationDepth && !flatParent.getTerminology().hasIdCode(nodeId)) ||
                    (codeSpecializationDepth == archetypeSpecializationDepth &&  !archetype.getTerminology().hasIdCode(nodeId))) {
                addMessageWithPath(ErrorType.VATID, cObject.path(), nodeId + " is not defined in terminology or parent terminology");
            }
        }
    }

    private boolean parentIsMultiple(CObject cObject) {
        if(cObject.getParent() != null) {

            CAttribute parent = cObject.getParent();
            CObject owningObject = parent.getParent();
            if (parent.getDifferentialPath() != null && flatParent != null) {
                CAttribute attributeFromParent = (CAttribute) AOMUtils.getDifferentialPathFromParent(flatParent, parent);
                if(attributeFromParent != null) {
                    owningObject = attributeFromParent.getParent();
                }

            }
            if(owningObject != null) {
                RMAttributeInfo attributeInfo = lookup.getAttributeInfo(owningObject.getRmTypeName(), parent.getRmAttributeName());
                return attributeInfo != null && attributeInfo.isMultipleValued();
            }
        }
        return false;
    }

    public void validate(CTerminologyCode cTerminologyCode) {
        //validate CTerminology codes
        int archetypeSpecializationDepth = archetype.specializationDepth();

        for(String constraint:cTerminologyCode.getConstraint()) {
            if(AOMUtils.isValueSetCode(constraint)) {
                int codeSpecializationDepth = AOMUtils.getSpecializationDepthFromCode(constraint);
                if(codeSpecializationDepth > archetypeSpecializationDepth) {
                    addMessageWithPath(ErrorType.VATCD, cTerminologyCode.path());
                } else if((codeSpecializationDepth < archetypeSpecializationDepth && !flatParent.getTerminology().hasValueSetCode(constraint)) ||
                        (codeSpecializationDepth== archetypeSpecializationDepth && !archetype.getTerminology().hasValueSetCode(constraint))) {
                    addMessageWithPath(ErrorType.VACDF, cTerminologyCode.path());
                } else if(cTerminologyCode.getAssumedValue() != null) {
                    ValueSet valueSet = archetype.getTerminology().getValueSets().get(constraint);
                    if(valueSet != null) {
                        if(!valueSet.getMembers().contains(cTerminologyCode.getAssumedValue().getCodeString())) {
                            addMessageWithPath(ErrorType.VATDA, cTerminologyCode.path());
                        }
                    }
                }


            } else if (AOMUtils.isValueCode(constraint)) {
                int codeSpecializationDepth = AOMUtils.getSpecializationDepthFromCode(constraint);
                if(codeSpecializationDepth > archetypeSpecializationDepth) {
                    addMessageWithPath(ErrorType.VATCD, cTerminologyCode.path());
                } else if((codeSpecializationDepth < archetypeSpecializationDepth && !flatParent.getTerminology().hasValueCode(constraint)) ||
                        (codeSpecializationDepth== archetypeSpecializationDepth && !archetype.getTerminology().hasValueCode(constraint))) {
                    addMessageWithPath(ErrorType.VATDF, cTerminologyCode.path());
                }
            }
        }

    }

}
