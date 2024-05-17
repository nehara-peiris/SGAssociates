package lk.ijse.SGA.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CalDeedCharge {
    private Payment payment;
    private List<DeedCharge> deedChargeList;
}
