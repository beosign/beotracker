package de.beosign.beotracker.settings;

import java.io.Serializable;
import java.util.Objects;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Backing component of the <code>validateSetting</code> tag.
 * 
 * @author florian
 */
@FacesValidator(SettingsValidator.ID)
public class SettingsValidator implements Validator, Serializable {
    public static final String ID = "de.beosign.beotracker.settings.SettingsValidator";

    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(SettingsValidator.class);

    private Setting<Object> setting;
    private String settingVar;

    static void validate(FacesContext context, UIComponent component, Object value, Setting<Object> setting) throws ValidatorException {
        final String stringValue;
        if (value instanceof String) {
            stringValue = (String) value;
        } else {
            stringValue = Objects.toString(value);
        }

        Object convertedValue = setting.getConverter().apply(stringValue);

        boolean isValid = setting.getValidationRules().stream().allMatch(rule -> rule.test(convertedValue));

        if (!isValid) {
            FacesMessage message = new FacesMessage("Setting " + setting.getKey() + " has an invalid converted value " + convertedValue + " (value = " + value + ")");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
        log.trace("Setting {} with value {} passed validation", setting, convertedValue);

    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        log.debug("Validating value {} with setting {} and settingVar {}", value, getSetting(), getSettingVar());

        Setting<Object> setting = getSetting();

        if (setting == null) {
            @SuppressWarnings("unchecked")
            Setting<Object> resolvedSetting = (Setting<Object>) context.getELContext().getELResolver().getValue(context.getELContext(), null, getSettingVar());
            setting = resolvedSetting;
            log.debug("Setting was null, so using settingVar instead, which resolved to {}", setting);
        }

        validate(context, component, value, setting);

    }

    public Setting<Object> getSetting() {
        return setting;
    }

    public void setSetting(Setting<Object> setting) {
        this.setting = setting;
        log.trace("{}: Set setting {}", FacesContext.getCurrentInstance().getCurrentPhaseId(), setting);
    }

    public String getSettingVar() {
        return settingVar;
    }

    public void setSettingVar(String settingVar) {
        this.settingVar = settingVar;
        log.trace("{}: Set settingVar {}", FacesContext.getCurrentInstance().getCurrentPhaseId(), settingVar);
    }

}
