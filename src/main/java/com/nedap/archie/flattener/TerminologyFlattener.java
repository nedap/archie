package com.nedap.archie.flattener;

import com.google.common.collect.Sets;
import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.OperationalTemplate;
import com.nedap.archie.aom.terminology.ArchetypeTerminology;
import com.nedap.archie.aom.terminology.ValueSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TerminologyFlattener {

    protected static void flattenTerminology(Archetype result, Archetype child) {

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

    private static void flattenValueSets(Map<String, ValueSet> childValueSets, Map<String, ValueSet> resultValueSets) {
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

    private static <T> void flattenTerminologyDefinitions(Map<String, Map<String, T>> resultTermDefinitions, Map<String, Map<String, T>> childTermDefinitions) {
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


    /* OPT CREATION */

    /**
     * Remove all languages from an archetype terminology unless listed in languages to keep.
     * @param result
     */
    protected static void filterLanguages(OperationalTemplate result, boolean removeLanguagesFromMetaData, String[] languagesToKeep) {
        if(languagesToKeep != null) {

            Set<String> languagesSet = Sets.newHashSet(languagesToKeep);

            filterLanguages(languagesSet, result.getTerminology());
            for(ArchetypeTerminology terminology: result.getComponentTerminologies().values()) {
                filterLanguages(languagesSet, terminology);
            }
            for(ArchetypeTerminology terminology: result.getTerminologyExtracts().values()) {
                filterLanguages(languagesSet, terminology);
            }

            if(removeLanguagesFromMetaData) {
                if(result.getDescription() != null) {
                    filterLanguages(languagesSet, result.getDescription().getDetails());
                }
                if(result.getTranslations() != null) {
                    filterLanguages(languagesSet, result.getTranslations());
                }
            }
        }
    }

    private static void filterLanguages(Set<String> languagesSet, ArchetypeTerminology terminology) {

        filterLanguages(languagesSet, terminology.getTermDefinitions());
        filterLanguages(languagesSet, terminology.getTerminologyExtracts());
    }

    private static void filterLanguages(Set<String> languagesSet, Map<String, ?> termDefinitions) {
        if(termDefinitions == null) {
            return;
        }
        List<String> toRemove = new ArrayList<>();
        for(String key: termDefinitions.keySet()) {
            if(!languagesSet.contains(key)) {
                toRemove.add(key);
            }
        }
        for(String key:toRemove) {
            termDefinitions.remove(key);
        }
    }

}
