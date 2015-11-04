package com.nedap.archie.rm.composition;

import com.nedap.archie.rm.archetypes.Locatable;
import com.nedap.archie.rm.datatypes.PartyProxy;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 03/11/15.
 */
public class ContentItem extends Locatable {

    private PartyProxy subject;
    @Nullable
    private PartyProxy provider;

    private List<PartyProxy> otherParticipants = new ArrayList<>();

    public PartyProxy getSubject() {
        return subject;
    }

    public void setSubject(PartyProxy subject) {
        this.subject = subject;
    }

    @Nullable
    public PartyProxy getProvider() {
        return provider;
    }

    public void setProvider(@Nullable PartyProxy provider) {
        this.provider = provider;
    }

    public List<PartyProxy> getOtherParticipants() {
        return otherParticipants;
    }

    public void setOtherParticipants(List<PartyProxy> otherParticipants) {
        this.otherParticipants = otherParticipants;
    }

    public void addOtherParticipant(PartyProxy participant) {
        otherParticipants.add(participant);
    }
}
