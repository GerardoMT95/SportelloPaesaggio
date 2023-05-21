/**
 * 
 */
package it.eng.tz.puglia.autpae.jms.diogene.service;

import it.eng.tz.puglia.autpae.dto.AllegatoDiogeneInfoDTO;


/**
 * @author Adriano Colaianni
 * @date 19 ott 2021
 */
public interface IDiogeneClientService {
	
	/**
	 * compone il messaggio da inviare al microservizio diogene
	 * @autor Adriano Colaianni
	 * @date 19 ott 2021
	 * @param docInfoDiogene
	 * @throws Exception
	 */
	public void componiEdInviaMessaggio(AllegatoDiogeneInfoDTO docInfoDiogene) throws Exception;
	
	/**
	 * elaborazione notifica ricevuta su coda
	 * @autor Adriano Colaianni
	 * @date 19 ott 2021
	 * @param textMessageFromQueue
	 * @throws Exception
	 */
	public void elaboraNotifica(String textMessageFromQueue) throws Exception;

}
