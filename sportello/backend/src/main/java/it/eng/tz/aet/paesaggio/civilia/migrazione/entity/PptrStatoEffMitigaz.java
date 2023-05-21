package it.eng.tz.aet.paesaggio.civilia.migrazione.entity;
/**
 * @Table(name="TNO_PPTR_STATO_EFF_MITIGAZ")
 * @author acolaianni
 *
 */
public class PptrStatoEffMitigaz {
	
	//@Column(name="STATO_EFF_MITIGAZ_ID")
	private long id;
	
	//@Column(name="T_PRATICA_APPTR_ID")
	private long tPraticaApptrId;
	
	//@Column(name="CODICE_PRATICA_APPPTR")
	private String codicePraticaAppptr;

	//@Column(name="DESCR_STATO_ATTUALE")
	private String descrStatoAttuale;
	
	//@Column(name="EFFETTI_REALIZ_OPERA")
	private String effettiRealizOpera;
	
	//@Column(name="MITIGAZIONE_IMP_INTERV")
	private String mitigazioneImpInterv;
	
	//@Column(name="INDICAZ_CONTENUTI_PERCETTIVI")
	private String indicazContenutiPercettivi;

	//@Column( name="PROG" )
	private long prog;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long gettPraticaApptrId() {
		return tPraticaApptrId;
	}

	public void settPraticaApptrId(long tPraticaApptrId) {
		this.tPraticaApptrId = tPraticaApptrId;
	}

	public String getCodicePraticaAppptr() {
		return codicePraticaAppptr;
	}

	public void setCodicePraticaAppptr(String codicePraticaAppptr) {
		this.codicePraticaAppptr = codicePraticaAppptr;
	}

	public String getDescrStatoAttuale() {
		return descrStatoAttuale;
	}

	public void setDescrStatoAttuale(String descrStatoAttuale) {
		this.descrStatoAttuale = descrStatoAttuale;
	}

	public String getEffettiRealizOpera() {
		return effettiRealizOpera;
	}

	public void setEffettiRealizOpera(String effettiRealizOpera) {
		this.effettiRealizOpera = effettiRealizOpera;
	}

	public String getMitigazioneImpInterv() {
		return mitigazioneImpInterv;
	}

	public void setMitigazioneImpInterv(String mitigazioneImpInterv) {
		this.mitigazioneImpInterv = mitigazioneImpInterv;
	}

	public String getIndicazContenutiPercettivi() {
		return indicazContenutiPercettivi;
	}

	public void setIndicazContenutiPercettivi(String indicazContenutiPercettivi) {
		this.indicazContenutiPercettivi = indicazContenutiPercettivi;
	}

	public long getProg() {
		return prog;
	}

	public void setProg(long prog) {
		this.prog = prog;
	}
	
	
	
}
