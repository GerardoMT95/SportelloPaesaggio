/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.exception;

import it.eng.tz.puglia.autpae.civilia.migration.dto.InfoAutPaesPptrAlfa;

/**
 * @author Adriano Colaianni
 * @date 26 apr 2021
 */
public class QualificazioneInterventoException extends BaseMigrationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param infoPratica
	 * @param message
	 * @param cause
	 */
	public QualificazioneInterventoException(InfoAutPaesPptrAlfa infoPratica, String message, Throwable cause) {
		super(infoPratica, message, cause);
		// TODO Auto-generated constructor stub
	}

}
