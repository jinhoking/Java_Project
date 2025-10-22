package CAD_Prj; // 실제 패키지 이름 사용

import java.awt.BorderLayout;
import java.awt.FlowLayout; // FlowLayout 추가
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.Timer;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MiniCadGui {

    public static void main(String[] args) {
        JFrame frame = new JFrame("나의 미니 CAD (Ver 2.1 - 상세 정보)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setLayout(new BorderLayout());

        CadService cadService = new CadService(); // 서비스 객체 생성

        // --- 하단 상태표시줄 수정 ---
        JPanel statusBar = new JPanel(new BorderLayout());
        JLabel dateLabel = new JLabel();
        JLabel fileNameLabel = new JLabel("불러온 도면 없음");

        // [수정] 왼쪽 상태 정보를 담을 패널 (FlowLayout 사용)
        JPanel statusLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel totalCountLabel = new JLabel("Total: 0"); // 전체 개수
        JLabel circleCountLabel = new JLabel("○: 0");    // 원 개수
        JLabel rectCountLabel = new JLabel("□: 0");      // 사각형 개수
        JLabel triCountLabel = new JLabel("△: 0");       // 삼각형 개수
        JLabel lastShapeInfoLabel = new JLabel("Last: N/A"); // 마지막 도형 정보

        // 라벨들 사이에 간격 추가
        circleCountLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        rectCountLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        triCountLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        lastShapeInfoLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        statusLeftPanel.add(totalCountLabel);
        statusLeftPanel.add(circleCountLabel);
        statusLeftPanel.add(rectCountLabel);
        statusLeftPanel.add(triCountLabel);
        statusLeftPanel.add(lastShapeInfoLabel); // 마지막 정보 라벨 추가

        fileNameLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        statusBar.add(statusLeftPanel, BorderLayout.WEST);
        statusBar.add(fileNameLabel, BorderLayout.CENTER);
        statusBar.add(dateLabel, BorderLayout.EAST);

        // --- 캔버스 생성 수정 ---
        // [수정] 생성자에 모든 라벨 전달 (CadService 포함)
        CanvasPanel canvas = new CanvasPanel(
            cadService, totalCountLabel, circleCountLabel, rectCountLabel, triCountLabel,
            fileNameLabel, lastShapeInfoLabel
        );

        // ... (이후 버튼 패널 생성, 창 배치, 버튼 기능 연결, 타이머 코드는 모두 동일) ...
         JPanel buttonPanel = new JPanel();
         JButton loadButton = new JButton("도면 불러오기");
         JButton circleButton = new JButton("원 그리기(위치 센서)");
         JButton rectButton = new JButton("사각형 그리기(분전반 위치)");
         JButton triButton = new JButton("삼각형 그리기(안전 장치)");
         JButton clearButton = new JButton("전체 삭제");

         buttonPanel.add(loadButton);
         buttonPanel.add(circleButton);
         buttonPanel.add(rectButton);
         buttonPanel.add(triButton);
         buttonPanel.add(clearButton);

         frame.add(buttonPanel, BorderLayout.NORTH);
         frame.add(canvas, BorderLayout.CENTER);
         frame.add(statusBar , BorderLayout.SOUTH);

         loadButton.addActionListener(e -> canvas.loadImage());
         circleButton.addActionListener(e -> canvas.setCurrentShapeType("circle"));
         rectButton.addActionListener(e -> canvas.setCurrentShapeType("rectangle"));
         triButton.addActionListener(e-> canvas.setCurrentShapeType("triangle"));
         clearButton.addActionListener(e -> canvas.clearAllShapes()); // CanvasPanel의 clearAllShapes 호출

         new Timer(1000, e -> {
             String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
             dateLabel.setText(dateTime);
         }).start();

         frame.setVisible(true);
    }
}