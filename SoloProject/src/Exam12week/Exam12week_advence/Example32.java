package Exam12week_advence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Example32 implements Runnable{

	// 각각의 클라이언트를 구분함(5개의 클라이언트)
	private static Socket clientSocket;
	static PrintWriter out = null;
	
	public Example32(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}
	
	public static void main(String[] args) {
		// 스레드 생성을 최대 6개로 제한 시켜놓은 코드(0부터 1개로 셈)
		ExecutorService eService = Executors.newFixedThreadPool(5);
		System.out.println("#서버 시작함");
		
		try(ServerSocket serversocket = new ServerSocket(10000)){
			while(true) {
				System.out.println("#연결 대기중 ....");
				clientSocket = serversocket.accept();
				Example32 exam32 = new Example32(clientSocket);
				eService.submit(exam32);
			}
		}catch (IOException e) {
			// TODO: handle exception
			System.out.println("오류 발생 지점 1");
		}
		System.out.println("#서버 종료함.");
		eService.shutdown();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			OutputStream out = clientSocket.getOutputStream();
			String userID = br.readLine(); //줄 단위로 받음
			System.out.println("#["+userID+"]님이 접속하셨습니다.");
			Thread.sleep(5000);
			out.close();
			System.out.println("#클라이언트 : "+userID+" 종료되었음.");
		} catch (Exception e) {
			System.out.println("오류 발생 지점 2");
		}
	}
	
	

}
