package lk.ijse.SGA.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TotalCaseCharge {
    private String CCPayId;
    private String caseId;
    private Date date;
    private double TotalCharge;
}
