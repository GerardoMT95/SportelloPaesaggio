package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.relazione_ente
 */
public class RelazioneEnteSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 142635064L;
    //COLUMN id_relazione_ente
    private String idRelazioneEnte;
    //COLUMN id_pratica
    private String idPratica;
    //COLUMN numero_protocollo_ente
    private String numeroProtocolloEnte;
    //COLUMN nominativo_istruttore
    private String nominativoIstruttore;
    //COLUMN dettaglio_relazione
    private String dettaglioRelazione;
    //COLUMN nota_interna
    private String notaInterna;

    public String getIdRelazioneEnte(){
        return this.idRelazioneEnte;
    }

    public void setIdRelazioneEnte(String idRelazioneEnte){
        this.idRelazioneEnte = idRelazioneEnte;
    }

    public String getIdPratica(){
        return this.idPratica;
    }

    public void setIdPratica(String idPratica){
        this.idPratica = idPratica;
    }

    public String getNumeroProtocolloEnte(){
        return this.numeroProtocolloEnte;
    }

    public void setNumeroProtocolloEnte(String numeroProtocolloEnte){
        this.numeroProtocolloEnte = numeroProtocolloEnte;
    }

    public String getNominativoIstruttore(){
        return this.nominativoIstruttore;
    }

    public void setNominativoIstruttore(String nominativoIstruttore){
        this.nominativoIstruttore = nominativoIstruttore;
    }

    public String getDettaglioRelazione(){
        return this.dettaglioRelazione;
    }

    public void setDettaglioRelazione(String dettaglioRelazione){
        this.dettaglioRelazione = dettaglioRelazione;
    }

    public String getNotaInterna(){
        return this.notaInterna;
    }

    public void setNotaInterna(String notaInterna){
        this.notaInterna = notaInterna;
    }


}
