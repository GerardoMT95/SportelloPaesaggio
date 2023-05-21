package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service;

import java.util.List;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.SelectOptionDto;

public interface ITipoAziendaService {
	/**
	 * @author Antonio La Gatta
	 * @date 9 giu 2022
	 * @return lista Tipo azienda
	 */
	List<SelectOptionDto> getList();
	
}
