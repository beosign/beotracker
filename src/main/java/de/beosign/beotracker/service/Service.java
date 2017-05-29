package de.beosign.beotracker.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public interface Service<T> {
    EntityManager getEntityManager();

    Class<T> getEntityClass();

    default List<? extends T> findAll() {
        TypedQuery<T> q = getEntityManager().createQuery("SELECT t FROM " + getEntityName() + " t", getEntityClass());

        return q.getResultList();
    }

    default T find(int id) {
        TypedQuery<T> q = getEntityManager().createQuery("SELECT t FROM " + getEntityName() + " t WHERE t.id = :id", getEntityClass());
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

    /**
     * Returns the entity name associated with this class' type parameter <code>&lt;T&gt;</code>.
     * 
     * @return the entity name associated with <code>&lt;T&gt;</code>.
     */
    default String getEntityName() {
        return getEntityManager()
                .getEntityManagerFactory()
                .getMetamodel()
                .getEntities()
                .stream()
                .filter(et -> et.getJavaType().equals(getEntityClass()))
                .map(et -> et.getName())
                .findFirst()
                .orElse(null);
    }
}
