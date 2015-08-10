package models;

public class ErrorsReporter extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TestsErrosLogger errorLogger = new TestsErrosLogger(ErrorsReporter.class);
	public ErrorsReporter() {
    }

    public ErrorsReporter(String message) {
        super(message);

    	errorLogger.logError(message);
    }

    public ErrorsReporter(Throwable cause) {
        super(cause);
    }

    public ErrorsReporter(String message, Throwable cause) {
    	super(message, cause);
    	
    	errorLogger.logError(message + " " + cause);
    }
}
