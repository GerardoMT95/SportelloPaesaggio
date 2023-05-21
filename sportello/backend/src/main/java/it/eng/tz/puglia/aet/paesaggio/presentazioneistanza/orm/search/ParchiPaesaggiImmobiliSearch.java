package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.parchi_paesaggi_immobili
 */
public class ParchiPaesaggiImmobiliSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 6772840931L;
    //COLUMN pratica_id
    private String praticaId;
    //COLUMN comune_id
    private String comuneId;
    //COLUMN tipo_vincolo
    private String tipoVincolo;
    //COLUMN codice
    private String codice;
    //COLUMN descrizione
    private String descrizione;
    //COLUMN selezionato
    private String selezionato;
    //COLUMN info
    private String info;
    //COLUMN data_inserimento
    private String dataInserimento;

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

    public String getTipoVincolo(){
        return this.tipoVincolo;
    }

    public void setTipoVincolo(String tipoVincolo){
        this.tipoVincolo = tipoVincolo;
    }

    public String getCodice(){
        return this.codice;
    }

    public void setCodice(String codice){
        this.codice = codice;
    }

    public String getDescrizione(){
        return this.descrizione;
    }

    public void setDescrizione(String descrizione){
        this.descrizione = descrizione;
    }

    public String getSelezionato(){
        return this.selezionato;
    }

    public void setSelezionato(String selezionato){
        this.selezionato = selezionato;
    }

    public String getInfo(){
        return this.info;
    }

    public void setInfo(String info){
        this.info = info;
    }

    public String getDataInserimento(){
        return this.dataInserimento;
    }

    public void setDataInserimento(String dataInserimento){
        this.dataInserimento = dataInserimento;
    }


}
