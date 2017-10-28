package com.nedap.archie.archetypevalidator.validations;

import com.nedap.archie.aom.CAttribute;
import com.nedap.archie.aom.CComplexObject;
import com.nedap.archie.aom.CObject;
import com.nedap.archie.aom.CPrimitiveObject;
import com.nedap.archie.archetypevalidator.ErrorType;
import com.nedap.archie.archetypevalidator.ValidatingVisitor;
import com.nedap.archie.archetypevalidator.ValidationMessage;
import com.nedap.archie.rminfo.ModelInfoLookup;
import com.nedap.archie.rminfo.RMAttributeInfo;
import com.nedap.archie.rminfo.RMTypeInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by pieter.bos on 31/03/2017.
 */
public class ModelConformanceValidation extends ValidatingVisitor {


    public ModelConformanceValidation(ModelInfoLookup lookup) {
        super(lookup);
    }
    
    @Override
    protected void validate(CComplexObject cObject) {

        RMTypeInfo typeInfo = lookup.getTypeInfo(cObject.getRmTypeName());
        if (typeInfo == null) {
            addMessageWithPath(ErrorType.VCORM, cObject.getPath(), cObject.getRmTypeName());
        } else {
            CAttribute owningAttribute = cObject.getParent();
            if (owningAttribute != null) { //at path "/" there will be no owning attribute
                CObject owningObject = owningAttribute.getParent();

                if (owningObject != null) {
                    RMAttributeInfo owningAttributeInfo = lookup.getAttributeInfo(owningObject.getRmTypeName(), owningAttribute.getRmAttributeName());
                    if (owningAttributeInfo != null) {//this case is another validation, see the validate(cattribute) method of this class
                        Class typeInCollection = owningAttributeInfo.getTypeInCollection();
                        if (!typeInCollection.isAssignableFrom(typeInfo.getJavaClass())) {
                            addMessageWithPath(ErrorType.VCORMT, cObject.getPath(),
                                    owningAttributeInfo.getTypeInCollection() + " is not assignable from " + typeInfo.getRmName() +
                                            ", at type.attributeName: " + owningObject.getRmTypeName() + "." + owningAttribute.getRmAttributeName());
                        }
                    }
                }
            }
        }
    }

    // TODO: check the type of a CPrimitiveObject with respect to CAttribute, as pat of VCORMT.
    @Override
    protected void validate(CPrimitiveObject cObject) {

    }


    @Override
    public void validate(CAttribute cAttribute) {
        CObject owningObject = cAttribute.getParent();
        if(owningObject != null) {
            if(cAttribute.getDifferentialPath() == null) {
                RMAttributeInfo attributeInfo = lookup.getAttributeInfo(owningObject.getRmTypeName(), cAttribute.getRmAttributeName());
                if (attributeInfo == null) {
                    addMessageWithPath(ErrorType.VCARM, cAttribute.getPath(), cAttribute.getRmAttributeName() + " is not a known attribute of " + owningObject.getRmTypeName() + " or it is has not been implemented in Archie");
                } else {
                    //TODO: VCAEX and VCAM, multiplicity and existence
                }
            } //todo: flat parent.
        }
    }
}
