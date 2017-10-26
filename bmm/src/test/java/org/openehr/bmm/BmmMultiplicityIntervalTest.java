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

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by cnanjo on 10/1/16.
 */
public class BmmMultiplicityIntervalTest {

    @Test
    public void testLowerBoundIncludedOnly() {
        BmmMultiplicityInterval cardinality = new BmmMultiplicityInterval(0, false, null, null);
        assertEquals("|>=0|", cardinality.toString());
    }

    @Test
    public void testLowerBoundExcludedOnly() {
        BmmMultiplicityInterval cardinality = new BmmMultiplicityInterval(0, true, null, null);
        assertEquals("|>0|", cardinality.toString());
    }

    @Test
    public void testUpperBoundIncludedOnly() {
        BmmMultiplicityInterval cardinality = new BmmMultiplicityInterval(null, null, 5, false);
        assertEquals("|<=5|", cardinality.toString());
    }

    @Test
    public void testUpperBoundExcludedOnly() {
        BmmMultiplicityInterval cardinality = new BmmMultiplicityInterval(null, null, 5, true);
        assertEquals("|<5|", cardinality.toString());
    }

    @Test
    public void testRangeAllIncluded() {
        BmmMultiplicityInterval cardinality = new BmmMultiplicityInterval(0, false, 5, false);
        assertEquals("|0..5|", cardinality.toString());
    }

    @Test
    public void testRangeExcludeLowIncludeHigh() {
        BmmMultiplicityInterval cardinality = new BmmMultiplicityInterval(0, true, 5, false);
        assertEquals("|>0..5|", cardinality.toString());
    }

    @Test
    public void testRangeIncludeLowExcludeHigh() {
        BmmMultiplicityInterval cardinality = new BmmMultiplicityInterval(0, false, 5, true);
        assertEquals("|0..<5|", cardinality.toString());
    }

    @Test
    public void testRangeExcludeBoth() {
        BmmMultiplicityInterval cardinality = new BmmMultiplicityInterval(0, true, 5, true);
        assertEquals("|>0..<5|", cardinality.toString());
    }

    @Test
    public void testRangeByInterval() {
        BmmMultiplicityInterval cardinality = new BmmMultiplicityInterval();
        cardinality.setIntervalExpression("0+/-5");
        assertEquals("|0+/-5|", cardinality.toString());
    }
}