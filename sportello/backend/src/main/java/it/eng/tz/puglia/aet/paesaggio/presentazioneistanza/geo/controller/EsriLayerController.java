package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.geo.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.ApplicationProperties;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.geo.repository.EnumNomeVista;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.geo.service.LayerGeoService;


@RestController
@RequestMapping(value = "/esriEditing")
public class EsriLayerController extends GenericEsriLayerController{
	private String layerID="0";
	private String layerName;
	private String layerDescription;
	private String layerDescrizioneEstesa;
	private String coloreArea;
	
//	final  String layerName="PresentazioneIstanzaEditing";
//	final  String layerDescription="Istanze autorizzazioni paesaggistiche";
	
	@Autowired
	private ApplicationProperties props;
	
	@PostConstruct
	public void initLayerInfo() {
		this.layerName="PresentazioneIstanza"+"Editing";
		this.layerDescription=props.getLayerEditingDes();
		this.layerDescrizioneEstesa=props.getLayerEditingDesEst();
		this.coloreArea=props.getLayerEditingCol();
	}
	
	@Override
	protected EnumNomeVista getVista() {
		return EnumNomeVista.EDITING;
	}

	@Override
	protected String getLayerId() {
		return this.layerID;
	}

	@Override
	protected String getLayerName() {
		return this.layerName;
	}

	@Override
	protected String getLayerDescription() {
		return this.layerDescription;
	}

	@Override
	protected String getLayerDescriptionEstesa() {
		return this.layerDescrizioneEstesa;
	}

	@Override
	protected String getColoreArea() {
		return this.coloreArea;
	}
	
	@Autowired
	private LayerGeoService geoService;

//	@RequestMapping(value = "/"+layerName, produces = "text/javascript")
//	public String getInfo(@RequestParam(name = "f", required = false, defaultValue = "json") String f) {
//		return "{\"currentVersion\":10.41,\"id\":0,\"name\":\""+layerDescription
//				+ "\",\"type\":\"Feature Layer\",\"description\":\"\",\"copyrightText\":\"\",\"defaultVisibility\":true"
//				+ ",\"editFieldsInfo\":null,\"ownershipBasedAccessControlForFeatures\":null"
//				+ ",\"syncCanReturnChanges\":false,\"relationships\":[],\"isDataVersioned\":false"
//				+ ",\"supportsRollbackOnFailureParameter\":true,\"supportsStatistics\":true,\"supportsAdvancedQueries\":true"
//				+ ",\"supportsValidateSQL\":true,\"supportsCalculate\":true"
//				+ ",\"advancedQueryCapabilities\":{\"supportsPagination\":true,\"supportsQueryWithDistance\":true,"
//				+ "\"supportsReturningQueryExtent\":true,\"supportsStatistics\":true,\"supportsOrderBy\":true,"
//				+ "\"supportsDistinct\":true},\"geometryType\":\"esriGeometryPolygon\",\"minScale\":0,\"maxScale\":0,"
//				+ "\"extent\":{\"xmin\":475931,\"ymin\":4386719,\"xmax\":814598,\"ymax\":4662064"
//				+ ",\"spatialReference\":{\"wkid\":32633,\"latestWkid\":32633}}"
//				+ ",\"drawingInfo\":{\"renderer\":{\"type\":\"simple\""
//				+ ",\"symbol\":{\"type\":\"esriSFS\",\"style\":\"esriSFSSolid\",\"color\":[201,242,208,255]"
//				+ ",\"outline\":{\"type\":\"esriSLS\",\"style\":\"esriSLSSolid\",\"color\":[110,110,110,255],\"width\":0.4}}"
//				+ ",\"label\":\"\",\"description\":\"\"},\"transparency\":0,\"labelingInfo\":null},\"hasM\":false,\"hasZ\":false"
//				+ ",\"allowGeometryUpdates\":true,\"hasAttachments\":false,\"supportsApplyEditsWithGlobalIds\":false"
//				+ ",\"htmlPopupType\":\"esriServerHTMLPopupTypeAsHTMLText\",\"objectIdField\":\"oid\",\"globalIdField\":\"\""
//				+ ",\"displayField\":\"id_fascicolo\",\"typeIdField\":\"\""
//				+ ",\"fields\":[{\"name\":\"oid\",\"type\":\"esriFieldTypeOID\",\"alias\":\"oid\""
//				+ ",\"domain\":null,\"editable\":false,\"nullable\":false}"
//				+ ",{\"name\":\"id_fascicolo\""
//				+ ",\"type\":\"esriFieldTypeString\",\"alias\":\"id_fascicolo\",\"domain\":null,\"editable\":true"
//				+ ",\"nullable\":false,\"length\":100}"
//				+ ",{\"name\":\"geom\""
//				+ ",\"type\":\"esriFieldTypeGeometry\",\"alias\":\"geom\",\"domain\":null,\"editable\":false"
//				+ ",\"nullable\":false}"
//				+ ",{\"name\":\"user_id\",\"type\":\"esriFieldTypeString\",\"alias\":\"user_id\",\"domain\":null"
//				+ ",\"editable\":true,\"nullable\":false,\"length\":100}"
//				+ ",{\"name\":\"user_fk\",\"type\":\"esriFieldTypeInteger\",\"alias\":\"user_fk\",\"domain\":null,\"editable\":true,\"nullable\":true}"
//				+ ",{\"name\":\"date_created\",\"type\":\"esriFieldTypeDate\",\"alias\":\"date_created\",\"domain\":null,\"editable\":true,\"nullable\":true,\"length\":8}"
//				+ ",{\"name\":\"date_updated\",\"type\":\"esriFieldTypeDate\",\"alias\":\"date_updated\",\"domain\":null,\"editable\":true,\"nullable\":true,\"length\":8}"
//				+ ",{\"name\":\"is_custom\",\"type\":\"esriFieldTypeInteger\",\"alias\":\"is_custom\",\"domain\":null,\"editable\":true,\"nullable\":true}]"
//				+ ",\"indexes\":[{\"name\":\"r5027_sde_rowid_uk\",\"fields\":\"oid\",\"isAscending\":true,\"isUnique\":true,\"description\":\"\"},{\"name\":\"idx_rr242005_fasc_geom_autocert_rr2405_id_fascicolo\",\"fields\":\"id_fascicolo\",\"isAscending\":true,\"isUnique\":false,\"description\":\"\"}"
//				+ ",{\"name\":\"idx_rr242005_fasc_geom_autocert_rr2405_user_fk\",\"fields\":\"user_fk\",\"isAscending\":true,\"isUnique\":false,\"description\":\"\"},{\"name\":\"idx_rr242005_fasc_geom_autocert_rr2405_user_id\""
//				+ ",\"fields\":\"user_id\",\"isAscending\":true,\"isUnique\":false,\"description\":\"\"},{\"name\":\"a3787_ix1\",\"fields\":\"geometry\",\"isAscending\":true,\"isUnique\":true,\"description\":\"\"}],\"dateFieldsTimeReference\":{\"timeZone\":\"UTC\",\"respectsDaylightSaving\":false},\"types\":[],"
//				+ "\"templates\":[{\"name\": \""+layerDescription+"\","
//				+ "\"description\":\"\",\"prototype\":"
//				+ "{\"attributes\":{\"date_updated\":null,\"is_custom\":0,\"id_fascicolo\":\" \""
//				+ ",\"user_id\":\" \",\"user_fk\":null,\"date_created\":null}},\"drawingTool\":\"esriFeatureEditToolPolygon\"}],\"maxRecordCount\":1000,\"supportedQueryFormats\":\"JSON, AMF, geoJSON\",\"capabilities\":\"Query,Create,Update,Delete,Uploads,Editing\",\"useStandardizedQueries\":true}";
//	}
//
//	// TODO add query
//	/**
//	 * 
//	 * @param f
//	 * @param geom
//	 * @throws Exception
//	 */
//	@RequestMapping(value = "/"+layerName+"/applyEdits", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded;charset=ISO-8859-1")
//	// @PostMapping(value = "/applyEdits",consumes =
//	// MediaType.APPLICATION_JSON_UTF8_VALUE,produces =
//	// MediaType.APPLICATION_JSON_UTF8_VALUE) //@RequestBody
//	public @ResponseBody EsriResponse applyEdits(
//			@RequestParam(name = "f", required = false, defaultValue = "json") String f,
//			@RequestParam(name = "adds", required = false) EsriGeometryWithAttribute[] adds,
//			@RequestParam(name = "updates", required = false) EsriGeometryWithAttribute[] updates,
//			@RequestParam(name = "deletes", required = false) String deletes) throws Exception {
//		EsriResponse ret = new EsriResponse();
//		if (!StringUtil.isEmpty(deletes)) {
//			System.out.println("deletes" + deletes);
//			ret.addDelete(deletes);
//		}
//		if (adds != null && adds.length > 0) { // inserisci la geometria
//			for (EsriGeometryWithAttribute geometria : Arrays.asList(adds)) {
//				MapGeometry geo = geometria.getGeometry();
//				// converto in dto
//				GeoDTO pevgeo = new GeoDTO();
//				pevgeo.setGeometry(GeometryUtils.esriGeometryToJTSGeometry(geo));
//				String idFascicolo = ((String) geometria.getAttribute("id_fascicolo"));
//				Integer isCustom = (Integer)geometria.getAttribute("is_custom");
//				pevgeo.setIdFascicolo(UUID.fromString(idFascicolo));
//				pevgeo.setIsCustom(isCustom);
//				Long key = geoService.insert(pevgeo);
//				ret.addResult("" + key);
//			}
////            EsriGeometryWithAttribute geom=adds[0];
////            MapGeometry geo=geom.getGeometry();
////            //converto in dto
////            PevGeoDTO pevgeo=new PevGeoDTO();
////            pevgeo.setGeometry(GeometryUtils.esriGeometryToJTSGeometry(geo));
////            Long idFascicolo=((Integer)geom.getAttribute("id_fascicolo")).longValue();
////            pevgeo.setId(idFascicolo);
////            int key=pevgeoRepo.insert(pevgeo);
////            ret.addResult(""+key);
//		}
//		if (updates != null && updates.length > 0) {// aggiorna
//			for (EsriGeometryWithAttribute geom : Arrays.asList(updates)) {
//				MapGeometry geo = geom.getGeometry();
//				GeoDTO pevgeo = new GeoDTO();
//				Object oid=geom.getAttribute("oid");
//				Integer gid=null;
//				if( oid instanceof Integer) {
//					 gid =  (Integer)oid;	
//				}else {
//					gid=Integer.parseInt((String)oid);
//				}
//				pevgeo.setOid(gid);
//				pevgeo.setGeometry(GeometryUtils.esriGeometryToJTSGeometry(geo));
//				geoService.update(pevgeo);
//				ret.addUpdate("" + pevgeo.getOid());
//			}
////            EsriGeometryWithAttribute geom=updates[0];
////            MapGeometry geo=geom.getGeometry();
////             
////            PevGeoDTO pevgeo=new PevGeoDTO();
////            Integer gid=(Integer)geom.getAttribute("oid");
////            
////            pevgeo.setOid(gid);
////            pevgeo.setGeometry(GeometryUtils.esriGeometryToJTSGeometry(geo));             
////            pevgeoRepo.update(pevgeo);
////        ret.addUpdate(""+pevgeo.getOid());
//		}
//		if (!StringUtil.isEmpty(deletes)) { //mi arrivano anche con separatore ,
//			// elimina oid
//			String[] oids = deletes.split(",");
//			if(oids!=null && oids.length>0) {
//				for(String oid:oids) {
//				GeoDTO pevgeo = new GeoDTO();
//				pevgeo.setOid(Long.parseLong(oid));
//				geoService.delete(pevgeo);
//				}
//			}
//		}
//		return ret;
//	}
//
//	@RequestMapping(value = "/"+layerName+"/query", method = RequestMethod.GET, consumes = "*/*")
//	// @PostMapping(value = "/applyEdits",consumes =
//	// MediaType.APPLICATION_JSON_UTF8_VALUE,produces =
//	// MediaType.APPLICATION_JSON_UTF8_VALUE) //@RequestBody
//	public @ResponseBody EsriQueryResponse query(
//			@RequestParam(name = "returnExtentOnly", required = false, defaultValue = "false") boolean returnExtentOnly,
//			@RequestParam(name = "returnCountOnly", required = false, defaultValue = "false") boolean returnCountOnly,
//			@RequestParam(name = "returnGeometry", required = false, defaultValue = "true") boolean returnGeometry,
//			@RequestParam(name = "where", required = false, defaultValue = "1=1") String where,
//			@RequestParam(name = "geometry", required = false) EsriBBox bbox) throws Exception {
//		EsriQueryResponse response = new EsriQueryResponse();
//		if (returnExtentOnly) {
//			// {"error":{"code":400,"message":"Unable to complete operation.","details":[]}}
//			// response.setError(new EsriKoObject("400"));
//			response=geoService.queryExtent(where);
////			response.setCount(1);
////			response.setExtent(new EsriBBox());
//			// TODO set extension of layer
//			return response;
//		}
//
////        response.setGeometryType("esriGeometryPolygon");
////        response.setObjectIdFieldName("gid");
////        EsriFieldType ftype = new EsriFieldType();
////        ftype.setName("gid");
////        ftype.setAlias("gid");
////        ftype.setType(EsriFieldTypesValues.esriFieldTypeOID);
////        response.addField(ftype);
////        response.setSpatialReference( new SimplifiedSpatialReference(32633, 32633));
////        PevGeoDTO pevgeo=new PevGeoDTO();
////        pevgeo.setGid(2);
////        pevgeo =pevgeoRepo.find(pevgeo);
////        MapGeometry esriMapGeom=GeometryUtils.esriJTSPostgisGeometryToEsriGeometry(pevgeo.getGeometry());
////        EsriGeometryWithAttribute result= new EsriGeometryWithAttribute();
////        result.addAttribute("gid", 2);
////        result.setGeometry(esriMapGeom);
////        //add attribute
////        response.addFeature(result);
////        return response;
//		return geoService.queryLayer(where, bbox);
//
//	}
//
//	@InitBinder
//	public void initBinder(WebDataBinder binder) {
//		binder.registerCustomEditor(EsriGeometryWithAttribute[].class, new EsriGeometryWithAttributeEditor());
//		binder.registerCustomEditor(EsriBBox.class, new EsriBBoxEditor());
//	}
//
////	public GeoRepository getPevgeoRepo() {
////		return pevgeoRepo;
////	}
////
////	public void setPevgeoRepo(GeoRepository pevgeoRepo) {
////		this.pevgeoRepo = pevgeoRepo;
////	}

}