package chap02;

import static java.util.Objects.isNull;

public class PasswordStrengthMeter {

    public PasswordStrength meter(String s) {
        if (isNull(s) || s.isEmpty()) {
            return PasswordStrength.INVALID;
        }
        int metCounts = getMetCriteriaCounts(s);

        if (metCounts <= 1) {
            return PasswordStrength.WEAK;
        }
        if (metCounts == 2) {
            return PasswordStrength.NORMAL;
        }

        return PasswordStrength.STRONG;
    }

    private int getMetCriteriaCounts(String s) {
        int metCounts = 0;
        if (s.length() >= 8) {
            metCounts++;
        }
        if (meetsContainingNumberCriteria(s)) {
            metCounts++;
        }
        if (meetsContainingUppercaseCriteria(s)) {
            metCounts++;
        }
        return metCounts;
    }

    private boolean meetsContainingNumberCriteria(String s) {
        return s.chars()
                .filter(Character::isDigit)
                .findAny()
                .isPresent();
    }

    private boolean meetsContainingUppercaseCriteria(String s) {
        return s.chars()
                .filter(Character::isUpperCase)
                .findAny()
                .isPresent();
    }
}
