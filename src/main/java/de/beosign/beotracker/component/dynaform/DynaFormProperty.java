package de.beosign.beotracker.component.dynaform;

import java.util.Arrays;

import org.apache.commons.beanutils.PropertyUtils;

public class DynaFormProperty {
    private Object baseObject;
    private String type;
    private String label;
    private String name;
    private Object value;

    public DynaFormProperty(Object baseObject, String name, Object value) {
        this(baseObject, name, value, null);
    }

    public DynaFormProperty(Object baseObject, String name, Object value, String type) {
        this(baseObject, name, value, type, null);
    }

    public DynaFormProperty(Object baseObject, String name, Object value, String type, String label) {
        this.baseObject = baseObject;
        this.name = name;
        this.value = value;
        this.type = type;
        this.label = label;
    }

    public static <T> DynaFormProperty of(T entity, String propertyName) throws ReflectiveOperationException {
        Object value = PropertyUtils.getProperty(entity, propertyName);
        Class<?> valueType = PropertyUtils.getPropertyType(entity, propertyName);

        if (value instanceof Enum) {
            return new SingleListDynaFormProperty<>(entity, propertyName, value, propertyName, Arrays.asList(valueType.getEnumConstants()));
        } else {
            return new DynaFormProperty(entity, propertyName, value, "input", propertyName);
        }

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getBaseObject() {
        return baseObject;
    }

}
