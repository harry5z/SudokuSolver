package core;

import java.util.HashSet;
import java.util.Set;

import exceptions.CandidateNotFoundException;
import exceptions.InvalidInputException;
import exceptions.NoCandidateException;

public class Cell {
	
	private boolean fixed;
	private boolean initial;
	private Set<Integer> candidates;
	private int value;
	private final int x;
	private final int y;
	
	public Cell(int x, int y) {
		this.candidates = new HashSet<Integer>();
		for (int i = 1; i <= 9; i++) {
			this.candidates.add(i);
		}
		this.value = 0;
		this.fixed = false;
		this.initial = false;
		this.x = x;
		this.y = y;
	}
	
	public void setInitialValue(int value) {
		if (value < 1 || value > 9) {
			throw new InvalidInputException("Invalid initial value");
		}
		this.value = value;
		this.fixed = true;
		this.initial = true;
		this.candidates.clear();
	}
	
	public boolean isFixed() {
		return fixed;
	}
	
	public boolean isInitial() {
		return initial;
	}
	
	public void addCandidate(int candidate) {
		if (fixed) {
			return;
		}
		candidates.add(candidate);
		fixed = false;
	}
	
	public void removeCandidate(int candidate) throws CandidateNotFoundException, NoCandidateException {
		if (fixed) {
			return;
		}
		if (candidates.size() <= 0) {
			throw new NoCandidateException();
		}
		candidates.remove(candidate);
	}
	
	public Set<Integer> getCandidates() {
		return candidates;
	}
	
	public void fixCandidate(int candidate) {
		value = candidate;
		fixed = true;
	}
	
	public int getCandidateCount() {
		return candidates.size();
	}
	
	public int getValue() {
		return value;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void unfix() {
		fixed = false;
	}
}
