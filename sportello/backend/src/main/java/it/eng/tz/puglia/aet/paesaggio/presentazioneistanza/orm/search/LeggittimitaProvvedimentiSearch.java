package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.leggittimita_provvedimenti
 */
public class LeggittimitaProvvedimentiSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 6066749268L;
    //COLUMN pratica_id
    private String praticaId;
    //COLUMN id
    private String id;
    //COLUMN leg_provv_denominazione
    private String legProvvDenominazione;
    //COLUMN leg_provv_rilasciato_da
    private String legProvvRilasciatoDa;
    //COLUMN leg_provv_num_protocollo
    private String legProvvNumProtocollo;
    //COLUMN leg_provv_data_rilascio
    private String legProvvDataRilascio;
    //COLUMN leg_provv_intestatario
    private String legProvvIntestatario;

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

    public String getLegProvvDenominazione(){
        return this.legProvvDenominazione;
    }

    public void setLegProvvDenominazione(String legProvvDenominazione){
        this.legProvvDenominazione = legProvvDenominazione;
    }

    public String getLegProvvRilasciatoDa(){
        return this.legProvvRilasciatoDa;
    }

    public void setLegProvvRilasciatoDa(String legProvvRilasciatoDa){
        this.legProvvRilasciatoDa = legProvvRilasciatoDa;
    }

    public String getLegProvvNumProtocollo(){
        return this.legProvvNumProtocollo;
    }

    public void setLegProvvNumProtocollo(String legProvvNumProtocollo){
        this.legProvvNumProtocollo = legProvvNumProtocollo;
    }

    public String getLegProvvDataRilascio(){
        return this.legProvvDataRilascio;
    }

    public void setLegProvvDataRilascio(String legProvvDataRilascio){
        this.legProvvDataRilascio = legProvvDataRilascio;
    }

    public String getLegProvvIntestatario(){
        return this.legProvvIntestatario;
    }

    public void setLegProvvIntestatario(String legProvvIntestatario){
        this.legProvvIntestatario = legProvvIntestatario;
    }


}
