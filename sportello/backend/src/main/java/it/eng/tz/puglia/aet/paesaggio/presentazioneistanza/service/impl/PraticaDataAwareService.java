package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.IstrPraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.AttivitaDaEspletare;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.UserUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ComuniCompetenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PrivacyDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ReferenteFascicoloDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.PraticaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.ReferenteFascicoloRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.PraticaSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.impl.PraticaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.IPraticaDataAwareService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria.IstrPraticaService;
import it.eng.tz.puglia.security.util.SecurityUtil;
import it.eng.tz.puglia.util.log.LogUtil;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * CHECK SPORTELLO
 * in questa classe sono implementati i metodi per controllare l'accesso 
 * ai dati pratica dell'utente di sessione e compatibilmente allo stato della pratica
 * @author acolaianni
 *
 */
public class PraticaDataAwareService implements IPraticaDataAwareService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PraticaDataAwareService.class);
	@Autowired UserUtil userUtil;
	
	@Autowired PraticaRepository praticaDao;
	@Autowired ReferenteFascicoloRepository referenteDAO;
	
	@Autowired
	@Qualifier("istrPraticaService")
	IstrPraticaService praticaService;
	
	
	public void checkDiMiaCompetenza(PraticaDTO pratica) throws CustomOperationToAdviceException {
		Gruppi gruppo = userUtil.getGruppo();
		if(!Arrays.asList(Gruppi.values()).contains(gruppo)) {
			throw new CustomOperationToAdviceException("Gruppo non ablitato all'accesso!!!");
		}
		if (gruppo==Gruppi.RICHIEDENTI && !pratica.diMiaCompetenza()) {
			LOGGER.error("Pratica non trovata o di proprietà di altro utente...");
			throw new CustomOperationToAdviceException("Pratica non trovata..");
		}
		//caso utente istruttoria
		if(!(gruppo==Gruppi.RICHIEDENTI)) {
			this.userIstruttoriaCanAccessPratica(pratica.getId(),null);
		}
	}
	
	//--------------------------------------------------------------------
	/**
	 * Metodo che controlla se l'utente loggato può visualizzare la pratica in lettura.
	 * Le condizioni sono che faccia parte di un gruppo abilitato all'accesso, che il suo username sia
	 * lo userId della pratica o che il suo codice fiscale corrisponda ad almeno un referente della pratica
	 * @author Giuseppe Canciello
	 * @date 30 giu 2022
	 * @param pratica
	 * @throws CustomOperationToAdviceException
	 */
	public void checkDiMiaCompetenzaInLettura(PraticaDTO pratica) throws CustomOperationToAdviceException {
		//Qui bisogna verificare anche che il CF si uno di quelli degli altri titolari
		Gruppi gruppo = userUtil.getGruppo();
		String codiceFiscale=SecurityUtil.getUserDetail().getFiscalCode();
		if(!Arrays.asList(Gruppi.values()).contains(gruppo)) {
			throw new CustomOperationToAdviceException("Gruppo non ablitato all'accesso!!!");
		}
		if (gruppo==Gruppi.RICHIEDENTI && (!pratica.diMiaCompetenza() && !this.isCodiceFiscaleAltriTitolari(pratica, codiceFiscale))){
			LOGGER.error("Pratica non trovata o di proprietà di altro utente (e codice fiscale utente loggato non presente tra gli altri titolari)...");
			throw new CustomOperationToAdviceException("Pratica non trovata..");
		}
		//caso utente istruttoria
		if(!(gruppo==Gruppi.RICHIEDENTI)) {
			this.userIstruttoriaCanAccessPratica(pratica.getId(),null);
		}
		
	}
	
	/**
	 * Metodo che ritorna true se per la pratica ricevuta come primo parametro c'è almeno un referente avente il codice
	 * fiscale ricevuto come secondo parametro
	 * @author Giuseppe Canciello
	 * @date 30 giu 2022
	 * @param pratica
	 * @param codiceFiscale
	 * @return
	 */
	private boolean isCodiceFiscaleAltriTitolari(PraticaDTO pratica, String codiceFiscale) {
		try {
			List<ReferenteFascicoloDTO>referenti=referenteDAO.searchByIdPRatica(pratica.getId());
			for (ReferenteFascicoloDTO ref:referenti) {
				if (StringUtil.isNotEmpty(ref.getCodiceFiscale())){ 
						if(ref.getCodiceFiscale().equalsIgnoreCase(codiceFiscale)) {
								return true;
						}
				}
			}
		} catch (EmptyResultDataAccessException e) {
			LOGGER.info("Nessun referente trovato per la pratica",pratica.getId());
			return false;
		}
		return false;
	}
	//-----------------------------------------------------
	
	
	@Override
	public boolean isRupForPratica(String rup) {
		Gruppi gruppo = userUtil.getGruppo();
		boolean isRup = Gruppi.hasRup(gruppo)
				&& ((rup != null && rup.equals(LogUtil.getUser()))
						|| gruppo.equals(Gruppi.REG_));
		return isRup;
	}
	
	@Override
	public void checkStatoForUpdate(PraticaDTO pratica) throws CustomOperationToAdviceException {
		Gruppi gruppo = userUtil.getGruppo();
		if (gruppo==Gruppi.RICHIEDENTI  && !pratica.getStato().equals(AttivitaDaEspletare.COMPILA_DOMANDA)) {
			LOGGER.error("Pratica in stato avanzato, impossibile procedere...");
			throw new CustomOperationToAdviceException("Pratica in stato avanzato, impossibile procedere...");
		}		
	}
	
	@Override
	public void checkComuniCompetenza(List<ComuniCompetenzaDTO> entiCompetenza) {
		if (entiCompetenza == null || entiCompetenza.size() <= 0) {
			new CustomOperationToAdviceException(
		   "L'ente delegato selezionato non ha alcun territorio di competenza, impossibile procedere!!!");
		}
	}
	
	@Override
	public void checkEsistenzaPrivacy(List<PrivacyDTO> privacyCurrent,PraticaDTO dto) throws CustomOperationToAdviceException {
		if(privacyCurrent!=null && privacyCurrent.size()>0) {
			dto.setPrivacyId(privacyCurrent.get(0).getId());	
		}else {
			throw new CustomOperationToAdviceException("Privacy da accettare scaduta o inesistente... contattare amministratore.");
		}
	}
	
	@Override
	public PraticaDTO checkDiMiaCompetenza(UUID idPratica) throws CustomOperationToAdviceException {
		PraticaDTO praticaDTO = this.praticaDao.find(idPratica);
		this.checkDiMiaCompetenzaInLettura(praticaDTO);
		return praticaDTO;
	}
	
	//Controllo per visualizzazione in sola lettura anche di altri titolari non owner
	public PraticaDTO checkDiMiaCompetenzaInLettura(UUID idPratica) throws CustomOperationToAdviceException {
		PraticaDTO praticaDTO = this.praticaDao.find(idPratica);
		this.checkDiMiaCompetenzaInLettura(praticaDTO);
		return praticaDTO;
	}

	@Override
	public PraticaDTO checkDiMiaCompetenzaByCodicePraticaAppptr(String codicePraticaAppptr) throws CustomOperationToAdviceException {
		PraticaDTO praticaDTO = this.praticaDao.findByCodice(codicePraticaAppptr);
		this.checkDiMiaCompetenza(praticaDTO);
		return praticaDTO;
	}
	
	//Controllo per visualizzazione in sola lettura anche di altri titolari non owner
	public PraticaDTO checkDiMiaCompetenzaInLetturaByCodicePraticaAppptr(String codicePraticaAppptr) throws CustomOperationToAdviceException {
		PraticaDTO praticaDTO = this.praticaDao.findByCodice(codicePraticaAppptr);
		this.checkDiMiaCompetenzaInLettura(praticaDTO);
		return praticaDTO;
	}
	
	private void userIstruttoriaCanAccessPratica(UUID idPratica, String codicePraticaAppptr)
			throws CustomOperationToAdviceException {
		if (StringUtil.isEmpty(idPratica.toString()) && StringUtil.isEmpty(codicePraticaAppptr)) {
			throw new CustomOperationToAdviceException("Riferimenti a pratica nulli, impossibile procedere !");
		}
		Gruppi gruppo = userUtil.getGruppo();
		// se fa parte di istruttoria....
		if (gruppo == Gruppi.RICHIEDENTI) {
			throw new CustomOperationToAdviceException("Accesso errato !");
		}
		List<PraticaDTO> list = null;
		try {
			// controlli di accesso
			PraticaSearch filters = new PraticaSearch();
			this.praticaService.prepareForSearch(filters); // qui vengono impostati tutti i criteri di visibilità delle
															// pratiche....
			if (idPratica != null) {
				filters.setId(idPratica);
			}
			if (codicePraticaAppptr != null) {
				filters.setCodicePraticaAppptr(codicePraticaAppptr);
			}
			list = this.praticaService.searchAll(filters,false);
		} catch (Exception e) {
			LOGGER.error("Errore in userIstruttoriaCanAccessPratica ", e);
			throw new CustomOperationToAdviceException("Pratica non trovata !");
		}
		if (list == null || list.isEmpty()) {
			LOGGER.error("pratica non trovata o non di competenza dello user corrente " + LogUtil.getUser());
			throw new CustomOperationToAdviceException("Pratica non trovata !");
		}
	}
	
}
