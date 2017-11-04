package com.nedap.archie.archetypevalidator.validations;

import com.nedap.archie.aom.CAttribute;
import com.nedap.archie.aom.CComplexObject;
import com.nedap.archie.archetypevalidator.ErrorType;
import com.nedap.archie.archetypevalidator.ValidatingVisitor;
import com.nedap.archie.archetypevalidator.ValidationMessage;
import com.nedap.archie.rminfo.ModelInfoLookup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by pieter.bos on 31/03/2017.
 */
public class AttributeUniquenessValidation extends ValidatingVisitor {

    public AttributeUniquenessValidation() {
        super();
    }

    @Override
    public void validate(CComplexObject cObject) {
        HashSet<String> attributeNames = new HashSet<>();
        for(CAttribute attribute:cObject.getAttributes()) {
            if(attribute.getDifferentialPath() != null) {
                //with different paths we get in trouble if we do this. perhaps check for duplicate entire paths, or do
                //something complicated?
                continue;
            }
            if (attributeNames.contains(attribute.getRmAttributeName())) {
                addMessageWithPath(ErrorType.VCATU, attribute.getPath());
            }
            attributeNames.add(attribute.getRmAttributeName());
        }
    }


}