package de.beosign.beotracker.settings;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.beosign.beotracker.component.inputtext.ParameterizedValidator;

/**
 * Backing component of the <code>validateSetting</code> tag.
 * 
 * @author florian
 */
@FacesValidator(ParameterizedSettingsValidator.ID)
public class ParameterizedSettingsValidator extends ParameterizedValidator {
    public static final String ID = "de.beosign.beotracker.settings.ParameterizedSettingsValidator";

    private static final Logger log = LoggerFactory.getLogger(ParameterizedSettingsValidator.class);

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        log.debug("Validating value {} with parameters {}", value, getParameters(component));

        Setting<Object> setting = (Setting<Object>) getParameters(component).get("setting");

        SettingsValidator.validate(context, component, value, setting);
    }

}
