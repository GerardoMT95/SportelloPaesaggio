package it.eng.tz.puglia.autpae.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.autpae.entity.LocalizzazioneInterventoDTO;
import it.eng.tz.puglia.autpae.entity.composedKeys.LocalizzazioneInterventoPK;
import it.eng.tz.puglia.autpae.repository.LocalizzazioneInterventoFullRepository;
import it.eng.tz.puglia.autpae.service.interfacce.LocalizzazioneInterventoService;

@Service
public class LocalizzazioneInterventoServiceImpl implements LocalizzazioneInterventoService {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(LocalizzazioneInterventoServiceImpl.class);
//	private static final String UNAUTHORIZED_EXCEPTION_MESSAGE = "User cannot access attachments for plan exclusion of this municipality";

	@Autowired 	private LocalizzazioneInterventoFullRepository repository;
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// BaseRepository
	
	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public LocalizzazioneInterventoPK insert(LocalizzazioneInterventoDTO entity) throws Exception {
		return repository.insert(entity);
	}
	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public int update(LocalizzazioneInterventoDTO entity) throws Exception {
		return repository.update(entity);
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// FullRepository
	
	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true )
	public List<LocalizzazioneInterventoDTO> select(Long praticaId) {
		return repository.select(praticaId);
	}
	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public int delete(LocalizzazioneInterventoDTO entity) {
		return repository.delete(entity);
	}
	@Override @Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class}, timeout=60000, readOnly=true )
	public List<Long> selectEffettivo(long idPratica) {
		return repository.selectEffettivo(idPratica);
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// Altri Metodi
	
}