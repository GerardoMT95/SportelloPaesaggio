/**
 * 
 */
package it.eng.tz.puglia.autpae.jms.diogene;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.autpae.config.ApplicationProperties;
import it.eng.tz.puglia.autpae.dto.AllegatoDiogeneInfoDTO;
import it.eng.tz.puglia.autpae.generated.orm.repository.PaesaggioOrganizzazioneRepository;
import it.eng.tz.puglia.autpae.jms.diogene.service.IDiogeneClientService;
import it.eng.tz.puglia.autpae.repository.AllegatoDiogeneInfoRepository;
import it.eng.tz.puglia.autpae.repository.AllegatoFullRepository;
import it.eng.tz.puglia.bean.PaginatedList;
import it.eng.tz.puglia.util.log.LogUtil;

/**
 * @author Adriano Colaianni
 * @date 18 ott 2021
 */
@Component
@ConditionalOnProperty("jms.enable")
public class DiogeneScheduledTask {
	private static final Logger LOGGER = LoggerFactory.getLogger(DiogeneScheduledTask.class);

	@Autowired
	AllegatoFullRepository allDao;
	@Autowired
	AllegatoDiogeneInfoRepository allDiogeneDao;

	@Autowired
	IDiogeneClientService diogeneSvc;

	@Scheduled(cron = "${diogene.send.cron.expression}")
	public void sendDocumentiToDiogene() throws Exception {
		PaginatedList<AllegatoDiogeneInfoDTO> paginatedDocs = null;
		LOGGER.info("Diogene fetching docs to send....");
		do {
			paginatedDocs = allDiogeneDao.getListaFileDaInviare(1, 10);
			if (ListUtil.isNotEmpty(paginatedDocs.getList())) {
				for (AllegatoDiogeneInfoDTO docInfoDiogene : paginatedDocs.getList()) {
					StopWatch sw = LogUtil.startLog("Sending message {}", docInfoDiogene);
					try {
						diogeneSvc.componiEdInviaMessaggio(docInfoDiogene);
						LOGGER.info("End Sending message");
					} catch (Exception e) {
						LOGGER.error("Errore durante l'invio del messaggio del file {}", docInfoDiogene, e);
					} finally {
						LOGGER.info(LogUtil.stopLog(sw));
					}
				}
			}
		} while (paginatedDocs != null && paginatedDocs.getCount() > 0);
		LOGGER.info("END Diogene fetching docs to send!");
	}

}
