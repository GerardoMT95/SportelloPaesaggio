package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.pptr_approvato
 */
public class PptrApprovatoSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 1169081428L;
    //COLUMN pratica_id
    private String praticaId;
    //COLUMN ambito_paesaggistico
    private String ambitoPaesaggistico;
    //COLUMN figure_ambito
    private String figureAmbito;
    //COLUMN ricade_terr_art_1_03_co_5_6
    private String ricadeTerrArt103Co56;
    //COLUMN ricade_terr_art_142_co_2
    private String ricadeTerrArt142Co2;

    public String getPraticaId(){
        return this.praticaId;
    }

    public void setPraticaId(String praticaId){
        this.praticaId = praticaId;
    }

    public String getAmbitoPaesaggistico(){
        return this.ambitoPaesaggistico;
    }

    public void setAmbitoPaesaggistico(String ambitoPaesaggistico){
        this.ambitoPaesaggistico = ambitoPaesaggistico;
    }

    public String getFigureAmbito(){
        return this.figureAmbito;
    }

    public void setFigureAmbito(String figureAmbito){
        this.figureAmbito = figureAmbito;
    }

    public String getRicadeTerrArt103Co56(){
        return this.ricadeTerrArt103Co56;
    }

    public void setRicadeTerrArt103Co56(String ricadeTerrArt103Co56){
        this.ricadeTerrArt103Co56 = ricadeTerrArt103Co56;
    }

    public String getRicadeTerrArt142Co2(){
        return this.ricadeTerrArt142Co2;
    }

    public void setRicadeTerrArt142Co2(String ricadeTerrArt142Co2){
        this.ricadeTerrArt142Co2 = ricadeTerrArt142Co2;
    }


}
