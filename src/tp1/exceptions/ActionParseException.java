package tp1.exceptions;

public class ActionParseException extends GameParseException {  // extends GameModelException

	private static final long serialVersionUID = 1L;
	
	
	public ActionParseException() { super(); }
    public ActionParseException(String message) { super(message); }
    public ActionParseException(Throwable cause) { super(cause); }
    public ActionParseException(String message, Throwable cause) { super(message, cause); }
    public ActionParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
