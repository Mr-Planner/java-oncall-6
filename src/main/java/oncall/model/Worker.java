package oncall.model;

public class Worker {
    private String name;
    private final int[] workDayFlag = new int[]{0, 0};
    private final int maxLength = 5; // todo 하드코딩 말고 상수로

    // 이름 글자수 검증 메소드
    public Boolean checkNameLength(String name) {
        return name.length() < maxLength;
    }

    // 평/휴일 근무 이력 검증 메소드
    public Boolean checkTypeValid() {
        // Type을 Worker의 field로 두었으나 클래스 명으로 접근하도록 변경.
        return (workDayFlag[WorkType.WEEKDAY.getType()] == 1 && workDayFlag[WorkType.HOLIDAY.getType()] == 1);
    }
}
