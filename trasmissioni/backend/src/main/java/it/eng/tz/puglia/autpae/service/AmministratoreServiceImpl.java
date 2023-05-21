package it.eng.tz.puglia.autpae.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.autpae.config.ApplicationProperties;
import it.eng.tz.puglia.autpae.controller.exception.CustomOperationToAdviceException;
import it.eng.tz.puglia.autpae.dto.DestinatarioTemplateDTO;
import it.eng.tz.puglia.autpae.dto.NuovaComunicazioneDTO;
import it.eng.tz.puglia.autpae.dto.TipologicaDTO;
import it.eng.tz.puglia.autpae.dto.TipologicaDestinatarioDTO;
import it.eng.tz.puglia.autpae.dto.BE_to_FE.PannelloAmministratoreDTO;
import it.eng.tz.puglia.autpae.entity.AllegatoCorrispondenzaDTO;
import it.eng.tz.puglia.autpae.entity.ConfigurazioneCampionamentoDTO;
import it.eng.tz.puglia.autpae.entity.ConfigurazionePermessiDTO;
import it.eng.tz.puglia.autpae.entity.DestinatarioDTO;
import it.eng.tz.puglia.autpae.entity.FascicoloDTO;
import it.eng.tz.puglia.autpae.enumeratori.EsitoVerifica;
import it.eng.tz.puglia.autpae.enumeratori.Gruppi;
import it.eng.tz.puglia.autpae.enumeratori.StatoFascicolo;
import it.eng.tz.puglia.autpae.enumeratori.TipoDestinatario;
import it.eng.tz.puglia.autpae.enumeratori.TipoTemplate;
import it.eng.tz.puglia.autpae.search.AllegatoCorrispondenzaSearch;
import it.eng.tz.puglia.autpae.search.CorrispondenzaSearch;
import it.eng.tz.puglia.autpae.search.DestinatarioSearch;
import it.eng.tz.puglia.autpae.search.FascicoloSearch;
import it.eng.tz.puglia.autpae.search.TemplateDestinatarioSearch;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoCorrispondenzaService;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoService;
import it.eng.tz.puglia.autpae.service.interfacce.AmministratoreService;
import it.eng.tz.puglia.autpae.service.interfacce.AzioniService;
import it.eng.tz.puglia.autpae.service.interfacce.CampionamentoService;
import it.eng.tz.puglia.autpae.service.interfacce.ConfigurazionePermessiService;
import it.eng.tz.puglia.autpae.service.interfacce.CorrispondenzaService;
import it.eng.tz.puglia.autpae.service.interfacce.DestinatarioService;
import it.eng.tz.puglia.autpae.service.interfacce.FascicoloService;
import it.eng.tz.puglia.autpae.service.interfacce.TemplateDestinatarioService;
import it.eng.tz.puglia.autpae.utility.VarieUtils;
import it.eng.tz.puglia.servizi_esterni.profileManager.dto.GruppoBean;
import it.eng.tz.puglia.servizi_esterni.profileManager.feign.IProfileManager;
import it.eng.tz.puglia.servizi_esterni.profileManager.feign.UserUtil;
import it.eng.tz.puglia.servizi_esterni.remote.service.interfacce.CommonService;

@Service
public class AmministratoreServiceImpl implements AmministratoreService {
	
	private static final Logger log = LoggerFactory.getLogger(AmministratoreServiceImpl.class);

	@Autowired private ApplicationProperties props;
	@Autowired private ConfigurazionePermessiService configurazionePermessiService;
	@Autowired private FascicoloService fascicoloService;
//	@Autowired private CustomScheduling customScheduling;  			TODO: da riattivare SOLO se necessario
	@Autowired private CorrispondenzaService corrispondenzaService;
	@Autowired private AllegatoCorrispondenzaService allegatoCorrispondenzaService;
	@Autowired private DestinatarioService destinatarioService;
	@Autowired private AllegatoService allegatoService;
	@Autowired private CampionamentoService campionamentoService;
	@Autowired private CommonService commonService;
	@Autowired private UserUtil userUtil;
	@Autowired private IProfileManager profileManager;
	@Autowired private TemplateDestinatarioService templateDestinatarioService;
	@Autowired private AzioniService azioniService;
	
	
/*	@Override
//	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
//	public PermessiCampionamentoDTO getPermessiCampionamento() throws Exception {
//		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
//		
//		ConfigurazioneCampionamentoDTO confCampionamento = campionamentoService.getConfigurazioneCampionamento();
//
//		PermessiCampionamentoDTO dto = new PermessiCampionamentoDTO(confCampionamento);
//		dto.setPermessi(configurazionePermessiService.select());
//		
//		// quando avrò a disposizione la lista completa degli enti, salvata in una tabella; dovrò integrare questa select(), che prendo dal db,
//		// con tutti gli enti per i quali non sono ancora stati settati i permessi, passando al FE la lista completa, con i permessi ignoti a FALSE.
//		
//		return null;
//	}	*/


/*	@Override
//	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
//	public boolean salvaPermessiCampionamento(PermessiCampionamentoDTO body) throws Exception {
//		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
//
//		if (body.getPermessi()==null || body.getPermessi().isEmpty()) {
//			log.error("Errore. Nessun ente presente nella lista permessi!");
//			throw new Exception("Errore. Nessun ente presente nella lista permessi!");
//		}
//		if (body.getIntervalloCampionamento()< 0 ||
//			body.getPercentualeIstanze()     < 0 ||
//			body.getTipoCampionamentoSuccessivo()==null) {
//			log.error("Dati incompleti o errati!");
//			throw new Exception("Dati incompleti o errati!");
//		}
//		
//		campionamentoService.updateConfigurazione(new ConfigurazioneCampionamentoDTO(body));
//		
//		// quando avrò a disposizione la lista completa degli enti, salvata in una tabella; dovrò integrare questa DTO che mi passa il FE,
//		// con tutti gli enti per i quali non sono ancora stati settati i permessi, salvando sul DB la lista completa, con i permessi ignoti a FALSE.
//
//		configurazionePermessiService.deleteAll();
//		configurazionePermessiService.insertAll(body.getPermessi());
//		
//		return true;
//	}	*/


	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public FascicoloDTO getFascicoloDaAnnullare(final String codice) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		FascicoloSearch searchF = new FascicoloSearch();
		searchF.setCodice(codice);
		searchF.setDeleted(false);
		fascicoloService.prepareForSearch(searchF);
		List<FascicoloDTO> listaFascicoli = fascicoloService.searchFascicolo(searchF).getList();
		if (listaFascicoli==null || listaFascicoli.isEmpty()) {
			log.info("Fascicolo con codice '"+codice+ "...' non presente!");
			throw new CustomOperationToAdviceException("Fascicolo con codice '"+codice+ "...' non presente!");
		}
		
		if (listaFascicoli.size()>1) {
			log.info("Sono stati trovati "+listaFascicoli.size()+" fascicoli associati al codice parziale inserito. Completare il codice ed effettuare una nuova ricerca");
			throw new CustomOperationToAdviceException("Sono stati trovati "+listaFascicoli.size()+" fascicoli associati al codice parziale inserito.\nCompletare il codice ed effettuare una nuova ricerca");
		}
		
		FascicoloDTO fascicolo = listaFascicoli.get(0);
			
		isAnnullabile(fascicolo.getStato(),fascicolo.getEsitoVerifica());
		return fascicolo;
	}


	@Override
	public void isAnnullabile(final StatoFascicolo stato,final EsitoVerifica esitoVerifica) throws CustomOperationToAdviceException {
		if (!(stato==StatoFascicolo.TRANSMITTED || 
			  stato==StatoFascicolo.SELECTED    ||
			  stato==StatoFascicolo.FINISHED    )) 
		{
			log.info("Stato fascicolo: '"+stato.getTextValue()+"' non consentito!");
			throw new CustomOperationToAdviceException("Stato fascicolo: '"+stato.getTextValue()+"' non consentito!");
		}
		
		if (!(esitoVerifica==EsitoVerifica.SAMPLING_PENDING  ||
			  esitoVerifica==EsitoVerifica.CHECK_IN_PROGRESS ||
			  esitoVerifica==EsitoVerifica.NOT_SELECTED		||
			  esitoVerifica==EsitoVerifica.NOT_SAMPLED		||
			  esitoVerifica==EsitoVerifica.POSITIVE			))
		{
			log.info("Esito verifica: '"+esitoVerifica.getTextValue()+"' non consentito!");
			throw new CustomOperationToAdviceException("Esito verifica: '"+esitoVerifica.getTextValue()+"' non consentito!");
		}
	}

	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public FascicoloDTO getFascicoloDaModificare(final String codice) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		FascicoloSearch searchF = new FascicoloSearch();
		searchF.setCodice(codice);
		searchF.setDeleted(false);
		fascicoloService.prepareForSearch(searchF);
		List<FascicoloDTO> listaFascicoli = fascicoloService.searchFascicolo(searchF).getList();
		if (listaFascicoli==null || listaFascicoli.isEmpty()) {
			log.info("Fascicolo con codice '"+codice+ "...' non presente!");
			throw new CustomOperationToAdviceException("Fascicolo con codice '"+codice+ "...' non presente!");
		}
		
		if (listaFascicoli.size()>1) {
			log.info("Sono stati trovati "+listaFascicoli.size()+" fascicoli associati al codice parziale inserito. Completare il codice ed effettuare una nuova ricerca");
			throw new CustomOperationToAdviceException("Sono stati trovati "+listaFascicoli.size()+" fascicoli associati al codice parziale inserito.\nCompletare il codice ed effettuare una nuova ricerca");
		}
		
		FascicoloDTO fascicolo = listaFascicoli.get(0);
		
		isModificabile(fascicolo.getStato(),fascicolo.getEsitoVerifica());
		return fascicolo;
	}


	/**
	 * check se lo stato fascicolo e lo stato del campionamento ammettono la modifica
	 * @autor Adriano Colaianni
	 * @date 30 ago 2021
	 * @param stato
	 * @param esito
	 * @throws CustomOperationToAdviceException
	 */
	@Override
	public void isModificabile(final StatoFascicolo stato,final EsitoVerifica esito) throws CustomOperationToAdviceException {
		if (!(stato==StatoFascicolo.TRANSMITTED || 
				stato==StatoFascicolo.FINISHED    )) 
		{
			log.info("Stato fascicolo: '"+stato.getTextValue()+"' non consentito!");
			throw new CustomOperationToAdviceException("Stato fascicolo: '"+stato.getTextValue()+"' non consentito!");
		}
		
		if (!(esito==EsitoVerifica.NOT_SELECTED		||
			  esito==EsitoVerifica.NOT_SAMPLED		||
			  esito==EsitoVerifica.POSITIVE			))
		{
			log.info("Esito verifica: '"+esito.getTextValue()+"' non consentito!");
			throw new CustomOperationToAdviceException("Esito verifica: '"+esito.getTextValue()+"' non consentito!");
		}
	}
	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public boolean annullaFascicolo(final long id) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		FascicoloDTO fascicolo = fascicoloService.find(id);

		// STATO
		if (fascicolo.getStato()==StatoFascicolo.TRANSMITTED) {
				 fascicolo.setStato(   StatoFascicolo.CANCELLED);
		}
		else if (fascicolo.getStato()==StatoFascicolo.SELECTED) {
				 fascicolo.setStato(   StatoFascicolo.CANCELLED);
		}
		else if (fascicolo.getStato()==StatoFascicolo.FINISHED && 
				(fascicolo.getEsitoVerifica()==EsitoVerifica.NOT_SELECTED || 
				 fascicolo.getEsitoVerifica()==EsitoVerifica.NOT_SAMPLED	)) {
				 fascicolo.setStato(   StatoFascicolo.CANCELLED);
		}
		else if (fascicolo.getStato()==StatoFascicolo.FINISHED && 
				 (fascicolo.getEsitoVerifica()==EsitoVerifica.POSITIVE ||
				  fascicolo.getEsitoVerifica()==EsitoVerifica.NEGATIVE)) {
				 fascicolo.setStato(   StatoFascicolo.CANCELLED);
		}
		else {
			log.info("Errore. Stato Fascicolo non consentito");
			throw new CustomOperationToAdviceException("Errore. Stato Fascicolo non consentito");
		}

		// ESITO VERIFICA
		if (fascicolo.getEsitoVerifica()==EsitoVerifica.SAMPLING_PENDING) {
				 fascicolo.setEsitoVerifica(   EsitoVerifica.CANCELLED);
		}
		else if (fascicolo.getEsitoVerifica()==EsitoVerifica.CHECK_IN_PROGRESS) {
				 fascicolo.setEsitoVerifica(   EsitoVerifica.CANCELLED);
		}
		else if (fascicolo.getEsitoVerifica()==EsitoVerifica.NOT_SELECTED) {
				 fascicolo.setEsitoVerifica(   EsitoVerifica.CANCELLED);
		}
		else if (fascicolo.getEsitoVerifica()==EsitoVerifica.NOT_SAMPLED) {
			 	 fascicolo.setEsitoVerifica(   EsitoVerifica.CANCELLED);
		}
		else if (fascicolo.getEsitoVerifica()==EsitoVerifica.POSITIVE ||
				fascicolo.getEsitoVerifica()==EsitoVerifica.NEGATIVE ) {
				 //rimane inalterato...
		}
		else {
			log.error("ERRORE. Esito Verifica non consentito");
			throw new CustomOperationToAdviceException("ERRORE. Esito Verifica non consentito");
		}

		// recupero l'allegato relativo alla (più recente) trasmissione del fascicolo
		Long idAllegato = allegatoService.findRicevutaTrasmissione(id).getCodice();
		
		// recupero la corrispondenza relativa alla (più recente) trasmissione del fascicolo
		List<AllegatoCorrispondenzaDTO> listaAC = allegatoCorrispondenzaService.search(new AllegatoCorrispondenzaSearch(idAllegato, null)).getList();
		if (listaAC==null || listaAC.size()!=1) {
			log.error("Errore. Trovate "+listaAC.size()+" corrispondenze associate alla Ricevuta di trasmissione del fascicolo con id="+id);
			throw new Exception("Errore. Trovate "+listaAC.size()+" corrispondenze associate alla Ricevuta di trasmissione del fascicolo con id="+id);
		}
		long idCorrispondenza = listaAC.get(0).getIdCorrispondenza();

		// verifico che le informazioni della corrispondenza siano corrette
		CorrispondenzaSearch searchC = new CorrispondenzaSearch();
		searchC.setId(idCorrispondenza);
		searchC.setIdFascicolo(id);
		searchC.setBozza(false);
		searchC.setComunicazione(false);
		searchC.setMittente(fascicolo.getUsernameUtenteTrasmissione());
		if (corrispondenzaService.count(searchC)!=1) {
			log.error("Errore. La corrispondenza associata alla Ricevuta di trasmissione del fascicolo non contiene i dati attesi");
			throw new Exception("Errore. La corrispondenza associata alla Ricevuta di trasmissione del fascicolo non contiene i dati attesi");
		}
		
		// 1) recupero i "vecchi" destinatari relativi a quella corrispondenza
		List<TipologicaDestinatarioDTO> listaDestinatariVecchi = new ArrayList<>();
		
		DestinatarioSearch searchD = new DestinatarioSearch();
		searchD.setIdCorrispondenza(idCorrispondenza);
		List<DestinatarioDTO> destinatari = destinatarioService.search(searchD).getList();
		
		destinatari.forEach(destinatario-> {
			listaDestinatariVecchi.add(new TipologicaDestinatarioDTO(destinatario)); 
		});
		
		// 2) cerco i destinatari di default
		List<TipologicaDestinatarioDTO> listaDestinatariDefault = new ArrayList<>();
		
		TemplateDestinatarioSearch searchTD = new TemplateDestinatarioSearch();
		searchTD.setCodiceTemplate(TipoTemplate.ELIMINAZIONE_POST_TRASMISSIONE);
		List<DestinatarioTemplateDTO> destinatariDefault = templateDestinatarioService.search(searchTD).getList();
		
		if (destinatariDefault!=null) {
			destinatariDefault.forEach(destinatario-> {
				listaDestinatariDefault.add(new DestinatarioTemplateDTO(destinatario)); 
			});
		}
		
		// 3) cerco i "nuovi" destinatari di trasmissione (che potrebbero essere cambiati nel frattempo)
		List<TipologicaDestinatarioDTO> listaDestinatariNuovi = new ArrayList<>();
		listaDestinatariNuovi = azioniService.getDestinatariTrasmissione(id, null, false);
		
		// invio la comunicazione (o corrispondenza) a tutti i destinatari interessati
		NuovaComunicazioneDTO corrispondenza = new NuovaComunicazioneDTO();
		corrispondenza.setIdFascicoli(Collections.singletonList(id));
		corrispondenza.setBozza(false);
		corrispondenza.setTipoTemplate(TipoTemplate.ELIMINAZIONE_POST_TRASMISSIONE);
		// 4) unisco le 3 liste eliminando i doppioni delle email
		corrispondenza.setDestinatari(VarieUtils.eliminaDuplicati(listaDestinatariDefault, listaDestinatariVecchi, listaDestinatariNuovi));
		
		// inserisco le informazioni per risolvere i PlaceHolders
		corrispondenza.getInfoPlaceHolders().setIdFascicolo(id);
		
		if (corrispondenzaService.inviaCorrispondenza(corrispondenza)==null) {
			log.error("Errore durante l'invio della corrispondenza");
			throw new Exception("Errore durante l'invio della corrispondenza");
		};

		if (campionamentoService.deleteByFascicoloId(id)==false) {
			log.error("Errore durante la cancellazione dei campionamenti associati al fascicolo");
			throw new Exception("Errore durante la cancellazione dei campionamenti associati al fascicolo");
		};
		
		fascicoloService.salva(fascicolo);
		return true;
	}

	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public boolean modificaFascicolo(final long idFascicolo, final int giorni) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		FascicoloDTO fascicolo = fascicoloService.find(idFascicolo);

		// STATO
		if (fascicolo.getStato()!=StatoFascicolo.TRANSMITTED && 
			fascicolo.getStato()!=StatoFascicolo.FINISHED)
		{
			log.info("Errore. Stato Fascicolo non consentito");
			throw new Exception("Errore. Stato Fascicolo non consentito");
		}

		// ESITO VERIFICA
		if (fascicolo.getEsitoVerifica()!=EsitoVerifica.NOT_SELECTED && 
			fascicolo.getEsitoVerifica()!=EsitoVerifica.NOT_SAMPLED && 
			fascicolo.getEsitoVerifica()!=EsitoVerifica.POSITIVE)
		{
			log.error("Errore. Esito Verifica non consentito");
			throw new Exception("Errore. Esito Verifica non consentito");
		}
		
        Date currentDate = new Date();
        LocalDateTime localDateTime = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        localDateTime = localDateTime.plusDays(giorni);
        Date modificabileFino = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		
   //   customScheduling.bloccaModifica(modificabileFino, id);    // non serve più, parte una revisione giornaliera che si occupa di bloccare le scadute
        
		// invio la comunicazione (o corrispondenza) all'utente che ha chiesto la modifica (in realtà all'utente che ha trasmesso)
        
        NuovaComunicazioneDTO corrispondenza = new NuovaComunicazioneDTO();
        corrispondenza.setIdFascicoli(Collections.singletonList(idFascicolo));
        corrispondenza.setBozza(false);
        corrispondenza.setTipoTemplate(TipoTemplate.MODIFICA_POST_TRASMISSIONE);
        
		// 1) cerco i destinatari di default
		List<TipologicaDestinatarioDTO> listaDestinatariDefault = new ArrayList<>();
		
		TemplateDestinatarioSearch searchTD = new TemplateDestinatarioSearch();
		searchTD.setCodiceTemplate(TipoTemplate.MODIFICA_POST_TRASMISSIONE);
		List<DestinatarioTemplateDTO> destinatariDefault = templateDestinatarioService.search(searchTD).getList();
		
		if (destinatariDefault!=null) {
			destinatariDefault.forEach(destinatario-> {
				listaDestinatariDefault.add(new DestinatarioTemplateDTO(destinatario)); 
			});
		}

		// 2) recupero l'utente che ha trasmesso il fascicolo
		TipologicaDestinatarioDTO destinatario = new TipologicaDestinatarioDTO(fascicolo.getEmailUtenteTrasmissione(), fascicolo.getDenominazioneUtenteTrasmissione(), TipoDestinatario.TO);
		
		// 3) unisco le 2 liste eliminando i doppioni delle email
		corrispondenza.setDestinatari(VarieUtils.eliminaDuplicati(listaDestinatariDefault, Collections.singletonList(destinatario)));
		
		// inserisco le informazioni per risolvere i PlaceHolders
		corrispondenza.getInfoPlaceHolders().setIdFascicolo(idFascicolo);
		
		boolean ret = fascicoloService.consentiModifica(idFascicolo, modificabileFino)==1;	// devo eseguire prima questa operazione!
		
		// invio la corrispondenza
		corrispondenzaService.inviaCorrispondenza(corrispondenza);
        
		return (ret);
	}


	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public PannelloAmministratoreDTO infoPannello(final String jwt) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		try {
			PannelloAmministratoreDTO pannello = new PannelloAmministratoreDTO(campionamentoService.getConfigurazioneCampionamento());
			
			List<ConfigurazionePermessiDTO> listaPermessi = configurazionePermessiService.select();
			
			List<TipologicaDTO> gruppiValidi = this.listaGruppi(jwt);
			
			listaPermessi.forEach(elem->{
				boolean valido = false;
				for (TipologicaDTO gruppo : gruppiValidi) {
					if (gruppo.getCodice().equalsIgnoreCase(elem.getCodiceEnte())) {
						valido = true;
						break;
					}
				}
				if (valido==true) {
					if (elem.isPermessoComunicazione ()==true) 
						pannello.getComunicazioni ().add(elem.getCodiceEnte());
					if (elem.isPermessoDocumentazione()==true) 
						pannello.getDocumentazione().add(elem.getCodiceEnte());
					if (elem.isPermessoOsservazione  ()==true) 
						pannello.getOsservazioni  ().add(elem.getCodiceEnte());
				}
				if (elem.getCodiceEnte().equalsIgnoreCase("PUBLIC")) {
					pannello.setStatoRegistrazione(elem.getStatoRegistrazionePubblico());
					pannello.setEsitoVerifica(elem.getEsitoVerificaPubblico());
				}
			});
			return pannello;
		} catch (Exception e) {
			throw e;
		}
	}


	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public boolean savePannello(final PannelloAmministratoreDTO pannello) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		ConfigurazioneCampionamentoDTO configCampionamento = new ConfigurazioneCampionamentoDTO();
		configCampionamento.setCampionamentoAttivo(pannello.isCampionamentoAttivo());
		configCampionamento.setIntervalloCampionamento(pannello.getIntervalloCampionamento());
		configCampionamento.setTipoCampionamentoSuccessivo(pannello.getTipoCampionamentoSuccessivo());
		configCampionamento.setPercentualeIstanze(pannello.getPercentualeIstanze());
		configCampionamento.setGiorniNotificaCampionamento(pannello.getGiorniNotificaCampionamento());
		configCampionamento.setGiorniNotificaVerifica(pannello.getGiorniNotificaVerifica());
		configCampionamento.setIntervalloVerifica(pannello.getIntervalloVerifica());
		configCampionamento.setEsitoPubblico(pannello.isEsitoPubblico());
		configCampionamento.setApplicaInCorso(pannello.isApplicaInCorso());
		
		campionamentoService.updateConfigurazione(configCampionamento);
		
		List<ConfigurazionePermessiDTO> listaPermessi = new ArrayList<ConfigurazionePermessiDTO>();
		
		if (pannello.getComunicazioni()!=null && !pannello.getComunicazioni().isEmpty()) {
			pannello.getComunicazioni().forEach(elem->{
				boolean giaPresente = false;
				for (ConfigurazionePermessiDTO permesso : listaPermessi) {
					if (permesso.getCodiceEnte().equalsIgnoreCase(elem)) {
						giaPresente = true;
						permesso.setPermessoComunicazione(pannello.isComunicazioniAttivo());
						break;
					}
				};
				if (giaPresente==false) {
					ConfigurazionePermessiDTO nuovoPermesso = new ConfigurazionePermessiDTO();
					nuovoPermesso.setCodiceEnte(elem);
					nuovoPermesso.setPermessoComunicazione(pannello.isComunicazioniAttivo());
					listaPermessi.add(nuovoPermesso);
				}
			});
		}
		if (pannello.getDocumentazione()!=null && !pannello.getDocumentazione().isEmpty()) {
			pannello.getDocumentazione().forEach(elem->{
				boolean giaPresente = false;
				for (ConfigurazionePermessiDTO permesso : listaPermessi) {
					if (permesso.getCodiceEnte().equalsIgnoreCase(elem)) {
						giaPresente = true;
						permesso.setPermessoDocumentazione(pannello.isDocumentazioneAttivo());
						break;
					}
				};
				if (giaPresente==false) {
					ConfigurazionePermessiDTO nuovoPermesso = new ConfigurazionePermessiDTO();
					nuovoPermesso.setCodiceEnte(elem);
					nuovoPermesso.setPermessoDocumentazione(pannello.isDocumentazioneAttivo());
					listaPermessi.add(nuovoPermesso);
				}
			});
		}
		if (pannello.getOsservazioni()!=null && !pannello.getOsservazioni().isEmpty()) {
			pannello.getOsservazioni().forEach(elem->{
				boolean giaPresente = false;
				for (ConfigurazionePermessiDTO permesso : listaPermessi) {
					if (permesso.getCodiceEnte().equalsIgnoreCase(elem)) {
						giaPresente = true;
						permesso.setPermessoOsservazione(true);
						break;
					}
				};
				if (giaPresente==false) {
					ConfigurazionePermessiDTO nuovoPermesso = new ConfigurazionePermessiDTO();
					nuovoPermesso.setCodiceEnte(elem);
					nuovoPermesso.setPermessoOsservazione(true);
					listaPermessi.add(nuovoPermesso);
				}
			});
		}
		
		ConfigurazionePermessiDTO infoPublic = new ConfigurazionePermessiDTO();
		infoPublic.setCodiceEnte("PUBLIC");
		infoPublic.setStatoRegistrazionePubblico(pannello.isStatoRegistrazione());
		infoPublic.setEsitoVerificaPubblico(pannello.isEsitoVerifica());
		listaPermessi.add(infoPublic);
		//admin ha sempre visibili i pannelli
		ConfigurazionePermessiDTO adminConfig = new ConfigurazionePermessiDTO();
		adminConfig.setCodiceEnte("ADMIN");
		adminConfig.setPermessoComunicazione(true);
		adminConfig.setPermessoOsservazione(true);
		adminConfig.setPermessoComunicazione(true);
		listaPermessi.add(adminConfig);
		
		configurazionePermessiService.deleteAll();
		configurazionePermessiService.insertAll(listaPermessi);
		return true;
	}

	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public List<TipologicaDTO> listaGruppi(final String jwt) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		try {
			// chiamata al profilatore
			GruppoBean[] infoGruppi = profileManager.getGruppiUtente(jwt, props.getCodiceApplicazioneProfile(), null).getPayload();

			List<TipologicaDTO> gruppiProfilatore = new ArrayList<>();

			if (infoGruppi!=null) {
				for (GruppoBean gruppo : infoGruppi) {
					gruppiProfilatore.add(new TipologicaDTO(gruppo.getCodiceGruppo(), gruppo.getNomeGruppo()));
				}
			}
			// prendo la lista degli idOrgPae ancora validi (non scaduti)
			List<Integer> idValidi = commonService.getOrganizzazioniValidePerApplicazione(props.getCodiceApplicazioneProfile());
			
			// costruire la "listaGruppi" a partire dalle informazioni del profilatore eliminando i ruoli e i gruppi scaduti
			List<TipologicaDTO> listaGruppi = new ArrayList<>();

			if (gruppiProfilatore!=null) {
				for (TipologicaDTO gruppoP : gruppiProfilatore) {
					boolean giaPresente = false;
					for (TipologicaDTO gruppo : listaGruppi) {
						if (gruppo.getCodice().equalsIgnoreCase(gruppoP.getCodice().substring(0, gruppoP.getCodice().length()-2))) {
							giaPresente = true;
							break;
						}
					};
					Gruppi gruppoEnum = userUtil.getGruppo(gruppoP.getCodice());
					if (giaPresente==false && 
							!gruppoP.getCodice().toUpperCase().equals(Gruppi.ADMIN.name()) &&
							gruppoEnum!=null && 
							idValidi.contains(userUtil.getIntegerId(gruppoP.getCodice()))) {
						listaGruppi.add(new TipologicaDTO(gruppoP.getCodice().substring(0, gruppoP.getCodice().length()-2), gruppoP.getLabel()));
					}
				};
			}
		//	return listaGruppi;
		//	List<Integer> organizzazioni = new ArrayList<Integer>();
		//	listaGruppi.forEach(elem->{
		//		organizzazioni.add(userUtil.getIntegerId(elem.getCodice()));
		//	});
		//	List<EnteDTO> enti = commonService.selectEntiById(organizzazioni);
		//	listaGruppi.forEach(elem->{
		//		int orgId = userUtil.getIntegerId(elem.getCodice());
		//		boolean trovato = false;
		//		for (EnteDTO ente : enti) {
		//			if (ente.getId().equals(orgId)) {
		//				String gruppo=userUtil.getGruppo(elem.getCodice()).name();
		//				elem.setLabel("["+gruppo.substring(0,gruppo.length()-1)+"] "+ente.getDescrizione());
		//				trovato = true;
		//				break;
		//			}
		//		}
		//		if (trovato==false) {
		//			log.error("Errore nel recupero dei nomi degli Enti");
		//		}
		//	});
			for (TipologicaDTO elem : listaGruppi) {
				Gruppi gruppoEnum = userUtil.getGruppo(elem.getCodice());
				if(gruppoEnum!=null) {
					String gruppo=gruppoEnum.name();
					elem.setLabel("["+gruppo.substring(0,gruppo.length()-1)+"] "
							 +commonService.getDenominazioneOrganizzazione(userUtil.getIntegerId(elem.getCodice())));
				}else {
					log.error("Gruppo con tipologia non riconosciuta:"+elem.getCodice());
				}
			};
			Collections.sort(listaGruppi, new Comparator<TipologicaDTO>() {	// ordino in base alla descrizione
				@Override
				public int compare(final TipologicaDTO o1, final TipologicaDTO o2) {
					return o1.getLabel().compareToIgnoreCase(o2.getLabel());
				}
			});
			return listaGruppi;
		} catch (Exception e) {
			throw e;
		}
	}

	// fabio lopez - bisogna chiamare questo metodo con un job singolo pianificato, da eseguire quando scade il periodo di modifica
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public boolean revocaModifica(final long idFascicolo) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		FascicoloDTO fascicolo = fascicoloService.find(idFascicolo);

		// STATO
		if (fascicolo.getStato()!=StatoFascicolo.ON_MODIFY)	{
			log.info("Errore. Il Fascicolo non è 'IN MODIFICA'");
			throw new CustomOperationToAdviceException("Errore. Il Fascicolo non è 'IN MODIFICA'");
		}
		
		// TODO: eliminare il task programmato: customScheduling.bloccaModifica(modificabileFino, id);
		// Occhio: "... per questo task NON ho bisogno di prevedere l'annullamento perchè l'effettiva applicazione dei cambiamenti è già gestita nella WHERE della query"
		
		// TODO: eliminare l'invio delle eventuali email (se previste in analisi) che ricordano all'utente n-giorni prima che sta scadendo il tempo
		
		// invio la comunicazione (o corrispondenza) all'utente che ha chiesto (e già ottenuto) la modifica (che è sempre chi ha trasmesso il fascicolo)
               
        NuovaComunicazioneDTO corrispondenza = new NuovaComunicazioneDTO();
        corrispondenza.setIdFascicoli(Collections.singletonList(idFascicolo));
        corrispondenza.setBozza(false);
        corrispondenza.setTipoTemplate(TipoTemplate.REVOCA_MODIFICA_POST_TRASMISSIONE);

		// 1) cerco i destinatari di default
		List<TipologicaDestinatarioDTO> listaDestinatariDefault = new ArrayList<>();
		
		TemplateDestinatarioSearch searchTD = new TemplateDestinatarioSearch();
		searchTD.setCodiceTemplate(TipoTemplate.REVOCA_MODIFICA_POST_TRASMISSIONE);
		List<DestinatarioTemplateDTO> destinatariDefault = templateDestinatarioService.search(searchTD).getList();
		
		if (destinatariDefault!=null) {
			destinatariDefault.forEach(destinatario-> {
				listaDestinatariDefault.add(new DestinatarioTemplateDTO(destinatario)); 
			});
		}
		
		// 2) recupero l'utente che ha trasmesso il fascicolo
		TipologicaDestinatarioDTO destinatario = new TipologicaDestinatarioDTO(fascicolo.getEmailUtenteTrasmissione(), fascicolo.getDenominazioneUtenteTrasmissione(), TipoDestinatario.TO);

		// 3) unisco le 2 liste eliminando i doppioni delle email
		corrispondenza.setDestinatari(VarieUtils.eliminaDuplicati(listaDestinatariDefault, Collections.singletonList(destinatario)));
		
		// inserisco le informazioni per risolvere i PlaceHolders
		corrispondenza.getInfoPlaceHolders().setIdFascicolo(idFascicolo);
		
		corrispondenzaService.inviaCorrispondenza(corrispondenza);
        
		fascicoloService.resettaAllaTrasmissione(idFascicolo);
		return (fascicoloService.bloccaModifica(idFascicolo)==1);
	}
	
}