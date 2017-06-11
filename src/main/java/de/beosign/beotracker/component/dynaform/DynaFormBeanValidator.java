package de.beosign.beotracker.component.dynaform;

import static de.beosign.beotracker.component.dynaform.DynaFormBeanValidator.*;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Map;
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

/**
 * Invokes BeanValidation for a {@link DynaFormProperty}.
 * 
 * @author florian
 */
@FacesValidator(VALIDATOR_ID)
public class DynaFormBeanValidator implements Validator, Serializable {
    public static final String VALIDATOR_ID = "dynaFormBeanValidator";

    private static final long serialVersionUID = 1L;

    public DynaFormBeanValidator() {
    }

    @Override
    public void validate(FacesContext ctx, UIComponent component, Object value) throws ValidatorException {
        System.out.println("VALIDATE: " + value + "; " + value.getClass().getSimpleName());

        DynaFormProperty dynaFormProperty = (DynaFormProperty) component.getAttributes().get("dynaFormProperty");
        System.out.println("VALIDATE: " + dynaFormProperty + "; " + dynaFormProperty.getBaseObject().getClass().getSimpleName());

        javax.validation.Validator validator = createValidator(ctx);

        BeanDescriptor beanDescriptor = validator.getConstraintsForClass(dynaFormProperty.getBaseObject().getClass());
        if (!beanDescriptor.isBeanConstrained()) {
            return;
        }

        Set<? extends ConstraintViolation<? extends Object>> constraintViolations = validator.validateValue(
                dynaFormProperty.getBaseObject().getClass(),
                dynaFormProperty.getName(),
                value, Default.class);

        System.out.println("violations: " + constraintViolations);

        if (!constraintViolations.isEmpty()) {
            Set<FacesMessage> messages = new LinkedHashSet<>(constraintViolations.size());
            for (ConstraintViolation<?> violation : constraintViolations) {
                messages.add(getMessage(ctx,
                        BeanValidator.MESSAGE_ID,
                        violation.getMessage(),
                        dynaFormProperty.getLabel()));
            }
            throw new ValidatorException(messages);
        }

    }

    private FacesMessage getMessage(FacesContext ctx, String messageId, String message, String label) {
        return new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
    }

    private ValidatorFactory getValidatorFactory(FacesContext context) {
        ValidatorFactory validatorFactory = null;
        Map<String, Object> applicationMap = context.getExternalContext().getApplicationMap();
        Object attr = applicationMap.get(BeanValidator.VALIDATOR_FACTORY_KEY);
        if (attr instanceof ValidatorFactory) {
            validatorFactory = (ValidatorFactory) attr;
        } else {

            validatorFactory = Validation.buildDefaultValidatorFactory();
            applicationMap.put(BeanValidator.VALIDATOR_FACTORY_KEY, validatorFactory);
        }
        return validatorFactory;
    }

    private javax.validation.Validator createValidator(FacesContext context) {
        return getValidatorFactory(context) //
                .usingContext() //
                .getValidator();

    }

}
