package de.beosign.beotracker.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public interface Service<T> {
    public static final String FIND_BY_ID = "findById";

    EntityManager getEntityManager();

    Class<T> getEntityClass();

    default T find(int id) {
        TypedQuery<T> q = getEntityManager().createNamedQuery(getFindByQueryName(), getEntityClass());
        q.setParameter("id", id);

        List<T> result = q.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    default T create(T t) {
        getEntityManager().persist(t);
        getEntityManager().flush();

        return t;
    }

    default T update(T t) {
        t = getEntityManager().merge(t);
        getEntityManager().flush();
        return t;
    }

    default void remove(T t) {
        getEntityManager().remove(t);
        getEntityManager().flush();
    }

    default String getFindByQueryName() {
        return getEntityClass().getSimpleName() + ".findById";
    }
}
