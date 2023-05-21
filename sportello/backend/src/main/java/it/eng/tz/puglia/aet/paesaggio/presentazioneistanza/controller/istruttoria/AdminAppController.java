package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller.istruttoria;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import it.eng.tz.puglia.aet.diogene.bean.DiogeneApplicationConfigBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller.RoleAwareController;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.CdsGetConfigurazioneDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.CdsSaveConfigurazioneDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Ruoli;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DichiarazioneDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TariffaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TipoProcedimentoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.IConferenzaServiziAdminService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.ITariffaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.impl.TipoProcedimentoService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.FascicoloService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.schedaTecnica.DichiarazioneService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.Logging;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.configuration.swagger.OpenApiConfig;
import it.eng.tz.puglia.service.http.IDiogeneClientConfigService;
import it.eng.tz.puglia.util.log.LogUtil;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * controller per funzionalità di superadmin o admin applicazione
 * 
 * @author acolaianni
 *
 */
@RestController
@RequestMapping(value = "istruttoria/admin", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AdminAppController extends RoleAwareController {

	private static final Logger log = LoggerFactory.getLogger(AdminAppController.class);

	private static final String CONFIGURATION_DIOGENE = "diogeneConfiguration";
	private static final String PROCEDIMENTI = "procedimenti";
	private static final String DICHIARAZIONE = "procedimenti/{id}/dichiarazione";
	private static final String TARIFFE = "procedimenti/{id}/tariffe";
	private static final String SAVE_TARIFFE = "procedimenti/save-tariffe";
	private static final String ALLINEA_FASCICOLO_DIOGENE = "allineaFascicoloDiogene/{id}";
	private static final String GET_CONFERENZA_SERVIZI = "procedimenti/{id}/get-conferenza-servizi";
	private static final String SAVE_CONFERENZA_SERVIZI = "procedimenti/{id}/save-conferenza-servizi";


	@Value("${spring.application.name}")
	private String codiceApplicazione;

	@Autowired
	private IDiogeneClientConfigService diogeneClientService;
	@Autowired
	private TipoProcedimentoService tipoProcSvc;
	@Autowired
	private DichiarazioneService dichSvc;
	@Autowired
	private ITariffaService tariffaSvc;
	@Autowired
	private FascicoloService fascicoloSvc;
	@Autowired
	private IConferenzaServiziAdminService cdsService;

	@GetMapping(value = CONFIGURATION_DIOGENE)
	@Logging
	public ResponseEntity<BaseRestResponse<DiogeneApplicationConfigBean>> getConfigurazioneDiogene() throws Exception {
		checkAbilitazioneFor(Ruoli.ADMIN);
		final DiogeneApplicationConfigBean ret = this.diogeneClientService
				.getConfigDiogene(this.codiceApplicazione.toUpperCase(), true);
		return super.okResponse(ret);
	}

	@PostMapping(value = CONFIGURATION_DIOGENE)
	@Logging
	public ResponseEntity<BaseRestResponse<Integer>> upsertConfigurazioneDiogene(
			@RequestBody final DiogeneApplicationConfigBean request) throws Exception {
		checkAbilitazioneFor(Ruoli.ADMIN);
		request.setCodiceApplicazione(this.codiceApplicazione.toUpperCase());
		final String errorMessage = this.diogeneClientService.validaConfigurazione(request);
		if (!StringUtil.isEmpty(errorMessage)) {
			return super.koResponse(new CustomOperationToAdviceException(errorMessage), new BaseRestResponse<>());
		}
		final Integer ret = this.diogeneClientService.upsertConfigDiogene(request);
		return super.okResponse(ret);
	}

	/**
	 * lista dei tipi procedimento
	 * 
	 * @author acolaianni
	 *
	 * @return
	 * @throws Exception
	 */
	@Operation(summary = "Elenco procedimenti", security = @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@GetMapping(value = PROCEDIMENTI)
	@Logging
	public ResponseEntity<BaseRestResponse<List<TipoProcedimentoDTO>>> listProcedimenti() throws Exception {
		// checkAbilitazioneFor(Ruoli.ADMIN);
		List<TipoProcedimentoDTO> ret = tipoProcSvc.select();
		return super.okResponse(ret);
	}

	@Operation(summary = "Aggiorna procedimento", security = @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@PutMapping(value = PROCEDIMENTI + "/{id}")
	@Logging
	public ResponseEntity<BaseRestResponse<Integer>> updateProcedimento(@PathVariable Integer id,
			@RequestBody TipoProcedimentoDTO updateBean) throws Exception {
		checkAbilitazioneFor(Ruoli.ADMIN);
		TipoProcedimentoDTO pk = new TipoProcedimentoDTO();
		pk.setId(id);
		pk = tipoProcSvc.find(pk);
		pk.setSanatoria(updateBean.getSanatoria());
		pk.setAbilitatoPresentazione(updateBean.getAbilitatoPresentazione());
		int ret = tipoProcSvc.update(pk);
		return super.okResponse(ret);
	}

	@Operation(summary = "Dettaglio procedimento", security = @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@GetMapping(value = PROCEDIMENTI + "/{id}")
	@Logging
	public ResponseEntity<BaseRestResponse<TipoProcedimentoDTO>> getDettaglioProcedimento(@PathVariable Integer id)
			throws Exception {
		checkAbilitazioneFor(Ruoli.ADMIN);
		TipoProcedimentoDTO pk = new TipoProcedimentoDTO();
		pk.setId(id);
		pk = tipoProcSvc.find(pk);
		return super.okResponse(pk);
	}

	@Operation(summary = "Dettaglio dichiarazione procedimento", security = @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@GetMapping(value = DICHIARAZIONE)
	@Logging
	public ResponseEntity<BaseRestResponse<DichiarazioneDTO>> getDettaglioDichiarazioneProcedimento(
			@PathVariable Integer id) throws Exception {
		checkAbilitazioneFor(Ruoli.ADMIN);
		DichiarazioneDTO dichDto = dichSvc.findByTipoProcedimentoAndData(id, new Date());
		return super.okResponse(dichDto);
	}

	@Operation(summary = "Aggiorna dichiarazione procedimento", security = @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@PutMapping(value = DICHIARAZIONE)
	@Logging
	public ResponseEntity<BaseRestResponse<Integer>> updateDichiarazioneProcedimento(@PathVariable Integer id,
			@RequestBody DichiarazioneDTO updateBean) throws Exception {
		checkAbilitazioneFor(Ruoli.ADMIN);
		dichSvc.valida(id, updateBean.getTesto(), updateBean.getLabelCb());
		int updated = dichSvc.upsertDichiarazione(id, updateBean.getTesto(), updateBean.getLabelCb());
		return super.okResponse(updated);
	}
	
	@Operation(summary = "Recupero configurazione tariffe", security = @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@GetMapping(value = TARIFFE)
	public ResponseEntity<BaseRestResponse<List<TariffaDTO>>> getConfigTariffe(@PathVariable final String id) throws Exception{
		final StopWatch sw = LogUtil.startLog("getConfigTariffe");
		try {
			checkAbilitazioneFor(Ruoli.AMMINISTRATORE);
			return super.okResponse(this.tariffaSvc.getTariffeByIdProcedimento(id));
		}catch(final Exception e) {
			this.logger.error("Error in getConfigTariffe", e);
			return super.koResponse(e, new BaseRestResponse<>());
		}finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
	
	@Operation(summary = "Salvataggio configurazione tariffe", security = @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@PostMapping(value = SAVE_TARIFFE)
	public ResponseEntity<BaseRestResponse<List<TariffaDTO>>> saveConfigTariffe(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Request payload", required = true)
			@RequestBody final List<TariffaDTO> configurazione
	) throws Exception{
		final StopWatch sw = LogUtil.startLog("saveConfigTariffe");
		try {
			checkAbilitazioneFor(Ruoli.AMMINISTRATORE);
			this.tariffaSvc.saveConfigurazioneTariffe(configurazione);
			final String idTipoProcedimento=String.valueOf(configurazione.get(0).getIdTipoProcedimento());
			return super.okResponse(this.tariffaSvc.getTariffeByIdProcedimento(idTipoProcedimento));
		}catch(final Exception e) {
			this.logger.error("Error in saveConfigTariffe", e);
			return super.koResponse(e, new BaseRestResponse<>());
		}finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
	
	@Operation(summary = "Recupero configurazione conferenza servizi", security = @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@GetMapping(value = GET_CONFERENZA_SERVIZI)
	public ResponseEntity<BaseRestResponse<CdsGetConfigurazioneDto>> getConferenzaServizi(@PathVariable("id") final String idTipoProcedimento) throws Exception{
		final StopWatch sw = LogUtil.startLog("getConferenzaServizi");
		try {
			checkAbilitazioneFor(Ruoli.AMMINISTRATORE);
			return this.okResponse(this.cdsService.getConfigurazione(idTipoProcedimento));
		}catch(final Exception e) {
			this.logger.error("Error in getConferenzaServizi", e);
			return super.koResponse(e, new BaseRestResponse<>());
		}finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
	

	/**
	 * allineamento diogene su singola pratica senza alcun controllo
	 * @author acolaianni
	 *
	 * @param idPratica
	 * @return
	 * @throws Exception
	 */
	@Operation(summary = "Allineamento incondizionato fasciolo su diogene by id pratica", security = @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@PostMapping(value = ALLINEA_FASCICOLO_DIOGENE)
	public ResponseEntity<BaseRestResponse<Boolean>> allineaDiogene(@PathVariable final UUID id) throws Exception{
		final StopWatch sw = LogUtil.startLog("allineaDiogene");
		try {
			checkAbilitazioneFor(Ruoli.ADMIN);
			this.fascicoloSvc.allineaDiogeneAllSezioni(id);
			return super.okResponse(true);
		}catch(final Exception e) {
			this.logger.error("Error in allineaDiogene", e);
			return super.koResponse(e, new BaseRestResponse<>());
		}finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}

	@Operation(summary = "Salvataggio configurazione conferenza servizi", security = @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	@PostMapping(value = SAVE_CONFERENZA_SERVIZI)
	public ResponseEntity<BaseRestResponse<CdsGetConfigurazioneDto>> saveConferenzaServizi(@PathVariable("id") final String idTipoProcedimento,
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Request payload", required = true)
			@RequestBody final CdsSaveConfigurazioneDto configurazione
	) throws Exception{
		final StopWatch sw = LogUtil.startLog("saveConferenzaServizi");
		try {
			checkAbilitazioneFor(Ruoli.AMMINISTRATORE);
			this.cdsService.saveConfigurazione(idTipoProcedimento, configurazione);
  		return this.okResponse(this.cdsService.getConfigurazione(idTipoProcedimento));
		}catch(final Exception e) {
			this.logger.error("Error in saveConferenzaServizi", e);
			return super.koResponse(e, new BaseRestResponse<>());
		}finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}


}