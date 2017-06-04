package de.beosign.beotracker.component.dynaform;

import java.util.List;
import java.util.function.Function;

public class SingleListDynaFormProperty<T> extends DynaFormProperty {
    private List<T> values;
    private Function<T, String> itemLabelPropertyMapper;

    public SingleListDynaFormProperty(String name, T value, List<T> values) {
        this(name, value, name, values);
    }

    public SingleListDynaFormProperty(String name, T value, String label, List<T> values) {
        super(name, value, "selectOne", label);
        this.values = values;
    }

    public SingleListDynaFormProperty(String name, T value, String label, List<T> values, Function<T, String> itemLabelPropertyMapper) {
        super(name, value, "selectOne", label);
        this.values = values;
        this.itemLabelPropertyMapper = itemLabelPropertyMapper;
    }

    public List<T> getValues() {
        return values;
    }

    public String getItemLabel(T value) {
        if (itemLabelPropertyMapper == null) {
            return value != null ? value.toString() : "";
        }

        return itemLabelPropertyMapper.apply(value);
    }
}
