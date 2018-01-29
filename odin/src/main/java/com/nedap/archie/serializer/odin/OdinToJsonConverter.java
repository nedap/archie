package com.nedap.archie.serializer.odin;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.nedap.archie.adlparser.antlr.AdlParser;
import com.nedap.archie.adlparser.antlr.AdlParser.*;

import java.util.List;

/**
 * A simple Odin to JSON-converter. This allows us to use standard tooling to bind to objects.
 *
 * Perhaps better would be to create a jackson-databinding for ODIN, but this is quite some work and I don't consider
 * ODIN as having considerable benefits over JSON or YAML.
 *
 * If anyone wants to do this - go ahead.
 *
 * Created by pieter.bos on 01/11/15.
 */
public class OdinToJsonConverter {

    private static ObjectMapper objectMapper = new ObjectMapper();
    private     StringBuilder output = new StringBuilder();

    static {
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        //keywords = <"value"> is indistinguishable from keywords = <"value1", "value2">
        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        objectMapper.enable(JsonParser.Feature.STRICT_DUPLICATE_DETECTION);
    }


    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public String convert(Odin_textContext context) {
        if(context == null) {
            return "{}";
        }
        if (context.attr_vals() != null) {
            output(context.attr_vals().attr_val(), null /* no type id here */);
        } else if(context.object_value_block() != null){
            output(context.object_value_block());
        } else if (context.keyed_object() != null && context.keyed_object().size() > 0) {
            outputKeyedObjects(context.keyed_object(), null /* no type id here */);
        } else {
            //empty
            return "{}";
        }
        return output.toString();

    }

    private void output(List<Attr_valContext> context, Type_idContext type_idContext) {
        output.append("{");
        boolean first = true;
        if(type_idContext != null) {
            first = false;
            outputTypeId(type_idContext);
        }
        for (Attr_valContext attrValContext : context) {
            if(!first) {
                output.append(',');
            }
            first = false;
            output.append('"');
            output.append(attrValContext.attribute_id().getText());
            output.append('"');
            output.append(':');
            output(attrValContext.object_block());
        }
        output.append("}");
    }

    private void outputTypeId(Type_idContext type_idContext) {
        outputEscaped("@type");
        output.append(":");
        outputEscaped(type_idContext.getText());//we might need to remove the generics from the type id if present
    }

    private void output(Object_blockContext context) {
        Object_value_blockContext valueBlockContext = context.object_value_block();
        if (context.object_reference_block() != null) {
            //WARN: not supported. not needed for adls?
        } else if (valueBlockContext != null) {
            output(valueBlockContext);
        } else {
            output.append("{}");
        }
    }

    private void output(Object_value_blockContext valueBlockContext) {
        List<Keyed_objectContext> keyedObjectContexts = valueBlockContext.keyed_object();
        Primitive_objectContext primitiveObjectContext = valueBlockContext.primitive_object();
        if (valueBlockContext.attr_vals() != null) {
            output(valueBlockContext.attr_vals().attr_val(), valueBlockContext.type_id());
        } else if (keyedObjectContexts != null && !keyedObjectContexts.isEmpty()) {
            outputKeyedObjects(keyedObjectContexts, valueBlockContext.type_id());
        } else if (primitiveObjectContext != null) {
            if(primitiveObjectContext.primitive_value() != null) {
                output(primitiveObjectContext.primitive_value());
            } else if (primitiveObjectContext.primitive_list_value() != null) {
                //json array
                Primitive_list_valueContext listContext = primitiveObjectContext.primitive_list_value();
                output(listContext);

            } else {
                //interval. TODO: implement interval-object notation in json :)
            }
        } else {
            output.append("{}");
        }
    }

    private void outputKeyedObjects(List<Keyed_objectContext> keyedObjectContexts, Type_idContext type_idContext) {
        output.append("{");
        boolean first = true;
        if(type_idContext != null) {
            first = false;
            outputTypeId(type_idContext);
        }
        for (Keyed_objectContext keyedObjectContext : keyedObjectContexts) {
            if(!first) {
                output.append(',');
            }
            first = false;
            //output.append('"');
            output(keyedObjectContext.primitive_value());
            //output.append('"');
            output.append(':');
            output(keyedObjectContext.object_block());

        }
        output.append("}");
    }

    private void output(Primitive_list_valueContext listContext) {
        List<Primitive_valueContext> primitiveValueContexts = listContext.primitive_value();
        output.append("[");
        boolean first = true;
        for(Primitive_valueContext valueContext:primitiveValueContexts) {
            if (!first) {
                output.append(',');
            }
            first = false;
            output(valueContext);
        }
        output.append("]");
    }

    private void output(Primitive_valueContext context) {
        if(context.uri_value() != null) {
            outputString(context.getText());
        } else if (context.date_time_value() != null) {
            outputString(context.getText());
        } else if (context.date_value()!= null) {
            outputString(context.getText());
        } else if (context.duration_value() != null) {
            outputString(context.getText());
        } else if (context.time_value() != null) {
            outputString(context.getText());
        } else if (context.term_code_value() != null) {
            outputString(context.getText());
        } else {
            //json-compatible anyway
            outputEscaped(context.getText());
        }

    }

    private void outputString(String text) {
        try {
            output.append(objectMapper.writeValueAsString(text));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void outputEscaped(String text) {
        try {
            //strip " if present, all the other "-characters will have to be escaped
            if(text.startsWith("\"") && text.endsWith("\"")) {
                String textWithoutQuotationMarks = text.substring(1, text.length()-1);
                output.append(objectMapper.writeValueAsString(textWithoutQuotationMarks));
            } else {
                output.append(text);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String getOutput() {
        return output.toString();
    }
}
