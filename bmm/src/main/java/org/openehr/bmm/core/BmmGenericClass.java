package org.openehr.bmm.core;

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

import org.openehr.bmm.BmmConstants;
import org.openehr.odin.utils.OdinSerializationUtils;

import java.io.Serializable;
import java.util.*;

/**
 * Definition of a generic class in an object model.
 *
 * Created by cnanjo on 4/11/16.
 */
public class BmmGenericClass extends BmmClass implements Serializable {

    /**
     * List of generic parameter definitions, keyed by name of generic parameter; these are defined either directly on
     * this class or by the addition of an ancestor class which is generic.
     */
    private Map<String, BmmGenericParameter> genericParameters;

    public BmmGenericClass() {
        super();
        genericParameters = new LinkedHashMap<>();
    }

    public BmmGenericClass(String name) {
        this();
        setName(name);
    }

    /**
     * Returns shallow cloned list of generic parameter definitions; these are defined either directly on
     * this class or by the addition of an ancestor class which is generic.
     *
     * @return
     */
    public List<BmmGenericParameter> getGenericParameters() {
        List<BmmGenericParameter> parameters = new ArrayList<>();
        parameters.addAll(genericParameters.values());
        return parameters;
    }

    /**
     * Sets list of generic parameter definitions, keyed by name of generic parameter; these are defined either directly
     * on this class or by the addition of an ancestor class which is generic.
     *
     *
     * @param parameters
     */
    public void setGenericParameters(List<BmmGenericParameter> parameters) {
        this.genericParameters.clear();
        parameters.forEach(param -> {
            this.genericParameters.put(param.getName().toUpperCase(), param);
        });
    }

    /**
     * Adds generic parameter definition to this generic class.
     *
     * @param genericParameter
     */
    public void addGenericParameter(BmmGenericParameter genericParameter) {
        genericParameters.put(genericParameter.getName().toUpperCase(), genericParameter);
    }

    /**
     * Returns the generic parameter with the name provided.
     *
     * @param genericParameterName
     */
    public BmmGenericParameter getGenericParameter(String genericParameterName) {
        return genericParameters.get(genericParameterName.toUpperCase());
    }

    /**
     * Add suppliers from generic parameters.
     *
     * @return
     */
    public List<String> getSuppliers() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Signature form of the type, which for generics includes generic parameter constrainer types
     * E.g. Interval<T:Ordered>
     *
     * @return
     */
    @Override
    public String getTypeSignature() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Formal string form of the type as per UML.
     *
     * @return
     */
    @Override
    public String getTypeName() {
        return "Class<T>";//throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     *
     * @return
     */
    @Override
    public BmmGenericClass duplicate() {
        BmmGenericClass target = (BmmGenericClass)super.duplicate();
        target.setGenericParameters(this.getGenericParameters());
        return target;
    }
}
