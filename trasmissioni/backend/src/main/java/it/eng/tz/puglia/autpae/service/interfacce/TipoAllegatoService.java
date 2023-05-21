package it.eng.tz.puglia.autpae.service.interfacce;

import it.eng.tz.puglia.autpae.entity.TipoAllegatoDTO;
import it.eng.tz.puglia.autpae.enumeratori.TipoAllegato;
import it.eng.tz.puglia.autpae.search.TipoAllegatoSearch;
import it.eng.tz.puglia.bean.PaginatedList;

public interface TipoAllegatoService {
	
	// BaseRepository
//	public List<TipoAllegatoDTO> select() throws Exception;
	public long count(TipoAllegatoSearch filter) throws Exception;
	public TipoAllegatoDTO find(TipoAllegato pk) throws Exception;
//	public TipoAllegato insert(TipoAllegatoDTO entity) throws Exception;
//	public int update(TipoAllegatoDTO entity) throws Exception;
//	public int delete(TipoAllegatoSearch search) throws Exception;
	public PaginatedList<TipoAllegatoDTO> search(TipoAllegatoSearch filter) throws Exception;

}