package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import java.util.UUID;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.integrazione
 */
public class IntegrazioneSearch extends BaseSearchRequestBean
{

	private static final long serialVersionUID = 6740251349L;
	// COLUMN id
	private Integer id;
	// COLUMN data_creazione
	private String dataCreazione;
	// COLUMN data_caricamento
	private String dataCaricamento;
	// COLUMN stato
	private String stato;
	// COLUMN richiesta_integrazione
	private String richiestaIntegrazione;
	// COLUMN username_utente_creazione
	private String usernameUtenteCreazione;
	// COLUMN descrizione
	private String descrizione;
	// COLUMN note
	private String note;
	// COLUMN pratica_id
	private UUID praticaId;

	private Boolean attiva;

	public Integer getId()
	{
		return this.id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getDataCreazione()
	{
		return this.dataCreazione;
	}
	public void setDataCreazione(String dataCreazione)
	{
		this.dataCreazione = dataCreazione;
	}

	public String getDataCaricamento()
	{
		return this.dataCaricamento;
	}
	public void setDataCaricamento(String dataCaricamento)
	{
		this.dataCaricamento = dataCaricamento;
	}

	public String getStato()
	{
		return this.stato;
	}
	public void setStato(String stato)
	{
		this.stato = stato;
	}

	public String getRichiestaIntegrazione()
	{
		return this.richiestaIntegrazione;
	}
	public void setRichiestaIntegrazione(String richiestaIntegrazione)
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

	public Boolean getAttiva()
	{
		return attiva;
	}
	public void setAttiva(Boolean attiva)
	{
		this.attiva = attiva;
	}

}
