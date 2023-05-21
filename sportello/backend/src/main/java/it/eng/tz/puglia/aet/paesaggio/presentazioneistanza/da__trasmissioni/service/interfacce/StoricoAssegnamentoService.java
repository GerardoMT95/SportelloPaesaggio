package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.service.interfacce;

import java.util.List;
import java.util.UUID;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.StoricoAssegnamentoNewDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.StoricoAssegnamentoOldDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.search.StoricoAssegnamentoSearch;
import it.eng.tz.puglia.bean.PaginatedList;

public interface StoricoAssegnamentoService {

	// BaseRepository
//	public List<StoricoAssegnamentoDTO> select() throws Exception;
	public long count(StoricoAssegnamentoSearch filter) throws Exception;
//	public StoricoAssegnamentoDTO find(Long pk) throws Exception;
	public Long insert(StoricoAssegnamentoOldDTO entity) throws Exception;
//	public int update(StoricoAssegnamentoDTO entity) throws Exception;
//	public int delete(StoricoAssegnamentoSearch search) throws Exception;
	public PaginatedList<StoricoAssegnamentoOldDTO> search(StoricoAssegnamentoSearch filter) throws Exception;
	
	// FullRepository
	
	
	
	public List<StoricoAssegnamentoOldDTO> getStoricoAssegnamento(UUID idFascicolo) throws Exception;
	public List<StoricoAssegnamentoNewDTO> creaNewDTO(List<StoricoAssegnamentoOldDTO> storicoOld);

}