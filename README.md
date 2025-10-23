🎨💻 안전 장치 도면 매핑(Mapping) 프로그램 (GUI & Console v1.0)1. 📖 프로젝트 개요본 프로젝트는 **Java(JDK 17)**로 개발된 안전 장치 도면 매핑 프로그램입니다. 시설 관리자나 안전 담당자가 현장 도면(이미지) 위에 주요 장치들의 위치를 시각적으로 기록하고 관리하는 것을 목표로 합니다.동일한 핵심 로직(CadService)을 공유하는 GUI 버전과 콘솔 버전을 모두 제공하여 사용 환경에 따라 선택적으로 사용할 수 있습니다. 모든 데이터는 shapes.dat 파일에 객체 직렬화(Object Serialization) 방식으로 저장되어 영속성을 가집니다.2. 📸 스크린샷 (예시)(여기에 GUI 및 콘솔 버전의 실제 실행 스크린샷을 추가하면 좋습니다.)GUI 버전 (MiniCadGui.java)콘솔 버전 (CadConsoleMain.java)[GUI 실행 화면 이미지][콘솔 실행 화면 이미지]3. ✨ 주요 기능💎 공통 핵심 기능 (Core Logic)데이터 모델링: 모든 도형(센서, 분전반, 장치) 정보는 ShapeVO 객체로 관리됩니다. (종류, 좌표, 크기, 색상)비즈니스 로직 분리: UI(GUI/콘솔)와 데이터 관리 로직(CadService)을 명확하게 분리하여 유지보수성을 높였습니다.데이터 영속성: ObjectOutputStream / ObjectInputStream을 사용하여 프로그램 종료 시 ArrayList<ShapeVO> 객체를 shapes.dat 파일에 저장하고, 시작 시 자동으로 불러옵니다.🖥️ GUI 버전 (MiniCadGui.java)동적 캔버스: 마우스 이벤트를 감지하여 JPanel 위에 도형을 그립니다.배경 도면 관리: JFileChooser를 통해 JPG, PNG 등 이미지 파일을 불러와 캔버스 배경으로 설정할 수 있습니다.실시간 상태 표시줄: Timer (1초 간격)를 사용하여 하단 JLabel에 다음 정보를 실시간으로 업데이트합니다.총 도형 개수 (Total)종류별 도형 개수 (원○, 사각형□, 삼각형△)마지막 작업 정보 (종류, 번호, 좌표 등)현재 도면 파일명 및 현재 시각일괄 삭제: 'Clear' 버튼으로 캔버스의 모든 도형과 배경 이미지를 초기화합니다.⌨️ 콘솔 버전 (CadConsoleMain.java & CadConsoleHandler.java)사용자 인증: 프로그램 시작 시 간단한 로그인 기능을 제공합니다. (ID: admin, PW: 1234)메뉴 기반 인터페이스: Scanner를 통해 사용자 입력을 받아 4가지 주요 기능을 실행합니다.도형 추가: 좌표, 크기, 색상 정보를 입력받아 CadService에 전달합니다.도형 목록: CadService의 ArrayList를 순회하며 toString()으로 정보를 출력합니다.도형 삭제: 목록 번호(인덱스)를 기준으로 데이터를 삭제합니다.통계 보기: HashMap을 활용하여 종류별 개수를 집계하고, 각 도형의 상세 정보를 포맷에 맞춰 출력합니다.4. 🏗️ 시스템 아키텍처본 프로젝트는 UI 레이어와 서비스 레이어를 분리하는 간단한 2-Tier 아키텍처를 따릅니다.[ UI Layer ]  <---- (호출) ----> [ Service Layer ] <---- (I/O) ----> [ Data File ]
+-------------------------+      +-----------------+     +---------------+
| - MiniCadGui.java       |      |                 |     |               |
| - CanvasPanel.java      | ===> |  CadService.java| ===>|  shapes.dat   |
| - CadConsoleMain.java   |      |  (ArrayList)    |     | (Serialized)  |
| - CadConsoleHandler.java|      |                 |     |               |
+-------------------------+      +-----------------+     +---------------+
                                        ^
                                        | (데이터 모델)
                                +---------------+
                                |  ShapeVO.java |
                                +---------------+
5. 🔧 사용 기술 (Tech Stack)Core: Java (JDK 17)GUI: Java Swing (JFrame, JPanel, JButton, JLabel, Graphics, Timer, JFileChooser, ImageIO, BorderLayout, FlowLayout)Console: ScannerData Structure: ArrayList<ShapeVO>, HashMap (콘솔 통계용)Data Persistence: ObjectOutputStream, ObjectInputStream, Serializable6. 📁 프로젝트 구조ProjectRoot/
├── src/
│   └── CAD_Prj/
│       ├── CadConsoleHandler.java  // 콘솔 기능 로직 구현
│       ├── CadConsoleMain.java     // 콘솔 메인 (실행)
│       ├── CanvasPanel.java        // GUI 캔버스 (도형 그리기, 마우스 이벤트)
│       ├── CadService.java         // ⭐️ 핵심 서비스 (데이터 관리 및 파일 I/O)
│       ├── Login.java              // 콘솔 로그인 처리
│       ├── MiniCadGui.java         // GUI 메인 (프레임, 버튼, 상태 표시줄)
│       └── ShapeVO.java            // ⭐️ 데이터 모델 (Serializable)
│
└── shapes.dat                      // 데이터 저장 파일 (프로젝트 루트에 자동 생성)
7. 🚀 시작하기 (Getting Started)전제 조건Java JDK 17 이상 설치 및 환경 변수 설정1. GUI 버전 실행Eclipse, IntelliJ 등 Java IDE에서 프로젝트를 임포트(Import)합니다.src/CAD_Prj/MiniCadGui.java 파일을 찾습니다.Run As > Java Application으로 실행합니다.2. 콘솔 버전 실행 (터미널)프로젝트의 src 폴더가 있는 상위 디렉토리에서 터미널(CMD, PowerShell 등)을 엽니다.컴파일:Bashjavac CAD_Prj/*.java
실행:Bashjava CAD_Prj.CadConsoleMain
로그인 정보를 입력합니다.ID: adminPW: 1234표시되는 메뉴 번호를 입력하여 프로그램을 사용합니다.8. ⚠️ 주의 사항GUI 버전과 콘솔 버전은 shapes.dat 파일을 공유합니다.두 버전을 동시에 실행할 경우, 파일 접근 충돌(IOException 등)이 발생하여 데이터가 손상될 수 있습니다. 반드시 하나의 버전만 실행하여 사용해 주세요.(이 README는 2025년 10월 22일자 원본 문서를 기반으로 2025년 10월 23일에 개선되었습니다.)
