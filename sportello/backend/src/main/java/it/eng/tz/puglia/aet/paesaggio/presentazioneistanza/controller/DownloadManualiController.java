package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.ApplicationProperties;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.DownloadManualeBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Ruoli;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.UserUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.repository.CommonRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.AllegatoService;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.util.log.LogUtil;
import it.eng.tz.puglia.util.string.StringUtil;

@RestController
@RequestMapping(value = "downloadManuale", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DownloadManualiController extends RoleAwareController {
	private final static String LABEL_MANUALE_ISTRUTTORIA_AMM_APP = "Download Manuale Amministratore Applicazione";
	private final static String LABEL_MANUALE_ISTRUTTORIA_AMM_ENTE = "Download Manuale Amministratore Ente";
	private final static String LABEL_MANUALE_ISTRUTTORIA_ENTE_DELEGATO = "Download Manuale Ente Delegato";
	private final static String LABEL_MANUALE_ISTRUTTORIA_SOPRINTENDENZA = "Download Manuale Soprintendenza";
	private final static String LABEL_MANUALE_ISTRUTTORIA_ENTE_TERRITORIALE = "Download Manuale Ente Territoriale";
	private final static String LABEL_MANUALE_ISTRUTTORIA_FUNZIONI_COMUNI = "Download Manuale Funzioni Comuni";
	private final static String MANUALE_ISTRUTTORIA_AMM_APP = "MANUALE-ISTRUTTORIA-AMM-APP-CMIS-ID";
	private final static String MANUALE_ISTRUTTORIA_AMM_ENTE = "MANUALE-ISTRUTTORIA-AMM-ENTE-CMIS-ID";
	private final static String MANUALE_ISTRUTTORIA_ENTE_DELEGATO = "MANUALE-ISTRUTTORIA-ENTE-DELEGATO-CMIS-ID";
	private final static String MANUALE_ISTRUTTORIA_SOPRINTENDENZA = "MANUALE-ISTRUTTORIA-SOPRINTENDENZA-CMIS-ID";
	private final static String MANUALE_ISTRUTTORIA_ENTE_TERRITORIALE = "MANUALE-ISTRUTTORIA-ENTE-TERRITORIALE-CMIS-ID";
	private final static String MANUALE_ISTRUTTORIA_FUNZIONI_COMUNI = "MANUALE-ISTRUTTORIA-FUNZIONI-COMUNI-CMIS-ID";

	@Autowired
	private AllegatoService allegatiService;
	@Autowired
	ApplicationProperties props;
	@Autowired
	CommonRepository commonDao;
	@Autowired
	UserUtil userUtil;

	@GetMapping(value = "/get-download")
	public ResponseEntity<BaseRestResponse<List<DownloadManualeBean>>> getDownloadUrls(
			final HttpServletResponse response) throws Exception {
		logger.info("Start getDownloadUrls");
		final StopWatch sw = LogUtil.startLog("getDownloadUrls");
		try {
			Gruppi gruppo = userUtil.getGruppo();
			final List<DownloadManualeBean> list = new ArrayList<DownloadManualeBean>();
			switch (gruppo.name()) {
			case "REG_":
			case "ED_":
				Ruoli ruolo = userUtil.getRuolo(userUtil.getCodice_GruppoIdRuolo());
				if (ruolo == Ruoli.DIRIGENTE || ruolo == Ruoli.AMMINISTRATORE) {
					final DownloadManualeBean adminEnte = new DownloadManualeBean(LABEL_MANUALE_ISTRUTTORIA_AMM_ENTE, MANUALE_ISTRUTTORIA_AMM_ENTE + ".pjson");
					list.add(adminEnte);
				}
				final DownloadManualeBean enteDelegato = new DownloadManualeBean(LABEL_MANUALE_ISTRUTTORIA_ENTE_DELEGATO, MANUALE_ISTRUTTORIA_ENTE_DELEGATO + ".pjson");
				list.add(enteDelegato);
				break;
			case "ETI_":
				final DownloadManualeBean adminEn = new DownloadManualeBean(LABEL_MANUALE_ISTRUTTORIA_AMM_ENTE, MANUALE_ISTRUTTORIA_AMM_ENTE + ".pjson");
				list.add(adminEn);
				final DownloadManualeBean enteTerritoriale = new DownloadManualeBean(LABEL_MANUALE_ISTRUTTORIA_ENTE_TERRITORIALE, MANUALE_ISTRUTTORIA_ENTE_TERRITORIALE + ".pjson");
				list.add(enteTerritoriale);
				break;
			case "SBAP_":
				final DownloadManualeBean adminEnte = new DownloadManualeBean(LABEL_MANUALE_ISTRUTTORIA_AMM_ENTE, MANUALE_ISTRUTTORIA_AMM_ENTE + ".pjson");
				list.add(adminEnte);
				final DownloadManualeBean soprintendenza = new DownloadManualeBean(LABEL_MANUALE_ISTRUTTORIA_SOPRINTENDENZA, MANUALE_ISTRUTTORIA_SOPRINTENDENZA + ".pjson");
				list.add(soprintendenza);
				break;
			case "ADMIN":
				final DownloadManualeBean adminApp = new DownloadManualeBean(LABEL_MANUALE_ISTRUTTORIA_AMM_APP, MANUALE_ISTRUTTORIA_AMM_APP + ".pjson");
				list.add(adminApp);
				final DownloadManualeBean adminE = new DownloadManualeBean(LABEL_MANUALE_ISTRUTTORIA_AMM_ENTE, MANUALE_ISTRUTTORIA_AMM_ENTE + ".pjson");
				list.add(adminE);
				final DownloadManualeBean enteDelgato = new DownloadManualeBean(LABEL_MANUALE_ISTRUTTORIA_ENTE_DELEGATO, MANUALE_ISTRUTTORIA_ENTE_DELEGATO + ".pjson");
				list.add(enteDelgato);
				final DownloadManualeBean enteT = new DownloadManualeBean(LABEL_MANUALE_ISTRUTTORIA_ENTE_TERRITORIALE, MANUALE_ISTRUTTORIA_ENTE_TERRITORIALE + ".pjson");
				list.add(enteT);
				final DownloadManualeBean sopIntendenza = new DownloadManualeBean(LABEL_MANUALE_ISTRUTTORIA_SOPRINTENDENZA, MANUALE_ISTRUTTORIA_SOPRINTENDENZA + ".pjson");
				list.add(sopIntendenza);
				break;
			}
			final DownloadManualeBean funzioniComuni = new DownloadManualeBean(LABEL_MANUALE_ISTRUTTORIA_FUNZIONI_COMUNI, MANUALE_ISTRUTTORIA_FUNZIONI_COMUNI + ".pjson");
			list.add(funzioniComuni);
			return super.okResponse(list);
		} catch (Exception e) {
			logger.error("Error in getDownloadUrls", e);
			return super.koResponse(e, new BaseRestResponse<>());
		} finally {
			logger.info(LogUtil.stopLog(sw));
		}
	}

	@GetMapping(value = MANUALE_ISTRUTTORIA_AMM_APP + ".pjson", produces = MEDIA_TYPE)
	public void downloadmanualeIstruttoriaAmministratoreApplicazione(final HttpServletResponse response)
			throws Exception {
		Gruppi gruppo = userUtil.getGruppo();
		if(gruppo != Gruppi.ADMIN) {
			throw new Exception("Non sei autorizzato a visualizzare questo manuale");
		}
		this.doDownloadFromCmisId(MANUALE_ISTRUTTORIA_AMM_APP, response);
	}

	@GetMapping(value = MANUALE_ISTRUTTORIA_AMM_ENTE + ".pjson", produces = MEDIA_TYPE)
	public void downloadmanualeIstruttoriaAmministratoreEnte(final HttpServletResponse response) throws Exception {
		Gruppi gruppo = userUtil.getGruppo();
		Ruoli ruolo = userUtil.getRuolo(userUtil.getCodice_GruppoIdRuolo());
		if(gruppo == Gruppi.REG_ || gruppo == Gruppi.ED_) {
			if(ruolo != Ruoli.DIRIGENTE && ruolo != Ruoli.AMMINISTRATORE) {
				response.setStatus(403);
				logger.error("Non sei autorizzato a visualizzare questo manuale");
			} else {
				this.doDownloadFromCmisId(MANUALE_ISTRUTTORIA_AMM_ENTE, response);
			}
		} else if(gruppo != Gruppi.SBAP_ && gruppo != Gruppi.ETI_ && gruppo != Gruppi.ADMIN) {
			response.setStatus(403);
			logger.error("Non sei autorizzato a visualizzare questo manuale");
		} else {
			this.doDownloadFromCmisId(MANUALE_ISTRUTTORIA_AMM_ENTE, response);
		}
	}

	@GetMapping(value = MANUALE_ISTRUTTORIA_ENTE_DELEGATO + ".pjson", produces = MEDIA_TYPE)
	public void downloadmanualeIstruttoriaEnteDelegato(final HttpServletResponse response) throws Exception {
		Gruppi gruppo = userUtil.getGruppo();
		if(gruppo != Gruppi.REG_ && gruppo != Gruppi.ED_ && gruppo != Gruppi.ADMIN) {
			response.setStatus(403);
			logger.error("Non sei autorizzato a visualizzare questo manuale");
		} else {
			this.doDownloadFromCmisId(MANUALE_ISTRUTTORIA_ENTE_DELEGATO, response);
		}
	}

	@GetMapping(value = MANUALE_ISTRUTTORIA_SOPRINTENDENZA + ".pjson", produces = MEDIA_TYPE)
	public void downloadmanualeIstruttoriaSoprintendenza(final HttpServletResponse response) throws Exception {
		Gruppi gruppo = userUtil.getGruppo();
		if(gruppo != Gruppi.SBAP_ && gruppo != Gruppi.ADMIN) {
			response.setStatus(403);
			logger.error("Non sei autorizzato a visualizzare questo manuale");
		} else {
			this.doDownloadFromCmisId(MANUALE_ISTRUTTORIA_SOPRINTENDENZA, response);
		}
	}

	@GetMapping(value =  MANUALE_ISTRUTTORIA_ENTE_TERRITORIALE + ".pjson", produces = MEDIA_TYPE)
	public void downloadmanualeIstruttoriaEnteTerritoriale(final HttpServletResponse response) throws Exception {
		Gruppi gruppo = userUtil.getGruppo();
		if(gruppo != Gruppi.ETI_ && gruppo != Gruppi.ADMIN) {
			response.setStatus(403);
			logger.error("Non sei autorizzato a visualizzare questo manuale");
		} else {
			this.doDownloadFromCmisId(MANUALE_ISTRUTTORIA_ENTE_TERRITORIALE, response);
		}
	}

	@GetMapping(value = MANUALE_ISTRUTTORIA_FUNZIONI_COMUNI + ".pjson", produces = MEDIA_TYPE)
	public void downloadmanualeIstruttoriaFunzioniComuni(final HttpServletResponse response) throws Exception {
		this.doDownloadFromCmisId(MANUALE_ISTRUTTORIA_FUNZIONI_COMUNI, response);
	}

	private void doDownloadFromCmisId(final String applicationKey, final HttpServletResponse response) {
		final StopWatch sw = LogUtil.startLog("doDownloadFromCmisId");
		try {
			final String configKey = applicationKey;
			final String idAlfresco = this.commonDao.getConfigurationValue(configKey,
					this.props.getCodiceApplicazione());
			if (!StringUtil.isBlank(idAlfresco)) {
				this.allegatiService.doDownloadFromCms(idAlfresco, response);
			} else {
				throw new Exception(configKey + " mancante in configurazione o vuoto");
			}
		} catch (final Exception e) {
			this.logger.error("Errore nel download del template  " + applicationKey, e);
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		} finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}
}
