package com.example.ch02.pubsubapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@RestController
public class TemperatureController {

    private static final long SSE_SESSION_TIMEOUT = 30 * 60 * 1000L;
    private static final Logger log = LoggerFactory.getLogger(TemperatureController.class);
    private final Set<SseEmitter> clients = new CopyOnWriteArraySet<>();

    @GetMapping("/temperature-stream")
    public SseEmitter events(HttpServletRequest request) {
        log.info("SSE stream opened for client: {}", request.getRemoteAddr());
        SseEmitter emitter = new SseEmitter(SSE_SESSION_TIMEOUT);
        clients.add(emitter);

        emitter.onTimeout(() -> clients.remove(emitter));
        emitter.onCompletion(() -> clients.remove(emitter));

        return emitter;
    }

    @Async
    @EventListener
    public void handleMessage(Temperature temperature) {
        log.info(String.format("Temperature: %4.2f C, active subscribers: %d",
                temperature.getValue(), clients.size()));
        List<SseEmitter> deadEmitters = new ArrayList<>();
        clients.forEach(emitter -> {
            try {
                Instant start = Instant.now();
                emitter.send(temperature, MediaType.APPLICATION_JSON);
                log.info("Sent to client, took: {}", Duration.between(start, Instant.now()));
            } catch (IOException e) {
                deadEmitters.add(emitter);
            }
        });

        deadEmitters.forEach(clients::remove);
    }

    @ExceptionHandler(value = AsyncRequestTimeoutException.class)
    public ModelAndView handleTimeout(HttpServletResponse rsp) throws IOException {
        if (!rsp.isCommitted()) {
            rsp.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        }
        return new ModelAndView();
    }
}
