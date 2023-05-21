package it.eng.tz.puglia.autpae.config;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import it.eng.tz.puglia.bean.UserDetail;
import it.eng.tz.puglia.security.ICompleteUserService;
import it.eng.tz.puglia.servizi_esterni.profileManager.dto.PM_GruppiRuoli;
import it.eng.tz.puglia.util.log.LogUtil;

@Component
public class UserDetailPopulate implements ICompleteUserService {

	private static final Logger log = LoggerFactory.getLogger(UserDetailPopulate.class);

	@Autowired
	ApplicationProperties props;

	@Autowired
	private UserDetailBuild userDetailBuild;
		
	@Override
	public void populateFields(UserDetail userDetail, List<SimpleGrantedAuthority> authorities) {
		// prendo gruppi e ruoli dell'utente loggato dal profile manager
		try {
			String token = LogUtil.getAuthorization();
			if (token == null || token.isEmpty())
				return;
			log.info("Calling populate fields ");
			List<PM_GruppiRuoli> gruppiRuoliFiltrati = userDetailBuild.buildGruppiRuoliFromTokenCached(token);
			userDetail.getOtherFields().put("GruppoRuoli", gruppiRuoliFiltrati);
			
		} catch (Exception e) {
			log.info("Errore nel retrieval delle info utente dal profile manager " + e.getMessage(), e);
		}
	}

	

}