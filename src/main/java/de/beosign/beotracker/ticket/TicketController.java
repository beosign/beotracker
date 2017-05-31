package de.beosign.beotracker.ticket;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
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

    @Override
    @PostConstruct
    protected void init() {
        super.init();

        assignableUsers = userService.findAll();
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
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

}
