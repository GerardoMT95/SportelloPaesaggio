package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions;

public class InavlidDateCLException extends Exception
{
	private static final long serialVersionUID = 6225335723271209615L;

	
	public InavlidDateCLException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) 
	{
		super(message, cause, enableSuppression, writableStackTrace);	
	}

	public InavlidDateCLException(String message, Throwable cause) 
	{
		super(message, cause);
	}

	public InavlidDateCLException(String message) 
	{
		super(message);
	}
	
	public InavlidDateCLException(Throwable cause) 
	{
		super(cause);	
	}
}
