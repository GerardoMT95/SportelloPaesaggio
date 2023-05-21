package it.eng.tz.puglia.autpae.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.autpae.entity.TipiEQualificazioniDTO;
import it.eng.tz.puglia.autpae.enumeratori.TipoProcedimento;
import it.eng.tz.puglia.autpae.repository.TipiEQualificazioniFullRepository;
import it.eng.tz.puglia.autpae.search.TipiEQualificazioniSearch;
import it.eng.tz.puglia.autpae.service.interfacce.TipiEQualificazioniService;
import it.eng.tz.puglia.bean.PaginatedList;

@Service
public class TipiEQualificazioniServiceImpl implements TipiEQualificazioniService {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(TipiEQualificazioniServiceImpl.class);
	//private static final String UNAUTHORIZED_EXCEPTION_MESSAGE = "User cannot access attachments for plan exclusion of this municipality";
	
	@Autowired	private TipiEQualificazioniFullRepository repository;
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// BaseRepository
		
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public 		  List<TipiEQualificazioniDTO> select() 					  			throws Exception { return repository.select(); 	   	 }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public long 						 		   count (TipiEQualificazioniSearch filter) throws Exception { return repository.count (filter); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public 			   TipiEQualificazioniDTO  find  (Long pk) 			  				throws Exception { return repository.find  (pk); 	 }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public Long 						 		   insert(TipiEQualificazioniDTO entity) 	throws Exception { return repository.insert(entity); }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 						 		   update(TipiEQualificazioniDTO entity)	throws Exception { return repository.update(entity); }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=false) public int 						 		   delete(TipiEQualificazioniSearch search) throws Exception { return repository.delete(search); }
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public PaginatedList<TipiEQualificazioniDTO> search(TipiEQualificazioniSearch filter) throws Exception { return repository.search(filter); }
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// FullRepository

/*	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public List<TipiEQualificazioniDTO> listByTipo					(String tipo) 						throws Exception {
	  																															  return 				   repository.listByTipo					(		tipo);				 					 	 } */
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public List<TipiEQualificazioniDTO> selectByCodiceTipoProcedimento(TipoProcedimento tipoProcedimento,Date dataRiferimentoConfigurazione,String gruppoOwner) throws Exception {
																																  return 				   repository.selectByCodiceTipoProcedimento(tipoProcedimento,null,dataRiferimentoConfigurazione,gruppoOwner);				 }
//	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true ) public List<TipiEQualificazioniDTO> selectByCodiceTipoProcedimentoPerMigrate(TipoProcedimento tipoProcedimento,Long idFascicolo) throws Exception {
//		  return 				   repository.selectByCodiceTipoProcedimento(			      tipoProcedimento,idFascicolo);				 }

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// Altri Metodi
	
}