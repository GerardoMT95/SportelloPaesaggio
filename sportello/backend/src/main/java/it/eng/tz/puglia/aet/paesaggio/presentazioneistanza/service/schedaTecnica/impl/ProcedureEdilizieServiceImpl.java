package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.schedaTecnica.impl;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.DatabaseConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.ProcedureEdilizieDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.TipologicaFE;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AssoggProcedureEdilizieDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.AssoggProcedureEdilizieRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.schedaTecnica.ProcedureEdilizieService;
import it.eng.tz.puglia.util.string.StringUtil;

@Service
public class ProcedureEdilizieServiceImpl implements ProcedureEdilizieService
{
	@Autowired
	private AssoggProcedureEdilizieRepository repository;
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, readOnly=false, propagation=Propagation.MANDATORY, timeout=60000, rollbackFor= {Exception.class})
	public ProcedureEdilizieDto saveOrUpdate(ProcedureEdilizieDto dto) throws Exception
	{
		try {
			valida(dto, false);
			AssoggProcedureEdilizieDTO entity = new AssoggProcedureEdilizieDTO(dto);
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
	public ProcedureEdilizieDto findByIdFascicolo(UUID idPratica) throws Exception
	{
		AssoggProcedureEdilizieDTO procedureEdilizie = null;
		
		try {
			if (repository.countByIdPratica(idPratica)==0)	// se non c'è ancora niente nel db per quella pratica
				procedureEdilizie = new AssoggProcedureEdilizieDTO();
			else
				procedureEdilizie = repository.selectByIdPratica(idPratica);
		} catch (Exception e) {
			logger.error("Error in findByIdFascicolo",e);
			throw e;
		}
		return new ProcedureEdilizieDto(procedureEdilizie);
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
	public void valida(ProcedureEdilizieDto entity) throws Exception {
		this.valida(entity, true);
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// VALIDAZIONE
	
	private void valida(ProcedureEdilizieDto procedureEdilizie, boolean completo) throws Exception {
		
		if (procedureEdilizie.getTipoIntervento()!=null && 
		   (procedureEdilizie.getTipoIntervento()<=0 || procedureEdilizie.getTipoIntervento()>2)) throw new Exception("Errore in Validazione Procedure Edilizie(1)");
		
		if (StringUtil.isNotEmpty(procedureEdilizie.getMotivazione()) && 
		    procedureEdilizie.getTipoIntervento()!=null && procedureEdilizie.getTipoIntervento()==2) throw new Exception("Errore in Validazione Procedure Edilizie (2)");
		
		if ((StringUtil.isNotEmpty(procedureEdilizie.getDetagglio()) || procedureEdilizie.getPressoData()!=null) && 
			procedureEdilizie.getTipoIntervento()!=null && procedureEdilizie.getTipoIntervento()==1) throw new Exception("Errore in Validazione Procedure Edilizie (3)");
		
		if (procedureEdilizie.getEspressoData()  !=null && 
			procedureEdilizie.getTipoIntervento()!=null && procedureEdilizie.getTipoIntervento()==1) throw new Exception("Errore in Validazione Procedure Edilizie (4)");
		
		if (procedureEdilizie.getTipoIntervento()!=null && procedureEdilizie.getTipoIntervento()==1 &&
			procedureEdilizie.getTipoStato()	 !=null && !procedureEdilizie.getTipoStato().isEmpty()) throw new Exception("Errore in Validazione Procedure Edilizie (5)");
		
		boolean cb1 = false;
		boolean cb2 = false;
		
		if (procedureEdilizie.getTipoStato()!=null && !procedureEdilizie.getTipoStato().isEmpty()) {
			if (procedureEdilizie.getTipoStato().size()>2) throw new Exception("Errore in Validazione Procedure Edilizie (6)");
			for (TipologicaFE elem : procedureEdilizie.getTipoStato()) {
					 if (elem.getValue()==null) throw new Exception("Errore in Validazione Procedure Edilizie (7)");
				else if (elem.getValue()==1) cb1 = true;
				else if (elem.getValue()==2) cb2 = true;
				else throw new Exception("Errore in Validazione Procedure Edilizie (8)");
			}
		}
		
		if ((StringUtil.isNotEmpty(procedureEdilizie.getDetagglio()) || procedureEdilizie.getPressoData()!=null) && 
			(procedureEdilizie.getTipoStato()==null || procedureEdilizie.getTipoStato().isEmpty() || cb1==false)) throw new Exception("Errore in Validazione Procedure Edilizie (9)");
		
		if (procedureEdilizie.getEspressoData()  !=null && 
		    (procedureEdilizie.getTipoStato()==null || procedureEdilizie.getTipoStato().isEmpty() || cb2==false)) throw new Exception("Errore in Validazione Procedure Edilizie (10)");
		
		if (completo == true) {
			if (procedureEdilizie.getTipoIntervento()==null) throw new Exception("Errore in Validazione Procedure Edilizie (11)");
			if (procedureEdilizie.getTipoIntervento()==1 && 
				StringUtil.isEmpty(procedureEdilizie.getMotivazione())) throw new Exception("Errore in Validazione Procedure Edilizie (12)");
			if (procedureEdilizie.getTipoIntervento()==2 && 
			   (procedureEdilizie.getTipoStato()==null || procedureEdilizie.getTipoStato().isEmpty())) throw new Exception("Errore in Validazione Procedure Edilizie (13)");
			if (cb1==true && 
			   (StringUtil.isEmpty(procedureEdilizie.getDetagglio()) || procedureEdilizie.getPressoData()==null)) throw new Exception("Errore in Validazione Procedure Edilizie (14)");
			if (cb2==true && 
				procedureEdilizie.getEspressoData()==null) throw new Exception("Errore in Validazione Procedure Edilizie (15)");
		}
	}
	
}