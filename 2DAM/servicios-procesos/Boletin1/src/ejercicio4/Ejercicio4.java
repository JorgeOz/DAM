package ejercicio4;

public class Ejercicio4 {
	static class MyThread extends Thread {
		String name;

		public MyThread(String name) {
			this.name = name;
		}

		@Override
		public void run() {
			for (int i = 0; i < 10000; i++) {
				System.out.println(this.name + "\t" + i);
				Thread.yield();
			}
		}
	}

	public static void main(String[] args) {
		MyThread t1 = new MyThread("T 1:");
		MyThread t2 = new MyThread("T 2:");
		t1.start();
		t2.start();
	}
}