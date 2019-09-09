package game.engine;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Pin extends JPanel {

	// Pin-Colors:
	private Color color;
	final Color PIN_SHADOW_LIGHT = new Color(240, 255, 240, 130);
	final Color PIN_SHADOW_DARK = new Color(10, 10, 10, 240);

	// Player-Colors:
	final Color PLAYER_COLOR_NONE = new Color(230, 230, 230, 150);
	final Color PLAYER_COLOR_ONE = new Color(220, 20, 20);
	final Color PLAYER_COLOR_TWO = new Color(50, 100, 200);

	int x;
	int y;
	int id;
	private boolean isPin;

	public Pin(int ID, int x, int y, Color color) {
		this.id = ID;
		this.x = x;
		this.y = y;
		this.color = color;
		this.isPin = false;
	}

	public void setColor(Color color) {
		this.color = color;
		setIsPin();
	}

	private void setIsPin() {
		this.isPin = true;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public Color getColor() {
		return this.color;
	}

	public boolean getIsPin() {
		return this.isPin;
	}

	public int getID() {
		return this.id;
	}

	public void drawMe(Graphics2D g2) {
		// Drawing Pins
		if (!this.isPin) {
			g2.setColor(PIN_SHADOW_LIGHT);
			g2.fillOval(this.x - 2, this.y - 2, 50, 50);
			g2.setColor(PIN_SHADOW_DARK);
			g2.fillOval(this.x - 2, this.y - 2, 52, 52);
		} else {
			g2.setColor(PIN_SHADOW_DARK);
			g2.fillOval(this.x + 5, this.y + 5, 49, 49);
		}

		g2.setColor(this.color);
		g2.fillOval(this.x, this.y, 51, 51);
	}

}
