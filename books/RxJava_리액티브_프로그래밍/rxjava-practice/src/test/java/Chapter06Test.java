import io.reactivex.rxjava3.core.Flowable;
import org.junit.jupiter.api.Test;

public class Chapter06Test {

    @Test
    void doOnNextSample() {
        Flowable.range(1, 5)
                .doOnNext(data -> System.out.println("--- 기존 데이터: " + data))
                .filter(data -> data % 2 == 0)
                .doOnNext(data -> System.out.println("------ filter 적용 후 데이터: " + data))
                .subscribe(new DebugSubscriber<>());
    }
}
