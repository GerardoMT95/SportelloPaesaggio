package it.eng.tz.puglia.autpae.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.eng.tz.puglia.autpae.entity.TipoProcedimentoDTO;
import it.eng.tz.puglia.autpae.enumeratori.Applicativo;
import it.eng.tz.puglia.autpae.enumeratori.Gruppi;
import it.eng.tz.puglia.autpae.enumeratori.Ruoli;
import it.eng.tz.puglia.autpae.service.interfacce.GruppiRuoliService;
import it.eng.tz.puglia.autpae.service.interfacce.TipoProcedimentoService;
import it.eng.tz.puglia.autpae.utility.LoggingAet;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.configuration.security.SecurityConfig;
import it.eng.tz.puglia.configuration.swagger.OpenApiConfig;
import it.eng.tz.puglia.privacy.Privacy;

@RestController
@Privacy
//@RequestMapping(value = "tipo-procedimento", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TipoProcedimentoController extends _RestController {
	
	private static final Logger log = LoggerFactory.getLogger(TipoProcedimentoController.class);

	private final String TIPO_PROCEDIMENTO_PATH="tipo-procedimento";
	private final String API_TIPO_PROCEDIMENTO_PATH=SecurityConfig.API_GATEWAY_VALUE +"/"+TIPO_PROCEDIMENTO_PATH;
	
	@Autowired 
	private GruppiRuoliService gruppiRuoliService;
	
	@Autowired 
	private TipoProcedimentoService tipoProcedimentoService;	
	
	@ApiOperation("Lista di tutti i tipi procedimento (anche quelli scaduti)")
	@GetMapping(value = TIPO_PROCEDIMENTO_PATH+"/getAll",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@LoggingAet
	public ResponseEntity<BaseRestResponse<List<TipoProcedimentoDTO>>> getAllTipiProcedimento() throws Exception {
		//Controllo che sia l'amministratore dell'applicazione
		gruppiRuoliService.checkAbilitazioneFor(Ruoli.ADMIN);
		return super.okResponse(tipoProcedimentoService.select());
	}
	
	@ApiOperation("Lista di tutti i tipi procedimento (anche quelli scaduti)")
	@GetMapping(value =TIPO_PROCEDIMENTO_PATH+ "/getAllByCurrentApp")
	public ResponseEntity<BaseRestResponse<List<TipoProcedimentoDTO>>> getAllTipiProcedimentoByApp() throws Exception {
		log.info("Chiamato il controller: 'TipoProcedimento'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		//Controllo che sia l'amministratore dell'applicazione
		gruppiRuoliService.checkAbilitazioneFor(Ruoli.ADMIN);
		return super.okResponse(tipoProcedimentoService.selectAllByApplication());
	}
	
	@ApiOperation("Aggiornamento delle date di inizio e fine validita del tipo procedimento")
	@PostMapping(value = TIPO_PROCEDIMENTO_PATH+"/updateValidita",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@LoggingAet
	public ResponseEntity<BaseRestResponse<Integer>> updateValidita(
			@RequestBody TipoProcedimentoDTO request
			) throws Exception {
		//Controllo che sia l'amministratore dell'applicazione
		gruppiRuoliService.checkAbilitazioneFor(Ruoli.ADMIN);
		return super.okResponse(tipoProcedimentoService.updateValidita(request));
	}
	
	@ApiOperation("Lista tipi procedimento (validi in base a data inizio e fine rispetto ad oggi) per l'applicativo corrente ")
	@Operation(summary = "Elenco dei codici e descrizione dei tipi procedimento attivi(validi in base a data inizio e fine rispetto ad oggi) per l'applicativo corrente, validi per la creazione di una nuova pratica ", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@GetMapping(value = { TIPO_PROCEDIMENTO_PATH+"/getByCurrentApp",API_TIPO_PROCEDIMENTO_PATH+"/getByCurrentApp"},produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@LoggingAet
	public ResponseEntity<BaseRestResponse<List<TipoProcedimentoDTO>>> getTipiProcedimento() throws Exception {
		super.checkAbilitazioneFor(Gruppi.values());
		return super.okResponse(tipoProcedimentoService.selectByApplication());
	}
	
	@ApiOperation("Lista tipi procedimento (validi in base a data inizio e fine rispetto ad oggi) per l'applicativo ricevuto come parametro (AUTPAE, PUTT, PARERI) ")
	@GetMapping(value =TIPO_PROCEDIMENTO_PATH+ "/getByApp",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@LoggingAet
	public ResponseEntity<BaseRestResponse<List<TipoProcedimentoDTO>>> getTipiProcedimento(String application) throws Exception {
		super.checkAbilitazioneFor(Gruppi.values());
		return super.okResponse(tipoProcedimentoService.selectByApplication(Applicativo.valueOf(application.toUpperCase())));
	}
	
	@ApiOperation("Lista tipi procedimento associati al fascicolo ricevuto come parametro ")
	@GetMapping(value = TIPO_PROCEDIMENTO_PATH+"/getByFascicolo",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@LoggingAet
	public ResponseEntity<BaseRestResponse<List<TipoProcedimentoDTO>>> getTipiProcedimentoByFascicolo(Long idFascicolo) throws Exception {
		checkPermission(idFascicolo);
		return super.okResponse(tipoProcedimentoService.findByFascicolo(idFascicolo));
	}
}