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
 * Service CRUD for table presentazione_istanza.referenti_documento
 */
@Service
public class ReferentiDocumentoService extends GenericCrudService<ReferentiDocumentoDTO, ReferentiDocumentoSearch, ReferentiDocumentoRepository> implements IReferentiDocumentoService{

    /**
     * dao
     */
    @Autowired
    private ReferentiDocumentoRepository dao;

    /**
     * get dao
     */
    @Override
    protected ReferentiDocumentoRepository getDao(){
        return this.dao;
    }
    /**
     * validate dto for insert
     */
    @Override
    protected void validateInsertDTO(final ReferentiDocumentoDTO entity) throws CustomValidationException{
    }
    /**
     * validate dto for update
     */
    @Override
    protected void validateUpdateDTO(final ReferentiDocumentoDTO entity) throws CustomValidationException{
        
    }

}
