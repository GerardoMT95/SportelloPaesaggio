package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.impl;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import it.eng.tz.puglia.error.exception.CustomValidationException;
import it.eng.tz.puglia.util.log.LogUtil;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Service CRUD for table presentazione_istanza.numero_pratica
 */
@Service
public class NumeroPraticaService
		extends GenericCrudService<NumeroPraticaDTO, NumeroPraticaSearch, NumeroPraticaRepository>
		implements INumeroPraticaService {

	private static final int PREFIX_COD_PRATICA = 0;
	/**
	 * dao
	 */
	@Autowired
	private NumeroPraticaRepository dao;

	/**
	 * get dao
	 */
	@Override
	protected NumeroPraticaRepository getDao() {
		return this.dao;
	}

	/**
	 * validate dto for insert
	 */
	@Override
	protected void validateInsertDTO(final NumeroPraticaDTO entity) throws CustomValidationException {
	}

	/**
	 * validate dto for update
	 */
	@Override
	protected void validateUpdateDTO(final NumeroPraticaDTO entity) throws CustomValidationException {
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = TX_WRITE)
	public long initNumPraticaAnnoCorrente() {
		return this.getDao().initNumPraticaAnnoCorrente();
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = TX_WRITE)
	public long checkNumPraticaAnnoCorrente() {
		return this.getDao().checkNumPraticaAnnoCorrente();
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = TX_WRITE)
	public NumeroPraticaDTO getOldNumeroPratica() {
		return this.getDao().getNumeroPratica();
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = TX_WRITE)
	public long insertNumeroPratica(Long nextNum) {
		return this.getDao().insertNumeroPratica(nextNum);
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = TX_WRITE)
	public long avanzaNumPraticaAnno(int anno, int numero) {
		long ret = 0;
		try {
			this.getDao().esisteNumeroPraticaAnno(anno, numero);
		} catch (EmptyResultDataAccessException e) {
			ret = this.getDao().avanzaNumPraticaAnno(anno, numero);
		}
		return ret;
	}

}
