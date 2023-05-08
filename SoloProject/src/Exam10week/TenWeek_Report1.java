package Exam10week;

import java.io.*;

public class TenWeek_Report1 {
    public static boolean IsFileCopy(String InFilePath, String OutFilePath) {
        File InFile = new File(InFilePath);
        File OutFile = new File(OutFilePath);
        String str = "복사할 내용";
        try {
            File f = new File(InFilePath);

            if (!f.exists()) {
                f.createNewFile();
            }
            FileInputStream fis = new FileInputStream(f);

            int i = 0;

            while ((i = fis.read()) != -1) {
                System.out.print((char) i);
            }
            fis.close();
            System.out.println("파일 작성");
        } catch (Exception e) {
            e.getMessage();
            return false;
        }

        try {
            File f = new File(OutFilePath);

            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(f);

            byte[] bytes = str.getBytes();

            fos.write(bytes);
            fos.close();
            System.out.println("파일 복사");
        } catch (Exception e) {
            e.getMessage();
            return false;
        }
        return true;

    }

    public static void main(String[] args) {
        String InFilePath = "C:\\Users\\user\\Desktop\\JAVA\\JAVA_PROJECT-solo-\\SoloProject\\src\\Exam10week\\TenWeek_Report1.java";
        String OutFilePath = "C:\\Users\\user\\Desktop\\JAVA\\JAVA_PROJECT-solo-\\SoloProject\\src\\Exam10week\\TenWeek_Report1_copy.java";

        if (IsFileCopy(InFilePath, OutFilePath)) {
            System.out.println("파일 복사 성공");
        } else {
            System.out.println("파일 복사 실패");
        }
    }
}
