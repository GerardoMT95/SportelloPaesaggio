package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.DatabaseConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.CdsGetConfigurazioneDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.CdsSaveConfigurazioneDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.SelectItemDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.SelectParentItemDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.exceptions.CustomOperationToAdviceException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.IConferenzaServiziAdminOrmService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.IConferenzaServiziAdminService;
import it.eng.tz.puglia.error.exception.CustomValidationException;
import it.eng.tz.puglia.util.list.ListUtil;
import it.eng.tz.puglia.util.log.LogUtil;

@Service
public class ConferenzaServiziAdminService implements IConferenzaServiziAdminService{
	/**
	 * LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ConferenzaServiziAdminService.class);
	
	@Autowired
	private IConferenzaServiziAdminOrmService ormService;
	
	
	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public CdsGetConfigurazioneDto getConfigurazione(String idProcedimento) throws Exception {
		final StopWatch sw = LogUtil.startLog("getConfigurazione ", idProcedimento);
		LOGGER.info("getConfigurazione {}", idProcedimento);
		try {
			final CdsGetConfigurazioneDto result = new CdsGetConfigurazioneDto();
			result.setAttivita(this.ormService.attivita(idProcedimento));
			result.setAttivitaSelezionabili(this.ormService.attivitaDisponibili());
			result.setAzioni(this.ormService.azioni(idProcedimento));
			result.setAzioniSelezionabili(this.ormService.azioniDisponibili());
			result.setTipo(this.ormService.tipo(idProcedimento));
			result.setTipoSelezionabili(this.ormService.tipoDisponibili());
			return result;
		}finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED, transactionManager = DatabaseConfiguration.TX_WRITE)
	public void saveConfigurazione(String idProcedimento, CdsSaveConfigurazioneDto dto) throws Exception {
		final StopWatch sw = LogUtil.startLog("saveConfigurazione ", idProcedimento);
		LOGGER.info("saveConfigurazione {}", idProcedimento);
		String error ="";
		try {
			if(ListUtil.isEmpty(dto.getAttivita())
            || ListUtil.isEmpty(dto.getAzioni  ())
            || ListUtil.isEmpty(dto.getTipo    ())
            ){
            	throw new CustomValidationException(error="Errore nel salvataggio della configurazione cds. Assicurarsi di aver valorizzato tutti i campi");
            }
			
			final List<SelectParentItemDto> azioniDisponibili = this.ormService.azioniDisponibili();
			for (final Iterator<SelectParentItemDto> iterator = dto.getAzioni().iterator(); iterator.hasNext();) {
				final SelectItemDto azione = iterator.next();
				final String attivitaPadre = azioniDisponibili.stream().filter(item ->{return item.getValue().equals(azione.getValue());}).findFirst().get().getParent();
				final Optional<SelectParentItemDto> attivita = dto.getAttivita().stream().filter(item ->{return item.getValue().equals(attivitaPadre);}).findFirst();
				if(attivita.isPresent() == false) {
					throw new CustomValidationException(error="Le azioni selezionate non sono legate alle attività selezionate");
				}
				
			}
		
		
			this.ormService.saveAttivita(idProcedimento, dto.getAttivita());
			this.ormService.saveAzioni  (idProcedimento, dto.getAzioni  ());
			this.ormService.saveTipo    (idProcedimento, dto.getTipo    ());
		}
		catch (CustomValidationException e) {
			LOGGER.error("Errore nella comparazione dei file in checkOriginalAndSigned", e);
			throw new CustomOperationToAdviceException(error);
		}
		finally {
			LOGGER.info(LogUtil.stopLog(sw));
		}
		
	}

}
