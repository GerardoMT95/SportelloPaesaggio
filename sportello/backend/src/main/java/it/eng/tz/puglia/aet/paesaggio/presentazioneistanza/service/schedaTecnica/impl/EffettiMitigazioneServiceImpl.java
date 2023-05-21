package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.schedaTecnica.impl;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.DatabaseConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.EffettiMitigazioneDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.EffettiMitigazioneDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.EffettiMitigazioneRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.schedaTecnica.EffettiMitigazioneService;
import it.eng.tz.puglia.util.string.StringUtil;

@Service
public class EffettiMitigazioneServiceImpl implements EffettiMitigazioneService
{
	@Autowired
	private EffettiMitigazioneRepository repository;

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, readOnly=false, propagation=Propagation.MANDATORY, timeout=60000, rollbackFor= {Exception.class})
	public EffettiMitigazioneDto saveOrUpdate(EffettiMitigazioneDto dto) throws Exception
	{
		try {
			valida(dto, false);
			EffettiMitigazioneDTO entity = new EffettiMitigazioneDTO(dto);
			if (repository.countByIdPratica(dto.getIdPratica())==1) {
				repository.update(entity);
			}
			else {
				repository.insert(entity);
			}
		} catch (Exception e) {
			logger.error("Error in saveOrUpdate ",e);
			throw e;
		}
		return dto;
	}

	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_READ, readOnly=true, propagation=Propagation.MANDATORY, timeout=60000, rollbackFor= {Exception.class})
	public EffettiMitigazioneDto findByIdFascicolo(UUID idPratica) throws Exception
	{
		EffettiMitigazioneDTO effettiMitigazione = null;
		
		try {
			if (repository.countByIdPratica(idPratica)==0)	// se non c'è ancora niente nel db per quella pratica
				effettiMitigazione = new EffettiMitigazioneDTO();
			else
				effettiMitigazione = repository.selectByIdPratica(idPratica);
		} catch (Exception e) {
			logger.error("Errore in findByIdFascicolo",e);
			throw e;
		}
		return new EffettiMitigazioneDto(effettiMitigazione);
	}

	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, readOnly=false, propagation=Propagation.MANDATORY, timeout=60000, rollbackFor= {Exception.class})
	public Integer delete(UUID idFascicolo) throws Exception
	{
		throw new Exception("Questo metodo non dovrebbe essere richiamato!");
	}

	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_READ, readOnly=true, propagation=Propagation.MANDATORY, timeout=60000, rollbackFor={Exception.class})
	public void valida(EffettiMitigazioneDto entity) throws Exception {
		this.valida(entity, true);
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// VALIDAZIONE
	
	private void valida(EffettiMitigazioneDto effettiMitigazione, boolean completo) throws Exception {
		
	//  ho deciso di validarlo SOLO per il TipoProcedimento in cui è previsto.
	//	if (praticaRepository.find(effettiMitigazione.getIdPratica()).getTipoProcedimento()!=2) throw new Exception("Errore. Tab Effetti Mitigazione non previsto per questo tipo procedimento!");
		
		if (completo==true) {
			if (StringUtil.isEmpty(effettiMitigazione.getDescrizione())) 		 throw new Exception("Errore in Validazione Effetti Mitigazione (1)");
			if (StringUtil.isEmpty(effettiMitigazione.getEffeti())) 			 throw new Exception("Errore in Validazione Effetti Mitigazione (2)");
			if (StringUtil.isEmpty(effettiMitigazione.getMisure())) 			 throw new Exception("Errore in Validazione Effetti Mitigazione (3)");
			if (StringUtil.isEmpty(effettiMitigazione.getContenutiPercettivi())) throw new Exception("Errore in Validazione Effetti Mitigazione (4)");
		}
	}
	
}