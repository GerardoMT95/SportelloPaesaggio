package it.eng.tz.puglia.autpae.controller;

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

import it.eng.tz.puglia.autpae.dto.BE_to_FE.RubricaAdminSearchDTO;
import it.eng.tz.puglia.autpae.dto.BE_to_FE.RubricaSearchDTO;
import it.eng.tz.puglia.autpae.enumeratori.Gruppi;
import it.eng.tz.puglia.autpae.search.RubricaEnteSearch;
import it.eng.tz.puglia.autpae.search.RubricaIstituzionaleSearch;
import it.eng.tz.puglia.autpae.service.interfacce.RubricaEnteService;
import it.eng.tz.puglia.autpae.service.interfacce.RubricaIstituzionaleService;
import it.eng.tz.puglia.autpae.utility.LoggingAet;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.remote.dto.RubricaEnteDTO;
import it.eng.tz.puglia.servizi_esterni.remote.dto.RubricaIstituzionaleDTO;

@RestController
@RequestMapping(value = "rubrica", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RubricaController extends _RestController {
	
	private static final Logger log = LoggerFactory.getLogger(RubricaController.class);
	
	@Autowired private RubricaIstituzionaleService rubricaIstituzionaleService;
	@Autowired private RubricaEnteService		   rubricaEnteService;

	
	@GetMapping(value = "/istituzionaleSearch")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<PaginatedList<RubricaSearchDTO>>> rubricaIstituzionaleSearch(RubricaSearchDTO filter) throws Exception {
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
	@LoggingAet
	public ResponseEntity<BaseRestResponse<PaginatedList<RubricaAdminSearchDTO>>> rubricaIstituzionaleAdminSearch(RubricaAdminSearchDTO entity) throws Exception {
		checkAbilitazioneFor(Gruppi.ADMIN);
		entity.clean(); 
		return super.okResponse(rubricaIstituzionaleService.adminSearch(entity));
	}
	
	@PutMapping(value = "/istituzionaleAdminUpdate")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<Boolean>> rubricaIstituzionaleAdminUpdate(@RequestBody RubricaAdminSearchDTO entity) throws Exception {
		checkAbilitazioneFor(Gruppi.ADMIN);
		entity.clean();
		return super.okResponse(rubricaIstituzionaleService.update(new RubricaIstituzionaleDTO(entity))==1);
	}
	
	@GetMapping(value = "/enteSearch")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<PaginatedList<RubricaSearchDTO>>> rubricaEnteSearch(RubricaSearchDTO filter) throws Exception {
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
	@LoggingAet
	public ResponseEntity<BaseRestResponse<RubricaSearchDTO>> rubricaEnteInsert(@RequestBody RubricaSearchDTO entity) throws Exception {
		checkAbilitazioneFor(Gruppi.ED_, Gruppi.ETI_, Gruppi.REG_, Gruppi.SBAP_);
		entity.clean();
		entity.setId(rubricaEnteService.insert(new RubricaEnteDTO(entity)));
		return super.okResponse(entity);
	}
	
	@PutMapping(value = "/enteUpdate")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<Boolean>> rubricaEnteUpdate(@RequestBody RubricaSearchDTO entity) throws Exception {
		checkAbilitazioneFor(Gruppi.ED_, Gruppi.ETI_, Gruppi.REG_, Gruppi.SBAP_);
		entity.clean();
		return super.okResponse(rubricaEnteService.update(new RubricaEnteDTO(entity))==1);
	}
	
	@DeleteMapping(value = "/enteDelete")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<Boolean>> rubricaEnteDelete(RubricaSearchDTO entity) throws Exception {
		checkAbilitazioneFor(Gruppi.ED_, Gruppi.ETI_, Gruppi.REG_, Gruppi.SBAP_);
		entity.clean();
		return super.okResponse(rubricaEnteService.delete(new RubricaEnteDTO(entity))==1);
	}
	
}