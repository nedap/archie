package org.openehr.referencemodels;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
import com.nedap.archie.rminfo.ArchieRMInfoLookup;
import org.junit.Test;
import org.openehr.bmm.core.BmmClass;
import org.openehr.bmm.core.BmmModel;
import org.openehr.bmm.v2.validation.BmmRepository;

import java.text.MessageFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RMComparedWithBmmTest {

    @Test
    public void compareBmmWithRM() {

        Map<String, String> typeMap =  new HashMap<>();
        typeMap.put("Any", "Object");
        typeMap.put("Integer64", "Long");
        typeMap.put("Integer", "Long");
        typeMap.put("REAL", "Double");
        typeMap.put("HASH", "Map");
        typeMap.put("Character", "char");
        typeMap.put("Octet", "Byte"); //TODO: fix this properly
        typeMap.put("PROPORTION_KIND", "Long");//TODO: replace with enum!

        Set<String> extraParams = Sets.newHashSet("parent", "path");

        Map<String, String> typeNamesOverride = new HashMap<>();
        typeNamesOverride.put("DV_URI.value", "URI");
        typeNamesOverride.put("DV_EHR_URI.value", "URI");
        typeNamesOverride.put("DV_DATE.value", "temporal");
        typeNamesOverride.put("DV_TIME.value", "temporal_accessor");
        typeNamesOverride.put("DV_DATE_TIME.value", "temporal_accessor");
        typeNamesOverride.put("DV_DURATION.value", "temporal_amount");
        typeNamesOverride.put("Interval.lower", "object");
        typeNamesOverride.put("Interval.upper", "object");

        BmmRepository bmmRepository = BuiltinReferenceModels.getBmmRepository();
        BmmModel model = bmmRepository.getModel("openehr_rm_1.0.4").getModel();


        List<ModelDifference> compared = new BmmComparison(extraParams, typeMap, typeNamesOverride).compare(model, ArchieRMInfoLookup.getInstance());

        compared.sort(Comparator.comparing((a) -> a.getClassName() + "." + a.getType().toString()));
        compared = compared.stream().filter((diff) -> {
                BmmClass classDefinition = model.getClassDefinition(diff.getClassName());
                return classDefinition == null ? true : !classDefinition.getSourceSchemaId().equalsIgnoreCase("openehr_rm_ehr_extract_1.0.4");
            }).collect(Collectors.toList());

        Set<ModelDifference> knownDifferences = new HashSet();

        //Needs a backwards incompatible fix, not changing now (and not important, new API is included)
        knownDifferences.add(new ModelDifference(ModelDifferenceType.ANCESTOR_DIFFERENCE, "", "ARCHETYPE_HRID", null));
        //Needs a backwards incompatible fix, not changing now (and not important, new API is included)
        knownDifferences.add(new ModelDifference(ModelDifferenceType.PROPERTY_MISSING_IN_BMM, "", "ARCHETYPE_HRID", "concept_id"));
        knownDifferences.add(new ModelDifference(ModelDifferenceType.PROPERTY_MISSING_IN_BMM, "", "ARCHETYPE_HRID", "version_status"));
        knownDifferences.add(new ModelDifference(ModelDifferenceType.PROPERTY_MISSING_IN_BMM, "", "ARCHETYPE_HRID", "namespace"));
        knownDifferences.add(new ModelDifference(ModelDifferenceType.PROPERTY_MISSING_IN_BMM, "", "ARCHETYPE_HRID", "rm_publisher"));
        knownDifferences.add(new ModelDifference(ModelDifferenceType.PROPERTY_MISSING_IN_BMM, "", "ARCHETYPE_HRID", "rm_class"));
        knownDifferences.add(new ModelDifference(ModelDifferenceType.PROPERTY_MISSING_IN_BMM, "", "ARCHETYPE_HRID", "release_version"));
        knownDifferences.add(new ModelDifference(ModelDifferenceType.PROPERTY_MISSING_IN_BMM, "", "ARCHETYPE_HRID", "build_count"));
        knownDifferences.add(new ModelDifference(ModelDifferenceType.PROPERTY_MISSING_IN_BMM, "", "ARCHETYPE_HRID", "rm_package"));
        knownDifferences.add(new ModelDifference(ModelDifferenceType.PROPERTY_MISSING_IN_MODEL, "", "ARCHETYPE_HRID", "value"));
        //Needs a backwards incompatible fix, not changing now (and not important, new API is included)
        knownDifferences.add(new ModelDifference(ModelDifferenceType.PROPERTY_MISSING_IN_BMM, "", "ARCHETYPE_ID", "rm_entity"));
        knownDifferences.add(new ModelDifference(ModelDifferenceType.PROPERTY_MISSING_IN_BMM, "", "ARCHETYPE_ID", "rm_originator"));
        knownDifferences.add(new ModelDifference(ModelDifferenceType.PROPERTY_MISSING_IN_BMM, "", "ARCHETYPE_ID", "qualified_rm_entity"));
        knownDifferences.add(new ModelDifference(ModelDifferenceType.PROPERTY_MISSING_IN_BMM, "", "ARCHETYPE_ID", "specialisation"));
        knownDifferences.add(new ModelDifference(ModelDifferenceType.PROPERTY_MISSING_IN_BMM, "", "ARCHETYPE_ID", "version_id"));
        knownDifferences.add(new ModelDifference(ModelDifferenceType.PROPERTY_MISSING_IN_BMM, "", "ARCHETYPE_ID", "rm_name"));
        knownDifferences.add(new ModelDifference(ModelDifferenceType.PROPERTY_MISSING_IN_BMM, "", "ARCHETYPE_ID", "domain_concept"));
        //AOM class. Differences are not important right now
        knownDifferences.add(new ModelDifference(ModelDifferenceType.PROPERTY_MISSING_IN_BMM, "", "AUTHORED_RESOURCE", "controlled"));
        knownDifferences.add(new ModelDifference(ModelDifferenceType.PROPERTY_MISSING_IN_BMM, "", "AUTHORED_RESOURCE", "uid"));
        knownDifferences.add(new ModelDifference(ModelDifferenceType.PROPERTY_MISSING_IN_BMM, "", "AUTHORED_RESOURCE", "annotations"));
        knownDifferences.add(new ModelDifference(ModelDifferenceType.PROPERTY_MISSING_IN_MODEL, "", "AUTHORED_RESOURCE", "is_controlled"));
        //two ancestors in BMM for all date types. Not going to happen in java unless interface, and cannot check automatically
        knownDifferences.add(new ModelDifference(ModelDifferenceType.ANCESTOR_DIFFERENCE, "", "DV_DATE", null));
        knownDifferences.add(new ModelDifference(ModelDifferenceType.ANCESTOR_DIFFERENCE, "", "DV_DATE_TIME", null));
        knownDifferences.add(new ModelDifference(ModelDifferenceType.ANCESTOR_DIFFERENCE, "", "DV_DURATION", null));
        knownDifferences.add(new ModelDifference(ModelDifferenceType.ANCESTOR_DIFFERENCE, "", "DV_TIME", null));
        //two ancestors for ordered as well
        knownDifferences.add(new ModelDifference(ModelDifferenceType.ANCESTOR_DIFFERENCE, "", "DV_ORDERED", null));
        //same for interval
        knownDifferences.add(new ModelDifference(ModelDifferenceType.ANCESTOR_DIFFERENCE, "", "DV_INTERVAL", null));
        //property in quantity is a fake field, not really in model, but mandatory in BMM. NOT going to fix that!
        knownDifferences.add(new ModelDifference(ModelDifferenceType.EXISTENCE_DIFFERENCE, "", "DV_QUANTITY", "property"));

        //TODO: not sure, looks the same to me
        knownDifferences.add(new ModelDifference(ModelDifferenceType.EXISTENCE_DIFFERENCE, "", "EHR", "contributions"));
        //not important
        knownDifferences.add(new ModelDifference(ModelDifferenceType.PROPERTY_MISSING_IN_MODEL, "", "EHR", "most_recent_composition"));

        //unused enum, not important
        knownDifferences.add(new ModelDifference(ModelDifferenceType.CLASS_MISSING_IN_MODEL, "", "PROPORTION_KIND", null));
        //included in base, not used in EHR,only AOM, so not included here until we need it
        knownDifferences.add(new ModelDifference(ModelDifferenceType.CLASS_MISSING_IN_MODEL, "", "RESOURCE_DESCRIPTION", null));
        //included in base, not used in EHR,only AOM, so not included here until we need it
        knownDifferences.add(new ModelDifference(ModelDifferenceType.CLASS_MISSING_IN_MODEL, "", "RESOURCE_DESCRIPTION_ITEM", null));

        //Archie specific implementation of base class for all objects
        knownDifferences.add(new ModelDifference(ModelDifferenceType.CLASS_MISSING_IN_BMM, "", "RMOBJECT", null));
        //AOM class. not important for RM
        knownDifferences.add(new ModelDifference(ModelDifferenceType.PROPERTY_MISSING_IN_BMM, "", "TRANSLATION_DETAILS", "version_last_translated"));
        //unused enum, not important
        knownDifferences.add(new ModelDifference(ModelDifferenceType.CLASS_MISSING_IN_MODEL, "", "VALIDITY_KIND", null));
        //RM implementation at least in old spec, not sure what happened to it, but not getting rid of it!
        knownDifferences.add(new ModelDifference(ModelDifferenceType.CLASS_MISSING_IN_BMM, "", "VERSIONED_COMPOSITION", null));
        //RM implementation at least in old spec, not sure what happened to it, but not getting rid of it!
        knownDifferences.add(new ModelDifference(ModelDifferenceType.CLASS_MISSING_IN_BMM, "", "VERSIONED_EHR_ACCESS", null));
        //RM implementation at least in old spec, not sure what happened to it, but not getting rid of it!
        knownDifferences.add(new ModelDifference(ModelDifferenceType.CLASS_MISSING_IN_BMM, "", "VERSIONED_EHR_STATUS", null));
        //RM implementation at least in old spec, not sure what happened to it, but not getting rid of it!
        knownDifferences.add(new ModelDifference(ModelDifferenceType.CLASS_MISSING_IN_BMM, "", "VERSIONED_FOLDER", null));

        //System.out.println(Joiner.on("\n").join(compared));

        for(ModelDifference difference:compared) {
//            System.out.println(MessageFormat.format("knownDifferences.add(new ModelDifference(ModelDifferenceType.{0}, \"\", \"{1}\", {2}));",
//                    difference.getType().toString(),
//                    difference.getClassName(),
//                    difference.getPropertyName() == null ? "null" : '"' + difference.getPropertyName() + '"'));

            assertTrue("unexpected difference: " + difference.toString(), knownDifferences.contains(difference));
        }
        assertEquals(knownDifferences.size(), compared.size());
    }
}
