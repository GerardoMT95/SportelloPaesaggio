package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.configuration.DatabaseConfiguration;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Ruoli;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.feign.controller.UserUtil;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TariffaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.TariffaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.TariffaSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.GenericCrudService;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.ITariffaService;
import it.eng.tz.puglia.error.exception.CustomValidationException;

@Service
public class TariffaService extends GenericCrudService<TariffaDTO, TariffaSearch, TariffaRepository> implements ITariffaService{
	
	@Autowired
	private TariffaRepository dao;
	@Autowired
	private UserUtil userUtil;
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_READ, readOnly=true, timeout=60000, rollbackFor= {Exception.class})
	public List<TariffaDTO> getTariffeByIdProcedimento(final String idTipoProcedimento) throws Exception{
		Ruoli ruolo = userUtil.getRuolo(userUtil.getCodice_GruppoIdRuolo());
		if(ruolo != Ruoli.AMMINISTRATORE) {
			throw new Exception("Devi essere amministratore di un ente per poter visualizzare le configurazioni delle tariffe");
		}
		return this.dao.getTariffeByTipoProcedimento(Integer.valueOf(idTipoProcedimento));
	}
	
	@Override
	@Transactional(transactionManager=DatabaseConfiguration.TX_WRITE, readOnly=false, timeout=60000, rollbackFor= {Exception.class})
	public void saveConfigurazioneTariffe(final List<TariffaDTO> tariffe) throws Exception {
		Ruoli ruolo = userUtil.getRuolo(userUtil.getCodice_GruppoIdRuolo());
		if(ruolo != Ruoli.AMMINISTRATORE) {
			throw new Exception("Devi essere amministratore di un ente per poter salvare le configurazioni delle tariffe");
		}
		if(tariffe != null && tariffe.size() > 0) {
			this.dao.endConfigurazioniTariffa(tariffe.get(0).getIdTipoProcedimento());
			for(int i = 0; i<tariffe.size(); i++) {
				if((i>0 && i<tariffe.size()-1) && (tariffe.get(i-1).getSogliaMassima()!=tariffe.get(i).getSogliaMinima())) {
	                throw new CustomValidationException("La soglia massima di ogni riga deve essere uguale alla soglia minima della riga successiva.");
				}
				tariffe.get(i).setCreateDate(new Timestamp(System.currentTimeMillis()));
				tariffe.get(i).setStartDate(new Timestamp(System.currentTimeMillis()));
				tariffe.get(i).setCreateUser(userUtil.getMyProfile().getUsername());
				tariffe.get(i).setIdOrganizzazione(userUtil.getIntegerId());
				this.dao.insert(tariffe.get(i));
			}
		}
	}

	
	
	@Override
	protected TariffaRepository getDao() {
		return this.dao;
	}

	@Override
	protected void validateInsertDTO(TariffaDTO entity) throws CustomValidationException {
		// TODO
		
	}

	@Override
	protected void validateUpdateDTO(TariffaDTO entity) throws CustomValidationException {
		// TODO
		
	}

}
