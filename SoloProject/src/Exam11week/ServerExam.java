package Exam11week;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerExam {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerSocket serversocket = null;
		Socket socket = null;
		OutputStream ops = null;
		DataOutputStream dos = null;

		System.out.println("t");
		Scanner sc = new Scanner(System.in);

		try {
			serversocket = new ServerSocket(8080);
			System.out.println("서버 실행 | 클라이언트 대기중: ");
			socket = serversocket.accept();
			System.out.println("클라이언트가 접속했습니다.");
			System.out.println("클라이언트 IP: " + socket.getInetAddress());

			ops = socket.getOutputStream();
			dos = new DataOutputStream(ops);

			try {
				while (true) {
					System.out.println("q를 누르면 종료 보낼 데이터는?:");
					String str = sc.nextLine();

					dos.writeUTF(str);

					if (str.equals("q")) {
						break;
					}
				}
			} catch (IOException e) {
				System.out.println("에러");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				dos.flush();
				dos.close();
				ops.close();
				socket.close();
				serversocket.close();
				System.out.println("연결 종료");

			} catch (IOException e) {
				System.out.println("소켓 통신 에러");
			}
		}
	}

}
