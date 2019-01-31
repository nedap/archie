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

import com.nedap.archie.base.MultiplicityInterval;
import org.openehr.bmm.BmmConstants;
import org.openehr.bmm.BmmMultiplicityInterval;
import org.openehr.bmm.core.*;
import org.openehr.odin.CompositeOdinObject;
import org.openehr.odin.IntegerIntervalObject;
import org.openehr.odin.OdinAttribute;
import org.openehr.odin.utils.OdinSerializationUtils;

import java.io.Serializable;

/**
 * Created by cnanjo on 4/11/16.
 */
@Deprecated
public abstract class PersistedBmmProperty<T extends PersistedBmmType> extends PersistedBmmModelElement implements Serializable {

	/**
	 * Name of this property within its class.
	 */
	private String name;
	/**
	 * True if this property is mandatory in its class.
	 */
	private boolean isMandatory = false;
	/**
	 * True if this property is computed rather than stored in objects of this class.
	 */
	private boolean isComputed = false;
	/**
	 * True if this property was marked with info model 'im_infrastructure' flag.
	 */
	private boolean isImInfrastructure = false;
	/**
	 * True if this property is info model 'infrastructure' rather than 'data'.
	 */
	private boolean isImRuntime = false;
	/**
	 * Type definition of this property, if not a simple String type reference.
	 */
	private T typeDefinition;
	/**
	 * BMM_PROPERTY created by create_bmm_property_definition.
	 */
	private BmmProperty bmmProperty;

	public PersistedBmmProperty() {
		super();
	}

	public PersistedBmmProperty(String name) {
		this();
		this.name = name;
	}

	public PersistedBmmProperty(String name, boolean isMandatory) {
		this(name);
		this.isMandatory = isMandatory;
	}

	/**
	 * Returns the name of this property within its class.
	 *
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of this property within its class.
	 *
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns true if this property is mandatory in its class.
	 *
	 * @return
	 */
	public Boolean getMandatory() {
		return isMandatory;
	}

	/**
	 * Set to true if this property is mandatory in its class.
	 * @param mandatory
	 */
	public void setMandatory(Boolean mandatory) {
		isMandatory = mandatory;
	}

	/**
	 * Returns true if this property is computed rather than stored in objects of this class.
	 *
	 * @return
	 */
	public Boolean getComputed() {
		return isComputed;
	}

	/**
	 * Set to true if this property is computed rather than stored in objects of this class.
	 *
	 * @param computed
	 */
	public void setComputed(Boolean computed) {
		isComputed = computed;
	}

	/**
	 * Returns true if this property is info model 'infrastructure' rather than 'data'.
	 *
	 * @return
	 */
	public Boolean getImInfrastructure() {
		return isImInfrastructure;
	}

	/**
	 * Set to true if this property is info model 'infrastructure' rather than 'data'.
	 *
	 * @param imInfrastructure
	 */
	public void setImInfrastructure(Boolean imInfrastructure) {
		isImInfrastructure = imInfrastructure;
	}

    /**
     * Returns true if this property is info model 'runtime' settable property.
	 *
	 * @return
     */
	public Boolean getImRuntime() {
		return isImRuntime;
	}

    /**
	 * Set to true if this property is info model 'runtime' settable property.
	 *
	 * @param imRuntime
	 */
	public void setImRuntime(Boolean imRuntime) {
		isImRuntime = imRuntime;
	}

	/**
	 * Returns type definition of this property, if not a simple String type reference.
	 * @return
	 */
	public T getTypeDefinition() {
		return this.typeDefinition;
	}

	/**
	 * Sets type definition of this property, if not a simple String type reference.
	 * @param typeDefinition
	 */
	public void setTypeDefinition(T typeDefinition) {
		this.typeDefinition = typeDefinition;
	}

	/**
	 * BMM_PROPERTY created by create_bmm_property_definition.
	 *
	 * @return
	 */
	public BmmProperty getBmmProperty() {
		return bmmProperty;
	}

	/**
	 * BMM_PROPERTY created by create_bmm_property_definition.
	 *
	 * @param bmmProperty
	 */
	protected void setBmmProperty(BmmProperty bmmProperty) {
		this.bmmProperty = bmmProperty;
	}

	public void createBmmProperty(BmmModel bmmSchema, BmmClass classDefinition) {
		if(getTypeDefinition() != null) {
			getTypeDefinition().createBmmType(bmmSchema, classDefinition);
			if(getTypeDefinition().getBmmType() != null) {
				bmmProperty = new BmmProperty(name, typeDefinition.getBmmType());
				bmmProperty.setDocumentation(getDocumentation());
				bmmProperty.setMandatory(isMandatory);
				bmmProperty.setComputed(isComputed);
				bmmProperty.setImInfrastructure(isImInfrastructure);
				bmmProperty.setImRuntime(isImRuntime);
			}
		}
	}
}
