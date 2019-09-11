package game.engine;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Pin extends JPanel {

	// Pin-Colors:
	private Color color;
	private final Color PIN_SHADOW_LIGHT = new Color(240, 255, 240, 130);
	private final Color PIN_SHADOW_DARK = new Color(10, 10, 10, 240);

	private int x;
	private int y;
	private int id;
	private boolean isPin;

	public Pin(int ID, int x, int y, Color color) {
		// create a Pin
		this.id = ID;
		this.x = x;
		this.y = y;
		this.color = color;
		this.isPin = false;
	}

	public void drawMe(Graphics2D g2) {
		// Drawing Pins
		if (!this.isPin) { // drawing Pin-Shadow positive
			g2.setColor(PIN_SHADOW_LIGHT);
			g2.fillOval(this.x - 2, this.y - 2, 50, 50);
			g2.setColor(PIN_SHADOW_DARK);
			g2.fillOval(this.x - 2, this.y - 2, 52, 52);
		} else {// drawing Pin-Shadow negative
			g2.setColor(PIN_SHADOW_DARK);
			g2.fillOval(this.x + 5, this.y + 5, 49, 49);
		}

		// drawing Pin-Deck
		g2.setColor(this.color);
		g2.fillOval(this.x, this.y, 51, 51);
	}

	public Color getColor() {
		// make Color present for other class
		return this.color;
	}

	public int getID() {
		// make ID present for other class
		return this.id;
	}

	public int getX() {
		// make X-Coordinate present for other class
		return this.x;
	}

	public int getY() {
		// make Y-Coordinate present for other class
		return this.y;
	}

	public void setColor(Color color) {
		// set Player Color from Gam-Core
		this.color = color;
		setIsPin();
	}

	private void setIsPin() {
		// set Pin as Pin to different the drawing
		this.isPin = true;
	}

}
