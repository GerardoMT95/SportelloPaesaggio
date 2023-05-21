package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.localizzazione_intervento
 */
public class LocalizzazioneInterventoSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 8427839322L;
    //COLUMN pratica_id
    private String praticaId;
    //COLUMN comune_id
    private String comuneId;
    //COLUMN indirizzo
    private String indirizzo;
    //COLUMN num_civico
    private String numCivico;
    //COLUMN piano
    private String piano;
    //COLUMN interno
    private String interno;
    //COLUMN dest_uso_att
    private String destUsoAtt;
    //COLUMN dest_uso_prog
    private String destUsoProg;
    //COLUMN comune
    private String comune;
    //COLUMN sigla_provincia
    private String siglaProvincia;
    //COLUMN data_riferimento_catastale
    private String dataRiferimentoCatastale;
    //COLUMN strade
    private String strade;

    public String getPraticaId(){
        return this.praticaId;
    }

    public void setPraticaId(String praticaId){
        this.praticaId = praticaId;
    }

    public String getComuneId(){
        return this.comuneId;
    }

    public void setComuneId(String comuneId){
        this.comuneId = comuneId;
    }

    public String getIndirizzo(){
        return this.indirizzo;
    }

    public void setIndirizzo(String indirizzo){
        this.indirizzo = indirizzo;
    }

    public String getNumCivico(){
        return this.numCivico;
    }

    public void setNumCivico(String numCivico){
        this.numCivico = numCivico;
    }

    public String getPiano(){
        return this.piano;
    }

    public void setPiano(String piano){
        this.piano = piano;
    }

    public String getInterno(){
        return this.interno;
    }

    public void setInterno(String interno){
        this.interno = interno;
    }

    public String getDestUsoAtt(){
        return this.destUsoAtt;
    }

    public void setDestUsoAtt(String destUsoAtt){
        this.destUsoAtt = destUsoAtt;
    }

    public String getDestUsoProg(){
        return this.destUsoProg;
    }

    public void setDestUsoProg(String destUsoProg){
        this.destUsoProg = destUsoProg;
    }

    public String getComune(){
        return this.comune;
    }

    public void setComune(String comune){
        this.comune = comune;
    }

    public String getSiglaProvincia(){
        return this.siglaProvincia;
    }

    public void setSiglaProvincia(String siglaProvincia){
        this.siglaProvincia = siglaProvincia;
    }

    public String getDataRiferimentoCatastale(){
        return this.dataRiferimentoCatastale;
    }

    public void setDataRiferimentoCatastale(String dataRiferimentoCatastale){
        this.dataRiferimentoCatastale = dataRiferimentoCatastale;
    }

    public String getStrade(){
        return this.strade;
    }

    public void setStrade(String strade){
        this.strade = strade;
    }


}
