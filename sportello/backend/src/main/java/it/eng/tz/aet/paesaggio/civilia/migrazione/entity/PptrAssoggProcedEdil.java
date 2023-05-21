package it.eng.tz.aet.paesaggio.civilia.migrazione.entity;

import java.util.Date;

/**
 * TNO_PPTR_ASSOGG_PROCED_EDIL
 * @author acolaianni
 *
 */
public class PptrAssoggProcedEdil {

	//@Column(name="TNO_PPTR_ASSOGG_PROCED_EDIL_ID")
	private long tnoPptrAssoggProcedEdilId;
	
	//@Column(name="ASSOGG_DATA_APPROV_PRATICA")
	private Date assoggDataApprovPratica;

	//@Column(name="ASSOGG_DATAPR_PRATICA_IN_CORSO")
	private Date assoggDataprPraticaInCorso;

	//@Column(name="ASSOGG_ENTE_PRATICA_IN_CORSO")
	private String assoggEntePraticaInCorso;

	//@Column(name="ASSOGG_FLAG_PARERE_URB_ESPR")
	private String assoggFlagParereUrbEspr;

	//@Column(name="ASSOGG_FLAG_PRATICA_IN_CORSO")
	private String assoggFlagPraticaInCorso;

	//@Column(name="CODICE_PRATICA_APPPTR")
	private String codiceAppptrPratica;

	//@Column(name="FLAG_ASSOGGETTATO")
	private String flagAssoggettato;

	//@Column(name="NO_ASSOGG_SPECIFICARE")
	private String noAssoggSpecificare;

	//@Column(name="T_PRATICA_APPTR_ID")
	private long tnoPptrPraticaId;
	
	//@Column( name="PROG" )
	private long prog;

	public long getTnoPptrAssoggProcedEdilId() {
		return tnoPptrAssoggProcedEdilId;
	}

	public void setTnoPptrAssoggProcedEdilId(long tnoPptrAssoggProcedEdilId) {
		this.tnoPptrAssoggProcedEdilId = tnoPptrAssoggProcedEdilId;
	}

	public Date getAssoggDataApprovPratica() {
		return assoggDataApprovPratica;
	}

	public void setAssoggDataApprovPratica(Date assoggDataApprovPratica) {
		this.assoggDataApprovPratica = assoggDataApprovPratica;
	}

	public Date getAssoggDataprPraticaInCorso() {
		return assoggDataprPraticaInCorso;
	}

	public void setAssoggDataprPraticaInCorso(Date assoggDataprPraticaInCorso) {
		this.assoggDataprPraticaInCorso = assoggDataprPraticaInCorso;
	}

	public String getAssoggEntePraticaInCorso() {
		return assoggEntePraticaInCorso;
	}

	public void setAssoggEntePraticaInCorso(String assoggEntePraticaInCorso) {
		this.assoggEntePraticaInCorso = assoggEntePraticaInCorso;
	}

	public String getAssoggFlagParereUrbEspr() {
		return assoggFlagParereUrbEspr;
	}

	public void setAssoggFlagParereUrbEspr(String assoggFlagParereUrbEspr) {
		this.assoggFlagParereUrbEspr = assoggFlagParereUrbEspr;
	}

	public String getAssoggFlagPraticaInCorso() {
		return assoggFlagPraticaInCorso;
	}

	public void setAssoggFlagPraticaInCorso(String assoggFlagPraticaInCorso) {
		this.assoggFlagPraticaInCorso = assoggFlagPraticaInCorso;
	}

	public String getCodiceAppptrPratica() {
		return codiceAppptrPratica;
	}

	public void setCodiceAppptrPratica(String codiceAppptrPratica) {
		this.codiceAppptrPratica = codiceAppptrPratica;
	}

	public String getFlagAssoggettato() {
		return flagAssoggettato;
	}

	public void setFlagAssoggettato(String flagAssoggettato) {
		this.flagAssoggettato = flagAssoggettato;
	}

	public String getNoAssoggSpecificare() {
		return noAssoggSpecificare;
	}

	public void setNoAssoggSpecificare(String noAssoggSpecificare) {
		this.noAssoggSpecificare = noAssoggSpecificare;
	}

	public long getTnoPptrPraticaId() {
		return tnoPptrPraticaId;
	}

	public void setTnoPptrPraticaId(long tnoPptrPraticaId) {
		this.tnoPptrPraticaId = tnoPptrPraticaId;
	}

	public long getProg() {
		return prog;
	}

	public void setProg(long prog) {
		this.prog = prog;
	}
	
	
}
