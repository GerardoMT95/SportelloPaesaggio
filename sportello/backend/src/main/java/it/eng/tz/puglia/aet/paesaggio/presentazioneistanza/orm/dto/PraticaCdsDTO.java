package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainStringValueLabel;
import it.eng.tz.puglia.util.json.DateSerializer;
import it.eng.tz.puglia.util.json.DateSqlDeserializer;

/**
 * DTO for table presentazione_istanza.pratica_cds
 */
public class PraticaCdsDTO implements Serializable{

    private static final long serialVersionUID = 6845791453L;
    //COLUMN id
    private String id;
    //COLUMN id_pratica
    private UUID idPratica;
    //COLUMN tipo
    private String tipo;
    //COLUMN attivita
    private String attivita;
    //COLUMN azione
    private String azione;
    //COLUMN termine_richiesta_integrazione
    @JsonDeserialize(using = DateSqlDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date termineRichiestaIntegrazione;
    //COLUMN termine_pareri
    @JsonDeserialize(using = DateSqlDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date terminePareri;
    //COLUMN prima_sessione
    @JsonDeserialize(using = DateSqlDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date primaSessione;
    //COLUMN data_termine
    @JsonDeserialize(using = DateSqlDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date dataTermine;
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
    private Timestamp dateCreate;
    //COLUMN user_create
    private String userCreate;
    //COLUMN date_update
    private Timestamp dateUpdate;
    //COLUMN user_update
    private String userUpdate;
    //COLUMN completed
    private boolean completed;
    //COLUMN id_cds
    private Long idCds;
    //COLUMN comitato
    private Boolean comitato;
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
    
    private List<PlainStringValueLabel> partecipanti;

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public UUID getIdPratica(){
        return this.idPratica;
    }

    public void setIdPratica(UUID idPratica){
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

    public Date getTermineRichiestaIntegrazione(){
        return this.termineRichiestaIntegrazione;
    }

    public void setTermineRichiestaIntegrazione(Date termineRichiestaIntegrazione){
        this.termineRichiestaIntegrazione = termineRichiestaIntegrazione;
    }

    public Date getTerminePareri(){
        return this.terminePareri;
    }

    public void setTerminePareri(Date terminePareri){
        this.terminePareri = terminePareri;
    }

    public Date getPrimaSessione(){
        return this.primaSessione;
    }

    public void setPrimaSessione(Date primaSessione){
        this.primaSessione = primaSessione;
    }

    public Date getDataTermine(){
        return this.dataTermine;
    }

    public void setDataTermine(Date dataTermine){
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

    public Timestamp getDateCreate(){
        return this.dateCreate;
    }

    public void setDateCreate(Timestamp dateCreate){
        this.dateCreate = dateCreate;
    }

    public String getUserCreate(){
        return this.userCreate;
    }

    public void setUserCreate(String userCreate){
        this.userCreate = userCreate;
    }

    public Timestamp getDateUpdate(){
        return this.dateUpdate;
    }

    public void setDateUpdate(Timestamp dateUpdate){
        this.dateUpdate = dateUpdate;
    }

    public String getUserUpdate(){
        return this.userUpdate;
    }

    public void setUserUpdate(String userUpdate){
        this.userUpdate = userUpdate;
    }

    public boolean getCompleted(){
        return this.completed;
    }

    public void setCompleted(boolean completed){
        this.completed = completed;
    }

    public Long getIdCds(){
        return this.idCds;
    }

    public void setIdCds(Long idCds){
        this.idCds = idCds;
    }

    public Boolean getComitato(){
        return this.comitato;
    }

    public void setComitato(Boolean comitato){
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
    
    public List<PlainStringValueLabel> getPartecipanti(){
        return this.partecipanti;
    }

    public void setPartecipanti(List<PlainStringValueLabel> partecipanti){
        this.partecipanti = partecipanti;
    }
}
