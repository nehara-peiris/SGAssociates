package lk.ijse.SGA.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Deed {
    private String deedId;
    private String description;
    private String type;
    private Date date;
    private String lawyerId;
    private String clientId;
}

