package oncall.enums.model.worker;

public enum WorkType {
    WEEKDAY(0, "평일"), HOLIDAY(1, "휴일");

    private final int type;
    private final String dayType;

    WorkType(int type, String dayType) {
        this.type = type;
        this.dayType = dayType;
    }

    public int getType() {
        return type;
    }

    public String getDayType() {
        return dayType;
    }
}