package it.eng.tz.puglia.autpae.controller;


import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.eng.tz.puglia.autpae.controller.exception.CustomOperationToAdviceException;
import it.eng.tz.puglia.autpae.dto.BE_to_FE.organizzazioni.PaesaggioOrganizzazioneBean;
import it.eng.tz.puglia.autpae.dto.config.TipoDocumentoSportelloPaesaggioConfigDTO;
import it.eng.tz.puglia.autpae.enumeratori.Gruppi;
import it.eng.tz.puglia.autpae.enumeratori.StatoFascicolo;
import it.eng.tz.puglia.autpae.enumeratori.TipoAllegato;
import it.eng.tz.puglia.autpae.generated.orm.dto.VPaesaggioProvvedimentiDTO;
import it.eng.tz.puglia.autpae.generated.orm.search.VPaesaggioProvvedimentiSearch;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoService;
import it.eng.tz.puglia.autpae.service.interfacce.GruppiRuoliService;
import it.eng.tz.puglia.autpae.service.interfacce.IConfigurazioneService;
import it.eng.tz.puglia.autpae.service.interfacce.PaesaggioOrganizzazioneService;
import it.eng.tz.puglia.autpae.service.interfacce.SportelloService;
import it.eng.tz.puglia.autpae.utility.LoggingAet;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.configuration.swagger.OpenApiConfig;
import it.eng.tz.puglia.error.exception.CustomOperationException;
import it.eng.tz.puglia.security.util.SecurityUtil;
import it.eng.tz.puglia.servizi_esterni.profileManager.feign.UserUtil;
import it.eng.tz.puglia.servizi_esterni.remote.dto.EnteDTO;
import it.eng.tz.puglia.servizi_esterni.remote.service.interfacce.CommonService;

/**
 * controller per gestione dell'import provvedimento su bozza trasmissione a partire da documenti 
 * aggiunti da soggetti coninvolti su sportello unico ambiente
 * @author Adriano Colaianni
 * @date 27 lug 2022
 */
@RestController
public class SportelloAmbienteController extends CheckGroupAbstractController 
{
	final static String SPORTELLO_PATH = "sportello";
	private static final Logger log = LoggerFactory.getLogger(SportelloAmbienteController.class);

	@Autowired
	SportelloService sportelloSvc;
	
	@Autowired
	GruppiRuoliService gruppiRuoliService;
	
	@Autowired
	UserUtil userUtil;
	
	@Autowired
	PaesaggioOrganizzazioneService paeOrgSvc;
	
	@Autowired
	CommonService commonSvc;
	
	@Autowired
	IConfigurazioneService configSvc;
	
	@Autowired
	AllegatoService allSvc;
	
	
	@PostMapping(value = { SPORTELLO_PATH + "/importa"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@LoggingAet
	@Operation(summary = "Importazione in un fascicolo a stato bozza di provvedimento aggiunto su sportello ambiente",
	security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	public ResponseEntity<BaseRestResponse<String>> importa(
			@RequestParam(name = "idFascicolo", required = true) long idFascicolo,
			@RequestParam(name = "codiceTrasmissione", required = true) String codiceTrasmissione,
			@RequestParam(name = "idProvvedimento", required = true) String idProvvedimento
			) throws Exception {
		EnteDTO enteDTO = checkAccessoAndGetEnte(idFascicolo);
		String infoMessage = sportelloSvc.importProvvedimento(Long.valueOf(enteDTO.getId().longValue()), enteDTO.getCodiceFiscale(), 
				codiceTrasmissione, UUID.fromString(idProvvedimento), Long.valueOf(idFascicolo),
				SecurityUtil.getUsername());
		return super.okResponse(infoMessage);
	}


	private EnteDTO checkAccessoAndGetEnte(long idFascicolo) throws CustomOperationToAdviceException, Exception {
		gruppiRuoliService.checkAbilitazioneFor(Gruppi.ED_);
		checkPermission(idFascicolo,StatoFascicolo.WORKING);
		PaesaggioOrganizzazioneBean org = paeOrgSvc.findPaesaggioOrganizzazione((long)userUtil.getIntegerId());
		Integer idEnte = org.getEnteId();
		EnteDTO enteDTO=commonSvc.findEnteById(idEnte);
		if(org.getEnteId()==null) {
			final String message="Impossibile determinare l'ente di riferimento dell'utente !!!!";
			log.info(message);
			throw new CustomOperationToAdviceException(message);
		}
		return enteDTO;
	}

	

	@GetMapping(value = { SPORTELLO_PATH + "/provvedimentiUtilizzabili"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@LoggingAet
	@Operation(summary = "Fetch potenziali documenti che possono essere importati", 
	security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	public ResponseEntity<BaseRestResponse<PaginatedList<VPaesaggioProvvedimentiDTO>>> provvedimentiUtilizzabili(
			VPaesaggioProvvedimentiSearch search) throws Exception {
		gruppiRuoliService.checkAbilitazioneFor(Gruppi.ED_);
		Long idFascicolo=null;
		try {
			idFascicolo=Long.parseLong(search.getIdFascicoloPaesaggio());
		}catch(Exception e) {
			throw new CustomOperationToAdviceException("id fascicolo errato !!!");
		}
		checkPermission(idFascicolo,StatoFascicolo.WORKING);
		PaesaggioOrganizzazioneBean org = paeOrgSvc.findPaesaggioOrganizzazione((long)userUtil.getIntegerId());
		if(org.getEnteId()==null) {
			log.info("Impossibile determinare l'ente di riferimento dell'utente !!!!");
			return super.okResponse(new PaginatedList<VPaesaggioProvvedimentiDTO>(null,0));
		}
		Integer idEnte = org.getEnteId();
		EnteDTO enteDTO=commonSvc.findEnteById(idEnte);
		if(sportelloSvc.hasImport(idFascicolo)) {
			throw new CustomOperationToAdviceException("Sul fascicolo risulta già effettuato un import impossibile procedere !!!");
		}
		TipoDocumentoSportelloPaesaggioConfigDTO configSportello = 
				configSvc.findConfigurazioneCorrente(new Date(), it.eng.tz.puglia.autpae.dto.config.TipoDocumentoSportelloPaesaggioConfigDTO.class);
		search.setIdTipoCaricaDocumentoAmmessi(configSportello.getTipiSelezionati());
		search.setEnteId(idEnte+"");
		search.setCodiceFiscaleEnte(enteDTO.getCodiceFiscale());
		PaginatedList<VPaesaggioProvvedimentiDTO> provvedimenti = sportelloSvc.search(search);
		return super.okResponse(provvedimenti);
	}
	
	
	@GetMapping(value = { SPORTELLO_PATH + "/provvedimentiUtilizzabili/download"}, 
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@LoggingAet
	@Operation(summary = "download del provvedimento", 
	security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	public void download(
			@RequestParam(name = "idFascicolo", required = true) long idFascicolo,
			@RequestParam(name = "codiceTrasmissione", required = true) String codiceTrasmissione,
			@RequestParam(name = "idProvvedimento", required = true) String idProvvedimento,
			HttpServletResponse response
			) throws Exception {
		EnteDTO enteDTO = checkAccessoAndGetEnte(idFascicolo);
		TipoDocumentoSportelloPaesaggioConfigDTO configSportello = 
				configSvc.findConfigurazioneCorrente(new Date(), it.eng.tz.puglia.autpae.dto.config.TipoDocumentoSportelloPaesaggioConfigDTO.class);
		VPaesaggioProvvedimentiSearch search=new VPaesaggioProvvedimentiSearch();
		search.setIdTipoCaricaDocumentoAmmessi(configSportello.getTipiSelezionati());
		search.setEnteId(enteDTO.getId()+"");
		search.setCodiceFiscaleEnte(enteDTO.getCodiceFiscale());
		search.setIdDoc(idProvvedimento);
		PaginatedList<VPaesaggioProvvedimentiDTO> provvedimenti = sportelloSvc.search(search);
		if(provvedimenti.getCount()>0) {
			this.allSvc.downloadFile(provvedimenti.getList().get(0).getCmisId(), response);
		}else {
			response.setStatus(HttpStatus.SC_NOT_FOUND);
		}
	}
	
}