package lk.ijse.SGA.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TotalSalaryTm {
    private String lawyerId;
    private Date date;
    private double totSalary;

}


//select lawyerId, SUM(amount+bonus) from salary group by lawyerId;