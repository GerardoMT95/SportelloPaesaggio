package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.schedaTecnica.impl;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.DatabaseConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.ProcedimentoContenziosoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ProcedimentiContenziosoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.ProcedimentiContenziosoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.schedaTecnica.ProcedimentoContenziosoService;
import it.eng.tz.puglia.util.string.StringUtil;

@Service
public class ProcedimentoContenziosoServiceImpl implements ProcedimentoContenziosoService
{
	@Autowired
	private ProcedimentiContenziosoRepository repository;
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, readOnly=false, propagation=Propagation.MANDATORY, timeout=60000, rollbackFor= {Exception.class})
	public ProcedimentoContenziosoDto saveOrUpdate(ProcedimentoContenziosoDto dto) throws Exception
	{
		try {
			valida(dto, false);
			ProcedimentiContenziosoDTO entity = new ProcedimentiContenziosoDTO(dto);
			if (repository.countByIdPratica(dto.getIdPratica())==1) {
				repository.update(entity);
			}
			else {
				repository.insert(entity);
			}
		} catch (Exception e) {
			logger.error("Error in saveOrUpdate",e);
			throw e;
		}
		return dto;
	}

	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_READ, readOnly=true, propagation=Propagation.MANDATORY, timeout=60000, rollbackFor= {Exception.class})
	public ProcedimentoContenziosoDto findByIdFascicolo(UUID idPratica) throws Exception
	{
		ProcedimentiContenziosoDTO procedimentiContenzioso = null;
		
		try {
			if (repository.countByIdPratica(idPratica)==0)	// se non c'è ancora niente nel db per quella pratica
				procedimentiContenzioso = new ProcedimentiContenziosoDTO();
			else
				procedimentiContenzioso = repository.selectByIdPratica(idPratica);
		} catch (Exception e) {
			logger.error("Error in findByIdFascicolo",e);
			throw e;
		}
		return new ProcedimentoContenziosoDto(procedimentiContenzioso);
	}

	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, readOnly=false, propagation=Propagation.MANDATORY, timeout=60000, rollbackFor= {Exception.class})
	public Integer delete(UUID idFascicolo) throws Exception
	{
		throw new Exception("Questo metodo non dovrebbe essere richiamato!");
		// return null (???);
	}
	
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_READ, readOnly=true, propagation=Propagation.MANDATORY, timeout=60000, rollbackFor={Exception.class})
	public void valida(ProcedimentoContenziosoDto entity) throws Exception {
		this.valida(entity, true);
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// VALIDAZIONE
	
	private void valida(ProcedimentoContenziosoDto procedimentoContenzioso, boolean completo) throws Exception {
		
		if (procedimentoContenzioso.getContenzisoAtto()!=null  && 
			procedimentoContenzioso.getContenzisoAtto()==false && 
			StringUtil.isNotEmpty(procedimentoContenzioso.getDescrizione())) 	 throw new Exception("Errore in Validazione Procedimento Contenzioso (1)");
			
		if (completo == true) {
			if (procedimentoContenzioso.getContenzisoAtto()==null) 				 throw new Exception("Errore in Validazione Procedimento Contenzioso (2)");
			
			if (procedimentoContenzioso.getContenzisoAtto()==false && 
				StringUtil.isNotEmpty(procedimentoContenzioso.getDescrizione())) throw new Exception("Errore in Validazione Procedimento Contenzioso (3)");
			
			if (procedimentoContenzioso.getContenzisoAtto()==true  && 
				StringUtil.	  isEmpty(procedimentoContenzioso.getDescrizione())) throw new Exception("Errore in Validazione Procedimento Contenzioso (4)");
		}
	}

}