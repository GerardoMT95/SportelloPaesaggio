package it.eng.tz.puglia.autpae.service.interfacce;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.hsqldb.lib.StringUtil;

import it.eng.tz.puglia.autpae.entity.CampionamentoDTO;
import it.eng.tz.puglia.autpae.entity.ConfigurazioneCampionamentoDTO;
import it.eng.tz.puglia.autpae.entity.FascicoloDTO;
import it.eng.tz.puglia.autpae.search.CampionamentoSearch;
import it.eng.tz.puglia.bean.PaginatedList;

public interface CampionamentoService {
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////// STATIC
	
	/**
	 * Ordina una lista di Short e la converte in una stringa separata da virgole 
	 * @param  array									- Lista non ordinata di Short
	 * @return numeri 									- Stringa ordinata separata da virgole
	 */
	static String shortArrayToString(List<Short> array) { 
		if(array==null || array.isEmpty()) {
			return null;
		}
		Collections.sort(array);
		String numeri = "";
		array.forEach(numero->{
			numeri.concat(""+numero+",");
		});
		return numeri.substring(0, numeri.length()-1);
	}
	
	/**
	 * Ordina una stringa di Short separati da virgole e la converte in un ArrayList ordinato di Short 
	 * @param  numeri									- stringa di Short non ordinati, separati da virgole
	 * @return array				   					- ArrayList ordinato di Short
	 */
	static ArrayList<Short> stringToShortArray(String numeri) { 
		ArrayList<Short> array = new ArrayList<Short>();
		if (!StringUtil.isEmpty(numeri)) {
			String[] strArray = numeri.split(",");
			for(int i = 0; i < strArray.length; i++) {
				array.add(Short.parseShort(strArray[i]));
			}
			Collections.sort(array);
		}
		return array;
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////// BaseRepository
	
//	public List<CampionamentoDTO> select() throws Exception;
	public long count(CampionamentoSearch filter) throws Exception;
	public CampionamentoDTO find(Long pk) throws Exception;
	public Long insert(CampionamentoDTO entity) throws Exception;
	public int update(CampionamentoDTO entity) throws Exception;
	public int delete(CampionamentoSearch search) throws Exception;
	public PaginatedList<CampionamentoDTO> search(CampionamentoSearch filter) throws Exception;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////// FullRepository
	
//	public void setPercentOnActiveSampling(short percent, boolean customized);
	
/*	/**
	 * This service returns the percent of an active sampling.
	 * @return short									- short value of percentage of an active sampling
	 **
	short getMaxPercentage(); */
	
	/**
	 * This service returns the CampionamentoDTO of an active sampling 
	 * @return CampionamentoDTO				   			- returns sampling data 
	 */
	CampionamentoDTO getActiveSampling();
	
	/**
	 * This service sets the active column of sampling to false, therefore deactivating it 
	 */
	void deactivateSampling();
	
	/**
	 * This service returns all the samplings which don't have selected fascicoli verified 
	 * @return List<SamplingDTO>						- list of sampling entities
	 */
	List<CampionamentoDTO> getSamplingsNotVerified();
	
	public int findActiveSamplingFascicoli(long samplingId, Date publishDate, Date samplingDate);
	
	public List<FascicoloDTO> getAllActiveFascicoli(long samplingId);
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////// ALTRI metodi
	
/*	/**
	 * This service returns the CampionamentoModificatoDTO of an active sampling.
	 * It contains the number of fascicoli included in it so far.
	 * @return CampionamentoModificatoDTO				- returns sampling data 
	 **
	CampionamentoDTO getFascicoli(); */

/*	/**
	 * This service is setting the percent of an active sampling.
	 * @param percent									- short value of the percent of fascicoli
	 **
	void setPercentage(short percent); */

/*	/**
	 * This service resets the percent of an active sampling on default value based on provided formula.
	 * @return int										- short value of calculated percentage
	 **
	short resetPercentage(); */

	/**
	 * This service checks if there are any active samplings and if not, creates new one 
	 * @param fascicoloId 								- long value of a fascicolo's id
	 */
	void samplingCheck(long fascicoloId);

	/**
	 * This service performs the sampling and returns the sampled fascicoli for verification 
	 * in form of map of Strings and list of FascicoloDTO fascicolo data
	 * @param entityId									- long value of entity id
	 * @param samplingPercent							- short value of sampling percent
	 * @return Map<String, List<FascicoloDTO>>			- map of fascicoli for verification
	 */
	Map<String, List<FascicoloDTO>> getVerificationFascicoli(long entityId, short samplingPercent);

	/**
	 * This service creates new active sampling
	 * @return int										- int value of the id of the inserted sampling
	 */
	long createSamplingProcess();
	
	/**
	 * Ottiene l'attuale ConfigurazioneCampionamento
	 * @return ConfigurazioneCampionamentoDTO			- la ConfigurazioneCampionamento
	 * @throws Exception 
	 */
	ConfigurazioneCampionamentoDTO getConfigurazioneCampionamento() throws Exception;

	/**
	 * Aggiorna l'attuale ConfigurazioneCampionamento
	 * @param  ConfigurazioneCampionamentoDTO			- la ConfigurazioneCampionamento da salvare
	 * @throws Exception 
	 */
	void updateConfigurazione(ConfigurazioneCampionamentoDTO configurazioneCampionamento) throws Exception;

	/**
	 * Cancella tutti i campionamenti associati ad un fascicolo 
	 * @return boolean									- returns true se è stato cancellato almeno un campionamento
	 * @throws Exception 
	 */
	boolean deleteByFascicoloId(long idFascicolo) throws Exception;

	
	/**
	 * invia la mail a Regione con la notifica del campionamento imminente..
	 * @param idCampionamento (id del record di campionamento)
	 * @throws Exception
	 */
	public void inviaPresamplingNotification(long idCampionamento) throws Exception;

	public void inviaNotificaVerifica(long idCampionamento) throws Exception;

	/**
	 * effettua tutto il task di campionamento ed invio notifica
	 *  - seleziona i fascicoli del campionamento in questione,
	 *  - genera i numeri casuali per "campionare"
	 *  - genera un documento da protocollare in uscita ed inviare via mail  (TODO)
	 * @param entity
	 * @throws Exception
	 */
	public void effettuaCampionamentoEInviaNotifica(CampionamentoDTO entity) throws Exception;
	
}