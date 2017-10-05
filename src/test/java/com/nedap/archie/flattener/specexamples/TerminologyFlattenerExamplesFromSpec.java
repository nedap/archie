package com.nedap.archie.flattener.specexamples;

import com.nedap.archie.flattener.SimpleArchetypeRepository;
import org.junit.Before;
import static com.nedap.archie.flattener.specexamples.FlattenerTestUtil.*;
import static org.junit.Assert.*;

public class TerminologyFlattenerExamplesFromSpec {

    private static SimpleArchetypeRepository repository;

    @Before
    public void setup() throws Exception {
        repository = new SimpleArchetypeRepository();
    }
}
