package org.openehr.utils.validation;

import org.junit.Before;
import org.junit.Test;
import org.openehr.utils.message.MessageDatabaseManager;
import org.openehr.utils.message.MessageLogger;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class AnyValidatorTest {

    AnyValidator validator = null;

    @Before
    public void setup() {
        validator = new BasicValidator();
        MessageDatabaseManager.getInstance().getMessageDatabase().getMessageTable().put("ErrorKey", "Error is {0}");
    }

    @Test
    public void hasPassed1() throws Exception {
        assertTrue(validator.hasPassed());
        validator.addError("ErrorKey", new ArrayList<String>() {{
            add("argument 1");
        }});
        assertTrue(validator.hasPassed());
        validator.validate();
        assertFalse(validator.hasPassed());
        assertEquals(1, validator.getMessageLogger().getMessageList().size());
    }

    @Test
    public void hasPassed2() throws Exception {
        assertTrue(validator.hasPassed());
        MessageLogger other = new MessageLogger();
        other.addError("ErrorKey", null, null);
        validator.mergeErrors(other);
        assertFalse(validator.hasPassed());
    }

    @Test
    public void getErrorCache() throws Exception {
        assertNotNull(validator.getMessageLogger());
        assertTrue(validator.getMessageLogger() instanceof MessageLogger);
    }

    @Test
    public void getErrorStrings() throws Exception {
    }

    @Test
    public void reset() throws Exception {
        assertTrue(validator.hasPassed());
        validator.addError("ErrorKey", new ArrayList<String>() {{
            add("argument 1");
        }});
        validator.validate();
        assertFalse(validator.hasPassed());
        validator.reset();
        assertTrue(validator.hasPassed());
        assertEquals(0,validator.getMessageLogger().getMessageList().size());
    }

    @Test
    public void hasErrors1() throws Exception {
        assertFalse(validator.hasErrors());
        validator.addError("ErrorKey", new ArrayList<String>() {{
            add("argument 1");
        }});
        assertTrue(validator.hasErrors());
    }

    @Test
    public void hasErrors2() throws Exception {
        assertFalse(validator.hasErrors());
        validator.addWarning("ErrorKey", new ArrayList<String>() {{
            add("argument 1");
        }});
        assertFalse(validator.hasErrors());
    }

    @Test
    public void hasNoErrors1() throws Exception {
        assertTrue(validator.hasNoErrors());
        validator.addError("ErrorKey", new ArrayList<String>() {{
            add("argument 1");
        }});
        assertFalse(validator.hasNoErrors());
    }

    @Test
    public void hasNoErrors2() throws Exception {
        assertTrue(validator.hasNoErrors());
        validator.addWarning("ErrorKey", new ArrayList<String>() {{
            add("argument 1");
        }});
        assertTrue(validator.hasNoErrors());
    }

    @Test
    public void hasError1() throws Exception {
        validator.addError("ErrorKey", new ArrayList<String>() {{
            add("argument 1");
        }});
        assertTrue(validator.hasError("ErrorKey"));
    }

    @Test
    public void hasError2() throws Exception {
        validator.addWarning("ErrorKey", new ArrayList<String>() {{
            add("argument 1");
        }});
        assertFalse(validator.hasError("ErrorKey"));
    }

    @Test
    public void hasWarning1() throws Exception {
        validator.addWarning("ErrorKey", new ArrayList<String>() {{
            add("argument 1");
        }});
        assertTrue(validator.hasWarning("ErrorKey"));
    }

    @Test
    public void hasWarning2() throws Exception {
        validator.addError("ErrorKey", new ArrayList<String>() {{
            add("argument 1");
        }});
        assertFalse(validator.hasWarning("ErrorKey"));
    }

    @Test
    public void mergeErrors() throws Exception {
        validator.addError("ErrorKey", new ArrayList<String>() {{
            add("argument 1");
        }});
        MessageLogger other = new MessageLogger();
        other.addError("ErrorKey", new ArrayList<String>() {{
            add("argument 1");
        }}, null);
        validator.mergeErrors(other);
        assertEquals(2, validator.getMessageCount());
    }

    @Test
    public void addError() throws Exception {
        validator.addError("ErrorKey", new ArrayList<String>() {{
            add("argument 1");
        }});
        assertTrue(validator.hasError("ErrorKey"));
        assertFalse(validator.hasWarning("ErrorKey"));
        assertFalse(validator.hasInfo("ErrorKey"));
    }

    @Test
    public void addWarning() throws Exception {
        validator.addWarning("ErrorKey", new ArrayList<String>() {{
            add("argument 1");
        }});
        assertTrue(validator.hasWarning("ErrorKey"));
        assertFalse(validator.hasError("ErrorKey"));
        assertFalse(validator.hasInfo("ErrorKey"));
    }

    @Test
    public void addInfo() throws Exception {
        validator.addInfo("ErrorKey", new ArrayList<String>() {{
            add("argument 1");
        }});
        assertTrue(validator.hasInfo("ErrorKey"));
        assertFalse(validator.hasWarning("ErrorKey"));
        assertFalse(validator.hasError("ErrorKey"));
    }

    @Test
    public void addErrorWithLocation() throws Exception {
        validator.addErrorWithLocation("ErrorKey", new ArrayList<String>() {{
            add("argument 1");
        }}, null);
        assertTrue(validator.hasError("ErrorKey"));
        assertFalse(validator.hasWarning("ErrorKey"));
        assertFalse(validator.hasInfo("ErrorKey"));
    }

    @Test
    public void addWarningWithLocation() throws Exception {
        validator.addWarningWithLocation("ErrorKey", new ArrayList<String>() {{
            add("argument 1");
        }}, null);
        assertTrue(validator.hasWarning("ErrorKey"));
        assertFalse(validator.hasError("ErrorKey"));
        assertFalse(validator.hasInfo("ErrorKey"));
    }

    @Test
    public void addInfoWithLocation() throws Exception {
        validator.addInfoWithLocation("ErrorKey", new ArrayList<String>() {{
            add("argument 1");
        }}, null);
        assertTrue(validator.hasInfo("ErrorKey"));
        assertFalse(validator.hasWarning("ErrorKey"));
        assertFalse(validator.hasError("ErrorKey"));
    }

    @Test
    public void readyToValidate1() throws Exception {
        assertTrue(validator.readyToValidate());
    }

    @Test
    public void readyToValidate2() throws Exception {
        validator.addErrorWithLocation("ErrorKey", new ArrayList<String>() {{
            add("argument 1");
        }}, null);
        validator.validate();
        assertFalse(validator.readyToValidate());
    }

    @Test
    public void readyToValidate3() throws Exception {
        validator.addErrorWithLocation("ErrorKey", new ArrayList<String>() {{
            add("argument 1");
        }}, null);
        validator.validate();
        assertFalse(validator.readyToValidate());
        validator.reset();
        assertTrue(validator.readyToValidate());
    }

    @Test
    public void validate() throws Exception {
        try {
            validator.setPassed(false);
            validator.validate();
            fail();
        } catch(Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }

}