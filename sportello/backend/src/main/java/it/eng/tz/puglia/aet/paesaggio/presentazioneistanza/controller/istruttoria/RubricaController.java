package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller.istruttoria;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller.RoleAwareController;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.RubricaAdminSearchDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.RubricaSearchDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.search.RubricaEnteSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.search.RubricaIstituzionaleSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.service.interfacce.RubricaEnteService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.service.interfacce.RubricaIstituzionaleService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.RubricaEnteDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.RubricaIstituzionaleDTO;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.bean.PaginatedList;

@RestController
@RequestMapping(value = "rubrica", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RubricaController extends RoleAwareController {
	
	private static final Logger log = LoggerFactory.getLogger(RubricaController.class);
	
	@Autowired private RubricaIstituzionaleService rubricaIstituzionaleService;
	@Autowired private RubricaEnteService		   rubricaEnteService;

	
	@GetMapping(value = "/istituzionaleSearch")
	public ResponseEntity<BaseRestResponse<PaginatedList<RubricaSearchDTO>>> rubricaIstituzionaleSearch(RubricaSearchDTO filter) throws Exception {
		log.info("Chiamato il controller: 'rubrica'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		filter.clean();
		PaginatedList<RubricaSearchDTO> listaPaginata = new PaginatedList<>();
	    listaPaginata.setList(new ArrayList<RubricaSearchDTO>());
		listaPaginata.setCount(0);

		PaginatedList<RubricaIstituzionaleDTO> listaPaginataIstituzionale = rubricaIstituzionaleService.search(new RubricaIstituzionaleSearch(filter));
		
		if (listaPaginataIstituzionale!=null && listaPaginataIstituzionale.getList()!=null && !listaPaginataIstituzionale.getList().isEmpty()) {
			listaPaginataIstituzionale.getList().forEach(elem->{
				listaPaginata.getList().add(new RubricaSearchDTO(elem));
			});
			listaPaginata.setCount(listaPaginataIstituzionale.getCount());
		}
		return super.okResponse(listaPaginata);
	}
	
	@GetMapping(value = "/istituzionaleAdminSearch")
	public ResponseEntity<BaseRestResponse<PaginatedList<RubricaAdminSearchDTO>>> rubricaIstituzionaleAdminSearch(RubricaAdminSearchDTO entity) throws Exception {
		log.info("Chiamato il controller: 'rubrica'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Gruppi.ADMIN);
		entity.clean(); 
		return super.okResponse(rubricaIstituzionaleService.adminSearch(entity));
	}
	
	@PutMapping(value = "/istituzionaleAdminUpdate")
	public ResponseEntity<BaseRestResponse<Boolean>> rubricaIstituzionaleAdminUpdate(@RequestBody RubricaAdminSearchDTO entity) throws Exception {
		log.info("Chiamato il controller: 'rubrica'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Gruppi.ADMIN);
		entity.clean();
		return super.okResponse(rubricaIstituzionaleService.update(new RubricaIstituzionaleDTO(entity))==1);
	}
	
	@GetMapping(value = "/enteSearch")
	public ResponseEntity<BaseRestResponse<PaginatedList<RubricaSearchDTO>>> rubricaEnteSearch(RubricaSearchDTO filter) throws Exception {
		log.info("Chiamato il controller: 'rubrica'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		filter.clean();
		PaginatedList<RubricaSearchDTO> listaPaginata = new PaginatedList<>();
		listaPaginata.setList(new ArrayList<RubricaSearchDTO>());
	    listaPaginata.setCount(0);

		PaginatedList<RubricaEnteDTO> listaPaginataEnte = rubricaEnteService.search(new RubricaEnteSearch(filter));
		
		if (listaPaginataEnte!=null && listaPaginataEnte.getList()!=null && !listaPaginataEnte.getList().isEmpty()) {
			listaPaginataEnte.getList().forEach(elem->{
				listaPaginata.getList().add(new RubricaSearchDTO(elem));
			});
			listaPaginata.setCount(listaPaginataEnte.getCount());
		}
		return super.okResponse(listaPaginata);
	}
	
	@PostMapping(value = "/enteInsert")
	public ResponseEntity<BaseRestResponse<RubricaSearchDTO>> rubricaEnteInsert(@RequestBody RubricaSearchDTO entity) throws Exception {
		log.info("Chiamato il controller: 'rubrica'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Gruppi.ED_, Gruppi.ETI_, Gruppi.REG_, Gruppi.SBAP_,Gruppi.CL_);
		entity.clean();
		entity.setId(rubricaEnteService.insert(new RubricaEnteDTO(entity)));
		return super.okResponse(entity);
	}
	
	@PutMapping(value = "/enteUpdate")
	public ResponseEntity<BaseRestResponse<Boolean>> rubricaEnteUpdate(@RequestBody RubricaSearchDTO entity) throws Exception {
		log.info("Chiamato il controller: 'rubrica'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Gruppi.ED_, Gruppi.ETI_, Gruppi.REG_, Gruppi.SBAP_,Gruppi.CL_);
		entity.clean();
		return super.okResponse(rubricaEnteService.update(new RubricaEnteDTO(entity))==1);
	}
	
	@DeleteMapping(value = "/enteDelete")
	public ResponseEntity<BaseRestResponse<Boolean>> rubricaEnteDelete(RubricaSearchDTO entity) throws Exception {
		log.info("Chiamato il controller: 'rubrica'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Gruppi.ED_, Gruppi.ETI_, Gruppi.REG_, Gruppi.SBAP_,Gruppi.CL_);
		entity.clean();
		return super.okResponse(rubricaEnteService.delete(new RubricaEnteDTO(entity))==1);
	}
	
}