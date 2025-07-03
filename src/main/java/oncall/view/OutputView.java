package oncall.view;

import oncall.enums.exception.ErrorCode;

public class OutputView {
    public static void printInputError() {
        System.out.println(ErrorCode.INVALID_INPUT.getErrorMessage());
    }
}
