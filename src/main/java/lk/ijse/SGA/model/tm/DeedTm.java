package lk.ijse.SGA.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeedTm {
    private String description;
    private String type;
    private Date date;
    private String lawyerId;
    private String clientId;
}
