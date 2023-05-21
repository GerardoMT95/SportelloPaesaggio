package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.disclaimer
 */
public class DisclaimerSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 2089799885L;
    //COLUMN id
    private String id;
    //COLUMN testo
    private String testo;
    //COLUMN tipo_procedimento
    private String tipoProcedimento;
    //COLUMN tipo_referente
    private String tipoReferente;

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

    public String getTipoProcedimento(){
        return this.tipoProcedimento;
    }

    public void setTipoProcedimento(String tipoProcedimento){
        this.tipoProcedimento = tipoProcedimento;
    }

    public String getTipoReferente(){
        return this.tipoReferente;
    }

    public void setTipoReferente(String tipoReferente){
        this.tipoReferente = tipoReferente;
    }


}
