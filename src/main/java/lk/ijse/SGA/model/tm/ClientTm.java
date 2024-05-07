package lk.ijse.SGA.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClientTm {
    private String name;
    private String address;
    private String email;
    private int contact;
    private String LawyerID;
}
