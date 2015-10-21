package com.nedap.archie.flattener;

import com.nedap.archie.aom.Archetype;

/**
 * Created by pieter.bos on 21/10/15.
 */
public class TemplateFlattener {

    private ArchetypeRepository repository;

    public TemplateFlattener(ArchetypeRepository repository) {
        this.repository = repository;
    }

    public Archetype flatten(Archetype template) {
        //validate that we can legally flatten first
        String parentId = template.getParentArchetypeId();
        if(parentId == null) {
            throw new IllegalArgumentException("Cannot flatten archetype without a parent");
        }

        Archetype parent = repository.getArchetype(template.getParentArchetypeId());
        if(parent == null) {
            throw new IllegalArgumentException("parent archetype not found in repository");
        }
//        while(parent.getParentArchetypeId() != null && parent.isDifferential()) {
//            //parent needs flattening first
//            parent = flatten(parent);//TODO: this might end up in an infinite loop
//        }

        return flatten(parent, template);
    }

    private Archetype flatten(Archetype parent, Archetype template) {
        return null;
    }


}
