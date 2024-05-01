package lk.ijse.SGA.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AssignedWork {
    private String lawyerId;
    private String name;
    private Date date;
    private String caseId;
    private String clientId;
    private String deedId;
}
