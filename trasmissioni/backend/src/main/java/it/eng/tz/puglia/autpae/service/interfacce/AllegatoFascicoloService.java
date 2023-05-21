package it.eng.tz.puglia.autpae.service.interfacce;

import it.eng.tz.puglia.autpae.entity.AllegatoFascicoloDTO;
import it.eng.tz.puglia.autpae.entity.composedKeys.AllegatoFascicoloPK;
import it.eng.tz.puglia.autpae.enumeratori.TipoAllegato;
import it.eng.tz.puglia.autpae.search.AllegatoFascicoloSearch;
import it.eng.tz.puglia.bean.PaginatedList;

public interface AllegatoFascicoloService {
	
	// BaseRepository
//	public List<AllegatoFascicoloDTO> select() throws Exception;
	public long count(AllegatoFascicoloSearch filter) throws Exception;
	public AllegatoFascicoloDTO find(AllegatoFascicoloPK pk) throws Exception;
	public AllegatoFascicoloPK insert(AllegatoFascicoloDTO entity) throws Exception;
//	public int update(AllegatoFascicoloDTO entity) throws Exception;
	public int delete(AllegatoFascicoloSearch search) throws Exception;
	public PaginatedList<AllegatoFascicoloDTO> search(AllegatoFascicoloSearch filter) throws Exception;

	
	// FullRepository
	public int cambiaTipo(long idFascicolo, long idAllegato, TipoAllegato tipoPrecedente, TipoAllegato nuovoTipo) throws Exception;
	
}