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

public class Board extends JPanel {

	// Board-Colors:
	private final Color MAIN_COLOR = new Color(250, 250, 250, 255);
	final static Color SHADOW_COLOR = new Color(10, 10, 10, 30);

	// Game-Over Colors:
	private final Color LABEL_COLOR = new Color(240, 255, 240, 160);
	private final Color LABEL_COLOR_FONT = new Color(0, 0, 0);
	private final Color LABEL_COLOR_BORDER = new Color(255, 185, 19, 180);

	// Font for Game-Over Label
	private final Font FONT_HEADLINE = new Font("Arial", Font.BOLD, 60);
	private final Font FONT_MAIN = new Font("Arial", Font.BOLD, 25);

	// other Settings for Game over
	private boolean isGameOver;
	private String gameOverLabel;

	public Board() {
		
		// set this Panel (Gameboard)
		new JPanel();
		this.setPreferredSize(new Dimension(600, 600));
		this.setOpaque(false);
	}

	public void setGameOver(boolean isGameOver, String gameOverLabel) {
		// is getting the Game-Over Message from GameCore-class
		this.isGameOver = isGameOver;
		this.gameOverLabel = gameOverLabel;
	}
	
	public boolean getGameOver() {
		// tell other class if is Game over
		return this.isGameOver;
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		Dimension d = this.getPreferredSize();

		//drawing Board
		g2.setColor(SHADOW_COLOR);
		g2.fillRoundRect(30, 25, d.width - 52, d.height - 61, 100, 100);
		g2.setColor(MAIN_COLOR);
		g2.fillRoundRect(20, 20, d.width - 50, d.height - 60, 100, 100);

		// drawing Pins on Board
		for (ListIterator<Pin> i = GameCore.getPinList().listIterator(); i.hasNext();) {
			Pin tmp = i.next();
			// draw every Pin in List
			tmp.drawMe(g2);
		}

		// Paint Game-Over Label
		if (isGameOver) {
			g2.setColor(LABEL_COLOR);
			g2.fillRoundRect(60, 200, 470, 200, 100, 100);
			g2.setColor(LABEL_COLOR_BORDER);
			g2.setStroke(new BasicStroke(10));
			g2.drawRoundRect(55, 195, 480, 210, 100, 100);
			g2.setColor(LABEL_COLOR_FONT);
			g2.setFont(FONT_HEADLINE);
			g2.drawString("GAME OVER!", 100, 290);
			g2.setFont(FONT_MAIN);
			g2.drawString(gameOverLabel, 110, 330);
		}
	}
}
