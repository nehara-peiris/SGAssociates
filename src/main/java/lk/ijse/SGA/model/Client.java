package lk.ijse.SGA.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Client {

    private String clientId;
    private String name;
    private String address;
    private String email;
    private int contact;
    private String lawyerId;

}
