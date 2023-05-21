package it.eng.tz.puglia.autpae.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.autpae.dto.DestinatarioTemplateDTO;
import it.eng.tz.puglia.autpae.repository.base.TemplateDestinatarioDefaultBaseRepository;
import it.eng.tz.puglia.autpae.search.TemplateDestinatarioSearch;
import it.eng.tz.puglia.autpae.service.interfacce.TemplateDestinatarioDefaultService;
import it.eng.tz.puglia.bean.PaginatedList;

@Service
public class TemplateDestinatarioDefaultServiceImpl implements TemplateDestinatarioDefaultService {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(TemplateDestinatarioDefaultServiceImpl.class);
	//private static final String UNAUTHORIZED_EXCEPTION_MESSAGE = "User cannot access attachments for plan exclusion of this municipality";
	
	@Autowired	private TemplateDestinatarioDefaultBaseRepository repository;
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// BaseRepository
		
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public 		  List<TemplateDestinatarioDTO> select() 					  			  throws Exception { return repository.select(); 	   }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public long 						 			count (TemplateDestinatarioSearch filter) throws Exception { return repository.count (filter); }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public 			   TemplateDestinatarioDTO  find  (Long pk) 			  			  throws Exception { return repository.find  (pk); 	   }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public Long 						 			insert(TemplateDestinatarioDTO entity) 	  throws Exception { return repository.insert(entity); }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 						 			update(TemplateDestinatarioDTO entity)	  throws Exception { return repository.update(entity); }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 						 			delete(TemplateDestinatarioSearch search) throws Exception { return repository.delete(search); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public PaginatedList<DestinatarioTemplateDTO> search(TemplateDestinatarioSearch filter) throws Exception { return repository.search(filter); }
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// FullRepository
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// Altri Metodi
	
}