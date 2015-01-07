package exceptions;

public class InvalidInputException extends RuntimeException {

	private static final long serialVersionUID = -4346322581772139027L;

	public InvalidInputException(String msg) {
		super(msg);
	}
}
