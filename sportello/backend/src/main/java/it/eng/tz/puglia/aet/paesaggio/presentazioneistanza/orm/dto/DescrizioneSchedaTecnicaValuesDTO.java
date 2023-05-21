package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.util.UUID;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoQualificazione;

/**
 * DTO for table presentazione_istanza.descrizione_scheda_tecnica_values
 */
public class DescrizioneSchedaTecnicaValuesDTO implements Serializable
{

	private static final long serialVersionUID = 6932119860L;
	// COLUMN pratica_id
	private UUID praticaId;
	// COLUMN codice
	private String codice;
	// COLUMN text
	private String text;
	// COLUMN sezione
	private TipoQualificazione sezione;
	
	public DescrizioneSchedaTecnicaValuesDTO()
	{
		super();
	}
	public DescrizioneSchedaTecnicaValuesDTO(UUID praticaId, String codice, String text,  TipoQualificazione sezione)
	{
		this.praticaId = praticaId;
		this.codice = codice;
		this.text = text;
		this.sezione = sezione;
	}
	public DescrizioneSchedaTecnicaValuesDTO(UUID praticaId, String codice, TipoQualificazione sezione)
	{
		this(praticaId, codice, null, sezione);
	}

	public UUID getPraticaId()
	{
		return this.praticaId;
	}
	public void setPraticaId(UUID praticaId)
	{
		this.praticaId = praticaId;
	}

	public String getCodice()
	{
		return this.codice;
	}
	public void setCodice(String codice)
	{
		this.codice = codice;
	}

	public String getText()
	{
		return this.text;
	}
	public void setText(String text)
	{
		this.text = text;
	}

	public TipoQualificazione getSezione()
	{
		return this.sezione;
	}
	public void setSezione(TipoQualificazione sezione)
	{
		this.sezione = sezione;
	}

}
