package it.eng.tz.puglia.autpae.service.interfacce;

import it.eng.tz.puglia.autpae.entity.ResponsabileDTO;
import it.eng.tz.puglia.autpae.search.ResponsabileSearch;
import it.eng.tz.puglia.bean.PaginatedList;

public interface ResponsabileService {
	
	// BaseRepository
//	public List<ResponsabileDTO> select() throws Exception;
	public long count(ResponsabileSearch filter) throws Exception;
//	public ResponsabileDTO find(Long pk) throws Exception;
//	public Long insert(ResponsabileDTO entity) throws Exception;
//	public int update(ResponsabileDTO entity) throws Exception;
//	public int delete(ResponsabileSearch entity) throws Exception;
	public PaginatedList<ResponsabileDTO> search(ResponsabileSearch bean) throws Exception;
	
	
	
	
	public void salva(ResponsabileDTO responsabile) throws Exception;
	
	public Long inserisci(ResponsabileDTO responsabile) throws Exception;

}