package de.beosign.beotracker.app;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import org.omnifaces.cdi.Eager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
@Eager
public class ApplicationBean {
    private static final Logger log = LoggerFactory.getLogger(ApplicationBean.class);

    @PostConstruct
    private void init() {
        log.info("Creating " + getClass().getName());
    }

}
