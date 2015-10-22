package com.nedap.archie.flattener;

import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.ArchetypeModelObject;
import com.nedap.archie.aom.CArchetypeRoot;
import com.nedap.archie.aom.CAttribute;
import com.nedap.archie.aom.CComplexObject;
import com.nedap.archie.aom.CObject;
import com.nedap.archie.aom.Template;
import com.nedap.archie.aom.TemplateOverlay;
import com.nedap.archie.query.APathQuery;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Flattener. For single use only, create a new flattener for every flatten-action you want to do!
 *
 * TODO: the parent/child naming is very confusing. Make a new name. Original/specialized? Root/specialized? something else? Check the specs!
 * TODO: new Node IDs should be assigned, i think. With a idDIGIT+(\.DIGIT+)+ syntax?
 * TODO: Archetype terminologies should be merged
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
        if(parent != null) {
            throw new IllegalStateException("You've used this flattener before - single use instance, please create a new one!");
        }
        //validate that we can legally flatten first
        String parentId = toFlatten.getParentArchetypeId();
        if(parentId == null) {
            throw new IllegalArgumentException("Cannot flatten archetype without a parent");
        }

        this.parent = repository.getArchetype(toFlatten.getParentArchetypeId());
        if(parent == null) {
            throw new IllegalArgumentException("parent archetype not found in repository: " + toFlatten.getParentArchetypeId());
        }
        this.child = toFlatten.clone();//just to be sure, so we don't have to copy more things deeper down

        if(child instanceof Template) {
            Template childTemplate = (Template) child;
            for(TemplateOverlay overlay:childTemplate.getTemplateOverlays()) {
                //we'll flatten them later when we need them, otherwise, you run into problems with archetypes
                //not yet added to repository while we already need them
                repository.addArchetype(overlay);

               /* try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

                    System.out.println(objectMapper.writeValueAsString(flattened));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }*/
            }
        }

        while(parent.getParentArchetypeId() != null && parent.isDifferential()) {
            //parent needs flattening first
            parent = new Flattener(repository).flatten(parent);//TODO: this might end up in an infinite loop. Detect depth?
        }


        this.result = parent.clone();
        flatten(result, child);//TODO: this way around, or the other one? :)
        return result;
    }

    private void flatten(Archetype parent, Archetype child) {
        parent.setArchetypeId(child.getArchetypeId()); //TODO: override all metadata?
        flattenCObject(parent.getDefinition(), child.getDefinition());

    }

    private void flattenCObject(CObject parent, CObject child) {
        parent.setNodeId(getPossiblyOverridenValue(parent.getNodeId(), child.getNodeId()));
        parent.setRmTypeName(getPossiblyOverridenValue(parent.getRmTypeName(), child.getRmTypeName()));
        if(child != null && child instanceof CArchetypeRoot) {
            fillArchetypeRoot(parent, (CArchetypeRoot) child);
        }
        else if(parent instanceof CComplexObject) {
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

    private void fillArchetypeRoot(CObject parent, CArchetypeRoot child) {
        String archetypeRef = child.getArchetypeRef(); //TODO: is a ref always an id, or can it be a bit different?
        Archetype archetype = this.repository.getArchetype(archetypeRef);
        if(archetype == null) {
            throw new IllegalArgumentException("Archetype with reference :" + archetypeRef + " not found.");
        }
        if(archetype.getParentArchetypeId() != null) {
            archetype = new Flattener(repository).flatten(archetype);
        }
        archetype = archetype.clone();//make sure we don't change this archetype :)
        parent.getParent().replaceChild(child.getNodeId(), archetype.getDefinition());//TODO: check this!
        //TODO: set id?
    }

    private void flattenAttribute(CComplexObject root, CAttribute parent, CAttribute child) {
        if(parent == null) {
            CAttribute childCloned = child.clone();
            root.addAttribute(childCloned);
            //this is a new attribute, but we still have to process it, for example to expand archetype roots. So set
            //the parent to be the new child.
            parent = childCloned;
        }
        for (CObject childObject : child.getChildren()) {
            boolean overrideFound = false;
            for (CObject possibleMatch : parent.getChildren()) {
                //TODO: this is wrong when matching CPrimitiveObjects, since they don't have a unique node id.
                //if these are primitive objects, replace ALL primitive objects with the new set?
                if (isOverridenCObject(childObject, possibleMatch)) {
                    //TODO: this works with complexObjects. but not with CObjects because we do not set extra constraints
                    flattenCObject(possibleMatch, childObject);
                    overrideFound = true;
                }
            }

        }

    }

    private boolean isOverridenCObject(CObject childObject, CObject possibleMatch) {
        String childNode = childObject.getNodeId();
        if(childObject.getNodeId().lastIndexOf('.') > 0) {
            childNode = childObject.getNodeId().substring(0, childObject.getNodeId().lastIndexOf('.'));//-1?
        }
        return childNode.startsWith(possibleMatch.getNodeId());
    }

    public <T> T getPossiblyOverridenValue(T parent, T child) {
        if(child != null) {
            return child;
        }
        return parent;
    }


}
