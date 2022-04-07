package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import core.Cell;

public class SolverCellUI extends JButton {

 	private static final long serialVersionUID = 1001;
	private Cell cell;
	
	public SolverCellUI(Cell cell) {
		this.cell = cell;
		this.setPreferredSize(new Dimension(100, 100));
		this.addActionListener(event -> {
			try {
				int input = Integer.parseInt(JOptionPane.showInputDialog("Input an initial value"));
				if (input < 1 || input > 9) {
					throw new NumberFormatException();
				}
				this.cell.setInitialValue(input);
				update(false);
			} catch (NumberFormatException exception) {
				JOptionPane.showMessageDialog(null, "Must be an integer from 1 to 9");
			}
		});
	}
	
	public void setNewCell(Cell cell) {
		this.cell = cell;
		this.setBackground(null);
		this.setText(null);
		this.setEnabled(true);
	}
	
	public void update(boolean freeze) {
		if (cell.isFixed()) {
			if (cell.isInitial()) {
				this.setBackground(Color.WHITE);
			} else {
				this.setBackground(Color.YELLOW);
			}
			this.setFont(new Font(Font.MONOSPACED, Font.BOLD, 40));
			this.setText(Integer.toString(cell.getValue()));
			this.setEnabled(!freeze);
		}
	}
}
