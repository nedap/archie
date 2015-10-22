package com.nedap.archie.flattener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.nedap.archie.adlparser.ADLParser;
import com.nedap.archie.aom.Archetype;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TODO: all this tests is that the flattener runs, with some diagnostic JSON-output. Implement way more tests.
 *
 *
 * Created by pieter.bos on 21/10/15.
 */
public class FlattenerTest {

    Archetype report;
    Archetype device;
    Archetype bloodPressureObservation;
    Archetype reportResult;
    Archetype bloodPressureComposition;
    SimpleArchetypeRepository repository;

    Flattener flattener;

    @Before
    public void setup() throws Exception {

        // reportresult specializes report.
        // blood pressure composition specializes report result.

        // it adds a blood pressure observation
        // it also adds a device
        // it contains specific template overlays for both blood pressure observation and device
        report = new ADLParser().parse(FlattenerTest.class.getResourceAsStream("openEHR-EHR-COMPOSITION.report.v1.adls"));
        reportResult = new ADLParser().parse(FlattenerTest.class.getResourceAsStream("openEHR-EHR-COMPOSITION.report-result.v1.adls"));
        device = new ADLParser().parse(FlattenerTest.class.getResourceAsStream("openEHR-EHR-CLUSTER.device.v1.adls"));

        bloodPressureObservation = new ADLParser().parse(FlattenerTest.class.getResourceAsStream("openEHR-EHR-OBSERVATION.blood_pressure.v1.adls"));
        bloodPressureComposition = new ADLParser().parse(FlattenerTest.class.getResourceAsStream("openEHR-EHR-COMPOSITION.blood_pressure.v1.0.0.adlt"));


        repository = new SimpleArchetypeRepository();
        repository.addArchetype(report);
        repository.addArchetype(device);
        repository.addArchetype(bloodPressureComposition);
        repository.addArchetype(bloodPressureObservation);
        repository.addArchetype(reportResult);

        flattener = new Flattener(repository);
    }

    @Test
    public void reportResult() throws Exception {
        Archetype flattened = flattener.flatten(reportResult);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        System.out.println(objectMapper.writeValueAsString(flattened));
    }


    @Test
    public void test() throws Exception {
        Archetype flattened = flattener.flatten(bloodPressureComposition);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        System.out.println(objectMapper.writeValueAsString(flattened));

    }
}
