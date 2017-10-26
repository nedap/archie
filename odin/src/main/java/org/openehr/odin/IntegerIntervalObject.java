package org.openehr.odin;

/*
 * #%L
 * OpenEHR - Java Model Stack
 * %%
 * Copyright (C) 2016 - 2017 Cognitive Medical Systems
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 * Author: Claude Nanjo
 */

import org.openehr.odin.antlr.odinParser;

import java.io.Serializable;

public class IntegerIntervalObject extends IntervalObject<IntegerObject, odinParser.Integer_valueContext> implements Serializable {

    @Override
    protected IntegerObject extractFromContext(odinParser.Integer_valueContext ctx) {
        return IntegerObject.extractIntegerObject(ctx);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        if(getLow() != null) {
            if(getHigh() != null) {
                if(isExcludeLowerBound()) {
                    builder.append("|>").append(getLow().getAsInteger()).append("..");
                } else {
                    builder.append("|").append(getLow().getAsInteger()).append("..");
                }
                if(isExcludeUpperBound()) {
                    builder.append("<").append(getHigh().getAsInteger()).append("|");
                } else {
                    builder.append(getHigh().getAsInteger()).append("|");
                }
            } else {
                if(isExcludeLowerBound()) {
                    builder.append("|>").append(getLow().getAsInteger()).append("|");
                } else {
                    builder.append("|>=").append(getLow().getAsInteger()).append("|");
                }
            }
        } else if(getHigh() != null){
            if(isExcludeUpperBound()) {
                builder.append("|<").append(getHigh().getAsInteger()).append("|");
            } else {
                builder.append("|<=").append(getHigh().getAsInteger()).append("|");
            }
        } else {
            builder.append("|").append(getIntervalExpression()).append("|");
        }
        return builder.toString();
    }
}
