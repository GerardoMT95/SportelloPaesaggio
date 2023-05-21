/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.dto;

import java.util.Date;

/**
 * @author Adriano Colaianni
 * @date 19 apr 2021
 */
public class PraticaLavorazione {
	private String codicePraticaEnteDelegato;
	private String tPraticaDescrizione;
	private String codicePraticaAppptr;
	private long prog;
	private Long tPraticaId;
	private String ufficio;
	private Date dataAttivazione;
	
	public String getCodicePraticaEnteDelegato() {
		return codicePraticaEnteDelegato;
	}
	public void setCodicePraticaEnteDelegato(String codicePraticaEnteDelegato) {
		this.codicePraticaEnteDelegato = codicePraticaEnteDelegato;
	}
	public String gettPraticaDescrizione() {
		return tPraticaDescrizione;
	}
	public void settPraticaDescrizione(String tPraticaDescrizione) {
		this.tPraticaDescrizione = tPraticaDescrizione;
	}
	public String getCodicePraticaAppptr() {
		return codicePraticaAppptr;
	}
	public void setCodicePraticaAppptr(String codicePraticaAppptr) {
		this.codicePraticaAppptr = codicePraticaAppptr;
	}
	public long getProg() {
		return prog;
	}
	public void setProg(long prog) {
		this.prog = prog;
	}
	public Long gettPraticaId() {
		return tPraticaId;
	}
	public void settPraticaId(Long tPraticaId) {
		this.tPraticaId = tPraticaId;
	}
	public Date getDataAttivazione() {
		return dataAttivazione;
	}
	public void setDataAttivazione(Date dataAttivazione) {
		this.dataAttivazione = dataAttivazione;
	}
	public String getUfficio() {
		return ufficio;
	}
	public void setUfficio(String ufficio) {
		this.ufficio = ufficio;
	}
	
}
