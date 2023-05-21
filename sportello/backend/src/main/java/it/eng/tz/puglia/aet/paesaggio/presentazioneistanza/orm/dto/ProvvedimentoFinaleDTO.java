package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.EsitoParere;

/**
 * DTO for table presentazione_istanza.provvedimento_finale
 */
public class ProvvedimentoFinaleDTO implements Serializable
{

	private static final long serialVersionUID = 3910787324L;
	// COLUMN id
	@NotNull(message="Id Campo obbligatorio")
	private Long id;
	// COLUMN id_pratica
	@NotNull(message="Id pratica Campo obbligatorio")
	private UUID idPratica;
	// COLUMN numero_provvedimento
	@NotNull(message="Numero provvedimento obbligatorio")
	@NotBlank(message="Numero provvedimento obbligatorio")
	private String numeroProvvedimento;
	// COLUMN data_rilascio_autorizzazione
	@NotNull(message="Data rilascio autorizzazione obbligatorio")
	private Timestamp dataRilascioAutorizzazione;
	// COLUMN esito_provvedimento
	@NotNull(message="Esito provvedimento obbligatorio")
	private EsitoParere esitoProvvedimento;
	// COLUMN rup
	@NotNull(message="Rup obbligatorio")
	@NotBlank(message="Rup obbligatorio")
	private String rup;
	// COLUMN draft
	private Boolean draft;
	// COLUMN id_corrispondenza
	@NotNull(message="Corrispondenza obbligatoria")
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

	public String getNumeroProvvedimento()
	{
		return this.numeroProvvedimento;
	}
	public void setNumeroProvvedimento(String numeroProvvedimento)
	{
		this.numeroProvvedimento = numeroProvvedimento;
	}

	public Timestamp getDataRilascioAutorizzazione()
	{
		return this.dataRilascioAutorizzazione;
	}
	public void setDataRilascioAutorizzazione(Timestamp dataRilascioAutorizzazione)
	{
		this.dataRilascioAutorizzazione = dataRilascioAutorizzazione;
	}

	public EsitoParere getEsitoProvvedimento()
	{
		return this.esitoProvvedimento;
	}
	public void setEsitoProvvedimento(EsitoParere esitoProvvedimento)
	{
		this.esitoProvvedimento = esitoProvvedimento;
	}

	public String getRup()
	{
		return this.rup;
	}
	public void setRup(String rup)
	{
		this.rup = rup;
	}

	public Boolean getDraft()
	{
		return this.draft;
	}
	public void setDraft(Boolean draft)
	{
		this.draft = draft;
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
