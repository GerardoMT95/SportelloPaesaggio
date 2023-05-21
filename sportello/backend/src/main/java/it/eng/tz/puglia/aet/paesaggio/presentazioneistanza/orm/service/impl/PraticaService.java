package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.PraticaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.PraticaSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.GenericCrudService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.IPraticaService;
import it.eng.tz.puglia.error.exception.CustomValidationException;

/**
 * Service CRUD for table presentazione_istanza.pratica
 */
@Primary
@Service
public class PraticaService extends GenericCrudService<PraticaDTO, PraticaSearch, PraticaRepository> implements IPraticaService{

    /**
     * dao
     */
    @Autowired
    private PraticaRepository dao;

    /**
     * get dao
     */
    @Override
    protected PraticaRepository getDao(){
        return this.dao;
    }
    /**
     * validate dto for insert
     */
    @Override
    protected void validateInsertDTO(final PraticaDTO entity) throws CustomValidationException{
        if(entity.getId() == null){
            throw new CustomValidationException("id can't be null");
        }
        if(entity.getCodicePraticaAppptr() == null){
            throw new CustomValidationException("codicePraticaAppptr can't be null");
        }
        if(entity.getEnteDelegato() == null){
            throw new CustomValidationException("enteDelegato can't be null");
        }
       
        if(entity.getStato() == null){
            throw new CustomValidationException("stato can't be null");
        }
        if(entity.getDataCreazione() == null){
            throw new CustomValidationException("dataCreazione can't be null");
        }
        if(entity.getOggetto() == null){
            throw new CustomValidationException("oggetto can't be null");
        }
        if(entity.getUserId() == null){
            throw new CustomValidationException("userId can't be null");
        }
    }
    /**
     * validate dto for update
     */
    @Override
    protected void validateUpdateDTO(final PraticaDTO entity) throws CustomValidationException{
        /*if(entity.getId() == null){
            throw new CustomValidationException("id can't be null");
        }*/
        if(entity.getCodicePraticaAppptr() == null){
            throw new CustomValidationException("codicePraticaAppptr can't be null");
        }
        if(entity.getEnteDelegato() == null){
            throw new CustomValidationException("enteDelegato can't be null");
        }
        
        if(entity.getStato() == null){
            throw new CustomValidationException("stato can't be null");
        }
        if(entity.getDataCreazione() == null){
            throw new CustomValidationException("dataCreazione can't be null");
        }
        if(entity.getOggetto() == null){
            throw new CustomValidationException("oggetto can't be null");
        }
        if(entity.getUserId() == null){
            throw new CustomValidationException("userId can't be null");
        }
        if(entity.getDataModifica() == null){
            throw new CustomValidationException("dataModifica can't be null");
        }
    }
    
    
	@Override
	@Transactional(readOnly=true, propagation=Propagation.REQUIRED, transactionManager = TX_READ)
	public PraticaDTO findByCodice(String codiceApptr) {
		return dao.findByCodice(codiceApptr);
	}
    
    

}
