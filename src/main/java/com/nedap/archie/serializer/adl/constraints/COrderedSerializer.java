package com.nedap.archie.serializer.adl.constraints;

import com.nedap.archie.aom.primitives.COrdered;
import com.nedap.archie.base.Interval;
import com.nedap.archie.serializer.adl.ADLDefinitionSerializer;

/**
 * @author markopi
 */
abstract public class COrderedSerializer<T extends COrdered<?>> extends ConstraintSerializer<T> {
    public COrderedSerializer(ADLDefinitionSerializer serializer) {
        super(serializer);
    }

    @Override
    public final void serialize(T cobj) {
        int original = builder.mark();

        serializeBefore(cobj);
        serializeConstraintIntervals(cobj);
        serializeAssumedValue(cobj);

        if (original == builder.mark()) {
            builder.append("*");
        }
    }

    protected void serializeBefore(T cobj) {
    }

    private void serializeAssumedValue(T cobj) {
        if (cobj.getAssumedValue() != null) {
            if (shouldIncludeAssumedValue(cobj)) {
                builder.append(";").append(cobj.getAssumedValue());
            }
        }
    }

    protected boolean shouldIncludeAssumedValue(T cobj) {
        if (!isSingleValueConstraint(cobj)) return true;
        if (cobj.getConstraint().size()==1 && !cobj.getConstraint().get(0).getLower().equals(cobj.getAssumedValue())) {
            return true;
        }
        return false;
    }

    private void serializeConstraintIntervals(T cobj) {
        if (!cobj.getConstraint().isEmpty()) {
            boolean first = true;
            for (Interval<?> interval : cobj.getConstraint()) {
                if (!first) {
                    builder.append(", ");
                }
                if (isSingleValueInterval(interval)) {
                    builder.append(interval.getLower());
                } else {
                    builder.append(interval);
                }
                first = false;
            }
        }
    }

    private boolean isSingleValueInterval(Interval<?> interval) {
        return interval.getLower() != null && interval.getLower().equals(interval.getUpper());
    }

    private boolean isSingleValueConstraint(T cobj) {
        if (cobj.getConstraint().size() != 1) return false;
        Interval<?> interval = cobj.getConstraint().get(0);
        return isSingleValueInterval(interval);
    }
}
