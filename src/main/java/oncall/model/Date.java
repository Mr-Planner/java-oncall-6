package oncall.model;

import oncall.enums.model.date.Day;
import oncall.enums.model.date.Month;
import oncall.view.OutputView;

/*
todo Date 클래스의 활용에 대하여...
1. 최종 출력은 그냥 입력에 따른 출력만 하면된다. 굳이 '월'에 대한 근무 배치 기록을 유지할 필요가 없다
2. 각 월의 날 수와 휴일을 관리하고 저장
... 앞으로 마저 더 생각
* */
public class Date {
    private int month;
    private String day;

    public Date() {
    }

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

