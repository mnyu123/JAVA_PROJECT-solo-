package Exam12week;

class Buffer {
	private int contents;
	private boolean able = false;

	public synchronized void put(int value) {
		while (able == true) {
			try {
				wait();// 생산 공간이 없다면 스레드를 재운다
			} catch (InterruptedException e) {

			}
		}

		contents = value;
		System.out.println("생산자##### : 생산 " + contents);
		try { // 소비자가 대기를 하고 있다면 스레드를 깨운다
			notify();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("대기중인 스레드가 없습니다.");
		}

		able = true;
	}

	public synchronized int get() {
		while (able == false) {
			try {
				wait();
			} catch (InterruptedException e) {

			}
		}

		System.out.println("소비자##### : 소비 " + contents);
		notify();
		able = false;
		return contents;
	}

}

class Producer extends Thread {
	private Buffer b;

	public Producer(Buffer blank) {
		b = blank;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (int i = 1; i <= 5; i++) {
			b.put(i);
		}
	}

}

class Consumer extends Thread {
	private Buffer b;

	public Consumer(Buffer blank) {
		b = blank;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		int value = 0;
		for (int i = 1; i <= 5; i++) {
			value = b.get();
		}
	}

}

public class SyncExam3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Buffer buffer = new Buffer();
		Producer producer = new Producer(buffer);
		Consumer customer = new Consumer(buffer);
		producer.start();
		customer.start();
	}

}
