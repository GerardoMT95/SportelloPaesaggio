package it.eng.tz.aet.paesaggio.civilia.migrazione.entity;

/**
 * @Table( name="TNO_PPTR_PROCED_CONTENZIOSO" )
 * @author acolaianni
 *
 */
public class PptrProcedimentiContenzioso {
	
	//@Column( name="TNO_PPTR_PROCED_CONTENZIOSO_ID" )
	private long id;

	//@Column( name="CODICE_PRATICA_APPPTR" )
	private String codicePraticaApptr;
	
	//@Column( name="T_PRATICA_APPTR_ID" )
	private long tPraticaApptrId;

	//@Column( name="FLAG_CONTENZIOSO_IN_ATTO" )
	private String flagContenziosoInAtto;
	
	//@Column( name="DESCRIZIONE" )
	private String descrizione;
	
	//@Column( name="PROG" )
	private long prog;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCodicePraticaApptr() {
		return codicePraticaApptr;
	}

	public void setCodicePraticaApptr(String codicePraticaApptr) {
		this.codicePraticaApptr = codicePraticaApptr;
	}

	public long gettPraticaApptrId() {
		return tPraticaApptrId;
	}

	public void settPraticaApptrId(long tPraticaApptrId) {
		this.tPraticaApptrId = tPraticaApptrId;
	}

	public String getFlagContenziosoInAtto() {
		return flagContenziosoInAtto;
	}

	public void setFlagContenziosoInAtto(String flagContenziosoInAtto) {
		this.flagContenziosoInAtto = flagContenziosoInAtto;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public long getProg() {
		return prog;
	}

	public void setProg(long prog) {
		this.prog = prog;
	}
	
	
}
