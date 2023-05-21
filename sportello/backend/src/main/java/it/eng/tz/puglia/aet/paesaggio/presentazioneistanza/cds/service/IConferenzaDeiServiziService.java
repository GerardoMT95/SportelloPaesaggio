package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.cds.service;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.ConferenzaApiPartecipantiExtendedDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.SezioneAllegati;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.cds.bean.ConferenzaApiCreatoreDto;
import it.eng.tz.puglia.cds.bean.ConferenzaApiDto;

/**
 * Creazione conferenza
 * @author Antonio La Gatta
 * @date 30 nov 2021
 */
public interface IConferenzaDeiServiziService {

	String UNITA_ORGANIZZATIVA = "UO";
	String COMITATO = "CM";
	
	/**
	 * @author Antonio La Gatta
	 * @date 30 nov 2021
	 * @param id
	 * @return {@link ConferenzaApiDto} per creazione conferenza
	 * @throws Exception
	 */
	ConferenzaApiDto getConferenzaApiDto(String id, String codiceGruppo) throws Exception;
	/**
	 * @author Antonio La Gatta
	 * @date 17 mar 2022
	 * @param id
	 * @return creatore
	 * @throws Exception
	 */
	ConferenzaApiCreatoreDto getCreatoreApiDto(String id) throws Exception;
	/**
	 * @author Antonio La Gatta
	 * @date 17 mar 2022
	 * @param id
	 * @return id Pratica
	 * @throws Exception
	 */
	String getIdPratica(String id) throws Exception;
	/**
	 * Aggiorna id cds
	 * @author Antonio La Gatta
	 * @date 30 nov 2021
	 * @param id
	 * @param idCds
	 */
	void updateIdCds(String id, long idCds);
	/**
	 * @author Antonio La Gatta
	 * @date 17 mar 2022
	 * @param idPratica
	 * @return lista id cds associate a Pratica
	 */
	List<Long> idCds(String idPratica);
	/**
	 * @author Antonio La Gatta
	 * @date 21 mar 2022
	 * @param idPratica
	 * @return lista id cds associate a Pratica di tipo non comitato
	 */
	List<Long> idCdsNonComitato(String idPratica);
	/**
	 * @author Antonio La Gatta
	 * @date 17 mar 2022
	 * @param idPratica
	 * @param idEnte
	 * @return partecipante
	 * @throws Exception
	 */
	ConferenzaApiPartecipantiExtendedDto getPartecipante(String idPratica , int idEnte)throws Exception;
	/**
	 * aggiunta dell'allegato alle cds aperte sulla pratica
	 * @author acolaianni
	 *
	 * @param idPratica
	 * @param sezione
	 * @param allegato
	 * @throws JsonProcessingException
	 * @throws SQLException
	 */
	void appendiDocumentoACds(UUID idPratica, SezioneAllegati sezione, AllegatiDTO allegato)
			throws JsonProcessingException, SQLException;
}
