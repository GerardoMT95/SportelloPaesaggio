package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service;

import java.util.List;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.*;

/**
 * Service interface CRUD for table presentazione_istanza.tariffa
 */
public interface ITariffaService extends ICrudService<TariffaDTO, TariffaSearch, TariffaRepository>{
	
	public List<TariffaDTO> getTariffeByIdProcedimento(final String idTipoProcedimento) throws Exception;
	public void saveConfigurazioneTariffe(final List<TariffaDTO> tariffe) throws Exception;


}
