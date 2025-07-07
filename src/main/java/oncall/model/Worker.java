package oncall.model;

import oncall.enums.model.worker.WorkType;

public class Worker {
    private final String name;
    // todo [checkpoint] invalidWorkDayFlagTest에서 saveHolidayWorkers에서 테스트시에 checkWorkDaysValid에서 NPE발생, boolean으로 변경함
    private final boolean[] workDayFlag = new boolean[2];

    public Worker(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // 평/휴일 근무 flag 배치 설정
    public void setWorkDayFlag(int flag) {
        int size = workDayFlag.length;
        if (flag >= size || flag < 0) {
            throw new IndexOutOfBoundsException("Workday type index out of range");
        }
        workDayFlag[flag] = true;
    }

    // 평/휴일 근무 이력 검증 메소드
    public Boolean checkWorkDaysValid() {
        // Type을 Worker의 field로 두었으나 클래스 명으로 접근하도록 변경.
        return (workDayFlag[WorkType.WEEKDAY.getType()] && workDayFlag[WorkType.HOLIDAY.getType()]);
    }
}
