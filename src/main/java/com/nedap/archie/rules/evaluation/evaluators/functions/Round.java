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
        if(arguments.size() != 1) {
            throw new FunctionCallException("round expects one argument, but got " + arguments.size());
        }

        ValueList result = new ValueList();
        result.setType(PrimitiveType.Integer);

        for(Value valueObject : arguments.get(0).getValues()) {
            result.addValue(new Value(Math.round((Double) valueObject.getValue())));
        }

        return result;
    }
}
