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

        //TODO: this should also be with differentialPath != null, but NO idea how that would work
          if(attribute.getDifferentialPath() == null && attribute.isMultiple() != parentAttribute.isMultiple()) {
              validation.addMessageWithPath(ErrorType.VSAM, attribute.path());//cannot think of a case how this happens
          }
          else if(!attribute.existenceConformsTo(parentAttribute)) {
                validation.addMessageWithPath(ErrorType.VSANCE, attribute.path());
          } else if (!attribute.cardinalityConformsTo(parentAttribute)) {
              validation.addMessageWithPath(ErrorType.VSANCC, attribute.path());
          }
        }
    }
}
