package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * DTO for table presentazione_istanza.report
 */
public class ReportDTO implements Serializable{

    private static final long serialVersionUID = 2218543610L;
    //COLUMN id
    private String id;
    //COLUMN tipo
    private String tipo;
    //COLUMN parametri
    private String parametri;
    //COLUMN username
    private String username;
    //COLUMN path_file
    private String pathFile;
    //COLUMN file_name
    private String fileName;
    //COLUMN stato
    private String stato;
    //COLUMN dimensione_file
    private Long dimensioneFile;
    //COLUMN data_richiesta
    private Timestamp dataRichiesta;
    //COLUMN data_avvio
    private Timestamp dataAvvio;
    //COLUMN data_creazione
    private Timestamp dataCreazione;
    //COLUMN data_scadenza
    private Timestamp dataScadenza;
    //COLUMN hash
    private String hash;
    //COLUMN stato_originale
    private String statoOriginale;
    //COLUMN ente_delegato
    private Integer enteDelegato;
    //COLUMN descrizione_ente
    private String descrizioneEnte;
    //COLUMN mail
    private String mail;

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getTipo(){
        return this.tipo;
    }

    public void setTipo(String tipo){
        this.tipo = tipo;
    }

    public String getParametri(){
        return this.parametri;
    }

    public void setParametri(String parametri){
        this.parametri = parametri;
    }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPathFile(){
        return this.pathFile;
    }

    public void setPathFile(String pathFile){
        this.pathFile = pathFile;
    }

    public String getFileName(){
        return this.fileName;
    }

    public void setFileName(String fileName){
        this.fileName = fileName;
    }

    public String getStato(){
        return this.stato;
    }

    public void setStato(String stato){
        this.stato = stato;
    }

    public Long getDimensioneFile(){
        return this.dimensioneFile;
    }

    public void setDimensioneFile(Long dimensioneFile){
        this.dimensioneFile = dimensioneFile;
    }

    public Timestamp getDataRichiesta(){
        return this.dataRichiesta;
    }

    public void setDataRichiesta(Timestamp dataRichiesta){
        this.dataRichiesta = dataRichiesta;
    }

    public Timestamp getDataAvvio(){
        return this.dataAvvio;
    }

    public void setDataAvvio(Timestamp dataAvvio){
        this.dataAvvio = dataAvvio;
    }

    public Timestamp getDataCreazione(){
        return this.dataCreazione;
    }

    public void setDataCreazione(Timestamp dataCreazione){
        this.dataCreazione = dataCreazione;
    }

    public Timestamp getDataScadenza(){
        return this.dataScadenza;
    }

    public void setDataScadenza(Timestamp dataScadenza){
        this.dataScadenza = dataScadenza;
    }

    public String getHash(){
        return this.hash;
    }

    public void setHash(String hash){
        this.hash = hash;
    }

    public String getStatoOriginale(){
        return this.statoOriginale;
    }

    public void setStatoOriginale(String statoOriginale){
        this.statoOriginale = statoOriginale;
    }

    public Integer getEnteDelegato(){
        return this.enteDelegato;
    }

    public void setEnteDelegato(Integer enteDelegato){
        this.enteDelegato = enteDelegato;
    }

    public String getDescrizioneEnte(){
        return this.descrizioneEnte;
    }

    public void setDescrizioneEnte(String descrizioneEnte){
        this.descrizioneEnte = descrizioneEnte;
    }
    
    public String getMail(){
        return this.mail;
    }

    public void setMail(String mail){
        this.mail = mail;
    }


}
