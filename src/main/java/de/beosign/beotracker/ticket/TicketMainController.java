package de.beosign.beotracker.ticket;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.beosign.beotracker.cdi.All;
import de.beosign.beotracker.cdi.Selected;
import de.beosign.beotracker.jsf.AbstractController;

@Named
@ViewScoped
public class TicketMainController extends AbstractController {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(TicketMainController.class);

    @Inject
    private TicketController ticketController;
    @Inject
    private TicketsController ticketsController;

    private String includeFile = "/WEB-INF/includes/ticket/tickets.xhtml";

    @PostConstruct
    @Override
    protected void init() {
        super.init();
        log.trace("init");
    }

    @PreDestroy
    @Override
    protected void preDestroy() {
        super.preDestroy();
        log.trace("destroy");
    }

    public void onTicketSelected(@Observes @Selected Ticket ticket) {
        log.debug("Selected ticket: {}", ticket);

        includeFile = "/WEB-INF/includes/ticket/ticket.xhtml";
        ticketController.setSelectedTicket(ticket);
        RequestContext.getCurrentInstance().update("ticket-center");
    }

    public void onShowAllTickets(@Observes @All Ticket ticket) {
        log.debug("Selected ticket: {}", ticket);

        includeFile = "/WEB-INF/includes/ticket/tickets.xhtml";
        ticketController.setTicket(null);
        ticketsController.init();

        RequestContext.getCurrentInstance().update("ticket-center");
    }

    public String getIncludeFile() {
        return includeFile;
    }

    public void setIncludeFile(String includeFile) {
        this.includeFile = includeFile;
    }

}
