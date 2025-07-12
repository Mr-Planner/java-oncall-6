package oncall.view;

import oncall.enums.exception.ErrorCode;

public class OutputView {
    public static void printInputError() {
        System.out.println(ErrorCode.INVALID_INPUT.getErrorMessage());
    }

    /*
    ----------------------------------- 근무자 출력 메소드 -----------------------------------
    */
    // todo
    // 1. 월 : 날짜 수 / 휴일 파악
    // 2. 요일 : 주말 파악
    // 3. date++해가면서 요일 판단, 근무자 배치
    // 4. 연속시에 다음 근무자와 교체

    // 출력메소드
    // param : 근무자 순서 배열 / 월 / 요일 / 주말 시작일 cnt
    // 내부에서 그 월에 해당하는 휴일도 가져오기


}
