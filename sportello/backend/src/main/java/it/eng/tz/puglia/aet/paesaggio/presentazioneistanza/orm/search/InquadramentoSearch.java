package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.inquadramento
 */
public class InquadramentoSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 4743669438L;
    //COLUMN pratica_id
    private String praticaId;
    //COLUMN inq_dest_uso_interv
    private String inqDestUsoInterv;
    //COLUMN inq_dest_uso_interv_altro
    private String inqDestUsoIntervAltro;
    //COLUMN inq_dest_uso_suolo
    private String inqDestUsoSuolo;
    //COLUMN inq_dest_uso_suolo_altro
    private String inqDestUsoSuoloAltro;
    //COLUMN inq_contesto_paesag
    private String inqContestoPaesag;
    //COLUMN inq_contesto_paesag_altro
    private String inqContestoPaesagAltro;
    //COLUMN inq_morfologia_paesag
    private String inqMorfologiaPaesag;
    //COLUMN inq_morfologia_paesag_altro
    private String inqMorfologiaPaesagAltro;

    public String getPraticaId(){
        return this.praticaId;
    }

    public void setPraticaId(String praticaId){
        this.praticaId = praticaId;
    }

    public String getInqDestUsoInterv(){
        return this.inqDestUsoInterv;
    }

    public void setInqDestUsoInterv(String inqDestUsoInterv){
        this.inqDestUsoInterv = inqDestUsoInterv;
    }

    public String getInqDestUsoIntervAltro(){
        return this.inqDestUsoIntervAltro;
    }

    public void setInqDestUsoIntervAltro(String inqDestUsoIntervAltro){
        this.inqDestUsoIntervAltro = inqDestUsoIntervAltro;
    }

    public String getInqDestUsoSuolo(){
        return this.inqDestUsoSuolo;
    }

    public void setInqDestUsoSuolo(String inqDestUsoSuolo){
        this.inqDestUsoSuolo = inqDestUsoSuolo;
    }

    public String getInqDestUsoSuoloAltro(){
        return this.inqDestUsoSuoloAltro;
    }

    public void setInqDestUsoSuoloAltro(String inqDestUsoSuoloAltro){
        this.inqDestUsoSuoloAltro = inqDestUsoSuoloAltro;
    }

    public String getInqContestoPaesag(){
        return this.inqContestoPaesag;
    }

    public void setInqContestoPaesag(String inqContestoPaesag){
        this.inqContestoPaesag = inqContestoPaesag;
    }

    public String getInqContestoPaesagAltro(){
        return this.inqContestoPaesagAltro;
    }

    public void setInqContestoPaesagAltro(String inqContestoPaesagAltro){
        this.inqContestoPaesagAltro = inqContestoPaesagAltro;
    }

    public String getInqMorfologiaPaesag(){
        return this.inqMorfologiaPaesag;
    }

    public void setInqMorfologiaPaesag(String inqMorfologiaPaesag){
        this.inqMorfologiaPaesag = inqMorfologiaPaesag;
    }

    public String getInqMorfologiaPaesagAltro(){
        return this.inqMorfologiaPaesagAltro;
    }

    public void setInqMorfologiaPaesagAltro(String inqMorfologiaPaesagAltro){
        this.inqMorfologiaPaesagAltro = inqMorfologiaPaesagAltro;
    }


}
