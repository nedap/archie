package com.nedap.archie.treewalker;

import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.ArchetypeSlot;
import com.nedap.archie.aom.CComplexObject;
import com.nedap.archie.aom.CObject;

/**
 * Default implementation, all listeners have a no-op implementation. You can override what you want yourself.
 *
 *
 * Created by pieter.bos on 20/10/15.
 */
public class BaseRMArchetypeTreeListener implements RMArchetypeTreeListener {
    @Override
    public void beginArchetype(Archetype archetype) {

    }

    @Override
    public void endArchetype(Archetype archetype) {

    }

    @Override
    public void count(CComplexObject parent, CComplexObject countConstraint) {

    }

    @Override
    public void codedText(CComplexObject parent, CComplexObject codedTextConstraint) {

    }

    @Override
    public void text(CComplexObject parent, CComplexObject text) {

    }

    @Override
    public void dateTime(CComplexObject parent, CComplexObject constraint) {

    }

    @Override
    public void date(CComplexObject parent, CComplexObject constraint) {

    }

    @Override
    public void time(CComplexObject parent, CComplexObject constraint) {

    }

    @Override
    public void bool(CComplexObject parent, CComplexObject booleanConstraint) {

    }

    @Override
    public void quantity(CComplexObject parent, CComplexObject elementConstraint) {

    }

    @Override
    public void interval(CComplexObject parent, CComplexObject elementConstraint) {

    }

    @Override
    public void proportion(CComplexObject parent, CComplexObject elementConstraint) {

    }

    @Override
    public void duration(CComplexObject parent, CComplexObject elementConstraint) {

    }

    @Override
    public void ordinal(CComplexObject parent, CComplexObject elementConstraint) {

    }

    @Override
    public void otherLeafnodeElement(CComplexObject parent, CObject elementConstraint) {

    }

    @Override
    public void anyElement(CComplexObject object) {

    }

    @Override
    public void beginComposition(CComplexObject object) {

    }

    @Override
    public void endComposition(CComplexObject object) {

    }

    @Override
    public void beginSection(CComplexObject object) {

    }

    @Override
    public void endSection(CComplexObject object) {

    }

    @Override
    public void beginHistory(CComplexObject object) {

    }

    @Override
    public void endHistory(CComplexObject object) {

    }

    @Override
    public void beginItemStructure(CComplexObject object) {

    }

    @Override
    public void endItemStructure(CComplexObject object) {

    }

    @Override
    public void beginEntry(CComplexObject object) {

    }

    @Override
    public void endEntry(CComplexObject object) {

    }

    @Override
    public void beginCluster(CComplexObject object) {

    }

    @Override
    public void endCluster(CComplexObject object) {

    }

    @Override
    public void beginEvent(CComplexObject object) {

    }

    @Override
    public void endEvent(CComplexObject object) {

    }

    @Override
    public void beginOtherComplexObject(CComplexObject object) {

    }

    @Override
    public void endOtherComplexObject(CComplexObject object) {

    }

    @Override
    public void archetypeSlot(ArchetypeSlot archetypeSlot) {

    }

    @Override
    public void otherObjectConstraint(CObject object) {

    }
}
