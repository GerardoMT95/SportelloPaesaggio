package it.eng.tz.puglia.autpae.dto.config;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class TipoDocumentoSportelloPaesaggioConfigDTO implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<UUID> tipiSelezionati;

	public List<UUID> getTipiSelezionati() {
		return tipiSelezionati;
	}

	public void setTipiSelezionati(List<UUID> tipiSelezionati) {
		this.tipiSelezionati = tipiSelezionati;
	}
	
	
	
}
