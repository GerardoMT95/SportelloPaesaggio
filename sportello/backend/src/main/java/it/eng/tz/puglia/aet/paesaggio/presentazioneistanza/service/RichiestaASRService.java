package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service;

import java.util.List;
import java.util.UUID;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.DettaglioCorrispondenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.RichiestaAsrDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.SospensioneArchiviazioneAttivazioneDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.SospensioneArchiviazioneAttivazioneSearch;
import it.eng.tz.puglia.bean.PaginatedList;

public interface RichiestaASRService
{
	PaginatedList<SospensioneArchiviazioneAttivazioneDTO> search(SospensioneArchiviazioneAttivazioneSearch search) throws Exception;
	RichiestaAsrDTO findActiveDraft(UUID idPratica ) throws Exception;
	RichiestaAsrDTO findById(Long id) throws Exception;
	SospensioneArchiviazioneAttivazioneDTO insert(SospensioneArchiviazioneAttivazioneDTO entity) throws Exception;
	SospensioneArchiviazioneAttivazioneDTO update(SospensioneArchiviazioneAttivazioneDTO entity) throws Exception;
	Integer delete(Long id) throws Exception;
	Long count(SospensioneArchiviazioneAttivazioneSearch search) throws Exception;
	void verifyPermissions(Long idRichiesta, UUID idPratica) throws Exception;
	List<RichiestaAsrDTO> getStorico(UUID idPratica) throws Exception;
	DettaglioCorrispondenzaDTO prepareTemplateRichiesta(UUID idPratica, String codice) throws Exception;
}
