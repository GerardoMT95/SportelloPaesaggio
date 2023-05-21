package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service;


import java.util.List;
import java.util.Set;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.TipologicaIntegerBooleanDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ConfigurazioniEnteDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.ConfigurazioniEnteRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.ConfigurazioniEnteSearch;

/**
 * Service interface CRUD for table presentazione_istanza.configurazioni_ente
 */
public interface IConfigurazioniEnteService extends ICrudService<ConfigurazioniEnteDTO, ConfigurazioniEnteSearch, ConfigurazioniEnteRepository>{

	ConfigurazioniEnteDTO inserisciOaggiorna(ConfigurazioniEnteDTO dto) throws Exception;
	
	/**
	 * ricava l'idOrganizzazione dall'ente o dall'MDC
	 * @author acolaianni
	 *
	 * @return
	 * @throws Exception
	 */
	
	ConfigurazioniEnteDTO find(Integer idOrganizzazione) throws Exception;
	
	Boolean showVerbaleCommissionelocale(Long enteDelegato, Integer tipoProcedimento) throws Exception;

	List<TipologicaIntegerBooleanDto> visibilitaStatoAvanzamento(Set<Integer> idOrganizzazioni) throws Exception;
	
	List<Integer> tipologieParereCommissioneLocale(long idOrganizzazione) throws Exception;
}