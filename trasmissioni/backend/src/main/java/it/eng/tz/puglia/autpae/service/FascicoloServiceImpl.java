package it.eng.tz.puglia.autpae.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.postgresql.util.PGobject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.aet.paesaggio.bean.MyOrganizzazioniBean;
import it.eng.tz.puglia.autpae.config.ApplicationProperties;
import it.eng.tz.puglia.autpae.controller.exception.CustomOperationToAdviceException;
import it.eng.tz.puglia.autpae.dto.AcceleratoriFascicoloDTO;
import it.eng.tz.puglia.autpae.dto.AllegatoCustomDTO;
import it.eng.tz.puglia.autpae.dto.FascicoloTabDTO;
import it.eng.tz.puglia.autpae.dto.InformazioniDTO;
import it.eng.tz.puglia.autpae.dto.InterventoTabDTO;
import it.eng.tz.puglia.autpae.dto.LineaTemporaleDTO;
import it.eng.tz.puglia.autpae.dto.LocalizzazioneTabDTO;
import it.eng.tz.puglia.autpae.dto.ProvvedimentoTabDTO;
import it.eng.tz.puglia.autpae.dto.TipologicaDTO;
import it.eng.tz.puglia.autpae.dto.TipologicaNumeriDTO;
import it.eng.tz.puglia.autpae.entity.ComuniCompetenzaDTO;
import it.eng.tz.puglia.autpae.entity.ConfigurazionePermessiDTO;
import it.eng.tz.puglia.autpae.entity.FascicoloBaseDto;
import it.eng.tz.puglia.autpae.entity.FascicoloDTO;
import it.eng.tz.puglia.autpae.entity.FascicoloPublicDto;
import it.eng.tz.puglia.autpae.entity.LocalizzazioneInterventoDTO;
import it.eng.tz.puglia.autpae.entity.ModificheTabDTO;
import it.eng.tz.puglia.autpae.entity.ParchiPaesaggiImmobiliDTO;
import it.eng.tz.puglia.autpae.entity.ParticelleCatastaliDTO;
import it.eng.tz.puglia.autpae.entity.RichiedenteDTO;
import it.eng.tz.puglia.autpae.entity.TipoProcedimentoDTO;
import it.eng.tz.puglia.autpae.enumeratori.Applicativo;
import it.eng.tz.puglia.autpae.enumeratori.EsitoVerifica;
import it.eng.tz.puglia.autpae.enumeratori.Gruppi;
import it.eng.tz.puglia.autpae.enumeratori.NomiTab;
import it.eng.tz.puglia.autpae.enumeratori.Ruoli;
import it.eng.tz.puglia.autpae.enumeratori.StatoFascicolo;
import it.eng.tz.puglia.autpae.enumeratori.TipoProcedimento;
import it.eng.tz.puglia.autpae.generated.orm.repository.DocCaricatiSportelloRepository;
import it.eng.tz.puglia.autpae.repository.FascicoloFullRepository;
import it.eng.tz.puglia.autpae.repository.FascicoloTipiProcedimentoRepository;
import it.eng.tz.puglia.autpae.repository.ProcedimentiAmbientaliRepository;
import it.eng.tz.puglia.autpae.search.ComuniCompetenzaSearch;
import it.eng.tz.puglia.autpae.search.FascicoloSearch;
import it.eng.tz.puglia.autpae.search.ParchiPaesaggiImmobiliSearch;
import it.eng.tz.puglia.autpae.search.ParticelleCatastaliSearch;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoService;
import it.eng.tz.puglia.autpae.service.interfacce.AzioniService;
import it.eng.tz.puglia.autpae.service.interfacce.ClientSportelloService;
import it.eng.tz.puglia.autpae.service.interfacce.ComuniCompetenzaService;
import it.eng.tz.puglia.autpae.service.interfacce.ConfigurazionePermessiService;
import it.eng.tz.puglia.autpae.service.interfacce.FascicoloService;
import it.eng.tz.puglia.autpae.service.interfacce.GruppiRuoliService;
import it.eng.tz.puglia.autpae.service.interfacce.InterventoService;
import it.eng.tz.puglia.autpae.service.interfacce.LocalizzazioneInterventoService;
import it.eng.tz.puglia.autpae.service.interfacce.LocalizzazioneService;
import it.eng.tz.puglia.autpae.service.interfacce.ModificheTabService;
import it.eng.tz.puglia.autpae.service.interfacce.MyCompetenzaService;
import it.eng.tz.puglia.autpae.service.interfacce.ParchiPaesaggiImmobiliService;
import it.eng.tz.puglia.autpae.service.interfacce.ParticelleCatastaliService;
import it.eng.tz.puglia.autpae.service.interfacce.ProvvedimentoService;
import it.eng.tz.puglia.autpae.service.interfacce.RichiedenteService;
import it.eng.tz.puglia.autpae.service.interfacce.TipoProcedimentoService;
import it.eng.tz.puglia.autpae.utility.validator.CapValidator;
import it.eng.tz.puglia.autpae.utility.validator.CodiceFiscaleValidator;
import it.eng.tz.puglia.autpae.utility.validator.PartitaIvaValidator;
import it.eng.tz.puglia.autpae.utility.validator.TelefonoValidator;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.security.util.SecurityUtil;
import it.eng.tz.puglia.servizi_esterni.profileManager.dto.response.EnteResponseBean;
import it.eng.tz.puglia.servizi_esterni.profileManager.feign.UserUtil;
import it.eng.tz.puglia.servizi_esterni.remote.service.interfacce.AnagraficaService;
import it.eng.tz.puglia.servizi_esterni.remote.service.interfacce.CommonService;
import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.util.string.StringUtil;

@Service
public class FascicoloServiceImpl implements FascicoloService {

	private static final Logger log = LoggerFactory.getLogger(FascicoloServiceImpl.class);

	@Autowired
	private FascicoloFullRepository repository;

	@Autowired
	private FascicoloTipiProcedimentoRepository fascicoloProcedimentiAttiviRepo;

	@Autowired
	DocCaricatiSportelloRepository docSportelloDao;
	
	@Autowired
	private InterventoService interventoService;
	@Autowired
	private RichiedenteService richiedenteService;
	@Autowired
	private LocalizzazioneService localizzazioneService;
	@Autowired
	private LocalizzazioneInterventoService localizzazioneInterventoService;
	@Autowired
	private ParticelleCatastaliService particelleCatastaliService;
	@Autowired
	private ModificheTabService modificheTabService;
	@Autowired
	private GruppiRuoliService gruppiRuoliService;
	@Autowired
	private ProvvedimentoService provvedimentoService;
	@Autowired
	private AllegatoService allegatiService;
	@Autowired
	private TipoProcedimentoService tipoProcedimentoService;
	@Autowired
	private AnagraficaService anagraficaService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private ParchiPaesaggiImmobiliService parchiPaesaggiImmobiliService;
	@Autowired
	private ComuniCompetenzaService comuniCompetenzaService;
	@Autowired
	private UserUtil userUtil;
	@Autowired
	private ApplicationProperties props;
	@Autowired
	private AzioniService azioniService;
	@Autowired
	private ConfigurazionePermessiService configurazionePermessiService;


	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = true)
	public long count(final FascicoloSearch filter) throws Exception {
		return repository.count(filter);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = true)
	public FascicoloDTO find(final Long pk) throws Exception {
		return repository.find(pk);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = false)
	public Long insert(final FascicoloDTO entity) throws Exception {
		return repository.insert(entity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = false)
	public int update(final FascicoloDTO entity) throws Exception {
		return repository.update(entity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = false)
	public int updateForMigration(final FascicoloDTO entity) throws Exception {
		return repository.updateForMigration(entity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = true)
	public PaginatedList<FascicoloDTO> search(final FascicoloSearch filter) throws Exception {
		return repository.search(filter);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = false)
	public int cambiaStato(final long idFascicolo, final StatoFascicolo stato, final Boolean verifica)
			throws Exception {
		return repository.cambiaStato(idFascicolo, stato, verifica);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = false)
	public int setStatoInTrasmissione(final long idFascicolo, final boolean flag) throws Exception {
		return repository.setStatoInTrasmissione(idFascicolo, flag);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = false)
	public int aggiornaJsonInfo(final long idFascicolo, final PGobject jsonInfo) throws Exception {
		return repository.aggiornaJsonInfo(idFascicolo, jsonInfo);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = false)
	public int consentiModifica(final long idFascicolo, final Date modificabileFino) throws Exception {
		return repository.consentiModifica(idFascicolo, modificabileFino);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = false)
	public int bloccaModifica(final long idFascicolo) throws Exception {
		return repository.bloccaModifica(idFascicolo);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = false)
	public int bloccaModificheScadute() throws Exception {
		return repository.bloccaModificheScadute();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = false)
	public int cancella(final long idFascicolo, final boolean deleted) throws Exception {
		return repository.cancella(idFascicolo, deleted);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = true)
	public Date getModificabileFino(final long id) throws Exception {
		return repository.getModificabileFino(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = true)
	public List<String> autocompleteCodice(final String codice, final Integer limit) throws Exception {
		return repository.autocompleteCodice(codice, limit);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = true)
	public List<String> autocompleteCodiceAnnMod(final String codice, final Integer limit, Boolean isAnnullabile,
			Boolean isModificabile, Boolean isInModifica) throws Exception {
		return repository.autocompleteCodiceAnnMod(codice, limit, isAnnullabile, isModificabile, isInModifica);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = false)
	public int aggiornaEsito(final Long idFascicolo, final EsitoVerifica esito, final Date data, final String numero)
			throws Exception {
		return repository.aggiornaEsito(idFascicolo, esito, data, numero);
	}

	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = true)
	public void prepareForSearch(final FascicoloSearch filter) throws Exception {
		String codiceGruppo = null;
		if (!filter.isRicercaPubblica()) {
			codiceGruppo = userUtil.getCodice_GruppoIdRuolo();
		}
		this.prepareForSearch(filter, codiceGruppo);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = true)
	public void prepareForSearch(final FascicoloSearch filter, final String codiceGruppo) throws Exception {
		doPrepareForSearch(filter, codiceGruppo);
	}

	private void doPrepareForSearch(final FascicoloSearch filter, final String codiceGruppo) throws Exception {
		// qui setto tutti i parametri di visibilità legati all'utente
		List<Integer> entiDiCompetenza = null;
		String organizzazioneLoggato = null;
		Ruoli ruolo = null;
		Gruppi gruppo = null;
		Integer idOrganizzazioneLoggato = null;
		if (!filter.isRicercaPubblica()) {
			organizzazioneLoggato = userUtil.getGruppo_Id(codiceGruppo);
			if (!organizzazioneLoggato.toUpperCase().equals(Gruppi.ADMIN.name())) {
				idOrganizzazioneLoggato = userUtil.getIntegerId(codiceGruppo);
				entiDiCompetenza = gruppiRuoliService.entiForGruppo(codiceGruppo);
				ruolo = userUtil.getRuolo(codiceGruppo);
				gruppo = userUtil.getGruppo(codiceGruppo);
			}
		}
		filter.setRuolo(ruolo);
		filter.setGruppo(gruppo);
		filter.setEntiDiCompetenza(entiDiCompetenza);
		filter.setOrganizzazioneLoggato(organizzazioneLoggato);
		filter.setIdOrganizzazioneLoggato(idOrganizzazioneLoggato);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = true)
	public MyOrganizzazioniBean organizzazioniEComunidiCompetenza() throws Exception {
		MyOrganizzazioniBean ret = new MyOrganizzazioniBean();
		Map<String, List<Integer>> mapOut = new HashMap<String, List<Integer>>();
		ret.setCodiciGruppo(new ArrayList<String>());
		FascicoloSearch filter = new FascicoloSearch();
		List<Gruppi> etiSbap = List.of(Gruppi.ETI_, Gruppi.SBAP_);
		for (EnteResponseBean ente : userUtil.getMyEnti()) {
			filter.setGruppo(null);
			filter.setEntiDiCompetenza(null);
			filter.setIdOrganizzazioneLoggato(null);
			this.doPrepareForSearch(filter, ente.getCodiceGruppo());
			if (filter.getIdOrganizzazioneLoggato() != null && filter.getGruppo() != null
					&& etiSbap.contains(filter.getGruppo())) {
				mapOut.put(filter.getIdOrganizzazioneLoggato().toString(), filter.getEntiDiCompetenza());
			} else if (filter.getIdOrganizzazioneLoggato() != null) {
				mapOut.put(filter.getIdOrganizzazioneLoggato().toString(), null);
			}
			ret.getCodiciGruppo().add(ente.getCodiceGruppo());
		}
		ret.setOrganizzazioniMap(mapOut);
		return ret;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = false)
	public FascicoloDTO creaDaApi(final FascicoloBaseDto fascicoloBase) throws Exception {
		FascicoloDTO fascicolo = new FascicoloDTO(fascicoloBase);
		String codiceGruppo = userUtil.getCodice_GruppoIdRuolo();
		if (StringUtils.isEmpty(codiceGruppo)) {
			throw new CustomOperationToAdviceException("Gruppo non valido per la creazione pratica");
		}
		fascicolo.setUfficio(codiceGruppo);
		fascicolo.setUsernameUtenteCreazione(SecurityUtil.getUserDetail().getUsername());
		return this.doCrea(fascicolo);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = false)
	public FascicoloDTO crea(final FascicoloDTO fascicolo) throws Exception {
		return doCrea(fascicolo);
	}

	private FascicoloDTO doCrea(final FascicoloDTO fascicolo) throws CustomOperationToAdviceException, Exception {
		log.trace("Entrato nel service: '" + this.getClass().getSimpleName() + "'. Metodo: '" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "'");

		// Controllo che il tipo procedimento selezionato non sia scaduto (non dovrebbe
		// mai accadere)
		if (!this.checkTipoProcedimentoAttivoOnCreate(fascicolo.getTipoProcedimento().name(), new Date())) {
			throw new CustomOperationToAdviceException(
					"Il tipo procedimento selezionato non è più abilitato ai nuovi inserimenti.");
		}
		if (!validazioneCreazione(fascicolo)) {
			log.error("ERRORE. Fascicolo incompleto");
			throw new CustomOperationToAdviceException("ERRORE. Fascicolo incompleto");
		}
		fascicolo.setDataCreazione(new Date());
		fascicolo.setCodice(commonService.generaCodiceFascicolo());
		fascicolo.setStato(StatoFascicolo.WORKING);

		if (tipoProcedimentoService.find(fascicolo.getTipoProcedimento().name()).isSanatoria() == false)
			fascicolo.setSanatoria(false);

		fascicolo.setVersFascicolo(modificheTabService.getLast(NomiTab.FASCICOLO).getHashcode());
		fascicolo.setVersRichiedente(modificheTabService.getLast(NomiTab.RICHIEDENTE).getHashcode());
		fascicolo.setVersIntervento(modificheTabService.getLast(NomiTab.INTERVENTO).getHashcode());
		fascicolo.setVersLocalizzazione(modificheTabService.getLast(NomiTab.LOCALIZZAZIONE).getHashcode());
		fascicolo.setVersAllegati(modificheTabService.getLast(NomiTab.ALLEGATI).getHashcode());
		fascicolo.setVersProvvedimento(modificheTabService.getLast(NomiTab.PROVVEDIMENTO).getHashcode());

		fascicolo.setId(this.insert(fascicolo));

		// Salvataggio di tutti i tipi procedimento presenti per avere una sorta di
		// "storico"
		// dei procedimenti validi al momento della creazione del fascicolo e poter
		// quindi
		// usare quegli stessi tipi procedimento in una eventuale fase successiva di
		// editing del fascicolo
		log.info(
				"Salvataggio dei tipi procedimento (in tabella fascicolo_tipi_procedimento) attuali associati al fascicolo con id {}",
				fascicolo.getId());
		int result = fascicoloProcedimentiAttiviRepo.insertOnCreateFascicolo(fascicolo.getId(),
				Applicativo.valueOf(props.getCodiceApplicazione().toUpperCase()));
		if (result == 0) {
			log.warn("**** Attenzione!!! Nessuna riga salvata in fascicolo_tipi_procedimento! ******");
		} else {
			log.info(
					"Salvataggio dei tipi procedimento attuali effettuato! Salvati {} tipi_procedimento in fascicolo_tipi_procedimento.",
					result);
		}

		comuniCompetenzaService.crea(fascicolo.getId());

		return fascicolo;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = true)
	public FascicoloTabDTO tabFascicolo() throws Exception {
		log.trace("Entrato nel service: '" + this.getClass().getSimpleName() + "'. Metodo: '" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "'");

		FascicoloTabDTO fascicoloTab = new FascicoloTabDTO();
		fascicoloTab.setTipiProcedimento(tipoProcedimentoService.select());
//		fascicoloTab.setUfficio(ufficio);    // la lista verrà popolata in base ai gruppi e ai ruoli dell'utente loggato (profilatore)
		return fascicoloTab;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, timeout = 60000, readOnly = true, rollbackFor = Exception.class)
	public FascicoloTabDTO datiFascicolo(final long id) throws Exception {
		log.trace("Entrato nel service: '" + this.getClass().getSimpleName() + "'. Metodo: '" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "'");

		FascicoloTabDTO fascicolo = new FascicoloTabDTO(repository.find(id));

		if (fascicolo.isDeleted())
			throw new CustomOperationToAdviceException("Pratica non trovata");

		if (fascicolo.getStato() == StatoFascicolo.WORKING && fascicolo.getDataUltimaModifica() != null) // TODO: forse
																											// dovrebbero
																											// essere
																											// aggiunti
																											// anche
																											// altri
																											// stati (in
																											// modifica...)
			this.controlloVersioniTab(fascicolo);

		if (tipoProcedimentoService.find(fascicolo.getTipoProcedimento().name()).isSanatoria() == false)
			fascicolo.setSanatoria(null);

		// ufficio a cui è settato il fascicolo
		String ufficio = fascicolo.getUfficio();
		int idOrganizzazione = userUtil.getIntegerId(ufficio);
		TipologicaDTO content = new TipologicaDTO();
		content.setCodice(ufficio);
		content.setLabel(commonService.getDenominazioneOrganizzazione(idOrganizzazione));
		fascicolo.setUffici(Collections.singletonList(content));

		return fascicolo;
	}

	@Transactional(propagation = Propagation.MANDATORY, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = true)
	private void controlloVersioniTab(final FascicoloTabDTO fascicolo) throws Exception {

		String avviso = "";

		ModificheTabDTO modFascicolo = modificheTabService.getLast(NomiTab.FASCICOLO);
		if (modFascicolo.getHashcode() != (fascicolo.getVersFascicolo()))
			avviso = avviso + (this.parsingAvvisi(
					modificheTabService.getInfoFromVersion(NomiTab.FASCICOLO, fascicolo.getVersFascicolo())));

		ModificheTabDTO modRichiedente = modificheTabService.getLast(NomiTab.RICHIEDENTE);
		if (modRichiedente.getHashcode() != (fascicolo.getVersRichiedente()))
			avviso = avviso + (this.parsingAvvisi(
					modificheTabService.getInfoFromVersion(NomiTab.RICHIEDENTE, fascicolo.getVersRichiedente())));

		ModificheTabDTO modIntervento = modificheTabService.getLast(NomiTab.INTERVENTO);
		if (modIntervento.getHashcode() != (fascicolo.getVersIntervento()))
			avviso = avviso + (this.parsingAvvisi(
					modificheTabService.getInfoFromVersion(NomiTab.INTERVENTO, fascicolo.getVersIntervento())));

		ModificheTabDTO modLocalizzazione = modificheTabService.getLast(NomiTab.LOCALIZZAZIONE);
		if (modLocalizzazione.getHashcode() != (fascicolo.getVersLocalizzazione()))
			avviso = avviso + (this.parsingAvvisi(
					modificheTabService.getInfoFromVersion(NomiTab.LOCALIZZAZIONE, fascicolo.getVersLocalizzazione())));

		ModificheTabDTO modAllegati = modificheTabService.getLast(NomiTab.ALLEGATI);
		if (modAllegati.getHashcode() != (fascicolo.getVersAllegati()))
			avviso = avviso + (this.parsingAvvisi(
					modificheTabService.getInfoFromVersion(NomiTab.ALLEGATI, fascicolo.getVersAllegati())));

		ModificheTabDTO modProvvedimento = modificheTabService.getLast(NomiTab.PROVVEDIMENTO);
		if (modProvvedimento.getHashcode() != (fascicolo.getVersProvvedimento()))
			avviso = avviso + (this.parsingAvvisi(
					modificheTabService.getInfoFromVersion(NomiTab.PROVVEDIMENTO, fascicolo.getVersProvvedimento())));

		List<ComuniCompetenzaDTO> comuniCompetenze = comuniCompetenzaService
				.search(new ComuniCompetenzaSearch(fascicolo.getId(), false)).getList();
		if (comuniCompetenze != null && !comuniCompetenze.isEmpty()) { // diversamente significa che non c'è mai stato
																		// nessun salvataggio
			Set<String> codiciComuniSalvati = new HashSet<>();
			comuniCompetenze.forEach(elem -> {
				codiciComuniSalvati.add(elem.getCodCat());
			});
			Set<String> codiciComuniAttuali = new HashSet<>(gruppiRuoliService.comuniForGruppoUtenteLoggato());
			Gruppi gruppo = userUtil.getGruppo();
			// se è admin ha tutti i comuni e l'avviso non ha senso
			if (!codiciComuniAttuali.equals(codiciComuniSalvati)) {
				if (gruppo != Gruppi.ADMIN) {
					avviso = avviso + (this
							.parsingAvvisi(Collections.singletonList("La lista dei comuni di competenza è cambiata.")));
				}
			} else {
				// creo un set di codici che contiene tutte le opzioni selezionabili
				// attualmente, MA SOLO PER I COMUNI SUI QUALI L'UTENTE HA GIA' OPERATO
				Set<Long> idComuni = new HashSet<>(); // della tabella ente
				try {
					ParticelleCatastaliSearch searchPC = new ParticelleCatastaliSearch();
					searchPC.setPraticaId(fascicolo.getId());
					List<ParticelleCatastaliDTO> pc = particelleCatastaliService.search(searchPC).getList();
					if (pc != null)
						pc.forEach(elem -> idComuni.add(elem.getComuneId()));

					ParchiPaesaggiImmobiliSearch searchPPI = new ParchiPaesaggiImmobiliSearch();
					searchPPI.setPraticaId(fascicolo.getId());
					searchPPI.setSelezionato("S");
					List<ParchiPaesaggiImmobiliDTO> ppi = parchiPaesaggiImmobiliService.search(searchPPI).getList();
					if (ppi != null)
						ppi.forEach(elem -> idComuni.add(elem.getComuneId()));

					List<LocalizzazioneInterventoDTO> li = localizzazioneInterventoService.select(fascicolo.getId());
					if (li != null)
						li.forEach(elem -> idComuni.add(elem.getComuneId()));

				} catch (Exception e) {
					throw e;
				}
				Set<String> codiciComuniOperativi = new HashSet<>();
				for (Long idComune : idComuni) {
					codiciComuniOperativi.add(commonService.getCodiceEnte(idComune.intValue()));
				}
				List<TipologicaDTO> listaAttuale = new ArrayList<TipologicaDTO>();
				listaAttuale.addAll(anagraficaService.getListaBPparchiRiserve(codiciComuniOperativi));
				if (!props.isPutt()) {
					listaAttuale.addAll(anagraficaService.getListaUcpPaesaggioRurale(codiciComuniOperativi));
				}
				if (!props.isPutt()) {
					listaAttuale.addAll(anagraficaService.getListaBPimmobiliAree(codiciComuniOperativi));
				}
				Set<String> codiciAttuali = new HashSet<>();
				listaAttuale.forEach(e -> {
					codiciAttuali.add(e.getCodice().toUpperCase());
				});

				// creo un set di codici che contiene tutte le opzioni selezionabili al momento
				// dell'ultimo salvataggio
				ParchiPaesaggiImmobiliSearch search = new ParchiPaesaggiImmobiliSearch();
				search.setPraticaId(fascicolo.getId());
				List<ParchiPaesaggiImmobiliDTO> listaSalvataggio = parchiPaesaggiImmobiliService.search(search)
						.getList();
				Set<String> codiciSalvati = new HashSet<>();
				listaSalvataggio.forEach(e -> {
					codiciSalvati.add(e.getCodice().toUpperCase());
				});

				if (!codiciAttuali.equals(codiciSalvati) && gruppo != Gruppi.ADMIN) {
					avviso = avviso + (this.parsingAvvisi(Collections.singletonList(
							"La lista di 'Parchi e Riserve', 'Paesaggi Rurali' e 'Immobili e Aree di notevole interesse pubblico' è cambiata.")));
				}
			}
		}
		if (avviso.equals(""))
			avviso = null;
		fascicolo.setAvviso(avviso);
	}

	private String parsingAvvisi(final List<String> infoList) {
		String delim = "\n-";
		String result = String.join(delim, infoList);
		return delim + result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = true)
	public InformazioniDTO informazioniComplete(final TipoProcedimento tipoProcedimento) throws Exception {
		log.trace("Entrato nel service: '" + this.getClass().getSimpleName() + "'. Metodo: '" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "'");

		FascicoloTabDTO fascicolo = this.tabFascicolo();
		InterventoTabDTO intervento = interventoService.tabIntervento(tipoProcedimento, fascicolo.getId());
		ArrayList<AllegatoCustomDTO> allegati = allegatiService.tabAllegati(tipoProcedimento);
		ProvvedimentoTabDTO provvedimento = new ProvvedimentoTabDTO();

		return new InformazioniDTO(fascicolo, null, intervento, null, null, allegati, provvedimento, null, false,
				false);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = true)
	public InformazioniDTO datiCompleti(final long idFascicolo) throws Exception {
		log.trace("Entrato nel service: '" + this.getClass().getSimpleName() + "'. Metodo: '" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "'");

		FascicoloTabDTO fascicolo = this.datiFascicolo(idFascicolo);

		if (fascicolo.getStato() == StatoFascicolo.ON_MODIFY
				&& !fascicolo.getUsernameUtenteTrasmissione().equals(SecurityUtil.getUsername())
				&& fascicolo.getModificabileFino().after(new Date())) {
			return fascicolo.getInfoComplete();
		}
		InterventoTabDTO intervento = interventoService.tabIntervento(fascicolo.getTipoProcedimento(),
				fascicolo.getId()); // in realtà non servirebbe qua (perchè fa parte di informazioniComplete).
									// Potrei passarlo 'null' ma Michele lo vuole
		List<Long> interventoSelezionati = interventoService.datiIntervento(idFascicolo);
		ArrayList<AllegatoCustomDTO> allegati = allegatiService.datiAllegati(idFascicolo);
		ProvvedimentoTabDTO provvedimento = provvedimentoService.datiProvvedimento(idFascicolo);
		RichiedenteDTO richiedente = richiedenteService.datiRichiedente(idFascicolo);
		LocalizzazioneTabDTO localizzazione = localizzazioneService.datiLocalizzazione(idFascicolo);
		List<ComuniCompetenzaDTO> comuniCompetenza = comuniCompetenzaService
				.search(new ComuniCompetenzaSearch(idFascicolo, true)).getList();
		ConfigurazionePermessiDTO permessi = null;
		try {
			permessi = configurazionePermessiService.find(userUtil.getGruppo_Id());
		} catch (Exception e) {
			log.error("Errore nel retrieve della configurazione permessi");
		}
		if (permessi == null)
			permessi = new ConfigurazionePermessiDTO();

		return new InformazioniDTO(fascicolo, richiedente, intervento, interventoSelezionati, localizzazione, allegati,
				provvedimento, comuniCompetenza, permessi.isPermessoComunicazione(),
				permessi.isPermessoDocumentazione());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {
			Exception.class }, timeout = 60000)
	public void salva(final FascicoloDTO fascicolo) throws Exception {
		log.trace("Entrato nel service: '" + this.getClass().getSimpleName() + "'. Metodo: '" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "'");

		// Controllo che il tipo procedimento selezionato sia valido per il fascicolo da
		// salvare (cioè se era attivo al momento della prima creazione del fascicolo)
		if (!this.checkTipoProcedimentoValidoForFascicolo(fascicolo.getId(), fascicolo.getTipoProcedimento().name())) {
			throw new CustomOperationToAdviceException(
					"Il tipo procedimento selezionato non era abilitato al momento della creazione del fasciolo.");
		}

		if (tipoProcedimentoService.find(fascicolo.getTipoProcedimento().name()).isSanatoria() == false)
			fascicolo.setSanatoria(false);

		fascicolo.setVersFascicolo(modificheTabService.getLast(NomiTab.FASCICOLO).getHashcode());
		fascicolo.setVersRichiedente(modificheTabService.getLast(NomiTab.RICHIEDENTE).getHashcode());
		fascicolo.setVersIntervento(modificheTabService.getLast(NomiTab.INTERVENTO).getHashcode());
		fascicolo.setVersLocalizzazione(modificheTabService.getLast(NomiTab.LOCALIZZAZIONE).getHashcode());
		fascicolo.setVersAllegati(modificheTabService.getLast(NomiTab.ALLEGATI).getHashcode());
		fascicolo.setVersProvvedimento(modificheTabService.getLast(NomiTab.PROVVEDIMENTO).getHashcode());

		this.update(fascicolo);
	}

	@Override
	public boolean validazioneCreazione(final FascicoloDTO fascicolo) {
		log.trace("Entrato nel service: '" + this.getClass().getSimpleName() + "'. Metodo: '" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "'");

		boolean valido = false;

		if (fascicolo.getTipoProcedimento() != null && this.checkTipoProcedimentoValido(fascicolo.getTipoProcedimento())
				&& fascicolo.getUfficio() != null && StringUtil.isNotEmpty(fascicolo.getOggettoIntervento())) {
			valido = true;
		}

		if (props.isPutt()) {
			if (fascicolo.getDataDelibera() == null) // TODO: probabilmente sarà necessario inserire una validazione
														// sulla data (es. nel futuro)
				valido = false;
		}

		return valido;
	}

	/**
	 * Metodo che controlla se il tipo procedimento ricevuto come parametro
	 * appartiene all'applicativo corrente
	 * 
	 * @param fascicolo
	 * @return testa il tipo procedimento rispetto all'applicazione
	 * @throws Exception
	 */
	private boolean checkTipoProcedimentoValido(final TipoProcedimento tipoProcedimento) {
		if (props.isAutPae()) {
			return tipoProcedimento.isForAutpae();
		} else if (props.isPareri()) {
			return tipoProcedimento.isForPareri();
		} else if (props.isPutt()) {
			return tipoProcedimento.isForPutt();
		} else
			return false;
	}

	/**
	 * Metodo che controlla se il tipo procedimento ricevuto in input è attualmente
	 * attivo (presente sul db e non scaduto)
	 * 
	 * @author Giuseppe Canciello
	 * @date 14 mag 2021
	 * @param codiceTipoProcedimento
	 * @return
	 * @throws Exception
	 */
	private boolean checkTipoProcedimentoAttivoOnCreate(final String codiceTipoProcedimento, final Date riferimento)
			throws Exception {
		try {
			// Controllo che il tipo procedimento sia valido e non scaduto
			TipoProcedimentoDTO proc = tipoProcedimentoService.find(codiceTipoProcedimento);
			if (proc == null || proc.getInizioValidita().after(riferimento)
					|| proc.getFineValidita().before(riferimento)) {
				log.warn(
						"checkTipoProcedimentoValido: Il tipo procedimento risulta scaduto. Data inizio validità: {}, data fine validità: {}",
						proc.getInizioValidita(), proc.getFineValidita());
				return false;
			}
		} catch (Exception e) {
			log.error(
					"**** checkTipoProcedimentoValido: Errore durante estrapolazione tipo procedimento dal db. Errore: {}",
					e.getMessage());
			throw e;
		}
		return true;
	}

	/**
	 * Metodo che controlla se il tipo di procedimento ricevuto come secondo
	 * parametro è valido per il fascicolo con id ricevuto come primo parametro,
	 * (cioè se al momento della creazione del fascicolo il tipo procedimento era
	 * attivo e valido)
	 * 
	 * @author Giuseppe Canciello
	 * @date 14 mag 2021
	 * @param codiceTipoProcedimento
	 * @return
	 * @throws Exception
	 */
	private boolean checkTipoProcedimentoValidoForFascicolo(final Long idFascicolo, final String codiceTipoProcedimento)
			throws Exception {
		try {
			return fascicoloProcedimentiAttiviRepo.isTipoProcedimentoValidoForFascicolo(idFascicolo,
					codiceTipoProcedimento);
		} catch (Exception e) {
			log.error(
					"**** checkTipoProcedimentoValidoForFascicolo: Errore durante estrapolazione tipo procedimento dal db. Errore: {}",
					e.getMessage());
			throw e;
		}
	}

	@Override
	public boolean validazione(final FascicoloDTO fascicolo) {
		log.trace("Entrato nel service: '" + this.getClass().getSimpleName() + "'. Metodo: '" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "'");

		boolean valido = (validazioneCreazione(fascicolo));
		return valido;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class, timeout = 60000)
	public PaginatedList<FascicoloDTO> searchFascicolo(final FascicoloSearch filter) throws Exception {
		log.trace("Entrato nel service: '" + this.getClass().getSimpleName() + "'. Metodo: '" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "'");

		filter.setDeleted(false);
		PaginatedList<FascicoloDTO> listaPaginata = this.search(filter);

		if (listaPaginata.getList() != null) {
			for (FascicoloDTO fascicolo : listaPaginata.getList()) {
				Set<Long> idComuni = new HashSet<>();
				try {
					ParticelleCatastaliSearch searchPC = new ParticelleCatastaliSearch();
					searchPC.setPraticaId(fascicolo.getId());
					List<ParticelleCatastaliDTO> pc = particelleCatastaliService.search(searchPC).getList();
					if (pc != null)
						pc.forEach(elem -> idComuni.add(elem.getComuneId()));

					ParchiPaesaggiImmobiliSearch searchPPI = new ParchiPaesaggiImmobiliSearch();
					searchPPI.setPraticaId(fascicolo.getId());
					searchPPI.setSelezionato("S");
					List<ParchiPaesaggiImmobiliDTO> ppi = parchiPaesaggiImmobiliService.search(searchPPI).getList();
					if (ppi != null)
						ppi.forEach(elem -> idComuni.add(elem.getComuneId()));

					List<LocalizzazioneInterventoDTO> li = localizzazioneInterventoService.select(fascicolo.getId());
					if (li != null)
						li.forEach(elem -> idComuni.add(elem.getComuneId()));

					if (fascicolo.getComuni() == null)
						fascicolo.setComuni(new ArrayList<String>());

					for (Long elem : idComuni) {
						fascicolo.getComuni().add(commonService.findEnteById(elem.intValue()).getNome());
					}
					;
				} catch (Exception e) {
					throw e;
				}
			}
			;
		}
		;
		return listaPaginata;
	}

	@Autowired
	MyCompetenzaService myCompSvc;
	@Autowired
	ClientSportelloService sportelloSvc;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class, timeout = 60000)
	public PaginatedList<FascicoloPublicDto> searchPublicFascicolo(final FascicoloSearch filter) throws Exception {
		Map<String, MyOrganizzazioniBean> myOrgMap = null;
		final String token = SecurityUtil.getAuthorizationHeader();
		if (StringUtil.isNotEmpty(token)) {
			myOrgMap = myCompSvc.getMyOrganizzazioniPaesaggio();
		}
		PaginatedList<FascicoloPublicDto> listaPaginata = repository.publicSearch(filter, myOrgMap);
		if (listaPaginata.getList() != null) {
			for (FascicoloPublicDto fascicolo : listaPaginata.getList()) {
				List<Integer> listaComuniId = repository.getComuniId(fascicolo.getCodice());
				if (fascicolo.getComuni() == null)
					fascicolo.setComuni(new ArrayList<String>());
				for (Integer elem : listaComuniId) {
					fascicolo.getComuni().add(commonService.findEnteById(elem.intValue()).getNome());
				}
				if (fascicolo.getApplicazione().equals(props.getCodiceApplicazione()) && myOrgMap != null
						&& myOrgMap.get(props.getCodiceApplicazione()) != null
						&& ListUtil.isNotEmpty(myOrgMap.get(props.getCodiceApplicazione()).getCodiciGruppo())) {
					String gruppo = myCompSvc.groupCanAccessPratica(fascicolo.getCodice(),
							myOrgMap.get(props.getCodiceApplicazione()).getCodiciGruppo(), SecurityUtil.getUsername());
					fascicolo.setGroupCanAccess(gruppo);
				}
			}
			setCodiciGruppoAccessoSportello(myOrgMap, token, listaPaginata);
		}
		return listaPaginata;
	}

	private void setCodiciGruppoAccessoSportello(Map<String, MyOrganizzazioniBean> myOrgMap, final String token,
			PaginatedList<FascicoloPublicDto> listaPaginata) {
		if (myOrgMap != null && myOrgMap.get(MyCompetenzaService.PAE_PRES_IST) != null
				&& ListUtil.isNotEmpty(myOrgMap.get(MyCompetenzaService.PAE_PRES_IST).getCodiciGruppo())) {
			List<String> codiciPraticaSportello = listaPaginata.getList().stream()
					.filter(fascicolo -> fascicolo.getApplicazione().equals(MyCompetenzaService.PAE_PRES_IST))
					.map(fascicolo -> fascicolo.getCodice()).collect(Collectors.toList());
			if (ListUtil.isNotEmpty(codiciPraticaSportello)) {
				// effettua call a sportello e verifica eventuali gruppi con cui posso accedere
				try {
					Map<String, String> mapCodici = sportelloSvc.praticheGruppo(token, codiciPraticaSportello);
					if (mapCodici != null) {
						mapCodici.keySet().stream().forEach(codicePratica -> {
							listaPaginata.getList().stream()
									.filter(fascPub -> fascPub.getCodice().equals(codicePratica)).findFirst().ifPresent(
											fascPub -> fascPub.setGroupCanAccess(mapCodici.get(fascPub.getCodice())));
						});
					}
				} catch (Exception e) {
					log.error("mapping per accesso a pratiche di sportello non effettuato !!!");
				}
			}
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class, timeout = 60000)
	public boolean cancella(final long id) throws Exception {
		log.trace("Entrato nel service: '" + this.getClass().getSimpleName() + "'. Metodo: '" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "'");

		FascicoloSearch searchF = new FascicoloSearch();
		this.prepareForSearch(searchF);
		searchF.setId(id);
		searchF.setStato(StatoFascicolo.WORKING);
		searchF.setDeleted(false);
		searchF.setUfficio(userUtil.getGruppo_Id());
		long numToDelete = repository.count(searchF);
		if (numToDelete == 0) {
			return false;
		} else if (numToDelete == 1) {
			if (repository.cancella(id, true) != 1) {
				log.error("Errore durante la cancellazione del fascicolo");
				throw new Exception("Errore durante la cancellazione del fascicolo");
			}
		} else {
			log.error("Solo il gruppo creazione può cancellare i fascicoli 'In Lavorazione'!");
			throw new Exception("Solo il gruppo creazione può cancellare i fascicoli 'In Lavorazione'!");
		}
		return true;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class, timeout = 60000)
	public List<String> autocompleteRup(final String rup) throws Exception {
		return repository.autocompleteRup(userUtil.getIntegerId(), rup);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class, timeout = 60000)
	public LineaTemporaleDTO lineaTemporale(final Long idFascicolo) throws Exception {
		log.trace("Entrato nel service: '" + this.getClass().getSimpleName() + "'. Metodo: '" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "'");
		LineaTemporaleDTO lt = repository.lineaTemporale(idFascicolo);
		lt.setEnte(commonService.getDenominazioneOrganizzazione(userUtil.getIntegerId(lt.getEnte())));
		FascicoloSearch searchF = new FascicoloSearch();
		searchF.setId(idFascicolo);
		searchF.setStato(StatoFascicolo.WORKING);
		this.prepareForSearch(searchF);
		if (this.count(searchF) == 0) {
			TipologicaNumeriDTO ricData = allegatiService.getRicevutaTrasmissione(idFascicolo);
			if (ricData != null) {
				lt.setProtocollo(ricData.getLabel());
			} else {
				lt.setProtocollo("");
				log.error("Errore nella get ricevuta trasmissione per settare il protocollo nella lineaTemporale ");
			}
		}
		return lt;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class, timeout = 60000)
	public AcceleratoriFascicoloDTO getCountForAccelerator(final FascicoloSearch search) throws Exception {
		log.trace("Entrato nel service: '" + this.getClass().getSimpleName() + "'. Metodo: '" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "'");
		AcceleratoriFascicoloDTO dto = new AcceleratoriFascicoloDTO();
		search.setDeleted(false);
		this.prepareForSearch(search);
		search.setStato(StatoFascicolo.WORKING);
		dto.setnWorking(this.count(search));
		search.setStato(StatoFascicolo.TRANSMITTED);
		dto.setnTrasmitted(this.count(search));
		search.setStato(StatoFascicolo.SELECTED);
		dto.setnSelected(this.count(search));
		search.setStato(StatoFascicolo.FINISHED);
		dto.setnFinished(this.count(search));
		search.setStato(StatoFascicolo.ON_MODIFY);
		dto.setnOnModify(this.count(search));
		search.setStato(StatoFascicolo.CANCELLED);
		dto.setnCancelled(this.count(search));
		// search.setStato(StatoFascicolo.DELETED); dto.setnDeleted
		// (this.count(search));
		return dto;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class, timeout = 60000)
	public void resettaAllaTrasmissione(final long idFascicolo) throws Exception {
		log.trace("Entrato nel service: '" + this.getClass().getSimpleName() + "'. Metodo: '" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "'");

		// recupero il DTO di informazioni relativo alla trasmissione
		InformazioniDTO infoTrasm = this.find(idFascicolo).getInfoComplete();

		// if (infoTrasm.getStato()!=StatoFascicolo.TRANSMITTED) { // potrebbe avere
		// altri stati leciti oltre TRANSMITTED
		// throw new Exception("Errore. Stato Fascicolo
		// '"+infoTrasm.getStato().getTextValue()+"' non consentito!");
		// }
		azioniService.salvaInModifica(infoTrasm);
	}

	@Override
	public String checkCodiceFiscale(final String codiceFiscale) {
		return CodiceFiscaleValidator.validate(codiceFiscale);
	}

	@Override
	public String checkPartitaIVA(final String partitaIVA) {
		return PartitaIvaValidator.validate(partitaIVA);
	}

	@Override
	public String checkCAP(final String cap) {
		return CapValidator.validate(cap);
	}

	@Override
	public String checkTelefono(final String telefono) {
		return TelefonoValidator.validate(telefono);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = false)
	public FascicoloDTO creaDaMigrazione(final FascicoloDTO fascicolo) throws Exception {
		log.trace("Entrato nel service: '" + this.getClass().getSimpleName() + "'. Metodo: '" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "'");
		fascicolo.setStato(StatoFascicolo.WORKING);
		fascicolo.setVersFascicolo(modificheTabService.getLast(NomiTab.FASCICOLO).getHashcode());
		fascicolo.setVersRichiedente(modificheTabService.getLast(NomiTab.RICHIEDENTE).getHashcode());
		fascicolo.setVersIntervento(modificheTabService.getLast(NomiTab.INTERVENTO).getHashcode());
		fascicolo.setVersLocalizzazione(modificheTabService.getLast(NomiTab.LOCALIZZAZIONE).getHashcode());
		fascicolo.setVersAllegati(modificheTabService.getLast(NomiTab.ALLEGATI).getHashcode());
		fascicolo.setVersProvvedimento(modificheTabService.getLast(NomiTab.PROVVEDIMENTO).getHashcode());
		fascicolo.setId(this.insert(fascicolo));
		this.updateForMigration(fascicolo);// imposta data creazione
		// comuniCompetenzaService.crea(fascicolo.getId());

		return fascicolo;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = false)
	public void aggiornaDataCreazione(final long idFascicolo, final Date dataCreazione) throws Exception {
		if (dataCreazione != null)
			repository.aggiornaDataCreazione(idFascicolo, dataCreazione);
		else
			log.info("Aggiornamento data creazione non effettuato, dataCreazione nulla");
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = false)
	public Integer findByTPraticaId(final Long tPraticaId) {
		return this.repository.findByTPraticaId(tPraticaId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = true)
	public void checkPermission(final Long idFascicolo) throws Exception {
		FascicoloSearch search = new FascicoloSearch();
		search.setId(idFascicolo);
		this.prepareForSearch(search);
		if (this.count(search) == 0) {
			log.error("Errore: non possiedi i permessi per visualizzare il fascicolo con id {}", idFascicolo);
			throw new CustomOperationToAdviceException(
					"Errore: non possiedi i permessi per l'accesso al fascicolo con id " + idFascicolo);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = true)
	public void checkPermission(final Long idFascicolo, final StatoFascicolo stato) throws Exception {
		FascicoloSearch search = new FascicoloSearch();
		search.setId(idFascicolo);
		search.setStato(stato);
		this.prepareForSearch(search);
		PaginatedList<FascicoloDTO> fascicoli = this.searchFascicolo(search);
		if (fascicoli == null || ListUtil.isEmpty(fascicoli.getList())) {
			log.error("Errore: non possiedi i permessi per visualizzare il fascicolo con id {}", idFascicolo);
			throw new CustomOperationToAdviceException(
					"Errore: non possiedi i permessi per l'accesso al fascicolo con id " + idFascicolo);
		}
	}

}