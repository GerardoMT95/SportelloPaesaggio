package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.PraticaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.PraticaSearch;

/**
 * Service interface CRUD for table presentazione_istanza.pratica
 */
public interface IPraticaService extends ICrudService<PraticaDTO, PraticaSearch, PraticaRepository>{
	
	PraticaDTO findByCodice(String codiceApptr);

}
