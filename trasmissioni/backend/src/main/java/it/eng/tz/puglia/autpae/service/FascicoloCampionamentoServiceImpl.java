package it.eng.tz.puglia.autpae.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.autpae.entity.FascicoloCampionamentoDTO;
import it.eng.tz.puglia.autpae.entity.composedKeys.FascicoloCampionamentoPK;
import it.eng.tz.puglia.autpae.repository.base.FascicoloCampionamentoBaseRepository;
import it.eng.tz.puglia.autpae.search.FascicoloCampionamentoSearch;
import it.eng.tz.puglia.autpae.service.interfacce.FascicoloCampionamentoService;
import it.eng.tz.puglia.bean.PaginatedList;

@Service
public class FascicoloCampionamentoServiceImpl implements FascicoloCampionamentoService {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(FascicoloCampionamentoServiceImpl.class);
	//private static final String UNAUTHORIZED_EXCEPTION_MESSAGE = "User cannot access attachments for plan exclusion of this municipality";
	
	@Autowired	private FascicoloCampionamentoBaseRepository repository;
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// BaseRepository
		
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public 		  List<FascicoloCampionamentoDTO> select() 					  				  throws Exception { return repository.select(); 	   }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public long 						 			  count (FascicoloCampionamentoSearch filter) throws Exception { return repository.count (filter); }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public 			   FascicoloCampionamentoDTO  find  (FascicoloCampionamentoPK pk) 		  throws Exception { return repository.find  (pk); 	   }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public FascicoloCampionamentoPK 				  insert(FascicoloCampionamentoDTO entity) 	  throws Exception { return repository.insert(entity); }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 						 			  update(FascicoloCampionamentoDTO entity)	  throws Exception { return repository.update(entity); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 						 			  delete(FascicoloCampionamentoSearch search) throws Exception { return repository.delete(search); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public PaginatedList<FascicoloCampionamentoDTO> search(FascicoloCampionamentoSearch filter) throws Exception { return repository.search(filter); }
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// FullRepository
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// Altri Metodi
	
}