package de.beosign.beotracker.session;

import java.io.Serializable;

import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.event.TransactionPhase;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.beosign.beotracker.jsf.AbstractController;
import de.beosign.beotracker.session.TestProcessor.ResultComplete;
import de.beosign.beotracker.user.User;
import de.beosign.beotracker.user.UserService;

@Named
@ViewScoped
public class SessionTestController extends AbstractController implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger log = LoggerFactory.getLogger(SessionTestController.class);

    @Inject
    private TestProcessor testProcessor;

    @Inject
    private UserService userService;

    @Inject
    private SessionUser sessionUser;

    public SessionUser login(String login) {
        User user = userService.findByLogin(login).orElse(null);
        if (user != null) {
            sessionUser.setInitialized(true);
            sessionUser.setUser(user);
            log.info("Session user {} initialized", user);
        } else {
            log.info("No user '{}' found", login);
        }
        return sessionUser;
    }

    public void startProcess() {
        log.debug("Starting process");
        testProcessor.asyncAdd(2, 3);
        log.debug("Process started");
    }

    public void onResultComplete(@Observes(notifyObserver = Reception.ALWAYS, during = TransactionPhase.AFTER_COMPLETION) ResultComplete resultComplete) {
        log.debug("Result: {}", resultComplete.getResult());
        log.debug("FacesContext: {}", FacesContext.getCurrentInstance());
    }

}
