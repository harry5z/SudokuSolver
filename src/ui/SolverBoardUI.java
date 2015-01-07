package ui;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import core.Board;

public class SolverBoardUI extends JPanel {

	private static final long serialVersionUID = -153847978792230232L;
	private InnerBoard[][] grid;
	public SolverBoardUI(Board board) {
		this.grid = new InnerBoard[3][3];
		this.setLayout(new GridLayout(3, 3));
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				this.grid[i][j] = new InnerBoard(board, i * 3, j * 3);
				this.add(this.grid[i][j]);
			}
		}
	}

	public void setNewBoard(Board board) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				this.remove(grid[i][j]);
			}
		}
		this.validate();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				this.grid[i][j] = new InnerBoard(board, i * 3, j * 3);
				this.add(this.grid[i][j]);
			}
		}
		refresh();
	}
	
	public void refresh() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				this.grid[i][j].refresh();
			}
		}
		repaint();
	}
	
	private static class InnerBoard extends JPanel {
		
		private static final long serialVersionUID = 532643791918708284L;
		private SolverCellUI[][] grid;

		InnerBoard(Board board, int h, int v) {
			this.setLayout(new GridLayout(3, 3));
			this.grid = new SolverCellUI[3][3];
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					this.grid[i][j] = new SolverCellUI(board.getCell(i + h, j + v));
					this.add(this.grid[i][j]);
				}
			}
			this.setBorder(new LineBorder(Color.BLACK, 5));
		}
		
		void refresh() {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					this.grid[i][j].update();
				}
			}
		}
	}
}
