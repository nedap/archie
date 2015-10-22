package com.nedap.archie.flattener;

import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.ArchetypeModelObject;
import com.nedap.archie.aom.CAttribute;
import com.nedap.archie.aom.CComplexObject;
import com.nedap.archie.aom.CObject;
import com.nedap.archie.aom.Template;
import com.nedap.archie.aom.TemplateOverlay;
import com.nedap.archie.query.APathQuery;

/**
 * Flattener. For single use only, create a new flattener for every flatten-action you want to do!
 *
 * Created by pieter.bos on 21/10/15.
 */
public class Flattener {

    //to be able to store Template Overlays transparently during flattening
    private OverridingArchetypeRepository repository;

    private Archetype parent;
    private Archetype child;

    private Archetype result;

    public Flattener(ArchetypeRepository repository) {
        this.repository = new OverridingArchetypeRepository(repository);
    }

    public Archetype flatten(Archetype toFlatten) {
        //validate that we can legally flatten first
        String parentId = toFlatten.getParentArchetypeId();
        if(parentId == null) {
            throw new IllegalArgumentException("Cannot flatten archetype without a parent");
        }

        Archetype parent = repository.getArchetype(toFlatten.getParentArchetypeId());
        if(parent == null) {
            throw new IllegalArgumentException("parent archetype not found in repository");
        }
//        while(parent.getParentArchetypeId() != null && parent.isDifferential()) {
//            //parent needs flattening first
//            parent = flatten(parent);//TODO: this might end up in an infinite loop
//        }
        this.parent = parent;
        this.child = toFlatten.clone();//just to be sure, so we don't have to copy more things deeper down

        if(child instanceof Template) {
            Template childTemplate = (Template) child;
            for(TemplateOverlay overlay:childTemplate.getTemplateOverlays()) {
                repository.addArchetype(new Flattener(repository).flatten(overlay));
            }
        }


        this.result = parent.clone();
        flatten(result, child);
        return result;
    }

    private void flatten(Archetype parent, Archetype child) {
        flattenCObject(parent.getDefinition(), child.getDefinition());

    }

    private void flattenCObject(CObject parent, CObject child) {
        parent.setNodeId(getPossiblyOverridenValue(parent.getNodeId(), child.getNodeId()));
        parent.setRmTypeName(getPossiblyOverridenValue(parent.getRmTypeName(), child.getRmTypeName()));
        if(parent instanceof CComplexObject) {
            flattenCComplexObject((CComplexObject) parent, (CComplexObject) child);
        }


    }

    private void flattenCComplexObject(CComplexObject parent, CComplexObject child) {
        for(CAttribute attribute:child.getAttributes()) {
            if(attribute.getDifferentialPath() != null) {
                //this overrides a specific path
                ArchetypeModelObject object = new APathQuery(attribute.getDifferentialPath()).find(this.parent.getDefinition());
                if(object instanceof CAttribute) {
                    CAttribute realAttribute = (CAttribute) object;
                    flattenAttribute(parent, realAttribute, attribute);
                } else if (object instanceof CObject) {
                    //TODO: what does this mean?
                }

            } else {
                //this overrides the same path
                flattenAttribute(parent, parent.getAttribute(attribute.getRmAttributeName()), attribute);
            }
        }
    }

    private void flattenAttribute(CComplexObject root, CAttribute parent, CAttribute child) {
        if(parent == null) {
            root.addAttribute(child); //TODO: this is a new attribute. should this continue with subobjects or are we done?
        } else {
            for (CObject childObject : child.getChildren()) {
                boolean overrideFound = false;
                for (CObject possibleMatch : parent.getChildren()) {
                    if (isOverridenCObject(childObject, possibleMatch)) {
                        //TODO: this works with complexObjects. but not with CObjects because we do not set extra constraints
                        flattenCObject(possibleMatch, childObject);
                        overrideFound = true;
                    }
                }

            }
        }

    }

    private boolean isOverridenCObject(CObject childObject, CObject possibleMatch) {
        String childNode = childObject.getNodeId().substring(0, childObject.getNodeId().lastIndexOf('.'));//-1?
        return childNode.startsWith(possibleMatch.getNodeId());
    }

    public <T> T getPossiblyOverridenValue(T parent, T child) {
        if(child != null) {
            return child;
        }
        return parent;
    }


}
