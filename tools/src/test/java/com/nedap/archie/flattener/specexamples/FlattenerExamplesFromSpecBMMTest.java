package com.nedap.archie.flattener.specexamples;

import com.nedap.archie.flattener.SimpleArchetypeRepository;
import com.nedap.archie.testutil.TestUtil;
import org.junit.Before;

public class FlattenerExamplesFromSpecBMMTest extends FlattenerExamplesFromSpecTest {

    @Before
    public void setup() throws Exception {
        repository = new SimpleArchetypeRepository();
        models = TestUtil.getBMMReferenceModels();
    }
}
