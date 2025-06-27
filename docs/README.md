# woowa-precourse

### 1. 기능 요구사항
구현 : **비상 근무표 자동화**

**비상 근무 배정 규칙**
- 평일 <-> 휴일 근무 순서는 달라야
- 2일 연속 근무 불가 cf) 평일/휴일 다음 근무자와 임시교대

**기능 정리**
1. 입력 기능
2. 평일 / 휴일 근무 순번 배치 기능
3. 연속 근무 순번 처리 기능

**기타 규칙**
- 닉네임 중복 X
- 최대 5자
- 최대 35명까지

**입력 요구사항**
- 월(숫자), 요일(일 ~ 토) 입력 cf) 연도 고려 X

cf) 2월은 28일까지만 존재 가정

error) [ERROR] 유효하지 않은 입력 값입니다.
-> 안내 후 월, 시작 요일 다시 입력

- 평일 근무 순번 닉네임 -> 휴일 근무 순번 닉네임 입력

error) [ERROR] 유효하지 않은 입력 값입니다.
-> 안내 후 평일 근무 순번부터 다시 입력

**출력 요구사항**\
ex.\
5월 4일 목 수아\
5월 5일 금 (휴일) 루루

### 2. 프로그래밍 요구사항

1) 진입점 : main()
2) 종료시 System.exit()호출 X
3) 중첩은 최대 2번 (메소드 분리)
4) 3항연산자 X
5) 메소드는 15줄 이내 (한 가지 기능만)
6) JUnit5 / AssertJ로 테스트
7) else / switch case 사용 X
8) 잘못된 값 입력 시 IllegalArgumentException (명확한 유형의 에러 사용)
9) 라이브러리 사용

camp.nextstep.deu.missionutils의 Console API 사용\
입력 값 : camp.nextstep.deu.missionutils.Console의 readLine()활용


### 3. 과제 진행 요구사항

1. docs/README.md에 구현 목록 정리
2. commit은 README의 기능 목록 단위로 추가