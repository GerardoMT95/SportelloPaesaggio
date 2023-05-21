package it.eng.tz.puglia.geo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.postgis.jts.JtsGeometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.esri.core.geometry.MapGeometry;

import it.eng.tz.puglia.autpae.controller.exception.CustomOperationToAdviceException;
import it.eng.tz.puglia.autpae.entity.FascicoloDTO;
import it.eng.tz.puglia.autpae.enumeratori.StatoFascicolo;
import it.eng.tz.puglia.autpae.repository.base.FascicoloBaseRepository;
import it.eng.tz.puglia.autpae.search.FascicoloSearch;
import it.eng.tz.puglia.autpae.service.interfacce.FascicoloService;
import it.eng.tz.puglia.autpae.service.interfacce.GruppiRuoliService;
import it.eng.tz.puglia.error.exception.CustomValidationException;
import it.eng.tz.puglia.geo.dto.GeoDTO;
import it.eng.tz.puglia.geo.repository.EnumNomeVista;
import it.eng.tz.puglia.geo.repository.GeoRepository;
import it.eng.tz.puglia.geo.service.LayerGeoService;
import it.eng.tz.puglia.geo.util.GeometryUtils;
import it.eng.tz.puglia.geo.util.esri.EsriBBox;
import it.eng.tz.puglia.geo.util.esri.EsriQueryResponse;
import it.eng.tz.puglia.security.util.SecurityUtil;


@Service
public class LayerGeoServiceImpl implements LayerGeoService {

	@Autowired
	private GeoRepository pevgeoRepo;
	
	@Autowired
	private FascicoloBaseRepository fascicoloRepo;
	
	@Autowired
	private GruppiRuoliService gruppiRuoliSvc;
	
	/**
	 * verifico che lo stato sia in WORKING e che l'utente ha i permessi sul fascicolo.
	 * purtroppo non arrivandomi il GRUPPO-SCELTO devo testare che l'utente abbia un gruppo di quelli 
	 * che possono editare il fascicolo
	 * @autor Adriano Colaianni
	 * @date 20 set 2021
	 * @param idFascicolo
	 * @throws CustomOperationToAdviceException
	 */
	private void checkStatoFascicolo(Long idFascicolo) throws CustomOperationToAdviceException {
		FascicoloDTO plan = new FascicoloDTO();
		plan.setId(idFascicolo);
		try {
			plan = fascicoloRepo.find(idFascicolo);
			if (!plan.getStato().equals(StatoFascicolo.WORKING)) {
				throw new CustomOperationToAdviceException(
						"Stato fasciolo non ammesso per l'aggiornamento del layer !!!");
			}
			if(!gruppiRuoliSvc.userHasOrganizzazione(plan.getOrgCreazione())) {
				throw new CustomOperationToAdviceException(
						"Fascicolo non aggiornabile dall'utente !!!");
			}
		} catch (CustomOperationToAdviceException e) {
			throw e;
		} catch (Exception e) {
			throw new CustomOperationToAdviceException("Nessun fascicolo trovato " + idFascicolo);
		}
	}
	
	private void checkStatoFascicolo(GeoDTO entity) throws CustomOperationToAdviceException {
		if (entity.getIdFascicolo() == 0 && entity.getOid() > 0) {
			entity = pevgeoRepo.find(entity);
		}
		this.checkStatoFascicolo(entity.getIdFascicolo());
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false,transactionManager = "tx_geo", rollbackFor=Exception.class)
	public Long insert(GeoDTO entity) throws CustomOperationToAdviceException {
		checkStatoFascicolo(entity);
		return pevgeoRepo.insertAndGetKey(entity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false,transactionManager = "tx_geo", rollbackFor=Exception.class)
	public Long insertFromWkt(Long idFascicolo,String wkt) throws CustomOperationToAdviceException {
		this.checkStatoFascicolo(idFascicolo);
		GeoDTO pevgeo = new GeoDTO();
		pevgeo.setIdFascicolo(idFascicolo);
		Integer isCustom = 1;
		pevgeo.setIsCustom(isCustom);
		pevgeo.setUserId(SecurityUtil.getUsername());
		return pevgeoRepo.insertFromWktAndGetKey(pevgeo,wkt);
	}
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false,transactionManager = "tx_geo", rollbackFor=Exception.class)
	public int delete(GeoDTO entity) throws CustomOperationToAdviceException {
		try {
			checkStatoFascicolo(entity);
		}catch(EmptyResultDataAccessException e) {
			return 0;
		}
		return pevgeoRepo.delete(entity);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = "tx_geo", rollbackFor=Exception.class)
	public EsriQueryResponse queryLayer(String where, EsriBBox bbox,EnumNomeVista vista) throws CustomValidationException {
		return pevgeoRepo.queryLayer(where, bbox,vista);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = "tx_geo", rollbackFor=Exception.class)
	public EsriQueryResponse queryExtent(String where) throws CustomValidationException {
		return pevgeoRepo.queryExtent(where);
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true,transactionManager = "tx_geo", rollbackFor=Exception.class)
	public EsriQueryResponse identify(JtsGeometry geom,EnumNomeVista vista) throws CustomValidationException {
		return pevgeoRepo.intersect(geom,vista);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false,transactionManager = "tx_geo", rollbackFor=Exception.class)
	public int update(GeoDTO entity) throws CustomOperationToAdviceException {
		checkStatoFascicolo(entity);
		return pevgeoRepo.update(entity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false,transactionManager = "tx_geo", rollbackFor=Exception.class)
	public int deleteShapeFascicolo(Long idFascicolo) throws CustomOperationToAdviceException {
		try {
			checkStatoFascicolo(idFascicolo);
			return pevgeoRepo.deleteByIdFascicolo(idFascicolo);
		}catch(EmptyResultDataAccessException e) {
			return 0;
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true,transactionManager = "tx_geo", rollbackFor=Exception.class)
	public List<Long> findShapeFascicolo(Long idFascicolo) throws CustomOperationToAdviceException {
		try {
			return pevgeoRepo.findByIdFascicolo(idFascicolo);
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false,transactionManager = "tx_geo", rollbackFor=Exception.class)
	public List<Long> insertWkts(List<String> wkt, Long idFascicolo, int isCustom, String userId) {
		List<Long> retList=new ArrayList<Long>();
		for(String wktItem:wkt) {
			GeoDTO pevgeo = new GeoDTO();
			pevgeo.setIdFascicolo(idFascicolo);
			pevgeo.setIsCustom(isCustom);
			pevgeo.setUserId(userId);
			retList.add(pevgeoRepo.insertFromWktAndGetKey(pevgeo,wktItem));	
		}
		return retList;
	}

}
