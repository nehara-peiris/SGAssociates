package lk.ijse.SGA.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Salary {
    private String salaryId;
    private String lawyerId;
    private Date date;
    private double totalSalary;

}
