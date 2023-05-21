package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.DatabaseConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ConfigurazioneCasellaPostaleDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.ConfigurazioneCasellaPostaleRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.ConfigurazioneCasellaPostaleSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.GenericCrudService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.IConfigurazioneCasellaPostaleService;
import it.eng.tz.puglia.error.exception.CustomValidationException;

/**
 * Service CRUD for table presentazione_istanza.configurazione_casella_postale
 */
@Service
public class ConfigurazioneCasellaPostaleService extends GenericCrudService<ConfigurazioneCasellaPostaleDTO, ConfigurazioneCasellaPostaleSearch, ConfigurazioneCasellaPostaleRepository> implements IConfigurazioneCasellaPostaleService{

    /**
     * dao
     */
    @Autowired
    private ConfigurazioneCasellaPostaleRepository dao;

    /**
     * get dao
     */
    @Override
    protected ConfigurazioneCasellaPostaleRepository getDao(){
        return this.dao;
    }
    /**
     * validate dto for insert
     */
    @Override
    protected void validateInsertDTO(final ConfigurazioneCasellaPostaleDTO entity) throws CustomValidationException{
        if(entity.getEmail() == null){
            throw new CustomValidationException("email can't be null");
        }
        if(entity.getConfigurazione() == null){
            throw new CustomValidationException("configurazione can't be null");
        }
    }
    /**
     * validate dto for update
     */
    @Override
    protected void validateUpdateDTO(final ConfigurazioneCasellaPostaleDTO entity) throws CustomValidationException{
        if(entity.getEmail() == null){
            throw new CustomValidationException("email can't be null");
        }
        if(entity.getConfigurazione() == null){
            throw new CustomValidationException("configurazione can't be null");
        }
    }
    
    @Override
	@Transactional(readOnly=true, propagation=Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
    public List<ConfigurazioneCasellaPostaleDTO> getCaselleDaDisattivare(){
    	
		return this.dao.getCaselleDaDisattivare();
    	
    }
    
    @Override
   	@Transactional(readOnly=true, propagation=Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
   public List<ConfigurazioneCasellaPostaleDTO> getCaselleDaAttivare(){
   	
    	return this.dao.getCaselleDaAttivare();
   	
   }
   	@Transactional(readOnly=true, propagation=Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public long insertOrUpdate(ConfigurazioneCasellaPostaleDTO ccpDTO) {
		
		
   		int res=this.dao.update(ccpDTO);
		if(res==0) {
			Long result=this.dao.insert(ccpDTO);
			return result;
		}else {
			return res;
		}
		
	}

}
