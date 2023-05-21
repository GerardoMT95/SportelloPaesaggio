package it.eng.tz.aet.paesaggio.civilia.migrazione.entity;

import java.util.Date;

//@Table(name="TNO_PPTR_STRUMENTI_URBANISTICI")
public class PptrStrumentiUrbanistici {
	
	//@Column(name="ISTAT_6_PROV")
	private String codIstat;
	
	//@Column(name="TIPO_STRUMENTO")
	private long tipoStrumento;
	
	//@Column(name="STATO")
	private String stato;
	
	//@Column(name="ATTO")
	private String atto;

	//@Column(name="DATA_ATTO")
	private Date dataAtto;

	public String getCodIstat() {
		return codIstat;
	}

	public void setCodIstat(String codIstat) {
		this.codIstat = codIstat;
	}

	public long getTipoStrumento() {
		return tipoStrumento;
	}

	public void setTipoStrumento(long tipoStrumento) {
		this.tipoStrumento = tipoStrumento;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getAtto() {
		return atto;
	}

	public void setAtto(String atto) {
		this.atto = atto;
	}

	public Date getDataAtto() {
		return dataAtto;
	}

	public void setDataAtto(Date dataAtto) {
		this.dataAtto = dataAtto;
	}

	
}
