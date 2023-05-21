package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.*;
import java.util.List;

/**
 * Service interface CRUD for table presentazione_istanza.web_content
 */
public interface IWebContentService extends ICrudService<WebContentDTO, WebContentSearch, WebContentRepository>{

	/**
	 * aggiorna il canpo contenuto solo per i messaggi effettivamente trovati (per codiceContenuto)
	 * @author acolaianni
	 *
	 * @param dtos
	 * @return numero di record aggiornati
	 */
	Integer updateSelected(List<WebContentDTO> dtos);

}
