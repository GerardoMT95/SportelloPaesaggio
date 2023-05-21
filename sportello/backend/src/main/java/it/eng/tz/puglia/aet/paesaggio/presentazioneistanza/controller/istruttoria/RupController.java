package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller.istruttoria;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller.RoleAwareController;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.service.interfacce.ConfigurazioneAssegnamentoService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainStringValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Ruoli;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.UserUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.RupDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.RupSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.GruppiRuoliService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria.impl.RupService;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.bean.PaginatedList;

@RestController
@RequestMapping(value = "rup", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RupController extends RoleAwareController {
	
	private static final Logger log = LoggerFactory.getLogger(RupController.class);
	@Autowired private RupService  rupSvc;
	@Autowired private GruppiRuoliService gruppiSvc;
	@Autowired private UserUtil userUtil;
	@Autowired private ConfigurazioneAssegnamentoService assegnamentoSvc;

	
	@GetMapping(value = "/search")
	public ResponseEntity<BaseRestResponse<PaginatedList<RupDTO>>> rupSearch(RupSearch filter) throws Exception {
		log.info("Chiamato il controller: 'rup'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		gruppiSvc.checkAbilitazioneFor(Ruoli.AMMINISTRATORE);
		gruppiSvc.checkAbilitazioneFor(Gruppi.ED_);
		int organizzazione = userUtil.getIntegerId();
		if(organizzazione==0) {
			throw new CustomOperationToAdviceException("Ruolo non ammesso a questa operazione");
		}
		filter.setIdOrganizzazione(organizzazione+"");
		return super.okResponse(rupSvc.search(filter));
	}
	
	
	/**
	 * restituisce la lista dei funzionari 
	 * @author acolaianni
	 *
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/potenzialiRup")
	public ResponseEntity<BaseRestResponse<List<PlainStringValueLabel>>> potenzialiRup() throws Exception {
		log.info("Chiamato il controller: 'rup'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		gruppiSvc.checkAbilitazioneFor(Ruoli.AMMINISTRATORE);
		gruppiSvc.checkAbilitazioneFor(Gruppi.ED_);
		List<PlainStringValueLabel> ret = assegnamentoSvc.getFunzionariForOrganizzazione();
		return super.okResponse(ret);
	}
	
	@DeleteMapping(value = "/deleteRup")
	public ResponseEntity<BaseRestResponse<Integer>> delete(RupSearch rup) throws Exception {
		log.info("Chiamato il controller: 'rup'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		gruppiSvc.checkAbilitazioneFor(Ruoli.AMMINISTRATORE);
		gruppiSvc.checkAbilitazioneFor(Gruppi.ED_);
		int organizzazione = userUtil.getIntegerId();
		if(organizzazione==0) {
			throw new CustomOperationToAdviceException("Ruolo non ammesso a questa operazione");
		}
		RupDTO pk=new RupDTO();
		pk.setIdOrganizzazione(organizzazione);
		pk.setUsername(rup.getUsername());
		try {
			rupSvc.find(pk);
			int ret=rupSvc.delete(pk);
			return super.okResponse(ret);
		}catch(EmptyResultDataAccessException e) {
			return super.okResponse(0);
		}
	}
	
	@PostMapping(value = "/addRup")
	public ResponseEntity<BaseRestResponse<Integer>> addRup(@RequestBody RupDTO rup) throws Exception {
		log.info("Chiamato il controller: 'rup'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		gruppiSvc.checkAbilitazioneFor(Ruoli.AMMINISTRATORE);
		gruppiSvc.checkAbilitazioneFor(Gruppi.ED_);
		int organizzazione = userUtil.getIntegerId();
		if(organizzazione==0) {
			throw new CustomOperationToAdviceException("Ruolo non ammesso a questa operazione");
		}
		rup.setIdOrganizzazione(organizzazione); //l'organizzazione la risetto sempre...
		RupDTO pk=new RupDTO();
		pk.setIdOrganizzazione(rup.getIdOrganizzazione());
		pk.setUsername(rup.getUsername());
		try {
			rupSvc.find(pk);
			//update
			int ret=rupSvc.update(rup);
			return super.okResponse(ret);
		}catch(Exception e) {
			rupSvc.insert(rup);
			return super.okResponse(1);
		}
	}
	
	
	
	
}