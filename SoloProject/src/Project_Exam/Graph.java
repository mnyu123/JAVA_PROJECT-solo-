

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Graph extends JFrame {

    private List<Integer> data;

    public Graph() {
        setTitle("Graph Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);

        // 데이터 생성
         data = generateRandomNumbers(200, 200);
        
        // 생성된 난수 출력
         

        GraphPanel graphPanel = new GraphPanel();
        add(graphPanel);

        setVisible(true);
    }

    class GraphPanel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // 그래프 그리기
            int barWidth = getWidth() / data.size();
            int maxHeight = getHeight() - 10; // 그래프의 최대 높이를 패널의 높이보다 20px 작게 설정
            int x = 0;

            for (Integer value : data) {
                int barHeight = maxHeight * value / 200; 
                int y = getHeight() - barHeight;

                g.setColor(Color.BLUE);
                g.fillRect(x, y, barWidth, barHeight);

                g.setColor(Color.BLACK);
                g.drawRect(x, y, barWidth, barHeight);

                x += barWidth;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Graph::new);
    }
    private static List<Integer> generateRandomNumbers(int size, int range) {
        List<Integer> numbers = new ArrayList<>();
        Random random = new Random();
        
        // 중복 없이 난수 생성
        while (numbers.size() < size) {
            int randomNumber = random.nextInt(range) + 1;
            
            // 중복 체크
            if (!numbers.contains(randomNumber)) {
                numbers.add(randomNumber);
            }
        }
        
        return numbers;
    }
}