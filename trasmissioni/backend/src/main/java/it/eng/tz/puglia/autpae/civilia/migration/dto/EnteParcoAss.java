/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.dto;

/**
 * Ricavata da TNO_ENTEPARCO_MAP m,CIVILIA_CS.TNO_ENTEPARCO_ASS
 * @author Adriano Colaianni
 * @date 21 apr 2021
 */
public class EnteParcoAss {
	
	private Long prog;
	private Long enteParcoTipoAssId ; //1=RICADE  2=NON RICADE
	private String comune ;
	private String codCat ;
	private String codIstat ;
	private String descParco ;
	private String mail ;
	private String codicePraticaAppptr ;
	private Long tPraticaId ;
	private Long tnoEnteParcoMapId ;
	
	
	
	public Long getTnoEnteParcoMapId() {
		return tnoEnteParcoMapId;
	}
	public void setTnoEnteParcoMapId(Long tnoEnteParcoMapId) {
		this.tnoEnteParcoMapId = tnoEnteParcoMapId;
	}
	public Long getProg() {
		return prog;
	}
	public void setProg(Long prog) {
		this.prog = prog;
	}
	public Long getEnteParcoTipoAssId() {
		return enteParcoTipoAssId;
	}
	public void setEnteParcoTipoAssId(Long enteParcoTipoAssId) {
		this.enteParcoTipoAssId = enteParcoTipoAssId;
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
	public String getDescParco() {
		return descParco;
	}
	public void setDescParco(String descParco) {
		this.descParco = descParco;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
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
