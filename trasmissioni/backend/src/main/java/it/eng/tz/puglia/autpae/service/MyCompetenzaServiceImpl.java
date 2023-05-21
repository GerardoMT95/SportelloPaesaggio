package it.eng.tz.puglia.autpae.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.aet.paesaggio.bean.MyOrganizzazioniBean;
import it.eng.tz.puglia.autpae.config.ApplicationProperties;
import it.eng.tz.puglia.autpae.search.FascicoloSearch;
import it.eng.tz.puglia.autpae.service.interfacce.ClientSportelloService;
import it.eng.tz.puglia.autpae.service.interfacce.FascicoloService;
import it.eng.tz.puglia.autpae.service.interfacce.MyCompetenzaService;
import it.eng.tz.puglia.security.util.SecurityUtil;

@Service
public class MyCompetenzaServiceImpl implements MyCompetenzaService {

	private static final Logger log = LoggerFactory.getLogger(MyCompetenzaServiceImpl.class);
	
	@Autowired
	ClientSportelloService udBuild; 
	@Autowired
	FascicoloService fascicoloSvc;
	@Autowired
	ApplicationProperties props;

	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS, timeout=60000, readOnly=true, rollbackFor=Exception.class)
	public Map<String,MyOrganizzazioniBean>  getMyOrganizzazioniPaesaggio() {
		String token = SecurityUtil.getAuthorizationHeader();
		MyOrganizzazioniBean myOrgSportello=null;
		if(props.isAutPae()) {
			try {
				myOrgSportello = udBuild.buildOrganizzazioniSportelloFromTokenCached(token);
			} catch (Exception e) {
				log.error("Errore durante il retrieve delle organizzazioni su sportello!",e);
			}	
		}
		MyOrganizzazioniBean myOrgAutpae=null;
		try {
			myOrgAutpae = fascicoloSvc.organizzazioniEComunidiCompetenza();
		} catch (Exception e) {
			log.error("Errore durante il retrieve delle organizzazioni su autpae!",e);
		}
		Map<String,MyOrganizzazioniBean> mapOut=new HashMap<>();
		mapOut.put(MyCompetenzaService.PAE_PRES_IST,myOrgSportello);
		mapOut.put(props.getCodiceApplicazione(),myOrgAutpae);
		return mapOut;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, timeout=60000, readOnly=true, rollbackFor=Exception.class)
	public String groupCanAccessPratica(String codiceTrasmissione, List<String> myGruppi, String username) throws Exception {
		String gruppoGranted=null;
		for(String gruppo:myGruppi) {
			boolean canAccess = this.userInGroupCanAccessDetail(codiceTrasmissione, gruppo, username);
			if(canAccess) {
				gruppoGranted=gruppo;
				break;
			}
		}
		return gruppoGranted;
	}
	
	
	private boolean userInGroupCanAccessDetail(String codiceTrasmissione, String codiceGruppo, String username) throws Exception {
		FascicoloSearch filter=new FascicoloSearch();
		filter.setRicercaPubblica(false);
		filter.setCodiceTrasmissione(codiceTrasmissione);
		filter.setUsernameLoggato(username);
		fascicoloSvc.prepareForSearch(filter, codiceGruppo);
		long ret = fascicoloSvc.count(filter);
		return ret>0;
	}
}