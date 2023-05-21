package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.geo.service.impl;

import java.util.List;
import java.util.UUID;

import org.postgis.jts.JtsGeometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.DatabaseConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.geo.dto.GeoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.geo.repository.EnumNomeVista;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.geo.repository.GeoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.geo.service.LayerGeoService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.PraticaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.impl.PraticaDataAwareService;
import it.eng.tz.puglia.error.exception.CustomValidationException;
import it.eng.tz.puglia.geo.util.esri.EsriBBox;
import it.eng.tz.puglia.geo.util.esri.EsriQueryResponse;
import it.eng.tz.puglia.util.string.StringUtil;


@Service
public class LayerGeoServiceImpl extends PraticaDataAwareService implements LayerGeoService {

	@Autowired
	private GeoRepository pevgeoRepo;
	
	@Autowired
	private PraticaRepository praticaDao ;
	
	
	private void checkStatoPratica(GeoDTO entity) throws CustomOperationToAdviceException {
		PraticaDTO pratica=new PraticaDTO();
		if((entity.getIdFascicolo()==null || StringUtil.isEmpty(entity.getIdFascicolo().toString()))
				&& entity.getOid()>0) {
			entity=pevgeoRepo.find(entity);
		}
		pratica.setId(entity.getIdFascicolo());	
		pratica=praticaDao.find(pratica);
		//non controllo se di mia competenza perchè arrivo da un controller public...
		super.checkStatoForUpdate(pratica);
	}
	
	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public Long insert(GeoDTO entity) throws CustomOperationToAdviceException {
		checkStatoPratica(entity);
		return pevgeoRepo.insertAndGetKey(entity);
	}

	
	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public int delete(GeoDTO entity) throws CustomOperationToAdviceException {
		try{
			checkStatoPratica(entity);
		}catch(EmptyResultDataAccessException e) {
			//hanno già cancellato l'oid
			return 0;
		}
		return pevgeoRepo.delete(entity);
	}

	@Override
	@Transactional(readOnly=true, propagation=Propagation.SUPPORTS, transactionManager = DatabaseConfiguration.TX_READ)
	public EsriQueryResponse queryLayer(String where, EsriBBox bbox,EnumNomeVista vista) throws CustomValidationException {
		return pevgeoRepo.queryLayer(where, bbox,vista);
	}

	@Override
	@Transactional(readOnly=true, propagation=Propagation.SUPPORTS, transactionManager = DatabaseConfiguration.TX_READ)
	public EsriQueryResponse queryExtent(String where) throws CustomValidationException {
		return pevgeoRepo.queryExtent(where);
	}
	
	@Override
	@Transactional(readOnly=true, propagation=Propagation.SUPPORTS, transactionManager = DatabaseConfiguration.TX_READ)
	public EsriQueryResponse identify(JtsGeometry geom,EnumNomeVista vista) throws CustomValidationException {
		return pevgeoRepo.intersect(geom,vista);
	}

	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public int update(GeoDTO entity) throws CustomOperationToAdviceException {
		checkStatoPratica(entity);
		return pevgeoRepo.update(entity);
	}

	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public int removeShape(UUID praticaId,List<Long> oidsToRemove) {
		return pevgeoRepo.deleteByPraticaId(praticaId,oidsToRemove);
	}

}
