package oncall.controller;

import oncall.enums.model.worker.WorkType;
import oncall.model.Date;
import oncall.model.Worker;
import oncall.model.WorkerRoster;
import oncall.view.InputView;
import oncall.view.OutputView;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

public class OnCallControllerTest {
    private OnCallController onCallController;
    private WorkerRoster workerRoster;
    private InputView inputView;
    private OutputView outputView;
    private Date date;

    // 테스트 시작 전
    @BeforeEach
    void setUpController() {
        // refresh controller
        onCallController = new OnCallController();
        inputView = new InputView();
    }

    /*
    ----------------------------------- 월 / 요일 입력 테스트 -----------------------------------
    */

    @DisplayName("월 / 요일 입력 테스트 (valid input case)")
    @ParameterizedTest
    @ValueSource(strings = {"5,월", "4, 토"})
    void validMonthAndDayInputLogicTest(String input) {

        date = inputView.getMonthAndDay(input);

        assertThat(inputView.checkMonthAndDayInput(input)).isEqualTo(true);
        assertThat(Date.checkValidMonth(date.getMonth()) && Date.checkValidDay(date.getDay())).isEqualTo(true);
    }

    // 올바른 형식 but  유효하지 않은 값
    @DisplayName("월 / 요일 입력 테스트 (Right but invalid input case)")
    @ParameterizedTest
    @ValueSource(strings = {"13, 월", "4, 몫", "-1, 월", "12, 월화", "12, 월 화", "12, 5"})
    void invalidMonthAndDayInputLogicTest(String input) {

        date = inputView.getMonthAndDay(input);

        // 1차 : split개수가 정확히 2개인지
        // 2차 : 월 / 요일 중 하나라도 false인 결과가 있는지
        assertThat(inputView.checkMonthAndDayInput(input)).isEqualTo(true);
        assertThat(Date.checkValidMonth(date.getMonth()) && Date.checkValidDay(date.getDay())).isNotEqualTo(true);
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

    @DisplayName("근무자 입력 테스트 (valid input case)")
    @Test
    void validWorkersInputLogicTest() {
        // given
        String weekdayWorkersInput = "준팍,도밥,고니,수아,루루,글로";
        String holidayWorkersInput = "수아,루루,글로,고니,도밥,준팍";

        // when
        String[] weekdayWorkers = inputView.parseWorkersInput(weekdayWorkersInput);
        String[] holidayWorkers = inputView.parseWorkersInput(holidayWorkersInput);

        // then
        assertThatCode(() -> Worker.workersInputCheck(weekdayWorkers))
                .doesNotThrowAnyException();

        assertThatCode(() -> Worker.workersInputCheck(holidayWorkers))
                .doesNotThrowAnyException();

        assertThat(WorkerRoster.checkAllTypeWorkerExists(weekdayWorkers, holidayWorkers))
                .isEqualTo(true);
    }

    // 실패 테스트 케이스 (이름 길이 / (최소 or 최대) 인원 / 중복 인원) 체크
    // 1. 5자 이상 이름, 2. 5명 이하, 3. 35명 이상, 4. 중복 이름
    @DisplayName("근무자 입력 테스트 실패 (이름길이 초과 or (최소 / 최대) 인원 미충족)")
    @ParameterizedTest
    @CsvSource(
            delimiter = '|',
            value = {
                    "가나다라마, 민수, 철원, 영희, 대영 | 대영, 민수, 가나다라마, 영희, 철원",
                    "영희, 민수, 철원 | 민수, 철원, 영희",
                    "나윤,하늘,보람,재희,지후,선미,태윤,지안,서율,민재,세린,유진,다온,하랑,민서,도윤,예진,하민,서아,가온,소율,은우,예림,지아,채원,수빈,시윤,하진,다현,윤서,주아,지운,유림,다빈,서진,아린 " +
                            "| 서아,지운,보람,하진,가온,윤서,태윤,지아,다온,유림,지후,서율,하민,예림,민서,도윤,지안,다빈,하랑,세린,예진,재희,나윤,민재,유진,아린,다현,은우,서진,하늘,수빈,소율,채원,하진,선미,지안"
            }
    )
    void invalidWorkersInputLogicTest(String weekdayWorkersInput, String holidayWorkersInput) {
        String[] weekdayWorkers = inputView.parseWorkersInput(weekdayWorkersInput);
        String[] holidayWorkers = inputView.parseWorkersInput(holidayWorkersInput);

        assertThatThrownBy(() -> Worker.workersInputCheck(weekdayWorkers))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Worker.workersInputCheck(holidayWorkers))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("근무자 입력 테스트 실패 (중복인원 존재)")
    @ParameterizedTest
    @CsvSource(
            delimiter = '|',
            value = {
                    "영희, 영희, 민수, 철원, 대영, 성민 | 민수, 철원, 영희, 성민, 영희, 대영",
                    "준팍,도밥,고니,수아,루루,글로,철수, 수아 | 수아,루루,글로,고니,철수,도밥,준팍,수아"
            }
    )
    void duplicatedWorkersInputLogicTest(String weekdayWorkersInput, String holidayWorkersInput) {
        String[] weekdayWorkers = inputView.parseWorkersInput(weekdayWorkersInput);
        String[] holidayWorkers = inputView.parseWorkersInput(holidayWorkersInput);

        assertThatThrownBy(() -> Worker.workersInputCheck(weekdayWorkers))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Worker.workersInputCheck(holidayWorkers))
                .isInstanceOf(IllegalArgumentException.class);
    }

    // 실패 테스트 케이스 (평 / 휴일 중 하나에만 입력된 경우) 체크
    @DisplayName("근무자 입력 테스트 실패 (평일 <-> 휴일 근무자 일치 X)")
    @ParameterizedTest
    @CsvSource(
            delimiter = '|',
            value = {
                    "준팍,도밥,고니,수아,루루,글로 | 도밥, 고니, 수아, 글로, 민수",
                    "도밥, 고니, 수아, 글로, 민수 | 준팍,도밥,고니,수아,루루,글로",
            }
    )
    void invalidWorkDayFlagTest(String weekdayWorkersInput, String holidayWorkersInput) {
        String[] weekdayWorkers = inputView.parseWorkersInput(weekdayWorkersInput);
        String[] holidayWorkers = inputView.parseWorkersInput(holidayWorkersInput);

        // 입력 형식에는 문제가 없어야 함
        assertThatCode(() -> Worker.workersInputCheck(weekdayWorkers))
                .doesNotThrowAnyException();

        assertThatCode(() -> Worker.workersInputCheck(holidayWorkers))
                .doesNotThrowAnyException();

        // weekday <-> holiday 비교가 같으면 안됨
        assertThat(WorkerRoster.checkAllTypeWorkerExists(weekdayWorkers, holidayWorkers))
                .isNotEqualTo(true);
    }
}
