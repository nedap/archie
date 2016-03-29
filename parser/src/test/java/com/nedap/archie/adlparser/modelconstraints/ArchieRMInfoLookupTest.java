package com.nedap.archie.adlparser.modelconstraints;

import com.nedap.archie.rm.datastructures.ItemList;
import com.nedap.archie.rm.datavalues.DvURI;
import com.nedap.archie.rm.datavalues.quantity.DvQuantity;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URI;

import static org.junit.Assert.*;

/**
 * Created by pieter.bos on 29/03/16.
 */
public class ArchieRMInfoLookupTest {

    private static ArchieRMInfoLookup rmInfoLookup;

    @BeforeClass
    public static void setup() {
        rmInfoLookup = new ArchieRMInfoLookup();
    }

    @Test
    public void rmTypeInfo() {
        RMTypeInfo uriInfo = rmInfoLookup.getTypeInfo("DV_URI");
        assertEquals("DV_URI", uriInfo.getRmName());
        assertEquals(DvURI.class, uriInfo.getJavaClass());
        RMAttributeInfo valueAttribute = uriInfo.getAttribute("value");
        assertNotNull(valueAttribute);
        assertEquals("value", valueAttribute.getRmName());
        assertEquals(URI.class, valueAttribute.getType());
        assertFalse(valueAttribute.isNullable());
        assertNotNull(valueAttribute.getGetMethod());
        assertNotNull(valueAttribute.getSetMethod());
        assertNull(valueAttribute.getAddMethod());
    }

    @Test
    public void genericType() {
        RMTypeInfo quantityInfo = rmInfoLookup.getTypeInfo("DV_QUANTITY");
        assertEquals("DV_QUANTITY", quantityInfo.getRmName());
        assertEquals(DvQuantity.class, quantityInfo.getJavaClass());
        RMAttributeInfo valueAttribute = quantityInfo.getAttribute("magnitude");
        assertNotNull(valueAttribute);
        assertEquals("magnitude", valueAttribute.getRmName());
        assertFalse(valueAttribute.isNullable());
        assertEquals("DvQuantity extends DvAmount<Double> should have a double magnitude field", Double.class, valueAttribute.getType());

    }

    @Test
    public void addMethod() {
        RMTypeInfo itemListInfo = rmInfoLookup.getTypeInfo("ITEM_LIST");
        assertEquals("addItem", itemListInfo.getAttribute("items").getAddMethod().getName());
    }

    @Test
    public void nullable() {
        RMAttributeInfo precisionInfo = rmInfoLookup.getAttributeInfo("DV_QUANTITY", "precision");
        assertTrue(precisionInfo.isNullable());
    }

}
