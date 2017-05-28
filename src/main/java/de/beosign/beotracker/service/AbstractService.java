package de.beosign.beotracker.service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@TransactionAttribute(TransactionAttributeType.REQUIRED)
public abstract class AbstractService<T> implements Service<T> {
    @PersistenceContext(unitName = "BeoTracker")
    protected EntityManager em;

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public Class<T> getEntityClass() {
        Type type = getClass().getGenericSuperclass();

        while (!(type instanceof ParameterizedType) || ((ParameterizedType) type).getRawType() != AbstractService.class) {
            if (type instanceof ParameterizedType) {
                type = ((Class<?>) ((ParameterizedType) type).getRawType()).getGenericSuperclass();
            } else {
                type = ((Class<?>) type).getGenericSuperclass();
            }
        }

        return (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
    }

    /**
     * Overridden so a transaction is started.
     */
    @Override
    public T create(T t) {
        return Service.super.create(t);
    }
}
