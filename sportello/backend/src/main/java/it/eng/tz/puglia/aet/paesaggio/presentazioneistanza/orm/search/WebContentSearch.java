package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.web_content
 */
public class WebContentSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 7256670047L;
    //COLUMN codice_contenuto
    private String codiceContenuto;
    //COLUMN tooltip
    private String tooltip;
    //COLUMN contenuto
    private String contenuto;

    public String getCodiceContenuto(){
        return this.codiceContenuto;
    }

    public void setCodiceContenuto(String codiceContenuto){
        this.codiceContenuto = codiceContenuto;
    }

    public String getTooltip(){
        return this.tooltip;
    }

    public void setTooltip(String tooltip){
        this.tooltip = tooltip;
    }

    public String getContenuto(){
        return this.contenuto;
    }

    public void setContenuto(String contenuto){
        this.contenuto = contenuto;
    }


}
