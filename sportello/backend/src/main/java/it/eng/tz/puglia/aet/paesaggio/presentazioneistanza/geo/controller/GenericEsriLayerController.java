package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.geo.controller;

import java.util.Arrays;
import java.util.UUID;

import org.postgis.jts.JtsGeometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.MapGeometry;
import com.esri.core.geometry.OperatorImportFromJson;
import com.esri.core.geometry.SpatialReference;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.geo.dto.GeoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.geo.repository.EnumNomeVista;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.geo.service.LayerGeoService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils.Logging;
import it.eng.tz.puglia.controller.BaseRestController;
import it.eng.tz.puglia.error.exception.CustomOperationException;
import it.eng.tz.puglia.geo.util.GeometryUtils;
import it.eng.tz.puglia.geo.util.esri.EsriBBox;
import it.eng.tz.puglia.geo.util.esri.EsriBBoxEditor;
import it.eng.tz.puglia.geo.util.esri.EsriGeometryWithAttribute;
import it.eng.tz.puglia.geo.util.esri.EsriGeometryWithAttributeEditor;
import it.eng.tz.puglia.geo.util.esri.EsriIdentifyResponse;
import it.eng.tz.puglia.geo.util.esri.EsriQueryResponse;
import it.eng.tz.puglia.geo.util.esri.EsriResponse;
import it.eng.tz.puglia.security.util.SecurityUtil;
import it.eng.tz.puglia.util.string.StringUtil;

public abstract class GenericEsriLayerController extends BaseRestController {
	
	protected abstract EnumNomeVista getVista();
	protected abstract String getLayerId();
	protected abstract String getLayerName();
	protected abstract String getLayerDescription();
	protected abstract String getLayerDescriptionEstesa();
	protected abstract String getColoreArea();
	
	final String pathLayer="/{layerName}/{layerID}";
	
	@Autowired
	private LayerGeoService geoService;
	
	protected void canEdit() throws CustomOperationException{};
	
	private void checkMyLayer(String layerNamePar,String layerIdPar) throws Exception {
		if(!(layerNamePar!=null && layerNamePar.equals(this.getLayerName()) && 
			 layerIdPar!=null && layerIdPar.equals(this.getLayerId()))) 
				throw new Exception("Layer Not found !!!");
	}

	@RequestMapping(value = pathLayer, produces = "text/javascript")
	public String getInfo(@RequestParam(name = "f", required = false, defaultValue = "json") String f,
			@RequestParam(name="callback", required = false) String callback) {
		String ret= "{\"currentVersion\":10.41,\"id\":"+getLayerId()+",\"name\":\""+getLayerDescription()
				+ "\",\"type\":\"Feature Layer\",\"description\":\""+getLayerDescriptionEstesa()
				+"\",\"copyrightText\":\"\",\"defaultVisibility\":true,\"editFieldsInfo\":null,"
				+ "\"ownershipBasedAccessControlForFeatures\":null,\"syncCanReturnChanges\":false,"
				+ "\"relationships\":[],\"isDataVersioned\":false,\"supportsRollbackOnFailureParameter\":true,"
				+ "\"supportsStatistics\":true,\"supportsAdvancedQueries\":true,\"supportsValidateSQL\":true,"
				+ "\"supportsCalculate\":true,\"advancedQueryCapabilities\":{\"supportsPagination\":true,"
				+ "\"supportsQueryWithDistance\":true,\"supportsReturningQueryExtent\":true,\"supportsStatistics\":true,"
				+ "\"supportsOrderBy\":true,\"supportsDistinct\":true},\"geometryType\":\"esriGeometryPolygon\","
				+ "\"minScale\":0,\"maxScale\":0,\"extent\":{\"xmin\":475931,\"ymin\":4386719,\"xmax\":814598,\"ymax\":4662064,"
				+ "\"spatialReference\":{\"wkid\":32633,\"latestWkid\":32633}},"
				+ "\"drawingInfo\":{"
				+ "\"renderer\":"
				+ "{\"type\":\"simple\","
				+ "\"symbol\":{"
				+ "\"type\":\"esriSFS\","
				+ "\"style\":\"esriSFSSolid\","
				+ "\"color\":["+this.getColoreArea()+"],"
				+ "\"outline\":{"
				+ "\"type\":\"esriSLS\","
				+ "\"style\":\"esriSLSSolid\","
				+ "\"color\":[110,110,110,255],"
				+ "\"width\":0.4}},"
				+ "\"label\":\"\",\"description\":\"\"},"
				+ "\"transparency\":0,\"labelingInfo\":null},"
				+ "\"hasM\":false,\"hasZ\":false,"
				+ "\"allowGeometryUpdates\":true,"
				+ "\"hasAttachments\":false,\"supportsApplyEditsWithGlobalIds\":false,"
				+ "\"htmlPopupType\":\"esriServerHTMLPopupTypeAsHTMLText\","
				+ "\"objectIdField\":\"oid\",\"globalIdField\":\"\","
				+ "\"displayField\":\"id_fascicolo\",\"typeIdField\":\"\","
				+ "\"fields\":["
				+ "{\"name\":\"oid\",\"type\":\"esriFieldTypeOID\",\"alias\":\"oid\",\"domain\":null,\"editable\":false,\"nullable\":false},"
				+ "{\"name\":\"id_fascicolo\",\"type\":\"esriFieldTypeString\",\"alias\":\"id_fascicolo\",\"domain\":null,\"editable\":true,\"nullable\":false,\"length\":100},"
				+ "{\"name\":\"user_id\",\"type\":\"esriFieldTypeString\",\"alias\":\"user_id\",\"domain\":null,\"editable\":true,\"nullable\":false,\"length\":100},"
				+ "{\"name\":\"user_fk\",\"type\":\"esriFieldTypeInteger\",\"alias\":\"user_fk\",\"domain\":null,\"editable\":true,\"nullable\":true},"
				+ "{\"name\":\"date_created\",\"type\":\"esriFieldTypeDate\",\"alias\":\"date_created\",\"domain\":null,\"editable\":true,\"nullable\":true,\"length\":8},"
				+ "{\"name\":\"date_updated\",\"type\":\"esriFieldTypeDate\",\"alias\":\"date_updated\",\"domain\":null,\"editable\":true,\"nullable\":true,\"length\":8},"
				+ "{\"name\":\"is_custom\",\"type\":\"esriFieldTypeInteger\",\"alias\":\"is_custom\",\"domain\":null,\"editable\":true,\"nullable\":true}],"
				+ "\"indexes\":[{\"name\":\"r5027_sde_rowid_uk\",\"fields\":\"oid\",\"isAscending\":true,\"isUnique\":true,\"description\":\"\"},"
				+ "{\"name\":\"idx_rr242005_fasc_geom_autocert_rr2405_id_fascicolo\",\"fields\":\"id_fascicolo\",\"isAscending\":true,\"isUnique\":false,\"description\":\"\"},"
				+ "{\"name\":\"idx_rr242005_fasc_geom_autocert_rr2405_user_fk\",\"fields\":\"user_fk\",\"isAscending\":true,\"isUnique\":false,\"description\":\"\"},"
				+ "{\"name\":\"idx_rr242005_fasc_geom_autocert_rr2405_user_id\",\"fields\":\"user_id\",\"isAscending\":true,\"isUnique\":false,\"description\":\"\"},"
				+ "{\"name\":\"a3787_ix1\",\"fields\":\"geometry\",\"isAscending\":true,\"isUnique\":true,\"description\":\"\"}],\"dateFieldsTimeReference\":{\"timeZone\":\"UTC\",\"respectsDaylightSaving\":false},\"types\":[],"
				+ "\"templates\":[{\"name\": \""+this.getLayerDescription()+"\","
				+ "\"description\":\"\",\"prototype\":"
				+ "{\"attributes\":"
				+ "{"
				+ "\"date_updated\":null,"
				+ "\"is_custom\":0,"
				+ "\"id_fascicolo\":\" \","
				+ "\"codice\":\"\","
				+ "\"user_id\":\" \","
				+ "\"user_fk\":null,"
				+ "\"date_created\":null,"
				+"\"name\":null}},\"drawingTool\":\"esriFeatureEditToolPolygon\"}],\"maxRecordCount\":1000,"
				+ "\"supportedQueryFormats\":\"JSON, AMF, geoJSON\",\"capabilities\":"
				+ "\"Query,Create,Update,Delete,Uploads,Editing\",\"useStandardizedQueries\":true}";
		if(StringUtil.isNotEmpty(callback)) {
			ret=callback+"("+ret+");";
		}
		
	return ret;
	}

	
	/**
	 * 
	 * @param f
	 * @param geom
	 * @throws Exception
	 */
	@Logging
	@RequestMapping(value = pathLayer+"/applyEdits", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded;charset=ISO-8859-1")
	public @ResponseBody EsriResponse applyEdits(
			@RequestParam(name = "f", required = false, defaultValue = "json") String f,
			@RequestParam(name = "adds", required = false) EsriGeometryWithAttribute[] adds,
			@RequestParam(name = "updates", required = false) EsriGeometryWithAttribute[] updates,
			@RequestParam(name = "deletes", required = false) String deletes) throws Exception {
		this.canEdit();
		EsriResponse ret = new EsriResponse();
		if (!StringUtil.isEmpty(deletes)) {
			System.out.println("deletes" + deletes);
			ret.addDelete(deletes);
		}
		if (adds != null && adds.length > 0) { // inserisci la geometria
			for (EsriGeometryWithAttribute geometria : Arrays.asList(adds)) {
				MapGeometry geo = geometria.getGeometry();
				// converto in dto
				GeoDTO pevgeo = new GeoDTO();
				pevgeo.setGeometry(GeometryUtils.esriGeometryToJTSGeometry(geo));
				
				String idFascicolo = ((String) geometria.getAttribute("id_fascicolo"));
				Integer isCustom = (Integer)geometria.getAttribute("is_custom");
				pevgeo.setIdFascicolo(UUID.fromString(idFascicolo));
				pevgeo.setIsCustom(isCustom);
				pevgeo.setUserId(SecurityUtil.getUsername());
				Long key = geoService.insert(pevgeo);
				ret.addResult("" + key);
			}
		}
		if (updates != null && updates.length > 0) {// aggiorna
			for (EsriGeometryWithAttribute geom : Arrays.asList(updates)) {
				MapGeometry geo = geom.getGeometry();
				GeoDTO pevgeo = new GeoDTO();
				Object oid=geom.getAttribute("oid");
				Integer gid=null;
				if( oid instanceof Integer) {
					 gid =  (Integer)oid;	
				}else {
					gid=Integer.parseInt((String)oid);
				}
				pevgeo.setOid(gid);
				pevgeo.setUpdateUserId(SecurityUtil.getUsername());
				pevgeo.setGeometry(GeometryUtils.esriGeometryToJTSGeometry(geo));
				geoService.update(pevgeo);
				ret.addUpdate("" + pevgeo.getOid());
			}
		}
		if (!StringUtil.isEmpty(deletes)) { //mi arrivano anche con separatore ,
			// elimina oid
			String[] oids = deletes.split(",");
			if(oids!=null && oids.length>0) {
				for(String oid:oids) {
				GeoDTO pevgeo = new GeoDTO();
				pevgeo.setOid(Long.parseLong(oid));
				geoService.delete(pevgeo);
				}
			}
		}
		return ret;
	}

	@Logging
	@RequestMapping(value = pathLayer+"/query", method = RequestMethod.GET, consumes = "*/*")
	public @ResponseBody EsriQueryResponse query(
			@PathVariable("layerName") String layerNamePar,
			@PathVariable("layerID") String layerIdPar,
			@RequestParam(name = "returnExtentOnly", required = false, defaultValue = "false") boolean returnExtentOnly,
			@RequestParam(name = "returnCountOnly", required = false, defaultValue = "false") boolean returnCountOnly,
			@RequestParam(name = "returnGeometry", required = false, defaultValue = "true") boolean returnGeometry,
			@RequestParam(name = "where", required = false, defaultValue = "1=1") String where,
			@RequestParam(name = "geometry", required = false) EsriBBox bbox) throws Exception {
		checkMyLayer(layerNamePar, layerIdPar);
		EsriQueryResponse response = new EsriQueryResponse();
		if (returnExtentOnly) {
			response=geoService.queryExtent(where);
			return response;
		}
		response=geoService.queryLayer(where, bbox,this.getVista());
		if(response.getFeatures()!=null) {
			addLayerInfo(response);
		}
		return response;

	}
	
	@Logging
	@RequestMapping(value = pathLayer+"/identify", method = RequestMethod.GET, consumes = "*/*")
	public @ResponseBody EsriIdentifyResponse identify(
			@PathVariable("layerName") String layerNamePar,
			@PathVariable("layerID") String layerIdPar,
			@RequestParam(name = "returnGeometry", required = false, defaultValue = "true") boolean returnGeometry,
			@RequestParam(name = "sr", required = false, defaultValue = "true") String sr, //32633
			@RequestParam(name = "geometryType", required = false, defaultValue = "true") String geometryType,
			@RequestParam(name = "geometry", required = false) String geometryJson) throws Exception {
		checkMyLayer(layerNamePar, layerIdPar);
		EsriIdentifyResponse response = new EsriIdentifyResponse();
		MapGeometry geom = OperatorImportFromJson.local().execute(Geometry.Type.Unknown, geometryJson);
		if(geom.getSpatialReference()==null && StringUtil.isNotEmpty(sr)) {
			geom.setSpatialReference(SpatialReference.create(Integer.parseInt(sr)));
		}
		JtsGeometry jtsGeom = GeometryUtils.esriGeometryToJTSGeometry(geom);
		EsriQueryResponse ret = geoService.identify(jtsGeom,this.getVista());
		if(ret!=null) {
			addLayerInfo(ret);
			response.setResults(ret.getFeatures());	
		}
		return response;
	}

	protected void addLayerInfo(EsriQueryResponse ret) {
		ret.getFeatures().forEach(esriGeom->{
			esriGeom.setLayerID(Integer.parseInt(this.getLayerId()));
			esriGeom.setLayerName(this.getLayerDescription());
			esriGeom.setDisplayFieldName("codice");
			esriGeom.setValue((String)esriGeom.getAttribute("codice"));
		});
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(EsriGeometryWithAttribute[].class, new EsriGeometryWithAttributeEditor());
		binder.registerCustomEditor(EsriBBox.class, new EsriBBoxEditor());
	}

}