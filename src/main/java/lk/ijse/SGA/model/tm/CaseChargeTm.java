package lk.ijse.SGA.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CaseChargeTm {
    private String CCPayId;
    private String caseId;
    private String chargeId;
    private Date date;
    private double amount;
    private String clientId;
}
