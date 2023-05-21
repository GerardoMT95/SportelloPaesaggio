package it.eng.tz.aet.paesaggio.civilia.migrazione.entity;


//@Table(name="TNO_PPTR_DESCR_INTERVENTO")
public class PptrDescrIntervento {

	//@Column(name="PPTR_DESCR_INTERVENTO_ID")
	private long id;

	//@Column(name="CODICE_PRATICA_APPPTR")
	private String codicePraticaAppptr;

	//@Column(name="DESCR_INTERVENTO")
	private String descrIntervento;

	//@Column(name="T_PRATICA_APPTR_ID")
	private long tPraticaApptrId;
	
	//@Column(name="PROG")
	private long prog;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCodicePraticaAppptr() {
		return codicePraticaAppptr;
	}

	public void setCodicePraticaAppptr(String codicePraticaAppptr) {
		this.codicePraticaAppptr = codicePraticaAppptr;
	}

	public String getDescrIntervento() {
		return descrIntervento;
	}

	public void setDescrIntervento(String descrIntervento) {
		this.descrIntervento = descrIntervento;
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
