package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.impl;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.*;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.eng.tz.puglia.error.exception.CustomValidationException;

/**
 * Service CRUD for table presentazione_istanza.referenti
 */
@Service
public class RicevutaService extends GenericCrudService<RicevutaDTO, RicevutaSearch, RicevutaRepository>
		implements IRicevutaService {

	/**
	 * dao
	 */
	@Autowired
	private RicevutaRepository dao;

	/**
	 * get dao
	 */
	@Override
	protected RicevutaRepository getDao() {
		return this.dao;
	}

	@Override
	protected void validateInsertDTO(RicevutaDTO entity) throws CustomValidationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validateUpdateDTO(RicevutaDTO entity) throws CustomValidationException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * validate dto for insert
	 */
	

}
