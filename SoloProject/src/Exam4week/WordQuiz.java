package Exam4week;

import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

class Word {
    String English;
    String Korean;

    public Word(String english, String korean) {
        English = english;
        Korean = korean;
    }

    @Override
    public String toString() {
        return English + " : " + Korean;
    }

    public String getEnglish() {
        return English;
    }

    public String getKorean() {
        return Korean;
    }

}

public class WordQuiz {
    public static void main(String[] args) {
        Vector<Word> v = new Vector<Word>();

        Random random = new Random();
        Scanner sc = new Scanner(System.in);

        v.add(new Word("love", "사랑"));
        v.add(new Word("animal", "동물"));
        v.add(new Word("bear", "곰"));
        v.add(new Word("painting", "그림"));
        v.add(new Word("eye", "눈"));
        v.add(new Word("picture", "사진"));
        v.add(new Word("society", "사회"));
        v.add(new Word("human", "인간"));
        v.add(new Word("baby", "아기"));
        v.add(new Word("error", "오류"));
        v.add(new Word("doll", "인형"));
        v.add(new Word("emotion", "감정"));
        v.add(new Word("status", "조각상"));
        v.add(new Word("fish", "물고기"));
        v.add(new Word("pig", "돼지"));
        v.add(new Word("world", "세상"));
        v.add(new Word("word", "단어"));
        v.add(new Word("bank", "은행"));

        System.out.println("명품영어의 단어테스트를 시작합니다.");
        System.out.println("-1을 입력하면 종료합니다.");

        while (true) {
            int randomindex = random.nextInt(v.size());
            Word word = v.get(randomindex);

            System.out.println("다음 영어단어의 뜻은?: " + word.getEnglish());

            Vector<Integer> koreanIndex = new Vector<Integer>();
            koreanIndex.add(randomindex);
            for (int i = 0; i < 3; i++) {
                int index;
                do {
                    index = random.nextInt(v.size());
                } while (koreanIndex.indexOf(index) != -1);
                koreanIndex.add(index);
            }
            koreanIndex.sort(null);
            System.out.print("(1)" + v.get(koreanIndex.get(0)).getKorean() + " ");
            System.out.print("(2)" + v.get(koreanIndex.get(1)).getKorean() + " ");
            System.out.print("(3)" + v.get(koreanIndex.get(2)).getKorean() + " ");
            System.out.print("(4)" + v.get(koreanIndex.get(3)).getKorean() + " : ");

            String input = sc.nextLine(); // 사용자 입력

            if (input.equals("-1")) {
                System.out.println("명품 영어를 종료합니다.");
                break;
            } else if (input.equals(Integer.toString(koreanIndex.indexOf(randomindex) + 1))) {
                System.out.println("정답입니다.");
            } else {
                System.out.println("틀렸습니다. 정답은 " + (koreanIndex.indexOf(randomindex) + 1));
            }
        }
    }
}