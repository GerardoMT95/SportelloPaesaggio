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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import it.eng.tz.puglia.autpae.dto.TabelleAssegnamentoFascicoliDTO;
import it.eng.tz.puglia.autpae.dto.TipologicaDTO;
import it.eng.tz.puglia.autpae.entity.AssegnamentoFascicoloDTO;
import it.eng.tz.puglia.autpae.entity.ConfigurazioneAssegnamentoDTO;
import it.eng.tz.puglia.autpae.entity.StoricoAssegnamentoDTO;
import it.eng.tz.puglia.autpae.entity.ValoriAssegnamentoDTO;
import it.eng.tz.puglia.autpae.enumeratori.Gruppi;
import it.eng.tz.puglia.autpae.enumeratori.Ruoli;
import it.eng.tz.puglia.autpae.enumeratori.TipoOperazione;
import it.eng.tz.puglia.autpae.search.TabelleAssegnamentoFascicoliSearch;
import it.eng.tz.puglia.autpae.service.interfacce.AssegnamentoFascicoloService;
import it.eng.tz.puglia.autpae.service.interfacce.ConfigurazioneAssegnamentoService;
import it.eng.tz.puglia.autpae.service.interfacce.StoricoAssegnamentoService;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.security.util.SecurityUtil;

@RestController
@RequestMapping(value = "assegnazione", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AssegnazioneController extends _RestController {

	private static final Logger log = LoggerFactory.getLogger(AssegnazioneController.class);

	@Autowired private AssegnamentoFascicoloService assegnamentoFascicoloService;
	@Autowired private StoricoAssegnamentoService storicoAssegnamentoService;
	@Autowired private ConfigurazioneAssegnamentoService configurazioneAssegnamentoService;
//	@Autowired private ValoriAssegnamentoService valoriAssegnamentoService;


	
	@GetMapping(value = "/getUtentiForOrganizzazione")
	public ResponseEntity<BaseRestResponse<List<TipologicaDTO>>> getUtentiForOrganizzazione() throws Exception {
		final String jwt=SecurityUtil.getAuthorizationHeader();
		log.info("Chiamato il controller: 'assegnazione'. Metodo: '" + new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Ruoli.AMMINISTRATORE, Ruoli.DIRIGENTE);	checkAbilitazioneFor(Gruppi.ETI_, Gruppi.REG_, Gruppi.SBAP_);
		return super.okResponse(configurazioneAssegnamentoService.getUtentiForOrganizzazione(jwt));
	}
	
/*	@GetMapping(value = "/getPrefissoCodice")
	public ResponseEntity<BaseRestResponse<String>> getPrefissoCodice() throws Exception {
		log.info("Chiamato il controller: 'assegnazione'. Metodo: '" + new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Ruoli.AMMINISTRATORE, Ruoli.DIRIGENTE);	checkAbilitazioneFor(Gruppi.ETI_, Gruppi.REG_, Gruppi.SBAP_);
		return super.okResponse(assegnamentoFascicoloService.getPrefissoCodice());
	}	*/
	
	@GetMapping(value = "/autocompleteCodice")
	public ResponseEntity<BaseRestResponse<List<String>>> autocompleteCodice(@RequestParam(value="codice"	   , required=false) String  codice,
																			 @RequestParam(value="giaAssegnato", required=true ) boolean giaAssegnato) throws Exception {
		log.info("Chiamato il controller: 'amministratore'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Ruoli.AMMINISTRATORE, Ruoli.DIRIGENTE);	checkAbilitazioneFor(Gruppi.ETI_, Gruppi.REG_, Gruppi.SBAP_);
		if (giaAssegnato==true)
			return super.okResponse(assegnamentoFascicoloService.autocompleteCodiceFascicoliAssegnati   (codice));
		else
			return super.okResponse(assegnamentoFascicoloService.autocompleteCodiceFascicoliNonAssegnati(codice));
	}
	
	@GetMapping(value = "/getConfigurazioneForOrganizzazione")
	public ResponseEntity<BaseRestResponse<ConfigurazioneAssegnamentoDTO>> getConfigurazioneForOrganizzazione() throws Exception {
		log.info("Chiamato il controller: 'assegnazione'. Metodo: '" + new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Ruoli.AMMINISTRATORE, Ruoli.DIRIGENTE);	checkAbilitazioneFor(Gruppi.ETI_, Gruppi.REG_, Gruppi.SBAP_);
		return super.okResponse(configurazioneAssegnamentoService.getConfigurazioneForOrganizzazioneUtenteLoggato());
	}

	@PostMapping(value = "/saveConfigurazioneForOrganizzazione")
	public ResponseEntity<BaseRestResponse<Boolean>> saveConfigurazioneForOrganizzazione(@RequestBody ConfigurazioneAssegnamentoDTO configurazione) throws Exception {
		log.info("Chiamato il controller: 'assegnazione'. Metodo: '" + new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Ruoli.AMMINISTRATORE, Ruoli.DIRIGENTE);	checkAbilitazioneFor(Gruppi.ETI_, Gruppi.REG_, Gruppi.SBAP_);
		return super.okResponse(configurazioneAssegnamentoService.saveConfigurazioneForOrganizzazione(configurazione));
	}

	@GetMapping(value = "/configurazioneAutomaticaSearch")
	public ResponseEntity<BaseRestResponse<List<ValoriAssegnamentoDTO>>> configurazioneAutomaticaSearch(@RequestPart  ConfigurazioneAssegnamentoDTO configurazione,
																										@RequestParam(name="idComuneTipoProcedimento", required=false) String idComuneTipoProcedimento,
																										@RequestParam(name="usernameFunzionario"	 , required=false) String usernameFunzionario	  ) throws Exception {
		log.info("Chiamato il controller: 'assegnazione'. Metodo: '" + new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Ruoli.AMMINISTRATORE, Ruoli.DIRIGENTE);	checkAbilitazioneFor(Gruppi.ETI_, Gruppi.REG_, Gruppi.SBAP_);
		return super.okResponse(configurazioneAssegnamentoService.configurazioneAutomaticaSearch(configurazione, idComuneTipoProcedimento, usernameFunzionario));
	}
	
	@GetMapping(value = "/assegnamentoFascicoliSearch")
	public ResponseEntity<BaseRestResponse<PaginatedList<TabelleAssegnamentoFascicoliDTO>>> tabellaAssegnamentoFascicoliSearch(TabelleAssegnamentoFascicoliSearch filter) throws Exception {
		log.info("Chiamato il controller: 'assegnazione'. Metodo: '" + new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Ruoli.AMMINISTRATORE, Ruoli.DIRIGENTE);	checkAbilitazioneFor(Gruppi.ETI_, Gruppi.REG_, Gruppi.SBAP_);
			return super.okResponse(assegnamentoFascicoloService.tabelleAssegnamentoFascicoliSearch(filter));
	}
	
	@GetMapping(value = "/getStoricoAssegnamento")
	public ResponseEntity<BaseRestResponse<List<StoricoAssegnamentoDTO>>> getStoricoAssegnamento(@RequestParam(name="idFascicolo", required=true) long idFascicolo) throws Exception {
		log.info("Chiamato il controller: 'assegnazione'. Metodo: '" + new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Ruoli.AMMINISTRATORE, Ruoli.DIRIGENTE);	checkAbilitazioneFor(Gruppi.ETI_, Gruppi.REG_, Gruppi.SBAP_);
		return super.okResponse(storicoAssegnamentoService.getStoricoAssegnamento(idFascicolo));
	}
	
	@PostMapping(value = "/assegnaFascicolo")
	public ResponseEntity<BaseRestResponse<AssegnamentoFascicoloDTO>> assegnaFascicolo(@RequestBody AssegnamentoFascicoloDTO body) throws Exception {
		log.info("Chiamato il controller: 'assegnazione'. Metodo: '" + new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Ruoli.AMMINISTRATORE, Ruoli.DIRIGENTE);	checkAbilitazioneFor(Gruppi.ETI_, Gruppi.REG_, Gruppi.SBAP_);
		return super.okResponse(assegnamentoFascicoloService.assegnamentoFascicolo(body, TipoOperazione.ASSEGNAZIONE));
	}
	
	@PostMapping(value = "/riassegnaFascicolo")
	public ResponseEntity<BaseRestResponse<AssegnamentoFascicoloDTO>> riassegnaFascicolo(@RequestBody AssegnamentoFascicoloDTO body) throws Exception {
		log.info("Chiamato il controller: 'assegnazione'. Metodo: '" + new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Ruoli.AMMINISTRATORE, Ruoli.DIRIGENTE);	checkAbilitazioneFor(Gruppi.ETI_, Gruppi.REG_, Gruppi.SBAP_);
		return super.okResponse(assegnamentoFascicoloService.assegnamentoFascicolo(body, TipoOperazione.RIASSEGNAZIONE));
	}
	
	@PostMapping(value = "/disassegnaFascicolo")
	public ResponseEntity<BaseRestResponse<AssegnamentoFascicoloDTO>> disassegnaFascicolo(@RequestBody AssegnamentoFascicoloDTO body) throws Exception {
		log.info("Chiamato il controller: 'assegnazione'. Metodo: '" + new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Ruoli.AMMINISTRATORE, Ruoli.DIRIGENTE);	checkAbilitazioneFor(Gruppi.ETI_, Gruppi.REG_, Gruppi.SBAP_);
		return super.okResponse(assegnamentoFascicoloService.assegnamentoFascicolo(body, TipoOperazione.DISASSEGNAZIONE));
	}
	
}