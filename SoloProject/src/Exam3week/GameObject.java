package Exam3week; // 정렬 ctrl + k + f

import java.util.Random;
import java.util.Scanner;

// Bear , Fish 객체 따로따로
// 10행 20열 = arr[10][20] -> 이차원배열 계산
// Bear는 사용자 입력으로 한칸씩 움직임
// Fish는 5번중 세번은 안움직임 , 나머지 두번은 랜덤으로 아무방향으로 움직임
// Bear가 Fish위치로 이동하면 성공하고 프로그램 바로 종료

// move()는 각 Fish와 Bear의 움직임 정의
// getShape()는 각 객체의 모양을 정의
// collide메소드는 충돌하면 true , 아니면 false

public abstract class GameObject {

    protected int distance; // 한번의 이동거리
    protected int x, y; // 현재 Bear의 위치

    public GameObject(int distance, int startX, int startY) { // 1,0,0 그리고 1,6,5
        this.distance = distance; // 1,1
        this.x = startX; // bear -> 0 , fish -> 6
        this.y = startY; // bear -> 0 , fish -> 5
    }

    public int getX() { // 현재 위치의 x값을 반환
        return x;
    }

    public int getY() { // 현재 위치의 y값을 반환
        return y;
    }

    public boolean collide(GameObject p) { // GameObject의 객체 변수가 p임
        if (this.x == p.getX() && this.y == p.getY()) { // 현재위치 x값과 getX에서 가져온 x값
                                                        // 현재위치 y값과 getY에서 가져온 y값
                                                        // 둘이 서로 같다면 true(즉,충돌했다)
            return true;
        } else
            return false; // boolean메소드는 반환값은 true,false 둘중 하나
    }

    protected abstract void move(); // 이동하는 내용을 정의한 메소드를 만들어야함

    protected abstract String getShape(); // 객체(Bear,Fish)의 모양을 나타내는 문자를 리턴
    // 이 위의 내용은 GameObject 클래스 내용임

}

class Bear extends GameObject {

    Scanner sc = new Scanner(System.in);

    public Bear(int distance, int startX, int startY) {
        super(distance, startX, startY);
    }

    @Override
    public void move() { // 메소드 오버라이딩
        System.out.println("곰이 이동함");

        System.out.print("곰을 이동시키려면 w,a,s,d중 하나를 입력하세요: ");
        char player = sc.next().charAt(0); // 사용자 입력 w,a,s,d 받을거임

        switch (player) {
            case 'w':
                System.out.println("*****얘는 위쪽으로 가는거*****");

                if (this.x >= this.distance) {
                    this.x -= this.distance;
                }
                break;
            case 'a':
                System.out.println("*****얘는 왼쪽으로 가는거*****");

                if (this.y >= distance) {
                    this.y -= this.distance;
                }
                break;
            case 's':
                System.out.println("*****얘는 아래로 가는거*****");

                if (this.x < 10 - this.distance) {
                    this.x += this.distance;
                }

                break;
            case 'd':
                System.out.println("*****얘는 오른쪽으로 가는거*****");

                if (this.y < 20 - this.distance) {
                    this.y += this.distance;
                }

                break;
        }

    }

    @Override
    public String getShape() { // Bear는 B로 정해짐
        return "B";
    }

}

class Fish extends GameObject {

    Random random = new Random(); // 0~4의 랜덤한 값 나옴
    int temp = 0;
    int MoveArray[] = new int[5];
    int count = 0;

    public Fish(int distance, int startX, int startY) {
        super(distance, startX, startY);
    }

    public void FishGame() {
        temp = 0;

        for (int i = 0; i < MoveArray.length; i++) {
            MoveArray[i] = random.nextInt(5);

            if (MoveArray[i] == 4) {
                temp++;
            }

            if (temp == 3) {
                break;
            }
        }

    }

    @Override
    protected void move() {
        System.out.println("생선이 이동함");

        if (count == 5) {
            count = 0;
            FishGame();
        } else if (count == 0) {
            FishGame();
        }

        switch (MoveArray[count]) {
            case 0:
                System.out.println("*****얘는 위쪽으로 가는거*****");

                if (this.x >= this.distance) {
                    this.x -= this.distance;
                }
                
                break;
            case 1:
                System.out.println("*****얘는 왼쪽으로 가는거*****");

                if (this.y >= distance) {
                    this.y -= this.distance;
                }
                
                break;
            case 2:
                System.out.println("*****얘는 아래로 가는거*****");

                if (this.x < 10 - this.distance) {
                    this.x += this.distance;
                }
                
                break;
            case 3:
                System.out.println("*****얘는 오른쪽으로 가는거*****");

                if (this.y < 20 - this.distance) {
                    this.y += this.distance;
                }
                
            case 4:
                break;
        }
        count++;

    }

    @Override
    protected String getShape() {
        return "@";
    }

}

class Game {

    private Bear bear;
    private Fish fish;

    public Game() {
        bear = new Bear(1, 0, 0);
        fish = new Fish(1, 5, 6);
    }

    public void PrintBoard() {
        while (true) {
            System.out.println("*****************");
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 20; j++) {
                    if (bear.getX() == i && bear.getY() == j) {
                        System.out.print(bear.getShape());
                    } else if (fish.getX() == i && fish.getY() == j) {
                        System.out.print(fish.getShape());
                    } else
                        System.out.print('-');
                }
                System.out.println(" ");
            }

            bear.move();
            fish.move();

            if (bear.collide(fish)) {
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 20; j++) {
                        if (bear.getX() == i && bear.getY() == j)
                            System.out.print(bear.getShape());
                        else
                            System.out.print("-");
                    }
                    System.out.println();
                }
                System.out.println("게임종료");
                break;
            }
        }

    }

    public static void main(String[] args) {
        Game game = new Game();
        game.PrintBoard();
    }

}
