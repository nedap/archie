package com.nedap.archie.archetypevalidator.validations;

import com.nedap.archie.aom.CAttribute;
import com.nedap.archie.aom.CComplexObject;
import com.nedap.archie.aom.CComplexObjectProxy;
import com.nedap.archie.archetypevalidator.ErrorType;
import com.nedap.archie.archetypevalidator.ValidatingVisitor;
import com.nedap.archie.base.Cardinality;
import com.nedap.archie.flattener.ComplexObjectProxyReplacement;

public class FlatFormValidation extends ValidatingVisitor {

    @Override
    protected void validate(CComplexObjectProxy cObject) {
        //validate that CComplexObjectProxy nodes have a valid path
        ComplexObjectProxyReplacement complexObjectProxyReplacement = ComplexObjectProxyReplacement.getComplexObjectProxyReplacement(cObject);
        if(complexObjectProxyReplacement == null) {
            addMessageWithPath(ErrorType.VUNP, cObject.path());
        } else {
            CComplexObject replacement = complexObjectProxyReplacement.getReplacement();
            if(!combinedModels.rmTypesConformant(replacement.getRmTypeName(), cObject.getRmTypeName())) {
                addMessageWithPath(ErrorType.VUNT, cObject.path());
            }
        }
    }

    @Override
    protected void validate(CAttribute cAttribute) {
        Cardinality cardinality = cAttribute.getCardinality();
        if(cardinality != null && !cardinality.getInterval().isUpperUnbounded()) {
            if(cAttribute.getAggregateOccurrencesLowerSum() > cardinality.getInterval().getUpper()) {
                addWarningWithPath(ErrorType.WACMCL, cAttribute.path());
            } else if (cAttribute.getMinimumChildCount() > cardinality.getInterval().getUpper()) {
                addMessageWithPath(ErrorType.VACMCO, cAttribute.path());
            }
        }
    }
}
