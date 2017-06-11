package de.beosign.beotracker.component.dynaform;

import static de.beosign.beotracker.component.dynaform.DynaFormBeanValidator.*;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.BeanValidator;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;
import javax.validation.metadata.BeanDescriptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Invokes BeanValidation for a {@link DynaFormProperty}.
 * 
 * @author florian
 */
@FacesValidator(VALIDATOR_ID)
public class DynaFormBeanValidator implements Validator, Serializable {
    public static final String VALIDATOR_ID = "dynaFormBeanValidator";

    private static final Logger log = LoggerFactory.getLogger(DynaFormBeanValidator.class);
    private static final long serialVersionUID = 1L;

    private static final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    @Override
    public void validate(FacesContext ctx, UIComponent component, Object value) throws ValidatorException {
        DynaFormProperty dynaFormProperty = (DynaFormProperty) component.getAttributes().get("dynaFormProperty");
        if (dynaFormProperty == null) {
            throw new IllegalArgumentException("A property named 'dynaFormProperty' must be set");
        }

        log.trace("Validate {}.{} on component {}", dynaFormProperty.getBaseObject().getClass().getSimpleName(), dynaFormProperty.getName(),
                component.getClientId());

        javax.validation.Validator validator = createValidator(ctx);

        BeanDescriptor beanDescriptor = validator.getConstraintsForClass(dynaFormProperty.getBaseObject().getClass());
        if (!beanDescriptor.isBeanConstrained()) {
            return;
        }

        Set<? extends ConstraintViolation<? extends Object>> constraintViolations = validator.validateValue(
                dynaFormProperty.getBaseObject().getClass(),
                dynaFormProperty.getName(),
                value, Default.class);

        if (!constraintViolations.isEmpty()) {
            log.debug("Constraint violations for {}.{} on component {}: {}", dynaFormProperty.getBaseObject().getClass().getSimpleName(),
                    dynaFormProperty.getName(),
                    component.getClientId(), constraintViolations);

            Set<FacesMessage> messages = new LinkedHashSet<>(constraintViolations.size());
            for (ConstraintViolation<?> violation : constraintViolations) {
                messages.add(getMessage(ctx,
                        BeanValidator.MESSAGE_ID,
                        violation.getMessage(),
                        dynaFormProperty.getLabel()));
            }
            throw new ValidatorException(messages);
        }

        log.trace("Successful validation of {}.{} on component {}", dynaFormProperty.getBaseObject().getClass().getSimpleName(), dynaFormProperty.getName(),
                component.getClientId());

    }

    private FacesMessage getMessage(FacesContext ctx, String messageId, String message, String label) {
        return new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
    }

    private javax.validation.Validator createValidator(FacesContext context) {
        return validatorFactory.getValidator();

    }

}
