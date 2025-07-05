package oncall.view;

import java.util.Arrays;

import static camp.nextstep.edu.missionutils.Console.readLine;

public class InputView {
    String input;
    int month;
    String day;

    /*
    ----------------------------------- getter / setter -----------------------------------
     */

    public int getMonth() {
        return month;
    }

    public String getDay() {
        return day;
    }

    public String getInput() {
        return input;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(String day) {
        this.day = day;
    }

    /*
    ----------------------------------- 출력 메소드 -----------------------------------
     */

    public static void printMonthAndDayInput() {
        System.out.print("비상 근무를 배정할 월과 시작 요일을 입력하세요> ");
    }

    public static void printWeekdayWorkerInput() {
        System.out.print("평일 비상 근무 순번대로 사원 닉네임을 입력하세요> ");
    }

    public static void printHolidayWorkerInput() {
        System.out.print("휴일 비상 근무 순번대로 사원 닉네임을 입력하세요> ");
    }

    /*
    ----------------------------------- 입력 메소드 -----------------------------------
     */

    public void input() {
        input = readLine();
    }

    /*
    ----------------------------------- 입력값 파싱 및 체크 메소드 -----------------------------------
     */

    // 월 / 요일 입력 파싱체크
    public boolean checkMonthAndDayInput(String input) {
        // 일단 input이 2개인지 개수 체크
        // month의 type 체크
        if (! checkMonthAndDayInputLength(input)) {
            OutputView.printInputError();
            return false;
        }

        // month 검사, 예외 발생 시 return false
        try {
            setMonth(Integer.parseInt(input.split(",")[0]));
            checkMonthIsInteger(month);
        } catch (NumberFormatException e) {
            OutputView.printInputError();
            return false;
        }

        setDay(input.split(",")[1].trim());

        return true;
    }


    public boolean checkMonthAndDayInputLength(String input) {

        return input.split(",").length == 2;
    }

    // month가 Int인지 판별
    public void checkMonthIsInteger(Object month) {
        if (! (month instanceof Integer)) {
            throw new NumberFormatException();
        }
    }

    // 근무자 입력 파싱 메소드
    public String[] parseWorkersInput(String input) {
        String[] names;
        // todo stream과 람다는 일단 구현 시도해보고 추후 gpt로 리팩토링 시도할 것 (람다는 경험이 중요)
        names = Arrays.stream(input.split(",")).map(String::trim).toArray(String[]::new);

        return names;
    }



}
