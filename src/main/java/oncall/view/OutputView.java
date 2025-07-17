package oncall.view;

import oncall.enums.exception.ErrorCode;
import oncall.enums.model.date.Day;
import oncall.enums.model.date.Month;

import java.util.List;

public class OutputView {
    public static void printInputError() {
        System.out.println(ErrorCode.INVALID_INPUT.getErrorMessage());
    }

    /*
    ----------------------------------- 근무자 출력 메소드 -----------------------------------
    */
    // todo 출력메소드 solid 원칙 적용
    // param : 배치된 근무자 / 월 / 요일
    // 내부에서 그 월에 해당하는 휴일도 가져오기
    // assignWorkers메소드 보고 똑같이 출력

    // totalWorkersOrder를 차례대로 출력...
    public static void printMonthlyTable(List<String> totalWorkersOrder, int month, String day) {
        String sentence;
        String holiday = "(휴일)";
        String worker;

        for (int date = 1, order = 0; date <= Month.getDaysInMonth(month); date++, order++) {

            // 주말 출력
            if (day.equals(Day.SAT.getDay()) || day.equals(Day.SUN.getDay()))
            {
                worker = totalWorkersOrder.get(order);
                sentence = month+"월 " + date +"일 " + day + " " + worker;

                System.out.println(sentence);
                day = Day.getNextDay(day);
                continue;
            }

            // 공휴일 출력
            if (Month.getHolidays(month).contains(date)) {
                worker = totalWorkersOrder.get(order);
                sentence = month+"월 " + date +"일 " + day + holiday + " " + worker;

                System.out.println(sentence);
                day = Day.getNextDay(day);
                continue;
            }

            worker = totalWorkersOrder.get(order);
            sentence = month+"월 " + date +"일 " + day + " " + worker;

            System.out.println(sentence);
            day = Day.getNextDay(day);
        }
    }
}
