package it.eng.tz.puglia.autpae.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.autpae.entity.ConfigurazioneCampionamentoDTO;
import it.eng.tz.puglia.autpae.repository.base.ConfigurazioneCampionamentoBaseRepository;
import it.eng.tz.puglia.autpae.service.interfacce.ConfigurazioneCampionamentoService;

@Service
public class ConfigurazioneCampionamentoServiceImpl implements ConfigurazioneCampionamentoService {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(ConfigurazioneCampionamentoServiceImpl.class);
	//private static final String UNAUTHORIZED_EXCEPTION_MESSAGE = "User cannot access attachments for plan exclusion of this municipality";
	
	@Autowired	private ConfigurazioneCampionamentoBaseRepository repository;
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// BaseRepository
		
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public 		  List<ConfigurazioneCampionamentoDTO> select() 					  					throws Exception { return repository.select(); 	   	 }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public long 						 				   count (ConfigurazioneCampionamentoSearch filter) throws Exception { return repository.count (filter); }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public 			   ConfigurazioneCampionamentoDTO  find  (Long pk) 			  						throws Exception { return repository.find  (pk); 	 }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public Long 						 				   insert(ConfigurazioneCampionamentoDTO entity) 	throws Exception { return repository.insert(entity); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 						 				   update(ConfigurazioneCampionamentoDTO entity)	throws Exception { return repository.update(entity); }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 						 				   delete(ConfigurazioneCampionamentoSearch search) throws Exception { return repository.delete(search); }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public PaginatedList<ConfigurazioneCampionamentoDTO> search(ConfigurazioneCampionamentoSearch filter) throws Exception { return repository.search(filter); }
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// FullRepository
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// Altri Metodi
	
}