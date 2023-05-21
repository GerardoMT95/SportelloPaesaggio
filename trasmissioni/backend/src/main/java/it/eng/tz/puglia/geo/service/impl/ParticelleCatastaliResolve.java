package it.eng.tz.puglia.geo.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import it.eng.tz.puglia.autpae.entity.ParticelleCatastaliDTO;
import it.eng.tz.puglia.autpae.repository.ParticelleCatastaliFullRepository;
import it.eng.tz.puglia.geo.repository.GeoRepository;
import it.eng.tz.puglia.geo.service.IConfiniService;
import it.eng.tz.puglia.geo.service.IParticelleCatastaliResolve;
import it.eng.tz.puglia.geo.service.LayerGeoService;
import it.eng.tz.puglia.servizi_esterni.remote.dto.EnteDTO;
import it.eng.tz.puglia.servizi_esterni.remote.repository.EnteRepository;
import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.util.log.LogUtil;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * classe che implementa il servizio di risoluzione della geometria di un comune
 * e la scrive nella tabella del layer emulato
 * @author Alessio Bottalico
 * @date 3 ott 2022
 */
@Service
public class ParticelleCatastaliResolve implements IParticelleCatastaliResolve {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	ParticelleCatastaliFullRepository particelleRepo;
	
	@Autowired
	IConfiniService confiniSvc;

	@Autowired
	LayerGeoService layerGeoSvc;
	
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
	public void elaboraParticelle(ParticelleCatastaliDTO location) throws Exception {
		final StopWatch sw = LogUtil.startLog("elaboraParticelle ", location.getId());
		this.logger.info("elaboraParticelle {}", location.getId());
		try {
			final String comune     = location.getCodCat();
			final String foglio     = location.getFoglio();
			final String particella = location.getParticella();
			final String sezione    = location.getSezione();
			//recupero il codice del comune e se non lo trovo errore
			this.logger.info("comune     = {}", comune    );
			this.logger.info("foglio     = {}", foglio    );
			this.logger.info("particella = {}", particella);
			this.logger.info("sezione    = {}", sezione   );
			if(StringUtil.isEmpty(comune)
			|| StringUtil.isEmpty(foglio)
			|| StringUtil.isEmpty(particella)
			) {
				this.particelleRepo.updateElaborato(location.getId(), "E");
				
			}else {
				final List<String> geomRetieved = this.confiniSvc.wktParticelle(comune, foglio, particella, sezione);
				final List<Long> oid = this.layerGeoSvc.insertWkts(geomRetieved, location.getPraticaId(), 0, "batchuser");
				this.particelleRepo.updateElaborato(location.getId(), "C", ListUtil.isEmpty(oid) ? null : oid.get(0));
			}
		}finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
		
	}

}
