package oncall.exception;

public enum ErrorCode {
    INVALID_INPUT("[ERROR] 유효하지 않은 입력 값입니다. 다시 입력해주세요");

    private final String errorMessage;

    ErrorCode(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
