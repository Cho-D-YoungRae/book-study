import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableEmitter;
import io.reactivex.rxjava3.core.FlowableOnSubscribe;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

class Chapter01Test {

    // 1.1
    @Test
    void helloWorldSample() {
        Flowable.just("Hello", "World")
                .subscribe(System.out::println);
    }

    // 1.3
    @Test
    void methodChainSample() {
        Flowable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .filter(it -> it % 2 == 0)
                .map(it -> it * 100)
                .subscribe(it -> System.out.println("data = " + it));
    }

    // 1.5
    @Test
    void l11_FlowableSample() throws Exception {
        // 인사말을 통지하는 Flowable 을 생성
        Flowable<String> flowable = Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<String> emitter) throws Throwable {
                String[] datas = {"Hello, World!", "안녕, RxJava!"};

                for (String data : datas) {
                    // 구독이 해지되면 처리를 중단
                    if (emitter.isCancelled()) {
                        return;
                    }
                    // 데이터를 통지
                    emitter.onNext(data);
                }
                // 완료를 통지
                emitter.onComplete();
            }
        }, BackpressureStrategy.BUFFER);// 초과한 데이터는 버퍼링

        flowable
                // Subscriber 처리를 개별 스레드에서 실행
                .observeOn(Schedulers.computation())
                // 구독
                .subscribe(new Subscriber<String>() {

                    private Subscription subscription;

                    @Override
                    public void onSubscribe(Subscription s) {
                        this.subscription = s;
                        this.subscription.request(1L);
                    }

                    @Override
                    public void onNext(String s) {
                        String threadName = Thread.currentThread().getName();
                        System.out.println(threadName + ": " + s);
                        this.subscription.request(1L);
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

        Thread.sleep(1000L);
    }

    @Test
    void l17_SubscriptionCancelSample() throws InterruptedException {
        Flowable.interval(100L, TimeUnit.MILLISECONDS)
                .subscribe(new Subscriber<Long>() {

                    private Subscription subscription;
                    private long startTime;

                    @Override
                    public void onSubscribe(Subscription s) {
                        this.subscription = s;
                        this.startTime = System.currentTimeMillis();
                        this.subscription.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        if ((System.currentTimeMillis() - startTime) > 500) {
                            subscription.cancel();
                            System.out.println("구독 해지");
                            return;
                        }

                        System.out.println("data=" + aLong);
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("완료");
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }
                });
        Thread.sleep(2000L);
    }

    /**
     * 1.6
     */
    @Test
    void l21_ViolatedReactiveStreamsSample() {
        Flowable.range(1, 3)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        System.out.println("onSubscribe: start");
                        s.request(Long.MAX_VALUE);
                        System.out.println("onSubscribe: end");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println(integer);
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("완료");
                    }

                    @Override
                    public void onError(Throwable t) {
                        System.out.println("에러=" + t);
                    }
                });
    }
}
