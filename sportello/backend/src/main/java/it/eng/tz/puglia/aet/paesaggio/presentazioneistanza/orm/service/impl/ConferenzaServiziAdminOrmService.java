package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.SelectParentItemDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.TipoCdsAttivitaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.TipoCdsAzioneDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.TipoCdsTipoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.UserUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.ConfigurazioneCdsRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.TipoCdsAttivitaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.TipoCdsAzioneRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.TipoCdsTipoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.VCdsAttivitaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.VCdsAzioneRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.VCdsTipoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.IConferenzaServiziAdminOrmService;
import it.eng.tz.puglia.security.util.SecurityUtil;
import it.eng.tz.puglia.util.date.DateUtil;
import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.util.log.LogUtil;

@Service
public class ConferenzaServiziAdminOrmService implements IConferenzaServiziAdminOrmService{

	private static final Logger logger = LoggerFactory.getLogger(ConferenzaServiziAdminOrmService.class);
	
	@Autowired
	private VCdsTipoRepository tipoRepo;
	@Autowired
	private VCdsAzioneRepository azioniRepo;
	@Autowired
	private VCdsAttivitaRepository attivitaRepo;
	@Autowired 
	private ConfigurazioneCdsRepository confCdsRepo;
	@Autowired
	private TipoCdsTipoRepository tipoCdsTipoRepo;
	@Autowired
	private TipoCdsAttivitaRepository tipoCdsAttivitaRepo;
	@Autowired
	private TipoCdsAzioneRepository tipoCdsAzioneRepo;
	@Autowired
	private UserUtil userUtil;
	/**
	 * LOGGER
	 */
	
	@Override
	public List<SelectParentItemDto> attivita(String idProcedimento) {
		final StopWatch sw = LogUtil.startLog("attivita ", idProcedimento);
		logger.info("Start attivita {}", idProcedimento);
		try {
			return confCdsRepo.getAttivitaList(idProcedimento);	
		}finally {
			logger.info(LogUtil.stopLog(sw));
		}
		
	}

	@Override
	public List<SelectParentItemDto> tipo(String idProcedimento) {
		final StopWatch sw = LogUtil.startLog("tipo ", idProcedimento);
		logger.info("Start tipo {}", idProcedimento);
		try {
			return confCdsRepo.getTipoList(idProcedimento);	
		}finally {
			logger.info(LogUtil.stopLog(sw));
		}
	}

	@Override
	public List<SelectParentItemDto> azioni(String idProcedimento) {
		final StopWatch sw = LogUtil.startLog("azioni ", idProcedimento);
		logger.info("Start azioni {}", idProcedimento);
		try {
			return confCdsRepo.getAzioniList(idProcedimento);	
		}finally {
			logger.info(LogUtil.stopLog(sw));
		}
	}

	@Override
	public List<SelectParentItemDto> attivitaDisponibili() {
		final StopWatch sw = LogUtil.startLog("attivitaDisponibili");
		logger.info("Start attivitaDisponibili");
		try {
			return attivitaRepo.select();
		}finally {
			logger.info(LogUtil.stopLog(sw));
		}
	}

	@Override
	public List<SelectParentItemDto> tipoDisponibili() {
		final StopWatch sw = LogUtil.startLog("tipoDisponibili");
		logger.info("Start tipoDisponibili");
		try {
			return tipoRepo.select();
		}finally {
			logger.info(LogUtil.stopLog(sw));
		}
	}

	@Override
	public List<SelectParentItemDto> azioniDisponibili() {
		final StopWatch sw = LogUtil.startLog("azioniDisponibili");
		logger.info("Start azioniDisponibili");
		try {
			return azioniRepo.select();
		}finally {
			logger.info(LogUtil.stopLog(sw));
		}
	}

	@Override
	public void saveAttivita(String idProcedimento, List<SelectParentItemDto> attivitaList) {
		final StopWatch sw = LogUtil.startLog("saveAttivita");
		logger.info("Start saveAttivita");
		try {
			final Timestamp now = DateUtil.currentTimestamp();;
			tipoCdsAttivitaRepo.endConfigurazioniCdsAttivita(Integer.parseInt(idProcedimento));
			if(ListUtil.isNotEmpty(attivitaList)) {
				attivitaList.forEach(item ->{
					final TipoCdsAttivitaDTO entity = new TipoCdsAttivitaDTO();
					entity.setAttivita(item.getValue());
					entity.setStartDate(now);
					entity.setIdTipoProcedimento(Integer.parseInt(idProcedimento));
					entity.setIdOrganizzazione(userUtil.getIntegerId());
					entity.setUserCreate(SecurityUtil.getUsername());
					tipoCdsAttivitaRepo.insert(entity);
				});
			}
		}finally {
			logger.info(LogUtil.stopLog(sw));
		}
		
	}

	@Override
	public void saveTipo(String idProcedimento, List<SelectParentItemDto> tipiList) {
		final StopWatch sw = LogUtil.startLog("saveTipo");
		logger.info("Start saveTipo");
		try {
			final Timestamp now = DateUtil.currentTimestamp();;
			tipoCdsTipoRepo.endConfigurazioniCdsTipo(Integer.parseInt(idProcedimento));
			if(ListUtil.isNotEmpty(tipiList)) {
				tipiList.forEach(item ->{
					final TipoCdsTipoDTO entity = new TipoCdsTipoDTO();
					entity.setTipo(item.getValue());
					entity.setStartDate(now);
					entity.setIdTipoProcedimento(Integer.parseInt(idProcedimento));
					entity.setIdOrganizzazione(userUtil.getIntegerId());
					entity.setUserCreate(SecurityUtil.getUsername());
					tipoCdsTipoRepo.insert(entity);
				});
			}
		}finally {
			logger.info(LogUtil.stopLog(sw));
		}
		
	}

	@Override
	public void saveAzioni(String idProcedimento, List<SelectParentItemDto> azioniList) {
		final StopWatch sw = LogUtil.startLog("saveAzioni");
		logger.info("Start saveAzioni");
		try {
			final Timestamp now = DateUtil.currentTimestamp();;
			tipoCdsAzioneRepo.endConfigurazioniCdsAzione(Integer.parseInt(idProcedimento));
			if(ListUtil.isNotEmpty(azioniList)) {
				azioniList.forEach(item ->{
					final TipoCdsAzioneDTO entity = new TipoCdsAzioneDTO();
					entity.setAzione(item.getValue());
					entity.setStartDate(now);
					entity.setIdTipoProcedimento(Integer.parseInt(idProcedimento));
					entity.setIdOrganizzazione(userUtil.getIntegerId());
					entity.setUserCreate(SecurityUtil.getUsername());
					tipoCdsAzioneRepo.insert(entity);
				});
			}
		}finally {
			logger.info(LogUtil.stopLog(sw));
		}
		
	}

}
