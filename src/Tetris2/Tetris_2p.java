package Tetris2;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class Tetris_2p extends Tetris_1p{
	Thread tet = new Thread(this);
	public Tetris_2p () {
		tet.start();
		this.init();
		this.setBorder(new TitledBorder(new LineBorder(Color.red, 5)));
	}
	
	KeyListener k = new KeyListener() {
		public void keyTyped(KeyEvent e) {}

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
				tet.run();
				break;
			case KeyEvent.VK_Q:// q
				if (status == "run") {
					status = "pause";
					tet.resume();
					bigWord = "PAUSE";
					smallWord = " Press \"Q\" to return the game";
				} else {
					status = "run";
					tet.run();
					bigWord = "";
					smallWord = "";
				}
				break;
			}
		}
		public void keyReleased(KeyEvent e) {}
	};
}
