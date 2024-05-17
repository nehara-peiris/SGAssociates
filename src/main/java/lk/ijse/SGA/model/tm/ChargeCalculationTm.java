package lk.ijse.SGA.model.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChargeCalculationTm {
    private String chargeId;
    private String description;
    private double amount;
    private JFXButton btnRemove;
}
