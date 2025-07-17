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

    public static void printMonthlyTable(List<String> totalWorkersOrder, int month, String day) {

        for (int date = 1, order = 0; date <= Month.getDaysInMonth(month); date++, order++) {
            // 공휴일 여부
            boolean isHoliday = Month.getHolidays(month).contains(date);
            String worker = totalWorkersOrder.get(order);

            String sentence = makeSentence(worker, month, day, date, isHoliday);
            System.out.println(sentence);

            day = Day.getNextDay(day);
        }
    }

    public static String makeSentence(String worker, int month, String day, int date, boolean isHoliday) {
        String holiday = isHoliday ? "(휴일)" : "";

        return month + "월 " + date + "일 " + day + holiday + " " + worker;
    }
}
