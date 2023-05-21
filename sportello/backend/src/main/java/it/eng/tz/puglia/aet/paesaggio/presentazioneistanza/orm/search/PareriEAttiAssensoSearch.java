package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.pareri_e_atti_assenso
 */
public class PareriEAttiAssensoSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 9791370989L;
    //COLUMN pratica_id
    private String praticaId;
    //COLUMN id
    private String id;
    //COLUMN tipologia_atto
    private String tipologiaAtto;
    //COLUMN autorita_rilascio
    private String autoritaRilascio;
    //COLUMN protocollo
    private String protocollo;
    //COLUMN data_rilascio
    private String dataRilascio;
    //COLUMN flag_atto_parere
    private String flagAttoParere;
    //COLUMN intestatario_atto
    private String intestatarioAtto;

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

    public String getTipologiaAtto(){
        return this.tipologiaAtto;
    }

    public void setTipologiaAtto(String tipologiaAtto){
        this.tipologiaAtto = tipologiaAtto;
    }

    public String getAutoritaRilascio(){
        return this.autoritaRilascio;
    }

    public void setAutoritaRilascio(String autoritaRilascio){
        this.autoritaRilascio = autoritaRilascio;
    }

    public String getProtocollo(){
        return this.protocollo;
    }

    public void setProtocollo(String protocollo){
        this.protocollo = protocollo;
    }

    public String getDataRilascio(){
        return this.dataRilascio;
    }

    public void setDataRilascio(String dataRilascio){
        this.dataRilascio = dataRilascio;
    }

    public String getFlagAttoParere(){
        return this.flagAttoParere;
    }

    public void setFlagAttoParere(String flagAttoParere){
        this.flagAttoParere = flagAttoParere;
    }

    public String getIntestatarioAtto(){
        return this.intestatarioAtto;
    }

    public void setIntestatarioAtto(String intestatarioAtto){
        this.intestatarioAtto = intestatarioAtto;
    }


}
