package com.nedap.archie.rules.evaluation.evaluators.functions;

import com.nedap.archie.rules.PrimitiveType;
import com.nedap.archie.rules.evaluation.FunctionCallException;
import com.nedap.archie.rules.evaluation.FunctionImplementation;
import com.nedap.archie.rules.evaluation.Value;
import com.nedap.archie.rules.evaluation.ValueList;

import java.util.ArrayList;
import java.util.List;

import static com.nedap.archie.rules.evaluation.evaluators.FunctionUtil.castToDouble;
import static com.nedap.archie.rules.evaluation.evaluators.FunctionUtil.checkAndHandleNull;
import static com.nedap.archie.rules.evaluation.evaluators.FunctionUtil.checkEqualLength;

public class Round implements FunctionImplementation {
    @Override
    public String getName() {
        return "round";
    }

    @Override
    public ValueList evaluate(List<ValueList> arguments) throws FunctionCallException {
        if(arguments.get(0) != null) {
            ValueList argument = arguments.get(0);
            if(argument.get(0) != null) {
                Value valueObject = argument.get(0);
                if(valueObject != null) {
                    Long roundedValue = Math.round((Double) valueObject.getValue());
                    ValueList result = new ValueList(roundedValue);
                    return result;
                }
            }
        }

        throw new FunctionCallException("The function round should have one argument with a double value, but got none");
    }
}
