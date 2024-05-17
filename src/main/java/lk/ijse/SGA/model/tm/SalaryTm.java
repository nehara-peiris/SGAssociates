package lk.ijse.SGA.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SalaryTm {
    private String lawyerId;
    private double totalSalary;
}
