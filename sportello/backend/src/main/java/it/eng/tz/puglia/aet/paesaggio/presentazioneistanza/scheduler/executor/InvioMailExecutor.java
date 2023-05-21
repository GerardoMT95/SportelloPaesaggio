package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler.executor;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.SezioneAllegati;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.impl.PraticaService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler.SendPlanToDiogeneScheduler;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler.bean.FascicoloStatoBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.scheduler.bean.ProtocollazioneBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.FascicoloService;
import it.eng.tz.puglia.batch.queue.IQueueService;
import it.eng.tz.puglia.util.json.JsonUtil;
import it.eng.tz.puglia.util.log.LogUtil;

/**
 * Executor per inviare su diogene i documenti
 * 
 * @author Antonio La Gatta
 * @date 3 feb 2022
 */
@Component(InvioMailExecutor.ID)
public class InvioMailExecutor extends SportelloBaseExecutor {

	/**
	 * ID
	 */
	public static final String ID = "InvioMailExecutor";
	/**
	 * LOGGER
	 */
	private final static Logger LOGGER = LoggerFactory.getLogger(InvioMailExecutor.class);
	/**
	 * service
	 */
	@Autowired
	FascicoloService fascicoloSvc;
	@Autowired
	PraticaService praticaSvc;
	@Autowired
	IQueueService queueService;
	
	/**
	 * 
	 */
	@Override
	public UUID internalExecute(final String parameters) throws Exception {
		final StopWatch sw = LogUtil.startLog("internalExecute");
		LOGGER.info("Start internalExecute");
		try {
			final ProtocollazioneBean bean = JsonUtil.toBean(parameters, ProtocollazioneBean.class);
			this.fascicoloSvc.inviaNotificaRicezioneIstanza(UUID.fromString(bean.getIdPratica()), bean.getUsername(), bean.getRuolo(), bean.getEnteDelegato());
			final PraticaDTO pk = new PraticaDTO();
			pk.setId(UUID.fromString(bean.getIdPratica()));
			final FascicoloStatoBean fsb=new FascicoloStatoBean();
			fsb.setPratica(this.praticaSvc.find(pk));
			fsb.setSezioniAllegati(List.of(
						SezioneAllegati.GENERA_STAMPA,
						SezioneAllegati.DOC_AMMINISTRATIVA_D,
						SezioneAllegati.DOC_AMMINISTRATIVA_E,
						SezioneAllegati.DOC_TECNICA,
						SezioneAllegati.DICHIARAZIONI_ASSENSO,
						SezioneAllegati.LOCALIZZAZIONE,
						SezioneAllegati.ALLEGATO_DELEGA));
			this.queueService.insertNewQueue(SendPlanToDiogeneScheduler.ID, fsb);
			return UUID.fromString(bean.getIdPratica());
		} finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

	@Override
	public String getId() {
		return ID;
	}

}
