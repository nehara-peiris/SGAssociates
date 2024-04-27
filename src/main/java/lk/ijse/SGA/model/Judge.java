package lk.ijse.SGA.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Judge {
    private String judgeId;
    private String name;
    private String courtId;
    private int yrsOfExp;

}

