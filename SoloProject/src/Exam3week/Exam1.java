package Exam3week;
import java.util.Random;
import java.util.Scanner;

public class Exam1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("행의 수를 입력하세요: ");
        int num1 = sc.nextInt(); // 행 입력 받는 숫자
        int num2 = 0;
        int sum = 0; // 평균값 계산하는 변수

        Random r = new Random();

        int Array[][] = new int[num1][]; //

        for (int i = 0; i < Array.length; i++) {
            System.out.print("각 열의 수를 입력하세요: ");
            num2 = sc.nextInt(); // 열의 수를 입력받는 숫자 num2
            Array[i] = new int[num2]; // new 타입[숫자]
            // 이러면 Array[0] = new int[2]
            // Array[1] = new int[3]
            // 2행 (2,3) 열 2차원 배열

            for (int j = 0; j < Array[i].length; j++) { // 배열 Array[0],Array[1] 각 행의 길이가 달라서 그 크기만큼만 반복해야함 , 비정형 배열
                // 이제 열의 수 만큼 반복
                // Array[0].length = 0,1 -> 2크기만큼 반복
                // Array[1].length = 0,1,2 -> 3크기만큼 반복
                Array[i][j] = r.nextInt(50); // 각 행,렬에 랜덤값 삽입
                sum += Array[i][j]; // sum에 각 행,열의 모든 원소값 이중 루프를 돌면서 덧셈 연산
            }

            System.out.println("평균값은: " + (double) sum / num2);
            System.out.println("각 열의 원소의 합은: " + num2);
            sum = 0;
        }

        sc.close();
    }
}
