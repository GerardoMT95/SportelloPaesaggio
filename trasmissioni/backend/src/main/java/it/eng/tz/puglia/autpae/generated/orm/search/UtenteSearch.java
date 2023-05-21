package it.eng.tz.puglia.autpae.generated.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table common.utente
 */
public class UtenteSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 224089535L;
    //COLUMN id
    private String id;
    //COLUMN username
    private String username;
    //COLUMN nome
    private String nome;
    //COLUMN cognome
    private String cognome;
    //COLUMN cf
    private String cf;
    //COLUMN data_richiesta_registrazione
    private String dataRichiestaRegistrazione;
    //COLUMN data_conferma_registrazione
    private String dataConfermaRegistrazione;
    //COLUMN id_stato_registrazione
    private String idStatoRegistrazione;
    //COLUMN note
    private String note;

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getNome(){
        return this.nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getCognome(){
        return this.cognome;
    }

    public void setCognome(String cognome){
        this.cognome = cognome;
    }

    public String getCf(){
        return this.cf;
    }

    public void setCf(String cf){
        this.cf = cf;
    }

    public String getDataRichiestaRegistrazione(){
        return this.dataRichiestaRegistrazione;
    }

    public void setDataRichiestaRegistrazione(String dataRichiestaRegistrazione){
        this.dataRichiestaRegistrazione = dataRichiestaRegistrazione;
    }

    public String getDataConfermaRegistrazione(){
        return this.dataConfermaRegistrazione;
    }

    public void setDataConfermaRegistrazione(String dataConfermaRegistrazione){
        this.dataConfermaRegistrazione = dataConfermaRegistrazione;
    }

    public String getIdStatoRegistrazione(){
        return this.idStatoRegistrazione;
    }

    public void setIdStatoRegistrazione(String idStatoRegistrazione){
        this.idStatoRegistrazione = idStatoRegistrazione;
    }

    public String getNote(){
        return this.note;
    }

    public void setNote(String note){
        this.note = note;
    }


}
