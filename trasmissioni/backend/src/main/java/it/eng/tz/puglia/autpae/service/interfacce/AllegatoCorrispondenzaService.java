package it.eng.tz.puglia.autpae.service.interfacce;

import it.eng.tz.puglia.autpae.entity.AllegatoCorrispondenzaDTO;
import it.eng.tz.puglia.autpae.entity.composedKeys.AllegatoCorrispondenzaPK;
import it.eng.tz.puglia.autpae.search.AllegatoCorrispondenzaSearch;
import it.eng.tz.puglia.bean.PaginatedList;

public interface AllegatoCorrispondenzaService {
	
	// BaseRepository
//	public List<AllegatoCorrispondenzaDTO> select() throws Exception;
	public long count(AllegatoCorrispondenzaSearch filter) throws Exception;
	public AllegatoCorrispondenzaDTO find(AllegatoCorrispondenzaPK pk) throws Exception;
	public AllegatoCorrispondenzaPK insert(AllegatoCorrispondenzaDTO entity) throws Exception;
//	public int update(AllegatoCorrispondenzaDTO entity) throws Exception;
	public int delete(AllegatoCorrispondenzaSearch search) throws Exception;
	public PaginatedList<AllegatoCorrispondenzaDTO> search(AllegatoCorrispondenzaSearch filter) throws Exception;

}