package oncall.view;

public class InputView {

    public static void printMonthAndDayInput() {
        System.out.print("비상 근무를 배정할 월과 시작 요일을 입력하세요> ");
    }

    public static void printWeekDayWorkerInput() {
        System.out.print("평일 비상 근무 순번대로 사원 닉네임을 입력하세요> ");
    }

    public static void printHolidayWorkerInput() {
        System.out.print("휴일 비상 근무 순번대로 사원 닉네임을 입력하세요> ");
    }
}
