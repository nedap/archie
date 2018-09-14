package com.nedap.archie.rmobjectvalidator;

import com.nedap.archie.aom.primitives.CInteger;
import com.nedap.archie.aom.primitives.CReal;
import com.nedap.archie.aom.primitives.CString;
import com.nedap.archie.base.Interval;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConstraintToStringUtilTest {

    @Test
    public void testCIntegerToString() {
        CInteger cInteger = new CInteger();
        assertEquals("anything", ConstraintToStringUtil.primitiveObjectConstraintToString(cInteger));

        cInteger.addConstraint(new Interval<>(10L));
        assertEquals("equal to 10", ConstraintToStringUtil.primitiveObjectConstraintToString(cInteger));

        cInteger.addConstraint(new Interval<>(20L));
        assertEquals("equal to 10 or equal to 20", ConstraintToStringUtil.primitiveObjectConstraintToString(cInteger));

        cInteger.addConstraint(new Interval<>(30L));
        assertEquals("equal to 10 or equal to 20 or equal to 30", ConstraintToStringUtil.primitiveObjectConstraintToString(cInteger));

        cInteger = new CInteger();
        cInteger.addConstraint(Interval.lowerUnbounded(-10L, false));
        cInteger.addConstraint(new Interval<>(0L, 10L, false, true));
        cInteger.addConstraint(Interval.upperUnbounded(20L, true));

        String expected = "less than -10 or greater than 0 and less than or equal to 10 or greater than or equal to 20";
        assertEquals(expected, ConstraintToStringUtil.primitiveObjectConstraintToString(cInteger));
    }

    @Test
    public void testCRealToString() {
        CReal cReal = new CReal();
        assertEquals("anything", ConstraintToStringUtil.primitiveObjectConstraintToString(cReal));

        cReal.addConstraint(new Interval<>(1.42d));
        assertEquals("equal to 1.42", ConstraintToStringUtil.primitiveObjectConstraintToString(cReal));

        cReal.addConstraint(new Interval<>(Math.E));
        assertEquals("equal to 1.42 or equal to 2.718", ConstraintToStringUtil.primitiveObjectConstraintToString(cReal));

        cReal.addConstraint(new Interval<>(Math.PI));
        assertEquals("equal to 1.42 or equal to 2.718 or equal to 3.142", ConstraintToStringUtil.primitiveObjectConstraintToString(cReal));

        cReal = new CReal();
        cReal.addConstraint(Interval.lowerUnbounded(-10.5, false));
        cReal.addConstraint(new Interval<>(0.0, 10.0, false, true));
        cReal.addConstraint(Interval.upperUnbounded(20.42, true));

        String expected = "less than -10.5 or greater than 0 and less than or equal to 10 or greater than or equal to 20.42";
        assertEquals(expected, ConstraintToStringUtil.primitiveObjectConstraintToString(cReal));
    }

    @Test
    public void testCStringToString() {
        CString cString = new CString();
        assertEquals("anything", ConstraintToStringUtil.primitiveObjectConstraintToString(cString));

        cString.addConstraint("eggs");
        assertEquals("\"eggs\"", ConstraintToStringUtil.primitiveObjectConstraintToString(cString));

        cString.addConstraint("bacon");
        assertEquals("\"eggs\" or \"bacon\"", ConstraintToStringUtil.primitiveObjectConstraintToString(cString));

        cString.addConstraint("spam");
        assertEquals("\"eggs\" or \"bacon\" or \"spam\"", ConstraintToStringUtil.primitiveObjectConstraintToString(cString));

        cString = new CString("quotes ' and \" test");
        assertEquals("\"quotes ' and \\\" test\"", ConstraintToStringUtil.primitiveObjectConstraintToString(cString));
    }

    @Test
    public void testIntervalToStringUnbounded() {
        Interval<Long> interval = new Interval<>(null, null);
        interval.setLowerUnbounded(true);
        interval.setUpperUnbounded(true);

        String actual = ConstraintToStringUtil.intervalToString(interval);
        assertEquals("anything", actual);
    }

    @Test
    public void testIntervalToStringLong() {
        assertEquals("equal to 55", ConstraintToStringUtil.intervalToString(new Interval<>(55L)));
        assertEquals("greater than or equal to 0 and less than or equal to 100", ConstraintToStringUtil.intervalToString(new Interval<>(0L, 100L)));
        assertEquals("greater than 0 and less than or equal to 100", ConstraintToStringUtil.intervalToString(new Interval<>(0L, 100L, false, true)));
        assertEquals("greater than or equal to 0 and less than 100", ConstraintToStringUtil.intervalToString(new Interval<>(0L, 100L, true, false)));
        assertEquals("greater than 0 and less than 100", ConstraintToStringUtil.intervalToString(new Interval<>(0L, 100L, false, false)));
        assertEquals("greater than 10", ConstraintToStringUtil.intervalToString(Interval.upperUnbounded(10L, false)));
        assertEquals("less than 10", ConstraintToStringUtil.intervalToString(Interval.lowerUnbounded(10L, false)));
        assertEquals("greater than or equal to 10", ConstraintToStringUtil.intervalToString(Interval.upperUnbounded(10L, true)));
        assertEquals("less than or equal to 10", ConstraintToStringUtil.intervalToString(Interval.lowerUnbounded(10L, true)));
        assertEquals("greater than or equal to -10 and less than or equal to -5", ConstraintToStringUtil.intervalToString(new Interval<>(-10L, -5L)));
    }

    @Test
    public void testIntervalToStringDouble() {
        assertEquals("equal to 55", ConstraintToStringUtil.intervalToString(new Interval<>(55d)));
        assertEquals("greater than or equal to 0 and less than or equal to 100", ConstraintToStringUtil.intervalToString(new Interval<>(0.0, 100.0)));
        assertEquals("greater than 2.3 and less than 7.424", ConstraintToStringUtil.intervalToString(new Interval<>(2.3, 7.4242, false, false)));
        assertEquals("greater than or equal to 2.718 and less than or equal to 3.142", ConstraintToStringUtil.intervalToString(new Interval<>(Math.E, Math.PI)));
    }
}
