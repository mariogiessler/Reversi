package game.engine;

import java.awt.Color;

public class Player {
	
	private String name;
	private Color color;
	private int movesDone, noMovesDone;

	public Player(Color color, String name) {
		// create a Player
		this.color = color;
		this.movesDone = 0;
		this.noMovesDone = 0;
		this.name = name;
	}

	public Color getColor() {
		// make Color present for other class
		return this.color;
	}

	public String getLabel() {
		// make Player Name present for other class
		return this.name;
	}

	public int getMoves() {
		// make Player Moves (done) present for other class
		return this.movesDone;
	}

	public int getNoMoves() {
		// make Player Moves (if there was some Moves not Possible) present for other class
		return this.noMovesDone;
	}

	public void increaseMoves() {
		// count Moves from Core-class
		this.movesDone++;
	}
	
	public void increaseNoMoves() {
		// count Move not Possible from Core-class
		this.noMovesDone++;
	}
	
	public void reset() {
		// Reset Player Moves
		this.movesDone=0;
		this.noMovesDone=0;
	}
}
