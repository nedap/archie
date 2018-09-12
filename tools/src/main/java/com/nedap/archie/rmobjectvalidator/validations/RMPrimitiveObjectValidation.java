package com.nedap.archie.rmobjectvalidator.validations;

import com.nedap.archie.aom.CPrimitiveObject;
import com.nedap.archie.query.RMObjectWithPath;
import com.nedap.archie.rminfo.ModelInfoLookup;
import com.nedap.archie.rmobjectvalidator.ConstraintToStringUtil;
import com.nedap.archie.rmobjectvalidator.RMObjectValidationMessage;
import com.nedap.archie.rmobjectvalidator.RMObjectValidationMessageIds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RMPrimitiveObjectValidation {
    public static List<RMObjectValidationMessage> validate(ModelInfoLookup lookup, List<RMObjectWithPath> rmObjects, String pathSoFar, CPrimitiveObject cobject) {
        if (cobject.getSocParent() != null) {
            //validate the tuple, not the primitive object directly
            return Collections.emptyList();
        }
        if (rmObjects.size() != 1) {
            List<RMObjectValidationMessage> result = new ArrayList<>();
            result.add(createValidationMessage(rmObjects, pathSoFar, cobject));
            return result;
        }
        Object rmObject = rmObjects.get(0).getObject();
        return validate_inner(lookup, rmObject, pathSoFar, cobject);
    }

    static List<RMObjectValidationMessage> validate_inner(ModelInfoLookup lookup, Object rmObject, String pathSoFar, CPrimitiveObject cobject) {
        List<RMObjectValidationMessage> result = new ArrayList<>();
        if (!cobject.isValidValue(lookup, rmObject)) {
            result.add(createValidationMessage(rmObject, pathSoFar, cobject));
        }
        return result;
    }

    private static RMObjectValidationMessage createValidationMessage(Object value, String pathSoFar, CPrimitiveObject cobject) {
        String constraint = ConstraintToStringUtil.primitiveObjectConstraintToString(cobject);
        String message = RMObjectValidationMessageIds.rm_INVALID_FOR_CONSTRAINT.getMessage(constraint, value.toString());
        return new RMObjectValidationMessage(cobject, pathSoFar, message);
    }
}
