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

    public static int getOrdinal (String day) {
        for (Day d : Day.values()) {
            if (d.getDay().equals(day)) {
                return d.ordinal();
            }
        }
        throw new IllegalArgumentException();
    }

    // 다음날 반환 메소드
    public static String getNextDay (String day) {
        int nextIndex = (getOrdinal(day)+1) % Day.values().length;

        return Day.values()[nextIndex].getDay();
    }


}