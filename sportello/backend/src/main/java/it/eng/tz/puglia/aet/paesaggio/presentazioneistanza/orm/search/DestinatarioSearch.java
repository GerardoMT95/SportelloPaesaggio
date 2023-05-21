package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.destinatario
 */
public class DestinatarioSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 6532753077L;
    //COLUMN id
    private String id;
    //COLUMN type
    private String type;
    //COLUMN email
    private String email;
    //COLUMN stato
    private String stato;
    //COLUMN pec
    private String pec;
    //COLUMN denominazione
    private String denominazione;
    //COLUMN data_ricezione
    private String dataRicezione;
    //COLUMN id_corrispondenza
    private String idCorrispondenza;

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getType(){
        return this.type;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getStato(){
        return this.stato;
    }

    public void setStato(String stato){
        this.stato = stato;
    }

    public String getPec(){
        return this.pec;
    }

    public void setPec(String pec){
        this.pec = pec;
    }

    public String getDenominazione(){
        return this.denominazione;
    }

    public void setDenominazione(String denominazione){
        this.denominazione = denominazione;
    }

    public String getDataRicezione(){
        return this.dataRicezione;
    }

    public void setDataRicezione(String dataRicezione){
        this.dataRicezione = dataRicezione;
    }

    public String getIdCorrispondenza(){
        return this.idCorrispondenza;
    }

    public void setIdCorrispondenza(String idCorrispondenza){
        this.idCorrispondenza = idCorrispondenza;
    }


}
