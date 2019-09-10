package game.engine;

import java.awt.Color;

public class Player {
	private String label;
	private String name;
	private Color color;
	private int movesDone, noMovesDone;

	public Player(Color color, String label, String name) {
		this.color = color;
		this.movesDone = 0;
		this.noMovesDone = 0;
		this.label = label;
		this.name = name;
	}

	public Color getColor() {
		return this.color;
	}

	public String getLabel() {
		return this.label;
	}

	public int getMoves() {
		return this.movesDone;
	}

	public String getName() {
		return this.name;
	}

	public int getNoMoves() {
		return this.noMovesDone;
	}

	public void increaseMoves() {
		this.movesDone++;
	}

	public void increaseNoMoves() {
		this.noMovesDone++;
	}
}
