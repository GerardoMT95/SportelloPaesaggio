package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.tno_pptr_strumenti_urbanistici
 */
public class TnoPptrStrumentiUrbanisticiSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 2231113509L;
    //COLUMN id
    private String id;
    //COLUMN istat_6_prov
    private String istat6Prov;
    //COLUMN tipo_strumento
    private String tipoStrumento;
    //COLUMN stato
    private String stato;
    //COLUMN atto
    private String atto;
    //COLUMN data_atto
    private String dataAtto;
    //COLUMN utente_modifica
    private String utenteModifica;
    //COLUMN data_modifica
    private String dataModifica;

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getIstat6Prov(){
        return this.istat6Prov;
    }

    public void setIstat6Prov(String istat6Prov){
        this.istat6Prov = istat6Prov;
    }

    public String getTipoStrumento(){
        return this.tipoStrumento;
    }

    public void setTipoStrumento(String tipoStrumento){
        this.tipoStrumento = tipoStrumento;
    }

    public String getStato(){
        return this.stato;
    }

    public void setStato(String stato){
        this.stato = stato;
    }

    public String getAtto(){
        return this.atto;
    }

    public void setAtto(String atto){
        this.atto = atto;
    }

    public String getDataAtto(){
        return this.dataAtto;
    }

    public void setDataAtto(String dataAtto){
        this.dataAtto = dataAtto;
    }

    public String getUtenteModifica(){
        return this.utenteModifica;
    }

    public void setUtenteModifica(String utenteModifica){
        this.utenteModifica = utenteModifica;
    }

    public String getDataModifica(){
        return this.dataModifica;
    }

    public void setDataModifica(String dataModifica){
        this.dataModifica = dataModifica;
    }


}
