package Exam12week_advence;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Example33 {
	Socket clientSocket;
	PrintWriter pw;

	public Example33(String name) {
		System.out.println("#[" + name + "] 클라이언트가 서버에 접속하는 중입니다.");
		try {
			InetAddress localAddress = InetAddress.getLocalHost();
			clientSocket = new Socket(localAddress, 10000);
			pw = new PrintWriter(clientSocket.getOutputStream(), true);
			System.out.println("#[" + name + "] 클라이언트가 서버에 연결됨.");
			pw.println(name); // println이 아니면 서버의 readline()에서 멈춰 있음(줄 단위로 가져오는것이 아니게 되므로)
		} catch (Exception e) {
			System.out.println("오류 발생 지점 3");
		}
	}

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		String[] array = { "영재", "진우", "승진", "스레드", "반복" };
		for (int i = 0; i < 5; i++) {
			final int index = i;
			new Thread(() -> {
				new Example33(array[index]); //스레드 5개 반복으로 생성
			}).start();
			Thread.sleep(1000);
		}
	}

}
