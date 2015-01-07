package core;

import exceptions.InvalidInputException;

public class Board {

	private final Cell[][] grid;
	
	public Board() {
		this.grid = new Cell[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				this.grid[i][j] = new Cell(i, j);
			}
		}
//		grid[0][0].setInitialValue(8);
//		grid[1][2].setInitialValue(3);
//		grid[1][3].setInitialValue(6);
//		grid[2][1].setInitialValue(7);
//		grid[2][4].setInitialValue(9);
//		grid[2][6].setInitialValue(2);
//		grid[3][1].setInitialValue(5);
//		grid[3][5].setInitialValue(7);
//		grid[4][4].setInitialValue(4);
//		grid[4][5].setInitialValue(5);
//		grid[4][6].setInitialValue(7);
//		grid[5][3].setInitialValue(1);
//		grid[5][7].setInitialValue(3);
//		grid[6][2].setInitialValue(1);
//		grid[6][7].setInitialValue(6);
//		grid[6][8].setInitialValue(8);
//		grid[7][2].setInitialValue(8);
//		grid[7][3].setInitialValue(5);
//		grid[7][7].setInitialValue(1);
//		grid[8][1].setInitialValue(9);
//		grid[8][6].setInitialValue(4);
	}
	
	public Cell getCell(int i, int j) {
		if (i < 0 || i > 8 || j < 0 || j > 8) {
			throw new InvalidInputException("Getting cell at invalid location (" + i + ", " + j + ")");
		}
		return grid[i][j];
	}
}
