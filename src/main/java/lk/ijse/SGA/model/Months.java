package lk.ijse.SGA.model;

import lombok.Getter;

@Getter
public enum Months {
    JANUARY(1), FEBRUARY(2), MARCH(3), APRIL(4), MAY(5), JUNE(6), JULY(7), AUGUST(8), SEPTEMBER(9), OCTOBER(10), NOVEMBER(11), DECEMBER(12);

    private int monthOrder;

    Months(int monthOrder) {
        this.monthOrder = monthOrder;
    }

}
