package it.eng.tz.puglia.autpae.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.eng.tz.puglia.autpae.controller.exception.CustomOperationToAdviceException;
import it.eng.tz.puglia.autpae.dto.AcceleratoriFascicoloDTO;
import it.eng.tz.puglia.autpae.dto.FascicoloTabDTO;
import it.eng.tz.puglia.autpae.dto.InformazioniDTO;
import it.eng.tz.puglia.autpae.entity.FascicoloBaseDto;
import it.eng.tz.puglia.autpae.entity.FascicoloDTO;
import it.eng.tz.puglia.autpae.entity.FascicoloPublicDto;
import it.eng.tz.puglia.autpae.enumeratori.Gruppi;
import it.eng.tz.puglia.autpae.search.FascicoloSearch;
import it.eng.tz.puglia.autpae.service.interfacce.FascicoloService;
import it.eng.tz.puglia.autpae.service.interfacce.SportelloService;
import it.eng.tz.puglia.autpae.utility.LoggingAet;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.configuration.security.SecurityConfig;
import it.eng.tz.puglia.configuration.swagger.OpenApiConfig;
import it.eng.tz.puglia.privacy.Privacy;
import it.eng.tz.puglia.security.util.SecurityUtil;
import it.eng.tz.puglia.servizi_esterni.jasper.CreaReportService;

@RestController
@Privacy
public class FascicoloController extends CheckGroupAbstractController//extends _RestController 
{
	final static String FASCICOLO_PATH = "fascicolo";
	final static String API_FASCICOLO_PATH = SecurityConfig.API_GATEWAY_VALUE + "/" + FASCICOLO_PATH;
	private static final Logger log = LoggerFactory.getLogger(FascicoloController.class);

	@Value("${max.righe.export.elenchi.private:100}")
	private Integer maxRigheExportPrivate;

	@Autowired
	private FascicoloService fascicoloService;
	@Autowired
	private CreaReportService creaReportService;
	@Autowired
	private SportelloService sportelloService;

	@PostMapping(value = FASCICOLO_PATH + "/crea", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@LoggingAet
	public ResponseEntity<BaseRestResponse<FascicoloDTO>> creaFascicolo(
			@RequestBody FascicoloDTO body)
			throws Exception {
		//gruppiRuoliService.checkAbilitazioneFor(Gruppi.ED_);
		gruppiRuoliService.checkAbilitazioneForTrasmissione();
		super.check();
		return super.okResponse(fascicoloService.crea(body));
	}

	@Operation(summary = "Creazione della pratica, campi obbligatori da popolare nell'oggetto passato", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@PostMapping(value = { API_FASCICOLO_PATH + "/create" }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@LoggingAet
	public ResponseEntity<BaseRestResponse<FascicoloDTO>> creaFascicoloDaApi(
			@Parameter(description = "oggetto contenente i dati minimi per la creazione della pratica")
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "oggetto contenente i dati minimi per la creazione della pratica.", required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                    schema=@io.swagger.v3.oas.annotations.media.Schema(implementation = FascicoloBaseDto.class)))
			@RequestBody FascicoloBaseDto body)
			throws Exception {
		//gruppiRuoliService.checkAbilitazioneFor(Gruppi.ED_);
		gruppiRuoliService.checkAbilitazioneForTrasmissione();
		super.check();
		return super.okResponse(fascicoloService.creaDaApi(body));
	}

	@DeleteMapping(value = { FASCICOLO_PATH + "/cancella",
			API_FASCICOLO_PATH + "/cancella" }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@LoggingAet
	@Operation(summary = "Rimozione irreversibile di tutto il contenuto del fascicolo, solo se lo stato lo permette.",
	security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	public ResponseEntity<BaseRestResponse<Boolean>> cancellaFascicolo(
			@RequestParam(name = "id", required = true) long id) throws Exception {
		gruppiRuoliService.checkAbilitazioneFor(Gruppi.ED_, Gruppi.ADMIN);
		checkPermission(id);
		boolean ret = fascicoloService.cancella(id);
		sportelloService.setLibero(null, SecurityUtil.getUsername(),id);
		return super.okResponse(ret);
	}

	@GetMapping(value = { FASCICOLO_PATH + "/get",
			API_FASCICOLO_PATH + "/get" }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@LoggingAet
	@Operation(summary = "Fetch dei tab che compongono il fascicolo", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	public ResponseEntity<BaseRestResponse<FascicoloTabDTO>> tabFascicolo() throws Exception {
		gruppiRuoliService.checkAbilitazioneFor(Gruppi.values());
		return super.okResponse(fascicoloService.tabFascicolo());
	}

	@GetMapping(value = { FASCICOLO_PATH + "/dati",
			API_FASCICOLO_PATH + "/dati" }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@LoggingAet
	public ResponseEntity<BaseRestResponse<FascicoloTabDTO>> datiFascicolo(
			@RequestParam(name = "id", required = true) long id) throws Exception {
		checkPermission(id);
		return super.okResponse(fascicoloService.datiFascicolo(id));
	}

	@GetMapping(value = { FASCICOLO_PATH + "/search",
			API_FASCICOLO_PATH + "/search" }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@LoggingAet
	@Operation(summary = "Ricerca di un fascicolo di mia competenza", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	public ResponseEntity<BaseRestResponse<PaginatedList<FascicoloDTO>>> searchFascicolo(FascicoloSearch filter)
			throws Exception {
		fascicoloService.prepareForSearch(filter);
		return super.okResponse(fascicoloService.searchFascicolo(filter));
	}

//non viene mai chiamato lato FE	
//	@GetMapping(value = { FASCICOLO_PATH + "/getAll",
//			API_FASCICOLO_PATH + "/getAll" }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//	@LoggingAet
//	@Operation(summary = "Informazioni complete fasciolo per tipologia di procedimento", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
//	public ResponseEntity<BaseRestResponse<InformazioniDTO>> informazioniComplete(
//			@RequestParam(name = "tipoProcedimento", required = true) TipoProcedimento tipoProcedimento)
//			throws Exception {
//		return super.okResponse(fascicoloService.informazioniComplete(tipoProcedimento));
//	}

	@GetMapping(value = { FASCICOLO_PATH + "/datiAll",
			API_FASCICOLO_PATH + "/datiAll" }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Operation(summary = "Effettua retrieve delle informazioni del fascicolo in input, compresi anche i vocabolari per il popolamento delle informazioni", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@LoggingAet
	public ResponseEntity<BaseRestResponse<InformazioniDTO>> datiCompleti(
			@RequestParam(name = "idFascicolo", required = true) long idFascicolo) throws Exception {
		checkPermission(idFascicolo);
		return super.okResponse(fascicoloService.datiCompleti(idFascicolo));
	}

	@GetMapping(value = FASCICOLO_PATH + "/getForAccelerators", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@LoggingAet
	public ResponseEntity<BaseRestResponse<AcceleratoriFascicoloDTO>> getForAcceleratori(FascicoloSearch filter)
			throws Exception {
		filter.setRicercaPubblica(false);
		return super.okResponse(fascicoloService.getCountForAccelerator(filter));
	}

	@GetMapping(value = FASCICOLO_PATH + "/autocompleteRup", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@LoggingAet
	public ResponseEntity<BaseRestResponse<List<String>>> autocompleteRup(
			@RequestParam(name = "rup", required = false) String rup) throws Exception {
		gruppiRuoliService.checkAbilitazioneFor(Gruppi.values());
		return super.okResponse(fascicoloService.autocompleteRup(rup));
	}

	@GetMapping(value = FASCICOLO_PATH + "/exportFascicoliPDF", produces = "application/pdf")
	@LoggingAet
	public ResponseEntity<InputStreamResource> exportFascicoliPDF(FascicoloSearch filter) throws Exception {
		try {
			filter.setLimit(maxRigheExportPrivate);
			filter.setPage(1);
			fascicoloService.prepareForSearch(filter);
			PaginatedList<FascicoloDTO> lista = fascicoloService.searchFascicolo(filter);
			log.info("Bean per la generazione del file Piani PDF: " + lista);
			return creaReportService.exportFascicoliPDF(lista);
		} catch (CustomOperationToAdviceException e) {
			throw e;
		} catch (Exception e) {
			log.error("Errore nella creazione del file PDF con i risultati di ricerca dei fascicoli", e);
			throw new CustomOperationToAdviceException(
					"Errore nella creazione del file PDF con i risultati di ricerca dei fascicoli");
		}
	}

	@GetMapping(value = FASCICOLO_PATH + "/exportFascicoliCSV", produces = "application/csv")
	@LoggingAet
	public ResponseEntity<InputStreamResource> exportFascicoliCSV(FascicoloSearch filter) throws Exception {
		try {
			filter.setLimit(maxRigheExportPrivate);
			filter.setPage(1);
			fascicoloService.prepareForSearch(filter);
			PaginatedList<? extends FascicoloPublicDto> lista = fascicoloService.searchFascicolo(filter);
			log.info("Bean per la generazione del file Piani CSV: " + lista);
			return creaReportService.exportFascicoliCSV(lista);
		} catch (CustomOperationToAdviceException e) {
			throw e;
		} catch (Exception e) {
			log.error("Errore nella creazione del file CSV con i risultati di ricerca dei fascicoli", e);
			throw new CustomOperationToAdviceException(
					"Errore nella creazione del file CSV con i risultati di ricerca dei fascicoli");
		}
	}

}