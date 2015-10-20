package com.nedap.archie.treewalker;

import com.nedap.archie.aom.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Tree walker for archetypes describing reference model objects.
 *
 * TODO: at least activity objects. Perhaps a separate listener for demographic objects?
 * TODO: split the item tree and item structures in separate calls?
 * Created by pieter.bos on 20/10/15.
 */
public interface RMArchetypeTreeListener {

    public static final Logger logger = LoggerFactory.getLogger(RMArchetypeTreeListener.class);

    void beginArchetype(Archetype archetype);

    void endArchetype(Archetype archetype);

    void count(CComplexObject parent, CComplexObject countConstraint);

    /**
     * Coded text with list of values not defined in this archetype
     */
    void codedText(CComplexObject parent, CComplexObject codedTextConstraint);

    //TODO: perhaps it helps if we add a coded text with a limited value set here for convenience

    void text(CComplexObject parent, CComplexObject text);

    void dateTime(CComplexObject parent, CComplexObject constraint);
    void date(CComplexObject parent, CComplexObject constraint);
    void time(CComplexObject parent, CComplexObject constraint);

    void bool(CComplexObject parent, CComplexObject booleanConstraint);

    void quantity(CComplexObject parent, CComplexObject elementConstraint);

    void interval(CComplexObject parent, CComplexObject elementConstraint);

    void proportion(CComplexObject parent, CComplexObject elementConstraint);

    void duration(CComplexObject parent, CComplexObject elementConstraint);

    void ordinal(CComplexObject parent, CComplexObject elementConstraint);

    void otherLeafnodeElement(CComplexObject parent, CObject elementConstraint);

    void anyElement(CComplexObject object);

    void beginComposition(CComplexObject object);
    void endComposition(CComplexObject object);

    void beginSection(CComplexObject object);
    void endSection(CComplexObject object);

    void beginHistory(CComplexObject object);
    void endHistory(CComplexObject object);

    /**
     * item structure can be: item tree, item list, item table or item single
     * @param object
     */
    void beginItemStructure(CComplexObject object);
    void endItemStructure(CComplexObject object);

    /** care entry or admin entry. Care entry can be observation, evaluation, instruction or action */
    void beginEntry(CComplexObject object);
    void endEntry(CComplexObject object);

    void beginCluster(CComplexObject object);
    void endCluster(CComplexObject object);

    /** point event or interval event*/
    void beginEvent(CComplexObject object);
    void endEvent(CComplexObject object);

    void beginOtherComplexObject(CComplexObject object);
    void endOtherComplexObject(CComplexObject object);

    void archetypeSlot(ArchetypeSlot archetypeSlot);

    void otherObjectConstraint(CObject object);

    static final String[] EVENT_TYPES_ARRAY = {"EVENT", "POINT_EVENT", "INTERVAL_EVENT"};
    static final Set<String> EVENT_TYPES = new HashSet(Arrays.asList(EVENT_TYPES_ARRAY));


    static final String[] ENTRY_TYPES_ARRAY = {"ENTRY", "OBSERVATION", "EVALUATION", "INSTRUCTION", "ACTION", "ADMIN_ENTRY"};
    static final Set<String> ENTRY_TYPES = new HashSet(Arrays.asList(ENTRY_TYPES_ARRAY));


    default void walk(Archetype archetype) {
        beginArchetype(archetype);
        walk(archetype.getDefinition());
        endArchetype(archetype);
    }

    default void walk(CComplexObject complexObject) {
        if (complexObject.getRmTypeName().equals("ELEMENT")) {
            CAttribute value = complexObject.getAttribute("value");
            if(value == null) {
                if(complexObject.isAnyAllowed()) {
                    anyElement(complexObject);
                } else {
                    throw new IllegalArgumentException("????? any allowed???? ");
                }

            } else {
                for (CObject elementConstraint : value.getChildren()) {
                    //TODO: check if the <> thing still happens in element names in ADL 2
                    Matcher matcher = Pattern.compile("([^<]*)(<(.*)>)?").matcher(elementConstraint.getRmTypeName());
                    if(!matcher.matches()) {
                        throw new IllegalStateException("programmer error.");
                    };//you know it's true
                    String parsedElementName = matcher.group(1);
                    String subElementName = matcher.group(3);

                    switch (parsedElementName) {
                        case "DV_QUANTITY":
                            quantity(complexObject, (CComplexObject) elementConstraint);
                            break;
                        case "DV_TEXT":
                            text(complexObject, (CComplexObject) elementConstraint);
                            break;
                        case "DV_CODED_TEXT":
                            CAttribute codes = ((CComplexObject) elementConstraint).getAttribute("defining_code");//TODO
                            codedText(complexObject, (CComplexObject) elementConstraint);
                            break;
                        case "DV_COUNT":
                            count(complexObject, (CComplexObject) elementConstraint);
                            break;
                        case "DV_INTERVAL":
                            interval(complexObject, (CComplexObject) elementConstraint);
                            break;
                        case "DV_ORDINAL":
                            ordinal(complexObject, (CComplexObject) elementConstraint);
                            break;
                        case "DV_DATE":
                            date(complexObject, (CComplexObject) elementConstraint);
                            break;
                        case "DV_DATE_TIME":
                            dateTime(complexObject, (CComplexObject) elementConstraint);
                            break;
                        case "DV_TIME":
                            time(complexObject, (CComplexObject) elementConstraint);
                            break;
                        case "DV_PROPORTION":
                            proportion(complexObject, (CComplexObject) elementConstraint);
                            break;
                        case "DV_BOOLEAN":
                            bool(complexObject, ((CComplexObject) elementConstraint));
                            break;
                        case "DV_DURATION":
                            duration(complexObject, ((CComplexObject) elementConstraint));
                            break;
                        default:
                            otherLeafnodeElement(complexObject, elementConstraint);
                            break;
                    }
                }
            }
        } else if (complexObject.getRmTypeName().equals("CLUSTER")) {
            beginCluster(complexObject);
            walkOverAllAttributes(complexObject);
            endCluster(complexObject);
        } else if (complexObject.getRmTypeName().equals("SECTION")) {
            beginSection(complexObject);
            walkOverAllAttributes(complexObject);
            endSection(complexObject);
        } else if (complexObject.getRmTypeName().equals("ITEM_TREE")) {
            beginItemStructure(complexObject);
            walkOverAllAttributes(complexObject);
            endItemStructure(complexObject);
        } else if (complexObject.getRmTypeName().equals("ITEM_LIST")) {
            beginItemStructure(complexObject);
            walkOverAllAttributes(complexObject);
            endItemStructure(complexObject);
        } else if (complexObject.getRmTypeName().equals("ITEM_SINGLE")) {
            beginItemStructure(complexObject);
            walkOverAllAttributes(complexObject);
            endItemStructure(complexObject);
        } else if (complexObject.getRmTypeName().equals("ITEM_TABLE")) {
            beginItemStructure(complexObject);
            walkOverAllAttributes(complexObject);
            endItemStructure(complexObject);
        } else if (complexObject.getRmTypeName().equals("HISTORY")) {
            beginHistory(complexObject);
            walkOverAllAttributes(complexObject);
            endHistory(complexObject);
        } else if (complexObject.getRmTypeName().equals("COMPOSITION")) {
            beginComposition(complexObject);
            //TODO: just walk ALL attributes
            walkOverAllAttributes(complexObject);
            //TODO: category and context
            endComposition(complexObject);
        } else if (ENTRY_TYPES.contains(complexObject.getRmTypeName())) {
            beginEntry(complexObject);
            walkOverAllAttributes(complexObject);
            endEntry(complexObject);
        } else if (EVENT_TYPES.contains(complexObject.getRmTypeName())) {
            beginEvent(complexObject);
            walkOverAllAttributes(complexObject);
            endEvent(complexObject);
        } else {
            //?? shouldn't happen?
            beginOtherComplexObject(complexObject);
            walkOverAllAttributes(complexObject);
            endOtherComplexObject(complexObject);
        }
    }

    default void walkOverAllAttributes(CComplexObject complexObject) {
        for(CAttribute attribute:complexObject.getAttributes()) {
            for(CObject object:attribute.getChildren()) {
                walk(object);//???
            }
        }
    }

    default void walk(CObject object) {
        if (object instanceof CComplexObject) {
            walk((CComplexObject) object);
        } else if (object instanceof ArchetypeSlot) {
            archetypeSlot((ArchetypeSlot) object);
        } else {
            otherObjectConstraint(object);
        }
    }
}


