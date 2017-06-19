package de.beosign.beotracker.session;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.ContextNotActiveException;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.beosign.beotracker.session.TestProcessor.ResultComplete;

@ApplicationScoped
public class ApplicationScopedResultCompleteEventObserver implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(ApplicationScopedResultCompleteEventObserver.class);

    @Inject
    private SessionUser sessionUser;

    public void onResultComplete(@Observes(notifyObserver = Reception.ALWAYS) ResultComplete resultComplete) {
        log.debug("Result: {}", resultComplete.getResult());
        log.debug("FacesContext: {}", FacesContext.getCurrentInstance());

        try {
            log.debug("Session User: {}", sessionUser.getUser().getLoginName());
        } catch (ContextNotActiveException e) {
            log.info("No session user available: {}", e.getMessage());
        }
    }

}
