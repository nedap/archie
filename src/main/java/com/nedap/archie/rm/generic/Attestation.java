package com.nedap.archie.rm.generic;

import com.nedap.archie.rm.datavalues.DvEHRURI;
import com.nedap.archie.rm.datavalues.DvText;
import com.nedap.archie.rm.datavalues.encapsulated.DvMultimedia;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 08/07/16.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ATTESTATION")
public class Attestation extends AuditDetails {

    @Nullable
    @XmlElement(name="attested_view")
    private DvMultimedia attestedView;
    @Nullable
    private String proof;
    @Nullable
    private List<DvEHRURI> items = new ArrayList<>();
    private DvText reason;
    @XmlElement(name="is_pending")
    private boolean isPending;



}
