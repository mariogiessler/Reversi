package game.engine;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.swing.JButton;
import javax.swing.JPanel;

public class GameCore extends JPanel implements MouseListener {

	// Player-Colors:
	private final static Color PLAYER_COLOR_ONE = new Color(220, 20, 20);
	private final static Color PLAYER_COLOR_TWO = new Color(50, 100, 200);
	private final Color PLAYER_COLOR_NONE = new Color(230, 230, 230, 150);

	// Button-Settings
	private final Font FONT_BUTTON = new Font("Arial", Font.CENTER_BASELINE, 16);
	private final Color BUTTON_COLOR = new Color(250, 180, 50);
	private String gameOverLabel;

	// storing Pin-Data
	private static ArrayList<Pin> pin;

	// variables for the Players
	private static Player playerNow;
	private static Player playerSecond;
	private boolean hasPlayerTurnedPin;
	private int playerNowPossible, playerSecondPossible;

	// Frames and Panels
	private GameWindow main;
	private infoBoard info;
	private Board reversi;
	private JButton restart;

	public GameCore() {
		// initialisize Window and Display-Panels
		main = new GameWindow();
		info = new infoBoard();
		info.setLayout(null);

		reversi = new Board();
		reversi.addMouseListener(this);

		// set Button for Restart on Info-Board (Sidebar)
		restart = new JButton("Restart");
		restart.setFont(FONT_BUTTON);
		restart.setBackground(BUTTON_COLOR);
		restart.setBorderPainted(false);
		restart.setFocusPainted(false);
		restart.setBounds(50, 530, 100, 30);
		restart.addMouseListener(this);

		info.add(restart);

		// add Main-Gameboard and Sidebar to Frame
		main.getBack().add(info, BorderLayout.EAST);
		main.getBack().add(reversi, BorderLayout.WEST);

		start();
	}

	private void start() {
		reversi.setGameOver(false, null);
		initialisePin();
		initPlayer();
	}

	private void restart() {
		// reset Player and Panels
		playerNow.reset();
		playerSecond.reset();
		reversi.removeAll();
		reversi.repaint();

		// redraw Panels
		info.repaint();
		start();
	}

	private void initialisePin() {
		// drawing all Pins to an ArrayList:
		pin = new ArrayList<Pin>();

		for (int y = 1; y <= 8; y++) {
			for (int x = 1; x <= 8; x++) {
				Pin pin1 = new Pin((x - 1) + ((y - 1) * 8), x * 60, y * 60, PLAYER_COLOR_NONE);
				pin.add(pin1);
			}
		}

		// set Start-Pins for each Player
		pin.get(27).setColor(PLAYER_COLOR_ONE);
		pin.get(28).setColor(PLAYER_COLOR_ONE);

		pin.get(35).setColor(PLAYER_COLOR_TWO);
		pin.get(36).setColor(PLAYER_COLOR_TWO);
	}

	private void initPlayer() {
		// create Players
		hasPlayerTurnedPin = false;

		playerNow = new Player(PLAYER_COLOR_ONE, " Rot");
		playerSecond = new Player(PLAYER_COLOR_TWO, "Blau");

		setBackground();
	}

	private void setBackground() {
		// set Background to Player Color
		main.getBack().setBackground(playerNow.getColor());
	}

	private void setMove(int clickedX, int clickedY, int clickedPin) {
		// set Player has done no move
		hasPlayerTurnedPin = false;

		// ask for moves in each Cardinal-direction
		int cardinals = 0;
		int nw = checkMoves(-1, -1, playerNow.getColor(), playerSecond.getColor(), clickedX, clickedY, clickedPin);
		int n = checkMoves(0, -1, playerNow.getColor(), playerSecond.getColor(), clickedX, clickedY, clickedPin);
		int ne = checkMoves(+1, -1, playerNow.getColor(), playerSecond.getColor(), clickedX, clickedY, clickedPin);
		int e = checkMoves(+1, 0, playerNow.getColor(), playerSecond.getColor(), clickedX, clickedY, clickedPin);
		int w = checkMoves(-1, 0, playerNow.getColor(), playerSecond.getColor(), clickedX, clickedY, clickedPin);
		int se = checkMoves(+1, +1, playerNow.getColor(), playerSecond.getColor(), clickedX, clickedY, clickedPin);
		int s = checkMoves(0, +1, playerNow.getColor(), playerSecond.getColor(), clickedX, clickedY, clickedPin);
		int sw = checkMoves(-1, +1, playerNow.getColor(), playerSecond.getColor(), clickedX, clickedY, clickedPin);
		cardinals = nw + n + ne + e + w + sw + s + se;

		// set Player has done a move, if move is possible
		if (cardinals > 0) {
			hasPlayerTurnedPin = true;
		}

		// drawing move Possebilities for each cardinal-direction
		if (nw > 0) {
			for (int i = 0; i <= nw; i++) {
				pin.get(clickedPin - (i * 9)).setColor(playerNow.getColor());
			}
		}
		if (n > 0) {
			for (int i = 0; i <= n; i++) {
				pin.get(clickedPin - (i * 8)).setColor(playerNow.getColor());
			}
		}
		if (ne > 0) {
			for (int i = 0; i <= ne; i++) {
				pin.get(clickedPin - (i * 7)).setColor(playerNow.getColor());
			}
		}
		if (e > 0) {
			for (int i = 0; i <= e; i++) {
				pin.get(clickedPin + i).setColor(playerNow.getColor());
			}
		}
		if (w > 0) {
			for (int i = 0; i <= w; i++) {
				pin.get(clickedPin - i).setColor(playerNow.getColor());
			}

		}
		if (sw > 0) {
			for (int i = 0; i <= sw; i++) {
				pin.get(clickedPin + (i * 7)).setColor(playerNow.getColor());
			}
		}
		if (s > 0) {
			for (int i = 0; i <= s; i++) {
				pin.get(clickedPin + (i * 8)).setColor(playerNow.getColor());
			}
		}
		if (se > 0) {
			for (int i = 0; i <= se; i++) {
				pin.get(clickedPin + (i * 9)).setColor(playerNow.getColor());
			}
		}
		if (hasPlayerTurnedPin) {
			playerNow.increaseMoves(); // count Move
			switchPlayer(); // turn to other Player
		}
	}

	private int checkMoves(int RangeX, int RangeY, Color colorPlayerNow, Color colorPlayerSecond, int clickedX,
			int clickedY, int clickedPin) {

		/*
		 * Checking for possible Moves beginning from clicked Pin and counting how many
		 * turns are there at each Direction.
		 * 
		 * NW, N, NE W, E SW, S, SE
		 */

		int possible = 0;
		int posPin = 0;
		int x = 0;
		int y = 0;

		int rangeWidth = 0;

		if (pin.get(clickedPin).getColor() == PLAYER_COLOR_NONE) {

			while (true) {

				rangeWidth++;

				x = clickedX + (RangeX * rangeWidth);
				y = clickedY + (RangeY * rangeWidth);
				posPin = x + (y * 8);

				if (x < 0 || x > 7) {
					possible = 0;
					break;
				} else if (y < 0 || y > 7) {
					possible = 0;
					break;
				} else {

					if (pin.get(posPin).getColor() == colorPlayerSecond) {
						possible++;
					} else if (pin.get(posPin).getColor() == PLAYER_COLOR_NONE) {
						possible = 0;
						break;
					} else if (pin.get(posPin).getColor() == colorPlayerNow && posPin == clickedPin) {
						possible = 0;
						break;
					} else if (pin.get(posPin).getColor() == colorPlayerNow && possible > 0) {
						break;
					} else {
						possible = 0;
						break;
					}
				}
			}
		}
		return possible;
	}

	private int checkPossibilities(Color playerNowColor, Color playerSecondColor) {
		/*
		 * search Pin-List for each free Pin and check if there are possible Moves
		 */

		// set all Counter for cardinals to zero
		int nw = 0, n = 0, ne = 0;
		int w = 0, e = 0;
		int sw = 0, s = 0, se = 0;
		int possible = 0;

		// search in Pin-List
		for (ListIterator<Pin> i = pin.listIterator(); i.hasNext();) {
			Pin p = i.next();

			// get simulated Pin
			int calculatedPinX = (p.getX() / 60) - 1;
			int calculatedPinY = (p.getY() / 60) - 1;
			int calculatedPos = p.getID();

			if (p.getColor() == PLAYER_COLOR_NONE) { // if Pin isn't set
				int cardinals = 0;

				// check cardinals for Possibility
				nw = checkMoves(-1, -1, playerNowColor, playerSecondColor, calculatedPinX, calculatedPinY,
						calculatedPos);
				n = checkMoves(0, -1, playerNowColor, playerSecondColor, calculatedPinX, calculatedPinY, calculatedPos);
				ne = checkMoves(+1, -1, playerNowColor, playerSecondColor, calculatedPinX, calculatedPinY,
						calculatedPos);
				e = checkMoves(+1, 0, playerNowColor, playerSecondColor, calculatedPinX, calculatedPinY, calculatedPos);
				w = checkMoves(-1, 0, playerNowColor, playerSecondColor, calculatedPinX, calculatedPinY, calculatedPos);
				se = checkMoves(+1, +1, playerNowColor, playerSecondColor, calculatedPinX, calculatedPinY,
						calculatedPos);
				s = checkMoves(0, +1, playerNowColor, playerSecondColor, calculatedPinX, calculatedPinY, calculatedPos);
				sw = checkMoves(-1, +1, playerNowColor, playerSecondColor, calculatedPinX, calculatedPinY,
						calculatedPos);

				cardinals = nw + n + ne + e + w + sw + s + se;

				if (cardinals > 0) { // if there is only one Possibility to Move
					possible++; // count Possibility
				}
			}
		}
		return possible;
	}

	private void switchPlayer() {
		// turn Player to each other
		hasPlayerTurnedPin = false;

		if (!reversi.getGameOver()) {
			Player tmp1 = playerNow;
			Player tmp2 = playerSecond;
			playerNow = tmp2;
			playerSecond = tmp1;
			setBackground();
		}
		checkForGameOver();
		reversi.repaint();
		info.repaint();
	}

	private void checkForGameOver() {
		// checking if there are possible Moves left
		playerNowPossible = 0;
		playerSecondPossible = 0;
		playerNowPossible = checkPossibilities(playerNow.getColor(), playerSecond.getColor());
		playerSecondPossible = checkPossibilities(playerSecond.getColor(), playerNow.getColor());

		if (playerNowPossible == 0 && playerSecondPossible == 0) {
			reversi.setGameOver(true, info.gameOverLabel());
		} else if (playerNowPossible == 0) {
			playerNow.increaseNoMoves();
			switchPlayer();
		}

		if (playerNow.getMoves() + playerNow.getNoMoves() + playerSecond.getMoves() + playerSecond.getNoMoves() >= 60) {
			reversi.setGameOver(true, info.gameOverLabel());
		}

	}

	public static ArrayList<Pin> getPinList() {
		// show Pin-List in other class
		return pin;
	}

	public static Player getPlayerNow() {
		// show Player in other class
		return playerNow;
	}

	public static Player getPlayerSecond() {
		// show second Player in other class
		return playerSecond;
	}
	
	public static Color getPlayerNowColor() {
		// show Player in other class
		return PLAYER_COLOR_ONE;
	}
	
	public static Color getPlayerSecondColor() {
		// show Player in other class
		return PLAYER_COLOR_TWO;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getSource().equals(restart)) { // click on Restart-Button
			restart();
		}

		if (!reversi.getGameOver()) {
			// get Position in Pin-List for this click
			int clickedX = (e.getX() / 60) - 1;
			int clickedY = (e.getY() / 60) - 1;
			int clickedPin = clickedX + (clickedY * 8);

			// get Position out of Pin-List
			for (ListIterator<Pin> i = GameCore.getPinList().listIterator(); i.hasNext();) {

				Pin tmp = i.next();
				// look for click is inside Circle
				if (((tmp.getX() + 26) - e.getX()) * ((tmp.getX() + 26) - e.getX())
						+ ((tmp.getY() + 26) - e.getY()) * ((tmp.getY() + 26) - e.getY()) < 25 * 25) {
					setMove(clickedX, clickedY, clickedPin);
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
