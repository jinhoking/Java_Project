# 🎨💻 미니 CAD 프로젝트 기술서 (GUI & 콘솔 통합 버전)

## 1. 프로젝트 개요

본 프로젝트는 Java로 개발된 간단한 2D CAD 프로그램으로, **GUI(그래픽 사용자 인터페이스)** 버전과 **콘솔(텍스트 기반)** 버전을 모두 제공합니다. 사용자는 이미지 도면을 배경으로 불러와 그 위에 원(위치 센서), 사각형(분전반), 삼각형(안전 장치) 등의 기본 도형을 추가하고 관리할 수 있습니다. 두 버전은 동일한 핵심 데이터 관리 로직(`CadService`)을 공유하며, 데이터는 파일(`shapes.dat`)에 저장되어 영속성을 가집니다.

## 2. 주요 기능

### 공통 핵심 기능
* **데이터 관리**: 도형의 종류, 위치(x, y), 크기, 색상 정보를 `ShapeVO` 객체로 관리합니다.
* **핵심 로직 분리**: 실제 데이터(`ArrayList<ShapeVO>`)를 추가, 조회, 삭제하는 로직은 `CadService` 클래스에 집중되어 UI와 분리됩니다.
* **데이터 영속성**: 프로그램 종료 시 현재 도형 목록을 `shapes.dat` 파일에 저장하고, 프로그램 시작 시 자동으로 불러옵니다.

### GUI 버전 (`MiniCadGui`)
* **배경 도면 로딩**: JPG, PNG 이미지 파일을 불러와 캔버스의 배경으로 설정합니다.
* **도형 그리기**: 마우스 클릭으로 선택된 도형을 캔버스에 추가합니다.
* **실시간 상태 표시**: 하단 상태 표시줄에 다음 정보를 실시간으로 표시합니다.
    * 총 도형 개수 (Total)
    * 종류별 도형 개수 (원○, 사각형□, 삼각형△)
    * 마지막으로 그린 도형 정보 (종류, 번호, 좌표, 크기)
    * 현재 불러온 도면 파일 이름
    * 현재 날짜 및 시간 (1초마다 갱신)
* **전체 삭제**: 캔버스에 그려진 모든 도형과 배경 이미지를 지웁니다.

### 콘솔 버전 (`CadConsoleMain`)
* **로그인**: 프로그램 시작 시 아이디(**admin**)와 비밀번호(**1234**)를 확인합니다.
* **도형 추가**: 콘솔 입력을 통해 도형 정보를 `CadService`에 추가합니다.
* **도형 목록 보기**: 저장된 모든 도형 정보를 `toString()` 형식으로 출력합니다.
* **도형 삭제**: 목록 번호를 입력받아 해당 도형을 `CadService`에서 제거합니다.
* **통계 보기**:
    * 총 도형 개수 및 종류별 개수 요약 출력.
    * 각 도형의 상세 정보(종류, 해당 종류 내 번호, 좌표, 크기) 목록 출력.

## 3. 사용 기술

* **언어**: Java (JDK 17)
* **데이터 구조**: `ArrayList<ShapeVO>`
* **GUI 버전**: Java Swing (`JFrame`, `JPanel`, `JButton`, `JLabel`, `Timer`, `Graphics`, `BufferedImage`, `ImageIO`, `JFileChooser`, `BorderLayout`, `FlowLayout`, `BorderFactory`)
* **콘솔 버전**: `Scanner`, `HashMap` (통계 처리용)
* **파일 입출력**: `ObjectOutputStream`, `ObjectInputStream`, `Serializable` (객체 직렬화)

## 4. 프로젝트 구조

* **`CadService.java`**: (핵심 두뇌) 도형 데이터(`ArrayList<ShapeVO>`) 관리 및 파일 저장/불러오기 로직 담당.
* **`ShapeVO.java`**: (데이터 틀) 도형 정보 객체 (`Serializable` 구현).
* **`MiniCadGui.java`**: (GUI 화면) `JFrame` 기반 UI 구성 및 버튼/상태 표시줄 관리.
* **`CanvasPanel.java`**: (GUI 그리기 담당) `JPanel` 기반 도형 그리기, 마우스 이벤트 처리, 상태 라벨 업데이트.
* **`CadConsoleMain.java`**: (콘솔 화면) 콘솔 메뉴 표시, 사용자 입력 처리, `CadConsoleHandler` 호출.
* **`CadConsoleHandler.java`**: 콘솔 입/출력 로직 분리 클래스 (도형 추가/목록/삭제/통계 기능 구현).
* **`Login.java`**: 콘솔 버전 로그인 처리 클래스.

## 5. 실행 방법

### GUI 버전 실행
1. IDE(Eclipse, IntelliJ 등)에서 프로젝트를 엽니다.
2. `src` 폴더 안의 `CAD_Prj/MiniCadGui.java` 파일을 실행합니다 (`Run As` > `Java Application`).

### 콘솔 버전 실행
1. 프로젝트 `src` 폴더 상위에서 터미널을 엽니다.
2. **컴파일**: `javac CAD_Prj/*.java`
3. **실행**: `java CAD_Prj.CadConsoleMain`
4. 로그인 (ID: `admin`, PW: `1234`) 후 메뉴 번호를 입력하여 사용합니다.

## 6. 추가 정보
* 데이터 파일(`shapes.dat`)은 프로젝트 루트 디렉토리에 생성됩니다.
* GUI 버전과 콘솔 버전은 `shapes.dat` 파일을 통해 데이터를 공유할 수 있습니다. (단, 동시에 실행 시 파일 접근 충돌이 발생할 수 있으니 주의)

---

*(이 README는 2025년 10월 22일에 마지막으로 업데이트되었습니다.)*
