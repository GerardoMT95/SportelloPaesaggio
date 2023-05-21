package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.descrizione_scheda_tecnica_values
 */
public class DescrizioneSchedaTecnicaValuesSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 2067143283L;
    //COLUMN pratica_id
    private String praticaId;
    //COLUMN codice
    private String codice;
    //COLUMN text
    private String text;
    //COLUMN sezione
    private String sezione;

    public String getPraticaId(){
        return this.praticaId;
    }

    public void setPraticaId(String praticaId){
        this.praticaId = praticaId;
    }

    public String getCodice(){
        return this.codice;
    }

    public void setCodice(String codice){
        this.codice = codice;
    }

    public String getText(){
        return this.text;
    }

    public void setText(String text){
        this.text = text;
    }

    public String getSezione(){
        return this.sezione;
    }

    public void setSezione(String sezione){
        this.sezione = sezione;
    }


}
