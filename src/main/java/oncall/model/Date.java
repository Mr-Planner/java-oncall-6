package oncall.model;

import oncall.exception.ErrorCode;

import static camp.nextstep.edu.missionutils.Console.readLine;

public class Date {
    private String input;
    private int month;
    private int date;
    private String day;

    public Date() {
    }

    public Date(int month, int date, String day) {
        this.month = month;
        this.date = date;
        this.day = day;
    }

    // 월 / 요일 저장 메소드
    public void SaveStartMonthDay() {
        do {
            input = readLine();
            month = Integer.parseInt(input.split(",")[0]);
            day = input.split(",")[1].trim();
        } while (! (CheckValidMonth(month) && CheckValidDay(day)));

        // System.out.println("통과");
    }

    // 유효성 검사 (month의 range)
    public boolean CheckValidMonth(int month) {

        try {
            if(month >= Month.JAN.getMonth() && month <= Month.DEC.getMonth()) {
                return true;
            }
            //System.out.println("월 검사 통과 X");

        } catch (IllegalArgumentException e) {
            System.out.println(ErrorCode.INVALID_INPUT.getErrorMessage());
        }
        return false;
    }

    public boolean CheckValidDay(String day) {
        int dayCnt = Day.values().length;

        try {
            for (int cnt = 0; cnt < dayCnt; cnt++) {
                if(day.equals(Day.values()[cnt].getDay())) {
                    return true;
                }
            }
            //System.out.println("날짜 검사 통과 X");

        } catch (IllegalArgumentException e) {
            // 예외 정보 출력
            System.out.println(ErrorCode.INVALID_INPUT.getErrorMessage());
        }
        return false;
    }
}

// 휴일 정보, 일 수 정보 저장

// Month 숫자 상수
enum Month {
    JAN(1), FEB(2), MAR(3), APR(4), MAY(5), JUN(6), JUL(7), AUG(8), SEP(9), OCT(10), NOV(11), DEC(12);

    private final int month;

    Month(int month) {
        this.month = month;
    }

    public int getMonth() {
        return month;
    }
}

enum Day {
    MON("월"), TUE("화"), WED("수"), THR("목"),FRI("금"),SAT("토"), SUN("일");

    private final String day;

    Day(String day) {
        this.day = day;
    }

    public String getDay() {
        return day;
    }
}