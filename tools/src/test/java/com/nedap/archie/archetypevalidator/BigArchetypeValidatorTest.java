package com.nedap.archie.archetypevalidator;

import com.google.common.io.Files;
import com.google.common.io.Resources;
import com.nedap.archie.adlparser.ADLParser;
import com.nedap.archie.adlparser.ADLParserErrors;
import com.nedap.archie.aom.Archetype;
import com.nedap.archie.flattener.ArchetypeRepository;
import com.nedap.archie.flattener.SimpleArchetypeRepository;
import com.nedap.archie.rminfo.ArchieRMInfoLookup;
import org.junit.Assert;
import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;


import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

/**
 * Runs through the full test files of the validity directory of the archetype validator. Every archetype should either
 * fail to parse or fail to validate, unless it has regression = "PASS"
 *
 * Currently skips all unimplemented ErrorTypes. As soon as you add the enum constant, it will also test those
 */
public class BigArchetypeValidatorTest {

    private static final Logger log = LoggerFactory.getLogger(BigArchetypeValidatorTest.class);

    private ArchetypeRepository parseAll() {
        SimpleArchetypeRepository result = new SimpleArchetypeRepository();
        Reflections reflections = new Reflections("adl2-tests", new ResourcesScanner());
        List<String> adlFiles = new ArrayList(reflections.getResources(Pattern.compile(".*\\.adls")));
        for(String file:adlFiles) {
            if (file.contains("legacy_adl_1.4")) {
                continue;
            }
            Archetype archetype = null;
            Exception exception = null;
            ADLParserErrors errors = null;
            try (InputStream stream = getClass().getResourceAsStream("/" + file)) {
                ADLParser parser = new ADLParser();
                archetype = parser.parse(stream);
                errors = parser.getErrors();
                if (errors.hasNoErrors()) {
                    result.addArchetype(archetype);
                }

            } catch (Exception e) {
            }
        }
        return result;
    }


    @Test
    public void testFullValidityPackage() {
        Reflections reflections = new Reflections("adl2-tests.validity", new ResourcesScanner());
        List<String> adlFiles = new ArrayList(reflections.getResources(Pattern.compile(".*\\.adls")));


        int errorCount = 0;
        int correctCount = 0;
        int shouldBeFineButWasinvalid = 0;
        int notImplemented = 0;
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
                try {
                    String a = new String(Resources.toByteArray(Resources.getResource(file)));
                    if(a.contains("PASS")) {
                        log.info("{} has parse errors but should not!", file);
                        if(exception != null) {
                            if(errors != null) {
                                log.error(errors.toString());
                            }
                            log.error("exception:", exception);
                        } else {
                            log.error(errors.toString());
                        }

                        errorCount++;
                    } else {
                        log.info("{} has parse errors, so that's fine", file);
                        correctCount++;
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                repository.addArchetype(archetype);
            }
        }

        ArchetypeRepository all = parseAll();
        for(Archetype archetype:repository.getAllArchetypes()) {
            List<ValidationMessage> validation = validator.validate(archetype, all);
            String regression = archetype.getDescription().getOtherDetails().get("regression");
            if(regression != null && !regression.equalsIgnoreCase("PASS")) {
                if(!errorTypeImplemented(regression)) {
                    log.info("regression {} not implemented yet, so not yet tested", regression);
                    notImplemented++;
                } else {
                    if (validation.isEmpty()) {
                        log.error("validation failed: archetype {} valid, it should not", archetype.getArchetypeId());
                        errorCount++;
                    } else {
                        boolean found = false;
                        for (ValidationMessage message : validation) {
                            if (regression.startsWith(message.getType().name())) {
                                found = true;
                                correctCount++;
                            }
                        }
                        if (!found) {
                            log.error("validation failed: archetype {} invalid but with wrong message", archetype.getArchetypeId());
                            printErrors(validation);
                            errorCount++;
                        }

                    }
                }
            } else {
                if(!validation.isEmpty()) {
                    log.error("should validate but failed: {}, {}", archetype.getArchetypeId(), regression);
                    printErrors(validation);
                    shouldBeFineButWasinvalid++;
                } else {
                    correctCount++;
                }
            }
        }
        if(errorCount > 0) {
            Assert.fail(String.format("%s validated but should not, %s correct, %s did not validate but should, %s not yet implemented", errorCount, correctCount, shouldBeFineButWasinvalid, notImplemented));
        }


    }

    private boolean errorTypeImplemented(String regression) {
        for(ErrorType errorType: ErrorType.values()) {
            if(regression.startsWith(errorType.name())) {
                return true;
            }
        }
        return false;
    }

    private void printErrors(List<ValidationMessage> messages) {
        for(ValidationMessage message:messages) {
            log.error(message.toString());
        }
    }
}
