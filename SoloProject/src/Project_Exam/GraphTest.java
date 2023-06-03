// int형 숫자인 2,0,1,8,3,2,7,7 이 숫자들의 크기에 따라서 Jframe을 이용해 막대 그래프를 그리는 프로그램
// 입력은 받지 않으며 , 배열도 사용하지 않음.
// 각 숫자의 크기에 따라 그래프의 길이가 달라야함.
// 20183277 이라는 숫자를 입력받아서 그래프를 그리는 프로그램을 만들어보자
// 2는 길이가 20 , 0은 0 1은 10 8은 80 3은 30 2는 20 7은 70 7은 70 이렇게 그래프를 그려보자



import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

public class GraphTest extends JFrame {
    public GraphTest() {
        setTitle("GraphTest");
        setSize(500, 500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawLine(50, 400, 450, 400);
        g.drawLine(50, 400, 50, 50);
        g.drawString("0", 40, 410);
        g.drawString("1", 90, 410);
        g.drawString("2", 140, 410);
        g.drawString("3", 190, 410);
        g.drawString("4", 240, 410);
        g.drawString("5", 290, 410);
        g.drawString("6", 340, 410);
        g.drawString("7", 390, 410);
        g.drawString("8", 440, 410);
        g.drawString("9", 490, 410);
        g.drawString("20183277", 200, 45);
        g.setColor(Color.RED);
        g.fillRect(90, 400 - 10 * 2, 20, 10 * 2);
        g.setColor(Color.BLUE);
        g.fillRect(140, 400 - 10 * 0, 20, 10 * 0);
        g.setColor(Color.GREEN);
        g.fillRect(190, 400 - 10 * 1, 20, 10 * 1);
        g.setColor(Color.YELLOW);
        g.fillRect(240, 400 - 10 * 8, 20, 10 * 8);
        g.setColor(Color.ORANGE);
        g.fillRect(290, 400 - 10 * 3, 20, 10 * 3);
        g.setColor(Color.PINK);
        g.fillRect(340, 400 - 10 * 2, 20, 10 * 2);
        g.setColor(Color.CYAN);
        g.fillRect(390, 400 - 10 * 7, 20, 10 * 7);
        g.setColor(Color.MAGENTA);
        g.fillRect(440, 400 - 10 * 7, 20, 10 * 7);
    }

    public static void main(String[] args) {
        new GraphTest();
    }
}
