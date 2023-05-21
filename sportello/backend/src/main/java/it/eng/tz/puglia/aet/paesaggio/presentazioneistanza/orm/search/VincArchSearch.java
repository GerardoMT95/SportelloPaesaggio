package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.vinc_arch
 */
public class VincArchSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 3581174224L;
    //COLUMN pratica_id
    private String praticaId;
    //COLUMN vinc_arc_no_tutela
    private String vincArcNoTutela;
    //COLUMN vinc_arc_monum_diretto
    private String vincArcMonumDiretto;
    //COLUMN vinc_arc_monum_indiretto
    private String vincArcMonumIndiretto;
    //COLUMN vinc_arc_archeo_diretto
    private String vincArcArcheoDiretto;
    //COLUMN vinc_arc_archeo_indiretto
    private String vincArcArcheoIndiretto;
    //COLUMN altri_vincoli
    private String altriVincoli;

    public String getPraticaId(){
        return this.praticaId;
    }

    public void setPraticaId(String praticaId){
        this.praticaId = praticaId;
    }

    public String getVincArcNoTutela(){
        return this.vincArcNoTutela;
    }

    public void setVincArcNoTutela(String vincArcNoTutela){
        this.vincArcNoTutela = vincArcNoTutela;
    }

    public String getVincArcMonumDiretto(){
        return this.vincArcMonumDiretto;
    }

    public void setVincArcMonumDiretto(String vincArcMonumDiretto){
        this.vincArcMonumDiretto = vincArcMonumDiretto;
    }

    public String getVincArcMonumIndiretto(){
        return this.vincArcMonumIndiretto;
    }

    public void setVincArcMonumIndiretto(String vincArcMonumIndiretto){
        this.vincArcMonumIndiretto = vincArcMonumIndiretto;
    }

    public String getVincArcArcheoDiretto(){
        return this.vincArcArcheoDiretto;
    }

    public void setVincArcArcheoDiretto(String vincArcArcheoDiretto){
        this.vincArcArcheoDiretto = vincArcArcheoDiretto;
    }

    public String getVincArcArcheoIndiretto(){
        return this.vincArcArcheoIndiretto;
    }

    public void setVincArcArcheoIndiretto(String vincArcArcheoIndiretto){
        this.vincArcArcheoIndiretto = vincArcArcheoIndiretto;
    }

    public String getAltriVincoli(){
        return this.altriVincoli;
    }

    public void setAltriVincoli(String altriVincoli){
        this.altriVincoli = altriVincoli;
    }


}
