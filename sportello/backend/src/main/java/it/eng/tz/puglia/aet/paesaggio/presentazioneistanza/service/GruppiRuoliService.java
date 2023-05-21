package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service;

import java.util.List;
import java.util.function.Function;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainStringValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Ruoli;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.AssUtenteGruppo;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.profilemanager.dto.UtenteGruppo;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.report.bean.UtenteAttributeBean;

public interface GruppiRuoliService {

	public void 		  				checkGruppo				 	(String gruppo) 	throws Exception;

	public List<String>   				comuniForGruppoUtenteLoggato() 					throws Exception;
//	public List<String>   				comuniForGruppo			 	(String gruppo) 	throws Exception;

	public List<Integer> 				entiForGruppoUtenteLoggato  () 					throws Exception;
	public List<Integer> 				entiForGruppo				(String gruppo_id) 	throws Exception;
	
	public void 		  				checkAbilitazioneFor		(Gruppi ... gruppi) throws CustomOperationToAdviceException;
	public void 		 				checkAbilitazioneFor		(Ruoli  ... ruoli ) throws CustomOperationToAdviceException;
	
	public List<PlainStringValueLabel> 	getGruppiOrganizzazioni		() 					throws Exception;
	public List<PlainStringValueLabel> 	getGruppiOrganizzazioniConCL() 					throws Exception;

	@Deprecated
	public UtenteGruppo[] getAdministratorsProfile() throws Exception;
	
	public AssUtenteGruppo[] getAdministratorsProfilePM() throws Exception;
	
	/**
	 * Metodo che verifica che l'utente attualmente loggato sia rup per il gruppo con cui è loggato
	 * @return
	 * @throws Exception
	 */
	public boolean isValidRup() throws Exception;

	/**
	 * retrieve profilo utente da gruppo e username (contatta il PM)
	 * usare con attenzione.. violazione di privacy...
	 * @author acolaianni
	 *
	 * @param gruppo
	 * @param username
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	UtenteGruppo getUtenteProfiles(Gruppi gruppo, String username) throws Exception;
	
	/**
	 * retrieve profilo utente da gruppo e username (contatta il PM)
	 * usare con attenzione.. violazione di privacy...
	 * @author acolaianni
	 *
	 * @param gruppo
	 * @param username
	 * @return
	 * @throws Exception
	 */
	AssUtenteGruppo getUtenteProfilesPM(Gruppi gruppo, String username) throws Exception;
	
	/**
	 * esegue performCall impostanto il token batch e passandoglielo come parametro.
	 * @author acolaianni
	 *
	 * @param performCall
	 * @return
	 * @throws Exception
	 */
	Object execWithbatchToken(Function<String, ?> performCall) throws Exception;
	
	/**
	 * inserisce informazioni utente loggato in common.utente_attribute se non presenti
	 * @author Simone Verna
	 * @date 25 ago 2022
	 * @throws Exception
	 */
	public void upsertInfoUser() throws Exception;
	
	/**
	 * Aggiornamento dati utente attribute
	 * @author Simone Verna
	 * @date 26 ago 2022
	 * @param bean
	 * @throws Exception
	 */
	public void updateUtenteAttribute(UtenteAttributeBean bean) throws Exception;

	
	
	
}