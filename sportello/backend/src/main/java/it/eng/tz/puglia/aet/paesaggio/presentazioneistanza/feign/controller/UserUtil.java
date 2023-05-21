package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller;

import java.util.List;

import org.slf4j.MDC;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Ruoli;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.EnteResponseBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.ProfileUserResponseBean;

public interface UserUtil {

	
	/**
	 * utilizzate in caso di test
	 */
	final static String MDC_GRUPPO_MOCKED="MDC_GRUPPO_MOCKED";
	final static String MDC_USERNAME_MOCKED="MDC_USERNAME_MOCKED";
	
	final static String USERNAME_BATCH="BATCH-USER";//usato in caso di task batch 
	
	public ProfileUserResponseBean getMyProfile();
	
	public String getCodice_GruppoIdRuolo();
	
	public String getNomeGruppo();
	
	public Gruppi getGruppo();
	public Gruppi getGruppo(String gruppo_id_ruolo);
	
	public String getGruppo_Id();
	public String getGruppo_Id(String gruppo_id_ruolo);	

	public Ruoli  getRuolo();
	public Ruoli  getRuolo(String gruppo_id_ruolo);
	
	public long   getLongId();
	public long   getLongId(String gruppo_id_ruolo);
	
	public int    getIntegerId();
	public int    getIntegerId(String gruppo_id_ruolo);

	public boolean hasUserLogged();

	List<EnteResponseBean> getMyEnti();

}