package de.beosign.beotracker.ticket;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.beanutils.PropertyUtils;
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.beosign.beotracker.cdi.All;
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
    @All
    private Event<Ticket> ticketEvent;

    private Ticket ticket;

    private List<User> assignableUsers;

    private User assignedUser;

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
        this.dynaFormModel = createDynaFormModel();
    }

    public void cancel() {
        ticketEvent.fire(Ticket.NULL_TICKET);
    }

    public void assign() {
        log.debug("Assigning user {} to ticket {}", assignedUser, ticket);

        ticket.setAssignedUser(assignedUser);
    }

    public List<User> getAssignableUsers() {
        return assignableUsers;
    }

    public void setAssignableUsers(List<User> assignableUsers) {
        this.assignableUsers = assignableUsers;
    }

    public User getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(User assignedUser) {
        this.assignedUser = assignedUser;
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

        for (PropertyDescriptor pd : PropertyUtils.getPropertyDescriptors(Ticket.class)) {
            // 1. row
            DynaFormRow row = dynaFormModel.createRegularRow();

            DynaFormLabel label = row.addLabel(pd.getName());
            DynaFormControl control;
            try {
                GenericProperty<Ticket> property = new GenericProperty<>(ticket, pd.getName());
                control = row.addControl(property, property.guiType);
                label.setForControl(control);
            } catch (ReflectiveOperationException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

        }

        // 1. row
        // DynaFormRow row = dynaFormModel.createRegularRow();
        //
        // DynaFormLabel label11 = row.addLabel("Summary");
        // DynaFormControl control12 = row.addControl(new TicketProperty("summary", ticket.getSummary()), "input");
        // label11.setForControl(control12);
        //
        // row = dynaFormModel.createRegularRow();
        // DynaFormLabel label21 = row.addLabel("Status");
        // DynaFormControl control22 = row.addControl(new TicketProperty("status", ticket.getStatus(), Arrays.asList(Ticket.Status.values())), "select");
        // label21.setForControl(control22);

        return dynaFormModel;
    }

    public List<TicketProperty> getTicketProperties() {
        if (dynaFormModel == null) {
            return null;
        }

        List<TicketProperty> ticketProperties = new ArrayList<>();
        for (DynaFormControl dynaFormControl : dynaFormModel.getControls()) {
            ticketProperties.add((TicketProperty) dynaFormControl.getData());
        }

        return ticketProperties;
    }

    public static class TicketProperty {
        private String name;
        private Object value;
        private List<?> listValues;

        public TicketProperty(String name) {
            this(name, null);
        }

        public TicketProperty(String name, Object value) {
            this(name, value, null);
        }

        public <T> TicketProperty(String name, T value, List<T> listValues) {
            this.name = name;
            this.value = value;
            this.listValues = listValues;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public List<?> getListValues() {
            return listValues;
        }

        public void setListValues(List<?> listValues) {
            this.listValues = listValues;
        }

    }

    public static class GenericProperty<T> {
        private String propertyName;
        private T entity;
        private List<?> listValues;
        private Object value;
        private Class<?> valueType;
        private String guiType;

        public GenericProperty(T entity, String propertyName) throws ReflectiveOperationException {
            this.entity = entity;
            this.propertyName = propertyName;

            value = PropertyUtils.getProperty(entity, propertyName);
            valueType = PropertyUtils.getPropertyType(entity, propertyName);

            if (value instanceof Enum) {
                listValues = Arrays.asList(valueType.getEnumConstants());
                guiType = "select";
            } else {
                guiType = "input";
            }

        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public List<?> getListValues() {
            return listValues;
        }

        public void setListValues(List<?> listValues) {
            this.listValues = listValues;
        }

        public String getGuiType() {
            return guiType;
        }

        public void setGuiType(String guiType) {
            this.guiType = guiType;
        }

    }
}
