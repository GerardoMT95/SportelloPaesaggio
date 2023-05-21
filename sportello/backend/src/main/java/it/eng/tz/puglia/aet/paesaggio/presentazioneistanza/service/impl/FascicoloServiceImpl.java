package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.oxm.ValidationFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.DatabaseConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.service.interfacce.AssegnamentoFascicoloService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.DettaglioCorrispondenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.FascicoloDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.IpaEnteDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PagamentoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainStringValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PresentaIstanzaDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.ValidInfoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.SelectOptionDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.allegati.AllegatiDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.allegati.DocumentazioneAmministrativaDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.allegati.DocumentazioneTecnicaDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.allegati.DocumentoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.istanza.AltroTitolareDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.istanza.DichiarazioniDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.istanza.InformazionePersonaleDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.istanza.IstanzaDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.istanza.LocalizazioneDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.istanza.RichiedenteDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.istanza.RichiedenteInfo;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.istanza.TecnicoIncaricatoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.istanza.UlterioriInformazioniDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.istruttoria.ProtocolloDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.DestinazioneUrbanisticaDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.AllowedMimeType;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.AttivitaDaEspletare;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.CheckPagamentoContenutoEnum;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.FasiAssegnazione;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.ParchiPaesaggiImmobiliSigla;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.RuoloPraticaEnum;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.RuoloReferente;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.SezioneAllegati;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.StatoAttivita;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoReferente;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.geo.service.LayerGeoService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatoDelegatoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AltreDichiarazioniRichDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ComuniCompetenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ConfigurazioniEnteDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DestinatarioDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DisclaimerDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DisclaimerPraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.IntegrazioneDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.LocalizzazioneInterventoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.NumeroPraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PagamentiMypayDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ParchiPaesaggiImmobiliDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ParticelleCatastaliDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PptrSelezioniDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDelegatoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaPagamentiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PrivacyDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ProtocolloDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ProvvedimentoFinaleDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ReferenteFascicoloDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ReferentiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ReferentiDocumentoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.RelazioneEnteDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TemplateEmailDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TipoContenutoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TnoPptrStrumentiUrbanisticiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.AllegatiRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.AllegatoDelegatoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.AltreDichiarazioniRichRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.ComuniCompetenzaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.DestinazioneUrbanisticaInterventoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.DisclaimerPraticaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.DisclaimerRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.EffettiMitigazioneRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.InquadramentoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.IpaEnteRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.LocalizzazioneInterventoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.PagamentiMypayRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.ParchiPaesaggiImmobiliRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.ParticelleCatastaliRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.PptrSelezioniRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.PraticaDelegatoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.PraticaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.PrivacyRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.ReferenteFascicoloRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.ReferentiDocumentoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.ReferentiRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.RelazioneEnteRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.TipoAziendaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.TipoContenutoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.TnoPptrStrumentiUrbanisticiRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.PagamentiMypaySearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.ParticelleCatastaliSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.PptrSelezioniSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.PraticaSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.TemplateEmailSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.INumeroPraticaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.IRuoloPraticaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.PraticaDelegatoService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.impl.ConfigurazioniEnteService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.impl.ProtocolloLogService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.ProfileUserResponseBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.repository.AnagraficaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.repository.CommonRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.service.RemoteSchemaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler.InvioMailScheduler;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler.ProtocollazioneScheduler;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler.SendPlanToDiogeneScheduler;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler.bean.FascicoloStatoBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler.bean.ProtocollazioneBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.AllegatoService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.ComunicazioniService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.DigitalizzazioneIstanzaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.DiogeneDescrittoreService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.FascicoloService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.GruppiRuoliService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.IPagamentiService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.SchedaTecnicaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria.IstrPraticaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria.ProvvedimentoFinaleService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria.impl.ProtocolloCallService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.schedaTecnica.DescrizioneService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.schedaTecnica.DestinazioneUrbanisticaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.protocollo.ProtocolNumberType;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.protocollo.interceptor.ProtocolloLogInterceptor;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.protocollo.model.SegnaturaProtocollo;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.PlaceHolder;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.SezioneIstruttoria;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.dto.TemplateEmailDestinatariDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.service.IResolveTemplateService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.service.ITemplateMailService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.CapValidator;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.CodiceFiscaleValidator;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.MockMultipartFile;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.PartitaIvaValidator;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.TelefonoValidator;
import it.eng.tz.puglia.batch.queue.IQueueService;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.bean.UserDetail;
import it.eng.tz.puglia.error.exception.CustomCmisException;
import it.eng.tz.puglia.error.exception.CustomValidationException;
import it.eng.tz.puglia.security.util.SecurityUtil;
import it.eng.tz.puglia.service.http.IMyPayWSClientService;
import it.eng.tz.puglia.service.http.bean.ChiediPagatiInputBean;
import it.eng.tz.puglia.service.http.bean.ChiediPagatiOutputBean;
import it.eng.tz.puglia.service.http.bean.InviaDovutiInputBean;
import it.eng.tz.puglia.service.http.bean.InviaDovutiOutputBean;
import it.eng.tz.puglia.service.http.bean.MyPayBaseBean;
import it.eng.tz.puglia.service.http.bean.ProtocolloResponseBean;
import it.eng.tz.puglia.service.registroimprese.ICallRegistroImprese;
import it.eng.tz.puglia.service.registroimprese.bean.RegistroImpreseBean;
import it.eng.tz.puglia.util.crypt.CryptUtil;
import it.eng.tz.puglia.util.date.DateUtil;
import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.util.log.LogUtil;
import it.eng.tz.puglia.util.string.FiscalCodeValidation;
import it.eng.tz.puglia.util.string.StringUtil;

@Service
public class FascicoloServiceImpl extends PraticaDataAwareService implements FascicoloService
{

	private static final Logger LOGGER = LoggerFactory.getLogger(FascicoloServiceImpl.class);

	@Autowired
	private Validator validator;

	@Autowired
	private INumeroPraticaService numeroPratcaService;

	@Autowired
	private RemoteSchemaService remoteSchemaService;

	@Autowired
	private PrivacyRepository privacyDao;

	@Autowired
	private ComuniCompetenzaRepository comuniCompetenzaDao;

	@Autowired
	private LocalizzazioneInterventoRepository localizzazioneInterventoDao;

	@Autowired
	private PraticaRepository praticaDao;

	@Autowired
	private ReferentiRepository referentiDao;

	@Autowired
	private ReferentiDocumentoRepository referentiDocDao;

	@Autowired
	private AltreDichiarazioniRichRepository altreDichDao;

	@Autowired
	private DisclaimerPraticaRepository disclPraticaDao;

	@Autowired
	private DisclaimerRepository disclDao;

	@Autowired
	private LocalizzazioneInterventoRepository localIntDao;

	@Autowired
	private ParticelleCatastaliRepository particelleDao;

	@Autowired
	private ParchiPaesaggiImmobiliRepository parchiPaesaggiImmobiliDao;

	@Autowired
	private DestinazioneUrbanisticaInterventoRepository destinazioneUrbabisticaInterventoDao;

	@Autowired
	private DestinazioneUrbanisticaService destinazioneUrbanisticaService;

	@Autowired
	private AllegatiRepository allegatoDao;

	@Autowired
	private AllegatoService allegatoSvc;

	@Autowired
	private PptrSelezioniRepository pptrRepo;

	@Autowired
	private SchedaTecnicaService schedaTecnicaService;

	@Autowired
	private DescrizioneService descrizioneService;

	@Autowired
	ConfigurazioniEnteService confEnteService;

	@Autowired
	ProtocolloLogService protocolloService;

	@Autowired
	ProtocolloCallService protocolloCallService;

	@Autowired
	ProvvedimentoFinaleService provvedimentoService;

	@Autowired
	TnoPptrStrumentiUrbanisticiRepository strumentiUrbanisticiRepository;

	@Autowired
	private ITemplateMailService templateEmail;

	@Autowired
	private ComunicazioniService comunicazioniService;

	@Autowired
	CommonRepository commonRepository;

	@Autowired
	IstrPraticaService istrPraticaService;
	@Autowired
	private IResolveTemplateService resolvePlaceholderSvc;
	@Autowired
	GruppiRuoliService gruppiRuoliService;

	@Autowired
	private LayerGeoService layerGeoSvc;

	@Autowired
	private PptrSelezioniRepository pptrSelezioniDao;
	@Autowired
	private PagamentiMypayRepository myPayDao;

	@Autowired
	IMyPayWSClientService myPayClientSvc;

	@Autowired
	RemoteSchemaService remoteService;

	@Autowired
	private AssegnamentoFascicoloService assegnaFascicolo;

	@Autowired
	private AnagraficaRepository anagraficaRepository;
	@Autowired
	private TipoAziendaRepository tipoAziendaRepository;
	@Autowired
	private IpaEnteRepository ipaEnteRepository;

	@Autowired
	private IPagamentiService pagSvc;

	@Autowired
	private IQueueService queueService;
	@Autowired
	DiogeneDescrittoreService diogeneDescrSvc;

	@Autowired
	private ICallRegistroImprese callRegistroImprese;
	@Autowired
	RelazioneEnteRepository relazioneEnteDao;
	@Autowired
	TipoContenutoRepository tipoContenutoDao;
	@Autowired 
	private IRuoloPraticaService ruoloPraticaSvc;
	@Autowired
	private PraticaDelegatoService pdService;
	
	@Autowired
	DigitalizzazioneIstanzaService digitalizzazioneSvc;
	
	@Autowired
	PraticaDelegatoRepository pdDao;
	@Autowired
	AllegatoDelegatoRepository allDelDao;
	@Autowired
	ReferenteFascicoloRepository referenteDao;
	
	@Autowired
	EffettiMitigazioneRepository effettiMitigazioneDao;
	@Autowired
	InquadramentoRepository inquadramentoDao;

	private void createReferenteEdoc(final UUID praticaId, final String tipo, final InformazionePersonaleDto dtoOut)
	{
		final ReferentiDTO entity = new ReferentiDTO();
		entity.setTipoReferente(tipo);
		entity.setPraticaId(praticaId);
		if(tipo.contentEquals(TipoReferente.SD.name())) {
			entity.setNome(dtoOut.getNome());
			entity.setCognome(dtoOut.getCognome());
			entity.setCodiceFiscale(dtoOut.getCodiceFiscale());
		}
		final long richId = this.referentiDao.insert(entity);
		entity.setId(richId);
		dtoOut.setId(entity.getId());
		final ReferentiDocumentoDTO docRichiedente = new ReferentiDocumentoDTO(richId);
		docRichiedente.setReferenteId(richId);
		this.referentiDocDao.insert(docRichiedente);
	}
	
	/**
	 * validazione per inserimento
	 * @author acolaianni
	 *
	 * @param pratica
	 * @return
	 */
	private String validateForInsert(final PraticaDTO pratica) {
		final StringBuilder errorMessageSb = new StringBuilder();
		final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		final Validator validator = factory.getValidator();
		final Set<ConstraintViolation<PraticaDTO>> violations = validator.validate(pratica);
		if (ListUtil.isNotEmpty(violations)) {
			violations.stream().forEach(el -> errorMessageSb.append(el.getMessage()).append("\n"));
		}
		if(StringUtil.isEmpty(pratica.getRuoloPratica())) {
			final boolean trovato = 
					this.ruoloPraticaSvc.select().stream().filter(el->el.getValue().equals(pratica.getRuoloPratica())).findAny().isEmpty();
			if(!trovato) {
				errorMessageSb
				.append("Ruolo pratica non previsto ")
				.append(pratica.getRuoloPratica())
				.append("\n");
			}
		}
		return errorMessageSb.toString();
	}
	
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public FascicoloDto creaNuovaPratica(final PraticaDTO dto) throws Exception
	{
		final String message = this.validateForInsert(dto);
		if(StringUtil.isNotEmpty(message)) {
			throw new CustomOperationToAdviceException(message);
		}
		final FascicoloDto ret = new FascicoloDto();
		this.commonRepository.getEnteRegione();
		final List<ComuniCompetenzaDTO> comuniCompetenza = this.remoteSchemaService
				.getEntiCompetenzaConCodici(Integer.valueOf(dto.getEnteDelegato()), new Date());
		this.checkComuniCompetenza(comuniCompetenza);
		dto.setId(UUID.randomUUID());
		dto.setUserId(SecurityUtil.getUsername());
		dto.setUserUpdated(SecurityUtil.getUsername());
		dto.setOwner(SecurityUtil.getUsername());
		final Date dataCreazione = new Date();
		dto.setDataCreazione(dataCreazione);
		dto.setDataModifica(dataCreazione);
		LOGGER.info(StringUtil.concateneString("Start getNumeroPratica"));
		if (this.numeroPratcaService.checkNumPraticaAnnoCorrente() == 0)
		{
			LOGGER.info("Init numero pratica = " + this.numeroPratcaService.initNumPraticaAnnoCorrente());
		}
		final NumeroPraticaDTO numeroPratica = this.numeroPratcaService.getOldNumeroPratica();
		this.numeroPratcaService.insertNumeroPratica(numeroPratica.getNextNumero());
		LOGGER.info("Current num. pratica is :" + numeroPratica.getNumeroPratica());
		dto.setCodicePraticaAppptr("APPPTR-" + numeroPratica.getNumeroPratica());
		// carico la privacy da accettare
		final List<PrivacyDTO> privacyCurrent = this.privacyDao.selectCurrent(new Date());
		this.checkEsistenzaPrivacy(privacyCurrent, dto);
		this.praticaDao.insert(dto);
		ret.setFascicoloByPratica(dto);
		// aggiungo i comuni di competenza
		for (final ComuniCompetenzaDTO objToWrite : comuniCompetenza)
		{
			objToWrite.setPraticaId(dto.getId());
			// determino i vincoli artt. 38 e 100
			final TnoPptrStrumentiUrbanisticiDTO art38  = this.strumentiUrbanisticiRepository.findByCodiceIstatAndTipo(objToWrite.getCodIstat(), 2);
			final TnoPptrStrumentiUrbanisticiDTO art100 = this.strumentiUrbanisticiRepository.findByCodiceIstatAndTipo(objToWrite.getCodIstat(), 1);
			objToWrite.setVincoloArt38 (TnoPptrStrumentiUrbanisticiDTO.generaVincoloArt38 (art38 , objToWrite.getDescrizione()));
			objToWrite.setVincoloArt100(TnoPptrStrumentiUrbanisticiDTO.generaVincoloArt100(art100));
			this.comuniCompetenzaDao.insert(objToWrite);
		}
		ret.setComuniCompetenza(comuniCompetenza);
		// creo sezione istanza
		final IstanzaDto istanza = new IstanzaDto();
		ret.setIstanza(istanza);
		this.creaAltreEntity(dto, istanza);
		return ret;
	}
	
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public FascicoloDto creaNuovaPratica(PraticaDTO pratica, PraticaDelegatoDTO delegato, MultipartFile delega) throws Exception
	{
	    final String message = this.validateForInsert(pratica);
	    if(StringUtil.isNotEmpty(message)) 
		throw new CustomOperationToAdviceException(message);
	    final FascicoloDto fascicolo = new FascicoloDto();
	    this.commonRepository.getEnteRegione();
	    final List<ComuniCompetenzaDTO> comuniCompetenza = this.remoteSchemaService
		    .getEntiCompetenzaConCodici(Integer.valueOf(pratica.getEnteDelegato()), new Date());
	    this.checkComuniCompetenza(comuniCompetenza);
	    pratica.setId(UUID.randomUUID());
	    pratica.setUserId(SecurityUtil.getUsername());
	    pratica.setUserUpdated(SecurityUtil.getUsername());
	    pratica.setOwner(SecurityUtil.getUsername());
	    final Date dataCreazione = new Date();
	    pratica.setDataCreazione(dataCreazione);
	    pratica.setDataModifica(dataCreazione);
	    LOGGER.info(StringUtil.concateneString("Start getNumeroPratica"));
	    if (this.numeroPratcaService.checkNumPraticaAnnoCorrente() == 0)
		LOGGER.info("Init numero pratica = " + this.numeroPratcaService.initNumPraticaAnnoCorrente());
	    final NumeroPraticaDTO numeroPratica = this.numeroPratcaService.getOldNumeroPratica();
	    this.numeroPratcaService.insertNumeroPratica(numeroPratica.getNextNumero());
	    LOGGER.info("Current num. pratica is :" + numeroPratica.getNumeroPratica());
	    pratica.setCodicePraticaAppptr("APPPTR-" + numeroPratica.getNumeroPratica());
	    // carico la privacy da accettare
	    final List<PrivacyDTO> privacyCurrent = this.privacyDao.selectCurrent(new Date());
	    this.checkEsistenzaPrivacy(privacyCurrent, pratica);
	    this.praticaDao.insert(pratica);
	    fascicolo.setFascicoloByPratica(pratica);
	    // aggiungo i comuni di competenza
	    for (final ComuniCompetenzaDTO objToWrite : comuniCompetenza)
	    {
		objToWrite.setPraticaId(pratica.getId());
		// determino i vincoli artt. 38 e 100
		final TnoPptrStrumentiUrbanisticiDTO art38  = this.strumentiUrbanisticiRepository.findByCodiceIstatAndTipo(objToWrite.getCodIstat(), 2);
		final TnoPptrStrumentiUrbanisticiDTO art100 = this.strumentiUrbanisticiRepository.findByCodiceIstatAndTipo(objToWrite.getCodIstat(), 1);
		objToWrite.setVincoloArt38 (TnoPptrStrumentiUrbanisticiDTO.generaVincoloArt38 (art38 , objToWrite.getDescrizione()));
		objToWrite.setVincoloArt100(TnoPptrStrumentiUrbanisticiDTO.generaVincoloArt100(art100));
		this.comuniCompetenzaDao.insert(objToWrite);
	    }
	    fascicolo.setComuniCompetenza(comuniCompetenza);
	    // creo sezione istanza
	    final IstanzaDto istanza = new IstanzaDto();
	    fascicolo.setIstanza(istanza);
	    this.creaAltreEntity(pratica, istanza);
	    if(delegato != null) {
	    	delegato.setId(fascicolo.getId());
	    	delegato.setDelgatoCorrente(true);
	    	pdService.addDelegato(delegato, delega, pratica);
	    }
		return fascicolo;
	}

	private void creaAltreEntity(final PraticaDTO dto, final IstanzaDto istanza) {
		// scrivo già i record vuoti per le altre entità:
		// richiedente
		final RichiedenteDto richOut = new RichiedenteDto();
		if(dto.getRuoloPratica().equals(RuoloPraticaEnum.PROPONENTE.getCodice())) {
			//setto già nome cognome e cf
			UserDetail userDetail = SecurityUtil.getUserDetail();
			if(userDetail!=null) {
				richOut.setNome(userDetail.getFirstName());
				richOut.setCognome(userDetail.getLastName());
				richOut.setCodiceFiscale(userDetail.getFiscalCode());
			}
		}
		this.createReferenteEdoc(dto.getId(), TipoReferente.SD.getValue(), richOut);
		istanza.setRichiedente(richOut);
		// tecnico incaricato
		final TecnicoIncaricatoDto tecnicoOut = new TecnicoIncaricatoDto();
		this.createReferenteEdoc(dto.getId(), TipoReferente.TR.getValue(), richOut);
		istanza.setTecnicoIncaricato(tecnicoOut);
		// create record per DichiarazioniDto
		istanza.setDichiarazioni(new DichiarazioniDto());
		if (dto.getTipoProcedimento() == 2)
		{
			final AltreDichiarazioniRichDTO dichSpecifiche = new AltreDichiarazioniRichDTO();
			dichSpecifiche.setPraticaId(dto.getId());
			this.altreDichDao.insert(dichSpecifiche);
		}
		// inserisco già tutti i disclaimer della pratica da accettare.
		final List<DisclaimerDTO> disclaimer = this.disclDao.select(TipoReferente.SD.getValue(), dto.getTipoProcedimento(),dto.getDataCreazione(),false);
		if (disclaimer != null)
		{
			disclaimer.forEach(el -> {
				this.disclPraticaDao.insert(new DisclaimerPraticaDTO(el.getId(), "", dto.getId()));
			});
		}
	}
	
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public FascicoloDto creaNuovaPraticaDaMigrazione(final PraticaDTO dto) throws Exception
	{
		final FascicoloDto ret = new FascicoloDto();
		final List<ComuniCompetenzaDTO> comuniCompetenza = this.remoteSchemaService
				.getEntiCompetenzaConCodici(Integer.valueOf(dto.getEnteDelegato()), new Date());
		this.checkComuniCompetenza(comuniCompetenza);
		dto.setId(UUID.randomUUID());
		//aggiorno eventualmente il contatore dell'anno...
		final String[] codiceParts = dto.getCodicePraticaAppptr().split("-");
		this.numeroPratcaService.avanzaNumPraticaAnno(Integer.parseInt(codiceParts[2]),Integer.parseInt(codiceParts[1]));
		// carico la privacy da accettare
		final List<PrivacyDTO> privacyCurrent = this.privacyDao.selectCurrent(dto.getDataCreazione());
		this.checkEsistenzaPrivacy(privacyCurrent, dto);
		this.praticaDao.insert(dto);
		// aggiungo i comuni di competenza
		for (final ComuniCompetenzaDTO objToWrite : comuniCompetenza)
		{
			objToWrite.setPraticaId(dto.getId());
			// determino i vincoli artt. 38 e 100
			final TnoPptrStrumentiUrbanisticiDTO art38  = this.strumentiUrbanisticiRepository.findByCodiceIstatAndTipo(objToWrite.getCodIstat(), 2);
			final TnoPptrStrumentiUrbanisticiDTO art100 = this.strumentiUrbanisticiRepository.findByCodiceIstatAndTipo(objToWrite.getCodIstat(), 1);
			objToWrite.setVincoloArt38 (TnoPptrStrumentiUrbanisticiDTO.generaVincoloArt38 (art38 , objToWrite.getDescrizione()));
			objToWrite.setVincoloArt100(TnoPptrStrumentiUrbanisticiDTO.generaVincoloArt100(art100));
			this.comuniCompetenzaDao.insert(objToWrite);
		}
		// creo sezione istanza
		final IstanzaDto istanza = new IstanzaDto();
		ret.setIstanza(istanza);
		if (dto.getTipoProcedimento() == 2)
		{
			final AltreDichiarazioniRichDTO dichSpecifiche = new AltreDichiarazioniRichDTO();
			dichSpecifiche.setPraticaId(dto.getId());
			this.altreDichDao.insert(dichSpecifiche);
		}
		return ret;
	}
	
	
	private PraticaDTO checkFattibilita(final PraticaDTO pratica) throws CustomOperationToAdviceException {
		/*check fattibilità*/
		final PraticaDTO praticaSaved = this.praticaDao.find(pratica);
		this.checkDiMiaCompetenza(praticaSaved);
		this.checkStatoForUpdate(praticaSaved);
		return praticaSaved;
		/* end check*/
	}
	
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public FascicoloDto cambioEnteDelegatoPratica(final FascicoloDto dto) throws Exception{
		final PraticaDTO pratica = this.checkFattibilita(dto.getPraticaByFascicolo());
		this.checkPagamenti(pratica.getId().toString());
		final FascicoloDto oldDto = this.find(dto);
		if(oldDto.getEnteDelegato().equals(dto.getEnteDelegato())) //nessun cambiamento.... 
			return oldDto; 
		final List<ComuniCompetenzaDTO> comuniCompetenza = this.remoteSchemaService
				.getEntiCompetenzaConCodici(Integer.valueOf(dto.getEnteDelegato()), new Date());
		this.checkComuniCompetenza(comuniCompetenza);
		//per i comuni di competenza in intersezione lascio i dati già presenti....
		//per gli altri sego tutto...
		if(oldDto.getComuniCompetenza()!=null) {
			for(final ComuniCompetenzaDTO oldComune:oldDto.getComuniCompetenza()) {
				//cerco se esiste nel nuovo set..
				final boolean newPresent = comuniCompetenza.stream().filter(newComune->newComune.getEnteId()==oldComune.getEnteId()).findAny().isPresent();
				if(!newPresent) {
					//rimuovo tutti gli effetti di questo comune
					//localizzazione
					LocalizzazioneInterventoDTO localInterventoDTO = new LocalizzazioneInterventoDTO();
					localInterventoDTO.setPraticaId(dto.getId());
					localInterventoDTO.setComuneId(oldComune.getEnteId());
					try {
						localInterventoDTO=this.localIntDao.find(localInterventoDTO);
						final ParticelleCatastaliSearch psearch=new ParticelleCatastaliSearch();
						psearch.setComuneId(oldComune.getEnteId()+"");
						psearch.setPraticaId(oldDto.getId()+"");
						final List<ParticelleCatastaliDTO> particelle = this.particelleDao.select(oldDto.getId(),(long)oldComune.getEnteId());
						List<Long> oidsToRemove=null;
						if(particelle!=null) {
							oidsToRemove=particelle
									.stream()
									.map(particella->particella.getOid())
									.filter(oid->oid!=null).collect(Collectors.toList());
						}
							
						this.localIntDao.delete(localInterventoDTO); // a cascata vanno via anche parchi_paesaggi_immobili, particelle_catastali, destinazione_urbanistica_intervento
						//rimuovo eventuali file shape 
						this.allegatoSvc.deleteAllOfType(dto.getId(),SezioneAllegati.LOCALIZZAZIONE);
						oldDto.setHasShape(false); //rimuovo il flag hasShape
						this.praticaDao.update(oldDto.getPraticaByFascicolo());
						//rimuovo eventuali shape su layer con is_custom=1 oppure quelle legate alle particelle
						this.layerGeoSvc.removeShape(oldDto.getId(),oidsToRemove);
					}catch(final EmptyResultDataAccessException e) {
					//nessuna localizzazione...	
					}
					this.comuniCompetenzaDao.delete(oldComune);
				}else {
					//è di competenza del nuovo ente delegato... lascio tutto...
				}
			}
		}
		//i new non esistenti sui vecchi... li aggiungo..
		for (final ComuniCompetenzaDTO objToWrite : comuniCompetenza)
		{
			boolean giaPresente =false;
			if(oldDto.getComuniCompetenza()!=null) {
				giaPresente = oldDto.getComuniCompetenza().stream().filter(oldComune->oldComune.getEnteId()==objToWrite.getEnteId()).findAny().isPresent();
			}
			objToWrite.setPraticaId(dto.getId());
			// determino i vincoli artt. 38 e 100
			final TnoPptrStrumentiUrbanisticiDTO art38  = this.strumentiUrbanisticiRepository.findByCodiceIstatAndTipo(objToWrite.getCodIstat(), 2);
			final TnoPptrStrumentiUrbanisticiDTO art100 = this.strumentiUrbanisticiRepository.findByCodiceIstatAndTipo(objToWrite.getCodIstat(), 1);
			objToWrite.setVincoloArt38 (TnoPptrStrumentiUrbanisticiDTO.generaVincoloArt38 (art38 , objToWrite.getDescrizione()));
			objToWrite.setVincoloArt100(TnoPptrStrumentiUrbanisticiDTO.generaVincoloArt100(art100));
			if(giaPresente) {
				this.comuniCompetenzaDao.update(objToWrite);
			}else {
				this.comuniCompetenzaDao.insert(objToWrite);	
			}
		}
		pratica.setEnteDelegato(dto.getEnteDelegato());
		this.praticaDao.update(pratica);
		return oldDto;
	}

	
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public FascicoloDto cambioTipoProcedimentoPratica(final FascicoloDto dto) throws Exception{
		final PraticaDTO pratica = this.checkFattibilita(dto.getPraticaByFascicolo());
		final FascicoloDto oldDto = this.find(dto);
		if(oldDto.getTipoProcedimento()==dto.getTipoProcedimento()) //nessun cambiamento.... 
			return oldDto;
		final AltreDichiarazioniRichDTO dichSpecifiche = new AltreDichiarazioniRichDTO();
		dichSpecifiche.setPraticaId(dto.getId());
		this.altreDichDao.delete(dichSpecifiche);
		if (dto.getTipoProcedimento() == 2)  //nuovo 2 vecchio diverso da 2
		{
			final AltreDichiarazioniRichDTO dichSpecificheNew = new AltreDichiarazioniRichDTO();
			dichSpecificheNew.setPraticaId(dto.getId());
			this.altreDichDao.insert(dichSpecificheNew);
		}
		if (oldDto.getTipoProcedimento() == 2)  //vecchio 2 nuovo diverso da 2 elimino le sezioni aggiuntive del 2
		{
			this.effettiMitigazioneDao.deleteById(oldDto.getId());
			this.inquadramentoDao.deleteById(oldDto.getId());
		}
		this.disclPraticaDao.deleteByPratica(oldDto.getId());
		// inserisco già tutti i disclaimer della pratica da accettare.
		final List<DisclaimerDTO> disclaimer = this.disclDao.select(TipoReferente.SD.getValue(), dto.getTipoProcedimento(),
				dto.getDataCreazione(),false);
		if (disclaimer != null)
		{
			disclaimer.forEach(el -> {
				this.disclPraticaDao.insert(new DisclaimerPraticaDTO(el.getId(), "", dto.getId()));
			});
		}
		//rimuovo eventuali selezioni in scheda tecnica -> descrizione che non hanno piu' senso
		//procedimento_qualificazioni -> descrizione_scheda_tecnica_values
		this.descrizioneService.deleteValueNonAmmessi(oldDto.getId(), dto.getTipoProcedimento());
		//ucp_tipo_procedimento -> ulteriori_contesti_paesaggistici
		this.pptrSelezioniDao.deleteNonAmmessi(oldDto.getId(), dto.getTipoProcedimento());
		//rimuovo allegati che non sarebbero ammessi... (forse è meglio lasciarli con deleted a true...)
		this.allegatoDao.rimuoviAllegatiNonAmmessi(oldDto.getId(), oldDto.getTipoProcedimento(), dto.getTipoProcedimento());
		pratica.setTipoProcedimento(dto.getTipoProcedimento());
		this.praticaDao.update(pratica);
		return oldDto;
	}
	
	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_READ)
	public FascicoloDto find(FascicoloDto fascicolo) throws Exception
	{
		final PraticaDTO pratica = this.praticaDao.findByCodice(fascicolo.getPraticaByFascicolo());
		//this.checkDiMiaCompetenza(pratica);
		//Accesso in lettura per dare visualizzazione anche ad altri titolari (non owner)
		this.checkDiMiaCompetenzaInLettura(pratica);
		fascicolo = this.doFind(pratica, false);
		return fascicolo;
	}
	
	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_READ)
	public FascicoloDto findByCodicePraticaAppptr(String codice,boolean withoutCheck) throws Exception
	{
		final PraticaDTO pratica = this.praticaDao.findByCodice(codice);
		if(!withoutCheck) {
			this.checkDiMiaCompetenza(pratica);
		}
		FascicoloDto fascicolo = this.doFind(pratica, false,false,true);
		return fascicolo;
	}
	
	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_READ)
	public FascicoloDto find(final PraticaSearch filters) throws Exception
	{
		if(filters.getId() == null && StringUtil.isEmpty(filters.getCodicePraticaAppptr()))
		{
			LOGGER.error("Codice pratica e id assenti: non posso eseguire la ricerca");
			throw new Exception("Codice pratica e id assenti: non posso eseguire la ricerca");
		}
		PraticaDTO pratica = null;
		FascicoloDto fascicolo;
		if (filters.getId() != null) {
			pratica = this.praticaDao.find(filters.getId());
		} else {
			pratica = this.praticaDao.findByCodice(filters.getCodicePraticaAppptr());
		}
		//this.checkDiMiaCompetenza(pratica);
		//Accesso in lettura per dare visualizzazione anche ad altri referenti (non owner)
		this.checkDiMiaCompetenzaInLettura(pratica);
		fascicolo = this.doFind(pratica, true);
		List<String> denominazioneFunzionario = this.praticaDao.getDenominazioneFunzionario(pratica.getId(), filters.getOrganizzazioneAutenticata(), FasiAssegnazione.ISTRUTTORIA.name());
		List<String> denominazioneRup = this.praticaDao.getDenominazioneRup(pratica.getId(), filters.getOrganizzazioneAutenticata(), FasiAssegnazione.ISTRUTTORIA.name());
		if(denominazioneFunzionario.size() > 0)
			fascicolo.setDenominazioneFunzionario(denominazioneFunzionario.get(0));
		if(denominazioneRup.size() > 0)
			fascicolo.setDenominazioneRup(denominazioneRup.get(0));
		return fascicolo;
	}
	
	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public FascicoloDto findByIdTxWriteWithoutCheck(final UUID praticaId) throws Exception
	{
		final PraticaDTO pratica = this.praticaDao.findTxWrite(praticaId);
		return this.doFind(pratica,false,true,true);
	}
	
	
	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_READ)
	public FascicoloDto findById(final UUID praticaId) throws Exception
	{
		final PraticaDTO pratica = this.praticaDao.find(praticaId);
		this.checkDiMiaCompetenza(pratica);
		return this.doFind(pratica);
	}

	private FascicoloDto doFind(final PraticaDTO pratica) throws CustomOperationToAdviceException, Exception {
		return this.doFind(pratica, true);
	}
	
	private FascicoloDto doFind(final PraticaDTO pratica, final boolean inIstruttoria) throws Exception{
		return this.doFind(pratica, inIstruttoria, false);
	}
	
	private FascicoloDto doFind(final PraticaDTO pratica, final boolean inIstruttoria,final boolean forMigrazione) throws Exception{
		return this.doFind(pratica, inIstruttoria, forMigrazione, false);
	}
	
	/**
	 * 
	 * @author acolaianni
	 *
	 * @param pratica
	 * @param inIstruttoria
	 * @param forMigrazione
	 * @param batch bypassa i controlli sull' utente loggato solo nel caso in cui inIstruttoria è false  
	 * @return
	 * @throws Exception
	 */
	private FascicoloDto doFind(final PraticaDTO pratica, final boolean inIstruttoria,final boolean forMigrazione,boolean batch) throws Exception
	{
		final FascicoloDto fascicolo = new FascicoloDto();
		// ricarico i dati principali di pratica
		fascicolo.setFascicoloByPratica(pratica);
		settaDatiOwner(pratica, fascicolo,batch);
		// riprendo i comuni di competenza
		fascicolo.setComuniCompetenza(this.comuniCompetenzaDao.selectByPratica(pratica.getId(),forMigrazione));
		// costruisco il restante fascicolo
		final IstanzaDto istanza = new IstanzaDto();
		fascicolo.setIstanza(istanza);
		// istanza:
		try
		{
			// -richiedente
			ReferentiDTO referente;
			referente = this.referentiDao.selectRichiedente(pratica.getId(), TipoReferente.SD.getValue(),forMigrazione);
			final RichiedenteDto richiedente = new RichiedenteDto();
			richiedente.fromEntity(referente);
			fascicolo.getIstanza().setRichiedente(richiedente);
			ReferentiDocumentoDTO docReferente = new ReferentiDocumentoDTO(referente.getId());
			// docReferente.setReferenteId();
			docReferente = this.referentiDocDao.find(docReferente,forMigrazione);
			AllegatiDTO allegatoDoc = null;
			if(forMigrazione) {
				allegatoDoc=this.allegatoSvc.getDocumentoReferenteTxWrite(pratica.getId().toString(), referente.getId(),batch);	
			}else {
				allegatoDoc=this.allegatoSvc.getDocumentoReferente(pratica.getId().toString(), referente.getId(),false,batch);
			}
			docReferente.setDocAllegato(allegatoDoc);
			fascicolo.getIstanza().getRichiedente().setDocumento(docReferente);// fromEntityDoc(docReferente);
			// testo privacy
			fascicolo.setPrivacyText(this.privacyDao.find(new PrivacyDTO(pratica.getPrivacyId())).getTesto());
			// titolarita intervento...
			istanza.setDichiarazioni(new DichiarazioniDto());
			istanza.getDichiarazioni().setTitolarita(referente.getTitolaritaId());
			if (referente.getTitolaritaId() != null
					&& referente.getTitolaritaId() == RuoloReferente.RappLegale.getValue())
			{
				istanza.getDichiarazioni().setDiAvereTitoloRappSpec(referente.getSpecificaTitolarita());
			} else if (referente.getTitolaritaId() != null
					&& referente.getTitolaritaId() == RuoloReferente.Altro.getValue())
			{
				istanza.getDichiarazioni().setDiAvereTitoloAltroSpec(referente.getSpecificaTitolarita());
			}
			istanza.getDichiarazioni().setTitolaritaEsclusivaIntervento(referente.getTitolaritaEsclusiva());
			// altre_dichiarazioni_rich
			if (fascicolo.getTipoProcedimento() == 2)
			{
				AltreDichiarazioniRichDTO altreDichEntity = new AltreDichiarazioniRichDTO();
				altreDichEntity.setPraticaId(fascicolo.getId());
				try
				{
					altreDichEntity = this.altreDichDao.find(altreDichEntity,forMigrazione);
					altreDichEntity.toDto(istanza.getDichiarazioni());
					if (altreDichEntity.getCheck142() != null && altreDichEntity.getCheck142().equals("S"))
					{
						final List<String> art142 = new ArrayList<String>();
						art142.add("142");
						final List<PptrSelezioniDTO> selezioni = this.pptrRepo.searchByIdPraticaSezione(fascicolo.getId(), "art_142", forMigrazione);
						if (selezioni != null && !selezioni.isEmpty())
							selezioni.forEach(s -> {
								art142.add(s.getCodice());
							});
						istanza.getDichiarazioni().setArt142(art142);
					}
				} catch (final Exception e)
				{
					LOGGER.error("Errore nella find del record in altre_dichiarazioni_rich: ", e);
					this.altreDichDao.insert(altreDichEntity);
				}
			}
			// disclaimer_pratica
			istanza.getDichiarazioni().setAltreOpzioni(this.disclPraticaDao.selectIdByPratica(fascicolo.getId(), "S",forMigrazione));
			if (istanza.getDichiarazioni() != null
					&& istanza.getDichiarazioni().getTitolaritaEsclusivaIntervento() != null
					&& istanza.getDichiarazioni().getTitolaritaEsclusivaIntervento().equals("N"))
			{
				final List<AllegatiDTO> allegato = this.allegatoDao.findByPraticaETipo(fascicolo.getId(), 500, forMigrazione);
				if (allegato != null && !allegato.isEmpty())
					istanza.getDichiarazioni().setAllegato(allegato.get(0));
			}
			// tecnicoIncaricato
			ReferentiDTO refTecnico;
			refTecnico = this.referentiDao.selectRichiedente(pratica.getId(), TipoReferente.TR.getValue(),forMigrazione);
			final TecnicoIncaricatoDto tecnico = new TecnicoIncaricatoDto();
			tecnico.fromEntity(refTecnico);
			fascicolo.getIstanza().setTecnicoIncaricato(tecnico);
			ReferentiDocumentoDTO docTecnico = new ReferentiDocumentoDTO(refTecnico.getId());
			// docTecnico.setReferenteId(refTecnico.getId());
			docTecnico = this.referentiDocDao.find(docTecnico,forMigrazione);
			AllegatiDTO allegatoTecnicoDoc =null;
			if(forMigrazione) {
				allegatoTecnicoDoc = this.allegatoSvc.getDocumentoReferenteTxWrite(pratica.getId().toString(), refTecnico.getId(),batch);
			}else {
				allegatoTecnicoDoc = this.allegatoSvc.getDocumentoReferente(pratica.getId().toString(), refTecnico.getId(),forMigrazione,batch);	
			}
			docTecnico.setDocAllegato(allegatoTecnicoDoc);
			fascicolo.getIstanza().getTecnicoIncaricato().setDocumento(docTecnico);// fromEntityDoc(docTecnico);
			
			//Delegato
			popolaDelegati(fascicolo, istanza);
			
			// altri titolari
			final List<ReferentiDTO> altriTitolariEntity = this.referentiDao.selectAltriTitolari(pratica.getId(),
					TipoReferente.AT.getValue(),forMigrazione);
			final List<AltroTitolareDto> altriTitolari = new ArrayList<AltroTitolareDto>();
			fascicolo.getIstanza().setAltriTitolari(altriTitolari);
			if (altriTitolariEntity != null)
			{
				altriTitolariEntity.forEach(el -> {
					final AltroTitolareDto outObj = new AltroTitolareDto();
					outObj.fromEntity(el);
					// relativo documento di riconoscimento
					ReferentiDocumentoDTO doc = new ReferentiDocumentoDTO(el.getId());
					doc.setReferenteId(el.getId());
					doc = this.referentiDocDao.find(doc,forMigrazione);
					AllegatiDTO allegDoc=null;
					try {
						if(forMigrazione) {
							allegDoc = this.allegatoSvc.getDocumentoReferenteTxWrite(pratica.getId().toString(), el.getId(),batch);
						}else {
							allegDoc = this.allegatoSvc.getDocumentoReferente(pratica.getId().toString(), el.getId(),forMigrazione,batch);	
						}
						
					} catch (final CustomOperationToAdviceException e) {
					}
					doc.setDocAllegato(allegDoc);
					outObj.setDocumento(doc);// fromEntityDoc(doc);
					//outObj.setIndirizzoEmail("dummy@test.it"); //il campo non è gestito su altro titolare, ma per far passare la validazione ...
					altriTitolari.add(outObj);
				});
			}
			// carico gli id dei comuni di competenza
			fascicolo.setComuniCompetenza(this.comuniCompetenzaDao.selectByPratica(pratica.getId(),forMigrazione));
			// build localizzazione
			final LocalizazioneDto localizazione = new LocalizazioneDto();
			fascicolo.getIstanza().setLocalizzazione(localizazione);
			final List<LocalizzazioneInterventoDTO> listaComuni = this.localIntDao.select(pratica.getId(),forMigrazione);
			localizazione.setLocalizzazioneComuni(listaComuni);
			if (listaComuni != null && listaComuni.size() > 0)
			{
				for (final LocalizzazioneInterventoDTO comune : listaComuni)
				{
					// find particelle
					List<ParticelleCatastaliDTO> particelle = this.particelleDao.select(pratica.getId(),
							comune.getComuneId(),forMigrazione);
					if (particelle.size() == 0)
						particelle = null;
					if (particelle != null)
					{
						comune.setParticelle(particelle);
					}
					// find vincolistica
					comune.setUlterioriInformazioni(new UlterioriInformazioniDto());
					final UlterioriInformazioniDto ultInfo = comune.getUlterioriInformazioni();
					final List<ParchiPaesaggiImmobiliDTO> parchi = this.parchiPaesaggiImmobiliDao.select(pratica.getId(),
							comune.getComuneId(), ParchiPaesaggiImmobiliSigla.P.toString(),forMigrazione);
					ultInfo.setBpParchiEReserve(new ArrayList<String>());
					ultInfo.setBpParchiEReserveOptions(new ArrayList<SelectOptionDto>());
					this.toUlterioriInformazioniDto(parchi, ultInfo.getBpParchiEReserve(),
							ultInfo.getBpParchiEReserveOptions());
					final List<ParchiPaesaggiImmobiliDTO> immobili = this.parchiPaesaggiImmobiliDao.select(pratica.getId(),
							comune.getComuneId(), ParchiPaesaggiImmobiliSigla.I.toString(),forMigrazione);
					ultInfo.setBpImmobileAreePubblico(new ArrayList<String>());
					ultInfo.setBpImmobileAreePubblicoOptions(new ArrayList<SelectOptionDto>());
					this.toUlterioriInformazioniDto(immobili, ultInfo.getBpImmobileAreePubblico(),
							ultInfo.getBpImmobileAreePubblicoOptions());
					final List<ParchiPaesaggiImmobiliDTO> paesaggi = this.parchiPaesaggiImmobiliDao.select(pratica.getId(),
							comune.getComuneId(), ParchiPaesaggiImmobiliSigla.R.toString(),forMigrazione);
					ultInfo.setUcpPaesaggioRurale(new ArrayList<String>());
					ultInfo.setUcpPaesaggioRuraleOptions(new ArrayList<SelectOptionDto>());
					this.toUlterioriInformazioniDto(paesaggi, ultInfo.getUcpPaesaggioRurale(),
							ultInfo.getUcpPaesaggioRuraleOptions());
				}
			}
			if(!forMigrazione) {
				this.findAllegati(fascicolo, inIstruttoria,batch);
				//scheda tecnica
				fascicolo.setSchedaTecnica(this.schedaTecnicaService.find(fascicolo.getId()));
				//totali pagamenti online
				fascicolo.setTotPagamenti(this.totPagato(pratica));
				try {
				//calcolo dello stato attività
					final Gruppi gruppo = this.userUtil.getGruppo();
					fascicolo.setStatoAttivita(StatoAttivita.statoAttivita(fascicolo.getAttivitaDaEspletare(), fascicolo.getStatoSedutaCommissione(), fascicolo.getStatoRelazioneEnte(),fascicolo.getStatoParereSoprintendenza(), gruppo));
				}catch (final Exception e) {
					LOGGER.info("Impossibile calcolare lo stato attività",e);
				}
			}
		} catch (final EmptyResultDataAccessException e)
		{
			LOGGER.error("Non ho trovato il record del documento del richiedente.", e);
			throw new CustomOperationToAdviceException("Errore nel set dei dati dell'istanza");
		}
		return fascicolo;		
	}

	private void settaDatiOwner(final PraticaDTO pratica, final FascicoloDto fascicolo,boolean batch) {
		//Set del flag per capire subito se l'utente loggato è l'assegnatario/proprietario della pratica
		if(batch) {
			fascicolo.setCodiceSegreto(null);
		    fascicolo.setCurrentUserRichiedente(false);
		    fascicolo.setCurrentUserOwner(false);
			return;
		}
		String usernameLoggato=userUtil.getMyProfile().getUsername();
		fascicolo.setCurrentUserOwner(pratica.getOwner().equalsIgnoreCase(usernameLoggato));
		if(!referentiDao.isRichiedente(fascicolo.getId(), SecurityUtil.getUserDetail().getFiscalCode())) {
		    fascicolo.setCodiceSegreto(null);
		    fascicolo.setCurrentUserRichiedente(false);
		} else {
		    fascicolo.setCurrentUserRichiedente(true);
		}
	}
	
	
	private void popolaDelegati(final FascicoloDto fascicolo, final IstanzaDto istanza) throws Exception {
		List<PraticaDelegatoDTO> delegati = pdService.findDelegati(fascicolo.getId());
		if(ListUtil.isNotEmpty(delegati)) {
			for(PraticaDelegatoDTO delegato:delegati) {
				delegato.getId();
				AllegatoDelegatoDTO all = null;
				try {
					all = allDelDao.findAllegatoBySezione(delegato.getId(), delegato.getIndice(), SezioneAllegati.ALLEGATO_DELEGA);
				}catch(EmptyResultDataAccessException e) {
					LOGGER.error("File delega non trovato, l'elaborazione continua...");
				}
				if(all!=null && all.getIdAllegato()!=null) {
					AllegatiDTO allegatoDTO = allegatoDao.findById(all.getIdAllegato(),false);
					delegato.setAllegatoDelega(allegatoDTO);
				}
				all = null;
				try {
					all = allDelDao.findAllegatoBySezione(delegato.getId(), delegato.getIndice(), SezioneAllegati.ALLEGATO_SOLLEVAMENTO_INCARICO);
				}catch(EmptyResultDataAccessException e) {
					LOGGER.info("Nessun file di sollevamento trovato...");
				}
				if(all!=null && all.getIdAllegato()!=null) {
					AllegatiDTO allegatoDTO = allegatoDao.findById(all.getIdAllegato(),false);
					delegato.setAllegatoSollevamento(allegatoDTO);
				}
			}
		}
		istanza.setDelegatoPratica(delegati);
	}

	/**
	 * 
	 * @author acolaianni
	 *
	 * @param idFascicolo
	 * @param sezione
	 * @param batch
	 * @return
	 * @throws CustomOperationToAdviceException
	 */
	private List<AllegatiDTO> getAllegatiSezioneSportello(UUID idFascicolo, SezioneAllegati sezione,boolean batch) throws CustomOperationToAdviceException {
		if(batch) {
			return this.allegatoSvc.getAllegatiBatch(idFascicolo, sezione);			
		}
		else {
			return this.allegatoSvc.getAllegati(idFascicolo, sezione);	
		}
	}
	
	/**
	 * 
	 * @author acolaianni
	 *
	 * @param fascicolo
	 * @param inIstruttoria
	 * @param batch utilizzato solo se inIstruttoria a false
	 * @throws Exception
	 */
	private void findAllegati(final FascicoloDto fascicolo, final boolean inIstruttoria,boolean batch) throws Exception{
		final AllegatiDto allegati = new AllegatiDto();
		fascicolo.setAllegati(allegati);
		final DocumentazioneAmministrativaDto docAmm = new DocumentazioneAmministrativaDto();
		final DocumentazioneTecnicaDto docTecnica = new DocumentazioneTecnicaDto();
		allegati.setDocumentazioneAmministrativa(docAmm);
		allegati.setDocumentazioneTecnica(docTecnica);
		if(!inIstruttoria)
		{
			docAmm.setGrigliaPagamentoAllegati(this.getAllegatiSezioneSportello(fascicolo.getId(), SezioneAllegati.DOC_AMMINISTRATIVA_D,batch));
			docAmm.setGrigliaAllegatiCaricati(this.getAllegatiSezioneSportello(fascicolo.getId(), SezioneAllegati.DOC_AMMINISTRATIVA_E,batch));
			docTecnica.setGrigliaAllegatiCaricati(this.getAllegatiSezioneSportello(fascicolo.getId(), SezioneAllegati.DOC_TECNICA,batch));
			fascicolo.getIstanza().getLocalizzazione().setAllegaShapeFile(this.getAllegatiSezioneSportello(fascicolo.getId(), SezioneAllegati.LOCALIZZAZIONE,batch));
		}
		else
		{
			docAmm.setGrigliaPagamentoAllegati(this.allegatoSvc.getAllegatiIstruttoria(fascicolo.getId(), SezioneAllegati.DOC_AMMINISTRATIVA_D));
			docAmm.setGrigliaAllegatiCaricati(this.allegatoSvc.getAllegatiIstruttoria(fascicolo.getId(), SezioneAllegati.DOC_AMMINISTRATIVA_E));
			docTecnica.setGrigliaAllegatiCaricati(this.allegatoSvc.getAllegatiIstruttoria(fascicolo.getId(), SezioneAllegati.DOC_TECNICA));
			fascicolo.getIstanza().getLocalizzazione().setAllegaShapeFile(this.allegatoSvc.getAllegatiIstruttoria(fascicolo.getId(), SezioneAllegati.LOCALIZZAZIONE));
		}
	
	}
	
	/**
	 * precarico le selezioni possibili e le selezioni effettuate
	 * 
	 * @param entityOptions
	 * @param listaSelezioni
	 * @param listaOpzioni
	 */
	private void toUlterioriInformazioniDto(final List<ParchiPaesaggiImmobiliDTO> entityOptions, final List<String> listaSelezioni,
			final List<SelectOptionDto> listaOpzioni)
	{
		if (entityOptions != null && entityOptions.size() > 0)
		{
			for (final ParchiPaesaggiImmobiliDTO vincolo : entityOptions)
			{
				final SelectOptionDto opzione = new SelectOptionDto();
				opzione.setValue(vincolo.getCodice());
				opzione.setDescription(vincolo.getDescrizione());
				opzione.setLinked(vincolo.getInfo());
				listaOpzioni.add(opzione);
				if (vincolo.getSelezionato() != null && vincolo.getSelezionato().equalsIgnoreCase("S"))
				{
					listaSelezioni.add(vincolo.getCodice());
				}
				;
			}
		}
	}

	private void aggiornaDatiPratica(final FascicoloDto pk,final boolean withoutCheck) throws Exception
	{
		final PraticaDTO pratica = pk.getPraticaByFascicolo();
		final PraticaDTO praticaSaved = this.praticaDao.find(pratica);
		if(!withoutCheck) {
			this.checkDiMiaCompetenza(praticaSaved);
			this.checkStatoForUpdate(praticaSaved);	
		}
		if (!pratica.getEnteDelegato().equals(praticaSaved.getEnteDelegato()))
		{
			throw new CustomOperationToAdviceException(
					"Ente delegato difforme a quello presente nell'archivio, impossibile aggiornare");
		}
		if (pratica.getTipoProcedimento() != praticaSaved.getTipoProcedimento())
		{
			throw new CustomOperationToAdviceException(
					"Tipo procedimento difforme a quello presente nell'archivio, impossibile aggiornare");
		}
		pratica.setPrivacyId(praticaSaved.getPrivacyId());
		pratica.setDataModifica(new Date());
		this.praticaDao.update(pratica);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public FascicoloDto update(final FascicoloDto pk) throws Exception
	{
		return this.update(pk,false);
	}
	
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public FascicoloDto updateRichiedente(FascicoloDto pk) throws Exception
	{
	    if (pk.getId() == null)
		throw new CustomOperationToAdviceException("Impossibile aggiornare il richiedente: id fascicolo nullo");
	    if(praticaDao.getValidazioneRichiedente(pk.getId().toString()))
		throw new CustomOperationToAdviceException("Impossibile aggiornare il fascicolo: richiedente già validato");
	    final IstanzaDto istanza = pk.getIstanza();
	    final RichiedenteDto richiedente = istanza.getRichiedente();
	    if(richiedente == null)
		throw new CustomOperationToAdviceException("Impossibile aggiornare i dati del fascicolo");
	    final PraticaDTO pratica = pk.getPraticaByFascicolo();
		final PraticaDTO praticaSaved = this.praticaDao.find(pratica);
		this.checkDiMiaCompetenza(praticaSaved);
		this.checkStatoForUpdate(praticaSaved);	
		final ReferentiDTO richiedenteEntity = new ReferentiDTO();
	    richiedenteEntity.setPraticaId(pk.getId());
	    richiedente.toEntity(richiedenteEntity, TipoReferente.SD.getValue()); // Soggetto Dichiarante
	    if(StringUtil.isNotBlank(richiedenteEntity.getIdTipoAzienda())) {
			richiedenteEntity.setTipoAzienda(this.tipoAziendaRepository.getNome(richiedenteEntity.getIdTipoAzienda()));
	    }
	    else {
	    	richiedenteEntity.setCodiceIpa(null);
	    	richiedenteEntity.setDittaCf(null);
	    	richiedenteEntity.setDittaEnte(null);
	    	richiedenteEntity.setDittaPartitaIva(null);
	    	richiedenteEntity.setDittaQualitaDi(null);
	    	richiedenteEntity.setDitta(false);
	    	richiedenteEntity.setDittaCodiceUO(null);
	    }
	    if(praticaSaved.getRuoloPratica().equals(RuoloPraticaEnum.PROPONENTE.getCodice())) {
	    	ReferentiDTO richiedenteSaved = this.referentiDao.find(richiedenteEntity);
	    	richiedenteEntity.setCognome(richiedenteSaved.getCognome());
	    	richiedenteEntity.setNome(richiedenteSaved.getNome());
	    	richiedenteEntity.setCodiceFiscale(richiedenteSaved.getCodiceFiscale());
	    }
	    ReferentiDocumentoDTO documentoRiconoscimento = new ReferentiDocumentoDTO();
	    documentoRiconoscimento.setReferenteId(richiedenteEntity.getId());
	    documentoRiconoscimento = this.referentiDocDao.find(documentoRiconoscimento, false);
	    this.setDatiDocumento(richiedente, documentoRiconoscimento);
	    this.referentiDao.update(richiedenteEntity);
	    this.referentiDocDao.update(documentoRiconoscimento);
	    return pk;
	}
	
	
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public FascicoloDto updateWithoutCkeck(final FascicoloDto pk) throws Exception
	{
		return this.update(pk,true);
	}
	
	private FascicoloDto update(final FascicoloDto pk,final boolean withoutCheck) throws Exception
	{
		// prelevo i dati per scrivere il richiedente
		if (pk.getId() == null)
			throw new CustomOperationToAdviceException("Impossibile aggiornare il fascicolo");
		this.aggiornaDatiPratica(pk,withoutCheck);
		final IstanzaDto istanza = pk.getIstanza();
		if (istanza != null)
		{
	    	final ReferentiDTO richiedenteEntity = referentiDao.getRichiedente(pk.getId().toString());
	    	final DichiarazioniDto dichDto = istanza.getDichiarazioni();
			// titolarita
			if (dichDto != null)
			{
				richiedenteEntity.setTitolaritaId(dichDto.getTitolarita());
				richiedenteEntity.setTitolaritaEsclusiva(dichDto.getTitolaritaEsclusivaIntervento());
				if (dichDto.getTitolarita() != null && dichDto.getTitolarita() == RuoloReferente.Altro.getValue())
				{
					richiedenteEntity.setSpecificaTitolarita(dichDto.getDiAvereTitoloAltroSpec());
				} else if (dichDto.getTitolarita() != null
						&& dichDto.getTitolarita() == RuoloReferente.RappLegale.getValue())
				{
					richiedenteEntity.setSpecificaTitolarita(dichDto.getDiAvereTitoloRappSpec());
				} else
				{
					richiedenteEntity.setSpecificaTitolarita(null);
				}
				// altre_dichiarazioni_rich
				final AltreDichiarazioniRichDTO altreDichEntity = new AltreDichiarazioniRichDTO();
				altreDichEntity.setPraticaId(pk.getId());
				altreDichEntity.toEntity(istanza.getDichiarazioni());
				if (pk.getTipoProcedimento() == 2)
				{
					final int updated = this.altreDichDao.update(altreDichEntity);
					if (updated == 0)
						this.altreDichDao.insert(altreDichEntity);
					final PptrSelezioniSearch pptrss = new PptrSelezioniSearch();
					pptrss.setSezione("art_142");
					pptrss.setIdPratica(pk.getId());
					pptrss.setLimit(1000);
					pptrss.setPage(1);
					final List<PptrSelezioniDTO> selezioni = this.pptrRepo.search(pptrss).getList();
					// faccio un ciclo per aggiungere gli elementi mancanti
					if (istanza.getDichiarazioni().getArt142() != null)
					{
						for (final String art142 : istanza.getDichiarazioni().getArt142())
						{
							if (!art142.contentEquals("142") && (selezioni == null
									|| !selezioni.stream().anyMatch(p -> p.getCodice().equals(art142))))
							{
								final PptrSelezioniDTO nuova = new PptrSelezioniDTO();
								nuova.setSezione("art_142");
								nuova.setCodice(art142);
								nuova.setIdPratica(pk.getId());
								this.pptrRepo.insert(nuova);
							}
						}
					}
					// faccio un secondo ciclo per rimuovere gli elementi eventualmente rimossi in
					// dichiarazioni
					if (selezioni != null)
					{
						for (final PptrSelezioniDTO pptr : selezioni)
						{
							if (istanza.getDichiarazioni().getArt142() != null
									&& !istanza.getDichiarazioni().getArt142().contains(pptr.getCodice()))
								this.pptrRepo.delete(pptr);
						}
					}
				} else
				{// rimuovo eventuali record
					this.altreDichDao.delete(altreDichEntity);
				}
				
				// disclaimer_pratica
				final List<DisclaimerPraticaDTO> disclSaved = this.disclPraticaDao.selectByPratica(pk.getId(), null);
				disclSaved.forEach(el -> {
					if (istanza.getDichiarazioni().getAltreOpzioni() != null
							&& istanza.getDichiarazioni().getAltreOpzioni().contains(el.getDisclaimerId()))
					{
						el.setFlag("S");
						this.disclPraticaDao.update(el);
					} else
					{
						el.setFlag("");
						this.disclPraticaDao.update(el);
					}
				});
			}
			//aggiorno solo le dichiarazioni di titolarità
			this.referentiDao.updateDichiarazioniTitolarita(richiedenteEntity);
			// altri titolari
			this.updateAltriTitolari(pk, istanza,withoutCheck);
			//}
			final TecnicoIncaricatoDto tecnico = istanza.getTecnicoIncaricato();
			if (tecnico != null)
			{
				ReferentiDTO tecnicoEntity = new ReferentiDTO();
				tecnicoEntity.setPraticaId(pk.getId());
				tecnicoEntity.setId(tecnico.getId());
				tecnicoEntity = this.referentiDao.find(tecnicoEntity,withoutCheck);
				tecnico.toEntity(tecnicoEntity, TipoReferente.TR.getValue()); // Tecnico Redattore
				ReferentiDocumentoDTO documentoRiconoscimento = new ReferentiDocumentoDTO();
				documentoRiconoscimento.setReferenteId(tecnicoEntity.getId());
				documentoRiconoscimento = this.referentiDocDao.find(documentoRiconoscimento,withoutCheck);
				this.setDatiDocumento(tecnico, documentoRiconoscimento);
				this.referentiDao.update(tecnicoEntity);
				this.referentiDocDao.update(documentoRiconoscimento);
			}
			// localizzazione
			if(istanza.getLocalizzazione() != null)
			    this.updateLocalizzazione(pk, istanza);
		}
		return pk;
	}

	private void updateLocalizzazione(final FascicoloDto pk, final IstanzaDto istanza) throws Exception
	{
		final List<LocalizzazioneInterventoDTO> localComuniOld = this.localIntDao.select(pk.getId());
		final List<LocalizzazioneInterventoDTO> localComuni = istanza.getLocalizzazione().getLocalizzazioneComuni();
		// rimuovo quelle che dal FE non mi sono arrivate più
		if (localComuniOld != null)
		{
			for (final LocalizzazioneInterventoDTO locComuneOld : localComuniOld)
			{
				boolean esiste = false;
				// cerco nel new...
				if (localComuni != null && localComuni.size() > 0)
				{
					for (final LocalizzazioneInterventoDTO locNew : localComuni)
					{
						if (locNew.getComuneId() == locComuneOld.getComuneId())
						{
							esiste = true;
						}
					}
				}
				if (!esiste)
				{
					this.localIntDao.delete(locComuneOld);
				}
			}
		}
		// cancello eventualmente i record di "Destinazione Urbanistica" associati ai
		// comuni eliminati
		if (istanza.getLocalizzazione().getLocalizzazioneComuni() == null
				|| istanza.getLocalizzazione().getLocalizzazioneComuni().isEmpty())
		{
			this.destinazioneUrbanisticaService.delete(pk.getId());
		} else
		{
			final List<DestinazioneUrbanisticaDto> listaDestinazioni = this.destinazioneUrbanisticaService
					.findByIdFascicolo(pk.getId());
			if (listaDestinazioni != null && !listaDestinazioni.isEmpty())
			{
				for (final DestinazioneUrbanisticaDto destinazione : listaDestinazioni)
				{
					boolean esisteAncora = false;
					for (final LocalizzazioneInterventoDTO localizzazione : istanza.getLocalizzazione()
							.getLocalizzazioneComuni())
					{
						if (destinazione.getComuneId() == localizzazione.getComuneId())
							esisteAncora = true;
					}
					if (esisteAncora == false)
						this.destinazioneUrbanisticaService.deleteForComune(pk.getId(), destinazione.getComuneId());
				}
			}
		}

		if (localComuni != null && localComuni.size() > 0)
		{
			for (final LocalizzazioneInterventoDTO locComune : localComuni)
			{
				// vincolistica parchi paesaggi immobili
				final UlterioriInformazioniDto ulterioriInfo = locComune.getUlterioriInformazioni();
				locComune.setPraticaId(pk.getId());
				final int affected = this.localIntDao.update(locComune);
				if (affected == 0)
				{
					this.localIntDao.insert(locComune);
					// inserisco le selezioni possibili in parchi_paesaggi_immobili
					// solo all'inserimento della localizzazioneIntervento
					this.creaOptions(pk, locComune, ulterioriInfo.getBpParchiEReserveOptions(),
							ParchiPaesaggiImmobiliSigla.P.toString());
					this.creaOptions(pk, locComune, ulterioriInfo.getBpImmobileAreePubblicoOptions(),
							ParchiPaesaggiImmobiliSigla.I.toString());
					this.creaOptions(pk, locComune, ulterioriInfo.getUcpPaesaggioRuraleOptions(),
							ParchiPaesaggiImmobiliSigla.R.toString());
				}
				// update selezioni vincolistica
				this.parchiPaesaggiImmobiliDao.setSelezioni(pk.getId(), locComune.getComuneId(),
						ParchiPaesaggiImmobiliSigla.P.toString(), ulterioriInfo.getBpParchiEReserve());
				this.parchiPaesaggiImmobiliDao.setSelezioni(pk.getId(), locComune.getComuneId(),
						ParchiPaesaggiImmobiliSigla.I.toString(), ulterioriInfo.getBpImmobileAreePubblico());
				this.parchiPaesaggiImmobiliDao.setSelezioni(pk.getId(), locComune.getComuneId(),
						ParchiPaesaggiImmobiliSigla.R.toString(), ulterioriInfo.getUcpPaesaggioRurale());
				// particelle
				this.particelleDao.deleteByKeyLoc(pk.getId(), locComune.getComuneId());
				final List<ParticelleCatastaliDTO> particelle = locComune.getParticelle();
				if (particelle != null && particelle.size() > 0)
				{
					int index = 1;
					for (final ParticelleCatastaliDTO p : particelle)
					{
						p.setPraticaId(pk.getId());
						p.setComuneId(locComune.getComuneId());
						p.setId(index++);
						this.particelleDao.insert(p);
					}
				}
			}
		}
	}

	private void creaOptions(final FascicoloDto pk, final LocalizzazioneInterventoDTO locComune, final List<SelectOptionDto> lista,
			final String tipoVincolo)
	{
		if (lista != null)
		{
			lista.forEach(opzione -> {
				final ParchiPaesaggiImmobiliDTO ppi = new ParchiPaesaggiImmobiliDTO();
				ppi.setPraticaId(pk.getId());
				ppi.setComuneId(locComune.getComuneId());
				ppi.setTipoVincolo(tipoVincolo);
				ppi.setCodice(opzione.getValue());
				ppi.setDescrizione(opzione.getDescription());
				ppi.setSelezionato(null);
				ppi.setInfo(opzione.getLinked());
				ppi.setDataInserimento(new Date());
				this.parchiPaesaggiImmobiliDao.insert(ppi);
			});
		}
	}

	/**
	 * la sincronizzazione tra creazione e cancellazione è fatta già in modo
	 * sincrono rispetto a fe
	 * 
	 * @param pk
	 * @param istanza
	 */
	private void updateAltriTitolari(final FascicoloDto pk, final IstanzaDto istanza,final boolean withoutCheck)
	{
		final List<AltroTitolareDto> altriTitolari = istanza.getAltriTitolari();
//		// carico i preesistenti
//		List<ReferentiDTO> altriTitolariEntity = referentiDao.selectAltriTitolari(pk.getId(),
//				TipoReferente.AT.getValue());
//		List<ReferentiDTO> altriTitolariToRemove = new ArrayList<>();
		if (altriTitolari != null && altriTitolari.size() > 0)
		{
			altriTitolari.forEach(altroTitolare -> {
				// mi segno quelli aggiornati...
//				altriTitolariEntity.forEach(el -> {
//					if (altroTitolare.getId() != null && el.getId() == altroTitolare.getId())
//						altriTitolariToRemove.add(el);
//				});
				ReferentiDTO referente = new ReferentiDTO();
				referente.setPraticaId(pk.getId());
				referente.setId(altroTitolare.getId());
				referente = this.referentiDao.find(referente,withoutCheck);
				altroTitolare.toEntity(referente, TipoReferente.AT.getValue());
				if(referente.getDitta() == null || referente.getDitta() == false) {
					referente.setCodiceIpa(null);
					referente.setDittaCf(null);
					referente.setDittaEnte(null);
					referente.setDittaPartitaIva(null);
					referente.setDittaQualitaDi(null);
					referente.setDitta(false);
					referente.setDittaCodiceUO(null);
				}
				final int updated = this.referentiDao.update(referente);
				if (updated == 0)
				{
					referente.setId(this.referentiDao.insert(referente));
					altroTitolare.setId(referente.getId()); // risetto l'id verso il Dto
				}
				ReferentiDocumentoDTO entityDoc = new ReferentiDocumentoDTO();
				entityDoc.setReferenteId(referente.getId());
				entityDoc = this.referentiDocDao.find(entityDoc,withoutCheck);
				// altroTitolare.toEntityDocData(entityDoc);
				this.setDatiDocumento(altroTitolare, entityDoc);
				this.referentiDocDao.update(entityDoc);
			});
		}
//		else {
//			referentiDao.deleteByPraticaEtipo(pk.getId(), TipoReferente.AT.getValue());
//		}
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public AltroTitolareDto creaTitolare(final FascicoloDto pk) throws CustomOperationToAdviceException
	{
		final ReferentiDTO entity = new ReferentiDTO();
		entity.setPraticaId(pk.getId());
		final PraticaDTO pratica = this.praticaDao.find(pk.getId());
		this.checkDiMiaCompetenza(pratica);
		this.checkStatoForUpdate(pratica);
		final AltroTitolareDto toCreate = new AltroTitolareDto();
		toCreate.toEntity(entity, TipoReferente.AT.getValue());
		entity.setPraticaId(pk.getId());
		final Long id = this.referentiDao.insert(entity);
		entity.setId(id);
		toCreate.setId(id);
		final ReferentiDocumentoDTO entityDoc = new ReferentiDocumentoDTO();
		entityDoc.setReferenteId(id);
		// toCreate.toEntityDocData(entityDoc);
		this.setDatiDocumento(toCreate, entityDoc);
		this.referentiDocDao.insert(entityDoc);
		//metto un allegato vuoto
		toCreate.setDocumento(entityDoc);
		entityDoc.setDocAllegato(new AllegatiDTO());
		return toCreate;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public int eliminaTitolare(final Long pk) throws CustomOperationToAdviceException, CustomCmisException
	{
		ReferentiDTO entity = new ReferentiDTO();
		entity.setId(pk);
		entity = this.referentiDao.find(entity);
		final PraticaDTO pratica = this.praticaDao.find(entity.getPraticaId());
		this.checkDiMiaCompetenza(pratica);
		this.checkStatoForUpdate(pratica);
		ReferentiDocumentoDTO doc = new ReferentiDocumentoDTO();
		doc.setReferenteId(entity.getId());
		doc = this.referentiDocDao.find(doc);
		if (doc.getIdAllegato() != null)
		{
			// elimino l'allegato dal cms
			this.allegatoSvc.deleteDocumentoReferente(entity.getPraticaId().toString(), entity.getId());
		}
		return this.referentiDao.delete(entity);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public int delete(final FascicoloDto pk) throws Exception
	{
		int ret = 0;
		final PraticaDTO pratica = this.praticaDao.findByCodice(pk.getCodicePraticaAppptr());
		this.checkDiMiaCompetenza(pratica);
		this.checkStatoForUpdate(pratica);
		//verifico che non ci sia un pagamento effettuato
		this.checkPagamenti(pratica.getId().toString());
		// dovrei cancellare i contenuti sul cms
		this.allegatoSvc.deleteAllAllegatiPratica(pratica.getId());
		pdDao.deleteByPratica(pratica.getId());
		ret = this.praticaDao.delete(pratica);
		return ret;
	}

	private void checkPagamenti(final String idPratica) throws Exception, CustomOperationToAdviceException {
		final PagamentoDto pag = this.pagSvc.getPagamento(idPratica);
		if(pag!=null && pag.isPagato()) {
			throw new CustomOperationToAdviceException("Impossibile procedere, esiste un pagamento effettuato sulla pratica!!");
		}
		if(pag!=null && !pag.isPagato()) {
			throw new CustomOperationToAdviceException("Impossibile procedere, esiste un pagamento pendente sulla pratica annullarlo prima di procedere!!");
		}
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public Integer updateValidation(final UUID id, final Boolean istanza, final Boolean schedaTecnica, final Boolean allegati) throws CustomOperationToAdviceException, Exception
	{
		final PraticaDTO pratica = this.praticaDao.find(id);
		this.checkDiMiaCompetenza(pratica);
		this.checkStatoForUpdate(pratica);
		return this.praticaDao.updateValidation(id, istanza, schedaTecnica, allegati);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_READ)
	public Boolean validaIstanza(final String codicePratica) throws Exception
	{
		try {
			FascicoloDto fascicolo = new FascicoloDto();
			fascicolo.setCodicePraticaAppptr(codicePratica);
			fascicolo = this.find(fascicolo);
			
			
			this.validaAziendaEnte(fascicolo.getId().toString(), fascicolo.getIstanza().getRichiedente());
			
			if(ListUtil.isNotEmpty(fascicolo.getIstanza().getAltriTitolari())) {
				for(final Iterator<AltroTitolareDto> iterator = fascicolo.getIstanza().getAltriTitolari().iterator(); iterator.hasNext();) {
					this.validaAziendaEnte(fascicolo.getId().toString(), iterator.next());
				}
			}
			
			this.doValidate(fascicolo.getIstanza(),"Istanza");
			this.doValidate(fascicolo.getIstanza().getRichiedente(),"Richiedente");
			this.estendiValidazioneIstanzaRichiedente(fascicolo.getIstanza().getRichiedente());																			 
			this.validaDichiarazione(fascicolo.getId(),
								fascicolo.getIstanza().getDichiarazioni(), 
								fascicolo.getIstanza().getLocalizzazione().getLocalizzazioneComuni(), 
								fascicolo.getTipoProcedimento(),
								fascicolo.getIstanza().getAltriTitolari());
			this.doValidate(fascicolo.getIstanza().getAltriTitolari(),"Altro Titolare");
			this.estendiValidazioneIstanzaAltriTitolari(fascicolo.getIstanza().getAltriTitolari());																				 
			this.doValidate(fascicolo.getIstanza().getTecnicoIncaricato(),"Tecnico Incaricato");
			this.estendiValidazioneIstanzaTecnicoIncaricato(fascicolo.getIstanza().getTecnicoIncaricato());																						 
			this.doValidate(fascicolo.getIstanza().getLocalizzazione().getLocalizzazioneComuni(),"Localizzazione");
		} catch (final Exception e) {
			throw e;
		}
		return true;
	}
	
	
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public void validaRichiedente(String codicePratica) throws Exception
	{
	    FascicoloDto fascicolo = new FascicoloDto();
	    fascicolo.setCodicePraticaAppptr(codicePratica);
	    fascicolo = find(fascicolo);
	    doValidate(fascicolo.getIstanza().getRichiedente(),"Richiedente");
	    this.validaAziendaEnte(fascicolo.getId().toString(), fascicolo.getIstanza().getRichiedente());
	    estendiValidazioneIstanzaRichiedente(fascicolo.getIstanza().getRichiedente());
	    String codiceSegreto = praticaDao.updateCodiceSegreto(codicePratica);
	    praticaDao.validazioneRichiedente(codicePratica);
	    comunicazioniService.sendComunicazioneRichiedente(fascicolo.getId(), codiceSegreto);
	}

	private void validaAziendaEnte(final String idPratica, final RichiedenteDto dto) throws Exception{
		if(StringUtil.isNotBlank(dto.getSocietaCodiceFiscale())){
			if(StringUtil.isEmpty(dto.getIdTipoAzienda())) {
				throw new CustomOperationToAdviceException("Errore nella validazione del tipo ditta/società/ente del referente "+dto.getNome()+" "+dto.getCognome());
			}
			final boolean tipoAziendaPrivato = this.tipoAziendaRepository.getPrivato(dto.getIdTipoAzienda());
			if(tipoAziendaPrivato == false) {
				this.validaEnte(idPratica
						       ,dto.getId()
						       ,dto.getSocietaCodiceFiscale()
						       );
			}
			if(tipoAziendaPrivato) {
				final RegistroImpreseBean impresa = this.validaAzienda(idPratica
						                                              ,dto.getId()
						                                              ,dto.getSocietaCodiceFiscale()
						                                              );
				if(ListUtil.isNotEmpty(impresa.getCodiciFiscaliPersone())
				&& impresa.getCodiciFiscaliPersone().contains(dto.getCodiceFiscale()) == false
				) {
					throw new CustomOperationToAdviceException(dto.getCodiceFiscale() + " non presente su visura camerale per impresa selezionata");
				}
			}
		}
	}
	
	private boolean estendiValidazioneIstanzaRichiedente(final RichiedenteDto richiedente) throws Exception
	{
		if ("ITALIA".equalsIgnoreCase(richiedente.getNatoStato().getDescription())) {
			if (richiedente.getNatoProvincia()==null || richiedente.getNatoProvincia().getValue()==null || richiedente.getNatoProvincia().getValue()<1)
				throw new CustomOperationToAdviceException("Errore nel tab Richiedente: inserire la Provincia di nascita!");
			if (richiedente.getNatoComune()==null || richiedente.getNatoComune().getValue()==null || richiedente.getNatoComune().getValue()<1)
				throw new CustomOperationToAdviceException("Errore nel tab Richiedente: inserire il Comune di nascita!");
		}
		else if (StringUtil.isEmpty(richiedente.getNatoCitta())) {
				throw new CustomOperationToAdviceException("Errore nel tab Richiedente: inserire la Città di nascita!");
		}
		
		if (richiedente.getResidenteIn()==null || richiedente.getResidenteIn().getStato()==null)
			throw new CustomOperationToAdviceException("Errore nel tab Richiedente: inserire le informazioni di residenza!");
			
		if ("ITALIA".equalsIgnoreCase(richiedente.getResidenteIn().getStato().getDescription())) {
			if (richiedente.getResidenteIn().getProvincia()==null || richiedente.getResidenteIn().getProvincia().getValue()==null || richiedente.getResidenteIn().getProvincia().getValue()<1)
				throw new CustomOperationToAdviceException("Errore nel tab Richiedente: inserire la Provincia di residenza!");
			if (richiedente.getResidenteIn().getComune()==null || richiedente.getResidenteIn().getComune().getValue()==null || richiedente.getResidenteIn().getComune().getValue()<1)
				throw new CustomOperationToAdviceException("Errore nel tab Richiedente: inserire il Comune di residenza!");
			if (StringUtil.isEmpty(richiedente.getResidenteIn().getCap()) || CapValidator.validate(richiedente.getResidenteIn().getCap())!=null)
				throw new CustomOperationToAdviceException("Errore nel tab Richiedente: inserire un CAP di residenza valido!");
			else 
				richiedente.getResidenteIn().setCap(CapValidator.normalize(richiedente.getResidenteIn().getCap()));
		}
		else if (StringUtil.isEmpty(richiedente.getResidenteIn().getCitta())) {
				throw new CustomOperationToAdviceException("Errore nel tab Richiedente: inserire la Città di residenza!");
		}
		
		if (!this.codiceFiscaleValido(richiedente))
		//if (CodiceFiscaleValidator.validate(richiedente.getCodiceFiscale())!=null)
			throw new CustomOperationToAdviceException("Errore nel tab Richiedente: inserire un codice Fiscale valido!");
		else 
			richiedente.setCodiceFiscale(CodiceFiscaleValidator.normalize(richiedente.getCodiceFiscale()));
			
		if (TelefonoValidator.validate(richiedente.getRecapitoTelefonico())!=null)
			throw new CustomOperationToAdviceException("Errore nel tab Richiedente: inserire un recapito telefonico valido!");
		else
			richiedente.setRecapitoTelefonico(TelefonoValidator.normalize(richiedente.getRecapitoTelefonico()));
		
		if (!StringUtil.isEmpty(richiedente.getSocietaCodiceFiscale()) && CodiceFiscaleValidator.validate(richiedente.getSocietaCodiceFiscale())!=null)
			throw new CustomOperationToAdviceException("Errore nel tab Richiedente: inserire un Codice Fiscale valido per la ditta/società!");
		else 
			richiedente.setSocietaCodiceFiscale(CodiceFiscaleValidator.normalize(richiedente.getSocietaCodiceFiscale()));
		
		if (!StringUtil.isEmpty(richiedente.getPartitaIva()) && PartitaIvaValidator.validate(richiedente.getPartitaIva())!=null)
			throw new CustomOperationToAdviceException("Errore nel tab Richiedente: inserire una partita IVA valida per la ditta/società!");
		else 
			richiedente.setPartitaIva(PartitaIvaValidator.normalize(richiedente.getPartitaIva()));
		
		if (!StringUtil.isEmail(richiedente.getPec()))
			throw new CustomOperationToAdviceException("Errore nel tab Richiedente: inserire una PEC valida!");
		
		if (!StringUtil.isEmail(richiedente.getIndirizzoEmail()))
			throw new CustomOperationToAdviceException("Errore nel tab Richiedente: inserire un'email valida!");
		
		ReferentiDocumentoDTO refDoc = new ReferentiDocumentoDTO();
		refDoc.setReferenteId(richiedente.getId());
		try {
			refDoc = this.referentiDocDao.find(refDoc);
			if (refDoc==null || refDoc.getIdAllegato()==null) 
				throw new Exception("Errore");
		} catch (final Exception e) {
			throw new CustomOperationToAdviceException("Errore nel tab Richiedente: caricare il documento di riconoscimento!");
		}
		
		return true;
	}
	
	private boolean estendiValidazioneIstanzaAltriTitolari(final List<AltroTitolareDto> altriTitolari) throws Exception
	{
		if (altriTitolari!=null && !altriTitolari.isEmpty()) {
			for (final AltroTitolareDto titolare : altriTitolari) {
				if ("ITALIA".equalsIgnoreCase(titolare.getNatoStato().getDescription())) {
					if (titolare.getNatoProvincia()==null || titolare.getNatoProvincia().getValue()==null || titolare.getNatoProvincia().getValue()<1)
						throw new CustomOperationToAdviceException("Errore nel tab Altri Titolari: inserire la Provincia di nascita!");
					if (titolare.getNatoComune()==null || titolare.getNatoComune().getValue()==null || titolare.getNatoComune().getValue()<1)
						throw new CustomOperationToAdviceException("Errore nel tab Altri Titolari: inserire il Comune di nascita!");
				}
				else if (StringUtil.isEmpty(titolare.getNatoCitta())) {
					throw new CustomOperationToAdviceException("Errore nel tab Altri Titolari: inserire la Città di nascita!");
				}

				if (titolare.getResidenteIn()==null || titolare.getResidenteIn().getStato()==null)
					throw new CustomOperationToAdviceException("Errore nel tab Altri Titolari: inserire le informazioni di residenza!");
				if (StringUtils.isEmpty(titolare.getResidenteIn().getVia()))
					throw new CustomOperationToAdviceException("Errore nel tab Altri Titolari: inserire la via di residenza!");
				if (StringUtils.isEmpty(titolare.getResidenteIn().getN()))
					throw new CustomOperationToAdviceException("Errore nel tab Altri Titolari: inserire il numero civico di residenza!");

				if ("ITALIA".equalsIgnoreCase(titolare.getResidenteIn().getStato().getDescription())) {
					if (titolare.getResidenteIn().getProvincia()==null || titolare.getResidenteIn().getProvincia().getValue()==null || titolare.getResidenteIn().getProvincia().getValue()<1)
						throw new CustomOperationToAdviceException("Errore nel tab Altri Titolari: inserire la Provincia di residenza!");
					if (titolare.getResidenteIn().getComune()==null || titolare.getResidenteIn().getComune().getValue()==null || titolare.getResidenteIn().getComune().getValue()<1)
						throw new CustomOperationToAdviceException("Errore nel tab Altri Titolari: inserire il Comune di residenza!");
					if (StringUtil.isEmpty(titolare.getResidenteIn().getCap()) || CapValidator.validate(titolare.getResidenteIn().getCap())!=null)
						throw new CustomOperationToAdviceException("Errore nel tab Altri Titolari: inserire un CAP di residenza valido!");
					else 
						titolare.getResidenteIn().setCap(CapValidator.normalize(titolare.getResidenteIn().getCap()));
				}
				else if (StringUtil.isEmpty(titolare.getResidenteIn().getCitta())) {
					throw new CustomOperationToAdviceException("Errore nel tab Altri Titolari: inserire la Città di residenza!");
				}

				if (!this.codiceFiscaleValido(titolare))
				//if (CodiceFiscaleValidator.validate(titolare.getCodiceFiscale())!=null)
					throw new CustomOperationToAdviceException("Errore nel tab Altri Titolari: inserire un codice Fiscale valido!");
				else 
					titolare.setCodiceFiscale(CodiceFiscaleValidator.normalize(titolare.getCodiceFiscale()));

				if (TelefonoValidator.validate(titolare.getRecapitoTelefonico())!=null)
					throw new CustomOperationToAdviceException("Errore nel tab Altri Titolari: inserire un recapito telefonico valido!");
				else
					titolare.setRecapitoTelefonico(TelefonoValidator.normalize(titolare.getRecapitoTelefonico()));

				if (!StringUtil.isEmpty(titolare.getSocietaCodiceFiscale()) && CodiceFiscaleValidator.validate(titolare.getSocietaCodiceFiscale())!=null)
					throw new CustomOperationToAdviceException("Errore nel tab Altri Titolari: inserire un Codice Fiscale valido per la ditta/società!");
				else 
					titolare.setSocietaCodiceFiscale(CodiceFiscaleValidator.normalize(titolare.getSocietaCodiceFiscale()));

				if (!StringUtil.isEmpty(titolare.getPartitaIva()) && PartitaIvaValidator.validate(titolare.getPartitaIva())!=null)
					throw new CustomOperationToAdviceException("Errore nel tab Altri Titolari: inserire una partita IVA valida per la ditta/società!");
				else 
					titolare.setPartitaIva(PartitaIvaValidator.normalize(titolare.getPartitaIva()));

				if (!StringUtil.isEmail(titolare.getPec()))
					throw new CustomOperationToAdviceException("Errore nel tab Altri Titolari: inserire una PEC valida!");

//				if (!StringUtil.isEmail(titolare.getIndirizzoEmail()))
//					throw new CustomOperationToAdviceException("Errore nel tab Altri Titolari: inserire un'email valida!");
				
				if (titolare.getInQualitaDi()!=null) { // è da qua che capisco se ha selezionato la checkbox sulla dittà\società?
					if (titolare.getInQualitaDi()<=0)
						throw new CustomOperationToAdviceException("Errore nel tab Altri Titolari: valore 'in qualità di' non valido!");
					if (titolare.getInQualitaDi()==9 && StringUtil.isEmpty(titolare.getDescAltroDitta()))
						throw new CustomOperationToAdviceException("Errore nel tab Altri Titolari: specificare il ruolo ricoperto nella ditta/società!");
					if (StringUtil.isEmpty(titolare.getSocieta()))
						throw new CustomOperationToAdviceException("Errore nel tab Altri Titolari: specificare il nome della ditta/società!");
					if (StringUtil.isEmpty(titolare.getSocietaCodiceFiscale()) || CodiceFiscaleValidator.validate(titolare.getSocietaCodiceFiscale())!=null)
						throw new CustomOperationToAdviceException("Errore nel tab Altri Titolari: specificare il Codice Fiscale della ditta/società!");
					else 
						titolare.setSocietaCodiceFiscale(CodiceFiscaleValidator.normalize(titolare.getSocietaCodiceFiscale()));
					
					if (!StringUtil.isEmpty(titolare.getPartitaIva()) && PartitaIvaValidator.validate(titolare.getPartitaIva())!=null)
						throw new CustomOperationToAdviceException("Errore nel tab Altri Titolari: specificare la partita IVA della ditta/società!");
					else 
						titolare.setPartitaIva(PartitaIvaValidator.normalize(titolare.getPartitaIva()));
				}
				
				ReferentiDocumentoDTO refDoc = new ReferentiDocumentoDTO();
				refDoc.setReferenteId(titolare.getId());
				try {
					refDoc = this.referentiDocDao.find(refDoc);
					if (refDoc==null || refDoc.getIdAllegato()==null) 
						throw new Exception("Errore");
				} catch (final Exception e) {
					throw new CustomOperationToAdviceException("Errore nel tab Altri Titolari: caricare il documento di riconoscimento!");
				}
			}
		}
		return true;
	}

	private boolean estendiValidazioneIstanzaTecnicoIncaricato(final TecnicoIncaricatoDto tecnico) throws Exception
	{
		if ("ITALIA".equalsIgnoreCase(tecnico.getNatoStato().getDescription())) {
			if (tecnico.getNatoProvincia()==null || tecnico.getNatoProvincia().getValue()==null || tecnico.getNatoProvincia().getValue()<1)
				throw new CustomOperationToAdviceException("Errore nel tab Tecnico incaricato: inserire la Provincia di nascita!");
			if (tecnico.getNatoComune()==null || tecnico.getNatoComune().getValue()==null || tecnico.getNatoComune().getValue()<1)
				throw new CustomOperationToAdviceException("Errore nel tab Tecnico incaricato: inserire il Comune di nascita!");
		}
		else if (StringUtil.isEmpty(tecnico.getNatoCitta())) {
				throw new CustomOperationToAdviceException("Errore nel tab Tecnico incaricato: inserire la Città di nascita!");
		}
		
		//if (CodiceFiscaleValidator.validate(tecnico.getCodiceFiscale())!=null)
		if (!this.codiceFiscaleValido(tecnico))
			throw new CustomOperationToAdviceException("Errore nel tab Tecnico incaricato: inserire un Codice Fiscale valido!");
		else 
			tecnico.setCodiceFiscale(CodiceFiscaleValidator.normalize(tecnico.getCodiceFiscale()));
			
		if (tecnico.getResidenteIn()!=null && tecnico.getResidenteIn().getStato()!=null && "ITALIA".equalsIgnoreCase(tecnico.getResidenteIn().getStato().getDescription()) &&
			tecnico.getResidenteIn().getCap()!=null && !tecnico.getResidenteIn().getCap().trim().equals("")) {
				if (CapValidator.validate(tecnico.getResidenteIn().getCap())!=null)
					throw new CustomOperationToAdviceException("Errore nel tab Tecnico incaricato: inserire un CAP di residenza valido!");
				else 
					tecnico.getResidenteIn().setCap(CapValidator.normalize(tecnico.getResidenteIn().getCap()));
		}
		
		if (tecnico.getConStudioIn()!=null && tecnico.getConStudioIn().getStato()!=null && "ITALIA".equalsIgnoreCase(tecnico.getConStudioIn().getStato().getDescription()) &&
			tecnico.getConStudioIn().getCap()!=null && !tecnico.getConStudioIn().getCap().trim().equals("")) {
				if (CapValidator.validate(tecnico.getConStudioIn().getCap())!=null)
					throw new CustomOperationToAdviceException("Errore nel tab Tecnico incaricato: inserire un CAP valido relativo allo studio!");
				else 
					tecnico.getConStudioIn().setCap(CapValidator.normalize(tecnico.getConStudioIn().getCap()));
		}
		
		if (!StringUtil.isEmpty(tecnico.getRecapitoTelefonico())) {
			if (TelefonoValidator.validate(tecnico.getRecapitoTelefonico())!=null)
				throw new CustomOperationToAdviceException("Errore nel tab Tecnico incaricato: inserire un recapito telefonico valido!");
			else
				tecnico.setRecapitoTelefonico(TelefonoValidator.normalize(tecnico.getRecapitoTelefonico()));
		}
		
		if (StringUtil.isEmpty(tecnico.getPec()) || !StringUtil.isEmail(tecnico.getPec()))
			throw new CustomOperationToAdviceException("Errore nel tab Tecnico incaricato: inserire una PEC valida!");
		
		ReferentiDocumentoDTO refDoc = new ReferentiDocumentoDTO();
		refDoc.setReferenteId(tecnico.getId());
		try {
			refDoc = this.referentiDocDao.find(refDoc);
			if (refDoc==null || refDoc.getIdAllegato()==null) 
				throw new Exception("Errore");
		} catch (final Exception e) {
			throw new CustomOperationToAdviceException("Errore nel tab Tecnico incaricato: caricare il documento di riconoscimento!");
		}
  
		return true;
	}
 
	private boolean codiceFiscaleValido(final InformazionePersonaleDto soggetto) {
		//controllo formale...
		if(!FiscalCodeValidation.validate(soggetto.getCodiceFiscale())) return false;
		//per il tecnico incaricato non sono obbligatori gli altri campi 
		//per fare un controllo incrociato per la validazione del codice fiscale.
		if(soggetto!=null && 
		   (soggetto.getNatoStato()==null || 
			soggetto.getNatoComune()==null || 
			soggetto.getNatoIl()==null || 
			soggetto.getSesso()==null)) 
		{
			return true;
		}
		//se passa questo, allora faccio un controllo incrociato con gli altri dati
		final String codIstat=this.anagraficaRepository.getCodiceCatastale(soggetto.getNatoStato().getValue().intValue(), soggetto.getNatoComune().getValue().intValue());
		boolean ret=false;
		try {
			ret = FiscalCodeValidation.validateValue(soggetto.getCodiceFiscale(), soggetto.getNome(), soggetto.getCognome(), soggetto.getSesso(), soggetto.getNatoIl(),codIstat );
		} catch (final CustomValidationException e) {
			LOGGER.error("errore nella validazione del codice fiscale",e);
		}
		return ret;
	}

	private void validaDichiarazione(final UUID idPratica, final DichiarazioniDto dichiarazione, final List<LocalizzazioneInterventoDTO> localizzazioneComuni, final Integer tipoProcedimento, final List<AltroTitolareDto> altriTitolari) throws ValidationFailureException
	{
		this.doValidate(dichiarazione,"Dichiarazioni");
		final List<DisclaimerDTO> list = this.disclDao.selectByIdPratica(TipoReferente.SD.getValue(), idPratica);
		if(list == null || list.isEmpty() || dichiarazione.getAltreOpzioni() == null || 
		   !list.stream().allMatch(p1 -> dichiarazione.getAltreOpzioni().stream().anyMatch(p2 -> p1.getId() == p2)))
		{
			LOGGER.error("Validazione dichiarazione fallita, Non tutti i disclaimer risultano essere stati accettati.\nDisclaimer accettati: {}, Disclaimer che ci si aspettava: {}", dichiarazione.getAltreOpzioni(), list);
			throw new ValidationFailureException("Validazione dichiarazione fallita, Non tutti i disclaimer risultano essere stati accettati.");
		}
		if (tipoProcedimento == 2 && localizzazioneComuni != null && !localizzazioneComuni.isEmpty())
		{
			boolean bpParchiERiserve = false;
			boolean bpImmobiliAreeInteresse = false;
			for (final LocalizzazioneInterventoDTO l : localizzazioneComuni)
			{
				final UlterioriInformazioniDto ui = l.getUlterioriInformazioni();
				if (ui.getBpParchiEReserve() != null && !ui.getBpParchiEReserve().isEmpty())
					bpParchiERiserve = true;
				if (ui.getBpImmobileAreePubblico() != null && !ui.getBpImmobileAreePubblico().isEmpty())
					bpImmobiliAreeInteresse = true;
			}
			if (bpParchiERiserve && (dichiarazione.getArt142() == null || dichiarazione.getArt142().size() < 2))
				throw new ValidationFailureException("Validazione dichiarazione fallita, è obbligatorio che sia selezionato almeno un articolo 142.");
			if (dichiarazione.getArt142()!=null && dichiarazione.getArt142().size() ==1)
				throw new ValidationFailureException("Validazione dichiarazione fallita, selezionare almeno un articolo 142.");
			if (bpImmobiliAreeInteresse && (dichiarazione.getArt136() == null || dichiarazione.getArt136().size() < 2))
				throw new ValidationFailureException("Validazione dichiarazione fallita, è obbligatorio che sia selezionato almeno un articolo 136.");
			else if (!bpImmobiliAreeInteresse && (dichiarazione.getArt136() != null && !dichiarazione.getArt136().isEmpty()))
				throw new ValidationFailureException("Validazione dichiarazione fallita, articolo/i 136 non ammesso/i.");
			
			if (dichiarazione.getInCasoDiVariante() != null && dichiarazione.getInCasoDiVariante()==true) {
				if (StringUtil.isEmpty(dichiarazione.getNumero()) || StringUtil.isEmpty(dichiarazione.getDa()) || dichiarazione.getInData()==null)
					throw new ValidationFailureException("Validazione dichiarazione fallita, compilare tutti i campi riguardanti il caso di intervento di variante.");
			}
		}
		
		if (dichiarazione.getTitolaritaEsclusivaIntervento().equalsIgnoreCase("N"))
		{
			if (altriTitolari==null || altriTitolari.isEmpty())
			{
				LOGGER.error("Validazione dichiarazione fallita. Deve essere presente almeno un \"Altro Titolare\".");
				throw new ValidationFailureException("Validazione dichiarazione fallita.\nDeve essere presente almeno un \"Altro Titolare\".");
			}
			try {
				final List<AllegatiDTO> allegati = this.allegatoDao.searchBySezioni(idPratica, Collections.singletonList(SezioneAllegati.DICHIARAZIONI_ASSENSO.name()));
				if (allegati==null || allegati.isEmpty()) {
					LOGGER.error("Validazione dichiarazione fallita. Non è presente la dichiarazione d'assenso.");
					throw new ValidationFailureException("Validazione dichiarazione fallita.\nNon è presente la dichiarazione d'assenso.");
				}
			} catch (final Exception e) {
				LOGGER.error("Errore nella validazione! ", e);
				throw new ValidationFailureException("Errore nella validazione! ", e);
			}
		}
		else if (dichiarazione.getTitolaritaEsclusivaIntervento().equalsIgnoreCase("S"))
		{
			if (altriTitolari!=null && !altriTitolari.isEmpty())
			{
				LOGGER.error("Validazione dichiarazione fallita. Non è possibile aggiungere nessun \"Altro Titolare\".");
				throw new ValidationFailureException("Validazione dichiarazione fallita.\nNon è possibile aggiungere nessun \"Altro Titolare\".");
			}
		}
	}

	private <T extends Object> void doValidate(final T obj,final String tabRiferimento) throws ValidationFailureException
	{
		final Set<ConstraintViolation<T>> violations = this.validator.validate(obj);
		if (!violations.isEmpty())
		{
			final StringBuilder message = 
					new StringBuilder("Sono stati violati i seguenti vincoli di integrità: ");
			for (final ConstraintViolation<T> violation : violations)
				message
				.append(tabRiferimento +": "+violation.getMessage())
				.append("; ").append("\n");
			throw new ValidationFailureException(message.toString());
		}
	}

	private <T extends Object> void doValidate(final Collection<T> objs,final String tabRiferimento) throws ValidationFailureException
	{
		final int j = 1;
		for (final T obj : objs)
		{
			Set<ConstraintViolation<T>> violations=null;
			if(obj.getClass()==RichiedenteDto.class) {
				 violations = this.validator.validate(obj,RichiedenteInfo.class);	
			}else {
				 violations = this.validator.validate(obj);
			}
			if (!violations.isEmpty())
			{
				final StringBuilder message = new StringBuilder(" TAB " + tabRiferimento)
						//.append(obj.getClass().getName())
						.append(", elemento con indice ").append(j)
						.append(". Sono stati violati i seguenti vincoli di integrità: ");
				for (final ConstraintViolation<T> violation : violations)
					message.append(violation.getMessage()).append("; ");
				throw new ValidationFailureException(message.toString());
			}
		}
	}

	private void setDatiDocumento(final InformazionePersonaleDto richiedente, final ReferentiDocumentoDTO documentoRiconoscimento)
	{
		final ReferentiDocumentoDTO ref = richiedente.getDocumento();
		documentoRiconoscimento.setDataRilascio(ref != null ? ref.getDataRilascio() : null);
		documentoRiconoscimento.setDataScadenza(ref != null ? ref.getDataScadenza() : null);
		documentoRiconoscimento.setEnteRilascio(ref != null ? ref.getEnteRilascio() : null);
		documentoRiconoscimento.setIdTipo(ref != null ? ref.getIdTipo() : null);
		documentoRiconoscimento.setNumero(ref != null ? ref.getNumero() : null);
	}

	
	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_READ)
	public List<DocumentoDto> getDichiarazioni(final UUID idPratica) throws Exception
	{
		return dichiarazioni(idPratica, false);
	}
	
	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_READ)
	public List<DocumentoDto> getDichiarazioni(final UUID idPratica, final boolean batch) throws Exception
	{
		return dichiarazioni(idPratica, batch);
	}

	private List<DocumentoDto> dichiarazioni(final UUID idPratica, final boolean batch)
			throws CustomOperationToAdviceException {
		final List<AllegatiDTO> allegati = batch ? this.allegatoSvc.getAllegatiBatch(idPratica, SezioneAllegati.GENERA_STAMPA) :
			this.allegatoSvc.getAllegati(idPratica, SezioneAllegati.GENERA_STAMPA);
		final List<DocumentoDto> list = new LinkedList<DocumentoDto>();
		for(final AllegatiDTO allegato: allegati)
		{
			final DocumentoDto tmp = new DocumentoDto(allegato); 
			tmp.setType(tmp.getType().toLowerCase().equals("istanza") ? "700": "701");
			list.add(tmp);
		}
		return list;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public void rimuoviEventualiDocumentiGenerati(final PraticaDTO pratica) throws Exception
	{
		final List<AllegatiDTO> allegati = this.allegatoDao.findByPraticaAndSezione(pratica.getId(), pratica.getTipoProcedimento(), SezioneAllegati.GENERA_STAMPA_PREVIEW);
		if(allegati!=null && allegati.size()>0) {
			for(final AllegatiDTO allegato:allegati) {
				this.allegatoSvc.deleteAllegato(allegato.getId().toString(),false);	
			}
		}
	}
	
	private void generaDocumentiDigitalizzati(String codicePraticaAppptr) throws Exception {
		FascicoloDto fascicolo = new FascicoloDto();
		fascicolo.setCodicePraticaAppptr(codicePraticaAppptr);
		fascicolo = this.find(fascicolo);
		File domandaPdf=digitalizzazioneSvc.generateDomandaIstanza(fascicolo);
		//spedisco su cms ed inserisco negli allegati
		final MockMultipartFile mFileDomanda=new MockMultipartFile(domandaPdf.getName(),AllowedMimeType.PDF.getMimeType(), domandaPdf);
		this.allegatoSvc.uploadAllegato(fascicolo.getId().toString(), Collections.singletonList(750), "Istanza_"+codicePraticaAppptr+"_originale", mFileDomanda);		
		File schedaTecnicaPdf=digitalizzazioneSvc.generateDomandaSchedaTecnica(fascicolo);
		//spedisco su cms ed inserisco negli allegati
		final MockMultipartFile mFileSchedaTecnica=new MockMultipartFile(schedaTecnicaPdf.getName(),AllowedMimeType.PDF.getMimeType(), schedaTecnicaPdf);
		this.allegatoSvc.uploadAllegato(fascicolo.getId().toString(), Collections.singletonList(751), "Dichiarazione_Tecnica_" + codicePraticaAppptr +"_originale" , mFileSchedaTecnica);
	}
	
	
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public void cambiaStato(final UUID idPratica, final AttivitaDaEspletare stato) throws Exception
	{
		final Integer n = this.praticaDao.updateStatoPratica(idPratica, stato); 
		if(n != 1)
		{
			throw new Exception("Sono state aggiornate " + n + " pratiche quando mi aspettavo di assegnare lo stato " 
								+ stato.name() + " solo alla pratica con id "+ idPratica.toString());
		}
		if(stato==AttivitaDaEspletare.IN_ATTESA_DI_PROTOCOLLAZIONE)
		{
			this.assegnaFascicolo.assegnamentoAutomaticoInTrasmissione(idPratica);
		}
		if(stato==AttivitaDaEspletare.GENERA_STAMPA_DOMANDA) {//lato sportello
			//genero istanza e scheda tecnica e rimuovo eventuali file precedentemente generati...
			PraticaDTO pratica=new PraticaDTO();
			pratica.setId(idPratica);
			pratica= this.praticaDao.find(pratica);
			this.checkDiMiaCompetenza(pratica);
			try {
				this.rimuoviEventualiDocumentiGenerati(pratica);
				this.generaDocumentiDigitalizzati(pratica.getCodicePraticaAppptr());	
			}catch (final Exception e) {
				LOGGER.error("Errore durante la generazione dei documenti precompilati Istanza e Scheda Tecnica",e);
				throw new CustomOperationToAdviceException("Errore durante la generazione dei documenti precompilati Istanza e Scheda Tecnica, contattare l'amministratore.");
			}
		}
		if(stato==AttivitaDaEspletare.IN_LAVORAZIONE) {
			PraticaDTO pratica=new PraticaDTO();
			pratica.setId(idPratica);
			pratica= this.praticaDao.find(pratica);
			if(StringUtil.isBlank(pratica.getCodiceTrasmissione())){
				final String codiceTrasmissione=this.remoteService.generaCodiceFascicolo(Integer.parseInt(pratica.getEnteDelegato()));
				pratica.setCodiceTrasmissione(codiceTrasmissione);
				this.praticaDao.update(pratica);
			}
			
		}
		
	}
	
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public void passaTrasmissione(final UUID idPratica) throws Exception
	{
		final Integer n = this.praticaDao.updateStatoPratica(idPratica, AttivitaDaEspletare.IN_TRASMISSIONE);
		if(n != 1)
		{
			throw new Exception("Sono state aggiornate " + n + " pratiche quando mi aspettavo di assegnare lo stato " 
								+ AttivitaDaEspletare.IN_TRASMISSIONE.name() + " solo alla pratica con id "+ idPratica.toString());
		}	
		final ProvvedimentoFinaleDTO entity = new ProvvedimentoFinaleDTO();
		entity.setIdPratica(idPratica);
		entity.setDraft(true);
		this.provvedimentoService.saveProvvedimento(entity);
	}
	
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public void setDataPresentazione(final UUID idPratica) throws Exception
	{
		final int n=this.praticaDao.updateDataPresentazione(idPratica, new Date());
		if(n != 1)
			throw new Exception("Sono state aggiornate " + n + " pratiche quando mi aspettavo di assegnare la data_presentazione solo alla pratica con id "+ idPratica.toString());
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_READ)
	public AttivitaDaEspletare getStato(final UUID idPratica) throws Exception
	{
		return this.praticaDao.getStato(idPratica);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_READ)
	public Map<String, Long> getCounterForIstruttoria(final PraticaSearch filters) throws Exception
	{
		this.istrPraticaService.prepareForSearch(filters);
		final boolean withoutAssignment = this.userUtil.getGruppo() != null && (this.userUtil.getGruppo().equals(Gruppi.REG_) || this.userUtil.getGruppo().equals(Gruppi.ADMIN));
		LOGGER.info("Assenza contatore da assegnare: {}", withoutAssignment);
		return this.praticaDao.getCountersIstruttoria(filters, withoutAssignment);
	}
	
	/**
	 * Metodo che richiama il servizio SOAP per la protocollazione del Fascicolo 
	 *
	 * @author G.Lavermicocca
	 * @throws Exception 
	 * @date 11 set 2020
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public String protocollaFascicolo(final UUID idPratica, final boolean batch) throws Exception {
		try {
			//Protocollazione
			LOGGER.info("protocollaFascicolo - inizio protocollazione codicePratica:"+idPratica);
			final PraticaDTO praticaDto = this.praticaDao.find(idPratica);
			final List<DocumentoDto> listDocumenti = this.getDichiarazioni(idPratica, batch);
			final Optional<DocumentoDto> optIst = listDocumenti.stream().filter(el->el.getType().equalsIgnoreCase("700")).findAny();
			if(optIst.isEmpty()) {
				throw new CustomOperationToAdviceException("Errore nel recupero del documento istanza firmato in fase di protocollazione");
			}
			final UUID idIstanza=optIst.get().getId();
			final Optional<DocumentoDto> optSch = listDocumenti.stream().filter(el->el.getType().equalsIgnoreCase("701")).findAny();
			if(optSch.isEmpty()) {
				throw new CustomOperationToAdviceException("Errore nel recupero del documento Scheda tecnica firmato in fase di protocollazione");
			}
			final UUID idSchedaTecnica=optSch.get().getId();
			String numeroProtocollo="";
			ProtocolloResponseBean segnatura = null;
			//segnatura=this.protocolloCallService.getNumeroProtocollo(idAllegato, idPratica, ProtocolNumberType.I);
			segnatura=this.protocolloCallService.protocollaIstanza(idIstanza,idSchedaTecnica, idPratica, ProtocolNumberType.I);
			if(segnatura==null) {
				throw new CustomOperationToAdviceException("Errore nella protocollazione, riprovare piu' tardi !!!");
			}
			numeroProtocollo = segnatura.toFormatoEsteso();
			praticaDto.setNumeroProtocollo(numeroProtocollo);
			final Date date = new SimpleDateFormat("ddMMyyyy").parse(segnatura.getDataRegistrazione()); 
			final Timestamp t = new Timestamp(date.getTime());
			praticaDto.setDataProtocollo(t);
			//update dello stato in istanza presentata
			//praticaDto.setStato(AttivitaDaEspletare.ISTANZA_PRESENTATA);
			praticaDto.setStato(AttivitaDaEspletare.IN_PREISTRUTTORIA);
			this.praticaDao.update(praticaDto);
			return numeroProtocollo;
			
		}catch (final CustomOperationToAdviceException e) {
			throw e;
		}catch (final Exception e) {
			LOGGER.error("Errore durante la protocollazione",e);
			throw new CustomOperationToAdviceException("Errore durante la protocollazione del fascicolo, contattare il supporto !");
		}
	}
	
	/**
	 * Metodo per il salvataggio dei dati utilizzati e dei dati ritornati dalla protocollazione
	 *
	 * @author G.Lavermicocca
	 * @param idPratica 
	 * @date 11 set 2020
	 */
	void salvaDatiProtocollo(final ConfigurazioniEnteDTO configEnte,final SegnaturaProtocollo segnatura,final ProtocolNumberType tipo, final UUID idPratica) {
		try {
			final ProtocolloDTO protocolloEntity = new ProtocolloDTO();
			protocolloEntity.setIdProtocollo(UUID.randomUUID());
			
			protocolloEntity.setClientprotocolloadministration(configEnte.getProtocollazioneAdministration());
			protocolloEntity.setClientprotocolloaoo(configEnte.getProtocollazioneAoo());
			protocolloEntity.setClientprotocolloendpoint(configEnte.getProtocollazioneEndpoint());
			protocolloEntity.setClientprotocollohashalgorihtm(configEnte.getProtocollazioneHashAlgorithm());
			protocolloEntity.setClientprotocollopassword(configEnte.getProtocollazionePassword());
			protocolloEntity.setClientprotocolloregister(configEnte.getProtocollazioneRegister());
			protocolloEntity.setClientprotocollouser(configEnte.getProtocollazioneUser());
			
			protocolloEntity.setDataregistrazione(segnatura.getDataRegistrazione());
			protocolloEntity.setNumeroprotocollo(segnatura.getNumeroProtocollo());
			protocolloEntity.setCodiceregistro(segnatura.getCodiceRegistro());
			protocolloEntity.setCodiceamministrazione(segnatura.getCodiceAmministrazione());
			protocolloEntity.setCodiceaoo(segnatura.getCodiceAOO());
			protocolloEntity.setRequest(MDC.get(ProtocolloLogInterceptor.MDC_SOAP_REQUEST));
			protocolloEntity.setResponse(MDC.get(ProtocolloLogInterceptor.MDC_SOAP_RESPONSE));
			protocolloEntity.setIdAllegato(null);
			protocolloEntity.setIdPratica(idPratica);
			protocolloEntity.setProtocollo(segnatura.toString());
			protocolloEntity.setVerso(tipo.name());
			//verso
		//	LocalDate dd = LocalDate.parse(segnatura.getDataRegistrazione(), DateTimeFormatter.ofPattern("ddMMyyyy"));
		//	Date d = Date.from(dd.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			protocolloEntity.setDataEsecuzione(new Timestamp((new Date()).getTime()));
			this.protocolloService.insert(protocolloEntity);
		} catch (final Exception e) {
			LOGGER.error("Errore durante il salvataggio dei dati della transazione protocollo",e);
		}
	}
	
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public PraticaDTO updateProtocolloFascicolo(final ProtocolloDto protocollo) throws Exception {
		LOGGER.info("updateProtocollo codicePratica:"+protocollo.getCodiceFascicolo());
		//carica pratica
		PraticaDTO praticaDTO= new PraticaDTO();
		praticaDTO=this.praticaDao.findByCodice(protocollo.getCodiceFascicolo());
		if(!praticaDTO.getStato().equals(AttivitaDaEspletare.IN_ATTESA_DI_PROTOCOLLAZIONE))
		{
			LOGGER.error("Non è possibile effettuare la protocollazione per una pratica che non si trova nello stato {}", AttivitaDaEspletare.IN_ATTESA_DI_PROTOCOLLAZIONE.name());
			throw new Exception("Non è possibile effettuare la protocollazione per una pratica che non si trova nello stato "+ AttivitaDaEspletare.IN_ATTESA_DI_PROTOCOLLAZIONE.name());
		}
		praticaDTO.setNumeroProtocollo(protocollo.getNumeroProtocollo());
		final Timestamp t = new Timestamp(protocollo.getDataProtocollo().getTime());
		praticaDTO.setDataProtocollo(t);
//		praticaDTO.setDataProtocollo(protocollo.getDataProtocollo());
		//praticaDTO.setStato(AttivitaDaEspletare.ISTANZA_PRESENTATA);
		praticaDTO.setStato(AttivitaDaEspletare.IN_PREISTRUTTORIA);
		this.praticaDao.update(praticaDTO);
		this.inviaNotificaRicezioneIstanza(praticaDTO);
		return praticaDTO;
	}
	
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public void inviaNotificaRicezioneIstanza(final UUID pratica) throws Exception  {
		executeInviaNotificaRicezioneIstanza(pratica, null, null, null);
	}
	
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public void inviaNotificaRicezioneIstanza(final UUID pratica, final String username, final String ruolo, final int enteDelegato) throws Exception  {
		executeInviaNotificaRicezioneIstanza(pratica, username, ruolo, enteDelegato);
	}

	private void executeInviaNotificaRicezioneIstanza(final UUID pratica, final String username, final String ruolo, final Integer enteDelegato) throws Exception {
		PraticaDTO praticaDTO= new PraticaDTO();
		praticaDTO=this.praticaDao.findTxWrite(pratica);
		this.inviaNotificaRicezioneIstanza(praticaDTO, username, ruolo, enteDelegato);
	}
	
	
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public void inviaNotificaRicezioneIstanza(final PraticaDTO pratica) throws Exception  {
		this.inviaMailRichiedente(pratica,SezioneIstruttoria.RIC_ISTANZA , null, true, false);
	}
	
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public void inviaNotificaRicezioneIstanza(final PraticaDTO pratica, final String username, final String ruolo, final int enteDelegato) throws Exception  {
		this.inviaMailRichiedente(pratica,SezioneIstruttoria.RIC_ISTANZA , null, true, false, username, ruolo, enteDelegato);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public void inviaMailRichiedente(final PraticaDTO pratica,
			final SezioneIstruttoria sezioneTemplate,
			final IntegrazioneDTO integrazioneDto,
			final boolean toAltriTitolari,
			final boolean toTecnico) throws Exception  {
		//inserisco nella MDC l'organizzazione mockata... in quanto in questo thread sono con l'autenticazione dello user in RICHIEDENTI
		executeInvioMailRichiedente(pratica, sezioneTemplate, integrazioneDto, toAltriTitolari, toTecnico, null, null, null);
	}
	
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public void inviaMailRichiedente(final PraticaDTO pratica,
			final SezioneIstruttoria sezioneTemplate,
			final IntegrazioneDTO integrazioneDto,
			final boolean toAltriTitolari,
			final boolean toTecnico,
			final String username,
			final String ruolo,
			final int enteDelegato) throws Exception  {
		executeInvioMailRichiedente(pratica, sezioneTemplate, integrazioneDto, toAltriTitolari, toTecnico, username, ruolo, enteDelegato);
	}

	private void executeInvioMailRichiedente(final PraticaDTO pratica, final SezioneIstruttoria sezioneTemplate,
			final IntegrazioneDTO integrazioneDto, final boolean toAltriTitolari, final boolean toTecnico, final String username, final String ruolo,
			Integer enteDelegato)
			throws Exception, CustomOperationToAdviceException {
		final TemplateEmailSearch searchTemplate=new TemplateEmailSearch();
		searchTemplate.setIdOrganizzazione(Integer.parseInt(pratica.getEnteDelegato()));
		searchTemplate.setVisibilita(Gruppi.ED_.name());
		if(sezioneTemplate.equals(SezioneIstruttoria.RIC_ISTANZA)|| 
				sezioneTemplate.equals(SezioneIstruttoria.RIC_INTEGR_IST)) {
			//ok
		}else {
			throw new Exception("sezione template non prevista in questo metodo inviaMailRichiedente :" + sezioneTemplate);
		}
		searchTemplate.setSezione(sezioneTemplate.name());
		PaginatedList<TemplateEmailDTO> templ = this.templateEmail.search(searchTemplate);
		if(templ.getCount()==0) {
			//non si sa mai... magari non è sincronizzato...
			searchTemplate.setIdOrganizzazione(0);//default
			templ = this.templateEmail.search(searchTemplate);
		}
		if (templ.getCount()>0 && templ.getList()!=null && templ.getList().size()>0) {
			final TemplateEmailDTO template = templ.getList().get(0);
			final TemplateEmailDestinatariDto fullTemplate = this.templateEmail.info(template.getIdOrganizzazione(), template.getCodice());
			Function<String,String> customResolving=null;
			if (integrazioneDto != null && integrazioneDto.getDataCaricamento() != null) {
				//risolvo qui l'unico placeholder che non  riuscirebbe a risolvere il service ...
				customResolving = (pl) -> {
					if (pl.equals(PlaceHolder.DATA_INTEGRAZIONE.name())) {
						final SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
						return sdfDate.format(integrazioneDto.getDataCaricamento());
					}
					return pl;
				};
			}
			fullTemplate.getTemplate().setOggetto(this.resolvePlaceholderSvc.risolviTesto(
					fullTemplate.getTemplate().getPlaceholders(), 
					fullTemplate.getTemplate().getOggetto(), pratica,customResolving));
			fullTemplate.getTemplate().setTesto(this.resolvePlaceholderSvc.risolviTesto(
					fullTemplate.getTemplate().getPlaceholders(), 
					fullTemplate.getTemplate().getTesto(), pratica,customResolving));
			final DettaglioCorrispondenzaDTO corrDett = (username != null && !username.isEmpty() && ruolo != null && !ruolo.isEmpty()) ?
					this.comunicazioniService.create(pratica.getId(),enteDelegato,Gruppi.RICHIEDENTI,fullTemplate,false, username, ruolo) : 
						this.comunicazioniService.create(pratica.getId(),null,Gruppi.RICHIEDENTI,fullTemplate,false);
			corrDett.getCorrispondenza().setBozza(false);
			corrDett.getCorrispondenza().setOggetto(fullTemplate.getTemplate().getOggetto());
			corrDett.getCorrispondenza().setTesto(fullTemplate.getTemplate().getTesto());
			corrDett.getCorrispondenza().setCodiceTemplate(fullTemplate.getTemplate().getCodice());
			corrDett.getCorrispondenza().setRiservata(fullTemplate.getTemplate().isRiservata());
			fullTemplate.getDestinatari().forEach(dest->{
				corrDett.getDestinatari().add(new DestinatarioDTO(dest,true));	
			});
			this.addDestinatariPratica(corrDett.getDestinatari(), pratica, toAltriTitolari, toTecnico);
			final DettaglioCorrispondenzaDTO corrDettSaved = this.comunicazioniService.saveComunication(corrDett, pratica.getId(), username, Gruppi.RICHIEDENTI.name(), ruolo);
				this.comunicazioniService.sendComunication(corrDettSaved, pratica.getId());
		}else {
			
			throw new CustomOperationToAdviceException("Impossibile inviare la mail, template inesistente !!!");
		}
	}
	
	@Override
	public void addDestinatariPratica(final List<DestinatarioDTO> destinatari,final PraticaDTO pratica,final boolean toAltriTitolari,final boolean toTecnico) throws Exception {
		List<ReferenteFascicoloDTO> referenti = this.referenteDao.findByPratica(pratica.getId());
		final Optional<ReferenteFascicoloDTO> pecRichiedente = referenti.stream().filter(x -> x.getTipoReferente().equals(TipoReferente.SD.getValue())).findAny();
		//pec richiedente
		if(pecRichiedente.isPresent() && StringUtil.isEmail(pecRichiedente.get().getPec())) {
			destinatari.add(new DestinatarioDTO(pecRichiedente.get().getCognome() + "  " + pecRichiedente.get().getNome(), pecRichiedente.get().getPec(), "TO"));		
		}
		//aggiungo il delegato corrente se esiste
		if(pratica.getRuoloPratica().equals(RuoloPraticaEnum.DELEGATO.getCodice())) {
			PraticaDelegatoDTO delCorr = this.pdDao.getDelegatoCorrente(pratica.getId());
			if(delCorr!=null) {
				DestinatarioDTO destDto = new DestinatarioDTO(delCorr.getCognome() + "  " + delCorr.getNome(), delCorr.getPec() != null ? delCorr.getPec() : delCorr.getMail(), "TO");
				destinatari.add(destDto);
			}
		}
		//pec in altri titolari...
		if (toAltriTitolari) {
			List<ReferenteFascicoloDTO> altriTitolari = referenti.stream().filter(x -> 
				x.getTipoReferente().equals(TipoReferente.AT.getValue())).collect(Collectors.toList());
			if(altriTitolari != null && altriTitolari.size() > 0) {
				altriTitolari.forEach(titolare -> {
					try {
						destinatari.add(new DestinatarioDTO(titolare.getCognome() + "  " + titolare.getNome(), titolare.getPec(), "TO"));
					} catch (final Exception e) {
						LOGGER.error("errore nella creazione del destinatario per il titolare: " + titolare.getId()
						+ " pratica " + pratica.getCodicePraticaAppptr(), e);
					}
				});
			}
		}
		//pec tecnico incaricato...
		if (toTecnico ) {
			final Optional<ReferenteFascicoloDTO> pecTecnico = referenti.stream().filter(x -> x.getTipoReferente().equals(TipoReferente.TR.getValue())).findAny();
			if(pecTecnico.isPresent() && StringUtil.isEmail(pecTecnico.get().getPec())) {
				destinatari.add(new DestinatarioDTO(pecTecnico.get().getCognome() + "  " + pecTecnico.get().getNome(), pecTecnico.get().getPec(), "TO"));		
			}
		}	
	}
	
	

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_READ)
	public PraticaDTO findPraticaPubblica(final String codice) throws CustomOperationToAdviceException {
		return this.praticaDao.findPraticaPubblica(codice);
	}
	
	@Override
	public void controllaVincoli(final UUID idPratica, final ValidInfoDto dto, final boolean applicaModifiche) throws Exception {
		String avviso = "";
		final List<Integer> comuni = this.localizzazioneInterventoDao.selectEffettivo(idPratica);	// id (ente) dei comuni
		for (final Integer comune : comuni) {
			final ComuniCompetenzaDTO cc = new ComuniCompetenzaDTO();
			cc.setPraticaId(idPratica);
			cc.setEnteId(comune);
			final ComuniCompetenzaDTO comComp = this.comuniCompetenzaDao.find(cc);
			final String art38old  = comComp.getVincoloArt38 ();
			final String art100old = comComp.getVincoloArt100();
			final TnoPptrStrumentiUrbanisticiDTO art38  = this.strumentiUrbanisticiRepository.findByCodiceIstatAndTipo(comComp.getCodIstat(), 2);
			final TnoPptrStrumentiUrbanisticiDTO art100 = this.strumentiUrbanisticiRepository.findByCodiceIstatAndTipo(comComp.getCodIstat(), 1);
			final String art38new  = TnoPptrStrumentiUrbanisticiDTO.generaVincoloArt38 (art38 , comComp.getDescrizione());
			final String art100new = TnoPptrStrumentiUrbanisticiDTO.generaVincoloArt100(art100);
			if (!art38old.equals(art38new)) {
				avviso = avviso + "\nE' cambiato il vincolo di adeguatezza e conformità ai sensi dell'art.38 delle NTA del PPTR per il "+comComp.getDescrizione()+". Attualmente: ";
				avviso = avviso + "\n"+art38new;
				dto.setSchedaTecnica(false);
				if (applicaModifiche) {
					comComp.setVincoloArt38(art38new);
					this.comuniCompetenzaDao.aggiornaVincoli(comComp);
					this.comuniCompetenzaDao.accettaCambiamentiVincoli(comComp);
				}
			}
			if (!art100old.equals(art100new)) {
				avviso = avviso + "\nE' cambiato il vincolo di adeguatezza e conformità ai sensi dell'art.100 delle NTA del PPTR per il "+comComp.getDescrizione()+". Attualmente: ";
				avviso = avviso + "\n"+art100new;
				dto.setSchedaTecnica(false);
				if (applicaModifiche) {
					comComp.setVincoloArt100(art100new);
					this.comuniCompetenzaDao.aggiornaVincoli(comComp);
					this.comuniCompetenzaDao.accettaCambiamentiVincoli(comComp);
					this.destinazioneUrbabisticaInterventoDao.resettaPresaVisione(idPratica, comune);
				}
			}
			
			final List<ParchiPaesaggiImmobiliDTO> listaPPIold = new ArrayList<>();
			listaPPIold.addAll(this.parchiPaesaggiImmobiliDao.select(idPratica, comune));
			final Set<String> codIstat = new HashSet<>();
			codIstat.add(comComp.getCodIstat());
			final List<PlainStringValueLabel> listaPPInew = new ArrayList<>();
			// occhio che sto sovrascrivendo il campo che potenzialmente è usato per le email di "BP parchi e riserve (P)"
			listaPPInew.addAll(this.remoteSchemaService.findBpParchi   (codIstat)); listaPPInew.forEach(e->{							 e.setLinked("P"); });
			listaPPInew.addAll(this.remoteSchemaService.findBpImmobili (codIstat)); listaPPInew.forEach(e->{ if (e.getLinked()==null) e.setLinked("I"); });
			listaPPInew.addAll(this.remoteSchemaService.findUcpPaesaggi(codIstat)); listaPPInew.forEach(e->{ if (e.getLinked()==null) e.setLinked("R"); });
			
			for (final PlainStringValueLabel nuovo : listaPPInew) {
				if (!listaPPIold.stream().filter(o -> o.getCodice().equals(nuovo.getValue())).findFirst().isPresent()) { // se c'è nel nuovo ma non nel vecchio
					String tipoVincolo = "ERRORE! Tipo vincolo non riconosciuto";
					if (nuovo.getLinked().equalsIgnoreCase("P")) tipoVincolo = "BP parchi e riserve";
					if (nuovo.getLinked().equalsIgnoreCase("R")) tipoVincolo = "UCP paesaggi rurali";
					if (nuovo.getLinked().equalsIgnoreCase("I")) tipoVincolo = "BP immobili di interesse pubblico";
					avviso = avviso + "\nNel "+comComp.getDescrizione()+" è ora presente un nuovo vincolo '"+tipoVincolo+"':";
					avviso = avviso + "\n"+nuovo.getDescription()+" ("+nuovo.getValue()+")";
					dto.setIstanza(false);
					if (applicaModifiche) {
						final ParchiPaesaggiImmobiliDTO nuovoVincolo = new ParchiPaesaggiImmobiliDTO();
						nuovoVincolo.setPraticaId(idPratica);
						nuovoVincolo.setComuneId(comune);
						nuovoVincolo.setTipoVincolo(tipoVincolo);
						nuovoVincolo.setCodice(nuovo.getValue());
						nuovoVincolo.setDescrizione(nuovo.getDescription());
						this.parchiPaesaggiImmobiliDao.insert(nuovoVincolo);
						nuovoVincolo.setTipoVariazione('I');
						this.parchiPaesaggiImmobiliDao.aggiornaVincoli(nuovoVincolo);
						this.parchiPaesaggiImmobiliDao.accettaCambiamentiVincoli(nuovoVincolo);
					}
				}
			}
			for (final ParchiPaesaggiImmobiliDTO vecchio : listaPPIold) {
				if (!listaPPInew.stream().filter(o -> o.getValue().equals(vecchio.getCodice())).findFirst().isPresent()) { // se c'è nel vecchio ma non nel nuovo
					if ("S".equalsIgnoreCase(vecchio.getSelezionato())) {
						String tipoVincolo = "ERRORE! Tipo vincolo non riconosciuto";
						if (vecchio.getTipoVincolo().equalsIgnoreCase("P")) tipoVincolo = "BP parchi e riserve";
						if (vecchio.getTipoVincolo().equalsIgnoreCase("R")) tipoVincolo = "UCP paesaggi rurali";
						if (vecchio.getTipoVincolo().equalsIgnoreCase("I")) tipoVincolo = "BP immobili di interesse pubblico";
						avviso = avviso + "\nNel "+comComp.getDescrizione()+" non è più presente il vincolo '"+tipoVincolo+"':";
						avviso = avviso + "\n"+vecchio.getDescrizione()+" ("+vecchio.getCodice()+")";
						dto.setIstanza(false);
						if (applicaModifiche) {
							vecchio.setTipoVariazione('R');
							this.parchiPaesaggiImmobiliDao.aggiornaVincoli(vecchio);
							this.parchiPaesaggiImmobiliDao.accettaCambiamentiVincoli(vecchio);
						}
					}
				}
			}
		}
		if (!avviso.equals(""))
			dto.setAvviso(avviso);
	}

	@Override
	public PraticaDTO findPraticaFromCorrispondenza(final Long idCorrispondenza) {
		return this.praticaDao.findPraticaFromCorrispondenza(idCorrispondenza);
	}
	
	
	private void setAttibutiConfigurazione(final MyPayBaseBean bean,final Integer idOrganizzazione) throws CustomOperationToAdviceException {
		ConfigurazioniEnteDTO confEnte = new ConfigurazioniEnteDTO();
		confEnte.setIdOrganizzazione(idOrganizzazione);
		try {
			confEnte=this.confEnteService.find(confEnte);
		}catch(final Exception e) {
			LOGGER.error("Errore nella find della configurazione dell'ente con id: "+confEnte.getIdOrganizzazione(),e);
			throw new CustomOperationToAdviceException("L'ente delegato intestatrio della pratica non ha l'integrazione con mypay!!!!");
		}
		if(!confEnte.isSistemaPagamento()|| 
				StringUtil.isBlank(confEnte.getPagamentoCodEnte())||
				StringUtil.isBlank(confEnte.getPagamentoPassword())
				) {
			LOGGER.error("Errore nella configurazione pagamenti dell'ente con id: "+confEnte.getIdOrganizzazione());
			throw new CustomOperationToAdviceException("L'ente delegato intestatrio della pratica non ha l'integrazione con mypay!!!!");
		}
		//-dati da configurazione ente
		bean.setEndPoint(confEnte.getPagamentoEndPoint());
		bean.setPassword(CryptUtil.encrypt(confEnte.getPagamentoPassword()));
		bean.setCodEnte(confEnte.getPagamentoCodEnte());	
		if(bean instanceof  InviaDovutiInputBean) {
			((InviaDovutiInputBean) bean).setTipologia(confEnte.getPagamentoTipologia());
			((InviaDovutiInputBean) bean).setTipoRiscossione(confEnte.getPagamentoTipoRiscossione());
		}
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public PagamentiMypayDTO effettuaPagamento(final PraticaDTO pratica,final Double importo,final String causale,final String retUrl) throws Exception {
		//chiamo il servizio di pagamento 
		//restituisco l'url per poter andare a pagare alla cassa mypay
		final ProfileUserResponseBean userProfile = this.userUtil.getMyProfile();
		final UserDetail userProfileWso2 = SecurityUtil.getUserDetail();
		final InviaDovutiInputBean inviaDovutiBean = new InviaDovutiInputBean();
		this.setAttibutiConfigurazione(inviaDovutiBean,Integer.parseInt(pratica.getEnteDelegato()));
		inviaDovutiBean.setCausale(causale);
		inviaDovutiBean.setImporto(BigDecimal.valueOf(importo));
		inviaDovutiBean.setCf(userProfileWso2.getFiscalCode());
		inviaDovutiBean.setEmail(userProfile.getEmail());
		inviaDovutiBean.setRetUrl(retUrl);
		inviaDovutiBean.setSoggPaga(userProfile.getCognome() + "  "+ userProfile.getNome());
		final PagamentiMypayDTO pMyPayDTO=new PagamentiMypayDTO();
		pMyPayDTO.setCfgEndpointMyPay(inviaDovutiBean.getEndPoint());
		pMyPayDTO.setCfgPasswordMyPay(inviaDovutiBean.getPassword());
		pMyPayDTO.setCodIpaEnte(inviaDovutiBean.getCodEnte());
		pMyPayDTO.setCausale(inviaDovutiBean.getCausale());
		pMyPayDTO.setPraticaId(pratica.getId());
		pMyPayDTO.setImporto(inviaDovutiBean.getImporto());
		pMyPayDTO.setCfPagatore(inviaDovutiBean.getCf());
		pMyPayDTO.setEmailPagatore(inviaDovutiBean.getEmail());
		pMyPayDTO.setTipologia(inviaDovutiBean.getTipologia());
		pMyPayDTO.setTipoRiscossione(inviaDovutiBean.getTipoRiscossione());
		pMyPayDTO.setEnteId(pratica.getEnteDelegato());
		pMyPayDTO.setRetUrl(retUrl);
		final InviaDovutiOutputBean ret = this.myPayClientSvc.inviaDovuti(inviaDovutiBean);
		//mi scrivo un record nel DB con stato in pending....
		pMyPayDTO.setIud(ret.getIUD());
		pMyPayDTO.setIdsession(ret.getIdSession());
		pMyPayDTO.setError(ret.getError());
		pMyPayDTO.setEsito(ret.getEsito());
		pMyPayDTO.setUrlToPay(ret.getUrl());
		if(ret.getEsito().equals("OK")) {
			pMyPayDTO.setStato(
					ret.getEsito().equals("OK")?
							PagamentiMypayDTO.StatoPagamento.INCORSO.name():
								PagamentiMypayDTO.StatoPagamento.KO.name());
			this.myPayDao.insert(pMyPayDTO);	
		}else {
			throw new CustomOperationToAdviceException(ret.getError());
			//non inserisco nulla sul DB
		}
		return pMyPayDTO;
	}
	
	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_READ)
	public PaginatedList<PagamentiMypayDTO> searchPagamenti(final PagamentiMypaySearch search) throws Exception{
		final PaginatedList<PagamentiMypayDTO> ret = this.myPayDao.search(search);
		return ret;
	}
	
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public	void aggiornaEventualiStatiPagamento(final PagamentiMypaySearch pSearch) throws Exception{
		final PagamentiMypaySearch search=new PagamentiMypaySearch();
		//se ci sono pagamenti da aggiornare, chiamo il service per aggiornare lo stato...
		search.setStato(PagamentiMypayDTO.StatoPagamento.INCORSO.name());
		search.setLimit(99999);
		search.setPraticaId(pSearch.getPraticaId());
		search.setPage(1);
		//in teoria potrei mettere l'ultimo orario di controllo... cosi' da non ripetere il check a breve distanza...
		final PaginatedList<PagamentiMypayDTO> ret = this.myPayDao.search(search);
		if(ret!=null && ret.getList()!=null && ret.getList().size()>0) {
			final ChiediPagatiInputBean chiediBean=new ChiediPagatiInputBean();
			for(final PagamentiMypayDTO pagamentoInCorso:ret.getList()) {
				chiediBean.setEndPoint(pagamentoInCorso.getCfgEndpointMyPay());
				chiediBean.setPassword(pagamentoInCorso.getCfgPasswordMyPay());
				chiediBean.setCodEnte(pagamentoInCorso.getCodIpaEnte());
				chiediBean.setIdsession(pagamentoInCorso.getIdsession());
				chiediBean.setIud(pagamentoInCorso.getIud());
				ChiediPagatiOutputBean outChiedi=null;
				try {
					outChiedi = this.myPayClientSvc.chiediPagati(chiediBean);
				}catch(final Exception e) {
					LOGGER.error("Errore  nel retrieval della chiedi pagati ",e);
				}
				if(outChiedi!=null && outChiedi.getStatoPagamento()!=null) {
					pagamentoInCorso.setStato(outChiedi.getStatoPagamento());
					pagamentoInCorso.setDataModifica(new Date());
					this.myPayDao.update(pagamentoInCorso);
				}
			}
		}
	}
	
	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_READ)
	public Map<String,BigDecimal> totPagato(final PraticaDTO pratica) throws Exception{
		Map<String,BigDecimal> retMap=null;
		retMap=this.myPayDao.getTotaliPerStato(pratica.getId());
		return retMap;
	}
	
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public int updatePraticaPerMigrazione(final PraticaDTO pratica) throws Exception{
		return this.praticaDao.update(pratica);
	}
	

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public PresentaIstanzaDto presentaIstanza(final UUID idPratica, final String codicePratica) throws Exception {
		final PraticaDTO pratica = this.praticaDao.find(idPratica);
		this.checkDiMiaCompetenza(pratica);
		if(!pratica.getStato().equals(AttivitaDaEspletare.ALLEGA_DOCUMENTI_SOTTOSCRITTI)) {
			throw new CustomOperationToAdviceException("Impossibile procedere con la presentazione, lo stato della pratica non è congruente");
		}
		final PresentaIstanzaDto resp = new PresentaIstanzaDto();
		Boolean valido = this.validaIstanza(codicePratica) && this.schedaTecnicaService.valida(idPratica);
		final List<String> types = Arrays.asList("700", "701");
		final List<DocumentoDto> docs = this.getDichiarazioni(idPratica);
		valido = valido && types.stream().allMatch(p -> docs.stream().anyMatch(q -> q.getType().equals(p)));
		if (!valido) {
			LOGGER.error("Impossibile procedere, non risultano caricati i file sottoscritti !!!");
			throw new CustomOperationToAdviceException(
					"Impossibile procedere, non risultano caricati i file sottoscritti !!!");
		}
		this.cambiaStato(idPratica, AttivitaDaEspletare.IN_ATTESA_DI_PROTOCOLLAZIONE);
		// setto la data di presentazione sulla pratica
		this.setDataPresentazione(idPratica);
		pratica.setDataPresentazione(DateUtil.currentTimestamp());
		
		//verifico se ho la protocollazione automatica
		ConfigurazioniEnteDTO configEnte = new ConfigurazioniEnteDTO();
		configEnte.setIdOrganizzazione(Integer.parseInt(pratica.getEnteDelegato()));
		try {
			configEnte = this.confEnteService.find(configEnte);
		}catch(final Exception e) {
			LOGGER.error("Impossibile trovare la configurazione dell'ente: {}, l'elaborazione prosegue senza tener presente la protocollazione automatica ",pratica.getEnteDelegato());
		}
		final ProtocollazioneBean bean = new ProtocollazioneBean();
		bean.setIdPratica(String.valueOf(idPratica));
		bean.setUsername(userUtil.getMyProfile().getUsername());
		bean.setRuolo(userUtil.getRuolo().toString());
		bean.setEnteDelegato(Integer.valueOf(pratica.getEnteDelegato()));
		if(configEnte.isSistemaProtocollazione()) {
			this.queueService.insertNewQueue(ProtocollazioneScheduler.ID, bean);
		} else {
			this.queueService.insertNewQueue(InvioMailScheduler.ID, bean);
		}
		resp.setValido(valido);
		return resp;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public void allineaDiogene(final FascicoloStatoBean fascicoloStatoBean) throws Exception {
		final PraticaDTO praticaDto=fascicoloStatoBean.getPratica();
		LOGGER.info("Start allineaDiogene pratica {} sezioni {}",praticaDto.getCodicePraticaAppptr(),fascicoloStatoBean.getSezioniAllegati());
		this.elaboraAllegatiSezione(fascicoloStatoBean.getSezioniAllegati(), praticaDto);
		if(fascicoloStatoBean.getSezioniAllegati().contains(SezioneAllegati.GENERA_STAMPA)) {
			//aggiungo anche i documenti di riconoscimento
			final List<AllegatiDTO> docRiconoscimento = this.allegatoDao.findDocumentiReferentiForDiogene(praticaDto.getId());
			if(ListUtil.isNotEmpty(docRiconoscimento)) {
				this.elaboraAllegatiDelFolder(praticaDto, SezioneAllegati.DOC_AMMINISTRATIVA_E.getFolderDiogene(), docRiconoscimento);
			}
		}
		if(fascicoloStatoBean.getSezioniAllegati().contains(SezioneAllegati.RELAZIONE_ENTE)) {
			//nel caso di relazione ente non è presente la relazione procedimento_contenuto
			final List<RelazioneEnteDTO> relazioni = this.relazioneEnteDao.findByIdPratica(praticaDto.getId());
			if(ListUtil.isNotEmpty(relazioni)) {
				final List<AllegatiDTO> allegatiRelazione = this.relazioneEnteDao.searchAllegati(relazioni.get(0).getIdRelazioneEnte());
				if(ListUtil.isNotEmpty(allegatiRelazione)) {
					this.elaboraAllegatiDelFolder(praticaDto, SezioneAllegati.RELAZIONE_ENTE.getFolderDiogene(), allegatiRelazione);
				}
			}
		}
		//cerco sempre eventuali allegati corrispondenza da evadere per diogene
		final List<AllegatiDTO> allCorr = this.allegatoDao.findAllegatiCorrispondenzaForDiogene(praticaDto.getId());
		if(ListUtil.isNotEmpty(allCorr)) {
			this.elaboraAllegatiDelFolder(praticaDto, SezioneAllegati.COMUNICAZIONI.getFolderDiogene(), allCorr);
		}
	}
	
	/**
	 * estrae i documenti della sezione ed invia allo scheduler diogene
	 * @author acolaianni
	 *
	 * @param sezioni
	 * @param pratica
	 * @throws CustomOperationToAdviceException
	 * @throws JsonProcessingException
	 * @throws SQLException
	 */
	private void elaboraAllegatiSezione(final List<SezioneAllegati> sezioni,final PraticaDTO pratica) throws CustomOperationToAdviceException, JsonProcessingException, SQLException {
		for(final SezioneAllegati sezione:sezioni) {
			final List<AllegatiDTO> allegati = this.allegatoDao.findByPraticaAndSezioneForDiogene(pratica.getId(), sezione);
			this.elaboraAllegatiDelFolder(pratica, sezione.getFolderDiogene(), allegati);
		}
	}

	/**
	 * invia il descrittore e il file allo scheduler diogene
	 * @author acolaianni
	 *
	 * @param pratica
	 * @param folderDiogene
	 * @param allegati
	 * @throws JsonProcessingException
	 * @throws SQLException
	 */
	private void elaboraAllegatiDelFolder(final PraticaDTO pratica, final String folderDiogene, final List<AllegatiDTO> allegati)
			throws JsonProcessingException, SQLException {
		for(final AllegatiDTO allegato:allegati) {
			if(allegato.getIdDiogeneScheduler()==null) {
				//mai elaborato... lo elaboro
				LOGGER.info("Elabora allegato {} in sezione {}",allegato.getNomeFile(),folderDiogene);
				this.diogeneDescrSvc.buildAndSendFile(pratica, allegato, folderDiogene);
			}
		}
	}

	/**
	 * @author Antonio La Gatta
	 * @date 9 giu 2022
	 * @see it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.FascicoloService#validaAzienda(String, Long, String)
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public RegistroImpreseBean validaAzienda(final String idPratica, final Long id, final String codiceFiscale) throws Exception {
		final StopWatch sw = LogUtil.startLog("validaAzienda");
		LOGGER.info("Valida Azienda");
		try {
			final PraticaDTO pk = new PraticaDTO();
			pk.setId(UUID.fromString(idPratica));
			final PraticaDTO pratica = this.praticaDao.find(pk);
			this.checkDiMiaCompetenza(pratica);
			final RegistroImpreseBean registroImpreseBean = this.callRegistroImprese.registroImprese(codiceFiscale);
			if(registroImpreseBean == null) {
				throw new CustomOperationToAdviceException("Impresa " + codiceFiscale + " non presente su visura camerale");
			}
			this.referentiDao.impostaAzienda(idPratica, id, registroImpreseBean, this.tipoAziendaRepository.privato());
			return registroImpreseBean;
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

	/**
	 * @author Antonio La Gatta
	 * @date 9 giu 2022
	 * @see it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.FascicoloService#validaEnte(String, Long, String)
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public IpaEnteDto validaEnte(final String idPratica, final Long id, final String codiceFiscale) throws Exception {
		final StopWatch sw = LogUtil.startLog("validaEnte");
		LOGGER.info("validaEnte");
		try {
			final PraticaDTO pk = new PraticaDTO();
			pk.setId(UUID.fromString(idPratica));
			final PraticaDTO pratica = this.praticaDao.find(pk);
			this.checkDiMiaCompetenza(pratica);
			final List<IpaEnteDto> enti = this.ipaEnteRepository.getList(codiceFiscale);
			if(ListUtil.isEmpty(enti)) {
				throw new CustomOperationToAdviceException("Ente " + codiceFiscale + " non presente su Indice Ipa");
			}
			final IpaEnteDto ente = new IpaEnteDto();
			ente.setCodiceFiscale(codiceFiscale);
			final StringBuilder nome = new StringBuilder();
			final StringBuilder codiceIpa = new StringBuilder();
			enti.forEach(ipa ->{
				if(nome.length() > 0) {
					nome     .append(", ");
					codiceIpa.append(", ");
				}
				nome     .append(ipa.getNome());
				codiceIpa.append(ipa.getCodiceIpa());
			});
			ente.setNome(nome.toString());
			ente.setCodiceIpa(codiceIpa.toString());
			this.referentiDao.impostaEnte(idPratica, id, ente, this.tipoAziendaRepository.pubblico());
			return ente;
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public int updateEsoneri(PraticaDTO praticaDTO, Boolean esoneroOneri, Boolean esoneroBollo,String userUpdated) throws Exception {
		this.checkStatoForUpdate(praticaDTO);
		int ret = this.praticaDao.updateEsoneri(praticaDTO.getId(), esoneroOneri, esoneroBollo,userUpdated);
		this.praticaDao.updateValidation(praticaDTO.getId(), null, null, false);
		return ret;
	}

	
	
	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_READ)
	public Boolean validaAllegati(PraticaDTO praticaDTO) throws CustomOperationToAdviceException {
		StringBuilder sbMessaggio=new StringBuilder();
		this.checkStatoForUpdate(praticaDTO);
		List<TipoContenutoDTO> tipiConts = null;
		//check bolli
		if(praticaDTO.getEsoneroBollo()==null || !praticaDTO.getEsoneroBollo()) {
			tipiConts = initTipiContenuto(praticaDTO, tipiConts);
			//verifico che tra gli allegati ha caricato la scansione bolli
			checkHasAllegatoPagamento(praticaDTO, sbMessaggio, tipiConts,CheckPagamentoContenutoEnum.BL);
		}
		//check oneri
		if(praticaDTO.getEsoneroOneri()==null || !praticaDTO.getEsoneroOneri()) {
			tipiConts = initTipiContenuto(praticaDTO, tipiConts);
			ConfigurazioniEnteDTO ret;
			try {
				ret = this.confEnteService.find(Integer.parseInt(praticaDTO.getEnteDelegato()));
				boolean hasProto = ret.isSistemaPagamento();
				if(hasProto) {
					checkIsPagato(praticaDTO,sbMessaggio);
				}else {
					checkHasAllegatoPagamento(praticaDTO, sbMessaggio, tipiConts,CheckPagamentoContenutoEnum.RO);
				}
			} catch (Exception e) {
				final String msgErr="Errore durante il retrieve delle informazioni sul sistema di pagamento dell'ente delegato";
				LOGGER.error(StringUtil.concateneString(msgErr," {}"),
						praticaDTO.getEnteDelegato(),e);
				throw new CustomOperationToAdviceException(msgErr);
			}
		}
		if(sbMessaggio.length()>0) {
			throw new CustomOperationToAdviceException(sbMessaggio.toString());
		}	
		return true;
	}

	
	
	/**
	 * effettua verifica se pagamento è concluso con ok sulla pratica
	 * @author acolaianni
	 *
	 * @param praticaDTO
	 * @param sbMessage errore di pagamento non concluso
	 */
	private void checkIsPagato(PraticaDTO praticaDTO,StringBuilder sbMessage) {
		PraticaPagamentiDTO pag = this.pagSvc.getPagamentoPraticaWithoutCheck(praticaDTO);
		if(pag==null || pag.getPagato()==false) {
			sbMessage.append("\nPagamento non concluso !!");
		}
	}

	/**
	 * verifica se esiste un allegato che habbia il tipo contenuto con ckeckpagamento pari a checkType
	 * @author acolaianni
	 *
	 * @param praticaDTO
	 * @param sbMessaggio
	 * @param tipiConts
	 * @param checkType
	 */
	private void checkHasAllegatoPagamento(PraticaDTO praticaDTO, StringBuilder sbMessaggio,
			List<TipoContenutoDTO> tipiConts,CheckPagamentoContenutoEnum checkType) {
		Optional<TipoContenutoDTO> tipoRicOnOpt = tipiConts.stream()
				.filter(el->el.getCheckPagamento()!=null && 
						el.getCheckPagamento().equals(checkType.name()))
				.findAny();
		if(tipoRicOnOpt.isPresent()) {
			List<AllegatiDTO> all = this.allegatoDao.findByPraticaETipo(praticaDTO.getId(),
					tipoRicOnOpt.get().getId() ,false);
			if(ListUtil.isEmpty(all)) {
				sbMessaggio.append(StringUtil.concateneString("\nManca tra gli allegati: ",
						tipoRicOnOpt.get().getDescrizione()));
			}
		}
	}

	/**
	 * inizializza lista tipi contenuto 
	 * @author acolaianni
	 *
	 * @param praticaDTO
	 * @param tipiConts
	 * @return
	 */
	private List<TipoContenutoDTO> initTipiContenuto(PraticaDTO praticaDTO, List<TipoContenutoDTO> tipiConts) {
		if(tipiConts==null) {
			tipiConts=this.tipoContenutoDao.selectByTipoProc(praticaDTO.getTipoProcedimento());
		}
		return tipiConts;
	}

	@Override
	public void pulisciLineaTemporale(FascicoloDto response) {
		if(List.of(Gruppi.ETI_).contains(userUtil.getGruppo())){
			response.setDataInizioLavorazione(null);
			response.setDataTrasmissioneParere(null);
			response.setDataTrasmissioneRelazione(null);
			response.setDataTrasmissioneVerbale(null);
		}
		if(List.of(Gruppi.SBAP_).contains(userUtil.getGruppo())){
			response.setDataInizioLavorazione(null);
		}
	}
	
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public void allineaDiogeneAllSezioni(UUID idPratica) throws JsonProcessingException, SQLException {
		final FascicoloStatoBean fsb=new FascicoloStatoBean();
		fsb.setPratica(this.praticaDao.find(idPratica));
		fsb.setSezioniAllegati(List.of(SezioneAllegati.values()));
		this.queueService.insertNewQueue(SendPlanToDiogeneScheduler.ID, fsb);
	}
	
}
