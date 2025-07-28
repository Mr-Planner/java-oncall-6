package oncall.model;

import oncall.enums.model.date.Day;
import oncall.enums.model.date.Month;
import oncall.view.OutputView;

public class Date {
    private int month;
    private String day;

    public Date(int month, String day) {
        this.month = month;
        this.day = day;
    }

    public String getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    // static 메소드로 언제바꿔야 하는지 파악
    // 유효성 검사 (month의 range)
    public static boolean checkValidMonth(int month) {

        if(month >= Month.JAN.getMonth() && month <= Month.DEC.getMonth()) {
            return true;
        }

        OutputView.printInputError();

        return false;
    }

    public static boolean checkValidDay(String day) {
        int dayCnt = Day.values().length;

        for (int cnt = 0; cnt < dayCnt; cnt++) {
            if(day.equals(Day.values()[cnt].getDay())) {
                return true;
            }
        }

        OutputView.printInputError();
        return false;
    }
}

// 휴일 정보, 일 수 정보 저장

