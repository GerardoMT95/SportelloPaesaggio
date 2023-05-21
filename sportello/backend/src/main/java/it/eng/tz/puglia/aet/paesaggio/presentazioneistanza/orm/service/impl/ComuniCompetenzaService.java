package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ComuniCompetenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.ComuniCompetenzaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.ComuniCompetenzaSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.GenericCrudService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.IComuniCompetenzaService;
import it.eng.tz.puglia.error.exception.CustomValidationException;

/**
 * Service CRUD for table presentazione_istanza.comuni_competenza
 */
@Service
public class ComuniCompetenzaService extends GenericCrudService<ComuniCompetenzaDTO, ComuniCompetenzaSearch, ComuniCompetenzaRepository> implements IComuniCompetenzaService{

    /**
     * dao
     */
    @Autowired
    private ComuniCompetenzaRepository dao;

    /**
     * get dao
     */
    @Override
    protected ComuniCompetenzaRepository getDao(){
        return this.dao;
    }
    /**
     * validate dto for insert
     */
    @Override
    protected void validateInsertDTO(final ComuniCompetenzaDTO entity) throws CustomValidationException{
        if(entity.getPraticaId() == null){
            throw new CustomValidationException("praticaId can't be null");
        }
    }
    /**
     * validate dto for update
     */
    @Override
    protected void validateUpdateDTO(final ComuniCompetenzaDTO entity) throws CustomValidationException{
        if(entity.getPraticaId() == null){
            throw new CustomValidationException("praticaId can't be null");
        }
        if(entity.getDataInserimento() == null){
            throw new CustomValidationException("dataInserimento can't be null");
        }
    }

}
