package Exam12week;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

class PortScanThread extends Thread {
	String ip = null;
	int port = 0;
	int timeout = 1000;

	public PortScanThread(String ip, int port, int timeout) {
		this.ip = ip;
		this.port = port;
		this.timeout = timeout * this.timeout;
	}

	@Override
	public void run() {
		Socket socket = null;
		SocketAddress socketaddress = new InetSocketAddress(this.ip, this.port);
		try {
			socket = new Socket();
			socket.connect(socketaddress, this.timeout);
			System.out.println("열림: " + this.port);
			socket.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}

public class PortScanExam {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);

		int start = 1;
		int end = 0xffff;

		String Port_Range = null;
		String ip = null;

		System.out.print("대상 IP 입력: ");
		ip = scan.nextLine();

		System.out.print("포트 범위 입력(ex:1~100): ");
		Port_Range = scan.nextLine();

		try {
			new Socket(ip, 80);

			String[] ports = Port_Range.split("-");

			if (!(ports[0].equals(""))) {
				start = Integer.parseInt(ports[0]);

				end = Integer.parseInt(ports[1]);
			}
		} catch (UnknownHostException e) {
			// TODO: handle exception
			System.out.println("잘못된 IP 입력");
			return;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("잘못된 입력");
			return;
		}

		PortScanThread pst = null;

		for (; start <= end; start++) {
			pst = new PortScanThread(ip, start, 1);
			pst.start();
		}

		Thread.sleep(1000);
		System.out.println("\n포트 스캔 완료");
	}

}
