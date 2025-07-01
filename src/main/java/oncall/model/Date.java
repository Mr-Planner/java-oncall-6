package oncall.model;

import oncall.exception.ErrorCode;

import static camp.nextstep.edu.missionutils.Console.readLine;

/*
todo Date 클래스의 활용에 대하여...
1. 최종 출력은 그냥 입력에 따른 출력만 하면된다. 굳이 '월'에 대한 근무 배치 기록을 유지할 필요가 없다
2. 각 월의 날 수와 휴일을 관리하고 저장
* */
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
    // todo 입력받고 parsing은 컨트롤러가 -> 그 결과물을 model에 넘긴다
    public void saveStartMonthDay() {
        do {
            input = readLine();
            month = Integer.parseInt(input.split(",")[0]);
            day = input.split(",")[1].trim();
        } while (! (checkValidMonth(month) && checkValidDay(day)));

        // System.out.println("통과");
    }

    // 유효성 검사 (month의 range)
    public boolean checkValidMonth(int month) {

        try {
            if(month >= Month.JAN.getMonth() && month <= Month.DEC.getMonth()) {
                return true;
            }
            //System.out.println("월 검사 통과 X");

        } catch (IllegalArgumentException e) {
            // todo ErrorCde 클래스로 접근해서 출력하지 말고, View의 메소드를 사용해서 출력하도록
            System.out.println(ErrorCode.INVALID_INPUT.getErrorMessage());
        }
        return false;
    }

    public boolean checkValidDay(String day) {
        int dayCnt = Day.values().length;
        // todo try catch 대신 return true/false 구분 (exception이 아님, exception으로 처리하고 싶으면 customException 클래스)
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

// todo enum관리 패키지와 그 안에서 디렉토리 나누어서 관리 할 것

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