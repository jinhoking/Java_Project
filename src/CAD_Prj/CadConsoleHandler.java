package CAD_Prj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CadConsoleHandler {
	
	private Scanner sc;
	private CadService cadService;
	
	
	public CadConsoleHandler(Scanner sc, CadService cadService) {
		super();
		this.sc = sc;
		this.cadService = cadService;
	}
	
	//도형 추가
	public void addShapeFromConsole() {
		System.out.println("\n[도형을 추가합니다]");
		System.out.print("도형 종류 (circle(위치 센서) / rectangle(분전반) / triangle(안전 장치)) : ");
		String type = sc.next().toLowerCase();
		
		if(!type.equals("circle") && !type.equals("rectangle") && !type.equals("triangle")) {
			type ="circle";
			System.out.println(">> 잘못된 타입 입력일 시,  기본 도형('원')으로 설정합니다.");
		}
		System.out.println("x 좌표 : ");
		int x = sc.nextInt();
		System.out.println("y 좌표 : ");
		int y = sc.nextInt();
		System.out.println("크기 : ");
		int size = sc.nextInt();
		sc.nextLine(); //버퍼 비우기
		
		//명령
		cadService.addShape(type, x, y, size);
		
		System.out.println(">> 도형이 도면에 추가되었습니다.");
	}
	
	//도형 목록 보기
	public void listShapeFromService() {
		ArrayList<ShapeVO> shapes = cadService.getShapeList();
		if(shapes.isEmpty()) {
			System.out.println(">> 도면에 그려진 도형이 없습니다.");
			return;
		}
		System.out.println("\n---전체 도형 목록 ---");
		for(int i =0; i<shapes.size(); i++) {
			System.out.println((i+1)+ ". " + shapes.get(i));
		}
	}
   //도형 삭제
	public void removeShapeFromConsole() {
		ArrayList<ShapeVO> shapes = cadService.getShapeList();
		if(shapes.isEmpty()) {
			System.out.println(">> 삭제할 도형이 없습니다.");
			return;
		}
		listShapeFromService();
		System.out.println("삭제할 도형의 번호를 입력하세요: ");
		int idex = sc.nextInt();
		sc.nextLine();
		
		//명령
		cadService.removeShape(idex);
		//결과
		System.out.println(">> 요청이 처리되었습니다.");
		
	}
	//통계 보기
	public void showStatistics() {
        cadService.LoadShapesFromFile(); // 최신 데이터 로드 (오타 수정 가정: loadShapesFromFile)
        ArrayList<ShapeVO> shapes = cadService.getShapeList();

        if (shapes.isEmpty()) {
            System.out.println(">> 통계를 낼 도형 데이터가 없습니다.");
            return;
        }

        int total = shapes.size();
        int circles = 0;
        int rectangles = 0;
        int triangles = 0;

        
        // 종류별 개수를 세기 위한 Map (키를 영어 타입명으로 수정)
        Map<String, Integer> countMap = new HashMap<>();
        countMap.put("원(센서 장치)", 0);
        countMap.put("사각형(분전반)", 0);
        countMap.put("삼각형(안전 장치)", 0);

        // 개수 계산
        for (ShapeVO shape : shapes) {
            String type = shape.getType();
            if (type != null && countMap.containsKey(type)) { // null 체크 및 유효 타입 확인
                countMap.put(type, countMap.get(type) + 1); // 해당 타입 개수 증가
            }
        }
        circles = countMap.get("원(센서 장치)");
        rectangles = countMap.get("사각형(분전반)");
        triangles = countMap.get("삼각형(안전 장치)");
      

        System.out.println("\n--- 도형(장치) 통계 ---");
        System.out.println("총 도형(장치) 개수: " + total);
        System.out.println(" - 원_위치 센서 (○): " + circles + "개");
        System.out.println(" - 사각형_분전반 (□): " + rectangles + "개");
        System.out.println(" - 삼각형_안전 장치 (△): " + triangles + "개");

        System.out.println("\n--- 각 도형 상세 정보 ---");
       
        // 각 종류별로 몇 번째 도형인지 추적하기 위한 Map (상세 정보 출력용)
        Map<String, Integer> detailCounter = new HashMap<>();
        detailCounter.put("circle", 0);
        detailCounter.put("rectangle", 0);
        detailCounter.put("triangle", 0);

        for (int i = 0; i < shapes.size(); i++) {
            ShapeVO shape = shapes.get(i);
            String type = shape.getType();

            // 타입 유효성 검사 및 번호 계산
            if (type != null && detailCounter.containsKey(type)) {
                int currentTypeCount = detailCounter.get(type) + 1; // 현재 타입 번호
                detailCounter.put(type, currentTypeCount); // 다음 번호를 위해 Map 업데이트

                System.out.printf("%s %d: (%d, %d), 크기: %d\n",
                                  capitalize(type), currentTypeCount, shape.getX(), shape.getY(), shape.getSize());
            } else {
                // 알 수 없는 타입 처리 (선택 사항)
                System.out.printf("알 수 없는 타입 [%s]: (%d, %d), 크기: %d\n",
                                  type, shape.getX(), shape.getY(), shape.getSize());
            }
        }
       
        System.out.println("--------------------");
    }
	
	private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
	}



