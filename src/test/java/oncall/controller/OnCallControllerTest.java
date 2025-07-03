package oncall.controller;

import oncall.model.Date;
import oncall.model.Worker;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class OnCallControllerTest {
    private OnCallController onCallController;
    private Date date;
    private List<Worker> workers;

    // 테스트 시작 전
    @BeforeEach
    void setUpController() {
        // refresh controller
        onCallController = new OnCallController();
        date = new Date();
        workers = new ArrayList<>();
    }

    @DisplayName("월 / 요일 입력 테스트 (valid input case)")
    @ParameterizedTest
    @ValueSource(strings = {"5,월", "4, 토"})
    void validMonthAndDayInputLogicTest(String input) {
        // given
        // valueSource의 인자값
        int month = Integer.parseInt(input.split(",")[0]);
        String day = input.split(",")[1].trim();

        // when

        // then
        // 테스트 분리가 좋을 것 같아서 분리 해놓음
        assertThat(date.checkValidMonth(month)).isEqualTo(true);
        assertThat(date.checkValidDay(day)).isEqualTo(true);
    }

    @DisplayName("월 / 요일 입력 테스트 (Invalid input case)")
    @ParameterizedTest
    @ValueSource(strings = {"13, 월", "4, 몫", "-1, 월", "12, 월화", "12, 월 화"})
    void invalidMonthAndDayInputLogicTest(String input) {
        // given
        String[] splitResult = input.split(",");
        int month = Integer.parseInt(splitResult[0]);
        String day = splitResult[1].trim();
        // when

        // then
        // 1차 : split개수가 정확히 2개인지
        assertThat(splitResult).hasSize(2);
        // 2차 : 월 / 요일 중 하나라도 false인 결과가 있는지
        assertThat(date.checkValidMonth(month) && date.checkValidDay(day)).isNotEqualTo(true);
    }

    // todo 해결방법 모르겠음
    @DisplayName("월 / 요일 입력 테스트 (Exception case)")
    @ParameterizedTest
    @ValueSource(strings = {"12, 4", "월, 5", "월, 금", " ,월", "13", "월", " , ", " ", ""})
    void exceptionMonthAndDayInputLogicTest(String input) {
        // given
        // valueSource의 인자값
        // todo Parsing에서 에러 검출 할 것 + 메소드 분리 해야할 수도 있음
        String[] splitResult = input.split(",");
        int month;
        String day = splitResult[1].trim();
        // when
        // assertThatThrownBy 내부의 람다식 형식은 gpt 참고
        assertThatThrownBy(() -> Integer.parseInt(splitResult[0]))
                .isInstanceOfAny(
                        NumberFormatException.class,
                        IllegalArgumentException.class,
                        NullPointerException.class
                );

        assertThatThrownBy(() -> date.checkValidDay(day))
                .isInstanceOfAny(
                        NumberFormatException.class,
                        IllegalArgumentException.class,
                        NullPointerException.class
                );
    }


}

