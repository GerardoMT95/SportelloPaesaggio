package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service;

import java.util.List;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainStringValueLabel;

/**
 * Service interface CRUD for table presentazione_istanza.ruolo_referente
 */
public interface IRuoloPraticaService {

	/**
	 * elenco ruoli pratica
	 * @author acolaianni
	 *
	 * @return
	 */
	List<PlainStringValueLabel> select();
	
	

}
