package oncall.enums.model.date;

// Month 숫자 상수
public enum Month {
    JAN(1), FEB(2), MAR(3), APR(4), MAY(5), JUN(6), JUL(7), AUG(8), SEP(9), OCT(10), NOV(11), DEC(12);

    private final int month;

    Month(int month) {
        this.month = month;
    }

    public int getMonth() {
        return month;
    }
}
