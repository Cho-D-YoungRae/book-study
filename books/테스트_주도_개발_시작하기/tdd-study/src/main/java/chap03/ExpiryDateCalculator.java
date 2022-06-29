package chap03;

import java.time.LocalDate;
import java.time.YearMonth;

import static java.util.Objects.nonNull;

public class ExpiryDateCalculator {

    public static final int SUBSCRIPTION_FEE = 10_000;

    public LocalDate calculateExpiryDate(PayData payData) {
        int addedMonth = payData.getPayAmount() == SUBSCRIPTION_FEE * 10 ?
                12 : payData.getPayAmount() / SUBSCRIPTION_FEE;
        if (nonNull(payData.getFirstBillingDate())) {
            return expiryDateUsingFirstBillingDate(payData, addedMonth);
        } else {
            return payData.getBillingDate().plusMonths(addedMonth);
        }
    }

    private LocalDate expiryDateUsingFirstBillingDate(PayData payData, int addedMonth) {
        LocalDate candidateExp = payData.getBillingDate().plusMonths(addedMonth);
        if (!isSameDayOfMonth(payData.getFirstBillingDate(), candidateExp)) {
            final int dayLenOfCandiMon = lastDayOfMonth(candidateExp);
            final int dayOfFirstBilling = payData.getFirstBillingDate().getDayOfMonth();
            if (dayLenOfCandiMon < dayOfFirstBilling) {
                return candidateExp.withDayOfMonth(dayLenOfCandiMon);
            }
            return candidateExp.withDayOfMonth(dayOfFirstBilling);
        } else {
            return candidateExp;
        }
    }

    private int lastDayOfMonth(LocalDate candidateExp) {
        return YearMonth.from(candidateExp).lengthOfMonth();
    }

    private boolean isSameDayOfMonth(LocalDate date1, LocalDate date2) {
        return date1.getDayOfMonth() == date2.getDayOfMonth();
    }
}
