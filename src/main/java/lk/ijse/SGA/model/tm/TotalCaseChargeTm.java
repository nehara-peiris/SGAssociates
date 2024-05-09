package lk.ijse.SGA.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TotalCaseChargeTm {
    private String CCPayId;
    private String caseId;
    private Date date;
    private double TotalCharge;
}
