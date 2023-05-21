/**
 * 
 */
package it.eng.tz.aet.paesaggio.civilia.migrazione.entity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * CIVILIA_CS.T_MAIL_INVIATE
 * @author Adriano Colaianni
 * @date 23 apr 2021
 */
public class CiviliaMail {
	
	//@Id
	//@Column(name="T_MAIL_INVIATE_ID") FK in T_MAIL_INVIATE_ALLEGATI
	private long tMailInviateId;

	//@Column(name="T_PRATICA_ID")
	private long tPraticaId;

	//@Column(name="CC_TIME_STAMP") //timestamp invio
	private Long ccTimeStamp;
	
	//@Column(name="DESTINATARIO") //lista insirizzi email separata da es: comunelesina@pec.it, protocollo@cert.provincia.foggia.it, ufficio.attuazionepianificazionepaesaggistica@pec.rupar.puglia.it
	private String destinatario;	
	
	//@Column(name="OGGETTO")
	private String oggetto;
	              
	//@Column(name="TESTO")
	private String testo;
	
	//@Column(name="DATA_INVIO")
	//@javax.persistence.Temporal( TemporalType.TIMESTAMP)
	private Date dataInvio;
	
	//@Column(name="MITTENTE") email mittente in caso di verso NULL
	private String mittente;
	                
	//@Column(name="VERSO") I=ingresso U=uscita  o null
	private String verso;
	
	//@Column(name="IS_PEC") 1=true altrimenti null
	private String isPec;
	  
	//@Column(name="MESSAGE_ID")
	private String messageId;
	                     
	//@Column(name="T_TIPICOMUNICAZIONE_ID")
	private long tTipoComunicazioneId;

	//@Column(name="T_PROTOCOLLO_ID")
	private long tProtocolloId;
	    
	//@Column(name="FLG_ELIMINATO")
	private long flgEliminato;
	
	//set manuale
	private List<CiviliaMailAllegati> allegatiMail;

	public List<CiviliaMailAllegati> getAllegatiMail() {
		return allegatiMail;
	}

	public void setAllegatiMail(List<CiviliaMailAllegati> allegatiMail) {
		this.allegatiMail = allegatiMail;
	}

	public long gettMailInviateId() {
		return tMailInviateId;
	}

	public void settMailInviateId(long tMailInviateId) {
		this.tMailInviateId = tMailInviateId;
	}

	public long gettPraticaId() {
		return tPraticaId;
	}

	public void settPraticaId(long tPraticaId) {
		this.tPraticaId = tPraticaId;
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

	public String getIsPec() {
		return isPec;
	}

	public void setIsPec(String isPec) {
		this.isPec = isPec;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public long gettTipoComunicazioneId() {
		return tTipoComunicazioneId;
	}

	public void settTipoComunicazioneId(long tTipoComunicazioneId) {
		this.tTipoComunicazioneId = tTipoComunicazioneId;
	}

	public long gettProtocolloId() {
		return tProtocolloId;
	}

	public void settProtocolloId(long tProtocolloId) {
		this.tProtocolloId = tProtocolloId;
	}

	public long getFlgEliminato() {
		return flgEliminato;
	}

	public void setFlgEliminato(long flgEliminato) {
		this.flgEliminato = flgEliminato;
	}
	
	

}
