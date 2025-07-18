package oncall.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WorkerRoster {
    private final List<Worker> weekdayWorkers;
    private final List<Worker> holidayWorkers;

    public WorkerRoster(List<Worker> weekdayWorkers, List<Worker> holidayWorkers) {
        this.weekdayWorkers = weekdayWorkers;
        this.holidayWorkers = holidayWorkers;
    }

    // 모든 근무자가 평일 / 휴일에 있는지 판단
    public static boolean checkAllTypeWorkerExists(String[] weekdayWorkers, String[] holidayWorkers) {
        Set<String> weekdayWorkerSet = new HashSet<>(Arrays.asList(weekdayWorkers));
        Set<String> holidayWorkerSet = new HashSet<>(Arrays.asList(holidayWorkers));

        return weekdayWorkerSet.equals(holidayWorkerSet);
    }

    // todo String[]두개 받고 둘이 모든 이름이 같은지 체크 (equals메소드 사용)

    public String nextWeekdayWorker() {


    }

    public String nextHolidayWorker() {


    }
}
