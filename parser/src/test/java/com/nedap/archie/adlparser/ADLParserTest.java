package com.nedap.archie.adlparser;

import com.nedap.archie.aom.Archetype;
import org.junit.Test;

/**
 * Created by pieter.bos on 15/10/15.
 */
public class ADLParserTest {

    @Test
    public void basic() throws Exception {
        Archetype archetype = new ADLParser().parse(getClass().getResourceAsStream("/basic.adl"));
        System.out.println(archetype);


    }

}
