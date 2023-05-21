package it.eng.tz.puglia.autpae.service.interfacce;

import it.eng.tz.puglia.autpae.dto.LocalizzazioneTabDTO;
import it.eng.tz.puglia.autpae.entity.ComuniCompetenzaDTO;
import it.eng.tz.puglia.autpae.search.ComuniCompetenzaSearch;
import it.eng.tz.puglia.bean.PaginatedList;

public interface ComuniCompetenzaService {
	
	//BaseRepository
	public PaginatedList<ComuniCompetenzaDTO> search (ComuniCompetenzaSearch filter) throws Exception;

	
	//Altri metodi
	public void aggiorna (long idFascicolo, LocalizzazioneTabDTO localizzazione) throws Exception;
	public void	crea 	 (long idFascicolo) throws Exception;
}