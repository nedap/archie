package org.openehr.referencemodels;

public class ModelDifference {

    private ModelDifferenceType type;
    private String className;
    private String propertyName;

    private String message;

    public ModelDifference(ModelDifferenceType type, String message) {
        this(type, message, null, null);
    }

    public ModelDifference(ModelDifferenceType type, String message, String className) {
        this(type, message, className, null);
    }

    public ModelDifference(ModelDifferenceType type, String message, String className, String propertyName) {
        this.type = type;
        this.message = message;
        this.className = className;
        this.propertyName = propertyName;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }

    public ModelDifferenceType getType() {
        return type;
    }

    public String getClassName() {
        return className;
    }

    public String getPropertyName() {
        return propertyName;
    }
}
