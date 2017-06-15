package de.beosign.beotracker.app;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.omnifaces.cdi.Eager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.beosign.beotracker.ticket.Ticket;
import de.beosign.beotracker.ticket.Ticket.Priority;
import de.beosign.beotracker.ticket.TicketService;
import de.beosign.beotracker.user.User;
import de.beosign.beotracker.user.UserService;

@ApplicationScoped
@Eager
public class ApplicationBean {
    private static final Logger log = LoggerFactory.getLogger(ApplicationBean.class);

    @Inject
    private TicketService ticketService;

    @Inject
    private UserService userService;

    @PostConstruct
    private void init() {
        log.info("Creating " + getClass().getName());

        Ticket ticket = new Ticket();
        ticket.setSummary("Summary1");
        ticket.setDescription("Desc1");
        ticket.setPriority(Priority.LOW);
        ticket = ticketService.create(ticket);

        log.info("Created ticket {}", ticket);

        System.out.println(ticketService.find(1));
        System.out.println(ticketService.findAll());

        initUsers();
    }

    private void initUsers() {
        if (userService.findAll().isEmpty()) {
            User user = new User("admin");
            userService.create(user);

            user = new User("florian");
            userService.create(user);
        }
    }

}
