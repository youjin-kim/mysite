package kr.co.itcen.mysite.exception;

public class GuestbookDaoException extends RuntimeException {

	public GuestbookDaoException() {
		super("GuestbookDaoException Occurs");
	}
	
	public GuestbookDaoException(String message) {
		super(message);
	}
}
