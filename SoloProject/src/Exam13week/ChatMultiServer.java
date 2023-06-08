package Exam13week;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 메인 메소드
 * 
 * @author WKU
 *
 */

public class ChatMultiServer {
	static String inFilePath = "C:\\Users\\WKU\\Documents\\mnyu\\new.txt";
	static String OutFilePath = "C:\\Users\\WKU\\Documents\\mnyu\\Chat_Server_Folder\\new.txt";
	static HashMap<String, DataOutputStream> ClientMap; // 이름,ID값 받기 위해 해쉬맵 사용

	public static InputStream is = null; // 파일 전송을 위해 추가
	static final String Chat_Server_Folder = "Chat_Server_Folder";
	static final String Chat_User_Folder = "Chat_User_Folder";

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
				e.printStackTrace();
			}
		}

		/**
		 * 스레드 시작,이름을 입력 받고 해쉬맵에 저장함
		 */
		@Override
		public void run() {
			String name = "";
			try {
				System.out.println("[멀티서버] 서버의 소켓 : " + socket);

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

				All_Send_Message("[멀티서버] " + name + " 님이 입장하셨습니다.");

				// 입력 스트림이 null이 아니면 반복
				while (dis != null) {
					String message;
					message = dis.readUTF(); // 입력 스트림을 통해 읽어온 문자열을
					// message에 할당
					if (message.replaceAll(name + " >>> ", "").startsWith("@")) {

						if (message.replaceAll(name + " >>> ", "").trim().equals("@접속자")) {
							dos.writeUTF(Show_User_List(name)); // 접속자 리스트 출력

						} else if (message.replaceAll(name + " >>> ", "").trim().startsWith("@귓속말")) {
							// 받아온 message를 " "공백 을 기준으로 3개를 분리 / tokenerizing
							// 공백으로 split했을때 메시지에서 문제. 하지만 리미트를 정하면 해결

							String[] messageTemp = message.replaceAll(name + " >>> ", "").trim().split(" ", 3);

							if (messageTemp == null || messageTemp.length < 3) { // 리미트
								dos.writeUTF("[멀티서버] 귓속말을 잘못 사용하셨습니다. \r\n 사용법 : @귓속말 [보낼사람] [메시지]");

							} else {
								String toName = messageTemp[1];
								String toMessage = messageTemp[2];

								if (ClientMap.containsKey(toName)) { // 유저 체크
									Send_To_Meg(name, toName, toMessage);

								} else {
									dos.writeUTF("[멀티서버] 해당 사용자가 존재하지 않습니다.");

								}
							}
						} else if (message.replaceAll(name + " >>> ", "").trim().equals("@파일")) {
							Scanner scan = new Scanner(System.in);
							dos.writeUTF("[멀티서버] 파일 전송 시작");
							// String filePath = scan.nextLine();
							// String filePath = "C:\\Users\\WKU\\Documents\\mnyu\\yes.txt";
							String filePath = inFilePath;
							sendFile(filePath, OutFilePath);
							receiveFile(filePath, OutFilePath, 0);
						} else {
							dos.writeUTF("[멀티서버] 메시지가 전송되었습니다.");
						}
					} else {
						All_Send_Message(message, name);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				ClientMap.remove(name);
				All_Send_Message("[멀티서버] " + name + " 님이 퇴장하셨습니다.");
			}
		}

		/**
		 * 메시지를 전부 보내는 메소드
		 * 
		 * @param message 입력받은 메시지
		 * @return 지금까지의 모든 메시지
		 */
		public boolean All_Send_Message(String message, String name) {
			Iterator it = ClientMap.keySet().iterator();

			while (it.hasNext()) {
				try {
					Object obj = it.next();
					DataOutputStream it_out = (DataOutputStream) ClientMap.get(obj);
					if (!obj.toString().equals(name)) {
						it_out.writeUTF(message);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return true;
		}

		public boolean All_Send_Message(String message) {
			Iterator it = ClientMap.keySet().iterator();

			while (it.hasNext()) {
				try {
					DataOutputStream it_dos = (DataOutputStream) ClientMap.get(it.next());
					it_dos.writeUTF(message);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return true;
		}

		public String Show_User_List(String name) {
			StringBuilder strbuild = new StringBuilder("[멀티서버] 접속자 목록 \r\n");
			// hashmap에 있는 사용자 이름을 가져온다.
			Iterator it = ClientMap.keySet().iterator();

			while (it.hasNext()) {
				try {
					String key = (String) it.next();
					if (key.equals(name)) {
						key += "(*)";
					}
					strbuild.append(key + "\r\n");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			strbuild.append("[멀티서버] " + ClientMap.size() + "명 접속중 \r\n");
			return strbuild.toString();
		}

		public void Send_To_Meg(String FromName, String toName, String message) {
			try {
				ClientMap.get(toName).writeUTF("[멀티서버] 귓속말: from[" + FromName + "] >>> " + message);
				ClientMap.get(FromName).writeUTF("[멀티서버] 귓속말: to[" + toName + "] >>> " + message);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void sendFile(String filePath, String name) throws IOException {

			
			OutputStream ops = socket.getOutputStream();
			FileInputStream fis;

			DataOutputStream dos = new DataOutputStream(ops);

			fis = new FileInputStream(new File(filePath));

			byte[] buffer = new byte[1024]; // 바이트단위로 임시저장하는 버퍼를 생성합니다.
			int len; // 전송할 데이터의 길이를 측정하는 변수입니다.
			int data = 0;

			while ((len = fis.read(buffer)) > 0) { // FileInputStream을 통해 파일에서 입력받은 데이터를 버퍼에 임시저장하고 그 길이를 측정합니다.
				data++; // 데이터의 양을 측정합니다.
			}

			int datas = data; // 아래 for문을 통해 data가 0이되기때문에 임시저장한다.

			fis.close();
			fis = new FileInputStream(filePath); // FileInputStream이 만료되었으니 새롭게 개통합니다.
			dos.writeInt(data); // 데이터 전송횟수를 서버에 전송하고,
			dos.writeUTF(filePath); // 파일의 이름을 서버에 전송합니다.

			for (len = 0; data > 0; data--) { // 데이터를 읽어올 횟수만큼 FileInputStream에서 파일의 내용을 읽어옵니다.
				len = fis.read(buffer); // FileInputStream을 통해 파일에서 입력받은 데이터를 버퍼에 임시저장하고 그 길이를 측정합니다.
				ops.write(buffer, 0, len); // 서버에게 파일의 정보(1kbyte만큼보내고, 그 길이를 보냅니다.
			}

			System.out.println("약 " + datas + " kbyte");
			dos.writeUTF("[멀티서버] 파일 전송 중간체크");
			// String inFilePath = "C:\\Users\\WKU\\Documents\\mnyu\\yes.txt";
			// String OutFilePath =
			// "C:\\Users\\WKU\\Documents\\mnyu\\Chat_Server_Folder\\yes.txt";

			File file = new File(Chat_Server_Folder);

			// File Chat_File = new File(filePath);

			if (!file.exists()) {
				System.out.println("[멀티서버] : " + file.toString() + " 파일/폴더가 존재하지 않습니다.");
				file.mkdir();
			}

			// if (Chat_File.isFile()) {
			// dos.writeUTF("[멀티서버] 파일 전송을 시작합니다.");
			// dos.writeUTF(Chat_File.getName()); // 이름은 가져오는데

			// dos.writeLong(Chat_File.length()); // 파일 크기는 못가져와

			// FileInputStream fis = new FileInputStream(Chat_File);
			// byte[] buffer = new byte[1024];
			// int bytesRead;
			// while ((bytesRead = fis.read(buffer)) != -1) {
			// dos.write(buffer, 0, bytesRead);
			// }
			// ops = socket.getOutputStream();
			// ops.flush();
			// fis.close();

			// dos.writeUTF("[멀티서버] 파일 전송이 완료되었습니다.");

			// } else {

			// dos.writeUTF("[멀티서버] 파일 전송 실패: 해당 경로에 파일이 존재하지 않습니다.");
			// }

		}

		// private void receiveFile(String filePath, String outFilePath) throws
		// IOException {

		// FileOutputStream fos = null;

		// File User_file = new File(Chat_User_Folder);

		// String Chat_File_Name = Chat_Server_Folder + "\\";
		// File Chat_File = new File(Chat_File_Name);

		// if (!User_file.exists()) {
		// System.out.println("[멀티서버] : " + User_file.toString() + " 파일/폴더가 존재하지
		// 않습니다.");
		// User_file.mkdir();
		// return;
		// }
		// int data = dis.readInt();
		// try {
		// fos = new FileOutputStream(User_file);
		// byte[] buffer = new byte[1024];

		// for (int i; data > 0; data--) {
		// i = is.read(buffer);
		// fos.write(buffer, 0, i);
		// }
		// fos.close();
		// fos.flush();
		// dos.writeUTF("[멀티서버] 유저에게 파일 전송함");
		// } catch (FileNotFoundException e) {
		// e.printStackTrace();
		// }

		// }

		private void receiveFile(String filePath, String name, long fileSize) throws IOException {
			try {
				String str = "";
				File file = new File(Chat_User_Folder);
				File User_File = new File(filePath);
				if (!file.exists()) {
					System.out.println("[멀티서버] : " + file.toString() + " 파일/폴더가 존재하지 않습니다.");
					file.mkdir();
					return;
				}

				if (!User_File.exists()) {
					User_File.createNewFile();
				}

				try {
					File f = new File(OutFilePath);

					if (!f.exists()) {
						f.createNewFile();
					} else {
						dos.writeUTF("[멀티서버] 이미 해당 파일이 있습니다.");
						return;
					}
					FileOutputStream fos = new FileOutputStream(f);

					byte[] bytes = str.getBytes();

					fos.write(bytes);
					fos.close();
					dos.writeUTF("[멀티서버] 파일 전 송 완료");
				} catch (Exception e) {
					e.printStackTrace();
				}

				FileOutputStream fos = new FileOutputStream(User_File);

				byte[] buffer = new byte[4096];
				int bytesRead;
				long totalBytesRead = 0;

				while (totalBytesRead < fileSize && (bytesRead = dis.read(buffer, 0,
						(int) Math.min(buffer.length, fileSize - totalBytesRead))) != -1) {
					fos.write(buffer, 0, bytesRead);
					totalBytesRead += bytesRead;
				}

				fos.close();
				System.out.println("[멀티서버] 파일 수신 완료: " + file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}