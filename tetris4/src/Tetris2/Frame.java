package Tetris2;

import java.awt.GridLayout;

import javax.swing.JFrame;

public class Frame {
	public Frame() {
		JFrame f = new JFrame("Tetris");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Tetris_1p t1 = new Tetris_1p();
		Tetris_2p t2 = new Tetris_2p();
		f.setLayout(new GridLayout(1, 2));
		f.addKeyListener(t1.k);
		f.addKeyListener(t2.k);
		f.add(t2);
		f.add(t1);
		f.pack();
		f.setVisible(true);
		f.setSize(36 * 26 + 16, 25 * 26 + 20);
	}
}