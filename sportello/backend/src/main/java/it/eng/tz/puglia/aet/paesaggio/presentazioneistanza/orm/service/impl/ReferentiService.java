package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.DatabaseConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DestinatarioDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ReferentiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.ReferentiRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.ReferentiSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.GenericCrudService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.IReferentiService;
import it.eng.tz.puglia.error.exception.CustomValidationException;

/**
 * Service CRUD for table presentazione_istanza.referenti
 */
@Service
public class ReferentiService extends GenericCrudService<ReferentiDTO, ReferentiSearch, ReferentiRepository>
		implements IReferentiService {

	/**
	 * dao
	 */
	@Autowired
	private ReferentiRepository dao;

	/**
	 * get dao
	 */
	@Override
	protected ReferentiRepository getDao() {
		return this.dao;
	}

	/**
	 * validate dto for insert
	 */
	@Override
	protected void validateInsertDTO(final ReferentiDTO entity) throws CustomValidationException {
		if (entity.getTipoReferente() == null) {
			throw new CustomValidationException("tipoReferente can't be null");
		}
		if (entity.getPraticaId() == null) {
			throw new CustomValidationException("praticaId can't be null");
		}
	}

	/**
	 * validate dto for update
	 */
	@Override
	protected void validateUpdateDTO(final ReferentiDTO entity) throws CustomValidationException {
		if (entity.getTipoReferente() == null) {
			throw new CustomValidationException("tipoReferente can't be null");
		}
		if (entity.getPraticaId() == null) {
			throw new CustomValidationException("praticaId can't be null");
		}
	}

	@Override
	public ReferentiDTO selectRichiedente(UUID idPratica, String tipo) {
		return this.dao.selectRichiedente(idPratica, tipo);
	}

	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_READ, propagation=Propagation.REQUIRED, readOnly=true, timeout=60000)
	public Long count(ReferentiSearch search) throws Exception
	{
		return dao.count(search);
	}

	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_READ, propagation=Propagation.REQUIRED, readOnly=true, timeout=60000)
	public List<DestinatarioDTO> findDestinatariPratica(UUID idPratica) throws Exception
	{
		List<DestinatarioDTO> contatti = Collections.emptyList();
		List<ReferentiDTO> referenti = dao.findReferentiPratica(idPratica);
		if(referenti != null && !referenti.isEmpty())
			contatti = referenti.stream().map(DestinatarioDTO::new).collect(Collectors.toList());
		return contatti;
	}
	
	

}
