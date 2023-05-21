package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service;

import java.io.File;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.FascicoloDto;



/**
 * classe per digitalizzazione domanda e scheda tecnica con Jasper
 * @author acolaianni
 *
 */
public interface DigitalizzazioneIstanzaService {
	
	
	File generateDomandaIstanza(FascicoloDto fascicolo) throws Exception;
	
	File generateDomandaSchedaTecnica(FascicoloDto fascicolo) throws Exception;
	/**
	 * utilizzati per test per generare bean da dare in input a jasper
	 * @author acolaianni
	 *
	 * @param codice codice pratica appptr
	 * @return
	 * @throws Exception
	 */
	String generateJsonDomandaSchedaTecnica(String codice) throws Exception;
	
	/**
	 * utilizzato per test per generazione json bean x input jasper 
	 * @author acolaianni
	 *
	 * @param codice pratica appptr
	 * @return
	 * @throws Exception
	 */
	String generateJsonIstanza(String codice) throws Exception;

}
