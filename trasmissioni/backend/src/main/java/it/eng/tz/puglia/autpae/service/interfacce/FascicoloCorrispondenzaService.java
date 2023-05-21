package it.eng.tz.puglia.autpae.service.interfacce;

import it.eng.tz.puglia.autpae.entity.FascicoloCorrispondenzaDTO;
import it.eng.tz.puglia.autpae.search.FascicoloCorrispondenzaSearch;
import it.eng.tz.puglia.bean.PaginatedList;

public interface FascicoloCorrispondenzaService {
	
	// BaseRepository
//	public List<FascicoloCorrispondenzaDTO> select() throws Exception;
	public long count(FascicoloCorrispondenzaSearch filter) throws Exception;
	public FascicoloCorrispondenzaDTO find(Long id) throws Exception;
	public Long insert(FascicoloCorrispondenzaDTO entity) throws Exception;
//	public int update(FascicoloCorrispondenzaDTO entity) throws Exception;
	public int delete(FascicoloCorrispondenzaSearch search) throws Exception;
	public PaginatedList<FascicoloCorrispondenzaDTO> search(FascicoloCorrispondenzaSearch filter) throws Exception;
	
}