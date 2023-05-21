package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.schedaTecnica.impl;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.DatabaseConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.TipologicaFE;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.VincolisticaDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.VincArchDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.VincArchRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.schedaTecnica.VincolisticaService;

@Service
public class VincolisticaServiceImpl implements VincolisticaService
{

	@Autowired
	private VincArchRepository repository;

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, readOnly=false, propagation=Propagation.MANDATORY, timeout=60000, rollbackFor= {Exception.class})
	public VincolisticaDto saveOrUpdate(VincolisticaDto dto) throws Exception
	{
		try {
			valida(dto, false);
			VincArchDTO entity = new VincArchDTO(dto);
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
	public VincolisticaDto findByIdFascicolo(UUID idPratica) throws Exception
	{
		VincArchDTO vincolistica = null;
		
		try {
			if (repository.countByIdPratica(idPratica)==0)	// se non c'è ancora niente nel db per quella pratica
				vincolistica = new VincArchDTO();
			else
				vincolistica = repository.selectByIdPratica(idPratica);
		} catch (Exception e) {
			logger.error("Error in saveOrUpdate",e);
			throw e;
		}
		return new VincolisticaDto(vincolistica);
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
	public void valida(VincolisticaDto entity) throws Exception {
		this.valida(entity, true);		
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// VALIDAZIONE
	
	private void valida(VincolisticaDto vincolistica, boolean completo) throws Exception {
		
		if (vincolistica.getSottopostoATutela()!=null &&  vincolistica.getSottopostoATutela()==false &&
			vincolistica.getSpecificaVincolo ()!=null && !vincolistica.getSpecificaVincolo ().isEmpty()) throw new Exception("Errore in Validazione Vincolistica (1)");
		
		if (vincolistica.getSpecificaVincolo ()!=null && !vincolistica.getSpecificaVincolo ().isEmpty() && 
			vincolistica.getSpecificaVincolo ().size()>4) throw new Exception("Errore in Validazione Vincolistica (2)");
		
		if (vincolistica.getSpecificaVincolo ()!=null && !vincolistica.getSpecificaVincolo ().isEmpty()) {
			for (TipologicaFE elem : vincolistica.getSpecificaVincolo()) {
				if (elem==null || elem.getValue()==null || elem.getValue() <= 0 || elem.getValue() > 4)  throw new Exception("Errore in Validazione Vincolistica (3)");
			};
		}
		
		if (completo==true) {
			if (vincolistica.getSottopostoATutela()==null) throw new Exception("Errore in Validazione Vincolistica (4)");
			if (vincolistica.getSottopostoATutela()==true  && 
			   (vincolistica.getSpecificaVincolo ()==null  ||  vincolistica.getSpecificaVincolo().isEmpty())) throw new Exception("Errore in Validazione Vincolistica (5)");
			if (vincolistica.getSottopostoATutela()==false && 
				vincolistica.getSpecificaVincolo ()!=null  && !vincolistica.getSpecificaVincolo().isEmpty() ) throw new Exception("Errore in Validazione Vincolistica (6)");
		}
	}
	
}