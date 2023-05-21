package it.eng.tz.puglia.autpae.service.interfacce;

import java.util.List;

import it.eng.tz.puglia.autpae.entity.RicevutaDTO;
import it.eng.tz.puglia.autpae.search.RicevutaSearch;
import it.eng.tz.puglia.bean.PaginatedList;

public interface RicevutaService {
	
	// BaseRepository
//	public List<RicevutaDTO> select() throws Exception;
	public long count(RicevutaSearch filter) throws Exception;
	public RicevutaDTO find(Long pk) throws Exception;
//	public Long insert(RicevutaDTO entity) throws Exception;
	public int update(RicevutaDTO entity) throws Exception;
	public int delete(RicevutaSearch search) throws Exception;
	public PaginatedList<RicevutaDTO> search(RicevutaSearch filter) throws Exception;

	// FullRepository
	public PaginatedList<RicevutaDTO> getRicevuteCorrispondenza(RicevutaSearch filtro) throws Exception;
	public int insertMultipla(List<RicevutaDTO> listRicevutaDTO) throws Exception;
	
}