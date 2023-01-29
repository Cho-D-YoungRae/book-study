package com.example.reactorkotlin

import mu.KotlinLogging
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import reactor.core.publisher.BaseSubscriber
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration
import java.time.Instant
import java.util.*
import java.util.stream.IntStream
import kotlin.math.sqrt

class ReactorPracticeTest {

    private val log = KotlinLogging.logger {}

    /**
     * 무한 스트림은 전체 스트림 생성을 완료하지 않고도 각 요소를 변형하고 소비할 수 있으므로
     * 메모리 부족을 야기하지 않음
     */

    @Test
    @Disabled
    fun practice1() {
        Flux.range(1, 10)
            .repeat()   // 소스 스트림이 끝난 후 소스 스트림을 다시 구독 -> 1 ~ 100 결과 구독하고 onComplete 신호 수신 후 1 ~ 100 수신
            .collectList()  // 끝없는 스트림을 생성하기 때문에 OOM 발생 가능
            .block()
    }

    /**
     * Flux와 Mono의 차이는 메서드 시그니처에만 있는 것이 아님.
     * 버퍼 중복과 값비싼 동기화 작업을 생략하기 때문에 Mono가 더 효율적
     */

    /**
     * Mono는 클라이언트에게 작업이 완료됐음을 알리는 데 사용할 수 있습니다.
     * 그 경우 Mono<Void> 유형을 반환하고 처리가 완료되면 onComplete() 신호를 보내거나 실패한 경우 onError() 신호를 보냅니다.
     * 데이터를 반환하지는 않지만, 이후 연산을 위한 알림을 보내는 용도로 사용할 수 있습니다.
     */

    /**
     * Mono와 Flux는 분리된 것이 아니라 서로 쉽게 변환 가능
     * Flux<T>.colectList() -> Mono<List<T>>
     * Mono<T>.flux() -> Flux<T>
     */
    @Test
    fun practice2() {
        Mono.from(Flux.from(Mono.just(1)))
    }

    @Test
    fun practice3() {
        Flux.just("Hello", "world")
        Flux.fromArray(arrayOf(1, 2, 3))
        Flux.fromIterable(listOf(9, 8, 7))
        Flux.range(2010, 9)
    }

    @Test
    fun practice4() {
        val stream1 = Mono.just("One")
        val stream2: Mono<String> = Mono.justOrEmpty(null)
        val stream3: Mono<String> = Mono.justOrEmpty(Optional.empty())
    }

    @Test
    fun practice5() {
        val empty: Flux<String> = Flux.empty()  // 빈 인스턴스 생성
        val never: Flux<String> = Flux.never()  // 완료 메시지와 데이터, 오류에 대해서도 신호를 보내지 않는 스트림
        val error: Mono<String> =
            Mono.error(RuntimeException("Unknown id"))    // 구독할 떄 각 구독자의 onError(..) 를 통해 항상 오류를 전파하는 시퀀스 생성
    }

    @Test
    fun practice6() {
        /**
         * defer는 구독하는 순간에 행동을 결정하는 시퀀스 생성
         * 결과적으로 서로 다른 구독자에 대해 다른 데이터를 생성할 수 있음
         */
        val mono = requestUserData("session")
        val deferedMono = requestUserDataDefer("defer session")

        deferedMono.subscribe()
        mono.subscribe()
    }

    private fun requestUserDataDefer(sessionId: String) =
        Mono.defer {
            if (isValidSession(sessionId)) Mono.fromCallable { requestUser(sessionId) }
            else Mono.error(RuntimeException("Invalid user session"))
        }

    private fun requestUserData(sessionId: String) =
        if (isValidSession(sessionId)) Mono.fromCallable { requestUser(sessionId) }
        else Mono.error(RuntimeException("Invalid user session"))

    private fun isValidSession(sessionId: String): Boolean {
        log.info { "sessionId=$sessionId" }
        return sessionId in arrayOf("defer session", "session")
    }

    private fun requestUser(sessionId: String) = log.info { "Request session id = $sessionId" }

    @Test
    fun practice7() {
        Flux.just("A", "B", "C")
            .subscribe(
                { data -> log.info { "onNext: $data" } },
                { err -> {} },
                { log.info { "onComplete" } }
            )
    }

    @Test
    fun practice8() {
        /**
         * 구독자가 스트림이 끝나기 전에 구독을 취소했으므로 onComplete 신호를 수신하지 않음
         */
        // deprecated -> subscribe(consumer, errorConsumer, completeConsumer, subscriptionConsumer)
        Flux.range(1, 100)
            .subscribe(
                { data -> log.info { "onNext: $data" } },
                { err -> {} },
                { log.info { "onComplete" } },
                { subscription ->
                    run {
                        subscription.request(10)
                        subscription.cancel()
                    }
                }
            )
    }

    @Test
    fun practice9() {
        val disposable = Flux.interval(Duration.ofSeconds(1))
            .subscribe { log.info { "onNext:$it" } }

        Thread.sleep(1000 * 3)
        disposable.dispose()
    }

    @Test
    fun practice10() {
        /**
         * Subscriber 를 직접 구현해서 쓰기 보다는
         * practice11 과 같이 BaseSubscriber<T> 를 사용하자
         */
        val subscriber = object : Subscriber<String> {
            @Volatile
            lateinit var subscription: Subscription

            override fun onSubscribe(s: Subscription?) {
                subscription = s!!
                log.info { "initial request for 1 element" }
                subscription.request(1)
            }

            override fun onNext(t: String?) {
                log.info { "onNext: $t" }
                log.info { "requesting 1 more element" }
                subscription.request(1)
            }

            override fun onComplete() {
                log.info { "onComplete" }
            }

            override fun onError(t: Throwable?) {
                log.info { "onError: ${t?.message}" }
            }
        }

        Flux.just("Hello", "world", "!")
            .subscribe(subscriber)
    }

    @Test
    fun practice11() {
        val subscriber = object : BaseSubscriber<String>() {
            override fun hookOnSubscribe(subscription: Subscription) {
                log.info { "initial request for 1 element" }
                request(1)
            }

            override fun hookOnNext(value: String) {
                log.info { "onNext: $value" }
                log.info { "requesting 1 more element" }
                request(1)
            }
        }

        Flux.just("Hello", "world", "!")
            .subscribe(subscriber)
    }

    @Test
    fun practice12() {
        Flux.range(2018, 5)
            .timestamp()
            .index()
            .subscribe {
                log.info { log.info { "index: ${it.t1}, ts: ${Instant.ofEpochMilli(it.t2.t1)}, value: ${it.t2.t2}" } }
            }
    }

    @Test
    fun practice13() {
        val list = listOf(1, 6, 2, 8, 3, 1, 5, 9, 4)
        Flux.fromIterable(list)
            .collectList()
            .subscribe { log.info { it } }

        Flux.fromIterable(list)
            .collectSortedList()
            .subscribe { log.info { it } }

        Flux.fromIterable(list)
            .collectSortedList(Comparator.reverseOrder())
            .subscribe { log.info { it } }
    }

    /**
     * Flux.distinct() 는 입력 시퀀스의 중복을 제외하고 전달합니다.
     * 그러나 이 메서드는 모든 원소를 추적하므로 신중하게 사용해야 합니다(특히 스트림 원소의 개수가 매우 많은 경우).
     * distinct 메서드의 중복 체크를 위한 사용자 지정 알고리즘을 지정할 수 있습니다.
     * 따라서 distinct 연산자의 자원 사용을 수동으로 최적화하는 것도 가능합니다.
     * Flux.distinctUntilChanged() 연산자는 이러한 제한이 없기 때문에
     * 무한 스트림에 중단 없는 행에 나타나는 중복을 제거하는 데 사용할 수 있습니다.
     */

    @Test
    fun practice14() {
        val list = listOf(3, 5, 7, 9, 11, 13, 15, 17, 19)
        Flux.fromIterable(list)
            .any { it % 2 == 0 }
            .subscribe { hasEvens -> log.info { "Has evens: $hasEvens" } }

        Flux.fromIterable(list)
            .hasElement(3)
            .subscribe { hasEvens -> log.info { "Has evens: $hasEvens" } }
    }

    /**
     * sort 연산자는 백그라운드에서 원소를 정렬한 다음, 원래 시퀀스가 완료되면 정렬한 시퀀스를 출력으로 내보냅니다.
     */

    @Test
    fun practice15() {
        // reduce() : 최종 결과 하나만 출력으로 내보냄
        Flux.range(1, 5)
            .reduce(0) { acc, elem -> acc + elem }
            .subscribe { log.info { "Reduce result: $it" } }

        // scan() : 중간 과정도 모두 출력
        Flux.range(1, 5)
            .scan(0) { acc, elem -> acc + elem }
            .subscribe { log.info { "Scan result: $it" } }
    }

    @Test
    fun practice16() {
        // then, thenMany, thenEmpty -> 상위 스트림 처리가 완료되는 즉시 새 스트림을 기동하는데 사용 가능
        Flux.just(1, 2, 3)
            .thenMany(Flux.just(4, 5))
            .subscribe { log.info { "onNext: $it" } }
    }

    @Test
    fun practice17() {
        /**
         * concat 연산자는 수신된 원소를 모두 연결해 다운스트림으로 전달합니다.
         * 연산자가 두 개의 스트림을 연결하면 처음에는 첫 번째 스트림의 모든 원소를 소비한 후 다시 보내고
         * 두 번째 스트림에 대해 동일한 작업을 수행합니다.
         */
        log.info { ">>> concat" }
        Flux.concat(
            Flux.just(1, 2, 3),
            Flux.just(4, 5),
            Flux.just(6, 7, 8, 9)
        ).subscribe { log.info { "onNext: $it" } }
        /**
         * merge 연산자는 업스트림 시퀀스의 데이터를 하나의 다운스트림 시퀀스로 병합합니다.
         * concat 연산자와 달리 업스트림 소스는 각각 별개로 구독됩니다(동시에).
         * concat -> 순서대로 결합, merge -> 도착한 대로 결합
         */
        log.info { ">>> merge" }
        Flux.merge(
            Flux.just(1, 2, 3),
            Flux.just(4, 5),
            Flux.just(6, 7, 8, 9)
        ).subscribe { log.info { "onNext: $it" } }
        /**
         * zip 연산자는 모든 업스트림을 구독하고 모든 소스가 하나의 원소를 내보낼 떄까지 대기한 다음,
         * 수신된 원소를 출력원소로 결합합니다.
         */
        log.info { ">>> zip" }
        Flux.zip(
            Flux.just(1, 2, 3),
            Flux.just(4, 5),
            Flux.just(6, 7, 8, 9)
        ).subscribe { log.info { "onNext: $it" } }
        /**
         * combineLatest 연산자는 zip 연산자와 비슷하게 작동합니다.
         * 그러나 최소한 하나의 업스트림 소스가 값을 내면 바로 새 값을 생성합니다.
         */
    }

    @Test
    fun practice18() {
        /**
         * buffer 연산자는 하나의 원소만 가진 많은 작은 요청 대신에
         * 컬렉션을 이용해 요청 횟수를 줄이는 것이 바람직한 경우 사용 가능
         */
        log.info { ">>> buffer" }
        Flux.range(1, 13)
            .buffer(4)
            .subscribe { log.info { "onNext: $it" } }

        log.info { ">>> window" }
        Flux.range(101, 20)
            .windowUntil(this::isPrime, true)
            .subscribe { window ->
                window
                    .collectList()
                    .subscribe { log.info { "window: $it" } }
            }
        /**
         * buffer 와 window 연산자는 꽤 동작이 유사합니다.
         * 그러나 buffer 는 버퍼가 닫힐 때만 컬렉션을 내보내는 반면,
         * window 연산자는 원소가 도착하자마자 이벤트를 전파하므로 더빨리 반응하고 복잡한 워크플로를 구현할 수 있습니다.
         */
    }

    private fun isPrime(number: Int) =
        number > 2 &&
                IntStream.rangeClosed(2, sqrt(number.toDouble()).toInt())
                    .noneMatch { number % it == 0 }

    @Test
    fun practice19() {
        Flux.range(1, 10)
            .groupBy { if (it % 2 == 0) "Even" else "Odd" }
            .subscribe { groupFlux ->
                groupFlux
                    .scan(LinkedList<Int>()) { list, elem ->
                        list.add(elem)
                        if (list.size > 2) {
                            list.removeFirst()
                        }
                        list
                    }
                    .filter { list -> !list.isEmpty() }
                    .subscribe { data ->
                        log.info { "${groupFlux.key()} $data" }
                    }
            }
    }

    private fun requestBooks(user: String): Flux<String> =
        Flux.range(1, 3)
            .delayElements(Duration.ofMillis(3))
            .map { "book-$it" }


    @Test
    fun practice20() {
        /**
         * flatMap 연산자는 논리적으로 map과 flatten의 2가지 작업으로 구성 (리액터의 merge 연산자와 비슷)
         * map 파트는 들어오는 각 원소를 리액티브 스트림 T -> Flux<R>으로 변환하고,
         * flatten 파트는 생성된 모든 리액티브 시퀀스를 R 타입의 원소를 통과시키는 새로운 리액티브 시퀀스로 병합
         * 하나의 하위 스트림으로 병합
         *
         * flatMap 연산자의 다양한 변형을 제공 -> flatMapSequential, concatMap
         * - 연산자가 내부 스트림을 하나씩 구독하는지 여부
         *  - flatMap 및 flatMapSequential 연산자는 하나씩 구독
         *  - concatMap 은 다음 하위 스트림을 생성하고 구독하기 전에 스트림 내부 처리가 완료되기를 기다림
         * - 연산자가 생성된 원소의 순서를 유지하는지 여부
         *  - concatMap 은 원본과 동일한 순서를 유지
         *  - flatMapSequential 은 큐에 넣어 순서를 역순으로 유지
         *  - flatMap 은 원래 순서를 유지하지 않음
         * - 연산자가 다른 하위 스트림의 원소를 끼워 넣을 수 있는지 여부
         *  - flatMap 은 허용
         *  - flatMapSequential 및 concatMap 은 허용하지 않음
         */
        log.info { ">>> flatMap" }
        Flux.just("user-1", "user-2", "user-3")
            .flatMap { u -> requestBooks(u)
                .map { b -> "$u/$b" }
            }
            .subscribe { r -> log.info { "onNext: $r" }}
        Thread.sleep(1000)

        log.info { ">>> flatMapSequential" }
        Flux.just("user-1", "user-2", "user-3")
            .flatMapSequential { u -> requestBooks(u)
                .map { b -> "$u/$b" }
            }
            .subscribe { r -> log.info { "onNext: $r" }}
        Thread.sleep(1000)

        log.info { ">>> concatMap" }
        Flux.just("user-1", "user-2", "user-3")
            .concatMap { u -> requestBooks(u)
                .map { b -> "$u/$b" }
            }
            .subscribe { r -> log.info { "onNext: $r" }}
        Thread.sleep(1000)
    }

    @Test
    fun practice21() {
        /**
         * sample, sampleTimeout : 특정 기간 내 가장 최근에 본 값을 주기적으로 출력 가능
         */
        Flux.range(1, 100)
            .delayElements(Duration.ofMillis(1))
            .sample(Duration.ofMillis(20))
            .subscribe { log.info { "onNext: $it" }}
        Thread.sleep(1000)
    }

    /**
     * 리액티브 시퀀스를 블로킹 구조로 변화하기 위한 API 제공.
     * 리액티브 애플리케이션에서 블로킹 처리를 해서는 안 되지만, 상위 API에서 필요로 하는 경우도 있음.
     * - toIterable: 리액티브 Flux 를 블로킹 Iterable 로 변환.
     * - toStream: 리액티브 Flux 를 블로킹 스트림 API 로 변환.
     * - blockFirst: 업스트림이 첫 번쨰 값을 보내거나 완료될 때까지 현재 스레드를 차단
     * - blockLast: 업스트림이 마지막 값을 보내거나 완료될 때까지 현재 스레드를 차단.
     *
     */

    @Test
    fun practice22() {
        Flux.just(1, 2, 3)
            .concatWith(Flux.error(RuntimeException("Conn error")))
            .doOnEach { log.info { "signal: $it" } }
            .subscribe()
    }

    @Test
    fun practice23() {
        Flux.range(1, 3)
            .doOnNext { log.info { "data : $it" } }
            .materialize()
            .doOnNext { log.info { "signal: $it" } }
            .dematerialize<Int>()
            .collectList()
            .subscribe { log.info { "result: $it" } }
    }

    /**
     * Context 는 스트림을 따라 전달되는 인터페이스
     * -> 나중에 런타임 단계에서 필요한 컨텍스트 정보에 액세스 할 수 있도록 하는 것
     *
     * Map 인터페이스와 비슷한 메서드를 가진 Context 가 꼭 필요한가?
     * Context 는 본질적은로 Immutable 객체라서 새로운 요소를 추가하면 Context 는 새로운 인스턴스로 변경
     * 이는 멀티스레딩 액세스 모델을 고려해 이루어짐
     */
}