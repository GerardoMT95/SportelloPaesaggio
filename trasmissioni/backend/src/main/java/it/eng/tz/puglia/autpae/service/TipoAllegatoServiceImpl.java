package it.eng.tz.puglia.autpae.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.autpae.entity.TipoAllegatoDTO;
import it.eng.tz.puglia.autpae.enumeratori.TipoAllegato;
import it.eng.tz.puglia.autpae.repository.base.TipoAllegatoBaseRepository;
import it.eng.tz.puglia.autpae.search.TipoAllegatoSearch;
import it.eng.tz.puglia.autpae.service.interfacce.TipoAllegatoService;
import it.eng.tz.puglia.bean.PaginatedList;

@Service
public class TipoAllegatoServiceImpl implements TipoAllegatoService {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(TipoAllegatoServiceImpl.class);
	//private static final String UNAUTHORIZED_EXCEPTION_MESSAGE = "User cannot access attachments for plan exclusion of this municipality";
	
	@Autowired	private TipoAllegatoBaseRepository repository;
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// BaseRepository
		
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public 		  List<TipoAllegatoDTO> select() 					  	  throws Exception { return repository.select(); 	   }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public long 						 	count (TipoAllegatoSearch filter) throws Exception { return repository.count (filter); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public 			   TipoAllegatoDTO  find  (TipoAllegato pk) 		  throws Exception { return repository.find  (pk); 	   }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public TipoAllegato 					insert(TipoAllegatoDTO entity) 	  throws Exception { return repository.insert(entity); }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 						 	update(TipoAllegatoDTO entity)	  throws Exception { return repository.update(entity); }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 						 	delete(TipoAllegatoSearch search) throws Exception { return repository.delete(search); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public PaginatedList<TipoAllegatoDTO> search(TipoAllegatoSearch filter) throws Exception { return repository.search(filter); }
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// FullRepository
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// Altri Metodi
	
}