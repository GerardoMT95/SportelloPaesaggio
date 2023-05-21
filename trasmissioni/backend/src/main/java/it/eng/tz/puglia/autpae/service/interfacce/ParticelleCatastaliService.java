package it.eng.tz.puglia.autpae.service.interfacce;

import java.util.List;

import it.eng.tz.puglia.autpae.entity.ParticelleCatastaliDTO;
import it.eng.tz.puglia.autpae.entity.composedKeys.ParticelleCatastaliPK;
import it.eng.tz.puglia.autpae.search.ParticelleCatastaliSearch;
import it.eng.tz.puglia.bean.PaginatedList;

public interface ParticelleCatastaliService {
	
	//BaseRepository
	public ParticelleCatastaliPK 				 insert					   (ParticelleCatastaliDTO entity);
	public int 									 delete					   (ParticelleCatastaliSearch filter) throws Exception;
	public PaginatedList<ParticelleCatastaliDTO> search					   (ParticelleCatastaliSearch filter) throws Exception;
	
	//FullRepository
	public List<ParticelleCatastaliDTO> 		 select					   (Long praticaId, long comuneId);
	public int 									 deleteByKeyLoc			   (Long id, long comuneId);
	public int 									 insertMultiple			   (List<ParticelleCatastaliDTO> particella, long idFascicolo) throws Exception;
	public List<String> 						 selectComuniIdInteressati (long idFascicolo) throws Exception;
	
}