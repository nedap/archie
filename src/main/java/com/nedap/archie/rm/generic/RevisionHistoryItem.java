package com.nedap.archie.rm.generic;

import com.nedap.archie.rm.RMObject;
import com.nedap.archie.rm.support.identification.ObjectVersionId;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 08/07/16.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="REVISION_HISTORY_ITEM")
public class RevisionHistoryItem extends RMObject {

    private ObjectVersionId versionId;
    private List<AuditDetails> audits = new ArrayList<>();

    public ObjectVersionId getVersionId() {
        return versionId;
    }

    public void setVersionId(ObjectVersionId versionId) {
        this.versionId = versionId;
    }

    public List<AuditDetails> getAudits() {
        return audits;
    }

    public void setAudits(List<AuditDetails> audits) {
        this.audits = audits;
    }

    public void addAudit(AuditDetails audit) {
        this.audits.add(audit);
    }

}
