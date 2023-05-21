package it.eng.tz.puglia.autpae.service.interfacce;

import java.util.List;

import it.eng.tz.puglia.autpae.dto.TabelleAssegnamentoFascicoliDTO;
import it.eng.tz.puglia.autpae.entity.AssegnamentoFascicoloDTO;
import it.eng.tz.puglia.autpae.enumeratori.TipoOperazione;
import it.eng.tz.puglia.autpae.search.AssegnamentoFascicoloSearch;
import it.eng.tz.puglia.autpae.search.TabelleAssegnamentoFascicoliSearch;
import it.eng.tz.puglia.bean.PaginatedList;

public interface AssegnamentoFascicoloService {

	// BaseRepository
//	public List<AssegnamentoFascicoloDTO> select() throws Exception;
	public long count(AssegnamentoFascicoloSearch filter) throws Exception;
//	public AssegnamentoFascicoloDTO find(Long pk) throws Exception;
	public Integer insert(AssegnamentoFascicoloDTO entity) throws Exception;
	public int update(AssegnamentoFascicoloDTO entity) throws Exception;
//	public int delete(AssegnamentoFascicoloSearch search) throws Exception;
	public PaginatedList<AssegnamentoFascicoloDTO> search(AssegnamentoFascicoloSearch filter) throws Exception;


	// FullRepository
	public PaginatedList<TabelleAssegnamentoFascicoliDTO> tabelleAssegnamentoFascicoliSearch (TabelleAssegnamentoFascicoliSearch filters) throws Exception;
	public List<String> autocompleteCodiceFascicoliAssegnati   (String codice) throws Exception;
	public List<String> autocompleteCodiceFascicoliNonAssegnati(String codice) throws Exception;

	
	public AssegnamentoFascicoloDTO assegnamentoFascicolo(AssegnamentoFascicoloDTO entity, TipoOperazione tipoOperazione) throws Exception;
	public void assegnamentoAutomaticoInTrasmissione(long idFascicolo, boolean ritrasmissione) throws Exception;
//	public String getPrefissoCodice() throws Exception;
	
}