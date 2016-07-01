package com.nedap.archie.xml;

import com.nedap.archie.adlparser.ADLParser;
import com.nedap.archie.aom.Archetype;
import com.nedap.archie.json.JacksonRMRoundTripTest;
import com.nedap.archie.json.JacksonUtil;
import com.nedap.archie.query.RMQueryContext;
import com.nedap.archie.rm.RMObject;
import com.nedap.archie.rm.datastructures.Cluster;
import com.nedap.archie.rm.datavalues.DvText;
import com.nedap.archie.rm.datavalues.quantity.DvQuantity;
import com.nedap.archie.rm.datavalues.quantity.datetime.DvDate;
import com.nedap.archie.rm.datavalues.quantity.datetime.DvDateTime;
import com.nedap.archie.rm.datavalues.quantity.datetime.DvTime;
import com.nedap.archie.testutil.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.omg.CORBA.MARSHAL;

import javax.xml.bind.Marshaller;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by pieter.bos on 30/06/16.
 */
public class JAXBRMRoundTripTest {

    ADLParser parser;
    Archetype archetype;

    TestUtil testUtil;

    @Before
    public void setup() {
        testUtil = new TestUtil();
        parser = ADLParser.withRMConstraintsImposer();
    }

    @Test
    public void dataValues() throws Exception {
        archetype = parser.parse(JAXBRMRoundTripTest.class.getResourceAsStream("/com/nedap/archie/json/openEHR-EHR-CLUSTER.datavalues.v1.adls"));
        Cluster cluster =  (Cluster) testUtil.constructEmptyRMObject(archetype.getDefinition());
        RMQueryContext queryContext = new RMQueryContext(cluster);
        DvText text = queryContext.find("/items['Text']/value");
        text.setValue("test-text");
        DvQuantity quantity = queryContext.find("/items['Quantity']/value");
        quantity.setMagnitude(23d);
        DvDate date = queryContext.find("/items['Date']/value");
        date.setValue(LocalDate.of(2016, 1, 1));

        DvDateTime datetime = queryContext.find("/items['Datetime']/value");
        datetime.setValue(LocalDateTime.of(2016, 1, 1, 12, 00));

        DvTime time = queryContext.find("/items['Time']/value");
        time.setValue(LocalTime.of(12, 0));

        StringWriter writer = new StringWriter();
        Marshaller marshaller = JAXBUtil.getArchieJAXBContext().createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(cluster, writer);
        String xml = writer.toString();
        assertThat(xml, containsString(">12:00<"));
        assertThat(xml, containsString(">2016-01-01T12:00<"));
        assertThat(xml, containsString(">2016-01-01<"));

        System.out.println(xml);

        //now parse again
        Cluster parsedCluster = (Cluster) JAXBUtil.getArchieJAXBContext().createUnmarshaller().unmarshal(new StringReader(writer.toString()));
        RMQueryContext parsedQueryContext = new RMQueryContext(parsedCluster);
        
        assertThat(parsedQueryContext.<DvText>find("/items['Text']/value").getValue(), is("test-text"));
        assertThat(parsedQueryContext.<DvDate>find("/items['Date']/value").getValue(), is(LocalDate.of(2016, 1, 1)));
        assertThat(parsedQueryContext.<DvDateTime>find("/items['Datetime']/value").getValue(), is(LocalDateTime.of(2016, 1, 1, 12, 00)));
        assertThat(parsedQueryContext.<DvTime>find("/items['Time']/value").getValue(), is(LocalTime.of(12, 0)));
        assertEquals("double should be correct", parsedQueryContext.find("/items['Quantity']/value/magnitude"), 23d, 0.001d);
    }

}
