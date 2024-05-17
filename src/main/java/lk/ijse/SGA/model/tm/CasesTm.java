package lk.ijse.SGA.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CasesTm {
    private String caseId;
    private String description;
    private Date date;
    private String type;
    private String clientId;
}
