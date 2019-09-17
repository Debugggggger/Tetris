package Tetris2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class Tetris_2p extends Tetris_1p{
	private static final long serialVersionUID = 6660831457893383121L;
	public Tetris_2p () {
		this.init();
		
		//빨간색
		this.setBorder(new TitledBorder(new LineBorder(Color.red, 5)));
	}
	
	KeyAdapter k = new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_W:
				rotate(-1);
				break;
			case KeyEvent.VK_S:
				dropByOne();
				score += 1;
				break;
			case KeyEvent.VK_A:
				move(-1);
				break;
			case KeyEvent.VK_D:
				move(+1);
				break;
			case KeyEvent.VK_SHIFT:
				drop();
				break;
			case KeyEvent.VK_CONTROL:
				hold();
				holdable = false;
				break;
			case KeyEvent.VK_F5:// F5
				init();
				break;
			}
		}
	};
	public void paintText(Graphics g) {
		int y=130;
		String text =new String("A,S:이동 \nW:회전 \nS:한칸 내리기 \nShift:빠르게 내리기 \nCtrl:홀드");
		for (String line : text.split("\n"))
            g.drawString(line,19 * 16, y += g.getFontMetrics().getHeight());
	}
	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		while (true) {
			try {
				if (status == "run") {
					//System.out.println("t2 run");
					tet1.sleep(1000);
					dropByOne();
				}else {
					//System.out.println("t2 pause");
					tet1.sleep(1000);
					tet1.resume();
				}
			} catch (InterruptedException e) {}
			// Thread.sleep(100 + 900 / level); // InterruptedException 발생 => 예외 처리(catch)
			// 블록으로 이동
		}
	}
}
