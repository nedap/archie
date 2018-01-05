package com.nedap.archie.archetypevalidator.validations;


import com.nedap.archie.aom.AuthoredArchetype;
import com.nedap.archie.aom.ResourceAnnotations;
import com.nedap.archie.aom.utils.AOMUtils;
import com.nedap.archie.archetypevalidator.ArchetypeValidationBase;
import com.nedap.archie.archetypevalidator.ErrorType;

import java.util.Map;

public class AnnotationsValidation extends ArchetypeValidationBase {

    public AnnotationsValidation() {
        super();
    }

    @Override
    public void validate() {
        if(archetype instanceof AuthoredArchetype) {
            if(archetype.getAnnotations() != null) {
                ResourceAnnotations annotations = archetype.getAnnotations();
                for(String language: annotations.getDocumentation().keySet()) {
                    Map<String, Map<String, String>> annotationsForLanguage = annotations.getDocumentation().get(language);
                    for(String path: annotationsForLanguage.keySet()) {
                        Map<String, String> annotationForPath = annotationsForLanguage.get(path);
                        boolean isArchetypePath = AOMUtils.isArchetypePath(path) ; //TODO: NO idea what the eiffel code here suggests it does
                        if(isArchetypePath) {
                            if(!archetype.hasPath(path) || (flatParent != null && !flatParent.hasPath(path))) {
                                addMessage(ErrorType.VRANP, String.format("annotation with path %s does not exist in flat archetype", path));
                            }
                        } else { //TODO: this can also be referencemodel.has_path, but that's not implemented yet
                            if(!AOMUtils.hasReferenceModelPath(lookup, archetype.getDefinition().getRmTypeName(), path)) {
                                addMessage(ErrorType.VRANP, String.format("annotation with path %s does not exist in flat archetype", path));
                            }
                        }

                    }

                }
            }
        }


    }
}
