package it.eng.tz.puglia.autpae.service.interfacce;

import it.eng.tz.puglia.autpae.entity.AllegatoObbligatorioDTO;
import it.eng.tz.puglia.autpae.entity.composedKeys.AllegatoObbligatorioPK;
import it.eng.tz.puglia.autpae.search.AllegatoObbligatorioSearch;
import it.eng.tz.puglia.bean.PaginatedList;

public interface AllegatoObbligatorioService {
	
	// BaseRepository
//	public List<AllegatoObbligatorioDTO> select() throws Exception;
	public long count(AllegatoObbligatorioSearch filter) throws Exception;
	public AllegatoObbligatorioDTO find(AllegatoObbligatorioPK pk) throws Exception;
//	public AllegatoObbligatorioPK insert(AllegatoObbligatorioDTO entity) throws Exception;
//	public int update(AllegatoObbligatorioDTO entity) throws Exception;
//	public int delete(AllegatoObbligatorioSearch search) throws Exception;
	public PaginatedList<AllegatoObbligatorioDTO> search(AllegatoObbligatorioSearch filter) throws Exception;
	
	
}