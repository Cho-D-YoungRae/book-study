import kotlin.test.Test

class Chapter03Test {

    @Test
    fun practice1() {
        /**
         * if 문을 식으로 사용할 떄는 양 가지가 모두 있어야 함
         */
        val s = "10/3"
        val i = s.indexOf("/")

        val result = if (i >= 0) {
            val a = s.substring(0, i).toInt()
            val b = s.substring(i + 1).toInt()
            (a / b).toString()
        } else ""

        println(result)
    }

    @Test
    fun practice2() {
        /**
         * return 문은 존재하지 않는 값을 뜻하는 Nothing 이라는 특별한 타입의 값으로 간주
         * Unit 과 Nothing 의 차이
         * -> Nothing 과 달리 Unit 타입에는 한 가지 인스턴스가 존재하는데, 이 인스턴스는 보통 유용한 값이 없다는 사실을 표현.
         * -> 반면 Nothing 은 아예 값이 없다는 사실을 표현
         */
        println(renamePackage("foo.bar.old", "new"))
    }

    private fun renamePackage(fullName: String, newName: String): String {
        val i = fullName.lastIndexOf(".")
        val prefix = if (i >= 0) fullName.substring(0, i + 1) else return newName
        return prefix + newName
    }

    @Test
    fun practice3() {
        println(50 in 10..99)
        println("def" in "abc".."xyz")
        println("zzz" in "abc".."xyz")
        for (i in 5 until 10) {
            println("5 until 10 >> $i")
        }
        for (i in 10 downTo 5) {
            println("10 downTo 5 >> $i")
        }
        for (i in 1..10 step 3) {
            println("1..10 step 3 >> $i")
        }

        /**
         * 일반적으로 범위는 동적으로 할당되는 객체이기 때문에 비교 대신 범위를 사용하면 약간의 부가 비용이 든다.
         * 하지만 컴파일러는 꼭 필요할 때만 실제 객체를 만들어내기 위해 노력한다.
         * 예를 들어 비교연산을 진행할 수 있으면 범위 객체를 생성하지 않고 비교연산을 진행한다.
         */
    }

    @Test
    fun practice4() {
        val n1 = 5
        println(
            if (n1 in 0..9) '0' + n1
            else if (n1 in 10..15) 'A' + n1 - 10
            else '?'
        )

        println(
            when {
                n1 in 0..9 -> '0' + n1
                n1 in 10..15 -> 'A' + n1 - 10
                else -> '?'
            }
        )
        /**
         * Java switch VS Kotlin when
         * - switch 에서는 주어진 식의 여러 가지 값 중에 하나만 선택 VS when 에서는 임의의 조건을 검사
         * - when 은 조건을 만족하는 가지만 실행하고 절대 폴스루를 하지 않는다.
         */

        val n2 = 5
        val max = 100
        println(
            when(n2) {
                0 -> "Zero"
                1, 2, 3 -> "Small"
                in 4..9 -> "Medium"
                in 10..max -> "Large"
                !in Int.MIN_VALUE until 0 -> "Negative"
                else -> "Huge"
            }
        )
    }


    @Test
    fun practice5() {
        /**
         * 일반 변수와 달리 루프 변수에는 val 이나 var 를 붙이지 않고 자동으로 불변 값이 된다.
         */
        val arr = IntArray(10) { it * it }
        var sum = 0
        for (x in arr) {
            sum += x
        }
        println(sum)
    }

    @Test
    fun practice6() {
        /**
         * 재귀 함수는 비재귀 버전과 비교해보면, 성능 차원에서 약간의 부가 비용이 발생하고 스택 오버플로가 발생할 가능성이 있다.
         * 하지만 코틀린에서는 함수에 tailrec 을 붙이면 컴파일러가 재귀 함수를 비재귀적인 코드로 자동으로 변환해준다.
         * 그 결과 재귀 함수의 간결함과 비재귀루프의 성능만을 취할 수 있다.
         */
        val binIdx = binIndexOf(
            3,
            IntArray(10) { it }
        )
        println(binIdx)
    }

    private tailrec fun binIndexOf(
        x: Int,
        arr: IntArray,
        from: Int = 0,
        to: Int = arr.size
    ): Int {
        if (from == to)
            return -1

        val midIdx = (from + to - 1) / 2
        val mid = arr[midIdx]
        return when {
            mid < x -> binIndexOf(x, arr, midIdx + 1, to)
            mid > x -> binIndexOf(x, arr, from, midIdx)
            else -> midIdx
        }
    }
}