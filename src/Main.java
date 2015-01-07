import javax.swing.JFrame;

import ui.SolverPanelUI;


public class Main {

	public static void main(String[] args) {
		SolverPanelUI panel = new SolverPanelUI();
		JFrame frame = new JFrame("Sudoku Solver");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}
}
