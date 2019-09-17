class TestThread extends Thread{
	@Override
	public void run(){
		try {
			while(true){
				System.out.println("무한 실행");
				Thread.sleep(1); // InterruptedException 발생 => 예외 처리(catch) 블록으로 이동
			}
		} catch (InterruptedException e) {}
		System.out.println("실행 종료");
	}
}

public class ThreadTest {
	
	public static void main(String[] args) {

		TestThread thread = new TestThread();
		thread.start();
		
		// 0.5초 후에 스레드를 종료
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		thread.interrupt();
	}
}