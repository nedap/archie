package org.openehr.referencemodels;

import com.google.common.collect.Sets;
import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.ArchetypeHRID;
import com.nedap.archie.rminfo.MetaModels;
import org.junit.Test;
import org.openehr.bmm.core.BmmModel;
import org.openehr.bmm.rmaccess.ReferenceModelAccess;
import org.openehr.bmm.v2.validation.BmmRepository;
import org.openehr.bmm.v2.validation.BmmValidationResult;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BuiltInReferenceModelsTest {
    /**
     * This test tests the old BMM implementation thing and as such is deprecated.
     * It will start creating more and more errors as BMM is changed, but the implementation is not -
     * should be removed as soon as possible
     * @throws Exception
     */
    @Test
    @Deprecated
    public void getValidModels() throws Exception {

        ReferenceModelAccess access = BuiltinReferenceModels.getBMMReferenceModels();
        Map<String, BmmModel> models = access.getValidModels();
        assertTrue(access.getValidator().hasErrors()); //hl7 fihr is missing the Signature type, so this results in an error
        //fhir is broken, so is openehr_lang, so two error codes
        assertEquals("unexpected errors: " + access.getValidator().getMessageLogger(),
                2, access.getValidator().getMessageLogger().getErrorCodes().size());

        //if we don't set the top level schema, it has warnings, apparently. Don't know why, often you would want all of these
        //unless you want to override versions, in which case there are better mechanisms possible.
        assertTrue(access.getValidator().hasWarnings());

        assertEquals(Sets.newHashSet(
                "openehr_base_1.0.0",
                "cdisc_core_0.5.0",
                "cen_en13606_0.95",
                "openehr_rm_1.0.2",
                "cimi_rm_clinical_0.0.3",
                "openehr_rm_1.0.3"), models.keySet());
    }

    @Test
    public void bmmRepository() throws Exception {

        BmmRepository bmmRepository = BuiltinReferenceModels.getBmmRepository();

        for(BmmValidationResult validation:bmmRepository.getInvalidModels()) {
            System.out.println("validation " + validation.getSchemaId() + " contains errors:");
            System.out.println(validation.getLogger().toString());

        }
        assertEquals(34, bmmRepository.getPersistentSchemas().size());
        assertEquals(34, bmmRepository.getModels().size());
        assertEquals(29, bmmRepository.getValidModels().size());
        assertEquals(5, bmmRepository.getInvalidModels().size());
    }

    @Test
    public void overrideModelVersion() throws Exception {
        MetaModels metaModels = BuiltinReferenceModels.getMetaModels();
        Archetype archetype = new Archetype();
        archetype.setArchetypeId(new ArchetypeHRID("openEHR-EHR-CLUSTER.test.v1.0.0"));
        archetype.setRmRelease("1.0.3");
        metaModels.selectModel(archetype);
        assertEquals("openehr", metaModels.getSelectedBmmModel().getRmPublisher());
        assertEquals("EHR", metaModels.getSelectedBmmModel().getModelName());
        assertEquals("1.0.3", metaModels.getSelectedBmmModel().getRmRelease());


        //now overide the version to 1.0.4, and assert
        metaModels.overrideModelVersion("openEHR", "EHR", "1.0.4");

        metaModels.selectModel(archetype);

        metaModels.selectModel(archetype);
        assertEquals("openehr", metaModels.getSelectedBmmModel().getRmPublisher());
        assertEquals("EHR", metaModels.getSelectedBmmModel().getModelName());
        assertEquals("1.0.4", metaModels.getSelectedBmmModel().getRmRelease());

        //select a specific RM
        metaModels.selectModel(archetype, "1.0.2");

        assertEquals("openehr", metaModels.getSelectedBmmModel().getRmPublisher());
        assertEquals("EHR", metaModels.getSelectedBmmModel().getModelName());
        assertEquals("1.0.2", metaModels.getSelectedBmmModel().getRmRelease());

        //remove Override
        metaModels.removeOverridenModelVersion("openEHR", "EHR");
        metaModels.selectModel(archetype);
        assertEquals("openehr", metaModels.getSelectedBmmModel().getRmPublisher());
        assertEquals("EHR", metaModels.getSelectedBmmModel().getModelName());
        assertEquals("1.0.3", metaModels.getSelectedBmmModel().getRmRelease());
    }
}
