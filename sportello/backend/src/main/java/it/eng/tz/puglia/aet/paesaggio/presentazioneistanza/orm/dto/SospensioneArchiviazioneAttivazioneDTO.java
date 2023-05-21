package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.AttivitaDaEspletare;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.StoricoRichiesta;

/**
 * DTO for table presentazione_istanza.sospensione_archiviazione_attivazione
 */
public class SospensioneArchiviazioneAttivazioneDTO implements Serializable
{
	private static final long serialVersionUID = 6975005576L;
	// COLUMN id
	private Long id;
	// COLUMN id_pratica
	private UUID idPratica;
	private String codicePratica;
	// COLUMN type
	private StoricoRichiesta type;
	// COLUMN draft
	private Boolean draft;
	// COLUMN note
	private String note;
	// COLUMN data
	private Date data;
	// COLUMN username_utente_creazione
	private String usernameUtenteCreazione;
	// COLUMN stato_pratica
	private AttivitaDaEspletare statoPratica;
	
	public SospensioneArchiviazioneAttivazioneDTO()
	{
		super();
		draft = true;
	}
	public SospensioneArchiviazioneAttivazioneDTO(UUID idPratica, StoricoRichiesta type, String usernameUtenteCreazione)
	{
		this();
		this.type = type;
		this.idPratica = idPratica;
		this.usernameUtenteCreazione = usernameUtenteCreazione;
	}

	public Long getId()
	{
		return this.id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}

	public UUID getIdPratica()
	{
		return this.idPratica;
	}
	public void setIdPratica(UUID idPratica)
	{
		this.idPratica = idPratica;
	}

	public String getCodicePratica()
	{
		return codicePratica;
	}
	public void setCodicePratica(String codicePratica)
	{
		this.codicePratica = codicePratica;
	}

	public StoricoRichiesta getType()
	{
		return this.type;
	}
	public void setType(StoricoRichiesta type)
	{
		this.type = type;
	}
	
	public Boolean getDraft()
	{
		return this.draft;
	}

	public void setDraft(Boolean draft)
	{
		this.draft = draft;
	}

	public String getNote()
	{
		return this.note;
	}
	public void setNote(String note)
	{
		this.note = note;
	}

	public Date getData()
	{
		return this.data;
	}
	public void setData(Date data)
	{
		this.data = data;
	}

	public String getUsernameUtenteCreazione()
	{
		return this.usernameUtenteCreazione;
	}
	public void setUsernameUtenteCreazione(String usernameUtenteCreazione)
	{
		this.usernameUtenteCreazione = usernameUtenteCreazione;
	}
	
	public AttivitaDaEspletare getStatoPratica()
	{
		return statoPratica;
	}
	public void setStatoPratica(AttivitaDaEspletare statoPratica)
	{
		this.statoPratica = statoPratica;
	}

}
