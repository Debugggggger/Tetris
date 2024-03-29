package Tetris;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Tetris extends JPanel {

	private static String status = "run"; // 게임의 상태를 나타냄 run/ gameover/ pause
	static int well_c = 18; //맵의 가로
	static int well_r = 25; //맵의 세로
	private static final long serialVersionUID = -8715353373678321308L;

	private final Point[][][] Shapes = {
			// I-Piece
			{ { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1) },
					{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3) },
					{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1) },
					{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3) } },

			// J-Piece
			{ { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 0) },
					{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 2) },
					{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 2) },
					{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 0) } },

			// L-Piece
			{ { new Point(0, 0), new Point(1, 0), new Point(2, 0), new Point(2, 1) },
					{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 2) },
					{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 0) },
					{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 0) } },

			// O-Piece
			{ { new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
					{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
					{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
					{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) } },

			// S-Piece
			{ { new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1) },
					{ new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) },
					{ new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1) },
					{ new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) } },

			// T-Piece
			{ { new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(2, 1) },
					{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) },
					{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(1, 2) },
					{ new Point(1, 0), new Point(1, 1), new Point(2, 1), new Point(1, 2) } },

			// Z-Piece
			{ { new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1) },
					{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2) },
					{ new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1) },
					{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2) } } };

	private final Color[] Colors = { Color.cyan, Color.blue, Color.orange, Color.yellow, Color.green, Color.pink,
			Color.red };

	private int[] shapePieces = { 10, 10, 10, 10, 10 }; // 진행되는 블록의 모양
	private Point[] placePieces = { null, null, null, null }; // 진행되는 블록의 위치

	private int holdShape = 10; // 저장한 블록의 모양
	private Point placeHold = null; // 저장한 블록의 위치
	private static boolean holdable = true;

	private int rotation;
	private ArrayList<Integer> tempPieces = new ArrayList<>(); // 다음 블록을 선정함

	private int level = 1;
	private int line = 0;
	private long score;
	private String bigWord;
	private String smallWord;
	private Color[][] well;

	// 초기 설정
	private void init() {
		// 맵을 만듦
		well = new Color[well_c][well_r];
		for (int i = 0; i <= well_c - 1; i++) {
			for (int j = 0; j < well_r - 1; j++) {
				if (i == 0 || i >= 11 || j == 22) {
					well[i][j] = Color.GRAY;
				} else {
					well[i][j] = Color.BLACK;
				}
				if ((i >= 12 && i <= (well_c - 2))
						&& ((j > 0 && j < 5) || (j > 7 && j < 12) || (j > 12 && j < 17) || (j > 17 && j < 22))) {
					well[i][j] = Color.BLACK;
				}
			}
		}
		holdShape = 10;
		score = 0;
		placeHold = null;
		line = 0;
		// 첫블록 세팅
		placePieces[1] = new Point(13, 9);
		placePieces[2] = new Point(13, 14);
		placePieces[3] = new Point(13, 19);
		for (int i = 0; i < 5; i++) {
			Collections.addAll(tempPieces, 0, 1, 2, 3, 4, 5, 6);
			Collections.shuffle(tempPieces);
			shapePieces[i] = tempPieces.get(0);
		}
		newPiece();
	}

	// 떨어질 블록을 랜덤으로 생성
	public void newPiece() {
		int putPiece = 0;
		holdable = true;

		// 퍼즐의 위치 선정
		placePieces[0] = new Point(5, 2);
		placeHold = new Point(13, 2);

		if (collidesAt(placePieces[0].x, placePieces[0].y, rotation)) {
			status = "gameover";
			bigWord = "GAME OVER";
			smallWord = " Press \"R\" to start new game";
		}

		rotation = 0;
		if (shapePieces[4] == 10) { // 랜덤블록 선정
			Collections.addAll(tempPieces, 0, 1, 2, 3, 4, 5, 6);
			Collections.shuffle(tempPieces);
		}
		putPiece = tempPieces.get(0);

		for (int i = 0; i < 4; i++) { // 중복방지
			if (putPiece == shapePieces[i]) {
				putPiece = tempPieces.get(i);
			}
			shapePieces[4] = putPiece;
			shapePieces[i] = shapePieces[i + 1];
		}
		shapePieces[4] = 10;
		tempPieces.remove(0);

	}

	// 블록을 홀드한다.
	private void hold() {
		if (holdable) {
			if (holdShape == 10) {
				holdShape = shapePieces[0];
				newPiece();
			} else {
				int temp = holdShape;
				holdShape = shapePieces[0];
				shapePieces[0] = temp;
			}
			placePieces[0] = new Point(5, 2);
		}
	}

	// x값과 y값, rotation을 받아 x,y값과 현재 떨어지고 있는 조각의 값이 충돌되지 않는지 검사한다.
	private boolean collidesAt(int x, int y, int rotation) {
		for (Point p : Shapes[shapePieces[0]][rotation]) {
			if (well[p.x + x][p.y + y] != Color.BLACK) {
				return true;
			}
		}
		return false;
	}

	// 블록 회전
	public void rotate(int i) {
		int newRotation = (rotation + i) % 4;
		if (newRotation < 0) {
			newRotation = 3;
		}
		if (!collidesAt(placePieces[0].x, placePieces[0].y, newRotation)) {
			rotation = newRotation;
		}
		repaint();
	}

	// 블록을 왼쪽/오른쪽으로 이동
	public void move(int i) {
		if (!collidesAt(placePieces[0].x + i, placePieces[0].y, rotation)) {
			placePieces[0].x += i;
		}
		repaint();
	}

	// 블록을 한줄씩 내리거나 내려갈곳이 없으면 고정시킨다.
	public void dropByOne() {
		if (!collidesAt(placePieces[0].x, placePieces[0].y + 1, rotation)) {
			placePieces[0].y += 1;
		} else {
			fixToWell();
		}
		repaint();
	}

	// 블록을 한번에 내림
	public void drop() {
		while (!collidesAt(placePieces[0].x, placePieces[0].y + 1, rotation)) {
			placePieces[0].y += 1;
			this.score++;
		}
		fixToWell();
		repaint();
	}

	// 바닥에 닿아 움직일 수 없는 블록
	public void fixToWell() {
		for (Point p : Shapes[shapePieces[0]][rotation]) {
			well[placePieces[0].x + p.x][placePieces[0].y + p.y] = Colors[shapePieces[0]];
		}
		clearRows();
		newPiece();
	}

	
	public void deleteRow(int row) {
		for (int j = row - 1; j > 0; j--) {
			for (int i = 1; i < 11; i++) {
				well[i][j + 1] = well[i][j];
			}
		}
		line++;
	}

	// 완성된 열 삭제 및 점수 부여
	public void clearRows() {
		boolean gap;
		int numClears = 0;

		for (int j = 21; j > 0; j--) {
			gap = false;
			for (int i = 1; i < 11; i++) {
				if (well[i][j] == Color.BLACK) {
					gap = true;
					break;
				}
			}

			if (!gap) {
				deleteRow(j);
				j += 1;
				numClears += 1;
			}
		}

		switch (numClears) {
		case 1:
			score += 100;
			break;
		case 2:
			score += 300;
			break;
		case 3:
			score += 500;
			break;
		case 4:
			score += 800;
			break;
		}
	}

	// 레벨업
	public void levelUp() {
		this.level = (int) (this.line / 5) + 1;
	}

	// 블록을 그린다.
	private void drawPiece(Graphics g) {
		// 떨어지는 블록
		for (Point p : Shapes[shapePieces[0]][rotation]) {
			g.setColor(Colors[shapePieces[0]]);
			g.fillRect((p.x + placePieces[0].x) * 26, (p.y + placePieces[0].y) * 26, 25, 25);
		}
		// 다음에 나올 블록
		for (int i = 1; i < 4; i++) {
			for (Point p : Shapes[shapePieces[i]][0]) {
				g.setColor(Colors[shapePieces[i]]);
				g.fillRect((p.x + placePieces[i].x) * 26, (p.y + placePieces[i].y) * 26, 25, 25);
			}
		}
		// 홀드한 블록
		if (holdShape != 10) {
			for (Point p : Shapes[holdShape][0]) {
				g.setColor(Colors[holdShape]);
				g.fillRect((p.x + placeHold.x) * 26, (p.y + placeHold.y) * 26, 25, 25);
			}
		}
	}

	// 저장된 맵 정보를 그린다.
	@Override
	public void paintComponent(Graphics g) {
		g.clearRect(0, 0, 26 * well_c, 26 * well_r - 1);
		if (status == "run") { // 게임이 실행중일때
			bigWord = "";
			smallWord = "";
			// 벽을 그린다.
			g.fillRect(0, 0, 26 * well_c, 26 * well_r - 1);
			for (int i = 0; i < well_c; i++) {
				for (int j = 0; j < well_r - 1; j++) {
					g.setColor(well[i][j]);
					g.fillRect(26 * i, 26 * j, 25, 25);
				}
			}

			// 점수를 나타낸다.
			this.levelUp();
			g.setColor(Color.WHITE);
			g.drawString("level : " + level, 19 * 10, 26);
			g.drawString("score : " + score, 19 * 10, 52);

			// 블록을 그린다.
			drawPiece(g);
		} else { // 게임이 실행중이지 않을때
			for (int i = 0; i < well_c; i++) {
				for (int j = 0; j < well_r - 1; j++) {
					g.setColor(Color.BLACK);
					g.fillRect(0, 0, 26 * well_c, 26 * well_r - 1);
				}
			}
			g.setColor(Color.WHITE);
			g.drawString(bigWord, 200, 200);
			g.drawString(smallWord, 150, 500);
		}
	}

	
	public static void main(String[] args) {
		JFrame f = new JFrame("Tetris");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(well_c * 26 + 16, (well_r - 1) * 26 + 20);
		f.setVisible(true);

		final Tetris game = new Tetris();
		game.init();
		f.add(game);

		// 키보드 입력
		f.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					game.rotate(-1);
					break;
				case KeyEvent.VK_DOWN:
					game.dropByOne();
					game.score += 1;
					break;
				case KeyEvent.VK_LEFT:
					game.move(-1);
					break;
				case KeyEvent.VK_RIGHT:
					game.move(+1);
					break;
				case KeyEvent.VK_SPACE:
					game.drop();
					break;
				case KeyEvent.VK_CONTROL:
					game.hold();
					holdable = false;
					break;
				case KeyEvent.VK_R:// r
					game.init();
					game.status = "run"; // 추가하면 over에서 실행되긴 함
					break;
				case KeyEvent.VK_Q:// q
					if (game.status == "run") {
						game.status = "pause";
						game.bigWord = "PAUSE";
						game.smallWord = " Press \"Q\" to return the game";
					} else {
						game.status = "run";
						game.bigWord = "";
						game.smallWord = "";
					}
					break;
				}
			}

			public void keyReleased(KeyEvent e) {
			}
		});
		class TetrisThread extends Thread {
			@Override
			public void run() {
				try {
					Thread.sleep(100 + 900 / game.level); // InterruptedException 발생 => 예외 처리(catch) 블록으로 이동
					game.dropByOne();
				} catch (InterruptedException e) {

				}
			}

			@SuppressWarnings("deprecation")
			public void pause() {
				this.resume();
			}
		}
		TetrisThread tet = new TetrisThread();
		while (true) {
			if (game.status == "run") {
				tet.run();
			} else if (game.status == "pause") {
				tet.resume();
			} else if (game.status == "gameover") {
				tet.resume();
			}
		}

	}
}