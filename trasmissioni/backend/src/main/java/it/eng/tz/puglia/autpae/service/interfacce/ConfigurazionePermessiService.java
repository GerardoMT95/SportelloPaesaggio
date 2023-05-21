package it.eng.tz.puglia.autpae.service.interfacce;

import java.util.List;

import it.eng.tz.puglia.autpae.entity.ConfigurazionePermessiDTO;

public interface ConfigurazionePermessiService {

	// BaseRepository
	public List<ConfigurazionePermessiDTO> select() throws Exception;
//	public long count(ConfigurazionePermessiSearch filter) throws Exception;
	public ConfigurazionePermessiDTO find(String pk) throws Exception;
//	public String insert(ConfigurazionePermessiDTO entity) throws Exception;
//	public int update(ConfigurazionePermessiDTO entity) throws Exception;
//	public int delete(ConfigurazionePermessiSearch filters) throws Exception;
//	public PaginatedList<ConfigurazionePermessiDTO> search(ConfigurazionePermessiSearch filters) throws Exception;
	
	// FullRepository
	public void insertAll(List<ConfigurazionePermessiDTO> listaPermessi) throws Exception;
	public int  deleteAll() throws Exception;
	
}