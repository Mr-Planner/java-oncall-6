package oncall.controller;

import oncall.model.Date;
import oncall.model.Worker;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.text.NumberFormat;
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

    // 유효하지 않은 값
    @DisplayName("월 / 요일 입력 테스트 (Invalid input case)")
    @ParameterizedTest
    @ValueSource(strings = {"13, 월", "4, 몫", "-1, 월", "12, 월화", "12, 월 화", "12, 5"})
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

    @DisplayName("월 / 요일 입력 테스트 (Month Input Exception case)")
    @ParameterizedTest
    @ValueSource(strings = {"월, 5", "월, 금", " ,월", "월", " , ", " ", ""})
    void monthInputExceptionTest(String input) {
        // given
        // valueSource의 인자값
        // when

        // then
        // assertThatThrownBy 내부의 람다식 형식은 gpt 참고
        assertThatThrownBy(() -> Integer.parseInt(input.split(",")[0])).
                isInstanceOfAny(NumberFormatException.class);
    }

    // 형식이 잘못됐을때 테스트 (Failure)
    @DisplayName("월 / 요일 입력 테스트 (Day Input Exception case)")
    @ParameterizedTest
    @ValueSource(strings = {"13", "월", " , ", " ", ""})
    void InputExceptionTest(String input) {
        // given
        // valueSource의 인자값
        String[] splitResult = input.split(",");
        // when

        // then
        // assertThatThrownBy 내부의 람다식 형식은 gpt 참고
        assertThat(splitResult).isEqualTo(2);
    }

}

