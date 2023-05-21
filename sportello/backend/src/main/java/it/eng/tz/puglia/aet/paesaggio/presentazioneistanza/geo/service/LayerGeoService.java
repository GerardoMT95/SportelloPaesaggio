package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.geo.service;

import java.util.List;
import java.util.UUID;

import org.postgis.jts.JtsGeometry;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.geo.dto.GeoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.geo.repository.EnumNomeVista;
import it.eng.tz.puglia.error.exception.CustomValidationException;
import it.eng.tz.puglia.geo.util.esri.EsriBBox;
import it.eng.tz.puglia.geo.util.esri.EsriQueryResponse;

public interface LayerGeoService {
	
	public Long insert(GeoDTO entity) throws CustomOperationToAdviceException;
	public int delete(GeoDTO entity) throws CustomOperationToAdviceException;
	public EsriQueryResponse queryLayer(String where, EsriBBox bbox,EnumNomeVista vista) throws CustomValidationException;
	public EsriQueryResponse queryExtent(String where) throws CustomValidationException;
	public int update(GeoDTO entity) throws CustomOperationToAdviceException;
	public EsriQueryResponse identify(JtsGeometry geom,EnumNomeVista vista) throws CustomValidationException;
	/**
	 * rimuove tutte le shape con is_custom=1 oppure (is_custom=0 and oid in oidsToRemove)
	 * @author acolaianni
	 *
	 * @param praticaId
	 * @param oidsToRemove
	 * @return
	 */
	public int removeShape(UUID praticaId,List<Long> oidsToRemove);
	

}
