package core;

public interface SolverListener {

	public void onSuccess(long time);
	
	public void onFailure(String message);
	
}
