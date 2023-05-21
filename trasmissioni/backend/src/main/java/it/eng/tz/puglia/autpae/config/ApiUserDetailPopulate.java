/**
 * 
 */
package it.eng.tz.puglia.autpae.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.autpae.enumeratori.Gruppi;
import it.eng.tz.puglia.bean.UserDetail;
import it.eng.tz.puglia.security.util.SecurityUtil;
import it.eng.tz.puglia.service.apimanager.IApiManagerCompleteService;
import it.eng.tz.puglia.service.apimanager.impl.ApiManagerAccessTokenConverter;
import it.eng.tz.puglia.servizi_esterni.profileManager.dto.PM_GruppiRuoli;
import it.eng.tz.puglia.servizi_esterni.profileManager.feign.IProfileManager;
import it.eng.tz.puglia.servizi_esterni.profileManager.feign.UserUtil;
import it.eng.tz.puglia.servizi_esterni.remote.service.interfacce.CommonService;
import it.eng.tz.puglia.util.log.LogUtil;

/**
 * simile alla UserDetailPopulate ma per utenza API
 * @see UserDetailPopulate
 * @author Adriano Colaianni
 * @date 6 mag 2021
 */

@Component
public class ApiUserDetailPopulate implements IApiManagerCompleteService {

	
	private static final Logger log = LoggerFactory.getLogger(ApiUserDetailPopulate.class);

	@Autowired
	ApplicationProperties props;

	@Autowired
	private UserUtil userUtil;
	@Autowired
	private CommonService commonSvc;

	
	@Override
	public void completeUser(UserDetail userDetail, Map<String, ?> map, List<SimpleGrantedAuthority> authorities) {
		// prendo gruppi e ruoli dell'utente loggato dal profile manager
		try {
			 if(!userDetail.isApiUser())
				 return;
			log.info("Calling populate fields ");
			List<PM_GruppiRuoli> gruppiRuoli=new ArrayList<>();
			log.info("Calling populate fields from ApiAuthentication ");
				//i gruppi di appartenenza li trovo direttamente nella userDetail.getRole
				List<String> ruoliWso2Am = userDetail.getRoles();
				log.info("ruoli wso2  ",ruoliWso2Am);
				for(String ruoloWso2Am:ruoliWso2Am) {
					PM_GruppiRuoli gruppoSimulato=new PM_GruppiRuoli();
					gruppoSimulato.setCodiceGruppo(ruoloWso2Am);
					gruppiRuoli.add(gruppoSimulato);
				}
			List<PM_GruppiRuoli> gruppiRuoliFiltrati = new ArrayList<>();
			if (gruppiRuoli != null && gruppiRuoli.size() > 0) {
				// decoro ogni gruppo nella additionalProperties col valore 'denominazioneEnte'
				for (PM_GruppiRuoli gruppo : gruppiRuoli) {
					String codiceGruppo = gruppo.getCodiceGruppo();
					if (Gruppi.isValidSyntax(codiceGruppo,props.isPareri())) {
						try {
							if (!codiceGruppo.equals(Gruppi.ADMIN.name())) {
								int idOrganizzazione = userUtil.getIntegerId(codiceGruppo);
								String denominazioneEnte=commonSvc.getDenominazioneOrganizzazione(idOrganizzazione);
								gruppo.getAdditionalProperties().put("denominazioneEnte",	denominazioneEnte);
								gruppo.setDescrizioneGruppo(denominazioneEnte);
							}
							gruppiRuoliFiltrati.add(gruppo);
						} catch (Exception e) {
							log.error(
									"Errore nel retrieval dell'id o della denominazione dell'Organizzazione paesaggio con codiceGruppo="
											+ codiceGruppo/* ,e */);
							// throw new Exception("Errore nel retrieval dell'id o della denominazionde
							// dell'Organizzazione paesaggio con codiceGruppo="+codiceGruppo/*,e*/);
						}
					}
				}
			}
			userDetail.getOtherFields().put("GruppoRuoli", gruppiRuoliFiltrati);
			log.info("ruoli filtrati ",gruppiRuoliFiltrati);
			if(ListUtil.isNotEmpty(gruppiRuoliFiltrati)) {
				//prendo il primo gruppo e lo setto come default...
				userDetail.getOtherFields().put("codiceGruppo",gruppiRuoliFiltrati.get(0).getCodiceGruppo());
			}
		} catch (Exception e) {
			log.info("Errore nel retrieval delle info utente dal profile manager " + e.getMessage(), e);
		}
	}
	
	
	

}
