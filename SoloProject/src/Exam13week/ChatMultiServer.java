package Exam13week;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 메인 메소드
 * 
 * @author WKU
 *
 */
public class ChatMultiServer {
	static HashMap<String, DataOutputStream> ClientMap; // 이름,ID값 받기 위해 해쉬맵 사용

	public static void main(String[] args) {
		// 스레드 생성 7개로 제한
		ExecutorService executorService = Executors.newFixedThreadPool(6);

		ClientMap = new HashMap<String, DataOutputStream>();
		Collections.synchronizedMap(ClientMap);

		System.out.println("[멀티서버] 멀티 서버 준비 완료");
		try {
			ServerSocket serverSocket = new ServerSocket(7777); // 서버 소켓
			System.out.println("[멀티서버] 연결 대기중...");
			while (true) {
				Socket socket = serverSocket.accept();

				ServerReceiver serverreceiver = new ServerReceiver(socket);
				executorService.submit(serverreceiver);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 서버가 클라이언트에게 받음
	 * 
	 * @author WKU
	 *
	 */
	static class ServerReceiver extends Thread {
		Socket socket;
		DataInputStream dis;
		DataOutputStream dos;

		public ServerReceiver(Socket socket) {
			this.socket = socket;
			try {
				dis = new DataInputStream(socket.getInputStream());
				dos = new DataOutputStream(socket.getOutputStream());
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

		/**
		 * 스레드 시작,이름을 입력 받고 해쉬맵에 저장함
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			String name = "";
			try {
				System.out.println("[멀티서버] 서버의 소켓 : " + socket);
				// name = dis.readUTF();
				// System.out.println(name);

				// 이름을 받아서 중복체크를 한후 결과를 응답으로 보냄
				while ((name = dis.readUTF()) != null) {
					// 중복 되었을시 클라이언트에게 에러코드를 보냄
					if (ClientMap.containsKey(name)) {
						dos.writeUTF("!errCode404");
					} else {
						System.out.println(name);
						dos.writeUTF("");
						break;
					}
				}

				// 클라이언트를 해쉬맵에 저장
				ClientMap.put(name, dos);

				// 입력 스트림이 null이 아니면 반복
				while (dis != null) {
					String message;
					message = dis.readUTF(); // 입력 스트림을 통해 읽어온 문자열을
					// message에 할당

					All_Send_Message(message);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

		/**
		 * 메시지를 전부 보내는 메소드
		 * 
		 * @param message 입력받은 메시지
		 * @return 지금까지의 모든 메시지
		 */
		public boolean All_Send_Message(String message) {
			Iterator it = ClientMap.keySet().iterator();

			while (it.hasNext()) {
				try {
					DataOutputStream it_dos = (DataOutputStream) ClientMap.get(it.next());
					it_dos.writeUTF(message);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			return true;
		}
	}
}
