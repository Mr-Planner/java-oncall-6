package oncall.controller;


import oncall.enums.exception.ErrorCode;
import oncall.enums.model.date.Day;
import oncall.enums.model.date.Month;
import oncall.model.Date;
import oncall.enums.model.worker.WorkType;
import oncall.model.MonthAndDay;
import oncall.model.Worker;
import oncall.enums.model.worker.WorkerCount;
import oncall.view.InputView;
import oncall.view.OutputView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class OnCallController {
    // 필드
    private final InputView inputView = new InputView();
    private Date date;
    private final List<Worker> workers = new ArrayList<>();
    private final List<String> weekdayWorkersOrder = new ArrayList<>();
    private final List<String> holidayWorkersOrder = new ArrayList<>();
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

    // while문에는 flag검사로 최종
    public void workersInputLogic() {

        while (true) {
            // 평일 근무자 입력 및 체크
            InputView.printWeekdayWorkerInput();
            String[] names = inputWorkers();

            try {
                workersInputCheck(names);

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                continue;
            }

            // 평일 근무자 저장 (Worker 생성)
            saveWeekdayWorkers(names, WorkType.WEEKDAY.getType());

            // 휴일 근무자 입력 및 체크
            InputView.printHolidayWorkerInput();
            names = inputWorkers();

            try {
                workersInputCheck(names);
                saveHolidayWorkers(names, WorkType.HOLIDAY.getType());

                // 평일, 주말 모두 입력됐는지 체크 (마지막 단계)
                if (checkWorkDayFlag()) {
                    break;
                }
            } catch (IllegalArgumentException | NoSuchElementException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // 근무자 입력 및 파싱반환 메소드
    public String[] inputWorkers(){
        inputView.input();

        return inputView.parseWorkersInput(inputView.getInput());
    }

    /*
    ----------------------------------- 저장 메소드 -----------------------------------
    */
    // 평일 근무자 저장
    public void saveWeekdayWorkers(String[] names, int workType) {
        int index = 0;

        for (String name : names) {
            workers.add(new Worker(name));
            workers.get(index++).setWorkDayFlag(workType);
            weekdayWorkersOrder.add(name);
        }
    }

    // 휴일 근무자 저장
    public void saveHolidayWorkers(String[] names, int workType) {
        // 근무자 중에 name이 일치하는 거의 workType만 변경
        for (String name : names) {
            // 이름에 해당하는 근무자 찾기
            Worker worker = workers.stream()
                    .filter(one -> one.getName().equals(name))
                    .findFirst().orElseThrow(NoSuchElementException::new);
            // 휴일 근무 타입 설정
            worker.setWorkDayFlag(workType);
            holidayWorkersOrder.add(name);
        }
    }

    /*
    ----------------------------------- 유효성 체크 메소드 -----------------------------------
    */

    // 평일/휴일 공통 입력 유효성 체크 (이름의 길이 제한, 최소/최대 인원, 중복 인원)
    public void workersInputCheck(String[] names) {

        if (! checkDuplicatedName(names)) {
            throw new IllegalArgumentException(ErrorCode.INVALID_INPUT.getErrorMessage());

        }

        if (! checkWorkersCount(names)) {
            throw new IllegalArgumentException(ErrorCode.INVALID_INPUT.getErrorMessage());
        }

        for (String name : names) {
            if(! checkWorkerNameLength(name)) {
                throw new IllegalArgumentException(ErrorCode.INVALID_INPUT.getErrorMessage());
            };
        }
    }

    // todo 클래스 기준으로 나누어서 접근제한자 수정 (private으로), validate 용도의 함수는 private로 한다
    // todo 추후 3개 함수 한번에 람다로 리팩토링
    // 이름 길이 제한
    private Boolean checkWorkerNameLength(String name) {

        return !name.isEmpty() && name.length() < 5;
    }

    // 최소/최대 근무자 수 제한
    private Boolean checkWorkersCount(String[] names) {

        return names.length >= WorkerCount.MIN.getCount() && names.length <= WorkerCount.MAX.getCount();
    }

    // 중복 근무자 이름 체크
    private Boolean checkDuplicatedName(String[] names) {
        int count = names.length;

        return Arrays.stream(names).distinct().count() == count;
    }

    // 근무자 workDayFlag 체크 메소드
    // todo Test코드에서 사용해야 하므로 public으로 바꿈
    public boolean checkWorkDayFlag() {
        for (Worker worker : workers) {
            if (! worker.checkWorkDaysValid()) {
                OutputView.printInputError();
                return false;
            }
        }
        return true;
    }

    // 근무자 연속 배치 여부 메소드
    private Boolean checkContinuousWorker(String[] names) {

        return true;
    }


    /*
    ----------------------------------- 근무자 배치 메소드 -----------------------------------
    */
    // 배치 메소드 (평일 / 휴일 포함)
    public void assignWorkers(int month, String day) {

        for (int date = 1, order = 0; date <= Month.getDaysInMonth(month); date++, order++) {
            // 주말 및 공휴일
            int currentOrder = order % workers.size();

            if (day.equals(Day.SAT.getDay()) || day.equals(Day.SUN.getDay())
                    || Month.getHolidays(month).contains(date))
            {
                assignHolidayWorkers(currentOrder);
                day = Day.getNextDay(day);
                continue;
            }

            assignWeekdayWorkers(currentOrder);
            day = Day.getNextDay(day); // 다음날 설정
        }
    }

    // 평일 근무자 배치 메소드
    public void assignWeekdayWorkers(int currentOrder) {

        String todayWorker = weekdayWorkersOrder.get(currentOrder);

        // todo if문 2개가 동시에 만족되는 경우는 잘 모르겠음.. (없다고 가정함)
        // -> 재배치된 이후 다음
        if (reassignFlag[WorkType.WEEKDAY.getType()] == 1) {
            int previousOrder = (currentOrder - 1) % workers.size();
            todayWorker = weekdayWorkersOrder.get(previousOrder);
        }

        if (checkContinuousWorker(todayWorker)) {
            // 평일 재배치
            todayWorker =reassignWeekdayWorkers(currentOrder);
        }

        reassignFlag[WorkType.WEEKDAY.getType()] = 0;
        totalWorkersOrder.add(todayWorker);
    }

    // 휴일 근무자 배치 메소드
    public void assignHolidayWorkers(int currentOrder) {

        String todayWorker = holidayWorkersOrder.get(currentOrder);

        if (reassignFlag[WorkType.WEEKDAY.getType()] == 1) {
            int previousOrder = (currentOrder - 1) % workers.size();
            todayWorker = holidayWorkersOrder.get(previousOrder);
        }

        if (checkContinuousWorker(todayWorker)) {
            // 휴일 재배치
            todayWorker = reassignHolidayWorkers(currentOrder);
        }

        reassignFlag[WorkType.HOLIDAY.getType()] = 0;
        totalWorkersOrder.add(todayWorker);
    }

    private boolean checkContinuousWorker(String todayWorker ) {
        
        int lastIndex = totalWorkersOrder.size() - 1;
        return todayWorker.equals(totalWorkersOrder.get(lastIndex));
    }

    public String reassignWeekdayWorkers(int currentOrder) {

        reassignFlag[WorkType.WEEKDAY.getType()] = 1;
        int nextOrder = (currentOrder + 1) % workers.size();
        return weekdayWorkersOrder.get(nextOrder);
    }

    public String reassignHolidayWorkers(int currentOrder) {

        reassignFlag[WorkType.HOLIDAY.getType()] = 1;
        int nextOrder = (currentOrder + 1) % workers.size();
        return holidayWorkersOrder.get(nextOrder);
    }

}

