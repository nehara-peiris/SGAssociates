package lk.ijse.SGA.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CaseCharge {
    private String CCPayId;
    private String caseId;
    private String chargeId;
    private Date date;
    private double amount;
    private String clientId;
}
