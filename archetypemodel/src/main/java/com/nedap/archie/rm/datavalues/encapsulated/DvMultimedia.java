package com.nedap.archie.rm.datavalues.encapsulated;

import com.nedap.archie.rm.datatypes.CodePhrase;
import com.nedap.archie.rm.datavalues.DvURI;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by pieter.bos on 04/11/15.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "DV_MULTIMEDIA", propOrder = {
        "alternateText",
        "uri",
        "data",
        "mediaType",
        "compressionAlgorithm",
        "integrityCheck",
        "integrityCheckAlgorithm",
        "size",
        "thumbnail"
})
public class DvMultimedia extends DvEncapsulated {
    @Nullable

    private String alternateText;
    @Nullable
    private DvURI uri;
    @Nullable
    private byte[] data;
    private CodePhrase mediaType;//TODO: construct default codephrase with mime type already set as terminology id
    @Nullable
    private CodePhrase compressionAlgorithm;
    @Nullable
    private byte[] integrityCheck;

    @Nullable
    private CodePhrase integrityCheckAlgorithm;

    private Integer size;

    @Nullable
    private DvMultimedia thumbnail;

    @Nullable

    @XmlElement(name = "alternate_text")
    public String getAlternateText() {
        return alternateText;
    }

    public void setAlternateText(@Nullable String alternateText) {
        this.alternateText = alternateText;
    }

    @Nullable
    public DvURI getUri() {
        return uri;
    }

    public void setUri(@Nullable DvURI uri) {
        this.uri = uri;
    }

    @Nullable
    public byte[] getData() {
        return data;
    }

    public void setData(@Nullable byte[] data) {
        this.data = data;
    }

    @XmlElement(name = "media_type", required = true)
    public CodePhrase getMediaType() {
        return mediaType;
    }

    public void setMediaType(CodePhrase mediaType) {
        this.mediaType = mediaType;
    }

    @Nullable
    @XmlElement(name = "compression_algorithm")
    public CodePhrase getCompressionAlgorithm() {
        return compressionAlgorithm;
    }

    public void setCompressionAlgorithm(@Nullable CodePhrase compressionAlgorithm) {
        this.compressionAlgorithm = compressionAlgorithm;
    }

    @Nullable
    @XmlElement(name = "integrity_check")
    public byte[] getIntegrityCheck() {
        return integrityCheck;
    }

    public void setIntegrityCheck(@Nullable byte[] integrityCheck) {
        this.integrityCheck = integrityCheck;
    }


    @Nullable
    public DvMultimedia getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(@Nullable DvMultimedia thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Nullable
    @XmlElement(name = "integrity_check_algorithm")
    public CodePhrase getIntegrityCheckAlgorithm() {
        return integrityCheckAlgorithm;
    }

    public void setIntegrityCheckAlgorithm(@Nullable CodePhrase integrityCheckAlgorithm) {
        this.integrityCheckAlgorithm = integrityCheckAlgorithm;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
