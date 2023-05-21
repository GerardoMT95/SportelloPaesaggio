package it.eng.tz.puglia.autpae.service.interfacce;

import java.util.List;

import it.eng.tz.puglia.autpae.entity.ConfigurazioneCampionamentoDTO;

public interface ConfigurazioneCampionamentoService {
	
	// BaseRepository
	List<ConfigurazioneCampionamentoDTO> select() throws Exception;
//	public long count(ConfigurazioneCampionamentoSearch filter) throws Exception;
//	public ConfigurazioneCampionamentoDTO find(Long pk) throws Exception;
//	public Long insert(ConfigurazioneCampionamentoDTO entity) throws Exception;
	public int update(ConfigurazioneCampionamentoDTO entity) throws Exception;
//	public int delete(ConfigurazioneCampionamentoSearch filters) throws Exception;
//	public PaginatedList<ConfigurazioneCampionamentoDTO> search(ConfigurazioneCampionamentoSearch filters) throws Exception;
	
}