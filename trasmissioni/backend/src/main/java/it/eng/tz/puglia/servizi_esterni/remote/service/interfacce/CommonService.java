package it.eng.tz.puglia.servizi_esterni.remote.service.interfacce;

import java.util.Collection;
import java.util.List;

import it.eng.tz.puglia.autpae.dto.SelectOptionDto;
import it.eng.tz.puglia.autpae.dto.TipologicaDTO;
import it.eng.tz.puglia.autpae.dto.BE_to_FE.EnteBean;
import it.eng.tz.puglia.autpae.enumeratori.Gruppi;
import it.eng.tz.puglia.autpae.enumeratori.TipoOrganizzazioneSpecifica;
import it.eng.tz.puglia.autpae.enumeratori.TipoPaesaggioOrganizzazione;
import it.eng.tz.puglia.autpae.generated.orm.dto.UtenteAttributeDTO;
import it.eng.tz.puglia.servizi_esterni.remote.dto.EnteDTO;
import it.eng.tz.puglia.servizi_esterni.remote.dto.EnteWIstatDTO;
import it.eng.tz.puglia.servizi_esterni.remote.dto.PaesaggioEmailDTO;
import it.eng.tz.puglia.servizi_esterni.remote.dto.RubricaEnteDTO;
import it.eng.tz.puglia.servizi_esterni.remote.dto.RubricaIstituzionaleDTO;
import it.eng.tz.puglia.servizi_esterni.remote.search.PaesaggioEmailSearch;
import it.eng.tz.puglia.servizi_esterni.remote.utility.TipoEnte;


public interface CommonService {

public int getIdApplicazione(String codiceApplicazione) throws Exception;

//	public EnteDTO findRegione() throws Exception;
	public List<EnteDTO> selectEntiById(Collection<Integer> idEnti) throws Exception;
	public EnteDTO findEnteById(int idEnte) throws Exception;
	public EnteWIstatDTO findEnteWIstatById(int idEnte) throws Exception;
//	public EnteDTO findEnteByIdOrganizzazione(int idOrganizzazione) throws Exception;
	public EnteDTO findEnteByCodice(String codiceEnte) throws Exception;
	public String getTipoEnte(int idEnte) throws Exception;
	public String getCodiceEnte(int idEnte) throws Exception;
	public List<TipologicaDTO> selectInfoEntiByCodici(List<String> codiciEnti) throws Exception;
//	public List<Integer> getAllEnti() throws Exception;
	public List<Integer> getAllEntiCOMUNI() throws Exception;
	public List<String> getAllCodiciEntiCOMUNI() throws Exception;
	public List<String> selectCodiciEntiComuniInProvince(Collection<String> codiciProvince) throws Exception;
	public List<Integer> selectEntiComuniInProvincia(int idProvincia) throws Exception;
	public List<Integer> selectProvinceByCodiciComuni(List<String> codici) throws Exception;
	public List<Integer> selectProvinceByIdComuni(Collection<Long> idEntiComuni) throws Exception;
	public Integer findProvinciaForComune(int idEnteComune) throws Exception;
//	public Integer findEnteSoprintendenzaByProvincia(int idProvincia) throws Exception;
	
	
	
	
	public String getTipoOrganizzazione(int idOrganizzazione);
	public String getDenominazioneOrganizzazione(int idOrganizzazione);
	public String getCodiceCiviliaByIdOrganizzazione(int idOrganizzazione) throws Exception;
//	public Integer getRiferimentoOrganizzazione(int idOrganizzazione) throws Exception;
	public Integer getEnteDiRiferimentoForOrganizzazione(int idOrganizzazione) throws Exception;
	public void checkValiditaOrganizzazione(int idOrganizzazione) throws Exception;
	public void checkValiditaDelegaOrganizzazione(int idOrganizzazione) throws Exception;
	public void checkPermessoOrganizzazioneApplicazione(int idOrganizzazione, String codiceApplicazione) throws Exception;
	
//	public List<Integer> getEntiDiCompetenzaForOrganizzazione(int idOrganizzazione) throws Exception;
	public List<Integer> getEntiCOMUNIdiCompetenzaForOrganizzazione(int idOrganizzazione) throws Exception;
	public List<Integer> getOrganizzazioniCompetentiOnEnti(Collection<Integer> idEnti, String codiceApplicazione, Gruppi tipoOrganizzazione, TipoOrganizzazioneSpecifica tipoOrganizzazioneSpecifica) throws Exception;
	
	public List<TipologicaDTO> getOrganizzazioniInfoValidePerApplicazione(String codiceApplicazione, List<String>  idsOrganizzazioni) throws Exception;
	public List<Integer> 	   getOrganizzazioniValidePerApplicazione	 (String codiceApplicazione, List<Integer> idsOrganizzazioni) throws Exception;
	public List<Integer> 	   getOrganizzazioniValidePerApplicazione	 (String codiceApplicazione) 								  throws Exception;
	
//	RUBRICA Privata dell'Organizzazione (non territoriale) 
	public long insertRubricaEnte(RubricaEnteDTO info) throws Exception;
	public int  updateRubricaEnte(RubricaEnteDTO info) throws Exception;
	public int  deleteRubricaEnte(RubricaEnteDTO info) throws Exception;

//	RUBRICA Pubblica dell'Ente (non territoriale)
	public int  updateRubricaIstituzionale(RubricaIstituzionaleDTO info) throws Exception;
	public TipologicaDTO searchAutomatizzataRubricaIstituzionale(int idEnte, int idApplicazione) throws Exception;

//	RUBRICA Pubblica dell'Organizzazione (non territoriale) + RUBRICA Pubblica dell'Organizzazione (territoriale) + estensione della RUBRICA Pubblica dell'Ente (non territoriale)
//	public int getPaesaggioOrganizzazioneCompetenzeId(int idEnte, int idOrganizzazione) throws Exception;
//	public long insertRubricaPaesaggio(PaesaggioEmailDTO entity) throws Exception;
//	public long deleteRubricaPaesaggio(PaesaggioEmailSearch filter) throws Exception;
	public long countRubricaPaesaggio(PaesaggioEmailSearch filter) throws Exception;
	public List<PaesaggioEmailDTO> searchRubricaPaesaggio(PaesaggioEmailSearch search) throws Exception;
	
	public String getConfigurationValue(String key, String applicationCode) throws Exception;
	
	public String generaCodiceFascicolo() throws Exception;

	public List<Integer> findSoprintendenzeByDenominazione(String nomeProv) throws Exception;

	public EnteDTO findRegione() throws Exception;

	List<SelectOptionDto> getSezioniCatastali();

	/**
	 * @autor Adriano Colaianni
	 * @date 15 apr 2021
	 * @param idRichiestaAbilitazione
	 * @param codiceApplicazione
	 * @param username
	 * @param fax 
	 * @param telefono 
	 * @param email 
	 * @throws Exception
	 */
	void aggiornaUtenteCommon(Long idRichiestaAbilitazione, String codiceApplicazione, String username, String email, String telefono, String fax)
			throws Exception;

	/**
	 * @autor Adriano Colaianni
	 * @date 15 apr 2021
	 * @param codiceApplicazione
	 * @param username
	 * @return
	 * @throws Exception
	 * Viene fetch-ato da common.utente_attribute
	 */
	Long getLastIdRichiestaAbilitazione(String codiceApplicazione, String username) throws Exception;

	/**
	 * @autor Adriano Colaianni
	 * @date 15 apr 2021
	 * @param codiceApplicazione
	 * @param username
	 * @return
	 * @throws Exception
	 */
	UtenteAttributeDTO getLastUtenteAttribute(String codiceApplicazione, String username) throws Exception;

	/**
	 * solo per migrazione.... 
	 * @autor Adriano Colaianni
	 * @date 23 apr 2021
	 * @param istatAmm
	 * @param tipo
	 * @return
	 */
	Integer findEnteDelegatoFromCodiceCivilia(String istatAmm, Gruppi tipo);
	
	List<EnteDTO> getAllEnti() throws Exception;

	/** usato nella migrazione per avanzare i contatori
	 * @autor Adriano Colaianni
	 * @date 28 apr 2021
	 * @param codiceEnteDelegato
	 * @param codiceIstatEnte nullo o avvalorato con il nostro codice in caso di Regione....
	 * @return
	 * @throws Exception
	 */
	void updateProgressiviCodiceFascicolo(String codiceEnteDelegato,String codiceIstatEnte) throws Exception;
	
	/**
	 * Metodo che restituisce una lista di enti che possono essere associati ad un'organizzazione specifica e 
	 * definiscono il territorio di competenza
	 * 
	 * @param tipoOrganizzazione - filtro di ricerca da utilizzare: ammessi soo valori ED, ETI e SBAP
	 * @return Lista di {@link EnteBean} associabili ad un'organizzazione di tipo specifico
	 * @throws Exception
	 */
	List<EnteBean> findAllEntiBeTipoOrganizzazione(TipoPaesaggioOrganizzazione tipoOrganizzazione) throws Exception;
	
	/**
	 * Metodo che restituisce una lista di enti di tipo specificato
	 * 
	 * @param tipoEnte - filtro di ricerca da utilizzare: ammessi soo valori 
	 * @return Lista di {@link EnteBean} associabili ad un'organizzazione di tipo specifico
	 * @throws Exception
	 */
	List<EnteBean> findAllEntiBeTipo(List<TipoEnte> tipi) throws Exception;

	/**
	 * @autor Adriano Colaianni
	 * @date 25 giu 2021
	 * @param idEnti
	 * @return
	 * @throws Exception
	 */
	List<EnteWIstatDTO> selectEnteWIstatById(List<Integer> idEnti) throws Exception;

	
	/**
	 * restituisce true se corrisponde all'id di una organizzazione che si riferisce all'ente Regione Puglia (common.ente.tipo=Regione)
	 * @autor Adriano Colaianni
	 * @date 23 ago 2021
	 * @param idOrganizzazione
	 * @return
	 */
	boolean isRegione(Integer idOrganizzazione);

	/**
	 * organizzazioni valide in paesaggioOrganizzazione filtrate per ente_id
	 * @autor Adriano Colaianni
	 * @date 10 set 2021
	 * @param codiceApplicazione
	 * @param idsEnteCommon
	 * @return
	 * @throws Exception
	 */
	List<Integer> getOrganizzazioniValidePerApplicazioneByIdsEnte(String codiceApplicazione,
			List<Integer> idsEnteCommon) throws Exception;
}