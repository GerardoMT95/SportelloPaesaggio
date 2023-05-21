package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.effetti_mitigazione
 */
public class EffettiMitigazioneSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 3126493585L;
    //COLUMN pratica_id
    private String praticaId;
    //COLUMN descr_stato_attuale
    private String descrStatoAttuale;
    //COLUMN effetti_realiz_opera
    private String effettiRealizOpera;
    //COLUMN mitigazione_imp_interv
    private String mitigazioneImpInterv;
    //COLUMN indicaz_contenuti_percettivi
    private String indicazContenutiPercettivi;

    public String getPraticaId(){
        return this.praticaId;
    }

    public void setPraticaId(String praticaId){
        this.praticaId = praticaId;
    }

    public String getDescrStatoAttuale(){
        return this.descrStatoAttuale;
    }

    public void setDescrStatoAttuale(String descrStatoAttuale){
        this.descrStatoAttuale = descrStatoAttuale;
    }

    public String getEffettiRealizOpera(){
        return this.effettiRealizOpera;
    }

    public void setEffettiRealizOpera(String effettiRealizOpera){
        this.effettiRealizOpera = effettiRealizOpera;
    }

    public String getMitigazioneImpInterv(){
        return this.mitigazioneImpInterv;
    }

    public void setMitigazioneImpInterv(String mitigazioneImpInterv){
        this.mitigazioneImpInterv = mitigazioneImpInterv;
    }

    public String getIndicazContenutiPercettivi(){
        return this.indicazContenutiPercettivi;
    }

    public void setIndicazContenutiPercettivi(String indicazContenutiPercettivi){
        this.indicazContenutiPercettivi = indicazContenutiPercettivi;
    }


}
