package Exam9week;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Exam9week {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        File f = new File("test4.txt");

        try {

            if (!f.exists()) {
                f.createNewFile();

                // fi.write(b);

                System.out.println("파일에 str내용 썼다.");

            } else {
                System.out.println("파일 생성 X");
                String b = "";
                FileOutputStream fi = new FileOutputStream(f);

                for (int h = 1; h <= 3; h++) {
                    for (int i = 1; i <= 9; i++) {
                        String str = "";
                        for (int j = (h - 1) * 3 + 1; j <= h * 3; j++) {
                            str += j + "X" + i + "=" + (i * j) + "\t";
                        }
                        byte[] b1 = str.getBytes();
                        fi.write(b1);
                    }
                }

                System.out.println("파일의 크기:" + f.length());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
