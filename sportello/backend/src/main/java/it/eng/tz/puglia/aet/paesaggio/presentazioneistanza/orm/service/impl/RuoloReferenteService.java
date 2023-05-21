package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.impl;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.eng.tz.puglia.error.exception.CustomValidationException;

/**
 * Service CRUD for table presentazione_istanza.ruolo_referente
 */
@Service
public class RuoloReferenteService extends GenericCrudService<RuoloReferenteDTO, RuoloReferenteSearch, RuoloReferenteRepository> implements IRuoloReferenteService{

    /**
     * dao
     */
    @Autowired
    private RuoloReferenteRepository dao;

    /**
     * get dao
     */
    @Override
    protected RuoloReferenteRepository getDao(){
        return this.dao;
    }
    /**
     * validate dto for insert
     */
    @Override
    protected void validateInsertDTO(final RuoloReferenteDTO entity) throws CustomValidationException{
        if(entity.getDescrizione() == null){
            throw new CustomValidationException("descrizione can't be null");
        }
    }
    /**
     * validate dto for update
     */
    @Override
    protected void validateUpdateDTO(final RuoloReferenteDTO entity) throws CustomValidationException{
        if(entity.getDescrizione() == null){
            throw new CustomValidationException("descrizione can't be null");
        }
    }

}
