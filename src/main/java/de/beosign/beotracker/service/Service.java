package de.beosign.beotracker.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import de.beosign.beotracker.ticket.Ticket;

public interface Service<T> {
    EntityManager getEntityManager();

    Class<T> getEntityClass();

    default T find(int id) {
        TypedQuery<T> q = getEntityManager().createNamedQuery(Ticket.FIND_BY_ID, getEntityClass());
        q.setParameter("id", id);

        List<T> result = q.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    default T create(T t) {
        getEntityManager().persist(t);
        getEntityManager().flush();

        return t;
    }

    default String getFindByQueryName() {
        return getEntityClass().getSimpleName() + ".findById";
    }
}
