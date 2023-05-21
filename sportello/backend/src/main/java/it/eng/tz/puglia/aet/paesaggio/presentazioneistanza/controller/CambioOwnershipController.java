package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller;

import java.awt.print.Book;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.CambioOwnershipRequestBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.CambioOwnershipResponseBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.SubentroDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaCdsDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.ICambioOwnershipService;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.bean.CodiceEsitoEnum;
import it.eng.tz.puglia.configuration.swagger.OpenApiConfig;
import it.eng.tz.puglia.error.exception.CustomValidationException;
import it.eng.tz.puglia.privacy.Privacy;
import it.eng.tz.puglia.util.log.LogUtil;

@Privacy
@RestController
@RequestMapping("/cambio-ownership")
public class CambioOwnershipController  extends RoleAwareController{

	@Autowired
	private ICambioOwnershipService service;

	/**
	 * Cerca cambio
	 * @author Antonio La Gatta
	 * @date 6 lug 2021
	 * @param request
	 * @return response payload
	 */
	@Operation(summary = "Cambio ownership per pratiche", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Risposta in caso di operazione eseguita con stato ok/ko", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "400", description = "Risposta in caso di dati inviati non validi", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "401", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "403", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "405", description = "Risposta in caso di invocazione con METHOD errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "415", description = "Risposta in caso di content type errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "500", description = "Errore di sistema", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      }
	)
	@RequestMapping(value="/search", method = {RequestMethod.POST}, consumes = MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<CambioOwnershipResponseBean>> search(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Request payload", required = true)
			@RequestBody final CambioOwnershipRequestBean request
	){
		final StopWatch sw = LogUtil.startLog("search");
		try{
			return this.okResponse(this.service.cercaOwnership(request));
		}catch(final Exception e){
			this.logger.error("Errore in search", e);
			return super.koResponse(e, new BaseRestResponse<>());
		}finally{
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
	/**
	 * Save dati cambio ownership per pratiche
	 * @author Antonio La Gatta
	 * @date 6 lug 2021
	 * @param request
	 * @return response payload
	 */
	@Operation(summary = "Save dati cambio ownership per pratiche", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Risposta in caso di operazione eseguita con stato ok/ko", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "400", description = "Risposta in caso di dati inviati non validi", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "401", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "403", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "405", description = "Risposta in caso di invocazione con METHOD errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "415", description = "Risposta in caso di content type errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "500", description = "Errore di sistema", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      }
	)
	@RequestMapping(value="/save", method= {RequestMethod.POST})
	public ResponseEntity<BaseRestResponse<Boolean>> save(
			@RequestBody final SubentroDto subentro
			,@RequestParam final String codicePratica
	){
		final StopWatch sw = LogUtil.startLog("save");
		try{
			this.service.saveCambioOwnership(codicePratica, subentro);
			return this.okResponse(true);
		}catch(final Exception e){
			this.logger.error("Errore in save", e);
			return super.koResponse(e, new BaseRestResponse<>());
		}finally{
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
	/**
	 * Save dati cambio ownership per pratiche
	 * @author Antonio La Gatta
	 * @date 6 lug 2021
	 * @param request
	 * @return response payload
	 */
	@Operation(summary = "Save dati cambio ownership per pratiche", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Risposta in caso di operazione eseguita con stato ok/ko", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "400", description = "Risposta in caso di dati inviati non validi", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "401", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "403", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "405", description = "Risposta in caso di invocazione con METHOD errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "415", description = "Risposta in caso di content type errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "500", description = "Errore di sistema", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      }
	)
	@RequestMapping(value="/trasmetti", method= {RequestMethod.POST})
	public ResponseEntity<BaseRestResponse<String>> trasmetti(
			@RequestBody final CambioOwnershipRequestBean request
	){
		final StopWatch sw = LogUtil.startLog("trasmetti");
		try{
			return this.okResponse(this.service.cambiaOwnership(request));
		}catch(final Exception e){
			this.logger.error("Errore in trasmetti", e);
			return super.koResponse(e, new BaseRestResponse<>());
		}finally{
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
	/**
	 * Upload modulo di delega
	 * @author Antonio La Gatta
	 * @date 6 ago 2021
	 * @param delega
	 * @param codicePratica
	 * @return response payload
	 */
	@Operation(summary = "Upload Modulo di delega", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Risposta in caso di operazione eseguita con stato ok/ko", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "400", description = "Risposta in caso di dati inviati non validi", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "401", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "403", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "405", description = "Risposta in caso di invocazione con METHOD errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "415", description = "Risposta in caso di content type errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "500", description = "Errore di sistema", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      }
	)
	@RequestMapping(value="/uploadDelega", method= {RequestMethod.POST}, consumes = {MediaType.APPLICATION_OCTET_STREAM_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<BaseRestResponse<String>> uploadDelega(
			 @RequestPart(value="file") final MultipartFile delega
			,@RequestPart(value="codicePratica") final String codicePratica
	){
		final StopWatch sw = LogUtil.startLog("uploadDelega");
		try{
			return this.okResponse(this.service.addModulo(codicePratica, delega));
		}catch(final CustomOperationToAdviceException e){
			BaseRestResponse<String> response = new BaseRestResponse<String>();
			response.setCodiceEsito(CodiceEsitoEnum.OK);
			response.setDescrizioneEsito("INVALID_FORMAT");
			return new ResponseEntity<BaseRestResponse<String>>(response, HttpStatus.OK);
		}catch(final Exception e){
			this.logger.error("Errore in uploadDelega", e);
			return super.koResponse(e, new BaseRestResponse<>());
		}finally{
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
	/**
	 * Delete modulo di delega
	 * @author Antonio La Gatta
	 * @date 6 ago 2021
	 * @param delega
	 * @param codicePratica
	 * @return response payload
	 */
	@Operation(summary = "Delete Modulo di delega", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Risposta in caso di operazione eseguita con stato ok/ko", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "400", description = "Risposta in caso di dati inviati non validi", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "401", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "403", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "405", description = "Risposta in caso di invocazione con METHOD errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "415", description = "Risposta in caso di content type errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "500", description = "Errore di sistema", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      }
	)
	@RequestMapping(value="/deleteDelega", method= {RequestMethod.DELETE})
	public ResponseEntity<BaseRestResponse<Boolean>> deleteDelega(
			@RequestParam final String codicePratica
	){
		final StopWatch sw = LogUtil.startLog("deleteDelega");
		try{
			this.service.deleteModulo(codicePratica);
			return this.okResponse(true);
		}catch(final Exception e){
			this.logger.error("Errore in deleteDelega", e);
			return super.koResponse(e, new BaseRestResponse<>());
		}finally{
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
	/**
	 * Upload modulo di delega
	 * @author Antonio La Gatta
	 * @date 6 ago 2021
	 * @param delega
	 * @param codicePratica
	 * @return response payload
	 */
	@Operation(summary = "Upload Modulo di sollevamento incarico", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Risposta in caso di operazione eseguita con stato ok/ko", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "400", description = "Risposta in caso di dati inviati non validi", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "401", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "403", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "405", description = "Risposta in caso di invocazione con METHOD errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "415", description = "Risposta in caso di content type errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "500", description = "Errore di sistema", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      }
	)
	@RequestMapping(value="/uploadSollevamento", method= {RequestMethod.POST}, consumes = {MediaType.APPLICATION_OCTET_STREAM_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<BaseRestResponse<String>> uploadSollevamento(
			 @RequestPart(value="file") final MultipartFile file
			,@RequestPart(value="codicePratica") final String codicePratica
	){
		final StopWatch sw = LogUtil.startLog("uploadSollevamento");
		try{
			return this.okResponse(this.service.addSollevamento(codicePratica, file));
		}catch(final CustomOperationToAdviceException e){
			BaseRestResponse<String> response = new BaseRestResponse<String>();
			response.setCodiceEsito(CodiceEsitoEnum.OK);
			response.setDescrizioneEsito("INVALID_FORMAT");
			return new ResponseEntity<BaseRestResponse<String>>(response, HttpStatus.OK);
		}catch(final Exception e){
			this.logger.error("Errore in uploadSollevamento", e);
			return super.koResponse(e, new BaseRestResponse<>());
		}finally{
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
	/**
	 * Delete modulo di delega
	 * @author Antonio La Gatta
	 * @date 6 ago 2021
	 * @param delega
	 * @param codicePratica
	 * @return response payload
	 */
	@Operation(summary = "Delete Modulo di sollevamento incarico", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Risposta in caso di operazione eseguita con stato ok/ko", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "400", description = "Risposta in caso di dati inviati non validi", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "401", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "403", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "405", description = "Risposta in caso di invocazione con METHOD errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "415", description = "Risposta in caso di content type errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "500", description = "Errore di sistema", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      }
	)
	@RequestMapping(value="/deleteSollevamento", method= {RequestMethod.DELETE})
	public ResponseEntity<BaseRestResponse<Boolean>> deleteSollevamento(
			@RequestParam final String codicePratica
	){
		final StopWatch sw = LogUtil.startLog("deleteSollevamento");
		try{
			this.service.deleteSollevamento(codicePratica);
			return this.okResponse(true);
		}catch(final Exception e){
			this.logger.error("Errore in deleteSollevamento", e);
			return super.koResponse(e, new BaseRestResponse<>());
		}finally{
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
	/**
	 * Download di delega
	 * @author Antonio La Gatta
	 * @date 6 ago 2021
	 * @param delega
	 * @param codicePratica
	 * @return response payload
	 * @throws Exception
	 */
	@Operation(summary = "Download delega", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Risposta in caso di operazione eseguita con stato ok/ko", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "400", description = "Risposta in caso di dati inviati non validi", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "401", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "403", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "405", description = "Risposta in caso di invocazione con METHOD errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "415", description = "Risposta in caso di content type errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "500", description = "Errore di sistema", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      }
	)
	@RequestMapping(value="/downloadDelega", method= {RequestMethod.GET})
	public void downloadDelega(@RequestParam final String codicePratica, final HttpServletResponse response) throws Exception{
		final StopWatch sw = LogUtil.startLog("downloadDelega");
		try{
			this.service.downloadDelega(codicePratica, response);
		}catch(final Exception e){
			this.logger.error("Errore in downloadDelega", e);
			throw e;
		}finally{
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
	/**
	 * Download di sollevamento
	 * @author Antonio La Gatta
	 * @date 6 ago 2021
	 * @param delega
	 * @param codicePratica
	 * @return response payload
	 * @throws Exception
	 */
	@Operation(summary = "Download sollevamento", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Risposta in caso di operazione eseguita con stato ok/ko", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "400", description = "Risposta in caso di dati inviati non validi", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "401", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "403", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "405", description = "Risposta in caso di invocazione con METHOD errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "415", description = "Risposta in caso di content type errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "500", description = "Errore di sistema", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      }
	)
	@RequestMapping(value="/downloadSollevamento", method= {RequestMethod.GET})
	public void downloadSollevamento(@RequestParam final String codicePratica, final HttpServletResponse response) throws Exception{
		final StopWatch sw = LogUtil.startLog("downloadSollevamento");
		try{
			this.service.downloadSollevamento(codicePratica, response);
		}catch(final Exception e){
			this.logger.error("Errore in downloadSollevamento", e);
			throw e;
		}finally{
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
	
	@Operation(summary = "Solleva incarico", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Risposta in caso di operazione eseguita con stato ok/ko", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "400", description = "Risposta in caso di dati inviati non validi", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "401", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "403", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "405", description = "Risposta in caso di invocazione con METHOD errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "415", description = "Risposta in caso di content type errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "500", description = "Errore di sistema", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      }
	)
	@RequestMapping(value="/solleva-incarico/{idPratica}", method = {RequestMethod.GET})
	public ResponseEntity<BaseRestResponse<Boolean>> sollevaIncarico(
			@PathVariable(required = true) final String idPratica
	){
		final StopWatch sw = LogUtil.startLog("sollevaIncarico {}", idPratica);
		try{
			this.service.sollevaIncarico(idPratica);
			return super.okResponse(true);
		}catch(final Exception e){
			this.logger.error("Errore in sollevaIncarico", e);
			return super.koResponse(e, new BaseRestResponse<>());
		}finally{
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
}
