package com.nedap.archie.adlparser;

import com.nedap.archie.adlparser.antlr.AdlParser;
import com.nedap.archie.adlparser.antlr.AdlParser.*;
import com.nedap.archie.aom.*;
import com.nedap.archie.aom.terminology.ArchetypeTerm;
import com.nedap.archie.aom.terminology.ArchetypeTerminology;
import com.nedap.archie.aom.terminology.ValueSet;
import com.nedap.archie.base.MultiplicityInterval;
import org.antlr.v4.runtime.tree.TerminalNode;

import static com.nedap.archie.adlparser.PrimitivesConstraintParser.*;
import static com.nedap.archie.adlparser.NumberConstraintParser.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

/**
 * Created by pieter.bos on 15/10/15.
 */
public class ADLTreeWalker {


    public Archetype parse(AdlContext tree) {
        Archetype archetype = null;

        if (tree.archetype() != null) {
            archetype = parseArchetype(tree.archetype());
        }
        if(tree.template() != null) {

        }
        if(tree.template_overlay() != null) {

        }
        if(tree.operational_template() != null) {

        }
        return archetype;
    }

    private Archetype parseArchetype(ArchetypeContext archetypeContext) {
        Archetype archetype = new Archetype();
        if(archetypeContext.ARCHETYPE_HRID() != null) {
            archetype.setArchetypeId(new ArchetypeHRID(archetypeContext.ARCHETYPE_HRID().getText()));
        }
        if (archetypeContext.definition_section() != null) {
            archetype.setDefinition(parseComplexObject(archetypeContext.definition_section().c_complex_object()));
        }

        if(archetypeContext.rules_section() != null) {
            addRules(archetypeContext);

        }
        if(archetypeContext.terminology_section() != null) {
            addTerminology(archetype, archetypeContext.terminology_section());
        }
        return archetype;
    }

    private void addRules(ArchetypeContext archetypeContext) {
        //TODO: proper solution for this. Works for now.
        RuleStatement statement =new RuleStatement();
        statement.setRuleContent(archetypeContext.rules_section().getText());
    }

    private void addTerminology(Archetype archetype, Terminology_sectionContext terminologySectionContext) {
        Odin_textContext odinText = terminologySectionContext.odin_text();
        if(odinText != null) {
            ArchetypeTerminology terminology = new ArchetypeTerminology();
            archetype.setTerminology(terminology);
            Attr_valsContext values = odinText.attr_vals();
            if (values != null) {
                for (Attr_valContext value : values.attr_val()) {
                    switch (value.attribute_id().getText()) {
                        case "term_definitions":
                        case "term_definition":

                            terminology.setTermDefinitions(parseOdinMap(value.object_block(), ADLTreeWalker::parseOdinArchetypeTerm));
                            break;
                        case "term_bindings":
                        case "term_binding":
                            parseOdinMap(value.object_block(), ADLTreeWalker::parseOdinUri);

                            break;
                        case "terminology_extracts":
                        case "terminology_extract":
                            terminology.setTerminologyExtracts(parseOdinMap(value.object_block(), ADLTreeWalker::parseOdinArchetypeTerm));
                            break;
                        case "value_sets":
                        case "value_set":
                            terminology.setValueSets(parseOdinValueSets(value.object_block()));
                            break;
                        default:
                            //TODO: log some exception

                    }
                }
            }
            if (odinText.object_value_block() != null) {
                //i don't think this is allowed here?
            }
        }

    }

    private  Map<String, ValueSet> parseOdinValueSets(Object_blockContext context) {
        //TODO: a proper odin parser would be nice. Why all these custom tools, JSON would be amazing for this!
        Map<String, ValueSet> valueSets = new ConcurrentHashMap<>();
        Object_value_blockContext test = context.object_value_block();
        List<Keyed_objectContext> keyedContext = test.keyed_object();
        if(keyedContext == null) {
            //TODO: log some exception
        }
        for(Keyed_objectContext innerContext:keyedContext) {
            String valueSetId = OdinValueParser.parseOdinStringValue(innerContext.primitive_value().string_value());
            Object_value_blockContext valueSetContext = innerContext.object_block().object_value_block();
            List<Keyed_objectContext> valueSetProperties = valueSetContext.keyed_object();
            ValueSet valueSet = new ValueSet();
            for(Keyed_objectContext property:valueSetProperties) {
                switch(property.primitive_value().string_value().getText()) {
                    case "id":
                        valueSet.setId(property.object_block().object_value_block().primitive_object().primitive_value().string_value().getText());
                        break;
                    case "members":
                        Primitive_objectContext membersContext = property.object_block().object_value_block().primitive_object();
                        valueSet.setMembers(OdinValueParser.parseListOfStrings(membersContext));
                }

            }
            valueSets.put(valueSetId, valueSet);

        }
        return valueSets;
    }

    private <T> Map<String, Map<String, T>> parseOdinMap( Object_blockContext context, BiFunction<Map<String, T>, Keyed_objectContext, Void> parseInner) {
        //TODO: a proper odin parser would be nice. Why all these custom tools, JSON would be amazing for this!
        Map<String, Map<String, T>> terminology = new ConcurrentHashMap<>();
        Object_value_blockContext test = context.object_value_block();
        List<Keyed_objectContext> keyedContext = test.keyed_object();
        if(keyedContext == null) {
            //TODO: log some exception
        }
        for(Keyed_objectContext languageContext:keyedContext) {
            String language = OdinValueParser.parseOdinStringValue(languageContext.primitive_value().string_value());
            Map<String, T> translations = new ConcurrentHashMap<>();
            terminology.put(language, translations);

            Object_blockContext blockContext = languageContext.object_block();

            Object_value_blockContext termsContext = blockContext.object_value_block();
            for(Keyed_objectContext termCodeContext:termsContext.keyed_object()) {
                parseInner.apply(translations, termCodeContext);

            }
        }
        return terminology;
    }

    private static Void parseOdinArchetypeTerm(Map<String, ArchetypeTerm> map, Keyed_objectContext context) {
        String termCode = OdinValueParser.parseOdinStringValue(context.primitive_value().string_value());
        List<Attr_valContext> attr_valContexts = context.object_block().object_value_block().attr_vals().attr_val();
        ArchetypeTerm archetypeTerm = new ArchetypeTerm();
        archetypeTerm.setCode(termCode);
        map.put(termCode, archetypeTerm);
        for(Attr_valContext value:attr_valContexts) {
            String attribute = value.attribute_id().getText();
            String stringValue = OdinValueParser.parseOdinStringValue(value.object_block().object_value_block().primitive_object().primitive_value().string_value());
            archetypeTerm.put(attribute, stringValue);
        }
        return null;
    }

    private static Void parseOdinUri(Map<String, Object> map, Keyed_objectContext context) {
        String termCode = OdinValueParser.parseOdinStringValue(context.primitive_value().string_value());
        try {
            URI uri = OdinValueParser.parseOdinUri(context.object_block().object_value_block().primitive_object().primitive_value().uri_value());
            if(uri != null) {
                map.put(termCode, uri);
            }
        } catch (URISyntaxException e) {
            //todo: log exception
            e.printStackTrace();
        }

        return null;
    }

    private CComplexObject parseComplexObject(C_complex_objectContext context) {
        CComplexObject object = new CComplexObject();
        if(context.type_id() != null) {
            object.setRmTypeName(context.type_id().getText());
        }
        if(context.ID_CODE() != null) {
            object.setNodeId(context.ID_CODE().getText());
        } else if (context.ROOT_ID_CODE() != null) {
            object.setNodeId(context.ROOT_ID_CODE().getText());
        }
        //TODO: object.setDeprecated(context.) ?;
        if (context.c_occurrences() != null) {
            object.setOccurences(parseMultiplicityInterval(context.c_occurrences()));
        }
        for (C_attribute_defContext attribute : context.c_attribute_def()) {
            object.addAttribute(parseAttribute(object, attribute));
        }
        return object;
    }

    private CAttribute parseAttribute(CComplexObject parent, C_attribute_defContext attributeDefContext) {
        CAttribute attribute = null;
        if (attributeDefContext.c_attribute() != null) {
            attribute = new CAttribute();
            C_attributeContext attributeContext = attributeDefContext.c_attribute();
            attribute.setRmAttributeName(attributeContext.attribute_id().getText());
            if (attributeContext.c_existence() != null) {
                attribute.setExistence(parseMultiplicityInterval(attributeContext.c_existence()));
            }

            //TODO: attributeContext.adl_dir()
            if (attributeContext.c_cardinality() != null) {
                this.parseCardinalityInterval(attributeContext.c_cardinality());//TODO!
            }
            if (attributeContext.c_objects() != null) {
                attribute.setChildren(parseCObjects(attributeContext.c_objects()));
            }
        } else if (attributeDefContext.c_attribute_tuple() != null) {
            parseAttributeTuple(parent, attributeDefContext.c_attribute_tuple());
        }
        return attribute;

    }

    private CAttributeTuple parseAttributeTuple(CComplexObject parent, C_attribute_tupleContext attributeTupleContext) {
        List<Attribute_idContext> attributeIdList = attributeTupleContext.attribute_id();
        CAttributeTuple tuple = new CAttributeTuple();

        for(Attribute_idContext idContext:attributeIdList) {
            CAttribute attribute = new CAttribute();
            String id = idContext.getText();//TODO? parse odin string value?
            attribute.setRmAttributeName(id);
            tuple.addMember(attribute);
            parent.addAttribute(attribute);
        }
        List<C_object_tupleContext> tupleContexts = attributeTupleContext.c_object_tuple();
        for(C_object_tupleContext tupleContext:tupleContexts) {
            CPrimitiveTuple primitiveTuple = new CPrimitiveTuple();

            List<C_primitive_objectContext> primitiveObjectContexts = tupleContext.c_object_tuple_items().c_primitive_object();
            int i = 0;
            for(C_primitive_objectContext primitiveObjectContext:primitiveObjectContexts) {
                CPrimitiveObject primitiveObject = parsePrimitiveObject(primitiveObjectContext);
                tuple.getMembers().get(i).addChild(primitiveObject);
                primitiveTuple.addMember(primitiveObject);
                i++;
            }
            tuple.addTuple(primitiveTuple);
        }

        return tuple;
    }

    private List<CObject> parseCObjects(C_objectsContext objectsContext) {
        ArrayList<CObject> result = new ArrayList<>();

        if (objectsContext.c_primitive_object() != null) {
            result.add(parsePrimitiveObject(objectsContext.c_primitive_object()));
        } else if (objectsContext.c_non_primitive_object() != null) {
            //TODO: sibling order!
            List<C_non_primitive_objectContext> objects = objectsContext.c_non_primitive_object();
            for (C_non_primitive_objectContext object : objects) {
                result.add(parseNonPrimitiveObject(object));
            }
        }
        return result;
    }

    private CObject parseNonPrimitiveObject(C_non_primitive_objectContext objectContext) {
        /*
          c_complex_object
        | c_archetype_root
        | c_complex_object_proxy
        | archetype_slot
        */
        if (objectContext.c_complex_object() != null) {
            return parseComplexObject(objectContext.c_complex_object());
        } else if (objectContext.c_archetype_root() != null) {
            return parseArchetypeRoot(objectContext.c_archetype_root());

        } else if (objectContext.c_complex_object_proxy() != null) {
            return parseCComplexObjectProxy(objectContext.c_complex_object_proxy());
        } else if (objectContext.archetype_slot() != null) {
            return parseArchetypeSlot(objectContext.archetype_slot());

        }
        return null;
    }

    private CObject parseCComplexObjectProxy(C_complex_object_proxyContext c_complex_object_proxyContext) {
        return null;
    }

    private CObject parseArchetypeRoot(C_archetype_rootContext c_archetype_rootContext) {
        return null;
    }

    private ArchetypeSlot parseArchetypeSlot(Archetype_slotContext slotContext) {
        ArchetypeSlot slot = new ArchetypeSlot();
        C_archetype_slot_headContext headContext = slotContext.c_archetype_slot_head();
        slot.setNodeId(headContext.c_archetype_slot_id().ID_CODE().getText());
        slot.setRmTypeName(headContext.c_archetype_slot_id().type_id().getText());
        if(headContext.c_occurrences() != null) {
            slot.setOccurences(parseMultiplicityInterval(headContext.c_occurrences()));
        }
        if(slotContext.c_excludes() != null) {
            for(AssertionContext assertionContext:slotContext.c_excludes().assertion()) {
                slot.getExcludes().add(new Assertion(assertionContext.getText()));
            }
        }
        if(slotContext.c_includes() != null) {
            for(AssertionContext assertionContext:slotContext.c_includes().assertion()) {
                slot.getIncludes().add(new Assertion(assertionContext.getText()));
            }
        }
        return slot;
    }

    private CPrimitiveObject parsePrimitiveObject(C_primitive_objectContext objectContext) {
        /*c_integer
                | c_real
                | c_date
                | c_time
                | c_date_time
                | c_duration
                | c_string
                | c_terminology_code
                | c_boolean*/
        if(objectContext.c_integer() != null) {
            return parseCInteger(objectContext.c_integer());
        } else if (objectContext.c_real() != null) {
            return parseCReal(objectContext.c_real());
        } else if (objectContext.c_date() != null) {
            return parseCDate(objectContext.c_date());
        } else if (objectContext.c_time() != null) {
            return parseCTime(objectContext.c_time());
        } else if (objectContext.c_date_time() != null) {
            return parseCDateTime(objectContext.c_date_time());
        } else if (objectContext.c_duration() != null) {
            return parseCDuration(objectContext.c_duration());
        } else if (objectContext.c_string() != null) {
            return parseCString(objectContext.c_string());
        } else if (objectContext.c_terminology_code() != null) {
            return parseCTerminologyCode(objectContext.c_terminology_code());
        } else if (objectContext.c_boolean() != null) {
            return parseCBoolean(objectContext.c_boolean());
        }
        return null;
    }

    private Cardinality parseCardinalityInterval(C_cardinalityContext cardinalityContext) {
        Cardinality cardinality = new Cardinality();
        MultiplicityInterval interval = new MultiplicityInterval();
        cardinality.setInterval(interval);

        //TODO: cardinality().cardinatelyMod();
        List<TerminalNode> integers = cardinalityContext.cardinality().multiplicity().INTEGER();
        if(integers.size() == 1) {
            interval.setLower(Integer.parseInt(integers.get(0).getText()));
            interval.setUpper(interval.getLower());
        } else if (integers.size() == 2) {
            interval.setLower(Integer.parseInt(integers.get(0).getText()));
            interval.setUpper(Integer.parseInt(integers.get(1).getText()));
        }
        return cardinality;
    }

    private MultiplicityInterval parseMultiplicityInterval(C_existenceContext existenceContext) {
        MultiplicityInterval interval = new MultiplicityInterval();
        List<TerminalNode> integers = existenceContext.existence().INTEGER();
        if(integers.size() == 1) {
            interval.setLower(Integer.parseInt(integers.get(0).getText()));
            interval.setUpper(interval.getLower());
        } else if (integers.size() == 2) {
            interval.setLower(Integer.parseInt(integers.get(0).getText()));
            interval.setUpper(Integer.parseInt(integers.get(1).getText()));
        }
        return interval;
    }

    private MultiplicityInterval parseMultiplicityInterval(C_occurrencesContext occurrencesContext) {
        MultiplicityInterval interval = new MultiplicityInterval();
        List<TerminalNode> integers = occurrencesContext.multiplicity().INTEGER();

        if(occurrencesContext.multiplicity().SYM_INTERVAL_SEP() != null) {
            if(occurrencesContext.multiplicity().getText().contains("*")) {
                interval.setLower(Integer.parseInt(integers.get(0).getText()));
                interval.setUpperUnbounded(true);
            } else {
                interval.setLower(Integer.parseInt(integers.get(0).getText()));
                interval.setUpper(Integer.parseInt(integers.get(1).getText()));
            }
        } else {
            //one integer or *
            if(occurrencesContext.multiplicity().getText().contains("*")) {
                interval.setLowerUnbounded(false);
                interval.setLower(0);
                interval.setUpperUnbounded(true);
            } else {
                interval.setLower(Integer.parseInt(integers.get(0).getText()));
                interval.setUpper(interval.getLower());
            }
        }
        return interval;
    }
}
