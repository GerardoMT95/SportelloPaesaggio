package it.eng.tz.puglia.autpae.service.interfacce;

import java.util.List;

import it.eng.tz.puglia.autpae.entity.StoricoAssegnamentoDTO;
import it.eng.tz.puglia.autpae.search.StoricoAssegnamentoSearch;
import it.eng.tz.puglia.bean.PaginatedList;

public interface StoricoAssegnamentoService {

	// BaseRepository
//	public List<StoricoAssegnamentoDTO> select() throws Exception;
	public long count(StoricoAssegnamentoSearch filter) throws Exception;
//	public StoricoAssegnamentoDTO find(Long pk) throws Exception;
	public Long insert(StoricoAssegnamentoDTO entity) throws Exception;
//	public int update(StoricoAssegnamentoDTO entity) throws Exception;
//	public int delete(StoricoAssegnamentoSearch search) throws Exception;
	public PaginatedList<StoricoAssegnamentoDTO> search(StoricoAssegnamentoSearch filter) throws Exception;
	
	// FullRepository
	
	
	
	public List<StoricoAssegnamentoDTO> getStoricoAssegnamento(long idFascicolo) throws Exception;

}