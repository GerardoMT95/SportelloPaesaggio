package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_doc.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.template_doc_sezioni
 */
public class TemplateDocSezioniSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 3141121034L;
    //COLUMN id_organizzazione
    private String idOrganizzazione;
    //COLUMN nome
    private String nome;
    //COLUMN template_doc_nome
    private String templateDocNome;
    //COLUMN valore
    private String valore;
    //COLUMN id_allegato
    private String idAllegato;
    //COLUMN placeholders
    private String placeholders;
    //COLUMN tipo_sezione
    private String tipoSezione;

    public String getIdOrganizzazione(){
        return this.idOrganizzazione;
    }

    public void setIdOrganizzazione(String idOrganizzazione){
        this.idOrganizzazione = idOrganizzazione;
    }

    public String getNome(){
        return this.nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getTemplateDocNome(){
        return this.templateDocNome;
    }

    public void setTemplateDocNome(String templateDocNome){
        this.templateDocNome = templateDocNome;
    }

    public String getValore(){
        return this.valore;
    }

    public void setValore(String valore){
        this.valore = valore;
    }

    public String getIdAllegato(){
        return this.idAllegato;
    }

    public void setIdAllegato(String idAllegato){
        this.idAllegato = idAllegato;
    }

    public String getPlaceholders(){
        return this.placeholders;
    }

    public void setPlaceholders(String placeholders){
        this.placeholders = placeholders;
    }

    public String getTipoSezione(){
        return this.tipoSezione;
    }

    public void setTipoSezione(String tipoSezione){
        this.tipoSezione = tipoSezione;
    }


}
