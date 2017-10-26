package org.openehr.utils.validation;

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

import java.util.List;

public class MessageDatabaseManager {

    private static final MessageDatabaseManager instance = new MessageDatabaseManager();

    private MessageDatabase messageDb = new MessageDatabase();

    public static MessageDatabaseManager getInstance() {
        return instance;
    }

    public String getMessageLine(String anId, List<String> args) {
        return messageDb.createMessageLine(anId, args);
    }

    public String getMessage(String anId, List<String> args) {
        return messageDb.createMessageContent(anId, args);
    }

    public String getText(String anId) {
        return messageDb.createMessageText(anId);
    }
}
