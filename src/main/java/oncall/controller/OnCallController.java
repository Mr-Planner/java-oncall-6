package oncall.controller;


import oncall.enums.model.date.Day;
import oncall.enums.model.date.Month;
import oncall.model.Date;
import oncall.enums.model.worker.WorkType;
import oncall.model.MonthAndDay;
import oncall.model.Worker;
import oncall.model.WorkerRoster;
import oncall.view.InputView;
import oncall.view.OutputView;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class OnCallController {
    // 필드
    private final InputView inputView = new InputView();
    private Date date;

    private WorkerRoster workerRoster;
    private final List<String> totalWorkersOrder = new ArrayList<>();
    private final int[] reassignFlag = new int[2];

    // 프로그램 실행 흐름
    public void run() {
        // 입력
        monthAndDayInputLogic(); // 월 / 요일 입력
        workersInputLogic(); // 평 / 휴일 근무자들 입력
        // 배치 메소드 (내부에서 재배치 메소드)
        assignWorkers(date.getMonth(), date.getDay());
        // 출력 메소드
        OutputView.printMonthlyTable(totalWorkersOrder, date.getMonth(), date.getDay());

    }
    /*
    ----------------------------------- 입력 메소드 -----------------------------------
    */

    // 월, 요일 입력 및 유효성 검사
    // InputView에서 입력받고 Parsing 검사 진행
    public void monthAndDayInputLogic() {

        while (true) {
            InputView.printMonthAndDayInput();
            inputView.input();

            if (! inputView.checkMonthAndDayInput(inputView.getInput())) {
                continue;
            }

            MonthAndDay monthAndDay = inputView.getMonthAndDay(inputView.getInput());

            if (! Date.checkValidMonth(monthAndDay.getMonth())) {
                continue;
            }

            if (! Date.checkValidDay(monthAndDay.getDay())) {
                continue;
            }

            date = new Date(monthAndDay.getMonth(), monthAndDay.getDay());
            break;
        }

    }

    // 근무자 입력 메소드
    public void workersInputLogic() {
        List<Worker> weekdayWorkers = new ArrayList<>();
        List<Worker> holidayWorkers = new ArrayList<>();

        while (true) {
            // 평일 근무자 입력 및 체크
            InputView.printWeekdayWorkerInput();
            String[] weekdayWorkersName = inputWorkers();

            try {
                Worker.workersInputCheck(weekdayWorkersName);

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                continue;
            }

            // 평일 근무자 순서 저장
            for(String name : weekdayWorkersName) {
                weekdayWorkers.add(new Worker(name));
            }

            // 휴일 근무자 입력 및 체크
            InputView.printHolidayWorkerInput();
            String[] holidayWorkersName = inputWorkers();

            try {
                Worker.workersInputCheck(holidayWorkersName);

            } catch (IllegalArgumentException | NoSuchElementException e) { // todo 예외 종류 체크
                System.out.println(e.getMessage());
            }

            // 평 / 휴일 둘다 존재하는 지 체크
            if (! WorkerRoster.checkAllTypeWorkerExists(weekdayWorkersName, holidayWorkersName)) {
                continue;
            }

            // 휴일 근무자 순서 저장
            for(String name : holidayWorkersName) {
                holidayWorkers.add(new Worker(name));
            }

            workerRoster = new WorkerRoster(weekdayWorkers, holidayWorkers);
            break;
        }
    }

    // 근무자 입력 및 파싱반환 메소드
    public String[] inputWorkers(){
        inputView.input();

        return inputView.parseWorkersInput(inputView.getInput());
    }


    /*
    ----------------------------------- 유효성 체크 메소드 -----------------------------------
    */

    // 휴일/공휴일 체크 메소드
    public boolean checkIfDateHoliday(int month, String day, int date) {
        if (day.equals(Day.SAT.getDay()) || day.equals(Day.SUN.getDay())) {
            return true;
        }

        return Month.getHolidays(month).contains(date); // 공휴일 여부
    }


    /*
    ----------------------------------- 근무자 배치 메소드 -----------------------------------
    */
    // 배치 메소드 (평일 / 휴일 포함)
    public void assignWorkers(int month, String day) {
        int weekdayOrder = 0;
        int holidayOrder = 0;

        // 월간 근무자 배치 (재배치 포함)
        for (int date = 1; date <= Month.getDaysInMonth(month); date++) {

            // 휴일 (주말 / 공휴일) 체크
            if (checkIfDateHoliday(month, day, date))
            {
                assignHolidayWorkers(holidayOrder);
                day = Day.getNextDay(day);
                holidayOrder = workerRoster.nextWorkerOrder(holidayOrder);

                continue;
            }

            assignWeekdayWorkers(weekdayOrder);
            day = Day.getNextDay(day); // 다음날 설정
            weekdayOrder = workerRoster.nextWorkerOrder(weekdayOrder);
        }
    }

    // 평일 근무자 배치 메소드
    public void assignWeekdayWorkers(int currentOrder) {

        String todayWorker = workerRoster.getWeekdayWorker(currentOrder);

        // cf) 휴일이 연속으로 있고 평일과 격일로 있으면 재배치가 연속으로 이루어 질 수도
        // 직전에 재배치 -> 배치된 근무자 바로 이전의 근무자 추출
        if (reassignFlag[WorkType.WEEKDAY.getType()] == 1) {

            int previousOrder = workerRoster.previousWorkerOrder(currentOrder);
            todayWorker = workerRoster.getWeekdayWorker(previousOrder);
            reassignFlag[WorkType.WEEKDAY.getType()] = 0;
        }

        if (checkContinuousWorker(todayWorker)) {
            // 평일 재배치
            todayWorker =reassignWeekdayWorkers(currentOrder);
        }

        totalWorkersOrder.add(todayWorker);
    }

    // 휴일 근무자 배치 메소드
    public void assignHolidayWorkers(int currentOrder) {

        String todayWorker = workerRoster.getHolidayWorker(currentOrder);

        if (reassignFlag[WorkType.HOLIDAY.getType()] == 1) {

            int previousOrder = workerRoster.previousWorkerOrder(currentOrder);
            todayWorker = workerRoster.getHolidayWorker(previousOrder);
            reassignFlag[WorkType.HOLIDAY.getType()] = 0;
        }

        if (checkContinuousWorker(todayWorker)) {
            // 휴일 재배치
            todayWorker = reassignHolidayWorkers(currentOrder);
        }

        totalWorkersOrder.add(todayWorker);
    }

    private boolean checkContinuousWorker(String todayWorker) {

        int lastIndex = totalWorkersOrder.size() - 1;

        if (lastIndex < 0) {
            return false;
        }

        return todayWorker.equals(totalWorkersOrder.get(lastIndex));
    }

    public String reassignWeekdayWorkers(int currentOrder) {

        reassignFlag[WorkType.WEEKDAY.getType()] = 1;
        int nextOrder = workerRoster.nextWorkerOrder(currentOrder);
        return workerRoster.getWeekdayWorker(nextOrder);
    }

    public String reassignHolidayWorkers(int currentOrder) {

        reassignFlag[WorkType.HOLIDAY.getType()] = 1;
        int nextOrder = workerRoster.nextWorkerOrder(currentOrder);
        return workerRoster.getHolidayWorker(nextOrder);
    }

}

