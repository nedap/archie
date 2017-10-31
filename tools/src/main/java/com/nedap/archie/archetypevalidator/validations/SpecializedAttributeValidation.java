package com.nedap.archie.archetypevalidator.validations;

import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.CAttribute;
import com.nedap.archie.aom.CObject;
import com.nedap.archie.aom.utils.AOMUtils;
import com.nedap.archie.archetypevalidator.ErrorType;
import com.nedap.archie.rminfo.ModelInfoLookup;

/**
 * Not a full validation, but just the implementation for CAttribute validation as part of SpecializedDefinitionVBalidation to not end up with huge hard to read files
 */
public class SpecializedAttributeValidation {


    public boolean validateTest(CAttribute attribute, SpecializedDefinitionValidation validation) {
        if(!attribute.isSecondOrderConstrained()) {
            return !AOMUtils.isPhantomPathAtLevel(attribute.getPathSegments(), validation.getFlatParent().specializationDepth()) &&
                validation.getFlatParent().hasPath(AOMUtils.pathAtSpecializationLevel(attribute.getPathSegments(), validation.getFlatParent().specializationDepth()));
        }
        return false;
    }

    public void validate(CAttribute attribute, SpecializedDefinitionValidation validation) {
        Archetype flatParent = validation.getFlatParent();
        CAttribute parentAttribute = flatParent.itemAtPath(AOMUtils.pathAtSpecializationLevel(attribute.getPathSegments(), flatParent.specializationDepth()));
        if(!attribute.cConformsTo(parentAttribute)) {

          if(attribute.isMultiple() != parentAttribute.isMultiple()) {
              validation.addMessageWithPath(ErrorType.VSAM, attribute.path());
          }
        }
    }
}
