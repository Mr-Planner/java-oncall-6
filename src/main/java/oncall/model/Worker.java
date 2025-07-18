package oncall.model;

import oncall.enums.exception.ErrorCode;
import oncall.enums.model.worker.WorkType;
import oncall.enums.model.worker.WorkerCount;

import java.util.Arrays;

public class Worker {

    private final String name;

    public Worker(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /*
    ----------------------------------- 유효성 체크 메소드 -----------------------------------
    */

    // 평일/휴일 공통 입력 유효성 체크 (이름의 길이 제한, 최소/최대 인원, 중복 인원)
    public static void workersInputCheck(String[] names) {

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
    private static Boolean checkWorkerNameLength(String name) {

        return !name.isEmpty() && name.length() < 5;
    }

    // 최소/최대 근무자 수 제한
    private static Boolean checkWorkersCount(String[] names) {

        return names.length >= WorkerCount.MIN.getCount() && names.length <= WorkerCount.MAX.getCount();
    }

    // 중복 근무자 이름 체크
    private static Boolean checkDuplicatedName(String[] names) {
        int count = names.length;

        return Arrays.stream(names).distinct().count() == count;
    }
}
