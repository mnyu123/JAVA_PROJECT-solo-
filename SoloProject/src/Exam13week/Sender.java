package Exam13week;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Sender extends Thread {
	private DataOutputStream dos;
	private String name;

	public Sender(Socket socket, String name) {
		this.name = name;

		try {
			dos = new DataOutputStream(socket.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		Scanner scan = new Scanner(System.in);
		while (dos != null) {
			try {
				String msg = scan.nextLine();
				if (msg.trim().equals("@quit") || msg.trim().equals("@종료")) {
					System.out.println("[멀티서버] 서버와의 연결이 종료되었습니다.");
					System.out.println("[멀티서버] 프로그램을 종료합니다.");
					System.exit(0);
				} else {
					dos.writeUTF(name + ">>>" + msg);
				}
			} catch (Exception e) {
				e.printStackTrace();
				break; // 스레드 종료
			}

		}
		scan.close();
	}

}
