package de.beosign.beotracker.ticket;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.beanutils.BeanUtils;
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.beosign.beotracker.cdi.All;
import de.beosign.beotracker.component.dynaform.DynaFormProperty;
import de.beosign.beotracker.component.dynaform.DynaFormRowBuilder;
import de.beosign.beotracker.component.dynaform.SingleListDynaFormProperty;
import de.beosign.beotracker.jsf.AbstractController;
import de.beosign.beotracker.user.User;
import de.beosign.beotracker.user.UserService;

@Named
@ViewScoped
public class TicketController extends AbstractController {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(TicketController.class);

    @Inject
    private UserService userService;
    @Inject
    private TicketService ticketService;

    @Inject
    @All
    private Event<Ticket> ticketEvent;

    private Ticket ticket;

    private List<User> assignableUsers;

    private DynaFormModel dynaFormModel;

    @Override
    @PostConstruct
    protected void init() {
        super.init();

        assignableUsers = userService.findAll();
        dynaFormModel = createDynaFormModel();
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public void setSelectedTicket(Ticket ticket) {
        this.ticket = ticket;
        this.init();
    }

    public void cancel() {
        backToList();
    }

    public void assign() {
        log.debug("Assigning properties to ticket {}", ticket);

        for (DynaFormProperty p : getTicketProperties()) {
            try {
                log.debug("Setting property {} of ticket {}", p.getName(), ticket);
                BeanUtils.setProperty(ticket, p.getName(), p.getValue());
            } catch (ReflectiveOperationException e) {
                throw new IllegalArgumentException("Exception assigning property " + p.getName() + " to ticket", e);
            }
        }
        ticket = ticketService.update(ticket);
        backToList();
    }

    private void backToList() {
        ticketEvent.fire(Ticket.NULL_TICKET);
    }

    public List<User> getAssignableUsers() {
        return assignableUsers;
    }

    public void setAssignableUsers(List<User> assignableUsers) {
        this.assignableUsers = assignableUsers;
    }

    public DynaFormModel getDynaFormModel() {
        return dynaFormModel;
    }

    public void setDynaFormModel(DynaFormModel dynaFormModel) {
        this.dynaFormModel = dynaFormModel;
    }

    private DynaFormModel createDynaFormModel() {
        DynaFormModel dynaFormModel = new DynaFormModel();

        if (ticket == null) {
            return dynaFormModel;
        }

        try {
            DynaFormProperty summaryProperty = DynaFormProperty.of(ticket, "summary");
            DynaFormRow row = DynaFormRowBuilder.createWithLabelAndControl(dynaFormModel, summaryProperty);

            DynaFormProperty statusProperty = DynaFormProperty.of(ticket, "status");
            row = dynaFormModel.createRegularRow();
            DynaFormLabel label21 = row.addLabel(statusProperty.getLabel());
            DynaFormControl control22 = row.addControl(statusProperty, statusProperty.getType());
            label21.setForControl(control22);

            DynaFormProperty assignedUserProperty = new SingleListDynaFormProperty<>(ticket, "assignedUser", ticket.getAssignedUser(), "Assigned User",
                    assignableUsers,
                    user -> user.getLoginName());
            row = DynaFormRowBuilder.createWithLabelAndControl(dynaFormModel, assignedUserProperty);

            return dynaFormModel;
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public List<DynaFormProperty> getTicketProperties() {
        if (dynaFormModel == null) {
            return null;
        }

        List<DynaFormProperty> ticketProperties = new ArrayList<>();
        for (DynaFormControl dynaFormControl : dynaFormModel.getControls()) {
            ticketProperties.add((DynaFormProperty) dynaFormControl.getData());
        }

        return ticketProperties;
    }

}
