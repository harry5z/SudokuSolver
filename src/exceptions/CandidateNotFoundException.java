package exceptions;

public class CandidateNotFoundException extends Exception {

	private static final long serialVersionUID = 6023288938222546181L;

	public CandidateNotFoundException(String msg) {
		super(msg);
	}
}
