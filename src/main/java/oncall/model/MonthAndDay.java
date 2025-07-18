package oncall.model;

// todo MonthAndDay 대신 Date로 대체해보기
public class MonthAndDay {
    private int month;
    private String day;

    public MonthAndDay(int month, String day) {
        this.month = month;
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public String getDay() {
        return day;
    }
}
