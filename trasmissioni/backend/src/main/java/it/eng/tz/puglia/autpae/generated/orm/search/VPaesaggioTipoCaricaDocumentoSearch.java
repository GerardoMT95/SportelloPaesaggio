package it.eng.tz.puglia.autpae.generated.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table procedimenti_ambientali.v_paesaggio_tipo_carica_documento
 */
public class VPaesaggioTipoCaricaDocumentoSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 9530103629L;
    //COLUMN id
    private String id;
    //COLUMN nome
    private String nome;
    //COLUMN nome_famiglia
    private String nomeFamiglia;
    //COLUMN nome_ente
    private String nomeEnte;
    //COLUMN descrizione
    private String descrizione;
    //COLUMN valore
    private String valore;

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getNome(){
        return this.nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getNomeFamiglia(){
        return this.nomeFamiglia;
    }

    public void setNomeFamiglia(String nomeFamiglia){
        this.nomeFamiglia = nomeFamiglia;
    }

    public String getNomeEnte(){
        return this.nomeEnte;
    }

    public void setNomeEnte(String nomeEnte){
        this.nomeEnte = nomeEnte;
    }

    public String getDescrizione(){
        return this.descrizione;
    }

    public void setDescrizione(String descrizione){
        this.descrizione = descrizione;
    }

    public String getValore(){
        return this.valore;
    }

    public void setValore(String valore){
        this.valore = valore;
    }


}
