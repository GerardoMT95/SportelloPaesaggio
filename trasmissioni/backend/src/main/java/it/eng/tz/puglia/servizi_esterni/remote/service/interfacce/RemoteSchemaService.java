package it.eng.tz.puglia.servizi_esterni.remote.service.interfacce;

import java.util.List;

import it.eng.tz.puglia.servizi_esterni.remote.dto.EnteDTO;

public interface RemoteSchemaService {
	
	/**
	 * Retrieves List of all ente which are accessible for this application
	 * @return List&lt;EnteDAO&gt;
	 */
	List<EnteDTO> findAllAvailableEnte();
	
	/**
	 * Retrieves specific ente, identified by code, if it is accessible by the
	 * application
	 * 
	 * @param code
	 * @return EnteDAO
	 */
	EnteDTO findEnteByCode(String code);

	/**
	 * Retrieves all ente, identified by codes, if it is accessible by the
	 * application. If some ente is not accessible by the application then it is
	 * ignored.
	 * 
	 * @param codes
	 * @return List&lt;EnteDAO&gt;
	 */
	List<EnteDTO> findAllEnteByCodes(List<String> codes);
	
	/**
	 * @return tutti i comuni e le province della regione puglia
	 * @throws Exception
	 */
	List<EnteDTO> findComuniEProvince() throws Exception;
	
	/**
	 * Restituisce una lista di enti sulla base della lista di codici istat passati in input al metodo
	 * @param codici codici istat sulla base del quale si vuole cercare
	 * @return lista di {@link EnteDTO} il cui codice rientra fra quelli passati in input
	 * @throws Exception
	 */
	List<EnteDTO> getFromCodici(List<String> codici) throws Exception;
	
}