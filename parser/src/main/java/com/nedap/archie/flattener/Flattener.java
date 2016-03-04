package com.nedap.archie.flattener;

import com.nedap.archie.aom.*;
import com.nedap.archie.aom.terminology.ArchetypeTerm;
import com.nedap.archie.aom.terminology.ArchetypeTerminology;
import com.nedap.archie.aom.terminology.ValueSet;
import com.nedap.archie.query.APathQuery;

import java.util.Map;
import java.util.Stack;

/**
 * Flattener. For single use only, create a new flattener for every flatten-action you want to do!
 *
 * TODO: the parent/child naming is very confusing. Make a new name. Original/specialized? Root/specialized? something else? Check the specs!

 *
 * Created by pieter.bos on 21/10/15.
 */
public class Flattener {

    //to be able to store Template Overlays transparently during flattening
    private OverridingArchetypeRepository repository;

    private Archetype parent;
    private Archetype child;

    private Archetype result;
    private boolean createOperationalTemplate;

    public Flattener(ArchetypeRepository repository) {
        this.repository = new OverridingArchetypeRepository(repository);
    }

    public Flattener createOperationalTemplate(boolean makeTemplate) {
        this.createOperationalTemplate = makeTemplate;
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
            }
        }

        while(parent.getParentArchetypeId() != null && parent.isDifferential()) {
            //parent needs flattening first
            parent = getNewFlattener().flatten(parent);//TODO: this might end up in an infinite loop. Detect depth?
        }


        this.result = null;
        if(createOperationalTemplate) {
            result = createOperationalTemplate(parent, child);
        } else {
            result = parent.clone();
        }

        //1. redefine structure
        //2. fill archetype slots if we are creating an operational template
        flatten(result, child);//TODO: this way around, or the other one? :)
        if(createOperationalTemplate) {
            fillComplexObjectProxies(result);
            fillArchetypeSlots(result);
        }
        flattenTerminology();

        result.getDefinition().setArchetype(result);
        return result;
    }

    private void fillArchetypeSlots(Archetype result) {

        Stack<CObject> workList = new Stack<>();
        workList.push(result.getDefinition());
        while(!workList.isEmpty()) {
            CObject object = workList.pop();
            for(CAttribute attribute:object.getAttributes()) {
                for(CObject child:attribute.getChildren()) {
                    if(child instanceof CArchetypeRoot) { //use_archetype
                        fillArchetypeRoot((CArchetypeRoot) child);
                    }
                    workList.push(child);
                }
            }
        }
    }

    private void fillComplexObjectProxies(Archetype result) {

        Stack<CObject> workList = new Stack<>();
        workList.push(result.getDefinition());
        while(!workList.isEmpty()) {
            CObject object = workList.pop();
            for(CAttribute attribute:object.getAttributes()) {
                for(CObject child:attribute.getChildren()) {
                    if(child instanceof CComplexObjectProxy) { //use_node
                        fillComplexObjectProxy((CComplexObjectProxy) child);
                    }
                    workList.push(child);
                }
            }
        }
    }

    private void fillComplexObjectProxy(CComplexObjectProxy proxy) {
        if(createOperationalTemplate) {
            CObject newObject = new APathQuery(proxy.getTargetPath()).find(proxy.getArchetype().getDefinition());
            if(newObject == null) {
                throw new RuntimeException("cannot find target in CComplexObjectProxy");
            } else {
                CComplexObject clone = (CComplexObject) newObject.clone();
                clone.setNodeId(proxy.getNodeId());
                proxy.getParent().replaceChild(proxy.getNodeId(), clone);
            }
        }
    }

    private void fillArchetypeRoot(CArchetypeRoot root) {
        if(createOperationalTemplate) {
            String archetypeRef = root.getArchetypeRef(); //TODO: is a ref always an id, or can it be a bit different?
            Archetype archetype = this.repository.getArchetype(archetypeRef);
            if (archetype == null) {
                throw new IllegalArgumentException("Archetype with reference :" + archetypeRef + " not found.");
            }
            if (archetype.getParentArchetypeId() != null) {
                archetype = getNewFlattener().flatten(archetype);
            } else {
                archetype = archetype.clone();//make sure we don't change this archetype :)
            }

            root.getParent().replaceChild(root.getNodeId(), archetype.getDefinition());
            String newNodeId = archetype.getArchetypeId().getFullId();

            ArchetypeTerminology terminology = archetype.getTerminology();

            //The node id will be replaced from "id1" to something like "openEHR-EHR-COMPOSITION.template_overlay.v1.0.0
            //so store it in the terminology as well
            //TODO: value sets, term bindings, etc.
            Map<String, Map<String, ArchetypeTerm>> termDefinitions = terminology.getTermDefinitions();
            for(String language: termDefinitions.keySet()) {
                Map<String, ArchetypeTerm> translations = termDefinitions.get(language);
                ArchetypeTerm term = translations.get(archetype.getDefinition().getNodeId());
                translations.put(newNodeId, term);
            }


            archetype.getDefinition().setNodeId(newNodeId);

            OperationalTemplate templateResult = (OperationalTemplate) result;

            //todo: should we filter this?
            if(archetype instanceof OperationalTemplate) {
                OperationalTemplate template = (OperationalTemplate) archetype;
                //add all the component terminologies, otherwise we lose translation
                for(String subarchetypeId:template.getComponentTerminologies().keySet()) {
                    templateResult.addComponentTerminology(subarchetypeId, template.getComponentTerminologies().get(subarchetypeId));
                }
            }

            templateResult.addComponentTerminology(archetype.getDefinition().getNodeId(), terminology);
            //todo: do we have to put something in the terminology extracts?
            //templateResult.addTerminologyExtract(child.getNodeId(), archetype.getTerminology().);
        }

    }

    private void flattenTerminology() {

        ArchetypeTerminology resultTerminology = result.getTerminology();
        ArchetypeTerminology childTerminology = child.getTerminology();

        flattenTerminologyDefinitions(resultTerminology.getTermDefinitions(), childTerminology.getTermDefinitions());
        flattenTerminologyDefinitions(resultTerminology.getTerminologyExtracts(), childTerminology.getTerminologyExtracts());
        flattenTerminologyDefinitions(resultTerminology.getTermBindings(), childTerminology.getTermBindings());
        resultTerminology.setDifferential(false);//TODO: correct?

        Map<String, ValueSet> childValueSets = childTerminology.getValueSets();
        Map<String, ValueSet> resultValueSets = resultTerminology.getValueSets();

        flattenValueSets(childValueSets, resultValueSets);
    }

    private void flattenValueSets(Map<String, ValueSet> childValueSets, Map<String, ValueSet> resultValueSets) {
        for(String key:childValueSets.keySet()) {
            ValueSet childValueSet = childValueSets.get(key);
            if(!resultValueSets.containsKey(key)) {
                resultValueSets.put(key, childValueSet);
            } else {
                ValueSet resultValueSet = resultValueSets.get(key);
                for(String member:childValueSet.getMembers()) {
                    resultValueSet.addMember(member);
                }
            }
        }
    }

    private <T> void flattenTerminologyDefinitions(Map<String, Map<String, T>> resultTermDefinitions, Map<String, Map<String, T>> childTermDefinitions) {
        for(String language:childTermDefinitions.keySet()) {
            if(!resultTermDefinitions.containsKey(language)) {
                resultTermDefinitions.put(language, childTermDefinitions.get(language));
            } else {
                for(String nodeId:childTermDefinitions.get(language).keySet()) {
                    resultTermDefinitions.get(language)
                            .put(nodeId, childTermDefinitions.get(language).get(nodeId));
                }
            }
        }
    }

    private static Archetype createOperationalTemplate(Archetype parent, Archetype child) {
        Archetype toClone = parent.clone(); //clone so we do not overwrite the parent archetype. never
        OperationalTemplate result = new OperationalTemplate();
        result.setArchetypeId(child.getArchetypeId());
        result.setDefinition(toClone.getDefinition());
        result.setDifferential(false);
        result.setParentArchetypeId(child.getParentArchetypeId());
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
//        if(child != null && child instanceof CArchetypeRoot) {
//            fillArchetypeRoot(parent, (CArchetypeRoot) child);
//        }
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

    private Flattener getNewFlattener() {
        return new Flattener(repository).createOperationalTemplate(createOperationalTemplate);
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
                    if (isOverridenCObject(childObject, possibleMatch)) { //TODO: we now replace the node. but it's possible to replace one node by multiple ones in the archetype
                        flattenCObject(possibleMatch, childObject);
                        overrideFound = true;
                    }
                }
                if(!overrideFound) {
                    parent.addChild(childObject);
                    //flattenCObject(childObject, null);
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
