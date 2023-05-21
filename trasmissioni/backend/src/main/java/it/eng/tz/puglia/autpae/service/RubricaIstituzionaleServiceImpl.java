package it.eng.tz.puglia.autpae.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.autpae.config.ApplicationProperties;
import it.eng.tz.puglia.autpae.dto.BE_to_FE.RubricaAdminSearchDTO;
import it.eng.tz.puglia.autpae.repository.base.RubricaIstituzionaleBaseRepository;
import it.eng.tz.puglia.autpae.search.RubricaIstituzionaleSearch;
import it.eng.tz.puglia.autpae.service.interfacce.RubricaIstituzionaleService;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.remote.dto.RubricaIstituzionaleDTO;
import it.eng.tz.puglia.servizi_esterni.remote.service.interfacce.CommonService;

@Service
public class RubricaIstituzionaleServiceImpl implements RubricaIstituzionaleService {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(RubricaIstituzionaleServiceImpl.class);
	
	
	
	@Autowired	private RubricaIstituzionaleBaseRepository localRepository;
	@Autowired	private CommonService commonService;
	@Value("${ente_attribute.codice.applicazione}")
	private String codiceApplicazioneRubricaIstituzionale;

	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, 	rollbackFor={Exception.class}, timeout=60000, readOnly=false)
	public int update(RubricaIstituzionaleDTO entity) throws Exception {
		try {
			entity.setApplicazioneId(commonService.getIdApplicazione(codiceApplicazioneRubricaIstituzionale));
			return commonService.updateRubricaIstituzionale(entity);
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, 	rollbackFor={Exception.class}, timeout=60000, readOnly=true )
	public PaginatedList<RubricaIstituzionaleDTO> search(RubricaIstituzionaleSearch filter) throws Exception {
		try {
			filter.setApplicazioneId(commonService.getIdApplicazione(codiceApplicazioneRubricaIstituzionale));
			return localRepository.search(filter);
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, 	rollbackFor={Exception.class}, timeout=60000, readOnly=true )
	public PaginatedList<RubricaAdminSearchDTO> adminSearch(RubricaAdminSearchDTO entity) throws Exception {
		try {
			entity.setApplicazioneId(commonService.getIdApplicazione(codiceApplicazioneRubricaIstituzionale));
			return localRepository.adminSearch(entity);
		} catch (Exception e) {
			throw e;
		}
	}
	
	
}