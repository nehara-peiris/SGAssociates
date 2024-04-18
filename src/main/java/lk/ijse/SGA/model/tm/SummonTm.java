package lk.ijse.SGA.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SummonTm {
    private String summonId;
    private String description;
    private String defendant;
    private String lawyerId;
}
