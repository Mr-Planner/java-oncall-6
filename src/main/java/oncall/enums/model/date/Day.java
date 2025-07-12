package oncall.enums.model.date;

public enum Day {
    MON("월"), TUE("화"), WED("수"), THR("목"), FRI("금"), SAT("토"), SUN("일");

    private final String day;

    Day(String day) {
        this.day = day;
    }

    public String getDay() {
        return day;
    }
}