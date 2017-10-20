package com.nedap.archie.aom;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Defined Object. Parameterized so the default value methods can be overridden with a different type
 * Created by pieter.bos on 15/10/15.
 */
@XmlType(name="C_DEFINED_OBJECT", propOrder= {
        "frozen",
        "defaultValue"
})
public class CDefinedObject<T> extends CObject {

    @XmlElement(name="is_frozen")
    private Boolean frozen;
    @XmlElement(name="default_value") //TODO: this will not deserialize, it needs possible classes
    private T defaultValue;

    public Boolean getFrozen() {
        return frozen;
    }

    public void setFrozen(Boolean frozen) {
        this.frozen = frozen;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(T defaultValue) {
        this.defaultValue = defaultValue;
    }
}
