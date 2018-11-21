package org.openehr.utils.message;

import java.text.MessageFormat;
import java.util.Locale;

/**
 * A message code that includes both the code and the template.
 */
public interface MessageCode {
    /** Get the code that uniquely identifies this within the given context */
    public String getCode();
    /** Get the message template, in English */
    public String getMessageTemplate();

    /**
     * Get the message translated to the current locale. See I18n for more information on how to set this.
     * @param args
     * @return
     */
    default String getMessage(Object... args) {
        return I18n.t(getMessageTemplate(), args);
    }

    /**
     * Get the message translated to the specified locale. See I18n for more information on how to set this.
     * @param args
     * @return
     */
    default String getMessage(Locale locale, Object... args) {
        return I18n.t(getMessageTemplate(), locale, args);
    }
}
