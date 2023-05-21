package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.eng.tz.puglia.util.json.DateSerializer;
import it.eng.tz.puglia.util.json.DateSqlDeserializer;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

/**
 * Search for table presentazione_istanza.corrispondenza
 */
public class CorrispondenzaSearch extends BaseSearchRequestBean {

	private static final long serialVersionUID = 5372009601L;
	// COLUMN id
	private String id;
	// COLUMN id_eml_cmis
	private String idEmlCmis;
	// COLUMN message_id
	private String messageId;
	// COLUMN mittente_email
	private String mittenteEmail;
	// COLUMN mittente_denominazione
	private String mittenteDenominazione;
	// COLUMN mittente_username
	private String mittenteUsername;
	// COLUMN mittente_ente
	private String mittenteEnte;
	// COLUMN data_invio
	private String dataInvio;
	// COLUMN oggetto
	private String oggetto;
	// COLUMN stato
	private String stato;
	// COLUMN comunicazione
	private String comunicazione;
	// COLUMN bozza
	private Boolean bozza;
	// COLUMN text
	private String text;
	// COLUMN codice_template
	private String codiceTemplate;
	// COLUMN id_organizzazione_owner
	private String idOrganizzazioneOwner;
	// COLUMN visibilita
	private String visibilita;
	// COLUMN tipo_organizzazione_owner
	private String tipoOrganizzazioneOwner;
	// COLUMN riservata
	private String riservata;
	
	//customizzati per la search
	@JsonDeserialize(using = DateSqlDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
	private Date dataInvioDa;
	@JsonDeserialize(using = DateSqlDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
	private Date dataInvioA;
	//serve a filtrare le comunicazioni con not (\"bozza\" = true AND \"comunicazione\"=false ) per non visualizzare 
	//le bozze gestite automaticamente (es. relazione ente, parere sop, etc..)
	private boolean bozzeAutomatiche=false; //vengono escluse per default.....
	
	private String escludiBozzeOrganizzazione;
	private String escludiBozzeTipoOrganizzazione;
	//Flag per distinguere se la ricerca è in sola lettura (per utenti "altri titolari"  non owner)
	private boolean solaLettura=false;
	
	public String getEscludiBozzeOrganizzazione() {
		return escludiBozzeOrganizzazione;
	}

	public void setEscludiBozzeOrganizzazione(String escludiBozzeOrganizzazione) {
		this.escludiBozzeOrganizzazione = escludiBozzeOrganizzazione;
	}

	public String getEscludiBozzeTipoOrganizzazione() {
		return escludiBozzeTipoOrganizzazione;
	}

	public void setEscludiBozzeTipoOrganizzazione(String escludiBozzeTipoOrganizzazione) {
		this.escludiBozzeTipoOrganizzazione = escludiBozzeTipoOrganizzazione;
	}

	public boolean isBozzeAutomatiche() {
		return bozzeAutomatiche;
	}

	public void setBozzeAutomatiche(boolean bozzeAutomatiche) {
		this.bozzeAutomatiche = bozzeAutomatiche;
	}

	/**
	 * per cercare in tutti i campi mittente
	 */
	private String mittente;
	private String destinatario;
	

	public String getMittente() {
		return mittente;
	}

	public void setMittente(String mittente) {
		this.mittente = mittente;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public Date getDataInvioDa() {
		return dataInvioDa;
	}

	public void setDataInvioDa(Date dataInvioDa) {
		this.dataInvioDa = dataInvioDa;
	}

	public Date getDataInvioA() {
		return dataInvioA;
	}

	public void setDataInvioA(Date dataInvioA) {
		this.dataInvioA = dataInvioA;
	}

	public String getCodiceTemplate() {
		return codiceTemplate;
	}

	public void setCodiceTemplate(String codiceTemplate) {
		this.codiceTemplate = codiceTemplate;
	}

	public String getIdOrganizzazioneOwner() {
		return idOrganizzazioneOwner;
	}

	public void setIdOrganizzazioneOwner(String idOrganizzazioneOwner) {
		this.idOrganizzazioneOwner = idOrganizzazioneOwner;
	}

	public String getVisibilita() {
		return visibilita;
	}

	public void setVisibilita(String visibilita) {
		this.visibilita = visibilita;
	}

	public String getTipoOrganizzazioneOwner() {
		return tipoOrganizzazioneOwner;
	}

	public void setTipoOrganizzazioneOwner(String tipoOrganizzazioneOwner) {
		this.tipoOrganizzazioneOwner = tipoOrganizzazioneOwner;
	}

	public String getRiservata() {
		return riservata;
	}

	public void setRiservata(String riservata) {
		this.riservata = riservata;
	}

	private UUID idPratica;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdEmlCmis() {
		return this.idEmlCmis;
	}

	public void setIdEmlCmis(String idEmlCmis) {
		this.idEmlCmis = idEmlCmis;
	}

	public String getMessageId() {
		return this.messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getMittenteEmail() {
		return this.mittenteEmail;
	}

	public void setMittenteEmail(String mittenteEmail) {
		this.mittenteEmail = mittenteEmail;
	}

	public String getMittenteDenominazione() {
		return this.mittenteDenominazione;
	}

	public void setMittenteDenominazione(String mittenteDenominazione) {
		this.mittenteDenominazione = mittenteDenominazione;
	}

	public String getMittenteUsername() {
		return this.mittenteUsername;
	}

	public void setMittenteUsername(String mittenteUsername) {
		this.mittenteUsername = mittenteUsername;
	}

	public String getMittenteEnte() {
		return this.mittenteEnte;
	}

	public void setMittenteEnte(String mittenteEnte) {
		this.mittenteEnte = mittenteEnte;
	}

	public String getDataInvio() {
		return this.dataInvio;
	}

	public void setDataInvio(String dataInvio) {
		this.dataInvio = dataInvio;
	}

	public String getOggetto() {
		return this.oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getStato() {
		return this.stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getComunicazione() {
		return this.comunicazione;
	}

	public void setComunicazione(String comunicazione) {
		this.comunicazione = comunicazione;
	}

	public Boolean getBozza() {
		return this.bozza;
	}

	public void setBozza(Boolean bozza) {
		this.bozza = bozza;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public UUID getIdPratica() {
		return idPratica;
	}

	public void setIdPratica(UUID idPratica) {
		this.idPratica = idPratica;
	}

	public boolean isSolaLettura() {
		return solaLettura;
	}

	public void setSolaLettura(boolean solaLettura) {
		this.solaLettura = solaLettura;
	}

}
