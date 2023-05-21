package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.schedaTecnica.impl;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.DatabaseConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.InquadramentoDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.InquadramentoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.InquadramentoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.schedaTecnica.InquadramentoService;
import it.eng.tz.puglia.util.string.StringUtil;

@Service
public class InquadramentoServiceImpl implements InquadramentoService
{
	@Autowired
	private InquadramentoRepository repository;
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, readOnly=false, propagation=Propagation.MANDATORY, timeout=60000, rollbackFor= {Exception.class})
	public InquadramentoDto saveOrUpdate(InquadramentoDto dto) throws Exception
	{
		try {
			valida(dto, false);
			InquadramentoDTO entity = new InquadramentoDTO(dto);
			if (repository.countByIdPratica(dto.getIdPratica())==1) {
				repository.update(entity);
			}
			else {
				repository.insert(entity);
			}
		} catch (Exception e) {
			logger.error("Errore in saveOrUpdate",e);
			throw e;
		}
		return dto;
	}

	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_READ, readOnly=true, propagation=Propagation.MANDATORY, timeout=60000, rollbackFor= {Exception.class})
	public InquadramentoDto findByIdFascicolo(UUID idPratica) throws Exception
	{
		InquadramentoDTO inquadramento = null;
		
		try {
			if (repository.countByIdPratica(idPratica)==0)	// se non c'è ancora niente nel db per quella pratica
				inquadramento = new InquadramentoDTO();
			else
				inquadramento = repository.selectByIdPratica(idPratica);
		} catch (Exception e) {
			logger.error("Errore in findByIdFascicolo",e);
			throw e;
		}
		return new InquadramentoDto(inquadramento);
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
	public void valida(InquadramentoDto entity) throws Exception {
		this.valida(entity, true);
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// VALIDAZIONE
	
	private void valida(InquadramentoDto inquadramento, boolean completo) throws Exception {
	
	//  ho deciso di validarlo SOLO per il TipoProcedimento in cui è previsto.
	//	if (praticaRepository.find(inquadramento.getIdPratica()).getTipoProcedimento()!=2) throw new Exception("Errore. Tab Inquadramento non previsto per questo tipo procedimento!");
		
		if (inquadramento.getDestinazioneUso()		!=null && (inquadramento.getDestinazioneUso()	   <= 0 || inquadramento.getDestinazioneUso()	   > 6)) throw new Exception("Errore in Validazione Inquadramento (1)");
		if (inquadramento.getContestoPaesaggInterv()!=null && (inquadramento.getContestoPaesaggInterv()<= 0 || inquadramento.getContestoPaesaggInterv()>10)) throw new Exception("Errore in Validazione Inquadramento (2)");
		if (inquadramento.getMorfologiaConPaesag()	!=null && (inquadramento.getMorfologiaConPaesag()  <= 0 || inquadramento.getMorfologiaConPaesag()  > 7)) throw new Exception("Errore in Validazione Inquadramento (3)");
		
		if (inquadramento.getDestinazioneUso()		!=null &&  inquadramento.getDestinazioneUso()	   != 6 && StringUtil.isNotEmpty(inquadramento.getDestinazioneUsoSpecifica())) 		 throw new Exception("Errore in Validazione Inquadramento (4)");
		if (inquadramento.getContestoPaesaggInterv()!=null &&  inquadramento.getContestoPaesaggInterv()!=10 && StringUtil.isNotEmpty(inquadramento.getContestoPaesaggIntervSpecifica())) throw new Exception("Errore in Validazione Inquadramento (5)");
		if (inquadramento.getMorfologiaConPaesag()	!=null &&  inquadramento.getMorfologiaConPaesag()  != 7 && StringUtil.isNotEmpty(inquadramento.getMorfologiaConPaesagSpecifica())) 	 throw new Exception("Errore in Validazione Inquadramento (6)");
		
		if (completo == true) {
			if (inquadramento.getDestinazioneUso()		==null || inquadramento.getDestinazioneUso()	  <=0 || inquadramento.getDestinazioneUso()	  	 > 6) throw new Exception("Errore in Validazione Inquadramento (7)");
			if (inquadramento.getContestoPaesaggInterv()==null || inquadramento.getContestoPaesaggInterv()<=0 || inquadramento.getContestoPaesaggInterv()>10) throw new Exception("Errore in Validazione Inquadramento (8)");
			if (inquadramento.getMorfologiaConPaesag()	==null || inquadramento.getMorfologiaConPaesag()  <=0 || inquadramento.getMorfologiaConPaesag()  > 7) throw new Exception("Errore in Validazione Inquadramento (9)");

			if (inquadramento.getDestinazioneUso()	    != 6 && StringUtil.isNotEmpty(inquadramento.getDestinazioneUsoSpecifica())) 	  throw new Exception("Errore in Validazione Inquadramento (10)");
			if (inquadramento.getContestoPaesaggInterv()!=10 && StringUtil.isNotEmpty(inquadramento.getContestoPaesaggIntervSpecifica())) throw new Exception("Errore in Validazione Inquadramento (11)");
			if (inquadramento.getMorfologiaConPaesag()  != 7 && StringUtil.isNotEmpty(inquadramento.getMorfologiaConPaesagSpecifica()))   throw new Exception("Errore in Validazione Inquadramento (12)");

			if (inquadramento.getDestinazioneUso()	    == 6 && StringUtil.isEmpty(inquadramento.getDestinazioneUsoSpecifica())) 		  throw new Exception("Errore in Validazione Inquadramento (13)");
			if (inquadramento.getContestoPaesaggInterv()==10 && StringUtil.isEmpty(inquadramento.getContestoPaesaggIntervSpecifica())) 	  throw new Exception("Errore in Validazione Inquadramento (14)");
			if (inquadramento.getMorfologiaConPaesag()  == 7 && StringUtil.isEmpty(inquadramento.getMorfologiaConPaesagSpecifica())) 	  throw new Exception("Errore in Validazione Inquadramento (15)");
		}
	}

}