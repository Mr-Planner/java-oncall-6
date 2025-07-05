package oncall.controller;


import oncall.enums.exception.ErrorCode;
import oncall.model.Date;
import oncall.enums.model.worker.WorkType;
import oncall.model.Worker;
import oncall.enums.model.worker.WorkerCount;
import oncall.view.InputView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class OnCallController {
    // 필드
    private final Date date = new Date();
    private final InputView inputView = new InputView();
    private final List<Worker> workers = new ArrayList<>();

    // 프로그램 실행 흐름
    public void run() {
        // 입력
        monthAndDayInputLogic(); // 월 / 요일 입력
        workersInputLogic(); // 평 / 휴일 근무자들 입력
    }
    /*
    ----------------------------------- 입력 메소드 -----------------------------------
    */

    // 월, 요일 입력 및 유효성 검사
    // InputView에서 입력받고 Parsing 검사 진행
    public void monthAndDayInputLogic() {

        do {
            InputView.printMonthAndDayInput();
            inputView.input();

        } while (! (inputView.checkMonthAndDayInput(inputView.getInput()) && date.checkValidMonth(inputView.getMonth()) && date.checkValidDay(inputView.getDay())));

    }

    // 여기서 do while로 하고 weekday input 및 저장 (1차), holiday input 및 저장
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

                // 평일, 주말 모두 입력됐는지 체크
                checkWorkDayFlag();
                break;
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
    public void checkWorkDayFlag() {
        for (Worker worker : workers) {
            if (! worker.checkWorkDaysValid()) {
                throw new IllegalArgumentException(ErrorCode.INVALID_INPUT.getErrorMessage());
            }
        }

    }

    // 근무자 연속 배치 여부 메소드 (todo 평일 or 휴일 근무자 재배치 필요)
    private Boolean checkContinuousWorker(String[] names) {

        return true;
    }


    /*
    ----------------------------------- 근무자 재배치 메소드 -----------------------------------
    */
    // 평일 근무자 재배치 메소드

    // 휴일 근무자 재배치 메소드

}

