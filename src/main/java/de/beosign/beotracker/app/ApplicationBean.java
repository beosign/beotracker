package de.beosign.beotracker.app;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.omnifaces.cdi.Eager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.beosign.beotracker.ticket.Ticket;
import de.beosign.beotracker.ticket.TicketService;

@ApplicationScoped
@Eager
public class ApplicationBean {
    private static final Logger log = LoggerFactory.getLogger(ApplicationBean.class);

    @Inject
    private TicketService ticketService;

    @PostConstruct
    private void init() {
        log.info("Creating " + getClass().getName());

        Ticket ticket = new Ticket();
        ticket.setSummary("Summary1");
        ticket = ticketService.create(ticket);

        log.info("Created ticket {}", ticket);

        System.out.println(ticketService.find(1));
        System.out.println(ticketService.findAll());
    }

}
