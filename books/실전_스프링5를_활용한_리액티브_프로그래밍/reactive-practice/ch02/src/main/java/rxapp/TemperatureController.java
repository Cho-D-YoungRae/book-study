package rxapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import rx.Subscriber;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class TemperatureController {

    private static final Logger log = LoggerFactory.getLogger(TemperatureController.class);

    private final TemperatureSensor temperatureSensor;

    public TemperatureController(TemperatureSensor temperatureSensor) {
        this.temperatureSensor = temperatureSensor;
    }

    @GetMapping(value = "/temperature-stream")
    public SseEmitter events(HttpServletRequest request) {
        RxSeeEmitter emitter = new RxSeeEmitter();
        log.info("[{}] Rx SSE stream opened for client: {}",
                emitter.getSessionId(), request.getRemoteAddr());

        temperatureSensor.temperatureStream()
                .subscribe(emitter.getSubscriber());

        return emitter;
    }

    static class RxSeeEmitter extends SseEmitter {
        static final long SSE_SESSION_TIMEOUT = 30 * 60 * 1000L;
        private static final AtomicInteger sessionIdSequence = new AtomicInteger(0);

        private final int sessionId = sessionIdSequence.incrementAndGet();
        private final Subscriber<Temperature> subscriber;

        RxSeeEmitter() {
            super(SSE_SESSION_TIMEOUT);

            this.subscriber = new Subscriber<Temperature>() {
                @Override
                public void onNext(Temperature temperature) {
                    try {
                        RxSeeEmitter.this.send(temperature);
                        log.info("[{}] << {} ", sessionId, temperature.getValue());
                    } catch (IOException e) {
                        log.warn("[{}] Can not send event to SSE, closing subscription, message: {}",
                                sessionId, e.getMessage());
                        unsubscribe();
                    }
                }

                @Override
                public void onError(Throwable e) {
                    log.warn("[{}] Received sensor error: {}", sessionId, e.getMessage());
                }

                @Override
                public void onCompleted() {
                    log.warn("[{}] Stream completed", sessionId);
                }
            };

            onCompletion(() -> {
                log.info("[{}] SSE completed", sessionId);
                subscriber.unsubscribe();
            });
            onTimeout(() -> {
                log.info("[{}] SSE timeout", sessionId);
                subscriber.unsubscribe();
            });
        }

        Subscriber<Temperature> getSubscriber() {
            return subscriber;
        }

        int getSessionId() {
            return sessionId;
        }
    }
}
