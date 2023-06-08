package Exam13week;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Receiver extends Thread {
	private DataInputStream dis;

	public Receiver(Socket socket) {
		try {
			dis = new DataInputStream(socket.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (dis != null) {
			try {
				System.out.println(dis.readUTF());
				
				String message = dis.readUTF();
				
				if(message.startsWith("[멀티서버] 파일 전송을 시작합니다.")) {
					String fileName = dis.readUTF();
					long fileSize = dis.readLong();
					
					receiveFile(fileName, fileSize);
				}
				else {
					System.out.println(message);
				}
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}
	}

	private void receiveFile(String fileName, long fileSize) {
		try {
			
			File file = new File(fileName);
			FileOutputStream fos = new FileOutputStream(file);

			byte[] buffer = new byte[4096];
			int bytesRead;
			long totalBytesRead = 0;

			while (totalBytesRead < fileSize && (bytesRead = dis.read(buffer, 0,
					(int) Math.min(buffer.length, fileSize - totalBytesRead))) != -1) {
				fos.write(buffer, 0, bytesRead);
				totalBytesRead += bytesRead;
			}

			fos.close();
			System.out.println("[멀티서버] 파일 수신 완료: " + fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}