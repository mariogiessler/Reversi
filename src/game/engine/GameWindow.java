package game.engine;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameWindow extends JPanel {

	private JFrame main;
	private JPanel back;

	public GameWindow() {

		// create the Main-Frame
		this.main = new JFrame("Reversi");
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		main.getContentPane().add(this);
		main.setIgnoreRepaint(true);

		// define Background-Panel-Layer
		back = new JPanel();
		back.setPreferredSize(new Dimension(800, 600));
		back.setLayout(new BorderLayout());
		main.add(back);

		main.pack();

		main.setLocationRelativeTo(null);
		main.setVisible(true);
		main.setResizable(false);
	}

	public JPanel getBack() {
		// so can other class call on Back-Panel-Layer
		return this.back;
	}
}
