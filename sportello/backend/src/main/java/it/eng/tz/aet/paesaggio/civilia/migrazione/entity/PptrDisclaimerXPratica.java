package it.eng.tz.aet.paesaggio.civilia.migrazione.entity;

//@Table(name="TNO_PPTR_DISCLAIMER_X_PRATICA")
public class PptrDisclaimerXPratica {
private long PptrDisclXPraticaId;
	
	//@Column(name="CODICE_PRATICA_APPPTR")
	private String codicePraticaAppptr;

	//@Column(name="FLG_DISCL_X_PRATICA") S o N
	private String flgDisclXPratica;

	//@Column(name="REFERENTE_PROGETTO_ID")
	private long referenteProgettoId;

	//@Column(name="TNO_PPTR_DISCLAIMER_ID")
	private long pptrDisclaimerId;

	//@Column(name="T_PRATICA_APPTR_ID")
	private long pptrPraticaId;

	//@Column( name="PROG" )
	private long prog;

	public long getPptrDisclXPraticaId() {
		return PptrDisclXPraticaId;
	}

	public void setPptrDisclXPraticaId(long pptrDisclXPraticaId) {
		PptrDisclXPraticaId = pptrDisclXPraticaId;
	}

	public String getCodicePraticaAppptr() {
		return codicePraticaAppptr;
	}

	public void setCodicePraticaAppptr(String codicePraticaAppptr) {
		this.codicePraticaAppptr = codicePraticaAppptr;
	}

	public String getFlgDisclXPratica() {
		return flgDisclXPratica;
	}

	public void setFlgDisclXPratica(String flgDisclXPratica) {
		this.flgDisclXPratica = flgDisclXPratica;
	}

	public long getReferenteProgettoId() {
		return referenteProgettoId;
	}

	public void setReferenteProgettoId(long referenteProgettoId) {
		this.referenteProgettoId = referenteProgettoId;
	}

	public long getPptrDisclaimerId() {
		return pptrDisclaimerId;
	}

	public void setPptrDisclaimerId(long pptrDisclaimerId) {
		this.pptrDisclaimerId = pptrDisclaimerId;
	}

	public long getPptrPraticaId() {
		return pptrPraticaId;
	}

	public void setPptrPraticaId(long pptrPraticaId) {
		this.pptrPraticaId = pptrPraticaId;
	}

	public long getProg() {
		return prog;
	}

	public void setProg(long prog) {
		this.prog = prog;
	}

	
}
