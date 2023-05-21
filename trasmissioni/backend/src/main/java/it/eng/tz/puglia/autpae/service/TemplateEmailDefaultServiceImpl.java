package it.eng.tz.puglia.autpae.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.autpae.entity.TemplateEmailDTO;
import it.eng.tz.puglia.autpae.enumeratori.TipoTemplate;
import it.eng.tz.puglia.autpae.repository.base.TemplateEmailDefaultBaseRepository;
import it.eng.tz.puglia.autpae.service.interfacce.TemplateEmailDefaultService;

@Service
public class TemplateEmailDefaultServiceImpl implements TemplateEmailDefaultService {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(TemplateEmailDefaultServiceImpl.class);
	
	@Autowired TemplateEmailDefaultBaseRepository repository;
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// BaseRepository
	
//	@Override @Transactional(propagation=Propagation.REQUIRED, 	rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public 		  List<TemplateEmailDTO> select() 					  		throws Exception { return repository.select(); 	   	 }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 	rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public long 						 	 count (TemplateEmailSearch filter) throws Exception { return repository.count (filter); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 	rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public 			   TemplateEmailDTO  find  (TipoTemplate pk) 			throws Exception { return repository.find  (pk); 	 }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 	rollbackFor={Exception.class}, timeout=60000, readOnly=false) public TipoTemplate 					 insert(TemplateEmailDTO entity) 	throws Exception { return repository.insert(entity); }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 	rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 						 	 update(TemplateEmailDTO entity)	throws Exception { return repository.update(entity); }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 	rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 						 	 delete(TemplateEmailSearch search) throws Exception { return repository.delete(search); }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 	rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public PaginatedList<TemplateEmailDTO> search(TemplateEmailSearch filter) throws Exception { return repository.search(filter); }

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// FullRepository
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// Altri Metodi
	
}