package com.nedap.archie.archetypevalidator.validations;

import com.nedap.archie.aom.CAttribute;
import com.nedap.archie.aom.CComplexObject;
import com.nedap.archie.archetypevalidator.ErrorType;
import com.nedap.archie.archetypevalidator.ValidatingVisitor;
import com.nedap.archie.archetypevalidator.ValidationMessage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by pieter.bos on 31/03/2017.
 */
public class AttributeUniquenessValidation extends ValidatingVisitor {

    @Override
    public List<ValidationMessage> validate(CComplexObject cObject) {
        List<ValidationMessage> result = new ArrayList<>();
        HashSet<String> attributeNames = new HashSet<>();
        for(CAttribute attribute:cObject.getAttributes()) {
            if (attributeNames.contains(attribute.getRmAttributeName())) {
                result.add(new ValidationMessage(ErrorType.VCATU, attribute.getPath()));
            }
            attributeNames.add(attribute.getRmAttributeName());
        }
        return result;
    }


}