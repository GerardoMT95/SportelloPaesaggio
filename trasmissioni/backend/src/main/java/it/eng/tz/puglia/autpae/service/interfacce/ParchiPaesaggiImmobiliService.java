package it.eng.tz.puglia.autpae.service.interfacce;

import java.util.List;

import it.eng.tz.puglia.autpae.entity.ParchiPaesaggiImmobiliDTO;
import it.eng.tz.puglia.autpae.entity.composedKeys.ParchiPaesaggiImmobiliPK;
import it.eng.tz.puglia.autpae.search.ParchiPaesaggiImmobiliSearch;
import it.eng.tz.puglia.bean.PaginatedList;

public interface ParchiPaesaggiImmobiliService {
	
	//BaseRepository
	public ParchiPaesaggiImmobiliPK 				insert		(ParchiPaesaggiImmobiliDTO entity);
	public int 										delete		(ParchiPaesaggiImmobiliSearch filter) throws Exception;
	public PaginatedList<ParchiPaesaggiImmobiliDTO> search		(ParchiPaesaggiImmobiliSearch filter) throws Exception;
	
	//FullRepository
	public List<ParchiPaesaggiImmobiliDTO> 			select		(Long praticaId, Long comuneId, String tipoVincolo);
	public int 										setSelezioni(Long praticaId, Long comuneId, String tipoVincolo, List<String> codici);
	
}