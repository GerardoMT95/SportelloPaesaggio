/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.IOUtils;
import org.postgresql.util.PGobject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.autpae.civilia.migration.builder.AutpaeDtoBuilder;
import it.eng.tz.puglia.autpae.civilia.migration.dto.AutPaesPptrLocalizInterv;
import it.eng.tz.puglia.autpae.civilia.migration.dto.AutPaesPptrPratica;
import it.eng.tz.puglia.autpae.civilia.migration.dto.CiviliaMail;
import it.eng.tz.puglia.autpae.civilia.migration.dto.CiviliaMailAllegati;
import it.eng.tz.puglia.autpae.civilia.migration.dto.CiviliaPratica;
import it.eng.tz.puglia.autpae.civilia.migration.dto.Comuni_completo_cod_istat;
import it.eng.tz.puglia.autpae.civilia.migration.dto.EnteParcoAss;
import it.eng.tz.puglia.autpae.civilia.migration.dto.InfoAutPaesPptrAlfa;
import it.eng.tz.puglia.autpae.civilia.migration.dto.InteressePubblicoAss;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PaesaggioRuraleAss;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrCaratterizzazioneIntervento;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrCodiceInterno;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrGruppoUfficio;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrParticelleCatastali;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrProtocolloUscita;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrProvvedimento;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrQualificIntervento;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrSoprintendenzaNoteSt;
import it.eng.tz.puglia.autpae.civilia.migration.dto.PptrTipoIntervento;
import it.eng.tz.puglia.autpae.civilia.migration.dto.ReferentiProgettoDto;
import it.eng.tz.puglia.autpae.civilia.migration.dto.TnoMailEnti;
import it.eng.tz.puglia.autpae.civilia.migration.dto.TnoSopMatriceTerritorio;
import it.eng.tz.puglia.autpae.civilia.migration.dto.VtnoAllegatiPptr;
import it.eng.tz.puglia.autpae.civilia.migration.dto.putt.InfoAutPaesAlfaBean;
import it.eng.tz.puglia.autpae.civilia.migration.dto.putt.LocalizzazionePuttBean;
import it.eng.tz.puglia.autpae.civilia.migration.dto.putt.PuttDocBean;
import it.eng.tz.puglia.autpae.civilia.migration.dto.putt.TnoProtocolloUscita;
import it.eng.tz.puglia.autpae.civilia.migration.dto.utils.AllegatiWithMultipart;
import it.eng.tz.puglia.autpae.civilia.migration.exception.BaseMigrationException;
import it.eng.tz.puglia.autpae.civilia.migration.exception.CaratterizzazioneInterventoException;
import it.eng.tz.puglia.autpae.civilia.migration.exception.CorrispondenzaException;
import it.eng.tz.puglia.autpae.civilia.migration.exception.EnteDelegatoException;
import it.eng.tz.puglia.autpae.civilia.migration.exception.EnteDelegatoNotFoundException;
import it.eng.tz.puglia.autpae.civilia.migration.exception.FascicoloAlreadyExistsException;
import it.eng.tz.puglia.autpae.civilia.migration.exception.FileSizeToLargeException;
import it.eng.tz.puglia.autpae.civilia.migration.exception.LocalizzazioneInterventoException;
import it.eng.tz.puglia.autpae.civilia.migration.exception.ProvvedimentoException;
import it.eng.tz.puglia.autpae.civilia.migration.exception.QualificazioneInterventoException;
import it.eng.tz.puglia.autpae.civilia.migration.exception.RicevutaIstanzaException;
import it.eng.tz.puglia.autpae.civilia.migration.exception.RichiedenteException;
import it.eng.tz.puglia.autpae.civilia.migration.exception.TipoInterventoMancanteException;
import it.eng.tz.puglia.autpae.civilia.migration.service.IDatiPraticaCivService;
import it.eng.tz.puglia.autpae.civilia.migration.service.IMigratorService;
import it.eng.tz.puglia.autpae.civilia.migration.service.IProxyClientAlfresco;
import it.eng.tz.puglia.autpae.civilia.migration.service.PuttMigrationService;
import it.eng.tz.puglia.autpae.civilia.migration.territorio.MapComuniEntiDelegati;
import it.eng.tz.puglia.autpae.civilia.migration.util.MockBlob;
import it.eng.tz.puglia.autpae.config.ApplicationProperties;
import it.eng.tz.puglia.autpae.config.AutpaeConstants;
import it.eng.tz.puglia.autpae.dto.InformazioniDTO;
import it.eng.tz.puglia.autpae.entity.AllegatoCorrispondenzaDTO;
import it.eng.tz.puglia.autpae.entity.AllegatoDTO;
import it.eng.tz.puglia.autpae.entity.AllegatoFascicoloDTO;
import it.eng.tz.puglia.autpae.entity.ComuniCompetenzaDTO;
import it.eng.tz.puglia.autpae.entity.CorrispondenzaDTO;
import it.eng.tz.puglia.autpae.entity.DestinatarioDTO;
import it.eng.tz.puglia.autpae.entity.FascicoloCorrispondenzaDTO;
import it.eng.tz.puglia.autpae.entity.FascicoloDTO;
import it.eng.tz.puglia.autpae.entity.FascicoloInterventoDTO;
import it.eng.tz.puglia.autpae.entity.LocalizzazioneInterventoDTO;
import it.eng.tz.puglia.autpae.entity.ParchiPaesaggiImmobiliDTO;
import it.eng.tz.puglia.autpae.entity.ParticelleCatastaliDTO;
import it.eng.tz.puglia.autpae.entity.RicevutaDTO;
import it.eng.tz.puglia.autpae.entity.RichiedenteDTO;
import it.eng.tz.puglia.autpae.entity.TipiEQualificazioniDTO;
import it.eng.tz.puglia.autpae.enumeratori.Applicativo;
import it.eng.tz.puglia.autpae.enumeratori.EsitoControllo;
import it.eng.tz.puglia.autpae.enumeratori.EsitoVerifica;
import it.eng.tz.puglia.autpae.enumeratori.Gruppi;
import it.eng.tz.puglia.autpae.enumeratori.Ruoli;
import it.eng.tz.puglia.autpae.enumeratori.StatoCorrispondenza;
import it.eng.tz.puglia.autpae.enumeratori.StatoFascicolo;
import it.eng.tz.puglia.autpae.enumeratori.TipoAllegato;
import it.eng.tz.puglia.autpae.enumeratori.TipoDestinatario;
import it.eng.tz.puglia.autpae.enumeratori.TipoErrore;
import it.eng.tz.puglia.autpae.enumeratori.TipoLocalizzazione;
import it.eng.tz.puglia.autpae.enumeratori.TipoOrganizzazioneSpecifica;
import it.eng.tz.puglia.autpae.enumeratori.TipoRicevuta;
import it.eng.tz.puglia.autpae.generated.orm.dto.AnnotazioniInterneDTO;
import it.eng.tz.puglia.autpae.generated.orm.dto.ApplicazioneDTO;
import it.eng.tz.puglia.autpae.generated.orm.dto.PaesaggioOrganizzazioneAttributiDTO;
import it.eng.tz.puglia.autpae.generated.orm.dto.PaesaggioOrganizzazioneCompetenzeDTO;
import it.eng.tz.puglia.autpae.generated.orm.dto.PaesaggioOrganizzazioneDTO;
import it.eng.tz.puglia.autpae.generated.orm.repository.AnnotazioniInterneRepository;
import it.eng.tz.puglia.autpae.generated.orm.repository.ApplicazioneRepository;
import it.eng.tz.puglia.autpae.generated.orm.repository.PaesaggioOrganizzazioneAttributiRepository;
import it.eng.tz.puglia.autpae.generated.orm.repository.PaesaggioOrganizzazioneCompetenzeRepository;
import it.eng.tz.puglia.autpae.generated.orm.repository.PaesaggioOrganizzazioneRepository;
import it.eng.tz.puglia.autpae.generated.orm.search.ApplicazioneSearch;
import it.eng.tz.puglia.autpae.generated.orm.search.PaesaggioOrganizzazioneAttributiSearch;
import it.eng.tz.puglia.autpae.generated.orm.search.PaesaggioOrganizzazioneCompetenzeSearch;
import it.eng.tz.puglia.autpae.repository.AllegatoFascicoloFullRepository;
import it.eng.tz.puglia.autpae.repository.AllegatoFullRepository;
import it.eng.tz.puglia.autpae.repository.DestinatarioFullRepository;
import it.eng.tz.puglia.autpae.repository.FascicoloFullRepository;
import it.eng.tz.puglia.autpae.repository.FascicoloTipiProcedimentoRepository;
import it.eng.tz.puglia.autpae.repository.RicevutaFullRepository;
import it.eng.tz.puglia.autpae.repository.base.AllegatoBaseRepository;
import it.eng.tz.puglia.autpae.repository.base.ComuniCompetenzaBaseRepository;
import it.eng.tz.puglia.autpae.search.AllegatoFascicoloSearch;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoCorrispondenzaService;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoService;
import it.eng.tz.puglia.autpae.service.interfacce.AzioniService;
import it.eng.tz.puglia.autpae.service.interfacce.CorrispondenzaService;
import it.eng.tz.puglia.autpae.service.interfacce.FascicoloCorrispondenzaService;
import it.eng.tz.puglia.autpae.service.interfacce.FascicoloInterventoService;
import it.eng.tz.puglia.autpae.service.interfacce.FascicoloService;
import it.eng.tz.puglia.autpae.service.interfacce.LocalizzazioneInterventoService;
import it.eng.tz.puglia.autpae.service.interfacce.ParchiPaesaggiImmobiliService;
import it.eng.tz.puglia.autpae.service.interfacce.ParticelleCatastaliService;
import it.eng.tz.puglia.autpae.service.interfacce.RichiedenteService;
import it.eng.tz.puglia.autpae.service.interfacce.TipiEQualificazioniService;
import it.eng.tz.puglia.autpae.utility.CheckSumUtil;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.error.exception.CustomCmisException;
import it.eng.tz.puglia.service.http.IHttpClientService;
import it.eng.tz.puglia.servizi_esterni.profileManager.dto.GruppoBean;
import it.eng.tz.puglia.servizi_esterni.profileManager.dto.PMApplication;
import it.eng.tz.puglia.servizi_esterni.profileManager.dto.PMGroupsRequestBean;
import it.eng.tz.puglia.servizi_esterni.profileManager.feign.IProfileManager;
import it.eng.tz.puglia.servizi_esterni.remote.dto.EnteDTO;
import it.eng.tz.puglia.servizi_esterni.remote.dto.PaesaggioEmailDTO;
import it.eng.tz.puglia.servizi_esterni.remote.repository.CommonRepository;
import it.eng.tz.puglia.servizi_esterni.remote.search.PaesaggioEmailSearch;
import it.eng.tz.puglia.servizi_esterni.remote.service.interfacce.CommonService;
import it.eng.tz.puglia.servizi_esterni.remote.utility.TipoEnte;
import it.eng.tz.puglia.util.string.StringUtil;
import it.eng.tz.regione.puglia.webmail.be.mail.ricevuta.daticert.Destinatari;
import it.eng.tz.regione.puglia.webmail.be.mail.ricevuta.daticert.Postacert;
import net.sf.jasperreports.export.CommonExportConfiguration;

/**
 * @author Adriano Colaianni
 * @date 23 apr 2021
 */
@Service
@ConditionalOnProperty("datasource.civ.enableMigration")
public class MigratorService implements IMigratorService {

	@Value("${cms.url.getObjectByPath}")
	private String cmsUrlGetObjectByPath;

	@Value("${cms.codice.applicazione}")
	private String nomeApplicazione;

	@Autowired
	IDatiPraticaCivService civSvc;
	@Autowired
	FascicoloService fascicoloSvc;
	@Autowired
	FascicoloFullRepository fascicoloDao;
	@Autowired
	CommonService commonSvc;
	@Autowired
	FascicoloInterventoService interventoSvc;
	@Autowired
	ComuniCompetenzaBaseRepository comuniCompDao;
	@Autowired
	TipiEQualificazioniService tipiEqualSvc;
	@Autowired
	RichiedenteService richiedenteSvc;
	@Autowired
	LocalizzazioneInterventoService locIntSvc;
	@Autowired
	ParticelleCatastaliService particelleSvc;
	@Autowired
	ParchiPaesaggiImmobiliService vincoliSvc;
	@Autowired
	AllegatoService allegatoSvc;
	@Autowired
	CorrispondenzaService corrispondenzaSvc;
	@Autowired
	FascicoloCorrispondenzaService fascicoloCorrispondenzaSvc;
	@Autowired
	DestinatarioFullRepository destinatariDao;
	@Autowired
	AllegatoCorrispondenzaService allCorrSvc;
	@Autowired
	AzioniService azioniSvc;
	@Autowired
	IHttpClientService httpSvc;
	@Autowired
	AllegatoBaseRepository allegatoDao;
	@Autowired
	AllegatoFullRepository allegatoFullDao;
	@Autowired
	RicevutaFullRepository ricevutaDao;
	@Autowired
	PaesaggioOrganizzazioneRepository paesaggioOrganizzazioneDao;
	@Autowired
	PaesaggioOrganizzazioneCompetenzeRepository paesaggioOrganizzazioneCompetenzeDao;
	@Autowired
	PaesaggioOrganizzazioneAttributiRepository paesaggioOrganizzazioneAttributiDao;
	@Autowired
	ApplicazioneRepository appDao;
	@Autowired
	IProfileManager profileManager;
	@Autowired
	CommonRepository commonDao;
	
	@Autowired
	AnnotazioniInterneRepository annotazioniDao;
	
	@Autowired
	PuttMigrationService puttMigService;
	@Autowired
	AllegatoService allegatoService;
	@Autowired
	private FascicoloTipiProcedimentoRepository fascicoloProcedimentiAttiviRepo;
	@Autowired 
	private ApplicationProperties props;

	@Value("${cms.url.upload}")
	private String uploadUrl;
	
	@Value("${cms.url.delete}")
	private String deleteUrl;
	
	@Value("${migration.noallegati:false}")
	private boolean noAllegati;
	
	
	@Value("${migration.cms.maxuploadsize}")
	private long maxFileUploadCms;
	

	@Value("${migration.local.basepath}")
	private String localBasePath;

	private GruppoBean[] infoGruppi;// tutti i gruppi (cacheizzati)
	private PMApplication pmAppData;
	private ApplicazioneDTO applicazioneDTO;

	private List<Comuni_completo_cod_istat> comuniCompletoCodIstat;// li cache-izzo
	private List<EnteDTO> entiCommon;// li cache-izzo

	@Autowired
	IProxyClientAlfresco proxyAlfrescoOrigin;
	
	private static final Logger log = LoggerFactory.getLogger(MigratorService.class);
	
	MapComuniEntiDelegati mapComuniEnti = null;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = false)
	public void migraPratica(InfoAutPaesPptrAlfa infoPratica) throws Exception {
		this.migraPratica(infoPratica, false);
	}
	
	private void initComuniData() throws Exception {
		if (comuniCompletoCodIstat == null) {
			comuniCompletoCodIstat = civSvc.getComuniCompletoCodIstat();
		}
		if (entiCommon == null) {
			entiCommon = commonSvc.getAllEnti();
		}
		if(mapComuniEnti==null) {
			mapComuniEnti=new MapComuniEntiDelegati(this.comuniCompletoCodIstat);	
		}

	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = false)
	public void migraPratica(InfoAutPaesPptrAlfa infoPratica,boolean inLavorazione) throws Exception {
		AutpaeDtoBuilder.localBasePath = localBasePath;
		Map<Long, Long> comuniInterventoMap = new HashMap<>();
		// init comuniCompleto...
		this.initComuniData();
		// cerco se già esiste
		Integer idFascicoloPreesistente = null;
		try {
			idFascicoloPreesistente = this.fascicoloDao.findByTPraticaId(infoPratica.gettPraticaAppptrId());
		} catch (EmptyResultDataAccessException e) {
		}
		if (idFascicoloPreesistente != null) {
			throw new FascicoloAlreadyExistsException(infoPratica, "Fascicolo già esistente", null);
		}
		// calcolo del nostro campo ufficio a partire dal loro ...
		Integer idPaesaggioOrganizzazione = null;
		try {
			idPaesaggioOrganizzazione = commonSvc.findEnteDelegatoFromCodiceCivilia(infoPratica.getIstatAmm(),
					Gruppi.ED_);
		} catch (EmptyResultDataAccessException e) {
		}
		if (idPaesaggioOrganizzazione == null) {
			// potrebbe essere una associazione di comuni...vediamo quante ne sono e nel
			// caso mettiamo pezza
			throw new EnteDelegatoNotFoundException(infoPratica,
					"ente delegato non trovato con codice civilia " + infoPratica.getIstatAmm(), null);
		}
		// creo il fascicolo base:
		FascicoloDTO fascicoloDTO = AutpaeDtoBuilder.buildFascicolo(infoPratica);
		fascicoloDTO.setUfficio(Gruppi.ED_.name() + idPaesaggioOrganizzazione + "_F"); // simulo Funzionario...
		fascicoloDTO.setOrgCreazione(idPaesaggioOrganizzazione);
		if(inLavorazione) {
			fascicoloDTO.setDataCreazione(infoPratica.getDataCreazione());
		}else {
			fascicoloDTO.setDataCreazione(infoPratica.getDataTrasmissione());	
		}
		fascicoloDTO.setStato(StatoFascicolo.WORKING);
		fascicoloDTO.settPraticaId(infoPratica.gettPraticaAppptrId());
		fascicoloDTO.setCodicePraticaAppptr(infoPratica.getCodicePraticaAppptr());
		AutPaesPptrPratica pptrPratica = civSvc.getAutPaesPptrPratica(infoPratica.getCodicePraticaAppptr(),
				infoPratica.getProgPubblicazione());
		if(pptrPratica==null) {
			throw new Exception("Nessun record in PPTR_PRATICA codiceAppptr:"+infoPratica.getCodicePraticaAppptr()+" prog:"+infoPratica.getProgPubblicazione()); 
		}
		fascicoloDTO = AutpaeDtoBuilder.buildFascicolo(fascicoloDTO, pptrPratica);
		// pannello fascicolo
		List<PptrCodiceInterno> pptrEntes = civSvc.getDatiCodiceInterno(infoPratica.getCodicePraticaAppptr(),
				infoPratica.getProgPubblicazione());
		if (ListUtil.isNotEmpty(pptrEntes)) {
			PptrCodiceInterno pptrEnte = pptrEntes.get(0);
			fascicoloDTO = AutpaeDtoBuilder.buildFascicolo(fascicoloDTO, pptrEnte);
		}
		// qui ho creato il record....
		fascicoloDTO = fascicoloSvc.creaDaMigrazione(fascicoloDTO); // setta l'id
		fascicoloProcedimentiAttiviRepo.insertOnMigrateFascicolo(fascicoloDTO.getId(), 
				Applicativo.valueOf(props.getCodiceApplicazione().toUpperCase()),
				fascicoloDTO.getDataCreazione(),fascicoloDTO.getTipoProcedimento().name());
		// se è di quest'anno, mando avanti il progressivo rispetto all'attuale
		// codice...
		String[] tokensCodice = infoPratica.getCodicePraticaEnteDelegato().split("-");
		if (tokensCodice[0].startsWith("AP")) {
			commonSvc.updateProgressiviCodiceFascicolo(infoPratica.getCodicePraticaEnteDelegato(),
					infoPratica.getIstatAmm());
		} else if (tokensCodice[0].startsWith("AUTPAESAF")) {
			// da loro istatAmm per regione è 016
			commonSvc.updateProgressiviCodiceFascicolo(infoPratica.getCodicePraticaEnteDelegato(), "00016");
		}
		// devo creare i comuni di competenza mappandoli dagli attuali loro (solo quelli
		// selezionati sulla pratica)
		List<AutPaesPptrLocalizInterv> locIntervs = civSvc
				.getLocalizzazioneIntervento(infoPratica.getCodicePraticaAppptr(), infoPratica.getProgPubblicazione());
		if (ListUtil.isEmpty(locIntervs) && !inLavorazione) {
			throw new LocalizzazioneInterventoException(infoPratica, "Manca Localizzazione intervento ", null);
		}
		List<Integer> istatComuniElaborati=new ArrayList<>();
		if(!ListUtil.isEmpty(locIntervs)) {
			for (AutPaesPptrLocalizInterv locInt : locIntervs)
				aggiungiComuneCompetenza(infoPratica, comuniInterventoMap, fascicoloDTO, istatComuniElaborati, locInt.getLocalizIntervComuneId(),locInt.getComune());	
		}
		if(inLavorazione && ListUtil.isEmpty(locIntervs)) {
			//in questo caso i comuni di competenza li dovrei prendere dalla map comuni
			List<Comuni_completo_cod_istat> listaComuni = mapComuniEnti.getListaComuni_ByCodicePratica(infoPratica.getCodicePraticaEnteDelegato(), null);
			for(Comuni_completo_cod_istat comune:listaComuni) {
				aggiungiComuneCompetenza(infoPratica, comuniInterventoMap, fascicoloDTO, istatComuniElaborati, comune.getNumericIstat6Prov(),comune.getCodiceCatastale());
			}
		}
		// pannello richiedente
		List<ReferentiProgettoDto> referentiAsIs = civSvc.getRichiedente(infoPratica.gettPraticaAppptrId(),
				infoPratica.getProgPubblicazione());
		if (ListUtil.isNotEmpty(referentiAsIs)) {
			// composizione richiedente
			RichiedenteDTO richiedenteNew = AutpaeDtoBuilder.buildRichiedente(referentiAsIs.get(0));
			richiedenteNew.setIdFascicolo(fascicoloDTO.getId());
			richiedenteSvc.inserisci(richiedenteNew);
		} else {
			if(!inLavorazione) {
				throw new RichiedenteException(infoPratica, "Manca soggetto richiedente in as is ", null);	
			}else {
				RichiedenteDTO richiedenteNew = new RichiedenteDTO();
				richiedenteNew.setIdFascicolo(fascicoloDTO.getId());
				richiedenteSvc.inserisci(richiedenteNew);
			}
		}
		// pannello descrizione intervento
		// tipoIntervento
		List<PptrTipoIntervento> tipoInterventos = civSvc.getDatiTipoIntervento(infoPratica.gettPraticaAppptrId(),
				infoPratica.getProgPubblicazione());
		if (ListUtil.isNotEmpty(tipoInterventos)) {
			PptrTipoIntervento item = tipoInterventos.get(0);
			List<FascicoloInterventoDTO> tipoIntervs = AutpaeDtoBuilder.buildFascicoloTipoIntervento(fascicoloDTO, item,
					fascicoloDTO.getId(), infoPratica);
			for (FascicoloInterventoDTO entity : tipoIntervs) {
				interventoSvc.insert(entity);
			}
		} else {
			if(!inLavorazione) {
				throw new TipoInterventoMancanteException(infoPratica, "Manca tipo intervento in pannello Intervento",
						null);	
			}
		}
		// caratterizzazione intervento : checkbox a selezione multipla...+ temporaneo e
		// permanente + textbox altro o dettagliare
		List<PptrCaratterizzazioneIntervento> carattIntervs = civSvc.getDatiCaratterizzazioneIntervento(
				infoPratica.gettPraticaAppptrId(), infoPratica.getProgPubblicazione());
		if (ListUtil.isNotEmpty(carattIntervs)) {
			PptrCaratterizzazioneIntervento item = carattIntervs.get(0);
			List<FascicoloInterventoDTO> carattInts = AutpaeDtoBuilder.buildFascicoloIntervento(fascicoloDTO, item,
					fascicoloDTO.getId(), infoPratica);
			for (FascicoloInterventoDTO entity : carattInts) {
				interventoSvc.insert(entity);
			}
		} else {
			if(!inLavorazione) {
				throw new CaratterizzazioneInterventoException(infoPratica, "Manca caratterizzazione intervento ", null);
			}
		}
		// qualificazione intervento : checkbox a selezione multipla...
		List<PptrQualificIntervento> qualIntervs = civSvc
				.getDatiQualificazioneIntervento(infoPratica.gettPraticaAppptrId(), infoPratica.getProgPubblicazione());
		if (ListUtil.isNotEmpty(qualIntervs)) {
			PptrQualificIntervento item = qualIntervs.get(0);
			List<FascicoloInterventoDTO> carattInts = AutpaeDtoBuilder.buildQualificazioneIntervento(item,
					fascicoloDTO.getTipoProcedimento(), fascicoloDTO.getId());
			if (ListUtil.isEmpty(carattInts)) {
				log.warn(IMigratorService.LOG_MIGRAZIONE_MARKER,"Nessun valore di qualificazione intervento intercettato {}",item);
			}
			// qui controllo eventuali tipi non ammessi dal tipo procedimento....
			List<TipiEQualificazioniDTO> tipiAmmessi = this.tipiEqualSvc
					.selectByCodiceTipoProcedimento(fascicoloDTO.getTipoProcedimento(),fascicoloDTO.getDataCreazione(),fascicoloDTO.getUfficio());
			List<FascicoloInterventoDTO> nonAmmessi = carattInts.stream().filter(el -> {
				boolean isAmmesso = tipiAmmessi.stream()
						.filter(tipoAmmesso -> tipoAmmesso.getId().equals(el.getIdTipiQualificazioni())).findAny()
						.isPresent();
				return !isAmmesso;
			}).collect(Collectors.toList());
			if (ListUtil.isNotEmpty(nonAmmessi)) {
				throw new QualificazioneInterventoException(infoPratica,
						"Alcuni valori di tipi e qualificazioni per la qualificazione intervento non risultano ammissibili "
								+ nonAmmessi,
						null);
			}
			for (FascicoloInterventoDTO entity : carattInts) {
				interventoSvc.insert(entity);
			}
		} else {
			if(!inLavorazione) {
				throw new CaratterizzazioneInterventoException(infoPratica, "Manca caratterizzazione intervento ", null);
			}
		}
		// pannello localizzazione....
		fascicoloDTO.setTipoSelezioneLocalizzazione(TipoLocalizzazione.PTC);
		// creo i comuni dell'intervento
		List<Long> istatComuniLocIntElaborati=new ArrayList<>();
		if(ListUtil.isNotEmpty(locIntervs)) {
			for (AutPaesPptrLocalizInterv locIntervOld : locIntervs) {
				LocalizzazioneInterventoDTO locInterventoNew = AutpaeDtoBuilder.buildLocalizzazione(locIntervOld);
				Long idComuneNew = comuniInterventoMap.get(locIntervOld.getLocalizIntervComuneId());
				if (idComuneNew == null) {
					throw new LocalizzazioneInterventoException(infoPratica,
							"errore nel retrieve del comuneId new dal mapping, valore:"
									+ locIntervOld.getLocalizIntervComuneId(),
							null);
				}
				if(istatComuniLocIntElaborati.stream().filter(el->el==idComuneNew).findAny().isPresent())
				{
					log.warn(IMigratorService.LOG_MIGRAZIONE_MARKER,"Comune intervento doppio {} " ,idComuneNew );
					continue;
				}
				istatComuniLocIntElaborati.add(idComuneNew);
				
				locInterventoNew.setComuneId(idComuneNew);
				this.entiCommon.stream().filter(ente -> ente.getId() == idComuneNew.intValue()).findAny()
						.ifPresent(ente -> locInterventoNew.setComune(ente.getDescrizione()));
				locInterventoNew.setPraticaId(fascicoloDTO.getId());
				locIntSvc.insert(locInterventoNew);
				// creo le particelle
				List<PptrParticelleCatastali> particelleOld = civSvc.getParticelleCatastaliLocalizInterv(
						locIntervOld.getLocalizIntervId(), infoPratica.gettPraticaAppptrId(),
						infoPratica.getProgPubblicazione());
				List<ParticelleCatastaliDTO> particelleNew = AutpaeDtoBuilder.buildLocalizzazioneParticelle(particelleOld,
						locInterventoNew);
				for (ParticelleCatastaliDTO particellaNew : particelleNew) {
					particelleSvc.insert(particellaNew);
				}
				// parchi paesaggi immobili
				List<EnteParcoAss> parchiOld = civSvc.getEnteParcoAss(infoPratica.getCodicePraticaEnteDelegato(),
						locIntervOld.getLocalizIntervComuneId() + "", infoPratica.getProgPubblicazione());
				List<PaesaggioRuraleAss> paesaggiOld = civSvc.getPaesaggioRuraleAss(infoPratica.getCodicePraticaAppptr(),
						locIntervOld.getLocalizIntervComuneId() + "", infoPratica.getProgPubblicazione());
				List<InteressePubblicoAss> immobiliOld = civSvc.getInteressePubblicoAss(
						infoPratica.getCodicePraticaAppptr(), locIntervOld.getLocalizIntervComuneId() + "",
						infoPratica.getProgPubblicazione());
				List<ParchiPaesaggiImmobiliDTO> vincoliNew = AutpaeDtoBuilder.buildPrachiPaesaggiImmobili(
						fascicoloDTO.getId(), idComuneNew, parchiOld, immobiliOld, paesaggiOld);
				if (ListUtil.isNotEmpty(vincoliNew)) {
					vincoliNew.forEach(vincoloNew -> {
						vincoliSvc.insert(vincoloNew);
					});
				}
			}
		}
		// pannello provvedimento
		List<PptrProvvedimento> provvedimentoOld = civSvc.getProvvedimento(infoPratica.getCodicePraticaAppptr(),
				infoPratica.getProgPubblicazione());
		if (ListUtil.isEmpty(provvedimentoOld) && !inLavorazione) {
			throw new ProvvedimentoException(infoPratica, "Nessun record provvedimento trovato !!!", null);	
		}
		if(ListUtil.isNotEmpty(provvedimentoOld)) {
			AutpaeDtoBuilder.addProvvedimentoField(fascicoloDTO, provvedimentoOld.get(0), infoPratica);
		}
		fascicoloSvc.updateForMigration(fascicoloDTO); // update
		// provvedimento parte allegati...
		List<VtnoAllegatiPptr> allegatiProvvedimentoOld = civSvc
				.getListAllegatiProvvedimento(infoPratica.getCodicePraticaAppptr(), infoPratica.getProgPubblicazione());
		loadBlobFromAlfresco(allegatiProvvedimentoOld);
		AutpaeDtoBuilder.addProvvedimentoAttachment(infoPratica, fascicoloDTO, allegatiProvvedimentoOld, allegatoSvc,noAllegati,inLavorazione);	
		// pannello allegati
		// sono allegati multitipo...
		List<VtnoAllegatiPptr> allegatiPraticaOld = civSvc
				.getListAllegatiFascicolo(infoPratica.getCodicePraticaAppptr());
		loadBlobFromAlfresco(allegatiPraticaOld);
		AutpaeDtoBuilder.addAttachment(infoPratica, fascicoloDTO, allegatiPraticaOld, allegatoSvc,noAllegati);
		// aggiorno lo stato a finished
		if(!inLavorazione) {
			fascicoloDTO.setDataTrasmissione(infoPratica.getDataTrasmissione());
			fascicoloDTO.setStato(StatoFascicolo.FINISHED);
			fascicoloDTO.setEsitoVerifica(EsitoVerifica.NOT_SAMPLED);
			fascicoloDTO.setUsernameUtenteTrasmissione(pptrPratica.getJbpUname());
		}
		fascicoloDTO.setUsernameUtenteCreazione(pptrPratica.getJbpUname());
		fascicoloDTO.setUsernameUtenteUltimaModifica(pptrPratica.getJbpUname());
		fascicoloSvc.updateForMigration(fascicoloDTO); // update
		if(!inLavorazione) {
			migraDatiPostTrasmissione(infoPratica, fascicoloDTO, pptrPratica, istatComuniLocIntElaborati);	
		}
		log.info(LOG_MIGRAZIONE_MARKER,
				"pratica " + infoPratica.getCodicePraticaEnteDelegato() + " migrata con successo");
	}

	private void aggiungiComuneCompetenza(InfoAutPaesPptrAlfa infoPratica, Map<Long, Long> comuniInterventoMap,
			FascicoloDTO fascicoloDTO, List<Integer> istatComuniElaborati, long istat6ProvComune,String descrizioneComune)
			throws LocalizzazioneInterventoException, Exception {
		{
			Optional<Comuni_completo_cod_istat> optComune = comuniCompletoCodIstat.stream()
					.filter(el -> el.getNumericIstat6Prov() == istat6ProvComune).findAny();
			if (optComune.isEmpty()) {
				throw new LocalizzazioneInterventoException(infoPratica, "Record comune completo non trovato "
						+ istat6ProvComune + " " + descrizioneComune, null);
			}
			if(istatComuniElaborati.stream().filter(el->el==optComune.get().getNumericIstat6Prov()).findAny().isPresent())
			{
				log.warn(IMigratorService.LOG_MIGRAZIONE_MARKER,"Comune di competenza doppio {} " ,optComune.get() );
				return;
			}
				
			istatComuniElaborati.add(optComune.get().getNumericIstat6Prov());
			ComuniCompetenzaDTO entity = new ComuniCompetenzaDTO();
			entity.setPraticaId(fascicoloDTO.getId());
			entity.setCreazione(false);
			entity.setEffettivo(true);
			entity.setDataInserimento(infoPratica.getDataTrasmissione());
			entity.setCodCat(optComune.get().getCodiceCatastale());
			entity.setCodIstat(optComune.get().getNumericIstat6Prov() + "");
			// cerco in common.ente il relativo ente tramite codice catastale....
			Optional<EnteDTO> optEnte = entiCommon.stream()
					.filter(ente -> ente.getCodice().equalsIgnoreCase(entity.getCodCat())
							&& ente.getTipo().equals(TipoEnte.comune))
					.findFirst();
			if (optEnte.isEmpty()) {
				throw new LocalizzazioneInterventoException(infoPratica,
						"Record comune common.ente non trovato cod.catastale:" + entity.getCodCat() + " "
								+ descrizioneComune,
						null);
			}
			entity.setEnteId(optEnte.get().getId());
			entity.setDescrizione(optEnte.get().getDescrizione());
			comuniCompDao.insert(entity);
			entity.setCreazione(true);
			entity.setEffettivo(false);
			
			comuniCompDao.insert(entity);
			comuniInterventoMap.put(istat6ProvComune, entity.getEnteId());// mi serve dopo per inserire
																							// i record di
																							// localizzazione_intervento
		}
	}

	private void migraDatiPostTrasmissione(InfoAutPaesPptrAlfa infoPratica, FascicoloDTO fascicoloDTO,
			AutPaesPptrPratica pptrPratica, List<Long> istatComuniLocIntElaborati)
			throws Exception, SQLException, RicevutaIstanzaException, CorrispondenzaException {
		InformazioniDTO info = fascicoloSvc.datiCompleti(fascicoloDTO.getId());
		PGobject jsonInfo = azioniSvc.creaJsonInfo(info);
		fascicoloSvc.aggiornaJsonInfo(fascicoloDTO.getId(), jsonInfo);
		fascicoloDTO = fascicoloSvc.find(fascicoloDTO.getId());// rileggo
		// inserisco il file ricevuta
		List<PptrProtocolloUscita> ricTrasmsAll = civSvc.getRicevutaTrasmissione(infoPratica.getCodicePraticaAppptr(),
				infoPratica.getProgPubblicazione());
		if (ListUtil.isEmpty(ricTrasmsAll)) {
			throw new RicevutaIstanzaException(infoPratica, "Nessun record ricevuta Trasmissione trovato !!!", null);
		}
		List<PptrProtocolloUscita> ricTrasms = ricTrasmsAll.stream().filter(el->el.getEsitoProtocollazione().equalsIgnoreCase("SI")).collect(Collectors.toList());
		if (ListUtil.isEmpty(ricTrasms)) {
			throw new RicevutaIstanzaException(infoPratica, "Nessun record ricevuta Trasmissione con esito_protocollazione a SI trovato !!!", null);
		}
		PptrProtocolloUscita ricevuta = ricTrasms.get(0);
		AutpaeDtoBuilder.inserisciRicevutaTrasmissione(ricevuta, allegatoSvc, fascicoloDTO,noAllegati,infoPratica);
		// migrazione mail
		migraMailPraticaPptr(infoPratica, fascicoloDTO, pptrPratica);
		//inserisco eventuali NOTE SBAP
		List<PptrSoprintendenzaNoteSt> sopNotes = civSvc.getTnoPptrSopNote(infoPratica.getCodicePraticaAppptr(),infoPratica.getProgPubblicazione());
		if(ListUtil.isNotEmpty(sopNotes)) {
			PptrSoprintendenzaNoteSt sopNote = sopNotes.get(0);
			if(sopNote.getSpunta()!=0L || StringUtil.isNotEmpty(sopNote.getNote())) {
				AnnotazioniInterneDTO sbapNote=new AnnotazioniInterneDTO();
				sbapNote.setIdFascicolo(fascicoloDTO.getId());
				List<Integer> comuniIds = istatComuniLocIntElaborati.stream().map(lval->lval.intValue()).collect(Collectors.toList());
				if(!ListUtil.isEmpty(comuniIds)) {
					List<Integer> sbaps = commonSvc.getOrganizzazioniCompetentiOnEnti(comuniIds, props.getCodiceApplicazione(), Gruppi.SBAP_, null);
					if(ListUtil.isNotEmpty(sbaps)) {
						sbapNote.setIdOrganizzazione(sbaps.get(0));
						EsitoControllo esito = EsitoControllo.fromCiviliaCode((int)sopNote.getSpunta());
						sbapNote.setEsitoControllo(esito!=null?esito.getTextEsito():null);
						sbapNote.setNote(sopNote.getNote()==null?"":sopNote.getNote());
						sbapNote.setUserCreated("migrazione");
						sbapNote.setDateCreated(new Timestamp(new Date().getTime()));
						annotazioniDao.insert(sbapNote);
						log.info(LOG_MIGRAZIONE_MARKER,
								"Inserite annotazioni SBAP per pratica " + infoPratica.getCodicePraticaEnteDelegato() + "");	
					}else {
						log.warn(LOG_MIGRAZIONE_MARKER,
								"SBAP non trovata a partire dai comuni (enteId) "+comuniIds+ " pratica "+ infoPratica.getCodicePraticaEnteDelegato() + "");
					}
						
				}else {
					log.warn(LOG_MIGRAZIONE_MARKER,
							"Comuni intervento per la ricerca della SBAP nulli " + infoPratica.getCodicePraticaEnteDelegato() + "");
				}
			}
		}
	}

	private void migraMailPraticaPptr(InfoAutPaesPptrAlfa infoPratica, FascicoloDTO fascicoloDTO,
			AutPaesPptrPratica pptrPratica) throws Exception, CorrispondenzaException, SQLException {
		List<CiviliaMail> mailsCivilia = civSvc.getCiviliaEmail(infoPratica.gettPraticaAppptrId(),false);
		if (ListUtil.isNotEmpty(mailsCivilia)) {
			for (CiviliaMail mail : mailsCivilia) {
				Map<String, Long> destinatariIdMap = new HashMap<>();
				if (mail.getVerso().equalsIgnoreCase("U")) {
					CorrispondenzaDTO mailNew = new CorrispondenzaDTO();
					mailNew.setBozza(false);
					mailNew.setComunicazione(false);
					mailNew.setDataInvio(new Date(mail.getCcTimeStamp()));
					mailNew.setMessageId(mail.getMessageId());
					mailNew.setMittenteDenominazione(mail.getMittente());
					mailNew.setMittenteEmail(mail.getMittente());
					mailNew.setMittenteUsername(pptrPratica.getJbpUname());
					String[] tokens = fascicoloDTO.getUfficio().split("_");
					String mittenteEnte = tokens[0] + "_" + tokens[1];
					mailNew.setMittenteEnte(mittenteEnte);
					mailNew.setOggetto(mail.getOggetto());
					mailNew.setTesto(mail.getTesto());
					Long idCorrispondenza = corrispondenzaSvc.insert(mailNew, true);
					FascicoloCorrispondenzaDTO fascicoloCorrispondenza = new FascicoloCorrispondenzaDTO(null,
							idCorrispondenza, fascicoloDTO.getId());
					fascicoloCorrispondenzaSvc.insert(fascicoloCorrispondenza);
					// elaborazione destinatari
					if (StringUtil.isEmpty(mail.getDestinatario())) {
						throw new CorrispondenzaException(infoPratica, "Nessun destinatario trovato nella mail con id"
								+ mail.gettMailInviateId() + " oggetto: " + mail.getOggetto() + " !!!", null);
					}
					String[] mailDestinatari = mail.getDestinatario().split(",");
					addDestinatariCorrispondenza(mail, destinatariIdMap, idCorrispondenza, mailDestinatari,TipoDestinatario.TO);
					List<CiviliaMailAllegati> allegatiMailCivilia = civSvc
							.getCiviliaEmailAllegati(mail.gettMailInviateId(),false);
					mail.setAllegatiMail(allegatiMailCivilia);
					// elaborazione allegati
					if (ListUtil.isNotEmpty(allegatiMailCivilia) && !noAllegati) {
						for (CiviliaMailAllegati allegatoMailCivilia : allegatiMailCivilia) {
							AllegatoDTO ret = AutpaeDtoBuilder.inserisciAllegatoMail(allegatoMailCivilia, allegatoSvc,
									fascicoloDTO, mailNew.getDataInvio(), TipoAllegato.COMUNICAZIONE,noAllegati);
							Long idAllegato = ret.getId();
							AllegatoCorrispondenzaDTO allCorrDTO = new AllegatoCorrispondenzaDTO(idAllegato,
									idCorrispondenza);
							allCorrSvc.insert(allCorrDTO);	
						}
					}
					// elaborazione delle ricevute:
					for (CiviliaMail ricevutaPec : mailsCivilia) {
						// filtro solo quelle con verso Ingresso
						if (ricevutaPec.getVerso().equalsIgnoreCase("I")) {
							// prendo gli allegati
							List<CiviliaMailAllegati> allegatiRicevutaPecCivilia = civSvc
									.getCiviliaEmailAllegati(ricevutaPec.gettMailInviateId(),false);
							ricevutaPec.setAllegatiMail(allegatiRicevutaPecCivilia);
							// se ha daticert.xml lo parso e prelevo l'indirizzo destinatario
							if (ListUtil.isNotEmpty(allegatiRicevutaPecCivilia)) {
								final FascicoloDTO fascicoloLocalDTO = fascicoloDTO;
								allegatiRicevutaPecCivilia.stream()
										.filter(all -> all.getNomeFile().equalsIgnoreCase("DATICERT.XML")).findAny()
										.ifPresent(all -> {
											// parso il daticert.xml e prelevo tipo ricevuta,
											// destinatario,message-id-originale
											// Blob xmlContent=all.getBinContent();
											// xmlContent.getBinaryStream().readAllBytes()
											try {
												elaboraXmlRicevuta(ricevutaPec, all, mailNew.getMessageId(),
														idCorrispondenza, destinatariIdMap, fascicoloLocalDTO);
											} catch (Exception e) {
												log.error(LOG_MIGRAZIONE_MARKER, "Errore nella elaboraXmlRicevuta "+fascicoloLocalDTO.getCodice(), e);
											}
										});
							}
						}
					}
				}
			}
		}
	}

	private void addDestinatariCorrispondenza(CiviliaMail mail, Map<String, Long> destinatariIdMap,
			Long idCorrispondenza, String[] mailDestinatari,TipoDestinatario tipo) throws Exception {
		for (String mailDestinatario : mailDestinatari) {
			DestinatarioDTO destinDTO = new DestinatarioDTO(mailDestinatario, StatoCorrispondenza.INVIATA);
			destinDTO.setTipo(tipo);
			destinDTO.setNome(mailDestinatario);
			destinDTO.setIdCorrispondenza(idCorrispondenza);
			destinDTO.setPec(mail.getIsPec() != null && mail.getIsPec().equalsIgnoreCase("1"));
			Long idDest = destinatariDao.insert(destinDTO);
			// mi tengo una mappa con indirizzoDest e id
			destinatariIdMap.put(mailDestinatario, idDest);
		}
	}
	
	private void loadBlobFromPuttAlfresco(List<PuttDocBean> listaAllegati) throws IOException {
		if(noAllegati) return;
		if(ListUtil.isNotEmpty(listaAllegati)) {
			for(PuttDocBean allegato:listaAllegati) {
				 Blob blob = this.buildBlobMock(allegato.getAbinContent(),allegato.getIdToDownloadFromAlfresco(),allegato.getNomeFile());
				 allegato.setAbinContent(blob);
			}
		}
	}
	
	private void loadBlobFromAlfresco(List<VtnoAllegatiPptr> listaAllegati) throws IOException {
		if(noAllegati) return;
		if(ListUtil.isNotEmpty(listaAllegati)) {
			for(VtnoAllegatiPptr allegato:listaAllegati) {
				 Blob blob = this.buildBlobMock(allegato.getBinContent(),allegato.getIdToDownloadFromAlfresco(),allegato.getNomeFile());
				 allegato.setBinContent(blob);
			}
		}
	}
	
	private Blob buildBlobMock(Blob fileData,String idAlfresco,String nomeFile) throws IOException{
		Blob retBlob=fileData;
		long length=0;
		try{
			if(retBlob!=null) {
				length=retBlob.length();	
			}
		}catch(Exception e) {
			log.info(IMigratorService.LOG_MIGRAZIONE_MARKER,"Blob length error {}",nomeFile);
		}
		//if(StringUtil.isNotBlank(idAlfresco) && length==0) {
		if(StringUtil.isNotBlank(idAlfresco)) {
			//prelevo da alfresco e poggio su file temporaneo
			File file=proxyAlfrescoOrigin.getDocumentIntoLocalFile(idAlfresco, null);
			log.info(IMigratorService.LOG_MIGRAZIONE_MARKER,"Downloaded from alfresco {}  size {}",nomeFile,file.length());
			retBlob=new MockBlob(file);
		}
		return retBlob;
	}
	
	
	/**
	 * verifica se appartiene alla mail in uscita tramite message-id e se si lo
	 * inserisce nella tabella ricevute solo per i tipi 
	 * TipoRicevuta.ACCETTAZIONE,TipoRicevuta.AVVENUTA_CONSEGNA,TipoRicevuta.NON_ACCETTAZIONE,TipoRicevuta.ERRORE_CONSEGNA 
	 * okkio alla properties javax.xml.accessExternalDTD=all altrimenti in caso di xml con tag dtd non passa l'unmarshal
	 * @param ricevutaPec
	 * @autor Adriano Colaianni
	 * @date 28 apr 2021
	 * @param xmlContent
	 * @param messageIdOriginale message-id dell mail inviata
	 * @param idCorrispondenza   id record corrispondenza della mail inviata
	 * @param destinatariMap     mappa destinatari<indirizzo,id>
	 * @throws Exception
	 */
	private boolean elaboraXmlRicevuta(CiviliaMail ricevutaPec, CiviliaMailAllegati xmlContent,
			String messageIdOriginale, Long idCorrispondenza, Map<String, Long> destinatariMap,
			FascicoloDTO fascicoloDTO) throws Exception {
		boolean ret = false;
		JAXBContext jaxbContext;
		jaxbContext = JAXBContext.newInstance(Postacert.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Postacert ricevutaXmlObj = null;
		ricevutaXmlObj = (Postacert) jaxbUnmarshaller.unmarshal(xmlContent.getBinContent().getBinaryStream());
		xmlContent.getBinContent().getBinaryStream().reset();
		String msgIdOriginale = ricevutaXmlObj.getDati().getMsgid();
		final TipoRicevuta tipoRicevuta=TipoRicevuta.fromString(ricevutaXmlObj.getTipo());
		if(!List.of(TipoRicevuta.ACCETTAZIONE,TipoRicevuta.AVVENUTA_CONSEGNA,TipoRicevuta.NON_ACCETTAZIONE,TipoRicevuta.ERRORE_CONSEGNA).contains(tipoRicevuta)) {
			return false;
		}
		if (msgIdOriginale.equals(messageIdOriginale)) {
			// ok è relativo alla mail in uscita
			// creo la lista dei record ricevuta, uno per ogni destinatario menzionato in
			// ricevuta....
			List<String> destinatariMail = null;
			if (ListUtil.isNotEmpty(ricevutaXmlObj.getIntestazione().getDestinatari())) {
				// mi genero la lista dei destinatari menzionati in ricevuta
				destinatariMail = ricevutaXmlObj.getIntestazione().getDestinatari().stream()
						.filter(dest -> destinatariMap.containsKey(dest.getvalue())).map(Destinatari::getvalue)
						.collect(Collectors.toList());
			}
			// carico su alfresco
			AllegatoDTO all = AutpaeDtoBuilder.inserisciAllegatoMail(xmlContent, allegatoSvc, fascicoloDTO,
					new Date(ricevutaPec.getCcTimeStamp()), TipoAllegato.RICEVUTA_DATI_CERT,noAllegati);
			List<String> destinatariRicevuta=new ArrayList<>();
			//a volte capita che non sono inseriti nel campo destinatari tutti i destinatari...
			//li pesco dal DATICERT.XML...
			List<String> destinatariNonInseriti = ricevutaXmlObj.getIntestazione().getDestinatari().stream()
					.filter(dest -> !destinatariMap.containsKey(dest.getvalue())).map(Destinatari::getvalue)
					.collect(Collectors.toList());
			if(tipoRicevuta.equals(TipoRicevuta.AVVENUTA_CONSEGNA)||
					tipoRicevuta.equals(TipoRicevuta.ERRORE_CONSEGNA)){
				//il destinario è preso dal campo dati->consegna
				String destinatarioRicevuta=ricevutaXmlObj.getDati().getConsegna();
				destinatariRicevuta.add(destinatarioRicevuta);
				//qui aggiungo eventuali destinatari che mi spuntano tra i destinatari di avvenuta
				//consegna ma che non erano menzionati nella mail originale, strano ma vero,
				//vedi pratica Autpae AP72025-3-2015 APPPTR-281-2015 1170526 lavoripubblici.comune.locorotondo@pec.rupar.puglia.it
				if(!destinatariMap.containsKey(destinatarioRicevuta) && !destinatariNonInseriti.contains(destinatarioRicevuta)) {
					destinatariNonInseriti.add(destinatarioRicevuta);
					log.info(LOG_MIGRAZIONE_MARKER, "Aggiunto destinatario presente in ricevuta e non trovato nella mail originale {} ",destinatarioRicevuta);
				}
			}else {
				destinatariRicevuta.addAll(destinatariMail);
			}
			if(ListUtil.isNotEmpty(destinatariNonInseriti)) {
				addDestinatariCorrispondenza(ricevutaPec, destinatariMap, idCorrispondenza, destinatariNonInseriti.toArray(new String[] {}), TipoDestinatario.TO);
				log.info(LOG_MIGRAZIONE_MARKER, "Alcuni destinatari presenti nel daticert.xml non erano presenti nel record mail {} ",destinatariNonInseriti);
			}
			List<RicevutaDTO> listRicevutaDTO = new ArrayList<>();
//			if (ListUtil.isEmpty(destinatariMail)) {
//				log.warn(LOG_MIGRAZIONE_MARKER, "Errore nel retrieve dei destinatari della PEC {}",ricevutaXmlObj);
//				return false;
//			}
			for (String destinatario : destinatariRicevuta) {
				RicevutaDTO ricevuta = new RicevutaDTO();
				ricevuta.setData(new Date(ricevutaPec.getCcTimeStamp()));
				ricevuta.setTipoRicevuta(TipoRicevuta.fromString(ricevutaXmlObj.getTipo()));
				ricevuta.setErrore(TipoErrore.fromString(ricevutaXmlObj.getErrore()));
				ricevuta.setIdCorrispondenza(idCorrispondenza);
				ricevuta.setIdDestinatario(destinatariMap.get(destinatario));
				if(ricevuta.getIdDestinatario()==null) {
					log.error(LOG_MIGRAZIONE_MARKER, "Destinatario presente in ricevuta e non trovato nella mail originale:"
							+ destinatario);
					continue;
				}
				ricevuta.setIdCmsDatiCert(all.getContenuto());
				// non avendo l'eml completo della ricevuta, prendo solo il daticert.xml
				ricevuta.setIdCmsEml(all.getContenuto());
				ricevuta.setIdAllegatoDatiCert(all.getId());
				listRicevutaDTO.add(ricevuta);
				if (ricevuta.getTipoRicevuta().equals(TipoRicevuta.AVVENUTA_CONSEGNA)
						|| ricevuta.getTipoRicevuta().equals(TipoRicevuta.ERRORE_CONSEGNA)) {
					// setto sul relativo destinatario lo stato a ESITO_POSITIVO o ESITO_ERRORE
					try {
						DestinatarioDTO dest = destinatariDao.find(ricevuta.getIdDestinatario());
						if (dest != null) {
							dest.setStato(ricevuta.getTipoRicevuta().equals(TipoRicevuta.AVVENUTA_CONSEGNA)
									? StatoCorrispondenza.ESITO_POSITIVO
									: StatoCorrispondenza.ESITO_CON_ERRORE);
							dest.setDataRicezione(ricevuta.getData());
							destinatariDao.update(dest);
						}
					} catch (Exception e) {
						log.error(LOG_MIGRAZIONE_MARKER, "Destinatario non trovato per update statoCorrispondenza, id:"
								+ ricevuta.getIdDestinatario());
					}
				}
				ret = true;
			}
			if(ListUtil.isNotEmpty(listRicevutaDTO)) {
				this.ricevutaDao.insertMultipla(listRicevutaDTO);	
			}
		}
		return ret;

	}
	
	private  String uploadFromLocale(String pathCms) throws Exception {
		Path pathFile= Path.of(this.localBasePath,pathCms);
		Path pathCmsParent=Path.of(pathCms);
		File file = pathFile.toFile();
		if(file.length()>maxFileUploadCms) {
			throw new FileSizeToLargeException(null,"Dimensione del file "+pathFile+ "("+file.length() +")  supera il massimo di "+maxFileUploadCms,null);
		}
		if(!file.exists()) throw new Exception("File non trovato localmente:"+pathFile);
		String idCmis = httpSvc.uploadCms(file, uploadUrl,pathCmsParent.getParent().toString().replace('\\', '/'), nomeApplicazione.toLowerCase(), true);
		return idCmis;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = false)
	public void aggiornaAlfrescoId(AllegatoDTO allDTO) throws Exception {
		if (allDTO.getContenuto().equalsIgnoreCase(AllegatoService.PLACEHOLDERTEMPIDCMS)) {
//			AlfrescoItemBean alfrescoBean = httpSvc.getCmsObjectByPath(cmsUrlGetObjectByPath,
//					allDTO.getPathCms() + "/" + allDTO.getNome(), nomeApplicazione.toLowerCase(), true);
			String idCmis=this.uploadFromLocale(allDTO.getPathCms()+"/" + allDTO.getNome());
//			if (alfrescoBean.getId() != null) {
			if(StringUtil.isNotEmpty(idCmis)) {
//				allDTO.setContenuto(alfrescoBean.getId());
				allDTO.setContenuto(idCmis);
				allegatoDao.update(allDTO);
			}
		}
	}
	
	

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = false)
	public void aggiornaAlfrescoId(RicevutaDTO ricevutaDTO) throws Exception {
		AllegatoDTO allDTO = null;
		if (ricevutaDTO.getIdAllegatoDatiCert() != null) {
			allDTO = allegatoDao.find(ricevutaDTO.getIdAllegatoDatiCert());
		}
		if (allDTO == null) {
			log.error(LOG_MIGRAZIONE_MARKER,
					"Allegato relativo a ricevuta pec non trovato: " + ricevutaDTO.getIdAllegatoDatiCert());
			throw new Exception("Allegato relativo a ricevuta pec non trovato");
		}
		if (allDTO.getContenuto().equalsIgnoreCase(AllegatoService.PLACEHOLDERTEMPIDCMS)
				|| StringUtil.isEmpty(allDTO.getContenuto())) {
			throw new Exception("Allegato relativo a ricevuta pec con idCms vuoto o temporaneo ");
		}
		
		ricevutaDTO.setIdCmsDatiCert(allDTO.getContenuto());
		ricevutaDTO.setIdCmsEml(allDTO.getContenuto());
		ricevutaDTO.setIdCmsSmime(allDTO.getContenuto());
		ricevutaDao.update(ricevutaDTO);
	}

	private String convertiCodiceCivilia(String codiceCivilia) {
		if (codiceCivilia.equalsIgnoreCase("016")) {
			return "00016";
		} else if (codiceCivilia.equalsIgnoreCase("71")) { // FOGGIA
			return "071";
		} else if (codiceCivilia.equalsIgnoreCase("110")) { // BAT
			return "110";
		} else {
			return codiceCivilia;
		}
	}

	private void initAppDto() throws Exception {
		if (applicazioneDTO != null)
			return;
		ApplicazioneSearch appSearch = new ApplicazioneSearch();
		appSearch.setCodice("AUTPAE");
		PaginatedList<ApplicazioneDTO> listApp = appDao.search(appSearch);
		if (ListUtil.isEmpty(listApp.getList())) {
			throw new Exception("Errore nel retrieve del record applicazione AUTPAE");
		}
		applicazioneDTO = listApp.getList().get(0);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = false, transactionManager = "txmng_common")
	public void migraEnteDelegato(PptrGruppoUfficio ufficioGruppo) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		final Date MAXDATE = sdf.parse("31/12/9999");
		final Date MINDATE = sdf.parse("01/01/1970");
		// init
		this.initComuniData();
		this.initAppDto();
		// END INIT
		// verifico se nel mio paesaggio_organizzazioni c'è il relativo ED a partire dal
		// codice civilia.

		log.info(LOG_MIGRAZIONE_MARKER, "Migrazione ente delegato " + ufficioGruppo);
		Integer istatNumerico = null;
		try {
			istatNumerico = Integer.parseInt(ufficioGruppo.getCodiceGruppo());
		} catch (Exception e) {
		}
		if (istatNumerico == null || istatNumerico == 70000) // salto GUEST, APPPTR, TEST PERFORMANCE
		{
			log.info(LOG_MIGRAZIONE_MARKER, "record ufficio gruppo saltato " + ufficioGruppo);
			return;
		}
		Integer paesaggioOrganizzazioneId = null;
		try {
			paesaggioOrganizzazioneId = commonDao.findEnteDelegatoFromCodiceCivilia(
					convertiCodiceCivilia(ufficioGruppo.getCodiceGruppo()), Gruppi.ED_);
		} catch (Exception e) {
		}

		if (paesaggioOrganizzazioneId == null) {
			// devo crearla....
			log.info(LOG_MIGRAZIONE_MARKER, "Creazione paesaggio_organizzazione per:" + ufficioGruppo);
			if (ufficioGruppo.getCodiceGruppo().equalsIgnoreCase("016")) {
				scriviOrganizzazione(MAXDATE, MINDATE, applicazioneDTO, mapComuniEnti, ufficioGruppo, TipoEnte.regione,
						TipoOrganizzazioneSpecifica.REGIONE, null);
			} else if (ufficioGruppo.getCodiceGruppo().equalsIgnoreCase("71")) { // FOGGIA
				scriviOrganizzazione(MAXDATE, MINDATE, applicazioneDTO, mapComuniEnti, ufficioGruppo,
						TipoEnte.provincia, TipoOrganizzazioneSpecifica.PROVINCIA, "FG");
			} else if (ufficioGruppo.getCodiceGruppo().equalsIgnoreCase("110")) { // BAT
				scriviOrganizzazione(MAXDATE, MINDATE, applicazioneDTO, mapComuniEnti, ufficioGruppo,
						TipoEnte.provincia, TipoOrganizzazioneSpecifica.PROVINCIA, "BT");
			} else if (ufficioGruppo.getCodiceGruppo().startsWith("200")) {
				scriviOrganizzazione(MAXDATE, MINDATE, applicazioneDTO, mapComuniEnti, ufficioGruppo,
						TipoEnte.unione_di_comuni, TipoOrganizzazioneSpecifica.UNIONE, ufficioGruppo.getCodiceGruppo());
			} else if (ufficioGruppo.getCodiceGruppo().equalsIgnoreCase("30001")) {
				// SUBUNIONE VEGLIE ARNESANO, SEMBRA CHE NELLA STORIA ABBIAMO INSERITO 1 sola
				// pratica e poi i comuni singoli
				// in questo caso va creata scaduta... non devono poter mettere + nuove pratiche
				scriviOrganizzazione(sdf.parse("31/12/2005"), MINDATE, applicazioneDTO, mapComuniEnti, ufficioGruppo,
						TipoEnte.unione_di_comuni, TipoOrganizzazioneSpecifica.UNIONE, null);
			} else {
				// comuni singoli... cerco a partire dall'istat il catastale dalla loro
				// comuni_completo_codistat
				Optional<Comuni_completo_cod_istat> optComune = comuniCompletoCodIstat.stream().filter(com -> {
					try {
						int codIstat = Integer.parseInt(com.getIstat6province());
						int codIstatGruppo = Integer.parseInt(ufficioGruppo.getCodiceGruppo());
						return codIstat == codIstatGruppo;
					} catch (Exception e) {
						log.error(LOG_MIGRAZIONE_MARKER, "Errore durante il confronto tra codici istat "
								+ ufficioGruppo.getCodiceGruppo() + " <> " + com.getIstat6province());
						return false;
					}
				}).findAny();
				// cerchero' per codice catastale in ente, altrimenti passo null e non
				// referenzierà alcunchè in ente.
				scriviOrganizzazione(MAXDATE, MINDATE, applicazioneDTO, mapComuniEnti, ufficioGruppo, TipoEnte.comune,
						TipoOrganizzazioneSpecifica.COMUNE,
						optComune.isPresent() ? optComune.get().getCodiceCatastale() : null);
			}
		} else {
			// allineo le competenze territoriali laddove diverse....
			PaesaggioOrganizzazioneDTO paeOrgDTO = new PaesaggioOrganizzazioneDTO();
			paeOrgDTO.setId(paesaggioOrganizzazioneId);
			paeOrgDTO = paesaggioOrganizzazioneDao.find(paeOrgDTO);
			checkUpdatePaesaggioCompetenze(MAXDATE, MINDATE, mapComuniEnti, ufficioGruppo, paeOrgDTO);
			// infine inserisco la riga in paesaggio organizzazione attributi
			if(!ufficioGruppo.getCodiceGruppo().equalsIgnoreCase("016")) {
				//per regione non aggiorno nulla..
				aggiornaAttributi(MAXDATE, MINDATE, applicazioneDTO, paeOrgDTO);	
			}
			log.info(LOG_MIGRAZIONE_MARKER, "paesaggio_organizzazione preesistente per:" + ufficioGruppo);
		}
	}

	private void scriviOrganizzazione(final Date MAXDATE, final Date MINDATE, ApplicazioneDTO applicazioneDTO,
			MapComuniEntiDelegati mapComuniEnti, PptrGruppoUfficio ufficioGruppo, TipoEnte tipoEnte,
			TipoOrganizzazioneSpecifica tipoOrgSpecifica, String enteCodice) throws EnteDelegatoException {
		Optional<EnteDTO> optEnteOrganizzazione = entiCommon.stream().filter(item -> {
			boolean ret = false;
			if (tipoEnte.equals(TipoEnte.regione)) {
				// sto cercando regione
				ret = item.getTipo().equals(tipoEnte);
			} else if (tipoEnte.equals(TipoEnte.provincia)) {
				// sto cercando provincia
				ret = item.getTipo()!=null && item.getTipo().equals(tipoEnte) &&  item.getCodice()!=null && item.getCodice().equalsIgnoreCase(enteCodice);
			} else if (tipoEnte.equals(TipoEnte.unione_di_comuni)) {
				// sto cercando unione comuni
				ret = item.getTipo()!=null && item.getTipo().equals(tipoEnte) &&  item.getCodice()!=null && item.getCodice().equalsIgnoreCase(enteCodice);
			} else if (tipoEnte.equals(TipoEnte.comune)) {
				// sto cercando comune
				ret = item.getTipo()!=null && item.getTipo().equals(tipoEnte) &&  item.getCodice()!=null && item.getCodice().equalsIgnoreCase(enteCodice);
			}
			return ret;
		}).findFirst();
		if (optEnteOrganizzazione.isPresent() || enteCodice == null) { // solo per 30001 verrà passato a null
			// inserisco in paesaggio_organizzazione il nuovo record
			PaesaggioOrganizzazioneDTO paeOrgDTO = new PaesaggioOrganizzazioneDTO();
			paeOrgDTO.setCodiceCivilia(convertiCodiceCivilia(ufficioGruppo.getCodiceGruppo()));
			paeOrgDTO.setDataCreazione(MINDATE);
			paeOrgDTO.setDataTermine(MAXDATE);
			if (enteCodice == null) {
				// non creo riferimento ad ente
				paeOrgDTO.setDenominazione(ufficioGruppo.getDescrizioneGruppo());
				paeOrgDTO.setEnteId(null);
			} else {
				paeOrgDTO.setDenominazione(optEnteOrganizzazione.get().getDescrizione());
				paeOrgDTO.setEnteId(optEnteOrganizzazione.get().getId());
			}
			paeOrgDTO.setTipoOrganizzazione(Gruppi.ED_.getTipoOrganizzazione());
			paeOrgDTO.setTipoOrganizzazioneSpecifica(tipoOrgSpecifica.getValue());
			Long idPaeOrg = paesaggioOrganizzazioneDao.insert(paeOrgDTO);
			if (idPaeOrg <= 0) {
				throw new EnteDelegatoException("Errore nella insert in paesaggio_organizzazione");
			}
			paeOrgDTO.setId(idPaeOrg.intValue());
			// ora controllo il territorio
			checkUpdatePaesaggioCompetenze(MAXDATE, MINDATE, mapComuniEnti, ufficioGruppo, paeOrgDTO);
			// infine inserisco la riga in paesaggio organizzazione attributi
			aggiornaAttributi(MAXDATE, MINDATE, applicazioneDTO, paeOrgDTO);
			log.info(LOG_MIGRAZIONE_MARKER, "creata paesaggio_organizzazione " + paeOrgDTO);
		} else {
			throw new EnteDelegatoException(
					"record per " + ufficioGruppo.getCodiceGruppo() + " " + ufficioGruppo.getDescrizioneGruppo()
							+ " non trovato in commonEnte, inserire manualmente il record");
		}
	}

	private void aggiornaAttributi(final Date MAXDATE, final Date MINDATE, ApplicazioneDTO applicazioneDTO,
			PaesaggioOrganizzazioneDTO paeOrgDTO) {
		PaesaggioOrganizzazioneAttributiDTO paeOrgAttrDTO = new PaesaggioOrganizzazioneAttributiDTO();
		paeOrgAttrDTO.setApplicazioneId(applicazioneDTO.getId());
		paeOrgAttrDTO.setPaesaggioOrganizzazioneId(paeOrgDTO.getId());
		paeOrgAttrDTO.setDataCreazione(MINDATE);
		paeOrgAttrDTO.setDataTermine(MAXDATE);
		PaesaggioOrganizzazioneAttributiSearch searchAttr = new PaesaggioOrganizzazioneAttributiSearch();
		searchAttr.setApplicazioneId(applicazioneDTO.getId() + "");
		searchAttr.setPaesaggioOrganizzazioneId(paeOrgDTO.getId() + "");
		PaginatedList<PaesaggioOrganizzazioneAttributiDTO> listaAttr = paesaggioOrganizzazioneAttributiDao
				.search(searchAttr);
		if (listaAttr != null && ListUtil.isNotEmpty(listaAttr.getList())) {
			// controllo che no sia scaduto...
			Optional<PaesaggioOrganizzazioneAttributiDTO> preesistente = listaAttr.getList().stream()
					.filter(elem -> elem.getDataTermine() != null && elem.getDataTermine().after(new Date())).findAny();
			if (preesistente.isEmpty()) {
				paesaggioOrganizzazioneAttributiDao.insert(paeOrgAttrDTO);
			} else {
				preesistente.get().setDataCreazione(MINDATE);
				preesistente.get().setDataTermine(MAXDATE);
				paesaggioOrganizzazioneAttributiDao.update(preesistente.get());
				log.info(LOG_MIGRAZIONE_MARKER,
						"paesaggi_organizzazione_attribute  già presente, applicazione_id:" + applicazioneDTO.getId()
								+ " paesaggioOrganizzazioneId:" + paeOrgDTO.getId() + " "
								+ paeOrgDTO.getDenominazione());
			}
		} else {
			paesaggioOrganizzazioneAttributiDao.insert(paeOrgAttrDTO);
		}
	}

	private void checkUpdatePaesaggioCompetenze(final Date MAXDATE, final Date MINDATE,
			MapComuniEntiDelegati mapComuniEnti, PptrGruppoUfficio ufficioGruppo, PaesaggioOrganizzazioneDTO paeOrgDTO)
			throws EnteDelegatoException {
		List<Comuni_completo_cod_istat> listaComuni = mapComuniEnti
				.getListaComuni_ByCodiceEnteDelegato(convertiCodiceCivilia(ufficioGruppo.getCodiceGruppo()));
		if (ListUtil.isEmpty(listaComuni) || 
				(listaComuni.size()==1 && listaComuni.get(0)==null)) {
			log.info(LOG_MIGRAZIONE_MARKER,"L'ufficio:" + ufficioGruppo.getCodiceGruppo()+ " " +ufficioGruppo.getDescrizioneGruppo()
			+ " ha la lista dei comuni di competenza vuota");
			return;
		}
		for (Comuni_completo_cod_istat comuneComp : listaComuni) {
			if(comuneComp==null) continue; //caso in cui non ha competenze su territorio.. vedi SUBUNIONE VEGLIE ARNESANO
			PaesaggioOrganizzazioneCompetenzeDTO paeCompDTO = new PaesaggioOrganizzazioneCompetenzeDTO();
			paeCompDTO.setCodiceCivilia(paeOrgDTO.getCodiceCivilia());
			paeCompDTO.setDataInizioDelega(MINDATE);
			paeCompDTO.setDataFineDelega(MAXDATE);
			paeCompDTO.setPaesaggioOrganizzazioneId(paeOrgDTO.getId());
			Optional<EnteDTO> comuneCompEnteOpt = entiCommon.stream()
					.filter(enteCommon ->
							enteCommon.getTipo()!=null &&
							enteCommon.getTipo().equals(TipoEnte.comune) &&
							enteCommon.getCodice()!=null && 
							comuneComp!=null && 
							comuneComp.getCodiceCatastale()!=null &&
							enteCommon.getCodice().equalsIgnoreCase(comuneComp.getCodiceCatastale()))
					.findAny();
			if (comuneCompEnteOpt.isEmpty()) {
				throw new EnteDelegatoException(
						"errore nel retrieve del comune di competenza in common.ente: " + comuneComp + " listaComuni:" +listaComuni);
			}
			paeCompDTO.setEnteId(comuneCompEnteOpt.get().getId());
			// qui in teoria dovrei cercare se esiste già id_paesaggio_organizzazione,idEnte
			// e se è ancora valido...
			PaesaggioOrganizzazioneCompetenzeSearch searchComp = new PaesaggioOrganizzazioneCompetenzeSearch();
			searchComp.setEnteId(paeCompDTO.getEnteId() + "");
			searchComp.setPaesaggioOrganizzazioneId(paeOrgDTO.getId() + "");
			PaginatedList<PaesaggioOrganizzazioneCompetenzeDTO> searchedComp = paesaggioOrganizzazioneCompetenzeDao
					.search(searchComp);
			if (searchedComp != null && ListUtil.isNotEmpty(searchedComp.getList())) {
				// verifico se in vigore...
				Optional<PaesaggioOrganizzazioneCompetenzeDTO> esisteInVigore = searchedComp.getList().stream()
						.filter(elem -> elem.getDataFineDelega() != null && elem.getDataFineDelega().after(new Date())
								&& elem.getDataInizioDelega() != null && elem.getDataInizioDelega().before(new Date()))
						.findAny();
				if (!esisteInVigore.isPresent()) {
					paesaggioOrganizzazioneCompetenzeDao.insert(paeCompDTO);
				} else {
					log.info(LOG_MIGRAZIONE_MARKER, "comune di competenza preesistente " + esisteInVigore.get());
				}
			} else {
				// nuovo inserimento
				paesaggioOrganizzazioneCompetenzeDao.insert(paeCompDTO);
			}
		}
	}

	@Override
	public void checkGruppiProfileManager() throws Exception {
		this.initAppDto();
		this.initDataPM(applicazioneDTO.getCodice());
		PaesaggioOrganizzazioneAttributiSearch orgAttSearch = new PaesaggioOrganizzazioneAttributiSearch();
		orgAttSearch.setLimit(1200);
		orgAttSearch.setPage(1);
		orgAttSearch.setApplicazioneId(applicazioneDTO.getId() + "");
		PaginatedList<PaesaggioOrganizzazioneAttributiDTO> listOrg = paesaggioOrganizzazioneAttributiDao
				.search(orgAttSearch);
		if (listOrg != null && ListUtil.isNotEmpty(listOrg.getList())) {
			for (PaesaggioOrganizzazioneAttributiDTO orgAtt : listOrg.getList()) {
				if (orgAtt.getDataTermine().after(new Date())) {
					// in vigore...
					PaesaggioOrganizzazioneDTO org = new PaesaggioOrganizzazioneDTO();
					org.setId(orgAtt.getPaesaggioOrganizzazioneId());
					try {
						org = paesaggioOrganizzazioneDao.find(org);
					} catch (EmptyResultDataAccessException e) {
						log.error(LOG_MIGRAZIONE_MARKER,
								"Errore nel retrieve dell'organizzazione con id:" + org.getId());
					}
					if (org.getDataTermine().after(new Date())) {
						this.checkCreaGruppoPM(org, applicazioneDTO);
					}
				}

			}
		}
	}

	/**
	 * @throws Exception
	 * @autor Adriano Colaianni
	 * @date 4 mag 2021
	 */
	private void initDataPM(String codiceApplicazionePM) throws Exception {
		if (this.infoGruppi != null)
			return;
		String jwt = httpSvc.getBatchUser().getAuthorization();
		this.infoGruppi = profileManager.getGruppiUtente(jwt, codiceApplicazionePM, null).getPayload();
		if (infoGruppi == null)
			throw new Exception("Errore nel retrieval dei gruppi dal PM");
		pmAppData = profileManager.findAppByCode(jwt, codiceApplicazionePM).getPayload();
	}

	private void checkCreaGruppoPM(PaesaggioOrganizzazioneDTO org, ApplicazioneDTO applicazioneDTO) throws Exception {
		// in vigore
		// check su PM dell'esistenza dei GRUPPI
		Set<String> ruoliGiaPresenti = Arrays.asList(this.infoGruppi).stream().filter(gruppo -> {
			int idOrgFromCodice = Gruppi.getIdOrganizzazione(gruppo.getCodiceGruppo());
			return idOrgFromCodice > 0 && idOrgFromCodice == org.getId();

		}).map(gruppo -> Gruppi.getCharRuolo(gruppo.getCodiceGruppo())).collect(Collectors.toSet());
		for (String ruoloDaInserire : new String[] { "A", "D", "F" }) {
			if (!ruoliGiaPresenti.contains(ruoloDaInserire)) {
				String jwt = httpSvc.getBatchUser().getAuthorization();
				PMGroupsRequestBean bean = new PMGroupsRequestBean();
				bean.setCodiceGruppo(org.getTipoOrganizzazione() + "_" + org.getId() + "_" + ruoloDaInserire);
				bean.setIdApplicazione(pmAppData.getId());
				bean.setNomeGruppo(
						org.getDenominazione() + " (" + Ruoli.getFromPmCode(ruoloDaInserire).getPmAbbr() + ")");
				bean.setDescrizioneGruppo(
						"Gruppo " + Ruoli.getRuoloPlurale(ruoloDaInserire) + " " + org.getTipoOrganizzazione() + " " + org.getDenominazione());
				try {
				profileManager.saveGroup(jwt, bean);
				log.info(LOG_MIGRAZIONE_MARKER,
						"Inserito nuovo gruppo in PM: " + bean.getCodiceGruppo() + bean.getDescrizioneGruppo());
				}catch(Exception e) {
					log.error(LOG_MIGRAZIONE_MARKER,
							"Errore durante l'inserimento del nuovo gruppo in PM: " + bean.getCodiceGruppo() + bean.getDescrizioneGruppo());
				}
			}
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, timeout = 60000, readOnly = false)
	public void migraPraticaPutt(InfoAutPaesAlfaBean infoPratica) throws Exception
	{
		AutpaeDtoBuilder.localBasePath = localBasePath;
		// init comuniCompleto...
		this.initComuniData();
//		if (comuniCompletoCodIstat == null) comuniCompletoCodIstat = civSvc.getComuniCompletoCodIstat();
//		if (entiCommon == null)  entiCommon = commonSvc.getAllEnti();
		// cerco se già esiste
		Integer idFascicoloPreesistente = null;
		try 
		{
			idFascicoloPreesistente = fascicoloDao.findByTPraticaIdAndCodice(infoPratica.getPraticaId(),infoPratica.getCodicePratica());
		} 
		catch (EmptyResultDataAccessException e) {}
		if (idFascicoloPreesistente != null) 
			throw new FascicoloAlreadyExistsException(infoPratica, "Fascicolo già esistente", null);
		// calcolo del nostro campo ufficio a partire dal loro ...
		Integer idPaesaggioOrganizzazione = null;
		boolean is016=false; //se a true alcune query vengono passate allo schema CIVILIA_016
		String codiceCivilia = infoPratica.getIstatAmm();
		try 
		{
			if(infoPratica.getIstatAmm().equals("016")) {
				is016=true;
				codiceCivilia = String.format("%5s", infoPratica.getIstatAmm()).replace(' ', '0');
			}
			idPaesaggioOrganizzazione = commonSvc.findEnteDelegatoFromCodiceCivilia(codiceCivilia, Gruppi.ED_);
		} 
		catch (EmptyResultDataAccessException e) {
			throw new BaseMigrationException(infoPratica, "Ente delegato non trovato da istatAmm:"+codiceCivilia, null);
		}
		
		// creo il fascicolo base:
		RichiedenteDTO richiedente = new RichiedenteDTO();
		FascicoloDTO fascicoloDTO = AutpaeDtoBuilder.buildFascicoloPutt(infoPratica, richiedente);
		List<CiviliaPratica> praticaCivilias=puttMigService.getCiviliaPratica(infoPratica.getPraticaId(), is016);
		if(ListUtil.isEmpty(praticaCivilias)) {
			throw new BaseMigrationException(infoPratica,"record T_PRATICA  non trovato per tPraticaId:"+infoPratica.getPraticaId()+" suap_codep:"+infoPratica.getCodicePratica(), null);
		}
		CiviliaPratica praticaCivilia = praticaCivilias.get(0);
		fascicoloDTO.setDataCreazione(praticaCivilia.getDataAttivazione()); 
		List<FascicoloInterventoDTO> caratterizzazione = AutpaeDtoBuilder.buildFascicoloIntervento(infoPratica, fascicoloDTO, 0l);
		fascicoloDTO.setUfficio(Gruppi.ED_.name() + idPaesaggioOrganizzazione + "_D");
		fascicoloDTO.setOrgCreazione(idPaesaggioOrganizzazione);
		fascicoloDTO.setStato(StatoFascicolo.WORKING);
		fascicoloDTO.settPraticaId(infoPratica.getPraticaId());
		fascicoloDTO.setCodicePraticaAppptr(infoPratica.getCodicePratica());
		// pannello fascicolo
//		List<PptrCodiceInterno> pptrEntes = civSvc.getDatiCodiceInterno(infoPratica.getCodicePraticaAppptr(),
//				infoPratica.getProgPubblicazione());
//		if (ListUtil.isNotEmpty(pptrEntes)) {
//			PptrCodiceInterno pptrEnte = pptrEntes.get(0);
//			fascicoloDTO = AutpaeDtoBuilder.buildFascicolo(fascicoloDTO, pptrEnte);
//		}
		fascicoloDTO = fascicoloSvc.creaDaMigrazione(fascicoloDTO); // setta l'id
		fascicoloSvc.aggiornaDataCreazione(fascicoloDTO.getId(), fascicoloDTO.getDataCreazione());
		fascicoloProcedimentiAttiviRepo.insertOnCreateFascicolo(fascicoloDTO.getId(), Applicativo.valueOf(props.getCodiceApplicazione().toUpperCase()));
		//codice
		String[] tokensCodice = infoPratica.getCodicePratica().split("-");
		if (tokensCodice[0].startsWith("AP")) 
		{
			commonSvc.updateProgressiviCodiceFascicolo(infoPratica.getCodicePratica(), infoPratica.getIstatAmm());
		}
		else if (tokensCodice[0].startsWith(AutpaeConstants.PREFISSO_CODICE_REGIONE)) {
			commonSvc.updateProgressiviCodiceFascicolo(infoPratica.getCodicePratica(), "00016");
		}
		//richiedente
		richiedente.setIdFascicolo(fascicoloDTO.getId());
		richiedenteSvc.inserisci(richiedente);
		//localizzazione intervento
		final List<LocalizzazionePuttBean> particelle = puttMigService.getPuttLocalizzazione(infoPratica.getPraticaId(),is016);
		fascicoloDTO.setTipoSelezioneLocalizzazione(TipoLocalizzazione.PTC);
		if(ListUtil.isNotEmpty(particelle))
		{
			List<Integer> comuniIdsInseriti=new ArrayList<>();
			final Map<Integer, List<LocalizzazionePuttBean>> mappaParticella = particelle.stream().collect(Collectors.groupingBy(LocalizzazionePuttBean::getCodIstat));
			for(Integer k: mappaParticella.keySet())
			{
				Comuni_completo_cod_istat comune = comuniCompletoCodIstat.stream().filter(p -> Integer.parseInt(p.getIstat6province()) == k).findFirst().orElse(null);
				EnteDTO ente = entiCommon.stream()
										 .filter(e -> e.getCodice().equalsIgnoreCase(comune.getCodiceCatastale()) && e.getTipo().equals(TipoEnte.comune))
										 .findFirst()
										 .orElse(null);
				if(comuniIdsInseriti.contains(ente.getId()))
				{
					log.warn(IMigratorService.LOG_MIGRAZIONE_MARKER,"Comune di competenza doppio {} " ,ente);
					continue;
				}
				//creo comune competenza
				ComuniCompetenzaDTO comuneCompetenza = new ComuniCompetenzaDTO();
				comuneCompetenza.setPraticaId(fascicoloDTO.getId());
				comuneCompetenza.setCreazione(false);
				comuneCompetenza.setEffettivo(true);
				comuneCompetenza.setDataInserimento(infoPratica.getDataTrasmissione());
				comuneCompetenza.setCodCat(comune.getCodiceCatastale());
				comuneCompetenza.setCodIstat(comune.getNumericIstat6Prov() + "");
				comuneCompetenza.setEnteId(ente.getId());
				comuneCompetenza.setDescrizione(ente.getDescrizione());
				comuniCompDao.insert(comuneCompetenza);
				comuneCompetenza.setCreazione(true);
				comuneCompetenza.setEffettivo(false);
				comuniCompDao.insert(comuneCompetenza);
				//creo localizzazione intervento
				comuniIdsInseriti.add(ente.getId());
				LocalizzazioneInterventoDTO locBean = new LocalizzazioneInterventoDTO();
				locBean.setComune(comune.getComune());
				locBean.setComuneId(ente.getId());
				locBean.setPraticaId(fascicoloDTO.getId());
//				locBean.setParticelle(AutpaeDtoBuilder.buildParticelleCatastaliPutt(mappaParticella.get(k), fascicoloDTO.getId()));
				locIntSvc.insert(locBean);
				particelleSvc.insertMultiple(AutpaeDtoBuilder.buildParticelleCatastaliPutt(mappaParticella.get(k), ente.getId()), fascicoloDTO.getId());
			}
		}else {
			throw new BaseMigrationException(infoPratica, "Nessuna localizzazione per la pratica "+infoPratica.getCodicePratica(),null);
		}
		//tab descrizione intervento
		if(caratterizzazione != null && !caratterizzazione.isEmpty())
			interventoSvc.insertMultiple(caratterizzazione.stream().map(FascicoloInterventoDTO::getIdTipiQualificazioni).collect(Collectors.toList()), fascicoloDTO.getId());
		//allegati
		List<PuttDocBean> docs = puttMigService.getPuttAllegati(infoPratica.getCodicePratica(),is016);
		if(ListUtil.isNotEmpty(docs))
		{
			loadBlobFromPuttAlfresco(docs);//prelevo eventuali file da alfresco
			try
			{
				for(PuttDocBean doc: docs)
				{
					if(doc.getAbinContent() != null && doc.getAbinContent().length()>0)
					{
						AllegatiWithMultipart a = AutpaeDtoBuilder.buildAllegatoPutt(doc);
						allegatoService.inserisciAllegatoDaMigrazione(fascicoloDTO, Arrays.asList(a.getTipo()), doc.getAbinContent(), a.getNome(), a.getNome(), 
																	  a.getMimeType(), AutpaeDtoBuilder.MIGRAZIONE_SUBPATH, AutpaeDtoBuilder.localBasePath, 
																	  null, null, a.getDataCaricamento(), doc.getnTKeDocStID().toString(),noAllegati);		
					}else {
						log.info(IMigratorService.LOG_MIGRAZIONE_MARKER,
								"redord documento presente e contenuto assente filename: {} codicePratica{} tkestdocid{}",doc.getNomeFile(),infoPratica.getCodicePratica(),doc.getnTKeDocStID());
					}
				}
			}
			catch(Exception e)
			{
				throw new Exception("Errore durante l'upload degli allegati ", e);
			}
		}
		// aggiorno lo stato a finished
		if(infoPratica.getDataTrasmissione()!=null) {
			fascicoloDTO.setDataTrasmissione(infoPratica.getDataTrasmissione());	
		}
		fascicoloDTO.setStato(StatoFascicolo.FINISHED);
		fascicoloDTO.setEsitoVerifica(EsitoVerifica.NOT_SAMPLED);	
		fascicoloSvc.updateForMigration(fascicoloDTO); // update
		migraMailPutt("MIGRAZIONE", fascicoloDTO, infoPratica,is016);
		List<TnoProtocolloUscita> pus = puttMigService.getProtocolloUscita(infoPratica.getPraticaId());
		List<TnoProtocolloUscita> pu = 
				pus.stream().filter(entity->entity.getEsitoProtocollazione().equalsIgnoreCase("SI")).collect(Collectors.toList());
		//prendo solo i record con esito protocollazione=SI
		if(pu != null && !pu.isEmpty())
		{
			
			TnoProtocolloUscita protocollo = pu.get(0);
//			if(protocollo.getBinPdfProtContent() == null && protocollo.getBinPdfContent() == null)
//			{
//				log.error("Errore, ricevuta di trasmissione assente per la pratica con t_id_pratica {}", infoPratica.getPraticaId());
//				throw new Exception("Errore, ricevuta di trasmissione assente per la pratica con t_id_pratica " + infoPratica.getPraticaId());
//			}
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			final StringBuilder sb = new StringBuilder()
										.append(protocollo.getTitolarioProtocollo())
										.append("/")
										.append(sdf.format(protocollo.getDataProtocollo()))
										.append("/")
										.append(protocollo.getNumeroProtocollo());
			String nomeFile = TipoAllegato.RICEVUTA_TRASMISSIONE.getTextValue().replaceAll(" ", "_").concat(".pdf");
			String nomeDocumento = TipoAllegato.RICEVUTA_TRASMISSIONE.getTextValue();
			List<TipoAllegato> tipo = Collections.singletonList(TipoAllegato.RICEVUTA_TRASMISSIONE);
			Blob ricevutaTrasmissione = protocollo.getBinPdfContent();
			if(protocollo.getBinPdfContent().length()>protocollo.getBinPdfProtContent().length())
				ricevutaTrasmissione = protocollo.getBinPdfProtContent();
			allegatoSvc.inserisciAllegatoDaMigrazione(fascicoloDTO, tipo, ricevutaTrasmissione, nomeFile, nomeDocumento,  "application/pdf",
													  AutpaeDtoBuilder.MIGRAZIONE_SUBPATH, localBasePath, sb.toString(), protocollo.getDataProtocollo(), 
													  protocollo.getDataProtocollo(), protocollo.gettKeDocStId()+"",noAllegati);
			if(protocollo.getDataProtocollo()!=null) {
				fascicoloDTO.setDataTrasmissione(protocollo.getDataProtocollo());
				fascicoloSvc.updateForMigration(fascicoloDTO); 
			}
		}
		else
		{
			log.warn(LOG_MIGRAZIONE_MARKER,"Attenzione, ricevuta di trasmissione assente per la pratica con t_id_pratica {}", infoPratica.getPraticaId());
		}
		log.info(LOG_MIGRAZIONE_MARKER,"Pratica {} migrata con successo ",fascicoloDTO.getCodice());
	}
	
	private void migraMailPutt(String mittenteUser, FascicoloDTO fascicoloDTO, InfoAutPaesAlfaBean infoPratica,boolean is016) throws Exception
	{
		// migrazione mail
		List<CiviliaMail> mailsCivilia = civSvc.getCiviliaEmail(infoPratica.getPraticaId(),is016);
		if (ListUtil.isNotEmpty(mailsCivilia)) 
		{
			for (CiviliaMail mail : mailsCivilia) 
			{
				Map<String, Long> destinatariIdMap = new HashMap<>();
				if (mail.getVerso() == null || mail.getVerso().equalsIgnoreCase("U")) 
				{
					CorrispondenzaDTO mailNew = new CorrispondenzaDTO();
					mailNew.setBozza(false);
					mailNew.setComunicazione(false);
					mailNew.setDataInvio(new Date(mail.getCcTimeStamp()));
					mailNew.setMessageId(mail.getMessageId());
					mailNew.setMittenteDenominazione(mail.getMittente());
					mailNew.setMittenteEmail(mail.getMittente());
					mailNew.setMittenteUsername(mittenteUser);
					String[] tokens = fascicoloDTO.getUfficio().split("_");
					String mittenteEnte = tokens[0] + "_" + tokens[1];
					mailNew.setMittenteEnte(mittenteEnte);
					mailNew.setOggetto(mail.getOggetto());
					mailNew.setTesto(mail.getTesto());
					Long idCorrispondenza = corrispondenzaSvc.insert(mailNew, true);
					FascicoloCorrispondenzaDTO fascicoloCorrispondenza = new FascicoloCorrispondenzaDTO(null,
							idCorrispondenza, fascicoloDTO.getId());
					fascicoloCorrispondenzaSvc.insert(fascicoloCorrispondenza);
					// elaborazione destinatari
					if (StringUtil.isEmpty(mail.getDestinatario())) 
					{
						throw new CorrispondenzaException(infoPratica, "Nessun destinatario trovato nella mail con id"
								+ mail.gettMailInviateId() + " oggetto: " + mail.getOggetto() + " !!!", null);
					}
					String[] mailDestinatari = mail.getDestinatario().split(",");
					addDestinatariCorrispondenza(mail, destinatariIdMap, idCorrispondenza, mailDestinatari,TipoDestinatario.TO);
					List<CiviliaMailAllegati> allegatiMailCivilia = civSvc
							.getCiviliaEmailAllegati(mail.gettMailInviateId(),is016);
					mail.setAllegatiMail(allegatiMailCivilia);
					// elaborazione allegati
					if (ListUtil.isNotEmpty(allegatiMailCivilia)) 
					{
						for (CiviliaMailAllegati allegatoMailCivilia : allegatiMailCivilia) 
						{
							AllegatoDTO ret = AutpaeDtoBuilder.inserisciAllegatoMail(allegatoMailCivilia, allegatoSvc,
									fascicoloDTO, mailNew.getDataInvio(), TipoAllegato.COMUNICAZIONE,noAllegati);
							Long idAllegato = ret.getId();
							AllegatoCorrispondenzaDTO allCorrDTO = new AllegatoCorrispondenzaDTO(idAllegato,
									idCorrispondenza);
							allCorrSvc.insert(allCorrDTO);
						}
					}
					// elaborazione delle ricevute:
					for (CiviliaMail ricevutaPec : mailsCivilia) 
					{
						// filtro solo quelle con verso Ingresso
						if (ricevutaPec.getVerso() != null && ricevutaPec.getVerso().equalsIgnoreCase("I")) 
						{
							// prendo gli allegati
							List<CiviliaMailAllegati> allegatiRicevutaPecCivilia = civSvc
									.getCiviliaEmailAllegati(ricevutaPec.gettMailInviateId(),is016);
							ricevutaPec.setAllegatiMail(allegatiRicevutaPecCivilia);
							// se ha daticert.xml lo parso e prelevo l'indirizzo destinatario
							if (ListUtil.isNotEmpty(allegatiRicevutaPecCivilia)) {
								final FascicoloDTO fascicoloLocalDTO = fascicoloDTO;
								allegatiRicevutaPecCivilia.stream()
										.filter(all -> all.getNomeFile().equalsIgnoreCase("DATICERT.XML")).findAny()
										.ifPresent(all -> 
										{
											// parso il daticert.xml e prelevo tipo ricevuta,
											// destinatario,message-id-originale
											// Blob xmlContent=all.getBinContent();
											// xmlContent.getBinaryStream().readAllBytes()
											try 
											{
												elaboraXmlRicevuta(ricevutaPec, all, mailNew.getMessageId(),
														idCorrispondenza, destinatariIdMap, fascicoloLocalDTO);
											} catch (Exception e) 
											{
												log.error(LOG_MIGRAZIONE_MARKER, "Errore nella elaboraXmlRicevuta, codice pratica {} codice pratica appptr {} tPraticaId {} , l'elaborazione continua !!!",fascicoloLocalDTO.getCodice(),fascicoloLocalDTO.getCodicePraticaAppptr(),fascicoloLocalDTO.gettPraticaId(),e);
											}
										});
							}
						}
					}
				}
			}
		}
	}

	/**
	 * vanno aggiunte anche i record con organizzazione_id non nulla e ente_id nullo per gli enti delegati.
	 * INSERT INTO common.paesaggio_email( codice_applicazione, email, denominazione, pec, organizzazione_id, ente_id) 
select 'AUTPAE', pe.email, pe.denominazione, pe.pec,po.id, null
from common.paesaggio_organizzazione po, common.paesaggio_email pe 
where not po.ente_id is null and po.tipo_organizzazione in ('ED','REG') and po.id<>165
and pe.ente_id =po.ente_id and pe.organizzazione_id is null;
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class }, timeout = 60000, readOnly = false,transactionManager = "txmng_common")
	public void migraMailEntiESbap(boolean isProduzione) throws Exception {
		this.initComuniData();
		this.initAppDto();
		//step mail enti
		List<TnoMailEnti> listaMailEnti = civSvc.getTnoMailEnti();
		for(TnoMailEnti mailEnteCivilia:listaMailEnti) {
			//la chiave univoca in TnoMailEnti è il codice istat, mentre da noi abbiamo codice catastale
			Integer enteId=null;
			if(mailEnteCivilia.getTipoTerritorio().equalsIgnoreCase("CO")) {
				//cerco l'id del comune 
				enteId = this.getIdEnteCommonFromCodIstatCivilia(mailEnteCivilia.getCodIstat());
			}
			else {
				enteId = this.getIdEnteCommonFromIstatCiviliaProvinceoRegione(mailEnteCivilia.getCodIstat());
			}
			if(enteId==null)
				throw new Exception("Errore nel retrieval tipo_territorio:"+mailEnteCivilia.getTipoTerritorio()+" del common.ente.id dal codIstat civilia "+mailEnteCivilia.getCodIstat());
			aggiornaMail(isProduzione, mailEnteCivilia, enteId);
		}
		//step mail sbap
		List<TnoSopMatriceTerritorio> listaMailSbap = civSvc.getTnoSopMatriceTerritorio();
		for(TnoSopMatriceTerritorio mailSbapCivilia:listaMailSbap) {
			//per ogni soprintendenza ho piu' record uno per ogni comune...
			//identifico la provincia e sulla base di questa determino la SBAP corrispondente.
			//forse esiste quella di Taranto separata da Brindisi e Lecce
			Integer enteId = this.getIdEnteCommonFromCodIstatCivilia(mailSbapCivilia.getCodIstat());
			Integer idEnteCommonSbap = 
					this.getIdSbapCommonFromCodIstatCivilia(mailSbapCivilia.getCodIstat());
			List<Integer> idOrgSbap = commonSvc.getOrganizzazioniValidePerApplicazioneByIdsEnte(applicazioneDTO.getCodice(), Collections.singletonList(idEnteCommonSbap));
			if(ListUtil.isEmpty(idOrgSbap)) {
				throw new Exception("IdOrgSbap non trovato a partide da common.ente.id "+idEnteCommonSbap);
			}
			aggiornaMailSbap(isProduzione, mailSbapCivilia, enteId,idOrgSbap.get(0));
		}
	}

	private void aggiornaMail(boolean isProduzione, TnoMailEnti mailEnteCivilia, Integer enteId) throws Exception {
		PaesaggioEmailSearch filter = new PaesaggioEmailSearch();
		filter.setCodiceApplicazione(applicazioneDTO.getCodice());
		filter.setEnteId(enteId);
		List<PaesaggioEmailDTO> recRubrica = commonDao.searchRubricaPaesaggio(filter);
		if(!ListUtil.isEmpty(recRubrica)) {
			//rimuovo i record preesistenti...
			for(PaesaggioEmailDTO record:recRubrica) {
				paesaggioOrganizzazioneDao.deletePaesaggioEmail(record.getId());	
				log.info(IMigratorService.LOG_MIGRAZIONE_MARKER,"rimosso record mail ente "+record);
			}
		}
		boolean trovato=false;
//		//scandisco la lista e in corrispondenza di quello con id_organizzazione a null faccio aggiornamento
//		if(!ListUtil.isEmpty(recRubrica) && 
//			recRubrica.stream().filter(el->el.getOrganizzazioneId()==null).findAny().isPresent()){
//			PaesaggioEmailDTO mailDTO=recRubrica.stream().filter(el->el.getOrganizzazioneId()==null).findAny().get();
//			mailDTO.setDenominazione(mailEnteCivilia.getEnteRiferimento());
//			mailDTO.setEmail(mailEnteCivilia.getPecEnteRiferimento(isProduzione));
//			mailDTO.setPec(true);
//			paesaggioOrganizzazioneDao.updatePaesaggioEmail(mailDTO);
//			log.info(IMigratorService.LOG_MIGRAZIONE_MARKER,"aggiornato nuovo record mail ente "+mailDTO);
//		}
		if(!trovato) {
			PaesaggioEmailDTO mailDTO=new PaesaggioEmailDTO();
			mailDTO.setCodiceApplicazione(applicazioneDTO.getCodice());
			mailDTO.setEnteId(enteId);
			mailDTO.setEmail(mailEnteCivilia.getPecEnteRiferimento(isProduzione));
			mailDTO.setDenominazione(mailEnteCivilia.getEnteRiferimento());
			mailDTO.setPec(true);
			paesaggioOrganizzazioneDao.insertPaesaggioEmail(mailDTO);
			log.info(IMigratorService.LOG_MIGRAZIONE_MARKER,"inserito  nuovo record mail ente "+mailDTO);
		}
		//in teoria dovrei rimuovere tutte quelle che hanno un id_organizzazione non nullo...
	}
	
	
	private void aggiornaMailSbap(boolean isProduzione, TnoSopMatriceTerritorio mailEnteCivilia, Integer enteId,Integer sopOrgId) throws Exception {
		//devo cercare il record in paesaggio_organizzazione di sbap...
		PaesaggioEmailSearch filter = new PaesaggioEmailSearch();
		filter.setCodiceApplicazione(applicazioneDTO.getCodice());
		filter.setEnteId(enteId);
		filter.setOrganizzazioneId(sopOrgId);
		List<PaesaggioEmailDTO> recRubrica = commonDao.searchRubricaPaesaggio(filter);
		boolean trovato=false;
		if(!ListUtil.isEmpty(recRubrica)) {
			//rimuovo i record preesistenti...
			for(PaesaggioEmailDTO record:recRubrica) {
				paesaggioOrganizzazioneDao.deletePaesaggioEmail(record.getId());	
				log.info(IMigratorService.LOG_MIGRAZIONE_MARKER,"rimosso record mail ente "+record);
			}
		}
//		if(!ListUtil.isEmpty(recRubrica)) { 
//			PaesaggioEmailDTO mailDTO=recRubrica.get(0);
//			mailDTO.setDenominazione(mailEnteCivilia.getEnteRiferimento());
//			mailDTO.setEmail(mailEnteCivilia.getPecEnteRiferimento(isProduzione));
//			mailDTO.setPec(true);
//			paesaggioOrganizzazioneDao.updatePaesaggioEmail(mailDTO);
//			trovato=true;
//			log.info(IMigratorService.LOG_MIGRAZIONE_MARKER,"aggiornato nuovo record mail sbap-comune "+mailDTO);
//		}
		if(!trovato) {
			PaesaggioEmailDTO mailDTO=new PaesaggioEmailDTO();
			mailDTO.setCodiceApplicazione(applicazioneDTO.getCodice());
			mailDTO.setEnteId(enteId);
			mailDTO.setOrganizzazioneId(sopOrgId);
			mailDTO.setEmail(mailEnteCivilia.getPecEnteRiferimento(isProduzione));
			mailDTO.setDenominazione(mailEnteCivilia.getEnteRiferimento());
			mailDTO.setPec(true);
			paesaggioOrganizzazioneDao.insertPaesaggioEmail(mailDTO);
			log.info(IMigratorService.LOG_MIGRAZIONE_MARKER,"inserito  nuovo record mail sbap-comune "+mailDTO);
			if(StringUtil.isNotEmpty(mailEnteCivilia.getMailAggiuntiva(isProduzione)) && StringUtil.isEmail(mailEnteCivilia.getMailAggiuntiva(true))) {
				PaesaggioEmailDTO mailAggiuntiva=new PaesaggioEmailDTO();
				mailAggiuntiva.setCodiceApplicazione(applicazioneDTO.getCodice());
				mailAggiuntiva.setEnteId(enteId);
				mailAggiuntiva.setOrganizzazioneId(sopOrgId);
				mailAggiuntiva.setEmail(mailEnteCivilia.getPecEnteRiferimento(isProduzione));
				mailAggiuntiva.setDenominazione(mailEnteCivilia.getEnteRiferimento());
				mailAggiuntiva.setPec(true);
				paesaggioOrganizzazioneDao.insertPaesaggioEmail(mailAggiuntiva);
				log.info(IMigratorService.LOG_MIGRAZIONE_MARKER,"inserito  nuovo record mail aggiuntiva sbap-comune "+mailAggiuntiva);
			}
		}
	}
	
	/**
	 * a partire dal codice istat civilia (Long) restituisce il corrispondente common.ente.id tramite passaggio da 
	 * comuni_completo_cod_istat in cui c'è sia istat che catastale
	 * @autor Adriano Colaianni
	 * @date 10 set 2021
	 * @param codIstat
	 * @return
	 * @throws Exception 
	 */
	private Integer getIdEnteCommonFromCodIstatCivilia(Long codIstat) throws Exception {
		EnteDTO enteCommon=new EnteDTO();
		this.initComuniData();
		comuniCompletoCodIstat
		.stream()
		.filter(comcc->comcc.getNumericIstat6Prov()==codIstat.intValue())
		.map(comcc->comcc.getCodiceCatastale()).findAny()
		.ifPresent(codCatBeccato->
		{
			entiCommon
			.stream()
			.filter(ente->ente.getCodice().equals(codCatBeccato))
			.findAny()
			.ifPresent(comEnte->enteCommon.setId(comEnte.getId()));	
		});
		return enteCommon.getId();
	}
	
	/**
	 * a partire dal codistat del comune su Civilia, viene determinato il record corrispondente logico su common.ente della SBAP
	 * mappata tramite provincia di appartenenza del comune
	 * @autor Adriano Colaianni
	 * @date 10 set 2021
	 * @param codIstat
	 * @return id common.ente della SBAP corrispondente
	 * @throws Exception
	 */
	private Integer getIdSbapCommonFromCodIstatCivilia(Long codIstat) throws Exception {
		Integer ret=null;
		StringBuilder codiceSbapCommon=new StringBuilder();
		Integer idEnte=this.getIdEnteCommonFromCodIstatCivilia(codIstat);
		Optional<EnteDTO> enteComune = entiCommon
		.stream()
		.filter(ente->ente.getId().equals(idEnte))
		.findAny();
		if(enteComune.isPresent()) {
			//cerco il parent(provincia)
			Optional<EnteDTO> parentEnte = entiCommon
			.stream()
			.filter(ente->ente.getId().equals(enteComune.get().getParentId()))
			.findAny();
			if(!parentEnte.isPresent()) {
				throw new Exception("Errore nel retrieval della provincia dal comune parentId:"+enteComune.get().getParentId());
			}
			switch (parentEnte.get().getCodice()) {
			case "FG":
			case "BT":
				codiceSbapCommon.append("SABAPFG");
				break;
			case "BA":
				codiceSbapCommon.append("SABAPBA");
				break;
			case "BR":
			case "LE":
			case "TA":
				codiceSbapCommon.append("SABAPLE");
				break;
			default:
				break;
			}
			if(codiceSbapCommon.length()>0) {
					Optional<EnteDTO> sbapEnteCommonDTO = entiCommon
							.stream()
							.filter(ente->ente.getCodice().equals(codiceSbapCommon.toString()))
							.findAny();
					if(sbapEnteCommonDTO.isPresent()) {
						ret=sbapEnteCommonDTO.get().getId();
					}
			}
		}
		return ret;
	}
	
	
	
	/**
	 * mappa i codici istat usati nella TNO_MAIL_ENTE per le province o Regione
	 * @autor Adriano Colaianni
	 * @date 10 set 2021
	 * @param codIstat
	 * @return
	 * @throws Exception
	 */
	private Integer getIdEnteCommonFromIstatCiviliaProvinceoRegione(Long codIstat) throws Exception {
		EnteDTO enteCommon=new EnteDTO();
		int codIstatPr=codIstat.intValue();
		this.initComuniData();
		//caso province PR o RP(regione)
		/**
		 * 72 provincia bari 71 provincia foggia 11 provincia bat 75 provincia lecce 73
		 * provincia taranto 74 provincia brindisi
		 */
		
		StringBuilder codiceCommonEnte=new StringBuilder();
		switch (codIstatPr) {
		case 72://provincia bari
			codiceCommonEnte.append("BA");
			break;
		case 71://provincia foggia
			codiceCommonEnte.append("FG");
			break;
		case 11://provincia bat 
			codiceCommonEnte.append("BT");
			break;
		case 75://provincia lecce 
			codiceCommonEnte.append("LE");
			break;
		case 73://provincia taranto
			codiceCommonEnte.append("TA");
			break;	
		case 74://provincia brindisi
			codiceCommonEnte.append("BR");
			break;	
		case 16://Regione puglia
			codiceCommonEnte.append("R");
			break;	
		default:
			break;
		}
		if(StringUtil.isEmpty(codiceCommonEnte.toString())){
			return null;
		}
		entiCommon
		.stream()
		.filter(ente->ente.getCodice().equals(codiceCommonEnte.toString()))
		.findAny()
		.ifPresent(comEnte->enteCommon.setId(comEnte.getId()));
		return enteCommon.getId();
	}

	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class }, timeout = 60000, readOnly = false)
	public void downloadFromAlfresco(AllegatoDTO allegato) throws Exception {
		if(allegato.getDimensione()==0 && allegato.getDescrizione().equalsIgnoreCase("Provvedimento Finale (Pubblico)")) {
			if(!allegato.getContenuto().equals(AllegatoService.PLACEHOLDERTEMPIDCMS)) {
				//rimuovo il contenuto da alfresco applicazione
				//this.httpSvc.deleteCmisIdFromCms(deleteUrl, allegato.getContenuto(), nomeApplicazione.toLowerCase(), true);
			}
			Path pathFile= Path.of(this.localBasePath,allegato.getPathCms(),allegato.getNome());
			File file = pathFile.toFile();
			if(file.exists())
			{
				file.delete();
			}else {
				log.warn(IMigratorService.LOG_MIGRAZIONE_MARKER,"File vuoto non trovato localmente {}",pathFile);
			}
			//cerco l'idAlfresco corrispondente
			//es path /autpae/putt/MIGRAZIONE_AUTPAE/AP20004-18-2012/Trasmissione/395
			String[] tokensPath=allegato.getPathCms().split("/");
			if(tokensPath.length==0) {
				log.error(IMigratorService.LOG_MIGRAZIONE_MARKER,"Path cms inatteso {}",allegato.getPathCms());
				throw new RuntimeException();
			}
			Integer tkeStDocId=null;
			try {
				tkeStDocId=Integer.parseInt(tokensPath[tokensPath.length-1]);
			}catch(Exception e) {
				throw new RuntimeException(
						String.format("Errore nella conversione dell'ultimo token del path, atteso intero: {} , {}",
								tokensPath[tokensPath.length-1],allegato.getPathCms()));
			}
			String idAlfresco = this.civSvc.getIdCmsFromTkeStDocId(tkeStDocId);
			if(StringUtil.isNotEmpty(idAlfresco)) {
				proxyAlfrescoOrigin.getDocumentIntoLocalFile(idAlfresco, pathFile.toString());
				allegato.setDimensione((int)pathFile.toFile().length());
				String checksum = CheckSumUtil.getCheckSum(pathFile.toFile());
				allegato.setChecksum(checksum);
				allegato.setContenuto(AllegatoService.PLACEHOLDERTEMPIDCMS);
				allegatoDao.update(allegato);
			}else {
				log.error(IMigratorService.LOG_MIGRAZIONE_MARKER,"Nessun id alfresco trovato {}",allegato);
			}
		}else {
			log.error(IMigratorService.LOG_MIGRAZIONE_MARKER,"Il file da rielaborare non ha dimensione 0 {}",allegato);
		}
		
	}
	
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, timeout = 60000, readOnly = false)
	public void migraMailPraticaMigrataPutt(InfoAutPaesAlfaBean infoPratica) throws Exception
	{
		AutpaeDtoBuilder.localBasePath = localBasePath;
		boolean is016=false;
		// init comuniCompleto...
		this.initComuniData();
		// cerco se già esiste
		Integer idFascicoloPreesistente = null;
		try 
		{
			idFascicoloPreesistente = fascicoloDao.findByTPraticaIdAndCodice(infoPratica.getPraticaId(),infoPratica.getCodicePratica());
		} 
		catch (EmptyResultDataAccessException e) {
			throw new BaseMigrationException(infoPratica, "Pratica non migrata ", null);
		}
		if(infoPratica.getIstatAmm().equals("016")) {
			is016=true;
		}
		FascicoloDTO fascicoloDTO = new FascicoloDTO();
		fascicoloDTO=fascicoloDao.find(idFascicoloPreesistente.longValue());
		migraMailPutt("MIGRAZIONE", fascicoloDTO, infoPratica, is016);
	}
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, timeout = 60000, readOnly = false)
	public void migraMailPraticaMigrataPptr(InfoAutPaesPptrAlfa infoPratica) throws Exception
	{
		AutpaeDtoBuilder.localBasePath = localBasePath;
		boolean is016=false;
		// init comuniCompleto...
		this.initComuniData();
		// cerco se già esiste
		// cerco se già esiste
		Integer idFascicoloPreesistente = null;
		try {
			idFascicoloPreesistente = this.fascicoloDao.findByTPraticaId(infoPratica.gettPraticaAppptrId());
		} catch (EmptyResultDataAccessException e) {
			throw new BaseMigrationException(infoPratica, "Pratica non migrata ", null);
		}
		if(infoPratica.getIstatAmm().equals("016")) {
			is016=true;
		}
		FascicoloDTO fascicoloDTO = new FascicoloDTO();
		fascicoloDTO=fascicoloDao.find(idFascicoloPreesistente.longValue());
		AutPaesPptrPratica pptrPratica = civSvc.getAutPaesPptrPratica(infoPratica.getCodicePraticaAppptr(),
				infoPratica.getProgPubblicazione());
		if(pptrPratica==null) {
			throw new Exception("Nessun record in PPTR_PRATICA codiceAppptr:"+infoPratica.getCodicePraticaAppptr()+" prog:"+infoPratica.getProgPubblicazione()); 
		}
		migraMailPraticaPptr(infoPratica, fascicoloDTO, pptrPratica);
	}
	
	@Autowired
	private AllegatoFascicoloFullRepository allegatoFascicoloDao;
	
	/**
	 * per rielaborare i file che durante la migrazione sono risultati a 0
	 * @throws Exception 
	 * @autor Adriano Colaianni
	 * @date 23 dic 2021
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, timeout = 60000, readOnly = false)
	public void rielaboraFileVuotoPptr(AllegatoDTO allegato) throws Exception {
		try {
		if(allegato.getDimensione()==0 && 
				allegato.getContenuto().startsWith(AllegatoService.PLACEHOLDERTEMPIDCMS)) {
			//se provvedimento allora lo cerco con la query dei provvedimenti..
			//altrimenti prelevo con la query degli allegati
			AllegatoFascicoloSearch allFascSearch=new AllegatoFascicoloSearch();
			allFascSearch.setIdAllegato(allegato.getId());
			PaginatedList<AllegatoFascicoloDTO> allFascList = allegatoFascicoloDao.search(allFascSearch);
			if(ListUtil.isEmpty(allFascList.getList())){
				log.error(IMigratorService.LOG_MIGRAZIONE_MARKER,"Errore nel retrieval del fascicolo dall'allegato {}",allegato);
				return;
			}
			Long idFascicolo=allFascList.getList().get(0).getIdFascicolo();
			FascicoloDTO fascicoloDTO = fascicoloDao.find(idFascicolo);
			List<InfoAutPaesPptrAlfa> listaInfoPratiche = 
					this.civSvc.getPraticheByCodiciEnteDelegato(List.of(fascicoloDTO.getCodice()));
			InfoAutPaesPptrAlfa infoPratica = listaInfoPratiche.get(0);
			if(ListUtil.isEmpty(listaInfoPratiche)) {
				log.error(IMigratorService.LOG_MIGRAZIONE_MARKER,"Errore nel retrieval del fascicolo dall'allegato {}",allegato);
				return;
			}
			boolean trovato=false;
			if(allegato.getTitolo().equals("PROVVEDIMENTO_FINALE") ||
					allegato.getTitolo().equals("PARERE_MIBAC")) {
				List<VtnoAllegatiPptr> allegatiProvvedimentoOld = civSvc
						.getListAllegatiProvvedimento(fascicoloDTO.getCodicePraticaAppptr(),infoPratica.getProgPubblicazione());
				loadBlobFromAlfresco(allegatiProvvedimentoOld);
				// pathcms= /autpae/autpae/MIGRAZIONE_AUTPAE/AP72036-16-2017/Allegati/1861843
				for(VtnoAllegatiPptr provvAll:allegatiProvvedimentoOld) {
					if(provvAll.getNomeFile().equals(allegato.getNome())) {
						trovato=true;
						scriviSuFilesystem(provvAll.getBinContent(),allegato);
						allegatoDao.update(allegato);
					}
				}
			}else {
				// pannello allegati
				// sono allegati multitipo...
				List<VtnoAllegatiPptr> allegatiPraticaOld = civSvc
						.getListAllegatiFascicolo(infoPratica.getCodicePraticaAppptr());
				loadBlobFromAlfresco(allegatiPraticaOld);
				for(VtnoAllegatiPptr provvAll:allegatiPraticaOld) {
					if(provvAll.getNomeFile().equals(allegato.getNome())) {
						trovato=true;
						scriviSuFilesystem(provvAll.getBinContent(),allegato);
						allegatoDao.update(allegato);
					}
				}
			} 
			if(!trovato) {
				log.error(IMigratorService.LOG_MIGRAZIONE_MARKER,"Il file da rielaborare ha dimensione 0 non è stato trovato {}",allegato);
				allegato.setDimensione(-1);
				allegatoDao.update(allegato);
			}
		}else {
			log.error(IMigratorService.LOG_MIGRAZIONE_MARKER,"Il file da rielaborare non ha dimensione 0 {}",allegato);
			allegato.setDimensione(-1);
			allegatoDao.update(allegato);
		}
		}catch(Exception e) {
			allegato.setDimensione(-1);
			allegatoDao.update(allegato);
			log.error(IMigratorService.LOG_MIGRAZIONE_MARKER,"Errore durante il download del file a 0 {}",allegato);
		}
		
	}
	
	private void scriviSuFilesystem(Blob blob ,AllegatoDTO allegato) throws Exception {
		//lo scrivo sul filesystem
		Path localPath=Paths.get(localBasePath,allegato.getPathCms(),allegato.getNome());
		File outFile=localPath.toFile();
			if(outFile.exists() && outFile.length()==blob.length()) {
				//già scritto.. evito di riscriverlo
				allegato.setChecksum(CheckSumUtil.getCheckSum(outFile));
				allegato.setDimensione(Math.toIntExact(outFile.length()));
			}else {
				if(outFile.exists()) {
					outFile.delete();
				}
				try(BufferedOutputStream os=new BufferedOutputStream(new FileOutputStream(outFile))){
					IOUtils.copy(blob.getBinaryStream(), os);
					allegato.setChecksum(CheckSumUtil.getCheckSum(outFile));
					allegato.setDimensione(Math.toIntExact(outFile.length()));
				}catch(Exception e) {
					log.error(IMigratorService.LOG_MIGRAZIONE_MARKER,"Error writing file locally: " +outFile.getName(),e);
					throw e;
				}	
			}	
		if(blob instanceof MockBlob) {
			blob.free();//libero il file temporaneo
		}
	}
		
}
