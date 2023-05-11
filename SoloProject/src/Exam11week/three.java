package Exam11week;

package Exam11week;

// 2
class ThreadB implements Runnable {
	int i;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (i = 0; i < 10; i++) {
			if (i == 3 || i == 6 || i == 9) {
				System.out.println("A의 박수: 짝");
			} else {
				System.out.println("A는: " + i);
			}
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
}

// 1
class ThreadA extends Thread {
	int i;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (i = 0; i < 10; i++) {
			if (i == 3 || i == 6 || i == 9) {
				System.out.println("B의 박수: 짝");
			} else {
				System.out.println("B는: " + i);
			}
			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
}

public class ThreadExam3 {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		ThreadA tha = new ThreadA();
		Thread thb = new Thread(new ThreadB());
		tha.setPriority(Thread.MIN_PRIORITY);
		tha.start();
		thb.setPriority(Thread.MAX_PRIORITY);
		thb.start();

		// 3
		Runnable runC = () -> {
			int i;
			for (i = 0; i < 10; i++) {
				if (i == 3 || i == 6 || i == 9) {
					System.out.println("C의 박수: 짝");
				} else {
					System.out.println("C는: " + i);
				}
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		};

		new Thread(runC).start();
		
		
		Thread.sleep(5000);
		// 4
		new Thread(new Runnable() {
			int i;

			@Override
			public void run() {
				// TODO Auto-generated method stub
				for (i = 10; i > -1 ; i--) {
					System.out.println("369게임 시작 카운트다운: " + i + "초");
				}
			}
		}).start();
		

	}

}
