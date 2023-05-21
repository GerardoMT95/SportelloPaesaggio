package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.assogg_procedure_edilizie
 */
public class AssoggProcedureEdilizieSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 8181374019L;
    //COLUMN pratica_id
    private String praticaId;
    //COLUMN flag_assoggettato
    private String flagAssoggettato;
    //COLUMN no_assogg_specificare
    private String noAssoggSpecificare;
    //COLUMN assogg_flag_pratica_in_corso
    private String assoggFlagPraticaInCorso;
    //COLUMN assogg_ente_pratica_in_corso
    private String assoggEntePraticaInCorso;
    //COLUMN assogg_datapr_pratica_in_corso
    private String assoggDataprPraticaInCorso;
    //COLUMN assogg_flag_parere_urb_espr
    private String assoggFlagParereUrbEspr;
    //COLUMN assogg_data_approv_pratica
    private String assoggDataApprovPratica;

    public String getPraticaId(){
        return this.praticaId;
    }

    public void setPraticaId(String praticaId){
        this.praticaId = praticaId;
    }

    public String getFlagAssoggettato(){
        return this.flagAssoggettato;
    }

    public void setFlagAssoggettato(String flagAssoggettato){
        this.flagAssoggettato = flagAssoggettato;
    }

    public String getNoAssoggSpecificare(){
        return this.noAssoggSpecificare;
    }

    public void setNoAssoggSpecificare(String noAssoggSpecificare){
        this.noAssoggSpecificare = noAssoggSpecificare;
    }

    public String getAssoggFlagPraticaInCorso(){
        return this.assoggFlagPraticaInCorso;
    }

    public void setAssoggFlagPraticaInCorso(String assoggFlagPraticaInCorso){
        this.assoggFlagPraticaInCorso = assoggFlagPraticaInCorso;
    }

    public String getAssoggEntePraticaInCorso(){
        return this.assoggEntePraticaInCorso;
    }

    public void setAssoggEntePraticaInCorso(String assoggEntePraticaInCorso){
        this.assoggEntePraticaInCorso = assoggEntePraticaInCorso;
    }

    public String getAssoggDataprPraticaInCorso(){
        return this.assoggDataprPraticaInCorso;
    }

    public void setAssoggDataprPraticaInCorso(String assoggDataprPraticaInCorso){
        this.assoggDataprPraticaInCorso = assoggDataprPraticaInCorso;
    }

    public String getAssoggFlagParereUrbEspr(){
        return this.assoggFlagParereUrbEspr;
    }

    public void setAssoggFlagParereUrbEspr(String assoggFlagParereUrbEspr){
        this.assoggFlagParereUrbEspr = assoggFlagParereUrbEspr;
    }

    public String getAssoggDataApprovPratica(){
        return this.assoggDataApprovPratica;
    }

    public void setAssoggDataApprovPratica(String assoggDataApprovPratica){
        this.assoggDataApprovPratica = assoggDataApprovPratica;
    }


}
