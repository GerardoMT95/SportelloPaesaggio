package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.FascicoloDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.SchedaTecnicaDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.FascicoloService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.SchedaTecnicaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.Logging;
import it.eng.tz.puglia.bean.BaseRestResponse;

@Controller
@RequestMapping("schedaTecnica")
public class SchedaTecnicaController extends RoleAwareController {
	private final Logger logger = LoggerFactory.getLogger(SchedaTecnicaController.class);
	private final static String SAVE = "/save";
	private final static String FIND = "/find";
	private final static String VALIDA = "/valida";

	@Autowired
	private SchedaTecnicaService service;
	@Autowired
	private FascicoloService praticaService;

	@Logging
	@RequestMapping(path = SAVE, method = { RequestMethod.POST,
			RequestMethod.PUT }, consumes = MEDIA_TYPE, produces = MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<SchedaTecnicaDto>> saveSchedaTecnica(@RequestBody SchedaTecnicaDto entity)
			throws Exception {
		try {
			checkDiMiaCompetenza(entity.getIdPratica());
			SchedaTecnicaDto dto = service.saveOrUpdate(entity);
			updateFlagValidazione(entity);
			return super.okResponse(dto);
		} catch (Exception e) {
			logger.error("Errore nel salvataggio della scheda tecnica ", e.getMessage(), e);
			return super.koResponse(e, new BaseRestResponse<>());
		}
	}

	/**
	 * ricalcolo il flag di validazione di istanza e scheda tecnica visto che dipendono uno dall'altra...
	 * @param entity
	 * @throws Exception
	 * @throws CustomOperationToAdviceException
	 */
	private void updateFlagValidazione(SchedaTecnicaDto entity) throws Exception, CustomOperationToAdviceException {
		FascicoloDto pratica = praticaService.findById(entity.getIdPratica());
		try {
			if(pratica.getValidatoIstnza()!=null && pratica.getValidatoIstnza()) {
				//se era valido, lo ricalcolo....
				Boolean validaIstanza = praticaService.validaIstanza(pratica.getCodicePraticaAppptr());
				praticaService.updateValidation(entity.getIdPratica(), validaIstanza, null, null);
			}
		} catch (Exception e) {
			praticaService.updateValidation(entity.getIdPratica(), false, null, null);
		}
		try {
			if(pratica.getValidatoSchedaTecnica()!=null && pratica.getValidatoSchedaTecnica()) {
				//se era valido, lo ricalcolo....
				Boolean validaSchedaTecnica = service.valida(entity.getIdPratica());
				praticaService.updateValidation(entity.getIdPratica(), null, validaSchedaTecnica, null);
			}
		} catch (Exception e) {
			praticaService.updateValidation(entity.getIdPratica(), null, false, null);
		}
	}

	@Logging
	@GetMapping(FIND)
	public ResponseEntity<BaseRestResponse<SchedaTecnicaDto>> findSchedaTecnica(
			@RequestParam("idPratica") UUID idPratica) throws Exception {
		try {
			checkDiMiaCompetenza(idPratica);
			return super.okResponse(service.find(idPratica));
		} catch (Exception e) {
			logger.error("Errore nella lettura della scheda tecnica ", e.getMessage(), e);
			return super.koResponse(e, new BaseRestResponse<>());
		}
	}

	@Logging
	@GetMapping(VALIDA)
	public ResponseEntity<BaseRestResponse<Boolean>> validaSchedaTecnica(@RequestParam("idPratica") UUID idPratica)
			throws Exception {
		try {
			checkDiMiaCompetenza(idPratica);
			Boolean valido = service.valida(idPratica);
			ResponseEntity<BaseRestResponse<Boolean>> response = super.okResponse(valido);
			praticaService.updateValidation(idPratica, null, valido, null);
			return response;
		} catch (CustomOperationToAdviceException e) {
			throw e;
		} catch (Exception e) {
			logger.error("Errore nella validazione della scheda tecnica ", e.getMessage(), e);
			return super.koResponse(e, new BaseRestResponse<>());
		}
	}

}