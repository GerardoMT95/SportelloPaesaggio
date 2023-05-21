package it.eng.tz.puglia.autpae.service.interfacce;

import it.eng.tz.puglia.autpae.entity.ValoriAssegnamentoDTO;
import it.eng.tz.puglia.autpae.search.ValoriAssegnamentoSearch;
import it.eng.tz.puglia.bean.PaginatedList;

public interface ValoriAssegnamentoService {

	// BaseRepository
//	public List<ValoriAssegnamentoDTO> select() throws Exception;
	public long count(ValoriAssegnamentoSearch filter) throws Exception;
//	public ValoriAssegnamentoDTO find(Long pk) throws Exception;
	public Integer insert(ValoriAssegnamentoDTO entity) throws Exception;
	public int update(ValoriAssegnamentoDTO entity) throws Exception;
	public int delete(ValoriAssegnamentoSearch search) throws Exception;
	public PaginatedList<ValoriAssegnamentoDTO> search(ValoriAssegnamentoSearch filter) throws Exception;

	// FullRepository

	

}