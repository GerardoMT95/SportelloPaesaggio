package it.eng.tz.puglia.geo.service;

import java.util.List;

import org.postgis.jts.JtsGeometry;

import it.eng.tz.puglia.autpae.controller.exception.CustomOperationToAdviceException;
import it.eng.tz.puglia.error.exception.CustomValidationException;
import it.eng.tz.puglia.geo.dto.GeoDTO;
import it.eng.tz.puglia.geo.repository.EnumNomeVista;
import it.eng.tz.puglia.geo.util.esri.EsriBBox;
import it.eng.tz.puglia.geo.util.esri.EsriQueryResponse;


public interface LayerGeoService {
	
	public Long insert(GeoDTO entity) throws CustomOperationToAdviceException;
	public int delete(GeoDTO entity) throws CustomOperationToAdviceException;
	public EsriQueryResponse queryLayer(String where, EsriBBox bbox,EnumNomeVista vista) throws CustomValidationException;
	public EsriQueryResponse queryExtent(String where) throws CustomValidationException;
	public int update(GeoDTO entity) throws CustomOperationToAdviceException;
	public EsriQueryResponse identify(JtsGeometry geom,EnumNomeVista vista) throws CustomValidationException;
	
	/** inserimento da WKT usato nelle API per iniettare le geometrie di un fascicolo
	 * @autor Adriano Colaianni
	 * @date 11 mag 2021
	 * @param idFascicolo
	 * @param wkt
	 * @return oid assegnata alla shape
	 */
	Long insertFromWkt(Long idFascicolo, String wkt)throws CustomOperationToAdviceException;
	
	/**
	 * 
	 * @autor Adriano Colaianni
	 * @date 11 mag 2021
	 * @param idFascicolo
	 * @return
	 * @throws CustomOperationToAdviceException 
	 */
	int deleteShapeFascicolo(Long idFascicolo) throws CustomOperationToAdviceException;
	List<Long> findShapeFascicolo(Long idFascicolo) throws CustomOperationToAdviceException;
	/**
	 * 
	 * @autor Adriano Colaianni
	 * @date 6 ott 2022
	 * @param wkt
	 * @param idFascicolo
	 * @param isCustom
	 * @param userId
	 * @return
	 */
	public List<Long> insertWkts(final List<String> wkt, final Long idFascicolo, final int isCustom, final String userId);

}
