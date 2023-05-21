package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.NumeroPraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.NumeroPraticaRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.NumeroPraticaSearch;

/**
 * Service interface CRUD for table presentazione_istanza.numero_pratica
 */
public interface INumeroPraticaService extends ICrudService<NumeroPraticaDTO, NumeroPraticaSearch, NumeroPraticaRepository>{


	long insertNumeroPratica(Long nextNumero);

	NumeroPraticaDTO getOldNumeroPratica();

	long initNumPraticaAnnoCorrente();

	long checkNumPraticaAnnoCorrente();
	
	/**
	 * utilizzato per la migrazione per avanzare nell'anno il numero pratica
	 * @author acolaianni
	 *
	 * @param anno
	 * @param numero
	 * @return
	 */
	public long avanzaNumPraticaAnno(int anno,int numero);

}
