package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.altre_dichiarazioni_rich
 */
public class AltreDichiarazioniRichSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 4900046205L;
    //COLUMN pratica_id
    private String praticaId;
    //COLUMN check_136
    private String check136;
    //COLUMN check_142
    private String check142;
    //COLUMN check_134
    private String check134;
    //COLUMN ente_rilascio
    private String enteRilascio;
    //COLUMN descr_aut_rilasciata
    private String descrAutRilasciata;
    //COLUMN data_rilascio
    private String dataRilascio;
    //COLUMN is_caso_variante
    private String isCasoVariante;
    //COLUMN check_142_parchi
    private String check142Parchi;

    public String getPraticaId(){
        return this.praticaId;
    }

    public void setPraticaId(String praticaId){
        this.praticaId = praticaId;
    }

    public String getCheck136(){
        return this.check136;
    }

    public void setCheck136(String check136){
        this.check136 = check136;
    }

    public String getCheck142(){
        return this.check142;
    }

    public void setCheck142(String check142){
        this.check142 = check142;
    }

    public String getCheck134(){
        return this.check134;
    }

    public void setCheck134(String check134){
        this.check134 = check134;
    }

    
    public String getEnteRilascio(){
        return this.enteRilascio;
    }

    public void setEnteRilascio(String enteRilascio){
        this.enteRilascio = enteRilascio;
    }

    public String getDescrAutRilasciata(){
        return this.descrAutRilasciata;
    }

    public void setDescrAutRilasciata(String descrAutRilasciata){
        this.descrAutRilasciata = descrAutRilasciata;
    }

    public String getDataRilascio(){
        return this.dataRilascio;
    }

    public void setDataRilascio(String dataRilascio){
        this.dataRilascio = dataRilascio;
    }

    public String getIsCasoVariante(){
        return this.isCasoVariante;
    }

    public void setIsCasoVariante(String isCasoVariante){
        this.isCasoVariante = isCasoVariante;
    }

    public String getCheck142Parchi(){
        return this.check142Parchi;
    }

    public void setCheck142Parchi(String check142Parchi){
        this.check142Parchi = check142Parchi;
    }


}
