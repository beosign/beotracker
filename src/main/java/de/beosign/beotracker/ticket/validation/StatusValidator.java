package de.beosign.beotracker.ticket.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.beosign.beotracker.ticket.Ticket;
import de.beosign.beotracker.ticket.Ticket.Status;

public class StatusValidator implements ConstraintValidator<ValidStatus, Ticket> {
    private static final Logger log = LoggerFactory.getLogger(StatusValidator.class);

    @Override
    public void initialize(ValidStatus constraintAnnotation) {
    }

    @Override
    public boolean isValid(Ticket value, ConstraintValidatorContext context) {
        log.trace("Validating ticket {} ({})", value.getSummary(), value.getId());

        if (value.getAssignedUser() == null && value.getStatus() != Status.NEW || value.getAssignedUser() != null && value.getStatus() == Status.NEW) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("status")
                    .addConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("assignedUser")
                    .addConstraintViolation();
            log.debug("Validating ticket {} ({}): {}", value.getSummary(), value.getId(), false);
            return false;
        }

        log.trace("Validating ticket {} ({}): {}", value.getSummary(), value.getId(), true);
        return true;
    }

}
