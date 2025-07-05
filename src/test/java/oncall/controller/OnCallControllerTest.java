package oncall.controller;

import oncall.enums.model.worker.WorkType;
import oncall.model.Date;
import oncall.model.Worker;
import oncall.view.InputView;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

public class OnCallControllerTest {
    private OnCallController onCallController;
    private Date date;
    private List<Worker> workers;
    private InputView inputView;

    // 테스트 시작 전
    @BeforeEach
    void setUpController() {
        // refresh controller
        onCallController = new OnCallController();
        date = new Date();
        workers = new ArrayList<>();
        inputView = new InputView();
    }

    /*
    ----------------------------------- 월 / 요일 입력 테스트 -----------------------------------
    */

    @DisplayName("월 / 요일 입력 테스트 (valid input case)")
    @ParameterizedTest
    @ValueSource(strings = {"5,월", "4, 토"})
    void validMonthAndDayInputLogicTest(String input) {

        assertThat(inputView.checkMonthAndDayInput(input)).isEqualTo(true);
        assertThat(date.checkValidMonth(inputView.getMonth()) && date.checkValidDay(inputView.getDay())).isEqualTo(true);
    }

    // 올바른 형식 but  유효하지 않은 값
    @DisplayName("월 / 요일 입력 테스트 (Right but invalid input case)")
    @ParameterizedTest
    @ValueSource(strings = {"13, 월", "4, 몫", "-1, 월", "12, 월화", "12, 월 화", "12, 5"})
    void invalidMonthAndDayInputLogicTest(String input) {

        // 1차 : split개수가 정확히 2개인지
        // 2차 : 월 / 요일 중 하나라도 false인 결과가 있는지
        assertThat(inputView.checkMonthAndDayInput(input)).isEqualTo(true);
        assertThat(date.checkValidMonth(inputView.getMonth()) && date.checkValidDay(inputView.getDay())).isNotEqualTo(true);
    }

    @DisplayName("월 / 요일 입력 테스트 (False input)")
    @ParameterizedTest
    @ValueSource(strings = {"월, 5", "월, 금", " ,월", "월", " , ", " ", ""})
    void monthInputExceptionTest(String input) {

        assertThat(inputView.checkMonthAndDayInput(input)).isEqualTo(false);
    }

    /*
    ----------------------------------- 근무자 입력 테스트 -----------------------------------
    */

    // todo 현재 테스트 코드에서 컨트롤러의 필드를 직접 접근하고 수정하고 있음
    @DisplayName("근무자 입력 테스트 (valid input case)")
    @Test
    void validWorkersInputLogicTest() {
        // given
        String weekdayWorkersInput = "준팍,도밥,고니,수아,루루,글로";
        String holidayWorkersInput = "수아,루루,글로,고니,도밥,준팍";

        // when
        String[] weekdayWorkers = inputView.parseWorkersInput(weekdayWorkersInput);
        String[] holidayWorkers = inputView.parseWorkersInput(holidayWorkersInput);

        onCallController.saveWeekdayWorkers(weekdayWorkers, WorkType.WEEKDAY.getType());
        onCallController.saveHolidayWorkers(holidayWorkers, WorkType.HOLIDAY.getType());

        // then
        assertThatCode(() -> onCallController.workersInputCheck(weekdayWorkers))
                .doesNotThrowAnyException();

        assertThatCode(() -> onCallController.workersInputCheck(holidayWorkers))
                .doesNotThrowAnyException();

        assertThatCode(() -> onCallController.saveHolidayWorkers(holidayWorkers, WorkType.HOLIDAY.getType()))
                .doesNotThrowAnyException();

        assertThatCode(() -> onCallController.checkWorkDayFlag())
                .doesNotThrowAnyException();
    }
}

//assertThatThrownBy(() -> onCallController.workersInputCheck(weekdayWorkers))
//        .isInstanceOf(IllegalArgumentException.class);
//
//assertThatThrownBy(() -> onCallController.workersInputCheck(holidayWorkers))
//        .isInstanceOf(IllegalArgumentException.class);
//
//assertThatThrownBy(() -> onCallController.saveHolidayWorkers(holidayWorkers, WorkType.HOLIDAY.getType()))
//        .isInstanceOf(NoSuchElementException.class);
//
//assertThatThrownBy(() -> onCallController.checkWorkDayFlag())
//        .isInstanceOf(IllegalArgumentException.class);