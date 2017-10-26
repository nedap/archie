package org.openehr.utils.message;

import org.junit.Before;
import org.junit.Test;
import org.openehr.utils.message.MessageDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class MessageDatabaseTest {

    private MessageDatabase database;

    @Before
    public void setup() {
        Map<String,String> templates = new HashMap<>();
        templates.put("template1","Reference model schema(s) {0} specified in options not valid or not found in schema directories");
        templates.put("template2","First is {0} while second is {1}");
        templates.put("message_code_error", "Some error message: {0}");
        database = new MessageDatabase(templates);
    }
    @Test
    public void hasMessage() throws Exception {
        assertTrue(database.hasMessage("template1"));
        assertFalse(database.hasMessage("not_here"));
    }

    @Test
    public void isDatabaseLoaded() throws Exception {
        assertTrue(database.isDatabaseLoaded());
        database.setMessageTable(null);
        try {
            assertFalse(database.isDatabaseLoaded());
        } catch(Exception e) {
            fail();
        }
    }

    @Test
    public void createMessageContent() throws Exception {
        assertEquals("Reference model schema(s) my_schema specified in options not valid or not found in schema directories", database.createMessageContent("template1", new ArrayList<String>(){{
            add("my_schema");
        }}));
        assertEquals("First is great while second is not so", database.createMessageContent("template2", new ArrayList<String>(){{
            add("great");
            add("not so");
        }}));
        assertNotEquals("First is great while second is not so", database.createMessageContent("template2", new ArrayList<String>(){{
            add("great");
            add("so so");
        }}));
        assertEquals("Some error message: not_in_table", database.createMessageContent("not_in_table", new ArrayList<String>(){{
            add("item1");
            add("item2");
        }}));
        assertEquals("Some error message: not_in_table", database.createMessageContent("not_in_table", null));
    }

    @Test
    public void createMessageLine() throws Exception {
        assertEquals("Reference model schema(s) my_schema specified in options not valid or not found in schema directories\n", database.createMessageLine("template1", new ArrayList<String>(){{
            add("my_schema");
        }}));
    }

    @Test
    public void createMessageText() throws Exception {
    }

}