package it.eng.tz.aet.paesaggio.civilia.migrazione.entity;

import java.sql.Blob;
import java.sql.Timestamp;
import java.util.Date;

/**
 * TNO_PPTR_MAIL_INVIATE
 * @author acolaianni
 *
 */
public class PptrMailInviate {
	//@Column(name = "TNO_PPTR_MAIL_INVIATE_ID")
	private long mailInviateId;


	//@Column( name = "CODICE_PRATICA_APPPTR" )
	private String codicePraticaAppptr;
	
	//@Column( name = "CC_TIME_STAMP" )
	private Long ccTimeStamp;

	//@Column( name="DESTINATARIO" )
	private String destinatario;

	//@Column( name = "OGGETTO" )
	private String oggetto;
	
	//@Column( name = "TESTO" ) 
	private String testo;

	//@Column( name = "DATA_INVIO" )
	private Date dataInvio;
	
	//@Column( name = "MITTENTE" )
	private String mittente;
	
	//@Column( name= "VERSO" )
	private String verso;
	
	//@Column( name = "MESSAGE_ID" )
	private String messageId;
	
	//@Column( name = "EML" )
	private Blob eml;
	
	//@Column(name = "TNO_TIPO_COMUNICAZIONE_ID")
	private long tnoTipoComunicazioneId;
	
	//"TITOLO" deriva dal join con TNO_TIPO_COMUNICAZIONE
	private String titolo;

	
	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public long getMailInviateId() {
		return mailInviateId;
	}

	public void setMailInviateId(long mailInviateId) {
		this.mailInviateId = mailInviateId;
	}

	public String getCodicePraticaAppptr() {
		return codicePraticaAppptr;
	}

	public void setCodicePraticaAppptr(String codicePraticaAppptr) {
		this.codicePraticaAppptr = codicePraticaAppptr;
	}

	public Long getCcTimeStamp() {
		return ccTimeStamp;
	}

	public void setCcTimeStamp(Long ccTimeStamp) {
		this.ccTimeStamp = ccTimeStamp;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public Date getDataInvio() {
		return dataInvio;
	}

	public void setDataInvio(Date dataInvio) {
		this.dataInvio = dataInvio;
	}

	public String getMittente() {
		return mittente;
	}

	public void setMittente(String mittente) {
		this.mittente = mittente;
	}

	public String getVerso() {
		return verso;
	}

	public void setVerso(String verso) {
		this.verso = verso;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public Blob getEml() {
		return eml;
	}

	public void setEml(Blob eml) {
		this.eml = eml;
	}

	public long getTnoTipoComunicazioneId() {
		return tnoTipoComunicazioneId;
	}

	public void setTnoTipoComunicazioneId(long tnoTipoComunicazioneId) {
		this.tnoTipoComunicazioneId = tnoTipoComunicazioneId;
	}
	
}
