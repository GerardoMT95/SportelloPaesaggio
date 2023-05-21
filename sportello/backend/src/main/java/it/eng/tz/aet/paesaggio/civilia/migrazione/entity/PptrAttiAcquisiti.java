package it.eng.tz.aet.paesaggio.civilia.migrazione.entity;

import java.util.Date;

/**
 * @Table(name="TNO_PPTR_ATTI_ACQUISITI")
 * @author acolaianni
 *
 */
public class PptrAttiAcquisiti {
	
	//@Column(name="ATTO_ACQUISITO_ID")
	private long attoAcquisitoId;

	//@Column(name="AUTORITA_RILASCIO")
	private String autoritaRilascio;

	//@Column(name="CODICE_PRATICA_APPPTR")
	private String codicePraticaAppptr;

	//@Column(name="DATA_RILASCIO")
	private Date dataRilascio;

	//@Column(name="FLAG_ATTO_PARERE") //A=atto P=parere
	private String flagAttoParere;

	//@Column(name="PROTOCOLLO")
	private String protocollo;

	//@Column(name="TIPOLOGIA_ATTO")
	private String tipologiaAtto;
	
	//@Column(name="INTESTATARIO_ATTO")
	private String intestatario_atto;
	
	//@Column( name="PROG" )
	private long prog;

	public long getAttoAcquisitoId() {
		return attoAcquisitoId;
	}

	public void setAttoAcquisitoId(long attoAcquisitoId) {
		this.attoAcquisitoId = attoAcquisitoId;
	}

	public String getAutoritaRilascio() {
		return autoritaRilascio;
	}

	public void setAutoritaRilascio(String autoritaRilascio) {
		this.autoritaRilascio = autoritaRilascio;
	}

	public String getCodicePraticaAppptr() {
		return codicePraticaAppptr;
	}

	public void setCodicePraticaAppptr(String codicePraticaAppptr) {
		this.codicePraticaAppptr = codicePraticaAppptr;
	}

	public Date getDataRilascio() {
		return dataRilascio;
	}

	public void setDataRilascio(Date dataRilascio) {
		this.dataRilascio = dataRilascio;
	}

	public String getFlagAttoParere() {
		return flagAttoParere;
	}

	public void setFlagAttoParere(String flagAttoParere) {
		this.flagAttoParere = flagAttoParere;
	}

	public String getProtocollo() {
		return protocollo;
	}

	public void setProtocollo(String protocollo) {
		this.protocollo = protocollo;
	}

	public String getTipologiaAtto() {
		return tipologiaAtto;
	}

	public void setTipologiaAtto(String tipologiaAtto) {
		this.tipologiaAtto = tipologiaAtto;
	}

	public String getIntestatario_atto() {
		return intestatario_atto;
	}

	public void setIntestatario_atto(String intestatario_atto) {
		this.intestatario_atto = intestatario_atto;
	}

	public long getProg() {
		return prog;
	}

	public void setProg(long prog) {
		this.prog = prog;
	}

	
}
