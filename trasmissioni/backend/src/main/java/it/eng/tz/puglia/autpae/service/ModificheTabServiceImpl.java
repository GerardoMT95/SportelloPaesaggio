package it.eng.tz.puglia.autpae.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.autpae.entity.ModificheTabDTO;
import it.eng.tz.puglia.autpae.entity.composedKeys.ModificheTabPK;
import it.eng.tz.puglia.autpae.enumeratori.NomiTab;
import it.eng.tz.puglia.autpae.repository.ModificheTabFullRepository;
import it.eng.tz.puglia.autpae.search.ModificheTabSearch;
import it.eng.tz.puglia.autpae.service.interfacce.ModificheTabService;
import it.eng.tz.puglia.bean.PaginatedList;

@Service
public class ModificheTabServiceImpl implements ModificheTabService {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(ModificheTabServiceImpl.class);
	//private static final String UNAUTHORIZED_EXCEPTION_MESSAGE = "User cannot access attachments for plan exclusion of this municipality";
	
	@Autowired	private ModificheTabFullRepository repository;
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// BaseRepository
	
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public 		  List<ModificheTabDTO> select() 					  	  throws Exception { return repository.select(); 	   }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public long 						 	count (ModificheTabSearch filter) throws Exception { return repository.count (filter); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public 			   ModificheTabDTO  find  (ModificheTabPK pk) 		  throws Exception { return repository.find  (pk); 	   }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public ModificheTabPK 				insert(ModificheTabDTO entity) 	  throws Exception { return repository.insert(entity); }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 						 	update(ModificheTabDTO entity)	  throws Exception { return repository.update(entity); }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 						 	delete(ModificheTabSearch search) throws Exception { return repository.delete(search); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public PaginatedList<ModificheTabDTO> search(ModificheTabSearch filter) throws Exception { return repository.search(filter); }
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// FullRepository
	
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public ModificheTabDTO  	getLast		   	  (NomiTab tab)			  	  throws Exception {
		  																														  return repository		   .getLast		   	  (		   tab);  			  }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public List<String>  	  	getInfoFromVersion(NomiTab tab, int versione) throws Exception {
																																  return repository		   .getInfoFromVersion(		   tab,	 	versione);}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// Altri Metodi
	
}