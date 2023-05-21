package it.eng.tz.puglia.autpae.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.autpae.entity.ResponsabileDTO;
import it.eng.tz.puglia.autpae.repository.base.ResponsabileBaseRepository;
import it.eng.tz.puglia.autpae.search.ResponsabileSearch;
import it.eng.tz.puglia.autpae.service.interfacce.ResponsabileService;
import it.eng.tz.puglia.bean.PaginatedList;

@Service
public class ResponsabileServiceImpl implements ResponsabileService {

	private static final Logger log = LoggerFactory.getLogger(ResponsabileServiceImpl.class);
	//private static final String UNAUTHORIZED_EXCEPTION_MESSAGE = "User cannot access attachments for plan exclusion of this municipality";
	
	@Autowired	private ResponsabileBaseRepository repository;
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// BaseRepository
		
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public 		  List<ResponsabileDTO> select() 					  	   throws Exception { return repository.select(); 	    }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public  long 						 	count (ResponsabileSearch filter)  throws Exception { return repository.count (filter); }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public 			   ResponsabileDTO  find  (Long pk) 			  	   throws Exception { return repository.find  (pk); 	}
			  @Transactional(propagation=Propagation.MANDATORY, 	rollbackFor={Exception.class}, timeout=60000, readOnly=false) private Long 						 	insert(ResponsabileDTO entity) 	   throws Exception { return repository.insert(entity); }
			  @Transactional(propagation=Propagation.MANDATORY, 	rollbackFor={Exception.class}, timeout=60000, readOnly=false) private int 						 	update(ResponsabileDTO entity)	   throws Exception { return repository.update(entity); }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public  int 						 	delete(ResponsabileSearch search)  throws Exception { return repository.delete(search); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public  PaginatedList<ResponsabileDTO> search(ResponsabileSearch filter) throws Exception { return repository.search(filter); }
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// FullRepository
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// Altri Metodi
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor=Exception.class, timeout=60000)
	public Long inserisci(ResponsabileDTO responsabile) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		return this.insert(responsabile);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor=Exception.class, timeout=60000)
	public void salva(ResponsabileDTO responsabile) throws Exception {
		log.trace("Entrato nel service: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		this.update(responsabile);
	}
	
	
}