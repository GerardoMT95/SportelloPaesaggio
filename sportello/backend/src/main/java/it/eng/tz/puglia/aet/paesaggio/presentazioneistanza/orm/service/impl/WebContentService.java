package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.impl;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.*;
import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.error.exception.CustomValidationException;

/**
 * Service CRUD for table presentazione_istanza.web_content
 */
@Service
public class WebContentService extends GenericCrudService<WebContentDTO, WebContentSearch, WebContentRepository> implements IWebContentService{

    /**
     * dao
     */
    @Autowired
    private WebContentRepository dao;

    /**
     * get dao
     */
    @Override
    protected WebContentRepository getDao(){
        return this.dao;
    }
    /**
     * validate dto for insert
     */
    @Override
    protected void validateInsertDTO(final WebContentDTO entity) throws CustomValidationException{
        if(entity.getCodiceContenuto() == null){
            throw new CustomValidationException("codiceContenuto can't be null");
        }
    }
    /**
     * validate dto for update
     */
    @Override
    protected void validateUpdateDTO(final WebContentDTO entity) throws CustomValidationException{
        if(entity.getCodiceContenuto() == null){
            throw new CustomValidationException("codiceContenuto can't be null");
        }
    }
    
	@Override
	@Transactional(readOnly=false,rollbackFor=Exception.class,propagation=Propagation.REQUIRED, transactionManager = TX_WRITE)
	public Integer updateSelected(List<WebContentDTO> dtos) {
		int ret=0;
		if(ListUtil.isNotEmpty(dtos)) {
			for(WebContentDTO dto:dtos) {
				WebContentDTO dtoOld = this.getDao().find(dto);
				dtoOld.setContenuto(dto.getContenuto());
				ret=this.getDao().update(dtoOld);
			}
		}
		return ret;
	}

}
