package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions;

import it.eng.tz.puglia.error.exception.CustomOperationException;

/**
 * eccezione che viene notificata al FE e quindi all'utente
 * @author acolaianni
 *
 */
public class CustomOperationToAdviceException extends CustomOperationException {

	private static final long serialVersionUID = -5705260928215744483L;

	public CustomOperationToAdviceException() {
		super();
	}

	public CustomOperationToAdviceException(String message, Throwable cause) {
		super(message, cause);
	}

	public CustomOperationToAdviceException(String message) {
		super(message);
	}

}
