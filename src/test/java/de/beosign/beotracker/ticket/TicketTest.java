package de.beosign.beotracker.ticket;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.beosign.beotracker.ticket.Ticket.Priority;
import de.beosign.beotracker.ticket.Ticket.Status;
import de.beosign.beotracker.user.User;
import de.beosign.beotracker.user.UserService;

@Named
@ViewScoped
public class TicketTest implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger log = LoggerFactory.getLogger(TicketTest.class);

    @Inject
    private TicketService ticketService;
    @Inject
    private UserService userService;

    private List<User> users;
    private Ticket ticket;

    @PostConstruct
    private void init() {
        List<Ticket> tickets = ticketService.findAll();
        if (tickets.size() == 0) {
            ticket = new Ticket();
        } else {
            ticket = tickets.get(0);
        }

        users = userService.findAll();
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public void onSave() {
        log.info("Save called!");
    }

    public void onShowState() {
        log.debug("State: {}", ticket);
    }

    public void setInvalid() {
        ticket = new Ticket();
        ticket.setStatus(Status.ASSIGNED);
        ticket.setPriority(Priority.LOW);
    }

    public void setValid() {
        ticket = new Ticket();
        ticket.setStatus(Status.NEW);
        ticket.setPriority(Priority.LOW);
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}
