package de.beosign.beotracker.component.inputtext;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.validator.Validator;

/**
 * Base class for all parameterized validators.
 * 
 * @author florian
 */
public abstract class ParameterizedValidator implements Validator {

    /**
     * Returns the parameters that have been passed to this validator.
     * 
     * @param component component to be validated
     */
    @SuppressWarnings("unchecked")
    protected Map<String, Object> getParameters(UIComponent component) {
        return (Map<String, Object>) component.getAttributes().get("params");
    }
}
