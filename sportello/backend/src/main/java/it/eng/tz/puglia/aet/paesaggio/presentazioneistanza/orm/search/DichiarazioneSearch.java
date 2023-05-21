package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.dichiarazione
 */
public class DichiarazioneSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 6251929920L;
    //COLUMN id
    private String id;
    //COLUMN testo
    private String testo;
    //COLUMN label_cb
    private String labelCb;
    //COLUMN data_inizio
    private String dataInizio;
    //COLUMN data_fine
    private String dataFine;

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getTesto(){
        return this.testo;
    }

    public void setTesto(String testo){
        this.testo = testo;
    }

    public String getLabelCb(){
        return this.labelCb;
    }

    public void setLabelCb(String labelCb){
        this.labelCb = labelCb;
    }

    public String getDataInizio(){
        return this.dataInizio;
    }

    public void setDataInizio(String dataInizio){
        this.dataInizio = dataInizio;
    }

    public String getDataFine(){
        return this.dataFine;
    }

    public void setDataFine(String dataFine){
        this.dataFine = dataFine;
    }


}
