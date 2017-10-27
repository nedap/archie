package com.nedap.archie.rules.evaluation.evaluators.functions;

import com.nedap.archie.rules.PrimitiveType;
import com.nedap.archie.rules.evaluation.FunctionCallException;
import com.nedap.archie.rules.evaluation.FunctionImplementation;
import com.nedap.archie.rules.evaluation.Value;
import com.nedap.archie.rules.evaluation.ValueList;

import java.util.List;

/**
 * When given a list of paths, CountDefinedElements counts the number of these
 * paths that contain defined elements.
 *
 * Created by tanja.dejong on 27/10/2017.
 */
public class CountDefinedElements implements FunctionImplementation {

    @Override
    public String getName() {
        return "count_defined_elements";
    }

    /**
     * Counts the number of paths that contain defined elements
     * @param arguments The paths that are counted
     * @return The number of paths that contain defined elements
     */
    @Override
    public ValueList evaluate(List<ValueList> arguments) {
        Value countDefinedElements = new Value(0);

        for (ValueList argument : arguments) {
            if (!argument.isEmpty()) {
                countDefinedElements.setValue((Integer) countDefinedElements.getValue() + 1);
            }
        }

        ValueList result = new ValueList();
        result.addValue(countDefinedElements);
        result.setType(PrimitiveType.Integer);

        return result;
    }
}
