import io.reactivex.rxjava3.core.Flowable;
import org.junit.jupiter.api.Test;

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
}
