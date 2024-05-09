package lk.ijse.SGA.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TotalDeedChargeTm {
    private String DCPayId;
    private String deedId;
    private Date date;
    private double TotalCharge;
}
