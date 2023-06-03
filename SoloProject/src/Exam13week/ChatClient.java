package Exam13week;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ChatClient {

	public static void main(String[] args) {
		try {
			Socket socket = new Socket("localhost", 7777);
			System.out.println("[클라이언트] 서버에 연결 되었습니다.");
			System.out.println("[클라이언트] 클라이언트의 소켓 : " + socket);
			String name = "";

			while (true) {
				System.out.print("[클라이언트] 사용할 ID를 입력해 주세요: >>> ");
				Scanner scan = new Scanner(System.in);

				name = scan.next();

				/**
				 * 여러 사용자일때 ID가 각자 누구인지 알려주기 위해 존재
				 */
				DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
				dos.writeUTF(name); // ID를 보내준다
				dos.flush(); // 메모리 해제

				DataInputStream dis = new DataInputStream(socket.getInputStream());
				String message = dis.readUTF();

				if (message.equals("!errCode404")) {
					System.out.println("[클라이언트] 채팅방에 같은 이름이 존재합니다.");
				} else {
					break;
				}
			}

			Sender sender = new Sender(socket, name);
			Receiver receiver = new Receiver(socket);

			sender.start();
			receiver.start();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
