package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.istanza.IstanzaDto;

public interface IstanzaService {
	public IstanzaDto createIstanza(IstanzaDto istanza);
	public IstanzaDto findIstanza(IstanzaDto istanza);
}
