package de.beosign.beotracker.session;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.beosign.beotracker.session.TestProcessor.ResultComplete;

@SessionScoped
public class SessionScopedResultCompleteEventObserver implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(SessionScopedResultCompleteEventObserver.class);

    public void onResultComplete(@Observes(notifyObserver = Reception.ALWAYS) ResultComplete resultComplete) {
        log.debug("Result: {}", resultComplete.getResult());
        log.debug("FacesContext: {}", FacesContext.getCurrentInstance());
    }
}
