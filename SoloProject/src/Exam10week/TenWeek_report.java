package Exam10week;

import java.io.*;

public class TenWeek_report {

	public static void main(String[] args) {
		IsFileCopy(null, null);
		System.out.println("파일 복사 시작");
	}

	public static boolean IsFileCopy(String InFilePath, String OutFilePath) {
		// TODO Auto-generated method stub
		InFilePath = "test7.txt";
		OutFilePath = "test7-복사본.txt";
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
		}

		try {
			File f = new File(OutFilePath);
			if (!f.exists()) {
				f.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(f);

			byte[] b = str.getBytes();
			fos.write(b);
			fos.close();
			System.out.println("파일 복사 성공");

		} catch (Exception e) {
			e.getMessage();
		}
		return false;
	}

	

}