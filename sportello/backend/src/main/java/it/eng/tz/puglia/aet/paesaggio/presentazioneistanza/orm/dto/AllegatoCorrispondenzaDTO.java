package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for table presentazione_istanza.allegato_corrispondenza
 */
public class AllegatoCorrispondenzaDTO implements Serializable
{

	private static final long serialVersionUID = 3074393928L;
	// COLUMN id_allegato
	private UUID idAllegato;
	// COLUMN id_corrispondenza
	private Long idCorrispondenza;

	public UUID getIdAllegato()
	{
		return this.idAllegato;
	}
	public void setIdAllegato(UUID idAllegato)
	{
		this.idAllegato = idAllegato;
	}

	public Long getIdCorrispondenza()
	{
		return this.idCorrispondenza;
	}
	public void setIdCorrispondenza(Long idCorrispondenza)
	{
		this.idCorrispondenza = idCorrispondenza;
	}

}
