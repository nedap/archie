package com.nedap.archie.adlchecker;

import com.nedap.archie.adlparser.ADLParser;
import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.CAttribute;
import com.nedap.archie.aom.CObject;
import com.nedap.archie.aom.terminology.ArchetypeTerm;
import com.nedap.archie.aom.utils.AOMUtils;
import com.nedap.archie.rminfo.MetaModels;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * If you create an archetype, generates a line of empty terminology for every missing term in every language.
 * Adds the comments as default text
 */
public class TerminologyContentGenerator {

    private MetaModels models;
    Pattern commentPattern = Pattern.compile(".*\\[(?<idcode>id[0-9]+)(,|\\]).*--(?<comment>.*)");

    public TerminologyContentGenerator(MetaModels models) {
        this.models = models;
    }

    public Archetype addTerms(String adlContent) {
        ADLParser parser = new ADLParser();
        try {
            Archetype archetype = parser.parse(adlContent);
            models.selectModel(archetype);

            //TODO: just run the CodeValidation in the context of an archetype repository, and process the error messages?
            if(parser.getErrors().hasErrors()) {
                throw new RuntimeException("parse errors!" + parser.getErrors().toString());
            }
            Archetype resultingArchetype = parser.parse(adlContent); //instantiate twice
            walkArchetype(adlContent, archetype, resultingArchetype);
            sortTerminology(resultingArchetype);
            return resultingArchetype;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sortTerminology(Archetype resultingArchetype) {
        Map<String, Map<String, ArchetypeTerm>> termDefinitions = resultingArchetype.getTerminology().getTermDefinitions();
        for(String language: termDefinitions.keySet()) {
            Map<String, ArchetypeTerm> stringArchetypeTermMap = termDefinitions.get(language);
            Map<String, ArchetypeTerm> sortedArchetypeTermMap = new TreeMap<>(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    String code1 = o1.substring(0, 2);
                    String code2 = o2.substring(0, 2);
                    if(code1.equalsIgnoreCase(code2)) {
                        int number1 = Integer.parseInt(o1.substring(2));
                        int number2 = Integer.parseInt(o2.substring(2));
                        return number1 - number2;
                    } else {
                        return code2.compareTo(code1);
                    }
                }
            });//TODO: comparator!
            sortedArchetypeTermMap.putAll(stringArchetypeTermMap);
            termDefinitions.put(language, sortedArchetypeTermMap);

        }
    }

    private void walkArchetype(String sourceFile, Archetype archetype, Archetype resultingArchetype) {
        Stack<CObject> workList = new Stack<>();
        workList.push(archetype.getDefinition());
        List<CObject> cObjects = new ArrayList<>();
        while(!workList.empty()) {
            CObject next = workList.pop();
            if(!terminologyHasCode(archetype, next)) {
                cObjects.add(next);
                addCodeToTerminology(sourceFile, resultingArchetype, next);
            }
            for(CAttribute attribute:next.getAttributes()) {
                for(CObject child:attribute.getChildren()) {
                    workList.push(child);
                }
            }
        }
    }

    private void addCodeToTerminology(String sourceFile, Archetype resultingArchetype, CObject next) {
        String text = getCommentName(sourceFile, next.getNodeId());
        if(text == null) {
            text = "X";
        }
        String description = text; //TODO: grep comment from sourcefile!
        Map<String, Map<String, ArchetypeTerm>> termDefinitions = resultingArchetype.getTerminology().getTermDefinitions();
        for(String language: termDefinitions.keySet()) {
            if(termDefinitions.get(language).get(next.getNodeId()) == null) {
                ArchetypeTerm newCode = new ArchetypeTerm();
                newCode.setCode(next.getNodeId());
                newCode.setText(text);
                newCode.setDescription(description);
                termDefinitions.get(language).put(next.getNodeId(), newCode);
            }
        }
    }

    private String getCommentName(String sourceFile, String nodeId) {
        String[] lines = sourceFile.split("\n");
        for(String line:lines) {
            Matcher matcher = commentPattern.matcher(line);
            if(matcher.matches()) {
                String idcode = matcher.group("idcode");
                String comment = matcher.group("comment");
                if(idcode.equalsIgnoreCase(nodeId)) {
                    return comment.trim();
                }
            }

        }
        return null;
    }

    public boolean terminologyHasCode(Archetype archetype, CObject cObject) {
        String nodeId = cObject.getNodeId();
        int codeSpecializationDepth = AOMUtils.getSpecializationDepthFromCode(nodeId);
        int archetypeSpecializationDepth = archetype.specializationDepth();
        if(codeSpecializationDepth > archetypeSpecializationDepth) {
            return true;//not exactly, this is a validation failure that needs to be fixed. log?
        } else if (cObject.isRoot() || parentIsMultiple(cObject)) {
            if( codeSpecializationDepth == archetypeSpecializationDepth &&  !archetype.getTerminology().hasIdCodeInAllLanguages(nodeId)) {
                return false;
            }
        }
        return true;
    }

    private boolean parentIsMultiple(CObject cObject) {
        if(cObject.getParent() != null) {

            CAttribute parent = cObject.getParent();
            CObject owningObject = parent.getParent();
            if (parent.getDifferentialPath() != null) {
                //not supported yet here.
                return false;
                /*&&
            } flatParent != null) {
                CAttribute attributeFromParent = (CAttribute) AOMUtils.getDifferentialPathFromParent(flatParent, parent);
                if(attributeFromParent != null) {
                    owningObject = attributeFromParent.getParent();
                }*/

            }
            if(owningObject != null) {
                return models.isMultiple(owningObject.getRmTypeName(), parent.getRmAttributeName());
            }
        }
        return false;
    }

}