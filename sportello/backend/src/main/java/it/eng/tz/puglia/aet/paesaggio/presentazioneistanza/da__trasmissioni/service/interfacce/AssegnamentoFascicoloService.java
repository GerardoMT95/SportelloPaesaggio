package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.service.interfacce;

import java.util.List;
import java.util.UUID;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.AssegnamentoFascicoloOldDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.TabelleAssegnamentoFascicoliOldDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.enumetori.TipoOperazione;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.search.AssegnamentoFascicoloSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.search.TabelleAssegnamentoFascicoliSearch;
import it.eng.tz.puglia.bean.PaginatedList;

public interface AssegnamentoFascicoloService {

	// BaseRepository
//	public List<AssegnamentoFascicoloDTO> select() throws Exception;
	public long count(AssegnamentoFascicoloSearch filter) throws Exception;
//	public AssegnamentoFascicoloDTO find(Long pk) throws Exception;
	public Integer insert(AssegnamentoFascicoloOldDTO entity) throws Exception;
	public int update(AssegnamentoFascicoloOldDTO entity) throws Exception;
//	public int delete(AssegnamentoFascicoloSearch search) throws Exception;
	public PaginatedList<AssegnamentoFascicoloOldDTO> search(AssegnamentoFascicoloSearch filter) throws Exception;


	// FullRepository
	public PaginatedList<TabelleAssegnamentoFascicoliOldDTO> tabelleAssegnamentoFascicoliSearch (TabelleAssegnamentoFascicoliSearch filters) throws Exception;
	public List<String> autocompleteCodiceFascicoliAssegnati   (String codice) throws Exception;
	public List<String> autocompleteCodiceFascicoliNonAssegnati(String codice) throws Exception;

	
	public AssegnamentoFascicoloOldDTO assegnamentoFascicolo(AssegnamentoFascicoloOldDTO entity, TipoOperazione tipoOperazione) throws Exception;
	public void assegnamentoAutomaticoInTrasmissione(UUID idFascicolo) throws Exception;
//	public String getPrefissoCodice() throws Exception;
	
}