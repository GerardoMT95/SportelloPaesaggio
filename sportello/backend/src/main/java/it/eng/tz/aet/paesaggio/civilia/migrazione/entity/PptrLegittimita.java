package it.eng.tz.aet.paesaggio.civilia.migrazione.entity;

import java.util.Date;

//@Table(name="TNO_PPTR_LEGITTIMITA")
public class PptrLegittimita {

	//@Column(name="LEGITTIMITA_ID")
	private long legittimitaId;

	//@Column(name="CODICE_PRATICA_APPPTR")
	private String codicePraticaAppptr;

	//@Column(name="LEG_PAE_DATA_INTERVENTO")
	private Date legPaeDataIntervento;

	//@Column(name="LEG_PAE_DATA_VINCOLO")
	private Date legPaeDataVincolo;

	//@Column(name="LEG_PAE_TIPO_VINCOLO")
	private String legPaeTipoVincolo;

	//@Column(name="LEG_PAESAG_IMMOBILE")
	private String legPaesagImmobile;

	//@Column(name="LEG_URB_PRIVO_SPECIFICA")
	private String legUrbPrivoSpecifica;

	// Titolo edilizio	
	//@Column(name="LEG_URB_TIT_EDILIZIO")
	private String legUrbTitEdilizio;

	//@Column(name="T_PRATICA_APPTR_ID")
	private long tPraticaApptrId;
	
	//@Column( name="PROG" )
	private long prog;

	public long getLegittimitaId() {
		return legittimitaId;
	}

	public void setLegittimitaId(long legittimitaId) {
		this.legittimitaId = legittimitaId;
	}

	public String getCodicePraticaAppptr() {
		return codicePraticaAppptr;
	}

	public void setCodicePraticaAppptr(String codicePraticaAppptr) {
		this.codicePraticaAppptr = codicePraticaAppptr;
	}

	public Date getLegPaeDataIntervento() {
		return legPaeDataIntervento;
	}

	public void setLegPaeDataIntervento(Date legPaeDataIntervento) {
		this.legPaeDataIntervento = legPaeDataIntervento;
	}

	public Date getLegPaeDataVincolo() {
		return legPaeDataVincolo;
	}

	public void setLegPaeDataVincolo(Date legPaeDataVincolo) {
		this.legPaeDataVincolo = legPaeDataVincolo;
	}

	public String getLegPaeTipoVincolo() {
		return legPaeTipoVincolo;
	}

	public void setLegPaeTipoVincolo(String legPaeTipoVincolo) {
		this.legPaeTipoVincolo = legPaeTipoVincolo;
	}

	public String getLegPaesagImmobile() {
		return legPaesagImmobile;
	}

	public void setLegPaesagImmobile(String legPaesagImmobile) {
		this.legPaesagImmobile = legPaesagImmobile;
	}

	public String getLegUrbPrivoSpecifica() {
		return legUrbPrivoSpecifica;
	}

	public void setLegUrbPrivoSpecifica(String legUrbPrivoSpecifica) {
		this.legUrbPrivoSpecifica = legUrbPrivoSpecifica;
	}

	public String getLegUrbTitEdilizio() {
		return legUrbTitEdilizio;
	}

	public void setLegUrbTitEdilizio(String legUrbTitEdilizio) {
		this.legUrbTitEdilizio = legUrbTitEdilizio;
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
