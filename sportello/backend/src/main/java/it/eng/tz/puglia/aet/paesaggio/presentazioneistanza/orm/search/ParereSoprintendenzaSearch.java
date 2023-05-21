package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import java.util.Date;
import java.util.UUID;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.EsitoParere;
import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.parere_soprintendenza
 */
public class ParereSoprintendenzaSearch extends BaseSearchRequestBean
{

	private static final long serialVersionUID = 5135571695L;
	// COLUMN id
	private Long id;
	// COLUMN id_pratica
	private UUID idPratica;
	// COLUMN numero_protocollo
	private String numeroProtocollo;
	// COLUMN nominativo_istruttore
	private String nominativoIstruttore;
	// COLUMN esito_parere
	private EsitoParere esitoParere;
	// COLUMN note
	private String note;
	// COLUMN data_utente_creazione
	private Date dataUtenteCreazione;
	// COLUMN data_inserimento
	private Date dataInserimento;
	//COLUMN organizzazione_creazione
	private Long organizzazioneCreazione;

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

	public String getNumeroProtocollo()
	{
		return this.numeroProtocollo;
	}
	public void setNumeroProtocollo(String numeroProtocollo)
	{
		this.numeroProtocollo = numeroProtocollo;
	}

	public String getNominativoIstruttore()
	{
		return this.nominativoIstruttore;
	}
	public void setNominativoIstruttore(String nominativoIstruttore)
	{
		this.nominativoIstruttore = nominativoIstruttore;
	}

	public EsitoParere getEsitoParere()
	{
		return this.esitoParere;
	}
	public void setEsitoParere(EsitoParere esitoParere)
	{
		this.esitoParere = esitoParere;
	}

	public String getNote()
	{
		return this.note;
	}
	public void setNote(String note)
	{
		this.note = note;
	}

	public Date getDataUtenteCreazione()
	{
		return this.dataUtenteCreazione;
	}
	public void setDataUtenteCreazione(Date dataUtenteCreazione)
	{
		this.dataUtenteCreazione = dataUtenteCreazione;
	}

	public Date getDataInserimento()
	{
		return this.dataInserimento;
	}
	public void setDataInserimento(Date dataInserimento)
	{
		this.dataInserimento = dataInserimento;
	}
	
	public Long getOrganizzazioneCreazione()
	{
		return organizzazioneCreazione;
	}
	public void setOrganizzazioneCreazione(Long organizzazioneCreazione)
	{
		this.organizzazioneCreazione = organizzazioneCreazione;
	}

}
