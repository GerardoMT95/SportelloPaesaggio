package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.procedimenti_contenzioso
 */
public class ProcedimentiContenziosoSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 1159439270L;
    //COLUMN pratica_id
    private String praticaId;
    //COLUMN flag_contenzioso_in_atto
    private String flagContenziosoInAtto;
    //COLUMN descrizione
    private String descrizione;

    public String getPraticaId(){
        return this.praticaId;
    }

    public void setPraticaId(String praticaId){
        this.praticaId = praticaId;
    }

    public String getFlagContenziosoInAtto(){
        return this.flagContenziosoInAtto;
    }

    public void setFlagContenziosoInAtto(String flagContenziosoInAtto){
        this.flagContenziosoInAtto = flagContenziosoInAtto;
    }

    public String getDescrizione(){
        return this.descrizione;
    }

    public void setDescrizione(String descrizione){
        this.descrizione = descrizione;
    }


}
