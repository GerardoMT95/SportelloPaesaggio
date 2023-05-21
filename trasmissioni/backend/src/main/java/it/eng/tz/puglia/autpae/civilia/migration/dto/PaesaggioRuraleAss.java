/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.dto;

/**
 * Ricavata da CIVILIA_CS.TNO_PAESAGGIRURALI_MAP m,CIVILIA_CS.TNO_PAESAGGIRURALI_ASS
 * @author Adriano Colaianni
 * @date 21 apr 2021
 */
public class PaesaggioRuraleAss {
	
	private Long prog;
	private Long paesaggioRuraleTipoAssId ; //1=RICADE  2=NON RICADE
	private String comune ;
	private String codCat ;
	private String codIstat ;
	private String descPaesaggioRurale ;
	private String codicePraticaAppptr ;
	private Long tPraticaId ;
	private Long tnoPaesaggiRuraliMapId;
	
	
	public Long getTnoPaesaggiRuraliMapId() {
		return tnoPaesaggiRuraliMapId;
	}
	public void setTnoPaesaggiRuraliMapId(Long tnoPaesaggiRuraliMapId) {
		this.tnoPaesaggiRuraliMapId = tnoPaesaggiRuraliMapId;
	}
	public Long getProg() {
		return prog;
	}
	public void setProg(Long prog) {
		this.prog = prog;
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
	public String getDescPaesaggioRurale() {
		return descPaesaggioRurale;
	}
	public void setDescPaesaggioRurale(String descPaesaggioRurale) {
		this.descPaesaggioRurale = descPaesaggioRurale;
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
	public Long getPaesaggioRuraleTipoAssId() {
		return paesaggioRuraleTipoAssId;
	}
	public void setPaesaggioRuraleTipoAssId(Long paesaggioRuraleTipoAssId) {
		this.paesaggioRuraleTipoAssId = paesaggioRuraleTipoAssId;
	}
	
	

}
