package org.openehr.referencemodel.loader;

/*
 * #%L
 * OpenEHR - Java Model Stack
 * %%
 * Copyright (C) 2016 - 2017 Cognitive Medical Systems
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

import org.openehr.bmm.persistence.PersistedBmmSchema;
import org.openehr.odin.CompositeOdinObject;
import org.openehr.odin.antlr.OdinVisitorImpl;
import org.openehr.odin.loader.OdinLoaderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.InputStream;

/**
 * Created by cnanjo on 3/31/16.
 */
public class ReferenceModelLoader extends OdinLoaderImpl {

    private static Logger log = LoggerFactory.getLogger(ReferenceModelLoader.class);

    public PersistedBmmSchema loadCimiReferenceModel(InputStream inputStream) {
        OdinVisitorImpl visitor = loadOdinFile(inputStream);
        CompositeOdinObject root = visitor.getAstRootNode();
        return null;//PersistedBmmSchema.configureModelFromOdinObject(root);
    }
}
