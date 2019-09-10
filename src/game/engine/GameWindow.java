package game.engine;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameWindow extends JPanel {

	private JFrame main;
	private JPanel back;

	public GameWindow() {

		this.main = new JFrame("Reversi");
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		main.getContentPane().add(this);
		main.setIgnoreRepaint(true);

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
		return this.back;
	}
}
