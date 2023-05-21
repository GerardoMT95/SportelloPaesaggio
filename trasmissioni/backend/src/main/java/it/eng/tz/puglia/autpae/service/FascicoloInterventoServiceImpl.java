package it.eng.tz.puglia.autpae.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.autpae.entity.FascicoloInterventoDTO;
import it.eng.tz.puglia.autpae.entity.composedKeys.FascicoloInterventoPK;
import it.eng.tz.puglia.autpae.repository.FascicoloInterventoFullRepository;
import it.eng.tz.puglia.autpae.search.FascicoloInterventoSearch;
import it.eng.tz.puglia.autpae.service.interfacce.FascicoloInterventoService;
import it.eng.tz.puglia.bean.PaginatedList;

@Service
public class FascicoloInterventoServiceImpl implements FascicoloInterventoService {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(FascicoloInterventoServiceImpl.class);
	//private static final String UNAUTHORIZED_EXCEPTION_MESSAGE = "User cannot access attachments for plan exclusion of this municipality";
	
	@Autowired	private FascicoloInterventoFullRepository repository;
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// BaseRepository
		
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public 		  List<FascicoloInterventoDTO> select() 					  			throws Exception { return repository.select(); 	   	 }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public long 						 		   count (FascicoloInterventoSearch filter) throws Exception { return repository.count (filter); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public 			   FascicoloInterventoDTO  find  (FascicoloInterventoPK pk) 		throws Exception { return repository.find  (pk); 	 }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public FascicoloInterventoPK 				   insert(FascicoloInterventoDTO entity) 	throws Exception { return repository.insert(entity); }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 						 		   update(FascicoloInterventoDTO entity)	throws Exception { return repository.update(entity); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 						 		   delete(FascicoloInterventoSearch search) throws Exception { return repository.delete(search); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public PaginatedList<FascicoloInterventoDTO> search(FascicoloInterventoSearch filter) throws Exception { return repository.search(filter); }

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// FullRepository
	
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 		insertMultiple(List<Long> listaIdTipiQualificazioni, Long idFascicolo) throws Exception {
																																  return repository.insertMultiple(			  listaIdTipiQualificazioni, 	  idFascicolo);					}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// Altri Metodi
	
}