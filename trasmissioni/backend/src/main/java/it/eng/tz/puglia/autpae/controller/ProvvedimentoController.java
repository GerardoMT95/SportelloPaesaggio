package it.eng.tz.puglia.autpae.controller;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.eng.tz.puglia.autpae.controller.exception.CustomOperationToAdviceException;
import it.eng.tz.puglia.autpae.dto.AllegatoCustomDTO;
import it.eng.tz.puglia.autpae.dto.ProvvedimentoTabDTO;
import it.eng.tz.puglia.autpae.enumeratori.Gruppi;
import it.eng.tz.puglia.autpae.enumeratori.TipoAllegato;
import it.eng.tz.puglia.autpae.service.interfacce.ProvvedimentoService;
import it.eng.tz.puglia.autpae.utility.LoggingAet;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.configuration.security.SecurityConfig;
import it.eng.tz.puglia.configuration.swagger.OpenApiConfig;

@RestController
//@RequestMapping(value = {"provvedimento","api-gateway/provvedimento"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ProvvedimentoController extends CheckGroupAbstractController //extends _RestController 
{

	private final String PROVVEDIMENTO_PATH="provvedimento";
	private final String API_PROVVEDIMENTO_PATH=SecurityConfig.API_GATEWAY_VALUE +"/"+PROVVEDIMENTO_PATH;
	private static final Logger log = LoggerFactory.getLogger(ProvvedimentoController.class);
	
	@Autowired private ProvvedimentoService provvedimentoService;

	
	@GetMapping(value = PROVVEDIMENTO_PATH+"/get",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@LoggingAet
	public ResponseEntity<BaseRestResponse<ProvvedimentoTabDTO>> tabProvvedimento() throws Exception {
		super.checkAbilitazioneFor(Gruppi.values());
		return super.okResponse(new ProvvedimentoTabDTO());
	}
	
	
	@GetMapping(value = PROVVEDIMENTO_PATH+"/dati",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@LoggingAet
	public ResponseEntity<BaseRestResponse<ProvvedimentoTabDTO>> datiProvvedimento(@RequestParam(name="idFascicolo", required=true) long idFascicolo) throws Exception {
		super.checkPermission(idFascicolo);
		return super.okResponse(provvedimentoService.datiProvvedimento(idFascicolo));
	}
	
	
	@Operation(summary = "Caricamento allegato previsto nel pannello provvedimento:PROVVEDIMENTO_FINALE,PARERE_MIBAC. Un secondo upload copre l'eventuale contenuto precedente.", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@PostMapping(value = {PROVVEDIMENTO_PATH+"/carica",API_PROVVEDIMENTO_PATH+"/carica"}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@LoggingAet
	public ResponseEntity<BaseRestResponse<AllegatoCustomDTO>> caricaAllegato(@RequestParam(name="idFascicolo",  required=true) long idFascicolo,
																			  @Parameter(description="Tipi ammessi: PROVVEDIMENTO_FINALE,PARERE_MIBAC,PROVVEDIMENTO_FINALE_PRIVATO,PARERE_MIBAC_PRIVATO")
																			  @RequestParam(name="tipoAllegato", required=true) TipoAllegato tipoAllegato, 
																			  @Parameter(description="File allegato con dimensione massima ammessa di 50MB")
																			  @RequestBody MultipartFile file) throws Exception 
	{
		//checkAbilitazioneFor(Gruppi.ED_);
		checkAbilitazioneForTrasmissione();
		super.check();
		// FIXME: usare i metodi generici relativi ad "Allegato"		
		if (file==null || file.isEmpty()) {
			log.error("Allegato vuoto o non trovato!");
			CustomOperationToAdviceException e = new CustomOperationToAdviceException("Allegato vuoto o non trovato!");
			e.setTitle("allegato mancante");
			throw e;
		}
		if (tipoAllegato == null) {
			log.error("La tipologia di allegato non è specificata!");
			CustomOperationToAdviceException e=new CustomOperationToAdviceException("La tipologia di allegato non è specificata!");
			e.setTitle("tipoAllegato mancante");
			throw e;
		}
		else if(Arrays.asList(TipoAllegato.tipiProvvedimento()).contains(tipoAllegato)) {
			checkPermission(idFascicolo);
			return super.okResponse(provvedimentoService.inserisciAllegato(idFascicolo, tipoAllegato, file));
		}
		else {
			log.error("Tipologia di allegato non riconosciuta o non consentita!");
			CustomOperationToAdviceException e=new 
					CustomOperationToAdviceException("Tipologia di allegato non riconosciuta o non consentita, ammessi solo:"+Arrays.deepToString(TipoAllegato.tipiProvvedimento()));
			e.setTitle("tipoAllegato non valido");
			throw e; 
		}
	}
	
	/**
	 * sembra non essere usato in FE
	 * @autor Adriano Colaianni
	 * @date 20 mag 2021
	 * @param idFascicolo
	 * @param idAllegato
	 * @param nuovoTipo
	 * @return
	 * @throws Exception
	 */
	@PutMapping(value = PROVVEDIMENTO_PATH+"/cambiaTipo",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@LoggingAet
	public ResponseEntity<BaseRestResponse<Boolean>> cambiaTipo(@RequestParam(name="idFascicolo",  required=true) long idFascicolo,
																@RequestParam(name="idAllegato",   required=true) long idAllegato,
																@RequestParam(name="tipoAllegato", required=true) TipoAllegato nuovoTipo) throws Exception {
		checkAbilitazioneForTrasmissione();
		super.checkPermission(idFascicolo);
		super.check();
		return super.okResponse(provvedimentoService.cambiaTipo(idFascicolo, idAllegato, nuovoTipo));
	}
	
}