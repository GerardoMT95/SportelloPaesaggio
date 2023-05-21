package it.eng.tz.puglia.autpae.service.interfacce;

import java.util.List;

import it.eng.tz.puglia.autpae.dto.ConfigurazioneCasellaPostaleDTO;
import it.eng.tz.puglia.autpae.search.ConfigurazioneCasellaPostaleSearch;
import it.eng.tz.puglia.bean.PaginatedList;

public interface ConfigurazioneCasellaPostaleService {

	// BaseRepository
	public List<ConfigurazioneCasellaPostaleDTO> select() throws Exception;
	public long count(ConfigurazioneCasellaPostaleSearch filter) throws Exception;
	public ConfigurazioneCasellaPostaleDTO find(String pk) throws Exception;
	public String insert(ConfigurazioneCasellaPostaleDTO entity) throws Exception;
	public int update(ConfigurazioneCasellaPostaleDTO entity) throws Exception;
	public int delete(ConfigurazioneCasellaPostaleSearch entity) throws Exception;
	public PaginatedList<ConfigurazioneCasellaPostaleDTO> search(ConfigurazioneCasellaPostaleSearch bean) throws Exception;
	void insertOrUpdate(ConfigurazioneCasellaPostaleDTO ccpDTO) throws Exception;
	List<ConfigurazioneCasellaPostaleDTO> getCaselleDaAttivare() throws Exception;
	List<ConfigurazioneCasellaPostaleDTO> getCaselleDaDisattivare() throws Exception;
}
