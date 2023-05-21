package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.DatabaseConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.SelectOptionDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.TipoAziendaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.ITipoAziendaService;
import it.eng.tz.puglia.util.log.LogUtil;

@Service
public class TipoAziendaService implements ITipoAziendaService{

	private static final Logger LOGGER = LoggerFactory.getLogger(TipoAziendaService.class);
	
	@Autowired
	private TipoAziendaRepository repository;
	
	/**
	 * @author Antonio La Gatta
	 * @date 9 giu 2022
	 * @see it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.ITipoAziendaService#getList()
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public List<SelectOptionDto> getList() {
		final StopWatch sw = LogUtil.startLog("getList");
		LOGGER.info("getList");
		try {
			final List<SelectOptionDto> result = new ArrayList<>();
			this.repository.getList().forEach(item ->{
				final SelectOptionDto option = new SelectOptionDto();
				option.setValue(item.getId());
				option.setDescription(item.getNome());
				option.setPrivato(item.isPrivato());
				result.add(option);
			});
			return result;
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

}
