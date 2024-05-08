package lk.ijse.SGA.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeedCharge {
    private String DCPayId;
    private String deedId;
    private String lawyerId;
    private String chargeId;
    private Date date;
    private double amount;
    private String clientId;
}
