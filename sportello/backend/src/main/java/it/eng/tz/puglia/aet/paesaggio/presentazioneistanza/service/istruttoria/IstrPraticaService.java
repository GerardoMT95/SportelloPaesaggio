package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import it.eng.tz.puglia.aet.paesaggio.bean.MyOrganizzazioniBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.TabelleAssegnamentoFascicoliOldDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.FascicoloDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.allegati.DocumentoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.PraticaSearch;
import it.eng.tz.puglia.bean.PaginatedList;

public interface IstrPraticaService 
{
	PaginatedList<FascicoloDto> search_istr(PraticaSearch search) throws Exception;
	List<PraticaDTO> searchAll(PraticaSearch search) throws Exception;
	List<PraticaDTO> searchAll(PraticaSearch search, boolean protetto) throws Exception;
	List<DocumentoDto> getDichiarazioni(UUID idPratica) throws Exception;
	/**
	 * forse da spostare altrove, serve a prepopolare alcuni filtri per l'accesso corretto ai dati a seconda dell'utente loggato...
	 * @author acolaianni
	 *
	 * @param search
	 * @throws Exception
	 */
	void prepareForSearch(PraticaSearch search) throws Exception;
	void prepareForSearch(PraticaSearch search, String codiceGruppo, String username) throws Exception;
	
	boolean diMiaCompetenza(PraticaDTO pratica) throws Exception;
	
	PaginatedList<TabelleAssegnamentoFascicoliOldDTO> searchForAssegnamento(PraticaSearch search) throws Exception;
	
	/**
	 * 
	 * @author acolaianni
	 *
	 * @return mappa con idOrganizzazione e lista di id comuni di competenza solo nel caso di enti territoriali SBAP e ETI altrimenti null.
	 */
	MyOrganizzazioniBean organizzazioniEComunidiCompetenza();
}
