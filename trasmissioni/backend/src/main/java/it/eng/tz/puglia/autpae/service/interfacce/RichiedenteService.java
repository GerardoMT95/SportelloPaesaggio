package it.eng.tz.puglia.autpae.service.interfacce;

import java.util.List;

import it.eng.tz.puglia.autpae.entity.RichiedenteDTO;
import it.eng.tz.puglia.autpae.enumeratori.ErroriValidazioneBE;
import it.eng.tz.puglia.autpae.search.RichiedenteSearch;
import it.eng.tz.puglia.bean.PaginatedList;

public interface RichiedenteService {

	//BaseRepository
//	public List<RichiedenteDTO> select() throws Exception;
	public long count(RichiedenteSearch filter) throws Exception;
	public RichiedenteDTO find(Long pk) throws Exception;
	public Long insert(RichiedenteDTO entity) throws Exception;
	public int update(RichiedenteDTO entity) throws Exception;
	public int delete(RichiedenteSearch entity) throws Exception;
	public PaginatedList<RichiedenteDTO> search(RichiedenteSearch bean) throws Exception;
	
	
	
	public void salva(RichiedenteDTO richiedente) throws Exception;
	
	public Long inserisci(RichiedenteDTO richiedente) throws Exception;

	public List<ErroriValidazioneBE> validazione(RichiedenteDTO richiedente, long idFascicolo) throws Exception;

	public RichiedenteDTO datiRichiedente(Long idFascicolo) throws Exception;
	
}