package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.StoricoAssegnamentoNewDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.StoricoAssegnamentoOldDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.repository.StoricoAssegnamentoBaseRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.search.StoricoAssegnamentoSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.service.interfacce.StoricoAssegnamentoService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.UserUtil;
import it.eng.tz.puglia.bean.PaginatedList;

@Service
public class StoricoAssegnamentoServiceImpl implements StoricoAssegnamentoService {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(StoricoAssegnamentoServiceImpl.class);
	
	@Autowired private StoricoAssegnamentoBaseRepository repository;
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// BaseRepository
		
//	@Override @Transactional(transactionManager="txRead",  propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public 		  List<StoricoAssegnamentoOldDTO> select() 					  			   throws Exception { return repository.select(); 	  	}
	@Override @Transactional(transactionManager="txRead",  propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public long 						 		   	  count (StoricoAssegnamentoSearch filter) throws Exception { return repository.count (filter); }
//	@Override @Transactional(transactionManager="txRead",  propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public 			   StoricoAssegnamentoOldDTO  find  (StoricoAssegnamentoPK 	   pk) 	   throws Exception { return repository.find  (pk); 	}
	@Override @Transactional(transactionManager="txWrite", propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public Long 	 			  				   	  insert(StoricoAssegnamentoOldDTO entity) throws Exception { return repository.insert(entity); }
//	@Override @Transactional(transactionManager="txWrite", propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 						 		   	  update(StoricoAssegnamentoOldDTO entity) throws Exception { return repository.update(entity); }
//	@Override @Transactional(transactionManager="txWrite", propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 						 		   	  delete(StoricoAssegnamentoSearch search) throws Exception { return repository.delete(search); }
	@Override @Transactional(transactionManager="txRead",  propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public PaginatedList<StoricoAssegnamentoOldDTO> search(StoricoAssegnamentoSearch filter) throws Exception { return repository.search(filter); }
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// FullRepository
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// Altri Metodi

	@Autowired private UserUtil userUtil;
	
	
	@Override
	@Transactional(transactionManager="txRead", propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true )
	public List<StoricoAssegnamentoOldDTO> getStoricoAssegnamento(UUID idFascicolo) throws Exception {
		
		StoricoAssegnamentoSearch search = new StoricoAssegnamentoSearch();
		search.setIdFascicolo(idFascicolo);
		search.setIdOrganizzazione(userUtil.getIntegerId());
		
		List<StoricoAssegnamentoOldDTO> lista = this.search(search).getList();
		
		return lista;
	}
	
	@Override
	public List<StoricoAssegnamentoNewDTO> creaNewDTO(List<StoricoAssegnamentoOldDTO> storicoOld) {

		if (storicoOld.isEmpty()) return null;
		
		List<StoricoAssegnamentoNewDTO> listaNew = new ArrayList<StoricoAssegnamentoNewDTO>(storicoOld.size()/2);
		
		Collections.sort(storicoOld, new Comparator<StoricoAssegnamentoOldDTO>() {	// ordino in base alla "data operazione"
			public int compare(StoricoAssegnamentoOldDTO o1, StoricoAssegnamentoOldDTO o2) {
				return o1.getDataOperazione().compareTo(o2.getDataOperazione());
			}
		});
		
		for (int i=0; i<storicoOld.size(); i++) {
			if(i==0 || i%2==0) {
				StoricoAssegnamentoOldDTO st1 = storicoOld.get(i);
				StoricoAssegnamentoOldDTO st2 = new StoricoAssegnamentoOldDTO();
				if(i+1 < storicoOld.size())
					st2 = storicoOld.get(i+1);
				listaNew.add(new StoricoAssegnamentoNewDTO(st1, st2));
			}
		}
		return listaNew;
	}
	
	
}