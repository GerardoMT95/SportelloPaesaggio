package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller;

import java.awt.print.Book;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.CdsDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.DocumentazioneCdsDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PraticaCdsListSettingsDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.CommissioneLocaleDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainStringValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaCdsDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDocumentazioneCdsDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.IPraticaCdsService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.IPraticaDocumentazioneCdsService;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.bean.CodiceEsitoEnum;
import it.eng.tz.puglia.configuration.swagger.OpenApiConfig;
import it.eng.tz.puglia.error.exception.CustomValidationException;
import it.eng.tz.puglia.privacy.Privacy;
import it.eng.tz.puglia.util.log.LogUtil;

/**
 * Cds controller
 * @author Antonio La Gatta
 * @date 30 nov 2021
 */
@Privacy
@RestController
@RequestMapping("/cds")
public class PraticaCdsController extends RoleAwareController{
	
	/**
	 * service
	 */
	@Autowired
	private IPraticaCdsService service;
	/**
	 * documenti service
	 */
	@Autowired
	private IPraticaDocumentazioneCdsService documentiService;

	/**
	 * configurazione corrente
	 * @author Antonio La Gatta
	 * @date 30 nov 2021
	 * @param idPratica
	 * @return response payload 
	 * @throws Exception
	 */
	@Operation(summary = "Configurazione corrente", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Risposta in caso di operazione eseguita con stato ok/ko", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "400", description = "Risposta in caso di dati inviati non validi", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "401", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "403", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "405", description = "Risposta in caso di invocazione con METHOD errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "415", description = "Risposta in caso di content type errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "500", description = "Errore di sistema", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      }
	             )
	@RequestMapping(value="/get/{idPratica}/{id}", method= {RequestMethod.GET})
	public ResponseEntity<BaseRestResponse<PraticaCdsDTO>> get(
			 @PathVariable final String idPratica
			,@PathVariable final String id
	) throws Exception{
		final StopWatch sw = LogUtil.startLog("get");
		try {
			return super.okResponse(this.service.getSettings(idPratica, id));
		}catch(final Exception e) {
			this.logger.error("Error in get", e);
			return super.koResponse(e, new BaseRestResponse<>());
		}finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
	/**
	 * Lista settings 
	 * @author Antonio La Gatta
	 * @date 17 mar 2022
	 * @param idPratica
	 * @param id
	 * @return response payload
	 * @throws Exception
	 */
	@Operation(summary = "Lista settings", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Risposta in caso di operazione eseguita con stato ok/ko", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "400", description = "Risposta in caso di dati inviati non validi", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "401", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "403", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "405", description = "Risposta in caso di invocazione con METHOD errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "415", description = "Risposta in caso di content type errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "500", description = "Errore di sistema", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      }
	             )
	@RequestMapping(value="/list/{idPratica}", method= {RequestMethod.GET})
	public ResponseEntity<BaseRestResponse<List<PraticaCdsListSettingsDto>>> list(
			 @PathVariable final String idPratica
	) throws Exception{
		final StopWatch sw = LogUtil.startLog("list");
		try {
			return super.okResponse(this.service.listSettings(idPratica));
		}catch(final Exception e) {
			this.logger.error("Error in list", e);
			return super.koResponse(e, new BaseRestResponse<>());
		}finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
	/**
	 * @author Antonio La Gatta
	 * @date 17 mar 2022
	 * @param idPratica
	 * @return Nuova configurazione
	 * @throws Exception
	 */
	@Operation(summary = "Nuova Configurazione corrente", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Risposta in caso di operazione eseguita con stato ok/ko", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "400", description = "Risposta in caso di dati inviati non validi", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "401", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "403", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "405", description = "Risposta in caso di invocazione con METHOD errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "415", description = "Risposta in caso di content type errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "500", description = "Errore di sistema", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      }
	             )
	@RequestMapping(value="/new/{idPratica}", method= {RequestMethod.GET})
	public ResponseEntity<BaseRestResponse<PraticaCdsDTO>> nuova(@PathVariable final String idPratica) throws Exception{
		final StopWatch sw = LogUtil.startLog("new");
		try {
			final String id = this.service.newSettings(idPratica);
			return super.okResponse(this.service.getSettings(idPratica, id));
		}catch(final CustomValidationException e) {
			BaseRestResponse<PraticaCdsDTO> response = new BaseRestResponse<PraticaCdsDTO>();
			response.setCodiceEsito(CodiceEsitoEnum.OK);
			response.setDescrizioneEsito("INVALID_CREATOR");
			return new ResponseEntity<BaseRestResponse<PraticaCdsDTO>>(response, HttpStatus.OK);
		}catch(final Exception e) {
			this.logger.error("Error in new", e);
			return super.koResponse(e, new BaseRestResponse<>());
		}finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
	/**
	 * Elimina configurazione
	 * @author Antonio La Gatta
	 * @date 30 nov 2021
	 * @throws Exception
	 */
	@Operation(summary = "Elimina configurazione corrente", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Risposta in caso di operazione eseguita con stato ok/ko", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "400", description = "Risposta in caso di dati inviati non validi", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "401", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "403", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "405", description = "Risposta in caso di invocazione con METHOD errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "415", description = "Risposta in caso di content type errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "500", description = "Errore di sistema", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      }
	             )
	@RequestMapping(value="/delete/{idPratica}/{id}", method= {RequestMethod.DELETE})
	public ResponseEntity<BaseRestResponse<Boolean>> delete(
			 @PathVariable final String idPratica
			,@PathVariable final String id
	) throws Exception{
		final StopWatch sw = LogUtil.startLog("delete");
		try {
			this.service.deleteSettings(idPratica, id);
			return super.okResponse(true);
		}catch(final Exception e) {
			this.logger.error("Error in delete", e);
			return super.koResponse(e, new BaseRestResponse<>());
		}finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
	/**
	 * Verifica se pratica ha cds
	 * @author Antonio La Gatta
	 * @date 30 nov 2021
	 * @param idPratica
	 * @return response payload
	 * @throws Exception
	 */
	@Operation(summary = "Verifica se pratica ha cds", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Risposta in caso di operazione eseguita con stato ok/ko", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "400", description = "Risposta in caso di dati inviati non validi", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "401", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "403", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "405", description = "Risposta in caso di invocazione con METHOD errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "415", description = "Risposta in caso di content type errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "500", description = "Errore di sistema", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      }
	             )
	@RequestMapping(value="/has-cds/{id}", method= {RequestMethod.GET})
	public ResponseEntity<BaseRestResponse<Boolean>> hasCds(@PathVariable("id") final String idPratica) throws Exception{
		final StopWatch sw = LogUtil.startLog("hasCds");
		try {
			return super.okResponse(this.service.hasCds(idPratica));
		}catch(final Exception e) {
			this.logger.error("Error in hasCds", e);
			return super.koResponse(e, new BaseRestResponse<>());
		}finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
	/**
	 * @author Antonio La Gatta
	 * @date 30 nov 2021
	 * @param idPratica
	 * @return salva e ritorna configurazione corrente
	 * @throws Exception
	 */
	@Operation(summary = "Salva Configurazione corrente", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Risposta in caso di operazione eseguita con stato ok/ko", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "400", description = "Risposta in caso di dati inviati non validi", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "401", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "403", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "405", description = "Risposta in caso di invocazione con METHOD errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "415", description = "Risposta in caso di content type errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "500", description = "Errore di sistema", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      }
	             )
	@RequestMapping(value="/save/{id}", method= {RequestMethod.POST})
	public ResponseEntity<BaseRestResponse<PraticaCdsDTO>> save(@PathVariable("id") final String idPratica
			,@RequestBody final PraticaCdsDTO request 
	) throws Exception{
		final StopWatch sw = LogUtil.startLog("save");
		try {
			this.service.saveSettings(idPratica, request);
			return super.okResponse(this.service.getSettings(idPratica, request.getId()));
		}catch(final CustomValidationException e) {
			BaseRestResponse<PraticaCdsDTO> response = new BaseRestResponse<PraticaCdsDTO>();
			response.setCodiceEsito(CodiceEsitoEnum.OK);
			response.setDescrizioneEsito("INVALID_USERS");
			return new ResponseEntity<BaseRestResponse<PraticaCdsDTO>>(response, HttpStatus.OK);
		}catch(final Exception e) {
			this.logger.error("Error in save", e);
			return super.koResponse(e, new BaseRestResponse<>());
		}finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
	/**
	 * Avvia allineamento cds
	 * @author Antonio La Gatta
	 * @date 30 nov 2021
	 * @param idPratica
	 * @return response payload
	 * @throws Exception
	 */
	@Operation(summary = "Avvia allineamento cds", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Risposta in caso di operazione eseguita con stato ok/ko", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "400", description = "Risposta in caso di dati inviati non validi", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "401", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "403", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "405", description = "Risposta in caso di invocazione con METHOD errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "415", description = "Risposta in caso di content type errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "500", description = "Errore di sistema", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      }
	             )
	@RequestMapping(value="/avvia/{idPratica}/{id}", method= {RequestMethod.GET})
	public ResponseEntity<BaseRestResponse<Boolean>> avvia(@PathVariable final String idPratica, @PathVariable final String id) throws Exception{
		final StopWatch sw = LogUtil.startLog("avvia");
		try {
			this.service.avvia(idPratica, id);
			return super.okResponse(true);
		}catch(final Exception e) {
			this.logger.error("Error in avvia", e);
			return super.koResponse(e, new BaseRestResponse<>());
		}finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
	/**
	 * Autocomplete provincia
	 * @author Antonio La Gatta
	 * @date 30 nov 2021
	 * @param idPratica
	 * @return response payload
	 * @throws Exception
	 */
	@Operation(summary = "Autocomplete provincia", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Risposta in caso di operazione eseguita con stato ok/ko", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "400", description = "Risposta in caso di dati inviati non validi", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "401", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "403", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "405", description = "Risposta in caso di invocazione con METHOD errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "415", description = "Risposta in caso di content type errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "500", description = "Errore di sistema", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      }
	             )
	@RequestMapping(value="/provincia", method= {RequestMethod.GET})
	public ResponseEntity<BaseRestResponse<List<PlainStringValueLabel>>> autocompleteProvincia(@RequestParam final String query) throws Exception{
		final StopWatch sw = LogUtil.startLog("autocompleteProvincia");
		try {
			return super.okResponse(this.service.autocompleteProvince(query));
		}catch(final Exception e) {
			this.logger.error("Error in autocompleteProvincia", e);
			return super.koResponse(e, new BaseRestResponse<>());
		}finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
	/**
	 * Autocomplete comuni
	 * @author Antonio La Gatta
	 * @date 30 nov 2021
	 * @param idPratica
	 * @return response payload
	 * @throws Exception
	 */
	@Operation(summary = "Autocompelte comuni", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Risposta in caso di operazione eseguita con stato ok/ko", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "400", description = "Risposta in caso di dati inviati non validi", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "401", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "403", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "405", description = "Risposta in caso di invocazione con METHOD errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "415", description = "Risposta in caso di content type errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "500", description = "Errore di sistema", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      }
	             )
	@RequestMapping(value="/comune/{provincia}", method= {RequestMethod.GET})
	public ResponseEntity<BaseRestResponse<List<PlainStringValueLabel>>> autocompleteComune(
			@PathVariable("provincia") final String siglaProvincia
			,@RequestParam final String query
	) throws Exception{
		final StopWatch sw = LogUtil.startLog("autocompleteComune");
		try {
			return super.okResponse(this.service.autocompleteComuni(siglaProvincia, query));
		}catch(final Exception e) {
			this.logger.error("Error in autocompleteComune", e);
			return super.koResponse(e, new BaseRestResponse<>());
		}finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
	/**
	 * Tipo conferenza
	 * @author Antonio La Gatta
	 * @date 30 nov 2021
	 * @param idPratica
	 * @return response payload
	 * @throws Exception
	 */
	@Operation(summary = "Tipo conferenza", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Risposta in caso di operazione eseguita con stato ok/ko", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "400", description = "Risposta in caso di dati inviati non validi", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "401", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "403", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "405", description = "Risposta in caso di invocazione con METHOD errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "415", description = "Risposta in caso di content type errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "500", description = "Errore di sistema", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      }
	             )
	@RequestMapping(value="/tipo/{id}", method= {RequestMethod.GET})
	public ResponseEntity<BaseRestResponse<List<PlainStringValueLabel>>> tipo(@PathVariable("id") final String idPratica) throws Exception{
		final StopWatch sw = LogUtil.startLog("tipo");
		try {
			return super.okResponse(this.service.listaTipo(idPratica));
		}catch(final Exception e) {
			this.logger.error("Error in tipo", e);
			return super.koResponse(e, new BaseRestResponse<>());
		}finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
	/**
	 * Attività conferenza
	 * @author Antonio La Gatta
	 * @date 30 nov 2021
	 * @param idPratica
	 * @return response payload
	 * @throws Exception
	 */
	@Operation(summary = "Attività conferenza", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Risposta in caso di operazione eseguita con stato ok/ko", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "400", description = "Risposta in caso di dati inviati non validi", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "401", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "403", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "405", description = "Risposta in caso di invocazione con METHOD errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "415", description = "Risposta in caso di content type errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "500", description = "Errore di sistema", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      }
	             )
	@RequestMapping(value="/attivita/{id}", method= {RequestMethod.GET})
	public ResponseEntity<BaseRestResponse<List<PlainStringValueLabel>>> attivita(@PathVariable("id") final String idPratica) throws Exception{
		final StopWatch sw = LogUtil.startLog("attivita");
		try {
			return super.okResponse(this.service.listaAttivita(idPratica));
		}catch(final Exception e) {
			this.logger.error("Error in attivita", e);
			return super.koResponse(e, new BaseRestResponse<>());
		}finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
	/**
	 * Azione conferenza
	 * @author Antonio La Gatta
	 * @date 30 nov 2021
	 * @param idPratica
	 * @return response payload
	 * @throws Exception
	 */
	@Operation(summary = "Azione conferenza", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Risposta in caso di operazione eseguita con stato ok/ko", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "400", description = "Risposta in caso di dati inviati non validi", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "401", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "403", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "405", description = "Risposta in caso di invocazione con METHOD errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "415", description = "Risposta in caso di content type errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "500", description = "Errore di sistema", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      }
	             )
	@RequestMapping(value="/azione/{id}/{attivita}", method= {RequestMethod.GET})
	public ResponseEntity<BaseRestResponse<List<PlainStringValueLabel>>> azione(
			@PathVariable("id") final String idPratica,
			@PathVariable final String attivita) throws Exception{
		final StopWatch sw = LogUtil.startLog("azione");
		try {
			return super.okResponse(this.service.listaAzione(attivita, idPratica));
		}catch(final Exception e) {
			this.logger.error("Error in azione", e);
			return super.koResponse(e, new BaseRestResponse<>());
		}finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
	/**
	 * Modalita conferenza
	 * @author Antonio La Gatta
	 * @date 30 nov 2021
	 * @param idPratica
	 * @return response payload
	 * @throws Exception
	 */
	@Operation(summary = "Modalita conferenza", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Risposta in caso di operazione eseguita con stato ok/ko", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "400", description = "Risposta in caso di dati inviati non validi", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "401", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "403", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "405", description = "Risposta in caso di invocazione con METHOD errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "415", description = "Risposta in caso di content type errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "500", description = "Errore di sistema", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      }
	             )
	@RequestMapping(value="/modalita", method= {RequestMethod.GET})
	public ResponseEntity<BaseRestResponse<List<PlainStringValueLabel>>> modalita() throws Exception{
		final StopWatch sw = LogUtil.startLog("modalita");
		try {
			return super.okResponse(this.service.listaModalita());
		}catch(final Exception e) {
			this.logger.error("Error in modalita", e);
			return super.koResponse(e, new BaseRestResponse<>());
		}finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
	/**
	 * Documentazione CDS
	 * @author Antonio La Gatta
	 * @date 11 mar 2022
	 * @return response payload
	 * @throws Exception
	 */
	@Operation(summary = "Documentazione CDS", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Risposta in caso di operazione eseguita con stato ok/ko", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "400", description = "Risposta in caso di dati inviati non validi", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "401", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "403", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "405", description = "Risposta in caso di invocazione con METHOD errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "415", description = "Risposta in caso di content type errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "500", description = "Errore di sistema", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      }
	             )
	@RequestMapping(value="/documentazione-list/{idPratica}", method= {RequestMethod.GET})
	public ResponseEntity<BaseRestResponse<List<DocumentazioneCdsDto>>> documentazioneList(@PathVariable final String idPratica) throws Exception{
		final StopWatch sw = LogUtil.startLog("documentazioneList ", idPratica);
		try {
			return super.okResponse(this.documentiService.list(idPratica));
		}catch(final Exception e) {
			this.logger.error("Error in documentazioneList", e);
			return super.koResponse(e, new BaseRestResponse<>());
		}finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
	/**
	 * Download Documentazione CDS
	 * @author Antonio La Gatta
	 * @date 11 mar 2022
	 * @throws Exception
	 */
	@Operation(summary = "Download Documentazione CDS", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Risposta in caso di operazione eseguita con stato ok/ko", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "400", description = "Risposta in caso di dati inviati non validi", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "401", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "403", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "405", description = "Risposta in caso di invocazione con METHOD errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "415", description = "Risposta in caso di content type errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "500", description = "Errore di sistema", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      }
	             )
	@RequestMapping(value="/download/{idPratica}/{id}", method= {RequestMethod.GET})
	public void documentazioneDownload(@PathVariable final String idPratica
			                          ,@PathVariable final Long id
			                          ,final HttpServletResponse response
	) throws Exception{
		final StopWatch sw = LogUtil.startLog("documentazioneDownload ", idPratica);
		try {
			this.documentiService.download(idPratica, id, response);
		}catch(final Exception e) {
			this.logger.error("Error in documentazioneDownload", e);
			response.setStatus(500);
		}finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
	/**
	 * SalvaDocumentazione CDS
	 * @author Antonio La Gatta
	 * @date 11 mar 2022
	 * @return response payload
	 * @throws Exception
	 */
	@Operation(summary = "Salva Documentazione CDS", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Risposta in caso di operazione eseguita con stato ok/ko", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "400", description = "Risposta in caso di dati inviati non validi", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "401", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "403", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "405", description = "Risposta in caso di invocazione con METHOD errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "415", description = "Risposta in caso di content type errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "500", description = "Errore di sistema", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      }
	             )
	@RequestMapping(value="/save-documentazione-list/{idPratica}", method= {RequestMethod.POST})
	public ResponseEntity<BaseRestResponse<Boolean>> saveDocumentazione
	(@PathVariable final String idPratica
	,@RequestBody final List<PraticaDocumentazioneCdsDTO> list
	) throws Exception{
		final StopWatch sw = LogUtil.startLog("saveDocumentazione ", idPratica);
		try {
			this.documentiService.save(idPratica, list);
			return super.okResponse(true);
		}catch(final Exception e) {
			this.logger.error("Error in saveDocumentazione", e);
			return super.koResponse(e, new BaseRestResponse<>());
		}finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
	/**
	 * cds
	 * @author Antonio La Gatta
	 * @param idPratica
	 * @return response payload
	 * @throws Exception
	 */
	@Operation(summary = "Recupera Info cds", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Risposta in caso di operazione eseguita con stato ok/ko", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "400", description = "Risposta in caso di dati inviati non validi", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "401", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "403", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "405", description = "Risposta in caso di invocazione con METHOD errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "415", description = "Risposta in caso di content type errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "500", description = "Errore di sistema", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      }
	             )
	@RequestMapping(value="/get-cds/{id}", method= {RequestMethod.GET})
	public ResponseEntity<BaseRestResponse<List<CdsDto>>> getCds(@PathVariable("id") final String idPratica) throws Exception{
		final StopWatch sw = LogUtil.startLog("getCds");
		try {
			return super.okResponse(this.service.infoCds(idPratica));
		}catch(final Exception e) {
			this.logger.error("Error in getCds", e);
			return super.koResponse(e, new BaseRestResponse<>());
		}finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
	/**
	 * cds
	 * @author Antonio La Gatta
	 * @param idPratica
	 * @return response payload
	 * @throws Exception
	 */
	@Operation(summary = "Recupera Info cds", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Risposta in caso di operazione eseguita con stato ok/ko", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "400", description = "Risposta in caso di dati inviati non validi", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "401", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "403", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "405", description = "Risposta in caso di invocazione con METHOD errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "415", description = "Risposta in caso di content type errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "500", description = "Errore di sistema", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      }
	             )
	@RequestMapping(value="/get-cds/{idPratica}/{id}", method= {RequestMethod.GET})
	public ResponseEntity<BaseRestResponse<CdsDto>> getCds(
			@PathVariable final String idPratica
			,@PathVariable final String id
		) throws Exception{
		final StopWatch sw = LogUtil.startLog("getCds");
		try {
			return super.okResponse(this.service.infoCds(idPratica, id));
		}catch(final Exception e) {
			this.logger.error("Error in getCds", e);
			return super.koResponse(e, new BaseRestResponse<>());
		}finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
	/**
	 * @author Antonio La Gatta
	 * @date 18 mar 2022
	 * @param idPratica
	 * @return configurazione comitato
	 * @throws Exception
	 */
	@Operation(summary = "Configurazione comitato", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Risposta in caso di operazione eseguita con stato ok/ko", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "400", description = "Risposta in caso di dati inviati non validi", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "401", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "403", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "405", description = "Risposta in caso di invocazione con METHOD errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "415", description = "Risposta in caso di content type errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "500", description = "Errore di sistema", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      }
	             )
	@RequestMapping(value="/get-comitato/{idPratica}", method= {RequestMethod.GET})
	public ResponseEntity<BaseRestResponse<PraticaCdsDTO>> getComitato(@PathVariable final String idPratica) throws Exception{
		final StopWatch sw = LogUtil.startLog("getComitato");
		try {
			return super.okResponse(this.service.getSettingsComitato(idPratica));
		}catch(final Exception e) {
			this.logger.error("Error in getComitato", e);
			return super.koResponse(e, new BaseRestResponse<>());
		}finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
	/**
	 * @author Antonio La Gatta
	 * @date 18 mar 2022
	 * @param idPratica
	 * @return utenti comitato
	 * @throws Exception
	 */
	@Operation(summary = "Utenti comitato", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Risposta in caso di operazione eseguita con stato ok/ko", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "400", description = "Risposta in caso di dati inviati non validi", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "401", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "403", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "405", description = "Risposta in caso di invocazione con METHOD errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "415", description = "Risposta in caso di content type errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "500", description = "Errore di sistema", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      }
	             )
	@RequestMapping(value="/get-utenti-comitato/{idPratica}", method= {RequestMethod.GET})
	public ResponseEntity<BaseRestResponse<List<PlainStringValueLabel>>> getUtentiComitato(@PathVariable final String idPratica) throws Exception{
		final StopWatch sw = LogUtil.startLog("getUtentiComitato");
		try {
			return super.okResponse(this.service.listaComitato(idPratica));
		}catch(final Exception e) {
			this.logger.error("Error in getUtentiComitato", e);
			return super.koResponse(e, new BaseRestResponse<>());
		}finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
	/**
	 * Get Dirigenti
	 * @author Antonio La Gatta
	 * @date 25 mag 2022
	 * @param idPratica
	 * @return payload con lista responsabili
	 * @throws Exception
	 */
	@Operation(summary = "Lista Responsabili", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Risposta in caso di operazione eseguita con stato ok/ko", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "400", description = "Risposta in caso di dati inviati non validi", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "401", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "403", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "405", description = "Risposta in caso di invocazione con METHOD errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "415", description = "Risposta in caso di content type errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "500", description = "Errore di sistema", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      }
	             )
	@RequestMapping(value="/lista-responsabili/{idPratica}", method= {RequestMethod.GET})
	public ResponseEntity<BaseRestResponse<List<PlainStringValueLabel>>> listaResponsabili(@PathVariable final String idPratica) throws Exception{
		final StopWatch sw = LogUtil.startLog("listaResponsabili");
		try {
			return super.okResponse(this.service.listaResponsabili(idPratica));
		}catch(final Exception e) {
			this.logger.error("Error in listaResponsabili", e);
			return super.koResponse(e, new BaseRestResponse<>());
		}finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
	
	@Operation(summary = "Lista Funzionari", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Risposta in caso di operazione eseguita con stato ok/ko", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "400", description = "Risposta in caso di dati inviati non validi", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "401", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "403", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "405", description = "Risposta in caso di invocazione con METHOD errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "415", description = "Risposta in caso di content type errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "500", description = "Errore di sistema", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      }
	             )
	@RequestMapping(value="/lista-funzionari/{idPratica}", method= {RequestMethod.GET})
	public ResponseEntity<BaseRestResponse<List<PlainStringValueLabel>>> listaFunzionari(@PathVariable final String idPratica) throws Exception{
		final StopWatch sw = LogUtil.startLog("listaFunzionari");
		try {
			return super.okResponse(this.service.listaFunzionari(idPratica));
		}catch(final Exception e) {
			this.logger.error("Error in listaFunzionari", e);
			return super.koResponse(e, new BaseRestResponse<>());
		}finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
	
	@Operation(summary = "Can Create Conferenza", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Risposta in caso di operazione eseguita con stato ok/ko", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "400", description = "Risposta in caso di dati inviati non validi", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "401", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "403", description = "Risposta in caso di utente non autorizzato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "405", description = "Risposta in caso di invocazione con METHOD errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "415", description = "Risposta in caso di content type errato", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      ,@ApiResponse(responseCode = "500", description = "Errore di sistema", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)) })
	                      }
	             )
	@RequestMapping(value="/can-create-conferenza/{idPratica}", method= {RequestMethod.GET})
	public ResponseEntity<BaseRestResponse<Boolean>> canCreateConferenza(@PathVariable final String idPratica) throws Exception{
		final StopWatch sw = LogUtil.startLog("canCreateConferenza");
		try {
			return super.okResponse(this.service.canCreateConferenza(idPratica));
		}catch(final Exception e) {
			this.logger.error("Error in canCreateConferenza", e);
			return super.koResponse(e, new BaseRestResponse<>());
		}finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
}
