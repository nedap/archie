package org.openehr.referencemodels;

import java.util.Objects;

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
        return "ModelDifference{" +
                "type=" + type +
                ", className='" + className + '\'' +
                ", propertyName='" + propertyName + '\'' +
                ", message='" + message + '\'' +
                '}';
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ModelDifference)) return false;
        ModelDifference that = (ModelDifference) o;
        return type == that.type &&
                Objects.equals(className, that.className) &&
                Objects.equals(propertyName, that.propertyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, className, propertyName);
    }
}
