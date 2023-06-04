package Exam4week;
import java.util.Random;
import java.util.Scanner;

public class Exam2 {
    public static void main(String[] args) {
        Random r = new Random();
        int hiddenNum = r.nextInt(100); // 숨겨진 숫자

        Scanner sc = new Scanner(System.in);
        int num1 = 0;
        do {
            System.out.print("숫자를 입력하세요: ");
            num1 = sc.nextInt(); // 내가 입력하는 숫자

            if (num1 > hiddenNum) {
                System.out.println("입력한 숫자가 더 큽니다.");

            } 
            else if (num1 < hiddenNum) {
                System.out.println("숨겨진 숫자가 더 큽니다.");
            }
        } while (num1 != hiddenNum);
        {
            System.out.println("찾았습니다.");
        }

    }
}
