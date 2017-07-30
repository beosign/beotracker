package de.beosign.beotracker.settings;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Setting<T> {
    private String key;
    private T value;

    private List<Predicate<T>> validationRules = new ArrayList<>();
    private Function<String, T> converter;

    public Setting() {
    }

    public Setting(String key, T value) {
        this.key = key;
        this.value = value;
        if (value instanceof String) {
            converter = s -> asT(s);
        } else if (value instanceof Integer) {
            converter = s -> asT(Integer.parseInt(s));
        }
    }

    public Setting(String key, T value, Function<String, T> converter) {
        this.key = key;
        this.value = value;
        this.converter = converter;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public List<Predicate<T>> getValidationRules() {
        return validationRules;
    }

    @Override
    public String toString() {
        return "Setting [key=" + key + ", value=" + value + "]";
    }

    public Function<String, T> getConverter() {
        return converter;
    }

    public void setConverter(Function<String, T> converter) {
        this.converter = converter;
    }

    @SuppressWarnings("unchecked")
    private static <T> T asT(Object o) {
        return (T) o;
    }
}
