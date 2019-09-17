class TestThread extends Thread{
	@Override
	public void run(){
		try {
			while(true){
				System.out.println("���� ����");
				Thread.sleep(1); // InterruptedException �߻� => ���� ó��(catch) ������� �̵�
			}
		} catch (InterruptedException e) {}
		System.out.println("���� ����");
	}
}

public class ThreadTest {
	
	public static void main(String[] args) {

		TestThread thread = new TestThread();
		thread.start();
		
		// 0.5�� �Ŀ� �����带 ����
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		thread.interrupt();
	}
}