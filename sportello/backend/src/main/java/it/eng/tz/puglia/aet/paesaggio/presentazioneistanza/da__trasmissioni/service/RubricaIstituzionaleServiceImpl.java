package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.ApplicationProperties;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.RubricaAdminSearchDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.repository.RubricaIstituzionaleBaseRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.search.RubricaIstituzionaleSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.service.interfacce.RubricaIstituzionaleService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.RubricaIstituzionaleDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.service.RemoteSchemaService;
import it.eng.tz.puglia.bean.PaginatedList;

@Service
public class RubricaIstituzionaleServiceImpl implements RubricaIstituzionaleService {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(RubricaIstituzionaleServiceImpl.class);
	
	@Autowired	private RubricaIstituzionaleBaseRepository localRepository;
	@Autowired	private RemoteSchemaService commonService;
	@Autowired  private	ApplicationProperties props;
	
	
	@Override
	@Transactional(transactionManager="txmng_common", propagation=Propagation.REQUIRED, 	rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public int update(RubricaIstituzionaleDTO entity) throws Exception {
		try {
			entity.setApplicazioneId(commonService.getIdApplicazione(props.getRubricaRiferimentoApplicazione()));
			return commonService.updateRubricaIstituzionale(entity);
		} catch (Exception e) {
			throw e;
		}
	}
	
/*	@Override
//	@Transactional(propagation=Propagation.REQUIRED, 	rollbackFor={Exception.class}, timeout=60000, readOnly=true )
//	public long count(RubricaIstituzionaleSearch filter) throws Exception {
//		try {
//			filter.setApplicazioneId(commonService.getIdApplicazione(props.getCodiceApplicazioneProfile()));
//			return localRepository.count(filter);
//		} catch (Exception e) {
//			throw e;
//		}
//	}	*/
	
	@Override
	@Transactional(transactionManager="txmng_common", propagation=Propagation.REQUIRED, 	rollbackFor={Exception.class}, timeout=60000, readOnly=true )
	public PaginatedList<RubricaIstituzionaleDTO> search(RubricaIstituzionaleSearch filter) throws Exception {
		try {
			filter.setApplicazioneId(commonService.getIdApplicazione(props.getRubricaRiferimentoApplicazione()));
			return localRepository.search(filter);
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	
	@Override
	@Transactional(transactionManager="txmng_common", propagation=Propagation.REQUIRED, 	rollbackFor={Exception.class}, timeout=60000, readOnly=true )
	public PaginatedList<RubricaAdminSearchDTO> adminSearch(RubricaAdminSearchDTO entity) throws Exception {
		try {
			entity.setApplicazioneId(commonService.getIdApplicazione(props.getRubricaRiferimentoApplicazione()));
			return localRepository.adminSearch(entity);
		} catch (Exception e) {
			throw e;
		}
	}
	
}