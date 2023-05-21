package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;
import it.eng.tz.puglia.util.json.DateSerializer;

/**
 * Search for table presentazione_istanza.report
 */
public class ReportSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 9168610219L;
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
    private String dimensioneFile;
    //COLUMN data_richiesta
    private String dataRichiesta;
    //COLUMN data_avvio
    private String dataAvvio;
    //COLUMN data_creazione
    private String dataCreazione;
    //COLUMN data_scadenza
    private String dataScadenza;
    //COLUMN hash
    private String hash;
    //COLUMN stato_originale
    private String statoOriginale;
    //COLUMN ente_delegato
    private String enteDelegato;
    //COLUMN descrizione_ente
    private String descrizioneEnte;
    //COLUMN mail
    private String mail;
    
	@JsonSerialize(using = DateSerializer.class)
    private Date dataFrom;
	@JsonSerialize(using = DateSerializer.class)
    private Date dataTo;
    

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

    public String getDimensioneFile(){
        return this.dimensioneFile;
    }

    public void setDimensioneFile(String dimensioneFile){
        this.dimensioneFile = dimensioneFile;
    }

    public String getDataRichiesta(){
        return this.dataRichiesta;
    }

    public void setDataRichiesta(String dataRichiesta){
        this.dataRichiesta = dataRichiesta;
    }

    public String getDataAvvio(){
        return this.dataAvvio;
    }

    public void setDataAvvio(String dataAvvio){
        this.dataAvvio = dataAvvio;
    }

    public String getDataCreazione(){
        return this.dataCreazione;
    }

    public void setDataCreazione(String dataCreazione){
        this.dataCreazione = dataCreazione;
    }

    public String getDataScadenza(){
        return this.dataScadenza;
    }

    public void setDataScadenza(String dataScadenza){
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

    public String getEnteDelegato(){
        return this.enteDelegato;
    }

    public void setEnteDelegato(String enteDelegato){
        this.enteDelegato = enteDelegato;
    }

    public String getDescrizioneEnte(){
        return this.descrizioneEnte;
    }

    public void setDescrizioneEnte(String descrizioneEnte){
        this.descrizioneEnte = descrizioneEnte;
    }
    
    public Date getDataFrom() {
    	return this.dataFrom;
    }
    
    public void setDataFrom(final Date dataFrom) {
    	this.dataFrom = dataFrom;
    }
    
    public Date getDataTo() {
    	return this.dataTo;
    }
    
    public void setDateTo(final Date dataTo) {
    	this.dataTo = dataTo;
    }
    
    public String getMail() {
    	return this.mail;
    }
    
    public void setMail(final String mail) {
    	this.mail = mail;
    }

}
