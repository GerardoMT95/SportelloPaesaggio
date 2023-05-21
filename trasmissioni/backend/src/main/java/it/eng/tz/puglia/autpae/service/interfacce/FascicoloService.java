package it.eng.tz.puglia.autpae.service.interfacce;

import java.util.Date;
import java.util.List;

import org.postgresql.util.PGobject;

import it.eng.tz.puglia.aet.paesaggio.bean.MyOrganizzazioniBean;
import it.eng.tz.puglia.autpae.dto.AcceleratoriFascicoloDTO;
import it.eng.tz.puglia.autpae.dto.FascicoloTabDTO;
import it.eng.tz.puglia.autpae.dto.InformazioniDTO;
import it.eng.tz.puglia.autpae.dto.LineaTemporaleDTO;
import it.eng.tz.puglia.autpae.entity.FascicoloBaseDto;
import it.eng.tz.puglia.autpae.entity.FascicoloDTO;
import it.eng.tz.puglia.autpae.entity.FascicoloPublicDto;
import it.eng.tz.puglia.autpae.enumeratori.EsitoVerifica;
import it.eng.tz.puglia.autpae.enumeratori.StatoFascicolo;
import it.eng.tz.puglia.autpae.enumeratori.TipoProcedimento;
import it.eng.tz.puglia.autpae.search.FascicoloSearch;
import it.eng.tz.puglia.bean.PaginatedList;

public interface FascicoloService {

	//BaseRepository
//	public List<FascicoloDTO> select() throws Exception;
	public long count(FascicoloSearch filter) throws Exception;
	public FascicoloDTO find(Long pk) throws Exception;
	public Long insert(FascicoloDTO entity) throws Exception;
	public int update(FascicoloDTO entity) throws Exception;
//	public int delete(FascicoloSearch filter) throws Exception;	 // TODO: da riattivare quando sarà implementata la cancellazione fisica
	public PaginatedList<FascicoloDTO> search(FascicoloSearch filter) throws Exception;
	//FullRepository
	public AcceleratoriFascicoloDTO getCountForAccelerator(FascicoloSearch search) throws Exception;
	public int cambiaStato(long idFascicolo, StatoFascicolo stato, Boolean verifica) throws Exception;
	public int setStatoInTrasmissione(long idFascicolo, boolean flag) throws Exception;
	public int aggiornaJsonInfo(long idFascicolo, PGobject jsonInfo) throws Exception;
	public int consentiModifica(long idFascicolo, Date modificabileFino) throws Exception;
	public int bloccaModifica(long idFascicolo) throws Exception;
	public int cancella(long idFascicolo, boolean deleted) throws Exception;
	public Date getModificabileFino(long id) throws Exception;
	public List<String> autocompleteCodice(String codice, Integer limit) throws Exception;
	/**
	 * 
	 * @autor Adriano Colaianni
	 * @date 20 mag 2022
	 * @param codice
	 * @param limit
	 * @param isAnnullabile null per non filtrare 
	 * @param isModificabile null per non filtrare
	 * @param isInModifica null per non filtrare
	 * @return
	 * @throws Exception
	 */
	public List<String> autocompleteCodiceAnnMod(String codice, Integer limit,Boolean isAnnullabile,Boolean isModificabile,Boolean isInModificabile) throws Exception;
	public List<String> autocompleteRup(String rup) throws Exception;
	public int aggiornaEsito(Long idFascicolo, EsitoVerifica esito, Date data, String numero) throws Exception;
	public LineaTemporaleDTO lineaTemporale(Long idFascicolo) throws Exception;
	
	
	
	public FascicoloDTO crea(FascicoloDTO fascicolo) throws Exception;
	
	public void salva(FascicoloDTO fascicolo) throws Exception;

	public boolean validazioneCreazione(FascicoloDTO fascicolo);

	public boolean validazione(FascicoloDTO fascicolo);

	public FascicoloTabDTO tabFascicolo() throws Exception;
	
	public FascicoloTabDTO datiFascicolo(long id) throws Exception;

	public InformazioniDTO informazioniComplete(TipoProcedimento tipoProcedimento) throws Exception;
	
	public InformazioniDTO datiCompleti(long idFascicolo) throws Exception;

	public PaginatedList<FascicoloDTO> searchFascicolo(FascicoloSearch filter) throws Exception;
	
	public PaginatedList<FascicoloPublicDto> searchPublicFascicolo(FascicoloSearch filter) throws Exception;
	
	public boolean cancella(long id) throws Exception;
	
	public int bloccaModificheScadute() throws Exception;
	
	public void resettaAllaTrasmissione(long idFascicolo) throws Exception;
	
	public String checkCodiceFiscale(String codiceFiscale);

	public String checkPartitaIVA(String partitaIVA);

	public String checkCAP(String cap);

	public String checkTelefono(String telefono);
	
	/**
	 * imposta attributi nel search a partire dall'utente loggato (se isRicercaPubblica==false)
	 * @param filter
	 * @throws Exception
	 */
	void prepareForSearch(FascicoloSearch filter) throws Exception;
	
	/**
	 * 
	 * @autor Adriano Colaianni
	 * @date 8 set 2021
	 * @param filter
	 * @param codiceGruppo codice gruppo come su profile manager es ED_48_F
	 * @throws Exception
	 */
	void prepareForSearch(FascicoloSearch filter,String codiceGruppo) throws Exception;
	
	
	/**solo per migrazione, si aspetta popolato:
	 * codice,tipoProcedimento,oggettoIntervento,dataCreazione,stato,inSanatoria,ufficio,note
	 * @autor Adriano Colaianni
	 * @date 23 apr 2021
	 * @param fascicolo
	 * @return
	 * @throws Exception
	 */
	FascicoloDTO creaDaMigrazione(FascicoloDTO fascicolo) throws Exception;
	
	/**
	 * solo per migrazione, permette di settare la data creazione
	 * @param idFascicolo
	 * @param dataCreazione
	 * @throws Exception
	 */
	void aggiornaDataCreazione(long idFascicolo, Date dataCreazione) throws Exception;
	
	/**usato per migrazione.
	 * @autor Adriano Colaianni
	 * @date 23 apr 2021
	 * @param tPraticaId
	 * @return
	 */
	Integer findByTPraticaId(Long tPraticaId);
	
	/**usato per migrazione
	 * @autor Adriano Colaianni
	 * @date 29 apr 2021
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	int updateForMigration(FascicoloDTO entity) throws Exception;
	
	
	/**
	 * @autor Adriano Colaianni
	 * @date 6 mag 2021
	 * @param fascicoloBase
	 * @return
	 * @throws Exception
	 */
	FascicoloDTO creaDaApi(FascicoloBaseDto fascicoloBase) throws Exception;
	
	/**
	 * check permessi di consultazione dettaglio fascicolo
	 * @autor Adriano Colaianni
	 * @date 13 mag 2021
	 * @param idFascicolo
	 * @throws Exception
	 */
	void checkPermission(Long idFascicolo) throws Exception;
	/**
	 * check permessi di consultazione dettaglio fascicolo se si trova nello stato passato in input
	 * @autor Adriano Colaianni
	 * @date 13 mag 2021
	 * @param idFascicolo
	 * @param stato
	 * @throws Exception
	 */
	void checkPermission(Long idFascicolo, StatoFascicolo stato) throws Exception;
	
	/**
	 * @autor Adriano Colaianni
	 * @date 21 giu 2022
	 * @return
	 * @throws Exception
	 */
	MyOrganizzazioniBean organizzazioniEComunidiCompetenza() throws Exception;
	
	
	
	
	
}