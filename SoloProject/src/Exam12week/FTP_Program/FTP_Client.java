package FTP_Program;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class FTP_Client {
    static Socket socket = null;
    static InputStream is = null;
    static OutputStream ops = null;
    static final String Ftp_Client_Folder_Name = "FTP_Client";

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
                return "[클라이언트] : 데이터 받기 실패함.";
            } else {
                return (new String(bytes, 0, Read_Byte_Count, "UTF-8"));
            }
        } catch (Exception e) {
            return "[클라이언트] : 에러 , Receive 001";
        }
    }

    /**
     * 파일 보내는 메소드
     * 
     * @param file
     * @throws IOException
     */
    private static void File_Send(File file) throws IOException {
        OutputStream ops = socket.getOutputStream();
        String message = file.toString();

        if (Message_Send(message, ops)) {
            System.out.println("");
        }

        String Ftp_File_Name = Ftp_Client_Folder_Name + "\\" + message;
        File Ftp_File = new File(Ftp_File_Name);

        if (!Ftp_File.exists()) {
            System.out.println("[클라이언트] : " + Ftp_File.toString() + " 파일이 존재하지 않습니다.");
            return;
        }

        ops = socket.getOutputStream();

        /**
         * 아웃풋 스트림을 이용해 데이터 단위로 보내는 스트림을 개통함.
         */
        DataOutputStream dos = new DataOutputStream(ops);
        byte[] buffer = new byte[1024]; // 바이트 단위로 임시 저장하는 버퍼
        int len; // 전송할 데이터의 길이를 측정하는 변수
        int data = 0; // 전송횟수 , 용량을 측정하는 변수

        /**
         * 파일 인풋 스트림 생성
         */
        FileInputStream fis = new FileInputStream(Ftp_File);

        /**
         * 파일 인풋 스트림을 통해 파일에서 입력받은 데이터를
         * 버퍼에 임시저장 하고 그 길이(len)를 측정
         * ->데이터의 양 측정
         */
        while ((len = fis.read(buffer)) > 0) {
            data++;
        }

        System.out.println("[클라이언트] : 데이터 크기 = " + data);
        fis.close();

        /**
         * 파일 인풋 스트림이 만료되었으니 새롭게 개통
         */
        fis = new FileInputStream(Ftp_File);
        dos.writeInt(data);
        dos.writeUTF(Ftp_File.getName());

        /**
         * 데이터를 읽어올 횟수 만큼 파일인풋스트림 에서 파일의 내용을 읽어옴.
         */
        for (len = 0; data > 0; data--) {
            /*
             * 파일 인풋 스트림을 통해 파일에서 입력 받은 데이터를 버퍼에 임시저장하고 길이 측정
             */
            len = fis.read(buffer);
            /*
             * 서버에게 파일의 정보를(1KB)만큼 보내고 , 그 길이를 보냄.
             */
            ops.write(buffer, 0, len);
        }
        ops.flush();
        System.out.println("[클라이언트] : \"" + Ftp_File.getName() + "\" 파일보내기 성공 ");
    }

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

		String Ftp_File_Name = Ftp_Client_Folder_Name + "\\" + File_Name;
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

    public static void main(String[] args) {
        File Ftp_Client_Folder = new File(Ftp_Client_Folder_Name);
        if (!Ftp_Client_Folder.exists()) {
            Ftp_Client_Folder.mkdir();
        }

        try {
            socket = new Socket();
            System.out.println("[클라이언트] : 서버에 연결 요청");
            socket.connect(new InetSocketAddress("localhost", 5001));
            System.out.println("[클라이언트] : 서버에 연결 성공");

            boolean isExit = false;
            while (true) {
                /*
                 * 클라이언트 end 체크
                 */
                if (isExit == true) {
                    break;
                }
                byte[] bytes = null;
                String message = null;

                ops = socket.getOutputStream();
                is = socket.getInputStream();

                /*
                 * 프롬프트 메뉴 불러오기
                 */
                message = Message_Receive(is);
                System.out.println(message);

                Scanner scan = new Scanner(System.in);
                /*
                 * 문자열 입력
                 */
                message = scan.next();

                if (Message_Send(message, ops)) {
                    System.out.println("");
                }

                /**
                 * 파일 내역 조회
                 */
                if (message.equals("1")) {
                    System.out.println("====== 파일 목록 ======");
                    message = Message_Receive(is);
                    System.out.println(message);
                    System.out.println("=======================");
                }

                /**
                 * 폴더 만들기
                 */
                else if (message.equals("2")) {
                    /*
                     * 쓰레기 데이터 비우기
                     */
                    Message_Receive(is);
                    System.out.println("[클라이언트] : 생성할 폴더명을 입력 : ");
                    scan = new Scanner(System.in);
                    message = scan.next();

                    if (Message_Send(message, ops)) {
                        System.out.println("[클라이언트] : 폴더 이름 전송 성공");
                    }
                    Thread.sleep(500);
                }

                /**
                 * 클라이언트에서는 업로드
                 */
                else if (message.equals("3")) {
                    /*
                     * 쓰레기 데이터 비우기
                     */
                    Message_Receive(is);
                    System.out.print("[클라이언트] : 업로드할 파일명을 입력 : ");
                    scan = new Scanner(System.in);

                    /*
                     * 전송할 파일들을 불러들일 String 변수 선언
                     */
                    String Str_Send_Files = scan.nextLine();
                    /*
                     * 서버에 업로드할 파일들을 공백 기준으로 잘라서 String 배열에 넣음
                     */
                    String[] Str_Send_File_List = Str_Send_Files.split(" ");

                    /*
                     * 업로드할 파일의 갯수가 1개 이하라면 , 단일 처리
                     */
                    if (Str_Send_File_List.length == 1) {
                        File_Send(new File(Str_Send_Files));
                    } else {
                        File_Send(new File(Str_Send_File_List[1]));
                        // 레포트 4
                        System.out.println("[클라이언트] : 미구현 기능");
                    }
                    Thread.sleep(500);
                }

                /*
                 * 클라이언트 에서는 다운로드
                 */
                else if (message.equals("4")) {
                    // 서버의 업로드 부분을 참고하여 작성
                    // 서버와 클라이언트 반대
                    // 레포트3
                    Message_Receive(is);
                    System.out.print("[클라이언트] : 다운로드할 파일명을 입력: ");
                    scan = new Scanner(System.in);

                    /*
                     * 다운로드할 파일들을 불러들일 String 변수 선언
                     */
                    String Str_Download_Files = scan.nextLine();

                    System.out.println("[클라이언트] : 서버에게 이걸 다운하겠다 내용: "+Str_Download_Files);

                    Thread.sleep(500);

                }

                /*
                 * 서버로부터 파일 삭제
                 */
                else if (message.equals("5")) {
                    /*
                     * 쓰레기 데이터 비우기
                     */
                    Message_Receive(is);
                    System.out.println("삭제할 파일명을 입력해 주세요: ");

                    scan = new Scanner(System.in);
                    message = scan.next();

                    if (Message_Send(message, ops)) {
                        System.out.println("[클라이언트] : 파일 삭제 이름 전송 성공");
                    }
                    Thread.sleep(500);
                }
                /*
                 * 나가기
                 */
                else if (message.equals("6")) {
                    System.out.println("\n[클라이언트] : 접속 종료");
                    isExit = true;
                    break;
                }
            }

            ops.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!socket.isClosed()) {
            try {
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
