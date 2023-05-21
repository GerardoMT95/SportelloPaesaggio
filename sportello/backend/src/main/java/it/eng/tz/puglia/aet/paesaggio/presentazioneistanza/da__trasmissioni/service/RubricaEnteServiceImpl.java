package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.ApplicationProperties;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.repository.RubricaEnteBaseRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.search.RubricaEnteSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.service.interfacce.RubricaEnteService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.UserUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.RubricaEnteDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.service.RemoteSchemaService;
import it.eng.tz.puglia.bean.PaginatedList;

@Service
public class RubricaEnteServiceImpl implements RubricaEnteService {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(RubricaEnteServiceImpl.class);
	
	@Autowired	private RubricaEnteBaseRepository localRepository;
	@Autowired	private RemoteSchemaService commonService;
	@Autowired	private UserUtil userUtil;
	@Autowired  private	ApplicationProperties props;
	
	
	@Override
	@Transactional(transactionManager="txmng_common", propagation=Propagation.REQUIRED, 	rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public long insert(RubricaEnteDTO entity) throws Exception {
		try {
			entity.setCodiceApplicazione(props.getRubricaRiferimentoApplicazione());
			entity.setPaesaggioOrganizzazioneId(userUtil.getIntegerId());
			return commonService.insertRubricaEnte(entity);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", propagation=Propagation.REQUIRED, 	rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public int update(RubricaEnteDTO entity) throws Exception {
		try {
			entity.setCodiceApplicazione(props.getRubricaRiferimentoApplicazione());
			entity.setPaesaggioOrganizzazioneId(userUtil.getIntegerId());
			return commonService.updateRubricaEnte(entity);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional(transactionManager="txmng_common", propagation=Propagation.REQUIRED, 	rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public int delete(RubricaEnteDTO entity) throws Exception {
		try {
			entity.setCodiceApplicazione(props.getRubricaRiferimentoApplicazione());
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
	@Transactional(transactionManager="txmng_common", propagation=Propagation.REQUIRED, 	rollbackFor={Exception.class}, timeout=60000, readOnly=true)
	public PaginatedList<RubricaEnteDTO> search(RubricaEnteSearch filter) throws Exception {
		try {
			filter.setCodiceApplicazione(props.getRubricaRiferimentoApplicazione());
			if (userUtil.getGruppo()!=Gruppi.ADMIN) {
				filter.setPaesaggioOrganizzazioneId(userUtil.getIntegerId());
			}
			return localRepository.search(filter);
		} catch (Exception e) {
			throw e;
		}
	}

}