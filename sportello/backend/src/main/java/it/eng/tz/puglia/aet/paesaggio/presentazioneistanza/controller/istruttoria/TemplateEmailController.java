package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller.istruttoria;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller.RoleAwareController;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Ruoli;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.UserUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TemplateEmailDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.TemplateEmailSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.impl.PraticaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.dto.TemplateEmailDestinatariDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.service.ITemplateMailService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.service.impl.ResolveTemplateService;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.string.StringUtil;

@RestController
@RequestMapping(value = "istruttoria/templateEmail", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TemplateEmailController extends RoleAwareController {

	private static final Logger log = LoggerFactory.getLogger(TemplateEmailController.class);

	@Autowired private UserUtil userUtil;
	@Autowired private ITemplateMailService templateEmailService;
	@Autowired ResolveTemplateService resolveTemplate;
	@Autowired PraticaService praticaService;
	
	
	@GetMapping(value = "/info")
	public ResponseEntity<BaseRestResponse<TemplateEmailDestinatariDto>> info(@RequestParam(name="codice", required=true) String codice) throws Exception {
		log.info("Chiamato il controller: 'templateEmail'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Gruppi.ED_, Gruppi.SBAP_, Gruppi.ETI_,Gruppi.REG_,Gruppi.CL_);
		int idOrganizzazione = userUtil.getIntegerId();
		return super.okResponse(templateEmailService.info(idOrganizzazione,codice));
	}

	/**
	 * erstituisce il record vuoto, pronto per essere compilato e salvato
	 * @author acolaianni
	 *
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/getNew")
	public ResponseEntity<BaseRestResponse<TemplateEmailDestinatariDto>> getNew() throws Exception {
		log.info("Chiamato il controller: 'templateEmail'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Ruoli.AMMINISTRATORE);	checkAbilitazioneFor(Gruppi.ED_, Gruppi.SBAP_, Gruppi.ETI_,Gruppi.REG_);
		int idOrganizzazione = userUtil.getIntegerId();
		Gruppi gruppo = userUtil.getGruppo();
		return super.okResponse(templateEmailService.getNew(idOrganizzazione,gruppo.name()));
	}
	
	@GetMapping(value = "/search")
	public ResponseEntity<BaseRestResponse<PaginatedList<TemplateEmailDTO>>> search(TemplateEmailSearch search) throws Exception {
		log.info("Chiamato il controller: 'templateEmail'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Gruppi.ED_, Gruppi.SBAP_, Gruppi.ETI_,Gruppi.REG_,Gruppi.CL_);
		int idOrganizzazione = userUtil.getIntegerId();
		search.setIdOrganizzazione(idOrganizzazione);
		Gruppi gruppo = userUtil.getGruppo();
		search.setVisibilita(gruppo.name());
		templateEmailService.sincronizza(search);
		return super.okResponse(templateEmailService.search(search));
	}

	@DeleteMapping(value = "/delete")
	public ResponseEntity<BaseRestResponse<Integer>> delete(String codice) throws Exception {
		log.info("Chiamato il controller: 'templateEmail'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Ruoli.AMMINISTRATORE);	checkAbilitazioneFor(Gruppi.ED_, Gruppi.SBAP_, Gruppi.ETI_,Gruppi.REG_);
		int organizzazione = userUtil.getIntegerId();
		Gruppi gruppo = userUtil.getGruppo();
		if(organizzazione==0) {
			throw new CustomOperationToAdviceException("Ruolo non ammesso a questa operazione");
		}
		TemplateEmailDTO pk=new TemplateEmailDTO();
		pk.setIdOrganizzazione(organizzazione);
		pk.setCodice(codice);
		String visibilita = gruppo.name();
		try {
			templateEmailService.find(pk);
			int ret=0;
			if(pk.getCancellabile() && 
			   (StringUtil.isEmpty(pk.getVisibilita()) ||
					   pk.getVisibilita().contains(visibilita) )) {
				ret=templateEmailService.delete(pk);	
			}
			return super.okResponse(ret);
		}catch(EmptyResultDataAccessException e) {
			return super.okResponse(0);
		}
	}
	
	@PostMapping(value = "/saveOrInsert")
	public ResponseEntity<BaseRestResponse<Integer>> saveOrInsert(@RequestBody TemplateEmailDestinatariDto templateDto) throws Exception {
		log.info("Chiamato il controller: 'templateEmail'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Ruoli.AMMINISTRATORE);	checkAbilitazioneFor(Gruppi.ED_, Gruppi.SBAP_, Gruppi.ETI_,Gruppi.REG_);
		int organizzazione = userUtil.getIntegerId();
		Gruppi gruppo = userUtil.getGruppo();
		if(organizzazione==0) {
			throw new CustomOperationToAdviceException("Ruolo non ammesso a questa operazione");
		}
		//seek su template....
		// se esiste copro solo alcuni campi....
		TemplateEmailDTO template = templateDto.getTemplate();
		if(template==null) {
			throw new CustomOperationToAdviceException("Template non valido!!");
		}
		template.setIdOrganizzazione(organizzazione); //l'organizzazione la risetto sempre...
		return super.okResponse(templateEmailService.saveOrInsert(templateDto,gruppo.name()));
	}
	
	/*@GetMapping(value = "/listTemplateEmail")
	public ResponseEntity<BaseRestResponse<List<TemplateDestinatarioDTO>>> listTemplateEmail() throws Exception {
		log.info("Chiamato il controller: 'templateEmail'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Ruoli.AMMINISTRATORE);	checkAbilitazioneFor(Gruppi.ED_, Gruppi.SBAP_, Gruppi.ETI_,Gruppi.REG_);
		int idOrganizzazione = userUtil.getIntegerId();
		return super.okResponse(templateEmailService.infoList(idOrganizzazione));

	}*/
	
	@GetMapping(value = "/searchTemplate")
	public ResponseEntity<BaseRestResponse<List<TemplateEmailDestinatariDto>>> searchTemplate(@RequestParam String sezione,@RequestParam UUID idPratica) throws Exception {
		log.info("Chiamato il controller: 'templateEmail'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		checkAbilitazioneFor(Gruppi.ED_, Gruppi.SBAP_, Gruppi.ETI_,Gruppi.REG_,Gruppi.CL_);
		int idOrganizzazione = userUtil.getIntegerId();
		
		PraticaDTO pratica= new PraticaDTO();
		pratica.setId(idPratica);
		pratica=praticaService.find(pratica);
		
		TemplateEmailSearch search = new TemplateEmailSearch();
		search.setSezione(sezione);
		search .setIdOrganizzazione(idOrganizzazione);
		Gruppi gruppo = userUtil.getGruppo();
		search.setVisibilita(gruppo.name());
		search.setTipiProcedimentoAmmessi("%,"+pratica.getTipoProcedimento()+",%");
		templateEmailService.sincronizza(search);
		PaginatedList<TemplateEmailDTO> tempPagList = templateEmailService.search(search);
		List<TemplateEmailDTO> tempList = tempPagList.getList();
		List<TemplateEmailDestinatariDto> respTempList= new ArrayList<TemplateEmailDestinatariDto>();
		
		
		
		for (TemplateEmailDTO templateEmailDTO : tempList) {
			TemplateEmailDestinatariDto infoTemp = this.templateEmailService.info(idOrganizzazione,templateEmailDTO.getCodice());
			String oggetto=resolveTemplate.risolviTesto(templateEmailDTO.getPlaceholders(), infoTemp.getTemplate().getOggetto(), pratica);
			infoTemp.getTemplate().setOggetto(oggetto);
			String testo=resolveTemplate.risolviTesto(templateEmailDTO.getPlaceholders(), infoTemp.getTemplate().getTesto(), pratica);
			infoTemp.getTemplate().setTesto(testo);
			respTempList.add(infoTemp);
			
		}
		return super.okResponse(respTempList);
	}	
	
}