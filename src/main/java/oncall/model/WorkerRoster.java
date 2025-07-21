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

    /*
    ----------------------------------- Getter / Setter -----------------------------------
    */

    // equals 이후에 객체생성 했으므로, weekday or holiday 상관 X
    public int getWorkersCnt() {

        return weekdayWorkers.size();
    }

    public String getWeekdayWorker(int index) {

        return weekdayWorkers.get(index).getName();
    }

    public String getHolidayWorker(int index) {

        return holidayWorkers.get(index).getName();
    }

    /*
    ----------------------------------- 비즈니스 로직 메소드 -----------------------------------
    */

    // 모든 근무자가 평일 / 휴일에 있는지 판단
    public static boolean checkAllTypeWorkerExists(String[] weekdayWorkers, String[] holidayWorkers) {
        Set<String> weekdayWorkerSet = new HashSet<>(Arrays.asList(weekdayWorkers));
        Set<String> holidayWorkerSet = new HashSet<>(Arrays.asList(holidayWorkers));

        return weekdayWorkerSet.equals(holidayWorkerSet);
    }

    public int nextWorkerOrder(int order) {

        return (order+1) % getWorkersCnt();
    }

    public int previousWorkerOrder(int order) {

        return (order-1) % getWorkersCnt();
    }

}
