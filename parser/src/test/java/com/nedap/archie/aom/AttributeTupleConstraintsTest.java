package com.nedap.archie.aom;

import com.nedap.archie.adlparser.ADLParser;
import com.nedap.archie.query.APathQuery;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by pieter.bos on 21/03/16.
 */
public class AttributeTupleConstraintsTest {

    static CAttributeTuple attributeTuple;

    @BeforeClass
    public static void setup() throws Exception {
        Archetype archetype = new ADLParser().parse(AttributeTupleConstraintsTest.class.getResourceAsStream("/basic.adl"));
        CComplexObject valueObject = archetype.getDefinition().itemAtPath("/context[id11]/other_context[id2]/items[id3]/items[id7]/value[id16]");
        attributeTuple = valueObject.getAttributeTuples().get(0);
    }

    @Test
    public void tupleConstraint() throws Exception {

        HashMap<String, Object> kgValid = new HashMap<>();
        kgValid.put("units", "kg");
        kgValid.put("magnitude", 5l);
        HashMap<String, Object> lbValid = new HashMap<>();
        lbValid.put("units", "lb");
        lbValid.put("magnitude", 10l);

        assertTrue(attributeTuple.isValid(kgValid));
        assertTrue(attributeTuple.isValid(lbValid));

    }

    @Test
     public void tupleConstraintInvalid() throws Exception {

        HashMap<String, Object> lbInvalid = new HashMap<>();
        lbInvalid.put("units", "lb");
        lbInvalid.put("magnitude", 9l);
        HashMap<String, Object> kgInvalid = new HashMap<>();
        kgInvalid.put("units", "kg");
        kgInvalid.put("magnitude", 301l);

        HashMap<String, Object> invalidUnit = new HashMap<>();
        invalidUnit.put("units", "stone");
        invalidUnit.put("magnitude", 5l);

        assertFalse(attributeTuple.isValid(lbInvalid));
        assertFalse(attributeTuple.isValid(kgInvalid));
        assertFalse(attributeTuple.isValid(invalidUnit));

    }

    @Test
    public void tupleConstraintExtraAttribute() throws Exception {
        HashMap<String, Object> extraAttribute = new HashMap<>();
        extraAttribute.put("units", "lb");
        extraAttribute.put("magnitude", 150l);
        extraAttribute.put("precison", 0.1d);

        //any extra attributes can be valid, because they are not constrained by this tuple
        assertTrue(attributeTuple.isValid(extraAttribute));

    }

    @Test
    public void tupleConstraintMissingAttribute() throws Exception {
        HashMap<String, Object> missingAttribute = new HashMap<>();
        missingAttribute.put("units", "lb");

        //missing attributes are not valid
        assertFalse(attributeTuple.isValid(missingAttribute));

    }
}
