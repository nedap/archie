package com.nedap.archie.aom.profile;

import java.util.Map;

/**
 * Profile of common settings relating to use of reference model(s) and terminology for a given archetype
 * developing organisation.
 *
 * Created by cnanjo on 5/30/16.
 */
public class AomProfile {

    /**
     * Allowed type substitutions: Actual RM type names keyed by AOM built-in types which can substitute
     * for them in an archetype. E.g. <value = "String", key = "ISO8601_DATE"> means that if RM property
     * TYPE.some_property is of type String, an ISO8601_DATE is allowed at that position in the archetype.
     */
    private Map<String, String> aomRmTypeSubstitution;
    /**
     * List of mappings of lifecycle state names used in archetypes to AOM lifecycle state names. value = AOM
     * lifecycle state; key = source lifecycle state.
     */
    private Map<String, String> aomLifecycleMapping;
    /**
     * Name of this profile, usually based on the publisher it pertains to e.g. "openEHR", "cdisc", etc.
     */
    private String profileName;
    /**
     * Mappings from AOM built-in types to actual types in RM: whenever the type name is encountered in
     * an archetype, it is mapped to a specific RM type.
     */
    private Map<String, AomTypeMapping> aomRmTypeMappings;

    /**
     * @return Allowed type substitutions: Actual RM type names keyed by AOM built-in types which can substitute
     * for them in an archetype. E.g. <value = "String", key = "ISO8601_DATE"> means that if RM property
     * TYPE.some_property is of type String, an ISO8601_DATE is allowed at that position in the archetype.
     */
    public Map<String, String> getAomRmTypeSubstitution() {
        return aomRmTypeSubstitution;
    }

    /**
     *
     * @param aomRmTypeSubstitution Allowed type substitutions: Actual RM type names keyed by AOM built-in types which can substitute
     * for them in an archetype. E.g. <value = "String", key = "ISO8601_DATE"> means that if RM property
     * TYPE.some_property is of type String, an ISO8601_DATE is allowed at that position in the archetype.
     */
    public void setAomRmTypeSubstitution(Map<String, String> aomRmTypeSubstitution) {
        this.aomRmTypeSubstitution = aomRmTypeSubstitution;
    }

    /**
     *
     * @return List of mappings of lifecycle state names used in archetypes to AOM lifecycle state names. value = AOM
     * lifecycle state; key = source lifecycle state.
     */
    public Map<String, String> getAomLifecycleMapping() {
        return aomLifecycleMapping;
    }

    /**
     *
     * @param aomLifecycleMapping List of mappings of lifecycle state names used in archetypes to AOM lifecycle state names. value = AOM
     * lifecycle state; key = source lifecycle state.
     */
    public void setAomLifecycleMapping(Map<String, String> aomLifecycleMapping) {
        this.aomLifecycleMapping = aomLifecycleMapping;
    }

    /**
     *
     * @return Name of this profile, usually based on the publisher it pertains to e.g. "openEHR", "cdisc", etc.
     */
    public String getProfileName() {
        return profileName;
    }

    /**
     *
     * @param profileName Name of this profile, usually based on the publisher it pertains to e.g. "openEHR", "cdisc", etc.
     */
    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    /**
     *
     * @return Mappings from AOM built-in types to actual types in RM: whenever the type name is encountered in
     * an archetype, it is mapped to a specific RM type.
     */
    public Map<String, AomTypeMapping> getAomRmTypeMappings() {
        return aomRmTypeMappings;
    }

    /**
     *
     * @param aomRmTypeMappings Mappings from AOM built-in types to actual types in RM: whenever the type name is encountered in
     * an archetype, it is mapped to a specific RM type.
     */
    public void setAomRmTypeMappings(Map<String, AomTypeMapping> aomRmTypeMappings) {
        this.aomRmTypeMappings = aomRmTypeMappings;
    }
}
