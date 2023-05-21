package it.eng.tz.puglia.autpae.service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.autpae.config.ApplicationProperties;
import it.eng.tz.puglia.autpae.dto.AllegatoCustomDTO;
import it.eng.tz.puglia.autpae.dto.AllegatoInfoDTO;
import it.eng.tz.puglia.autpae.dto.DestinatarioTemplateDTO;
import it.eng.tz.puglia.autpae.dto.NuovaComunicazioneDTO;
import it.eng.tz.puglia.autpae.dto.TipologicaDTO;
import it.eng.tz.puglia.autpae.dto.TipologicaDestinatarioDTO;
import it.eng.tz.puglia.autpae.entity.AllegatoDTO;
import it.eng.tz.puglia.autpae.entity.CampionamentoDTO;
import it.eng.tz.puglia.autpae.entity.ConfigurazioneCampionamentoDTO;
import it.eng.tz.puglia.autpae.entity.FascicoloCampionamentoDTO;
import it.eng.tz.puglia.autpae.entity.FascicoloDTO;
import it.eng.tz.puglia.autpae.entity.RichiedenteDTO;
import it.eng.tz.puglia.autpae.enumeratori.EsitoVerifica;
import it.eng.tz.puglia.autpae.enumeratori.StatoFascicolo;
import it.eng.tz.puglia.autpae.enumeratori.TipoAllegato;
import it.eng.tz.puglia.autpae.enumeratori.TipoDestinatario;
import it.eng.tz.puglia.autpae.enumeratori.TipoTemplate;
import it.eng.tz.puglia.autpae.repository.CampionamentoFullRepository;
import it.eng.tz.puglia.autpae.search.CampionamentoSearch;
import it.eng.tz.puglia.autpae.search.FascicoloCampionamentoSearch;
import it.eng.tz.puglia.autpae.search.TemplateDestinatarioSearch;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoService;
import it.eng.tz.puglia.autpae.service.interfacce.CampionamentoService;
import it.eng.tz.puglia.autpae.service.interfacce.ConfigurazioneCampionamentoService;
import it.eng.tz.puglia.autpae.service.interfacce.CorrispondenzaService;
import it.eng.tz.puglia.autpae.service.interfacce.DestinatarioService;
import it.eng.tz.puglia.autpae.service.interfacce.FascicoloCampionamentoService;
import it.eng.tz.puglia.autpae.service.interfacce.FascicoloService;
import it.eng.tz.puglia.autpae.service.interfacce.LocalizzazioneInterventoService;
import it.eng.tz.puglia.autpae.service.interfacce.RichiedenteService;
import it.eng.tz.puglia.autpae.service.interfacce.TemplateDestinatarioService;
import it.eng.tz.puglia.autpae.utility.MockMultipartFile;
import it.eng.tz.puglia.autpae.utility.VarieUtils;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.service.http.IHttpClientService;
import it.eng.tz.puglia.service.http.bean.ProtocolloRequestBean;
import it.eng.tz.puglia.servizi_esterni.jasper.CreaReportService;
import it.eng.tz.puglia.servizi_esterni.jasper.SampledExclusions;
import it.eng.tz.puglia.servizi_esterni.profileManager.dto.UtenteGruppo;
import it.eng.tz.puglia.servizi_esterni.profileManager.feign.IProfileManager;
import it.eng.tz.puglia.servizi_esterni.profileManager.feign.UserUtil;
import it.eng.tz.puglia.servizi_esterni.protocollo.ProtocolNumberType;
import it.eng.tz.puglia.servizi_esterni.protocollo.model.SegnaturaProtocollo;
import it.eng.tz.puglia.util.log.LogUtil;
import it.eng.tz.puglia.util.string.StringUtil;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

@Service
public class CampionamentoServiceImpl implements CampionamentoService {

	private static final Logger log = LoggerFactory.getLogger(CampionamentoServiceImpl.class);

	@Autowired private CampionamentoFullRepository repository;										 
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// BaseRepository
	
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true )	public 		    List<CampionamentoDTO> select() 					  	  throws Exception { return repository.select(); 	   }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true )	public long 						   count (CampionamentoSearch filter) throws Exception { return repository.count (filter); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true )	public 			     CampionamentoDTO  find  (Long pk) 			  		  throws Exception { return repository.find  (pk); 	   }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false)	public Long 						   insert(CampionamentoDTO entity) 	  throws Exception { return repository.insert(entity); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false)	public int 							   update(CampionamentoDTO entity)	  throws Exception { return repository.update(entity); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false)	public int 							   delete(CampionamentoSearch search) throws Exception { return repository.delete(search); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true )	public PaginatedList<CampionamentoDTO> search(CampionamentoSearch filter) throws Exception { return repository.search(filter); }
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// FullRepository
	
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true )	public int 				  findActiveSamplingFascicoli(long samplingId, java.sql.Date publishDate, java.sql.Date samplingDate)  {
																																  	return 		   repository.findActiveSamplingFascicoli(	   samplingId, 			     publishDate, 			    samplingDate); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true )	public List<FascicoloDTO> getAllActiveFascicoli	   	 (long samplingId) 														   {
			  																													  	return 		   repository.getAllActiveFascicoli	   	 (	   samplingId);														   }
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// Altri Metodi
	
	@Autowired private FascicoloService fascicoloService;
	@Autowired private DestinatarioService destinatarioService;
	@Autowired private FascicoloCampionamentoService fascicoloCampionamentoService;
	@Autowired private ConfigurazioneCampionamentoService configurazioneCampionamentoService;
	@Autowired private DynamicSchedulerServiceImpl dynamicSchedulerService;
	@Autowired private TemplateDestinatarioService templateDestinatarioService;
	@Autowired private AllegatoService allegatoService;
	@Autowired private RichiedenteService richiedenteService;
	@Autowired private LocalizzazioneInterventoService localizzazioneInterventoService;
	@Autowired private ApplicationProperties props;
	@Autowired private IProfileManager profileManager;
	@Autowired private UserUtil userUtil;
	@Autowired private CorrispondenzaService corrispondenzaService;
	@Autowired private IHttpClientService clientSvc;

	
//	@Value("${campionamento.change.limit}")	private short campionamento_change_limit;
//	@Value("${campionamento.max.percent}")  private short campionamento_max_percent;
//	@Value("${campionamento.min.percent}")  private short campionamento_min_percent;
	
	@Value("${campionamento.presampling.notification.days}")
	private String campionamento_presampling_notification_days;

	@Value("${campionamento.verification.notification.days}")
	private String campionamento_verification_notification_days;
	
	@Value("${campionamento.verification.elapsing.days}")
	private Short campionamento_verification_elapsing_days;

	@Autowired
	private CreaReportService creaReportService;
	
	// FIXME: decommentando le annotazioni @Transactional (non so bene quali tra quelle presenti su questi metodi) la compilazione va in errore. Verificare e risolvere
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// SERVICE Amministratore (Annulla)
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public boolean deleteByFascicoloId(long idFascicolo) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		if (fascicoloCampionamentoService.count(new FascicoloCampionamentoSearch(null, idFascicolo))==0) {
			return true;
		}
		else {
			return (fascicoloCampionamentoService.delete(new FascicoloCampionamentoSearch(null, idFascicolo))>0);
		}
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// CONTROLLER Selezionata
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public ConfigurazioneCampionamentoDTO getConfigurazioneCampionamento() throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		return configurazioneCampionamentoService.select().get(0);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public void updateConfigurazione(ConfigurazioneCampionamentoDTO configurazioneCampionamento) throws Exception {
		configurazioneCampionamentoService.update(configurazioneCampionamento);
	}
	
/*	/**
	 * {@inheritDoc}
	 **
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor=Exception.class)
	public CampionamentoDTO getFascicoli() {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		CampionamentoDTO entity = null;
		try {
			entity = this.getActiveSampling();				// qui i serbi chiamavano direttamente il metodo della repository senza passare dal service
		} catch (EmptyResultDataAccessException erdae) {
			log.warn("No fascicoli available for sampling. There are no active samplings.");
			return new CampionamentoDTO();
		}
		FascicoloCampionamentoSearch searchFC = new FascicoloCampionamentoSearch();
		searchFC.setIdCampionamento(entity.getId());
		try {
			entity.setFascicoliRegistrati(fascicoloCampionamentoRepository.count(searchFC));
		} catch (Exception e) {
			log.error("Errore nel count di FascicoloCampionamento!");
			e.printStackTrace();
		}
		return entity;
	}	*/

/*	/**
	 * {@inheritDoc}
	 **
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public void setPercentage(short percent) {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		Assert.isTrue(
						percent >= campionamento_min_percent &&
						percent <= campionamento_max_percent ,
						"Percentuale must be between "+campionamento_min_percent+" and "+campionamento_max_percent+" precent!"
					 );
		campionamentoCustomRepository.setPercentOnActiveSampling(percent, true);
	}	*/

/*	/**
	 * {@inheritDoc}
	 **
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor=Exception.class)
	public short getMaxPercentage() {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		return campionamentoCustomRepository.getMaxPercentage();
	}	*/

/*	/**
	 * {@inheritDoc}
	 **
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public short resetPercentage() {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		CampionamentoDTO entity = this.getActiveSampling();

		int numberOfActiveSamplingFascicoli = campionamentoCustomRepository.findActiveSamplingFascicoli(entity.getId(),
																								 		new java.sql.Date(entity.getDataInizio().getTime()),
																								 		new java.sql.Date(entity.getDataCampionatura().getTime()));
		short percent = getDefaultPercentage(numberOfActiveSamplingFascicoli);
		campionamentoCustomRepository.setPercentOnActiveSampling(percent, false);

		return percent;
	}	*/

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// SERVICE Piano (Publish)
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public void samplingCheck(long idFascicolo) { 
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		CampionamentoDTO samplingEntity;
		boolean activateFuture;
		long samplingId;

		try {											 // c'è già un Campionamento attivo
			samplingEntity = this.getActiveSampling();
			samplingId = samplingEntity.getId();
			activateFuture = false;
		//	this.updateSamplingProcess(samplingEntity);
		} catch (DataAccessException erdae) {			 // non c'è un Campionamento attivo
			samplingId = this.createSamplingProcess();
			activateFuture = true;
		}

		// in entrambi i casi... la createSamplingProcess se restituisce 0 vuol dire che non è attivo il campionamento....
		try {
			if (samplingId > 0) {
				fascicoloCampionamentoService.insert(new FascicoloCampionamentoDTO(samplingId, idFascicolo));
				fascicoloService.aggiornaEsito(idFascicolo, EsitoVerifica.SAMPLING_PENDING, null, null);
				if (activateFuture == true) {
					dynamicSchedulerService.activateFuture();
				}
			}
		} catch (Exception e) {
			log.error("Errore nell'inserimento del FascicoloCampionamento!",e);
		}

		
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// SERVICE Dynamic Scheduler
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public Map<String, List<FascicoloDTO>> getVerificationFascicoli(long entityId, short samplingPercent) { 
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		List<FascicoloDTO> allFascicoli = repository.getAllActiveFascicoli(entityId);
		if (allFascicoli.isEmpty()) {
			return Collections.emptyMap();
		}
		int[] randomNumbers = getRandomNumbers(allFascicoli.size(), samplingPercent);
		List<FascicoloDTO> verificationFascicoli = new ArrayList<>(randomNumbers.length);
		List<FascicoloDTO> fascicoliNotSelected  = new ArrayList<>(allFascicoli.size() - randomNumbers.length);

		IntStream.of(randomNumbers).forEach(number -> {
			FascicoloDTO fascicolo = allFascicoli.get(number);

			fascicolo.setStato        (StatoFascicolo.TRANSMITTED);				  // StatoDiRegistrazione   - RegistrationStatus
			fascicolo.setEsitoVerifica(EsitoVerifica .CHECK_IN_PROGRESS);		  // RisultatoDellaVerifica - VerificationOutcome
			try {
				fascicoloService.update(fascicolo);
			} catch (Exception e) {
				log.error("Errore nell'aggiornamento del fascicolo a seguito della verifica ",e);
			}

			verificationFascicoli.add(fascicolo);
		});

		List<FascicoloDTO> refreshedFascicoli = repository.getAllActiveFascicoli(entityId);

		refreshedFascicoli.stream().forEach(filteredFascicolo -> {
			if (verificationFascicoli.stream().noneMatch(el -> el.getId().equals(filteredFascicolo.getId()))) {
				filteredFascicolo.setStato        (StatoFascicolo.FINISHED);	  // StatoDiRegistrazione   - RegistrationStatus
				filteredFascicolo.setEsitoVerifica(EsitoVerifica .NOT_SELECTED);  // RisultatoDellaVerifica - VerificationOutcome
				try {
					fascicoloService.update(filteredFascicolo);
				} catch (Exception e) {
					log.error("Errore nell'aggiornamento del fascicolo a seguito della verifica",e);
				}
				// eventuale chiamata a CDP
				fascicoliNotSelected.add(filteredFascicolo);
			}
		});

		Map<String, List<FascicoloDTO>> response = new HashMap<>(3);
		response.put(SampledExclusions.ALL.name(), allFascicoli);
		response.put(SampledExclusions.SELECTED.name(), verificationFascicoli);
		response.put(SampledExclusions.NOT_SELECTED.name(), fascicoliNotSelected);

		return response;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public void deactivateSampling() { 
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		repository.deactivateSampling();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public List<CampionamentoDTO> getSamplingsNotVerified() { 
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		return repository.getSamplingsNotVerified();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public CampionamentoDTO getActiveSampling() { 
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		return repository.getActiveSampling();
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// PRIVATE
	
/*	/**
	 * Default percentage based on provided formula which includes number of fascicoli
	 * 
	 * @param numberOfActiveSamplingFascicoli
	 * @return calculated percentage
	 **
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	private short getDefaultPercentage(int numberOfActiveSamplingFascicoli) {
		
		if (numberOfActiveSamplingFascicoli < campionamento_change_limit) {
			return campionamento_max_percent;
		} else {
			return campionamento_min_percent;
		}
	}  */

/*	/**
	 * This service updates the sampling process when fascicolo is being published
	 * @param entity									- a SamplingDTO entity object
	 **
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	private void updateSamplingProcess(CampionamentoDTO entity) {

		int numberOfActiveSamplingFascicoli = campionamentoCustomRepository.findActiveSamplingFascicoli(entity.getId(),
																										new java.sql.Date(entity.getDataInizio().getTime()),
																										new java.sql.Date(entity.getDataCampionatura().getTime()));
		short percentage = calculateSamplingPercentage(entity, numberOfActiveSamplingFascicoli);
		entity.setPercentuale(percentage);

		try {
			campionamentoRepository.update(entity);
		} catch (Exception e) {
			log.error("Errore nell'aggiornamento del campionamento!");
			e.printStackTrace();
		}
	}  */
	
/*	/**
	 * This service calculates sampling percentage based on number of fascicoli and "customized" property
	 * @param entity									- a SamplingDTO entity object
	 * @param numberOfActiveSamplingFascicoli			- int value of the number of active samping fascicoli
	 * @return calculated percent						- short value of calculated sampling percentage
	 **
	private short calculateSamplingPercentage(CampionamentoDTO entity, int numberOfActiveSamplingFascicoli) {

		if ((numberOfActiveSamplingFascicoli != campionamento_min_percent) && (entity.isCustomized() == false)) {
			return getDefaultPercentage(numberOfActiveSamplingFascicoli);
		} else {
			return entity.getPercentuale();
		}
	}  */
	
/*	/**
	 * This service creates the new FascicoloCampionamento which connects Fascicolo and Campionamento
	 * @param samplingId								- long value of sampling id
	 * @param fascicoloId								- long value of fascicolo's id
	 **
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	private void insertFascicoloCampionamento(long samplingId, long fascicoloId) {
	
		try {
			fascicoloCampionamentoRepository.insert(new FascicoloCampionamentoDTO(samplingId, fascicoloId));
		} catch (Exception e) {
			log.error("Errore nell'inserimento del FascicoloCampionamento!");
			e.printStackTrace();
		}
	}  */
	
	/**
	 * From number of total fascicoli get random number based on percentage of sampling
	 * Copiato da PEV:
	 * il numero di piani urbanistici comunali selezionati per le verifiche a 
	 * campione sarà determinato in base al numero complessivo di piani registrati 
	 * in ogni periodo, secondo le seguenti regole:
	 * I) per un numero di piani urbanistici comunali registrati compreso fra 0 e 30, 
	 *sarà selezionato il 15% dei piani, arrotondato all'unità più vicina e con un valore minimo di 2;
	 * II) per un numero di piani urbanistici comunali registrati uguale o superiore a 31, 
	 *sarà selezionato il 10% dei piani, arrotondato all'unità più vicina e con un valore minimo di 4;
	 * @param totalFascicoli number of fascicoli
	 * @param percentage 10 - 15 (percent)
	 * @return array of random numbers (all in range [1, totalFascicoli])
	 */
	private int[] getRandomNumbers(int totalFascicoli, short percentage) {
		log.trace("Entrato nel service: '" + this.getClass().getSimpleName() + "'. Metodo: '" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "'");

		int numberOfFascicoli = totalFascicoli > 6 ? (int) (Math.round(totalFascicoli * percentage / 100.0)) : 1;
		if (totalFascicoli >= 2 && numberOfFascicoli == 1) {
			numberOfFascicoli = 2;
		}
		// When percentage is 10 minimal number of plans is 4
		if (totalFascicoli >= 31 && percentage == 10 && numberOfFascicoli == 3) {
			numberOfFascicoli = 4;
		}
		List<Integer> randoms = null;
		while (randoms == null || randoms.size() != numberOfFascicoli) {
			randoms = ThreadLocalRandom.current().ints(numberOfFascicoli, 0, totalFascicoli).distinct().boxed()
					.collect(Collectors.toList());
		}
		return randoms.stream().sorted().mapToInt(Integer::intValue).toArray();
	}

	/**
	 * This service creates new active sampling
	 * @return int value of the id of the inserted sampling 
	 * 0 => se non è attivo il campionamento in configurazione
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public long createSamplingProcess() {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		ConfigurazioneCampionamentoDTO configurazione = null;
		try {
			configurazione = this.getConfigurazioneCampionamento();
		} catch (Exception e) {
			log.error("Errore nella lettura della ConfigurazioneCampionamento nessun campionamento inserito:",e);
			return 0;
		}
		if(!configurazione.isCampionamentoAttivo()) {
			//se non è attivo in configurazione non creo nulla e restituisco 0
			return 0;
		}
		CampionamentoDTO entity = new CampionamentoDTO();

		Date currentDate = new Date();
		LocalDateTime localDateTime = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
					  localDateTime = localDateTime.plusDays(configurazione.getIntervalloCampionamento() - 1L);
					  localDateTime = localDateTime.with(LocalTime.MAX);
					  localDateTime = localDateTime.plusSeconds(1); 
		//deve passare al giorno successivo.. ovvero la data di campionamento è settata al giorno successivo all'ultimo utile.
		Date samplingDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		
		entity.setAttivo(true);	
		entity.setIntervalloCamp(configurazione.getIntervalloCampionamento());
		entity.setTipoSuccessivo(configurazione.getTipoCampionamentoSuccessivo());
		entity.setPercentuale(configurazione.getPercentualeIstanze());
		entity.setNotificaCamp (!StringUtil.isEmpty(configurazione.getGiorniNotificaCampionamento()) ? configurazione.getGiorniNotificaCampionamento() : campionamento_presampling_notification_days );
		entity.setNotificaVer  (!StringUtil.isEmpty(configurazione.getGiorniNotificaVerifica     ()) ? configurazione.getGiorniNotificaVerifica     () : campionamento_verification_notification_days);
		entity.setIntervalloVer( 					configurazione.getIntervalloVerifica()!=null     ? configurazione.getIntervalloVerifica			() : campionamento_verification_elapsing_days    );
		entity.setEsitoPubb(configurazione.isEsitoPubblico());
		entity.setCustomized(false);
		entity.setDataInizio(currentDate);
		entity.setDataCampionatura(samplingDate);

		long id = 0;
		try {
			id = repository.insert(entity);
		} catch (Exception e2) {
			log.error("Errore nell'inserimento del campionamento!",e2);
		}
		return id;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor=Exception.class)
	public void inviaPresamplingNotification  (long idCampionamento)	throws Exception {  // notifico l'imminente data effettiva di campionamento  ///////// 2 
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
			
		NuovaComunicazioneDTO corrispondenza = new NuovaComunicazioneDTO();

		// recupero gli id di tutti i fascicoli interessati dal campionamento.... 
		FascicoloCampionamentoSearch searchFC = new FascicoloCampionamentoSearch(idCampionamento, null);
		List<FascicoloCampionamentoDTO> listaFC = fascicoloCampionamentoService.search(searchFC).getList();
		
		if (listaFC!=null) {
			listaFC.forEach(elem-> { corrispondenza.getIdFascicoli().add(elem.getIdFascicolo()); });
		}
		corrispondenza.setBozza(false);
		corrispondenza.setTipoTemplate(TipoTemplate.NOTIFICA_PRE_CAMPIONAMENTO);

		// 1) cerco i destinatari di default
		List<TipologicaDestinatarioDTO> listaDestinatariDefault = new ArrayList<>();
		
		TemplateDestinatarioSearch searchTD = new TemplateDestinatarioSearch();
		searchTD.setCodiceTemplate(TipoTemplate.NOTIFICA_PRE_CAMPIONAMENTO);
		List<DestinatarioTemplateDTO> destinatariDefault = templateDestinatarioService.search(searchTD).getList();
		
		if (destinatariDefault!=null) {
			destinatariDefault.forEach(destinatario-> {
				listaDestinatariDefault.add(new DestinatarioTemplateDTO(destinatario)); 
			});
		}
		// non ci sono altri destinatari da aggiungere, perchè gli Operatori Regionali e gli Amministratori sono tra i destinatari di default
		corrispondenza.setDestinatari(VarieUtils.eliminaDuplicati(listaDestinatariDefault));
		
		// inserisco le informazioni per risolvere i PlaceHolders
		corrispondenza.getInfoPlaceHolders().setCampionamento(this.find(idCampionamento));
		
		// invio la corrispondenza
		corrispondenzaService.inviaCorrispondenza(corrispondenza,true);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor=Exception.class)
	public void inviaNotificaVerifica (long idCampionamento)	throws Exception {  // notifico l'imminente scadenza del periodo di verifica ///////// 3 
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		
		NuovaComunicazioneDTO corrispondenza = new NuovaComunicazioneDTO();

		// recupero gli id di tutti i fascicoli interessati dal campionamento
		FascicoloCampionamentoSearch searchFC = new FascicoloCampionamentoSearch(idCampionamento, null);
		List<FascicoloCampionamentoDTO> listaFC = fascicoloCampionamentoService.search(searchFC).getList();
		
		if (listaFC!=null) {
			listaFC.forEach(elem-> { corrispondenza.getIdFascicoli().add(elem.getIdFascicolo()); });
		}
		corrispondenza.setBozza(false);
		corrispondenza.setTipoTemplate(TipoTemplate.NOTIFICA_PRE_VERIFICA);

		// 1) cerco i destinatari di default
		List<TipologicaDestinatarioDTO> listaDestinatariDefault = new ArrayList<>();
		
		TemplateDestinatarioSearch searchTD = new TemplateDestinatarioSearch();
		searchTD.setCodiceTemplate(TipoTemplate.NOTIFICA_PRE_VERIFICA);
		List<DestinatarioTemplateDTO> destinatariDefault = templateDestinatarioService.search(searchTD).getList();
		
		if (destinatariDefault!=null) {
			destinatariDefault.forEach(destinatario-> {
				listaDestinatariDefault.add(new DestinatarioTemplateDTO(destinatario)); 
			});
		}
		// non ci sono altri destinatari da aggiungere, perchè gli Operatori Regionali e gli Amministratori sono tra i destinatari di default
		corrispondenza.setDestinatari(VarieUtils.eliminaDuplicati(listaDestinatariDefault));
		
		// inserisco le informazioni per risolvere i PlaceHolders
		corrispondenza.getInfoPlaceHolders().setCampionamento(this.find(idCampionamento));
		
		// invio la corrispondenza
		corrispondenzaService.inviaCorrispondenza(corrispondenza, true);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor=Exception.class)
	public void effettuaCampionamentoEInviaNotifica(CampionamentoDTO campionamento) throws Exception {  // effettuo il campionamento (più notifica ecc. ecc.)    ///////// 1 
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		this.deactivateSampling(); //disattivo il campionamento 		
		Map<String, List<FascicoloDTO>> fascicoli = this.getVerificationFascicoli(campionamento.getId(), campionamento.getPercentuale());
		
		if (fascicoli.isEmpty()) { return; }
		
		List<FascicoloDTO> listaFascicoliAll = fascicoli.get(SampledExclusions.ALL.name());
					Set<Long> idFascicoliAll = new HashSet<Long>();
						   listaFascicoliAll.forEach(fascicolo->{ 
							  idFascicoliAll.add    (fascicolo.getId()); });

		List<TipologicaDestinatarioDTO> destinatari = getDestinatariEsitoCampionamento(listaFascicoliAll);
		List<TipologicaDTO> destinatariTO = new ArrayList<>();
		List<TipologicaDTO> destinatariCC = new ArrayList<>();
		destinatari.forEach(dest->{
			if (dest.getTipo()==TipoDestinatario.CC)
				destinatariCC.add(new TipologicaDTO(dest.getEmail(), dest.getNome()));
			else
				destinatariTO.add(new TipologicaDTO(dest.getEmail(), dest.getNome()));
		});
		
		JasperPrint jasper = creaReportService.creaPdf_EsitoCampionamento(null, campionamento, destinatariTO, destinatariCC, fascicoli.get(SampledExclusions.ALL.name()), fascicoli.get(SampledExclusions.SELECTED.name()), fascicoli.get(SampledExclusions.NOT_SELECTED.name()));
		MockMultipartFile file = new MockMultipartFile("Esito del Campionamento.pdf", JasperExportManager.exportReportToPdf(jasper));

		AllegatoDTO informazioniOriginale = new AllegatoDTO();
		informazioniOriginale.setNome("Esito del Campionamento.pdf");
		informazioniOriginale.setMimeType("application/pdf");
		//mi salvo il pdf originale non protocollato
		AllegatoCustomDTO ret = allegatoService.inserisciPdfPreTimbratura(new ArrayList<Long>(idFascicoliAll),file,informazioniOriginale);
		informazioniOriginale.setId(ret.getId());
		//build protoBean
		String[] denominazioneDestinatari=destinatari.stream().map(TipologicaDestinatarioDTO::getNome).collect(Collectors.toList()).toArray(new String[0]);
		String[] mailTo=destinatari.stream().map(TipologicaDestinatarioDTO::getEmail).collect(Collectors.toList()).toArray(new String[0]);
		ProtocolloRequestBean protoBean = new ProtocolloRequestBean();
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		Date endPeriodoCampionamento=it.eng.tz.puglia.autpae.utility.DateUtil.addDays(campionamento.getDataCampionatura(),-1);
		protoBean.setOggetto("Comunicazione esito campionamento delle pratiche inserite nel periodo dal " + 
		sdf.format(campionamento.getDataInizio()) +" al " + sdf.format(endPeriodoCampionamento));
		protoBean.setNomeDocumentoPrimario("Primario");
		protoBean.setTitoloDocumentoPrimario("vedi oggetto");
		protoBean.setOggettoDocumentoPrimario(protoBean.getOggetto());
		protoBean.setTipoRiferimento("MIME");
		protoBean.setTipoRiferimentoPrimario("MIME");
		protoBean.setDenominazioneDestinatari(denominazioneDestinatari);
		protoBean.setMailTo(mailTo);
		protoBean.setInOut(ProtocolloRequestBean.InOut.U.name());
		protoBean.setOggetto(protoBean.getOggettoDocumentoPrimario());
		protoBean.setTitoloDocumento("Comunicazione esito selezione campionamento");
		protoBean.setTipoDocumento(informazioniOriginale.getMimeType());
		
		SegnaturaProtocollo protocollo = allegatoService.protocollaEgetSegnatura(file, informazioniOriginale, ProtocolNumberType.O,protoBean);
		
		AllegatoDTO informazioni = new AllegatoDTO();
		informazioni.setNome("Esito del Campionamento.pdf");
		informazioni.setMimeType("application/pdf");
		
		jasper = creaReportService.creaPdf_EsitoCampionamento(protocollo.toString(), campionamento, destinatariTO, destinatariCC, fascicoli.get(SampledExclusions.ALL.name()), fascicoli.get(SampledExclusions.SELECTED.name()), fascicoli.get(SampledExclusions.NOT_SELECTED.name()));
		file = new MockMultipartFile("Esito del Campionamento.pdf", JasperExportManager.exportReportToPdf(jasper));
		
		NuovaComunicazioneDTO corrispondenza = new NuovaComunicazioneDTO();

		corrispondenza.setIdFascicoli(new ArrayList<Long>(idFascicoliAll));
		corrispondenza.setBozza(false);
		corrispondenza.setTipoTemplate(TipoTemplate.ESITO_CAMPIONAMENTO);

		informazioni.setNumeroProtocolloOut(protocollo.toString());
		SimpleDateFormat sdfProto =new SimpleDateFormat("ddMMyyyy");
		informazioni.setDataProtocolloOut(sdfProto.parse(protocollo.getDataRegistrazione()));
		campionamento.setDataProtocollo(informazioni.getDataProtocolloOut());
		informazioni.setTipo(TipoAllegato.ESITO_CAMPIONAMENTO);
		
		corrispondenza.getAllegati().add(new AllegatoInfoDTO(informazioni, file));

		corrispondenza.setDestinatari(destinatari);
		
		// inserisco le informazioni per risolvere i PlaceHolders
		campionamento.setProtocollo(protocollo.toString());
		corrispondenza.getInfoPlaceHolders().setCampionamento(campionamento);
		
		// invio la corrispondenza
		corrispondenzaService.inviaCorrispondenza(corrispondenza);
		
		// non dovrebbe essere necessario inserire dei record (uno per ogni destinatario) nella tabella "allegato_ente"
		
		// aggiorno il campo 'Stato' e 'EsitoVerifica' dei fascicoli
		//   per fascicolo NON SELEZIONATA: --> SR = CONCLUSA
		//									  	EV = NON SELEZIONATA
		//   per fascicolo	   SELEZIONATA: --> SR = IN CORSO
		//									  	EV = IN CORSO
		List<FascicoloDTO> listaFascicoliNotSelected = fascicoli.get(SampledExclusions.NOT_SELECTED.name());
					Set<Long> idFascicoliNotSelected = new HashSet<Long>();
						   listaFascicoliNotSelected.forEach(  fascicolo->{
							  idFascicoliNotSelected.add    (  fascicolo.getId()); });
						      idFascicoliNotSelected.forEach(idFascicolo->{ try { fascicoloService.cambiaStato(idFascicolo, StatoFascicolo.FINISHED, false);
						      											  } catch (Exception e) { 
						      												  log.error("Errore nell'aggiornamento dello stato del fascicolo con id="+idFascicolo,e);
						      											  						  }															});
		
		List<FascicoloDTO> listaFascicoliSelected    = fascicoli.get(SampledExclusions.    SELECTED.name());
					Set<Long> idFascicoliSelected    = new HashSet<Long>();
						   listaFascicoliSelected   .forEach(  fascicolo->{
							  idFascicoliSelected   .add    (  fascicolo.getId()); });
						   	  idFascicoliSelected   .forEach(idFascicolo->{ try { fascicoloService.cambiaStato(idFascicolo, StatoFascicolo.SELECTED,  null);
						   	  											  } catch (Exception e) { log.error("Errore nell'aggiornamento dello stato del fascicolo con id="+idFascicolo,e);
						   	  											  						   }			   	  											});
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.MANDATORY, rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	private List<TipologicaDestinatarioDTO> getDestinatariEsitoCampionamento(List<FascicoloDTO> listaFascicoliAll) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");

		// utenze degli Enti Delegati che hanno registrato pratiche durante il periodo di campionamento
		// Ente che esegue la trasmissione (indirizzo ufficiale)
		// Comune in cui è localizzato l’intervento (indirizzo ufficiale, solo se diverso dall’Ente che ha trasmesso)														
		// Responsabile comunale (NO)														
		// Utente che ha inserito le informazioni (creazione, modifica, trasmissione)
		// Proponente dello strumento urbanistico esecutivo (Richiedente)
		// Regione Puglia

		Set<TipologicaDestinatarioDTO> destinatari = new HashSet<>();
		
		Map<Integer, Set<Integer>> mappaEntiDelegati = new HashMap<Integer, Set<Integer>>();
		Set<Integer> allComuni = new HashSet<>();
		listaFascicoliAll.forEach(fascicolo->{
			List<Long> enti = localizzazioneInterventoService.selectEffettivo(fascicolo.getId());
			Set<Integer> entiComuni = new HashSet<>();
			enti.forEach(elem->{
				entiComuni.add(elem.intValue());
				 allComuni.add(elem.intValue());
			});
			mappaEntiDelegati.put(fascicolo.getOrgCreazione(), entiComuni);
		});
		
		// 1) Enti Delegati che hanno trasmesso pratiche durante il periodo di campionamento
		for (@SuppressWarnings("rawtypes") HashMap.Entry fascicolo : mappaEntiDelegati.entrySet()) {
			destinatari.addAll(destinatarioService.findDestinatariForEnteDelegato((Integer) fascicolo.getKey(), (HashSet<Integer>) fascicolo.getValue(), TipoDestinatario.TO));
		}
		/* int idApplicazione = commonService.getIdApplicazione(props.getCodiceApplicazioneProfile());
		Set<Integer> listaOrganizzazioni = new HashSet<>();
		listaFascicoliAll.forEach(fascicolo->{
			listaOrganizzazioni.add(fascicolo.getOrgCreazione());
		});
		for (int idOrg : listaOrganizzazioni) {
			RubricaIstituzionaleSearch searchRI = new RubricaIstituzionaleSearch();
			searchRI.setApplicazioneId(idApplicazione);
			searchRI.setEnteId(commonService.findEnteByIdOrganizzazione(idOrg).getId());
			List<RubricaIstituzionaleDTO> infoEnti = rubricaIstituzionaleService.search(searchRI).getList();
			if (!infoEnti.isEmpty()) {
				 if (StringUtil.isNotEmpty(infoEnti.get(0).getPec ()))
					 destinatari.add(new TipologicaDestinatarioDTO(infoEnti.get(0).getPec (),  true, infoEnti.get(0).getNome(), TipoDestinatario.TO));
			else if (StringUtil.isNotEmpty(infoEnti.get(0).getMail()))
					 destinatari.add(new TipologicaDestinatarioDTO(infoEnti.get(0).getMail(), false, infoEnti.get(0).getNome(), TipoDestinatario.TO));
			} else {
				log.error("Errore nel retrieval dell'indirizzo mail per il comune con id: ", idOrg);
			}
		};	*/
		
		// 2) Comuni in cui sono localizzati gli interventi
		destinatari.addAll(destinatarioService.findDestinatariForComuni(allComuni, TipoDestinatario.TO));
		/* 
		for (long idEnte : listaEnti) {
			RubricaIstituzionaleSearch searchRI = new RubricaIstituzionaleSearch();
			searchRI.setApplicazioneId(idApplicazione);
			searchRI.setEnteId(Math.toIntExact(idEnte));
			List<RubricaIstituzionaleDTO> infoEnti = rubricaIstituzionaleService.search(searchRI).getList();
			if (!infoEnti.isEmpty()) {
				 if (StringUtil.isNotEmpty(infoEnti.get(0).getPec ()))
					 destinatari.add(new TipologicaDestinatarioDTO(infoEnti.get(0).getPec (),  true, infoEnti.get(0).getNome(), TipoDestinatario.TO));
			else if (StringUtil.isNotEmpty(infoEnti.get(0).getMail()))
					 destinatari.add(new TipologicaDestinatarioDTO(infoEnti.get(0).getMail(), false, infoEnti.get(0).getNome(), TipoDestinatario.TO));
			} else {
				log.error("Errore nel retrieval dell'indirizzo mail per il comune con id: ", idEnte);
			}
		};	*/
		
		// 3) Richiedenti dello strumento urbanistico esecutivo (in analisi è chiamato "Proponente")
		for (FascicoloDTO fascicolo : listaFascicoliAll) {
			RichiedenteDTO richiedente = richiedenteService.datiRichiedente(fascicolo.getId());
				 if (StringUtil.isNotEmpty(richiedente.getPec  ()))
					 destinatari.add(new TipologicaDestinatarioDTO(richiedente.getPec (),  true, richiedente.getNome()+" "+richiedente.getCognome(), TipoDestinatario.CC));
			else if (StringUtil.isNotEmpty(richiedente.getEmail()))
					 destinatari.add(new TipologicaDestinatarioDTO(richiedente.getPec (), false, richiedente.getNome()+" "+richiedente.getCognome(), TipoDestinatario.CC));
		};
		
		// 4) Utenti che hanno trasmesso i fascicoli
		for (FascicoloDTO fascicolo : listaFascicoliAll) {
			 destinatari.add(new TipologicaDestinatarioDTO(fascicolo.getEmailUtenteTrasmissione(), fascicolo.getDenominazioneUtenteTrasmissione(), TipoDestinatario.CC));
		};
		
		// 5) Utenti che hanno creato o salvato i fascicoli
		Set<TipologicaDTO> utenti = new HashSet<>();
		for (FascicoloDTO fascicolo : listaFascicoliAll) {
			utenti.add(new TipologicaDTO(fascicolo.getUsernameUtenteCreazione(), userUtil.getGruppo_Id(fascicolo.getUfficio())));
			if (StringUtil.isNotEmpty(fascicolo.getUsernameUtenteUltimaModifica())) {
				utenti.add(new TipologicaDTO(fascicolo.getUsernameUtenteUltimaModifica(), userUtil.getGruppo_Id(fascicolo.getUfficio())));
			}
		};
		if(StringUtil.isEmpty(LogUtil.getAuthorization())){
			//caso batch ...nessun utente loggato, uso il batch user
			String authorization=clientSvc.getBatchUser().getAuthorization();
			LogUtil.addAuthorization(authorization);
			//in modo che venga aggiunto nell'header dall'FeignRequestInterceptor	
		}
		utenti.forEach(utente->{
			String email = null;
			String denominazione = null;
			//Il metodo getUtentiGruppo attualmente è corretto solo se non si vuole effettuare una ricerca solo per gruppo
			UtenteGruppo[] infoUtenti = profileManager.getUtentiGruppo(LogUtil.getAuthorization(), props.getCodiceApplicazioneProfile(), utente.getLabel(), utente.getCodice(), true).getPayload();
			log.info("RET:"+infoUtenti);
			if (infoUtenti!=null) {
				for (UtenteGruppo infoUtente : infoUtenti) {
					email = infoUtente.getEmail();
					denominazione = infoUtente.getNomeUtente()+" "+infoUtente.getCognomeUtente();
				}
			}
			destinatari.add(new TipologicaDestinatarioDTO(email, denominazione, TipoDestinatario.CC));
		});
		
		// 6) cerco i destinatari di default
		TemplateDestinatarioSearch searchTD = new TemplateDestinatarioSearch();
		searchTD.setCodiceTemplate(TipoTemplate.ESITO_CAMPIONAMENTO);
		List<DestinatarioTemplateDTO> destinatariDefault = templateDestinatarioService.search(searchTD).getList();
		
		if (destinatariDefault!=null) {
			destinatariDefault.forEach(destinatario-> {
				destinatari.add(new DestinatarioTemplateDTO(destinatario)); 
			});
		}
		
		// 7) Regione Puglia
		destinatari.addAll(destinatarioService.findDestinatariForRegione(allComuni, TipoDestinatario.CC));
		
		return VarieUtils.eliminaDuplicati(new ArrayList<TipologicaDestinatarioDTO>(destinatari));
	}

}