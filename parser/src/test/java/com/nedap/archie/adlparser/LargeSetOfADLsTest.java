package com.nedap.archie.adlparser;

import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by pieter.bos on 16/10/15.
 */
public class LargeSetOfADLsTest {

    @Test
    public void parseLots() throws Exception {
        Reflections reflections = new Reflections("adl2-tests", new ResourcesScanner());
        List<String> adlFiles = new ArrayList(reflections.getResources(Pattern.compile(".*\\.adls")));

        Map<String, Exception> exceptions = new LinkedHashMap<>();
        for(String file:adlFiles) {
            try (InputStream stream = getClass().getResourceAsStream("/" + file)) {
                System.err.println("trying to parse " + file);
                ADLParser parser = new ADLParser();
                parser.parse(stream);
                if(parser.tree.exception != null) {
                    exceptions.put(file, parser.tree.exception);
                }
            } catch (Exception e) {
                exceptions.put(file, e);
            }
        }

        for(String file:exceptions.keySet()) {
            System.err.println("exception found in " + file);
            exceptions.get(file).printStackTrace();
        }

        System.out.println("parsed adls: " + adlFiles.size());
        System.out.println("parsed adls with Exceptions: " + exceptions.size());


    }
}
