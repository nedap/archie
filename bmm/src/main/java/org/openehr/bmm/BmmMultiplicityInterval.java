package org.openehr.bmm;

/*
 * #%L
 * OpenEHR - Java Model Stack
 * %%
 * Copyright (C) 2016 - 2017  Cognitive Medical Systems, Inc (http://www.cognitivemedicine.com).
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

import com.nedap.archie.base.MultiplicityInterval;
import org.openehr.odin.IntegerIntervalObject;
import org.openehr.odin.IntegerObject;

import java.io.Serializable;

/**
 * Created by cnanjo on 4/11/16.
 */
public class BmmMultiplicityInterval extends IntegerIntervalObject implements Serializable {

    public BmmMultiplicityInterval() { super(); }

    public BmmMultiplicityInterval(Integer low, Boolean excludeLowerBound, Integer high, Boolean excludeHigherBound) {
        this();
        if(low != null) {
            setLow(new IntegerObject(low));
            setExcludeLowerBound(excludeLowerBound);
        }
        if(high !=  null) {
            setHigh(new IntegerObject(high));
            setExcludeUpperBound(excludeHigherBound);
        }
    }

    public BmmMultiplicityInterval(IntegerIntervalObject interval) {
        this();
        setLow(interval.getLow());
        setHigh(interval.getHigh());
        setExcludeLowerBound(interval.isExcludeLowerBound());
        setExcludeUpperBound(interval.isExcludeUpperBound());
        setIntervalExpression(interval.getIntervalExpression());
        setType(interval.getType());
    }

    public void setLow(int low) {
        super.setLow(new IntegerObject(low));
    }

    public void setHigh(int high) {
        super.setHigh(new IntegerObject(high));
    }

    public MultiplicityInterval convertToMultiplicityInterval() {
        Integer low = (getLow() != null)?getLow().getAsInteger():null;
        Integer high = (getHigh() != null)?getHigh().getAsInteger():null;
        return new MultiplicityInterval(low, !isExcludeLowerBound(), hasOpenLowerBound(), high, !isExcludeUpperBound(), hasOpenUpperBound());
    }
}
