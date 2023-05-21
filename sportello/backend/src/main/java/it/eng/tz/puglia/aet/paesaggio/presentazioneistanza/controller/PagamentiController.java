package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller;

import java.awt.print.Book;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.AvviaPagamentoRequestBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.FascicoloDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PagamentoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Ruoli;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.IPagamentiService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.Logging;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.bean.CodiceEsitoEnum;
import it.eng.tz.puglia.configuration.swagger.OpenApiConfig;
import it.eng.tz.puglia.http.exception.HttpException;
import it.eng.tz.puglia.privacy.Privacy;
import it.eng.tz.puglia.util.log.LogUtil;

/**
 * Controller per gestione pagamenti
 * @author Antonio La Gatta
 * @date 8 lug 2021
 */
@RestController
@RequestMapping("/pagamenti")
public class PagamentiController extends RoleAwareController{
	/**
	 * service
	 */
	@Autowired
	private IPagamentiService service;
	
	/**
	 * Avvia pagamento
	 * @author Antonio La Gatta
	 * @date 8 lug 2021
	 * @param idPratica
	 * @param request
	 * @return request payload
	 */
	@Operation(summary = "Avvia pagamento", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Risposta in caso di operazione eseguita con stato ok/ko", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "400", description = "Risposta in caso di dati inviati non validi", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "401", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "403", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "405", description = "Risposta in caso di invocazione con METHOD errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "415", description = "Risposta in caso di content type errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "500", description = "Errore di sistema", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      }
	)
	@RequestMapping(value="/avvia-pagamento/{idPratica}", method = {RequestMethod.POST}, consumes = MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<PagamentoDto>> avviaPagamento(
			@Parameter(description = "Id pratica")
			@PathVariable final String idPratica
			,
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Request payload", required = true)
			@RequestBody final AvviaPagamentoRequestBean request
	){
		final StopWatch sw = LogUtil.startLog("avviaPagamento");
		try{
			checkAbilitazioneFor(Gruppi.RICHIEDENTI);
			checkDiMiaCompetenza(UUID.fromString(idPratica));
			return okResponse(this.service.avviaPagamento(idPratica,request));
		}catch(final Exception e){
			logger.error("Errore in avviaPagamento", e);
			return this.koResponse(e, new BaseRestResponse<>());
		}finally{
			logger.info(LogUtil.stopLog(sw));
		}
	}

	/**
	 * Recupera attuale pagamento
	 * @author Antonio La Gatta
	 * @date 8 lug 2021
	 * @param idPratica
	 * @param request
	 * @return request payload
	 */
	@Operation(summary = "Recupera attuale pagamento", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Risposta in caso di operazione eseguita con stato ok/ko", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "400", description = "Risposta in caso di dati inviati non validi", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "401", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "403", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "405", description = "Risposta in caso di invocazione con METHOD errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "415", description = "Risposta in caso di content type errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "500", description = "Errore di sistema", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      }
	)
	@RequestMapping(value="/get-pagamento/{idPratica}", method = {RequestMethod.GET})
	public ResponseEntity<BaseRestResponse<PagamentoDto>> getPagamento(
			@Parameter(description = "Id pratica")
			@PathVariable final String idPratica
	){
		final StopWatch sw = LogUtil.startLog("getPagamento");
		try{
			checkAbilitazioneFor(Gruppi.values());
			checkDiMiaCompetenza(UUID.fromString(idPratica));
			return okResponse(this.service.getPagamento(idPratica));
		}catch(final Exception e){
			logger.error("Errore in getPagamento", e);
			return this.koResponse(e, new BaseRestResponse<>());
		}finally{
			logger.info(LogUtil.stopLog(sw));
		}
	}
	
	/**
	 * Recupera attuale pagamento
	 * @author Antonio La Gatta
	 * @date 8 lug 2021
	 * @param idPratica
	 * @param request
	 * @return request payload
	 */
	@Operation(summary = "Recupera attuale pagamento", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Risposta in caso di operazione eseguita con stato ok/ko", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "400", description = "Risposta in caso di dati inviati non validi", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "401", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "403", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "405", description = "Risposta in caso di invocazione con METHOD errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "415", description = "Risposta in caso di content type errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "500", description = "Errore di sistema", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      }
	)
	@RequestMapping(value="/get-pagamento-by-cod-appptr/{codice}", method = {RequestMethod.GET})
	public ResponseEntity<BaseRestResponse<PagamentoDto>> getPagamentoByCodicePraticaAppptr(
			@Parameter(description = "codice pratica APPPTR")
			@PathVariable final String codice
	){
		final StopWatch sw = LogUtil.startLog("getPagamentoByCodicePraticaAppptr");
		try{
			checkAbilitazioneFor(Gruppi.values());
			//Controllo in lettura per permettere visualizzazione ad altri titolari (non owner)
			checkDiMiaCompetenzaInLetturaByCodicePraticaAppptr(codice);
			return okResponse(this.service.getPagamentoByCodPraticaAppptr(codice));
		}catch(final Exception e){
			logger.error("Errore in getPagamentoByCodicePraticaAppptr", e);
			return this.koResponse(e, new BaseRestResponse<>());
		}finally{
			logger.info(LogUtil.stopLog(sw));
		}
	}
	
	/**
	 * Genera url mypay
	 * @author Antonio La Gatta
	 * @date 8 lug 2021
	 * @param idPratica
	 * @param request
	 * @return request payload
	 */
	@Operation(summary = "Recupera/Genera url mypay", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Risposta in caso di operazione eseguita con stato ok/ko", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "400", description = "Risposta in caso di dati inviati non validi", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "401", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "403", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "405", description = "Risposta in caso di invocazione con METHOD errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "415", description = "Risposta in caso di content type errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "500", description = "Errore di sistema", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      }
	)
	@RequestMapping(value="/get-url-mypay/{idPratica}", method = {RequestMethod.GET})
	public ResponseEntity<BaseRestResponse<String>> generaUrlMyPay(
			@Parameter(description = "Id pratica")
			@PathVariable final String idPratica
	){
		final StopWatch sw = LogUtil.startLog("generaUrlMyPay");
		try{
			checkDiMiaCompetenza(UUID.fromString(idPratica));
			return okResponse(this.service.generaUrlMyPay(idPratica));
		}catch(final Exception e){
			logger.error("Errore in generaUrlMyPay", e);
			return this.koResponse(e, new BaseRestResponse<>());
		}finally{
			logger.info(LogUtil.stopLog(sw));
		}
	}
	/**
	 * Verifica che il pagamento sia stato effettuato
	 * @author Antonio La Gatta
	 * @date 8 lug 2021
	 * @param idPratica
	 * @param request
	 * @return request payload
	 */
	@Operation(summary = "Verifica pagamento", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Risposta in caso di operazione eseguita con stato ok/ko", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "400", description = "Risposta in caso di dati inviati non validi", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "401", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "403", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "405", description = "Risposta in caso di invocazione con METHOD errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "415", description = "Risposta in caso di content type errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "500", description = "Errore di sistema", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      }
	)
	@RequestMapping(value="/verifica-pagamento/{idPratica}", method = {RequestMethod.GET})
	public ResponseEntity<BaseRestResponse<Boolean>> verificaPagamento(
			@Parameter(description = "Id pratica")
			@PathVariable final String idPratica
	){
		final StopWatch sw = LogUtil.startLog("verificaPagamento");
		try{
			checkDiMiaCompetenza(UUID.fromString(idPratica));
			return okResponse(this.service.verificaPagamento(idPratica));
		}catch(final Exception e){
			logger.error("Errore in verificaPagamento", e);
			return this.koResponse(e, new BaseRestResponse<>());
		}finally{
			logger.info(LogUtil.stopLog(sw));
		}
	}
	/**
	 * Annulla pagamento
	 * @author Antonio La Gatta
	 * @date 8 lug 2021
	 * @param idPratica
	 * @param request
	 * @return request payload
	 */
	@Operation(summary = "Annulla pagamento", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Risposta in caso di operazione eseguita con stato ok/ko", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "400", description = "Risposta in caso di dati inviati non validi", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "401", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "403", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "405", description = "Risposta in caso di invocazione con METHOD errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "415", description = "Risposta in caso di content type errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "500", description = "Errore di sistema", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      }
	)
	@RequestMapping(value="/delete-pagamento/{idPratica}", method = {RequestMethod.DELETE})
	public ResponseEntity<BaseRestResponse<Boolean>> annullaPagamento(
			@Parameter(description = "Id pratica")
			@PathVariable final String idPratica
	){
		final StopWatch sw = LogUtil.startLog("annullaPagamento");
		try{
			checkAbilitazioneFor(Gruppi.RICHIEDENTI);
			checkDiMiaCompetenza(UUID.fromString(idPratica));
			this.service.annullaPagamento(idPratica);
			return okResponse(true);
		}catch(final Exception e){
			logger.error("Errore in annullaPagamento", e);
			return this.koResponse(e, new BaseRestResponse<>());
		}finally{
			logger.info(LogUtil.stopLog(sw));
		}
	}
	/**
	 * Download ricevuta pagamento
	 * @author Antonio La Gatta
	 * @date 9 lug 2021
	 * @param idPratica
	 * @throws Exception
	 */
	@Operation(summary = "Download ricevuta pagamento", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Risposta in caso di operazione eseguita con stato ok/ko", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "400", description = "Risposta in caso di dati inviati non validi", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "401", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "403", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "405", description = "Risposta in caso di invocazione con METHOD errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "415", description = "Risposta in caso di content type errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "500", description = "Errore di sistema", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      }
	)
	@RequestMapping(value="/download-ricevuta/{idPratica}", method = {RequestMethod.GET})
	public void downloadRicevuta(final HttpServletResponse response,
			@Parameter(description = "Id pratica")
			@PathVariable final String idPratica
	)throws Exception{
		final StopWatch sw = LogUtil.startLog("downloadRicevuta");
		try{
			checkDiMiaCompetenza(UUID.fromString(idPratica));
			this.service.downloadRicevuta(idPratica, response);
		}catch(final Exception e){
			logger.error("Errore in downloadRicevuta", e);
			throw e;
		}finally{
			logger.info(LogUtil.stopLog(sw));
		}
	}
	/**
	 * @author Antonio La Gatta
	 * @date 9 lug 2021
	 * @see it.eng.tz.aet.controller.BaseProcedimentiAbientaliController#koResponse(java.lang.Exception, it.eng.tz.puglia.bean.BaseRestResponse)
	 */
	@Override
	protected <T> ResponseEntity<BaseRestResponse<T>> koResponse(final Exception e, final BaseRestResponse<T> body) {
		if(e instanceof HttpException) {
			body.setCodiceEsito(CodiceEsitoEnum.KO);
			body.setNumeroOggettiRestituiti(0);
			body.setNumeroTotaleOggetti(0);
			body.setDescrizioneEsito("PAGO_PA_ERROR");
			return ResponseEntity.ok(body);
		}
		return super.koResponse(e, body);
	}
	
	
	
}
