package com.nedap.archie.aom.profile;

/**
 * Data object expressing a mapping between two class properties.
 *
 * Created by cnanjo on 5/30/16.
 */
public class AomPropertyMapping {

    /**
     * Name of property in source class.
     */
    private String sourcePropertyName;
    /**
     * Name of property in target class.
     */
    private String targetPropertyName;

    /**
     *
     * @return Name of property in source class.
     */
    public String getSourcePropertyName() {
        return sourcePropertyName;
    }

    /**
     *
     * @param sourcePropertyName Name of property in source class.
     */
    public void setSourcePropertyName(String sourcePropertyName) {
        this.sourcePropertyName = sourcePropertyName;
    }

    /**
     *
     * @return Name of property in target class.
     */
    public String getTargetPropertyName() {
        return targetPropertyName;
    }

    /**
     *
     * @param targetPropertyName Name of property in target class.
     */
    public void setTargetPropertyName(String targetPropertyName) {
        this.targetPropertyName = targetPropertyName;
    }
}
