package org.openehr.bmm.persistence;

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

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Created by cnanjo on 4/11/16.
 */
public interface IPersistedBmmPackageContainer extends Serializable {

    public Map<String, PersistedBmmPackage> getPackages();
    public void setPackages(Map<String, PersistedBmmPackage> packages);
    public void setPackages(List<PersistedBmmPackage> packages);
    public void addPackage(PersistedBmmPackage aPackage);
    public void doRecursivePackages(Consumer<PersistedBmmPackage> agent);
}
