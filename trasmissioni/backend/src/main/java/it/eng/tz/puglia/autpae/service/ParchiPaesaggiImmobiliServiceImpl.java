package it.eng.tz.puglia.autpae.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.autpae.entity.ParchiPaesaggiImmobiliDTO;
import it.eng.tz.puglia.autpae.entity.composedKeys.ParchiPaesaggiImmobiliPK;
import it.eng.tz.puglia.autpae.repository.ParchiPaesaggiImmobiliFullRepository;
import it.eng.tz.puglia.autpae.search.ParchiPaesaggiImmobiliSearch;
import it.eng.tz.puglia.autpae.service.interfacce.ParchiPaesaggiImmobiliService;
import it.eng.tz.puglia.bean.PaginatedList;

@Service
public class ParchiPaesaggiImmobiliServiceImpl implements ParchiPaesaggiImmobiliService {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(ParchiPaesaggiImmobiliServiceImpl.class);
//	private static final String UNAUTHORIZED_EXCEPTION_MESSAGE = "User cannot access attachments for plan exclusion of this municipality";

	@Autowired 	private ParchiPaesaggiImmobiliFullRepository repository;
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// BaseRepository
	
	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public ParchiPaesaggiImmobiliPK insert(ParchiPaesaggiImmobiliDTO entity) {
		return repository.insert(entity);
	}
	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public int delete(ParchiPaesaggiImmobiliSearch filter) throws Exception {
		return repository.delete(filter);
	}
	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true )
	public PaginatedList<ParchiPaesaggiImmobiliDTO> search(ParchiPaesaggiImmobiliSearch filter) throws Exception {
		return repository.search(filter);
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// FullRepository
	
	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true )
	public List<ParchiPaesaggiImmobiliDTO> select(Long praticaId, Long comuneId, String tipoVincolo) {
		return repository.select(praticaId, comuneId, tipoVincolo);
	}
	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public int setSelezioni(Long praticaId, Long comuneId, String tipoVincolo, List<String> codici) {
		return repository.setSelezioni(praticaId, comuneId, tipoVincolo, codici);
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// Altri Metodi
	
}