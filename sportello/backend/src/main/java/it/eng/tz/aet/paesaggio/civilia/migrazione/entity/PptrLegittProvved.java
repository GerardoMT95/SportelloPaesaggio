package it.eng.tz.aet.paesaggio.civilia.migrazione.entity;

import java.util.Date;

/**
 * @Table(name="TNO_PPTR_LEGITT_PROVVED")
 * @author acolaianni
 *
 */
public class PptrLegittProvved {
	//@Column(name="LEGITT_PROVVED_ID")
	private long legittProvvedId;

	//@Column(name="CODICE_PRATICA_APPPTR")
	private String codicePraticaAppptr;

	//@Column(name="LEG_PROVV_DATA_RILASCIO")
	private Date legProvvDataRilascio;

	//@Column(name="LEG_PROVV_DENOMINAZIONE")
	private String legProvvDenominazione;

	//@Column(name="LEG_PROVV_INTESTATARIO")
	private String legProvvIntestatario;

	//@Column(name="LEG_PROVV_NUM_PROTOCOLLO")
	private String legProvvNumProtocollo;

	//@Column(name="LEG_PROVV_RILASCIATO_DA")
	private String legProvvRilasciatoDa;

	//@Column(name="LEGITTIMITA_ID")
	private long legittimitaId;

	//@Column(name="T_PRATICA_APPTR_ID")
	private long tPraticaApptrId;
	
	//@Column( name="PROG" )
	private long prog;

	public long getLegittProvvedId() {
		return legittProvvedId;
	}

	public void setLegittProvvedId(long legittProvvedId) {
		this.legittProvvedId = legittProvvedId;
	}

	public String getCodicePraticaAppptr() {
		return codicePraticaAppptr;
	}

	public void setCodicePraticaAppptr(String codicePraticaAppptr) {
		this.codicePraticaAppptr = codicePraticaAppptr;
	}

	public Date getLegProvvDataRilascio() {
		return legProvvDataRilascio;
	}

	public void setLegProvvDataRilascio(Date legProvvDataRilascio) {
		this.legProvvDataRilascio = legProvvDataRilascio;
	}

	public String getLegProvvDenominazione() {
		return legProvvDenominazione;
	}

	public void setLegProvvDenominazione(String legProvvDenominazione) {
		this.legProvvDenominazione = legProvvDenominazione;
	}

	public String getLegProvvIntestatario() {
		return legProvvIntestatario;
	}

	public void setLegProvvIntestatario(String legProvvIntestatario) {
		this.legProvvIntestatario = legProvvIntestatario;
	}

	public String getLegProvvNumProtocollo() {
		return legProvvNumProtocollo;
	}

	public void setLegProvvNumProtocollo(String legProvvNumProtocollo) {
		this.legProvvNumProtocollo = legProvvNumProtocollo;
	}

	public String getLegProvvRilasciatoDa() {
		return legProvvRilasciatoDa;
	}

	public void setLegProvvRilasciatoDa(String legProvvRilasciatoDa) {
		this.legProvvRilasciatoDa = legProvvRilasciatoDa;
	}

	public long getLegittimitaId() {
		return legittimitaId;
	}

	public void setLegittimitaId(long legittimitaId) {
		this.legittimitaId = legittimitaId;
	}

	public long gettPraticaApptrId() {
		return tPraticaApptrId;
	}

	public void settPraticaApptrId(long tPraticaApptrId) {
		this.tPraticaApptrId = tPraticaApptrId;
	}

	public long getProg() {
		return prog;
	}

	public void setProg(long prog) {
		this.prog = prog;
	}


}
