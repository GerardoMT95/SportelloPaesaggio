package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service;


import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.*;

import java.util.List;
import java.util.UUID;

/**
 * Service interface CRUD for table presentazione_istanza.referenti
 */
public interface IReferentiService extends ICrudService<ReferentiDTO, ReferentiSearch, ReferentiRepository>{

	Long count(ReferentiSearch search) throws Exception;
	ReferentiDTO selectRichiedente(UUID idPratica, String tipo);
	List<DestinatarioDTO> findDestinatariPratica(UUID idPratica) throws Exception;

}
