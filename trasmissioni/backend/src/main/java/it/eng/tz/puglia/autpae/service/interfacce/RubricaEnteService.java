package it.eng.tz.puglia.autpae.service.interfacce;

import it.eng.tz.puglia.autpae.search.RubricaEnteSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.remote.dto.RubricaEnteDTO;

public interface RubricaEnteService {
	
//	public 			List<RubricaEnteDTO>  select 	 () 						throws Exception;
//	public 				 RubricaEnteDTO   find   	 (RubricaEntePK  	pk	  ) throws Exception;
//	public long 						  count  	 (RubricaEnteSearch filter) throws Exception;
	public long 						  insert 	 (RubricaEnteDTO    entity) throws Exception;
	public int  						  update 	 (RubricaEnteDTO    entity) throws Exception;
	public int  						  delete 	 (RubricaEnteDTO    entity) throws Exception;
	public PaginatedList<RubricaEnteDTO>  search 	 (RubricaEnteSearch filter) throws Exception;
	
}