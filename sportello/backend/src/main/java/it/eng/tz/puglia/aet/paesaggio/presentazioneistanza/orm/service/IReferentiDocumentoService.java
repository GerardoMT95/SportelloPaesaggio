package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ReferentiDocumentoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.ReferentiDocumentoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.ReferentiDocumentoSearch;

/**
 * Service interface CRUD for table presentazione_istanza.referenti_documento
 */
public interface IReferentiDocumentoService extends ICrudService<ReferentiDocumentoDTO, ReferentiDocumentoSearch, ReferentiDocumentoRepository>{

}
