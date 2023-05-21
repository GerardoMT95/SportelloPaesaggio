package it.eng.tz.puglia.servizi_esterni.protocollo.generated.orm.repository;

import java.io.Serializable;
import java.util.List;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;
import it.eng.tz.puglia.bean.PaginatedList;

/**
 * Interface for crud
 * @author Antonio La Gatta
 * @date 29 lug 2019
 * @param <T>
 */
public interface IGenericCrudDao<T extends Serializable, S extends BaseSearchRequestBean>{
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @return list of all objects
	 */
	public abstract List<T> select();
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @return count
	 */
	public abstract long count();
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param pk
	 * @return entity for pk
	 */
	public abstract T find(T pk);
	/**
	 * Insert
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param entity
	 * @return if serial return new id otherwisw number of rows affected
	 */
	public abstract long insert(T entity);
	/**
	 * Update
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param entity
	 * @return number of rows affected
	 */
	public abstract int update(T entity);
	/**
	 * Delete
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param entity
	 * @return number of rows affected
	 */
	public abstract int delete(T entity);
	/**
	 * Search
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param search
	 * @param page
	 * @param limit
	 * @return number of rows affected
	 */
	public abstract PaginatedList<T> search(S bean);
}
