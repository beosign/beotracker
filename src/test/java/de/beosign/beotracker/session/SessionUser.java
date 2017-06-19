package de.beosign.beotracker.session;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.beosign.beotracker.user.User;

@Named
@SessionScoped
public class SessionUser implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(SessionUser.class);

    private static final AtomicInteger counter = new AtomicInteger(0);

    private boolean isInitialized;
    private User user;

    public SessionUser() {
        log.trace("Number of instances: {}", counter.incrementAndGet());
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    void setInitialized(boolean isInitialized) {
        this.isInitialized = isInitialized;
    }

    public User getUser() {
        return user;
    }

    void setUser(User user) {
        this.user = user;
    }

}
