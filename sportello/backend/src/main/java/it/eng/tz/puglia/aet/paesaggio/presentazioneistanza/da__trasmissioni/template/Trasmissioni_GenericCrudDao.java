package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.da__trasmissioni.template;

import java.io.Serializable;

/**
 * Mother class for all repository crud
 * @author Antonio La Gatta
 * @date 29 lug 2019
 * @param <T>
 * @param <S>
 * @param <PK>
 */
public abstract class Trasmissioni_GenericCrudDao<T  extends Serializable, 
												  S  extends Trasmissioni_Paging, 
												  PK extends Serializable> 
																				 extends 	Trasmissioni_GenericDao 
																				 implements Trasmissioni_IGenericCrudDao<T, S, PK> {

}