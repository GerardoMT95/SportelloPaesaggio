package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.PlaceHolder;

/**
 * DTO for table presentazione_istanza.template_email
 */
public class TemplateEmailDTO implements Serializable{

    private static final long serialVersionUID = 3810056676L;
    //COLUMN id_organizzazione
    private int idOrganizzazione;
    //COLUMN codice
    private String codice;
    //COLUMN nome
    private String nome;
    //COLUMN descrizione
    private String descrizione;
    //COLUMN oggetto
    private String oggetto;
    //COLUMN testo
    private String testo;
    //COLUMN readonly_oggetto
    private String readonlyOggetto="N";
    //COLUMN readonly_testo
    private String readonlyTesto="N";
    //COLUMN visibilita
    private String visibilita;
    //COLUMN sezione
    private String sezione;
    //COLUMN tipi_procedimento_ammessi
    private String tipiProcedimentoAmmessi;
    //COLUMN protocollazione
    private String protocollazione="N";
    //COLUMN placeholders
    private String placeholders=PlaceHolder.defaultSet();
    //COLUMN cancellabile
    private boolean cancellabile=true;
    
    //COLUMN riservata
    private boolean riservata=true;
    //COLUMN sotto_sezione
    private String sottoSezione;

    
    public boolean isRiservata() {
		return riservata;
	}

	public void setRiservata(boolean riservata) {
		this.riservata = riservata;
	}

	public String getSottoSezione() {
		return sottoSezione;
	}

	public void setSottoSezione(String sottoSezione) {
		this.sottoSezione = sottoSezione;
	}

	public int getIdOrganizzazione(){
        return this.idOrganizzazione;
    }

    public void setIdOrganizzazione(int idOrganizzazione){
        this.idOrganizzazione = idOrganizzazione;
    }

    public String getCodice(){
        return this.codice;
    }

    public void setCodice(String codice){
        this.codice = codice;
    }

    public String getNome(){
        return this.nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getDescrizione(){
        return this.descrizione;
    }

    public void setDescrizione(String descrizione){
        this.descrizione = descrizione;
    }

    public String getOggetto(){
        return this.oggetto;
    }

    public void setOggetto(String oggetto){
        this.oggetto = oggetto;
    }

    public String getTesto(){
        return this.testo;
    }

    public void setTesto(String testo){
        this.testo = testo;
    }

    public String getReadonlyOggetto(){
        return this.readonlyOggetto;
    }

    public void setReadonlyOggetto(String readonlyOggetto){
        this.readonlyOggetto = readonlyOggetto;
    }

    public String getReadonlyTesto(){
        return this.readonlyTesto;
    }

    public void setReadonlyTesto(String readonlyTesto){
        this.readonlyTesto = readonlyTesto;
    }

    public String getVisibilita(){
        return this.visibilita;
    }

    public void setVisibilita(String visibilita){
        this.visibilita = visibilita;
    }

    public String getSezione(){
        return this.sezione;
    }

    public void setSezione(String sezione){
        this.sezione = sezione;
    }

    public String getTipiProcedimentoAmmessi(){
        return this.tipiProcedimentoAmmessi;
    }

    public void setTipiProcedimentoAmmessi(String tipiProcedimentoAmmessi){
        this.tipiProcedimentoAmmessi = tipiProcedimentoAmmessi;
    }

    public String getProtocollazione(){
        return this.protocollazione;
    }

    public void setProtocollazione(String protocollazione){
        this.protocollazione = protocollazione;
    }

    public String getPlaceholders(){
        return this.placeholders;
    }

    public void setPlaceholders(String placeholders){
        this.placeholders = placeholders;
    }

    public boolean getCancellabile(){
        return this.cancellabile;
    }

    public void setCancellabile(boolean cancellabile){
        this.cancellabile = cancellabile;
    }
	
}
