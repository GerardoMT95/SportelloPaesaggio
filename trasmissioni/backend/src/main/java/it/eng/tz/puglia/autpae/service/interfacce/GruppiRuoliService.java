package it.eng.tz.puglia.autpae.service.interfacce;

import java.util.List;

import it.eng.tz.puglia.autpae.controller.exception.CustomOperationToAdviceException;
import it.eng.tz.puglia.autpae.dto.BE_to_FE.PlainTypeStringIdDTO;
import it.eng.tz.puglia.autpae.enumeratori.Gruppi;
import it.eng.tz.puglia.autpae.enumeratori.Ruoli;
import it.eng.tz.puglia.servizi_esterni.profileManager.dto.AssUtenteGruppo;
import it.eng.tz.puglia.servizi_esterni.profileManager.dto.UtenteGruppo;

public interface GruppiRuoliService {

	// BaseRepository
	
	// FullRepository
	
	// Altri Metodi
	
	public void 		 checkGruppo				 (String gruppo) 		throws Exception;
		
//	public List<String>  comuniForGruppo			 (String gruppo) 		throws Exception;

	public List<String>  comuniForGruppoUtenteLoggato() 					throws Exception;

	public void 		 checkAbilitazioneFor		 (Gruppi ... gruppi) 	throws CustomOperationToAdviceException;
	public void 		 checkAbilitazioneFor		 (Ruoli  ... ruoli ) 	throws CustomOperationToAdviceException;
	
	public List<Integer> entiForGruppoUtenteLoggato  () 					throws Exception;
	public List<Integer> entiForGruppo				 (String gruppo_id) 	throws Exception;
	
	public List<PlainTypeStringIdDTO> 		getGruppiOrganizzazioni() 		throws Exception;

	@Deprecated
	public UtenteGruppo[] getAdministratorsProfile() throws Exception;
	
	public AssUtenteGruppo[] getAdministratorsProfilePM() throws Exception;
	
	boolean organizzazioneValida() throws Exception;

	/**
	 * verifica se ED_ oppure in caso di PUTT è ammesso anche un ETI_ che sia legato a common.ente.tipo=CC ed abbia codiceCivilia non nullo in paesaggio_organizzazione
	 * @autor Adriano Colaianni
	 * @date 24 giu 2021
	 * @throws CustomOperationToAdviceException
	 */
	void checkAbilitazioneForTrasmissione() throws CustomOperationToAdviceException; 
	
	/**
	 * verifica se checkAbilitazioneTrasmissione oppure appartiene al gruppo ADMIN
	 * @autor Adriano Colaianni
	 * @date 24 giu 2021
	 * @throws CustomOperationToAdviceException
	 */
	void checkAbilitazioneForCancellazione() throws CustomOperationToAdviceException;

	/**
	 * true se l'organizzazione contiene anche l'utente loggato, verifica nel profilo dell'utente loggato
	 * SecurityUtil.getUserDetail() 
	 * @autor Adriano Colaianni
	 * @date 20 set 2021
	 * @param idOrganizzazione
	 * @return
	 */
	boolean userHasOrganizzazione(long idOrganizzazione);

	/**
	 * lista delle organizzazioni dell'utente loggato (SecurityUtil.getUserDetail())
	 * @autor Adriano Colaianni
	 * @date 20 set 2021
	 * @return
	 */
	List<Integer> organizzazioniUser();
	
	boolean isMultiComune(String codiceGruppo);

	
}