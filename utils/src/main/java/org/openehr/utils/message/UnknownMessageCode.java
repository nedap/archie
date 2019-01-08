package org.openehr.utils.message;

/**
 * A message that does not have a code is represented with an UnknownMessageCode
 *
 * This class is never required - just define error codes. It's still used for debug messages so waiting for removal until that has been fixed.
 */
@Deprecated
class UnknownMessageCode implements MessageCode {
    @Override
    public String getCode() {
        return "";
    }

    @Override
    public String getMessageTemplate() {
        return "{0}";
    }
}
