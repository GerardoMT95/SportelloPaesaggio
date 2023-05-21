package it.eng.tz.puglia.autpae.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.autpae.entity.ParticelleCatastaliDTO;
import it.eng.tz.puglia.autpae.entity.composedKeys.ParticelleCatastaliPK;
import it.eng.tz.puglia.autpae.repository.ParticelleCatastaliFullRepository;
import it.eng.tz.puglia.autpae.search.ParticelleCatastaliSearch;
import it.eng.tz.puglia.autpae.service.interfacce.ParticelleCatastaliService;
import it.eng.tz.puglia.bean.PaginatedList;

@Service
public class ParticelleCatastaliServiceImpl implements ParticelleCatastaliService {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(ParticelleCatastaliServiceImpl.class);
//	private static final String UNAUTHORIZED_EXCEPTION_MESSAGE = "User cannot access attachments for plan exclusion of this municipality";

	@Autowired 	private ParticelleCatastaliFullRepository repository;
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// BaseRepository
	
	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public ParticelleCatastaliPK insert(ParticelleCatastaliDTO entity) {
		return repository.insert(entity);
	}
	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public int delete(ParticelleCatastaliSearch filter) throws Exception {
		return repository.delete(filter);
	}
	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true )
	public PaginatedList<ParticelleCatastaliDTO> search(ParticelleCatastaliSearch filter) throws Exception {
		return repository.search(filter);
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// FullRepository
	
	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true )
	public List<ParticelleCatastaliDTO> select(Long praticaId, long comuneId) {
		return repository.select(praticaId, comuneId);
	}
	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public int deleteByKeyLoc(Long id, long comuneId) {
		return repository.deleteByKeyLoc(id, comuneId);
	}
	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public int insertMultiple(List<ParticelleCatastaliDTO> particella, long idFascicolo) throws Exception {
		return repository.insertMultiple(particella, idFascicolo);
	}
	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true )
	public List<String> selectComuniIdInteressati(long idFascicolo) throws Exception {
		return repository.selectComuniIdInteressati(idFascicolo);
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// Altri Metodi
	
}