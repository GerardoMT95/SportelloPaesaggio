package it.eng.tz.puglia.servizi_esterni.profileManager.feign;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import it.eng.tz.puglia.autpae.enumeratori.Gruppi;
import it.eng.tz.puglia.autpae.enumeratori.Ruoli;
import it.eng.tz.puglia.bean.UserDetail;
import it.eng.tz.puglia.security.util.SecurityUtil;
import it.eng.tz.puglia.servizi_esterni.profileManager.dto.PM_GruppiRuoli;
import it.eng.tz.puglia.servizi_esterni.profileManager.dto.response.EnteResponseBean;
import it.eng.tz.puglia.servizi_esterni.profileManager.dto.response.ProfileUserResponseBean;
import it.eng.tz.puglia.util.string.StringUtil;

@Component
public class UserUtilImpl implements UserUtil{
	
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
	
	@Override
	public ProfileUserResponseBean getMyProfile() {
		UserDetail userDetail=SecurityUtil.getUserDetail();
		ProfileUserResponseBean userBean=new ProfileUserResponseBean();
		userBean.setNome(userDetail.getFirstName());
		userBean.setCognome(userDetail.getLastName());
		userBean.setUsername(userDetail.getUsername());
		userBean.setEmail(userDetail.getEmailAddress());
		Map<String,List<EnteResponseBean>> enti = userBean.getEnti();	// sarebbero i Gruppi delle Organizzazioni
		List<EnteResponseBean> listaEnti = new ArrayList<EnteResponseBean>();
		Map<String,Object> otherField=userDetail.getOtherFields();
		List<?> gruppiRuoli=(List<?>)otherField.get("GruppoRuoli");
		if(gruppiRuoli instanceof List<?>) {
		  for(Object gruppoRuolo:gruppiRuoli) {
			  if(gruppoRuolo instanceof PM_GruppiRuoli) {
				  PM_GruppiRuoli casted=(PM_GruppiRuoli)gruppoRuolo;
				  Object codiceEnte = casted.getCodiceGruppo();
				  if(codiceEnte!=null && (codiceEnte instanceof String)) {
					  EnteResponseBean ente = new EnteResponseBean();
					  ente.setName(casted.getNomeGruppo());
					  ente.setCodiceGruppo(casted.getCodiceGruppo());
					  try {
						  if (!casted.getCodiceGruppo().equals(Gruppi.ADMIN.name())) {
							  long idPaesaggioOrganizzazione = this.getLongId(casted.getCodiceGruppo());
							  ente.setId(idPaesaggioOrganizzazione);
							  ente.setDenominazione((String)casted.getAdditionalProperties().get("denominazioneEnte"));
						  }
						  else {
							  ente.setDenominazione("AMMINISTRATORE");
						  }
					  } catch(NumberFormatException ne) {
						  log.error("Errore nel recupero dell'id della tabella 'paesaggio_organizzazione' per il gruppo di appartenenza "+casted.getCodiceGruppo()+" dell'utente loggato");
					  }
					  listaEnti.add(ente);
				  }
			  }
		  }	
		}
		enti.put("GRUPPI", listaEnti);
		return userBean;
	}
	
	@Override
	public String getCodice_GruppoIdRuolo() {
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
	public Gruppi getGruppo(final String gruppo_id_ruolo) {
		return Gruppi.fromCodiceGruppoPM(gruppo_id_ruolo);
	}

	@Override
	public String getGruppo_Id() {
		return this.getGruppo_Id(this.getCodice_GruppoIdRuolo());
	}

	@Override
	public String getGruppo_Id(final String gruppo_id_ruolo) {
		
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
	public Ruoli getRuolo(final String gruppo_id_ruolo) {
		
		if (StringUtil.isEmpty(gruppo_id_ruolo)) return null;
		
			 if (gruppo_id_ruolo.toUpperCase().endsWith		 ("_F"))	  	  return Ruoli.FUNZIONARIO;
		else if (gruppo_id_ruolo.toUpperCase().endsWith		 ("_D"))	  	  return Ruoli.DIRIGENTE;
		else if (gruppo_id_ruolo.toUpperCase().endsWith		 ("_A")) 	  	  return Ruoli.AMMINISTRATORE;
		else if (gruppo_id_ruolo.toUpperCase().equals  (Gruppi.ADMIN.name())) return Ruoli.ADMIN;
		else 								  					  			  return null;
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
	public int getIntegerId(final String gruppo_id_ruolo) {
		return Integer.parseInt(gruppo_id_ruolo.replaceAll("\\D+",""));
	}

	@Override
	public long getLongId(final String gruppo_id_ruolo) {
		return Long.parseLong(gruppo_id_ruolo.replaceAll("\\D+",""));
	}
	
}