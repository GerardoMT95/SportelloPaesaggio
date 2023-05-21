package it.eng.tz.puglia.autpae.generated.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table common.paesaggio_organizzazione_attributi
 */
public class PaesaggioOrganizzazioneAttributiSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 3101585618L;
    //COLUMN id
    private String id;
    //COLUMN applicazione_id
    private String applicazioneId;
    //COLUMN paesaggio_organizzazione_id
    private String paesaggioOrganizzazioneId;
    //COLUMN data_creazione
    private String dataCreazione;
    //COLUMN data_termine
    private String dataTermine;
    
    private java.util.Date dataCreazioneDa;
    private java.util.Date dataCreazioneA;
    private java.util.Date dataTermineDa;
    private java.util.Date dataTermineA;

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getApplicazioneId(){
        return this.applicazioneId;
    }

    public void setApplicazioneId(String applicazioneId){
        this.applicazioneId = applicazioneId;
    }

    public String getPaesaggioOrganizzazioneId(){
        return this.paesaggioOrganizzazioneId;
    }

    public void setPaesaggioOrganizzazioneId(String paesaggioOrganizzazioneId){
        this.paesaggioOrganizzazioneId = paesaggioOrganizzazioneId;
    }

    public String getDataCreazione(){
        return this.dataCreazione;
    }

    public void setDataCreazione(String dataCreazione){
        this.dataCreazione = dataCreazione;
    }

    public String getDataTermine(){
        return this.dataTermine;
    }

    public void setDataTermine(String dataTermine){
        this.dataTermine = dataTermine;
    }

	public java.util.Date getDataCreazioneDa()
	{
		return dataCreazioneDa;
	}
	public void setDataCreazioneDa(java.util.Date dataCreazioneDa)
	{
		this.dataCreazioneDa = dataCreazioneDa;
	}

	public java.util.Date getDataCreazioneA()
	{
		return dataCreazioneA;
	}
	public void setDataCreazioneA(java.util.Date dataCreazioneA)
	{
		this.dataCreazioneA = dataCreazioneA;
	}

	public java.util.Date getDataTermineDa()
	{
		return dataTermineDa;
	}
	public void setDataTermineDa(java.util.Date dataTermineDa)
	{
		this.dataTermineDa = dataTermineDa;
	}

	public java.util.Date getDataTermineA()
	{
		return dataTermineA;
	}
	public void setDataTermineA(java.util.Date dataTermineA)
	{
		this.dataTermineA = dataTermineA;
	}
    
    


}
