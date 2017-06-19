package de.beosign.beotracker.user;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import de.beosign.beotracker.service.AbstractService;

@Stateless
public class UserService extends AbstractService<User> {

    public Optional<User> findByLogin(String login) {
        TypedQuery<User> query = em.createNamedQuery(User.QUERY_FIND_BY_LOGIN, User.class);

        query.setParameter("loginName", login);

        List<User> result = query.getResultList();

        if (result.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(result.get(0));
    }
}
