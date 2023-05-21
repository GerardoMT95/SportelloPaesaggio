package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import java.util.Date;
import java.util.UUID;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.EsitoParere;
import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.provvedimento_finale
 */
public class ProvvedimentoFinaleSearch extends BaseSearchRequestBean
{
    private static final long serialVersionUID = 7953953267L;
    //COLUMN id
    private Long id;
    //COLUMN id_pratica
    private UUID idPratica;
    //COLUMN numero_provvedimento
    private String numeroProvvedimento;
    //COLUMN data_rilascio_autorizzazione
    private Date dataRilascioAutorizzazione;
    //COLUMN esito_provvedimento
    private EsitoParere esitoProvvedimento;
    //COLUMN rup
    private String rup;
    //COLUMN draft
    private Boolean draft;
    //COLUMN id_corrispondenza
    private Long idCorrispondenza;
    
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public UUID getIdPratica()
	{
		return idPratica;
	}
	public void setIdPratica(UUID idPratica)
	{
		this.idPratica = idPratica;
	}
	
	public String getNumeroProvvedimento()
	{
		return numeroProvvedimento;
	}
	public void setNumeroProvvedimento(String numeroProvvedimento)
	{
		this.numeroProvvedimento = numeroProvvedimento;
	}
	
	public Date getDataRilascioAutorizzazione()
	{
		return dataRilascioAutorizzazione;
	}
	public void setDataRilascioAutorizzazione(Date dataRilascioAutorizzazione)
	{
		this.dataRilascioAutorizzazione = dataRilascioAutorizzazione;
	}
	
	public EsitoParere getEsitoProvvedimento()
	{
		return esitoProvvedimento;
	}
	public void setEsitoProvvedimento(EsitoParere esitoProvvedimento)
	{
		this.esitoProvvedimento = esitoProvvedimento;
	}
	
	public String getRup()
	{
		return rup;
	}
	public void setRup(String rup)
	{
		this.rup = rup;
	}
	
	public Boolean getDraft()
	{
		return draft;
	}
	public void setDraft(Boolean draft)
	{
		this.draft = draft;
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
