package com.nedap.archie.adlparser;

import com.nedap.archie.aom.primitives.CInteger;
import com.nedap.archie.base.Interval;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by pieter.bos on 01/11/15.
 */
public class NumberConstraintParserTest extends PrimitivesConstraintParserTest {

    /*
    integer_attr1 matches {55}
		integer_attr2 matches {10, 20, 30}
		integer_attr3 matches {|0..100|}
		integer_attr4 matches {|>0..100|}
		integer_attr5 matches {|0..<100|}
		integer_attr6 matches {|>0..<100|}
		integer_attr7 matches {|>10|}
		integer_attr8 matches {|<10|}
		integer_attr9 matches {|>=10|}
		integer_attr10 matches {|<=10|}
		integer_attr11 matches {|-10..-5|}
		integer_attr12 matches {10}
		integer_attr13
     */

    @Test
    public void integers() {
        CInteger integerAttr1 = getAttribute("integer_attr1");
        CInteger integerAttr2 = getAttribute("integer_attr2");
        CInteger integerAttr3 = getAttribute("integer_attr3");
		CInteger integerAttr4 = getAttribute("integer_attr4");
		CInteger integerAttr5 = getAttribute("integer_attr5");
		CInteger integerAttr6 = getAttribute("integer_attr6");
		CInteger integerAttr7 = getAttribute("integer_attr7");
		CInteger integerAttr8 = getAttribute("integer_attr8");
		CInteger integerAttr9 = getAttribute("integer_attr9");
		CInteger integerAttr10 = getAttribute("integer_attr10");
		CInteger integerAttr11 = getAttribute("integer_attr11");
		CInteger integerAttr12 = getAttribute("integer_attr12");
        assertEquals(new Interval<>(55l), integerAttr1.getConstraint().get(0));
		//2 = list!
		assertEquals(new Interval<>(10l), integerAttr2.getConstraint().get(0));
		assertEquals(new Interval<>(20l), integerAttr2.getConstraint().get(1));
		assertEquals(new Interval<>(30l), integerAttr2.getConstraint().get(2));

        assertEquals(new Interval<>(0l, 100l), integerAttr3.getConstraint().get(0));
        assertEquals(new Interval<>(0l, 100l, false, true), integerAttr4.getConstraint().get(0));
		assertEquals(new Interval<>(0l, 100l, true, false), integerAttr5.getConstraint().get(0));
		assertEquals(new Interval<>(0l, 100l, false, false), integerAttr6.getConstraint().get(0));
		assertEquals(Interval.upperUnbounded(10l, false), integerAttr7.getConstraint().get(0));
		assertEquals(Interval.lowerUnbounded(10l, false), integerAttr8.getConstraint().get(0));
		assertEquals(Interval.upperUnbounded(10l, true), integerAttr9.getConstraint().get(0));
		assertEquals(Interval.lowerUnbounded(10l, true), integerAttr10.getConstraint().get(0));
		assertEquals(new Interval<>(-10l, -5l), integerAttr11.getConstraint().get(0));
		assertEquals(new Interval<>(10l), integerAttr12.getConstraint().get(0));
    }


	/*
	real_attr1 matches {100.0}
		real_attr2 matches {10.0, 20.0, 30.0}
		real_attr3 matches {|0.0..100.0|}
		real_attr4 matches {|>0.0..100.0|}
		real_attr5 matches {|0.0..<100.0|}
		real_attr6 matches {|>0.0..<100.0|}
		real_attr7 matches {|>=10.0|}
		real_attr8 matches {|<=10.0|}
		real_attr9 matches {|>=10.0|}
		real_attr10 matches {|<=10.0|}
		real_attr11 matches {|-10.0..-5.0|}
		real_attr12 matches {10.0}
		real_attr13
	 */
    @Test
    public void reals() {

    }

    @Test
    public void assumedValues() {

    }
}
