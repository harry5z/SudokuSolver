package ui;

import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import core.Solver;
import core.SolverListener;

public class SolverPanelUI extends JPanel implements SolverListener {

	private static final long serialVersionUID = -153847978792230232L;
	private SolverBoardUI board;
	
	public SolverPanelUI() {
		Solver solver = new Solver();
		solver.addSolverListener(this);
		board = new SolverBoardUI(solver.getBoard());
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JButton startButton = new JButton("快给老子算！");
		JButton newButton = new JButton("快给老子刷新！");
		startButton.setFont(new Font(Font.MONOSPACED, Font.BOLD, 40));
		startButton.addActionListener(event -> solver.start());
		newButton.setFont(new Font(Font.MONOSPACED, Font.BOLD, 40));
		newButton.addActionListener(event -> {
			solver.newBoard();
			board.setNewBoard(solver.getBoard());
		});
		JPanel buttons = new JPanel();
		buttons.add(newButton);
		buttons.add(startButton);
		add(buttons);
		add(board);
		board.refresh();
	}

	@Override
	public void onSuccess(Solver solver) {
		board.refresh();
		JOptionPane.showConfirmDialog(null, "老子花了" + solver.getTime() + "毫秒算完了，拿去装逼吧");
	}

	@Override
	public void onFailure(Solver solver) {
		JOptionPane.showConfirmDialog(null, "老子算不出来，目测是你的起始条件傻逼了");
	}
}
