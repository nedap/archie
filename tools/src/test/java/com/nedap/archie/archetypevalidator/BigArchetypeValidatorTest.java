package com.nedap.archie.archetypevalidator;

import com.nedap.archie.adlparser.ADLParser;
import com.nedap.archie.adlparser.ADLParserErrors;
import com.nedap.archie.aom.Archetype;
import com.nedap.archie.flattener.SimpleArchetypeRepository;
import com.nedap.archie.rminfo.ArchieRMInfoLookup;
import org.junit.Assert;
import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

/**
 * Runs through the full test files of the validaty directory of the archetype validator. Every archetype should either
 * fail to parse or fail to validate
 */
public class BigArchetypeValidatorTest {

    private static final Logger log = LoggerFactory.getLogger(BigArchetypeValidatorTest.class);

    @Test
    public void testFullValidityPackage() {
        Reflections reflections = new Reflections("adl2-tests.validity", new ResourcesScanner());
        List<String> adlFiles = new ArrayList(reflections.getResources(Pattern.compile(".*\\.adls")));


        int errorCount = 0;
        int correctCount = 0;
        int shouldBeFineButWasinvalid = 0;
        ArchetypeValidator validator = new ArchetypeValidator(ArchieRMInfoLookup.getInstance());
        SimpleArchetypeRepository repository = new SimpleArchetypeRepository();
        for(String file:adlFiles) {
            if(file.contains("legacy_adl_1.4")){
                continue;
            }
            Archetype archetype = null;
            Exception exception = null;
            ADLParserErrors errors = null;
            try (InputStream stream = getClass().getResourceAsStream("/" + file)) {
                ADLParser parser = new ADLParser();
                archetype = parser.parse(stream);
                errors = parser.getErrors();
            } catch (Exception e) {
                exception = e;
            }
            if(exception != null || errors.hasErrors()) {
                log.info("{} has parse errors, so that's fine", file);
                correctCount++;
            } else {
                repository.addArchetype(archetype);
            }
        }
        for(Archetype archetype:repository.getAllArchetypes()) {
            List<ValidationMessage> validation = validator.validate(archetype, repository);
            String regression = archetype.getDescription().getOtherDetails().get("regression");
            if(regression != null && !regression.equalsIgnoreCase("PASS")) {
                if(validation.isEmpty()) {
                    log.error("validation failed: archetype {} valid, it should not", archetype.getArchetypeId());
                    errorCount++;
                } else {
                    boolean found = false;
                    for(ValidationMessage message:validation) {
                        if(message.getType().name().equals(regression)) {
                            found = true;
                            correctCount++;
                        }
                    }
                    if(!found) {
                        log.error("validation failed: archetype {} invalid but with wrong message", archetype.getArchetypeId());
                        log.error(validation.toString());
                        errorCount++;
                    }

                }
            } else {
                if(!validation.isEmpty()) {
                    log.error("should validate but failed: {}, {}", archetype.getArchetypeId(), regression);
                    log.error(validation.toString());
                    shouldBeFineButWasinvalid++;
                } else {
                    correctCount++;
                }
            }
        }
        if(errorCount > 0) {
            Assert.fail(String.format("%s validated but should not, %s correct, %s did not validate but should", errorCount, correctCount, shouldBeFineButWasinvalid));
        }


    }
}
