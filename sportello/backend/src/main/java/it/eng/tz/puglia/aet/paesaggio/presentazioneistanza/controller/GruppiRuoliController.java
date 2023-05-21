package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.dto.UtenteAttributeDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.dto.UtenteDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.repository.UtenteAttributeRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.common.orm.repository.UtenteRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainStringValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.UserUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.UserUtilImpl;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.EnteResponseBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.GruppiRuoliDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.ProfileUserResponseBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.repository.CommonRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean.UtenteAttributeBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.GruppiRuoliService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria.impl.RupService;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.bean.UserDetail;
import it.eng.tz.puglia.security.util.SecurityUtil;
import it.eng.tz.puglia.util.log.LogUtil;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class GruppiRuoliController extends RoleAwareController {
	
	private static final Logger log = LoggerFactory.getLogger(GruppiRuoliController.class);

	@Autowired private UserUtilImpl userUtilImpl;
	@Autowired private GruppiRuoliService gruppiRuoliService;
	@Autowired private RupService rupService;
	@Autowired private UserUtil userUtil;
	@Autowired private CommonRepository commonDao;


	/**
	 * TODO: @Antonio La Gatta This should be removed once the service user-info
	 * starts to return correct roles for all the clients. Currently just some
	 * clients are getting correct roles, but others does not.
	 * 
	 * That is why this endpoint is created as a workaround until the service 
	 * user-info is fixed.
	 * 
	 * @return
	 */
/*	@GetMapping(value = "ruoli")	// TODO: il FE non deve più chiamare questo metodo!
	public ResponseEntity<BaseRestResponse<Set<String>>> getRoles() {
		log.info("Chiamato il controller: 'gruppi-ruoli'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		return super.okResponse(new HashSet<String>());
	}	*/

/*	@Loggable
	protected Set<String> getUserRoles() {
		// prendo la lista dei ruoli dell'utente loggato
    	List<String> listRoles = SecurityUtil.getUserDetail().getRoles();
    	// trasformo la lista in un set
    	Set<String> setRoles = new HashSet<String>();
    	for (String role : listRoles) {
    		if(AutPaeRole.getAutPaeRole(role)!=AutPaeRole.EMPTY.name()) {
    			setRoles.add(AutPaeRole.getAutPaeRole(role));	
    		}
		}
    	return setRoles;
	}	*/

/*	@GetMapping(value = "autorita-procedurale")
	public ResponseEntity<BaseRestResponse<Set<PlainTypeStringIdDTO>>> proceduralAuthority() throws Exception {
		log.info("Chiamato il controller: 'gruppi-ruoli'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		Set<PlainTypeStringIdDTO> ret = userUtilImpl.getMyComuniEnte().stream().map(ente -> {
			PlainTypeStringIdDTO dto = new PlainTypeStringIdDTO();
			dto.setId(ente.getCodiceGruppo());
			dto.setNome(ente.getDenominazione());
			return dto;
		}).collect(Collectors.toSet());
	  //return super.okResponse(PlainTypeStringIdDTO.creaDaTipologicaDTO(this.getProceduralAuthority()));
		return super.okResponse(ret);
	}	*/

	
	/**
	 * viene filtrato il gruppo RICHIEDENTI
	 * @author acolaianni
	 *
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "autorita-procedurale-nuovo-istruttoria")
	public ResponseEntity<BaseRestResponse<List<GruppiRuoliDTO>>> proceduralAuthorityNuovoIstruttoria() throws Exception {
		log.info("Chiamato il controller: 'gruppi-ruoli'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		return super.okResponse(GruppiRuoliDTO.creaDaPlainStringValueLabel(this.getProceduralAuthorityIstruttoria()));
	}
	
	@GetMapping(value = "autorita-procedurale-nuovo")
	public ResponseEntity<BaseRestResponse<List<GruppiRuoliDTO>>> proceduralAuthorityNuovo() throws Exception {
		log.info("Chiamato il controller: 'gruppi-ruoli'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		return super.okResponse(GruppiRuoliDTO.creaDaPlainStringValueLabel(this.getProceduralAuthority()));
	}

//	@Loggable
	public Set<PlainStringValueLabel> getProceduralAuthority() throws Exception {
		log.info("Chiamato il controller: 'gruppi-ruoli'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		final String cf = this.commonDao.getCfUtenteByUsername(SecurityUtil.getUsername());
		if(!cf.equals(SecurityUtil.getUserDetail().getFiscalCode())) {
			this.commonDao.setCfUtenteByUsername(SecurityUtil.getUsername(), SecurityUtil.getUserDetail().getFiscalCode());
		}
		return userUtilImpl.getMyEnti().stream().map(ente -> {
			PlainStringValueLabel dto = new PlainStringValueLabel();
			dto.setValue	  (ente.getCodiceGruppo());
			dto.setDescription(ente.getDenominazione());
			return dto;
		}).collect(Collectors.toSet());
	}
	
	public Set<PlainStringValueLabel> getProceduralAuthorityIstruttoria() throws Exception {
		log.info("Chiamato il controller: 'gruppi-ruoli'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		this.gruppiRuoliService.upsertInfoUser();
		List<EnteResponseBean> myComuniEnte = userUtilImpl.getMyEnti();
		return myComuniEnte.stream().filter(gruppo->!gruppo.getCodiceGruppo().equals(Gruppi.RICHIEDENTI.name()) &&
													 		 !gruppo.getCodiceGruppo().equals(Gruppi.USER_CL.name())).map(ente -> {
			PlainStringValueLabel dto = new PlainStringValueLabel();
			dto.setValue	  (ente.getCodiceGruppo());
			dto.setDescription(ente.getDenominazione());
			return dto;
		}).collect(Collectors.toSet());
	}

	@GetMapping(value = "checkGruppo")
	public ResponseEntity<BaseRestResponse<Boolean>> checkGruppo(@RequestParam(name="codiceGruppo", required=true) String codice_GruppoIdRuolo) throws Exception{
		log.info("Chiamato il controller: 'gruppi-ruoli'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		gruppiRuoliService.checkGruppo(codice_GruppoIdRuolo);
		//uso questa chiamata per dare l'info al FE se l'utente è RUp per il gruppo selezionato
		Boolean isRup = null;
		if(userUtil.getGruppo(codice_GruppoIdRuolo).equals(Gruppi.ED_))
			isRup = rupService.isRup(LogUtil.getUser(), userUtil.getIntegerId(codice_GruppoIdRuolo));
		return super.okResponse(isRup);
	}

	/**
	 * restituisce tutti i gruppi relativi alle organizzazioni attive al momento per poter richiedere eventuale abilitazione
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "gruppiOrganizzazioni")
	public ResponseEntity<BaseRestResponse<List<PlainStringValueLabel>>> getGruppiOrganizzazioni() throws Exception {
		log.info("Chiamato il controller: 'gruppi-ruoli'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		return super.okResponse(gruppiRuoliService.getGruppiOrganizzazioni());
	}
	
	/**
	 * restituisce tutti i gruppi relativi alle organizzazioni attive al momento per poter richiedere eventuale abilitazione (comprese le commissioni locali)
	 * @author Alessio Bottalico
	 * @date 23 set 2022
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "gruppiOrganizzazioniConCL")
	public ResponseEntity<BaseRestResponse<List<PlainStringValueLabel>>> getGruppiOrganizzazioniConCL() throws Exception {
		log.info("Chiamato il controller: 'gruppi-ruoli'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		return super.okResponse(gruppiRuoliService.getGruppiOrganizzazioniConCL());
	}
	
	@PostMapping(value = "updateUtenteAttribute")
	public ResponseEntity<BaseRestResponse<Boolean>> updateUtenteAttribute(@RequestBody(required=true) UtenteAttributeBean bean) throws Exception {
		log.info("Chiamato il controller: 'gruppi-ruoli'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		try {
			this.gruppiRuoliService.updateUtenteAttribute(bean);
			return super.okResponse(true);
		} catch(Exception e) {
			return super.koResponse(e, new BaseRestResponse<>());
		}
	}

}