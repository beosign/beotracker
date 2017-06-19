package de.beosign.beotracker.session;

import java.util.Date;
import java.util.concurrent.Future;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
@Asynchronous
public class TestProcessor {
    private static final Logger log = LoggerFactory.getLogger(TestProcessor.class);

    @Inject
    private Event<ResultComplete> resultCompleteEvent;

    public Future<Integer> asyncAdd(int num1, int num2) {
        log.info("{}: Start running asyncAdd in thread {}", new Date(), Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            log.error("Error occured while trying to make this thread asleep.", e);
        }
        int result = num1 + num2;
        log.debug("{}: Addition calculation is finished: {}", new Date(), result);

        ResultComplete event = new ResultComplete(result);
        resultCompleteEvent.fire(event);
        return new AsyncResult<>(result);
    }

    public static class ResultComplete {
        private final Object result;

        public ResultComplete(Object result) {
            this.result = result;
        }

        public Object getResult() {
            return result;
        }

    }
}
