package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.dto.UtenteAttributeDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.RubricaSearchDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.TipologicaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainNumberValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainStringValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.SelectOptionDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoOrganizzazioneSpecifica;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ComuniCompetenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DestinatarioDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.SezioneCatastaleSearchDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.AnagraficaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.EnteDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.PaesaggioEmailDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.PaesaggioOrganizzazioneDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.RubricaEnteDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.RubricaIstituzionaleDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.search.PaesaggioEmailSearch;
import it.eng.tz.puglia.bean.PaginatedList;


public interface RemoteSchemaService {
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////// REMOTE (Common-Ente)
	
	/**
	 * tutti i record della common.ente
	 * @return List<EnteDTO>
	 */
	List<EnteDTO> getAllEnti();
	
	/**
	 * Retrieves specific ente, identified by code, if it is accessible by the
	 * application
	 * 
	 * @param code
	 * @return EnteDTO
	 */
	EnteDTO findEnteByCode(String code);
	
	/**
	 * Retrieves all ente, identified by codes, if it is accessible by the
	 * application. If some ente is not accessible by the application then it is
	 * ignored.
	 * 
	 * @param codes
	 * @return List&lt;EnteDTO&gt;
	 */
	List<EnteDTO> findAllEnteByCodes(List<String> codes);
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////// ANAGRAFICA
	
	/**
	 * Retrieves specific anagrafica, identified by alphanumeric code, if it is
	 * accessible by the application. This is useful for getting numeric istatCode.
	 * 
	 * @param code
	 * @return AnagraficaDTO
	 */
	AnagraficaDTO findAnagraficaByCode(String code);
	
	/**
	 * {@inheritDoc}
	 * @throws Exception 
	 */
	PlainNumberValueLabel findByCatastale(String codCatastale) throws Exception;

	/**
	 * {@inheritDoc}
	 */
	String getCodiceIstatFromCatastale(String codiceCatastale);
	
	/**
	 * Retrieves all comuni of Puglia like 'pattern', if pattern is empty than retrieve all
	 * @param pattern
	 * @return
	 */
	List<AnagraficaDTO> findComuniLike(String pattern);
	
	/**
	 * Metodo che fa tornare una lista di nazioni con l'accoppiata nome nazione - sigla per riempire 
	 * un'autocomplete o una dropdown
	 * @param filter filtro di ricerca per nome nazione (la ricerca è in right like)
	 * @param limit numero massimo di record che si desidera ottenere
	 * @return lista di {@link PlainNumberValueLabel} a cui a label è associato il nome della nazione e a value id
	 * @throws Exception
	 */
	List<PlainNumberValueLabel> autocompleteNazioni(String filter, int limit) throws Exception;

	/**
	 * Metodo che fa tornare una lista di comuni con l'accoppiata nome della provincia - id per riempire 
	 * un'autocomplete o una dropdown
	 * @param filter filtro di ricerca per nome provincia (la ricerca è in right like)
	 * @param limit numero i record che si desidera ottenere
	 * @return lista di {@link PlainNumberValueLabel} a cui a label è associato il nome della provincia e a value il
	 * suo id
	 * @throws Exception
	 */
	List<PlainNumberValueLabel> autocompleteProvince(String filter, int limit) throws Exception;

	/**
	 * Metodo che fa tornare una lista di comuni con l'accoppiata nome comune - codice istat per riempire 
	 * un'autocomplete o una dropdown
	 * @param filter filtro di ricerca per nome comune (laricerca è in right like)
	 * @param idProvincia id della provincia a cui deve essere associato il comune
	 * @param limit numero i record che si desidera ottenere
	 * @return lista di {@link PlainNumberValueLabel} a cui a label è associato il nome del comune e a value il
	 * suo id
	 * @throws Exception
	 */
	List<PlainNumberValueLabel> autocompleteComuni(String filter, Integer idProvincia, Integer limit) throws Exception;

	List<PlainStringValueLabel> findBpParchi(Set<String> codIstat) throws Exception;

	List<PlainStringValueLabel> findUcpPaesaggi(Set<String> codIstat) throws Exception;

	List<PlainStringValueLabel> findBpImmobili(Set<String> codIstat) throws Exception;
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////// COMMON
	
	/**
	 * Retrieves all province of Puglia like 'pattern', if pattern is empty than retrieve all
	 * @param pattern
	 * @return
	 */
	List<EnteDTO> findEntiProvinceLike(String pattern);
	
	/**
	 * lista degli enti delegati (compresa regione Puglia) a cui è abilitata la presentazione istanza
	 * @return
	 */
	List<PlainNumberValueLabel> getEntiRiceventi();

	/**
	 * restituisce gli enti su cui ha competenza territoriale l'ente DELEGATO ricevente alla data di riferimento.
	 * @param enteRicevente
	 * @param riferimento
	 * @return
	 */
	List<PlainNumberValueLabel> getEntiCompetenza(int enteRicevente, Date riferimento);

	/**
	 * restituisce il DTO popolato in toto tranne che il praticaId
	 * @param enteRicevente
	 * @param riferimento
	 * @return
	 */
	List<ComuniCompetenzaDTO> getEntiCompetenzaConCodici(int enteRicevente, Date riferimento);

	String getCodiceCatastaleFromId(long id);
	
	
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public int getIdApplicazione(String codiceApplicazione) throws Exception;

//	public EnteDTO findRegione() throws Exception;
	public List<EnteDTO> selectEntiById(Collection<Integer> idEnti) throws Exception;
	public EnteDTO findEnteById(int idEnte) throws Exception;
	public EnteDTO findEnteByIdOrganizzazione(int idOrganizzazione) throws Exception;
	public EnteDTO findEnteByCodice(String codiceEnte) throws Exception;
	public String getTipoEnte(int idEnte) throws Exception;
	public String getCodiceEnte(int idEnte) throws Exception;
	public List<PlainStringValueLabel> selectInfoEntiByCodici(List<String> codiciEnti) throws Exception;
//	public List<Integer> getAllEnti() throws Exception;
	public List<Integer> getAllEntiCOMUNI() throws Exception;
	List<EnteDTO> getAllEntiComuniDetails() throws Exception;
	public List<String> getAllCodiciEntiCOMUNI() throws Exception;
	public List<String> selectCodiciEntiComuniInProvince(Collection<String> codiciProvince) throws Exception;
	public List<Integer> selectEntiComuniInProvincia(int idProvincia) throws Exception;
	List<EnteDTO> selectEntiComuniInProvinciaDetails(int idProvincia) throws Exception;
	public List<Integer> selectProvinceByCodiciComuni(List<String> codici) throws Exception;
	public List<Integer> selectProvinceByIdComuni(Collection<Long> idEntiComuni) throws Exception;
	public Integer findEnteSoprintendenzaByProvincia(int idProvincia) throws Exception;
	
	public String getTipoOrganizzazione(int idOrganizzazione) throws Exception;
	public String getCodiceCiviliaByIdOrganizzazione(int idOrganizzazione) throws Exception;
	public Integer getRiferimentoOrganizzazione(int idOrganizzazione) throws Exception;
	public void checkValiditaOrganizzazione(int idOrganizzazione) throws Exception;
	public void checkValiditaDelegaOrganizzazione(int idOrganizzazione) throws Exception;
	public void checkPermessoOrganizzazioneApplicazione(int idOrganizzazione, String codiceApplicazione) throws Exception;
	
//	public List<Integer> getEntiDiCompetenzaForOrganizzazione(int idOrganizzazione) throws Exception;
	public List<Integer> getEntiCOMUNIdiCompetenzaForOrganizzazione(int idOrganizzazione) throws Exception;
	List<PlainNumberValueLabel> getEntiCOMUNIdiCompetenzaForOrganizzazioneDetail(int idOrganizzazione) throws Exception;
	public List<Integer> getOrganizzazioniCompetentiOnEnti(Collection<Integer> idEnti, String codiceApplicazione) throws Exception;
	List<PaesaggioOrganizzazioneDTO> getOrganizzazioniCompetentiOnEntiDetails(Collection<Integer> idEnti, String codiceApplicazione) throws Exception;
	public List<Integer> getPaesaggiEntiForCommissione(Integer idCommissioneSeduta) throws Exception;
	
	public List<PlainStringValueLabel> getOrganizzazioniInfoValidePerApplicazione(String codiceApplicazione, List<String>  idsOrganizzazioni) throws Exception;
	public List<PlainStringValueLabel> getOrganizzazioniInfoValidePerApplicazioneConCL(String codiceApplicazione, List<String>  idsOrganizzazioni) throws Exception;

	public long insertRubricaEnte		  (RubricaEnteDTO 		   info) throws Exception;
	public int  updateRubricaEnte		  (RubricaEnteDTO 		   info) throws Exception;
	public int  deleteRubricaEnte		  (RubricaEnteDTO 		   info) throws Exception;
	public int  updateRubricaIstituzionale(RubricaIstituzionaleDTO info) throws Exception;
	
	List<PaesaggioEmailDTO> searchRubricaPaesaggio(PaesaggioEmailSearch search) throws Exception;
	long insertRubricaPaesaggio(PaesaggioEmailDTO entity) throws Exception;
	long updateRubricaPaesaggio(PaesaggioEmailDTO entity) throws Exception;
	
	String getDenominazioneOrganizzazione(int idOrganizzazione);
	
	/**
	 * puo' tornare null se non esiste il record nella common.ente_attribute
	 * @author acolaianni
	 *
	 * @param idEnte
	 * @param idApplicazione
	 * @return
	 * @throws Exception
	 */
	TipologicaDTO searchAutomatizzataRubricaIstituzionale(int idEnte, int idApplicazione) throws Exception;
	List<Integer> getOrganizzazioniCompetentiOnEnti(Collection<Integer> idEnti, String codiceApplicazione, Gruppi tipoOrganizzazione, TipoOrganizzazioneSpecifica tipoOrganizzazioneSpecifica) throws Exception;
	EnteDTO findRegione() throws Exception;
	Integer getEnteDiRiferimentoForOrganizzazione(int idOrganizzazione) throws Exception;
	Integer findProvinciaForComune(int idEnteComune) throws Exception;
	List<Integer> findSoprintendenzeByDenominazione(String nomeProv) throws Exception;
	
	PaesaggioOrganizzazioneDTO findPaesaggioById(Long id) throws Exception;
	List<PaesaggioOrganizzazioneDTO> getAllEntiDelegati() throws Exception;
	List<EnteDTO> getAllComuniPuglia() throws Exception;

	/**
	 * organizzazione attiva per l'applicazione con tipo_organizzazione REG (dovrebbe essere unica)
	 * @author acolaianni
	 *
	 * @return
	 */
	List<PlainNumberValueLabel> getEnteRegione();
	
	List<DestinatarioDTO> findEmailCommissione(Long idEnteDelegato, Date dataValidita) throws Exception;

	/**
	 * se non esiste il record, lo crea...
	 * @author acolaianni
	 *
	 * @param idOrganizzazioneEnteDelegato
	 * @return
	 * @throws Exception
	 */
	String generaCodiceFascicolo(Integer idOrganizzazioneEnteDelegato) throws Exception;

	List<SelectOptionDto> getSezioniCatastali();

	PaginatedList<SelectOptionDto> sezioneCatastaleSearch(SezioneCatastaleSearchDTO filter);

	int deleteSezioneCatastale(String codCatastale, String sezione);

	int saveOrUpdateSezioneCatastale(SelectOptionDto item);

	void aggiornaUtenteCommon(Long idRichiestaAbilitazione, String codiceApplicazione, String username, String email,
			String telefono, String fax) throws Exception;

	Long getLastIdRichiestaAbilitazione(String codiceApplicazione, String username) throws Exception;

	UtenteAttributeDTO getLastUtenteAttribute(String codiceApplicazione, String username) throws Exception;

	Long getIdSoprintendenzaByComuneId(Long comuneId, Date dataRiferimento);

	/**
	 * restituisce tutti gli enti riceventi anche quelli scaduti
	 * @author acolaianni
	 *
	 * @return
	 */
	List<PlainNumberValueLabel> getAllEntiRiceventi();	
	
	List<RubricaSearchDTO> getIndirizziMailDefaultEnte(Integer id) throws Exception;

}