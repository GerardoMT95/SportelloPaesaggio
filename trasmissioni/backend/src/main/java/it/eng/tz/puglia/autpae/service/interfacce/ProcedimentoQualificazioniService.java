package it.eng.tz.puglia.autpae.service.interfacce;

import it.eng.tz.puglia.autpae.entity.ProcedimentoQualificazioniDTO;
import it.eng.tz.puglia.autpae.entity.composedKeys.ProcedimentoQualificazioniPK;
import it.eng.tz.puglia.autpae.search.ProcedimentoQualificazioniSearch;
import it.eng.tz.puglia.bean.PaginatedList;

public interface ProcedimentoQualificazioniService {
	
	// BaseRepository
//	public List<ProcedimentoQualificazioniDTO> select() throws Exception;
//	public long count(ProcedimentoQualificazioniSearch filter) throws Exception;
//	public ProcedimentoQualificazioniDTO find(ProcedimentoQualificazioniPK pk) throws Exception;
//	public ProcedimentoQualificazioniPK insert(ProcedimentoQualificazioniDTO entity) throws Exception;
//	public int update(ProcedimentoQualificazioniDTO entity) throws Exception;
//	public int delete(ProcedimentoQualificazioniSearch search) throws Exception;
	public PaginatedList<ProcedimentoQualificazioniDTO> search(ProcedimentoQualificazioniSearch filter) throws Exception;

}