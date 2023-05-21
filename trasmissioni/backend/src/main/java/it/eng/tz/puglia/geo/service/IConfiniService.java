package it.eng.tz.puglia.geo.service;

import java.util.List;

/**
 * Service for retrieve wkt of confini amministrativi
 * @author Antonio La Gatta
 * @date 19 ott 2021
 */
public interface IConfiniService {

	/**
	 * @author Antonio La Gatta
	 * @date 12 mag 2022
	 * @param comune
	 * @param foglio
	 * @param particella
	 * @param sezione
	 * @return wkt delle particelle trovate
	 * @throws Exception
	 */
	List<String> wktParticelle(String comune, String foglio, String particella, String sezione) throws Exception;
	
}
