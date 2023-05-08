package Exam11week;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientExam {

	public static void main(String[] args) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
		Socket socket = null;
		InputStream is = null;
		DataInputStream dis = null;
		try {
			socket = new Socket("127.0.0.1", 8080);
			System.out.println("서버에 접속되었음.");

			is = socket.getInputStream();
			dis = new DataInputStream(is);

			// String str = dis.readUTF();

			try {
				for (int i = 0; i < 7; i++) {
					String str = dis.readUTF();
					System.out.println("서버 메시지: " + str);

					if (str.equals("q")) {
						break;
					}

				}
			} finally {
				try {
					dis.close();
					is.close();
					socket.close();
					System.out.println("연결 종료");
				} catch (IOException e) {
					System.out.println("소켓통신 에러");
				}
			}
		} finally {

		}
	}
}