package oncall.model;

public enum WorkType {
    WEEKDAY(0), HOLIDAY(1);

    private final int type;

    WorkType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
