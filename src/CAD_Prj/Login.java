package CAD_Prj;

import java.util.Scanner;

public class Login extends Object{
	
	//로그인
		String userId;
		String userPw;
		
		String inputId;
		String inputPw;
		
public Login() {
	this.userId = "admin";
	this.userPw = "1234";
}		
public boolean loginCheck(Scanner sc) {
	System.out.println("--- 미니 CAD(콘솔) 로그인 ---");
	System.out.println("아이디: ");
	userId = sc.next();
	System.out.println("비밀번호 : ");
	userPw = sc.next();
	sc.nextLine();
	
	return "admin".equals(userId) && "1234".equals(userPw);
}

}
