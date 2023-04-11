package Chatting_Program;

import java.io.*;
import java.net.*;

public class MultiThreadedChatClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 12345); // 로컬호스트에 12345 포트로 접속
        System.out.println("서버에 접속되었습니다.");

        // 입력 스레드 시작
        Thread inputThread = new Thread(new InputHandler(socket));
        inputThread.start();

        // 출력 스레드 시작
        Thread outputThread = new Thread(new OutputHandler(socket));
        outputThread.start();
    }

    // 키보드 입력을 처리하는 스레드 클래스
    private static class InputHandler implements Runnable {
        private BufferedReader in;
        private PrintWriter out;

        public InputHandler(Socket socket) throws IOException {
            in = new BufferedReader(new InputStreamReader(System.in));
            out = new PrintWriter(socket.getOutputStream(), true);
        }

        public void run() {
            try {
                while (true) {
                    String input = in.readLine(); // 키보드 입력을 읽어들임
                    out.println(input); // 서버로 메시지 전송
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 서버로부터 메시지를 받는 스레드 클래스
    private static class OutputHandler implements Runnable {
        private BufferedReader in;

        public OutputHandler(Socket socket) throws IOException {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }

        public void run() {
            try {
                while (true) {
                    String input = in.readLine(); // 서버로부터 메시지를 읽어들임
                    System.out.println("서버 메시지: " + input);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

