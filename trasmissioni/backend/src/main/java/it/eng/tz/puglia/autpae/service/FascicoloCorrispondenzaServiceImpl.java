package it.eng.tz.puglia.autpae.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.autpae.entity.FascicoloCorrispondenzaDTO;
import it.eng.tz.puglia.autpae.repository.base.FascicoloCorrispondenzaBaseRepository;
import it.eng.tz.puglia.autpae.search.FascicoloCorrispondenzaSearch;
import it.eng.tz.puglia.autpae.service.interfacce.FascicoloCorrispondenzaService;
import it.eng.tz.puglia.bean.PaginatedList;

@Service
public class FascicoloCorrispondenzaServiceImpl implements FascicoloCorrispondenzaService {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(FascicoloCorrispondenzaServiceImpl.class);
	//private static final String UNAUTHORIZED_EXCEPTION_MESSAGE = "User cannot access attachments for plan exclusion of this municipality";
	
	@Autowired	private FascicoloCorrispondenzaBaseRepository repository;
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// BaseRepository
		
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public 		  List<FascicoloCorrispondenzaDTO> select() 					  				throws Exception { return repository.select(); 	   	 }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public long 						 			   count (FascicoloCorrispondenzaSearch filter) throws Exception { return repository.count (filter); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public 			   FascicoloCorrispondenzaDTO  find  (Long pk) 			  					throws Exception { return repository.find  (pk); 	 }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public Long 						 			   insert(FascicoloCorrispondenzaDTO entity) 	throws Exception { return repository.insert(entity); }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 						 			   update(FascicoloCorrispondenzaDTO entity)	throws Exception { return repository.update(entity); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 						 			   delete(FascicoloCorrispondenzaSearch search) throws Exception { return repository.delete(search); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public PaginatedList<FascicoloCorrispondenzaDTO> search(FascicoloCorrispondenzaSearch filter) throws Exception { return repository.search(filter); }
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// FullRepository
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// Altri Metodi
	
}