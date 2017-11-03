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

import org.openehr.bmm.core.BmmClass;
import org.openehr.bmm.core.BmmEnumeration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Persistent form of BMM_ENUMERATION attributes.
 *
 * Created by cnanjo on 4/11/16.
 */
public abstract class PersistedBmmEnumeration<T> extends PersistedBmmClass implements Serializable {

    public PersistedBmmEnumeration() {
        super();
        this.itemNames = new ArrayList<>();
        this.itemValues = new ArrayList<>();
    }

    public PersistedBmmEnumeration(String name) {
        super(name);
        this.itemNames = new ArrayList<>();
        this.itemValues = new ArrayList<>();
    }

    /**
     * Names of enumeration elements.
     */
    private List<String> itemNames;
    /**
     * Values associated with enumeration elements.
     */
    private List<T> itemValues;

    /**
     * Returns list of enumeration element names.
     *
     * @return
     */
    public List<String> getItemNames() {
        return itemNames;
    }

    /**
     * Sets the list of enumeration element names.
     *
     * @param itemNames
     */
    public void setItemNames(List<String> itemNames) {
        this.itemNames = itemNames;
    }

    /**
     * Adds an item to the enumeration.
     *
     * @param itemName
     */
    public void addItemName(String itemName) {
        this.itemNames.add(itemName);
    }

    /**
     * Returns the list of item values associated with the named element at the same index.
     *
     * @return
     */
    public List<T> getItemValues() {
        return itemValues;
    }

    /**
     * Sets the list of item values associated with the named element at the same index.
     *
     * @param itemValues
     */
    public void setItemValues(List<T> itemValues) {
        this.itemValues = itemValues;
    }

    /**
     * Add an item's value to the enumeration.
     *
     * @param itemValue
     */
    public void addItemValue(T itemValue) {
        this.itemValues.add(itemValue);
    }

    /**
     * Adds an element and a value to this enumeration.
     *
     * @param name
     * @param value
     */
    public void addElementAndValue(String name, T value) {
        this.itemNames.add(name);
        this.itemValues.add(value);
    }

    /**
     * Create bmm_class_definition.
     *
     * @return
     */
    public abstract void createBmmClass();
}
