package com.nedap.archie.flattener;

import com.nedap.archie.aom.*;
import com.nedap.archie.query.APathQuery;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.ArrayList;

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
    private boolean makeOperationalTemplate;

    public Flattener(ArchetypeRepository repository) {
        this.repository = new OverridingArchetypeRepository(repository);
    }

    public Flattener makeOperationalTemplate(boolean makeTemplate) {
        this.makeOperationalTemplate = makeTemplate;
        return this;
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
            parent = getNewFlattener().flatten(parent);//TODO: this might end up in an infinite loop. Detect depth?
        }


        this.result = null;
        if(makeOperationalTemplate) {
            result = createOperationalTemplate(parent, child);
        } else {
            result = parent.clone();
        }

        //TODO: multiple steps?:on
        //1. redefine structure
        //2. fill archetype slots?
        flatten(result, child);//TODO: this way around, or the other one? :)
        return result;
    }

    private static Archetype createOperationalTemplate(Archetype parent, Archetype child) {
        Archetype toClone = parent.clone(); //clone so we do not overwrite the parent archetype. never
        OperationalTemplate result = new OperationalTemplate();
        result.setArchetypeId(child.getArchetypeId());
        result.setDefinition(toClone.getDefinition());
        result.setDifferential(false);
        result.setParentArchetypeId(null);
        result.setRmRelease(toClone.getRmRelease());
        result.setAdlVersion(toClone.getAdlVersion());
        result.setTerminology(toClone.getTerminology());
        result.setGenerated(true);
        result.setOtherMetaData(toClone.getOtherMetaData());
        result.setRules(toClone.getRules());
        result.setBuildUid(toClone.getBuildUid());
        result.setDescription(toClone.getDescription());
        result.setOriginalLanguage(toClone.getOriginalLanguage());
        result.setTranslations(toClone.getTranslations());
        return result;
    }

    private void flatten(Archetype parent, Archetype child) {
        parent.setArchetypeId(child.getArchetypeId()); //TODO: override all metadata?
        flattenCObject(parent.getDefinition(), child.getDefinition());

    }

    private void flattenCObject(CObject parent, CObject child) {
        parent.setOccurences(getPossiblyOverridenValue(parent.getOccurences(), child.getOccurences()));
        parent.setSiblingOrder(getPossiblyOverridenValue(parent.getSiblingOrder(), child.getSiblingOrder()));

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

    private Flattener getNewFlattener() {
        return new Flattener(repository).makeOperationalTemplate(makeOperationalTemplate);
    }

    private void fillArchetypeRoot(CObject parent, CArchetypeRoot child) {
        if(makeOperationalTemplate) {
            String archetypeRef = child.getArchetypeRef(); //TODO: is a ref always an id, or can it be a bit different?
            Archetype archetype = this.repository.getArchetype(archetypeRef);
            if (archetype == null) {
                throw new IllegalArgumentException("Archetype with reference :" + archetypeRef + " not found.");
            }
            if (archetype.getParentArchetypeId() != null) {
                archetype = getNewFlattener().flatten(archetype);
            } else {
                archetype = archetype.clone();//make sure we don't change this archetype :)
            }

            parent.getParent().replaceChild(child.getNodeId(), archetype.getDefinition());
            archetype.getDefinition().setNodeId(archetype.getArchetypeId().getFullId());
            archetype.getDefinition().setArchetype(parent.getArchetype());//update the pointer to the archetype parent.
            OperationalTemplate templateResult = (OperationalTemplate) result;

            //todo: should we filter this?
            if(archetype instanceof OperationalTemplate) {
                OperationalTemplate template = (OperationalTemplate) archetype;
                //add all the component terminologies, otherwise we lose translation
                for(String subarchetypeId:template.getComponentTerminologies().keySet()) {
                    templateResult.addComponentTerminology(subarchetypeId, template.getComponentTerminologies().get(subarchetypeId));
                }
            }
            templateResult.addComponentTerminology(archetype.getDefinition().getNodeId(), archetype.getTerminology());
            //todo: do we have to put something in the terminology extracts?
            //templateResult.addTerminologyExtract(child.getNodeId(), archetype.getTerminology().);
        }

    }

    private void flattenAttribute(CComplexObject root, CAttribute parent, CAttribute child) {
        if(parent == null) {
            CAttribute childCloned = child.clone();
            root.addAttribute(childCloned);
            //this is a new attribute, but we still have to process it, for example to expand archetype roots. So set
            //the parent to be the new child.
            parent = childCloned;
        }
        parent.setMultiple(getPossiblyOverridenValue(parent.isMultiple(), child.isMultiple()));
        parent.setExistence(getPossiblyOverridenValue(parent.getExistence(), child.getExistence()));
        parent.setCardinality(getPossiblyOverridenValue(parent.getCardinality(), child.getCardinality()));
        if(child.getChildren().size() > 0 && child.getChildren().get(0) instanceof CPrimitiveObject) {
            //TODO: is this correct? replace all child nodes
            parent.setChildren(child.getChildren());
        } else {
            for (CObject childObject : child.getChildren()) {
                boolean overrideFound = false;

                for (CObject possibleMatch : parent.getChildren()) {
                    if (isOverridenCObject(childObject, possibleMatch)) {
                        flattenCObject(possibleMatch, childObject);
                        overrideFound = true;
                    }
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
