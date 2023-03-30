package Exam3week;

import java.util.Scanner;

class StringInput {
    Scanner sc = new Scanner(System.in);

    String stext;
    int histo[] = new int[26]; // 알파벳 개수 만큼 배열 생성
    StringBuffer sb = new StringBuffer(); // 스트링 버퍼 생성

    public void Read() {

        while (true) {
            System.out.print("영문 텍스트를 입력하고 세미콜론을 입력하세요:");
            stext = sc.nextLine().toLowerCase(); // 입력 받은 내용을 전부 소문자로 바꾼다.

            if (stext.charAt(0) == ';') // charat 문자열 0번 인덱스가 ';'만 있는 라인이면
                break; // 입력 끝
            sb.append(stext); // 읽은 라인 문자열을 스트링버퍼에 추가한다.
        }
        System.out.println("스트링 버퍼의 내용: " + sb);

        String AfterBuffer = sb.toString();// 스트링 버퍼타입 -> 스트링 타입 변환
        String AfterToString = AfterBuffer.trim(); // 공백 제거
        System.out.println("String타입 -> 공백 제거후:" + AfterToString);// 체크

        for (int i = 0; i < AfterToString.length(); i++) { // String 크기 만큼 반복
            char c = AfterToString.charAt(i);// char형 변환 그리고 인덱스 0부터 char형으로 불러옴 c를
            if (c >= 'a' && c <= 'z') // c가 소문자 'a'보다 크거나 소문자'z'보다 작을때 true (a~z범위)
                histo[c - 'a']++;
            // histo[c에 만약 g가 들어왔다면 g(아스키코드103 - 아스키코드97<-a = 6 g는 순서상 histo[6]번째에 ++가 된다)]
            // histo[0]은 a 입력이 aa가 들어오면 histo[0]에(a자리) ++ (현재1) -> 두번째 histo[0](현재1) ++ ->
            // histo[0](현재2)
        }

        for (int i = 0; i < histo.length; i++) { // 알파벳 크기만큼 반복
            char c = (char) ('a' + i); // 'a부터 순서대로 1씩 증가시켜서'
            System.out.printf("%c: ", c); // 히스토그램 왼쪽에 알파벳 보여줄 용도로 출력
            for (int j = 0; j < histo[i]; j++) {
                System.out.print("-"); // 알파벳 개수만큼 '-' 출력
            }
            System.out.println();
        }

        // for(int i = 0; i < histo.length; i++){
        // System.out.println("배열테스트: "+histo[i]);
        // }

    }

}

public class Exam4 { //빈도분석법
    public static void main(String[] args) {
        StringInput si = new StringInput();
        si.Read();

    }

}
