package CAD_Prj;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class CadService {
	
	private ArrayList<ShapeVO> shapelist = new ArrayList<ShapeVO>();
	private String dataFilePath = "shape.txt";
	
	public CadService() {
		LoadShapesFromFile();
	}



	//타입 공유
	public void addShape(String type, int x, int y, int size) {
		Color color;
		if("circle".equals(type)) {
			color = Color.BLUE;
		}else if("rectangle".equals(type)) {
			color = Color.RED;
		}else {
			color = Color.YELLOW;
		}
		ShapeVO newShape = new ShapeVO(type, x, y, size, color);
		shapelist.add(newShape);
	}
	//리셋
	public void clearAllShapes() {
		shapelist.clear();
		
	}
	//list 공유
	public ArrayList<ShapeVO>getShapeList() {
		return shapelist;
		
	}
	//list 목록 중 삭제
	public void removeShape(int index) {
		if(index > 0 && index < shapelist.size()) {
			shapelist.remove(index);
		}
		
	}
	
	public void saveShapeToFile() {
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dataFilePath))) {
			oos.writeObject(shapelist);
		}catch (IOException e) {
			System.out.println("파일 저장 오류: "+e.getMessage());
			
		}
	}
	

	@SuppressWarnings("unchecked") // 타입 변환 경고 무시
	public void LoadShapesFromFile() {
		File file = new File(dataFilePath);
		if(file.exists()) {
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dataFilePath))) {
				shapelist = (ArrayList<ShapeVO>) ois.readObject();
				
			} catch (IOException | ClassNotFoundException e) {
				System.out.println("파일 불러오기 오류: "+ e.getMessage());
				shapelist = new ArrayList<ShapeVO>(); //오류 시 빈 목록으로 시작
				
			}
		}else {
			shapelist = new ArrayList<ShapeVO>(); //파일 없으면 빈 목록으로 시작
		}
		
		
	}


}
