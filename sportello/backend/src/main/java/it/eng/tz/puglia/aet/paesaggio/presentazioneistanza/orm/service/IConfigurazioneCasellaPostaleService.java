package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.*;
import java.sql.SQLException;
import java.util.List;

/**
 * Service interface CRUD for table presentazione_istanza.configurazione_casella_postale
 */
public interface IConfigurazioneCasellaPostaleService extends ICrudService<ConfigurazioneCasellaPostaleDTO, ConfigurazioneCasellaPostaleSearch, ConfigurazioneCasellaPostaleRepository>{

	List<ConfigurazioneCasellaPostaleDTO> getCaselleDaDisattivare();

	List<ConfigurazioneCasellaPostaleDTO> getCaselleDaAttivare();

	 
}
