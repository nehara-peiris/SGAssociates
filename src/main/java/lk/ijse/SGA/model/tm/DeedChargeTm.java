package lk.ijse.SGA.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeedChargeTm {
    private String DCPayId;
    private String deedId;
    private String lawyerId;
    private String chargeId;
    private Date date;
    private double amount;
    private String clientId;
}
