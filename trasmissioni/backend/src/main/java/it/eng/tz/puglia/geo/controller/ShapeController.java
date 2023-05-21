/**
 * 
 */
package it.eng.tz.puglia.geo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.eng.tz.puglia.autpae.controller._RestController;
import it.eng.tz.puglia.autpae.dto.WktShapeDTO;
import it.eng.tz.puglia.autpae.enumeratori.Gruppi;
import it.eng.tz.puglia.autpae.service.interfacce.GruppiRuoliService;
import it.eng.tz.puglia.bean.BaseRestResponse;
import it.eng.tz.puglia.configuration.security.SecurityConfig;
import it.eng.tz.puglia.configuration.swagger.OpenApiConfig;
import it.eng.tz.puglia.geo.service.LayerGeoService;
import it.eng.tz.puglia.util.log.LogUtil;


/**
 * @author Adriano Colaianni
 * @date 11 mag 2021
 */
@RestController
//@RequestMapping(value = {"geoshape","api-gateway/geoshape" }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ShapeController extends _RestController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ShapeController.class);
	private final String GEOSHAPE_PATH="geoshape";
	private final String API_GEOSHAPE_PATH=SecurityConfig.API_GATEWAY_VALUE +"/"+GEOSHAPE_PATH;
	@Autowired private GruppiRuoliService gruppiRuoliService;
	
	@Autowired private LayerGeoService layerGeoSvc;

	@PostMapping(value = API_GEOSHAPE_PATH+ "/insertShape",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Operation(summary = "inserimento di una geometria in mappa legata al fascicolo, restituisce l'oid (chiave univoca) alla geometria inserita, è relazionata nei dati della particella catastale", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	public ResponseEntity<BaseRestResponse<Long>> salva(@RequestBody(required=true) WktShapeDTO data) throws Exception {
		LOGGER.info("Chiamato il controller: 'azioni'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		LOGGER.info("Start salva");
		StopWatch sw = LogUtil.startLog("salva");
		try {
			//gruppiRuoliService.checkAbilitazioneFor(Gruppi.ED_);
			gruppiRuoliService.checkAbilitazioneForTrasmissione();
			checkPermission(data.getIdFascicolo());
			Long ret = layerGeoSvc.insertFromWkt(data.getIdFascicolo(), data.getWkt());
			return super.okResponse(ret);	
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}
	
	@DeleteMapping(value = API_GEOSHAPE_PATH+ "/deleteShapeByIdFascicolo",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Operation(summary = "cancellazione delle geometrie in mappa legate al fascicolo, restituisce il numero di tuple rimosse.", security = @SecurityRequirement(name = OpenApiConfig.BEARER_NAME))
	public ResponseEntity<BaseRestResponse<Integer>> delete(@RequestParam(required=true) Long idFascicolo) throws Exception {
		LOGGER.info("Chiamato il controller: 'azioni'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
		LOGGER.info("Start deleteShapeByIdFascicolo");
		StopWatch sw = LogUtil.startLog("deleteShapeByIdFascicolo");
		try {
			//gruppiRuoliService.checkAbilitazioneFor(Gruppi.ED_);
			gruppiRuoliService.checkAbilitazioneForTrasmissione();
			checkPermission(idFascicolo);
			Integer ret = layerGeoSvc.deleteShapeFascicolo(idFascicolo);
			return super.okResponse(ret);	
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

}
