package org.openehr.utils.message;

/**
 * A message code that includes both the code and the template.
 */
public interface MessageCode {
    /** Get the code that uniquely identifies this within the given context */
    public String getCode();
    /** Get the message template, in English */
    public String getMessageTemplate();
}
