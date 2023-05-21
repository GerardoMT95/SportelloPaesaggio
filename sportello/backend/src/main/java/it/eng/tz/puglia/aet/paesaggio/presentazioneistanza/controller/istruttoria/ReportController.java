package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller.istruttoria;

import java.awt.print.Book;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller.RoleAwareController;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainStringValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Ruoli;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.UserUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ReportDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.ReportSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.ITipoProcedimentoService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean.InsertReportBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean.SelectReportTypeDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.service.IReportService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.Logging;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.configuration.swagger.OpenApiConfig;
import it.eng.tz.puglia.util.log.LogUtil;

@RestController
@RequestMapping(value = "report", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ReportController extends RoleAwareController {
	
	private static final Logger log = LoggerFactory.getLogger(ReportController.class);
	
	private final static String SELECT_TIPOLOGIE="/select-tipologie";
	private final static String SELECT_TIPOLOGIE_PROCEDIMENTI="/tipologie-procedimenti";
	private final static String SEARCH_REPORT="/search-report";
	private final static String DOWNLOAD_REPORT="/download-report/{idDownload}";

	@Autowired
	private IReportService reportService;
	@Autowired
	private ITipoProcedimentoService tipoProcedimentoSvc;
	@Autowired
	private UserUtil userUtil;
	
	@Operation(summary = "Search report", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Risposta in caso di operazione eseguita con stato ok/ko", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "400", description = "Risposta in caso di dati inviati non validi", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "401", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "403", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "405", description = "Risposta in caso di invocazione con METHOD errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "415", description = "Risposta in caso di content type errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "500", description = "Errore di sistema", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      }
	             )
	@Logging
	@PostMapping(value = SEARCH_REPORT)
	public ResponseEntity<BaseRestResponse<PaginatedList<ReportDTO>>> searchReport(@RequestBody final ReportSearch search) throws Exception {
		log.info("Chiamato il controller: 'ReportController'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		this.check();
		return okResponse(this.reportService.search(search));
	}
	
	@Operation(summary = "Insert report", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Risposta in caso di operazione eseguita con stato ok/ko", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "400", description = "Risposta in caso di dati inviati non validi", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "401", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "403", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "405", description = "Risposta in caso di invocazione con METHOD errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "415", description = "Risposta in caso di content type errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "500", description = "Errore di sistema", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      }
	             )
	@RequestMapping(value="/insert", method= {RequestMethod.POST})
	public ResponseEntity<BaseRestResponse<Boolean>> insertReport(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Request payload", required = true)
			@RequestBody final InsertReportBean request
	) throws Exception{
		final StopWatch sw = LogUtil.startLog("insertReport");
		try {
			this.check();
			this.reportService.insert(request);
			return super.okResponse(true);
		}catch(final Exception e) {
			this.logger.error("Error in insertReport", e);
			return super.koResponse(e, new BaseRestResponse<>());
		}finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
	
	@Operation(summary = "Select Tipologie Report", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Risposta in caso di operazione eseguita con stato ok/ko", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "400", description = "Risposta in caso di dati inviati non validi", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "401", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "403", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "405", description = "Risposta in caso di invocazione con METHOD errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "415", description = "Risposta in caso di content type errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "500", description = "Errore di sistema", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      }
	             )
	@Logging
	@GetMapping(value = SELECT_TIPOLOGIE)
	public ResponseEntity<BaseRestResponse<List<SelectReportTypeDto>>> selectTipologie() throws Exception {
		log.info("Chiamato il controller: 'ReportController'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		this.check();
		//per prelevare il mio ente_delegato
		//int idEnteDelegato = userUtil.getIntegerId();
		//per prelevare la descrizione del mio ente
//		final PaesaggioOrganizzazioneDTO dto = this.remoteSchemaService.findPaesaggioById(idOrganizzazione.longValue());
//		descrizione = dto.getDenominazione();
		return okResponse(this.reportService.selectTipologie());
	}
	
	@Operation(summary = "Select Tipologie Procedimenti", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Risposta in caso di operazione eseguita con stato ok/ko", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "400", description = "Risposta in caso di dati inviati non validi", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "401", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "403", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "405", description = "Risposta in caso di invocazione con METHOD errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "415", description = "Risposta in caso di content type errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "500", description = "Errore di sistema", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      }
	             )
	@Logging
	@GetMapping(value = SELECT_TIPOLOGIE_PROCEDIMENTI)
	public ResponseEntity<BaseRestResponse<List<PlainStringValueLabel>>> selectTipologieProcedimenti() throws Exception {
		log.info("Chiamato il controller: 'ReportController'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		this.check();
		//per prelevare il mio ente_delegato
		//int idEnteDelegato = userUtil.getIntegerId();
		//per prelevare la descrizione del mio ente
//		final PaesaggioOrganizzazioneDTO dto = this.remoteSchemaService.findPaesaggioById(idOrganizzazione.longValue());
//		descrizione = dto.getDenominazione();
		return okResponse(this.tipoProcedimentoSvc.selectTipoProcedimenti());
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
	@RequestMapping(value = DOWNLOAD_REPORT, method= {RequestMethod.GET})
	public void downloadReport(
			@Parameter(description = "Id download") 
			@PathVariable final String idDownload, 
			final HttpServletResponse response) throws Exception {
		final StopWatch sw = LogUtil.startLog("downloadReport ", idDownload);
		try {
			this.check();
			final String username = userUtil.getMyProfile().getUsername();
			this.reportService.downloadReport(idDownload, username, response);
		} catch (Exception e) {
			response.setStatus(500);
		}finally {
			this.logger.info(LogUtil.stopLog(sw));
			LogUtil.removeKeys();
		
		}
	}
	
	
	private void check() throws Exception {
		super.checkAbilitazioneFor(Gruppi.ED_,Gruppi.REG_);
		super.checkAbilitazioneFor(Ruoli.DIRIGENTE);
	}
	
	
	
		
}