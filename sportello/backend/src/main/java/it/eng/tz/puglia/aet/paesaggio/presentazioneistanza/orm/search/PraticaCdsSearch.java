package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.pratica_cds
 */
public class PraticaCdsSearch extends BaseSearchRequestBean{

    private static final long serialVersionUID = 7934794373L;
    //COLUMN id
    private String id;
    //COLUMN id_pratica
    private String idPratica;
    //COLUMN tipo
    private String tipo;
    //COLUMN attivita
    private String attivita;
    //COLUMN azione
    private String azione;
    //COLUMN termine_richiesta_integrazione
    private String termineRichiestaIntegrazione;
    //COLUMN termine_pareri
    private String terminePareri;
    //COLUMN prima_sessione
    private String primaSessione;
    //COLUMN data_termine
    private String dataTermine;
    //COLUMN comune_pertinenza
    private String comunePertinenza;
    //COLUMN provincia_pertinenza
    private String provinciaPertinenza;
    //COLUMN indirizzo_pertinenza
    private String indirizzoPertinenza;
    //COLUMN modalita_incontro
    private String modalitaIncontro;
    //COLUMN link
    private String link;
    //COLUMN comune
    private String comune;
    //COLUMN provincia
    private String provincia;
    //COLUMN cap
    private String cap;
    //COLUMN indirizzo
    private String indirizzo;
    //COLUMN orario
    private String orario;
    //COLUMN date_create
    private String dateCreate;
    //COLUMN user_create
    private String userCreate;
    //COLUMN date_update
    private String dateUpdate;
    //COLUMN user_update
    private String userUpdate;
    //COLUMN completed
    private String completed;
    //COLUMN id_cds
    private String idCds;
    //COLUMN comitato
    private String comitato;
    //COLUMN username_creatore
    private String usernameCreatore;
    //COLUMN nome_creatore
    private String nomeCreatore;
    //COLUMN cognome_creatore
    private String cognomeCreatore;
    //COLUMN mail_creatore
    private String mailCreatore;
    //COLUMN telefono_creatore
    private String telefonoCreatore;
    //COLUMN codice_fiscale_creatore
    private String codiceFiscaleCreatore;
    //COLUMN username_responsabile
    private String usernameResponsabile;
    //COLUMN nome_responsabile
    private String nomeResponsabile;
    //COLUMN cognome_responsabile
    private String cognomeResponsabile;
    //COLUMN codice_fiscale_responsabile
    private String codiceFiscaleResponsabile;
    //COLUMN mail_responsabile
    private String mailResponsabile;
    //COLUMN telefono_responsabile
    private String telefonoResponsabile;

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getIdPratica(){
        return this.idPratica;
    }

    public void setIdPratica(String idPratica){
        this.idPratica = idPratica;
    }

    public String getTipo(){
        return this.tipo;
    }

    public void setTipo(String tipo){
        this.tipo = tipo;
    }

    public String getAttivita(){
        return this.attivita;
    }

    public void setAttivita(String attivita){
        this.attivita = attivita;
    }

    public String getAzione(){
        return this.azione;
    }

    public void setAzione(String azione){
        this.azione = azione;
    }

    public String getTermineRichiestaIntegrazione(){
        return this.termineRichiestaIntegrazione;
    }

    public void setTermineRichiestaIntegrazione(String termineRichiestaIntegrazione){
        this.termineRichiestaIntegrazione = termineRichiestaIntegrazione;
    }

    public String getTerminePareri(){
        return this.terminePareri;
    }

    public void setTerminePareri(String terminePareri){
        this.terminePareri = terminePareri;
    }

    public String getPrimaSessione(){
        return this.primaSessione;
    }

    public void setPrimaSessione(String primaSessione){
        this.primaSessione = primaSessione;
    }

    public String getDataTermine(){
        return this.dataTermine;
    }

    public void setDataTermine(String dataTermine){
        this.dataTermine = dataTermine;
    }

    public String getComunePertinenza(){
        return this.comunePertinenza;
    }

    public void setComunePertinenza(String comunePertinenza){
        this.comunePertinenza = comunePertinenza;
    }

    public String getProvinciaPertinenza(){
        return this.provinciaPertinenza;
    }

    public void setProvinciaPertinenza(String provinciaPertinenza){
        this.provinciaPertinenza = provinciaPertinenza;
    }

    public String getIndirizzoPertinenza(){
        return this.indirizzoPertinenza;
    }

    public void setIndirizzoPertinenza(String indirizzoPertinenza){
        this.indirizzoPertinenza = indirizzoPertinenza;
    }

    public String getModalitaIncontro(){
        return this.modalitaIncontro;
    }

    public void setModalitaIncontro(String modalitaIncontro){
        this.modalitaIncontro = modalitaIncontro;
    }

    public String getLink(){
        return this.link;
    }

    public void setLink(String link){
        this.link = link;
    }

    public String getComune(){
        return this.comune;
    }

    public void setComune(String comune){
        this.comune = comune;
    }

    public String getProvincia(){
        return this.provincia;
    }

    public void setProvincia(String provincia){
        this.provincia = provincia;
    }

    public String getCap(){
        return this.cap;
    }

    public void setCap(String cap){
        this.cap = cap;
    }

    public String getIndirizzo(){
        return this.indirizzo;
    }

    public void setIndirizzo(String indirizzo){
        this.indirizzo = indirizzo;
    }

    public String getOrario(){
        return this.orario;
    }

    public void setOrario(String orario){
        this.orario = orario;
    }

    public String getDateCreate(){
        return this.dateCreate;
    }

    public void setDateCreate(String dateCreate){
        this.dateCreate = dateCreate;
    }

    public String getUserCreate(){
        return this.userCreate;
    }

    public void setUserCreate(String userCreate){
        this.userCreate = userCreate;
    }

    public String getDateUpdate(){
        return this.dateUpdate;
    }

    public void setDateUpdate(String dateUpdate){
        this.dateUpdate = dateUpdate;
    }

    public String getUserUpdate(){
        return this.userUpdate;
    }

    public void setUserUpdate(String userUpdate){
        this.userUpdate = userUpdate;
    }

    public String getCompleted(){
        return this.completed;
    }

    public void setCompleted(String completed){
        this.completed = completed;
    }

    public String getIdCds(){
        return this.idCds;
    }

    public void setIdCds(String idCds){
        this.idCds = idCds;
    }

    public String getComitato(){
        return this.comitato;
    }

    public void setComitato(String comitato){
        this.comitato = comitato;
    }

    public String getUsernameCreatore(){
        return this.usernameCreatore;
    }

    public void setUsernameCreatore(String usernameCreatore){
        this.usernameCreatore = usernameCreatore;
    }

    public String getNomeCreatore(){
        return this.nomeCreatore;
    }

    public void setNomeCreatore(String nomeCreatore){
        this.nomeCreatore = nomeCreatore;
    }

    public String getCognomeCreatore(){
        return this.cognomeCreatore;
    }

    public void setCognomeCreatore(String cognomeCreatore){
        this.cognomeCreatore = cognomeCreatore;
    }

    public String getMailCreatore(){
        return this.mailCreatore;
    }

    public void setMailCreatore(String mailCreatore){
        this.mailCreatore = mailCreatore;
    }

    public String getTelefonoCreatore(){
        return this.telefonoCreatore;
    }

    public void setTelefonoCreatore(String telefonoCreatore){
        this.telefonoCreatore = telefonoCreatore;
    }

    public String getCodiceFiscaleCreatore(){
        return this.codiceFiscaleCreatore;
    }

    public void setCodiceFiscaleCreatore(String codiceFiscaleCreatore){
        this.codiceFiscaleCreatore = codiceFiscaleCreatore;
    }

    public String getUsernameResponsabile(){
        return this.usernameResponsabile;
    }

    public void setUsernameResponsabile(String usernameResponsabile){
        this.usernameResponsabile = usernameResponsabile;
    }

    public String getNomeResponsabile(){
        return this.nomeResponsabile;
    }

    public void setNomeResponsabile(String nomeResponsabile){
        this.nomeResponsabile = nomeResponsabile;
    }

    public String getCognomeResponsabile(){
        return this.cognomeResponsabile;
    }

    public void setCognomeResponsabile(String cognomeResponsabile){
        this.cognomeResponsabile = cognomeResponsabile;
    }

    public String getCodiceFiscaleResponsabile(){
        return this.codiceFiscaleResponsabile;
    }

    public void setCodiceFiscaleResponsabile(String codiceFiscaleResponsabile){
        this.codiceFiscaleResponsabile = codiceFiscaleResponsabile;
    }

    public String getMailResponsabile(){
        return this.mailResponsabile;
    }

    public void setMailResponsabile(String mailResponsabile){
        this.mailResponsabile = mailResponsabile;
    }

    public String getTelefonoResponsabile(){
        return this.telefonoResponsabile;
    }

    public void setTelefonoResponsabile(String telefonoResponsabile){
        this.telefonoResponsabile = telefonoResponsabile;
    }


}
