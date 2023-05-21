package it.eng.tz.puglia.servizi_esterni.profileManager.feign;

import java.util.List;

import it.eng.tz.puglia.autpae.enumeratori.Gruppi;
import it.eng.tz.puglia.autpae.enumeratori.Ruoli;
import it.eng.tz.puglia.servizi_esterni.profileManager.dto.response.EnteResponseBean;
import it.eng.tz.puglia.servizi_esterni.profileManager.dto.response.ProfileUserResponseBean;

public interface UserUtil {

	ProfileUserResponseBean getMyProfile();
	
	String getCodice_GruppoIdRuolo();
	
	String getNomeGruppo();
	
	Gruppi getGruppo();
	Gruppi getGruppo(String gruppo_id_ruolo);
	
	String getGruppo_Id();
	/**
	 * a partire da ED_58_F restituisce ED_58 quindi esclude la parte ruolo del gruppo
	 * @autor Adriano Colaianni
	 * @date 27 apr 2022
	 * @param gruppo_id_ruolo
	 * @return
	 */
	String getGruppo_Id(String gruppo_id_ruolo);	

	Ruoli getRuolo();
	Ruoli getRuolo(String gruppo_id_ruolo);
	
	long   getLongId();
	long   getLongId(String gruppo_id_ruolo);
	
	int    getIntegerId();
	int    getIntegerId(String gruppo_id_ruolo);

	boolean hasUserLogged();

	/**
	 * @autor Adriano Colaianni
	 * @date 21 giu 2022
	 * @return
	 */
	List<EnteResponseBean> getMyEnti();

}