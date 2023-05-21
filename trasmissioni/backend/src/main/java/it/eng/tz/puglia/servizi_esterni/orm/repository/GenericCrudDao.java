package it.eng.tz.puglia.servizi_esterni.orm.repository;

import java.io.Serializable;

import it.eng.tz.puglia.autpae.search.generic.Paging;

/**
 * Mother class for all repository crud
 * @author Antonio La Gatta
 * @date 29 lug 2019
 * @param <T>
 * @param <S>
 * @param <PK>
 */
public abstract class GenericCrudDao<T extends Serializable, S  extends Paging, PK extends Serializable> extends GenericDao implements IGenericCrudDao<T, S, PK>{
	

}
