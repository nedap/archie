package org.openehr.utils.message;

/**
 * A message that does not have a code is represented with an UnknownMessageCode
 */
public class UnknownMessageCode implements MessageCode {
    @Override
    public String getCode() {
        return "";
    }

    @Override
    public String getMessage() {
        return "";
    }
}
