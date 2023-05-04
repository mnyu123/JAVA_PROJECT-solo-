package Exam10week;

import java.io.*;

public class TenWeek_report {
    public static void main(String[] args) {
        String InFilePath = "C:\\Users\\whdud\\Desktop\\고급자바프로그래밍\\테스트\\Test1.txt";
        String OutFilePath = "C:\\Users\\whdud\\Desktop\\고급자바프로그래밍\\테스트\\Test1-copy.txt";

        if (IsFileCopy(InFilePath, OutFilePath)) {
            System.out.println("파일 복사 성공");
        } else {
            System.out.println("파일 복사 실패");
        }
    }

    public static boolean IsFileCopy(String InFilePath, String OutFilePath) {
        File InFile = new File(InFilePath);
        File OutFile = new File(OutFilePath);

        String str = " ";

        try {
            FileInputStream fis = new FileInputStream(InFile);
            FileOutputStream fos = new FileOutputStream(OutFile);

            if (!InFile.exists()) {
                InFile.createNewFile();
            }

            if (!OutFile.exists()) {
                OutFile.createNewFile();
            }

            byte[] b = str.getBytes();
            int length;
            while ((length = fis.read(b)) > 0) {
                fos.write(b, 0, length);
            }

            fis.close();
            fos.close();

            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }
}
