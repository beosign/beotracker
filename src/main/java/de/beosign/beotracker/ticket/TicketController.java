package de.beosign.beotracker.ticket;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import de.beosign.beotracker.jsf.AbstractController;

@Named
@ViewScoped
public class TicketController extends AbstractController {
    private static final long serialVersionUID = 1L;

    @Inject
    private Event<Ticket> ticketEvent;

    private Ticket ticket;

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public void cancel() {
        ticketEvent.fire(Ticket.NULL_TICKET);
    }

}
