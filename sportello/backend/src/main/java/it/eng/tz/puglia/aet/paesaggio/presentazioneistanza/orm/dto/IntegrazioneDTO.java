package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.StatoIntegrazioneDocumentale;

/**
 * DTO for table presentazione_istanza.integrazione
 */
public class IntegrazioneDTO implements Serializable
{
	private static final long serialVersionUID = 1268571411L;
	// COLUMN id
	private Integer id;
	// COLUMN data_creazione
	private Timestamp dataCreazione;
	// COLUMN data_caricamento
	private Timestamp dataCaricamento;
	// COLUMN stato
	private StatoIntegrazioneDocumentale stato;
	// COLUMN richiesta_integrazione
	private Boolean richiestaIntegrazione;
	// COLUMN username_utente_creazione
	private String usernameUtenteCreazione;
	// COLUMN descrizione
	private String descrizione;
	// COLUMN note
	private String note;
	// COLUMN pratica_id
	private UUID praticaId;

	public Integer getId()
	{
		return this.id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}

	public Timestamp getDataCreazione()
	{
		return this.dataCreazione;
	}
	public void setDataCreazione(Timestamp dataCreazione)
	{
		this.dataCreazione = dataCreazione;
	}

	public Timestamp getDataCaricamento()
	{
		return this.dataCaricamento;
	}
	public void setDataCaricamento(Timestamp dataCaricamento)
	{
		this.dataCaricamento = dataCaricamento;
	}

	public StatoIntegrazioneDocumentale getStato()
	{
		return this.stato;
	}
	public void setStato(StatoIntegrazioneDocumentale stato)
	{
		this.stato = stato;
	}

	public Boolean getRichiestaIntegrazione()
	{
		return this.richiestaIntegrazione;
	}
	public void setRichiestaIntegrazione(Boolean richiestaIntegrazione)
	{
		this.richiestaIntegrazione = richiestaIntegrazione;
	}

	public String getUsernameUtenteCreazione()
	{
		return this.usernameUtenteCreazione;
	}
	public void setUsernameUtenteCreazione(String usernameUtenteCreazione)
	{
		this.usernameUtenteCreazione = usernameUtenteCreazione;
	}

	public String getDescrizione()
	{
		return this.descrizione;
	}
	public void setDescrizione(String descrizione)
	{
		this.descrizione = descrizione;
	}

	public String getNote()
	{
		return this.note;
	}
	public void setNote(String note)
	{
		this.note = note;
	}

	public UUID getPraticaId()
	{
		return this.praticaId;
	}
	public void setPraticaId(UUID praticaId)
	{
		this.praticaId = praticaId;
	}

}
