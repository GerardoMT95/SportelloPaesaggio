package it.eng.tz.puglia.servizi_esterni.remote.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.autpae.dto.TipologicaDTO;
import it.eng.tz.puglia.servizi_esterni.remote.dto.AnagraficaDTO;
import it.eng.tz.puglia.servizi_esterni.remote.dto.AutoCompleteDTO;
import it.eng.tz.puglia.servizi_esterni.remote.repository.AnagraficaBaseRepository;
import it.eng.tz.puglia.servizi_esterni.remote.repository.AnagraficaFullRepository;
import it.eng.tz.puglia.servizi_esterni.remote.service.interfacce.AnagraficaService;

@Service
public class AnagraficaServiceImpl implements AnagraficaService {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(AnagraficaServiceImpl.class);
//	private static final String UNAUTHORIZED_EXCEPTION_MESSAGE = "User cannot access attachments for plan exclusion of this municipality";

	@Autowired 	private AnagraficaFullRepository repository;
	
	@Autowired 	private AnagraficaBaseRepository baseRepository;
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// FullRepository
	
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true )	public List<TipologicaDTO> getListaBPparchiRiserve			 (Set<String> listaCodici)  throws Exception {
																																	return repository		  .getListaBPparchiRiserve			 (			  listaCodici);	}
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true )	public List<TipologicaDTO> getListaUcpPaesaggioRurale		 (Set<String> listaCodici) 	throws Exception {
																																	return repository		  .getListaUcpPaesaggioRurale		 (			  listaCodici);	}
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true )	public List<TipologicaDTO> getListaBPimmobiliAree			 (Set<String> listaCodici) 	throws Exception {
																																	return repository		  .getListaBPimmobiliAree			 (			  listaCodici);	}
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true )	public List<TipologicaDTO> selectAllBPparchiRiserve			 () 						throws Exception {
																																	return repository		  .selectAllBPparchiRiserve			 ();						}
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true )	public List<TipologicaDTO> selectAllUcpPaesaggioRurale		 () 						throws Exception {
																																	return repository		  .selectAllUcpPaesaggioRurale		 ();						}
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true )	public List<TipologicaDTO> selectAllBPimmobiliAree			 () 						throws Exception {
																																	return repository		  .selectAllBPimmobiliAree			 ();						}
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true )	public long 			   countParchi						 (String codiceCatastale) 	throws Exception {
																																	return repository		  .countParchi						 (		 codiceCatastale);	}
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true )	public long 			   countPaesaggiRurali				 (String codiceCatastale) 	throws Exception {
																																	return repository		  .countPaesaggiRurali				 (		 codiceCatastale);	}
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true )	public long 			   countImmobiliAreeInteressePubblico(String codiceCatastale) 	throws Exception {
																																	return repository		  .countImmobiliAreeInteressePubblico(		 codiceCatastale);	}
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true )	public List<TipologicaDTO> emailParchi						 (List<String> codici) 		throws Exception {
																																	return repository		  .emailParchi						 (			   codici);		}
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true )	public List<String> 	   getAllCodiciIstat				 () 						throws Exception {
																																	return repository		  .getAllCodiciIstat				 ();						}
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true )	public List<String> 	   getAllCodiciCatastali			 () 						throws Exception {
																																	return repository		  .getAllCodiciCatastali			 ();						}
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true )	public List<String> 	   getAllCodiciCatastaliInProvincia  (String siglaProvincia) 	throws Exception {
																																	return repository		  .getAllCodiciCatastaliInProvincia	 (		 siglaProvincia);	}
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true )	public List<String> 	   getAllCodiciIstatInProvincia		 (String siglaProvincia) 	throws Exception {
																																	return repository		  .getAllCodiciIstatInProvincia		 (		 siglaProvincia);	}
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true )	public String 			   getCodIstatFromCatastale			 (String codiceCatastale) 	throws Exception {
																																	return repository		  .getCodIstatFromCatastale			 (		 codiceCatastale);	}
	@Override @Transactional(propagation=Propagation.REQUIRED, 		rollbackFor={Exception.class}, timeout=60000, readOnly=true )	public String 			   getCodCatastaleFromIstat			 (String codiceIstat) 		throws Exception {
																																	return repository		  .getCodCatastaleFromIstat			 (		 codiceIstat);		}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// Metodi già presenti

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(transactionManager="txmng_anagrafica", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public AnagraficaDTO findAnagraficaByCode(String code) {
		Optional<AnagraficaDTO> result = baseRepository.find(code);
		if (result.isPresent()) {
			return result.get();
		}
		return new AnagraficaDTO();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(transactionManager="txmng_anagrafica", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<AutoCompleteDTO> autocompleteNazioni(String filter, int limit) throws Exception
	{
		return repository.autocompleteNazioni(filter, limit);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(transactionManager="txmng_anagrafica", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<AutoCompleteDTO> autocompleteProvince(String filter, int limit) throws Exception
	{
		return repository.autocompleteProvince(filter, limit);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(transactionManager="txmng_anagrafica", readOnly=true, rollbackFor=Exception.class, propagation=Propagation.REQUIRED, timeout=60000)
	public List<AutoCompleteDTO> autocompleteComuni(String filter, String denominazioneProvincia, Integer idProvincia, Integer limit,Integer idRegione) throws Exception
	{
		return repository.autocompleteComuni(filter, denominazioneProvincia, idProvincia, limit,idRegione);
	}
	
}