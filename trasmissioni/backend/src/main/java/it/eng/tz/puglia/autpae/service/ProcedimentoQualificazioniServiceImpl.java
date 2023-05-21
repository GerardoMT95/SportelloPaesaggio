package it.eng.tz.puglia.autpae.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.autpae.entity.ProcedimentoQualificazioniDTO;
import it.eng.tz.puglia.autpae.entity.composedKeys.ProcedimentoQualificazioniPK;
import it.eng.tz.puglia.autpae.repository.base.ProcedimentoQualificazioniBaseRepository;
import it.eng.tz.puglia.autpae.search.ProcedimentoQualificazioniSearch;
import it.eng.tz.puglia.autpae.service.interfacce.ProcedimentoQualificazioniService;
import it.eng.tz.puglia.bean.PaginatedList;

@Service
public class ProcedimentoQualificazioniServiceImpl implements ProcedimentoQualificazioniService {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(ProcedimentoQualificazioniServiceImpl.class);
	//private static final String UNAUTHORIZED_EXCEPTION_MESSAGE = "User cannot access attachments for plan exclusion of this municipality";
	
	@Autowired	private ProcedimentoQualificazioniBaseRepository repository;
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// BaseRepository
		
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public 		  List<ProcedimentoQualificazioniDTO> select() 					  					  throws Exception { return repository.select(); 	   }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public long 						 				  count (ProcedimentoQualificazioniSearch filter) throws Exception { return repository.count (filter); }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public 			   ProcedimentoQualificazioniDTO  find  (ProcedimentoQualificazioniPK pk) 		  throws Exception { return repository.find  (pk); 	   }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public ProcedimentoQualificazioniPK 				  insert(ProcedimentoQualificazioniDTO entity) 	  throws Exception { return repository.insert(entity); }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 						 				  update(ProcedimentoQualificazioniDTO entity)	  throws Exception { return repository.update(entity); }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 						 				  delete(ProcedimentoQualificazioniSearch search) throws Exception { return repository.delete(search); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public PaginatedList<ProcedimentoQualificazioniDTO> search(ProcedimentoQualificazioniSearch filter) throws Exception { return repository.search(filter); }
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// FullRepository
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// Altri Metodi
	
}