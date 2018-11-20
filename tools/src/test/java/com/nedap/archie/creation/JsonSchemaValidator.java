package com.nedap.archie.creation;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.nedap.archie.adlparser.ADLParser;
import com.nedap.archie.antlr.errors.ANTLRParserErrors;
import com.nedap.archie.aom.Archetype;
import com.nedap.archie.rminfo.ArchieRMInfoLookup;
import com.nedap.archie.testutil.TestUtil;
import org.everit.json.schema.loader.SchemaClient;
import org.junit.Before;

import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Test;
import org.openehr.bmm.core.BmmClass;
import org.openehr.bmm.core.BmmModel;
import org.openehr.bmm.core.BmmPackage;
import org.openehr.bmm.persistence.validation.BmmDefinitions;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

public class JsonSchemaValidator {

    private final SchemaClient schemaClient = new SchemaClient() {

        @Override
        public InputStream get(String url) {
            if (url.startsWith("https://www.openEHR.org/releases/ITS-JSON/Release-1.0.0")) {
                return getClass().getResourceAsStream("/jsonschema/" + url.substring("https://www.openEHR.org/releases/ITS-JSON/Release-1.0.0".length()));
            } else {
                throw new RuntimeException("could not find schema " + url);
            }
        }
    };

    private final LoadingCache<String, Schema> schemaCache = CacheBuilder.newBuilder().build(new CacheLoader<String, Schema>() {
        @Override
        public Schema load(String type) throws Exception {
            BmmClass classDefinition = bmm.getClassDefinition(BmmDefinitions.typeNameToClassKey(type));
            String test = getPackagePath(classDefinition.getPackage());
            String packageName = test.substring(0, 1).toUpperCase() + test.substring(1);
            try(InputStream inputStream = getClass().getResourceAsStream("/jsonschema/RM/latest/" + packageName + "/" + type + ".json")) {
                JSONObject schemaJson = new JSONObject(new JSONTokener(inputStream));

                return SchemaLoader.load(schemaJson, schemaClient);

            }
        }
    });

    private String getPackagePath(BmmPackage bmmPackage) {
        BmmPackage currentPackage = bmmPackage;
        BmmPackage parentPackage = bmmPackage.getParent();

        while(currentPackage != null && parentPackage != null && !parentPackage.getName().equalsIgnoreCase("rm")) {
            currentPackage = parentPackage;
            parentPackage = parentPackage.getParent();
        }

        return currentPackage.getName();

    }

    private final BmmModel bmm;

    public JsonSchemaValidator(BmmModel bmm) {
        this.bmm = bmm;
    }

    public void validate(String type, String json) throws IOException {

        try {
            Schema schema = schemaCache.get(type);
            schema.validate(new JSONObject(json));
            System.out.println("validation ok!");
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

    }
}
