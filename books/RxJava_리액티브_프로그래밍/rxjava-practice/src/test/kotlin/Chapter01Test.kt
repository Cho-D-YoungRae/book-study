import io.reactivex.rxjava3.core.Flowable
import kotlin.test.Test

internal class Chapter01Test {

    @Test
    fun helloWorldSample() {
        Flowable.just("Hello", "World")
            .subscribe { data -> println(data) }
    }
}