package Chatting_Program;

import java.io.*;
import java.net.*;

public class MultiThreadedChatServer {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(12345); // 12345 포트로 서버 소켓 생성
        System.out.println("서버가 실행되었습니다.");

        while (true) {
            Socket socket = serverSocket.accept(); // 클라이언트의 연결을 대기
            System.out.println(socket.getInetAddress() + "에서 접속했습니다.");

            // 새로운 스레드를 생성하여 클라이언트와 통신을 처리
            Thread thread = new Thread(new ClientHandler(socket));
            thread.start();
        }
    }

    // 클라이언트와의 통신을 처리하는 스레드 클래스
    private static class ClientHandler implements Runnable {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                while (true) {
                    String input = in.readLine(); // 클라이언트의 메시지를 읽어들임
                    if (input == null) break;
                    System.out.println("클라이언트 메시지: " + input);
                    out.println("서버에서 응답합니다: " + input);
                }

                socket.close(); // 클라이언트와의 연결 종료
                System.out.println(socket.getInetAddress() + "의 접속이 종료되었습니다.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

