package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.DocumentazioneCdsDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDocumentazioneCdsDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.PraticaDocumentazioneCdsRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.PraticaDocumentazioneCdsSearch;


public interface IPraticaDocumentazioneCdsService extends ICrudService<PraticaDocumentazioneCdsDTO, PraticaDocumentazioneCdsSearch, PraticaDocumentazioneCdsRepository> {

	List<DocumentazioneCdsDto> list(String idIstruttoria) throws Exception;

	void download(String idIstruttoria, Long id, HttpServletResponse response) throws Exception;

	void save(String idIstruttoria, List<PraticaDocumentazioneCdsDTO> list) throws Exception;
}
