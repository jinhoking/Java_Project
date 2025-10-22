package CAD_Prj; // 실제 패키지 이름 사용

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException; // IOException 추가
import java.util.ArrayList; // ArrayList는 VO 타입을 위해 사용
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

class CanvasPanel extends JPanel {

    private CadService cadService; // 서비스 객체 참조
    private String currentShapeType = "circle";
    private BufferedImage backgroundImage = null;

    // 라벨 참조 변수들
    private JLabel totalCountRef, circleCountRef, rectCountRef, triCountRef;
    private JLabel fileNameRef;
    private JLabel lastShapeInfoRef;

    // 생성자: 모든 라벨과 CadService 객체를 전달받음
    public CanvasPanel(CadService service, JLabel total, JLabel circle, JLabel rect, JLabel tri,
                       JLabel file, JLabel lastInfo) {
        this.cadService = service; // 서비스 연결
        this.totalCountRef = total;
        this.circleCountRef = circle;
        this.rectCountRef = rect;
        this.triCountRef = tri;
        this.fileNameRef = file;
        this.lastShapeInfoRef = lastInfo;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Service에 도형 추가 요청
                cadService.addShape(currentShapeType, e.getX(), e.getY(), 25);
                updateStatusLabels(); // 상태 라벨 전체 업데이트
                cadService.saveShapeToFile(); // 파일 저장
                repaint();
            }
        });
    }

    // 도형 삭제 및 상태 업데이트
    public void clearAllShapes() {
        cadService.clearAllShapes();
        cadService.saveShapeToFile(); // 파일 저장
        fileNameRef.setText("불러온 도면 없음");
        updateStatusLabels(); // 라벨 리셋
        repaint();
    }

    // 현재 그릴 도형 타입 설정
    public void setCurrentShapeType(String type) {
        this.currentShapeType = type;
    }

    // 이미지 불러오기
    public void loadImage() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "이미지 파일 (JPG, PNG)", "jpg", "png");
        chooser.setFileFilter(filter);

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = chooser.getSelectedFile();
                backgroundImage = ImageIO.read(file);
                fileNameRef.setText("도면: " + file.getName());

                cadService.clearAllShapes(); // 기존 도형 삭제
                updateStatusLabels(); // 라벨 업데이트

            } catch (IOException e) { // IOException 명시
                 System.out.println("이미지 불러오기 실패 : " + e.getMessage());
                 backgroundImage = null;
                 fileNameRef.setText("도면 불러오기 실패");
                 updateStatusLabels();
            }
            repaint();
        }
    }

    // 모든 상태 라벨 업데이트
    private void updateStatusLabels() {
        ArrayList<ShapeVO> shapes = cadService.getShapeList(); // 서비스에서 목록 가져오기
        int total = shapes.size();
        int circles = 0;
        int rectangles = 0;
        int triangles = 0;

        // 도형 종류별 개수 세기
        for (ShapeVO shape : shapes) {
            String type = shape.getType();
            if ("circle".equals(type)) circles++;
            else if ("rectangle".equals(type)) rectangles++;
            else if ("triangle".equals(type)) triangles++;
        }

        // 라벨 텍스트 설정
        totalCountRef.setText("Total: " + total);
        circleCountRef.setText("○: " + circles);
        rectCountRef.setText("□: " + rectangles);
        triCountRef.setText("△: " + triangles);

        // 마지막 도형 정보 설정
        if (!shapes.isEmpty()) {
            ShapeVO lastShape = shapes.get(total - 1); // 마지막 도형 가져오기
            String type = lastShape.getType();
            int x = lastShape.getX();
            int y = lastShape.getY();
            int size = lastShape.getSize();

            // 해당 타입의 몇 번째 도형인지 계산 (현재 개수와 동일)
            int typeIndex = 0;
            if ("circle".equals(type)) typeIndex = circles;
            else if ("rectangle".equals(type)) typeIndex = rectangles;
            else if ("triangle".equals(type)) typeIndex = triangles;

            String info = String.format("Last: %s %d (%d, %d) Size:%d",
                                        capitalize(type), typeIndex, x, y, size);
            lastShapeInfoRef.setText(info);
        } else {
            lastShapeInfoRef.setText("Last: N/A");
        }
    }

    // 첫 글자 대문자 변환 헬퍼
    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    // 화면 그리기 (paintComponent)
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
        // Service에서 목록 가져와서 그리기
        for (ShapeVO shape : cadService.getShapeList()) {
            g.setColor(shape.getColor());
            int x = shape.getX();
            int y = shape.getY();
            int size = shape.getSize();
            String type = shape.getType();

            if ("circle".equals(type)) {
                g.fillOval(x - size / 2, y - size / 2, size, size);
            } else if ("rectangle".equals(type)) {
                g.fillRect(x - size / 2, y - size / 2, size, size);
            } else if ("triangle".equals(type)) {
                int[] xPoints = { x, x - size / 2, x + size / 2 };
                int[] yPoints = { y - size / 2, y + size / 2, y + size / 2 };
                g.fillPolygon(xPoints, yPoints, 3);
            }
        }
    }
}