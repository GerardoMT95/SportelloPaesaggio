package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.RupDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.RupSearch;
import it.eng.tz.puglia.bean.PaginatedList;

public interface IRupService
{
	PaginatedList<RupDTO> search(RupSearch search) throws Exception;
	Boolean isRup(String username, Integer idOrganizzazione) throws Exception;
	
}
