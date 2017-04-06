package com.nedap.archie.rminfo;

import com.nedap.archie.rm.archetyped.FeederAudit;
import com.nedap.archie.rm.composition.Composition;
import com.nedap.archie.rm.composition.Observation;
import com.nedap.archie.rm.datastructures.Cluster;
import com.nedap.archie.rm.datastructures.Event;
import com.nedap.archie.rm.datastructures.History;
import com.nedap.archie.rm.datavalues.DvEHRURI;
import com.nedap.archie.rm.datavalues.DvIdentifier;
import com.nedap.archie.rm.datavalues.DvURI;
import com.nedap.archie.rm.datavalues.encapsulated.DvParsable;
import com.nedap.archie.rm.support.identification.ArchetypeID;
import com.nedap.archie.rm.support.identification.UID;
import com.nedap.archie.rm.support.identification.UIDBasedId;
import com.nedap.archie.rm.support.identification.UUID;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by stefan.teijgeler on 31/08/16.
 */
public class ArchieRMInfoLookupTest {

    private ModelInfoLookup modelInfoLookup = ArchieRMInfoLookup.getInstance();

    @Test
    public void classForRMType() {
        // Not an exhaustive list, just a few examples and edge cases
        assertEquals(Composition.class, modelInfoLookup.getClass("COMPOSITION"));
        assertEquals(Observation.class, modelInfoLookup.getClass("OBSERVATION"));
        assertEquals(Cluster.class, modelInfoLookup.getClass("CLUSTER"));
        assertEquals(DvParsable.class, modelInfoLookup.getClass("DV_PARSABLE"));
        assertEquals(DvURI.class, modelInfoLookup.getClass("DV_URI"));
        assertEquals(DvEHRURI.class, modelInfoLookup.getClass("DV_EHR_URI"));
        assertEquals(UIDBasedId.class, modelInfoLookup.getClass("UID_BASED_ID"));
        assertEquals(UID.class, modelInfoLookup.getClass("UID"));
        assertEquals(UUID.class, modelInfoLookup.getClass("UUID"));
        assertEquals(ArchetypeID.class, modelInfoLookup.getClass("ARCHETYPE_ID"));
    }

    @Test
    public void typeInfoForClass() {
        RMTypeInfo typeInfo = modelInfoLookup.getTypeInfo(Observation.class);

        assertEquals(Observation.class, typeInfo.getJavaClass());
        assertEquals("OBSERVATION", typeInfo.getRmName());
        assertEquals("data", typeInfo.getAttribute("data").getRmName());
    }

    @Test
    public void typeInfoForString() {
        RMTypeInfo typeInfo = modelInfoLookup.getTypeInfo("OBSERVATION");

        assertEquals(Observation.class, typeInfo.getJavaClass());
        assertEquals("OBSERVATION", typeInfo.getRmName());
        assertEquals("data", typeInfo.getAttribute("data").getRmName());
    }

    @Test
    public void attributeInfo() {
        RMAttributeInfo attributeInfo = modelInfoLookup.getAttributeInfo(Observation.class, "data");

        assertEquals(attributeInfo.getRmName(), "data");
        assertEquals(attributeInfo.getField().getName(), "data");
        assertEquals(attributeInfo.getGetMethod().getName(), "getData");
        assertEquals(attributeInfo.getSetMethod().getName(), "setData");
        assertEquals(attributeInfo.getType(), History.class);
        assertEquals(attributeInfo.getTypeInCollection(), History.class);
        assertEquals(attributeInfo.isMultipleValued(), false);
    }

    @Test
    public void attributeInfoOfCollection() {
        RMAttributeInfo attributeInfo = modelInfoLookup.getAttributeInfo(FeederAudit.class, "originating_system_item_ids");

        assertEquals(attributeInfo.getType(), List.class);
        assertEquals(attributeInfo.getTypeInCollection(), DvIdentifier.class);
        assertEquals(attributeInfo.isMultipleValued(), true);
    }

    @Test
    public void attributeInfoOfParameterizedCollectionType() {
        RMAttributeInfo attributeInfo = modelInfoLookup.getAttributeInfo(History.class, "events");

        assertEquals(attributeInfo.getType(), List.class);
        assertEquals(attributeInfo.getTypeInCollection(), Event.class);
        assertEquals(attributeInfo.isMultipleValued(), true);
    }




}
