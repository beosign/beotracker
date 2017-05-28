package de.beosign.beotracker.ticket;

import javax.ejb.Stateless;

import de.beosign.beotracker.service.AbstractService;

@Stateless
public class TicketService extends AbstractService<Ticket> {

    // @Override
    // public Ticket find(int id) {
    // TypedQuery<Ticket> q = em.createNamedQuery(Ticket.FIND_BY_ID, Ticket.class);
    //
    // q.setParameter("id", id);
    //
    // List<Ticket> result = q.getResultList();
    //
    // return result.isEmpty() ? null : result.get(0);
    // }

}
