package com.nedap.archie.rmobjectvalidator;

import com.nedap.archie.aom.CPrimitiveObject;
import com.nedap.archie.base.Interval;
import org.openehr.utils.message.I18n;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Utility class to convert constraints to human readable constraints.
 */
public class ConstraintToStringUtil {
    /**
     * Convert constraint of Primitive object to human readable string.
     * @param cPrimitiveObject Primitive object
     * @return Human readable constraint
     */
    public static String primitiveObjectConstraintToString(CPrimitiveObject cPrimitiveObject) {
        return constraintListToString(cPrimitiveObject.getConstraint());
    }

    /**
     * Convert a constraint list to a human readable string.
     * @param constraint Constraint list
     * @return Human readable constraint
     */
    private static String constraintListToString(List<?> constraint) {
        if(constraint.isEmpty()) {
            return I18n.t("anything");
        }

        String delimiter = I18n.t(" or ");
        return constraint.stream()
            .map(ConstraintToStringUtil::constraintElementToString)
            .collect(Collectors.joining(delimiter));
    }

    /**
     * Convert a constraint element to a human readable string.
     * @param element Constraint element.
     * @return Human readable constraint element
     */
    private static String constraintElementToString(Object element) {
        String result;
        if (element instanceof Interval) {
            result = intervalToString((Interval<?>) element);
        } else if (element instanceof String) {
            result = '"' + ((String) element).replace("\"", "\\\"") + '"';
        } else {
            result = element.toString();
        }
        return result;
    }

    /**
     * Convert an interval to a human readable string.
     * @param interval Interval
     * @return Human readable interval
     */
    public static String intervalToString(Interval<?> interval) {
        String result;

        if (interval.isLowerUnbounded() && interval.isUpperUnbounded()) {
            // value unbounded
            result = I18n.t("anything");
        } else if (interval.isLowerUnbounded()) {
            result = upperBoundToString(interval);
        } else if (interval.isUpperUnbounded()) {
            result = lowerBoundToString(interval);
        } else {
            // lower and upper bounded
            if (interval.isLowerIncluded() && interval.isUpperIncluded() &&
                    Objects.equals(interval.getLower(), interval.getUpper())) {
                // lower == value == upper
                result = I18n.t("equal to {0}", interval.getLower());
            } else {
                result = I18n.t("{0} and {1}", lowerBoundToString(interval), upperBoundToString(interval));
            }
        }

        return result;
    }

    private static String lowerBoundToString(Interval<?> interval) {
        String result;
        if (interval.isLowerIncluded()) {
            // lower <= value
            result = I18n.t("greater than or equal to {0}", interval.getLower());
        } else {
            // lower < value
            result = I18n.t("greater than {0}", interval.getLower());
        }
        return result;
    }

    private static String upperBoundToString(Interval<?> interval) {
        String result;
        if (interval.isUpperIncluded()) {
            // value <= upper
            result = I18n.t("less than or equal to {0}", interval.getUpper());
        } else {
            // value < upper
            result = I18n.t("less than {0}", interval.getUpper());
        }
        return result;
    }

}
