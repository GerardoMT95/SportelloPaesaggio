package it.eng.tz.puglia.autpae.service.interfacce;

import java.util.List;

import it.eng.tz.puglia.autpae.entity.AllegatoEnteDTO;
import it.eng.tz.puglia.autpae.entity.composedKeys.AllegatoEntePK;
import it.eng.tz.puglia.autpae.search.AllegatoEnteSearch;
import it.eng.tz.puglia.bean.PaginatedList;

public interface AllegatoEnteService {
	
	// BaseRepository
//	public List<AllegatoEnteDTO> select() throws Exception;
	public long count(AllegatoEnteSearch filter) throws Exception;
	public AllegatoEnteDTO find(AllegatoEntePK pk) throws Exception;
	public AllegatoEntePK insert(AllegatoEnteDTO entity) throws Exception;
	void insert(List<AllegatoEnteDTO> entity) throws Exception;
//	public int update(AllegatoEnteDTO entity) throws Exception;
	public int delete(AllegatoEnteSearch search) throws Exception;
	public PaginatedList<AllegatoEnteDTO> search(AllegatoEnteSearch filter) throws Exception;
	
}