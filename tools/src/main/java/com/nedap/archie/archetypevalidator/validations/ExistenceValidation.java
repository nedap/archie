package com.nedap.archie.archetypevalidator.validations;

import com.nedap.archie.aom.CAttribute;
import com.nedap.archie.archetypevalidator.ErrorType;
import com.nedap.archie.archetypevalidator.ValidatingVisitor;
import com.nedap.archie.base.MultiplicityInterval;
import com.nedap.archie.rminfo.ModelInfoLookup;

/**
 * Phase 0 validator on existence
 */
public class ExistenceValidation extends ValidatingVisitor {
    public ExistenceValidation(ModelInfoLookup lookup) {
        super(lookup);
    }

    @Override
    public void validate(CAttribute attribute) {

        MultiplicityInterval existence = attribute.getExistence();
        if(existence != null) {
            if(existence.getLower() == null || existence.getUpper() == null || existence.isUpperUnbounded() ||
                    existence.isLowerUnbounded() || !existence.isLowerIncluded() || !existence.isUpperIncluded()) {
                //not good, can only be a very limited set
                addMessageWithPath(ErrorType.SEXLMG, attribute.path());
            } else {
                if(existence.getLower() == 0) {
                  if(existence.getUpper() != 0 && existence.getUpper() != 1) {
                      addMessageWithPath(ErrorType.SEXLU, attribute.path());
                  }
                } else if(existence.getLower() == 1) {
                    if(existence.getUpper() != 1) {
                        addMessageWithPath(ErrorType.SEXLU, attribute.path());
                    }
                }
            }
        }
    }

}
