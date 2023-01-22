package com.example.ch04;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.Disposable;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.IntStream;

class ReactorEssentialsTest {

    private static Logger log = LoggerFactory.getLogger(ReactorEssentialsTest.class);

    @Test
    @Disabled
    void endlessStream() {
        Flux.interval(Duration.ofSeconds(1))
                .doOnNext(System.out::println)
                .collectList()
                .block();
    }

    @Test
    void createFlux() {
        Flux<String> stream1 = Flux.just("Hello", "world");
        Flux<Integer> stream2 = Flux.fromArray(new Integer[]{1, 2, 3});
        Flux<Integer> stream3 = Flux.range(1, 500);

        Flux<Object> emptyStream = Flux.empty();
        Flux<Object> streamWithError = Flux.error(new RuntimeException("error"));
    }

    @Test
    void createMono() {
        Mono<String> mono1 = Mono.just("One");
        Mono<Object> mono2 = Mono.justOrEmpty(null);
        Mono<Object> mono3 = Mono.justOrEmpty(Optional.empty());

        Mono<String> mono4 = Mono.fromCallable(this::httpRequest);

        StepVerifier.create(mono4)
                .expectErrorMessage("IO error")
                .verify();
    }

    private String httpRequest() {
        System.out.println("Making HTTP request");
        throw new RuntimeException("IO error");
    }

    @Test
    void simpleSubscribe() {
        Flux.just("A", "B", "C")
                .subscribe(
                        System.out::println,
                        errorIgnored -> {
                        },
                        () -> System.out.println("Done")
                );
    }

    @Test
    void managingDemand() {
        Flux.range(1, 100)
                .subscribe(
                        data -> System.out.println("onNext: " + data),
                        err -> { /* ignore */ },
                        () -> System.out.println("onComplete"),
                        subscription -> {
                            subscription.request(4);
                            subscription.cancel();
                        }
                );
    }

    @Test
    void managingSubscription() throws InterruptedException {
        Disposable disposable = Flux.interval(Duration.ofMillis(50))
                .doOnCancel(() -> System.out.println("Cancelled"))
                .subscribe(
                        data -> System.out.println("onNext: " + data)
                );
        Thread.sleep(200);
        disposable.dispose();
    }

    @Test
    void subscribingOnStream() throws InterruptedException {
        Subscriber<String> subscriber = new Subscriber<>() {
            volatile Subscription s;

            @Override
            public void onSubscribe(Subscription s) {
                this.s = s;
                System.out.println("Initial request for 1 element");
                this.s.request(1);
            }

            @Override
            public void onNext(String s) {
                System.out.println("onNext: " + s);
                System.out.println("Request 1 more element");
                this.s.request(1);
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }

            @Override
            public void onError(Throwable t) {
            }
        };

        Flux.just("Hello", "World", "!")
                .subscribe(subscriber);

        Thread.sleep(100);
    }

    @Test
    void indexElements() {
        Flux.range(2018, 5)
                .timestamp()
                .index()
                .subscribe(
                        e -> System.out.println(String.format(
                                "index: %s, ts: %s, value: %s",
                                e.getT1(),
                                Instant.ofEpochMilli(e.getT2().getT1()),
                                e.getT2().getT2()
                        ))
                );
    }

    @Test
    void startStopStreamProcessing() throws InterruptedException {
        Mono<Long> startCommand = Mono.delay(Duration.ofSeconds(1));
        Mono<Long> stopCommand = Mono.delay(Duration.ofSeconds(3));
        Flux<Long> dataStream = Flux.interval(Duration.ofMillis(100));

        dataStream
                .skipUntilOther(startCommand)
                .takeUntilOther(stopCommand)
                .subscribe(System.out::println);

        Thread.sleep(4000);
    }

    @Test
    void findingIfThereIsEvenElements() {
        Flux.just(3, 5, 7, 9, 11, 15, 16, 17)
                .any(e -> e % 2 == 0)
                .subscribe(hasEvens -> System.out.println("Has evens: " + hasEvens));
    }

    @Test
    void runningAverageExample() {
        int bucketSize = 5;
        Flux.range(1, 500)
                .index()
                .scan(
                        new int[bucketSize],
                        (acc, elem) -> {
                            acc[(int) (elem.getT1() % bucketSize)] = elem.getT2();
                            return acc;
                        }
                )
                .skip(bucketSize)
                .map(arr -> Arrays.stream(arr).sum() * 1.0 / bucketSize)
                .subscribe(av -> System.out.println("Running average: " + av));
    }

    @Test
    void combineLatestOperator() {
        Flux.concat(
                Flux.range(1, 3),
                Flux.range(4, 2),
                Flux.range(6, 5)
        ).subscribe(e -> System.out.println("onNext: " + e));
    }

    @Test
    void windowByPredicate() {
        Flux<Flux<Integer>> fluxFlux = Flux.range(101, 20)
                .windowUntil(this::isPrime, true);

        fluxFlux.subscribe(window -> window
                .collectList()
                .subscribe(e -> System.out.println("window: " + e)));
    }

    private boolean isPrime(int number) {
        return number > 2
                && IntStream.rangeClosed(2, (int) Math.sqrt(number))
                .noneMatch(n -> (number % n == 0));
    }

    @Test
    void groupByExample() {
        Flux.range(1, 7)
                .groupBy(e -> e % 2 == 0 ? "Even" : "Odd")
                .subscribe(groupFlux -> groupFlux
                        .scan(
                                new ArrayList<>(),
                                (list, elem) -> {
                                    if (list.size() > 1) {
                                        list.remove(0);
                                    }
                                    list.add(elem);
                                    return list;
                                }
                        )
                        .filter(list -> !list.isEmpty())
                        .subscribe(data -> System.out.println(groupFlux.key() + ":" + data))
                );
    }

    @Test
    void flatMapExample() throws InterruptedException {
        Flux.just("user-1", "user-2", "user-3")
                .flatMap(u -> requestBooks(u)
                        .map(b -> u + "/" + b))
                .subscribe(r -> log.info("onNext: {}", r));

        Thread.sleep(1000);
    }

    private Flux<String> requestBooks(String user) {
        return Flux.range(1, new Random().nextInt(3) + 1)
                .delayElements(Duration.ofMillis(3))
                .map(i -> "book-" + i);
    }


    @Test
    void handlingErrors() throws InterruptedException {
        Flux.just("user-1")
                .flatMap(user ->
                        recommendedBooks(user)
                                .retryBackoff(5, Duration.ofMillis(100))
                                .timeout(Duration.ofSeconds(3))
                                .onErrorResume(e -> Flux.just("The Martian"))
                        )
                .subscribe(
                        b -> log.info("onNext: {}", b),
                        e -> log.warn("onError: {}", e.getMessage()),
                        () -> log.info("onComplete")
                );
        Thread.sleep(5000);
    }

    private Flux<String> recommendedBooks(String userId) {
        return Flux.defer(() -> {
            if (new Random().nextInt(10) < 7) {
                return Flux.<String>error(new RuntimeException("Conn error"))
                        .delaySequence(Duration.ofMillis(100));
            } else {
                return Flux.just("Blue Mars", "The Expanse")
                        .delayElements(Duration.ofMillis(50));
            }
        }).doOnSubscribe(s -> log.info("Request for {}", userId));
    }

    @Test
    void coldPublisher() {
        Flux<String> coldPublisher = Flux.defer(() -> {
            log.info("Generating new items");
            return Flux.just(UUID.randomUUID().toString());
        });

        log.info("No data was generated so far");
        coldPublisher.subscribe(e -> log.info("onNext: {}", e));
        coldPublisher.subscribe(e -> log.info("onNext: {}", e));
        log.info("Data was generated twice for two subscribers");
    }

    @Test
    void connectExample() throws InterruptedException {
        Flux<Integer> source = Flux.range(0, 3)
                .doOnSubscribe(s ->
                        log.info("new subscription for the cold publisher"));

        ConnectableFlux<Integer> conn = source.publish();

        conn.subscribe(e -> log.info("[Subscriber 1] onNext: {}", e));
        conn.subscribe(e -> log.info("[Subscriber 2] onNext: {}", e));

        log.info("all subscribers are ready, connecting");
        conn.connect();
    }

    @Test
    public void cachingExample() throws InterruptedException {
        Flux<Integer> source = Flux.range(0, 2)
                .doOnSubscribe(s ->
                        log.info("new subscription for the cold publisher"));

        Flux<Integer> cachedSource = source.cache(Duration.ofSeconds(1));

        cachedSource.subscribe(e -> log.info("[S 1] onNext: {}", e));
        cachedSource.subscribe(e -> log.info("[S 2] onNext: {}", e));

        Thread.sleep(1200);

        cachedSource.subscribe(e -> log.info("[S 3] onNext: {}", e));
    }

    @Test
    public void replayExample() throws InterruptedException {
        Flux<Integer> source = Flux.range(0, 5)
                .delayElements(Duration.ofMillis(100))
                .doOnSubscribe(s ->
                        log.info("new subscription for the cold publisher"));

        Flux<Integer> cachedSource = source.share();

        cachedSource.subscribe(e -> log.info("[S 1] onNext: {}", e));
        Thread.sleep(400);
        cachedSource.subscribe(e -> log.info("[S 2] onNext: {}", e));

        Thread.sleep(1000);
    }

    @Test
    void transformExample() {
        Function<Flux<String>, Flux<String>> logUserInfo =
                stream -> stream
                        .index()
                        .doOnNext(tp ->
                                log.info("[{}] User: {}", tp.getT1(), tp.getT2()))
                        .map(Tuple2::getT2);

        Flux.range(1000, 3)
                .map(i -> "user-" + i)
                .transform(logUserInfo)
                .subscribe(e -> log.info("onNext: {}", e));
    }

    @Test
    void composeExample() {
        Function<Flux<String>, Flux<String>> logUserInfo = (stream) -> {
            if (new Random().nextBoolean()) {
                return stream
                        .doOnNext(e -> log.info("[path A] User: {}", e));
            } else {
                return stream
                        .doOnNext(e -> log.info("[path B] User: {}", e));
            }
        };

        Flux<String> publisher = Flux.just("1", "2")
                .compose(logUserInfo);

        publisher.subscribe();
        publisher.subscribe();
    }
}
