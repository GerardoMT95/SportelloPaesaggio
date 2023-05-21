package it.eng.tz.puglia.autpae.service.interfacce;

import java.util.List;

import it.eng.tz.puglia.autpae.entity.TipoProcedimentoDTO;
import it.eng.tz.puglia.autpae.enumeratori.Applicativo;
import it.eng.tz.puglia.autpae.search.TipoProcedimentoSearch;
import it.eng.tz.puglia.bean.PaginatedList;

public interface TipoProcedimentoService {
	
	// BaseRepository
	public List<TipoProcedimentoDTO> select() throws Exception;
	
	/**
	 * Metodo che restituisce la lista dei tipi procedimento (validi in base a data inizio e fine rispetto ad oggi) di un dato applicativo
	 * @author Giuseppe Canciello
	 * @date 11 mag 2021
	 * @param app
	 * @return
	 * @throws Exception
	 */
	public List<TipoProcedimentoDTO> selectByApplication(Applicativo app) throws Exception;
	
	/**
	 * Metodo che restituisce la lista di tipi procedimento (anche quelli scaduti) dell'applicativo in esecuzione
	 * @author Giuseppe Canciello
	 * @date 11 mag 2021
	 * @return
	 * @throws Exception
	 */
	public List<TipoProcedimentoDTO> selectAllByApplication() throws Exception;
	
	
	/**
	 * Metodo che restituisce la lista di tipi procedimento (validi in base a data inizio e fine rispetto ad oggi) dell'applicativo in esecuzione
	 * @author Giuseppe Canciello
	 * @date 11 mag 2021
	 * @return
	 * @throws Exception
	 */
	public List<TipoProcedimentoDTO> selectByApplication() throws Exception;
	
	/**
	 * Metodo che restituisce la lista di tipi procedimento associati al fascicolo il cui
	 * id è ricevuto come parametro. La lista conterrà tutti i tipi procedimento che erano attivi
	 * al momento della creazione del fascicolo. Questo per permettere di utilizzarli in fase di editig
	 * di questo fascicolo anche se alcuni sono scaduti
	 * @author Giuseppe Canciello
	 * @date 12 mag 2021
	 * @param idFascicolo
	 * @return
	 * @throws Exception
	 */
	public List<TipoProcedimentoDTO> findByFascicolo(Long idFascicolo) throws Exception;
	
	/**
	 * Metodo che permette di salvare un nuovo periodo (data inizio e data fine) di validità per un dato tipo procedimento
	 * @author Giuseppe Canciello
	 * @date 12 mag 2021
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public int updateValidita(TipoProcedimentoDTO updateDto) throws Exception;
	
	public long count(TipoProcedimentoSearch filter) throws Exception;
	public TipoProcedimentoDTO find(String pk) throws Exception;
//	public String insert(TipoProcedimentoDTO entity) throws Exception;
//	public int update(TipoProcedimentoDTO entity) throws Exception;
//	public int delete(TipoProcedimentoSearch search) throws Exception;
	public PaginatedList<TipoProcedimentoDTO> search(TipoProcedimentoSearch filter) throws Exception;

}