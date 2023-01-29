package com.example.ch04;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.context.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

class ThreadLocalProblemShowcaseTest {

    @Test
    void shouldFailDueToDifferentThread() {
        ThreadLocal<Map<Object, Object>> threadLocal = new ThreadLocal<>();
        threadLocal.set(new HashMap<>());

        Assertions.assertThatThrownBy(() ->
                Flux.range(0, 10)
                        .doOnNext(k -> threadLocal.get().put(k, new Random(k).nextGaussian()))
                        .publishOn(Schedulers.parallel())
                        .map(k -> threadLocal.get().get(k))
                        .blockLast()
        ).isInstanceOf(NullPointerException.class);
    }

    @Test
    void showcaseContext() {
        printCurrentContext("top")
                .subscriberContext(Context.of("top", "context"))
                .flatMap(__ -> printCurrentContext("middle"))
                .subscriberContext(Context.of("middle", "context"))
                .flatMap(__ -> printCurrentContext("bottom"))
                .subscriberContext(Context.of("bottom", "context"))
                .flatMap(__ -> printCurrentContext("initial"))
                .block();
    }

    void print(String id, Context context) {
        System.out.println(id + " {");
        System.out.print("  ");
        System.out.println(context);
        System.out.println("}");
        System.out.println();
    }

    Mono<Context> printCurrentContext(String id) {
        return Mono
                .subscriberContext()
                .doOnNext(context -> print(id, context));
    }
}
