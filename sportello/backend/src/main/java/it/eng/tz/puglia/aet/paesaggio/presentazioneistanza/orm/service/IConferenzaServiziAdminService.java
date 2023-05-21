package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.CdsGetConfigurazioneDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.CdsSaveConfigurazioneDto;

public interface IConferenzaServiziAdminService {
	
	/**
	 * recupero della configurazione della conferenza dei servizi
	 * @author Alessio Bottalico
	 * @date 25 lug 2022
	 * @param idProcedimento
	 * @return
	 * @throws Exception
	 */
	CdsGetConfigurazioneDto getConfigurazione(String idProcedimento) throws Exception;
	/**
	 * Salvataggio configurazione conferenza dei servizi 
	 * @author Alessio Bottalico
	 * @date 25 lug 2022
	 * @param idProcedimento
	 * @param dto
	 * @throws Exception
	 */
	void saveConfigurazione(String idProcedimento, CdsSaveConfigurazioneDto dto) throws Exception;

}
