package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller.istruttoria;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller.RoleAwareController;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Ruoli;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DisclaimerDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.IDisclaimerService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.Logging;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.configuration.swagger.OpenApiConfig;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * controller per funzionalità di superadmin per la configurazione dei
 * disclaimer per tipo procedimento
 * 
 * @author acolaianni
 *
 */
@RestController
@RequestMapping(value = "istruttoria/admin/disclaimer", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AdminDisclaimerController extends RoleAwareController {

	private static final Logger log = LoggerFactory.getLogger(AdminDisclaimerController.class);

	private static final String DISCLAIMER = "procedimenti/{id}/disclaimer";

	@Autowired
	IDisclaimerService disclSvc;

	@Operation(summary = "Dettaglio disclaimers procedimento", security = @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@GetMapping(value = DISCLAIMER)
	@Logging
	public ResponseEntity<BaseRestResponse<List<DisclaimerDTO>>> getDettaglioDisclaimersProcedimento(
			@PathVariable Integer id) throws Exception {
		checkAbilitazioneFor(Ruoli.ADMIN);
		disclSvc.currentDisclaimers(id);
		return super.okResponse(disclSvc.currentDisclaimers(id));
	}

	@Operation(summary = "Aggiorna disclaimers procedimento", security = @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@PostMapping(value = DISCLAIMER)
	@Logging
	public ResponseEntity<BaseRestResponse<List<DisclaimerDTO>>> updateDichiarazioneProcedimento(
			@PathVariable Integer id, @RequestBody List<DisclaimerDTO> disclaimers) throws Exception {
		checkAbilitazioneFor(Ruoli.ADMIN);
		String message = disclSvc.validate(disclaimers);
		if (StringUtil.isNotEmpty(message)) {
			throw new CustomOperationToAdviceException(message);
		}
		List<DisclaimerDTO> updatedList = disclSvc.updateDisclaimers(id, disclaimers);
		return super.okResponse(updatedList);
	}

}