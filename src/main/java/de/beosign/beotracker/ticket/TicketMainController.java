package de.beosign.beotracker.ticket;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.event.Observes;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.beosign.beotracker.jsf.AbstractController;

@Named
@ViewScoped
public class TicketMainController extends AbstractController {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(TicketMainController.class);

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

    public void onTicketSelected(@Observes Ticket ticket) {
        log.debug("Selected ticket: {}", ticket);
        includeFile = "/WEB-INF/includes/ticket/ticket.xhtml";
    }

    public String getIncludeFile() {
        return includeFile;
    }

    public void setIncludeFile(String includeFile) {
        this.includeFile = includeFile;
    }

}
