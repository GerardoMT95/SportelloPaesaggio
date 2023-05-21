package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Ruoli;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.EnteResponseBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.PM_GruppiRuoli;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.ProfileUserResponseBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.servizi_esterni.protocollo.interceptor.ProtocolloLogInterceptor;
import it.eng.tz.puglia.bean.UserDetail;
import it.eng.tz.puglia.security.util.SecurityUtil;
import it.eng.tz.puglia.util.string.StringUtil;

@Component
public class UserUtilImpl implements UserUtil {
	
	private static final Logger log = LoggerFactory.getLogger(UserUtilImpl.class);
	
	

	@Override
	public List<EnteResponseBean> getMyEnti() {
		List<EnteResponseBean> entis = new ArrayList<>();
		this.getMyProfile().getEnti().entrySet().stream().forEach(entry -> {
					entry.getValue().stream().forEach(entis::add);
				});
		return entis;
	}
	
	@Override
	public boolean hasUserLogged() {
		return SecurityUtil.getUserDetail()!=null;
	}
	
	public ProfileUserResponseBean getMyProfile() {
		UserDetail userDetail = SecurityUtil.getUserDetail();
		ProfileUserResponseBean userBean = new ProfileUserResponseBean();
		userBean.setNome(userDetail.getFirstName());
		userBean.setCognome(userDetail.getLastName());
		userBean.setUsername(userDetail.getUsername());
		userBean.setEmail(userDetail.getEmailAddress());	
		List<EnteResponseBean> listaEnti = new ArrayList<EnteResponseBean>();
		// popolo gli enti tutti sotto la voce CO (comuni)
		Map<String, Object> otherField = userDetail.getOtherFields();
		List<?> gruppiRuoli = (List<?>) otherField.get("Gruppi_Ruoli");
		if(gruppiRuoli instanceof List<?>) {
		  for(Object gruppoRuolo : gruppiRuoli) {
			  if(gruppoRuolo instanceof PM_GruppiRuoli) {
				  PM_GruppiRuoli casted = (PM_GruppiRuoli) gruppoRuolo;
				  Object codiceEnte = casted.getCodiceGruppo();
				  if(codiceEnte!=null && (codiceEnte instanceof String)) {
					  EnteResponseBean ente = new EnteResponseBean();
					  ente.setName		   (		casted.getNomeGruppo());
					  ente.setCodiceGruppo (		casted.getCodiceGruppo());
					  ente.setId		   ((Long) 	casted.getAdditionalProperties().get("idOrganizzazione" ));
					  ente.setDenominazione((String)casted.getAdditionalProperties().get("denominazioneEnte"));
					  listaEnti.add(ente);
				  }
			  }
		  }
		}
		// TODO: a questo livello, o prima, magari nella "populateFields", bisognerebbe eliminare tutti i gruppi non più validi!
		Map<String, List<EnteResponseBean>> enti = new HashMap<>();
		enti.put("GRUPPI", listaEnti);
		userBean.setEnti(enti);
		return userBean;
	}
	
	@Override
	public String getCodice_GruppoIdRuolo() {
		String mockedGroup = MDC.get(UserUtil.MDC_GRUPPO_MOCKED);
		if(StringUtil.isNotEmpty(mockedGroup)) {
			return mockedGroup;
		}
		return (String) SecurityUtil.getUserDetail().getOtherFields().get("codiceGruppo");
	}
	
	@Override
	public String getNomeGruppo() {
		return (String) SecurityUtil.getUserDetail().getOtherFields().get("nomeGruppo");
	}
	
	@Override
	public Gruppi getGruppo() {
		return this.getGruppo(this.getCodice_GruppoIdRuolo());
	}

	@Override
	public Gruppi getGruppo(String gruppo_id_ruolo) {

		if (StringUtil.isEmpty(gruppo_id_ruolo)) return null;
		
			 if (gruppo_id_ruolo.toUpperCase().startsWith(Gruppi.REG_ 		.name())) return Gruppi.REG_;
		else if (gruppo_id_ruolo.toUpperCase().startsWith(Gruppi.ETI_ 		.name())) return Gruppi.ETI_;
		else if (gruppo_id_ruolo.toUpperCase().startsWith(Gruppi.SBAP_		.name())) return Gruppi.SBAP_;
		else if (gruppo_id_ruolo.toUpperCase().startsWith(Gruppi.ED_  		.name())) return Gruppi.ED_;
		else if (gruppo_id_ruolo.toUpperCase().startsWith(Gruppi.CL_  		.name())) return Gruppi.CL_;
		else if (gruppo_id_ruolo.toUpperCase().equals	 (Gruppi.RICHIEDENTI.name())) return Gruppi.RICHIEDENTI;
		else if (gruppo_id_ruolo.toUpperCase().equals	 (Gruppi.ADMIN		.name())) return Gruppi.ADMIN;
		else 								  					  	 				  return null;
	}

	@Override
	public String getGruppo_Id() {
		return this.getGruppo_Id(this.getCodice_GruppoIdRuolo());
	}

	@Override
	public String getGruppo_Id(String gruppo_id_ruolo) {
		
		if (StringUtil.isEmpty(gruppo_id_ruolo)) {
			return null;
		}
		else if(gruppo_id_ruolo.toUpperCase().equals(Gruppi.ADMIN.name())) {
			return Gruppi.ADMIN.name();
		}
		else {
			return (this.getGruppo(gruppo_id_ruolo).name()+this.getIntegerId(gruppo_id_ruolo));
		}
	}
	
	@Override
	public Ruoli getRuolo() {
		return this.getRuolo(this.getCodice_GruppoIdRuolo());
	}

	@Override
	public Ruoli getRuolo(String gruppo_id_ruolo) {
		
		if (StringUtil.isEmpty(gruppo_id_ruolo)) return null;
		
			 if (gruppo_id_ruolo.toUpperCase().endsWith		 ("_F"))	  	  		return Ruoli.FUNZIONARIO;
		else if (gruppo_id_ruolo.toUpperCase().endsWith		 ("_D"))	  	  		return Ruoli.DIRIGENTE;
		else if (gruppo_id_ruolo.toUpperCase().endsWith		 ("_A")) 	  	  		return Ruoli.AMMINISTRATORE;
		else if (gruppo_id_ruolo.toUpperCase().equals  (Gruppi.ADMIN	  .name())) return Ruoli.ADMIN;
		else if (gruppo_id_ruolo.toUpperCase().equals  (Gruppi.RICHIEDENTI.name())) return Ruoli.RICHIEDENTE;
		else 								  					  			  		return null;
	}

	@Override
	public long getLongId() {
		return this.getLongId(this.getCodice_GruppoIdRuolo());
	}

	@Override
	public int getIntegerId() {
		return this.getIntegerId(this.getCodice_GruppoIdRuolo());
	}

	@Override
	public int getIntegerId(String gruppo_id_ruolo) {
		return Integer.parseInt(gruppo_id_ruolo.replaceAll("\\D+",""));
	}

	@Override
	public long getLongId(String gruppo_id_ruolo) {
		return Long.parseLong(gruppo_id_ruolo.replaceAll("\\D+",""));
	}
	
}