package it.eng.tz.puglia.autpae.controller.exception;

import it.eng.tz.puglia.error.exception.CustomOperationException;

public class CustomOperationToAdviceWarningException extends CustomOperationException 
{
	private static final long serialVersionUID = 6250935075142721337L;

	public CustomOperationToAdviceWarningException() { super(); }
	public CustomOperationToAdviceWarningException(String message) { super(message); }
	public CustomOperationToAdviceWarningException(String message, Throwable cause) { super(message, cause); }
}
