package chap07;

import lombok.Setter;

public class StubWeakPasswordChecker implements WeakPasswordChecker {

    @Setter
    private boolean weak;

    @Override
    public boolean checkPasswordWeak(String pw) {
        return weak;
    }
}
