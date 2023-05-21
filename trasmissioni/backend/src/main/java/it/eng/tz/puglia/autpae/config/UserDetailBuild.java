/**
 * 
 */
package it.eng.tz.puglia.autpae.config;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import it.eng.tz.puglia.autpae.dto.BE_to_FE.organizzazioni.PaesaggioOrganizzazioneBean;
import it.eng.tz.puglia.autpae.enumeratori.Gruppi;
import it.eng.tz.puglia.autpae.service.interfacce.PaesaggioOrganizzazioneService;
import it.eng.tz.puglia.cache.ICacheConstants;
import it.eng.tz.puglia.servizi_esterni.profileManager.dto.PM_GruppiRuoli;
import it.eng.tz.puglia.servizi_esterni.profileManager.feign.IProfileManager;
import it.eng.tz.puglia.servizi_esterni.profileManager.feign.UserUtil;
import it.eng.tz.puglia.servizi_esterni.remote.dto.EnteDTO;
import it.eng.tz.puglia.servizi_esterni.remote.service.interfacce.CommonService;
import it.eng.tz.puglia.util.log.LogUtil;

/**
 * @author Adriano Colaianni
 * @date 25 giu 2021
 */
@Component
public class UserDetailBuild {
	private static final Logger log = LoggerFactory.getLogger(UserDetailPopulate.class);

	@Autowired
	ApplicationProperties props;

	@Autowired
	private IProfileManager profileManager;
	@Autowired
	private UserUtil userUtil;
	@Autowired
	private CommonService commonSvc;
	@Autowired
	private PaesaggioOrganizzazioneService paesaggioSvc;
	
	
	/**
	 * a partire dal token contatta il profile manager preleva i gruppi e aggiunge altre info dal db common
	 * @autor Adriano Colaianni
	 * @date 25 giu 2021
	 * @param token
	 * @return
	 */
	@Cacheable(keyGenerator = ICacheConstants.KEY_CONTEXT_NAME, value = "buildGruppiRuoliFromTokenCached", unless = "#result == null")
	public List<PM_GruppiRuoli> buildGruppiRuoliFromTokenCached(String token) {
		StopWatch sw = LogUtil.startLog("buildGruppiRuoliFromTokenCached");
		List<PM_GruppiRuoli> gruppiRuoliFiltrati = new ArrayList<>();
		try {
			log.info("buildGruppiRuoliFromTokenCached calling profile manager...");
			List<PM_GruppiRuoli> gruppiRuoli = profileManager.getLoggedUser(token, props.getCodiceApplicazioneProfile())
					.getPayload().getGruppiRuoli();
			log.info("buildGruppiRuoliFromTokenCached called profile manager, gruppiRuoli: {}",gruppiRuoli);
			if (gruppiRuoli != null && gruppiRuoli.size() > 0) {
				List<String> lista = gruppiRuoli.stream().map(el -> el.getCodiceGruppo()).collect(Collectors.toList());
				// decoro ogni gruppo nella additionalProperties col valore 'denominazioneEnte'
				for (PM_GruppiRuoli gruppo : gruppiRuoli) {
					String codiceGruppo = gruppo.getCodiceGruppo();
					if (Gruppi.isValidSyntax(codiceGruppo, props.isPareri())) {
						try {
							if (!codiceGruppo.equals(Gruppi.ADMIN.name())) {
								long idOrganizzazione = userUtil.getIntegerId(codiceGruppo);
								PaesaggioOrganizzazioneBean enteRiferimento = paesaggioSvc
										.findPaesaggioOrganizzazioneAllTipi(idOrganizzazione);
								gruppo.getAdditionalProperties().put("denominazioneEnte",
										enteRiferimento.getDenominazione());
								gruppo.getAdditionalProperties().put("codiceCivilia",
										enteRiferimento.getCodiceCivilia());
								if (enteRiferimento.getEnteId() != null) {
									EnteDTO enteCommon = commonSvc.findEnteById(enteRiferimento.getEnteId());
									gruppo.getAdditionalProperties().put("tipologiaEnte",
											enteCommon.getTipo().getCodice());
								}
							}
							gruppiRuoliFiltrati.add(gruppo);
						} catch (Exception e) {
							log.error(
									"Errore nel retrieval dell'id o della denominazione dell'Organizzazione paesaggio con codiceGruppo="
											+ codiceGruppo,
									e);
							// throw new Exception("Errore nel retrieval dell'id o della denominazionde
							// dell'Organizzazione paesaggio con codiceGruppo="+codiceGruppo/*,e*/);
						}
					} else {
						log.error("Gruppo con sintassi del codice inattesa, verrà scartato " + codiceGruppo/* ,e */);
					}
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			log.info(LogUtil.stopLog(sw));
		}
		return gruppiRuoliFiltrati;
	}
	
	
	

}
