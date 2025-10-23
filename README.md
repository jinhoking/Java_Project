## 🎨💻 안전 장치 도면 매핑(Mapping) 프로그램 (GUI & Console v1.0)

[![Java](https://img.shields.io/badge/Java-17-blue.svg?style=for-the-badge&logo=openjdk)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg?style=for-the-badge)](https://opensource.org/licenses/MIT)
[![Build](https://img.shields.io/badge/Build-Passing-brightgreen.svg?style=for-the-badge)](https://github.com/)

---

### 1. 📖 프로젝트 개요

본 프로젝트는 **Java(JDK 17)**로 개발된 안전 장치 도면 매핑 프로그램입니다. 시설 관리자나 안전 담당자가 현장 도면(이미지) 위에 주요 장치들의 위치를 시각적으로 기록하고 관리하는 것을 목표로 합니다.

동일한 핵심 로직(`CadService`)을 공유하는 **GUI 버전**과 **콘솔 버전**을 모두 제공하여 사용 환경에 따라 선택적으로 사용할 수 있습니다. 모든 데이터는 `shapes.dat` 파일에 **객체 직렬화(Object Serialization)** 방식으로 저장되어 영속성을 가집니다.

---

### 2. 📸 스크린샷 (예시)

*(여기에 GUI 및 콘솔 버전의 실제 실행 스크린샷을 추가하면 좋습니다.)*

| GUI 버전 (`MiniCadGui.java`) | 콘솔 버전 (`CadConsoleMain.java`) |
| :---: | :---: |
| \[GUI 실행 화면 이미지] | \[콘솔 실행 화면 이미지] |

---

### 3. ✨ 주요 기능

#### 💎 공통 핵심 기능 (Core Logic)

* **데이터 모델링**: 모든 도형(센서, 분전반, 장치) 정보는 `ShapeVO` 객체로 관리됩니다. (종류, 좌표, 크기, 색상)
* **비즈니스 로직 분리**: UI(GUI/콘솔)와 데이터 관리 로직(`CadService`)을 명확하게 분리하여 유지보수성을 높였습니다.
* **데이터 영속성**: `ObjectOutputStream` / `ObjectInputStream`을 사용하여 프로그램 종료 시 `ArrayList<ShapeVO>` 객체를 `shapes.dat` 파일에 저장하고, 시작 시 자동으로 불러옵니다.

#### 🖥️ GUI 버전 (`MiniCadGui.java`)

* **동적 캔버스**: 마우스 이벤트를 감지하여 `JPanel` 위에 도형을 그립니다.
* **배경 도면 관리**: `JFileChooser`를 통해 JPG, PNG 등 이미지 파일을 불러와 캔버스 배경으로 설정할 수 있습니다.
* **실시간 상태 표시줄**: `Timer` (1초 간격)를 사용하여 하단 `JLabel`에 다음 정보를 실시간으로 업데이트합니다.
    * 총 도형 개수 (Total)
    * 종류별 도형 개수 (원○, 사각형□, 삼각형△)
    * 마지막 작업 정보 (종류, 번호, 좌표 등)
    * 현재 도면 파일명 및 현재 시각
* **일괄 삭제**: 'Clear' 버튼으로 캔버스의 모든 도형과 배경 이미지를 초기화합니다.

#### ⌨️ 콘솔 버전 (`CadConsoleMain.java` & `CadConsoleHandler.java`)

* **사용자 인증**: 프로그램 시작 시 간단한 로그인 기능을 제공합니다. (ID: `admin`, PW: `1234`)
* **메뉴 기반 인터페이스**: `Scanner`를 통해 사용자 입력을 받아 4가지 주요 기능을 실행합니다.
    1.  **도형 추가**: 좌표, 크기, 색상 정보를 입력받아 `CadService`에 전달합니다.
    2.  **도형 목록**: `CadService`의 `ArrayList`를 순회하며 `toString()`으로 정보를 출력합니다.
    3.  **도형 삭제**: 목록 번호(인덱스)를 기준으로 데이터를 삭제합니다.
    4.  **통계 보기**: `HashMap`을 활용하여 종류별 개수를 집계하고, 각 도형의 상세 정보를 포맷에 맞춰 출력합니다.

---

### 4. 🏗️ 시스템 아키텍처

본 프로젝트는 UI 레이어와 서비스 레이어를 분리하는 간단한 2-Tier 아키텍처를 따릅니다.
