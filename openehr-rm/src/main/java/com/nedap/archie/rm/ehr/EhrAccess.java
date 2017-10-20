package com.nedap.archie.rm.ehr;

import com.nedap.archie.rm.archetyped.Locatable;
import com.nedap.archie.rm.security.AccessControlSettings;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by pieter.bos on 08/07/16.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="EHR_ACCESS")
public class EhrAccess extends Locatable {
    @Nullable
    private AccessControlSettings settings;

    @Nullable
    public AccessControlSettings getSettings() {
        return settings;
    }

    public void setSettings(@Nullable AccessControlSettings settings) {
        this.settings = settings;
    }
}
