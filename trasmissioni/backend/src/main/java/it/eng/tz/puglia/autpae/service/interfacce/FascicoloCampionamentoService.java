package it.eng.tz.puglia.autpae.service.interfacce;

import it.eng.tz.puglia.autpae.entity.FascicoloCampionamentoDTO;
import it.eng.tz.puglia.autpae.entity.composedKeys.FascicoloCampionamentoPK;
import it.eng.tz.puglia.autpae.search.FascicoloCampionamentoSearch;
import it.eng.tz.puglia.bean.PaginatedList;

public interface FascicoloCampionamentoService {
	
	// BaseRepository
//	public List<FascicoloCampionamentoDTO> select() throws Exception;
	public long count(FascicoloCampionamentoSearch filter) throws Exception;
//	public FascicoloCampionamentoDTO find(FascicoloCampionamentoPK pk) throws Exception;
	public FascicoloCampionamentoPK insert(FascicoloCampionamentoDTO entity) throws Exception;
//	public int update(FascicoloCampionamentoDTO entity) throws Exception;
	public int delete(FascicoloCampionamentoSearch search) throws Exception;
	public PaginatedList<FascicoloCampionamentoDTO> search(FascicoloCampionamentoSearch filter) throws Exception;
	
}