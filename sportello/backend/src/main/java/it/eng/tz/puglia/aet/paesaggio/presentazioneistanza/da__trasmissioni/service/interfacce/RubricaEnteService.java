package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.service.interfacce;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.search.RubricaEnteSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.RubricaEnteDTO;
import it.eng.tz.puglia.bean.PaginatedList;

public interface RubricaEnteService {
	
//	public 			List<RubricaEnteDTO>  select 	 () 						throws Exception;
//	public 				 RubricaEnteDTO   find   	 (RubricaEntePK  	pk	  ) throws Exception;
//	public long 						  count  	 (RubricaEnteSearch filter) throws Exception;
	public long 						  insert 	 (RubricaEnteDTO    entity) throws Exception;
	public int  						  update 	 (RubricaEnteDTO    entity) throws Exception;
	public int  						  delete 	 (RubricaEnteDTO    entity) throws Exception;
	public PaginatedList<RubricaEnteDTO>  search 	 (RubricaEnteSearch filter) throws Exception;
	
}