package core;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import exceptions.CandidateNotFoundException;
import exceptions.NoCandidateException;

public class Solver {
	
	private Board board;
	private Set<SolverListener> listeners;
	
	public Solver() {
		this.board = new Board();
		this.listeners = new HashSet<SolverListener>();
	}
	
	public Board getBoard() {
		return board;
	}
	
	public void addSolverListener(SolverListener listener) {
		listeners.add(listener);
	}
	
	public boolean removeSolverListener(SolverListener listener) {
		return listeners.remove(listener);
	}

	public void start() {
		long time = System.currentTimeMillis();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				Cell cell = board.getCell(i, j);
				if (cell.isFixed()) {
					try {
						removeCandidate(i, j, cell.getValue());
					} catch (CandidateNotFoundException | NoCandidateException e) {
						return;
					}
				}
			}
		}
		boolean solved = solve();
		time = System.currentTimeMillis() - time;
		for (SolverListener listener : listeners) {
			if (solved) {
				listener.onSuccess(time);
			} else {
				listener.onFailure("老子算不出来，目测是你的起始条件傻逼了");
			}
		}
	}
	
	private void removeCandidate(int i, int j, int candidate) throws NoCandidateException, CandidateNotFoundException {
		for (int x = 0; x < 9; x++) {
			board.getCell(x, j).removeCandidate(candidate);
		}
		for (int y = 0; y < 9; y++) {
			board.getCell(i, y).removeCandidate(candidate);
		}
		for (int x = i - i % 3; x < i - i % 3 + 3; x++) {
			for (int y = j - j % 3; y < j - j % 3 + 3; y++) {
				board.getCell(x, y).removeCandidate(candidate);
			}
		}
	}
	
	private void restoreCandidate(int i, int j, int candidate) {
		for (int x = 0; x < 9; x++) {
			if (board.getCell(x, j).isFixed() && board.getCell(x, j).getValue() == candidate) {
				return;
			}
		}		
		for (int y = 0; y < 9; y++) {
			if (board.getCell(i, y).isFixed() && board.getCell(i, y).getValue() == candidate) {
				return;
			}
		}
		for (int x = i - i % 3; x < i - i % 3 + 3; x++) {
			for (int y = j - j % 3; y < j - j % 3 + 3; y++) {
				if (board.getCell(x, y).isFixed() && board.getCell(x, y).getValue() == candidate) {
					return;
				}
			}
		}
		board.getCell(i, j).addCandidate(candidate);
	}
	private void addCandidate(int i, int j, int candidate) {
		for (int x = 0; x < 9; x++) {
			if (x != i) {
				restoreCandidate(x, j, candidate);
			}
		}
		for (int y = 0; y < 9; y++) {
			if (y != j) {
				restoreCandidate(i, y, candidate);
			}
		}
		for (int x = i - i % 3; x < i - i % 3 + 3; x++) {
			for (int y = j - j % 3; y < j - j % 3 + 3; y++) {
				if (!(x == i && y == j)) {
					restoreCandidate(x, y, candidate);
				}
			}
		}
	}
	
	private boolean solve() {
		Cell toTry = null;
		int minCandidates = Integer.MAX_VALUE;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				Cell cell = board.getCell(i, j);
				if (cell.isFixed()) {
					continue;
				}
				int candidateCount = cell.getCandidateCount();
				if (candidateCount < minCandidates && candidateCount > 0) {
					minCandidates = candidateCount;
					toTry = cell;
				}
			}
		}
		if (minCandidates == Integer.MAX_VALUE) {
			return true;
		}
		Iterator<Integer> candidates = toTry.getCandidates().iterator();
		while (candidates.hasNext()) {
			int tryValue = candidates.next();
			toTry.fixCandidate(tryValue);
			try {
				removeCandidate(toTry.getX(), toTry.getY(), tryValue);
			} catch (NoCandidateException | CandidateNotFoundException e) {
				toTry.unfix();
				addCandidate(toTry.getX(), toTry.getY(), tryValue);
				return false;
			}
			if (solve()) {
				return true;
			} else {
				toTry.unfix();
				addCandidate(toTry.getX(), toTry.getY(), tryValue);
			}
		}
		toTry.unfix();
		return false;
	}
	
	private Set<Integer> getFullSet() {
		Set<Integer> set = new HashSet<Integer>();
		for (int i = 1; i < 9; i++) {
			set.add(i);
		}
		return set;
	}
	
	public boolean verify() {
		for (int i = 0; i < 9; i++) {
			Set<Integer> setH = getFullSet();
			Set<Integer> setV = getFullSet();
			for (int j = 0; j < 9; j++) {
				if (!board.getCell(i, j).isFixed() || !board.getCell(j, i).isFixed()) {
					return false;
				}
				setH.remove(board.getCell(i, j).getValue());
				setV.remove(board.getCell(j, i).getValue());
			}
			if (!setH.isEmpty() || !setV.isEmpty()) {
				return false;
			}
		}
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				Set<Integer> set = getFullSet();
				for (int x = i * 3; x < i * 3 + 3; x++) {
					for (int y = j * 3; y < j * 3 + 3; y++) {
						if (!board.getCell(x, y).isFixed()) {
							return false;
						}
						set.remove(board.getCell(x, y).getValue());
					}
				}
				if (!set.isEmpty()) {
					return false;
				}
			}
		}
		return true;
	}

	public void newBoard() {
		board = new Board();
	}
}
