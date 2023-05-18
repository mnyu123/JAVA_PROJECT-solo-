package FTP_Program;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class FTP_Server {
	public static OutputStream ops;
	public static InputStream is;

	public static String FTP_Server_folder_name = "FTP_EXAM";

	/**
	 * 메시지 보내는 메소드
	 * 
	 * @param message 메시지
	 * @param ops     아웃풋 스트림 객체 변수
	 * @return 메시지 전송 성공 = true , 실패 = false 반환
	 */
	public static boolean Message_Send(String message, OutputStream ops) {
		byte[] bytes = null;
		try {
			bytes = message.getBytes("UTF-8");
			ops.write(bytes);
			ops.flush();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 메시지를 받는 메소드
	 * 
	 * @param is 인풋스트림
	 * @return 메시지가 반환
	 */
	public static String Message_Receive(InputStream is) {
		byte[] bytes = new byte[1024];
		try {
			int Read_Byte_Count = is.read(bytes);
			if (Read_Byte_Count < 0) {
				return "[서버] : 데이터 받기 실패함.";
			} else {
				return (new String(bytes, 0, Read_Byte_Count, "UTF-8"));
			}
		} catch (Exception e) {
			return "[서버] : 에러 , Receive 001";
		}
	}

	/**
	 * 인풋 스트림을 이용해 데이터 단위로 입력을 받는 "데이터 인풋 스트림"을 개통함
	 * 
	 * @param is
	 * @throws IOException
	 */
	public static void Receive_File(InputStream is) throws IOException {
		DataInputStream dis = new DataInputStream(is);
		FileOutputStream fos = null;

		/**
		 * int형 데이터를 전송 받습니다.
		 */
		int data = dis.readInt();

		/**
		 * String형 데이터를 전송받아 File_Name(파일의 이름으로 쓰인다.)에 저장함.
		 */
		String File_Name = dis.readUTF();

		String Ftp_File_Name = FTP_Server_folder_name + "\\" + File_Name;
		File Ftp_File = new File(Ftp_File_Name);

		System.out.println("[서버] : 크기 받기 성공 , 파일 크기 : " + data);

		if (Ftp_File.exists()) {
			System.out.println("[서버] : 이미 \"" + Ftp_File.toString() + "\" 파일이 존재합니다.");
			return;
		}

		/**
		 * 생성한 파일을 클라이언트로 부터 전송받아 완성시키는 "파일 아웃풋 스트림"을 개통함
		 */
		fos = new FileOutputStream(Ftp_File);

		/**
		 * 바이트 단위로 '임시저장' 하는 버퍼를 생성함.
		 */
		byte[] buffer = new byte[1024];

		/**
		 * 전송받은 'data'의 횟수만큼 전송받아서 "파일 아웃풋 스트림"을 이용하여 '파일'을 완성시킨다.
		 */
		for (int len; data > 0; data--) {
			len = is.read(buffer);
			fos.write(buffer, 0, len);
		}

		fos.close();
		fos.flush();
		System.out.println("[서버] : \"" + File_Name + "\" 파일 전송 완료");
	}

	/**
	 * 메인 메소드는 여기에 있음
	 * 
	 * @param args
	 * @throws InterruptedException 예외 처리구문
	 */
	public static void main(String[] args) throws InterruptedException {
		ServerSocket serversocket = null;
		/**
		 * 서버 루트 폴더
		 */
		Socket socket = null;

		File Ftp_Server_Folder = new File(FTP_Server_folder_name);
		/**
		 * 루트 폴더 체크함 없으면 '새로 만들고' 있으면 if절을 실행 안함.
		 */
		if (!Ftp_Server_Folder.exists()) {
			Ftp_Server_Folder.mkdir();
		}

		try {
			/**
			 * 소켓 생성
			 */
			serversocket = new ServerSocket();

			/**
			 * 포트 바인딩
			 */
			serversocket.bind(new InetSocketAddress("localhost", 5001));

			while (true) {
				System.out.println("[서버] : 연결 기다림");

				/**
				 * 연결 수락 클라이언트 올때까지 기다리고 접속이 되면 '통신용 소켓'을 리턴한다.
				 */
				socket = serversocket.accept();

				/**
				 * 연결된 클라이언트 IP 주소 얻기
				 */
				InetSocketAddress isa = (InetSocketAddress) socket.getRemoteSocketAddress();

				System.out.println("[서버] : 연결 수락함 \"" + isa.getHostName() + "\"");

				byte[] bytes = null;
				String message = null; // 메시지
				int Read_Byte_Count;

				while (true) {
					/**
					 * 데이터 보내기
					 */
					ops = socket.getOutputStream();

					/**
					 * 데이터 받기
					 */
					is = socket.getInputStream();

					message = "\n안녕하세요 " + isa.getHostName() + "... 입력을 기다립니다... \n";
					message += "====== < FTP 메뉴 > ======\n";
					message += " 1. dir(File List),파일 목록 보기\n";
					message += " 2. mkdir(Folder Create),폴더 생성하기\n";
					message += " 3. upload(파일 업로드)\n";
					message += " 4. download(파일 다운로드)\n";
					message += " 5. delete(파일 삭제)\n";
					message += " 6. quit(나가기)\n";
					message += "=========================\n";
					message += "번호를 하나 선택하세요: ";

					if (Message_Send(message, ops)) {
						System.out.println("[서버] : 메뉴 데이터 보내기 성공");
					}

					message = Message_Receive(is);

					/*
					 * 파일 목록 보는거
					 */
					if (message.equals("1")) {
						message = "";
						File[] fList = Ftp_Server_Folder.listFiles();
						for (int i = 0; i < fList.length; i++) {
							File file = fList[i];

							/**
							 * 파일 전송
							 */
							if (file.isFile()) {
								message += file.toString() + "\n";
							}
							/**
							 * 폴더 전송
							 */
							else {
								// 레포트1
								
							}
						}
						if (message == "") {
							message = "파일이 여기에 없습니다.\n";
						}
						if (Message_Send(message, ops)) {
							System.out.println("[서버] : DIR 데이터 보내기 성공");
						}
					}

					/**
					 * 폴더 만들기
					 */
					else if (message.equals("2")) {
						if (Message_Send(message, ops)) {
							System.out.println("[서버] : 폴더 이름 요청");

							/**
							 * 생성할 폴더 이름 받기
							 */
							message = Message_Receive((is));

							System.out.println("[서버] : 생성할 폴더명 받기 성공, 폴더 명: " + message);
							String Ftp_File_Name = FTP_Server_folder_name + "\\" + message;
							File Ftp_Folder = new File(Ftp_File_Name);

							/**
							 * 폴더가 없다면
							 */
							if (!Ftp_Folder.exists()) {
								Ftp_Folder.mkdirs(); // 새로 만듬
								System.out.println("[서버] : 폴더 생성 성공");
							} else {
								System.out.println("[서버] : 폴더 생성 실패 , 이미 폴더가 있습니다.");
							}
						}
					}

					/**
					 * 서버의 입장 : 클라이언트가 보낸 test1.txt 파일을 "받았음"
					 */
					else if (message.equals("3")) {
						if (Message_Send(message, ops)) {
							System.out.println("");
						}

						/**
						 * 업로드 할 파일명 받기
						 */
						is = socket.getInputStream();
						message = Message_Receive(is);

						System.out.println("[서버] : 업로드할 파일명 받기 성공, 파일명: " + message);
						Receive_File(is);
					}

					/**
					 * 서버(보내줌) -> 클라이언트 에게
					 */
					else if (message.equals("4")) {
						// 업로드 부분을 참조하여 작성
						// 서버와 클라이언트의 업로드 <-> 다운로드 부분 반대로
						// 레포트2
					}

					/**
					 * 삭제
					 */
					else if (message.equals("5")) {
						if (Message_Send(message, ops)) {
							System.out.println("");
						}

						is = socket.getInputStream();
						message = Message_Receive(is);

						String Ftp_File_Name = FTP_Server_folder_name + "\\" + message;
						File Ftp_File = new File(Ftp_File_Name);
						if (Ftp_File.exists()) {
							Ftp_File.delete();
							System.out.println("[서버] : 삭제 성공 , 파일명 : " + message);
						} else {
							System.out.println("[서버] : 삭제 실패 , 파일명 : " + message);
						}
					}

					/**
					 * 나가기
					 */
					else if (message.equals("6")) {
						bytes = message.getBytes("UTF-8");
						ops.write(bytes);
						ops.flush();
						System.out.println("[서버] : 접속 종료 " + message);

						break;
					} else {
						System.out.println(message);
						break;
					}

				}
				is.close();
				ops.close();
				socket.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		if (!serversocket.isClosed()) {
			try {
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}