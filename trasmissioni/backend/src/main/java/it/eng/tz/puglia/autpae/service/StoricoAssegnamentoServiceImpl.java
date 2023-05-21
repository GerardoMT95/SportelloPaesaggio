package it.eng.tz.puglia.autpae.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.autpae.entity.StoricoAssegnamentoDTO;
import it.eng.tz.puglia.autpae.repository.base.StoricoAssegnamentoBaseRepository;
import it.eng.tz.puglia.autpae.search.StoricoAssegnamentoSearch;
import it.eng.tz.puglia.autpae.service.interfacce.StoricoAssegnamentoService;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.profileManager.feign.UserUtil;

@Service
public class StoricoAssegnamentoServiceImpl implements StoricoAssegnamentoService {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(StoricoAssegnamentoServiceImpl.class);
	//private static final String UNAUTHORIZED_EXCEPTION_MESSAGE = "User cannot access attachments for plan exclusion of this municipality";
	
	@Autowired private StoricoAssegnamentoBaseRepository repository;
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// BaseRepository
		
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public 		  List<StoricoAssegnamentoDTO> select() 					  			throws Exception { return repository.select(); 	  	 }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public long 						 		   count (StoricoAssegnamentoSearch filter) throws Exception { return repository.count (filter); }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public 			   StoricoAssegnamentoDTO  find  (StoricoAssegnamentoPK pk) 		throws Exception { return repository.find  (pk); 	 }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public Long 	 			  				   insert(StoricoAssegnamentoDTO entity) 	throws Exception { return repository.insert(entity); }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 						 		   update(StoricoAssegnamentoDTO entity)	throws Exception { return repository.update(entity); }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 						 		   delete(StoricoAssegnamentoSearch search) throws Exception { return repository.delete(search); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public PaginatedList<StoricoAssegnamentoDTO> search(StoricoAssegnamentoSearch filter) throws Exception { return repository.search(filter); }
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// FullRepository
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// Altri Metodi

	@Autowired private UserUtil userUtil;
	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true )
	public List<StoricoAssegnamentoDTO> getStoricoAssegnamento(long idFascicolo) throws Exception {
		
		StoricoAssegnamentoSearch search = new StoricoAssegnamentoSearch();
		search.setIdFascicolo(idFascicolo);
		search.setIdOrganizzazione(userUtil.getIntegerId());
		
		List<StoricoAssegnamentoDTO> lista = this.search(search).getList();
		
		// non so se c'è bisogno di fare qualche elaborazione sulla lista ...
		
		return lista;
	}
	
	
}