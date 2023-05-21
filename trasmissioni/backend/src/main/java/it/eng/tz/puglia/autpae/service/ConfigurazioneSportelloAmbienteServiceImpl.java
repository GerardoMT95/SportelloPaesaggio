package it.eng.tz.puglia.autpae.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.puglia.autpae.dto.config.TipoDocumentoSportelloPaesaggioConfigDTO;
import it.eng.tz.puglia.autpae.generated.orm.dto.ConfigurazioneDTO;
import it.eng.tz.puglia.autpae.generated.orm.dto.VPaesaggioTipoCaricaDocumentoDTO;
import it.eng.tz.puglia.autpae.generated.orm.repository.ConfigurazioneRepository;
import it.eng.tz.puglia.autpae.generated.orm.repository.VPaesaggioTipoCaricaDocumentoRepository;
import it.eng.tz.puglia.autpae.service.interfacce.ConfigurazioneSportelloAmbienteService;

@Service
public class ConfigurazioneSportelloAmbienteServiceImpl implements ConfigurazioneSportelloAmbienteService {
	
	@Autowired
	VPaesaggioTipoCaricaDocumentoRepository tipoDocRepo;
	
	@Autowired
	ConfigurazioneService confSvc;
	
	@Override 
	@Transactional(propagation=Propagation.SUPPORTS,rollbackFor={Exception.class}, timeout=60000, readOnly=true )
	public List<VPaesaggioTipoCaricaDocumentoDTO> getElencoTipoDocumentoSportello() {
		return tipoDocRepo.select();
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,rollbackFor={Exception.class}, timeout=60000, readOnly=true )
	public List<UUID> getSelected() throws Exception {
		TipoDocumentoSportelloPaesaggioConfigDTO obj =
				confSvc.findConfigurazioneCorrente(new Date(), TipoDocumentoSportelloPaesaggioConfigDTO.class); 
		return obj.getTipiSelezionati();		
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={Exception.class}, timeout=60000, readOnly=false )
	public int setSelected(List<UUID> selezionati,String currentUser) throws Exception {
		TipoDocumentoSportelloPaesaggioConfigDTO obj =
				confSvc.findConfigurazioneCorrente(new Date(), TipoDocumentoSportelloPaesaggioConfigDTO.class);
		obj.setTipiSelezionati(selezionati);
		confSvc.upsertConfigurazione(obj, TipoDocumentoSportelloPaesaggioConfigDTO.class, currentUser);
		return 1;
	}

		
}