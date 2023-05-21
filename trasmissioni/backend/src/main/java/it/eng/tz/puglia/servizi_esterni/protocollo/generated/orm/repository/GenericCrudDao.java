package it.eng.tz.puglia.servizi_esterni.protocollo.generated.orm.repository;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Mother class for all repository crud
 * @author Antonio La Gatta
 * @date 29 lug 2019
 * @param <T>
 * @param <S>
 */
public abstract class GenericCrudDao<T extends Serializable, S extends BaseSearchRequestBean> extends GenericDao implements IGenericCrudDao<T, S>{
	/**
	 * logger
	 */
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * @author Antonio La Gatta
	 * @date 20 gen 2020
	 * @param sql
	 * @param parameters
	 * @param idColumn
	 * @return new id value
	 */
	public long insertAndGetAutoincrementValue(String sql, List<Object> parameters, String idColumn) {
		final KeyHolder keyHolder = new GeneratedKeyHolder();
		final int rows = super.jdbcTemplateWrite.update(new InsertPreparedStatementCreator(sql, parameters, idColumn),keyHolder);
		if(logger.isDebugEnabled())
			logger.debug(StringUtil.concateneString("Insert ", rows, " rows"));
		final long id = keyHolder.getKey().longValue();
		logger.info(StringUtil.concateneString("New Id: ", id));
		return id;
	}
	
}