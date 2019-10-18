package kr.co.itcen.mysite.exception;

public class BoardException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public BoardException() {
		super("BoardException");
	}
	
	public BoardException(String message) {
		super(message);
	}
}
