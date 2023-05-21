package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.EsitoParere;

/**
 * DTO for table presentazione_istanza.parere_soprintendenza
 */
public class ParereSoprintendenzaDTO implements Serializable
{
	private static final long serialVersionUID = 4111130000L;
	// COLUMN id
	private Long id;
	// COLUMN id_pratica
	private UUID idPratica;
	// COLUMN numero_protocollo
	private String numeroProtocollo;
	// COLUMN nominativo_istruttore
	private String nominativoIstruttore;
	// COLUMN esito_parere
	@NotNull(message="Esito parere obbligatorio")
	private EsitoParere esitoParere;
	// COLUMN note
	private String note;
	//COLUMN dettaglio_prescrizione
	private String dettaglioPrescrizione;
	// COLUMN data_utente_creazione
	private String usernameUtenteCreazione;
	// COLUMN data_inserimento
	private Timestamp dataInserimento;
	//COLUMN organizzazione_creazione
	private Long organizzazioneCreazione;
	//COLUMN id_corrispondenza
	private Long idCorrispondenza;
	

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

	public String getDettaglioPrescrizione()
	{
		return dettaglioPrescrizione;
	}
	public void setDettaglioPrescrizione(String dettaglioPrescrizione)
	{
		this.dettaglioPrescrizione = dettaglioPrescrizione;
	}
	
	public String getUsernameUtenteCreazione()
	{
		return this.usernameUtenteCreazione;
	}
	public void setUsernameUtenteCreazione(String usernameUtenteCreazione)
	{
		this.usernameUtenteCreazione = usernameUtenteCreazione;
	}

	public Timestamp getDataInserimento()
	{
		return this.dataInserimento;
	}
	public void setDataInserimento(Timestamp dataInserimento)
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
	
	public Long getIdCorrispondenza()
	{
		return idCorrispondenza;
	}
	public void setIdCorrispondenza(Long idCorrispondenza)
	{
		this.idCorrispondenza = idCorrispondenza;
	}
	
}
