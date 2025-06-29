package oncall.model;

// 최소 / 최대 근무자 수
public enum WorkerCount {
    MIN(5), MAX(35);

    private final int count;

    WorkerCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }
}
