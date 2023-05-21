package it.eng.tz.puglia.autpae.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.eng.tz.puglia.autpae.config.VerifyGroupFilter;
import it.eng.tz.puglia.autpae.controller.exception.CustomOperationToAdviceWarningException;
import it.eng.tz.puglia.autpae.dto.TipologicaDTO;
import it.eng.tz.puglia.autpae.dto.BE_to_FE.GruppiRuoliDTO;
import it.eng.tz.puglia.autpae.dto.BE_to_FE.PlainTypeStringIdDTO;
import it.eng.tz.puglia.autpae.service.interfacce.GruppiRuoliService;
import it.eng.tz.puglia.autpae.utility.LoggingAet;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.servizi_esterni.profileManager.feign.UserUtilImpl;
import it.eng.tz.puglia.util.log.LogUtil;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class GruppiRuoliController extends _RestController {
	
	private static final Logger log = LoggerFactory.getLogger(GruppiRuoliController.class);

	@Autowired private UserUtilImpl userUtilImpl;
	@Autowired private GruppiRuoliService gruppiRuoliService;


	/**
	 * lista piatta codice gruppo - descrizione 
	 * @autor Adriano Colaianni
	 * @date 26 mag 2022
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "autorita-procedurale")	
	@LoggingAet
	public ResponseEntity<BaseRestResponse<Set<PlainTypeStringIdDTO>>> proceduralAuthority() throws Exception {
		final Set<PlainTypeStringIdDTO> ret = this.userUtilImpl.getMyEnti().stream().map(ente -> {
			final PlainTypeStringIdDTO dto = new PlainTypeStringIdDTO();
			dto.setId(ente.getCodiceGruppo());
			dto.setNome(ente.getDenominazione());
			return dto;
		}).collect(Collectors.toSet());
		return super.okResponse(ret);
	}

	/**
	 * lista delle organizzazioni a cui appartengo e i relativi ruoli 
	 * @autor Adriano Colaianni
	 * @date 26 mag 2022
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "autorita-procedurale-nuovo")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<List<GruppiRuoliDTO>>> proceduralAuthorityNuovo() throws Exception {
		return super.okResponse(GruppiRuoliDTO.creaDaTipologicaDTO(this.getProceduralAuthority()));
	}

	@LoggingAet
	public Set<TipologicaDTO> getProceduralAuthority() throws Exception {
		return this.userUtilImpl.getMyEnti().stream().map(ente -> {
			final TipologicaDTO dto = new TipologicaDTO();
			dto.setCodice(ente.getCodiceGruppo());
			dto.setLabel(ente.getDenominazione());
			return dto;
		}).collect(Collectors.toSet());
	}

	@GetMapping(value = "checkGruppo")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<String>> checkGruppo(@RequestParam(name="codiceGruppo", required=true) final String codice_GruppoIdRuolo) throws Exception	{
		try
		{
			this.gruppiRuoliService.checkGruppo(codice_GruppoIdRuolo);
			return super.okResponse("OK");
		}
		catch(final Exception e)
		{
			this.logger.error("Gruppo non valido, restituisco un messaggio da mostrare come warning");
			throw new CustomOperationToAdviceWarningException(e.getMessage() + ".\nVerrà consentito l'accesso ma non sarà possibile creare o trasmettere pratiche.");
		}
	}

	/**
	 * restituisce tutti i gruppi relativi alle organizzazioni attive al momento per poter richiedere eventuale 
	 * abilitazione.
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "gruppiOrganizzazioni")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<List<PlainTypeStringIdDTO>>> getGruppiOrganizzazioni() throws Exception {
		return super.okResponse(this.gruppiRuoliService.getGruppiOrganizzazioni());
	}
	
	@GetMapping(value = "/multi-comune")
	@LoggingAet
	public ResponseEntity<BaseRestResponse<Boolean>> isMultiComune(@RequestHeader(name = VerifyGroupFilter.GROUP_KEY) final String gruppo) {
		final StopWatch sw = LogUtil.startLog("isMultiComune");
		try {
			this.logger.info("gruppo {}", gruppo);
			final String actualGroup = new String(Base64.decodeBase64(gruppo.getBytes()));
			return super.okResponse(this.gruppiRuoliService.isMultiComune(actualGroup));
		}catch(final Exception e) {
			this.logger.error("Error in isMultiComune", e);
			return super.koResponse(e, new BaseRestResponse<>());
		}finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}

}