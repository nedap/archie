package org.openehr.referencemodels;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
import com.nedap.archie.rminfo.ArchieRMInfoLookup;
import org.junit.Test;
import org.openehr.bmm.core.BmmModel;
import org.openehr.bmm.v2.validation.BmmRepository;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RMComparedWithBmmTest {

    @Test
    public void compareBmmWithRM() {

        Map<String, String> typeMap =  new HashMap<>();
        typeMap.put("Any", "Object");
        typeMap.put("Integer64", "Long");
        typeMap.put("Integer", "Long");
        typeMap.put("REAL", "Double");
        typeMap.put("Character", "char");
        typeMap.put("Octet", "Byte[]"); //TODO: fix this properly
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
        System.out.println(Joiner.on("\n").join(compared));
    }
}
