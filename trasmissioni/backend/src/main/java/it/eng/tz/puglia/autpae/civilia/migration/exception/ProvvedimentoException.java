/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.exception;

import it.eng.tz.puglia.autpae.civilia.migration.dto.InfoAutPaesPptrAlfa;

/**
 * @author Adriano Colaianni
 * @date 23 apr 2021
 */
public class ProvvedimentoException extends BaseMigrationException {

	
	/**
	 * @param infoPratica
	 */
	public ProvvedimentoException(InfoAutPaesPptrAlfa infoPratica,String message,Throwable cause) {
		super(infoPratica, message, cause);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
