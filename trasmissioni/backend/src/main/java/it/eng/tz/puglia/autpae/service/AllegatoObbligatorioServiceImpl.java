package it.eng.tz.puglia.autpae.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.autpae.entity.AllegatoObbligatorioDTO;
import it.eng.tz.puglia.autpae.entity.composedKeys.AllegatoObbligatorioPK;
import it.eng.tz.puglia.autpae.repository.base.AllegatoObbligatorioBaseRepository;
import it.eng.tz.puglia.autpae.search.AllegatoObbligatorioSearch;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoObbligatorioService;
import it.eng.tz.puglia.bean.PaginatedList;

@Service
public class AllegatoObbligatorioServiceImpl implements AllegatoObbligatorioService {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(AllegatoObbligatorioServiceImpl.class);
	//private static final String UNAUTHORIZED_EXCEPTION_MESSAGE = "User cannot access attachments for plan exclusion of this municipality";
	
	@Autowired	private AllegatoObbligatorioBaseRepository repository;
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// BaseRepository
		
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, 											  timeout=60000, readOnly=true ) public 		 List<AllegatoObbligatorioDTO> select() 					  			 throws Exception { return repository.select(); 	  }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, 											  timeout=60000, readOnly=true ) public long 						 		   count (AllegatoObbligatorioSearch filter) throws Exception { return repository.count (filter); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, noRollbackFor={DataAccessException.class}, timeout=60000, readOnly=true ) public 			  AllegatoObbligatorioDTO  find  (AllegatoObbligatorioPK pk) 		 throws Exception { return repository.find  (pk); 	  }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, 											  timeout=60000, readOnly=false) public AllegatoObbligatorioPK 	 			   insert(AllegatoObbligatorioDTO entity) 	 throws Exception { return repository.insert(entity); }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, 											  timeout=60000, readOnly=false) public int 						 		   update(AllegatoObbligatorioDTO entity)	 throws Exception { return repository.update(entity); }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, 											  timeout=60000, readOnly=false) public int 						 		   delete(AllegatoObbligatorioSearch search) throws Exception { return repository.delete(search); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, 											  timeout=60000, readOnly=true ) public PaginatedList<AllegatoObbligatorioDTO> search(AllegatoObbligatorioSearch filter) throws Exception { return repository.search(filter); }
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// FullRepository
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// Altri Metodi
	
}