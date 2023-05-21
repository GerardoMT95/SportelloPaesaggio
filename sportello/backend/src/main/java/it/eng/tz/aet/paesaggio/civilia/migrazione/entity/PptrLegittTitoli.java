package it.eng.tz.aet.paesaggio.civilia.migrazione.entity;

import java.util.Date;

/**
 * @Table(name="TNO_PPTR_LEGITT_TITOLI")
 * @author acolaianni
 *
 */
public class PptrLegittTitoli {
	
	//@Column(name="LEGITT_TITOLI_ID")
	private long legittTitoliId;

	//@Column(name="CODICE_PRATICA_APPPTR")
	private String codicePraticaAppptr;

	//@Column(name="LEG_TIT_DATA_RILASCIO")
	private Date legTitDataRilascio;

	//@Column(name="LEG_TIT_DENOMINAZIONE")
	private String legTitDenominazione;

	//@Column(name="LEG_TIT_INTESTATARIO")
	private String legTitIntestatario;

	//@Column(name="LEG_TIT_NUM_PROTOCOLLO")
	private String legTitNumProtocollo;

	//@Column(name="LEG_TIT_RILASCIATO_DA")
	private String legTitRilasciatoDa;

	//@Column(name="LEGITTIMITA_ID")
	private long legittimitaId;

	//@Column(name="T_PRATICA_APPTR_ID")
	private long tPraticaApptrId;
	
	//@Column( name="PROG" )
	private long prog;

	public long getLegittTitoliId() {
		return legittTitoliId;
	}

	public void setLegittTitoliId(long legittTitoliId) {
		this.legittTitoliId = legittTitoliId;
	}

	public String getCodicePraticaAppptr() {
		return codicePraticaAppptr;
	}

	public void setCodicePraticaAppptr(String codicePraticaAppptr) {
		this.codicePraticaAppptr = codicePraticaAppptr;
	}

	public Date getLegTitDataRilascio() {
		return legTitDataRilascio;
	}

	public void setLegTitDataRilascio(Date legTitDataRilascio) {
		this.legTitDataRilascio = legTitDataRilascio;
	}

	public String getLegTitDenominazione() {
		return legTitDenominazione;
	}

	public void setLegTitDenominazione(String legTitDenominazione) {
		this.legTitDenominazione = legTitDenominazione;
	}

	public String getLegTitIntestatario() {
		return legTitIntestatario;
	}

	public void setLegTitIntestatario(String legTitIntestatario) {
		this.legTitIntestatario = legTitIntestatario;
	}

	public String getLegTitNumProtocollo() {
		return legTitNumProtocollo;
	}

	public void setLegTitNumProtocollo(String legTitNumProtocollo) {
		this.legTitNumProtocollo = legTitNumProtocollo;
	}

	public String getLegTitRilasciatoDa() {
		return legTitRilasciatoDa;
	}

	public void setLegTitRilasciatoDa(String legTitRilasciatoDa) {
		this.legTitRilasciatoDa = legTitRilasciatoDa;
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
