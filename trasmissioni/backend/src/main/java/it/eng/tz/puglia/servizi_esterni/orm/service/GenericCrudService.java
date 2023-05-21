package it.eng.tz.puglia.servizi_esterni.orm.service;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import it.eng.tz.puglia.autpae.search.generic.Paging;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.servizi_esterni.orm.repository.IGenericCrudDao;
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
public abstract class GenericCrudService<T extends Serializable, S extends Paging, PK extends Serializable, DAO extends IGenericCrudDao<T,S,PK>> 
implements ICrudService<T, S, PK, DAO>{

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
	 * @date 29 lug 2019
	 * @see it.eng.tz.puglia.servizi_esterni.orm.service.ICrudService#select()
	 */
	@Override
	@Transactional(readOnly=true, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public List<T> select() throws Exception{
		String id = "select";
		StopWatch sw = this.logStart(id);
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
	 * @date 29 lug 2019
	 * @see it.eng.tz.puglia.aet.jdbc.service.ICrudService#count()
	 */
	@Override
	@Transactional(readOnly=true, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public long count(S filter) throws Exception{
		String id = "count";
		StopWatch sw = this.logStart(id);
		try {
			return this.getDao().count(filter);
		}catch(Exception e) {
			logger.error(StringUtil.concateneString("Error in ", id), e);
			throw new Exception(e);
		}finally {
			this.logEnd(sw);
		}
	}

	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @see it.eng.tz.puglia.aet.jdbc.service.ICrudService#find(java.io.Serializable)
	 */
	@Override
	@Transactional(readOnly=true, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public T find(PK pk) throws Exception{
		String id = "find";
		StopWatch sw = this.logStart(id);
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
	 * @date 29 lug 2019
	 * @see it.eng.tz.puglia.servizi_esterni.orm.service.ICrudService#search(java.io.Serializable, int, int)
	 */
	@Override
	@Transactional(readOnly=true, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public PaginatedList<T> search(S search) throws Exception{
		String id = "search";
		StopWatch sw = this.logStart(id);
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
	 * @date 29 lug 2019
	 * @see it.eng.tz.puglia.aet.jdbc.service.ICrudService#insert(java.io.Serializable)
	 */
	@Override
	@Transactional(readOnly=false,rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public PK insert(T entity) throws Exception{
		String id = "insert";
		StopWatch sw = this.logStart(id);
		try {
			return this.getDao().insert(entity);
		}catch(Exception e) {
			logger.error(StringUtil.concateneString("Error in ", id), e);
			throw new Exception(e);
		}finally {
			this.logEnd(sw);
		}
	}

	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @see it.eng.tz.puglia.aet.jdbc.service.ICrudService#update(java.io.Serializable)
	 */
	@Override
	@Transactional(readOnly=false,rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public int update(T entity) throws Exception{
		String id = "update";
		StopWatch sw = this.logStart(id);
		try {
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
	 * @date 29 lug 2019
	 * @see it.eng.tz.puglia.aet.jdbc.service.ICrudService#delete(java.io.Serializable)
	 */
	@Override
	@Transactional(readOnly=false,rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public int delete(S entity) throws Exception{
		String id = "delete";
		StopWatch sw = this.logStart(id);
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
