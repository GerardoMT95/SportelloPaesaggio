package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.ValoriAssegnamentoOldDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.repository.ValoriAssegnamentoBaseRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.search.ValoriAssegnamentoSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.service.interfacce.ValoriAssegnamentoService;
import it.eng.tz.puglia.bean.PaginatedList;

@Service
public class ValoriAssegnamentoServiceImpl implements ValoriAssegnamentoService {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(ValoriAssegnamentoServiceImpl.class);
	
	@Autowired private ValoriAssegnamentoBaseRepository repository;
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// BaseRepository
		
//	@Override @Transactional(transactionManager="txRead" , propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public 		  List<ValoriAssegnamentoOldDTO> select() 					  			 throws Exception { return repository.select(); 	  }
	@Override @Transactional(transactionManager="txRead" , propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public long 						 		  	 count (ValoriAssegnamentoSearch filter) throws Exception { return repository.count (filter); }
//	@Override @Transactional(transactionManager="txRead" , propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public 			   ValoriAssegnamentoOldDTO  find  (ValoriAssegnamentoPK pk) 		 throws Exception { return repository.find  (pk); 	  }
	@Override @Transactional(transactionManager="txWrite", propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public Integer 	 			  			  	 insert(ValoriAssegnamentoOldDTO entity) throws Exception { return repository.insert(entity); }
	@Override @Transactional(transactionManager="txWrite", propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 						 		  	 update(ValoriAssegnamentoOldDTO entity) throws Exception { return repository.update(entity); }
	@Override @Transactional(transactionManager="txWrite", propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 						 		  	 delete(ValoriAssegnamentoSearch search) throws Exception { return repository.delete(search); }
	@Override @Transactional(transactionManager="txRead" , propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public PaginatedList<ValoriAssegnamentoOldDTO> search(ValoriAssegnamentoSearch filter) throws Exception { return repository.search(filter); }
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// FullRepository
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// Altri Metodi
	
}