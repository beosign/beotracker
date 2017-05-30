package de.beosign.beotracker.ticket;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.event.Event;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.beosign.beotracker.jsf.AbstractController;

@Named
@ViewScoped
public class TicketsController extends AbstractController {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(TicketMainController.class);

    @Inject
    private TicketService ticketService;

    @Inject
    private Event<Ticket> ticketSelectedEvent;

    private List<Ticket> tickets;

    private Ticket selectedTicket;

    @PostConstruct
    @Override
    protected void init() {
        super.init();
        log.trace("init");

        tickets = ticketService.findAll();
    }

    @PreDestroy
    @Override
    protected void preDestroy() {
        super.preDestroy();
        log.trace("destroy");
    }

    public void onRowSelect(SelectEvent event) {
        log.debug("Selected row: {}", event.getObject());
        FacesMessage msg = new FacesMessage("Ticket selected", "" + ((Ticket) event.getObject()).getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        ticketSelectedEvent.fire(selectedTicket);

    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Ticket getSelectedTicket() {
        return selectedTicket;
    }

    public void setSelectedTicket(Ticket selectedTicket) {
        this.selectedTicket = selectedTicket;
    }

}
