package it.eng.tz.puglia.autpae.service.interfacce;

import java.util.List;

import it.eng.tz.puglia.autpae.entity.ModificheTabDTO;
import it.eng.tz.puglia.autpae.entity.composedKeys.ModificheTabPK;
import it.eng.tz.puglia.autpae.enumeratori.NomiTab;
import it.eng.tz.puglia.autpae.search.ModificheTabSearch;
import it.eng.tz.puglia.bean.PaginatedList;

public interface ModificheTabService {
	
	// BaseRepository
//	public List<ModificheTabDTO> select() throws Exception;
	public long count(ModificheTabSearch filter) throws Exception;
	public ModificheTabDTO find(ModificheTabPK pk) throws Exception;
//	public ModificheTabPK insert(ModificheTabDTO entity) throws Exception;
//	public int update(ModificheTabDTO entity) throws Exception;
//	public int delete(ModificheTabSearch search) throws Exception;
	public PaginatedList<ModificheTabDTO> search(ModificheTabSearch filter) throws Exception;
	
	// FullRepository
	public ModificheTabDTO getLast(NomiTab tab) throws Exception;
	public List<String> getInfoFromVersion(NomiTab tab, int versione) throws Exception;
	
	
}