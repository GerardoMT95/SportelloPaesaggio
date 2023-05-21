package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller;

import java.awt.print.Book;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.ApplicationProperties;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.DettaglioCorrispondenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.SelectOptionDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.AttivitaDaEspletare;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.AuthClient;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.CorrispondenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DestinatarioDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.PraticaSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.ReferentiSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.IReferentiService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.ITipoAziendaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.PM_GruppiRuoli;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.repository.CommonRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.service.IReportService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.AllegatoService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.ComunicazioniService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.FascicoloService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.IFirmaRemotaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria.IstrPraticaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria.ProvvedimentoFinaleService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.Logging;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.bean.UserDetail;
import it.eng.tz.puglia.configuration.swagger.OpenApiConfig;
import it.eng.tz.puglia.error.exception.CustomCmisException;
import it.eng.tz.puglia.error.exception.CustomOperationException;
import it.eng.tz.puglia.error.exception.CustomUnauthorizedException;
import it.eng.tz.puglia.error.exception.CustomValidationException;
import it.eng.tz.puglia.security.util.SecurityUtil;
import it.eng.tz.puglia.service.http.bean.CmsDownloadResponseBean;
import it.eng.tz.puglia.service.http.impl.HttpClientService;
import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.util.log.LogUtil;
import it.eng.tz.puglia.util.string.StringUtil;

@RestController
@RequestMapping(value = "public", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PublicController extends RoleAwareController {

	private enum TipoLinkInMail{
		ULTERIORE_DOCUMENTAZIONE,
		ALLEGATO_COMUNICAZIONE
	}
	
	private enum CodiciTemplateEnum{
		DELEGA_CMIS("DELEGA-CMIS"),
		SOLLEVAMENTO_INCARICO_CMIS("SOLLEVAMENTO-INCARICO-CMIS");
		private final String key;
		private CodiciTemplateEnum(final String key) {
			this.key = key;
		}
	
		String getKey() {
			return this.key;
		}

	}
	
	private static final Logger log = LoggerFactory.getLogger(PublicController.class);

	@Autowired private AllegatoService allegatiService;
	@Autowired private FascicoloService fascicoloSvc;
	@Autowired private ProvvedimentoFinaleService provvSvc;
	@Autowired private HttpClientService httpClientService;
	@Autowired private AuthClient authClient;
	@Autowired private ComunicazioniService comunicazioniService;
	@Autowired private IReferentiService referentiService;
	@Autowired private IstrPraticaService istrPraticaService;
	@Autowired private ProvvedimentoFinaleService provvedimentoService;
	@Autowired private IReportService reportService;
	@Autowired ApplicationProperties props;
	@Autowired CommonRepository commonDao;

	@Value("${enable.mock.firmaremota:false}")
	private Boolean enableMockFirmaRemota;
	
	@Autowired 
	private ITipoAziendaService tipoAziendaService;
	
	@Value("${pm.codice.applicazione}")
	private String codiceApplicazioneProfile;

	@GetMapping(value = "fascicolo/test", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<BaseRestResponse<String>> test() throws Exception {
		final String prefixUrl = this.commonDao.getConfigurationValue(ComunicazioniService.KEY_URL_DOWNLOAD_PREFIX,
				this.props.getCodiceApplicazione());
		return super.okResponse(prefixUrl);
	}
	
	
	@GetMapping(value = "downloadDocumentoDaMail/testHeaders", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<BaseRestResponse<List<String>>> test(final HttpServletRequest request) throws Exception {
		final Enumeration<String> headers = request.getHeaderNames();
		final List<String> sbOut = new ArrayList<String>();
		final Authentication ret = SecurityUtil.getAuthentication();
		sbOut.add("INFO  < SecurityUtil.getAuthentication() > = " + ret);
		final String jwt = SecurityUtil.getJwtToken();
		sbOut.add("INFO  < SecurityUtil.getJwtToken() > = " + jwt);
		final String username = SecurityUtil.getUsername();
		sbOut.add("INFO  < SecurityUtil.getUsername() > = " + username);
		final UserDetail user = SecurityUtil.getUserDetail();
		sbOut.add("INFO  < SecurityUtil.getUserdetail() > = " + user);
		final Iterator<String> iter = headers.asIterator();
		while (iter.hasNext()) {
			final String header = iter.next();
			final String headerVal = request.getHeader(header);
			sbOut.add("HEADER < " + header + "> = " + headerVal);
		}
		return super.okResponse(sbOut);
	}
	
	private final static String MANUALE_SPORTELLO="MANUALE-SPORTELLO-CMIS-ID";
	
	private void doDownloadFromCmisId(final String applicationKey,final HttpServletResponse response) {
		final StopWatch sw = LogUtil.startLog("doDownloadFromCmisId");
		try {
			final String configKey=applicationKey;
			final String idAlfresco=this.commonDao.getConfigurationValue(configKey,this.props.getCodiceApplicazione());
			if(!StringUtil.isBlank(idAlfresco)) {
				this.allegatiService.doDownloadFromCms(idAlfresco, response);
			}else {
				throw new Exception(configKey+" mancante in configurazione o vuoto");
			}
		} catch (final Exception e) {
			this.logger.error("Errore nel download del template  "+applicationKey,e);
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		} finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
	
	@GetMapping(value = "downloadManualeSportello.pjson", produces = MEDIA_TYPE)
	public void downloadmanualeSportello(final HttpServletResponse response) throws Exception {
		this.doDownloadFromCmisId(MANUALE_SPORTELLO,response);
	}

//	
//	@GetMapping(value = "downloadManualeIstruttoria.pjson", produces = MEDIA_TYPE)
//	public void downloadmanualeIstruttoria(final HttpServletResponse response) throws Exception {
//		this.doDownloadFromCmisId(MANUALE_ISTRUTTORIA,response);
//	}
//	
	/**
	 * download in generale di un template precaricato pubblico
	 * @author acolaianni
	 *,DELEGA-CMIS
	  ,SOLLEVAMENTO-INCARICO-CMIS
	 * @param codiceTemplate
	 * @param response
	 * @throws Exception
	 */
	@GetMapping(value = "downloadTemplateByCodice/{codiceTemplate}", produces = MEDIA_TYPE)
	public void downloadmanualeTemplate(@PathVariable CodiciTemplateEnum codiceTemplate,
			final HttpServletResponse response) throws Exception {
		this.doDownloadFromCmisId(codiceTemplate.getKey(),response);
	}
	
	/**
	 * effettua download a partire dal codice trasmissione
	 * @author acolaianni
	 *
	 * @param codice
	 * @param response
	 * @throws Exception
	 */
	@GetMapping(value = "fascicolo/downloadProvvedimentoByCodice", produces = MEDIA_TYPE)
	public void downloadProvvedimentoByCodice(@RequestParam(name = "codice", required = true) final String codice,final HttpServletResponse response) throws Exception {
		log.info("Chiamato il controller: 'public/fascicolo'. Metodo: '" + new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		log.info("Start download");
		final StopWatch sw = LogUtil.startLog("download");
		try {
			final PraticaDTO pratica = this.fascicoloSvc.findPraticaPubblica(codice);
			//se lo trovo nelle liste pubbliche... OK posso prelevare il provvedimento...
		    if(pratica==null) {
		    	throw new CustomOperationToAdviceException("Errore nel retrieve dell'autorizzazione con codice "+codice);
		    }
		    final List<AllegatiDTO> allegati = this.provvSvc.findAllegatiProvvedimenti(pratica.getId());
		    if(allegati==null || allegati.size()==0) {
		    	throw new CustomOperationToAdviceException("Errore nel retrieve del provvedimento per la pratica con codice "+codice);
		    }
		    final Optional<AllegatiDTO> optAllegatoPubblico = allegati.stream()
					.filter(all -> all.getTipiContenuto() != null && all.getTipiContenuto().stream()
							.filter(contenuto -> contenuto.equals("951")).findAny().isPresent())
					.findAny();
		    if (optAllegatoPubblico==null || optAllegatoPubblico.isEmpty()) {
		    	throw new CustomOperationToAdviceException("Nessun provvedimento pubblicabile nella pratica con codice "+codice);
		    }
		    final CmsDownloadResponseBean cms = this.allegatiService.downloadAllegatoGenerico(optAllegatoPubblico.get().getId());
		 	if (cms == null || cms.getFileName() == null)
		 				throw new CustomOperationToAdviceException("Nessun file trovato...");
		 	final File f = new File(cms.getFileName());
//			InputStream is = new FileInputStream(f);
//			HttpHeaders headers = new HttpHeaders();
//			headers.setContentDisposition(ContentDisposition.builder("inline").build());
//			headers.setContentLength(f.length());
//			try {
//				headers.setContentType(MediaType.parseMediaType(cms.getContentType()));
//			} catch (Exception e) {
//				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//			}
//			resource = new InputStreamResource(is);
//			return ResponseEntity.status(HttpStatus.OK).headers(headers).body(resource);
		 	this.copyToResponse(response, f, cms.getFilePathName(),cms.getContentType());
		} catch (final Exception e) {
			log.error("Error in download", e);
			response.setStatus(500);
			//return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		} finally {
			log.info(LogUtil.stopLog(sw));
		}
	}

	
	/**
	 * verifica se è uno dei destinatari oppure se è loggato deve aver accesso alla comunicazione.
	 * @author acolaianni
	 *
	 * @param idDocumento
	 * @param idCorrispondenza
	 * @param response
	 * @param request
	 */
	@GetMapping(ComunicazioniService.PUBLIC_DOWNLOAD_ALLEGATO_MAIL)
	public void downloadAllegatoComunicazione(@RequestParam final String idDocumento,
			@RequestParam final Long idCorrispondenza,
			final HttpServletResponse response,final HttpServletRequest request) {
		log.info("Start downloadAllegatoComunicazione");
		final StopWatch sw = LogUtil.startLog("download");
		try {
			this.checkAccessoAllegatoCorrispondenza(idDocumento, response, request,TipoLinkInMail.ALLEGATO_COMUNICAZIONE);
		} catch (final Exception e) {
			log.error("Error in downloadAllegatoComunicazione", e);
			response.setStatus(500);
		} finally {
			log.info(LogUtil.stopLog(sw));
		}
	}
	
	@GetMapping(value = ComunicazioniService.PUBLIC_DOWNLOAD_ULT_DOC, produces = MEDIA_TYPE)
	public void downloadDoc(@RequestParam final String idDocumento,final HttpServletResponse response,final HttpServletRequest request) {
		log.info("Start download");
		final StopWatch sw = LogUtil.startLog("download");
		try {
			this.checkAccessoAllegatoCorrispondenza(idDocumento, response, request,TipoLinkInMail.ULTERIORE_DOCUMENTAZIONE);
		} catch (final Exception e) {
			log.error("Error in download", e);
			response.setStatus(500);
		} finally {
			log.info(LogUtil.stopLog(sw));
		}
	}


	private void checkAccessoAllegatoCorrispondenza(final String idDocumento, final HttpServletResponse response,
			final HttpServletRequest request, final TipoLinkInMail tipoLink) throws Exception, CustomOperationException, IOException,
			CustomValidationException, CustomCmisException, CustomOperationToAdviceException, FileNotFoundException {
		boolean check=true;
		String username=null;
		if(request.getHeader("username") != null) {
			username = request.getHeader("username") ;
			final String email = request.getHeader("emailaddress");
			log.info("downloadDoc "+username);
			//check sui riferimenti passati in input....
			final CorrispondenzaDTO corrispondenza = this.comunicazioniService.getCorrispondenzaFromAllegato(UUID.fromString(idDocumento));
			if(corrispondenza==null) {
				log.info("error, nessuna corrispondenza trovata dall'allegato {}",idDocumento);
				response.setStatus(404);
				return;
			}
			final PraticaDTO pratica = this.fascicoloSvc.findPraticaFromCorrispondenza(corrispondenza.getId());
			if(pratica==null) {
				log.info("error, nessuna pratica trovata dalla corrispondenza {}",corrispondenza.getId());
				response.setStatus(404);
				return;
			}
			//se i riferimenti sono corretti passo a verificare se magari è uno dei destinatari della mail
			boolean isDestinatario=false;
			final DettaglioCorrispondenzaDTO detCorr = this.comunicazioniService.find(corrispondenza.getId());
			if(detCorr!=null && ListUtil.isNotEmpty(detCorr.getDestinatari())) {
				for(final DestinatarioDTO destinatario:detCorr.getDestinatari()){
					if(destinatario.getEmail().equals(email)) {
						isDestinatario=true;
						break;
					}
				}	
			}
			if(isDestinatario) {
				this.downloadAndSetResponse(idDocumento, response);
				return;
			}
			//verifico se è un utente che appartiene ad uno dei gruppi che vede l'istanza in istruttoria oppure è l'owner lato sportello.
			final String jwt = this.httpClientService.getBatchUser().getAuthorization();
			final List<PM_GruppiRuoli> infoUtente = this.authClient.findGruppiFromUserDetails(jwt, this.codiceApplicazioneProfile, username).getPayload();
			PraticaDTO praticaSearched=null;
			praticaSearched= this.checkAccessoByUsernameIstruttoria(infoUtente,pratica.getId(), username, praticaSearched,null);
			if(praticaSearched==null && !pratica.getUserId().equals(username)) {
				log.info("error, nessun accesso alla pratica in istruttoria");
				response.setStatus(404);
				return;
			}
			//calcolo tutti i gruppi e tutti i ruoli per la visibilità della comunicazione
			final List<String> idOrganizzazioni= new ArrayList<String>();
			final List<String> gruppi = new ArrayList<String>();
			final List<String> ruoli = new ArrayList<String>();
			for (final PM_GruppiRuoli info : infoUtente) {
				final String[] gruppoRuolo=info.getCodiceGruppo().split("_");
				if(gruppoRuolo.length==3) {
					gruppi.add(gruppoRuolo[0]+"_");
					idOrganizzazioni.add(gruppoRuolo[1]);
					ruoli.add(gruppoRuolo[2]);
				}else {
					gruppi.add(gruppoRuolo[0]);
				}
			}
			//check riservata, se è un RICHIEDENTE non la puo' vedere!
			if(corrispondenza.isRiservata() && 
				gruppi.contains(Gruppi.RICHIEDENTI.name()) && 
				!gruppi.contains(Gruppi.ADMIN.name())) {
				log.info("errore riservata");
				response.setStatus(404);
				return;
			}
			// check se faccio parte del gruppo dell'organizzazione owner della mail
			if(tipoLink.equals(TipoLinkInMail.ULTERIORE_DOCUMENTAZIONE)) {
				if(corrispondenza.getIdOrganizzazioneOwner()!=null && 	
				   idOrganizzazioni.contains(corrispondenza.getIdOrganizzazioneOwner().toString())) {
					this.downloadAndSetResponse(idDocumento, response);
					return;
				}	
			}
			//check visibilità
			if(StringUtil.isNotEmpty(corrispondenza.getVisibilita())) {
				final String[] visibili=corrispondenza.getVisibilita().split(",");
				boolean beccatoUnGruppoVisibile=false;
				for (final String visibile : visibili) {
					if(gruppi.contains(visibile)) {
						beccatoUnGruppoVisibile=true;						
					}
				}
				if(!beccatoUnGruppoVisibile) {
					response.setStatus(404);
					log.info("visibilità non ammessa {} attesa {}",gruppi,corrispondenza.getVisibilita());
					return;
				}
			}
			//verifica che il richiedente si effettivamente il richiedente della pratica
			check=true;
		}else {
			log.info("downloadDoc username mancante nell'header");
		}
		if(check) {
			this.downloadAndSetResponse(idDocumento, response);
		}else {
			response.setStatus(404);
		}
	}


	private void downloadAndSetResponse(final String idDocumento, final HttpServletResponse response)
			throws CustomOperationException, IOException, CustomValidationException, CustomCmisException,
			CustomOperationToAdviceException, FileNotFoundException {
		final CmsDownloadResponseBean cms = this.allegatiService.downloadAllegato(UUID.fromString(idDocumento));
		if (cms == null || cms.getFileName() == null)
			throw new CustomOperationToAdviceException("Nessun file trovato...");
		final File f = new File(cms.getFileName());
		response.setHeader("Access-Control-Expose-Headers","");
		this.copyToResponse(response, f, cms.getFilePathName(),cms.getContentType());
	}
	
	@Logging
	@GetMapping(ComunicazioniService.PUBLIC_DOWNLOAD_DOC_TRASM)
	public void downloadDocumentoTrasmissione(@RequestParam("idPratica") final UUID idPratica, final HttpServletRequest request, final HttpServletResponse response)
	{
		try
		{
			final String roles = request.getHeader("roles");
			final String username = request.getHeader("username");
			String codiceFiscale = request.getHeader("codFiscale");
			final String email = request.getHeader("emailaddress");
			
			if(StringUtil.isNotEmpty(roles) &&  roles.equals("Internal/SPID;Internal/everyone") &&
			   StringUtil.isBlank(codiceFiscale))
				codiceFiscale = StringUtils.split(username, "-")[1];
			
			this.logger.info("Download documento di trasmissione per pratica con id {} richiesta da: \n\nutente {} \nemail {} \ncodice fiscale {}", idPratica, username, email, codiceFiscale);
			
			PraticaDTO pratica = null;
			PraticaSearch psearch = new PraticaSearch();
			List<PraticaDTO> tmp = null;
			final List<DestinatarioDTO> dest = this.provvedimentoService.findDestinatari(idPratica);
			if(StringUtil.isNotBlank(codiceFiscale))
			{
				final ReferentiSearch search = new ReferentiSearch();
				search.setPraticaId(idPratica.toString());
				search.setCodiceFiscale(codiceFiscale);
				if(this.referentiService.count(search) > 0)
				{	
					this.logger.info("Il codice fiscale appartiene ad uno dei referenti della pratica");
					psearch.setId(idPratica);
					pratica = this.istrPraticaService.searchAll(psearch, false).get(0);
				}
			}
			
			if(pratica == null)
			{
				psearch = new PraticaSearch();
				psearch.setUserId(username);
				psearch.setId(idPratica);
				tmp = this.istrPraticaService.searchAll(psearch, false);
				if(tmp != null && !tmp.isEmpty())
					pratica = tmp.get(0);
			}
			
			if(pratica == null && dest != null && dest.stream().anyMatch(p -> p.getEmail().equals(email)))
			{
				psearch = new PraticaSearch();
				psearch.setId(idPratica);
				psearch.setStato(AttivitaDaEspletare.TRANSMITTED.name());
				tmp = this.istrPraticaService.searchAll(psearch, false);
				if(tmp != null && !tmp.isEmpty())
					pratica = tmp.get(0);
			}
			
			if(pratica == null)
			{
				final String jwt = this.httpClientService.getBatchUser().getAuthorization();
				final List<PM_GruppiRuoli> infoUtente = this.authClient.findGruppiFromUserDetails(jwt, this.codiceApplicazioneProfile, username).getPayload();
				pratica = this.checkAccessoByUsernameIstruttoria(infoUtente,idPratica, username, pratica,AttivitaDaEspletare.TRANSMITTED.name());
			}
			
			if(pratica != null)
			{
				final List<AllegatiDTO> all = this.provvedimentoService.findDocumentiTrasmissione(idPratica);
				this.logger.info("{} allegati trovati:  {}", all.size(), all);
				final AllegatiDTO allegato = all.stream().filter(p -> p.getType().equals("1101") || p.getTipiContenuto().contains("1101")).findFirst().get();
				final CmsDownloadResponseBean cms = this.allegatiService.downloadAllegatoGenerico(allegato.getId());
				if (cms == null || cms.getFileName() == null)
					throw new CustomOperationToAdviceException("Nessun file trovato...");
				final File f = new File(cms.getFileName());
				response.setHeader("Access-Control-Expose-Headers","");
				this.copyToResponse(response, f, cms.getFilePathName(),cms.getContentType());
				
			}
			else
			{
				this.logger.warn("Errore: l'utente {} non ha i permessi per vedere la pratica con id {}", username, idPratica);
				response.setStatus(404);
			}
		}
		catch (final Exception e) 
		{
			log.error("Errore durante il download del documento di trasmissione", e);
			response.setStatus(500);
		}
	}

	private PraticaDTO checkAccessoByUsernameIstruttoria(final List<PM_GruppiRuoli> infoUtente,final UUID idPratica, final String username, PraticaDTO pratica,final String stato) throws Exception {
		PraticaSearch psearch;
		List<PraticaDTO> tmp;
		Integer j = 0;
		while(pratica == null && j < infoUtente.size())
		{
			final PM_GruppiRuoli info = infoUtente.get(j++);
			if(Gruppi.isGruppoConOrganizzazione(info.getCodiceGruppo()))
			{
				psearch = new PraticaSearch();
				psearch.setId(idPratica);
				psearch.setStato(stato);
				this.istrPraticaService.prepareForSearch(psearch, info.getCodiceGruppo(), username);
				tmp = this.istrPraticaService.searchAll(psearch, false);
				if(tmp != null && !tmp.isEmpty())
					{
					log.info("accesso alla pratica in istruttoria con gruppo "+info.getCodiceGruppo());
					pratica = tmp.get(0);
					break; //non serve testare altro...
					}
				
			}
		}
		return pratica;
	}
	/**
	 * @author Antonio La Gatta
	 * @date 9 giu 2022
	 * @return Tipo azienda
	 */
	@Logging
	@RequestMapping(value="/lista-tipo-azienda", method = {RequestMethod.GET})
	public ResponseEntity<BaseRestResponse<List<SelectOptionDto>>> listaTipoAzienda(){
		final StopWatch sw = LogUtil.startLog("listaTipoAzienda");
		try{
			return super.okResponse(this.tipoAziendaService.getList());
		}catch(final Exception e){
			this.logger.error("Errore in listaTipoAzienda", e);
			return super.koResponse(e, new BaseRestResponse<>());
		}finally{
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
	
	@Operation(summary = "Download Report", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Risposta in caso di operazione eseguita con stato ok/ko", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "400", description = "Risposta in caso di dati inviati non validi", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "401", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "403", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "405", description = "Risposta in caso di invocazione con METHOD errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "415", description = "Risposta in caso di content type errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "500", description = "Errore di sistema", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      }
			     )
	@RequestMapping(value = "downloadDocumentoDaMail/download-report/{idDownload}", method= {RequestMethod.GET})
	public void downloadReport(
			@Parameter(description = "Id download") 
			@PathVariable final String idDownload, 
			@RequestHeader("username") final String username,
			final HttpServletResponse response) throws Exception {
		LogUtil.addUser(username);
		final StopWatch sw = LogUtil.startLog("downloadReport ", idDownload, " ", username);
		try {
			this.reportService.downloadReport(idDownload, username, response);
		}finally {
			this.logger.info(LogUtil.stopLog(sw));
			LogUtil.removeKeys();
		
		}
	}
	
	@Operation(summary = "Download Provvedimento Pubblico", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Risposta in caso di operazione eseguita con stato ok/ko", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "400", description = "Risposta in caso di dati inviati non validi", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "401", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "403", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "405", description = "Risposta in caso di invocazione con METHOD errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "415", description = "Risposta in caso di content type errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "500", description = "Errore di sistema", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      }
			     )
	@RequestMapping(value = "downloadDocumentoDaMail/download-provvedimento-pubblico/{idPratica}", method= {RequestMethod.GET})
	public void downloadProvvedimentoPubblico(
			@Parameter(description = "Id Pratica") 
			@PathVariable final String idPratica, 
			@RequestHeader("username") final String username,
			final HttpServletResponse response) throws Exception {
		LogUtil.addUser(username);
		final StopWatch sw = LogUtil.startLog("downloadProvvedimentoPubblico ", idPratica, " ", username);
		try {
			List<AllegatiDTO> allegati = this.provvedimentoService.findAllegatiProvvedimenti(UUID.fromString(idPratica));
			if(allegati != null && allegati.size() > 0) {
				final Optional<AllegatiDTO> allegato = allegati.stream().filter(x -> x.getType().equals("951")).findAny();
				if(allegato.isPresent()) {
					CmsDownloadResponseBean cms = allegatiService.downloadAllegato(allegato.get().getId());
					if (cms == null || cms.getFileName() == null)
						throw new CustomOperationToAdviceException("Nessun file trovato...");
					File f = new File(cms.getFileName());
					this.copyToResponse(response, f, cms.getFilePathName(),cms.getContentType());
				} else {
					throw new CustomUnauthorizedException("Provvedimento pubblico non trovato");
				}
			}
		}finally {
			this.logger.info(LogUtil.stopLog(sw));
			LogUtil.removeKeys();
		
		}
	}
	
	@Operation(summary = "Download Provvedimento Privato", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Risposta in caso di operazione eseguita con stato ok/ko", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "400", description = "Risposta in caso di dati inviati non validi", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "401", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "403", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "405", description = "Risposta in caso di invocazione con METHOD errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "415", description = "Risposta in caso di content type errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "500", description = "Errore di sistema", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      }
			     )
	@RequestMapping(value = "downloadDocumentoDaMail/download-provvedimento-privato/{idPratica}", method= {RequestMethod.GET})
	public void downloadProvvedimentoPrivato(
			@Parameter(description = "Id Pratica") 
			@PathVariable final String idPratica, 
			@RequestHeader("username") final String username,
			final HttpServletResponse response) throws Exception {
		LogUtil.addUser(username);
		final StopWatch sw = LogUtil.startLog("downloadProvvedimentoPrivato ", idPratica, " ", username);
		try {
			List<AllegatiDTO> allegati = this.provvedimentoService.findAllegatiProvvedimenti(UUID.fromString(idPratica));
			if(allegati != null && allegati.size() > 0) {
				final Optional<AllegatiDTO> allegato = allegati.stream().filter(x -> x.getType().equals("950")).findAny();
				if(allegato.isPresent()) {
					CmsDownloadResponseBean cms = allegatiService.downloadAllegato(allegato.get().getId());
					if (cms == null || cms.getFileName() == null)
						throw new CustomOperationToAdviceException("Nessun file trovato...");
					File f = new File(cms.getFileName());
					this.copyToResponse(response, f, cms.getFilePathName(),cms.getContentType());
				} else {
					throw new CustomUnauthorizedException("Provvedimento privato non trovato");
				}
			}
		}finally {
			this.logger.info(LogUtil.stopLog(sw));
			LogUtil.removeKeys();
		
		}
	}

	@Autowired
	IFirmaRemotaService firmaremotaSvc;
	/**
	 * firma in pades i pdf, usato solo per scopi di test in quando il firmatario è dummy.
	 * @author acolaianni
	 *
	 * @param file
	 * @param response
	 * @throws Exception
	 */
	@PostMapping(value = "firmaRemotaMock", consumes = {"multipart/form-data" }, produces = MEDIA_TYPE)
	public void firmaRemotaMock(
			@RequestPart("file") MultipartFile file,
			final HttpServletResponse response) throws Exception {
		final StopWatch sw = LogUtil.startLog("firmaRemotaMock ");
		try {
			if(enableMockFirmaRemota!=null && enableMockFirmaRemota) {
				File f=this.firmaremotaSvc.firmaDocumentoMock(file);
				super.copyToResponse(response, f, file.getOriginalFilename().toUpperCase().replace(".PDF", "-SIGNED.PDF"));
			}else {
				response.setStatus(404);
				logger.error("firmaRemotaMock not enabled!");
			}
		}finally {
			this.logger.info(LogUtil.stopLog(sw));
			LogUtil.removeKeys();
		
		}
	}

	
}