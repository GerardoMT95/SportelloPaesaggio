package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.schedaTecnica.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import org.hsqldb.lib.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import au.com.objectix.jgridshift.Util;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.DatabaseConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.DichiarazioneDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DichiarazioneDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.DichiarazioneRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.PraticaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.schedaTecnica.DichiarazioneService;
import it.eng.tz.puglia.util.date.DateUtil;

@Service
public class DichiarazioneServiceImpl implements DichiarazioneService
{
	@Autowired
	private DichiarazioneRepository repository;
	
		
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, readOnly=false, propagation=Propagation.MANDATORY, timeout=60000, rollbackFor= {Exception.class})
	public DichiarazioneDto saveOrUpdate(DichiarazioneDto dto) throws Exception
	{
		try {
			valida(dto, false);
			repository.setDichiarazSchTecAccettata(dto.getIdPratica(), dto.isAccettata());
		} catch (Exception e) {
			logger.error("Error in saveOrUpdate ",e);
			throw e;
		}
		return dto;
	}

	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_READ, readOnly=true, propagation=Propagation.MANDATORY, timeout=60000, rollbackFor= {Exception.class})
	public DichiarazioneDto findByIdFascicolo(UUID idPratica) throws Exception
	{
		DichiarazioneDTO dichiarazione = null;
		Boolean accettata = null;
		
		try {
				dichiarazione = repository.findByIdFascicolo(idPratica);	// cerca l'unica dichiarazione attualmente vigente per la pratica per data_creazione
				accettata = repository.getDichiarazSchTecAccettata(idPratica);
		} catch (Exception e) {
			logger.error("Error in findByIdFascicolo ",e);
			throw e;
		}
		return new DichiarazioneDto(dichiarazione, accettata);
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
	public void valida(DichiarazioneDto entity) throws Exception {
		this.valida(entity, true);
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// VALIDAZIONE
	
	private void valida(DichiarazioneDto dichiarazione, boolean completo) throws Exception 
	{
		if (completo==true) {
			if (dichiarazione.isAccettata()==false) throw new Exception("E' obbligatorio accettare la dichiarazione di responsabilità per la Scheda Tecnica!");
		};
	}


	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_READ, readOnly=true, propagation=Propagation.SUPPORTS, timeout=60000, rollbackFor={Exception.class})
	public DichiarazioneDTO findByTipoProcedimentoAndData(Integer id, Date date) {
		return repository.findByTipoProcedimentoAndData(id,date);	// cerca l'unica dichiarazione attualmente vigente per il tipoProcedimento con id e per data
	}
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, readOnly=false, propagation=Propagation.REQUIRED, timeout=60000, rollbackFor={Exception.class})
	public int upsertDichiarazione(int id, String testo,String labelCb ) {
		DichiarazioneDTO dich=repository.findLastValid(id);
		Timestamp currentTime = DateUtil.currentTimestamp();
		Timestamp nextTime =new Timestamp(currentTime.getTime()+1);
		int updated=0;
		if(dich!=null) {
			dich.setDataFine(currentTime);
			repository.update(dich);
		}
		dich=new DichiarazioneDTO();
		dich.setIdProcedimento(id);
		dich.setLabelCb(labelCb);
		dich.setTesto(testo);
		dich.setDataInizio(nextTime);
		updated=(int) repository.insert(dich);
		return updated;
	}
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_READ, readOnly=true, propagation=Propagation.SUPPORTS, timeout=60000, rollbackFor={Exception.class})	
	public void valida(int id, String testo,String labelCb ) throws CustomOperationToAdviceException {
		repository.findLastValid(id);
		StringBuilder messageError=new StringBuilder();
		if(StringUtil.isEmpty(testo)) {
			messageError.append("Testo vuoto impossibile procedere");
		}
		if(StringUtil.isEmpty(labelCb)) {
			messageError.append("Label Checkbox vuota impossibile procedere");
		}
		
	}

}