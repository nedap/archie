package com.nedap.archie.aom;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Defined Object. Parameterized so the default value methods can be overridden with a different type
 * Created by pieter.bos on 15/10/15.
 */
@XmlType(name="C_DEFINED_OBJECT", propOrder= {
        "defaultValue"
})
public abstract class CDefinedObject<T> extends CObject {

    @XmlElement(name="default_value") //TODO: this will not deserialize, it needs possible classes
    private T defaultValue;

    public T getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(T defaultValue) {
        this.defaultValue = defaultValue;
    }
}
