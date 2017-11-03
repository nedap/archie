package org.openehr.utils.error;

import org.junit.Before;
import org.junit.Test;
import org.openehr.utils.message.MessageCode;
import org.openehr.utils.message.MessageDescriptor;
import org.openehr.utils.message.MessageLogger;
import org.openehr.utils.message.MessageSeverity;
import org.openehr.utils.validation.TestErrorCode;

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
        errorAccumulator.addErrorWithLocation(TestErrorCode.code1, null, null);
        errorAccumulator.addErrorWithLocation(TestErrorCode.code2, null, null);
        errorAccumulator.addErrorWithLocation(TestErrorCode.code3, null, null);
        assertEquals(errorAccumulator.lastAdded().getCode(), TestErrorCode.code3);
    }

    @Test
    public void getErrorCodes() throws Exception {
        errorAccumulator.addErrorWithLocation(TestErrorCode.code1, null, null);
        errorAccumulator.addWarningWithLocation(TestErrorCode.code2, null, null);
        errorAccumulator.addInfoWithLocation(TestErrorCode.code3, null, null);
        errorAccumulator.addDebugWithLocation(TestErrorCode.code4.getMessage(), null);//debug does not accept codes, for some reason!
        List<MessageCode> codes = errorAccumulator.getErrorCodes();
        assertEquals(codes.size(), 1);
        assertEquals(codes.get(0), TestErrorCode.code1);
    }

    @Test
    public void getWarningCodes() throws Exception {
        errorAccumulator.addErrorWithLocation(TestErrorCode.code1, null, null);
        errorAccumulator.addWarningWithLocation(TestErrorCode.code2, null, null);
        errorAccumulator.addInfoWithLocation(TestErrorCode.code3, null, null);
        errorAccumulator.addDebugWithLocation(TestErrorCode.code4.getCode(), null);
        List<MessageCode> codes = errorAccumulator.getWarningCodes();
        assertEquals(codes.size(), 1);
        assertEquals(codes.get(0), TestErrorCode.code2);
    }

    @Test
    public void isEmpty1() throws Exception {
        assertTrue(errorAccumulator.isEmpty());
    }

    @Test
    public void isEmpty2() throws Exception {
        errorAccumulator.addErrorWithLocation(TestErrorCode.code, null, null);
        assertFalse(errorAccumulator.isEmpty());
    }

    @Test
    public void hasErrors() throws Exception {
        errorAccumulator.addErrorWithLocation(TestErrorCode.code, null, null);
        assertTrue(errorAccumulator.hasErrors());
        assertFalse(errorAccumulator.hasWarnings());
        assertFalse(errorAccumulator.hasInfo());
    }

    @Test
    public void hasWarnings() throws Exception {
        errorAccumulator.addWarningWithLocation(TestErrorCode.code, null, null);
        assertTrue(errorAccumulator.hasWarnings());
        assertFalse(errorAccumulator.hasErrors());
        assertFalse(errorAccumulator.hasInfo());
    }

    @Test
    public void hasInfo() throws Exception {
        errorAccumulator.addInfoWithLocation(TestErrorCode.code, null, null);
        assertTrue(errorAccumulator.hasInfo());
        assertFalse(errorAccumulator.hasWarnings());
        assertFalse(errorAccumulator.hasErrors());
    }

    @Test
    public void hasErrorsOrWarnings1() throws Exception {
        errorAccumulator.addErrorWithLocation(TestErrorCode.code, null, null);
        assertTrue(errorAccumulator.hasErrorsOrWarnings());
    }

    @Test
    public void hasErrorsOrWarnings2() throws Exception {
        errorAccumulator.addWarningWithLocation(TestErrorCode.code, null, null);
        assertTrue(errorAccumulator.hasErrorsOrWarnings());
    }

    @Test
    public void hasErrorsOrWarnings3() throws Exception {
        errorAccumulator.addWarningWithLocation(TestErrorCode.code1, null, null);
        errorAccumulator.addErrorWithLocation(TestErrorCode.code2, null, null);
        assertTrue(errorAccumulator.hasErrorsOrWarnings());
    }

    @Test
    public void hasErrorsOrWarnings4() throws Exception {
        assertFalse(errorAccumulator.hasErrorsOrWarnings());
    }

    @Test
    public void hasError1() throws Exception {
        errorAccumulator.addInfoWithLocation(TestErrorCode.code0, null, null);
        errorAccumulator.addWarningWithLocation(TestErrorCode.code1, null, null);
        errorAccumulator.addErrorWithLocation(TestErrorCode.code2, null, null);
        assertTrue(errorAccumulator.hasError(TestErrorCode.code2));
        assertFalse(errorAccumulator.hasError(TestErrorCode.code3));
        assertFalse(errorAccumulator.hasError(TestErrorCode.code1));
    }

    @Test
    public void hasError2() throws Exception {
        assertFalse(errorAccumulator.hasError(TestErrorCode.code1));
    }

    @Test
    public void hasMatchingError() throws Exception {
        errorAccumulator.addInfoWithLocation(TestErrorCode.code0, null, null);
        errorAccumulator.addWarningWithLocation(TestErrorCode.code1, null, null);
        errorAccumulator.addErrorWithLocation(TestErrorCode.code2, null, null);
        assertTrue(errorAccumulator.hasMatchingError(TestErrorCode.code2));
        assertFalse(errorAccumulator.hasMatchingError(TestErrorCode.code0));
        assertFalse(errorAccumulator.hasMatchingError(TestErrorCode.code1));
    }

    @Test
    public void hasMatchingWarning() throws Exception {
        errorAccumulator.addInfoWithLocation(TestErrorCode.code0, null, null);
        errorAccumulator.addWarningWithLocation(TestErrorCode.code1, null, null);
        errorAccumulator.addErrorWithLocation(TestErrorCode.code2, null, null);
        assertTrue(errorAccumulator.hasMatchingWarning(TestErrorCode.code1));
        assertFalse(errorAccumulator.hasMatchingWarning(TestErrorCode.code2));
        assertFalse(errorAccumulator.hasMatchingWarning(TestErrorCode.code0));
    }

    @Test
    public void addError() throws Exception {
        errorAccumulator.addErrorWithLocation(TestErrorCode.code2, null, null);
        assertEquals(1, errorAccumulator.getMessageList().size());
    }

    @Test
    public void addWarning() throws Exception {
        errorAccumulator.addWarningWithLocation(TestErrorCode.code1, null, null);
        assertEquals(1, errorAccumulator.getMessageList().size());
    }

    @Test
    public void addInfo() throws Exception {
        errorAccumulator.addInfoWithLocation(TestErrorCode.code0, null, null);
        assertEquals(1, errorAccumulator.getMessageList().size());
    }

    @Test
    public void addDebug() throws Exception {
        errorAccumulator.addDebugWithLocation(TestErrorCode.code0.getCode(), null);//debug does not use a code?!
        assertEquals(1, errorAccumulator.getMessageList().size());
    }

    @Test
    public void add() throws Exception {
        errorAccumulator.add(new MessageDescriptor(TestErrorCode.code0, MessageSeverity.DEBUG, null, null));
        assertEquals(1, errorAccumulator.getMessageList().size());
    }

    @Test
    public void append() throws Exception {
        MessageLogger one = new MessageLogger();
        MessageLogger two = new MessageLogger();

        one.addInfoWithLocation(TestErrorCode.code0, null, null);
        one.addWarningWithLocation(TestErrorCode.code1, null, null);
        one.addErrorWithLocation(TestErrorCode.code2, null, null);

        two.addInfoWithLocation(TestErrorCode.code0, null, null);
        two.addWarningWithLocation(TestErrorCode.code1, null, null);
        two.addErrorWithLocation(TestErrorCode.code2, null, null);

        one.append(two);

        assertEquals(6, one.getMessageList().size());
        assertEquals(3, two.getMessageList().size());

        assertEquals(2, one.getErrorCodes().size());
        assertEquals(2, one.getWarningCodes().size());

    }

    @Test
    public void clear() throws Exception {
        assertEquals(errorAccumulator.getMessageList().size(), 0);
        errorAccumulator.addInfoWithLocation(TestErrorCode.code0, null, null);
        errorAccumulator.addWarningWithLocation(TestErrorCode.code1, null, null);
        errorAccumulator.addErrorWithLocation(TestErrorCode.code2, null, null);
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
        errorAccumulator.addInfoWithLocation(TestErrorCode.code0, null, null);
        errorAccumulator.addWarningWithLocation(TestErrorCode.code1, null, null);
        errorAccumulator.addErrorWithLocation(TestErrorCode.code2, null, null);

        assertEquals(3, errorAccumulator.getMessageList().size());
    }

    @Test
    public void toStringFiltered() throws Exception {
        errorAccumulator.addInfoWithLocation(TestErrorCode.code0, null, null);
        errorAccumulator.addWarningWithLocation(TestErrorCode.code1, null, null);
        errorAccumulator.addErrorWithLocation(TestErrorCode.code2, null, null);

        assertEquals("ERROR (code2) (Error DB load failure); original error params %1; %2; %3; %4\n", errorAccumulator.toStringFiltered(true, false, false));
        assertEquals("WARNING (code1) (Error DB load failure); original error params %1; %2; %3; %4\n", errorAccumulator.toStringFiltered(false, true, false));
        assertEquals("INFO (code0) (Error DB load failure); original error params %1; %2; %3; %4\n", errorAccumulator.toStringFiltered(false, false, true));
        assertEquals("INFO (code0) (Error DB load failure); original error params %1; %2; %3; %4\nWARNING (code1) (Error DB load failure); original error params %1; %2; %3; %4\nERROR (code2) (Error DB load failure); original error params %1; %2; %3; %4\n", errorAccumulator.toStringFiltered(true, true, true));
    }

}