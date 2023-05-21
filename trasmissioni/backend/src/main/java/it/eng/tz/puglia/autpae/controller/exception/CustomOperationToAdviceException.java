package it.eng.tz.puglia.autpae.controller.exception;

import it.eng.tz.puglia.error.exception.CustomOperationException;

/**
 * eccezione che viene notificata al FE e quindi all'utente
 * @author acolaianni
 *
 */
public class CustomOperationToAdviceException extends CustomOperationException {

	private static final long serialVersionUID = -5705260928215744483L;
	private String title;
	private String instance;
	private String type;
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

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
