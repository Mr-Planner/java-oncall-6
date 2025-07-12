package oncall.enums.model.date;

import java.util.Arrays;
import java.util.List;


// 월 / 날짜수 / 휴일
public enum Month {
    JAN(1,31, List.of(1)),
    FEB(2,28,List.of()),
    MAR(3, 31, List.of(1)),
    APR(4, 30, List.of()),
    MAY(5, 31, List.of(5)),
    JUN(6, 30, List.of(6)),
    JUL(7, 31, List.of()),
    AUG(8, 31, List.of(15)),
    SEP(9, 30, List.of()),
    OCT(10, 31, List.of(3,9)),
    NOV(11, 30, List.of()),
    DEC(12, 31, List.of(25));

    private final int month;
    private final int daysInMonth;
    private List<Integer> holidays;

    Month(int month, int daysInMonth, List<Integer> holidays) {
        this.month = month;
        this.daysInMonth = daysInMonth;
        this.holidays = holidays;
    }

    public int getMonth() {
        return month;
    }

    public static int getDaysInMonth(int month) {

        // todo stream문법 잘 모르겠어서 일단 for each
        for (Month m : Month.values()) {
            if(m.getMonth() == month) {
                return m.daysInMonth;
            }
        }
        throw new IllegalArgumentException("Invalid month number: " + month);
    }

    public static List<Integer> getHolidays(int month) {
        for (Month m : Month.values()) {
            if(m.getMonth() == month) {
                return m.holidays;
            }
        }
        throw new IllegalArgumentException("Invalid month number: " + month);
    }


}
