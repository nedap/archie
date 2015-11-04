package com.nedap.archie.rm.datavalues.encapsulated;

import com.nedap.archie.rm.datatypes.CodePhrase;
import com.nedap.archie.rm.datavalues.DvURI;

import javax.annotation.Nullable;

/**
 * Created by pieter.bos on 04/11/15.
 */
public class DvMultimedia {
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
    private Byte integrityCheck;//TODO: one byte only?!

    @Nullable
    private CodePhrase integrity_check_algorithm;

    @Nullable
    private DvMultimedia thumbnail;

    @Nullable

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

    public CodePhrase getMediaType() {
        return mediaType;
    }

    public void setMediaType(CodePhrase mediaType) {
        this.mediaType = mediaType;
    }

    @Nullable
    public CodePhrase getCompressionAlgorithm() {
        return compressionAlgorithm;
    }

    public void setCompressionAlgorithm(@Nullable CodePhrase compressionAlgorithm) {
        this.compressionAlgorithm = compressionAlgorithm;
    }

    @Nullable
    public Byte getIntegrityCheck() {
        return integrityCheck;
    }

    public void setIntegrityCheck(@Nullable Byte integrityCheck) {
        this.integrityCheck = integrityCheck;
    }

    @Nullable
    public CodePhrase getIntegrity_check_algorithm() {
        return integrity_check_algorithm;
    }

    public void setIntegrity_check_algorithm(@Nullable CodePhrase integrity_check_algorithm) {
        this.integrity_check_algorithm = integrity_check_algorithm;
    }

    @Nullable
    public DvMultimedia getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(@Nullable DvMultimedia thumbnail) {
        this.thumbnail = thumbnail;
    }
}
