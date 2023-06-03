import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ScanGraph {
    public static void main(String[] args) {
        // 입력 받기
        Scanner scanner = new Scanner(System.in);
        System.out.print("막대그래프의 개수를 입력하세요: ");
        int count = scanner.nextInt();

        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            System.out.print("숫자 " + i + "을 입력하세요: ");
            int number = scanner.nextInt();
            numbers.add(number);
        }

        // 그래프 그리기
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 700);
        frame.setTitle("입력받은 값에 따라 크기를 그래프로 그리기");

        ScanGraphPanel panel = new ScanGraphPanel(numbers);
        frame.add(panel);

        frame.setVisible(true);
    }
}

class ScanGraphPanel extends JPanel {
    private List<Integer> numbers;

    public ScanGraphPanel(List<Integer> numbers) {
        this.numbers = numbers;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawString("0", 40, 20);
        g.drawString("1", 90, 20);
        g.drawString("2", 140, 20);
        g.drawString("3", 190, 20);
        g.drawString("4", 240, 20);
        g.drawString("5", 290, 20);
        g.drawString("6", 340, 20);
        g.drawString("7", 390, 20);
        g.drawString("8", 440, 20);
        g.drawString("9", 490, 20);
        g.drawString("10", 540, 20);
        g.drawString("11", 590, 20);
        g.drawString("12", 640, 20);
        g.drawString("13", 690, 20);
        

        int width = getWidth();
        int height = getHeight();

        int GraphWidth = width / numbers.size(); // 막대의 너비 설정

        for (int i = 0; i < numbers.size(); i++) {
            int number = numbers.get(i);
            int GraphHeight = number * height / 30; // 숫자에 따라 막대의 높이 조절
            int x = i * GraphWidth; // x 좌표 계산
            int y = height - GraphHeight; // 아래에서부터 막대를 그리기 위한 y 좌표 계산

            switch (i) {
                case 1:
                    g.setColor(Color.RED);
                    break;
                case 2:
                    g.setColor(Color.ORANGE);
                    break;
                case 3:
                    g.setColor(Color.YELLOW);
                    break;
                case 4:
                    g.setColor(Color.GREEN);
                    break;
                case 5:
                    g.setColor(Color.BLUE);
                    break;
                case 6:
                    g.setColor(Color.MAGENTA);
                    break;
                case 7:
                    g.setColor(Color.PINK);
                    break;
                case 8:
                    g.setColor(Color.CYAN);
                    break;
                default:
                    g.setColor(Color.lightGray);
                    break;
            }

            g.fillRect(x, y, GraphWidth, GraphHeight);
        }
    }
}