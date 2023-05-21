package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.service.interfacce;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.ValoriAssegnamentoOldDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.search.ValoriAssegnamentoSearch;
import it.eng.tz.puglia.bean.PaginatedList;

public interface ValoriAssegnamentoService {

	// BaseRepository
//	public List<ValoriAssegnamentoDTO> select() throws Exception;
	public long count(ValoriAssegnamentoSearch filter) throws Exception;
//	public ValoriAssegnamentoDTO find(Long pk) throws Exception;
	public Integer insert(ValoriAssegnamentoOldDTO entity) throws Exception;
	public int update(ValoriAssegnamentoOldDTO entity) throws Exception;
	public int delete(ValoriAssegnamentoSearch search) throws Exception;
	public PaginatedList<ValoriAssegnamentoOldDTO> search(ValoriAssegnamentoSearch filter) throws Exception;

	// FullRepository

	

}