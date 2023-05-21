package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service;

import java.io.File;
import java.util.List;
import java.util.UUID;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.allegati.AllegatiDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.StatoIntegrazioneDocumentale;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperIntegrazioneDocumentaleDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.IntegrazioneDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.IntegrazioneSearch;

public interface IntegrazioneDocumentale
{
	/**
	 * 
	 * @author acolaianni
	 *
	 * @param entity
	 * @param spontanea true se parte dal richiedente, altrimenti è una creazione che parte da una richiesta dell'ente
	 * @return
	 * @throws Exception
	 */
	IntegrazioneDTO save(IntegrazioneDTO entity,boolean spontanea) throws Exception;
	Integer update(IntegrazioneDTO entity) throws Exception;
	List<IntegrazioneDTO> search(IntegrazioneSearch filters) throws Exception;
	Integer delete(IntegrazioneSearch filters) throws Exception;
	IntegrazioneDTO findActiveIntegration(UUID idPratica) throws Exception;
	AllegatiDto getAllegatiIntegrazione(UUID idPratica, Integer idIntegrazione) throws Exception;
	AllegatiDTO getMetadatiDocumentoIntegrazione(UUID idPratica, Integer idIntegrazione) throws Exception;
	Integer updateStatus(Integer idIntegrazione, StatoIntegrazioneDocumentale nuovoStato) throws Exception;
	File generateJasperIntegrazioneDocumentale(Integer idIntegrazione, UUID idPratica, String codicePratica) throws Exception;
	/**
	 * usato per scopi di test
	 * @author acolaianni
	 *
	 * @param idIntegrazione
	 * @param idPratica
	 * @param codicePratica
	 * @return
	 * @throws Exception
	 */
	String generateJsonJasperIntegrazioneDocumentaleDto(Integer idIntegrazione, UUID idPratica,
			String codicePratica) throws Exception;
}
