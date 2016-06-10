package com.nedap.archie.serializer.adl;

import com.google.common.collect.ImmutableMap;
import com.nedap.archie.aom.ResourceAnnotations;
import com.nedap.archie.aom.ResourceDescription;
import com.nedap.archie.aom.ResourceDescriptionItem;
import com.nedap.archie.aom.TranslationDetails;
import com.nedap.archie.aom.terminology.ArchetypeTerm;
import com.nedap.archie.aom.terminology.ArchetypeTerminology;
import com.nedap.archie.aom.terminology.ValueSet;
import com.nedap.archie.rm.datatypes.CodePhrase;
import com.nedap.archie.serializer.odin.OdinObject;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Function;

import static java.util.Optional.ofNullable;

/**
 * @author markopi
 */
public class ADLOdinObjectMapper implements Function<Object, Object> {
    public static final ADLOdinObjectMapper INSTANCE = new ADLOdinObjectMapper();

    private final Map<Class, Function<Object, Object>> mappers = ImmutableMap.<Class, Function<Object, Object>>builder()
            .put(ArchetypeTerminology.class, o -> map((ArchetypeTerminology) o))
            .put(ResourceAnnotations.class, o -> map((ResourceAnnotations) o))
            .put(ResourceDescription.class, o -> map((ResourceDescription) o))
            .put(ResourceDescriptionItem.class, o -> map((ResourceDescriptionItem) o))
            .put(TranslationDetails.class, o -> map((TranslationDetails) o))
            .put(ArchetypeTerm.class, o -> map((ArchetypeTerm) o))
            .put(ValueSet.class, o -> map((ValueSet) o))
            .build();


    private ADLOdinObjectMapper() {
    }

    @Override
    public Object apply(Object o) {
        Function<Object, Object> mapper = mappers.get(o.getClass());
        if (mapper != null) {
            return mapper.apply(o);
        }
        return o;
    }

    private OdinObject map(ResourceAnnotations a) {
        return new OdinObject()
                .put("documentation", a.getDocumentation());
    }

    private OdinObject map(ArchetypeTerminology t) {
        return new OdinObject()
                .put("term_definitions", nullIfEmpty(t.getTermDefinitions()))
                .put("term_bindings", nullIfEmpty(t.getTermBindings()))
                .put("value_sets", nullIfEmpty(t.getValueSets()))
                .put("terminology_extracts", nullIfEmpty(t.getTerminologyExtracts()));
    }

    private OdinObject map(ArchetypeTerm a) {
        OdinObject result = new OdinObject()
                .put("text", a.getText())
                .put("description", a.getDescription());
        a.getOtherItems().forEach(result::put);
        return result;
    }

    private OdinObject map(ValueSet v) {
        return new OdinObject()
                .put("id", v.getId())
                .put("members", v.getMembers());
    }


    @Nullable
    private <K, V> Map<K, V> nullIfEmpty(Map<K, V> map) {
        if (map == null || map.isEmpty()) return null;
        return map;
    }

    private OdinObject map(ResourceDescription desc) {
        return new OdinObject()
                .put("lifecycle_state", ofNullable(desc.getLifecycleState()).map(CodePhrase::getCodeString))
                .put("original_author", desc.getOriginalAuthor())
                .put("original_publisher", desc.getOriginalPublisher())
                .put("original_namespace", desc.getOriginalNamespace())
                .put("license", desc.getLicence())
                .put("copyright", desc.getCopyright())
                .put("other_contributors", desc.getOtherContributors())
                .put("custodian_organization", desc.getCustodianOrganisation())
                .put("custodian_namespace", desc.getCustodianNamespace())
                .put("resource_package_uri", desc.getResourcePackageUri())
                .put("ip_acknowledgements", desc.getIpAcknowledgements())
                .put("references", desc.getReferences())
                .put("details", desc.getDetails())
                .put("conversion_details", desc.getConversionDetails())
                .put("other_details", desc.getOtherDetails());

    }

    private OdinObject map(ResourceDescriptionItem rdi) {
        return new OdinObject()
                .put("language", rdi.getLanguage())
                .put("purpose", rdi.getPurpose())
                .put("use", rdi.getUse())
                .put("misuse", rdi.getMisuse())
                .put("keywords", rdi.getKeywords())
                .put(rdi.getCopyright(), rdi.getCopyright())
                .put("original_resource_uri", rdi.getOriginalResourceUri())
                .put("other_details", rdi.getOtherDetails());
    }

    private OdinObject map(TranslationDetails td) {
        return new OdinObject()
                .put("language", td.getLanguage())
                .put("author", td.getAuthor())
                .put("accreditation", td.getAccreditation())
                .put("version_last_translated", td.getVersionLastTranslated())
                .put("other_details", td.getOtherDetails());
    }

}
