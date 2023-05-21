/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.dto;

/**
 * @author Adriano Colaianni
 * @date 21 apr 2021
 */
public class InteressePubblicoAss {
	
	private Long prog;
	private Long interessePubbTipoAssId ; //1=RICADE  2=NON RICADE
	private String comune ;
	private String codCat ;
	private String codIstat ;
	private String codiceVincolo ;
	private String oggetto ;
	private String codicePraticaAppptr ;
	private Long tPraticaId;
	
	public Long getProg() {
		return prog;
	}
	public void setProg(Long prog) {
		this.prog = prog;
	}
	public Long getInteressePubbTipoAssId() {
		return interessePubbTipoAssId;
	}
	public void setInteressePubbTipoAssId(Long interessePubbTipoAssId) {
		this.interessePubbTipoAssId = interessePubbTipoAssId;
	}
	public String getComune() {
		return comune;
	}
	public void setComune(String comune) {
		this.comune = comune;
	}
	public String getCodCat() {
		return codCat;
	}
	public void setCodCat(String codCat) {
		this.codCat = codCat;
	}
	public String getCodIstat() {
		return codIstat;
	}
	public void setCodIstat(String codIstat) {
		this.codIstat = codIstat;
	}
	public String getCodiceVincolo() {
		return codiceVincolo;
	}
	public void setCodiceVincolo(String codiceVincolo) {
		this.codiceVincolo = codiceVincolo;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	public String getCodicePraticaAppptr() {
		return codicePraticaAppptr;
	}
	public void setCodicePraticaAppptr(String codicePraticaAppptr) {
		this.codicePraticaAppptr = codicePraticaAppptr;
	}
	public Long gettPraticaId() {
		return tPraticaId;
	}
	public void settPraticaId(Long tPraticaId) {
		this.tPraticaId = tPraticaId;
	}
	
	
}
