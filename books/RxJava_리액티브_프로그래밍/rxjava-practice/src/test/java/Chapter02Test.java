import io.reactivex.rxjava3.functions.Action;
import org.junit.jupiter.api.Test;

class Chapter02Test {

    @Test
    void differenceOfThisSampleTest() throws Throwable {
        DifferenceOfThisSample target = new DifferenceOfThisSample();
        target.execute();
    }

    static class DifferenceOfThisSample {
        // 익명 클래스와 람다식의 this 출력
        public void execute() throws Throwable {
            // 익명 클래스
            Action anonymous = new Action() {
                @Override
                public void run() throws Throwable {
                    System.out.println("익명 클래스의 this: " + this);
                }
            };

            // 람다식
            Action lambda = () -> System.out.println("람다식의 this: " + this);

            anonymous.run();
            lambda.run();
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName();
        }
    }
}
