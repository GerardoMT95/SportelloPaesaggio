package it.eng.tz.puglia.autpae.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import it.eng.tz.puglia.autpae.config.ApplicationProperties;
import it.eng.tz.puglia.autpae.controller.exception.CustomOperationToAdviceException;
import it.eng.tz.puglia.autpae.dto.AcceleratoriFascicoloDTO;
import it.eng.tz.puglia.autpae.dto.DettaglioCorrispondenzaDTO;
import it.eng.tz.puglia.autpae.dto.UlterioreDocumentazioneDTO;
import it.eng.tz.puglia.autpae.dto.BE_to_FE.SchemaRicercaDTO;
import it.eng.tz.puglia.autpae.entity.AllegatoDTO;
import it.eng.tz.puglia.autpae.entity.FascicoloCorrispondenzaDTO;
import it.eng.tz.puglia.autpae.entity.FascicoloDTO;
import it.eng.tz.puglia.autpae.entity.FascicoloPublicDto;
import it.eng.tz.puglia.autpae.entity.RichiedenteDTO;
import it.eng.tz.puglia.autpae.entity.TipoProcedimentoDTO;
import it.eng.tz.puglia.autpae.entity.composedKeys.AllegatoCorrispondenzaPK;
import it.eng.tz.puglia.autpae.enumeratori.Gruppi;
import it.eng.tz.puglia.autpae.enumeratori.TipoAllegato;
import it.eng.tz.puglia.autpae.search.FascicoloCorrispondenzaSearch;
import it.eng.tz.puglia.autpae.search.FascicoloSearch;
import it.eng.tz.puglia.autpae.search.UlterioreDocumentazioneSearch;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoCorrispondenzaService;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoService;
import it.eng.tz.puglia.autpae.service.interfacce.ConfigurazionePermessiService;
import it.eng.tz.puglia.autpae.service.interfacce.CorrispondenzaService;
import it.eng.tz.puglia.autpae.service.interfacce.FascicoloCorrispondenzaService;
import it.eng.tz.puglia.autpae.service.interfacce.FascicoloService;
import it.eng.tz.puglia.autpae.service.interfacce.TipoProcedimentoService;
import it.eng.tz.puglia.autpae.service.interfacce.UlterioreDocumentazioneService;
import it.eng.tz.puglia.autpae.utility.FileWrapper;
import it.eng.tz.puglia.autpae.utility.LoggingAet;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.jasper.CreaReportService;
import it.eng.tz.puglia.servizi_esterni.profileManager.dto.PM_GruppiRuoli;
import it.eng.tz.puglia.servizi_esterni.profileManager.feign.IProfileManager;
import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.util.string.StringUtil;

@RestController
@RequestMapping(value = "public", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PublicController extends _RestController {
	
	private enum TipoLinkInMail{
		RICEVUTA_TRASMISSIONE,
		ULTERIORE_DOCUMENTAZIONE,
		ALLEGATO_COMUNICAZIONE,
		PROVVEDIMENTO_FINALE,
	}
	
	private enum TipoGrant{
		CF, //ha un cf intestatario
		DESTINATARIO_MAIL, //è un destinatario della mail
		APPLICATION_USER //è un utente dell'applicazione
	}

	@Value("${max.righe.export.elenchi.public:100}")
	private Integer maxRigheExportPublic;
	
	private static final Logger log = LoggerFactory.getLogger(PublicController.class);

	@Autowired private FascicoloService fascicoloService;
	@Autowired private CreaReportService creaReportService;
	@Autowired private CorrispondenzaService corrispondenzaService;
	@Autowired private AllegatoService allegatoService;
	@Autowired private AllegatoCorrispondenzaService allegatoCorrispondenzaService;
	@Autowired private ConfigurazionePermessiService configurazionePermessiService;
	@Autowired private IProfileManager profileManager;
	@Autowired private ApplicationProperties props;
	@Autowired private FascicoloCorrispondenzaService fascicoloCorrispondenzaService;
	@Autowired private TipoProcedimentoService tipoProcedimentoService;
	@Autowired private UlterioreDocumentazioneService ultDocService;
	
	@Value("${search.default.limit}")
	private Integer defaultLimit;
	
	
	@GetMapping(value = "checkCF")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<String>> checkCodiceFiscale(@RequestParam(name="codiceFiscale", required=true) String codiceFiscale) throws Exception {
		String res = fascicoloService.checkCodiceFiscale(codiceFiscale);
		
		if (res==null)
			return super.okResponse("OK");
		else
			return super.koResponse(res, new BaseRestResponse<>());
	}
	
	
	@GetMapping(value = "checkPI")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<String>> checkPartitaIVA(@RequestParam(name="partitaIVA", required=true) String partitaIVA) throws Exception	{
		String res = fascicoloService.checkPartitaIVA(partitaIVA);
		
		if (res==null)
			return super.okResponse("OK");
		else
			return super.koResponse(res, new BaseRestResponse<>());
	}
	
	
	@GetMapping(value = "checkCAP")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<String>> checkCAP(@RequestParam(name="cap", required=true) String cap) throws Exception	{
		String res = fascicoloService.checkCAP(cap);
		
		if (res==null)
			return super.okResponse("OK");
		else
			return super.koResponse(res, new BaseRestResponse<>());
	}
	
	
	@GetMapping(value = "checkTelefono")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<String>> checkTelefono(@RequestParam(name="telefono", required=true) String telefono) throws Exception	{
		String res = fascicoloService.checkTelefono(telefono);
		
		if (res==null)
			return super.okResponse("OK");
		else
			return super.koResponse(res, new BaseRestResponse<>());
	}
	
	
	@GetMapping(value = "checkEmail")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<String>> checkEmail(@RequestParam(name="email", required=true) String email) throws Exception {
		String res = corrispondenzaService.checkEmail(email);
		
		if (res==null)
			return super.okResponse("OK");
		else
			return super.koResponse(res, new BaseRestResponse<>());
	}
	
	
	@GetMapping(value = "fascicolo/search")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<PaginatedList<FascicoloDTO>>> searchFascicolo(FascicoloSearch filter) throws Exception {
		filter.setRicercaPubblica(true);
		return super.okResponse(fascicoloService.searchFascicolo(filter));
	}
	
	
	/**
	 * effettua il retrieve sulla vista congiunta tra autpae e presentazione_istanza
	 * attiva il campo per abilitare la navigazione verso il dettaglio del fascicolo
	 * nel caso in cui l'utente è autenticato ed ha almeno un ruolo su autpae o pae_pres_ist
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "fascicolo/searchAll")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<PaginatedList<FascicoloPublicDto>>> searchAllFascicolo(
			FascicoloSearch filter) throws Exception {
		return super.okResponse(fascicoloService.searchPublicFascicolo(filter));
	}
	
	
	@GetMapping(value = "fascicolo/getForAccelerators")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<AcceleratoriFascicoloDTO>> getForAcceleratori(FascicoloSearch filter) throws Exception	{
		filter.setRicercaPubblica(true);
		return super.okResponse(fascicoloService.getCountForAccelerator(filter));
	}
	
	
	@GetMapping(value = "fascicolo/exportFascicoliPDF", produces="application/pdf")
	@LoggingAet
	public ResponseEntity<InputStreamResource> exportFascicoliPDF(FascicoloSearch filter) throws Exception {
		try {
			filter.setLimit(maxRigheExportPublic);
			filter.setRicercaPubblica(true);
			filter.setPage(1);
			PaginatedList<FascicoloPublicDto> lista = fascicoloService.searchPublicFascicolo(filter);
			log.info("Bean per la generazione del file Piani PDF: " + lista);
			return creaReportService.exportFascicoliPDF(lista);
	
		}catch (CustomOperationToAdviceException e) {
			throw e;
		} 
		 catch (Exception e) {
			log.error("Errore nella creazione del file PDF con i risultati di ricerca dei fascicoli", e);
			throw new CustomOperationToAdviceException("Errore nella creazione del file PDF con i risultati di ricerca dei fascicoli");
		}
	}
	
	
	@GetMapping(value = "fascicolo/exportFascicoliCSV", produces="application/csv")
	@LoggingAet
	public ResponseEntity<InputStreamResource> exportFascicoliCSV(FascicoloSearch filter) throws Exception {
		try {
			filter.setLimit(maxRigheExportPublic);
			filter.setRicercaPubblica(true);
			filter.setPage(1);

			PaginatedList<FascicoloPublicDto> lista = fascicoloService.searchPublicFascicolo(filter);
			log.info("Bean per la generazione del file Piani CSV: " + lista);
				return creaReportService.exportFascicoliCSV(lista);
		}catch (CustomOperationToAdviceException e) {
			throw e;
		}
		 catch (Exception e) {
			log.error("Errore nella creazione del file CSV con i risultati di ricerca dei fascicoli", e);
			throw new CustomOperationToAdviceException("Errore nella creazione del file CSV con i risultati di ricerca dei fascicoli");
		}
	}
	
	
	@GetMapping(value = "fascicolo/schemaRicerca")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<SchemaRicercaDTO>> schemaRicerca() throws Exception {
		return super.okResponse(new SchemaRicercaDTO(configurazionePermessiService.find("PUBLIC")));
	}
	
	
	@GetMapping(value = "fascicolo/downloadProvvedimento", produces = MEDIA_TYPE)
	@LoggingAet
	public void downloadProvvedimento(
			@RequestParam(name = "idFascicolo", required = true) long idFascicolo,
			HttpServletResponse response) throws Exception {
		try {
			FileWrapper f = allegatoService.downloadAllegatoByTipoEidFascicolo(TipoAllegato.PROVVEDIMENTO_FINALE, idFascicolo);
			copyToResponse(response, f.getFile(),f.getNomeFile());
		} catch (Exception e) {
			log.error("Errore nel download del file!");
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
	}
	
	@GetMapping(value = "fascicolo/downloadProvvedimentoByCodice", produces = MEDIA_TYPE)
	@LoggingAet
	public void downloadProvvedimentoByCodice(@RequestParam(name = "codice", required = true) String codice,
			HttpServletResponse response) throws Exception {
		try {
			FascicoloSearch fsearch=new FascicoloSearch();
			fsearch.setCodice(codice);
//			//se lo trovo nelle liste pubbliche... OK posso prelevare il provvedimento...
//		    PaginatedList<FascicoloPublicDto> list = fascicoloService.searchPublicFascicolo(fsearch);
//		    if(list.getList()==null || list.getList().size()==0) {
//		    	throw new CustomOperationToAdviceException("Errore nel retrieve dell'autorizzazione con codice "+codice);
//		    }
//		    fsearch.setRicercaPubblica(true);
//		    PaginatedList<FascicoloDTO> listF = fascicoloService.searchFascicolo(fsearch);
//		    if(listF.getList()==null || listF.getList().size()==0) {
//		    	throw new CustomOperationToAdviceException("Errore nel retrieve dell'autorizzazione con codice "+codice);
//		    }
		    PaginatedList<FascicoloPublicDto> listF = fascicoloService.searchPublicFascicolo(fsearch);
		    if(listF.getList()==null || listF.getList().size()==0) {
		    	throw new CustomOperationToAdviceException("Errore nel retrieve dell'autorizzazione con codice "+codice);
		    }
		    FascicoloPublicDto fascicolo = listF.getList().get(0);
		    FileWrapper f = allegatoService.downloadAllegatoByTipoEidFascicolo(TipoAllegato.PROVVEDIMENTO_FINALE, fascicolo.getId());
			copyToResponse(response, f.getFile(),f.getNomeFile());
		} catch (Exception e) {
			log.error("Errore nel download del file!",e);
			response.setStatus(500);
		}
	}
	
	@GetMapping(value = "fascicolo/autocompleteCodice")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<List<String>>> autocompleteCodice(@RequestParam(value="codice", required=false) String codice) throws Exception {
		return super.okResponse(fascicoloService.autocompleteCodice(codice, defaultLimit));
	}
	
	
	@GetMapping(CorrispondenzaService.PUBLIC_DOWNLOAD_DOC_TRASM)
	@LoggingAet
	public void downloadDocumentoTrasmissione(@RequestParam("idPratica")Long idPratica, 
			@RequestParam("idCorrispondenza")Long idCorrispondenza,
			@RequestParam("idAllegato")Long idAllegato,
			HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			checkAndGetAllegato(idPratica, idCorrispondenza, idAllegato, request, response,TipoLinkInMail.RICEVUTA_TRASMISSIONE);
		}
		catch (Exception e) 
		{
			log.error("Errore durante il download del documento di trasmissione", e);
			response.setStatus(500);
		}
	}

	@GetMapping(CorrispondenzaService.PUBLIC_DOWNLOAD_PROVVEDIMENTO_FINALE)
	@LoggingAet
	public void downloadProvvedimentoFinale(@RequestParam("idPratica")Long idPratica, 
			@RequestParam("idCorrispondenza")Long idCorrispondenza,
			@RequestParam("idAllegato")Long idAllegato,
			HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			checkAndGetAllegato(idPratica, idCorrispondenza, idAllegato, request, response,TipoLinkInMail.PROVVEDIMENTO_FINALE);
		}
		catch (Exception e) 
		{
			log.error("Errore durante il download del documento di trasmissione", e);
			response.setStatus(500);
		}
	}


	private void checkAndGetAllegato(Long idPratica, Long idCorrispondenza, Long idAllegato, HttpServletRequest request,
			HttpServletResponse response, TipoLinkInMail tipoLink)
			throws Exception, IOException, FileNotFoundException {
		TipoGrant tipoGrant=null;
		List<PM_GruppiRuoli> infoUtente=null;
		String roles = request.getHeader("roles");
		String username = request.getHeader("username");
		String codiceFiscale = request.getHeader("codFiscale");
		String email = request.getHeader("emailaddress");
		boolean okToDownload = false;
		if (StringUtil.isNotEmpty(roles) && roles.equals("Internal/SPID;Internal/everyone") && StringUtil.isBlank(codiceFiscale))
			codiceFiscale = StringUtils.split(username, "-")[1];
		logger.info(
				"Download documento allegato alla mail per pratica con id {} richiesta da: \n\nutente {} \nemail {} \ncodice fiscale {} \ntipoLink {}",
				idPratica, username, email, codiceFiscale, tipoLink);
		if(StringUtil.isEmpty(roles)&& StringUtil.isEmpty(username) && StringUtil.isEmpty(email) ) {
			logger.info("Nessuna info minima disponibile tra: roles, username, email !");
			response.setStatus(401);
			return;
		}
		// cerco la pratica
		FascicoloDTO fascicolo = null;
		try {
			fascicolo = fascicoloService.find(idPratica);
			if (fascicolo == null)
				throw new EmptyResultDataAccessException(0);
		} catch (EmptyResultDataAccessException e) {
			logger.warn("Errore: pratica non trovata: id {}", idPratica);
			response.setStatus(404);
			return;
		}
		// cerco la chiave corrispondenza/allegato in allegato_corrispondenza.
		try {
			allegatoCorrispondenzaService
					.find(new AllegatoCorrispondenzaPK(idAllegato, idCorrispondenza));
		} catch (EmptyResultDataAccessException e) {
			logger.warn("Errore: allegato in corrispondenza non trovata: idAllegato {}  idCorrispondenza", idAllegato,
					idCorrispondenza);
			response.setStatus(404);
			return;
		}
		// cerco la chiave corrispondenza/fascicolo
		FascicoloCorrispondenzaSearch fcS = new FascicoloCorrispondenzaSearch(null, idCorrispondenza, idPratica);
		PaginatedList<FascicoloCorrispondenzaDTO> fscRet = fascicoloCorrispondenzaService.search(fcS);
		if (fscRet == null || fscRet.getCount() <= 0) {
			logger.warn("Errore: corrispondenza in fascicolo non trovata: idPratica {}  idCorrispondenza", idPratica,
					idCorrispondenza);
			response.setStatus(404);
			return;
		}
		// ok riferimenti corretti....
		// vediamo se ha permessi per accedervi
		RichiedenteDTO richiedente = fascicolo.getInfoComplete().getRichiedente();
		if (List.of(TipoLinkInMail.RICEVUTA_TRASMISSIONE,TipoLinkInMail.PROVVEDIMENTO_FINALE).contains(tipoLink)
				&& richiedente.getCodiceFiscale().equals(codiceFiscale)) {
			// ok puo' scaricare.
			okToDownload = true;
			tipoGrant=TipoGrant.CF;
		}
		if (!okToDownload) {
			// cerco se la mail dell'utente è tra i destinatari della trasmissione.
			DettaglioCorrispondenzaDTO dettCorr = corrispondenzaService.dettaglioCorrispondenza(idCorrispondenza, true);
			okToDownload = dettCorr.getDestinatari().stream()
					.filter(destinatario -> destinatario.getEmail().equals(email))
					.peek((dest) -> logger.info("destinatario presente nella mail")).findAny().isPresent();
			if(okToDownload) {
				tipoGrant=TipoGrant.DESTINATARIO_MAIL;
			}
		}
		if (!okToDownload && StringUtil.isNotBlank(username)) {
			// verifico se è uno degli utenti ammessi all'accesso alla pratica
			String jwt = httpClientService.getBatchUser().getAuthorization();
			infoUtente = profileManager
					.findGruppiFromUserDetails(jwt, props.getCodiceApplicazioneProfile(), username).getPayload();
			FascicoloSearch searchF = new FascicoloSearch();
			searchF.setRicercaPubblica(false);
			searchF.setId(idPratica);
			if (infoUtente != null && infoUtente.size() > 0) {
				okToDownload = infoUtente.stream().filter(infoUser -> {
					infoUser.getCodiceGruppo();
					PaginatedList<FascicoloDTO> list = null;
					try {
						fascicoloService.prepareForSearch(searchF, infoUser.getCodiceGruppo());
						list = fascicoloService.search(searchF);
					} catch (Exception e) {
						logger.error("errore durante la prepare for search ", e);
					}
					return list != null && list.getCount() > 0;
				}).findAny().isPresent();
			}
			if(okToDownload) {
				tipoGrant=TipoGrant.APPLICATION_USER;
			}
		}
		//per l'ulteriore documentazione il tipoGrant sarà solo per DESTINATARIO_MAIL o APPLICATION_USER (no CF)
		if (tipoLink.equals(TipoLinkInMail.ULTERIORE_DOCUMENTAZIONE) && okToDownload && 
				tipoGrant.equals(TipoGrant.APPLICATION_USER)) {
			okToDownload = false;
			if(ListUtil.isEmpty(infoUtente)) {
				response.setStatus(404);
				return;
			}
			Set<String> gruppiUtente=infoUtente.stream().map(infoUser -> {
				return Gruppi.fromCodiceGruppoPM(infoUser.getCodiceGruppo()).name();
			}).filter(gruppo->gruppo!=null).collect(Collectors.toSet());
			try {
				// devo controllare che rispetti la visibilità inserita ED / SBAP / ETI
				UlterioreDocumentazioneSearch searchUltDoc = new UlterioreDocumentazioneSearch();
				searchUltDoc.setIdFascicolo(idPratica);
				PaginatedList<UlterioreDocumentazioneDTO> listUltDocs = ultDocService
						.searchDocumentazioneWithoutCheckVisibilita(searchUltDoc);
				if (ListUtil.isNotEmpty(listUltDocs.getList())) {
					// cerco il riferimento idAllegato...
					for (UlterioreDocumentazioneDTO ultDoc : listUltDocs.getList()) {
						if (ultDoc.getId().equals(idAllegato)) {
							//se ho uno dei tipi gruppo tra quelli impostati nelle visibilità... OK
							CERCA_ENTE:for(String ente:ultDoc.getEnti()) {
								if(gruppiUtente.contains(ente)){
									okToDownload = true;
									break CERCA_ENTE;
								}
							}
							break;
						}
					}
				}
			} catch (CustomOperationToAdviceException e) {
			}
		}
		if (okToDownload) {
			File f = allegatoService.downloadFile(null, idAllegato, null, idCorrispondenza);
			AllegatoDTO allData = allegatoService.find(idAllegato);
			response.setHeader("Access-Control-Expose-Headers", "");
			copyToResponse(response, f, allData.getNome());
		} else {
			logger.warn("Errore: l'utente {} non ha i permessi per vedere la pratica con id {}", username, idPratica);
			response.setStatus(404);
		}
	}
	
	@ApiOperation("Lista tipi procedimento tutti ")
	@GetMapping(value ="/getAllTipoProcedimentoByApp",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@LoggingAet
	public ResponseEntity<BaseRestResponse<List<TipoProcedimentoDTO>>> getTipiProcedimento(String application) throws Exception {
		return super.okResponse(tipoProcedimentoService.selectAllByApplication());
	}
	
	/**
	 * 
	 * @autor Adriano Colaianni
	 * @date 16 lug 2021
	 * @param idPratica
	 * @param idCorrispondenza
	 * @param idAllegato
	 * @param request
	 * @param response
	 */
	@GetMapping(CorrispondenzaService.PUBLIC_DOWNLOAD_ULT_DOC)
	@LoggingAet
	public void downloadDocUlterioreDoc(@RequestParam("idPratica")Long idPratica, 
			@RequestParam("idCorrispondenza")Long idCorrispondenza,
			@RequestParam("idAllegato")Long idAllegato,
			HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			checkAndGetAllegato(idPratica, idCorrispondenza, idAllegato, request, response,TipoLinkInMail.ULTERIORE_DOCUMENTAZIONE);
		}
		catch (Exception e) 
		{
			log.error("Errore durante il download del documento allegato alla comunicazione di ulteriore documentazione", e);
			response.setStatus(500);
		}
	}
	
	
	/**
	 * 
	 * @autor Adriano Colaianni
	 * @date 16 lug 2021
	 * @param idPratica
	 * @param idCorrispondenza
	 * @param idAllegato
	 * @param request
	 * @param response
	 */
	@GetMapping(CorrispondenzaService.PUBLIC_DOWNLOAD_ALLEGATO_COMUNICAZIONE)
	@LoggingAet
	public void downloadDocAllegatoComunicazione(@RequestParam("idPratica")Long idPratica, 
			@RequestParam("idCorrispondenza")Long idCorrispondenza,
			@RequestParam("idAllegato")Long idAllegato,
			HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			checkAndGetAllegato(idPratica, idCorrispondenza, idAllegato, request, response,TipoLinkInMail.ALLEGATO_COMUNICAZIONE);
		}
		catch (Exception e) 
		{
			log.error("Errore durante il download del documento allegato alla comunicazione", e);
			response.setStatus(500);
		}
	}
	
}