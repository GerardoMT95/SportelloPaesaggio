package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service;

import java.sql.SQLException;

import com.fasterxml.jackson.core.JsonProcessingException;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;

public interface DiogeneDescrittoreService {

	void buildAndSendFile(PraticaDTO dto, AllegatiDTO allegato, String nomeSottocartella)
			throws JsonProcessingException, SQLException;

	
}
