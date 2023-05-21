/**
 * 
 */
package it.eng.tz.puglia.autpae.service.interfacce;

import java.util.List;
import java.util.Map;

import it.eng.tz.puglia.aet.paesaggio.bean.MyOrganizzazioniBean;

/**
 * @author Adriano Colaianni
 * @date 21 giu 2022
 */
public interface MyCompetenzaService {
	
	
	final public String PAE_PRES_IST="pae_pres_ist";

	/**
	 * tutte le mie organizzazioni a cui faccio capo sia sportello che autpae,
	 * serve per aprire avere visibilità in area pubblica anche alle pratiche NON autorizzate
	 * @autor Adriano Colaianni
	 * @date 21 giu 2022
	 * @return mappa con pae_pres_ist={MyOrganizzazioniBean} e autpae={MyOrganizzazioniBean}
	 */
	public Map<String,MyOrganizzazioniBean> getMyOrganizzazioniPaesaggio();

	/**
	 * check se uno dei miei gruppi ha accesso alla pratica
	 * @autor Adriano Colaianni
	 * @date 22 giu 2022
	 * @param idFascicolo
	 * @param myGruppi
	 * @param username
	 * @return null se nessuno ha accesso
	 * @throws Exception
	 */
	String groupCanAccessPratica(String codiceTrasmissione, List<String> myGruppi, String username) throws Exception;
	

}
