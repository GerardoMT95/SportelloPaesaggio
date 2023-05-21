package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.service.interfacce;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.RubricaAdminSearchDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.search.RubricaIstituzionaleSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.RubricaIstituzionaleDTO;
import it.eng.tz.puglia.bean.PaginatedList;

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