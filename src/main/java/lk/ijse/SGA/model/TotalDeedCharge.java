package lk.ijse.SGA.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TotalDeedCharge {
    private String DCPayId;
    private String deedId;
    private Date date;
    private double TotalCharge;
}
