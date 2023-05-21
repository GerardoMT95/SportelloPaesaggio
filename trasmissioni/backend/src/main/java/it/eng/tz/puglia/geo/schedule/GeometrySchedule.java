package it.eng.tz.puglia.geo.schedule;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import it.eng.tz.puglia.autpae.entity.ParticelleCatastaliDTO;
import it.eng.tz.puglia.autpae.repository.ParticelleCatastaliFullRepository;
import it.eng.tz.puglia.geo.service.impl.ParticelleCatastaliResolve;
import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.util.log.LogUtil;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * schedulatore task batch per caricare la geometria delle localizzaioni di tipo
 * comune
 * 
 * @author acolaianni
 *
 */
@Component
public class GeometrySchedule {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${abilita.scheduling}")
	private String abilitaScheduling;

	@Value("${geometry.cron.expression}")
	private String geomCronExpression;
	
	@Autowired
	ParticelleCatastaliFullRepository particelleRepo;

	@Autowired
	ParticelleCatastaliResolve locResolve;

	@Scheduled(cron = "${geometry.cron.expression}")
	public void generaGeometrieMancanti() {
		final String idLog = "generaGeometrieMancanti";
		this.logger.info("Start {}", idLog);
		final StopWatch sw = LogUtil.startLog(idLog);
		try {
			if (StringUtil.isNotEmpty(this.abilitaScheduling) && this.abilitaScheduling.equalsIgnoreCase("S")) {
				this.logger.info("Start caso particelle");
				final List<ParticelleCatastaliDTO> locationParticelle = this.particelleRepo.findLocationParticelleNonElaborati();
				this.logger.trace("Number of locations for particelle is {}", ListUtil.size(locationParticelle));
				locationParticelle.forEach(location ->{
					try {
						this.locResolve.elaboraParticelle(location);
					}catch(final Exception e) {
						this.logger.error("Errore durante l'elaborazione della localizzazione {}", location, e);
					}
				});
			} else {
				this.logger.info("Skipping GeometrySchedule... scheduling disabilitato");
			}
		} finally {
			this.logger.info(LogUtil.stopLog(sw));
		}
	}

	@PostConstruct
	private void init() {
		this.logger.info("Init GeometrySchedule abilita.scheduling={} geometry.cron.expression={}",
				
				this.abilitaScheduling, this.geomCronExpression);
	}

}
