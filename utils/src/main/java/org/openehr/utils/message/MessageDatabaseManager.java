package org.openehr.utils.message;

/*
 * #%L
 * OpenEHR - Java Model Stack
 * %%
 * Copyright (C) 2016 - 2017  Cognitive Medical Systems, Inc (http://www.cognitivemedicine.com).
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 * Author: Claude Nanjo
 */

import java.text.MessageFormat;
import java.util.List;

public class MessageDatabaseManager {

    public static MessageDatabaseManager getInstance() {
        return new MessageDatabaseManager();
    }

    public String getMessageLine(MessageCode code, List<String> args) {
        return MessageFormat.format(code.getMessageTemplate(), args);
    }

    public String getMessage(MessageCode  code, List<String> args) {
        return MessageFormat.format(code.getMessageTemplate(), args);
    }

    public String getMessage(MessageCode code, String... args) {
        //TODO: add i18n with ResourceBundles here
        return MessageFormat.format(code.getMessageTemplate(), args);
    }

}
