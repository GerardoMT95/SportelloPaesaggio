package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.leggittimita_titoli
 */
public class LeggittimitaTitoliSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 5742925906L;
    //COLUMN pratica_id
    private String praticaId;
    //COLUMN id
    private String id;
    //COLUMN leg_tit_denominazione
    private String legTitDenominazione;
    //COLUMN leg_tit_rilasciato_da
    private String legTitRilasciatoDa;
    //COLUMN leg_tit_num_protocollo
    private String legTitNumProtocollo;
    //COLUMN leg_tit_data_rilascio
    private String legTitDataRilascio;
    //COLUMN leg_tit_intestatario
    private String legTitIntestatario;

    public String getPraticaId(){
        return this.praticaId;
    }

    public void setPraticaId(String praticaId){
        this.praticaId = praticaId;
    }

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getLegTitDenominazione(){
        return this.legTitDenominazione;
    }

    public void setLegTitDenominazione(String legTitDenominazione){
        this.legTitDenominazione = legTitDenominazione;
    }

    public String getLegTitRilasciatoDa(){
        return this.legTitRilasciatoDa;
    }

    public void setLegTitRilasciatoDa(String legTitRilasciatoDa){
        this.legTitRilasciatoDa = legTitRilasciatoDa;
    }

    public String getLegTitNumProtocollo(){
        return this.legTitNumProtocollo;
    }

    public void setLegTitNumProtocollo(String legTitNumProtocollo){
        this.legTitNumProtocollo = legTitNumProtocollo;
    }

    public String getLegTitDataRilascio(){
        return this.legTitDataRilascio;
    }

    public void setLegTitDataRilascio(String legTitDataRilascio){
        this.legTitDataRilascio = legTitDataRilascio;
    }

    public String getLegTitIntestatario(){
        return this.legTitIntestatario;
    }

    public void setLegTitIntestatario(String legTitIntestatario){
        this.legTitIntestatario = legTitIntestatario;
    }


}
