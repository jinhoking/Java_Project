package CAD_Prj;

import java.awt.Color;
import java.io.Serializable;

public class ShapeVO implements Serializable{
	
	private String type;
	private int x, y, size;
	private Color color; // GUI를 위해 Color 타입도 추가

	public ShapeVO(String type, int x, int y, int size, Color color) {
		super();
		this.type = type;
		this.x = x;
		this.y = y;
		this.size = size;
		this.color = color;
	}
	

	public String getType() {
		return type;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getSize() {
		return size;
	}

	public Color getColor() {
		return color;
	}

	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("1.종류 : ").append(type).append("\n");
		sb.append("2.위치 : (").append(x).append(", ").append(y).append(")\n");
		sb.append("3.크기 : ").append(size).append("\n");
		sb.append("----------------------");
		
		return sb.toString();
		
		
		
	}
}
