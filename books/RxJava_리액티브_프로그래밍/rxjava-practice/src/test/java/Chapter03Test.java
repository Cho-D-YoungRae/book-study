import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

class Chapter03Test {

    // 3-2
    @Test
    void l04_syncSlowerSample() throws InterruptedException {
        Flowable.interval(1000L, TimeUnit.MILLISECONDS)
                // 데이터를 통지할 때의 시스템 시각을 출력
                .doOnNext(data -> System.out.println("emit: " + System.currentTimeMillis() + "ms: " + data))
                .subscribe(data -> Thread.sleep(2000L));

        Thread.sleep(5000L);
    }

    @Test
    void l05_SyncFasterSample() throws InterruptedException {
        Flowable.interval(1000L, TimeUnit.MILLISECONDS)
                // 데이터를 통지할 때의 시스템 시각을 출력
                .doOnNext(data -> System.out.println("emit: " + System.currentTimeMillis() + "ms: " + data))
                .subscribe(data -> Thread.sleep(500L));

        Thread.sleep(3000L);
    }

    @Test
    void l06_MainThreadSample() {
        System.out.println("start");

        Flowable.just(1, 2, 3)
                .subscribe(new ResourceSubscriber<Integer>() {
                    @Override
                    public void onNext(Integer integer) {
                        String threadName = Thread.currentThread().getName();
                        System.out.println(threadName + ": " + integer);
                    }

                    @Override
                    public void onComplete() {
                        String threadName = Thread.currentThread().getName();
                        System.out.println(threadName + ": 완료");
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }
                });

        System.out.println("end");
    }

    @Test
    void l07_NonMainThreadSample() throws InterruptedException {
        System.out.println("start");

        Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .subscribe(new ResourceSubscriber<Long>() {
                    @Override
                    public void onNext(Long aLong) {
                        String threadName = Thread.currentThread().getName();
                        System.out.println(threadName + ": " + aLong);
                    }

                    @Override
                    public void onComplete() {
                        String threadName = Thread.currentThread().getName();
                        System.out.println(threadName + ": 완료");
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }
                });

        System.out.println("end");

        // interval 메서드로 생성된 Flowable 은 메인스레드가 아니라 다른 스레드에서 작동하므로 기다려야함
        Thread.sleep(1000L);
    }

    @Test
    void l08_SubscribeOnSample() throws InterruptedException {
        Flowable.just(1, 2, 3, 4, 5)
                .subscribeOn(Schedulers.computation())
                .subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.single())
                .subscribe(data -> {
                    String threadName = Thread.currentThread().getName();
                    System.out.println(threadName + ": " + data);
                });

        Thread.sleep(500L);
    }

    @Test
    void l09_ObserveOnSample1() throws InterruptedException {
        Flowable<Long> flowable = Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .onBackpressureDrop();
        flowable
                .observeOn(Schedulers.computation(), false, 2)
                .subscribe(new ResourceSubscriber<Long>() {
                    @Override
                    public void onNext(Long aLong) {
                        // 무거운 처리 작업을 한다고 가정하고 1000밀리초를 기다린다
                        try {
                            Thread.sleep(1000L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            System.exit(1);
                        }

                        String threadName = Thread.currentThread().getName();
                        System.out.println(threadName + ": " + aLong);
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("Complete");
                    }

                    @Override
                    public void onError(Throwable t) {
                        System.out.println("error=" + t);
                    }
                });

        Thread.sleep(7000L);
    }

    @Test
    void l10_FlatMapSample() throws InterruptedException {
        Flowable<String> flowable = Flowable.just("A", "B", "C")
                // 받은 데이터로 Flowable 을 생성하고 이 Flowable 의 데이터를 통지
                .flatMap(data -> Flowable.just(data).delay(1000L, TimeUnit.MILLISECONDS));

        flowable.subscribe(data -> {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + ": " + data);
        });

        Thread.sleep(2000L);
    }

    @Test
    void l11_ConcatMapSample() throws InterruptedException {
        Flowable<String> flowable = Flowable.just("A", "B", "C")
                .concatMap(data -> Flowable.just(data).delay(1000L, TimeUnit.MILLISECONDS));

        flowable.subscribe(data -> {
            String threadName = Thread.currentThread().getName();
            String time = LocalTime.now().format(DateTimeFormatter.ofPattern("ss.SSS"));
            System.out.println(threadName + ": data=" + data + ", time=" + time);
        });

        Thread.sleep(4000L);
    }

    @Test
    void l12_ConcatMapEagerSample() throws InterruptedException {
        Flowable<String> flowable = Flowable.just("A", "B", "C")
                .concatMapEager(data -> Flowable.just(data).delay(1000L, TimeUnit.MILLISECONDS));

        flowable.subscribe(data -> {
            String threadName = Thread.currentThread().getName();
            String time =
                    LocalTime.now().format(DateTimeFormatter.ofPattern("ss.SSS"));
            System.out.println(threadName + ": data=" + data + ", time=" + time);
        });

        Thread.sleep(2000L);
    }

    @Test
    void l13_CounterSample() throws InterruptedException {
        final Counter counter = new Counter();

        Flowable.range(1, 10000)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.computation())
                .subscribe(
                        data -> counter.increment(),
                        error -> System.out.println("error = " + error),
                        () -> System.out.println("counter.get()=" + counter.get())
                );

        Flowable.range(1, 10000)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.computation())
                .subscribe(
                        data -> counter.increment(),
                        error -> System.out.println("error = " + error),
                        () -> System.out.println("counter.get()=" + counter.get())
                );

        Thread.sleep(1000L);
    }

    @Test
    void l15_CounterWithMergeSample() throws InterruptedException {
        final Counter counter = new Counter();

        Flowable<Integer> source1 = Flowable.range(1, 10000)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.computation());

        Flowable<Integer> source2 = Flowable.range(1, 10000)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.computation());

        Flowable.merge(source1, source2)
                .subscribe(
                        data -> counter.increment(),
                        error -> System.out.println("error=" + error),
                        () -> System.out.println("counter.get()=" + counter.get())
                );

        Thread.sleep(1000L);
    }

    // 3-3
    @Test
    void l16_retrySample() {
        Flowable<Integer> flowable = Flowable.<Integer>create(emitter -> {
                    System.out.println("Flowable 처리 시작");
                    for (int i = 0; i < 4; i++) {
                        if (i == 1) {
                            throw new RuntimeException("예외 발생");
                        }
                        emitter.onNext(i);
                    }

                    emitter.onComplete();
                    System.out.println("Flowable 처리 완료");
                }, BackpressureStrategy.BUFFER)
                .doOnSubscribe(
                        subscription -> System.out.println("flowable: doOnSubscribe")
                ).retry(2);

        flowable.subscribe(new Subscriber<Integer>() {

            @Override
            public void onSubscribe(Subscription subscription) {
                System.out.println("subscriber: onSubscribe");
                subscription.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer data) {
                System.out.println("data=" + data);
            }

            @Override
            public void onError(Throwable error) {
                System.out.println("에러=" + error);
            }

            @Override
            public void onComplete() {
                System.out.println("완료");
            }
        });
    }
}
