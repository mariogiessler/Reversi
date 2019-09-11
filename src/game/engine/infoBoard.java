package game.engine;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ListIterator;

import javax.swing.JPanel;

public class infoBoard extends JPanel {

	// Label-Text:
	private String PLAYER_BEFORE_TEXT;
	private String PLAYER_AFTER_TEXT;
	private String DONE_MOVES_BEFORE_TEXT;

	// Info-Board-Colors:
	private final Color LABEL_FONT_COLOR = new Color(255, 255, 255, 220);
	private final Color LABEL_FONT_COLOR_DARK = new Color(0, 0, 0, 220);
	private final Color LABEL_COLOR = new Color(250, 250, 250, 100);
	private final Color LABEL_COLOR_BORDER_LIGHT = new Color(30, 30, 30, 80);
	private final Color LABEL_COLOR_BORDER_DARK = new Color(5, 5, 5, 20);

	// Fonts:
	private final Font FONT_MAIN = new Font("Arial", Font.PLAIN, 20);
	private final Font FONT_MAIN_2 = new Font("Arial", Font.PLAIN, 16);
	private final Font FONT_HEADLINE = new Font("Arial", Font.BOLD, 50);
	
	// other Settings for Game over
	private String gameOverLabel;
	
	// Counters for get the Winner
	private int playerOnePins, playerTwoPins;


	public infoBoard() {		
		// set this Panel (Sidebar)
		new JPanel();
		this.setPreferredSize(new Dimension(200, 600));
		this.setOpaque(false);
		this.setVisible(true);
		
		setPlayersTurn();
	}
	
	private void setPlayersTurn() {
		// initialize Text for Players turn
		PLAYER_BEFORE_TEXT = "Spieler ";
		PLAYER_AFTER_TEXT = " ist am Zug!";
		DONE_MOVES_BEFORE_TEXT = "Du hast bisher";
	}

	private String movesDone(int playerMoves) {
		// create an output for actual Player Moves
		String movesText = String.valueOf(GameCore.getPlayerNow().getMoves());

		if (GameCore.getPlayerNow().getMoves() < 10) {
			movesText += " ";
		}
		if (GameCore.getPlayerNow().getMoves() == 1) {
			movesText += " Zug ";
		} else {
			movesText += " Züge";
		}
		movesText += " gespielt!";

		return movesText;
	}
	
		public String gameOverLabel() {
		// create the text for Game-Over-Label
		gameOverLabel = "Spieler ";

		// count set Moves for each Player to get the Winner
		for (ListIterator<Pin> i = GameCore.getPinList().listIterator(); i.hasNext();) {

			Pin p = i.next();

			Color color = p.getColor();

			if (color == GameCore.getPlayerNowColor()) {
				playerOnePins++;
			} else if (color == GameCore.getPlayerSecondColor()) {
				playerTwoPins++;
			}
		}

		// create Winner Text
		if (playerOnePins > playerTwoPins) {
			gameOverLabel += " ROT  hat gewonnen !!!";
		} else if (playerTwoPins > playerOnePins) {
			gameOverLabel += "BLAU  hat gewonnen !!!";
		} else {
			gameOverLabel = "          UNENTSCHIEDEN !!!";
		}

		return gameOverLabel;
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		Dimension d = this.getPreferredSize();

		if (GameCore.getPlayerNow() != null) {
			// Label-"Sticker":
			g2.setColor(Board.SHADOW_COLOR);
			g2.setStroke(new BasicStroke(12));
			g2.fillRect((d.width / 2) - 80, 20, 165, 165);
			g2.setColor(LABEL_COLOR);
			g2.fillRect((d.width / 2) - 80, 20, 160, 160);
			g2.setStroke(new BasicStroke(8));
			g2.setColor(LABEL_COLOR_BORDER_LIGHT);
			g2.drawRect((d.width / 2) - 70, 30, 140, 140);
			g2.setStroke(new BasicStroke(2));
			g2.setColor(LABEL_COLOR_BORDER_DARK);
			g2.drawRect((d.width / 2) - 72, 29, 144, 142);

			// showing Players turn:
			g2.setFont(FONT_MAIN);
			g2.setColor(LABEL_FONT_COLOR_DARK);

			g2.drawString(PLAYER_BEFORE_TEXT, (d.width / 2) - 30, 65);
			g2.setFont(FONT_HEADLINE);

			// showing Player Name
			g2.drawString(GameCore.getPlayerNow().getLabel(), (d.width / 2) - 55, 115);

			g2.setFont(FONT_MAIN);
			g2.drawString(PLAYER_AFTER_TEXT, (d.width / 2) - 50, 145);

			// showing done Game-Moves
			g2.setFont(FONT_MAIN_2);
			g2.setColor(LABEL_FONT_COLOR);

			// showing Moves done
			g2.drawString(DONE_MOVES_BEFORE_TEXT, (d.width / 2) - 50, 220);
			g2.drawString(movesDone(GameCore.getPlayerNow().getMoves()), (d.width / 2) - 60, 245);
		}
	}

}
