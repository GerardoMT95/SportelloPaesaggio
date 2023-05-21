package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.IGenericCrudDao;
import it.eng.tz.puglia.bean.BaseSearchRequestBean;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.error.exception.CustomValidationException;
import it.eng.tz.puglia.util.log.LogUtil;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Generic class for crud service
 * @author Antonio La Gatta
 * @date 29 lug 2019
 * @param <T>
 * @param <S>
 * @param <DAO>
 */
public abstract class GenericCrudService<T extends Serializable, S extends BaseSearchRequestBean, DAO extends IGenericCrudDao<T,S>> 
implements ICrudService<T, S, DAO>{

	/**
	 * logger
	 */
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @return Dao instance associated
	 */
	protected abstract DAO getDao();
	/**
	 * Validate dto for insert
	 * @author Antonio La Gatta
	 * @date 5 set 2019
	 * @param dto
	 * @throws CustomValidationException
	 */
	protected abstract void validateInsertDTO(T entity) throws CustomValidationException;
	/**
	 * Validate dto for update
	 * @author Antonio La Gatta
	 * @date 5 set 2019
	 * @param dto
	 * @throws CustomValidationException
	 */
	protected abstract void validateUpdateDTO(T entity) throws CustomValidationException;
	/**
	 * Log start and init watch
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @return {@link StopWatch}
	 */
	protected StopWatch logStart(String id) {
		logger.info(StringUtil.concateneString("Start ", id));
		return LogUtil.startLog(id);
	}
	/**
	 * Stop and log end watch
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param sw
	 */
	protected void logEnd(StopWatch sw) {
		logger.info(LogUtil.stopLog(sw));
	}
	/**
	 * @author Antonio La Gatta
	 * @date 20 gen 2020
	 * @see it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.ICrudService#select()
	 */
	@Override
	@Transactional(readOnly=true, propagation=Propagation.REQUIRED, transactionManager = TX_READ)
	public List<T> select() throws Exception{
		final String id = "select";
		final StopWatch sw = this.logStart(id);
		try {
			return this.getDao().select();
		}catch(Exception e) {
			logger.error(StringUtil.concateneString("Error in ", id), e);
			throw new Exception(e);
		}finally {
			this.logEnd(sw);
		}
	}
	/**
	 * @author Antonio La Gatta
	 * @date 20 gen 2020
	 * @see it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.ICrudService#count()
	 */
	@Override
	@Transactional(readOnly=true, propagation=Propagation.REQUIRED, transactionManager = TX_READ)
	public long count() throws Exception{
		final String id = "count";
		final StopWatch sw = this.logStart(id);
		try {
			return this.getDao().count();
		}catch(Exception e) {
			logger.error(StringUtil.concateneString("Error in ", id), e);
			throw new Exception(e);
		}finally {
			this.logEnd(sw);
		}
	}
	/**
	 * @author Antonio La Gatta
	 * @date 20 gen 2020
	 * @see it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.ICrudService#find(java.io.Serializable)
	 */
	@Override
	@Transactional(readOnly=true, propagation=Propagation.REQUIRED, transactionManager = TX_READ)
	public T find(T pk) throws Exception{
		final String id = "find";
		final StopWatch sw = this.logStart(id);
		try {
			return this.getDao().find(pk);
		}catch(Exception e) {
			logger.error(StringUtil.concateneString("Error in ", id), e);
			throw new Exception(e);
		}finally {
			this.logEnd(sw);
		}
	}
	/**
	 * @author Antonio La Gatta
	 * @date 20 gen 2020
	 * @see it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.ICrudService#search(it.eng.tz.puglia.bean.BaseSearchRequestBean)
	 */
	@Override
	@Transactional(readOnly=true, propagation=Propagation.REQUIRED, transactionManager = TX_READ)
	public PaginatedList<T> search(S search) throws Exception{
		final String id = "search";
		final StopWatch sw = this.logStart(id);
		try {
			return this.getDao().search(search);
		}catch(Exception e) {
			logger.error(StringUtil.concateneString("Error in ", id), e);
			throw new Exception(e);
		}finally {
			this.logEnd(sw);
		}
	}
	/**
	 * @author Antonio La Gatta
	 * @date 20 gen 2020
	 * @see it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.ICrudService#insert(java.io.Serializable)
	 */
	@Override
	@Transactional(readOnly=false,rollbackFor=Exception.class,propagation=Propagation.REQUIRED, transactionManager = TX_WRITE)
	public long insert(T entity) throws Exception{
		final String id = "insert";
		final StopWatch sw = this.logStart(id);
		try {
			this.validateInsertDTO(entity);
			return this.getDao().insert(entity);
		}catch(Exception e) {
			logger.error(StringUtil.concateneString("Error in ", id), e);
			throw new Exception(e);
		}finally {
			this.logEnd(sw);
		}
	}
	@Override
	@Transactional(readOnly=false,rollbackFor=Exception.class,propagation=Propagation.REQUIRED, transactionManager = TX_WRITE)
	public int update(T entity) throws Exception{
		final String id = "update";
		final StopWatch sw = this.logStart(id);
		try {
			this.validateUpdateDTO(entity);
			return this.getDao().update(entity);
		}catch(Exception e) {
			logger.error(StringUtil.concateneString("Error in ", id), e);
			throw new Exception(e);
		}finally {
			this.logEnd(sw);
		}
	}
	/**
	 * @author Antonio La Gatta
	 * @date 20 gen 2020
	 * @see it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.ICrudService#delete(java.io.Serializable)
	 */
	@Override
	@Transactional(readOnly=false,rollbackFor=Exception.class,propagation=Propagation.REQUIRED, transactionManager = TX_WRITE)
	public int delete(T entity) throws Exception{
		final String id = "delete";
		final StopWatch sw = this.logStart(id);
		try {
			return this.getDao().delete(entity);
		}catch(Exception e) {
			logger.error(StringUtil.concateneString("Error in ", id), e);
			throw new Exception(e);
		}finally {
			this.logEnd(sw);
		}
	}
	
}
