package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainStringValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TipoProcedimentoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.TipoProcedimentoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.TipoProcedimentoSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.GenericCrudService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.ITipoProcedimentoService;
import it.eng.tz.puglia.error.exception.CustomValidationException;

/**
 * Service CRUD for table presentazione_istanza.tipo_procedimento
 */
@Service
public class TipoProcedimentoService extends GenericCrudService<TipoProcedimentoDTO, TipoProcedimentoSearch, TipoProcedimentoRepository> implements ITipoProcedimentoService{

    /**
     * dao
     */
    @Autowired
    private TipoProcedimentoRepository dao;

    /**
     * get dao
     */
    @Override
    protected TipoProcedimentoRepository getDao(){
        return this.dao;
    }
    /**
     * validate dto for insert
     */
    @Override
    protected void validateInsertDTO(final TipoProcedimentoDTO entity) throws CustomValidationException{
        if(entity.getCodice() == null){
            throw new CustomValidationException("codice can't be null");
        }
    }
    /**
     * validate dto for update
     */
    @Override
    protected void validateUpdateDTO(final TipoProcedimentoDTO entity) throws CustomValidationException{
        if(entity.getCodice() == null){
            throw new CustomValidationException("codice can't be null");
        }
    }
    
    @Override
	public List<PlainStringValueLabel> selectTipoProcedimenti() {
    	List<TipoProcedimentoDTO> list = this.dao.select();
    	if(list != null && list.size() > 0) {
        	List<PlainStringValueLabel> select = new ArrayList<PlainStringValueLabel>();
    		for(TipoProcedimentoDTO tipo : list) {
    			PlainStringValueLabel option = new PlainStringValueLabel();
    			option.setDescription(tipo.getDescrizione());
    			option.setValue(String.valueOf(tipo.getId()));
    			select.add(option);
    		}
    		return select;
    	}
    	return null;
    }

    
    

}
