package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.StatoSedutaCommissione;

/**
 * DTO for table presentazione_istanza.seduta_commissione
 */
public class SedutaCommissioneDTO implements Serializable
{
	private static final long serialVersionUID = 4806507853L;
	// COLUMN id
	private Long id;
	// COLUMN id_organizzazione
	private Integer idOrganizzazione;
	//COLUMN id_ente_delegato
	private Integer idEnteDelegato;
	// COLUMN nome_seduta
	@NotNull
	@NotEmpty
	private String nomeSeduta;
	// COLUMN data_seduta
	@NotNull
	private Timestamp dataSeduta;
	// COLUMN username_utente_creazione
	private String usernameUtenteCreazione;
	// COLUMN data_creazione
	private Timestamp dataCreazione;
	//COLUMN pubblica
	@NotNull
	private Boolean pubblica;
	//COLUMN stato
	private StatoSedutaCommissione stato;
	
	@NotNull
	private List<DestinatarioDTO> destinatari;

	public Long getId()
	{
		return this.id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}

	public Integer getIdOrganizzazione()
	{
		return this.idOrganizzazione;
	}
	public void setIdOrganizzazione(Integer idOrganizzazione)
	{
		this.idOrganizzazione = idOrganizzazione;
	}

	public Integer getIdEnteDelegato()
	{
		return idEnteDelegato;
	}
	public void setIdEnteDelegato(Integer idEnteDelegato)
	{
		this.idEnteDelegato = idEnteDelegato;
	}
	
	public String getNomeSeduta()
	{
		return nomeSeduta;
	}
	public void setNomeSeduta(String nomeSeduta)
	{
		this.nomeSeduta = nomeSeduta;
	}
	
	public Timestamp getDataSeduta()
	{
		return this.dataSeduta;
	}
	public void setDataSeduta(Timestamp dataSeduta)
	{
		this.dataSeduta = dataSeduta;
	}

	public String getUsernameUtenteCreazione()
	{
		return this.usernameUtenteCreazione;
	}
	public void setUsernameUtenteCreazione(String usernameUtenteCreazione)
	{
		this.usernameUtenteCreazione = usernameUtenteCreazione;
	}

	public Timestamp getDataCreazione()
	{
		return this.dataCreazione;
	}
	public void setDataCreazione(Timestamp dataCreazione)
	{
		this.dataCreazione = dataCreazione;
	}

	public Boolean getPubblica()
	{
		return pubblica;
	}
	public void setPubblica(Boolean pubblica)
	{
		this.pubblica = pubblica;
	}
	
	public StatoSedutaCommissione getStato()
	{
		return stato;
	}
	public void setStato(StatoSedutaCommissione stato)
	{
		this.stato = stato;
	}
	
	public List<DestinatarioDTO> getDestinatari()
	{
		return destinatari;
	}
	public void setDestinatari(List<DestinatarioDTO> destinatari)
	{
		this.destinatari = destinatari;
	}

}
