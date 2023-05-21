package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service;

import java.util.List;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainStringValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TipoProcedimentoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.TipoProcedimentoRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.TipoProcedimentoSearch;

/**
 * Service interface CRUD for table presentazione_istanza.tipo_procedimento
 */
public interface ITipoProcedimentoService extends ICrudService<TipoProcedimentoDTO, TipoProcedimentoSearch, TipoProcedimentoRepository>{
	List<PlainStringValueLabel> selectTipoProcedimenti();
}
