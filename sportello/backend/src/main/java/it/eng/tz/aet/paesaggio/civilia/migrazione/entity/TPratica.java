package it.eng.tz.aet.paesaggio.civilia.migrazione.entity;

import java.util.Date;

public class TPratica {
	private String codice;
	private Date dataAttivazione;
	private Long tPraticaId;
	private Long tTipopraticaId;
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public Date getDataAttivazione() {
		return dataAttivazione;
	}
	public void setDataAttivazione(Date dataAttivazione) {
		this.dataAttivazione = dataAttivazione;
	}
	public Long gettPraticaId() {
		return tPraticaId;
	}
	public void settPraticaId(Long tPraticaId) {
		this.tPraticaId = tPraticaId;
	}
	public Long gettTipopraticaId() {
		return tTipopraticaId;
	}
	public void settTipopraticaId(Long tTipopraticaId) {
		this.tTipopraticaId = tTipopraticaId;
	}
	
	
}
