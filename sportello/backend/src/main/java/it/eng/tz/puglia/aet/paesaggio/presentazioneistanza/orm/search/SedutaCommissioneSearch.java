package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import java.util.Date;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.StatoSedutaCommissione;
import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.seduta_commissione
 */
public class SedutaCommissioneSearch extends BaseSearchRequestBean
{
	private static final long serialVersionUID = 561756351L;
	// COLUMN id
	private Long id;
	// COLUMN id_organizzazione
	private Integer idOrganizzazione;
	//COLUMN id_ente_delegato
	private Integer idEnteDelegato;
	// COLUMN nome_seduta
	private String nomeSeduta;
	// COLUMN data_seduta
	private Date dataSedutaDa;
	private Date dataSedutaA;
	// COLUMN username_utente_creazione
	private String usernameUtenteCreazione;
	// COLUMN data_creazione
	private Date dataCreazione;
	// COLUMN pubblica
	private Boolean pubblica;
	// COLUMN stato
	private StatoSedutaCommissione stato;
	
	private String codicePratica;

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
	
	public Date getDataSedutaDa()
	{
		return this.dataSedutaDa;
	}
	public void setDataSedutaDa(Date dataSedutaDa)
	{
		this.dataSedutaDa = dataSedutaDa;
	}
	
	public Date getDataSedutaA()
	{
		return this.dataSedutaA;
	}
	public void setDataSedutaA(Date dataSedutaA)
	{
		this.dataSedutaA = dataSedutaA;
	}

	public String getUsernameUtenteCreazione()
	{
		return this.usernameUtenteCreazione;
	}
	public void setUsernameUtenteCreazione(String usernameUtenteCreazione)
	{
		this.usernameUtenteCreazione = usernameUtenteCreazione;
	}

	public Date getDataCreazione()
	{
		return this.dataCreazione;
	}
	public void setDataCreazione(Date dataCreazione)
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
	
	public String getCodicePratica()
	{
		return codicePratica;
	}
	public void setCodicePratica(String codicePratica)
	{
		this.codicePratica = codicePratica;
	}
}
