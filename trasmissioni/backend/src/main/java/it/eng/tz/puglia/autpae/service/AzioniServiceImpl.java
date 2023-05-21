package it.eng.tz.puglia.autpae.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.EnumUtils;
import org.postgresql.util.PGobject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.oxm.ValidationFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.autpae.config.ApplicationProperties;
import it.eng.tz.puglia.autpae.controller.exception.CustomOperationToAdviceException;
import it.eng.tz.puglia.autpae.dto.DestinatarioTemplateDTO;
import it.eng.tz.puglia.autpae.dto.InformazioniDTO;
import it.eng.tz.puglia.autpae.dto.LineaTemporaleDTO;
import it.eng.tz.puglia.autpae.dto.LocalizzazioneTabDTO;
import it.eng.tz.puglia.autpae.dto.SelectOptionDto;
import it.eng.tz.puglia.autpae.dto.TipologicaDTO;
import it.eng.tz.puglia.autpae.dto.TipologicaDestinatarioDTO;
import it.eng.tz.puglia.autpae.dto.ValidationBeanDto;
import it.eng.tz.puglia.autpae.entity.AllegatoCorrispondenzaDTO;
import it.eng.tz.puglia.autpae.entity.AllegatoFascicoloDTO;
import it.eng.tz.puglia.autpae.entity.DestinatarioDTO;
import it.eng.tz.puglia.autpae.entity.FascicoloDTO;
import it.eng.tz.puglia.autpae.entity.LocalizzazioneInterventoDTO;
import it.eng.tz.puglia.autpae.entity.ParchiPaesaggiImmobiliDTO;
import it.eng.tz.puglia.autpae.entity.RichiedenteDTO;
import it.eng.tz.puglia.autpae.entity.composedKeys.AllegatoObbligatorioPK;
import it.eng.tz.puglia.autpae.enumeratori.Ditta;
import it.eng.tz.puglia.autpae.enumeratori.ErroriValidazioneBE;
import it.eng.tz.puglia.autpae.enumeratori.Gruppi;
import it.eng.tz.puglia.autpae.enumeratori.ParchiPaesaggiImmobiliSigla;
import it.eng.tz.puglia.autpae.enumeratori.StatoFascicolo;
import it.eng.tz.puglia.autpae.enumeratori.TipoAllegato;
import it.eng.tz.puglia.autpae.enumeratori.TipoDestinatario;
import it.eng.tz.puglia.autpae.enumeratori.TipoTemplate;
import it.eng.tz.puglia.autpae.scheduler.ProtocollazioneFascicoloScheduler;
import it.eng.tz.puglia.autpae.scheduler.executor.bean.TrasmissioneBean;
import it.eng.tz.puglia.autpae.search.AllegatoCorrispondenzaSearch;
import it.eng.tz.puglia.autpae.search.AllegatoFascicoloSearch;
import it.eng.tz.puglia.autpae.search.CorrispondenzaSearch;
import it.eng.tz.puglia.autpae.search.DestinatarioSearch;
import it.eng.tz.puglia.autpae.search.FascicoloSearch;
import it.eng.tz.puglia.autpae.search.ParchiPaesaggiImmobiliSearch;
import it.eng.tz.puglia.autpae.search.ResponsabileSearch;
import it.eng.tz.puglia.autpae.search.RichiedenteSearch;
import it.eng.tz.puglia.autpae.search.TemplateDestinatarioSearch;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoCorrispondenzaService;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoFascicoloService;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoObbligatorioService;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoService;
import it.eng.tz.puglia.autpae.service.interfacce.AzioniService;
import it.eng.tz.puglia.autpae.service.interfacce.ComuniCompetenzaService;
import it.eng.tz.puglia.autpae.service.interfacce.CorrispondenzaService;
import it.eng.tz.puglia.autpae.service.interfacce.DestinatarioService;
import it.eng.tz.puglia.autpae.service.interfacce.FascicoloService;
import it.eng.tz.puglia.autpae.service.interfacce.GruppiRuoliService;
import it.eng.tz.puglia.autpae.service.interfacce.InterventoService;
import it.eng.tz.puglia.autpae.service.interfacce.LocalizzazioneInterventoService;
import it.eng.tz.puglia.autpae.service.interfacce.LocalizzazioneService;
import it.eng.tz.puglia.autpae.service.interfacce.ParchiPaesaggiImmobiliService;
import it.eng.tz.puglia.autpae.service.interfacce.ProvvedimentoService;
import it.eng.tz.puglia.autpae.service.interfacce.ResponsabileService;
import it.eng.tz.puglia.autpae.service.interfacce.RichiedenteService;
import it.eng.tz.puglia.autpae.service.interfacce.TemplateDestinatarioService;
import it.eng.tz.puglia.autpae.service.interfacce.TipoProcedimentoService;
import it.eng.tz.puglia.autpae.utility.VarieUtils;
import it.eng.tz.puglia.autpae.utility.validator.GenericValidator;
import it.eng.tz.puglia.batch.queue.IQueueService;
import it.eng.tz.puglia.error.exception.CustomValidationException;
import it.eng.tz.puglia.security.util.SecurityUtil;
import it.eng.tz.puglia.servizi_esterni.profileManager.feign.UserUtil;
import it.eng.tz.puglia.servizi_esterni.remote.service.interfacce.AnagraficaService;
import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.util.string.StringUtil;

@Service
public class AzioniServiceImpl implements AzioniService {
	
	private static final Logger log = LoggerFactory.getLogger(AzioniServiceImpl.class);
	
	@Value("${spring.application.name}")
	private String applicationName;

	@Autowired  private ApplicationProperties props;
	@Autowired  private UserUtil userUtil;
	@Autowired	private FascicoloService fascicoloService;
	@Autowired	private RichiedenteService richiedenteService;
	@Autowired	private ResponsabileService responsabileService;
	@Autowired	private AllegatoService allegatoService;
	@Autowired	private AllegatoFascicoloService allegatoFascicoloService;
	@Autowired	private InterventoService interventoService;
	@Autowired	private LocalizzazioneService localizzazioneService;
	@Autowired	private ComuniCompetenzaService comuniCompetenzaService;
	@Autowired	private AnagraficaService anagraficaService;
	@Autowired	private ProvvedimentoService provvedimentoService;
	@Autowired	private LocalizzazioneInterventoService localizzazioneInterventoService;
	@Autowired	private ParchiPaesaggiImmobiliService parchiPaesaggiImmobiliService;
	@Autowired  private AllegatoObbligatorioService allegatoObbligatorioService;
	@Autowired	private TipoProcedimentoService tipoProcedimentoService;
	@Autowired  private TemplateDestinatarioService templateDestinatarioService;
	@Autowired  private CorrispondenzaService corrispondenzaService;
	@Autowired  private DestinatarioService destinatarioService;
	@Autowired  private AllegatoCorrispondenzaService allegatoCorrispondenzaService;
	@Autowired  private MessageSource messageSource;
	@Autowired  private GruppiRuoliService gruppiRuoliService;
	@Autowired  private IQueueService queueService;
	@Autowired  private GenericValidator validator;
	
	
	
	@Override
	@Transactional(readOnly=false, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public LocalizzazioneTabDTO salvaLocalizzazione(final LocalizzazioneTabDTO body, final Long idFascicolo) throws Exception{
		if(body != null)
		{
			this.checkModificabilita(idFascicolo);
			this.localizzazioneService.salva(body, idFascicolo);
		}
		this.comuniCompetenzaService.aggiorna(idFascicolo, body);
		return body;
	}
	
	
	@Override
	@Transactional(readOnly=false, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public InformazioniDTO salva(final InformazioniDTO body) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		if(SecurityUtil.isApiUser() == false
		&& body.isMultiComune()==false
		&& body.getLocalizzazione() != null
		&& ListUtil.size(body.getLocalizzazione().getLocalizzazioneComuni()) > 1
		) {
			throw new CustomOperationToAdviceException("Solo regione e provincia possono inserire più comuni");
		}
		// forzare, se necessario, le chiavi esterne riferite a id_Fascicolo nelle dipendenze
		// controllo sul db che il fascicolo sia in stato WORKING. In caso contrario verifico che sia ON_MODIFY
		final boolean inModifica = this.checkModificabilita(body.getId());
		FascicoloDTO oldFascicolo=null;
		if (inModifica==true) {
			this.salvaInModifica(body);
		}
		else {
			body.setStato(StatoFascicolo.WORKING);
			if(SecurityUtil.isApiUser()) {
				//in questo caso reimposto una serie di campi che mi arrivano in body ma non possono essere variati 
				// recupero il DTO di informazioni relativo alla trasmissione
				oldFascicolo= this.fascicoloService.find(body.getId());
				body.setCodice(oldFascicolo.getCodice());
				body.setUfficio(oldFascicolo.getUfficio());
				body.setTipoProcedimento(oldFascicolo.getTipoProcedimento());
				body.setStato(oldFascicolo.getStato());
				body.setDataTrasmissione(oldFascicolo.getDataTrasmissione());
				body.setDataCampionamento(oldFascicolo.getDataCampionamento());
				body.setDataVerifica(oldFascicolo.getDataVerifica());
				body.setEsitoVerifica(oldFascicolo.getEsitoVerifica());
				body.setStatoRegistrazione(oldFascicolo.getStatoRegistrazione());
				body.setVersFascicolo(oldFascicolo.getVersFascicolo());
				body.setVersRichiedente(oldFascicolo.getVersRichiedente());
				body.setVersIntervento(oldFascicolo.getVersIntervento());
				body.setVersLocalizzazione(oldFascicolo.getVersLocalizzazione());
				body.setVersAllegati(oldFascicolo.getVersAllegati());
				body.setVersProvvedimento(oldFascicolo.getVersProvvedimento());
				body.setCodicePraticaAppptr(oldFascicolo.getCodicePraticaAppptr());
				body.settPraticaId(oldFascicolo.gettPraticaId());
			}
			this.fascicoloService.salva(body);
			
			if(body.getRichiedente() != null)
			{
				//controllo che il valore della ditta sia idoneo se presente
				if(body.getRichiedente().isDitta() && body.getRichiedente().getDittaInQualitaDi() != null && !body.getRichiedente().getDittaInQualitaDi().isEmpty() && !EnumUtils.isValidEnum(Ditta.class, body.getRichiedente().getDittaInQualitaDi())) 
					throw new CustomOperationToAdviceException("In qualità di non conforme");
				
				body.getRichiedente().setIdFascicolo(body.getId());
				// controllo sul db se esiste IL richiedente, se non c'è lo inserisco (insert), se c'è lo aggiorno (update)
				final RichiedenteSearch searchR = new RichiedenteSearch();
				searchR.setIdFascicolo(body.getId());
				if (this.richiedenteService.count(searchR)==0)
					body.getRichiedente().setId(this.richiedenteService.inserisci(body.getRichiedente()));
				else {
					body.getRichiedente().setId(this.richiedenteService.datiRichiedente(body.getId()).getId());
					this.richiedenteService.salva(body.getRichiedente());
				}
				
				if (this.props.isPareri() && body.getRichiedente().getResponsabile()!=null) {
					body.getRichiedente().getResponsabile().setIdFascicolo(body.getId());
					// controllo sul db se esiste IL responsabile, se non c'è lo inserisco (insert), se c'è lo aggiorno (update)
					final ResponsabileSearch searchResp = new ResponsabileSearch();
					searchResp.setIdFascicolo(body.getId());
					if (this.responsabileService.count(searchResp)==0)
						body.getRichiedente().getResponsabile().setId(this.responsabileService.inserisci(body.getRichiedente().getResponsabile()));
					else {
						body.getRichiedente().getResponsabile().setId(this.richiedenteService.datiRichiedente(body.getId()).getResponsabile().getId());
						this.responsabileService.salva(body.getRichiedente().getResponsabile());
					}
				}
			}
			// TODO: sarebbe molto meglio se usassi questa parte di codice, facendomi passare dal FE l'id del richiedente (ma a volte il FE canna e me lo passa NULL)
			// TODO: se dall'analisi fossimo certi che dovrà esistere sempre solo UN richiedente, settargli 'id'='id_fascicolo' in modo da avere 1:1 tra le 2 tabelle
			
			//			if (body.getRichiedente().getId()==null)
			//				body.getRichiedente().setId(richiedenteService.inserisci(body.getRichiedente()));
			//			else
			//				richiedenteService.salva(body.getRichiedente());
	
			if(body.getInterventoSelezionati()!=null /*&& body.getInterventoSelezionati().size()>0*/)
				this.interventoService.salva(body.getInterventoSelezionati(), body.getId());
	
			this.salvaLocalizzazione(body.getLocalizzazione(),body.getId());
			
			// se il 'Parere Mibac' non è previsto per il tipo procedimento selezionato, lo cancello
			try {
				this.allegatoObbligatorioService.find(new AllegatoObbligatorioPK(TipoAllegato.PARERE_MIBAC, body.getTipoProcedimento().name()));
				log.info("L'allegato "+TipoAllegato.PARERE_MIBAC.name()+" è previsto");
			} catch (final Exception e) {
				log.info("L'allegato "+TipoAllegato.PARERE_MIBAC.name()+" non è previsto");
				final AllegatoFascicoloSearch search = new AllegatoFascicoloSearch();
				search.setIdFascicolo(body.getId());
				search.setType(TipoAllegato.PARERE_MIBAC);
				final List<AllegatoFascicoloDTO> listaAF = this.allegatoFascicoloService.search(search).getList();
				if (!listaAF.isEmpty()) {
					this.allegatoService.eliminaAllegato(listaAF.get(0).getIdAllegato());
					if (body.getProvvedimento()!=null)
						body.getProvvedimento().setParere(null);
				}
			}
		}
		return body;
	}
	
	@Override
	@Transactional(readOnly=false, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public void salvaInModifica(final InformazioniDTO body) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		final long idFascicolo = body.getId();
		
		// recupero il DTO di informazioni relativo alla trasmissione
		final InformazioniDTO infoTrasm = this.fascicoloService.find(idFascicolo).getInfoComplete();
		
		// inserisco i dati modificabili del Tab Fascicolo
		infoTrasm.setStato(StatoFascicolo.ON_MODIFY);
		infoTrasm.setNote(body.getNote());
		infoTrasm.setCodiceInternoEnte(body.getCodiceInternoEnte());
		infoTrasm.setNumeroInternoEnte(body.getNumeroInternoEnte());
		infoTrasm.setProtocollo(body.getProtocollo());
		infoTrasm.setDataProtocollo(body.getDataProtocollo());
		// inserisco i dati modificabili del Tab Intervento
		infoTrasm.setInterventoDettaglio(body.getInterventoDettaglio());
		infoTrasm.setInterventoSpecifica(body.getInterventoSpecifica());
		if (this.props.isPareri()) {
			infoTrasm.setDataDelibera(body.getDataDelibera());
			infoTrasm.setDeliberaNum(body.getDeliberaNum());
			infoTrasm.setOggettoDelibera(body.getOggettoDelibera());
			infoTrasm.setInfoDeliberePrecedenti(body.getInfoDeliberePrecedenti());
			infoTrasm.setDescrizioneIntervento(body.getDescrizioneIntervento());
		}
		// inserisco i dati modificabili del Tab Provvedimento
		infoTrasm.setNumeroProvvedimento(body.getNumeroProvvedimento());
		infoTrasm.setDataRilascioAutorizzazione(body.getDataRilascioAutorizzazione());
		infoTrasm.setRup(body.getRup());
		
		this.fascicoloService.salva(infoTrasm);
		
		// aggiorno integralmente il Tab Richiedente\Responsabile
		if(body.getRichiedente()!=null)	// probabilmente superfluo in questa fase
		{
			//controllo che il valore della ditta sia idoneo se presente
			if(body.getRichiedente().isDitta() && body.getRichiedente().getDittaInQualitaDi() != null && !body.getRichiedente().getDittaInQualitaDi().isEmpty() && !EnumUtils.isValidEnum(Ditta.class, body.getRichiedente().getDittaInQualitaDi())) 
				throw new CustomOperationToAdviceException("In qualità di non conforme");
			body.getRichiedente().setIdFascicolo(idFascicolo);
			body.getRichiedente().setId(this.richiedenteService.datiRichiedente(idFascicolo).getId());
			this.richiedenteService.salva(body.getRichiedente());
			
			if (this.props.isPareri() && body.getRichiedente().getResponsabile()!=null) { // probabilmente &&... superfluo in questa fase
				body.getRichiedente().getResponsabile().setIdFascicolo(idFascicolo);
				body.getRichiedente().getResponsabile().setId(this.richiedenteService.datiRichiedente(idFascicolo).getResponsabile().getId());
				this.responsabileService.salva(body.getRichiedente().getResponsabile());
			}
		}
		
		// aggiorno integralmente il Tab Descrizione Intervento
		if(body.getInterventoSelezionati()!=null /*&& !body.getInterventoSelezionati().isEmpty()*/) { // probabilmente superfluo in questa fase
			this.interventoService.salva(body.getInterventoSelezionati(), idFascicolo);
		}

		// aggiorno ciò che è possibile modificare nel Tab Localizzazione
		final LocalizzazioneTabDTO localizzazione = infoTrasm.getLocalizzazione();
		
		for (final LocalizzazioneInterventoDTO comuneOld : localizzazione.getLocalizzazioneComuni()) {
			for (final LocalizzazioneInterventoDTO comuneNew : body.getLocalizzazione().getLocalizzazioneComuni()) {
				if (comuneOld.getComuneId()==comuneNew.getComuneId()) {
					comuneOld.setIndirizzo(comuneNew.getIndirizzo());
					comuneOld.setNumCivico(comuneNew.getNumCivico());
					comuneOld.setPiano(comuneNew.getPiano());
					comuneOld.setInterno(comuneNew.getInterno());
					comuneOld.setDestUsoAtt(comuneNew.getDestUsoAtt());
					comuneOld.setDestUsoProg(comuneNew.getDestUsoProg());
					comuneOld.setComune(comuneNew.getComune());
					comuneOld.setSiglaProvincia(comuneNew.getSiglaProvincia());
					comuneOld.setDataRiferimentoCatastale(comuneNew.getDataRiferimentoCatastale());
					comuneOld.setStrade(comuneNew.getStrade());
				    //in tabella particelle_catastali...
					comuneOld.setParticelle(comuneNew.getParticelle());
				}
			}
		}
		this.localizzazioneService.salva(localizzazione, idFascicolo);
	}

	
	private boolean checkModificabilita(final long idFascicolo) throws Exception {
		final FascicoloSearch searchF = new FascicoloSearch();
		searchF.setId(idFascicolo);
		searchF.setStato(StatoFascicolo.WORKING);
		this.fascicoloService.prepareForSearch(searchF);
		final long count = this.fascicoloService.count(searchF);
		
		if (count==1)
			return false;
		
		else if (count==0) {
			log.info("Attenzione. Fascicolo non in WORKING");

			searchF.setStato(StatoFascicolo.ON_MODIFY);
			searchF.setUsernameUtenteTrasmissione(SecurityUtil.getUsername());
			if (this.fascicoloService.count(searchF)!=1) {
				log.error("Fascicolo non 'In Modifica' o utente non abilitato alla modifica!");
				throw new CustomOperationToAdviceException("Fascicolo non 'In Modifica' o utente non abilitato alla modifica!");
			}
			
			final Date modificabileFino = this.fascicoloService.getModificabileFino(idFascicolo);
			
			if(modificabileFino==null) {
				log.error("Errore. Termine ultimo di modifica non trovato");
				throw new Exception("Errore. Termine ultimo di modifica non trovato");
			}
			if (new Date().after(modificabileFino)) {
				log.error("Il Fascicolo non è più modificabile dopo il giorno: "+modificabileFino);
				throw new CustomOperationToAdviceException("Il Fascicolo non è più modificabile dopo il giorno: "+modificabileFino);
			}
		return true;
		}
		
		else
			throw new Exception("Errore. Valore non previsto: [count="+count+"]");
	}


/*	@Override
//	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
//	public boolean valida(InformazioniDTO body) throws Exception {
//
//	boolean valido = (
//						 fascicoloService.validazione(body)
//					  && richiedenteService.validazione(body.getRichiedente())
//					  && interventoService.validazione(body.getIntervento(), body.getId(), body.getTipoProcedimento().name())
//				 	  && localizzazioneService.validazione(body.getLocalizzazione(), body.getId())
//					  && provvedimentoService.validazione(body.getId(), body.getTipoProcedimento())
//					  && allegatoService.validazione(body.getId(), body.getTipoProcedimento())
//					 );
//		
//		// se valido è true deve fare anche SALVA ? -- probabilmente il salvataggio viene fatto come operazione preliminare alla validazione
//		return valido;
//	} */


//  NO:   in pratica qua devo decidere se validare con i dati che mi passa il FE oppure con i dati nel DB, 
//  NO:   ma in quest'ultimo caso ho necessità di salvare prima di validare. Non so se sia giusto forzare il salvataggio prima di fare la validazione
//  SI:   per poter trasmettere un fascicolo "ON_MODIFY" devo poter validare senza salvare!!! Quindi la validazione devo farla solo con i dati che mi passa il FE 
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public ValidationBeanDto valida(final Long idFascicolo) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		//valido su ciò che è già persistente....
		final InformazioniDTO body = this.fascicoloService.datiCompleti(idFascicolo);
		
		final ValidationBeanDto validazioneBean=new ValidationBeanDto();
		// InformazioniDTO body = fascicoloService.datiCompleti(idFascicolo);
		
		if (!this.fascicoloService.validazione(body))
		{
			validazioneBean.setFascicoloError(Collections.singletonList(this.buildOldError(ErroriValidazioneBE.FASCICOLO)));
		}
		//errori.add(this.buildOldError(ErroriValidazioneBE.FASCICOLO));
		
		final List<ErroriValidazioneBE> erroriRichiedente = this.richiedenteService.validazione(body.getRichiedente(), body.getId());
		if(ListUtil.isNotEmpty(erroriRichiedente)) {
			validazioneBean.setRichiedenteError(erroriRichiedente.stream().map(oldErr->this.buildOldError(oldErr)).collect(Collectors.toList()));
			//errori.addAll(erroriRichiedente.stream().map(oldErr->this.buildOldError(oldErr)).collect(Collectors.toList()));
		}
		final List<ErroriValidazioneBE> erroriIntervento = 
				this.interventoService.validazione(body.getInterventoSelezionati(), body.getId(), body.getTipoProcedimento(),body.getInterventoDettaglio(),body.getDataCreazione(),body.getUfficio());
		if (ListUtil.isNotEmpty(erroriIntervento))
		{
			validazioneBean.setInterventoError(erroriIntervento.stream().map(oldErr->this.buildOldError(oldErr)).collect(Collectors.toList()));
			//errori.addAll(erroriIntervento.stream().map(oldErr->this.buildOldError(oldErr)).collect(Collectors.toList()));
		}
		final List<ErroriValidazioneBE> erroriLocalizzazione=this.localizzazioneService.validazione(body.getLocalizzazione(), body.getId(),body.getTipoSelezioneLocalizzazione());
		if(body.getTipoSelezioneLocalizzazione()==null) {
			erroriLocalizzazione.add(ErroriValidazioneBE.LOCALIZZAZIONE_TIPO_SELEZIONE_MANCANTE);
		}
		if (ListUtil.isNotEmpty(erroriLocalizzazione))
		{
			//errori.add(this.buildOldError(ErroriValidazioneBE.LOCALIZZAZIONE));
			validazioneBean.setLocalizzazioneError(erroriLocalizzazione.stream().map(oldErr->this.buildOldError(oldErr)).collect(Collectors.toList()));
		}
		final List<SelectOptionDto> erroriAllegati = this.allegatoService.validazione(body.getId(), body.getTipoProcedimento());
		if (ListUtil.isNotEmpty(erroriAllegati)) {
			//validazioneBean.setAllegatiError(Collections.singletonList(this.buildOldError(ErroriValidazioneBE.ALLEGATI)));
			validazioneBean.setAllegatiError(erroriAllegati);
		}
		final List<SelectOptionDto> erroriProvvedimento = this.provvedimentoService.validazione(body,body.getId(), body.getTipoProcedimento());
		if (ListUtil.isNotEmpty(erroriProvvedimento))
		{
			validazioneBean.setProvvedimentoError(erroriProvvedimento);
			//errori.add(this.buildOldError(ErroriValidazioneBE.PROVVEDIMENTO));
		}
//		if (validazioneBean.isValid() /*&& body.getStato()==StatoFascicolo.WORKING*/) // se è valido faccio anche un salvataggio
//			this.salva(body);											 	 // SOLO se il fascicolo è in WORKING
		
		//return errori.isEmpty() ? null : errori;
		return validazioneBean;
	}
	
	private SelectOptionDto buildOldError(final ErroriValidazioneBE item) {
		return new SelectOptionDto(item.name(), this.messageSource.getMessage("validazioneFascicolo."+item.name(), null, Locale.getDefault()));
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public List<TipologicaDestinatarioDTO> getDestinatariTrasmissione(final Long idFascicolo, final List<TipologicaDestinatarioDTO> ulterioriDestinatari, final boolean isBatchUser) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		final FascicoloDTO fascicolo = this.fascicoloService.find(idFascicolo);
		final List<Long> idEntiComuni = this.localizzazioneInterventoService.selectEffettivo(idFascicolo);
		final Set<Integer> entiComuni = new HashSet<>();
		idEntiComuni.forEach(elem->{
			entiComuni.add(elem.intValue());
		});
		
		// 1) cerco i destinatari riferiti ai Parchi
		final List<TipologicaDestinatarioDTO> listaDestinatariParchi = new ArrayList<>();

		final ParchiPaesaggiImmobiliSearch search = new ParchiPaesaggiImmobiliSearch();
		search.setPraticaId(idFascicolo);
		search.setTipoVincolo(ParchiPaesaggiImmobiliSigla.P.name());
		search.setSelezionato("S");
		final List<ParchiPaesaggiImmobiliDTO> listaParchi = this.parchiPaesaggiImmobiliService.search(search).getList();

		if (!listaParchi.isEmpty()) {
			final List<String> codici = new ArrayList<String>();
			listaParchi.forEach(elem -> {
				codici.add(elem.getCodice());
			});
			final List<TipologicaDTO> listaEmailParchi = this.anagraficaService.emailParchi(codici);
			listaEmailParchi.forEach(elem->{
				listaDestinatariParchi.add(new TipologicaDestinatarioDTO(elem, TipoDestinatario.TO));	// da verificare se è TO o CC
			});
		}

		// 2) cerco i destinatari riferiti alle Soprintendenze
		List<TipologicaDestinatarioDTO> listaDestinatariSoprintendenze = new ArrayList<>();
		final boolean inviaMail = this.tipoProcedimentoService.find(fascicolo.getTipoProcedimento().name()).isInviaMail();
		if (inviaMail==true) {
			listaDestinatariSoprintendenze = this.destinatarioService.findDestinatariForSoprintendenze(entiComuni, TipoDestinatario.TO);	// da verificare se è TO o CC
		}
		
		// 3) cerco i destinatari di Default
		final List<TipologicaDestinatarioDTO> listaDestinatariDefault = new ArrayList<>();
		
		final boolean inModifica = fascicolo.getStato()==StatoFascicolo.ON_MODIFY;
		
		final TemplateDestinatarioSearch searchTD = new TemplateDestinatarioSearch();
		if(inModifica==true)
			searchTD.setCodiceTemplate(TipoTemplate.INVIO_RITRASMISSIONE);
		else
			searchTD.setCodiceTemplate(TipoTemplate.INVIO_TRASMISSIONE);
		final List<DestinatarioTemplateDTO> destinatariDefault = this.templateDestinatarioService.search(searchTD).getList();
		
		if (destinatariDefault!=null) {
			destinatariDefault.forEach(destinatario-> {
				listaDestinatariDefault.add(new DestinatarioTemplateDTO(destinatario)); 
			});
		}
		
		// 4) cerco tutti i destinatari della precedente Trasmissione (se sto RI-trasmettendo)
		final List<TipologicaDestinatarioDTO> listaDestinatariTrasmissione = new ArrayList<>();
		
		if (fascicolo.getStato()==StatoFascicolo.ON_MODIFY) {
			// recupero l'allegato relativo alla (più recente) trasmissione del fascicolo
			final long idCorrispondenza = this.getIdCorrispondenzaTrasmissione(idFascicolo);
	
			// verifico che le informazioni della corrispondenza siano corrette
			final CorrispondenzaSearch searchC = new CorrispondenzaSearch();
			searchC.setId(idCorrispondenza);
			searchC.setIdFascicolo(idFascicolo);
			searchC.setBozza(false);
			searchC.setComunicazione(false);
			searchC.setMittente(fascicolo.getUsernameUtenteTrasmissione());
			if (this.corrispondenzaService.count(searchC)!=1) {
				log.error("Errore. La corrispondenza associata alla Ricevuta di trasmissione del fascicolo non contiene i dati attesi");
				throw new Exception("Errore. La corrispondenza associata alla Ricevuta di trasmissione del fascicolo non contiene i dati attesi");
			}
			
			final DestinatarioSearch searchD = new DestinatarioSearch();
			searchD.setIdCorrispondenza(idCorrispondenza);
			final List<DestinatarioDTO> destinatari = this.destinatarioService.search(searchD).getList();
			
			destinatari.forEach(destinatario-> {
				listaDestinatariTrasmissione.add(new TipologicaDestinatarioDTO(destinatario)); 
			});
		}
		
		// 5) aggiungo il Funzionario che sta trasmettendo la pratica
		String denominazioneFunzionario = null;
		String emailFunzionario = null;
		if (SecurityUtil.getUserDetail() != null && this.userUtil.getGruppo()==Gruppi.ADMIN) {	// se sto Annullando una pratica già trasmessa
			denominazioneFunzionario = fascicolo.getDenominazioneUtenteTrasmissione();
			emailFunzionario = fascicolo.getEmailUtenteTrasmissione();
		}
		//else if (userUtil.getGruppo()==Gruppi.ED_ ) {// se sto (ri)trasmettendo
		else {
			try {
				if(!isBatchUser) {
					this.gruppiRuoliService.checkAbilitazioneForTrasmissione();
				}
				if(isBatchUser) {
					denominazioneFunzionario = fascicolo.getDenominazioneUtenteTrasmissione();
					emailFunzionario = fascicolo.getEmailUtenteTrasmissione();
				}else {
					denominazioneFunzionario = SecurityUtil.getUserDetail() != null ?  SecurityUtil.getUserDetail().getFirstName()+" "+SecurityUtil.getUserDetail().getLastName() : this.props.getBatchUsr();
					emailFunzionario = SecurityUtil.getUserDetail() != null ? SecurityUtil.getUserDetail().getEmailAddress() : "";
				}

			}catch(final CustomOperationToAdviceException e) {
				throw new CustomOperationToAdviceException("Errore! Gruppo '"+this.userUtil.getGruppo().getTextValue()+"' non autorizzato ad eseguire l'operazione");
			}
		}
//		else {
//			throw new CustomOperationToAdviceException("Errore! Gruppo '"+userUtil.getGruppo().getTextValue()+"' non autorizzato ad eseguire l'operazione");
//		}
		final TipologicaDestinatarioDTO funzionario = new TipologicaDestinatarioDTO(emailFunzionario, denominazioneFunzionario, TipoDestinatario.CC);
		
		// 6) aggiungo il Richiedente
		final RichiedenteDTO richiedenteTab = this.richiedenteService.datiRichiedente(fascicolo.getId());
		TipologicaDestinatarioDTO richiedente = null;
		 	  if (StringUtil.isNotEmpty(richiedenteTab.getPec  ()))
		 		 richiedente = new TipologicaDestinatarioDTO(richiedenteTab.getPec (),  true, richiedenteTab.getNome()+" "+richiedenteTab.getCognome(), TipoDestinatario.CC);
		 else if (StringUtil.isNotEmpty(richiedenteTab.getEmail()))
			 	 richiedente = new TipologicaDestinatarioDTO(richiedenteTab.getPec (), false, richiedenteTab.getNome()+" "+richiedenteTab.getCognome(), TipoDestinatario.CC);
		
		// 7) aggiungo l'Ente Delegato che sta trasmettendo la pratica 			
		List<TipologicaDestinatarioDTO> listaDestinatariEnteDelegato = new ArrayList<>();
		listaDestinatariEnteDelegato = this.destinatarioService.findDestinatariForEnteDelegato(fascicolo.getOrgCreazione(), entiComuni, TipoDestinatario.CC);	// da verificare se è TO o CC
		/* molto probabilmente non va fatto così, dovrò usare la rubrica dell'ente!
		RubricaIstituzionaleSearch searchRIente = new RubricaIstituzionaleSearch();
		searchRIente.setEnteId(commonService.findEnteByIdOrganizzazione(userUtil.getIntegerId()).getId());
		RubricaIstituzionaleDTO enteDelegatoInfo = rubricaIstituzionaleService.search(searchRIente).getList().get(0);
		TipologicaDestinatarioDTO enteDelegato = null;
		if (StringUtil.isNotEmpty(enteDelegatoInfo.getPec()))
			enteDelegato = new TipologicaDestinatarioDTO(enteDelegatoInfo.getPec (),  true, enteDelegatoInfo.getNome(), TipoDestinatario.CC);
		else
			enteDelegato = new TipologicaDestinatarioDTO(enteDelegatoInfo.getMail(), false, enteDelegatoInfo.getNome(), TipoDestinatario.CC);
		*/
		
		// 8) aggiungo gli Enti (cioè i Comuni) nei quali è localizzato l'intervento
		List<TipologicaDestinatarioDTO> listaDestinatariComuni = new ArrayList<>();
		listaDestinatariComuni = this.destinatarioService.findDestinatariForComuni(entiComuni, TipoDestinatario.TO);	// da verificare se è TO o CC
		/* RubricaIstituzionaleSearch searchRIcomuni = new RubricaIstituzionaleSearch();
		for (Integer idEnte : entiComuni) {
			searchRIcomuni.setEnteId(idEnte.intValue());
			List<RubricaIstituzionaleDTO> dests = rubricaIstituzionaleService.search(searchRIcomuni).getList();
			if(dests.size()>0) {
				RubricaIstituzionaleDTO enteInfo = dests.get(0);
				if (StringUtil.isNotEmpty(enteInfo.getPec()))
					listaComuni.add(new TipologicaDestinatarioDTO(enteInfo.getPec (),  true, enteInfo.getNome(), TipoDestinatario.TO));
				else
					listaComuni.add(new TipologicaDestinatarioDTO(enteInfo.getMail(), false, enteInfo.getNome(), TipoDestinatario.TO));	
			}
		}	*/
		
		// 9) aggiungo le Province interessate
		List<TipologicaDestinatarioDTO> listaDestinatariProvince = new ArrayList<>();
		listaDestinatariProvince = this.destinatarioService.findDestinatariForProvince(entiComuni, TipoDestinatario.CC);	// da verificare se è TO o CC
		/* List<Integer> province = commonService.selectProvinceByIdComuni(entiComuni);
		RubricaIstituzionaleSearch searchRIprovince = new RubricaIstituzionaleSearch();
		for (int provincia : province) {
			searchRIprovince.setEnteId(provincia);
			List<RubricaIstituzionaleDTO> dests = rubricaIstituzionaleService.search(searchRIprovince).getList();
			if(dests.size()>0) {
			RubricaIstituzionaleDTO enteInfo = dests.get(0);
			if (StringUtil.isNotEmpty(enteInfo.getPec()))
				listaProvince.add(new TipologicaDestinatarioDTO(enteInfo.getPec (),  true, enteInfo.getNome(), TipoDestinatario.CC));
			else
				listaProvince.add(new TipologicaDestinatarioDTO(enteInfo.getMail(), false, enteInfo.getNome(), TipoDestinatario.CC));
			}
		}	*/
		
		// 10) aggiungo Regione Puglia
		List<TipologicaDestinatarioDTO> listaDestinatariRegione = new ArrayList<>();
		listaDestinatariRegione = this.destinatarioService.findDestinatariForRegione(entiComuni, TipoDestinatario.CC);	// da verificare se è TO o CC
		
		// 11) unisco le 11 liste eliminando i doppioni delle email
		return VarieUtils.eliminaDuplicati(listaDestinatariRegione, listaDestinatariParchi, listaDestinatariSoprintendenze, listaDestinatariDefault,
										   Arrays.asList(funzionario), Arrays.asList(richiedente), listaDestinatariEnteDelegato, 
										   listaDestinatariComuni, listaDestinatariProvince, listaDestinatariTrasmissione, ulterioriDestinatari);
	}


	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public long getIdCorrispondenzaTrasmissione(final Long idFascicolo) throws Exception {
		final Long idAllegato = this.allegatoService.findRicevutaTrasmissione(idFascicolo).getCodice();
		
		// recupero la corrispondenza relativa alla (più recente) trasmissione del fascicolo
		final List<AllegatoCorrispondenzaDTO> listaAC = this.allegatoCorrispondenzaService.search(new AllegatoCorrispondenzaSearch(idAllegato, null)).getList();
		if (listaAC==null || listaAC.size()!=1) {
			log.error("Errore. Trovate "+listaAC.size()+" corrispondenze associate alla Ricevuta di trasmissione del fascicolo con id="+idFascicolo);
			throw new Exception("Errore. Trovate "+listaAC.size()+" corrispondenze associate alla Ricevuta di trasmissione del fascicolo con id="+idFascicolo);
		}
		final long idCorrispondenza = listaAC.get(0).getIdCorrispondenza();
		return idCorrispondenza;
	}
	

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public LineaTemporaleDTO lineaTemporale(final long idFascicolo) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		return this.fascicoloService.lineaTemporale(idFascicolo);
	}

	
//	private String urlDownloadRicevutaTrasmissione(final Long idPratica, final Long idCorrispondenza, final Long idAllegato) throws Exception {
////		String baseUrl = configuration.getString(CorrispondenzaService.KEY_URL_DOWNLOAD_PREFIX);
//		String baseUrl = commonService.getConfigurationValue(CorrispondenzaService.KEY_URL_DOWNLOAD_PREFIX,this.props.getCodiceApplicazione());
//		String url = baseUrl + "/public/"+CorrispondenzaService.PUBLIC_DOWNLOAD_DOC_TRASM;
//			return url + "?idPratica="+idPratica+"&idCorrispondenza="+idCorrispondenza + "&idAllegato="+idAllegato;
//		
//	}
	
	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public Boolean trasmetti(final long idFascicolo, final List<TipologicaDestinatarioDTO> ulterioriDestinatari) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		final InformazioniDTO infoDTO = this.fascicoloService.datiCompleti(idFascicolo);
		final boolean inModifica = (infoDTO.getStato()==StatoFascicolo.ON_MODIFY);
		final boolean inWorking = (infoDTO.getStato()==StatoFascicolo.WORKING);
		if(!inModifica && !inWorking) {
			throw new CustomOperationToAdviceException("Stato fascicolo non valido per la Trasmissione!");
		}
		if(inModifica && !infoDTO.getUsernameUtenteTrasmissione().equals(SecurityUtil.getUsername())) {
			//check se utente corrente è lo stesso di colui che ha effettuato la trasmissione
			throw new CustomOperationToAdviceException("L'utente corrente non è lo stesso che ha effettuato la trasmissione, impossibile procedere !!!");
		}
		final ValidationBeanDto errorBean = this.valida(idFascicolo);
		if (errorBean!=null && !errorBean.isValid()) {
			throw new CustomOperationToAdviceException("Impossibile effettuare la trasmissione.\nIl fascicolo non ha superato la validazione!");
		}
		//check sui destinatari ulteriori aggiunti manualmente
		if(ListUtil.isNotEmpty(ulterioriDestinatari)) {
			try {
				for(TipologicaDestinatarioDTO dest:ulterioriDestinatari) {
					this.validator.doValidate(dest);
				}
			}catch(ValidationFailureException e) {
				throw new CustomOperationToAdviceException("Errore durante la validazione dei destinatari aggiuntivi: "+e.getMessage());
			}
		}
		final List<TipologicaDestinatarioDTO> destinatari = this.getDestinatariTrasmissione(idFascicolo, ulterioriDestinatari, false);
		
		final TrasmissioneBean bean = new TrasmissioneBean();
		bean.setDestinatari(destinatari);
		bean.setInfoDTO(infoDTO);

		// modifico lo stato del Fascicolo
		this.fascicoloService.cambiaStato(idFascicolo, StatoFascicolo.TRANSMITTED, null);
		this.fascicoloService.setStatoInTrasmissione(idFascicolo, true);
		this.fascicoloService.aggiornaJsonInfo(idFascicolo, this.creaJsonInfo(infoDTO));
		

		this.queueService.setApplicationName(this.applicationName.toUpperCase());
		this.queueService.insertNewQueue(ProtocollazioneFascicoloScheduler.ID, 3600, bean);
		return true;
	}

	
/*	private PGobject creaJsonInfo(long idFascicolo) throws Exception {	// OLD 
		
		InformazioniDTO info = fascicoloService.datiCompleti(idFascicolo);
		
		Set<String> codiciComuni = new HashSet<>(gruppiRuoliService.comuniForGruppoUtenteLoggato());

		// creo una lista con tutte le opzioni selezionabili
		List<TipologicaDTO> listaSelezionabili = new ArrayList<TipologicaDTO>();
		listaSelezionabili.addAll(anagraficaService.getListaBPparchiRiserve(codiciComuni));
		listaSelezionabili.addAll(anagraficaService.getListaUcpPaesaggioRurale(codiciComuni));
		listaSelezionabili.addAll(anagraficaService.getListaBPimmobiliAree(codiciComuni));
		listaSelezionabili.forEach(e->{
			e.setLabel("false");
		});
		
		// creo una lista con tutte le opzioni selezionate
		List<TipologicaDTO> listaSelezionati = new ArrayList<TipologicaDTO>();
		listaSelezionati.addAll(info.getLocalizzazione().getParchi());
		listaSelezionati.addAll(info.getLocalizzazione().getPaesaggiRurali());
		listaSelezionati.addAll(info.getLocalizzazione().getImmobiliAreeInteresse());
		
		// setto a "true" gli elementi della lista corrispondenti alle opzioni selezionate
		listaSelezionati.forEach(selezionato->{
			for (int i = 0; i < listaSelezionabili.size(); i++) {
				if (selezionato.getCodice().equals(listaSelezionabili.get(i).getCodice())) {
					listaSelezionabili.get(i).setLabel("true");
				}
			}
		});
		
		List<TipologicaDTO> lista = listaSelezionabili;
		int countTrue=0;
		// verifico che il numero delle opzioni selezionate coincida con il numero degli elementi a "true"
		for (TipologicaDTO elemento : lista) {
			if (elemento.getLabel().equals("true")) countTrue++;
		};
		if (listaSelezionati.size()!=countTrue) {
			log.error("Errore in Localizzazione! E' stata selezionata un'opzione non consentita");
			throw new Exception("Errore in Localizzazione! E' stata selezionata un'opzione non consentita");
		}
		
		// suddivido la lista completa in 3 liste
		List<TipologicaDTO> listaParchi = new ArrayList<TipologicaDTO>();
		List<TipologicaDTO> listaPaesaggiRurali = new ArrayList<TipologicaDTO>();
		List<TipologicaDTO> listaImmobiliAreeInteressePubblico = new ArrayList<TipologicaDTO>();
		
		if (lista!=null && !lista.isEmpty()) {
			for (TipologicaDTO elemento : lista) {
					 if (elemento.getCodice().toUpperCase().startsWith("PR")) {
							listaParchi.add(new TipologicaDTO(elemento.getCodice(), null)); 
				}
				else if (elemento.getCodice().toUpperCase().startsWith("PAERUR")) {
							listaPaesaggiRurali.add(new TipologicaDTO(elemento.getCodice(), null));
				}
				else if (elemento.getCodice().toUpperCase().startsWith("PAE")) {
							listaImmobiliAreeInteressePubblico.add(new TipologicaDTO(elemento.getCodice(), null));
				}
				else {
						log.error("Elemento di Parchi/Paesaggi/Aree non identificato!");
						throw new Exception("Elemento di Parchi/Paesaggi/Aree non identificato!");
				}
			}
		}
		// setto le nuove liste all'interno di InformazioniDTO
		info.getLocalizzazione().setParchi(listaParchi);
		info.getLocalizzazione().setPaesaggiRurali(listaPaesaggiRurali);
		info.getLocalizzazione().setImmobiliAreeInteresse(listaImmobiliAreeInteressePubblico);
		
		// creo il json
		PGobject json = new PGobject();
		json.setType("json");
		json.setValue(info.toJson().value());
		
		return json;
	}	*/
	
	@Override
	public PGobject creaJsonInfo(final InformazioniDTO info) throws SQLException {
		final PGobject pgObj = new PGobject();
		pgObj.setType("json");
		pgObj.setValue(info.toJson());
		return pgObj;
	}
	
//	private PGobject creaJsonInfo(final long idFascicolo) throws Exception {
//		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
//		
//		InformazioniDTO info = fascicoloService.datiCompleti(idFascicolo);
//	/*	
//	 	// inserisco le informazioni mancanti
//		info.setId(idFascicolo);
//		info.setStato(StatoFascicolo.TRANSMITTED);
//		info.setDataTrasmissione(new Date());
//		info.setUsernameUtenteTrasmissione(SecurityUtil.getUsername());
//		info.setDenominazioneUtenteTrasmissione(userUtil.hasUserLogged() ?
//												userUtil.getMyProfile().getNome().concat(" ").concat(userUtil.getMyProfile().getCognome()) :
//												props.getBatchUsr());
//		info.setEmailUtenteTrasmissione(userUtil.hasUserLogged() ? userUtil.getMyProfile().getEmail() : null);
//		info.setEsitoVerifica(EsitoVerifica.NOT_SAMPLED); //per adesso, poi se è attivo il campionamento....
//		info.setStatoPrecedente(null);
//		info.setEsitoVerificaPrecedente(null);
//		info.setModificabileFino(null);
//		info.setInfoComplete(null);
//	*/
////		PGobject pgObj = new PGobject();
////		pgObj.setType("json");
////		pgObj.setValue(info.toJson());
//		return this.creaJsonInfo(info);
//	}
	

}