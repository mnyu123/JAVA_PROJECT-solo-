package Exam13week;

import java.io.DataInputStream;
import java.net.Socket;

public class Receiver extends Thread {
	private DataInputStream dis;

	public Receiver(Socket socket) {
		try {
			dis = new DataInputStream(socket.getInputStream());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (dis != null) {
			try {
				System.out.println(dis.readUTF());
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				interrupt();
			}
		}
	}

}