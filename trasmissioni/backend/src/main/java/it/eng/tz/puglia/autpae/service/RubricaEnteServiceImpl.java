package it.eng.tz.puglia.autpae.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.autpae.config.ApplicationProperties;
import it.eng.tz.puglia.autpae.enumeratori.Gruppi;
import it.eng.tz.puglia.autpae.repository.base.RubricaEnteBaseRepository;
import it.eng.tz.puglia.autpae.search.RubricaEnteSearch;
import it.eng.tz.puglia.autpae.service.interfacce.RubricaEnteService;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.profileManager.feign.UserUtil;
import it.eng.tz.puglia.servizi_esterni.remote.dto.RubricaEnteDTO;
import it.eng.tz.puglia.servizi_esterni.remote.service.interfacce.CommonService;

@Service
public class RubricaEnteServiceImpl implements RubricaEnteService {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(RubricaEnteServiceImpl.class);
	
	@Autowired	private RubricaEnteBaseRepository localRepository;
	@Autowired	private CommonService commonService;
	@Autowired	private UserUtil userUtil;
	@Autowired  private	ApplicationProperties props;
	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, 	rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public long insert(RubricaEnteDTO entity) throws Exception {
		try {
			entity.setCodiceApplicazione(props.getCodiceApplicazioneProfile());
			entity.setPaesaggioOrganizzazioneId(userUtil.getIntegerId());
			return commonService.insertRubricaEnte(entity);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, 	rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public int update(RubricaEnteDTO entity) throws Exception {
		try {
			entity.setCodiceApplicazione(props.getCodiceApplicazioneProfile());
			entity.setPaesaggioOrganizzazioneId(userUtil.getIntegerId());
			return commonService.updateRubricaEnte(entity);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, 	rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public int delete(RubricaEnteDTO entity) throws Exception {
		try {
			entity.setCodiceApplicazione(props.getCodiceApplicazioneProfile());
			entity.setPaesaggioOrganizzazioneId(userUtil.getIntegerId());
			return commonService.deleteRubricaEnte(entity);
		} catch (Exception e) {
			throw e;
		}
	}
	
/*	@Override
//	@Transactional(propagation=Propagation.REQUIRED, 	rollbackFor={Exception.class}, timeout=60000, readOnly=true )
//	public long count(RubricaEnteSearch filter) throws Exception {
//		try {
//			filter.setCodiceApplicazione(props.getCodiceApplicazioneProfile());
//			filter.setPaesaggioOrganizzazioneId(userUtil.getIntegerId());
//			return localRepository.count(filter);
//		} catch (Exception e) {
//			throw e;
//		}
//	}	*/
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, 	rollbackFor={Exception.class}, timeout=60000, readOnly=true )
	public PaginatedList<RubricaEnteDTO> search(RubricaEnteSearch filter) throws Exception {
		try {
			filter.setCodiceApplicazione(props.getCodiceApplicazioneProfile());
			if (userUtil.getGruppo()!=Gruppi.ADMIN) {
				filter.setPaesaggioOrganizzazioneId(userUtil.getIntegerId());
			}
			return localRepository.search(filter);
		} catch (Exception e) {
			throw e;
		}
	}

}