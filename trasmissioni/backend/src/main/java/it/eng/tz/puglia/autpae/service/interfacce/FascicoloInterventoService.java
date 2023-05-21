package it.eng.tz.puglia.autpae.service.interfacce;

import java.util.List;

import it.eng.tz.puglia.autpae.entity.FascicoloInterventoDTO;
import it.eng.tz.puglia.autpae.entity.composedKeys.FascicoloInterventoPK;
import it.eng.tz.puglia.autpae.search.FascicoloInterventoSearch;
import it.eng.tz.puglia.bean.PaginatedList;

public interface FascicoloInterventoService {
	
	// BaseRepository
//	public List<FascicoloInterventoDTO> select() throws Exception;
	public long count(FascicoloInterventoSearch filter) throws Exception;
	public FascicoloInterventoDTO find(FascicoloInterventoPK pk) throws Exception;
	
	public FascicoloInterventoPK insert(FascicoloInterventoDTO entity) throws Exception;
//	public int update(FascicoloInterventoDTO entity) throws Exception;
	public int delete(FascicoloInterventoSearch entity) throws Exception;
	public PaginatedList<FascicoloInterventoDTO> search(FascicoloInterventoSearch bean) throws Exception;
	// FullRepository
	public int insertMultiple(List<Long> listaIdTipiQualificazioni, Long idFascicolo) throws Exception;
	
}