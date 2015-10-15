package com.nedap.archie.adlparser;

import com.nedap.archie.adlparser.antlr.*;
import com.nedap.archie.aom.*;
import com.nedap.archie.base.MultiplicityInterval;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter.bos on 15/10/15.
 */
public class ADLTreeWalker {


    public Archetype parse(adlParser.AdlContext tree) {
        Archetype archetype = null;

        if (tree.archetype() != null) {
            archetype = parseArchetype(tree.archetype());
        }
        return archetype;
    }

    private Archetype parseArchetype(adlParser.ArchetypeContext archetypeContext) {
        Archetype archetype = new Archetype();
        archetype.setArchetypeId(new ArchetypeHRID(archetypeContext.ARCHETYPE_HRID().getText()));
        if (archetypeContext.definition_section() != null) {
            archetype.setDefinition(parseComplexObject(archetypeContext.definition_section().c_complex_object()));
        }
        return archetype;
    }

    private CComplexObject parseComplexObject(adlParser.C_complex_objectContext context) {
        CComplexObject object = new CComplexObject();
        if(context.type_id() != null) {
            object.setRmTypeName(context.type_id().getText());
        }
        if(context.ID_CODE() != null) {
            object.setNodeId(context.ID_CODE().getText());
        } else if (context.ROOT_ID_CODE() != null) {
            object.setNodeId(context.ROOT_ID_CODE().getText());
        }
        //TODO: object.setDeprecated(context.);
        if (context.c_occurrences() != null) {
            object.setOccurences(parseMultiplicityInterval(context.c_occurrences()));
        }
        for (adlParser.C_attribute_defContext attribute : context.c_attribute_def()) {
            object.addAttribute(parseAttribute(attribute));
        }
        return object;
    }

    private CAttribute parseAttribute(adlParser.C_attribute_defContext attributeDefContext) {
        CAttribute attribute = null;
        if (attributeDefContext.c_attribute() != null) {
            attribute = new CAttribute();
            adlParser.C_attributeContext attributeContext = attributeDefContext.c_attribute();
            attribute.setRmAttributeName(attributeContext.attribute_id().getText());
            if (attributeContext.c_existence() != null) {
                attribute.setExistence(parseMultiplicityInterval(attributeContext.c_existence()));
            }

            //TODO: attributeContext.adl_dir()
            if (attributeContext.c_cardinality() != null) {
                attributeContext.c_cardinality();//TODO!
            }
            if (attributeContext.c_objects() != null) {
                attribute.setChildren(parseCObjects(attributeContext.c_objects()));
            }
        } else if (attributeDefContext.c_attribute_tuple() != null) {
            adlParser.C_attribute_tupleContext tupleContext = attributeDefContext.c_attribute_tuple();
            //TODO: i have no clue

        }
        return attribute;

    }

    private List<CObject> parseCObjects(adlParser.C_objectsContext objectsContext) {
        ArrayList<CObject> result = new ArrayList<>();

        if (objectsContext.c_primitive_object() != null) {
            result.add(parsePrimitiveObject(objectsContext.c_primitive_object()));
        } else if (objectsContext.c_non_primitive_object() != null) {
            //TODO: sibling order!
            List<adlParser.C_non_primitive_objectContext> objects = objectsContext.c_non_primitive_object();
            for (adlParser.C_non_primitive_objectContext object : objects) {
                result.add(parseNonPrimitiveObject(object));
            }
        }
        return result;
    }

    private CObject parseNonPrimitiveObject(adlParser.C_non_primitive_objectContext objectContext) {
        /*
          c_complex_object
        | c_archetype_root
        | c_complex_object_proxy
        | archetype_slot
        */
        if (objectContext.c_complex_object() != null) {
            return parseComplexObject(objectContext.c_complex_object());
        } else if (objectContext.c_archetype_root() != null) {
            return null;//TODO
        } else if (objectContext.c_complex_object_proxy() != null) {
            return null;//TODO
        } else if (objectContext.archetype_slot() != null) {
            return null;//TODO
        }
        return null;
    }

    private CObject parsePrimitiveObject(adlParser.C_primitive_objectContext objectContext) {
        /*c_integer
                | c_real
                | c_date
                | c_time
                | c_date_time
                | c_duration
                | c_string
                | c_terminology_code
                | c_boolean*/
        return null;
    }

    private MultiplicityInterval parseMultiplicityInterval(adlParser.C_existenceContext existenceContext) {
        MultiplicityInterval interval = new MultiplicityInterval();
        //TODO: occurrencesContext.multiplicity().INTEGER()
        return interval;
    }

    private MultiplicityInterval parseMultiplicityInterval(adlParser.C_occurrencesContext occurrencesContext) {
        MultiplicityInterval interval = new MultiplicityInterval();
        //TODO: occurrencesContext.multiplicity().INTEGER()
        return interval;
    }
}
