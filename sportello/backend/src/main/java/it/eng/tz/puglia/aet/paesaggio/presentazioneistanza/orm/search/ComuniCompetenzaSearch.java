package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import java.util.Date;
import java.util.UUID;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.comuni_competenza
 */
public class ComuniCompetenzaSearch extends BaseSearchRequestBean
{
    private static final long serialVersionUID = 876510635L;
    //COLUMN pratica_id
    private UUID praticaId;
    //COLUMN ente_id
    private Integer enteId;
    //COLUMN data_inserimento
    private Date dataInserimento;
    
    private boolean soloComuniIntervento;
 
    public ComuniCompetenzaSearch()
    {
    	super();
    }

	public UUID getPraticaId()
	{
		return praticaId;
	}
	public void setPraticaId(UUID praticaId)
	{
		this.praticaId = praticaId;
	}
	
	public Integer getEnteId()
	{
		return enteId;
	}
	public void setEnteId(Integer enteId)
	{
		this.enteId = enteId;
	}
	
	public Date getDataInserimento()
	{
		return dataInserimento;
	}
	public void setDataInserimento(Date dataInserimento)
	{
		this.dataInserimento = dataInserimento;
	}
	
	public boolean isSoloComuniIntervento()
	{
		return soloComuniIntervento;
	}
	public void setSoloComuniIntervento(boolean soloComuniIntervento)
	{
		this.soloComuniIntervento = soloComuniIntervento;
	}
}
