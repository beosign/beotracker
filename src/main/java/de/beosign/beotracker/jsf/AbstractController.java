package de.beosign.beotracker.jsf;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractController implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(AbstractController.class);

    @PostConstruct
    protected void init() {
        log.trace("init");
    }

    @PreDestroy
    protected void preDestroy() {
        log.trace("destroy");
    }

}
