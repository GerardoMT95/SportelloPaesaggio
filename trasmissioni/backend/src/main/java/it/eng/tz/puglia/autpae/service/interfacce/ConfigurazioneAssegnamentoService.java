package it.eng.tz.puglia.autpae.service.interfacce;

import java.util.List;

import it.eng.tz.puglia.autpae.dto.TipologicaDTO;
import it.eng.tz.puglia.autpae.entity.ConfigurazioneAssegnamentoDTO;
import it.eng.tz.puglia.autpae.entity.ValoriAssegnamentoDTO;
import it.eng.tz.puglia.autpae.search.ConfigurazioneAssegnamentoSearch;
import it.eng.tz.puglia.bean.PaginatedList;

public interface ConfigurazioneAssegnamentoService {

	// BaseRepository
//	public List<ConfigurazioneAssegnamentoDTO> select() throws Exception;
	public long count(ConfigurazioneAssegnamentoSearch filter) throws Exception;
//	public ConfigurazioneAssegnamentoDTO find(Long pk) throws Exception;
	public Integer insert(ConfigurazioneAssegnamentoDTO entity) throws Exception;
	public int update(ConfigurazioneAssegnamentoDTO entity) throws Exception;
	public int delete(ConfigurazioneAssegnamentoSearch search) throws Exception;
	public PaginatedList<ConfigurazioneAssegnamentoDTO> search(ConfigurazioneAssegnamentoSearch filter) throws Exception;

	// FullRepository

	
	
	
	/**
	 * viene utilizzato l'utente batch per effettuare il retrieval di tutti gli utenti di un gruppo dal PM
	 * @param jwt
	 * @return
	 * @throws Exception
	 */
	public List<TipologicaDTO> getUtentiForOrganizzazione(String jwt) throws Exception;
	public ConfigurazioneAssegnamentoDTO getConfigurazioneForOrganizzazioneUtenteLoggato() throws Exception;
	public ConfigurazioneAssegnamentoDTO getConfigurazioneForOrganizzazione(int idOrganizzazione) throws Exception;
	public boolean saveConfigurazioneForOrganizzazione(ConfigurazioneAssegnamentoDTO configurazione) throws Exception;
	public List<ValoriAssegnamentoDTO> configurazioneAutomaticaSearch(ConfigurazioneAssegnamentoDTO configurazione, String idComuneTipoProcedimento, String usernameFunzionario);

}