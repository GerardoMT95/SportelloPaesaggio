package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.template;

import java.io.Serializable;
import java.util.List;

import it.eng.tz.puglia.bean.PaginatedList;

/**
 * Interface for crud
 * @author Antonio La Gatta
 * @date 29 lug 2019
 * @param <T> dto legato all'entità
 * @param <S> classe di ricerca
 * @param <PK> tipo chiave primaria
 */
public interface Trasmissioni_IGenericCrudDao<T  extends Serializable, 
											  S  extends Trasmissioni_Paging, 
											  PK extends Serializable> 		 {
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @return list of all objects
	 */
	public abstract List<T> select() throws Exception;
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @return count
	 */
	
		
	public abstract long count(S filter) throws Exception;
	/**
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param pk
	 * @return entity for pk
	 */
	public abstract T find(PK pk) throws Exception;
	/**
	 * Insert
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param entity
	 * @return number of rows affected
	 */
	public abstract PK insert(T entity) throws Exception;
	/**
	 * Update
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param entity
	 * @return number of rows affected
	 */
	public abstract int update(T entity) throws Exception;
	/**
	 * Delete
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param entity
	 * @return number of rows affected
	 */
	public abstract int delete(S entity) throws Exception;
	/**
	 * Search
	 * @author Antonio La Gatta
	 * @date 29 lug 2019
	 * @param search
	 * @param page
	 * @param limit
	 * @return number of rows affected
	 */
	public abstract PaginatedList<T> search(S bean) throws Exception;
}
