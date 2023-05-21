/**
 * 
 */
package it.eng.tz.puglia.autpae.service.interfacce;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import it.eng.tz.puglia.aet.paesaggio.bean.MyOrganizzazioniBean;
import it.eng.tz.puglia.http.exception.HttpException;

/**
 * @author Adriano Colaianni
 * @date 22 giu 2022
 */
public interface ClientSportelloService {

	
	/**
	 * @autor Adriano Colaianni
	 * @date 22 giu 2022
	 * @param token
	 * @return
	 * @throws URISyntaxException
	 * @throws HttpException
	 */
	MyOrganizzazioniBean buildOrganizzazioniSportelloFromTokenCached(String token)
			throws URISyntaxException, HttpException;

	/**
	 * mappa codicePratica=codiceGruppo per accesso a pratiche relative allo sportello
	 * @autor Adriano Colaianni
	 * @date 22 giu 2022
	 * @param token
	 * @param codiciTrasmissione
	 * @return
	 * @throws Exception
	 */
	Map<String, String> praticheGruppo(String token, List<String> codiciTrasmissione) throws Exception;

}
