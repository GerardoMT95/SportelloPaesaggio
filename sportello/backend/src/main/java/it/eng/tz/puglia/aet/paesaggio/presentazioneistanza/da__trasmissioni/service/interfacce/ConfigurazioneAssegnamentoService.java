package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.service.interfacce;

import java.util.List;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.ConfigurazioneAssegnamentoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.ValoriAssegnamentoNewDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.search.ConfigurazioneAssegnamentoSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainStringUtentiValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainStringValueLabel;
import it.eng.tz.puglia.bean.PaginatedList;

public interface ConfigurazioneAssegnamentoService {

	// BaseRepository
//	public List<ConfigurazioneAssegnamentoDTO> select() throws Exception;
	public long count(ConfigurazioneAssegnamentoSearch filter) throws Exception;
//	public ConfigurazioneAssegnamentoDTO find(Long pk) throws Exception;
	public Integer insert(ConfigurazioneAssegnamentoDTO entity) throws Exception;
	public int update(ConfigurazioneAssegnamentoDTO entity) throws Exception;
//	public int delete(ConfigurazioneAssegnamentoSearch search) throws Exception;
	public PaginatedList<ConfigurazioneAssegnamentoDTO> search(ConfigurazioneAssegnamentoSearch filter) throws Exception;

	// FullRepository

	
	
	
	/**
	 * viene utilizzato l'utente batch per effettuare il retrieval di tutti gli utenti di un gruppo dal PM
	 * in caso di ED_ o REG_ è richiesta l'esistenza del/i RUP, altrimenti la lista è vuota, mentre per gli altri enti 
	 * per i quali NON è previsto il RUP NON è così (acolaianni 05.10.2020)
	 * @param jwt
	 * @return
	 * @throws Exception
	 */
	public List<PlainStringUtentiValueLabel> getUtentiForOrganizzazione() throws Exception;
//	public List<PlainStringValueLabel> getFunzionariForOrganizzazione() throws Exception;
//	public List<PlainStringValueLabel> getRupForOrganizzazione() throws Exception;
	public ConfigurazioneAssegnamentoDTO getConfigurazioneForOrganizzazioneUtenteLoggato() throws Exception;
	public ConfigurazioneAssegnamentoDTO getConfigurazioneForOrganizzazione(int idOrganizzazione) throws Exception;
	public boolean saveConfigurazioneForOrganizzazione(ConfigurazioneAssegnamentoDTO configurazione) throws Exception;
	public List<ValoriAssegnamentoNewDTO> configurazioneAutomaticaSearch(ConfigurazioneAssegnamentoDTO configurazione, Integer idComuneTipoProcedimento, String usernameFunzionario);
	public List<PlainStringValueLabel> getFunzionariForOrganizzazione() throws Exception;


}