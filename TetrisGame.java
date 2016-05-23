/*
 
 Contains main class. Creates GUI via swing libraries
 
 Authors : Dhrumil Patel
 
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Stack;

/**
 * Main Tetris game class that creates the frame of tetris game on it's object creation
 *
 */
public class TetrisGame extends JFrame implements KeyListener, ActionListener {
	
	private JLabel Board[][], sc;
	private int score = 0, level = 1,timer, right = 4, down = 0, currentCoordinates[][], next, count = 0, delay, row = 20, column = 10;
	private boolean stop = false, timeToggle;

	private JButton timeButton;

	private Timer timeClock, TIMEELAPSED;
	
	
	private Icon iconArray[];
	private String names[] = { "bluesquare.jpg", "blacksquare.jpg", "whitesquare.jpg" }, abouter = "Default";
	private Tetromino Tetrominoes[],
			currentTetromino;
	private JButton restart;

	
	private JPanel NextPanel, rightPanel;
	
	
	private JLabel countLabel, nextBoard[][], timeLabel, levelLabel;
	
	
	


	/**
	 * Constructor Method that constructs the Game (Including frame)
	 */
	private TetrisGame() {
		super("Tetris Game");
		
		addKeyListener(this);
		Container c = getContentPane();
		c.setLayout(new BorderLayout());

		JPanel labelPanel = new JPanel();
		NextPanel = new JPanel();
		NextPanel.setLayout(new GridLayout(10, 10, 0, 0));
		NextPanel.setSize(200, 200);
		rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(2, 1));
		rightPanel.setSize(400, 200);
		row = 20;
		column = 10;
		labelPanel.setLayout(new GridLayout(row, column, 0, 0));
		labelPanel.setSize(row * 20, column * 20);

		c.add(labelPanel, BorderLayout.WEST);

		// create and add icons
		iconArray = new Icon[names.length];

		for (int count = 0; count < names.length; count++) {
			iconArray[count] = new ImageIcon(names[count]);
		}

		Board = new JLabel[row][column];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				Board[i][j] = new JLabel(iconArray[2]);
				labelPanel.add(Board[i][j]);
			}
		}

		nextBoard = new JLabel[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				nextBoard[i][j] = new JLabel(iconArray[2]);
				NextPanel.add(nextBoard[i][j]);
			}
		}
		currentTetromino = new L("awaker");
		this.Tetrominoes = currentTetromino.getList();
		currentTetromino = Tetrominoes[(int) Math.floor(Math.random() * 7)];
		next = (int) Math.floor(Math.random() * 7);
		nextPainter();
		currentCoordinates = currentTetromino.getCurrent();

		delay = 1000 * (50 - (level * 2)) / 60;
		TIMEELAPSED = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				timer++;
				timeLabel.setText("Time Elapsed: " + timer);

			}

		});
		TIMEELAPSED.start();
		timeClock = new Timer(delay, this);
		// create button
		timeButton = new JButton("Start");
		timeButton.setFocusable(false);
		restart = new JButton("Restart");
		restart.setFocusable(false);

		JPanel pane = new JPanel();
		pane.setLayout(new GridLayout(6, 1));
		sc = new JLabel("Score: " + score);
		levelLabel = new JLabel("Level: " + level);
		countLabel = new JLabel("Lines Cleared: " + count);
		timeLabel = new JLabel("Time Elapsed: " + timer);
		pane.add(levelLabel);
		pane.add(sc);
		pane.add(countLabel);
		pane.add(timeLabel);
		pane.add(timeButton);
		pane.add(restart);

		rightPanel.add(pane);
		rightPanel.add(NextPanel);
		JPanel temp = new JPanel();
		temp.setSize(200, 20);
		add(temp, BorderLayout.CENTER);
		c.add(rightPanel, BorderLayout.EAST);

		// c.add( timeButton, BorderLayout.CENTER);
		ButtonHandler handler = new ButtonHandler();
		timeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				if (timeToggle == false) {
					timeClock.start();
					timeButton.setText("Pause");
				} else {
					timeClock.stop();
					timeButton.setText("Start");
				}
				timeToggle = !timeToggle;
			}

		});
		restart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				restart();

			}

		});
		timeToggle = false;

		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Menu");
		menuBar.add(menu);
		JMenuItem exit = new JMenuItem("Exit");
		JMenuItem about = new JMenuItem("About");
		JMenuItem howtoplay = new JMenuItem("How to Play!");
		JMenuItem Restart = new JMenuItem("Restart Game");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		about.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "5th Programming project for CS342.\nAuthors: Dhrumil Patel");
			}
		});
		howtoplay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,
						"RightArrow: Move right\nLeftArrow: Move Right\nUpArrow: Rotate Tetromino\nDownArrow: Drop");
			}
		});
		Restart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				restart();
			}
		});
		menu.add(howtoplay);
		menu.add(Restart);
		menu.add(about);
		menu.add(exit);
		this.setJMenuBar(menuBar);

		// setSize( 275, 170 );
		pack();
		show();
	}

	/**
	 * @param coordinates coordinates of blocks 
	 * @return if that coordinated move is valid returns true, else false
	 */
	boolean isValidMove(int[][] coordinates) {
		boolean valid = true;
		int x, y;

		for (int i = 0; i < 4; i++) {
			x = coordinates[i][0] + down;
			y = coordinates[i][1] + right;
			if (x < row && y >= 0 && y < column) {
				if (x >= 0) {
					if (!Board[x][y].getIcon().equals(this.iconArray[1])) {
						valid = true;
					} else {
						valid = false;
						break;
					}
				}

			} else {
				valid = false;
				break;
			}
		}

		return valid;

	}

	public static void main(String args[]) {
		TetrisGame app = new TetrisGame();

		app.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	/**
	 * restarts the game, reset every data game has i.e score, level etc including clearing the board
	 */
	void restart() {
		count = 0;
		score = 0;
		level = 1;
		down = 0;
		right = 4;
		this.timer = 0;

		timeLabel.setText("Time Elapsed: " + timer);
		TIMEELAPSED.start();
		delay = 1000 * (50 - (level * 2)) / 60;
		this.timeClock.start();
		//clears the board
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 10; j++) {
				Board[i][j].setIcon(this.iconArray[2]);
			}
		}

		currentTetromino = Tetrominoes[next];
		next = (int) Math.floor(Math.random() * 7);
		nextPainter();
		currentCoordinates = currentTetromino.getCurrent();
		this.timeToggle = true;
		sc.setText("Score: " + score);
		countLabel.setText("Lines Cleared: " + count);
		levelLabel.setText("Level: " + level);
		this.timeButton.setText("Pause");
	}

	// inner class for button event handling
	private class ButtonHandler implements ActionListener {

		// handle button event
		public void actionPerformed(ActionEvent event) {
			if (timeToggle == false) {
				timeClock.start();
				// TIMEELAPSED.start();
				timeButton.setText("Pause");
			} else {

				timeClock.stop();

				timeButton.setText("Start");
			}

			timeToggle = !timeToggle;
		}

	} // end private inner class ButtonHandler

	// inner class for timer event handling

	@Override
	public void actionPerformed(ActionEvent e) {
		int x, y, temp;
		if (this.isValidMove(getOff(currentTetromino.getCurrent(), 1, 0))) {
			
			if (!currentCoordinates.equals(null)) {
				
				for (int i = 0; i < 4; i++) {
					x = currentCoordinates[i][0];
					y = currentCoordinates[i][1];
					if (x + down >= 0) {
						Board[x + down][y + right].setIcon(this.iconArray[2]);
					}
				}
			}
			currentCoordinates = currentTetromino.getCurrent();
			down++;
			for (int i = 0; i < 4; i++) {
				x = currentCoordinates[i][0];
				y = currentCoordinates[i][1];
				if (x + down >= 0) {
					Board[x + down][y + right].setIcon(this.iconArray[0]);
				}
			}
		} else {
			this.currentTetromino.setDefault();
			if (down == 0) {
				this.currentTetromino.setDefault();
				timeClock.stop();
				JOptionPane.showMessageDialog(this, "Game Ended, Score: " + score);

				TIMEELAPSED.stop();
				// timeButton.setText("start");
			}
			
			for (int i = 0; i < 4; i++) {
				x = currentCoordinates[i][0];
				y = currentCoordinates[i][1];
				if (x + down >= 0) {
					Board[x + down][y + right].setIcon(this.iconArray[1]);
				}
			}

			currentTetromino = Tetrominoes[next];
			next = (int) Math.floor(Math.random() * 7);
			nextPainter();
			down = 0;
			right = 4;
			checker();
		}

	}

	/**
	 * Paints the next Tetromino on Next Screen
	 */
	private void nextPainter() {
		// TODO Auto-generated method stub
		Tetromino temp = this.Tetrominoes[next];
		int[][] ne = temp.getDefault();
		boolean equal = false;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				equal = false;
				if (nextBoard[i][j].getIcon().equals(this.iconArray[0])) {
					nextBoard[i][j].setIcon(this.iconArray[2]);
				}

				for (int k = 0; k < 4; k++) {
					if (i == ne[k][0] + 4 && j == ne[k][1] + 3) {
						equal = true;
						break;
					}
				}
				if (equal) {
					nextBoard[i][j].setIcon(this.iconArray[0]);
				}
			}
		}

	}

	/**
	 * @param cord coordinates of Tetromino
	 * @param down offset downward
	 * @param right offset rightward
	 * @return Coordinates of tetromino with offset down downward and right rightward
	 */
	private int[][] getOff(int[][] cord, int down, int right) {
		// TODO Auto-generated method stub
		int[][] temp = new int[4][2];
		for (int i = 0; i < 4; i++) {

			temp[i][0] = cord[i][0] + down;
			temp[i][1] = cord[i][1] + right;

		}
		return temp;

	}

	/**
	 * Checks if their is any completed line, clears that line
	 */
	private void checker() {
		Stack complete = new Stack();
		boolean filled;
		for (int i = 0; i < 20; i++) {
			filled = true;
			for (int j = 0; j < 10; j++) {
				if (!Board[i][j].getIcon().equals(this.iconArray[1])) {
					filled = false;
					break;
				}
			}
			if (filled) {

				count++;
				countLabel.setText("Lines Cleared: " + count);
				if (count % 10 == 0) {

					if (level <= 24) {
						level++;
						this.levelLabel.setText("Level: " + level);
						delay = 1000 * (50 - (level * 2)) / 60;
						this.timeClock.setDelay(delay);
					}
				}
				complete.push(i);
				
				for (int j = 0; j < 10; j++) {
					Board[i][j].setIcon(this.iconArray[2]);
				}
			}
		}
		int end, val, size = complete.size();
		if (size == 1) {
			this.score = this.score + (40 * level);

		} else if (size == 2) {
			this.score = this.score + (100 * level);
		} else if (size == 3) {
			this.score = this.score + (300 * level);
		} else if (size == 4) {
			this.score = this.score + (1200 * level);
		}
		this.sc.setText("Score: " + score);

		for (int i = 0; i < size; i++) {
			val = (int) complete.pop();

			if (complete.isEmpty()) {
				end = 0;
			} else {
				end = (int) complete.peek();
			}
			for (int j = val - 1; j >= end; j--) {
				for (int k = 0; k < 10; k++) {
					if (Board[j][k].getIcon().equals(this.iconArray[1])) {
						Board[j][k].setIcon(this.iconArray[2]);
						Board[j + i + 1][k].setIcon(this.iconArray[1]);
					}
				}
			}
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			this.timeClock.restart();
			int x, y;
			if (this.isValidMove(this.getOff(this.currentTetromino.getCurrent(), 0, 1))) {
				if (!currentCoordinates.equals(null)) {

					for (int i = 0; i < 4; i++) {
						x = currentCoordinates[i][0];
						y = currentCoordinates[i][1];
						if (x + down >= 0) {
							Board[x + down][y + right].setIcon(this.iconArray[2]);
						}
					}
				}
				right++;

				if (!currentCoordinates.equals(null)) {
					
					for (int i = 0; i < 4; i++) {
						x = currentCoordinates[i][0];
						y = currentCoordinates[i][1];
						if (x + down >= 0) {
							Board[x + down][y + right].setIcon(this.iconArray[0]);
						}
					}
				}
			}

		}

		else if (e.getKeyCode() == KeyEvent.VK_LEFT) {

			this.timeClock.restart();
			int x, y;
			if (this.isValidMove(this.getOff(this.currentTetromino.getCurrent(), 0, -1))) {
				if (!currentCoordinates.equals(null)) {

					for (int i = 0; i < 4; i++) {
						x = currentCoordinates[i][0];
						y = currentCoordinates[i][1];
						if (x + down >= 0) {
							Board[x + down][y + right].setIcon(this.iconArray[2]);
						}
					}
				}
				right--;

				if (!currentCoordinates.equals(null)) {
					
					for (int i = 0; i < 4; i++) {
						x = currentCoordinates[i][0];
						y = currentCoordinates[i][1];
						if (x + down >= 0) {
							Board[x + down][y + right].setIcon(this.iconArray[0]);
						}
					}
				}
			}

		}

		else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			int temp = down, x, y;
			while (this.isValidMove(this.getOff(currentCoordinates, 1, 0))) {

				if (!currentCoordinates.equals(null)) {

					for (int i = 0; i < 4; i++) {
						x = currentCoordinates[i][0];
						y = currentCoordinates[i][1];
						if (x + down >= 0) {
							Board[x + down][y + right].setIcon(this.iconArray[2]);
						}
					}
				}
				currentCoordinates = currentTetromino.getCurrent();
				down++;
				for (int i = 0; i < 4; i++) {
					x = currentCoordinates[i][0];
					y = currentCoordinates[i][1];
					if (x + down >= 0) {
						Board[x + down][y + right].setIcon(this.iconArray[0]);
					}
				}
				checker();
			}

		}

		else if (e.getKeyCode() == KeyEvent.VK_UP) {
			this.timeClock.restart();
			int x, y;
			if (this.isValidMove(this.currentTetromino.getNext())) {
				if (!currentCoordinates.equals(null)) {

					for (int i = 0; i < 4; i++) {
						x = currentCoordinates[i][0];
						y = currentCoordinates[i][1];
						if (x + down >= 0) {
							Board[x + down][y + right].setIcon(this.iconArray[2]);
						}
					}
				}
				currentCoordinates = currentTetromino.getNext();
				currentTetromino.rotate();
				if (!currentCoordinates.equals(null)) {
					
					for (int i = 0; i < 4; i++) {
						x = currentCoordinates[i][0];
						y = currentCoordinates[i][1];
						if (x + down >= 0) {
							Board[x + down][y + right].setIcon(this.iconArray[0]);
						}
					}
				}
			}
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	

}
