package it.eng.tz.puglia.autpae.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.autpae.entity.AllegatoFascicoloDTO;
import it.eng.tz.puglia.autpae.entity.composedKeys.AllegatoFascicoloPK;
import it.eng.tz.puglia.autpae.enumeratori.TipoAllegato;
import it.eng.tz.puglia.autpae.repository.AllegatoFascicoloFullRepository;
import it.eng.tz.puglia.autpae.search.AllegatoFascicoloSearch;
import it.eng.tz.puglia.autpae.service.interfacce.AllegatoFascicoloService;
import it.eng.tz.puglia.bean.PaginatedList;

@Service
public class AllegatoFascicoloServiceImpl implements AllegatoFascicoloService {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(AllegatoFascicoloServiceImpl.class);
	//private static final String UNAUTHORIZED_EXCEPTION_MESSAGE = "User cannot access attachments for plan exclusion of this municipality";
	
	@Autowired	private AllegatoFascicoloFullRepository repository;
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// BaseRepository
		
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public 		  List<AllegatoFascicoloDTO> select() 					  			throws Exception { return repository.select(); 	   	 }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public long 						 		 count (AllegatoFascicoloSearch filter) throws Exception { return repository.count (filter); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public 			   AllegatoFascicoloDTO  find  (AllegatoFascicoloPK pk) 		throws Exception { return repository.find  (pk); 	 }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public AllegatoFascicoloPK				 insert(AllegatoFascicoloDTO entity) 	throws Exception { return repository.insert(entity); }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 						 		 update(AllegatoFascicoloDTO entity)	throws Exception { return repository.update(entity); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 						 		 delete(AllegatoFascicoloSearch search) throws Exception { return repository.delete(search); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public PaginatedList<AllegatoFascicoloDTO> search(AllegatoFascicoloSearch filter) throws Exception { return repository.search(filter); }
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// FullRepository

	@Override @Transactional(propagation=Propagation.MANDATORY, 	rollbackFor={Exception.class}, timeout=60000, readOnly=false) 
	public int cambiaTipo(long idFascicolo, long idAllegato, TipoAllegato tipoPrecedente, TipoAllegato nuovoTipo) throws Exception {
		return repository.cambiaTipo(idFascicolo, idAllegato, tipoPrecedente, nuovoTipo);
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// Altri Metodi
	
}