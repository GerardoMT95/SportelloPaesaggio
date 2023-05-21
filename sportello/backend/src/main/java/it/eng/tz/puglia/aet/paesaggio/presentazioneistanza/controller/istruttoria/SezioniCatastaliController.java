package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller.istruttoria;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller.RoleAwareController;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.RubricaAdminSearchDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.RubricaSearchDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.search.RubricaEnteSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.search.RubricaIstituzionaleSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.service.interfacce.RubricaEnteService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.service.interfacce.RubricaIstituzionaleService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.SelectOptionDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Ruoli;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.RupDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.RupSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.SezioneCatastaleSearchDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.RubricaEnteDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.RubricaIstituzionaleDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.service.RemoteSchemaService;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.bean.PaginatedList;

@RestController
@RequestMapping(value = "sezCatastaliConfig", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SezioniCatastaliController extends RoleAwareController {
	
	private static final Logger log = LoggerFactory.getLogger(SezioniCatastaliController.class);
	
	@Autowired private RemoteSchemaService remoteSchemaService;

	
	@GetMapping(value = "/sezioniCatastaliSearch")
	public ResponseEntity<BaseRestResponse<PaginatedList<SelectOptionDto>>> rubricaIstituzionaleSearch(SezioneCatastaleSearchDTO filter) throws Exception {
		log.info("Chiamato il controller: 'SezioniCatastali'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Gruppi.ADMIN);
		PaginatedList<SelectOptionDto> lista = remoteSchemaService.sezioneCatastaleSearch(filter);
		return super.okResponse(lista);
	}
	
	@DeleteMapping(value = "/sezioniCatastaliDelete")
	public ResponseEntity<BaseRestResponse<Integer>> delete(@RequestParam(value="codCatastale"	   , required=true) String  codCatastale,
			@RequestParam(value="sezione"	   , required=true) String  sezione) throws Exception {
		log.info("Chiamato il controller: 'SezioniCatastali'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Gruppi.ADMIN);
		try {
			int ret=remoteSchemaService.deleteSezioneCatastale(codCatastale,sezione);
			return super.okResponse(ret);
		}catch(EmptyResultDataAccessException e) {
			return super.okResponse(0);
		}
	}
	
	@PostMapping(value = "/sezioniCatastaliSaveOrUpdate")
	public ResponseEntity<BaseRestResponse<Integer>> saveOrUpdate(@RequestBody SelectOptionDto item) throws Exception {
		log.info("Chiamato il controller: 'SezioniCatastali'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Gruppi.ADMIN);
		try {
			int ret=remoteSchemaService.saveOrUpdateSezioneCatastale(item);
			return super.okResponse(ret);
		}catch(Exception e) {
			logger.error("errore in saveOrUpdate ",e);
			return super.okResponse(1);
		}
	}
	
}
