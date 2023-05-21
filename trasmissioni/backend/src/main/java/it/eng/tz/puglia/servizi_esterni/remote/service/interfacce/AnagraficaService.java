package it.eng.tz.puglia.servizi_esterni.remote.service.interfacce;

import java.util.List;
import java.util.Set;

import it.eng.tz.puglia.autpae.dto.TipologicaDTO;
import it.eng.tz.puglia.servizi_esterni.remote.dto.AnagraficaDTO;
import it.eng.tz.puglia.servizi_esterni.remote.dto.AutoCompleteDTO;

public interface AnagraficaService {
	
	//FullRepository
	public List<TipologicaDTO> getListaBPparchiRiserve(Set<String> listaCodici) throws Exception;
	public List<TipologicaDTO> getListaUcpPaesaggioRurale(Set<String> listaCodici) throws Exception;
	public List<TipologicaDTO> getListaBPimmobiliAree(Set<String> listaCodici) throws Exception;
	public List<TipologicaDTO> selectAllBPparchiRiserve() throws Exception;
	public List<TipologicaDTO> selectAllUcpPaesaggioRurale() throws Exception;
	public List<TipologicaDTO> selectAllBPimmobiliAree() throws Exception;
	public long countParchi(String codiceCatastale) throws Exception;
	public long countPaesaggiRurali(String codiceCatastale) throws Exception;
	public long countImmobiliAreeInteressePubblico(String codiceCatastale) throws Exception;
	public List<TipologicaDTO> emailParchi(List<String> codici) throws Exception;
	public List<String> getAllCodiciIstat() throws Exception;
	public List<String> getAllCodiciCatastali() throws Exception;
	public List<String> getAllCodiciCatastaliInProvincia(String siglaProvincia) throws Exception;
	public List<String> getAllCodiciIstatInProvincia(String siglaProvincia) throws Exception;
	public String getCodIstatFromCatastale(String codiceCatastale) throws Exception;
	public String getCodCatastaleFromIstat(String codiceIstat) throws Exception;
	
	
	
	/**
	 * Retrieves specific anagrafica, identified by alphanumeric code, if it is
	 * accessible by the application. This is useful for getting numeric istatCode.
	 * 
	 * @param code
	 * @return AnagraficaDAO
	 */
	AnagraficaDTO findAnagraficaByCode(String code);
	
	/**
	 * Metodo che fa tornare una lista di nazioni con l'accoppiata nome nazione - sigla per riempire 
	 * un'autocomplete o una dropdown
	 * @param filter filtro di ricerca per nome nazione (la ricerca è in right like)
	 * @param limit numero massimo di record che si desidera ottenere
	 * @return lista di {@link AutoCompleteDTO} a cui a label è associato il nome della nazione e a value la sigla
	 * @throws Exception
	 */
	List<AutoCompleteDTO> autocompleteNazioni(String filter, int limit) throws Exception;
	
	/**
	 * Metodo che fa tornare una lista di comuni con l'accoppiata nome della provincia - id per riempire 
	 * un'autocomplete o una dropdown
	 * @param filter filtro di ricerca per nome provincia (la ricerca è in right like)
	 * @param limit numero i record che si desidera ottenere
	 * @return lista di {@link AutoCompleteDTO} a cui a label è associato il nome della provincia e a value il
	 * suo id
	 * @throws Exception
	 */
	List<AutoCompleteDTO> autocompleteProvince(String filter, int limit) throws Exception;
	
	/**
	 * Metodo che fa tornare una lista di comuni con l'accoppiata nome comune - codice istat per riempire 
	 * un'autocomplete o una dropdown
	 * @param filter filtro di ricerca per nome comune (laricerca è in right like)
	 * @param denominazioneProvincia
	 * @param idProvincia id della provincia a cui deve essere associato il comune (nullable)
	 * @param limit numero i record che si desidera ottenere 
	 * @param idRegione id della regione a cui deve essere associato il comune (nullable)
	 * @return lista di {@link AutoCompleteDTO} a cui a label è associato il nome del comune e a value il
	 * suo condice istat
	 * @throws Exception
	 */
	List<AutoCompleteDTO> autocompleteComuni(String filter, String denominazioneProvincia, Integer idProvincia, Integer limit, Integer idRegione) throws Exception;
	
}