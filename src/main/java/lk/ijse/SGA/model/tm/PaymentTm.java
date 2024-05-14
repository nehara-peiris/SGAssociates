package lk.ijse.SGA.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTm {
    private String paymentId;
    private String lawyerId;
    private Date date;
    private double amount;
    private double bonus;
}
