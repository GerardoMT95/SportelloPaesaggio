package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller.istruttoria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller.RoleAwareController;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Ruoli;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ConfigurazioniEnteDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.impl.ConfigurazioniEnteService;
import it.eng.tz.puglia.bean.BaseRestResponse;

@RestController
@RequestMapping(value = "configurazioniEnte", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ConfigurazioniEnteController extends RoleAwareController {
	
	private static final Logger log = LoggerFactory.getLogger(ConfigurazioniEnteController.class);
	
	@Autowired private ConfigurazioniEnteService configurazioniEnteService;

	
	@PostMapping(value = "/save")
	public ResponseEntity<BaseRestResponse<ConfigurazioniEnteDTO>> configurazioneEnteSave(@RequestBody(required=true) ConfigurazioniEnteDTO dto) throws Exception {
		log.info("Chiamato il controller: 'configurazioniEnte'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Ruoli.AMMINISTRATORE);
		return super.okResponse(configurazioniEnteService.inserisciOaggiorna(dto));
	}
	
	@GetMapping(value = "/find")
	public ResponseEntity<BaseRestResponse<ConfigurazioniEnteDTO>> configurazioneEnteFind() throws Exception {
		log.info("Chiamato il controller: 'configurazioniEnte'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Ruoli.AMMINISTRATORE);
		return super.okResponse(configurazioniEnteService.find(super.myEnteId().intValue()));
	}
	
	@GetMapping(value = "/hasProtocollo")
	public ResponseEntity<BaseRestResponse<Boolean>> hasProtocolloAutomatico(@RequestParam(required=true)Integer idOrganizzazione) throws Exception {
		log.info("Chiamato il controller: 'configurazioniEnte'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		Boolean hasProto=false;
		try {
			ConfigurazioniEnteDTO ret = configurazioniEnteService.find(idOrganizzazione);
			hasProto=ret.isSistemaProtocollazione();
		}catch(Exception e) {
			log.error("Errore nel retrieval della configurazione dell'organizzazione con id "+idOrganizzazione);
		}
		return super.okResponse(hasProto);
	}
	
	@GetMapping(value = "/hasPagamenti")
	public ResponseEntity<BaseRestResponse<Boolean>> hasPagamenti(@RequestParam(required=true)Integer idOrganizzazione) throws Exception {
		log.info("Chiamato il controller: 'configurazioniEnte'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		Boolean hasProto=false;
		try {
			ConfigurazioniEnteDTO ret = configurazioniEnteService.find(idOrganizzazione);
			hasProto=ret.isSistemaPagamento();
		}catch(Exception e) {
			log.error("Errore nel retrieval della configurazione dell'organizzazione con id "+idOrganizzazione);
		}
		return super.okResponse(hasProto);
	}
	
}