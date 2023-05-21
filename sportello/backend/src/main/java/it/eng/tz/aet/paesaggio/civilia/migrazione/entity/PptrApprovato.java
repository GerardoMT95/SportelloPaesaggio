package it.eng.tz.aet.paesaggio.civilia.migrazione.entity;

/**
 * @Table( name="TNO_PPTR_APPROVATO" )
 * @author acolaianni
 *
 */
public class PptrApprovato {
	//@Column( name="PPTR_APPROVATO_ID" )
	private long pptrApprovatoId;
	
	//@Column( name="AMBITO_PAESAGGISTICO" )
	private String ambitoPaesaggistico;

	//@Column( name="CODICE_PRATICA_APPTR" )
	private String codicePraticaApptr;

	//@Column( name="FIGURE_AMBITO_PAESAGGISTICO" )
	private String figureAmbitoPaesaggistico;

	//@Column( name="RICADE_TERR_ART_1_03_CO_5_6" )
	private String ricadeTerrArt103Co56;

	//@Column( name="RICADE_TERR_ART_142_CO_2" )
	private String ricadeTerrArt142Co2;

	//@Column( name="T_PRATICA_APPTR" )
	private long tPraticaApptr;
	
	//@Column( name="PROG" )
	private long prog;

	public long getPptrApprovatoId() {
		return pptrApprovatoId;
	}

	public void setPptrApprovatoId(long pptrApprovatoId) {
		this.pptrApprovatoId = pptrApprovatoId;
	}

	public String getAmbitoPaesaggistico() {
		return ambitoPaesaggistico;
	}

	public void setAmbitoPaesaggistico(String ambitoPaesaggistico) {
		this.ambitoPaesaggistico = ambitoPaesaggistico;
	}

	public String getCodicePraticaApptr() {
		return codicePraticaApptr;
	}

	public void setCodicePraticaApptr(String codicePraticaApptr) {
		this.codicePraticaApptr = codicePraticaApptr;
	}

	public String getFigureAmbitoPaesaggistico() {
		return figureAmbitoPaesaggistico;
	}

	public void setFigureAmbitoPaesaggistico(String figureAmbitoPaesaggistico) {
		this.figureAmbitoPaesaggistico = figureAmbitoPaesaggistico;
	}

	public String getRicadeTerrArt103Co56() {
		return ricadeTerrArt103Co56;
	}

	public void setRicadeTerrArt103Co56(String ricadeTerrArt103Co56) {
		this.ricadeTerrArt103Co56 = ricadeTerrArt103Co56;
	}

	public String getRicadeTerrArt142Co2() {
		return ricadeTerrArt142Co2;
	}

	public void setRicadeTerrArt142Co2(String ricadeTerrArt142Co2) {
		this.ricadeTerrArt142Co2 = ricadeTerrArt142Co2;
	}

	public long gettPraticaApptr() {
		return tPraticaApptr;
	}

	public void settPraticaApptr(long tPraticaApptr) {
		this.tPraticaApptr = tPraticaApptr;
	}

	public long getProg() {
		return prog;
	}

	public void setProg(long prog) {
		this.prog = prog;
	}	

}
