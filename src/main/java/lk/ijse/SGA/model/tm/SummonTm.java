package lk.ijse.SGA.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SummonTm {
    private String summonId;
    private String description;
    private String defendant;
    private String lawyerId;
    private Date date;
}
