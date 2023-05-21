package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import java.util.Date;
import java.util.UUID;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.StoricoRichiesta;
import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.sospensione_archiviazione_attivazione
 */
public class SospensioneArchiviazioneAttivazioneSearch extends BaseSearchRequestBean
{

	private static final long serialVersionUID = 4039194486L;
	// COLUMN id
	private Long id;
	// COLUMN id_pratica
	private UUID idPratica;
	// COLUMN type
	private StoricoRichiesta type;
	// COLUMN draft
	private Boolean draft;
	// COLUMN note
	private String note;
	// COLUMN data
	private Date dataDa;
	private Date dataA;
	// COLUMN username_utente_creazione
	private String usernameUtenteCreazione;

	private String codicePratica;

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

	public Date getDataDa()
	{
		return dataDa;
	}
	public void setDataDa(Date dataDa)
	{
		this.dataDa = dataDa;
	}
	
	public Date getDataA()
	{
		return dataA;
	}
	public void setDataA(Date dataA)
	{
		this.dataA = dataA;
	}
	
	public String getUsernameUtenteCreazione()
	{
		return this.usernameUtenteCreazione;
	}
	public void setUsernameUtenteCreazione(String usernameUtenteCreazione)
	{
		this.usernameUtenteCreazione = usernameUtenteCreazione;
	}

	public String getCodicePratica()
	{
		return codicePratica;
	}
	public void setCodicePratica(String codicePratica)
	{
		this.codicePratica = codicePratica;
	}

}
