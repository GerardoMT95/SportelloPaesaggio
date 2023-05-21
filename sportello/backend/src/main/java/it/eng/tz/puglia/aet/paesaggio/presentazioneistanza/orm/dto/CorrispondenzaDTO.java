package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * DTO for table presentazione_istanza.corrispondenza
 */
public class CorrispondenzaDTO implements Serializable {

	private static final long serialVersionUID = 7405243846L;
	// COLUMN id
	private Long id;
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
	private Timestamp dataInvio;
	// COLUMN oggetto
	private String oggetto;
	// COLUMN stato
	private boolean stato;
	// COLUMN comunicazione
	private boolean comunicazione;
	// COLUMN bozza
	private boolean bozza;
	// COLUMN text
	private String testo;
	// COLUMN codice_template
	private String codiceTemplate;
	// COLUMN id_organizzazione_owner
	private Integer idOrganizzazioneOwner;
	// COLUMN visibilita
	private String visibilita;
	// COLUMN tipo_organizzazione_owner
	private String tipoOrganizzazioneOwner;
	// COLUMN riservata
	private boolean riservata;
	// COLUMN protocollo
	private String protocollo;
	// COLUMN data_protocollo
	private Date dataProtocollo;
	//COLUMN t_mailinviate_id
	private Long tMailInviateId;
	//COLUMN t_pptr_mailinviate_id
	private Long tPptrMailInviateId;
	
	private String Ruolo;

	
	
	public Long gettMailInviateId() {
		return tMailInviateId;
	}

	public void settMailInviateId(Long tMailInviateId) {
		this.tMailInviateId = tMailInviateId;
	}

	public Long gettPptrMailInviateId() {
		return tPptrMailInviateId;
	}

	public void settPptrMailInviateId(Long tPptrMailInviateId) {
		this.tPptrMailInviateId = tPptrMailInviateId;
	}

	public String getProtocollo() {
		return protocollo;
	}

	public void setProtocollo(String protocollo) {
		this.protocollo = protocollo;
	}

	public Date getDataProtocollo() {
		return dataProtocollo;
	}

	public void setDataProtocollo(Date dataProtocollo) {
		this.dataProtocollo = dataProtocollo;
	}

	public Integer getIdOrganizzazioneOwner() {
		return idOrganizzazioneOwner;
	}

	public void setIdOrganizzazioneOwner(Integer idOrganizzazioneOwner) {
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

	public boolean isRiservata() {
		return riservata;
	}

	public void setRiservata(boolean riservata) {
		this.riservata = riservata;
	}

	public CorrispondenzaDTO() {
		super();
	}

	public CorrispondenzaDTO(CorrispondenzaDTO dto) {
		id = dto.getId();
		idEmlCmis = dto.getIdEmlCmis();
		messageId = dto.getMessageId();
		mittenteEmail = dto.getMittenteEmail();
		mittenteDenominazione = dto.getMittenteDenominazione();
		mittenteUsername = dto.getMittenteUsername();
		mittenteEnte = dto.getMittenteEnte();
		dataInvio = dto.getDataInvio();
		oggetto = dto.getOggetto();
		stato = dto.getStato();
		comunicazione = dto.getComunicazione();
		bozza = dto.getBozza();
		setTesto(dto.getTesto());
		setVisibilita(dto.getVisibilita());
		setTipoOrganizzazioneOwner(dto.getTipoOrganizzazioneOwner());
		setIdOrganizzazioneOwner(dto.getIdOrganizzazioneOwner());
		setRiservata(dto.isRiservata());
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
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

	public Timestamp getDataInvio() {
		return this.dataInvio;
	}

	public void setDataInvio(Timestamp dataInvio) {
		this.dataInvio = dataInvio;
	}

	public String getOggetto() {
		return this.oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public boolean getStato() {
		return this.stato;
	}

	public void setStato(boolean stato) {
		this.stato = stato;
	}

	public boolean getComunicazione() {
		return this.comunicazione;
	}

	public void setComunicazione(boolean comunicazione) {
		this.comunicazione = comunicazione;
	}

	public boolean getBozza() {
		return this.bozza;
	}

	public void setBozza(boolean bozza) {
		this.bozza = bozza;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public String getCodiceTemplate() {
		return codiceTemplate;
	}

	public void setCodiceTemplate(String tipologia) {
		this.codiceTemplate = tipologia;
	}

	public String getRuolo() {
		return Ruolo;
	}

	public void setRuolo(String ruolo) {
		Ruolo = ruolo;
	}

}
