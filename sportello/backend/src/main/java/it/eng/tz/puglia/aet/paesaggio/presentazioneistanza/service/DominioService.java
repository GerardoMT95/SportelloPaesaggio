package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service;

import java.util.List;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainNumberValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TipoContenutoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.IpaEnteDTO;


public interface DominioService {

	/**
	 * lista dei tipi procedimento attivi
	 * @return
	 */
	public List<PlainNumberValueLabel> getTipiProcedimento(Boolean ancheInattivi);
	
	/**
	 * lista dei tipi contenuto previsti per ogni tipo procedimento
	 * @return
	 */
	public List<TipoContenutoDTO> getTipiContenuto(Integer tipoProcedimento);

	List<IpaEnteDTO> autocompleteIpaEnte(final String query) throws Exception;

	

}
