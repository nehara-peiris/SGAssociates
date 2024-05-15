package lk.ijse.SGA.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LawyerTm {
    private String lawyerId;
    private String name;
    private int yrsOfExp;
    private String address;
    private String email;
    private int contact;

}
