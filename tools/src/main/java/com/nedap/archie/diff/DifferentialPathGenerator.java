package com.nedap.archie.diff;

import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.CAttribute;
import com.nedap.archie.aom.CComplexObject;
import com.nedap.archie.aom.CObject;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Replaces a tree of specialized CObjects and CAttributes with the differential paths where possible
 **/
public class DifferentialPathGenerator {

    private Archetype diffed;

    public void replace(Archetype diffed) {
        this.diffed = diffed;
        for(CAttribute attribute:diffed.getDefinition().getAttributes()) {
            replace( attribute, null);
        }
    }

    private void replace(CAttribute attribute, @Nullable CompressablePath compressablePath) {
        if(canAddToCompressablePath(attribute)) {
            if(compressablePath == null) {
                compressablePath = new CompressablePath(attribute);
                CObject cObject = attribute.getChildren().get(0); //can only be one exactly. This prevents ConcurrentModificationExceptions
                if(canAddToCompressablePath(cObject)) {
                    //no need to add the cObject, it's a single one anyway
                    replaceChildAttribute(cObject, compressablePath);

                } else {
                    replaceChildAttributes(cObject);
                }

            } else {
                compressablePath.addAttribute(attribute);
                //this could be refactored into smaller code by combining the case above, but if single attribute paths are to be supported
                //it will get messy
                CObject cObject = attribute.getChildren().get(0); //can only be one exactly. This prevents ConcurrentModificationExceptions
                if(canAddToCompressablePath(cObject)) {
                    //no need to add the cObject, it's a single one anyway
                    replaceChildAttribute(cObject, compressablePath);
                } else {
                    //end the compressable path here
                    compressablePath.replaceWithSingleAttribute();
                    replaceChildAttributes(cObject);
                }
            }
        } else {
            if(compressablePath != null) {
                compressablePath.addAttribute(attribute); //even though this cannot be further compressed, the attribute can be added just fine
                //this is the end of the compressable path. This attribute must be added as the last bit.
                compressablePath.replaceWithSingleAttribute();
            }
            for(CObject cObject: attribute.getChildren()){
                replaceChildAttributes(cObject);
            }
        }
    }

    private void replaceChildAttribute(CObject cObject, CompressablePath compressablePath) {
        CAttribute childAttribute = cObject.getAttributes().get(0); //can only be one exactly. This prevents ConcurrentModificationExceptions
        replace( childAttribute, compressablePath);
    }

    private void replaceChildAttributes(CObject cObject) {
        for (CAttribute childAttribute : cObject.getAttributes()) {
            replace( childAttribute, null);
        }
    }

    private boolean canAddToCompressablePath(CAttribute attribute) {
        return attribute.getCardinality() == null && attribute.getExistence() == null && attribute.getChildren().size() == 1;
    }

    private boolean canAddToCompressablePath(CObject cObject) {
        if(cObject instanceof CComplexObject) {
            return cObject.specialisationDepth() < diffed.specializationDepth() &&
                    cObject.getAttributes().size() == 1 &&
                    cObject.getOccurrences() == null &&
                    cObject.getSiblingOrder() == null &&
                    ((CComplexObject) cObject).getAttributeTuples().isEmpty();
        } else {
            return false;
        }
    }

    class CompressablePath {
        //storing just the start and end would be enough and save minor memory at no cpu cost, but not worth the optimization now
        private List<CAttribute> attributes = new ArrayList<>();

        public CompressablePath(CAttribute rootAtttribute) {
            attributes.add(rootAtttribute);
        }

        public void addAttribute(CAttribute attribute) {
            this.attributes.add(attribute);
        }

        public CAttribute getRootAttribute() {
            return attributes.get(0);
        }

        public CAttribute getLastAttribute() {
            return attributes.get(attributes.size()-1);
        }

        public List<CAttribute> getAttributes() {
            return attributes;
        }

        public void replaceWithSingleAttribute() {
            CAttribute root = getRootAttribute();
            CAttribute last = getLastAttribute();
            root.setChildren(last.getChildren());
            root.setDifferentialPath(getPath());
            root.setRmAttributeName(last.getRmAttributeName());
        }

        public String getPath() {
            CAttribute last = getLastAttribute();
            CAttribute root = getRootAttribute();
            CObject cObject = null;
            CAttribute currentAttribute = last;
            StringBuilder pathBuilder = new StringBuilder();
            while(currentAttribute != root) { //object != is really what we want here, do not replace with equals
                addToPath(currentAttribute, cObject, pathBuilder);
                cObject = currentAttribute.getParent();
                currentAttribute = cObject.getParent();
            }
            addToPath(root, cObject, pathBuilder);
            return pathBuilder.toString();
        }

        private void addToPath(CAttribute currentAttribute, CObject cObject, StringBuilder pathBuilder) {
            if(cObject != null) {
                //prepend, so this insertion order is backwards
                pathBuilder.insert(0, "]");
                pathBuilder.insert(0, cObject.getNodeId());
                pathBuilder.insert(0, "[");
            }
            pathBuilder.insert(0, currentAttribute.getRmAttributeName());
            pathBuilder.insert(0, "/");
        }


    }

}
