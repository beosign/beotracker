package de.beosign.beotracker.ticket;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.beosign.beotracker.jsf.AbstractController;

@Named
@ViewScoped
public class TicketController extends AbstractController {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(TicketController.class);

    @Inject
    private TicketService ticketService;

    private List<Ticket> tickets;

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

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

}
