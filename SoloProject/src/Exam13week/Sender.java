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
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		while (dos != null) {
			try {
				dos.writeUTF(name + ">>>" + scan.nextLine());
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				interrupt(); //스레드 종료
			}
		}
		scan.close();
	}

}
