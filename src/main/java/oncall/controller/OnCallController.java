package oncall.controller;

import oncall.model.Date;
import oncall.model.Worker;
import oncall.view.InputView;
import oncall.view.OutPutView;

import java.util.ArrayList;
import java.util.List;

public class OnCallController {
    // 필드
    private final Date date = new Date();
    private final List<Worker> workers = new ArrayList<>();
    private final InputView inputView = new InputView();
    private final OutPutView outPutView = new OutPutView();

    // 프로그램 실행 흐름
    public void run() {
        // 입력
        monthAndDayInputLogic();
    }

    public void monthAndDayInputLogic() {
        inputView.printMonthAndDayInput();
        date.SaveStartMonthDay(); // 월 / 일 형식 유효성 검사 포함
    }

    // 평일 근무자 저장 메소드
//    public void saveWeekDayWorkers() {
//        do {
//
//        } while ();
//    }

    // 휴일 근무자 저장 메소드

    // 평일/휴일 근무자 저장 유효성 검사 (1. 평일 / 휴일에 각각 한번인지, 2. 연속됐는지)

    // 평일 근무자 재배치 메소드

    // 휴일 근무자 재배치 메소드

}

