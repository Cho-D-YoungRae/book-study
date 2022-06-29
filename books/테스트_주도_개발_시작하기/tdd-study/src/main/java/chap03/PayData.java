package chap03;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PayData {

    LocalDate firstBillingDate;

    LocalDate billingDate;

    int payAmount;
}
