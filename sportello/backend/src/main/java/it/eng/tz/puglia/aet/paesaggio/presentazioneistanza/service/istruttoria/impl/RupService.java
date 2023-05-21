package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.DatabaseConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.RupDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.RupRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.RupSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.GenericCrudService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria.IRupService;
import it.eng.tz.puglia.error.exception.CustomValidationException;

/**
 * Service CRUD for table presentazione_istanza.rup
 */
@Service
public class RupService extends GenericCrudService<RupDTO, RupSearch, RupRepository> implements IRupService{

    /**
     * dao
     */
    @Autowired
    private RupRepository dao;

    /**
     * get dao
     */
    @Override
    protected RupRepository getDao(){
        return this.dao;
    }
    
	@Override
	protected void validateInsertDTO(RupDTO entity) throws CustomValidationException {
		if(entity.getIdOrganizzazione()==0 || entity.getUsername()==null || 
				entity.getDataScadenza()==null)throw new CustomValidationException("Errore di validazione del RUP ");
	}
	@Override
	protected void validateUpdateDTO(RupDTO entity) throws CustomValidationException {
		
	}

	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_READ, readOnly=true, rollbackFor=Exception.class, timeout=60000, propagation=Propagation.REQUIRED)
	public Boolean isRup(String username, Integer idOrganizzazione) throws Exception
	{
		return dao.validRup(username, new Date(), idOrganizzazione);
	}
}