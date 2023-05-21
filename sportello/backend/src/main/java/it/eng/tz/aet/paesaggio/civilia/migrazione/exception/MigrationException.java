package it.eng.tz.aet.paesaggio.civilia.migrazione.exception;

public class MigrationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MigrationException(String message) {
		super(message);
	}
	
	public MigrationException(String message,Throwable e) {
		super(message,e);
	}

	
	

}
