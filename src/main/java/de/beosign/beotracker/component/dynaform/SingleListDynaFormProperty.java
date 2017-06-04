package de.beosign.beotracker.component.dynaform;

import java.util.List;

public class SingleListDynaFormProperty extends DynaFormProperty {
    private List<?> values;

    public <T> SingleListDynaFormProperty(String name, Object value, List<T> values) {
        this(name, value, null, values);
    }

    public <T> SingleListDynaFormProperty(String name, Object value, String label, List<T> values) {
        super(name, value, "selectOne", label);
        this.values = values;
    }

    public List<?> getValues() {
        return values;
    }
}