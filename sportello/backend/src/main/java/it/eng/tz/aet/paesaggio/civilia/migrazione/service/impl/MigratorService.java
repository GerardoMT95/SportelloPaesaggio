/**
 * 
 */
package it.eng.tz.aet.paesaggio.civilia.migrazione.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.aet.paesaggio.civilia.migrazione.builder.AutpaeDtoBuilder;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.AutPaesPptrLocalizInterv;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.CiviliaMail;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.CiviliaMailAllegati;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.Comuni_completo_cod_istat;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.DocAmmVPratiche;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.EnteParcoAss;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.InteressePubblicoAss;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PaesaggioRuraleAss;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrAltreDichiarRich;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrApprovato;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrAssoggProcedEdil;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrAttiAcquisiti;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrCaratterizzazioneIntervento;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrDescrIntervento;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrDestUrbInterv;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrDisclaimerXPratica;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrInquadramentoIntervento;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrIstanzaStampe;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrLegittProvved;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrLegittTitoli;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrLegittimita;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrMailInviate;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrParereSop;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrParticelleCatastali;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrProcedimentiContenzioso;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrProtocollo;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrProtocolloIstanza;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrProtocolloUscita;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrProvvedimento;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrQualificIntervento;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrReferentiDoc;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrReferentiProgetto;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrRelazioneEnte;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrSchedaAllegato;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrStatoEffMitigaz;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrStrumentiUrbanistici;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrStrutAntroStorCult;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrStrutEcosistemica;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrStrutIdrogeomorf;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrTipoIntervento;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrTitolarita;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.PptrVincoliArchArch;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.TPratica;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.VtnoAllegatiPptr;
import it.eng.tz.aet.paesaggio.civilia.migrazione.entity.VtnoAttivitaPptr;
import it.eng.tz.aet.paesaggio.civilia.migrazione.exception.FascicoloPreesistenteException;
import it.eng.tz.aet.paesaggio.civilia.migrazione.exception.FileSizeToLargeException;
import it.eng.tz.aet.paesaggio.civilia.migrazione.exception.MigrationException;
import it.eng.tz.aet.paesaggio.civilia.migrazione.service.IDatiPraticaCivService;
import it.eng.tz.aet.paesaggio.civilia.migrazione.service.IMigratorService;
import it.eng.tz.aet.paesaggio.civilia.migrazione.service.IProxyClientAlfresco;
import it.eng.tz.puglia.aet.alfresco.client.node.folder.bean.AlfrescoItemBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.DatabaseConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainNumberValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.CaratterizzazioneInterventoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.ConfigOptionValue;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.AlfrescoPaths;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.AllowedMimeType;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.AttivitaDaEspletare;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.EsitoParere;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.RuoloPraticaEnum;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.SezioneAllegati;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.StatoCorrispondenza;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.StatoParere;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.StatoRelazione;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.StatoSeduta;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoDestinatario;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoErrore;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoQualificazione;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoReferente;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoRicevuta;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiTipoContenutoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatoCorrispondenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AltreDichiarazioniRichDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AssoggProcedureEdilizieDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ComuniCompetenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.CorrispondenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DescrizioneSchedaTecnicaValuesDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DestinatarioDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DestinazioneUrbanisticaInterventoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DisclaimerDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DisclaimerPraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.EffettiMitigazioneDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.FascicoloCorrispondenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.InquadramentoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.LeggittimitaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.LeggittimitaProvvedimentiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.LeggittimitaTitoliDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.LocalizzazioneInterventoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ParchiPaesaggiImmobiliDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ParereSoprintendenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PareriEAttiAssensoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ParticelleCatastaliDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PptrApprovatoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PptrSelezioniDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDelegatoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ProcedimentiContenziosoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ProvvedimentoFinaleDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ReferentiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ReferentiDocumentoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.RelazioneEnteDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.RicevutaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TipoContenutoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TipoInterventoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TnoPptrStrumentiUrbanisticiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.VincArchDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.AllegatiRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.AllegatiTipoContenutoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.AllegatoCorrispondenzaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.AltreDichiarazioniRichRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.AssoggProcedureEdilizieRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.ComuniCompetenzaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.CorrispondenzaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.DescrizioneSchedaTecnicaValuesRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.DestinatarioRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.DestinazioneUrbanisticaInterventoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.DichiarazioneRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.DisclaimerPraticaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.DisclaimerRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.EffettiMitigazioneRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.FascicoloCorrispondenzaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.InquadramentoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.LeggittimitaProvvedimentiRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.LeggittimitaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.LeggittimitaTitoliRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.LocalizzazioneInterventoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.ParchiPaesaggiImmobiliRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.ParereSoprintendenzaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.PareriEAttiAssensoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.ParticelleCatastaliRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.PptrApprovatoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.PptrSelezioniRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.PraticaDelegatoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.PraticaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.ProcedimentiContenziosoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.ProvvedimentoFinaleRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.ReferentiDocumentoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.ReferentiRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.RelazioneEnteRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.RicevutaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.TipoInterventoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.VincArchRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.EnteDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.service.RemoteSchemaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.AllegatoService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.FascicoloService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.webmail.DynamicKafkaConsumer;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.MockMultipartFile;
import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.error.exception.CustomCmisException;
import it.eng.tz.puglia.error.exception.CustomOperationException;
import it.eng.tz.puglia.error.exception.CustomValidationException;
import it.eng.tz.puglia.service.http.IHttpClientService;
import it.eng.tz.puglia.util.string.StringUtil;
import it.eng.tz.regione.puglia.webmail.be.mail.ricevuta.daticert.Destinatari;
import it.eng.tz.regione.puglia.webmail.be.mail.ricevuta.daticert.Postacert;

/**
 * @author Adriano Colaianni
 * @date 23 apr 2021
 */
@Service
@ConditionalOnProperty("datasource.civ.enableMigration")
public class MigratorService implements IMigratorService {

	@Value("${cms.url.getObjectByPath}")
	private String cmsUrlGetObjectByPath;

	@Value("${pm.codice.applicazione}")
	private String nomeApplicazione;
	
	@Value("${cms.url.upload}")
	private String uploadUrl;
	
	
	@Value("${migration.cms.maxuploadsize}")
	private long maxFileUploadCms;
	
	
	@Autowired
	RemoteSchemaService commonSvc;

	@Autowired
	IHttpClientService httpSvc;
	
	@Autowired
	PraticaRepository praticaDao;
	@Autowired
	IDatiPraticaCivService praticaCivSvc;

	@Autowired
	FascicoloService fascicoloSvc;

	@Autowired
	AllegatoService allegatoSvc;
	
	@Autowired
	AllegatiRepository allegatiDao;
	
	@Autowired
	AllegatiTipoContenutoRepository allegatiTipoDao;
	
	@Autowired 
	ReferentiRepository referentiDao;
	@Autowired 
	ReferentiDocumentoRepository referentiDocDao;
	@Autowired 
	AltreDichiarazioniRichRepository altreDichDao;
	
	@Autowired 
	LocalizzazioneInterventoRepository locIntDao;
	
	@Autowired 
	DisclaimerPraticaRepository disclPraticaDao;
	@Autowired 
	DisclaimerRepository disclDao;
	@Autowired 
	ParticelleCatastaliRepository particelleDao;
	
	@Autowired 
	ParchiPaesaggiImmobiliRepository ppiDao;
	
	@Autowired
	private ComuniCompetenzaRepository comuniCompetenzaDao;
	
	@Autowired
	private DescrizioneSchedaTecnicaValuesRepository descrSTDao;
	@Autowired
	private TipoInterventoRepository tipoIntDao;
	@Autowired
	private DestinazioneUrbanisticaInterventoRepository destUrbIntDao;
	
	@Autowired
	private LeggittimitaRepository leggUrbDao;
	@Autowired
	private LeggittimitaTitoliRepository leggUrbTitDao;
	@Autowired
	private LeggittimitaProvvedimentiRepository leggUrbProvvDao;

	@Autowired
	private AssoggProcedureEdilizieRepository procEdilizieDao;
	
	@Autowired
	private InquadramentoRepository inquadramentoDao;
	
	@Autowired
	private EffettiMitigazioneRepository effMitDao;
	
	@Autowired
	private ProcedimentiContenziosoRepository procContDao;
	
	@Autowired
	private PareriEAttiAssensoRepository pareriAttiDao;
	
	@Autowired
	private PptrApprovatoRepository pptrApprovatoDao;
	@Autowired
	private PptrSelezioniRepository pptrSelezioniDao;
	
	@Autowired
	private VincArchRepository vincArchDao;
	
	@Autowired
	private DichiarazioneRepository dichiarazDao;
	
	@Autowired
	private RelazioneEnteRepository relazioneEnteDao;
	@Autowired
	private CorrispondenzaRepository corrDao;
	@Autowired
	private FascicoloCorrispondenzaRepository fascicoloCorrDao;
	
	@Autowired
	private DestinatarioRepository destinatariDao;
	
	@Autowired
	private AllegatoCorrispondenzaRepository allCorrDao;
	
	@Autowired
	private RicevutaRepository ricevutaDao;
	
	@Autowired
	private ParereSoprintendenzaRepository parereSopDao;
	@Autowired
	private ProvvedimentoFinaleRepository provvDao;
	
	
	@Autowired
	PraticaDelegatoRepository praticaDelegatoDao;
	
	
	@Autowired
	IProxyClientAlfresco proxyAlfrescoOrigin;
	
	@Value("${migration.local.basepath}")
	private String localBasePath;
	
	@Value("/${cms.path.base}")
	private String cmsRootPath;
	
	@Value("${migration.noallegati:false}")
	private boolean noAllegati;

	private List<Comuni_completo_cod_istat> comuniCompletoCodIstat;// li cache-izzo
	private List<EnteDTO> entiCommon;// li cache-izzo

	private static final Logger log = LoggerFactory.getLogger(MigratorService.class);
	private Map<String, Map<?, ?>> maps = null;
	private Long idOrgRegione = null;

	private class RiepilogoPraticaCivilia{
		boolean hasMetadatiRelazione;
		boolean hasAllegatiRelazione;
		boolean hasMailRelazione;
		boolean hasMetadatiParere;
		boolean hasAllegatiParere;
		boolean hasMailParere;
		boolean isPresentata;
		boolean hasRicevutaTrasmissione;
		@Override
		public String toString() {
			return "RiepilogoPraticaCivilia [hasMetadatiRelazione=" + hasMetadatiRelazione + ", hasAllegatiRelazione="
					+ hasAllegatiRelazione + ", hasMailRelazione=" + hasMailRelazione + ", hasMetadatiParere="
					+ hasMetadatiParere + ", hasAllegatiParere=" + hasAllegatiParere + ", hasMailParere="
					+ hasMailParere + ", isPresentata=" + isPresentata + ", hasRicevutaTrasmissione="
					+ hasRicevutaTrasmissione + "]";
		}
	}
	
	/**
	 * precarico tutti i mapping in una mappa di mappe
	 * 
	 * @author acolaianni
	 * @	throws MigrationException
	 *
	 */
	private void initMapping() throws MigrationException {
		if (maps != null)
			return;
		List<PlainNumberValueLabel> entiRegione = commonSvc.getEnteRegione();
		idOrgRegione = entiRegione.get(0).getValue();
		if (idOrgRegione == null) {
			throw new MigrationException("Organizzazione Regione non trovata !!!");
		}
		maps = new HashMap<>();
		Map<Integer, Integer> mapTipi = new HashMap<>();
//		AS-IS
//		1	AUTORIZZAZIONE PAESAGGISTICA art. 146, D.Lgs. 42/2004 - art. 90, NTA PPTR (ORDINARIA)
//		2	AUTORIZZAZIONE PAESAGGISTICA SEMPLIFICATA D.P.R. 139/2010 - art. 90, NTA PPTR (PER LE OPERE IL CUI IMPATTO PAESAGGISTICO E' VALUTATO MEDIANTE UNA DOCUMENTAZIONE SEMPLIFICATA)
//		3	ACCERTAMENTO DI COMPATIBILITA’ PAESAGGISTICA Artt. 167 e 181, D.Lgs. 42/2004 (PER OPERE IN DIFFORMITA’ O ASSENZA DI AUTORIZZAZIONE PAESAGGISTICA)
//		4	ACCERTAMENTO DI COMPATIBILITA'  PAESAGGISTICA art. 91, NTA PPTR  [IN VIGENZA D.P.R. 139/2010] (PER INTERVENTI CHE COMPORTINO MODIFICA DELLO STATO DEI LUOGHI NEGLI ULTERIORI CONTESTI COME INDIVIDUATI NELL’ART. 38 C. 3.1 NTA PPTR)
//		5	AUTORIZZAZIONE PAESAGGISTICA SEMPLIFICATA D.P.R. 31/2017 – ART 90, NTA PPTR PER INTERVENTI ED OPERE DI LIEVE ENTITA' SOGGETTI AL PROCEDIMENTO AUTORIZZATORIO SEMPLIFICATO A NORMA DELL' ART 146 c.9 DEL D.LGS. 42/2004
//		6	ACCERTAMENTO DI COMPATIBILITA'  PAESAGGISTICA art. 91, NTA PPTR [IN VIGENZA D.P.R. 31/2017] (PER INTERVENTI CHE COMPORTINO MODIFICA DELLO STATO DEI LUOGHI NEGLI ULTERIORI CONTESTI COME INDIVIDUATI NELL’ART. 38 C. 3.1 NTA PPTR)
//		TO-BE
//		1	AUT_PAES_ORD
//		2	AUT_PAES_SEMPL_DPR_31_2017
//		3	ACCERT_COMPAT_PAES_DLGS_42_2004
//		4	ACCERT_COMPAT_PAES_DPR_31_2017
//		5	ACCERT_COMPAT_PAES_DPR_139_2010
//		6	AUT_PAES_SEMPL_DPR_139_2010
		mapTipi.put(1, 1);
		mapTipi.put(2, 6);
		mapTipi.put(3, 3);
		mapTipi.put(4, 5);
		mapTipi.put(5, 2);
		mapTipi.put(6, 4);
		maps.put("tipoProcedimento", mapTipi);
//		AS-IS
//		CD	Compila Domanda
//		GS	Genera Stampa Domanda
//		AS	Allega Scansione Domanda
//		PI	PreIstruttoria
//		IS	Istruttoria
//		AF	Adempimenti Finali
		Map<String, String> mapFaseIstanzaStato = new HashMap<>();
		mapFaseIstanzaStato.put("CD", AttivitaDaEspletare.COMPILA_DOMANDA.name());
		mapFaseIstanzaStato.put("GS", AttivitaDaEspletare.GENERA_STAMPA_DOMANDA.name());
		mapFaseIstanzaStato.put("AS", AttivitaDaEspletare.ALLEGA_DOCUMENTI_SOTTOSCRITTI.name());
		mapFaseIstanzaStato.put("PI", AttivitaDaEspletare.IN_PREISTRUTTORIA.name());
		mapFaseIstanzaStato.put("IS", AttivitaDaEspletare.IN_LAVORAZIONE.name());
		mapFaseIstanzaStato.put("AF", AttivitaDaEspletare.IN_TRASMISSIONE.name());
		// in teoria potrebbe esseere già trasmessa se ObjectIDPubblicazione è non nullo
		maps.put("faseIstanzaStato", mapFaseIstanzaStato);
		Map<String, String> mapTitolaritaEsclusiva = new HashMap<>();
		//da loro in colonna è null o i due valori: 
		mapTitolaritaEsclusiva.put("titolarita_esclusiva", "S");
		mapTitolaritaEsclusiva.put("non_titolarita_esclusiva", "N");
		maps.put("titolaritaEsclusiva", mapTitolaritaEsclusiva);
		Map<Integer, Integer> mapDisclaimerTipoProc = new HashMap<>();
		mapDisclaimerTipoProc.put(1, 3);
		mapDisclaimerTipoProc.put(2, 4);
		mapDisclaimerTipoProc.put(3, 5);
		mapDisclaimerTipoProc.put(4, 2);
		mapDisclaimerTipoProc.put(5, 1);
		mapDisclaimerTipoProc.put(6, 16); //per il TR 
		mapDisclaimerTipoProc.put(7, 17);
		mapDisclaimerTipoProc.put(8, 18);
		mapDisclaimerTipoProc.put(9, 19);
		mapDisclaimerTipoProc.put(10, 20);
		mapDisclaimerTipoProc.put(11, 10);
		mapDisclaimerTipoProc.put(12, 11);
		mapDisclaimerTipoProc.put(13, 12);
		mapDisclaimerTipoProc.put(14, 21);
		mapDisclaimerTipoProc.put(15, 22);
		mapDisclaimerTipoProc.put(16, 23);
		mapDisclaimerTipoProc.put(17, 6);
		mapDisclaimerTipoProc.put(18, 9);
		mapDisclaimerTipoProc.put(19, 8);
		mapDisclaimerTipoProc.put(20, 7);
		mapDisclaimerTipoProc.put(21, 19);//???? da noi non esiste....
		mapDisclaimerTipoProc.put(22, 13);
		mapDisclaimerTipoProc.put(23, 15);
		mapDisclaimerTipoProc.put(24, 14);
		maps.put("disclaimerTipoProc", mapDisclaimerTipoProc);
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = false, transactionManager = DatabaseConfiguration.TX_WRITE)
	public void migraPratica(VtnoAttivitaPptr infoPratica) throws Exception {
		initMapping();
		RiepilogoPraticaCivilia riepPrat = new RiepilogoPraticaCivilia();
		PraticaDTO praticaToBe = new PraticaDTO();
		//Map<Long, Long> comuniInterventoMap = new HashMap<>();
		// init comuniCompleto...
		if (comuniCompletoCodIstat == null) {
			comuniCompletoCodIstat = praticaCivSvc.getComuniCompletoCodIstat();
		}
		if (entiCommon == null) {
			entiCommon = commonSvc.getAllEnti();
		}
		// cerco se già esiste
		PraticaDTO fascicoloPreesistente = null;
		try {
			fascicoloPreesistente = this.praticaDao.findByTPraticaId(infoPratica.gettPraticaId());
		} catch (EmptyResultDataAccessException e) {
		}
		if (fascicoloPreesistente != null) {
			throw new FascicoloPreesistenteException("Fascicolo con tpratica_id=" + infoPratica.gettPraticaId() + " preesistente!");
		}
		Long maxProgCittadino = this.praticaCivSvc.getMaxProgCittadino(infoPratica.gettPraticaCodice());
		if (maxProgCittadino == null) {
			throw new MigrationException("MaxProgrCittadino a null su tpratica_id=" + infoPratica.gettPraticaId() + "");
		}
		// mapping tipoProcedimento
		Integer newTipoProcedimento = (Integer) maps.get("tipoProcedimento").get(infoPratica.getTipoProcedimento().intValue());
		if (newTipoProcedimento == null) {
			throw new MigrationException("Fascicolo con tpratica_id=" + infoPratica.gettPraticaId()
					+ " tipo procedimento errato o non mappabile:" + infoPratica.getTipoProcedimento());
		}
		praticaToBe.setTipoProcedimento(newTipoProcedimento);
		if (!infoPratica.getEntedelegato().equalsIgnoreCase("00016!REGIONE PUGLIA")) {
			throw new MigrationException("Fascicolo con tpratica_id=" + infoPratica.gettPraticaId()
					+ " ente delegato non mappabile:" + infoPratica.getEntedelegato());
		}
		// setting del campo ente delegato
		praticaToBe.setEnteDelegato(idOrgRegione + "");
		// codice_pratica_appptr
		praticaToBe.setCodicePraticaAppptr(infoPratica.gettPraticaCodice());
		// username
		praticaToBe.setUserId(infoPratica.getJbpUname());
		praticaToBe.setOwner(infoPratica.getJbpUname());
		// oggetto
		praticaToBe.setOggetto(infoPratica.gettPraticaDescrizione());
		// sanatoria
		Long sanatoria = praticaCivSvc.getProvvedimentoSanatoria(infoPratica.gettPraticaCodice(), maxProgCittadino);
		if (sanatoria != null && sanatoria == 1) {
			praticaToBe.setInSanatoria(true);
		}
		praticaToBe.setTipoSelezioneLocalizzazione("PTC");
		praticaToBe.setStatoSedutaCommissione(StatoSeduta.VERBALE_NON_PREVISTO);
		TPratica tPratica = praticaCivSvc.getTPratica(infoPratica.gettPraticaCodice());
		if (tPratica == null) {
			throw new MigrationException(
					"Record T_PRATICA con CODICE=" + infoPratica.gettPraticaCodice() + " non trovato");
		}
		tPratica.getDataAttivazione();
		praticaToBe.setDataCreazione(tPratica.getDataAttivazione());
		//simulo che l'utente che ha inserito è un delegato
		praticaToBe.setRuoloPratica(RuoloPraticaEnum.DELEGATO.getCodice());
		
		praticaToBe.setDataModifica(tPratica.getDataAttivazione());
		praticaToBe.setStato(AttivitaDaEspletare.COMPILA_DOMANDA);// temporaneo.. per la creazione, poi lo aggiorno...
		praticaToBe.settPraticaId(infoPratica.gettPraticaId());
		this.fascicoloSvc.creaNuovaPraticaDaMigrazione(praticaToBe);// viene assegnato l'id
		PraticaDelegatoDTO praticaDelegato=new PraticaDelegatoDTO();
		praticaDelegato.setId(praticaToBe.getId());
		praticaDelegato.setIndice((short)1);
		praticaDelegato.setNome(praticaToBe.getUserId());
		praticaDelegato.setCognome(praticaToBe.getUserId());
		praticaDelegatoDao.insert(praticaDelegato);
		
		praticaToBe.setCodiceTrasmissione(infoPratica.getCodicePraticaEnteDelegato());
		praticaToBe.setValidazioneAllegati(true);
		praticaToBe.setValidazioneIstanza(true);
		praticaToBe.setValidazioneSchedaTecnica(true);
		praticaToBe.setHasShape(false);
		// PROTOCOLLO ISTANZA
		PptrProtocolloIstanza protoIstanza = praticaCivSvc.getPptrProtocolloIstanza(infoPratica.gettPraticaCodice(),
				maxProgCittadino);
		if (protoIstanza != null) {
			riepPrat.isPresentata=true;
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			praticaToBe.setNumeroProtocollo(protoIstanza.getTitolarioProtocollo() + "/"
					+ sdf.format(protoIstanza.getDataProtocollo()) + "/" + protoIstanza.getNumeroProtocollo());
			praticaToBe.setDataProtocollo(new Timestamp(protoIstanza.getDataProtocollo().getTime()));
			if (protoIstanza.getDataInvioPec() != null) {
				praticaToBe.setDataPresentazione(new Timestamp(protoIstanza.getDataInvioPec().getTime()));
			}
			praticaToBe.setStato(AttivitaDaEspletare.IN_LAVORAZIONE);
			praticaDao.updateDataPresentazione(praticaToBe.getId(), praticaToBe.getDataPresentazione());
			praticaDao.updateWithoutCheck(praticaToBe);
			praticaDao.validazioneRichiedente(praticaToBe.getCodicePraticaAppptr());
		}
		// aggiorno
		fascicoloSvc.updatePraticaPerMigrazione(praticaToBe);
		// dati istanza
		// pannello richiedente tecnico incaricato e altri titolari
		//FascicoloDto fascicolo = this.fascicoloSvc.findByIdTxWriteWithoutCheck(praticaToBe.getId());
		List<PptrReferentiProgetto> referenti = this.praticaCivSvc
				.getReferenti(infoPratica.gettPraticaId(), Long.valueOf(maxProgCittadino));
		//richiedente altrititolari e tecnico
		migraReferenti(infoPratica, praticaToBe, maxProgCittadino, referenti);
		//pannello dichiarazioni
		List<PptrAltreDichiarRich> altreDichRich = this.praticaCivSvc.getAltreDichRich(infoPratica.gettPraticaId());
		if(ListUtil.isNotEmpty(altreDichRich)) {
			scriviAltreDichRichiedente(praticaToBe, altreDichRich);
		}else if(praticaToBe.getTipoProcedimento() == 2){
			//se non ci sono le inserisco io per il tipo procedimento 2
			AltreDichiarazioniRichDTO dichSpecifiche = new AltreDichiarazioniRichDTO();
			dichSpecifiche.setPraticaId(praticaToBe.getId());
			altreDichDao.delete(dichSpecifiche);
			altreDichDao.insert(dichSpecifiche);
		}
		// localizzazione
		List<AutPaesPptrLocalizInterv> locIntervs = this.praticaCivSvc
				.getLocalizzazioneIntervento(infoPratica.gettPraticaCodice(), maxProgCittadino);
		List<ComuniCompetenzaDTO> comuniCompetenzaPratica = this.comuniCompetenzaDao.selectByPratica(praticaToBe.getId(), true);
		List<Long> comuniLocalizzazioneIntervento=new ArrayList<>();
		// creo i comuni dell'intervento
		for (AutPaesPptrLocalizInterv locIntervOld : locIntervs) {
			migraLocalizzazioneIntervento(infoPratica, praticaToBe, maxProgCittadino, comuniCompetenzaPratica,
					comuniLocalizzazioneIntervento, locIntervOld);
		}
		//scheda tecnica....
		//pannello descrizione
		migraDescrizioneIntervento(infoPratica, riepPrat, praticaToBe, maxProgCittadino);
		//pannello destinazione urbanistica legato a localizzazione intervento
		int counter=0;
		for(AutPaesPptrLocalizInterv locInterv:locIntervs) {
			counter = migraDestinazioneUrbanisticaComune(infoPratica, praticaToBe, maxProgCittadino,
					comuniCompetenzaPratica, counter, locInterv);
		}
		//leggittimita urbanistica
		List<PptrLegittProvved> leggUrbProvCivs = migraLeggittimita(infoPratica, praticaToBe, maxProgCittadino);
		//procedure edilizie
		migraProcedureEdil(infoPratica, praticaToBe, maxProgCittadino);
		//inquadramento  rispetto al nostro ci sono colonne in piu' usate per il loro tipoProcedimento=2 che per ora non usiamo
		//TODO verificare su dump che ci invieranno
		//INQ_OPERA_CORRELATA_A INQ_CARATTERE_INTERVENTO INQ_USO_SUOLO
		migraInquadramento(infoPratica, praticaToBe, maxProgCittadino);
		//Effetti cons.Mitigazione
		migraEffettiConsMitigazione(infoPratica, praticaToBe, maxProgCittadino);
		//procedimenti contenzioso
		migraProcContenz(infoPratica, praticaToBe, maxProgCittadino);
		//Pareri Atti
		migraPareriAtti(infoPratica, praticaToBe, maxProgCittadino);
		//PPTR
		migraPptr(infoPratica, praticaToBe, maxProgCittadino); 
		//Vincolistica
		migraVincolistica(infoPratica, praticaToBe, maxProgCittadino);
		//Allegati
		//documentazione amministrativa
		List<DocAmmVPratiche> allegatoAmms = this.praticaCivSvc.getDocumentazioneAmministrativa(infoPratica.gettPraticaCodice(), maxProgCittadino);
		if(ListUtil.isNotEmpty(allegatoAmms)) {
			for(DocAmmVPratiche allegato:allegatoAmms) {
				if(noAllegati) break;
				MockMultipartFile file = new MockMultipartFile(this.parsedFilename(allegato.getNomeFile()), allegato.getBinContent().getBinaryStream());
				file.setContentType(allegato.getFormatoFile());
				Integer tipo=AutpaeDtoBuilder.getTipoDocAmm(allegato.getTipoDocumento());
				AllegatiDTO all = this.allegatoSvc.uploadAllegatoDaMigrazione(file, Collections.singletonList(tipo), allegato.getTipoDocumento(), praticaToBe.getCodicePraticaAppptr(), praticaToBe.getId(), null, null);
			}
		}
		//documentazione tecnica
		List<VtnoAllegatiPptr> allegatiTecns = this.praticaCivSvc.getDocumentazioneTecnica(infoPratica.gettPraticaCodice(), maxProgCittadino);
		if(ListUtil.isNotEmpty(allegatiTecns )) {
			Map<String, List<VtnoAllegatiPptr>> mappaContenuti = VtnoAllegatiPptr.getMapAllegatiFacoltativi_byKey_tnTKeDocStId(allegatiTecns);
			//sono raggruppati per file
			Set<String> files = mappaContenuti.keySet();
			for(String file:files)
			{
				if(noAllegati) break;
				List<VtnoAllegatiPptr> listaContenuti = mappaContenuti.get(file);
				List<Integer> tipi=AutpaeDtoBuilder.getTipiDocTecnica(listaContenuti, praticaToBe.getTipoProcedimento());
				if(ListUtil.isNotEmpty(tipi)) {
					VtnoAllegatiPptr fileData = listaContenuti.get(0);
					try {
						MockMultipartFile filemock = buildMultipart(fileData);
						filemock.setContentType(fileData.gettKeDocAttrValue());
						AllegatiDTO ret = this.allegatoSvc.uploadAllegatoDaMigrazione(filemock, tipi, 
								listaContenuti.get(0).getNomeAllegato(), praticaToBe.getCodicePraticaAppptr(), praticaToBe.getId(), null, null);
						ret.setDataCaricamento(fileData.getDataArrivo());
						allegatiDao.update(ret);
					} catch (Exception e) {
						throw new MigrationException("Errore durante l'elaborazione del'allegato " + tipi + " " +fileData.getNomeFile()+" allegato:"+fileData, e);
					}	
				}
			}
		}
		// pdf istanza e scheda tecnica
		// check presenza istanze firmate in caso di pratica avanzata
		// se trovo istanza e scheda tecnica firmate allora per me la pratica è valida.
		PptrIstanzaStampe pptrIstanzaStampe = this.praticaCivSvc.getPptrIstanzaStampe(infoPratica.gettPraticaCodice(),
				maxProgCittadino);
		if (!AttivitaDaEspletare.getStatiPresentazioneIstanza().contains(praticaToBe.getStato())
				&& 
				(pptrIstanzaStampe == null || 
				pptrIstanzaStampe.getDichiarazioneTecnicaS() == null || 
				pptrIstanzaStampe.getIstanzaS() == null || 
				pptrIstanzaStampe.getDichiarazioneTecnicaS().length() == 0|| 
				pptrIstanzaStampe.getIstanzaS().length() == 0)) {
			throw new MigrationException("Record T_PRATICA con CODICE=" + infoPratica.gettPraticaCodice() + " in stato "
					+ praticaToBe.getStato() + " e senza documenti sottoscritti");
		}
		if(pptrIstanzaStampe!=null) {
			if(praticaToBe.getStato().ordinal()<AttivitaDaEspletare.GENERA_STAMPA_DOMANDA.ordinal()) {
				praticaToBe.setStato(AttivitaDaEspletare.GENERA_STAMPA_DOMANDA);
				praticaDao.updateWithoutCheck(praticaToBe);
			}
			dichiarazDao.setDichiarazSchTecAccettata(praticaToBe.getId(),true);
			praticaToBe.setValidazioneAllegati(true);
			praticaToBe.setValidazioneIstanza(true);
			praticaToBe.setValidazioneSchedaTecnica(true);
			praticaDao.updateWithoutCheck(praticaToBe);
			final String pathCmsCodPrat=praticaToBe.getCodicePraticaAppptr();
			final UUID idPratica=praticaToBe.getId();
			final String mimePDF=AllowedMimeType.PDF.getMimeType();
			if(pptrIstanzaStampe.getIstanza()!=null) {
				this.uploadIS("Istanza.pdf",pptrIstanzaStampe.getIstanza(),
						mimePDF,750,idPratica,pathCmsCodPrat,"istanza originale",praticaToBe.getDataProtocollo());	
			}if(pptrIstanzaStampe.getDichiarazioneTecnica()!=null) {
				this.uploadIS("DichiarazioneTecnica.pdf",pptrIstanzaStampe.getDichiarazioneTecnica(),
						mimePDF,751,idPratica,pathCmsCodPrat,"Dichiarazione tecnica originale",praticaToBe.getDataProtocollo());				
			}
			if(pptrIstanzaStampe.getIstanzaS()!=null) {
				this.uploadIS(this.parsedFilename(pptrIstanzaStampe.getIstanzaSname()),pptrIstanzaStampe.getIstanzaS(),
						mimePDF,700,idPratica,pathCmsCodPrat,"istanza",praticaToBe.getDataProtocollo());	
			}
			if(pptrIstanzaStampe.getDichiarazioneTecnicaS()!=null) {
				this.uploadIS(this.parsedFilename(pptrIstanzaStampe.getDichiarazioneTecnicaSname()),pptrIstanzaStampe.getDichiarazioneTecnicaS(),
						mimePDF,701,idPratica,pathCmsCodPrat,"Dichiarazione tecnica",praticaToBe.getDataProtocollo());	
			}
		}
		//parte istruttoria
		//allegati relazione ente
		 List<VtnoAllegatiPptr> allRelEntes = this.praticaCivSvc.getAllegatiRelazioneEnte(infoPratica.gettPraticaCodice(),maxProgCittadino);
		 List<UUID> allegatiRelazioneCaricati=new ArrayList<>();
		 if(ListUtil.isNotEmpty(allRelEntes)) {
			 riepPrat.hasAllegatiRelazione=true;
			 for(VtnoAllegatiPptr allegatoRelazione:allRelEntes) {
				 	 //Relazione tecnico illustrativa o proposta di provvedimento o proposta di accoglimento, 
					 //insomma dipende dal tipo procedimento, mentre a noi è fissa a 2 tipologie
					  AllegatiDTO allRelazione = this.uploadIS(this.parsedFilename(allegatoRelazione.getNomeFile()),allegatoRelazione.getBinContent(),
						allegatoRelazione.gettKeDocAttrValue(),
						allegatoRelazione.getProvv().equalsIgnoreCase("RI_PA")?1000:1001,
						praticaToBe.getId(),
						praticaToBe.getCodicePraticaAppptr(),allegatoRelazione.getPptrTipoAllegatoDescrizione(),
						allegatoRelazione.getDataArrivo(),allegatoRelazione.getIdToDownloadFromAlfresco());
					  allegatiRelazioneCaricati.add(allRelazione.getId());
					  if(allegatoRelazione.getDataArrivo()!=null) {
						  //setto questa come datainiziolavorazione.... TODO verificare se si puo' prendere una piu' precisa
						  praticaToBe.setDataInizioLavorazione(new Timestamp(allegatoRelazione.getDataArrivo().getTime()));
						  praticaDao.updateWithoutCheck(praticaToBe);
					  }
			 }
		 }
		//relazione Ente
		List<PptrRelazioneEnte> relazioneEntes = this.praticaCivSvc.getRelazioneEnte(infoPratica.gettPraticaCodice(),maxProgCittadino);
		RelazioneEnteDTO relazioneNew=new RelazioneEnteDTO();
		if(ListUtil.isNotEmpty(relazioneEntes)) {
			riepPrat.hasMetadatiRelazione=true;
		}
		if(ListUtil.isNotEmpty(relazioneEntes) || ListUtil.isNotEmpty(allRelEntes)) {
			PptrRelazioneEnte relazioneEnteOld = relazioneEntes.get(0);
			relazioneNew.setIdPratica(praticaToBe.getId());
			relazioneNew.setDettaglioRelazione(relazioneEnteOld.getDettaglioRelazione());
			//relazioneEnteOld.getFlagEsito();
			relazioneNew.setNominativoIstruttore(relazioneEnteOld.getNomeIstruttoreEnte());
			relazioneNew.setNotaInterna(relazioneEnteOld.getNoteEnte());
			relazioneNew.setNumeroProtocolloEnte(relazioneEnteOld.getNumeroProtocolloEnte());
			long idRelazioneEnte = relazioneEnteDao.insert(relazioneNew);
			relazioneNew.setIdRelazioneEnte(idRelazioneEnte);
			//in teoria se ci sono già gli allegati devo caricarli
			for(UUID allegatoCaricato:allegatiRelazioneCaricati) {
				relazioneEnteDao.insertAllegatoRelazioneEnte(allegatoCaricato,idRelazioneEnte);
			}
			praticaToBe.setStatoRelazioneEnte(StatoRelazione.RELAZIONE_NON_TRASMESSA);
			praticaDao.updateWithoutCheck(praticaToBe);
		}
		//allegati parere SOP
		 List<VtnoAllegatiPptr> allSops = this.praticaCivSvc.getListParereSopVtnoAllegatiPptr(infoPratica.gettPraticaCodice());
		 List<UUID> allegatiSopCaricati=new ArrayList<>();
		 if(ListUtil.isEmpty(allSops)) {
			//provo a prelevare eventuali caricati da Ente delegato
			allSops=this.praticaCivSvc.getListParereSopDaEnteDelegato(infoPratica.gettPraticaCodice(),maxProgCittadino);
		 }
		 if(ListUtil.isNotEmpty(allSops)) {
			 riepPrat.hasAllegatiParere=true;
			 //caricati da Soprintendenza....
			 for(VtnoAllegatiPptr allSop:allSops) {
				 	 //Relazione tecnico illustrativa o proposta di provvedimento o proposta di accoglimento, 
					 //insomma dipende dal tipo procedimento, mentre a noi è fissa a 2 tipologie
					  AllegatiDTO all = this.uploadIS(this.parsedFilename(allSop.getNomeFile()),allSop.getBinContent(),
						allSop.gettKeDocAttrValue(),
						904, //parere mibac pubblico
						praticaToBe.getId(),
						praticaToBe.getCodicePraticaAppptr(),allSop.getPptrTipoAllegatoDescrizione(),
						allSop.getDataArrivo(),allSop.getIdToDownloadFromAlfresco());
					  allegatiSopCaricati.add(all.getId());
			 }
		 }
		//migrazione parere SOP
		List<PptrParereSop> parereSops = this.praticaCivSvc.getParereSOP(infoPratica.gettPraticaCodice(),maxProgCittadino);
		ParereSoprintendenzaDTO parereSopNew=new ParereSoprintendenzaDTO();
		parereSopNew.setIdPratica(praticaToBe.getId());
		if(ListUtil.isNotEmpty(parereSops)){
			riepPrat.hasMetadatiParere=true;
			PptrParereSop parereSop = parereSops.get(0);
			parereSopNew.setNumeroProtocollo(parereSop.getNumeroProtocolloSop());
			parereSopNew.setNominativoIstruttore(parereSop.getNomeIstruttoreSop());
			parereSopNew.setNote(parereSop.getNoteParereSop());
			parereSopNew.setDettaglioPrescrizione(parereSop.getDettaglioPrescrizioni());
			parereSopNew.setEsitoParere(EsitoParere.fromCiviliaValue(parereSop.getFlagEsito()));
			if(parereSop.getFlag_provenienza()==1) {//ente delegato
				parereSopNew.setOrganizzazioneCreazione(Long.parseLong(praticaToBe.getEnteDelegato()));
				praticaToBe.setStatoParereSoprintendenza(StatoParere.PARERE_INSERITO_ENTE);
			}else {
				//è di soprintendenza
				Long idEnteComune=comuniLocalizzazioneIntervento.get(0);
				try {
					Long idOrgSop=commonSvc.getIdSoprintendenzaByComuneId(idEnteComune, new Date());
					parereSopNew.setOrganizzazioneCreazione(idOrgSop);
				}catch(Exception e) {
					
					throw new MigrationException("Errore nel retrieval della soprintendenza dall'id comune:" +idEnteComune);
				}
				praticaToBe.setStatoParereSoprintendenza(StatoParere.PARERE_IN_BOZZA_SOPRINTENDENZA);
			}
			praticaDao.updateWithoutCheck(praticaToBe);
			long idParereSop = parereSopDao.insert(parereSopNew);
			parereSopNew.setId(idParereSop);
			for(UUID allegatoCaricato:allegatiSopCaricati) {
				parereSopDao.insertParereAllegato(idParereSop,allegatoCaricato);
			}
		}
		//migrazione mail non riservate (civiliamail)
		this.migraMailCivilia(praticaToBe.getId(),infoPratica.gettPraticaId(),infoPratica.gettPraticaCodice(),maxProgCittadino);
		/**
		 * in questa lista mantengo gli id delle mail già migrate in modo da escluderle nello step di migrazione di tutte le mail in 
		 * TNO_PPTR_MAIL_INVIATE
		 */
		List<Long> pptrMailInviateMigrate=new ArrayList<Long>();
		//migrazione mail specifiche riservate
		this.migraMailRelazioneEnte(praticaToBe.getId(),infoPratica.gettPraticaId(),infoPratica.gettPraticaCodice(),maxProgCittadino,praticaToBe,relazioneNew,pptrMailInviateMigrate,riepPrat);
		//migrazione mail parere sop se immesso da soprintendenza. 
		this.migraMailParereSop(praticaToBe.getId(),infoPratica.gettPraticaId(),infoPratica.gettPraticaCodice(),maxProgCittadino,praticaToBe,parereSopNew,pptrMailInviateMigrate,riepPrat);
		//in teoria se lo immette l'ED non viene inviata alcuna mail
		//pannello ulteriore documentazione
		this.migraUlterioreDocumentazione(praticaToBe.getId(),infoPratica.gettPraticaId(),infoPratica.gettPraticaCodice(),maxProgCittadino);
		//pannello comunicazioni
		this.migraMailInterne(praticaToBe.getId(),infoPratica.gettPraticaId(),infoPratica.gettPraticaCodice(),maxProgCittadino,praticaToBe,pptrMailInviateMigrate);
		//provvedimento finale
		this.migraProvvedimento(praticaToBe.getId(),infoPratica.gettPraticaId(),infoPratica.gettPraticaCodice(),maxProgCittadino,praticaToBe,riepPrat);
		// stato  qui metto lo stato avanzato effettivo 
		String faseIstanza = infoPratica.getPptrFaseIstanzaCodice();
		AttivitaDaEspletare statoSecondoCivilia=AttivitaDaEspletare.valueOf((String) maps.get("faseIstanzaStato").get(faseIstanza));
		if(statoSecondoCivilia.ordinal()<praticaToBe.getStato().ordinal()) {
			praticaToBe.setStato(statoSecondoCivilia);
			praticaDao.updateWithoutCheck(praticaToBe);
			if(statoSecondoCivilia.ordinal()>=AttivitaDaEspletare.IN_ATTESA_DI_PROTOCOLLAZIONE.ordinal()) {
				praticaDao.validazioneRichiedente(praticaToBe.getCodicePraticaAppptr());	
			}
		}
		if (infoPratica.getObjectIdPubblicazione() != null) {
			praticaToBe.setStato(AttivitaDaEspletare.TRANSMITTED);
			if(riepPrat.hasAllegatiRelazione && riepPrat.hasMetadatiRelazione && 
					praticaToBe.getStatoRelazioneEnte().ordinal()<
					StatoRelazione.RELAZIONE_TRASMESSA_SENZA_AVVIO.ordinal()) {
				praticaToBe.setStatoRelazioneEnte(StatoRelazione.RELAZIONE_TRASMESSA_SENZA_AVVIO);
			}
			if(riepPrat.hasAllegatiParere && riepPrat.hasMetadatiParere && 
					praticaToBe.getStatoParereSoprintendenza().ordinal()<
					StatoParere.PARERE_IN_BOZZA_SOPRINTENDENZA.ordinal()) {
				praticaToBe.setStatoParereSoprintendenza(StatoParere.PARERE_INSERITO_SOPRINTENDENZA);
			}
			praticaDao.updateWithoutCheck(praticaToBe);
		}
		log.info(LOG_MIGRAZIONE_MARKER,
				"riepilogo pratica codice {} : {}", infoPratica.getCodicePraticaEnteDelegato(), riepPrat);
	}

	private MockMultipartFile buildMultipart(VtnoAllegatiPptr fileData)
			throws IOException, MigrationException, SQLException {
		MockMultipartFile filemock =null;
		if(StringUtil.isNotBlank(fileData.getIdToDownloadFromAlfresco())) {
			//prelevo da alfresco e poggio su file temporaneo
			File file=proxyAlfrescoOrigin.getDocumentIntoLocalFile(fileData.getIdToDownloadFromAlfresco(), null);
			log.info(IMigratorService.LOG_MIGRAZIONE_MARKER,"Downloaded from alfresco {}  size {}",fileData.getNomeFile(),file.length());
			filemock=new MockMultipartFile(this.parsedFilename(fileData.getNomeFile()), file);
		}else {
			filemock = new MockMultipartFile(this.parsedFilename(fileData.getNomeFile()), fileData.getBinContent().getBinaryStream());	
		}
		
		return filemock;
	}

	private void migraVincolistica(VtnoAttivitaPptr infoPratica, PraticaDTO praticaToBe, Long maxProgCittadino) {
		List<PptrVincoliArchArch> pptrVincs = this.praticaCivSvc.getPptrVincolistica(infoPratica.gettPraticaCodice(), maxProgCittadino);
		VincArchDTO vincnew=new VincArchDTO();
		vincnew.setPraticaId(praticaToBe.getId());
		if(ListUtil.isNotEmpty(pptrVincs)){
			PptrVincoliArchArch pptrVincOld = pptrVincs.get(0);
			vincnew.setAltriVincoli(pptrVincOld.getAltriVincoli());
			vincnew.setVincArcArcheoDiretto(pptrVincOld.getVincArcArcheoDiretto());
			vincnew.setVincArcArcheoIndiretto(pptrVincOld.getVincArcArcheoIndirett());
			vincnew.setVincArcMonumDiretto(pptrVincOld.getVincArcMonumDiretto());
			vincnew.setVincArcMonumIndiretto(pptrVincOld.getVincArcMonumIndirett());
			vincnew.setVincArcNoTutela(pptrVincOld.getVincArcNoTutuela());
		}
		vincArchDao.insert(vincnew);
	}

	private void migraPptr(VtnoAttivitaPptr infoPratica, PraticaDTO praticaToBe, Long maxProgCittadino) {
		List<PptrApprovato> pptrApprs = this.praticaCivSvc.getPptrApprovato(infoPratica.gettPraticaCodice(), maxProgCittadino);
		PptrApprovatoDTO pptrApprnew=new PptrApprovatoDTO();
		pptrApprnew.setPraticaId(praticaToBe.getId());
		if(ListUtil.isNotEmpty(pptrApprs )) {
			PptrApprovato pptrApprOld = pptrApprs.get(0);
			pptrApprnew.setAmbitoPaesaggistico(pptrApprOld.getAmbitoPaesaggistico());
			pptrApprnew.setFigureAmbito(pptrApprOld.getFigureAmbitoPaesaggistico());
			if(pptrApprOld.getRicadeTerrArt103Co56()!=null) {
				pptrApprnew.setRicadeTerrArt103Co56(pptrApprOld.getRicadeTerrArt103Co56().equalsIgnoreCase("S"));	
			}
			if(pptrApprOld.getRicadeTerrArt142Co2()!=null) {
				pptrApprnew.setRicadeTerrArt142Co2(pptrApprOld.getRicadeTerrArt142Co2().equalsIgnoreCase("S"));	
			}
			
		}
		pptrApprovatoDao.insert(pptrApprnew);
		//PPTR TABELLA 
		List<PptrStrutAntroStorCult> pptrSats = this.praticaCivSvc.getPptrAntroStorCult(infoPratica.gettPraticaCodice(), maxProgCittadino);
		List<PptrStrutEcosistemica> pptrEcos = this.praticaCivSvc.getPptrEcosistemica(infoPratica.gettPraticaCodice(), maxProgCittadino);
		List<PptrStrutIdrogeomorf> pptrIgms = this.praticaCivSvc.getPptrIdrogeomorf(infoPratica.gettPraticaCodice(), maxProgCittadino);
		List<PptrSelezioniDTO> pptrSelezioni=AutpaeDtoBuilder.buildPptrSelezionis(pptrSats,pptrEcos,pptrIgms,praticaToBe.getId());
		for(PptrSelezioniDTO entity:pptrSelezioni) {
			pptrSelezioniDao.insert(entity);
		}
	}

	private void migraPareriAtti(VtnoAttivitaPptr infoPratica, PraticaDTO praticaToBe, Long maxProgCittadino) {
		int counter;
		List<PptrAttiAcquisiti> attis = this.praticaCivSvc.getPareriAtti(infoPratica.gettPraticaCodice(), maxProgCittadino);
		if(ListUtil.isNotEmpty(attis)) {
			counter=0;
			for(PptrAttiAcquisiti attoOld:attis) {
				PareriEAttiAssensoDTO pareriAttiNew=new PareriEAttiAssensoDTO();
				counter++;
				pareriAttiNew.setId(counter);
				pareriAttiNew.setPraticaId(praticaToBe.getId());
				pareriAttiNew.setAutoritaRilascio(attoOld.getAutoritaRilascio());
				pareriAttiNew.setDataRilascio(attoOld.getDataRilascio());
				pareriAttiNew.setFlagAttoParere(attoOld.getFlagAttoParere());
				pareriAttiNew.setIntestatarioAtto(attoOld.getIntestatario_atto());
				pareriAttiNew.setProtocollo(attoOld.getProtocollo());
				pareriAttiNew.setTipologiaAtto(attoOld.getTipologiaAtto());
				pareriAttiDao.insert(pareriAttiNew);
			}
		}
	}

	private void migraProcContenz(VtnoAttivitaPptr infoPratica, PraticaDTO praticaToBe, Long maxProgCittadino) {
		ProcedimentiContenziosoDTO procNew=new ProcedimentiContenziosoDTO();
		List<PptrProcedimentiContenzioso> procs = this.praticaCivSvc.getProcedimentiContenzioso(infoPratica.gettPraticaCodice(), maxProgCittadino);
		if(ListUtil.isNotEmpty(procs)) {
			PptrProcedimentiContenzioso oldProc = procs.get(0);
			procNew.setFlagContenziosoInAtto(oldProc.getFlagContenziosoInAtto());
			procNew.setDescrizione(oldProc.getDescrizione());
		}
		procNew.setPraticaId(praticaToBe.getId());
		procContDao.insert(procNew);
	}

	private void migraEffettiConsMitigazione(VtnoAttivitaPptr infoPratica, PraticaDTO praticaToBe,
			Long maxProgCittadino) {
		List<PptrStatoEffMitigaz> effMits = this.praticaCivSvc.getStatoEffmitigazione(infoPratica.gettPraticaCodice(), maxProgCittadino);
		if(ListUtil.isNotEmpty(effMits)) {
			PptrStatoEffMitigaz effOld = effMits.get(0);
			EffettiMitigazioneDTO  effNew=new EffettiMitigazioneDTO();
			effNew.setPraticaId(praticaToBe.getId());
			effNew.setDescrStatoAttuale(effOld.getDescrStatoAttuale());
			effNew.setEffettiRealizOpera(effOld.getEffettiRealizOpera());
			effNew.setIndicazContenutiPercettivi(effOld.getIndicazContenutiPercettivi());
			effNew.setMitigazioneImpInterv(effOld.getMitigazioneImpInterv());
			effMitDao.insert(effNew);
		}
	}

	private void migraInquadramento(VtnoAttivitaPptr infoPratica, PraticaDTO praticaToBe, Long maxProgCittadino) {
		List<PptrInquadramentoIntervento> inqIntervs = this.praticaCivSvc.getInquadramentoIntervento(infoPratica.gettPraticaCodice(), maxProgCittadino);
		if(ListUtil.isNotEmpty(inqIntervs)) {
			PptrInquadramentoIntervento inqOld = inqIntervs.get(0);
			InquadramentoDTO inqNew=new InquadramentoDTO();
			inqNew.setPraticaId(praticaToBe.getId());
			AutpaeDtoBuilder.convertiInquadramento(inqOld, inqNew);
			inquadramentoDao.insert(inqNew);
		}
	}

	private void migraProcedureEdil(VtnoAttivitaPptr infoPratica, PraticaDTO praticaToBe, Long maxProgCittadino) {
		List<PptrAssoggProcedEdil> procEdilCivs = this.praticaCivSvc.getProcedureEdilizie(infoPratica.gettPraticaCodice(), maxProgCittadino);
		AssoggProcedureEdilizieDTO procEdilNew=new AssoggProcedureEdilizieDTO();
		procEdilNew.setPraticaId(praticaToBe.getId());
		if(ListUtil.isNotEmpty(procEdilCivs)) {
			PptrAssoggProcedEdil procEdilCiv = procEdilCivs.get(0);
			procEdilNew.setAssoggDataApprovPratica(procEdilCiv.getAssoggDataApprovPratica());
			procEdilNew.setAssoggDataprPraticaInCorso(procEdilCiv.getAssoggDataprPraticaInCorso());
			procEdilNew.setAssoggEntePraticaInCorso(procEdilCiv.getAssoggEntePraticaInCorso());
			procEdilNew.setAssoggFlagParereUrbEspr(procEdilCiv.getAssoggFlagParereUrbEspr());
			procEdilNew.setAssoggFlagPraticaInCorso(procEdilCiv.getAssoggFlagPraticaInCorso());
			procEdilNew.setFlagAssoggettato(procEdilCiv.getFlagAssoggettato());
			procEdilNew.setNoAssoggSpecificare(procEdilCiv.getNoAssoggSpecificare());
		}
		procEdilizieDao.insert(procEdilNew);
	}

	private List<PptrLegittProvved> migraLeggittimita(VtnoAttivitaPptr infoPratica, PraticaDTO praticaToBe,
			Long maxProgCittadino) {
		int counter;
		List<PptrLegittimita> leggUrbCivs = this.praticaCivSvc.getLeggittimita(infoPratica.gettPraticaCodice(), maxProgCittadino);
		LeggittimitaDTO leggUrbNew=new LeggittimitaDTO();
		leggUrbNew.setPraticaId(praticaToBe.getId());
		if(ListUtil.isNotEmpty(leggUrbCivs)){
			PptrLegittimita leggUrbCiv = leggUrbCivs.get(0);
			leggUrbNew.setLegPaeDataIntervento(leggUrbCiv.getLegPaeDataIntervento());
			leggUrbNew.setLegPaeDataVincolo(leggUrbCiv.getLegPaeDataVincolo());
			leggUrbNew.setLegPaesagImmobile(leggUrbCiv.getLegPaesagImmobile());
			leggUrbNew.setLegPaeTipoVincolo(leggUrbCiv.getLegPaeTipoVincolo());
			leggUrbNew.setLegUrbPrivoSpecifica(leggUrbCiv.getLegUrbPrivoSpecifica());
			leggUrbNew.setLegUrbTitEdilizio(leggUrbCiv.getLegUrbTitEdilizio());
		}
		this.leggUrbDao.insert(leggUrbNew);
		//leggittimita urbanistica titoli edilizi
		List<PptrLegittTitoli> leggUrbTitCivs = this.praticaCivSvc.getLeggittimitaTitoli(infoPratica.gettPraticaCodice(), maxProgCittadino);
		if(ListUtil.isNotEmpty(leggUrbTitCivs)) {
			counter=1;
			for(PptrLegittTitoli titolo:leggUrbTitCivs) {
				LeggittimitaTitoliDTO titoloNew= new LeggittimitaTitoliDTO();
				titoloNew.setPraticaId(praticaToBe.getId());
				titoloNew.setId(counter++);
				titoloNew.setLegTitDataRilascio(titolo.getLegTitDataRilascio());
				titoloNew.setLegTitDenominazione(titolo.getLegTitDenominazione());
				titoloNew.setLegTitIntestatario(titolo.getLegTitIntestatario());
				titoloNew.setLegTitNumProtocollo(titolo.getLegTitNumProtocollo());
				titoloNew.setLegTitRilasciatoDa(titolo.getLegTitRilasciatoDa());
				this.leggUrbTitDao.insert(titoloNew);
			}
		}
		//leggittimita urbanistica provvedimenti paesaggistici
		List<PptrLegittProvved> leggUrbProvCivs = this.praticaCivSvc.getLeggittimitaProvv(infoPratica.gettPraticaCodice(), maxProgCittadino);
		if(ListUtil.isNotEmpty(leggUrbProvCivs)) {
			counter=1;
			for(PptrLegittProvved provv:leggUrbProvCivs) {
				LeggittimitaProvvedimentiDTO provvNew= new LeggittimitaProvvedimentiDTO();
				provvNew.setPraticaId(praticaToBe.getId());
				provvNew.setId(counter++);
				provvNew.setLegProvvDataRilascio(provv.getLegProvvDataRilascio());
				provvNew.setLegProvvDenominazione(provv.getLegProvvDenominazione());
				provvNew.setLegProvvIntestatario(provv.getLegProvvIntestatario());
				provvNew.setLegProvvNumProtocollo(provv.getLegProvvNumProtocollo());
				provvNew.setLegProvvRilasciatoDa(provv.getLegProvvRilasciatoDa());
				this.leggUrbProvvDao.insert(provvNew);
			}
		}
		return leggUrbProvCivs;
	}

	private int migraDestinazioneUrbanisticaComune(VtnoAttivitaPptr infoPratica, PraticaDTO praticaToBe,
			Long maxProgCittadino, List<ComuniCompetenzaDTO> comuniCompetenzaPratica, int counter,
			AutPaesPptrLocalizInterv locInterv) throws MigrationException {
		//infoPratica.gettPraticaId(), maxProgCittadino
		List<PptrDestUrbInterv> destUrbans = praticaCivSvc.getDestinazioneUrbanistica(locInterv.getLocalizIntervId());
		if(!ListUtil.isNotEmpty(destUrbans)) {
			//provo vecchio modo
			destUrbans = praticaCivSvc.getDestinazioneUrbanisticaOldMode(infoPratica.gettPraticaCodice(), maxProgCittadino);
		}
		PptrDestUrbInterv oldDestUrb=new PptrDestUrbInterv();
		if(ListUtil.isNotEmpty(destUrbans)) {
			oldDestUrb=destUrbans.get(0);
		}
		DestinazioneUrbanisticaInterventoDTO dstUrbNew=new DestinazioneUrbanisticaInterventoDTO();
		dstUrbNew.setPraticaId(praticaToBe.getId());
		dstUrbNew.setCheckPresaVisione(oldDestUrb.getCheckPresaVisione());
		dstUrbNew.setComuneId(locInterv.getLocalizIntervComuneIdNew());
		dstUrbNew.setId(++counter);
		dstUrbNew.setStrumUrbApprovato(oldDestUrb.getStrumUrbApprovato());
		dstUrbNew.setStrumUrbApprovatoData(oldDestUrb.getStrumUrbApprovatoData());
		dstUrbNew.setStrumUrbApprovatoAtto(oldDestUrb.getStrumUrbApprovatoAtto());
		dstUrbNew.setDestinAreaStrVig(oldDestUrb.getDestinAreaStrVig());
		dstUrbNew.setStrumApprovUltTutele(oldDestUrb.getStrumApprovUltTutele());
		dstUrbNew.setStrumUrbAdottato(oldDestUrb.getStrumUrbAdottato());
		dstUrbNew.setStrumUrbAdottatoData(oldDestUrb.getStrumUrbAdottatoData());
		dstUrbNew.setStrumUrbAdottatoAtto(oldDestUrb.getStrumUrbAdottatoAtto());
		dstUrbNew.setDestinAreaStrAdott(oldDestUrb.getDestinAreaStrAdott());
		dstUrbNew.setStrumAdottUltTutele(oldDestUrb.getStrumAdottUltTutele());
		dstUrbNew.setConformeDisciplUrbVigente(oldDestUrb.getConformeDisciplUrbVig());
		destUrbIntDao.insert(dstUrbNew);
		//aggiorno le stringhe dei vincoli sul relativo comune sia per l'articolo 100 che per l'articolo 38
		//se la pratica risulterà già trasmessa non verrà aggiornata ai nuovi valori e quindi rimarranno i vecchi.
		if(oldDestUrb.getIdStrumUrbanArt100()>0) {
			List<PptrStrumentiUrbanistici> art100s = this.praticaCivSvc.getPptrStrumentiUrbanistici(oldDestUrb.getIdStrumUrbanArt100());
			if(ListUtil.isNotEmpty(art100s)) {
				PptrStrumentiUrbanistici art100 = art100s.get(0);
				updateVincoloArt(comuniCompetenzaPratica, locInterv, art100,true);
			}
		}
		if(oldDestUrb.getIdStrumUrbanArt38()>0) {
			List<PptrStrumentiUrbanistici> art38s = this.praticaCivSvc.getPptrStrumentiUrbanistici(oldDestUrb.getIdStrumUrbanArt38());
			if(ListUtil.isNotEmpty(art38s)) {
				PptrStrumentiUrbanistici art38= art38s.get(0);
				updateVincoloArt(comuniCompetenzaPratica, locInterv, art38,false);
			}
		}
		return counter;
	}

	private void migraDescrizioneIntervento(VtnoAttivitaPptr infoPratica, RiepilogoPraticaCivilia riepPrat,
			PraticaDTO praticaToBe, Long maxProgCittadino) throws MigrationException {
		List<PptrDescrIntervento> descrIntervento = this.praticaCivSvc.getDescrIntervento(infoPratica.gettPraticaCodice(), maxProgCittadino);
		if(ListUtil.isNotEmpty(descrIntervento)) {
			//descrizione intervento
			DescrizioneSchedaTecnicaValuesDTO desc=new 
					DescrizioneSchedaTecnicaValuesDTO(praticaToBe.getId(), "DESCR", descrIntervento.get(0).getDescrIntervento(), 
							TipoQualificazione.DESCR);
			descrSTDao.insert(desc);
		}
		List<PptrTipoIntervento> tipoIntervento = this.praticaCivSvc.getDatiTipoIntervento(infoPratica.gettPraticaId(), maxProgCittadino);
		if(ListUtil.isNotEmpty(tipoIntervento)) {
			PptrTipoIntervento tipoInterv = tipoIntervento.get(0);
			TipoInterventoDTO tipoInt=new TipoInterventoDTO();
			tipoInt.setIdPratica(praticaToBe.getId());
			tipoInt.setCodice(AutpaeDtoBuilder.buildFascicoloTipoIntervento(tipoInterv));
			tipoInt.setArtt(tipoInterv.getConformIntervArttRegEdil());
			tipoInt.setCon(tipoInterv.getConformAttoApprovRegEdil());
			tipoInt.setDataApprovazione(tipoInterv.getConformDataApprovRegEdil());
			tipoIntDao.insert(tipoInt);
		}
		//caratterizzazione intervento
		List<PptrCaratterizzazioneIntervento> carattIntervs = praticaCivSvc.getDatiCaratterizzazioneIntervento(
				infoPratica.gettPraticaId(), maxProgCittadino);
		if (ListUtil.isNotEmpty(carattIntervs)) {
			PptrCaratterizzazioneIntervento item = carattIntervs.get(0);
			CaratterizzazioneInterventoDto buildedNew = AutpaeDtoBuilder.buildFascicoloCarattIntervento(item);
			List<ConfigOptionValue> checkBoxs = buildedNew.getCaratterizzazione();	
			for(ConfigOptionValue checkBox:checkBoxs) {
				DescrizioneSchedaTecnicaValuesDTO entity=new DescrizioneSchedaTecnicaValuesDTO();
				entity.setSezione(TipoQualificazione.CAR_INT);
				entity.setPraticaId(praticaToBe.getId());
				entity.setText(checkBox.getText());
				entity.setCodice(checkBox.getValue());
				descrSTDao.insert(entity);
			}
			DescrizioneSchedaTecnicaValuesDTO entity=new DescrizioneSchedaTecnicaValuesDTO();
			entity.setSezione(TipoQualificazione.CAR_INT_TP);
			entity.setPraticaId(praticaToBe.getId());
			if(buildedNew.getDurata()!=null) {
				entity.setCodice(buildedNew.getDurata());
				descrSTDao.insert(entity);	
			}
		} 
		migraQualificazioneIntervento(infoPratica, riepPrat.isPresentata, praticaToBe, maxProgCittadino);
	}

	private void migraQualificazioneIntervento(VtnoAttivitaPptr infoPratica, boolean isPresentata,
			PraticaDTO praticaToBe, Long maxProgCittadino) throws MigrationException {
		//qualificazione intervento
		// qualificazione intervento : checkbox a selezione multipla...
		List<PptrQualificIntervento> qualIntervs = praticaCivSvc
				.getDatiQualificazioneIntervento(infoPratica.gettPraticaId(), maxProgCittadino);
		if (ListUtil.isNotEmpty(qualIntervs)) {
			PptrQualificIntervento item = qualIntervs.get(0);
			List<ConfigOptionValue> checkBoxs = AutpaeDtoBuilder.buildFascicoloQualifIntervento(item,isPresentata,praticaToBe.getTipoProcedimento());
			descrSTDao.deleteBySezioneAndPratica(TipoQualificazione.QUAL.name(), praticaToBe.getId());
			for(ConfigOptionValue checkBox:checkBoxs) {
				DescrizioneSchedaTecnicaValuesDTO entity=new DescrizioneSchedaTecnicaValuesDTO();
				entity.setSezione(TipoQualificazione.QUAL);
				entity.setPraticaId(praticaToBe.getId());
				entity.setText(checkBox.getText());
				entity.setCodice(checkBox.getValue());
				descrSTDao.insert(entity);
			}
		}
	}

	private void migraLocalizzazioneIntervento(VtnoAttivitaPptr infoPratica, PraticaDTO praticaToBe,
			Long maxProgCittadino, List<ComuniCompetenzaDTO> comuniCompetenzaPratica,
			List<Long> comuniLocalizzazioneIntervento, AutPaesPptrLocalizInterv locIntervOld)
			throws MigrationException {
		LocalizzazioneInterventoDTO locInterventoNew = AutpaeDtoBuilder.buildLocalizzazione(locIntervOld);
		// qui devo inserire il mio comuneId che corrisponde all'ente.id
		
		Optional<ComuniCompetenzaDTO> optComune = comuniCompetenzaPratica.stream()
				.filter(com -> Long.parseLong(com.getCodIstat())==locIntervOld.getLocalizIntervComuneId())
				.findAny();
		if (optComune.isEmpty()) {
			throw new MigrationException("errore nel retrieve del comuneId new , valore istat6prov:"
					+ locIntervOld.getLocalizIntervComuneId());
		}
		Long idComuneNew = (long) optComune.get().getEnteId();
		comuniLocalizzazioneIntervento.add(idComuneNew);
		//setto sul vecchio bean il mio riferimento comune, mi serve dopo per la destinazione urbanistica
		locIntervOld.setLocalizIntervComuneIdNew(idComuneNew);
		locInterventoNew.setComuneId(idComuneNew);
		this.entiCommon.stream().filter(ente -> ente.getId() == idComuneNew.intValue()).findAny()
				.ifPresent(ente -> locInterventoNew.setComune(ente.getDescrizione()));
		locInterventoNew.setPraticaId(praticaToBe.getId());
		//scrivo record localizzazione_intervento
		locIntDao.insert(locInterventoNew);
		// creo le particelle
		List<PptrParticelleCatastali> particelleOld = praticaCivSvc.getParticelleCatastaliLocalizInterv(
				locIntervOld.getLocalizIntervId(), infoPratica.gettPraticaId(), maxProgCittadino);
		List<ParticelleCatastaliDTO> particelleNew = AutpaeDtoBuilder.buildLocalizzazioneParticelle(particelleOld,
				locInterventoNew);
		//scrivo le particelle
		int particellaCounter=0;
		for(ParticelleCatastaliDTO particella:particelleNew) {
			particella.setPraticaId(praticaToBe.getId());
			particella.setComuneId(locInterventoNew.getComuneId());
			particella.setId(++particellaCounter);
			particelleDao.insert(particella);
		}
		
		// parchi paesaggi immobili
		List<EnteParcoAss> parchiOld = praticaCivSvc.getEnteParcoAss(infoPratica.getCodicePraticaEnteDelegato(),
				locIntervOld.getLocalizIntervComuneId() + "", maxProgCittadino);
		if(ListUtil.isNotEmpty(parchiOld)) {
			for(EnteParcoAss parcoOld:parchiOld) {
				if(parcoOld==null) continue;
				ParchiPaesaggiImmobiliDTO ppi=new ParchiPaesaggiImmobiliDTO();
				ppi.setComuneId(locInterventoNew.getComuneId());
				ppi.setTipoVincolo("P");
				ppi.setCodice(parcoOld.getTnoEnteParcoMapId()+"");
				ppi.setDescrizione(parcoOld.getDescParco());
				ppi.setSelezionato(parcoOld.getEnteParcoTipoAssId()==1?"S":null);
				ppi.setInfo(parcoOld.getMail());
				ppi.setDataInserimento(praticaToBe.getDataCreazione());
				ppi.setPraticaId(praticaToBe.getId());
				ppiDao.insert(ppi);
			}
		}
		List<PaesaggioRuraleAss> paesaggiOld = praticaCivSvc.getPaesaggioRuraleAss(infoPratica.gettPraticaCodice(),
				locIntervOld.getLocalizIntervComuneId() + "", maxProgCittadino);
		if(ListUtil.isNotEmpty(paesaggiOld)) {
			for(PaesaggioRuraleAss ruraleOld:paesaggiOld) {
				ParchiPaesaggiImmobiliDTO ppi=new ParchiPaesaggiImmobiliDTO();
				if(ruraleOld==null) continue;
				ppi.setComuneId(locInterventoNew.getComuneId());
				ppi.setTipoVincolo("R");
				ppi.setCodice(ruraleOld.getTnoPaesaggiRuraliMapId()+"");
				ppi.setDescrizione(ruraleOld.getDescPaesaggioRurale());
				ppi.setSelezionato(ruraleOld.getPaesaggioRuraleTipoAssId()==1?"S":null);
				ppi.setDataInserimento(praticaToBe.getDataCreazione());
				ppi.setPraticaId(praticaToBe.getId());
				ppiDao.insert(ppi);
			}
		}
		List<InteressePubblicoAss> immobiliOld = praticaCivSvc.getInteressePubblicoAss(
				infoPratica.gettPraticaCodice(), locIntervOld.getLocalizIntervComuneId() + "", maxProgCittadino);
		if(ListUtil.isNotEmpty(immobiliOld)) {
			for(InteressePubblicoAss ruraleOld:immobiliOld) {
				if(ruraleOld==null) continue;
				ParchiPaesaggiImmobiliDTO ppi=new ParchiPaesaggiImmobiliDTO();
				ppi.setComuneId(locInterventoNew.getComuneId());
				ppi.setTipoVincolo("I");
				ppi.setCodice(ruraleOld.getCodiceVincolo()+"");
				ppi.setDescrizione(ruraleOld.getOggetto());
				ppi.setSelezionato(ruraleOld.getInteressePubbTipoAssId()==1?"S":null);
				ppi.setDataInserimento(praticaToBe.getDataCreazione());
				ppi.setPraticaId(praticaToBe.getId());
				ppiDao.insert(ppi);
			}
		}
	}

	private void migraReferenti(VtnoAttivitaPptr infoPratica, PraticaDTO praticaToBe, Long maxProgCittadino,
			List<PptrReferentiProgetto> referenti) throws IOException, SQLException, CustomCmisException,
			CustomOperationException, CustomValidationException, MigrationException {
		boolean hasTecnico=false;
		boolean hasRichiedente=false;
		for (PptrReferentiProgetto referente : referenti) {
			List<PptrTitolarita> titolaritaList = this.praticaCivSvc.getTitolaritaReferente(infoPratica.gettPraticaCodice(), 
					referente.getReferenteProgettoId(),
					Long.valueOf(maxProgCittadino));
			if(referente.getTipoReferente()==null) {
				log.error("Errore nei referenti, trovato un referente senza tipo... id:"+referente.getReferenteProgettoId());
				continue;
			}
			PptrTitolarita titolarita=null;
			if(ListUtil.isNotEmpty(titolaritaList)){
				titolarita=titolaritaList.get(0);	
			}
			if (referente.getTipoReferente().equalsIgnoreCase("SD")) {
				// soggetto dichiarante o richiedente
				elaboraReferente(praticaToBe, referente,titolarita);
				hasRichiedente=true;
				if(titolarita!=null) {
					if(titolarita.getFileDichAssenso()!=null && titolarita.getFileDichAssenso().length()>0) {
						this.uploadIS(this.parsedFilename(titolarita.getNomeFile()), titolarita.getFileDichAssenso(), 
								titolarita.getFormatoFile(), 500, praticaToBe.getId(), praticaToBe.getCodicePraticaAppptr(), 
								"dichiarazione d'assenso",titolarita.getDataCaricamentoFile());
					}
				}
				List<PptrDisclaimerXPratica> disclaimers = praticaCivSvc.getDisclaimerXPratica(infoPratica.gettPraticaCodice(), 
						referente.getReferenteProgettoId(),
						Long.valueOf(maxProgCittadino));
				scriviDisclaimers(praticaToBe, disclaimers);
				
			}else if (referente.getTipoReferente().equalsIgnoreCase("TR")) {
				// tecnico riferimento
				elaboraReferente(praticaToBe, referente, null);
				hasTecnico=true;
			}else if (referente.getTipoReferente().equalsIgnoreCase("AT")) {
				// altro titolare
				//creo il record per l'altro titolare e elaboro l'anagrafica
				elaboraReferente(praticaToBe, referente, titolarita);
			}
		}
		if(!hasRichiedente) {
			this.creaReferenteEDoc(praticaToBe.getId(), "SD");
		}
		if(!hasTecnico) {
			this.creaReferenteEDoc(praticaToBe.getId(), "TR");
		}
	}
	
	private void creaReferenteEDoc(UUID praticaId,String tipo) {
		final ReferentiDTO entity = new ReferentiDTO();
		entity.setTipoReferente(tipo);
		entity.setPraticaId(praticaId);
		final long richId = this.referentiDao.insert(entity);
		entity.setId(richId);
		final ReferentiDocumentoDTO docRichiedente = new ReferentiDocumentoDTO(richId);
		docRichiedente.setReferenteId(richId);
		this.referentiDocDao.insert(docRichiedente);
	}

	private void migraProvvedimento(UUID id, long tPraticaId, String codicePraticaAppptr, Long prog,PraticaDTO pratica,RiepilogoPraticaCivilia riepPrat) throws Exception {
		List<PptrProvvedimento> provvs = this.praticaCivSvc.getProvvedimento(codicePraticaAppptr, prog);
		if(ListUtil.isNotEmpty(provvs)) {
			PptrProvvedimento provv = provvs.get(0);
			ProvvedimentoFinaleDTO provvNew=new ProvvedimentoFinaleDTO();
			provvNew.setNumeroProvvedimento(provv.getNumeroProvvedimento());
			provvNew.setDataRilascioAutorizzazione(new Timestamp(provv.getDataProvvedimento().getTime()));
			provvNew.setEsitoProvvedimento(EsitoParere.fromCiviliaValue(provv.getFlagEsito()));
			provvNew.setDraft(true);
			provvNew.setIdPratica(id);
			provvNew.setRup(provv.getRup());
			long idProvv = provvDao.insert(provvNew);
			provvNew.setId(idProvv);
			//prelevo gli allegati
			List<VtnoAllegatiPptr> allProvvs = this.praticaCivSvc.getProvvedimentoAllegati(codicePraticaAppptr, prog);
			if(ListUtil.isNotEmpty(allProvvs)) {
				VtnoAllegatiPptr all = allProvvs.get(0);
					this.uploadIS(this.parsedFilename(all.getNomeFile()),all.getBinContent(),
							all.gettKeDocAttrValue(),
							951, //provvedimento finale pubblico
							id,
							codicePraticaAppptr,all.getPptrTipoAllegatoDescrizione(),
							all.getDataArrivo(),all.getIdToDownloadFromAlfresco());
				if(allProvvs.size()>1) {
					log.error("Il numero dei provvedimenti finali è > 1 codicePraticaAppptr:"+codicePraticaAppptr);
				}
			}
			//prelevo la ricevuta di trasmissione se esiste
			List<PptrProtocolloUscita> rics = this.praticaCivSvc.getRicevutaTrasmissione(codicePraticaAppptr, prog);
			if(ListUtil.isNotEmpty(rics)) {
				PptrProtocolloUscita ric = rics.get(0);
				riepPrat.hasRicevutaTrasmissione=true;
				provvNew.setDraft(false);
				provvDao.update(provvNew);
				AllegatiDTO allRic = this.uploadIS("Anteprima_ricevuta_trasmissione.pdf",
								ric.getBinPdfContent()
						,
						AllowedMimeType.PDF.getMimeType(),
						1100, //Documento Trasmissione
						id,
						codicePraticaAppptr,"Documento trasmissione",ric.getDataprotocollo());
				AllegatiDTO allRicProto = this.uploadIS("Ricevuta_trasmissione_prot.pdf",
						ric.getBinPdfContent().length()>ric.getBinPdfProtContent().length()?
								ric.getBinPdfContent():ric.getBinPdfProtContent()
						,
						AllowedMimeType.PDF.getMimeType(),
						1101, //Documento Trasmissione protocollato
						id,
						codicePraticaAppptr,"Documento trasmissione (protocollato)",ric.getDataprotocollo());
				SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
				final String protocolloStr=ric.getTitolarioProtocollo()+"/"+sdf.format(ric.getDataprotocollo())+"/"+ric.getNumeroProtocollo();
				allRicProto.setProtocollo(protocolloStr);
				allRicProto.setIdAllegatoPreProtocollazione(allRic.getId());
				allegatiDao.update(allRicProto);
				pratica.setStato(AttivitaDaEspletare.TRANSMITTED);
				pratica.setDataTrasmissioneProvvedimentoFinale(new Timestamp(ric.getDataprotocollo().getTime()));
				praticaDao.updateWithoutCheck(pratica);
			}
		}
	}

	/**
	 * migrazione delle altre mail in TNO_PPTR_MAIL_INVIATE escludendo quella della relaizone ente e quella del parere sop
	 * @author acolaianni
	 *
	 * @param id
	 * @param tPraticaId
	 * @param codicePraticaAppptr
	 * @param prog
	 * @param praticaToBe
	 * @param pptrMailInviateMigrate 
	 * @throws Exception 
	 */
	private void migraMailInterne(UUID id, long tPraticaId, String codicePraticaAppptr, Long prog,
			PraticaDTO praticaToBe, List<Long> pptrMailInviateMigrate) throws Exception {
		//TODO le bozze di comunicazione NON verranno migrate....
		List<PptrMailInviate> mails = praticaCivSvc.getPptrMailInviate(codicePraticaAppptr);
		if(ListUtil.isNotEmpty(mails)) {
			for(PptrMailInviate mail:mails) {
				if(!pptrMailInviateMigrate.contains(mail.getMailInviateId())){
					//se non è stata già migrata
					CorrispondenzaDTO corrispondenza = scriviCorrispondenza(id, mail);
					corrispondenza.setTipoOrganizzazioneOwner(Gruppi.REG_.name()); //anche se non è così....
					corrispondenza.setVisibilita(String.join(",", Gruppi.SBAP_.name() ,Gruppi.REG_.name(),Gruppi.ETI_.name()));
					corrDao.update(corrispondenza);
					corrDao.updateDraft(corrispondenza.getId(), new Timestamp(corrispondenza.getDataInvio().getTime()));
					//metto in cms il file eml sullo stesso path delle ricevute pec, in quanto per queste mail non saranno migrate...
					StringBuilder pathCms=DynamicKafkaConsumer.getUploadPathRicevuta(cmsRootPath, mail.getMessageId(), codicePraticaAppptr);
					final String fileName="mail.eml";
					MockMultipartFile fileMock = new MockMultipartFile(fileName,mail.getEml().getBinaryStream());
					fileMock.setContentType("text/xml");
					AllegatiDTO all=allegatoSvc.doUploadPerMigrazione(fileMock, pathCms.toString());
					final String phPathName=AllegatoService.PLACEHOLDERTEMPIDCMS+pathCms.toString()+"/"+fileName;
					corrispondenza.setIdEmlCmis(phPathName);
					corrDao.update(corrispondenza);
				}
			}
		}
		
	}

	/**
	 * il loro modello è differente dal nostro, loro hanno parte allegato inviato e non sempre la mail relativa 
	 * noi invece no, abbiamo una corrispondenza con allegati e attributo visibilità
	 * @author acolaianni
	 *
	 * @param id
	 * @param tPraticaId
	 * @param codicePraticaAppptr
	 * @param prog
	 * @throws Exception 
	 */
	private void migraUlterioreDocumentazione(UUID id, long tPraticaId, String codicePraticaAppptr, Long prog) throws Exception {
		List<PptrSchedaAllegato> ultDocs = this.praticaCivSvc.getListaUlterioreDocumentazione(codicePraticaAppptr);
		if(ListUtil.isNotEmpty(ultDocs)) {
			for(PptrSchedaAllegato ultDoc:ultDocs) {
				//carico l'allegato
				AllegatiDTO allMail = this.uploadIS(this.parsedFilename(ultDoc.getNomeFile()),
						ultDoc.getAllegato(),
						ultDoc.getContentType(),
						1200,//Allegato generico comunicazione
						id,
						codicePraticaAppptr,
						ultDoc.getTitolo(),ultDoc.getDataInserimento(),ultDoc.getAlfrescoId());
				allMail.setTitolo(ultDoc.getTitolo());
				allMail.setDescrizione(ultDoc.getDescrizione());
				allMail.setDataCaricamento(ultDoc.getDataInserimento());
				allegatiDao.update(allMail);
				UUID idAllegato = allMail.getId();
				//scrivo in corrispondenza e destinatari simulando la mail di invio ulteriore documentazione
				//anche se puo' accadere che la trovo realmente nelle mail inviate
				//TODO capire se è possibile legarle
				PptrMailInviate dummy=new PptrMailInviate();
				dummy.setMittente(ultDoc.getMittente());
				dummy.setDestinatario(ultDoc.getDestinatario());
				dummy.setMittente(ultDoc.getMittente());
				dummy.setCcTimeStamp(ultDoc.getDataInserimento().getTime());
				dummy.setMessageId(codicePraticaAppptr+"-"+ultDoc.getDataInserimento().getTime());
				dummy.setOggetto("Ulteriore documentazione inviata PRATICA: "+codicePraticaAppptr);
				dummy.setTesto("Ulteriore documentazione inviata PRATICA:" +codicePraticaAppptr+"\n"+
								"Titolo   documento:" + allMail.getTitolo()+"\n" +
								"Descrizione doc.to:" +allMail.getDescrizione()+"\n" + 
								"Nome file         :" +allMail.getNomeFile() +"\n"+
								"Inserito il       :" +ultDoc.getDataInserimento());
				
				CorrispondenzaDTO corrispondenza = this.scriviCorrispondenza(id, dummy);
				String codiceTemplate="ULTER_DOC_ED";
				if(ultDoc.getRuoloMittente().equalsIgnoreCase("SOP")) {
					codiceTemplate="ULTER_DOC_SOP";
				}
				if(ultDoc.getRuoloMittente().equalsIgnoreCase("ET")) {
					codiceTemplate="ULTER_DOC_ETI";
				}
				//TODO in teoria da loro esiste anche RI=Richiedente ma nell'area di test non l'ho mai visto attivo lato sportello 
			    corrispondenza.setCodiceTemplate(codiceTemplate);
			    StringBuilder visibilita=new StringBuilder();
			    if(ultDoc.getVisibilita_ED()==1) {
			    	visibilita.append(visibilita.length()>0?",":""+Gruppi.REG_.name());
			    	this.allegatiDao.insertVisibilitaUlerioreDoc(idAllegato,Gruppi.REG_.name());
			    }
			    if(ultDoc.getVisibilita_SOP()==1) {
			    	visibilita.append(visibilita.length()>0?",":""+Gruppi.SBAP_.name());
			    	this.allegatiDao.insertVisibilitaUlerioreDoc(idAllegato,Gruppi.SBAP_.name());
			    }
			    if(ultDoc.getVisibilita_ET()==1) {
			    	visibilita.append(visibilita.length()>0?",":""+Gruppi.ETI_.name());
			    	this.allegatiDao.insertVisibilitaUlerioreDoc(idAllegato,Gruppi.ETI_.name());
			    }
			    if(ultDoc.getVisibilita_RI()==1) {
			    	visibilita.append(visibilita.length()>0?",":""+Gruppi.RICHIEDENTI.name());
			    	this.allegatiDao.insertVisibilitaUlerioreDoc(idAllegato,Gruppi.RICHIEDENTI.name());
			    }
			    corrispondenza.setVisibilita(visibilita.toString());
			    Gruppi gruppoOwner=Gruppi.fromRuoloCivilia(ultDoc.getRuoloMittente());
			    if(gruppoOwner!=null) {
			    	if(gruppoOwner.equals(Gruppi.ED_)) {
			    		gruppoOwner=Gruppi.REG_;
			    	}
			    	corrispondenza.setTipoOrganizzazioneOwner(gruppoOwner.name());
			    	this.allegatiDao.insertVisibilitaUlerioreDoc(idAllegato,gruppoOwner.name());
			    }else {
			    	log.error("Errore nella determinazione del gruppo OWNER della documentazione aggiuntiva:"+
			    				ultDoc.getRuoloMittente());
			    }
			    corrDao.update(corrispondenza);
			    corrDao.updateDraft(corrispondenza.getId(), new Timestamp(ultDoc.getDataInserimento().getTime()));
			    AllegatoCorrispondenzaDTO allCorrDTO = new AllegatoCorrispondenzaDTO();
				allCorrDTO.setIdAllegato(idAllegato);
				allCorrDTO.setIdCorrispondenza(corrispondenza.getId());
				allCorrDao.insert(allCorrDTO);
			}
		}
	}

	/**
	 * carica in corrispondenza la mail di immissione del parere sop 
	 * @author acolaianni
	 *
	 * @param id
	 * @param tPraticaId
	 * @param codiceAppptr
	 * @param maxProgCittadino
	 * @param praticaToBe
	 * @param parereSop
	 * @param riepPrat 
	 * @throws Exception 
	 */
	private void migraMailParereSop(UUID id, long tPraticaId, String codiceAppptr, 
			Long maxProgCittadino,PraticaDTO praticaToBe,
			ParereSoprintendenzaDTO parereSop,List<Long> pptrMailInviateMigrate, RiepilogoPraticaCivilia riepPrat) throws Exception {
		List<PptrMailInviate> mailPareres = praticaCivSvc.getPptrMailInviate_by_codicePraticaApPptr_tnoGruppoComCodice_tnoTipoComVisibilita(codiceAppptr, "COM_IMM_PAR_SOP", "SOP");
		if(ListUtil.isNotEmpty(mailPareres)) {
			riepPrat.hasMailParere=true;
			PptrMailInviate mail = mailPareres.get(0);
			CorrispondenzaDTO corrispondenza = scriviCorrispondenza(id, mail);
			pptrMailInviateMigrate.add(mail.getMailInviateId());
			if(parereSop!=null && parereSop.getId()>0) {
				parereSop.setIdCorrispondenza(corrispondenza.getId());
				parereSopDao.update(parereSop);
			}
			corrispondenza.setTipoOrganizzazioneOwner(Gruppi.SBAP_.name());
			corrispondenza.setVisibilita(String.join(",", Gruppi.SBAP_.name() ,Gruppi.REG_.name(),Gruppi.ETI_.name()));
			corrDao.update(corrispondenza);//questo update me la risetta a draft
			corrDao.updateDraft(corrispondenza.getId(), corrispondenza.getDataInvio());
			praticaToBe.setStatoParereSoprintendenza(StatoParere.PARERE_INSERITO_SOPRINTENDENZA);
			praticaToBe.setDataTrasmissioneParere(new Timestamp(mail.getDataInvio().getTime()));
			praticaDao.updateWithoutCheck(praticaToBe);
			//per esistere la trasmissione Parere allora è sicuramente almeno  IN_LAVORAZIONE
		} 
	}
	
	
	/**
	 * carica in corrispondenza la relazione ente
	 * @author acolaianni
	 *
	 * @param id
	 * @param tPraticaId
	 * @param codiceAppptr
	 * @param maxProgCittadino
	 * @param praticaToBe
	 * @param relazioneEnte
	 * @param riepPrat 
	 * @throws Exception 
	 */
	private void migraMailRelazioneEnte(UUID id, long tPraticaId, String codiceAppptr, Long maxProgCittadino,
			PraticaDTO praticaToBe,RelazioneEnteDTO relazioneEnte,List<Long> pptrMailInviateMigrate, RiepilogoPraticaCivilia riepPrat) throws Exception {
		List<PptrMailInviate> mailRelaziones = praticaCivSvc.getPptrMailInviate_by_codicePraticaApPptr_tnoGruppoComCodice_tnoTipoComVisibilita(codiceAppptr, "RICH_PAR_SOP", "ED");
		if(ListUtil.isNotEmpty(mailRelaziones)) {
			riepPrat.hasMailRelazione=true;
			//solo 1 ammessa
			//se esiste significa che la relazione ente è trasmessa
			PptrMailInviate mail = mailRelaziones.get(0);
			if(mail.getTitolo().toLowerCase().contains("con avvio")) {
				praticaToBe.setStatoRelazioneEnte(StatoRelazione.RELAZIONE_TRASMESSA_CON_AVVIO);	
			}else {
				praticaToBe.setStatoRelazioneEnte(StatoRelazione.RELAZIONE_TRASMESSA_SENZA_AVVIO);
			}
			praticaToBe.setDataTrasmissioneRelazione(new Timestamp(mail.getDataInvio().getTime()));
			praticaDao.updateWithoutCheck(praticaToBe);
			//per esistere la trasmissione relazione allora è sicuramente almeno  IN_LAVORAZIONE
			if(praticaToBe.getStato().ordinal()<AttivitaDaEspletare.IN_LAVORAZIONE.ordinal()) {
				praticaToBe.setStato(AttivitaDaEspletare.IN_LAVORAZIONE);
				praticaDao.updateWithoutCheck(praticaToBe);
			}
			CorrispondenzaDTO corrispondenza = scriviCorrispondenza(id, mail);
			corrispondenza.setTipoOrganizzazioneOwner(Gruppi.REG_.name());
			corrispondenza.setVisibilita(String.join(",", Gruppi.SBAP_.name() ,Gruppi.REG_.name(),Gruppi.ETI_.name()));
			//vedo se è stata protocollata
			String protocollo=null;
			Date dataProtocollo=null;
			List<PptrProtocollo> protos = praticaCivSvc.getProtocolloByCodice(codiceAppptr, "U_PROT_RICH_PAR");
			if(ListUtil.isNotEmpty(protos)) {
				for(PptrProtocollo proto:protos) {
					if(proto.getEsitoProtocollazione().equalsIgnoreCase("SI")) {
						protocollo=proto.getTitolarioProtocollo().trim()+"/"+proto.getNumeroProtocollo();
						dataProtocollo=proto.getDataprotocollo();
						log.info("Trovato protocollo per mail di relazione ente, codiceAppptr:" +codiceAppptr+" proto:"+protocollo);
						break;
					}
				}
			}
			corrispondenza.setProtocollo(protocollo);
			corrispondenza.setDataProtocollo(dataProtocollo);
			corrDao.update(corrispondenza);//questo update me la risetta a draft
			corrDao.updateDraft(corrispondenza.getId(), corrispondenza.getDataInvio());
			pptrMailInviateMigrate.add(mail.getMailInviateId());
			if(relazioneEnte!=null && relazioneEnte.getIdRelazioneEnte()>0) {
				relazioneEnte.setIdCorrispondenza(corrispondenza.getId());
				relazioneEnteDao.update(relazioneEnte);
			}
		}
	}

	private CorrispondenzaDTO scriviCorrispondenza(UUID id, PptrMailInviate mail)
			throws MigrationException {
		//in teoria abbiamo a disposizione l'eml della mail inviata
		CorrispondenzaDTO mailNew = new CorrispondenzaDTO();
		mailNew.setBozza(false);
		mailNew.setComunicazione(false);
		mailNew.setMittenteDenominazione(mail.getMittente());
		mailNew.setMittenteEmail(mail.getMittente());
		mailNew.setMittenteUsername(mail.getMittente());
		mailNew.setMittenteEnte(mail.getMittente());
		mailNew.setDataInvio(new Timestamp(mail.getCcTimeStamp()));
		mailNew.setMessageId(mail.getMessageId());
		mailNew.setOggetto(mail.getOggetto());
		mailNew.setTesto(mail.getTesto());
		mailNew.settPptrMailInviateId(mail.getMailInviateId());
		mailNew.setStato(false);
		mailNew.setRiservata(true);
		Long idCorrispondenza = corrDao.insert(mailNew);
		mailNew.setId(idCorrispondenza);
		FascicoloCorrispondenzaDTO fascicoloCorrispondenza = new FascicoloCorrispondenzaDTO();
		fascicoloCorrispondenza.setIdCorrispondenza(idCorrispondenza);
		fascicoloCorrispondenza.setIdPratica(id);
		fascicoloCorrDao.insert(fascicoloCorrispondenza);
		// elaborazione destinatari
		if (StringUtil.isEmpty(mail.getDestinatario())) {
			throw new MigrationException("Nessun destinatario trovato nella mail con id"
					+ mail.getMailInviateId() + " oggetto: " + mail.getOggetto() + " !!!", null);
		}
		String[] mailDestinatari = mail.getDestinatario().split(",");
		for (String mailDestinatario : mailDestinatari) {
			//DestinatarioDTO destinDTO = new DestinatarioDTO(mailDestinatario, StatoCorrispondenza.INVIATA);
			DestinatarioDTO destinDTO = new DestinatarioDTO();
			destinDTO.setTipo(TipoDestinatario.TO);
			destinDTO.setEmail(mailDestinatario);
			destinDTO.setNome(mailDestinatario);
			destinDTO.setIdCorrispondenza(idCorrispondenza);
			destinDTO.setPec(true);
		    destinatariDao.insert(destinDTO);
		}
		return mailNew;
	}

	private void migraMailCivilia(UUID idPratica, long tPraticaId, String codiceAppptr, Long maxProgCittadino) throws CustomCmisException, IOException, SQLException, CustomOperationException, MigrationException {
		List<CiviliaMail> mailsCivilia = praticaCivSvc.getCiviliaEmail(tPraticaId);
		if (ListUtil.isNotEmpty(mailsCivilia)) {
			for (CiviliaMail mail : mailsCivilia) {
				Map<String, Long> destinatariIdMap = new HashMap<>();
				if (mail.getVerso().equalsIgnoreCase("U")) {
					CorrispondenzaDTO mailNew = new CorrispondenzaDTO();
					mailNew.setBozza(false);
					mailNew.setComunicazione(false);
					mailNew.setDataInvio(new Timestamp(mail.getCcTimeStamp()));
					mailNew.setMessageId(mail.getMessageId());
					mailNew.setMittenteDenominazione(mail.getMittente());
					mailNew.setMittenteEmail(mail.getMittente());
					mailNew.setMittenteUsername(mail.getMittente());
					mailNew.setMittenteEnte(mail.getMittente());
					mailNew.setOggetto(mail.getOggetto());
					mailNew.setTesto(mail.getTesto());
					mailNew.setStato(false);
					mailNew.setRiservata(false);
					mailNew.settMailInviateId(mail.gettMailInviateId());
					Long idCorrispondenza = corrDao.insert(mailNew);
					FascicoloCorrispondenzaDTO fascicoloCorrispondenza = new FascicoloCorrispondenzaDTO();
					fascicoloCorrispondenza.setIdCorrispondenza(idCorrispondenza);
					fascicoloCorrispondenza.setIdPratica(idPratica);
					fascicoloCorrDao.insert(fascicoloCorrispondenza);
					// elaborazione destinatari
					if (StringUtil.isEmpty(mail.getDestinatario())) {
						throw new MigrationException("Nessun destinatario trovato nella mail con id"
								+ mail.gettMailInviateId() + " oggetto: " + mail.getOggetto() + " !!!", null);
					}
					String[] mailDestinatari = mail.getDestinatario().split(",");
					addDestinatariCorrispondenza(mail, destinatariIdMap, idCorrispondenza, mailDestinatari,TipoDestinatario.TO);
					List<CiviliaMailAllegati> allegatiMailCivilia = praticaCivSvc
							.getCiviliaEmailAllegati(mail.gettMailInviateId());
					mail.setAllegatiMail(allegatiMailCivilia);
					// elaborazione allegati
					if (ListUtil.isNotEmpty(allegatiMailCivilia)) {
						for (CiviliaMailAllegati allegatoMailCivilia : allegatiMailCivilia) {
							StringBuilder contentType=new StringBuilder();
							allegatoMailCivilia.getAttributi().stream().filter(attr->attr.getName().equalsIgnoreCase("FORMATO")).findAny().ifPresent(attr->contentType.append(attr.getValue()));
							AllegatiDTO allMail = this.uploadIS(this.parsedFilename(allegatoMailCivilia.getNomeFile()),
									allegatoMailCivilia.getBinContent(),
									contentType.toString(),
									905,//Allegato generico comunicazione
									idPratica,
									codiceAppptr,
									"Allegato generico per la comunicazione",new Date(allegatoMailCivilia.getCcTimeStamp()));
							 UUID idAllegato = allMail.getId();
							AllegatoCorrispondenzaDTO allCorrDTO = new AllegatoCorrispondenzaDTO();
							allCorrDTO.setIdAllegato(idAllegato);
							allCorrDTO.setIdCorrispondenza(idCorrispondenza);
							allCorrDao.insert(allCorrDTO);
						}
					}
					// elaborazione delle ricevute:
					for (CiviliaMail ricevutaPec : mailsCivilia) {
						// filtro solo quelle con verso Ingresso
						if (ricevutaPec.getVerso().equalsIgnoreCase("I")) {
							// prendo gli allegati
							List<CiviliaMailAllegati> allegatiRicevutaPecCivilia = praticaCivSvc
									.getCiviliaEmailAllegati(ricevutaPec.gettMailInviateId());
							ricevutaPec.setAllegatiMail(allegatiRicevutaPecCivilia);
							// se ha daticert.xml lo parso e prelevo l'indirizzo destinatario
							if (ListUtil.isNotEmpty(allegatiRicevutaPecCivilia)) {
								allegatiRicevutaPecCivilia.stream()
										.filter(all -> all.getNomeFile().equalsIgnoreCase("DATICERT.XML")).findAny()
										.ifPresent(all -> {
											// parso il daticert.xml e prelevo tipo ricevuta,
											// destinatario,message-id-originale
											// Blob xmlContent=all.getBinContent();
											// xmlContent.getBinaryStream().readAllBytes()
											try {
												elaboraXmlRicevuta(ricevutaPec, all, mailNew.getMessageId(),
														idCorrispondenza, destinatariIdMap, idPratica,codiceAppptr);
											} catch (Exception e) {
												log.error(LOG_MIGRAZIONE_MARKER, "Errore nella elaboraXmlRicevuta ", e);
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
			Long idCorrispondenza, String[] mailDestinatari,TipoDestinatario tipoDest) {
		for (String mailDestinatario : mailDestinatari) {
			//DestinatarioDTO destinDTO = new DestinatarioDTO(mailDestinatario, StatoCorrispondenza.INVIATA);
			DestinatarioDTO destinDTO = new DestinatarioDTO();
			destinDTO.setTipo(tipoDest);
			destinDTO.setEmail(mailDestinatario);
			destinDTO.setNome(mailDestinatario);
			destinDTO.setIdCorrispondenza(idCorrispondenza);
			destinDTO.setPec(mail.getIsPec() != null && mail.getIsPec().equalsIgnoreCase("1"));
			Long idDest = destinatariDao.insert(destinDTO);
			// mi tengo una mappa con indirizzoDest e id
			destinatariIdMap.put(mailDestinatario, idDest);
		}
	}

	
	/**
	 * 
	 * verifica se appartiene alla mail in uscita tramite message-id e se si lo
	 * inserisce nella tabella ricevute
	 * 
	 * @param ricevutaPec
	 * @autor Adriano Colaianni
	 * @date 28 apr 2021
	 * @param xmlContent è l'allegato mail corrispondente al DATICERT.XML
	 * @param messageIdOriginale message-id dell mail inviata
	 * @param idCorrispondenza   id record corrispondenza della mail inviata
	 * @param destinatariMap     mappa destinatari<indirizzo,id>
	 * @throws Exception
	 */
	private boolean elaboraXmlRicevuta(CiviliaMail ricevutaPec, CiviliaMailAllegati xmlContent,
			String messageIdOriginale, Long idCorrispondenza, Map<String, Long> destinatariMap,
			UUID praticaId,String codicePraticaAppptr) throws Exception {
		boolean ret = false;
		JAXBContext jaxbContext;
		jaxbContext = JAXBContext.newInstance(Postacert.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Postacert ricevutaXmlObj = null;
		ricevutaXmlObj = (Postacert) jaxbUnmarshaller.unmarshal(xmlContent.getBinContent().getBinaryStream());
		xmlContent.getBinContent().getBinaryStream().reset();
		final TipoRicevuta tipoRicevuta=TipoRicevuta.fromString(ricevutaXmlObj.getTipo());
		if(!List.of(TipoRicevuta.ACCETTAZIONE,TipoRicevuta.AVVENUTA_CONSEGNA,TipoRicevuta.NON_ACCETTAZIONE,TipoRicevuta.ERRORE_CONSEGNA).contains(tipoRicevuta)) {
			return false;
		}
		String msgIdOriginale = ricevutaXmlObj.getDati().getMsgid();
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
			//a volte capita che non sono inseriti nel campo destinatari tutti i destinatari...
			//li pesco dal DATICERT.XML...
			List<String> destinatariNonInseriti = ricevutaXmlObj.getIntestazione().getDestinatari().stream()
					.filter(dest -> !destinatariMap.containsKey(dest.getvalue())).map(Destinatari::getvalue)
					.collect(Collectors.toList());
			if(ListUtil.isNotEmpty(destinatariNonInseriti)) {
				addDestinatariCorrispondenza(ricevutaPec, destinatariMap, idCorrispondenza, destinatariNonInseriti.toArray(new String[] {}), TipoDestinatario.TO);
				log.info(LOG_MIGRAZIONE_MARKER, "Alcuni destinatari presenti nel daticert.xml non erano presenti nel record mail {} ",destinatariNonInseriti);
			}
			List<RicevutaDTO> listRicevutaDTO = new ArrayList<>();
			if (ListUtil.isEmpty(destinatariMail)) {
				log.error(LOG_MIGRAZIONE_MARKER, "Errore nel retrieve dei destinatari della ricevuta PEC ");
				return false;
			}
			// carico su alfresco
			final String fileName=this.parsedFilename(xmlContent.getNomeFile());
			MockMultipartFile fileMock = new MockMultipartFile(fileName, 
					xmlContent.getBinContent().getBinaryStream());
			fileMock.setContentType("text/xml");
			StringBuilder pathCms=DynamicKafkaConsumer.getUploadPathRicevuta(cmsRootPath, msgIdOriginale, codicePraticaAppptr);
			StringBuilder pathCmsWithoutBasePath=DynamicKafkaConsumer.getUploadPathRicevuta(cmsRootPath, msgIdOriginale, codicePraticaAppptr);
			AllegatiDTO all=allegatoSvc.doUploadPerMigrazione(fileMock, pathCmsWithoutBasePath.toString());
			final String phPathName=all.getIdCms()+pathCms.toString()+"/"+fileName;
			List<String> destinatariRicevuta=new ArrayList<>();
			if(tipoRicevuta.equals(TipoRicevuta.AVVENUTA_CONSEGNA)||
					tipoRicevuta.equals(TipoRicevuta.ERRORE_CONSEGNA)){
				//il destinario è preso dal campo dati->consegna
				destinatariRicevuta.add(ricevutaXmlObj.getDati().getConsegna());
			}else {
				destinatariRicevuta.addAll(destinatariMail);
			}
			for (String destinatario : destinatariRicevuta) {
				RicevutaDTO ricevuta = new RicevutaDTO();
				ricevuta.setData(new Timestamp(ricevutaPec.getCcTimeStamp()));
				ricevuta.setTipoRicevuta(TipoRicevuta.fromString(ricevutaXmlObj.getTipo()));
				ricevuta.setErrore(TipoErrore.fromString(ricevutaXmlObj.getErrore()));
				ricevuta.setIdCorrispondenza(idCorrispondenza);
				ricevuta.setIdDestinatario(destinatariMap.get(destinatario));
				ricevuta.setIdCmsDatiCert(phPathName);
				// non avendo l'eml completo della ricevuta, prendo solo il daticert.xml
				ricevuta.setIdCmsEml(phPathName);
				listRicevutaDTO.add(ricevuta);
				if (ricevuta.getTipoRicevuta().equals(TipoRicevuta.AVVENUTA_CONSEGNA)
						|| ricevuta.getTipoRicevuta().equals(TipoRicevuta.ERRORE_CONSEGNA)) {
					// setto sul relativo destinatario lo stato a ESITO_POSITIVO o ESITO_ERRORE
					try {
						DestinatarioDTO dest=new DestinatarioDTO();
						dest.setId(ricevuta.getIdDestinatario());
						dest = destinatariDao.findTxWrite(dest);
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
				this.ricevutaDao.insert(ricevuta);
			}
		}
		return ret;

	}

	private AllegatiDTO mockUpload(List<Integer> tipiContenuto,
			String descrAllegato,UUID praticaId,String contentType,
			String nomeFile) {
		AllegatiDTO retDto=new AllegatiDTO();
		TipoContenutoDTO tipoContenuto = new TipoContenutoDTO();
		tipoContenuto.setId(tipiContenuto.get(0));
		retDto.setId(UUID.randomUUID());
		retDto.setPraticaId(praticaId);
		retDto.setNomeFile(nomeFile);
		retDto.setChecksum("686845f035cdd83066cc191a428be4b0");
		retDto.setSize(0L);
		retDto.setDataCaricamento(new Date());
		retDto.setDeleted(false);
		retDto.setFormatoFile(contentType);
		allegatiDao.insert(retDto);
		// inserisco tutti i tipi di contenuto nella tabella allegati_tipo_contenuto
		//e popolo le descrizioni nell'oggetto restituito in tipiContenuto
		retDto.setTipiContenuto(new ArrayList<String>());
		for (Integer idTipo : tipiContenuto) {
			AllegatiTipoContenutoDTO tipoAllegato = new AllegatiTipoContenutoDTO();
			tipoAllegato.setAllegatiId(retDto.getId());
			tipoAllegato.setTipoContenutoId(idTipo);
			allegatiTipoDao.insert(tipoAllegato);
		}
		return retDto;
	}
	
	private AllegatiDTO uploadIS(String fileName, Blob blob, String mimeType, int tipo, 
			UUID idPratica,String pathCms,String descrAllegato,Date dataCaricamento,
			String idAlfresco) throws IOException, SQLException, CustomCmisException, CustomOperationException {
		if(noAllegati) {
			return this.mockUpload(Collections.singletonList(tipo), descrAllegato, idPratica, "application/pdf", fileName);
		}
		MockMultipartFile fileMock =null;
		if(StringUtil.isNotBlank(idAlfresco)) {
			File file = proxyAlfrescoOrigin.getDocumentIntoLocalFile(idAlfresco, null);
			log.info(IMigratorService.LOG_MIGRAZIONE_MARKER,"Downloaded from alfresco {}  size {}",fileName,file.length());
			fileMock = new MockMultipartFile(fileName,file);
		}else {
			fileMock = new MockMultipartFile(fileName,blob.getBinaryStream());	
		}
		
		fileMock.setContentType(mimeType);
		AllegatiDTO ret = this.allegatoSvc.uploadAllegatoDaMigrazione(fileMock, Collections.singletonList(tipo),
				descrAllegato, pathCms, idPratica, null, null);
		if(dataCaricamento!=null) {
			ret.setDataCaricamento(dataCaricamento);
			allegatiDao.update(ret);
		}
		return ret;
	}
	
	private AllegatiDTO uploadIS(String fileName, Blob blob, String mimeType, int tipo, UUID idPratica,String pathCms,String descrAllegato,Date dataCaricamento) throws IOException, SQLException, CustomCmisException, CustomOperationException {
		return this.uploadIS(fileName, blob, mimeType, tipo, idPratica, pathCms, descrAllegato, dataCaricamento,null);
	}

	private String parsedFilename(String nomeFile) throws MigrationException {
		//a volte i nomi di file contengono anche il path locale del pc dal quale è stato effettuato upload (dipende dal browser)
		if(StringUtil.isEmpty(nomeFile)){
			throw new MigrationException("Nomefile vuoto "+nomeFile);
		}
		Path p=Path.of(nomeFile);
		return p.getFileName().toString();
	}

	/**
	 * aggiorno in comuni di competenza selezionati nella localizzazione il relativo vincolo 100 e 38 secondo i vecchi parametri su civilia.
	 * @author acolaianni
	 *
	 * @param comuniCompetenzaPratica
	 * @param locInterv
	 * @param artXX
	 * @param isArt100
	 * @throws MigrationException
	 */
	private void updateVincoloArt(List<ComuniCompetenzaDTO> comuniCompetenzaPratica, AutPaesPptrLocalizInterv locInterv,
			PptrStrumentiUrbanistici artXX,boolean isArt100) throws MigrationException {
		TnoPptrStrumentiUrbanisticiDTO recNostro=new TnoPptrStrumentiUrbanisticiDTO();
		recNostro.setIstat6Prov(artXX.getCodIstat());
		recNostro.setTipoStrumento(Long.valueOf(artXX.getTipoStrumento()).intValue());
		recNostro.setStato(artXX.getStato());
		recNostro.setAtto(artXX.getAtto());
		recNostro.setDataAtto(artXX.getDataAtto());
		Optional<ComuniCompetenzaDTO> comComp = comuniCompetenzaPratica.parallelStream().filter(com->com.getEnteId()==(locInterv.getLocalizIntervComuneIdNew()))
				.findAny();
		if(comComp.isEmpty()) {
			throw new MigrationException("Errore nel recuper del comune di competenza dall'id interno "+locInterv.getLocalizIntervComuneIdNew());
		}
		String informativaVincolo =null;
		if(isArt100) {
			informativaVincolo= TnoPptrStrumentiUrbanisticiDTO.generaVincoloArt100(recNostro); 
		}else {
			informativaVincolo= TnoPptrStrumentiUrbanisticiDTO.generaVincoloArt38(recNostro,comComp.get().getDescrizione());
		}
		if(isArt100) {
			comComp.get().setVincoloArt100(informativaVincolo);	
		}else {
			comComp.get().setVincoloArt38(informativaVincolo);
		}
		this.comuniCompetenzaDao.update(comComp.get());
	}

	private void scriviAltreDichRichiedente(PraticaDTO praticaToBe, List<PptrAltreDichiarRich> altreDichRich) {
		PptrAltreDichiarRich altreDich = altreDichRich.get(0);
		AltreDichiarazioniRichDTO altreDichNew =new AltreDichiarazioniRichDTO();
		altreDichNew.setPraticaId(praticaToBe.getId());
		altreDichNew.setCheck136(altreDich.getCheck136());
		altreDichNew.setCheck142(altreDich.getCheck142());
		altreDichNew.setCheck134(altreDich.getCheck134());
		altreDichNew.setCheck136a(altreDich.getCheck136A());
		altreDichNew.setCheck136b(altreDich.getCheck136B());
		altreDichNew.setCheck136c(altreDich.getCheck136C());
		altreDichNew.setCheck136d(altreDich.getCheck136D());
		altreDichNew.setCheck142Parchi(altreDich.getCheck142Parchi());
		//in caso di variante (NUMERO,DA,IN DATA)
		if(StringUtil.isNotEmpty(
				altreDich.getDescrAutRilasciata()+
				altreDich.getEnteRilascio())) {
			altreDichNew.setIsCasoVariante(true);
		}
		altreDichNew.setDescrAutRilasciata(altreDich.getDescrAutRilasciata());
		altreDichNew.setDataRilascio(altreDich.getDataRilascio());
		altreDichNew.setEnteRilascio(altreDich.getEnteRilascio());
		altreDichDao.delete(altreDichNew);
		altreDichDao.insert(altreDichNew);
	}

	private void scriviDisclaimers(PraticaDTO praticaToBe, List<PptrDisclaimerXPratica> disclaimers)
			throws MigrationException {
		if(ListUtil.isEmpty(disclaimers)) {
			//scrivo quelli miei
			List<DisclaimerDTO> disclDefault = disclDao.select("SD", praticaToBe.getTipoProcedimento(),
					praticaToBe.getDataCreazione(),false);
			if(ListUtil.isNotEmpty(disclDefault)) {
				for(DisclaimerDTO discl:disclDefault) {
					DisclaimerPraticaDTO entity=new DisclaimerPraticaDTO();
					entity.setDisclaimerId(discl.getId());
					entity.setPraticaId(praticaToBe.getId());
					disclPraticaDao.insert(entity);
				}
			}
			return;
		}
		for(PptrDisclaimerXPratica disclaimer:disclaimers) {
			Integer nostroVal=
					(Integer)maps.get("disclaimerTipoProc").get(Long.valueOf(disclaimer.getPptrDisclaimerId()).intValue());
			DisclaimerPraticaDTO entity=new DisclaimerPraticaDTO();
			entity.setDisclaimerId(nostroVal);
			entity.setPraticaId(praticaToBe.getId());
			if(nostroVal!=null) {
				entity.setFlag(disclaimer.getFlgDisclXPratica());
				int aggiornati = disclPraticaDao.update(entity);
				if(aggiornati==0) {
					disclPraticaDao.insert(entity);	
				}
			}else{
				throw new MigrationException("Errore nel mapping del nostro disclaimer, id loro:"+disclaimer.getPptrDisclaimerId());
			}
		}
	}

	/**
	 * scrive il referente in referenti e il relativo documento di identita
	 * @author acolaianni
	 *
	 * @param praticaToBe
	 * @param referente
	 * @param titolarita 
	 * @throws IOException
	 * @throws SQLException
	 * @throws CustomCmisException
	 * @throws CustomOperationException
	 * @throws CustomValidationException
	 * @throws MigrationException 
	 */
	private void elaboraReferente(PraticaDTO praticaToBe, PptrReferentiProgetto referente, PptrTitolarita titolarita)
			throws IOException, SQLException, CustomCmisException, CustomOperationException, CustomValidationException, MigrationException {
		ReferentiDTO anagrafica=new ReferentiDTO();
		anagrafica.setPraticaId(praticaToBe.getId());
		anagrafica.setCognome(referente.getCognome());
		anagrafica.setNome(referente.getNome());
		anagrafica.setCodiceFiscale(referente.getCodiceFiscale());
		anagrafica.setDataNascita(referente.getDataNascita());
		anagrafica.setNazionalitaNascitaName(referente.getStatoNascita());
		anagrafica.setProvinciaNascitaName(referente.getProvNascita());
		anagrafica.setComuneNascitaName(referente.getComuneNascita());
		anagrafica.setNazionalitaResidenzaName(referente.getStatoResidenza());
		anagrafica.setProvinciaResidenzaName(referente.getProvResidenza());
		anagrafica.setComuneResidenzaName(referente.getComuneResidenza());
		anagrafica.setIndirizzoResidenza(referente.getIndirizzo());
		anagrafica.setCivicoResidenza(referente.getNumCivico());
		anagrafica.setCapResidenza(referente.getCap());
		anagrafica.setTelefono(referente.getTelFisso());
		anagrafica.setFax(referente.getTelFax());
		anagrafica.setCellulare(referente.getTelCellulare());
		anagrafica.setMail(referente.getIndirizzoEmail());
		anagrafica.setPec(referente.getIndirizzoPec());
		anagrafica.setTipoReferente(referente.getTipoReferente());
		if(referente.getTipoReferente().equalsIgnoreCase("SD") || referente.getTipoReferente().equalsIgnoreCase("AT")) {
			anagrafica.setTipoReferente(referente.getTipoReferente());
			if (StringUtil.isNotBlank(referente.getDittaRuoloRich())) {
				anagrafica.setDittaQualitaDi(3);// altro
				anagrafica.setDitta(true);
				anagrafica.setDittaQualitaAltro(referente.getDittaRuoloRich());
			}
			anagrafica.setDittaEnte(referente.getDittaRagioneSociale());
			anagrafica.setDittaPartitaIva(referente.getDittaPartitaIva());
			anagrafica.setDittaCf(referente.getDittaCodiceFiscale());
		}
		//i dettagli di altro titolare li setto fuori
		if(referente.getTipoReferente().equalsIgnoreCase("TR")) {
			anagrafica.setTecnicoStudioNazionalitaName(referente.getTecnicoStatoStudio());
			anagrafica.setTecnicoStudioProvinciaName(referente.getTecnicoProvStudio());
			anagrafica.setTecnicoStudioComuneName(referente.getTecnicoComuneStudio());
			anagrafica.setTecnicoStudioIndirizzo(referente.getTecnicoIndirizzoStudio());
			anagrafica.setTecnicoStudioCivico(referente.getTecnicoNumCivStudio());
			anagrafica.setTecnicoStudioCap(referente.getTecnicoCapStudio());
			anagrafica.setTecnicoOrdCollegio(referente.getTecnicoOrdCollegio());
			anagrafica.setTecnicoCollegioNIscr(referente.getTecnicoOrdCollegioNIscr());
			anagrafica.setTecnicoCollegioSede(referente.getTecnicoOrdCollegioSede());
		}
		if(titolarita!=null) {
			if(titolarita.getTitRuoloId()<=0) {
				anagrafica.setTitolaritaId(null);	
			}else {
				anagrafica.setTitolaritaId(Long.valueOf(titolarita.getTitRuoloId()).intValue());
				if(anagrafica.getTitolaritaId()==9 ||anagrafica.getTitolaritaId()==5) {
					anagrafica.setSpecificaTitolarita(titolarita.getTitRuoloAltro());	
				}
			}
			//qui trovo anche il file della dichiarazione di assenso altri titolari
			String titEsclusivaSN=(String)this.maps.get("titolaritaEsclusiva").get(titolarita.getTitEsclusivo());
			anagrafica.setTitolaritaEsclusiva(titEsclusivaSN);
		}
		long idRef = referentiDao.insert(anagrafica);
		anagrafica.setId(idRef);
		// documento di identita
		ReferentiDocumentoDTO docReferente = new ReferentiDocumentoDTO();
		docReferente.setReferenteId(anagrafica.getId());
		List<PptrReferentiDoc> docRefs = this.praticaCivSvc.getDocumento(referente.getReferenteProgettoId());
		if (ListUtil.isNotEmpty(docRefs)) {
			PptrReferentiDoc doc = docRefs.get(0);
			docReferente.setNumero(doc.getNumDocIdent());
			docReferente.setDataRilascio(doc.getDataDocIdent());
			docReferente.setEnteRilascio(doc.getEnteRilDocIdent());
			// la data di scadenza non c'è nei loro dati...
			if (doc.getTipoDocIdentId() != null) {
				docReferente.setIdTipo((int) (long) doc.getTipoDocIdentId());// stesso mapping 1 CI 2 PAT 3 PASSPORT
			}
			if(!noAllegati && StringUtil.isNotEmpty(doc.getNomeFile())) {
				MockMultipartFile file = new MockMultipartFile(this.parsedFilename(doc.getNomeFile()), doc.getContenutoFile().getBinaryStream());
				file.setContentType(doc.getFormatoFile());
				AllegatiDTO allDoc = this.allegatoSvc.uploadAllegatoReferenteDaMigrazione(praticaToBe, anagrafica,(MultipartFile)file);
				allDoc.setDataCaricamento(doc.getDataCaricamentoFile());
				allegatiDao.update(allDoc);
				docReferente.setIdAllegato(allDoc.getId());	
			}
			
		}
		referentiDocDao.insert(docReferente);
	}

	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = false,transactionManager = DatabaseConfiguration.TX_WRITE)
	public void aggiornaAlfrescoId(AllegatiDTO allDTO) throws Exception {
		if (allDTO.getIdCms().equalsIgnoreCase(AllegatoService.PLACEHOLDERTEMPIDCMS)) {
			
//			AlfrescoItemBean alfrescoBean = httpSvc.getCmsObjectByPath(cmsUrlGetObjectByPath,
//					allDTO.getPathCms() + "/" + allDTO.getNomeFile(), nomeApplicazione.toLowerCase(), true);
			String idCmis=this.uploadFromLocale(allDTO.getPathCms()+"/" + allDTO.getNomeFile());
//			if (alfrescoBean.getId() != null) {
			if(StringUtil.isNotEmpty(idCmis)) {
				allDTO.setIdCms(idCmis);
				allegatiDao.update(allDTO);
			}else {
				log.error(LOG_MIGRAZIONE_MARKER, "Errore nella conversione dell'id cms nell'allegato id:"+allDTO.getId()+ " nomefile:"+allDTO.getNomeFile());
			}
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = false,transactionManager = DatabaseConfiguration.TX_WRITE)
	public void aggiornaAlfrescoId(RicevutaDTO ricevutaDTO) throws Exception {
		if(StringUtil.isNotEmpty(ricevutaDTO.getIdCmsDatiCert()) && 
				ricevutaDTO.getIdCmsDatiCert().startsWith(AllegatoService.PLACEHOLDERTEMPIDCMS)){
			final String pathCms=ricevutaDTO.getIdCmsDatiCert().replace(AllegatoService.PLACEHOLDERTEMPIDCMS, "");
			String idCmis=this.uploadFromLocale(pathCms);
//			AlfrescoItemBean alfrescoBean = httpSvc.getCmsObjectByPath(cmsUrlGetObjectByPath,
//					pathCms, nomeApplicazione.toLowerCase(), true);
//			if (alfrescoBean.getId() != null) {
			if(StringUtil.isNotEmpty(idCmis)) {
				ricevutaDTO.setIdCmsDatiCert(idCmis);
				ricevutaDTO.setIdCmsEml(idCmis);
				ricevutaDTO.setIdCmsSmime(idCmis);
				ricevutaDao.update(ricevutaDTO);
			}else {
				log.error(LOG_MIGRAZIONE_MARKER, "Errore nella conversione dell'id cms nella ricevuta  pec per "+ pathCms + " id:"+ricevutaDTO.getId());
			}
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = false,transactionManager = DatabaseConfiguration.TX_WRITE)
	public void aggiornaAlfrescoId(CorrispondenzaDTO corr) throws Exception {
		if(StringUtil.isNotEmpty(corr.getIdEmlCmis()) && 
				corr.getIdEmlCmis().startsWith(AllegatoService.PLACEHOLDERTEMPIDCMS)){
			final String pathCms=corr.getIdEmlCmis().replace(AllegatoService.PLACEHOLDERTEMPIDCMS, "");
			String idCmis=this.uploadFromLocale(pathCms);
//			AlfrescoItemBean alfrescoBean = httpSvc.getCmsObjectByPath(cmsUrlGetObjectByPath,
//					pathCms, nomeApplicazione.toLowerCase(), true);
//			if (alfrescoBean.getId() != null) {
			if(StringUtil.isNotEmpty(idCmis)) {
				corr.setIdEmlCmis(idCmis);
				corrDao.update(corr);
			}else {
				log.error(LOG_MIGRAZIONE_MARKER, "Errore nella conversione dell'id cms nel record corrispondenza  per "+ pathCms + " id:"+corr.getId());
			}
		}
	}
	
	private  String uploadFromLocale(String pathCms) throws Exception {
		Path pathFile= Path.of(this.localBasePath,pathCms);
		Path pathCmsParent=Path.of(pathCms);
		File file = pathFile.toFile();
		if(file.length()>maxFileUploadCms) {
			//throw new Exception("Dimensione del file "+pathFile+ "("+file.length() +")  supera il massimo di "+maxFileUploadCms);
			throw new FileSizeToLargeException("Dimensione del file "+pathFile+ "("+file.length() +")  supera il massimo di "+maxFileUploadCms);
		}
		if(!file.exists()) throw new Exception("File non trovato localmente:"+pathFile);
		String idCmis = httpSvc.uploadCms(file, uploadUrl,pathCmsParent.getParent().toString().replace('\\', '/'), nomeApplicazione, true);
		return idCmis;
	}
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, timeout = 60000, readOnly = false, transactionManager = DatabaseConfiguration.TX_WRITE)
	public void aggiornaQualificazioneInterventoPratica(VtnoAttivitaPptr infoPratica) throws Exception {
		RiepilogoPraticaCivilia riepPrat = new RiepilogoPraticaCivilia();
		PraticaDTO praticaToBe = new PraticaDTO();
		if (comuniCompletoCodIstat == null) {
			comuniCompletoCodIstat = praticaCivSvc.getComuniCompletoCodIstat();
		}
		if (entiCommon == null) {
			entiCommon = commonSvc.getAllEnti();
		}
		try {
			praticaToBe = this.praticaDao.findByTPraticaId(infoPratica.gettPraticaId());
		} catch (EmptyResultDataAccessException e) {
			throw new FascicoloPreesistenteException("Fascicolo con tpratica_id=" + infoPratica.gettPraticaId() + " non preesistente, impossibile aggiornare!");
		}
		Long maxProgCittadino = this.praticaCivSvc.getMaxProgCittadino(infoPratica.gettPraticaCodice());
		if (maxProgCittadino == null) {
			throw new MigrationException("MaxProgrCittadino a null su tpratica_id=" + infoPratica.gettPraticaId() + "");
		}
		migraQualificazioneIntervento(infoPratica, riepPrat.isPresentata, praticaToBe, maxProgCittadino);
	}
	
}
