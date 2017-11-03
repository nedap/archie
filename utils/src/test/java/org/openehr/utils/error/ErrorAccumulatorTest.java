package org.openehr.utils.error;

import org.junit.Before;
import org.junit.Test;
import org.openehr.utils.message.MessageDescriptor;
import org.openehr.utils.message.MessageLogger;

import java.util.List;

import static org.junit.Assert.*;

public class ErrorAccumulatorTest {

    private MessageLogger errorAccumulator;

    @Before
    public void setup() {
        errorAccumulator = new MessageLogger();
    }

    @Test
    public void lastAdded() throws Exception {
        errorAccumulator.addError("code1", null, null);
        errorAccumulator.addError("code2", null, null);
        errorAccumulator.addError("code3", null, null);
        assertEquals(errorAccumulator.lastAdded().getCode(), "code3");
    }

    @Test
    public void getErrorCodes() throws Exception {
        errorAccumulator.addError("code1", null, null);
        errorAccumulator.addWarning("code2", null, null);
        errorAccumulator.addInfo("code3", null, null);
        errorAccumulator.addDebug("code4", null);
        List<String> codes = errorAccumulator.getErrorCodes();
        assertEquals(codes.size(), 1);
        assertEquals(codes.get(0), "code1");
    }

    @Test
    public void getWarningCodes() throws Exception {
        errorAccumulator.addError("code1", null, null);
        errorAccumulator.addWarning("code2", null, null);
        errorAccumulator.addInfo("code3", null, null);
        errorAccumulator.addDebug("code4", null);
        List<String> codes = errorAccumulator.getWarningCodes();
        assertEquals(codes.size(), 1);
        assertEquals(codes.get(0), "code2");
    }

    @Test
    public void isEmpty1() throws Exception {
        assertTrue(errorAccumulator.isEmpty());
    }

    @Test
    public void isEmpty2() throws Exception {
        errorAccumulator.addError("code", null, null);
        assertFalse(errorAccumulator.isEmpty());
    }

    @Test
    public void hasErrors() throws Exception {
        errorAccumulator.addError("code", null, null);
        assertTrue(errorAccumulator.hasErrors());
        assertFalse(errorAccumulator.hasWarnings());
        assertFalse(errorAccumulator.hasInfo());
    }

    @Test
    public void hasWarnings() throws Exception {
        errorAccumulator.addWarning("code", null, null);
        assertTrue(errorAccumulator.hasWarnings());
        assertFalse(errorAccumulator.hasErrors());
        assertFalse(errorAccumulator.hasInfo());
    }

    @Test
    public void hasInfo() throws Exception {
        errorAccumulator.addInfo("code", null, null);
        assertTrue(errorAccumulator.hasInfo());
        assertFalse(errorAccumulator.hasWarnings());
        assertFalse(errorAccumulator.hasErrors());
    }

    @Test
    public void hasErrorsOrWarnings1() throws Exception {
        errorAccumulator.addError("code", null, null);
        assertTrue(errorAccumulator.hasErrorsOrWarnings());
    }

    @Test
    public void hasErrorsOrWarnings2() throws Exception {
        errorAccumulator.addWarning("code", null, null);
        assertTrue(errorAccumulator.hasErrorsOrWarnings());
    }

    @Test
    public void hasErrorsOrWarnings3() throws Exception {
        errorAccumulator.addWarning("code1", null, null);
        errorAccumulator.addError("code2", null, null);
        assertTrue(errorAccumulator.hasErrorsOrWarnings());
    }

    @Test
    public void hasErrorsOrWarnings4() throws Exception {
        assertFalse(errorAccumulator.hasErrorsOrWarnings());
    }

    @Test
    public void hasError1() throws Exception {
        errorAccumulator.addInfo("code0", null, null);
        errorAccumulator.addWarning("code1", null, null);
        errorAccumulator.addError("code2", null, null);
        assertTrue(errorAccumulator.hasError("code2"));
        assertFalse(errorAccumulator.hasError("code3"));
        assertFalse(errorAccumulator.hasError("code1"));
    }

    @Test
    public void hasError2() throws Exception {
        assertFalse(errorAccumulator.hasError("code1"));
    }

    @Test
    public void hasMatchingError() throws Exception {
        errorAccumulator.addInfo("code0", null, null);
        errorAccumulator.addWarning("code1", null, null);
        errorAccumulator.addError("code2", null, null);
        assertTrue(errorAccumulator.hasMatchingError("code2"));
        assertFalse(errorAccumulator.hasMatchingError("code0"));
        assertFalse(errorAccumulator.hasMatchingError("code1"));
    }

    @Test
    public void hasMatchingWarning() throws Exception {
        errorAccumulator.addInfo("code0", null, null);
        errorAccumulator.addWarning("code1", null, null);
        errorAccumulator.addError("code2", null, null);
        assertTrue(errorAccumulator.hasMatchingWarning("code1"));
        assertFalse(errorAccumulator.hasMatchingWarning("code2"));
        assertFalse(errorAccumulator.hasMatchingWarning("code0"));
    }

    @Test
    public void addError() throws Exception {
        errorAccumulator.addError("code2", null, null);
        assertEquals(1, errorAccumulator.getMessageList().size());
    }

    @Test
    public void addWarning() throws Exception {
        errorAccumulator.addWarning("code1", null, null);
        assertEquals(1, errorAccumulator.getMessageList().size());
    }

    @Test
    public void addInfo() throws Exception {
        errorAccumulator.addInfo("code0", null, null);
        assertEquals(1, errorAccumulator.getMessageList().size());
    }

    @Test
    public void addDebug() throws Exception {
        errorAccumulator.addDebug("code0", null);
        assertEquals(1, errorAccumulator.getMessageList().size());
    }

    @Test
    public void add() throws Exception {
        errorAccumulator.add(new MessageDescriptor("code0", 0, null, null));
        assertEquals(1, errorAccumulator.getMessageList().size());
    }

    @Test
    public void append() throws Exception {
        MessageLogger one = new MessageLogger();
        MessageLogger two = new MessageLogger();

        one.addInfo("code0", null, null);
        one.addWarning("code1", null, null);
        one.addError("code2", null, null);

        two.addInfo("code0", null, null);
        two.addWarning("code1", null, null);
        two.addError("code2", null, null);

        one.append(two);

        assertEquals(6, one.getMessageList().size());
        assertEquals(3, two.getMessageList().size());

        assertEquals(2, one.getErrorCodes().size());
        assertEquals(2, one.getWarningCodes().size());

    }

    @Test
    public void clear() throws Exception {
        assertEquals(errorAccumulator.getMessageList().size(), 0);
        errorAccumulator.addInfo("code0", null, null);
        errorAccumulator.addWarning("code1", null, null);
        errorAccumulator.addError("code2", null, null);
        assertEquals(errorAccumulator.getMessageList().size(), 3);
        assertTrue(errorAccumulator.hasErrors());
        assertTrue(errorAccumulator.hasWarnings());
        assertTrue(errorAccumulator.hasInfo());
        errorAccumulator.clear();
        assertEquals(errorAccumulator.getMessageList().size(), 0);
        assertFalse(errorAccumulator.hasErrors());
        assertFalse(errorAccumulator.hasWarnings());
        assertFalse(errorAccumulator.hasInfo());
    }

    @Test
    public void getErrorList() throws Exception {
        errorAccumulator.addInfo("code0", null, null);
        errorAccumulator.addWarning("code1", null, null);
        errorAccumulator.addError("code2", null, null);

        assertEquals(3, errorAccumulator.getMessageList().size());
    }

    @Test
    public void toStringFiltered() throws Exception {
        errorAccumulator.addInfo("code0", null, null);
        errorAccumulator.addWarning("code1", null, null);
        errorAccumulator.addError("code2", null, null);

        assertEquals("ERROR (code2) (Error DB load failure); original error params %1; %2; %3; %4\n", errorAccumulator.toStringFiltered(true, false, false));
        assertEquals("WARNING (code1) (Error DB load failure); original error params %1; %2; %3; %4\n", errorAccumulator.toStringFiltered(false, true, false));
        assertEquals("INFO (code0) (Error DB load failure); original error params %1; %2; %3; %4\n", errorAccumulator.toStringFiltered(false, false, true));
        assertEquals("INFO (code0) (Error DB load failure); original error params %1; %2; %3; %4\nWARNING (code1) (Error DB load failure); original error params %1; %2; %3; %4\nERROR (code2) (Error DB load failure); original error params %1; %2; %3; %4\n", errorAccumulator.toStringFiltered(true, true, true));
    }

}