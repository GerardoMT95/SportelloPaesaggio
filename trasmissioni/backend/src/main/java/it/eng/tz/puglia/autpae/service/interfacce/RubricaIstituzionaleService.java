package it.eng.tz.puglia.autpae.service.interfacce;

import it.eng.tz.puglia.autpae.dto.BE_to_FE.RubricaAdminSearchDTO;
import it.eng.tz.puglia.autpae.search.RubricaIstituzionaleSearch;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.remote.dto.RubricaIstituzionaleDTO;

public interface RubricaIstituzionaleService {
	
//	public 			List<RubricaIstituzionaleDTO> select 	 () 						   		 throws Exception;
//	public 				 RubricaIstituzionaleDTO  find   	 (RubricaIstituzionalePK 	 pk	   ) throws Exception;
//	public long 						 		  count  	 (RubricaIstituzionaleSearch filter) throws Exception;
//	public long 						 		  insert 	 (RubricaIstituzionaleDTO    entity) throws Exception;
	public int  						 		  update 	 (RubricaIstituzionaleDTO    entity) throws Exception;
//	public int  								  delete 	 (RubricaIstituzionaleDTO    entity) throws Exception;
	public PaginatedList<RubricaIstituzionaleDTO> search 	 (RubricaIstituzionaleSearch filter) throws Exception;
	public PaginatedList<RubricaAdminSearchDTO>   adminSearch(RubricaAdminSearchDTO 	 entity) throws Exception;
		
}