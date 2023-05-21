package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.geo.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FeatureSource;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.referencing.CRS;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Polygonal;
import org.opengis.feature.Feature;
import org.opengis.feature.GeometryAttribute;
import org.opengis.feature.type.FeatureType;
import org.opengis.filter.Filter;
import org.opengis.filter.FilterFactory2;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller.RoleAwareController;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.AllegatoCustomDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.AlfrescoPaths;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.AllowedMimeType;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.SezioneAllegati;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.geo.dto.InfoAndGeoJsonDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.AllegatoService;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.error.exception.CustomValidationException;
import it.eng.tz.puglia.util.log.LogUtil;

@RestController
@RequestMapping(value = "geoutil", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class GeoUtilController extends RoleAwareController {
	protected static final String MEDIA_TYPE = MediaType.APPLICATION_JSON_UTF8_VALUE;
	
	final String tipoFileShape="Area intervento SHAPE FILE - UTM33"; //e' cosi' nel flyway di popolamento della tabella dei tipi allegato
	
	@Value("${shape.filtering.bbox.xmin}")
	int xmin;
	@Value("${shape.filtering.bbox.xmax}")
	int xmax;
	@Value("${shape.filtering.bbox.ymin}")
	int ymin;
	@Value("${shape.filtering.bbox.ymax}")
	int ymax;
	@Value("${shape.filtering.enable}")
	Boolean enablePreFiltering=false;		
	
	@Autowired
	AllegatoService allegatoService;


	private List<File> esplodiZip(Path zipFile, Path pathOut) throws FileNotFoundException, IOException {
		List<File> listaShape = new ArrayList<File>();
		try (FileInputStream fis = new FileInputStream(zipFile.toFile());
				BufferedInputStream bis = new BufferedInputStream(fis);
				ZipInputStream stream = new ZipInputStream(bis)) {
			ZipEntry entry;
			while ((entry = stream.getNextEntry()) != null) {
				Path filePath = pathOut.resolve(entry.getName());
				if (entry.isDirectory() && !filePath.getParent().toFile().exists()) {
					Files.createDirectories(filePath);
				}else {
					if(!filePath.getParent().toFile().exists()) {
						Files.createDirectories(filePath.getParent());
					}
				} 
				if (FilenameUtils.getExtension(entry.getName()).toUpperCase().compareTo("SHX") == 0
						|| FilenameUtils.getExtension(entry.getName()).toUpperCase().compareTo("SHP") == 0
						|| FilenameUtils.getExtension(entry.getName()).toUpperCase().compareTo("DBF") == 0
						|| FilenameUtils.getExtension(entry.getName()).toUpperCase().compareTo("PRJ") == 0) {
					logger.info("Adding to scan shape:" + entry.getName());
					try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
						IOUtils.copy(stream,fos);
					}
					if (FilenameUtils.getExtension(entry.getName()).toUpperCase().compareTo("SHP") == 0) {
						listaShape.add(filePath.toFile());
					}
				}
			}
		}
		return listaShape;
	}

	/**
	 * classe in cui avvolgo la FeatureCollection per dare eventuali messaggi di errore in validazione
	 * @author acolaianni
	 *
	 */
//	private class InfoAndGeoJson implements Serializable{
//		private static final long serialVersionUID = 533808554551970976L;
//		it.eng.tz.puglia.geo.util.FeatureCollection fc;
//		int codeValidazione=0; //0==OK -1=errore validazione
//		String messaggioValidazione="";
//		UUID idAllegato;
//		public it.eng.tz.puglia.geo.util.FeatureCollection getFc() {
//			return fc;
//		}
//		public void setFc(it.eng.tz.puglia.geo.util.FeatureCollection fc) {
//			this.fc = fc;
//		}
//		public int getCodeValidazione() {
//			return codeValidazione;
//		}
//		public void setCodeValidazione(int codeValidazione) {
//			this.codeValidazione = codeValidazione;
//		}
//		public String getMessaggioValidazione() {
//			return messaggioValidazione;
//		}
//		public void setMessaggioValidazione(String messaggioValidazione) {
//			this.messaggioValidazione = messaggioValidazione;
//		}
//		public UUID getIdAllegato() {
//			return idAllegato;
//		}
//		public void setIdAllegato(UUID idAllegato) {
//			this.idAllegato = idAllegato;
//		}
//
//	}

	/**
	 * filtra le feature in base ad alcune regole
	 * per ora solo  in boundingbox (regione puglia)
	 * @return filter or null
	 */
	private Filter getFeatureFilter(FeatureSource  featureSource) {
		logger.debug("prefiltering enabled="+enablePreFiltering);
		Filter filter = null;
		if(enablePreFiltering) {
			FilterFactory2 ff = CommonFactoryFinder.getFilterFactory2();
			FeatureType schema = featureSource.getSchema();
			// usually "THE_GEOM" for shapefiles
			String geometryPropertyName = schema.getGeometryDescriptor().getLocalName();
			CoordinateReferenceSystem targetCRS =
					schema.getGeometryDescriptor().getCoordinateReferenceSystem();
			ReferencedEnvelope bbox = new ReferencedEnvelope(xmin, ymin, xmax, ymax, targetCRS);
			filter = ff.bbox(ff.property(geometryPropertyName), bbox);
		}
		return filter;
	}
	
	@PostMapping(value = "/shape2geoJSON", 
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MEDIA_TYPE)
	public ResponseEntity<BaseRestResponse<InfoAndGeoJsonDTO>> shape2GeoJson(
			@RequestPart("file") MultipartFile file,
			@RequestPart("idPiano") String idPiano) throws Exception {
		final StopWatch sw = LogUtil.startLog("shape2GeoJson");
		logger.info("Chiamato il controller: 'GeoUtil'. Metodo: '" + new Object() {
		}.getClass().getEnclosingMethod().getName() + "'");

		CoordinateReferenceSystem dataCRS;
		InfoAndGeoJsonDTO retBean=new InfoAndGeoJsonDTO();
		AllowedMimeType mime = AllowedMimeType.getMimeType(AlfrescoPaths.detectMimeType(file.getInputStream()));
		// controllo che sia uno zip
		if (mime != AllowedMimeType.ZIP) {
			retBean.setCodeValidazione(-1);
			retBean.setMessaggioValidazione("Atteso formato ZIP!!!");
			return super.okResponse(retBean);
		}
		// directory ove esplodere i file
		Path tmpDirShp = Files.createTempDirectory("shape2geoJSON");
		Path tmpFileShp = Files.createTempFile(tmpDirShp, "",file.getOriginalFilename());
		try(InputStream is = file.getInputStream();
                OutputStream os = new FileOutputStream(tmpFileShp.toFile())
            ){
                IOUtils.copy(is, os);
            }
		//file.transferTo(tmpFileShp);
		// unzippo il contenuto
		List<File> listaShape;
		try {
			listaShape = this.esplodiZip(tmpFileShp, tmpDirShp);
		} catch (Exception e) {
			retBean.setCodeValidazione(-1);
			retBean.setMessaggioValidazione("Errore nell'estrazione del contenuto del file ZIP!!!");
			logger.error("errore in esplodiZip",e);
			return super.okResponse(retBean);
		}
		try {
			// una volta esploso lo ZIP
			it.eng.tz.puglia.geo.util.FeatureCollection fc = new it.eng.tz.puglia.geo.util.FeatureCollection();
			for (File fileShape : listaShape) {
				Map<String, String> connect = new HashMap<String, String>();
				connect.put("url", fileShape.toURI().toString());
				DataStore dataStore = DataStoreFinder.getDataStore(connect);
				String[] typeNames = dataStore.getTypeNames();
				String typeName = typeNames[0];
				logger.info("Reading content " + typeName);
				FeatureSource featureSource = dataStore.getFeatureSource(typeName);
				dataCRS = featureSource.getSchema().getCoordinateReferenceSystem();
				// check CRS e se non EPSG:32633 lanciare errore di validazione interno
				// TODO spedup check
				// così se vuoi leggerlo dal prj CoordinateReferenceSystem crs =
				// CRS.parseWKT(wkt);
				// name rilevato usando QGis WGS_1984_UTM_Zone_33N
				try {
					Integer code=CRS.lookupEpsgCode(dataCRS, false);
					logger.debug("srs rilevato code"+code);
					if(code == null ||!code.equals(32633)) {
						retBean.setCodeValidazione(-1);
						retBean.setMessaggioValidazione("Sistema di riferimento non valido, atteso EPSG:32633, rilevato: EPSG:"+code+" !!!");
						return super.okResponse(retBean);
					}	
				}catch(Exception e) {
					logger.info("Errore nel retrieval del CRS ",e);
					retBean.setCodeValidazione(-1);
					retBean.setMessaggioValidazione("Impossibile rilevare il sistema di riferimento, atteso EPSG:32633 !!!");
					return super.okResponse(retBean);
				}
				// creo l'envelope per test delle coordinate all'interno dii una zona che
				// comprende la puglia
				String geometryPropertyName = featureSource.getSchema().getGeometryDescriptor().getLocalName();
				CoordinateReferenceSystem targetCRS = featureSource.getSchema().getGeometryDescriptor()
						.getCoordinateReferenceSystem();
				Envelope refbbox = new Envelope(xmin, xmax, ymin, ymax);
				// TODO verificare se sono punti o linee convertirli con buffer (chiedere
				// cliente)
				// featureSource.getSchema().getGeometryDescriptor().getType()
				logger.info("featuresource:" + featureSource);
				FeatureCollection collection = featureSource.getFeatures(getFeatureFilter(featureSource));
				FeatureIterator iterator=null;
				try {
					iterator = collection.features();
					while (iterator.hasNext()) {
						Feature feature = iterator.next();
						// System.out.println("feature:"+feature);
						GeometryAttribute sourceGeometry = feature.getDefaultGeometryProperty();
						// System.out.println("geom: "+sourceGeometry);
						it.eng.tz.puglia.geo.util.Feature f = new it.eng.tz.puglia.geo.util.Feature();
						logger.info("fc get Value " + sourceGeometry.getValue().getClass());
						Geometry geom = (Geometry) sourceGeometry.getValue();
//						if (!refbbox.contains(geom.getEnvelopeInternal())) {
//							throw new CustomOperationToAdviceException(
//									"Rilevata geometria fuori dalla zona ammessa!!!");
//						}
						f.setProperties(new HashMap<>());
						f.setGeometry((Geometry) sourceGeometry.getValue());
						if(f.getGeometry() instanceof Polygonal == false) {
	                        retBean.setCodeValidazione(-1);
	                        retBean.setMessaggioValidazione("Sono ammessi solo poligoni e multipoligoni");
	                        return super.okResponse(retBean);
	                    }
						fc.addFeature(f);
					}
				} finally {
					iterator.close();
					dataStore.dispose();
				}
			}
			if (fc!=null && fc.getFeatures()!=null &&  fc.getFeatures().size()>0) {
				aggiungiAllegatoShape(file, idPiano, retBean);
				retBean.setFc(fc);
			}else {
				retBean.setCodeValidazione(-1);
				retBean.setMessaggioValidazione("Nessuna geometria valida trovata nel file shape!!!");
			}
			return super.okResponse(retBean);
		} 
		catch (Exception e) {
			logger.error("errore nell'elaborazione del file shape ",e);
			retBean.setCodeValidazione(-1);
			retBean.setMessaggioValidazione("errore nell'elaborazione del file shape  !!!");
			return super.okResponse(retBean);	
		}finally {
			if(tmpDirShp!=null && Files.exists(tmpDirShp)) {
				try {
				this.deleteTmpDir(tmpDirShp);
				}catch(Exception e) {
					logger.error("errore nella rimozione dei file temporanei ",e);
				}
			}
			logger.info(LogUtil.stopLog(sw));
		}
//		try {
//			// una volta esploso lo ZIP
//			it.eng.tz.puglia.geo.util.FeatureCollection fc = new it.eng.tz.puglia.geo.util.FeatureCollection();
//			for (File fileShape : listaShape) {
//				Map<String, String> connect = new HashMap<String, String>();
//				connect.put("url", fileShape.toURI().toString());
//				DataStore dataStore = DataStoreFinder.getDataStore(connect);
//				String[] typeNames = dataStore.getTypeNames();
//				String typeName = typeNames[0];
//				logger.info("Reading content " + typeName);
//				FeatureSource featureSource = dataStore.getFeatureSource(typeName);
//				dataCRS=featureSource.getSchema().getCoordinateReferenceSystem();
//				// check CRS e se non EPSG:32633 lanciare errore di validazione interno 
//				//TODO spedup check 
//				//così se vuoi leggerlo dal  prj CoordinateReferenceSystem crs = CRS.parseWKT(wkt);
//				//name rilevato usando QGis WGS_1984_UTM_Zone_33N
//				Integer code=CRS.lookupEpsgCode(dataCRS, false);
//				logger.debug("srs rilevato code"+code);
//				if(!code.equals(32633)) {
//					retBean.setCodeValidazione(-1);
//					retBean.setMessaggioValidazione("Sistema di riferimento non valido, atteso EPSG:32633, rilevato: EPSG:"+code+" !!!");
//					return super.okResponse(retBean);
//				}
//				//creo l'envelope per test delle coordinate all'interno dii una zona che comprende la puglia
//				String geometryPropertyName = featureSource.getSchema().getGeometryDescriptor().getLocalName();
//				CoordinateReferenceSystem targetCRS =
//						featureSource.getSchema().getGeometryDescriptor().getCoordinateReferenceSystem();
//				Envelope refbbox = new Envelope(xmin,xmax,ymin,ymax);
//				//TODO verificare se sono punti o linee convertirli con buffer (chiedere cliente)
//				//featureSource.getSchema().getGeometryDescriptor().getType()
//				logger.info("featuresource:" + featureSource);
//				FeatureCollection collection = featureSource.getFeatures(getFeatureFilter(featureSource));
//				FeatureIterator iterator = collection.features();
//				while (iterator.hasNext()) {
//					Feature feature = iterator.next();
//					// System.out.println("feature:"+feature);
//					GeometryAttribute sourceGeometry = feature.getDefaultGeometryProperty();
//					// System.out.println("geom: "+sourceGeometry);
//					it.eng.tz.puglia.geo.util.Feature f = new it.eng.tz.puglia.geo.util.Feature();
//					logger.info("fc get Value " + sourceGeometry.getValue().getClass());
//					Geometry geom=(Geometry) sourceGeometry.getValue();
//					if(!refbbox.contains(geom.getEnvelopeInternal())) {
//						retBean.setCodeValidazione(-1);
//						retBean.setMessaggioValidazione("Rilevata geometria fuori dalla zona ammessa!!!");
//						return super.okResponse(retBean);
//					}
//					f.setProperties(new HashMap<>());
//					f.setGeometry((Geometry) sourceGeometry.getValue());
//					fc.addFeature(f);
//				}
//				iterator.close();
//				dataStore.dispose();
//			}
//			//se e' tutto ok lo aggiungo tra gli allegati chiamando l'allegati service
//			UUID idAllegato = null;
//			if (fc!=null && fc.getFeatures()!=null &&  fc.getFeatures().size()>0) {
//				idAllegato = aggiungiAllegatoShape(file, idPiano, retBean);
//			}else {
//				retBean.setCodeValidazione(-1);
//				retBean.setMessaggioValidazione("Nessuna geometria valida trovata nel file shape!!!");
//			}
//			retBean.setFc(fc);
//			retBean.setIdAllegato(idAllegato);
//			return super.okResponse(retBean);
//		} catch (Exception e) {
//			return super.koResponse(new CustomValidationException(), new BaseRestResponse<>());
//		}finally {
//			if(tmpDirShp!=null && Files.exists(tmpDirShp)) {
//				try {
//				this.deleteTmpDir(tmpDirShp);
//				}catch(Exception e) {
//					logger.error("errore nella rimozione dei file temporanei ",e);
//				}
//			}
//			logger.info(LogUtil.stopLog(sw));
//		}
	}

	/**
	 * TODO
	 * @param file
	 * @param idPiano
	 * @param retBean
	 */
	private UUID aggiungiAllegatoShape(MultipartFile file, String idPiano, InfoAndGeoJsonDTO retBean) {
		UUID idAllegato = null;
		AllegatiDTO out=null;
		try {
			List<Integer> tipiContenuto = new ArrayList<Integer>();
			tipiContenuto.add(600);
			out = allegatoService.uploadAllegato(idPiano, tipiContenuto, tipoFileShape, file);
			retBean.setCodeValidazione(0);
			retBean.setAllegatoDto(new AllegatoCustomDTO(out));
		}catch(NumberFormatException e) {
			logger.error("id piano non numerico:" + idPiano);
			retBean.setCodeValidazione(0);
			retBean.setMessaggioValidazione("Numero di piano errato, non è stato possibile inserire il file negli allegati!!!");
		}
		catch(Exception e) {
			logger.error("errore nel salvataggio dell'allegato shape:" + idPiano);
			retBean.setCodeValidazione(0);
			retBean.setMessaggioValidazione("Errore durante l'aggiunta del file negli allegati !!!");
		}
		return idAllegato;
	}
	
	private void deleteTmpDir(Path path) throws IOException {
        logger.info("deleteTmpDir {}", path);
        if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
            try (DirectoryStream<Path> entries = Files.newDirectoryStream(path)) {
                for (Path entry : entries) {
                    deleteTmpDir(entry);
                }
            }
        }
        Files.delete(path);
    }
}
