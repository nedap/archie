package com.nedap.archie.aom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nedap.archie.ArchieLanguageConfiguration;
import com.nedap.archie.aom.terminology.ArchetypeTerm;
import com.nedap.archie.base.MultiplicityInterval;
import com.nedap.archie.paths.PathSegment;

import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Constraint on an object.
 *
 * Slightly deviates from the openEHR Archetype Model by including the getAttributes() and getAttribute() methods here
 * This enables one to type: archetype.getDefinition().getAttribute("context").getChild("id13").getAttribute("value")
 * without casting.
 *
 * Created by pieter.bos on 15/10/15.
 */
public class CObject extends ArchetypeConstraint {

    private String rmTypeName;
    private MultiplicityInterval occurrences;
    private String nodeId;
    private Boolean deprecated;

    private SiblingOrder siblingOrder;


    public String getRmTypeName() {
        return rmTypeName;
    }

    public void setRmTypeName(String rmTypeName) {
        this.rmTypeName = rmTypeName;
    }

    public MultiplicityInterval getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(MultiplicityInterval occurrences) {
        this.occurrences = occurrences;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public Boolean getDeprecated() {
        return deprecated;
    }

    public SiblingOrder getSiblingOrder() {
        return siblingOrder;
    }

    public void setSiblingOrder(SiblingOrder siblingOrder) {
        this.siblingOrder = siblingOrder;
    }

    public void setDeprecated(Boolean deprecated) {
        this.deprecated = deprecated;
    }

    @Override
    public List<PathSegment> getPathSegments() {
        CAttribute parent = getParent();
        if(parent == null) {
            return new ArrayList<>();
        }
        List<PathSegment> segments = parent.getPathSegments();
        if(!segments.isEmpty()) {
            segments.get(segments.size()-1).setNodeId(getNodeId());
        }
        return segments;
    }

    /**
     * Get the archetype term, in the defined meaning and description language
     */
    public ArchetypeTerm getTerm() {
        if(nodeId == null) {
            return null;
        }
        ArchetypeTerm result = getArchetype().getTerm(this, ArchieLanguageConfiguration.getMeaningAndDescriptionLanguage());
        if(result == null) {
            //no translation in the given language. Fall back to the default.
            result = getArchetype().getTerm(this, ArchieLanguageConfiguration.getDefaultMeaningAndDescriptionLanguage());
        }
        return result;
    }

    /**
     * Get the meaning of this CObject in the defined meaning and description language.
     * See ArchieLanguageConfiguation
     */
    @JsonIgnore
    @XmlTransient
    public String getMeaning() {
        ArchetypeTerm termDefinition = getTerm();
        if(termDefinition!=null && termDefinition.getText()!=null) {
            return termDefinition.getText();
        }
        return null;
    }

    /**
     * Get the meaning of this CObject in the defined meaning and description language.
     * See ArchieLanguageConfiguation
     */
    @JsonIgnore
    @XmlTransient
    public String getDescription() {
        ArchetypeTerm termDefinition = getTerm();
        if(termDefinition!=null && termDefinition.getDescription()!=null) {
            return termDefinition.getDescription();
        }
        return null;
    }

    private String getLogicalPathMeaning() {
        if(nodeId == null) {
            return null;
        }
        String meaning = null;
        ArchetypeTerm termDefinition = getArchetype().getTerm(this, ArchieLanguageConfiguration.getLogicalPathLanguage());
        if(termDefinition!=null && termDefinition.getText()!=null) {
            meaning = termDefinition.getText();
        }
        return meaning;
    }


    public String getLogicalPath() {
        //TODO: this can cause name clashes. Solve them!
        //TODO: the text can contain []-characters. Replace them?
        //TODO: lowercase and replace spaces with underscores?
        if(getParent() == null) {
            return "/";
        }

        String nodeName = getLogicalPathMeaning();
        if(nodeName == null) {
            nodeName = nodeId;
        }
        String path = getParent().getLogicalPath();
        //TODO: this is a bit slow because we have to walk the tree to the archetype every single time
        if(nodeName != null) {
            path += "[" + nodeName + "]";
        }
        if(path.startsWith("//")) {
            return path.substring(1);
        }
        return path;
    }

    public boolean isAllowed() {
        if(occurrences == null) {
            return true;
        }
        return occurrences.isUpperUnbounded() || occurrences.getUpper() > 0;
    }

    @Override
    public CAttribute getParent() {
        return (CAttribute) super.getParent();
    }

    public boolean isRequired() {
        if(occurrences == null) {
            return false;
        }
        return occurrences.getLower() > 0;
    }

    /**
     * Return the named attribute if this is a constrained complex object. Return null if there is no such named attribute,
     * or this is not a CComplexObject
     *
     * @param name
     * @return
     */
    public CAttribute getAttribute(String name) {
        return null;
    }

    /**
     * Get the underlying attributes of this CObject. From this class always returns an empty list. Overriden with
     * different implementations in subclasses.
     *
     * @return
     */
    public List<CAttribute> getAttributes() {
        return Collections.EMPTY_LIST;
    }

    /**
     * Return true if and only if this is a root node.
     * @return
     */
    public boolean isRootNode() {
        return false;
    }

    @Override
    public String toString() {
        return "CObject: " + getRmTypeName() + "[" + getNodeId() + "]";
    }
}
