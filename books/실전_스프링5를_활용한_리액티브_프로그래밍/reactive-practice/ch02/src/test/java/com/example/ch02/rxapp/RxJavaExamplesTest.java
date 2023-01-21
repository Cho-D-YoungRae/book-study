package com.example.ch02.rxapp;

import org.junit.jupiter.api.Test;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

class RxJavaExamplesTest {

    @Test
    void simpleRxJavaWorkflow() {
        Observable<String> observable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        subscriber.onNext("Hello, reactive world!");
                        subscriber.onCompleted();
                    }
                }
        );
    }

    @Test
    void simpleRxJavaWorkflowWithLambdas() {
        Observable.create(subscriber -> {
            subscriber.onNext("Hello, reactive world");
            subscriber.onCompleted();

        }).subscribe(
                System.out::println,
                System.out::println,
                () -> System.out.println("Done!")
        );
    }

    @Test
    void createRxStreams() {
        Observable.just("1", "2", "3", "4");
        Observable.from(new String[]{"A", "B", "C"});
        Observable.from(List.of("a", "b", "c"));

        Observable<String> hello = Observable.fromCallable(() -> "Hello ");
        Future<String> future = Executors.newCachedThreadPool().submit(() -> "World");
        Observable<String> world = Observable.from(future);

        Observable.concat(hello, world, Observable.just("!"))
                .forEach(System.out::println);
    }

    @Test
    void timeBasedSequence() throws InterruptedException {
        Observable.interval(1L, TimeUnit.SECONDS)
                .subscribe(e -> System.out.println("Received: " + e));

        Thread.sleep(5000);
    }

    @Test
    void managingSubscription() throws InterruptedException {
        CountDownLatch externalSignal = new CountDownLatch(3);

        Subscription subscription = Observable
                .interval(1000, TimeUnit.MILLISECONDS)
                .subscribe(System.out::println);

        externalSignal.await(4500, TimeUnit.MILLISECONDS);
        subscription.unsubscribe();
    }

    @Test
    void simOperation() {
        Observable.zip(
                Observable.just("A", "B", "C"),
                Observable.just("1", "2", "3"),
                (x, y) -> x + y
        ).forEach(System.out::println);
    }
}
