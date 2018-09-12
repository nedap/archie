package com.nedap.archie.rmobjectvalidatortest;

import com.nedap.archie.adlparser.ADLParser;
import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.CComplexObject;
import com.nedap.archie.archetypevalidator.ArchetypeValidatorTest;
import com.nedap.archie.creation.RMObjectCreator;
import com.nedap.archie.openehrtestrm.TestRMInfoLookup;
import com.nedap.archie.rm.datastructures.Element;
import com.nedap.archie.rm.datavalues.DataValue;
import com.nedap.archie.rm.datavalues.quantity.DvProportion;
import com.nedap.archie.rminfo.ArchieRMInfoLookup;
import com.nedap.archie.rminfo.ReferenceModels;
import com.nedap.archie.rmobjectvalidator.RMObjectValidationMessage;
import com.nedap.archie.rmobjectvalidator.RMObjectValidator;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RmObjectValidatorTest {

    private ADLParser parser;
    private Archetype archetype;

    private ReferenceModels models;
    RMObjectCreator creator;
    RMObjectValidator validator;

    @Before
    public void setup() {
        parser = new ADLParser();
        models = new ReferenceModels();
        models.registerModel(ArchieRMInfoLookup.getInstance());
        creator = new RMObjectCreator(ArchieRMInfoLookup.getInstance());
        validator = new RMObjectValidator(ArchieRMInfoLookup.getInstance());
    }

    @Test
    public void requiredAttributesShouldBePresent() throws Exception {
        archetype = parse("/adl2-tests/rmobjectvalidity/openEHR-TEST_RMOBJECT-ELEMENT.element_with_required_attributes.v1.0.0.adls");

        Element rmObject = creator.create(archetype.getDefinition());
        DvProportion dvProportion = creator.create(archetype.getDefinition().itemAtPath("/value[id2]"));
        creator.addElementToListOrSetSingleValues(rmObject, "value", dvProportion);

        dvProportion.setDenominator(4D);
        dvProportion.setType(3L);
        List<RMObjectValidationMessage> validationMessages = validator.validate(archetype, rmObject);
        assertEquals("There should be 1 errors", 1, validationMessages.size());
        assertEquals("There should be a validation message about the numerator", "Attribute numerator of class DV_PROPORTION does not match existence 1..1", validationMessages.get(0).getMessage());

        dvProportion.setNumerator(2D);
        validationMessages = validator.validate(archetype, rmObject);
        assertEquals("There should be 0 errors", 0, validationMessages.size());
    }

    private Archetype parse(String filename) throws IOException {
        archetype = parser.parse(ArchetypeValidatorTest.class.getResourceAsStream(filename));
        assertTrue(parser.getErrors().toString(), parser.getErrors().hasNoErrors());
        return archetype;
    }

}