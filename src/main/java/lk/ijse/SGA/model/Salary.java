package lk.ijse.SGA.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Salary {
    private String lawyerId;
    private double totalSalary;

}
