package it.eng.tz.puglia.autpae.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.autpae.entity.AllegatoEnteDTO;
import it.eng.tz.puglia.autpae.entity.composedKeys.AllegatoEntePK;
import it.eng.tz.puglia.autpae.repository.base.AllegatoEnteBaseRepository;
import it.eng.tz.puglia.autpae.search.AllegatoEnteSearch;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoEnteService;
import it.eng.tz.puglia.bean.PaginatedList;

@Service
public class AllegatoEnteServiceImpl implements AllegatoEnteService {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(AllegatoEnteServiceImpl.class);
	//private static final String UNAUTHORIZED_EXCEPTION_MESSAGE = "User cannot access attachments for plan exclusion of this municipality";
	
	@Autowired	private AllegatoEnteBaseRepository repository;
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// BaseRepository
		
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public 		  List<AllegatoEnteDTO> select() 					  	  throws Exception { return repository.select(); 	   }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public long 						 	count (AllegatoEnteSearch filter) throws Exception { return repository.count (filter); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public 			   AllegatoEnteDTO  find  (AllegatoEntePK pk) 		  throws Exception { return repository.find  (pk); 	   }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public AllegatoEntePK 				insert(AllegatoEnteDTO entity) 	  throws Exception { return repository.insert(entity); }
	@Override 
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class, timeout=60000, readOnly=false) 
	public void insert(List<AllegatoEnteDTO> entity) throws Exception 
	{ 
	    repository.insert(entity); 
	}
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 						 	update(AllegatoEnteDTO entity)	  throws Exception { return repository.update(entity); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 						 	delete(AllegatoEnteSearch search) throws Exception { return repository.delete(search); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public PaginatedList<AllegatoEnteDTO> search(AllegatoEnteSearch filter) throws Exception { return repository.search(filter); }
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// FullRepository
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// Altri Metodi
	
}