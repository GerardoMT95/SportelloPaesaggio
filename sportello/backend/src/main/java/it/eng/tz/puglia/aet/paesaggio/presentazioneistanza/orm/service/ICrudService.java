package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service;

import java.io.Serializable;
import java.util.List;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.DatabaseConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.IGenericCrudDao;
import it.eng.tz.puglia.bean.BaseSearchRequestBean;
import it.eng.tz.puglia.bean.PaginatedList;

/**
 * Interface for crud service
 * @author Antonio La Gatta
 * @date 29 lug 2019
 * @param <T>
 * @param <S>
 * @param <DAO>
 */
public interface ICrudService<T extends Serializable, S extends BaseSearchRequestBean, DAO extends IGenericCrudDao<T, S>> {
	
	String TX_READ = DatabaseConfiguration.TX_READ;
	String TX_WRITE = DatabaseConfiguration.TX_WRITE;

	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @return list of object
	 * @throws SitPugliaSQLException;
	 */
	List<T> select() throws Exception;
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @return count of table
	 * @throws Exception;
	 */
	long count() throws Exception;
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @return find by pk
	 * @throws Exception;
	 */
	T find(T pk) throws Exception;
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param search
	 * @param page
	 * @param limit
	 * @return risultato ricerca
	 * @throws Exception
	 */
	PaginatedList<T> search(S search) throws Exception;
	/**
	 * Insert record
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param entity
	 * @return if serial return new id otherwise rows affected
	 * @throws Exception;
	 */
	long insert(T entity) throws Exception;
	/**
	 * Update record
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param entity
	 * @return rows affected
	 * @throws Exception;
	 */
	int update(T entity) throws Exception;
	/**
	 * Delete record
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param entity
	 * @return rows affected
	 * @throws Exception;
	 */
	int delete(T entity) throws Exception;

}
