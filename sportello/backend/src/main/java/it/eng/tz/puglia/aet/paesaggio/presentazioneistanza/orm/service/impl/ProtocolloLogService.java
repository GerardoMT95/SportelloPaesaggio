package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.impl;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.eng.tz.puglia.error.exception.CustomValidationException;

/**
 * Service CRUD for table presentazione_istanza.tipo_ruolo_ditta
 */
@Service
public class ProtocolloLogService extends GenericCrudService<ProtocolloDTO, ProtocolloSearch, ProtocolloRepository> implements IProtocolloLogService{

    /**
     * dao
     */
    @Autowired
    private ProtocolloRepository dao;

    /**
     * get dao
     */
    @Override
    protected ProtocolloRepository getDao(){
        return this.dao;
    }
    /**
     * validate dto for insert
     */
    @Override
    protected void validateInsertDTO(final ProtocolloDTO entity) throws CustomValidationException{
        
    }
    /**
     * validate dto for update
     */
    @Override
    protected void validateUpdateDTO(final ProtocolloDTO entity) throws CustomValidationException{
       
    }

}
