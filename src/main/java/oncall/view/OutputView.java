package oncall.view;

import oncall.enums.exception.ErrorCode;

import java.util.List;

public class OutputView {
    public static void printInputError() {
        System.out.println(ErrorCode.INVALID_INPUT.getErrorMessage());
    }

    /*
    ----------------------------------- 근무자 출력 메소드 -----------------------------------
    */
    // todo 출력메소드
    // param : 배치된 근무자 / 월 / 요일
    // 내부에서 그 월에 해당하는 휴일도 가져오기
    // assignWorkers메소드 보고 똑같이 출력
    public static void printMonthlyTable(List<String> workers, int month, String day) {

    }

}
