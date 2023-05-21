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
 * Service CRUD for table presentazione_istanza.tipo_ruolo_ditta
 */
@Service
public class TipoRuoloDittaService extends GenericCrudService<TipoRuoloDittaDTO, TipoRuoloDittaSearch, TipoRuoloDittaRepository> implements ITipoRuoloDittaService{

    /**
     * dao
     */
    @Autowired
    private TipoRuoloDittaRepository dao;

    /**
     * get dao
     */
    @Override
    protected TipoRuoloDittaRepository getDao(){
        return this.dao;
    }
    /**
     * validate dto for insert
     */
    @Override
    protected void validateInsertDTO(final TipoRuoloDittaDTO entity) throws CustomValidationException{
        if(entity.getNome() == null){
            throw new CustomValidationException("nome can't be null");
        }
    }
    /**
     * validate dto for update
     */
    @Override
    protected void validateUpdateDTO(final TipoRuoloDittaDTO entity) throws CustomValidationException{
        if(entity.getNome() == null){
            throw new CustomValidationException("nome can't be null");
        }
    }

}
