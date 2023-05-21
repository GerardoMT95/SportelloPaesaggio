package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.template_destinatario
 */
public class TemplateDestinatarioSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 769196129L;
    //COLUMN id
    private String id;
    //COLUMN id_organizzazione
    private String idOrganizzazione;
    //COLUMN codice_template
    private String codiceTemplate;
    //COLUMN email
    private String email;
    //COLUMN pec
    private String pec;
    //COLUMN denominazione
    private String denominazione;
    //COLUMN tipo
    private String tipo;

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getIdOrganizzazione(){
        return this.idOrganizzazione;
    }

    public void setIdOrganizzazione(String idOrganizzazione){
        this.idOrganizzazione = idOrganizzazione;
    }

    public String getCodiceTemplate(){
        return this.codiceTemplate;
    }

    public void setCodiceTemplate(String codiceTemplate){
        this.codiceTemplate = codiceTemplate;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
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

    public String getTipo(){
        return this.tipo;
    }

    public void setTipo(String tipo){
        this.tipo = tipo;
    }


}
