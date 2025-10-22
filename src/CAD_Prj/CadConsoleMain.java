package CAD_Prj;

import java.util.Scanner;

public class CadConsoleMain {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		CadService cadService = new CadService();
	    Login login = new Login();
		CadConsoleHandler consoleHandler = new CadConsoleHandler(sc, cadService);
		boolean isRunning = true;
		
	if(!login.loginCheck(sc)) {
		System.out.println(">>로그인 실패. 프로그램을 종료합니다.");
		sc.close();
		return;
	}
	System.out.println(">>로그인 성공!");
		
	
		
		while(isRunning) {
			System.out.println("\n===== 미니 CAD (콘솔 - Service Ver) =====");
			System.out.println("1. 도형 추가");
			System.out.println("2. 도형 목록 보기");
			System.out.println("3. 도형 삭제");
			System.out.println("4. 통계 보기");
			System.out.println("5. 종료");
			System.out.println("선택>> ");
			String menu = sc.next();
			
			switch (menu) {
			case "1": {
				consoleHandler.addShapeFromConsole();
				break;
				}
			case "2": {
				cadService.LoadShapesFromFile();
				consoleHandler.listShapeFromService();
				break;
			}
			case "3": {
				consoleHandler.removeShapeFromConsole();
				cadService.saveShapeToFile();
				break;
			}
			case "4": {
				consoleHandler.showStatistics();
				break;
				
			}	
			case "5": {
				cadService.saveShapeToFile();
				isRunning = false;
				System.out.println("프로그램을 종료합니다.");
				break;
				
			}
			default:
				System.out.println(">> 잘못된 메뉴입니다. 다시 입력해주세요.");
				break;
			}
			
		}

	}



}
