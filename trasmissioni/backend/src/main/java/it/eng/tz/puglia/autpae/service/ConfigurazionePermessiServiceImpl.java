package it.eng.tz.puglia.autpae.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.autpae.entity.ConfigurazionePermessiDTO;
import it.eng.tz.puglia.autpae.repository.ConfigurazionePermessiFullRepository;
import it.eng.tz.puglia.autpae.service.interfacce.ConfigurazionePermessiService;

@Service
public class ConfigurazionePermessiServiceImpl implements ConfigurazionePermessiService {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(ConfigurazionePermessiServiceImpl.class);
	//private static final String UNAUTHORIZED_EXCEPTION_MESSAGE = "User cannot access attachments for plan exclusion of this municipality";
	
	@Autowired	private ConfigurazionePermessiFullRepository repository;
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// BaseRepository
		
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public 		  List<ConfigurazionePermessiDTO> select() 					  				  throws Exception { return repository.select(); 	   }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public long 						 			  count (ConfigurazionePermessiSearch filter) throws Exception { return repository.count (filter); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public 			   ConfigurazionePermessiDTO  find  (String pk) 			  			  throws Exception { return repository.find  (pk); 	   }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public String 						 		  insert(ConfigurazionePermessiDTO entity) 	  throws Exception { return repository.insert(entity); }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 						 			  update(ConfigurazionePermessiDTO entity)	  throws Exception { return repository.update(entity); }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 						 			  delete(ConfigurazionePermessiSearch search) throws Exception { return repository.delete(search); }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public PaginatedList<ConfigurazionePermessiDTO> search(ConfigurazionePermessiSearch filter) throws Exception { return repository.search(filter); }

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// FullRepository
	
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public void		insertAll(List<ConfigurazionePermessiDTO> listaPermessi) throws Exception {
																																   		 repository.insertAll(listaPermessi);												  }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 		deleteAll() 											 throws Exception {
																																  return repository.deleteAll();															  }
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// Altri Metodi

}