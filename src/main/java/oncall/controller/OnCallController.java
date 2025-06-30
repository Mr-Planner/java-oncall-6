package oncall.controller;


import oncall.exception.ErrorCode;
import oncall.model.Date;
import oncall.model.WorkType;
import oncall.model.Worker;
import oncall.model.WorkerCount;
import oncall.view.InputView;
import oncall.view.OutPutView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static camp.nextstep.edu.missionutils.Console.readLine;

public class OnCallController {
    // 필드
    private final Date date = new Date();
    private final List<Worker> workers = new ArrayList<>();
    private final InputView inputView = new InputView();
    private final OutPutView outPutView = new OutPutView();

    // 프로그램 실행 흐름
    public void run() {
        // 입력
        monthAndDayInputLogic(); // 월 / 요일 입력
    }
    /*
    ----------------------------------- 입력 메소드 -----------------------------------
    */
    public void monthAndDayInputLogic() {
        inputView.printMonthAndDayInput();
        date.SaveStartMonthDay(); // 월 / 일 형식 유효성 검사 포함
    }

    public void weekDayWorkerInputLogic() {
        inputView.printWeekDayWorkerInput();
        saveWorkers(WorkType.WEEKDAY.getType());
    }

    public void HolidayWorkerInputLogic() {
        inputView.printHolidayWorkerInput();
        saveWorkers(WorkType.HOLIDAY.getType());
    }

    /*
    ----------------------------------- 저장 메소드 -----------------------------------
    */
    // 근무자 저장 메소드 (workType -> 평/휴일 구별)
    public void saveWorkers(int workType) {
        String input;
        String[] names;

        do {
            input = readLine(); // "," 구분자로 한번에 입력
            // 중복 제거 해서 String[]에 저장
            names = Arrays.stream(input.split(",")).map(String::trim).toArray(String[]::new);
            // 1차 조건
            if(!workerInputCheck(names)) {
                throw new IllegalArgumentException(ErrorCode.INVALID_INPUT.getErrorMessage());
            }
        } while (! workerInputCheck(names));

        // workType별로 Worker의 flag에 나누어서 저장
        if (workType == WorkType.WEEKDAY.getType()) {
            saveWeekDayWorkers(names, workType);
        }

        if (workType == WorkType.HOLIDAY.getType()) {
            saveHoliDayWorkers(names, workType);
        }

        // todo flag가 다 켜졌는지 검사해야 -> 검증 X시 반복 (어떻게 다시 돌아가지)
        // save시에

    }

    // 평일 근무자 저장
    public void saveWeekDayWorkers(String[] names, int workType) {
        int index = 0;

        for (String name : names) {
            workers.add(new Worker(name));
            workers.get(index++).setWorkDayFlag(workType);
        }
    }

    // 휴일 근무자 저장
    public void saveHoliDayWorkers(String[] names, int workType) {
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
    public Boolean workerInputCheck(String[] names) {

        for (String name : names) {
            if(checkWorkerNameLength(name) && checkWorkersCount(names) && checkDuplicatedName(names)) {
                return true;
            };
        }

        return false;
    }

    // 이름 길이 제한
    public Boolean checkWorkerNameLength(String name) {

        return !name.isEmpty() && name.length() < 5;
    }

    // 최소/최대 근무자 수 제한
    public Boolean checkWorkersCount(String[] names) {

        return names.length >= WorkerCount.MIN.getCount() && names.length <= WorkerCount.MAX.getCount();
    }

    // 중복 근무자 이름 체크
    public Boolean checkDuplicatedName(String[] names) {
        int count = names.length;

        return Arrays.stream(names).distinct().count() == count;
    }

    // 근무자 workDayFlag 체크 메소드
    public Boolean checkWorkDayFlag(Worker worker) {

        return worker.checkWorkDayValid();
    }


    // 근무자 연속 배치 여부 메소드 (todo 평일 or 휴일 근무자 재배치 필요)
    public Boolean checkContinuousWorker(String[] names) {

        return true;
    }

    // 평일/휴일 근무자 저장 유효성 검사 (평일 / 휴일에 각각 한번인지)

    /*
    ----------------------------------- 근무자 재배치 메소드 -----------------------------------
    */
    // 평일 근무자 재배치 메소드

    // 휴일 근무자 재배치 메소드

}

